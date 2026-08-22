/**
 * @file VaultRepository.kt
 * @description Single source of truth for vault state: unlock/lock lifecycle
 *              (§5.2), §4.4 persistence, §4.5 merge-before-write, S13 cooldown.
 *
 * [TABLE OF CONTENTS]
 * 1. STATE TYPES
 * 2. REPOSITORY — UNLOCK / CREATE / LOCK
 * 3. PERSISTENCE (merge + atomic write)
 * 4. RECORD MUTATIONS
 * 5. COOLDOWN (S13)
 */
package org.zerokosh.app.data

// #region Imports
import android.content.Context
import android.net.Uri
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.zerokosh.app.crypto.AndroidCrypto
import org.zerokosh.core.crypto.wipe
import org.zerokosh.core.model.Record
import org.zerokosh.core.model.VaultBody
import org.zerokosh.core.vault.UnlockResult
import org.zerokosh.core.vault.VaultEnvelope
import org.zerokosh.core.vault.VaultFileCodec
import org.zerokosh.core.vault.VaultMerge
import org.zerokosh.core.vault.VaultOperations
import java.util.UUID
// #endregion

// #region State types
sealed interface VaultState {
    data object NoVault : VaultState
    data object Locked : VaultState
    data object Unlocked : VaultState

    /** File failed to open and backup restore also failed. */
    data object Damaged : VaultState
}

enum class UnlockOutcome { SUCCESS, WRONG_CREDENTIAL, DAMAGED_RESTORED, DAMAGED, COOLDOWN }

/** What importing a .kosh file did. [Merged] counts are what the user is told. */
sealed interface ImportOutcome {
    data class Merged(val added: Int, val updated: Int, val conflicts: Int) : ImportOutcome

    /** The backup's own passphrase, which need not be this vault's. */
    data object WrongPassphrase : ImportOutcome
    data object NotAVault : ImportOutcome
    data object Locked : ImportOutcome
}
// #endregion

class VaultRepository(
    val crypto: AndroidCrypto,
    @Volatile var store: VaultStore,
    private val prefs: Prefs,
) {

    // #region Observable state
    private val _state = MutableStateFlow(if (store.exists()) VaultState.Locked else VaultState.NoVault)
    val state: StateFlow<VaultState> = _state

    private val _body = MutableStateFlow<VaultBody?>(null)
    val body: StateFlow<VaultBody?> = _body
    // #endregion

    // #region Session secrets (zeroed on lock, §5.2/§3.5)
    private var vaultKey: ByteArray? = null
    private var envelope: VaultEnvelope? = null
    private var lastSeenModifiedMs: Long = -1
    private var backupDoneThisSession = false
    private val ioMutex = Mutex()
    // #endregion

    // #region Unlock / create / lock
    suspend fun createVault(passphrase: ByteArray): String = withContext(Dispatchers.Default) {
        if (prefs.kdfMem == 0L) {
            val (ops, mem) = crypto.chooseKdfParams()
            prefs.kdfOps = ops
            prefs.kdfMem = mem
        }
        val created = VaultOperations.createVault(
            passphrase = passphrase,
            deviceId = prefs.deviceId,
            nowMs = System.currentTimeMillis(),
            crypto = crypto,
            ops = prefs.kdfOps,
            memBytes = prefs.kdfMem,
            initialBody = VaultBody(),
        )
        store.writeAtomic(created.fileBytes) { candidate ->
            VaultOperations.unlockWithPassphrase(candidate, passphrase, crypto) is UnlockResult.Success
        }
        // BV-25: deliberately do NOT open the session here. Flipping to Unlocked
        // swaps OnboardingFlow for MainScaffold in the root switch, which disposes
        // the onboarding NavHost and orphans S5 (Recovery Kit) and S6 (Quick
        // Unlock) — the user would never see their one-time Recovery Key.
        // completeOnboarding() opens the session once S6 is done.
        created.recoveryKeyFormatted
    }

    /**
     * S6 → S7. Opens the session that [createVault] prepared, re-deriving from
     * disk so a Recovery Key rotation during S5 can't leave a stale envelope
     * behind. Falls back to Locked rather than stranding the user in onboarding.
     */
    suspend fun completeOnboarding(passphrase: ByteArray): Boolean = withContext(Dispatchers.Default) {
        val bytes = store.read() ?: return@withContext false
        when (val result = VaultOperations.unlockWithPassphrase(bytes, passphrase, crypto)) {
            is UnlockResult.Success -> {
                applyUnlock(result)
                true
            }
            else -> {
                _state.value = VaultState.Locked
                false
            }
        }
    }

    suspend fun unlockWithPassphrase(passphrase: ByteArray): UnlockOutcome =
        attemptUnlock { bytes -> VaultOperations.unlockWithPassphrase(bytes, passphrase, crypto) }

    suspend fun unlockWithRecoveryKey(recovery: String): UnlockOutcome =
        attemptUnlock { bytes -> VaultOperations.unlockWithRecoveryKey(bytes, recovery, crypto) }

    private suspend fun attemptUnlock(unlock: (ByteArray) -> UnlockResult): UnlockOutcome =
        withContext(Dispatchers.Default) {
            if (cooldownRemainingSeconds() > 0) return@withContext UnlockOutcome.COOLDOWN
            val bytes = store.read() ?: return@withContext UnlockOutcome.DAMAGED
            when (val result = unlock(bytes)) {
                is UnlockResult.Success -> {
                    prefs.failedAttempts = 0
                    prefs.cooldownSeconds = 30
                    applyUnlock(result)
                    mergeConflictSiblings()
                    UnlockOutcome.SUCCESS
                }
                is UnlockResult.WrongCredential -> {
                    registerFailure()
                    UnlockOutcome.WRONG_CREDENTIAL
                }
                is UnlockResult.Corrupt -> restoreFromBackup(unlock)
            }
        }

    /** §11.2: "File damaged — restored from backup" using .bak. */
    private fun restoreFromBackup(unlock: (ByteArray) -> UnlockResult): UnlockOutcome {
        val bak = store.readBackup() ?: run { _state.value = VaultState.Damaged; return UnlockOutcome.DAMAGED }
        val result = unlock(bak)
        return if (result is UnlockResult.Success) {
            store.writeAtomic(bak) { true }
            prefs.failedAttempts = 0
            applyUnlock(result)
            UnlockOutcome.DAMAGED_RESTORED
        } else {
            _state.value = VaultState.Damaged
            UnlockOutcome.DAMAGED
        }
    }

    private fun applyUnlock(result: UnlockResult.Success) {
        vaultKey?.wipe()
        vaultKey = result.vaultKey
        envelope = result.envelope
        lastSeenModifiedMs = result.envelope.lastModifiedMs
        _body.value = result.body
        _state.value = VaultState.Unlocked
    }

    /** §5.2: zero keys, drop plaintext, back to S13. */
    fun lock() {
        org.zerokosh.app.nfc.PendingCard.clear()
        vaultKey?.wipe()
        vaultKey = null
        envelope = null
        _body.value = null
        backupDoneThisSession = false
        if (_state.value == VaultState.Unlocked) _state.value = VaultState.Locked
    }

    /** Applies a §3.4 biometric quick-unlock result obtained via BiometricPrompt. */
    suspend fun adoptBiometricUnlock(result: UnlockResult.Success) {
        prefs.failedAttempts = 0
        prefs.cooldownSeconds = 30
        applyUnlock(result)
        mergeConflictSiblings()
    }

    /** For H-field reveal re-auth (§5.4) when biometrics are unavailable. */
    suspend fun verifyPassphrase(passphrase: ByteArray): Boolean = withContext(Dispatchers.Default) {
        val bytes = store.read() ?: return@withContext false
        VaultOperations.unlockWithPassphrase(bytes, passphrase, crypto) is UnlockResult.Success
    }
    /** §3.3: re-wraps wrap_mk only. Caller must also disable quick-unlock (old MasterKey is stale). */
    suspend fun changePassphrase(current: ByteArray, new: ByteArray): Boolean = withContext(Dispatchers.Default) {
        ioMutex.withLock {
            val bytes = store.read() ?: return@withLock false
            val now = System.currentTimeMillis()
            val out = VaultOperations.changePassphrase(bytes, current, new, prefs.deviceId, now, crypto)
                ?: return@withLock false
            if (!backupDoneThisSession) {
                store.backupCurrent()
                backupDoneThisSession = true
            }
            store.writeAtomic(out) { candidate -> runCatching { VaultFileCodec.decode(candidate) }.isSuccess }
            envelope = VaultFileCodec.decode(out)
            lastSeenModifiedMs = now
            true
        }
    }

    /** S4 from Settings: new RecoveryKey; re-wraps wrap_rk only. Returned string is shown ONCE. */
    suspend fun rotateRecoveryKey(passphrase: ByteArray): String? = withContext(Dispatchers.Default) {
        ioMutex.withLock {
            val bytes = store.read() ?: return@withLock null
            val now = System.currentTimeMillis()
            val (out, formatted) = VaultOperations.rotateRecoveryKey(bytes, passphrase, prefs.deviceId, now, crypto)
                ?: return@withLock null
            if (!backupDoneThisSession) {
                store.backupCurrent()
                backupDoneThisSession = true
            }
            store.writeAtomic(out) { candidate -> runCatching { VaultFileCodec.decode(candidate) }.isSuccess }
            envelope = VaultFileCodec.decode(out)
            lastSeenModifiedMs = now
            formatted
        }
    }
    // #endregion

    // #region Persistence (§4.4 atomic write + §4.5 merge-before-write)
    private suspend fun persist(newBody: VaultBody) = ioMutex.withLock {
        withContext(Dispatchers.Default) {
            val key = vaultKey ?: error("locked")
            var env = envelope ?: error("locked")
            var bodyToWrite = newBody
            val now = System.currentTimeMillis()

            // §4.5.1: re-read before every save; merge if someone else wrote.
            val onDisk = store.read()
            if (onDisk != null) {
                val diskEnv = runCatching { VaultFileCodec.decode(onDisk) }.getOrNull()
                if (diskEnv != null && diskEnv.lastModifiedMs != lastSeenModifiedMs) {
                    val diskUnlock = VaultOperations.unlockWithMasterKeyless(diskEnv, key, crypto)
                    if (diskUnlock != null) {
                        bodyToWrite = VaultMerge.merge(newBody, diskUnlock, now)
                        env = diskEnv
                    }
                }
            }

            if (!backupDoneThisSession) {
                store.backupCurrent()
                backupDoneThisSession = true
            }
            val fileBytes = VaultOperations.save(env, bodyToWrite, key, prefs.deviceId, now, crypto)
            store.writeAtomic(fileBytes) { candidate ->
                runCatching { VaultFileCodec.decode(candidate) }.isSuccess
            }
            envelope = VaultFileCodec.decode(fileBytes)
            lastSeenModifiedMs = now
            _body.value = bodyToWrite
        }
    }

    /** §4.5.1: on foreground/unlock — pick up external edits (cloud sync). */
    suspend fun refreshFromDisk() {
        val key = vaultKey ?: return
        val current = _body.value ?: return
        withContext(Dispatchers.Default) {
            val onDisk = store.read() ?: return@withContext
            val diskEnv = runCatching { VaultFileCodec.decode(onDisk) }.getOrNull() ?: return@withContext
            if (diskEnv.lastModifiedMs == lastSeenModifiedMs) return@withContext
            val diskBody = VaultOperations.unlockWithMasterKeyless(diskEnv, key, crypto) ?: return@withContext
            val merged = VaultMerge.merge(current, diskBody, System.currentTimeMillis())
            envelope = diskEnv
            lastSeenModifiedMs = diskEnv.lastModifiedMs
            if (merged != diskBody) persist(merged) else _body.value = merged
        }
        mergeConflictSiblings()
    }

    /** §5.6/§6.6: Update active VaultStore between LocalVaultStore and SafVaultStore */
    fun updateSyncFolder(context: Context, treeUri: Uri?) {
        if (treeUri != null) {
            prefs.syncFolderUri = treeUri.toString()
            store = SafVaultStore(context, treeUri)
        } else {
            prefs.syncFolderUri = ""
            store = LocalVaultStore(context)
        }
    }

    /** Manually sync current encrypted vault bytes to the SAF backup directory */
    suspend fun manualBackup(context: Context): Boolean = ioMutex.withLock {
        withContext(Dispatchers.Default) {
            val uriStr = prefs.syncFolderUri
            if (uriStr.isEmpty()) return@withContext false
            val treeUri = Uri.parse(uriStr)
            val safStore = SafVaultStore(context, treeUri)
            val currentBytes = store.read() ?: return@withContext false
            safStore.writeAtomic(currentBytes) { candidate ->
                runCatching { VaultFileCodec.decode(candidate) }.isSuccess
            }
            true
        }
    }

    /**
     * §4.5 merge a .kosh file the user picked into this vault.
     *
     * The file is unlocked with its own passphrase, not this vault's — a backup
     * worth importing is often older than the last passphrase change, and it may
     * be a different vault entirely.
     *
     * Nothing is replaced. The merge is a union by uuid: newer modified_at wins,
     * and a losing side whose content differs survives as a conflict copy. An
     * import can therefore add records but never silently drop one.
     */
    suspend fun importVaultFile(fileBytes: ByteArray, passphrase: ByteArray): ImportOutcome =
        withContext(Dispatchers.Default) {
            val current = _body.value
            if (current == null || vaultKey == null) return@withContext ImportOutcome.Locked
            val unlocked = runCatching {
                VaultOperations.unlockWithPassphrase(fileBytes, passphrase, crypto)
            }.getOrElse { return@withContext ImportOutcome.NotAVault }
            when (unlocked) {
                is UnlockResult.WrongCredential -> ImportOutcome.WrongPassphrase
                is UnlockResult.Corrupt -> ImportOutcome.NotAVault
                is UnlockResult.Success -> {
                    val before = current.records.associateBy { it.uuid }
                    val merged = VaultMerge.merge(current, unlocked.body, System.currentTimeMillis())
                    val fresh = merged.records.filter { it.uuid !in before }
                    val outcome = ImportOutcome.Merged(
                        // A conflict copy is a new record too, but the user needs
                        // it called out — it means both sides had edits.
                        added = fresh.count { !it.title.endsWith(VaultMerge.CONFLICT_SUFFIX) },
                        updated = merged.records.count { r ->
                            before[r.uuid]?.let { it != r } == true
                        },
                        conflicts = fresh.count { it.title.endsWith(VaultMerge.CONFLICT_SUFFIX) },
                    )
                    if (merged != current) persist(merged)
                    outcome
                }
            }
        }

    /** §4.5.3: merge and remove `vault*.kosh` conflict siblings with our vault_uuid. */
    private suspend fun mergeConflictSiblings() {
        val key = vaultKey ?: return
        val env = envelope ?: return
        val siblings = store.conflictSiblings()
        if (siblings.isEmpty()) return
        var merged = _body.value ?: return
        var changed = false
        for ((name, bytes) in siblings) {
            val sibEnv = runCatching { VaultFileCodec.decode(bytes) }.getOrNull() ?: continue
            if (!sibEnv.vaultUuid.contentEquals(env.vaultUuid)) continue
            val sibBody = VaultOperations.unlockWithMasterKeyless(sibEnv, key, crypto) ?: continue
            merged = VaultMerge.merge(merged, sibBody, System.currentTimeMillis())
            store.deleteSibling(name)
            changed = true
        }
        if (changed) persist(merged)
    }
    // #endregion

    // #region Record mutations
    suspend fun upsertRecord(record: Record) {
        val current = _body.value ?: return
        val now = System.currentTimeMillis()
        val existing = current.records.firstOrNull { it.uuid == record.uuid }
        val prepared = if (existing == null) {
            record.copy(
                uuid = record.uuid.ifEmpty { UUID.randomUUID().toString() },
                created_at = now, modified_at = now, rev = 1, device_id = prefs.deviceId,
            )
        } else {
            record.copy(modified_at = now, rev = existing.rev + 1, device_id = prefs.deviceId) // §2.1: rev+1 every save
        }
        val records = if (existing == null) current.records + prepared
        else current.records.map { if (it.uuid == prepared.uuid) prepared else it }
        persist(
            current.copy(
                records = records,
                meta = current.meta.copy(device_names = current.meta.device_names + (prefs.deviceId to deviceName())),
            ),
        )
    }

    suspend fun deleteRecord(uuid: String) {
        val current = _body.value ?: return
        persist(VaultMerge.applyDeletion(current, uuid, System.currentTimeMillis()))
    }

    suspend fun toggleFavorite(uuid: String) {
        val record = _body.value?.records?.firstOrNull { it.uuid == uuid } ?: return
        upsertRecord(record.copy(favorite = !record.favorite))
    }

    private fun deviceName(): String = "${Build.MANUFACTURER} ${Build.MODEL}".trim()
    // #endregion

    // #region Cooldown (S13: 5 fails → 30 s, doubling; NEVER wipe)
    private fun registerFailure() {
        val fails = prefs.failedAttempts + 1
        prefs.failedAttempts = fails
        if (fails >= 5) {
            val seconds = prefs.cooldownSeconds
            prefs.cooldownUntilMs = System.currentTimeMillis() + seconds * 1000L
            prefs.cooldownSeconds = (seconds * 2).coerceAtMost(1800)
        }
    }

    fun cooldownRemainingSeconds(): Int =
        ((prefs.cooldownUntilMs - System.currentTimeMillis()) / 1000L).coerceAtLeast(0).toInt()
    // #endregion
}

/** Decrypt an already-parsed envelope with the session VaultKey (merge path). */
private fun VaultOperations.unlockWithMasterKeyless(
    envelope: VaultEnvelope,
    vaultKey: ByteArray,
    crypto: AndroidCrypto,
): VaultBody? {
    val plain = crypto.aeadDecrypt(envelope.bodyCiphertext, envelope.prefix, envelope.bodyNonce, vaultKey) ?: return null
    return runCatching { org.zerokosh.core.model.VaultJson.decodeFromString<VaultBody>(plain.decodeToString()) }
        .getOrNull()
        .also { plain.wipe() }
}
