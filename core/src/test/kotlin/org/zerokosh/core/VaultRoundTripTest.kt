package org.zerokosh.core

// #region Imports
import org.zerokosh.core.crypto.CryptoProvider
import org.zerokosh.core.vault.UnlockResult
import org.zerokosh.core.vault.VaultOperations
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue
// #endregion

/** §11.2 vault round-trip gate. Uses reduced KDF memory (32 MiB floor, §3.2) to keep the suite fast. */
class VaultRoundTripTest {

    private val crypto = LazySodiumTestCrypto.INSTANCE
    private val mem = CryptoProvider.MIN_MEM_BYTES

    private fun createFixtureFile() = VaultOperations.createVault(
        passphrase = FixtureVault.PASSPHRASE.toByteArray(),
        deviceId = FixtureVault.DEVICE_ID,
        nowMs = FixtureVault.TS,
        crypto = crypto,
        memBytes = mem,
        initialBody = FixtureVault.body,
    )

    @Test
    fun `create - save - reopen with passphrase - records byte-compare`() {
        val created = createFixtureFile()
        val result = VaultOperations.unlockWithPassphrase(created.fileBytes, FixtureVault.PASSPHRASE.toByteArray(), crypto)
        assertIs<UnlockResult.Success>(result)
        assertEquals(FixtureVault.body, result.body) // full structural equality: records, attachments, meta

        // edit + save + reopen
        val edited = result.body.copy(
            records = result.body.records.map {
                if (it.uuid.startsWith("2")) it.copy(fields = it.fields + ("password" to "NewPass@456"), rev = it.rev + 1, modified_at = FixtureVault.TS + 1000) else it
            },
        )
        val saved = VaultOperations.save(result.envelope, edited, result.vaultKey, FixtureVault.DEVICE_ID, FixtureVault.TS + 1000, crypto)
        val reopened = VaultOperations.unlockWithPassphrase(saved, FixtureVault.PASSPHRASE.toByteArray(), crypto)
        assertIs<UnlockResult.Success>(reopened)
        assertEquals(edited, reopened.body)
        assertEquals(FixtureVault.TS + 1000, reopened.envelope.lastModifiedMs)
    }

    @Test
    fun `reopen with recovery key`() {
        val created = createFixtureFile()
        val result = VaultOperations.unlockWithRecoveryKey(created.fileBytes, created.recoveryKeyFormatted, crypto)
        assertIs<UnlockResult.Success>(result)
        assertEquals(FixtureVault.body, result.body)
    }

    @Test
    fun `wrong passphrase is WrongCredential - never a crypto error`() {
        val created = createFixtureFile()
        val result = VaultOperations.unlockWithPassphrase(created.fileBytes, "wrong-passphrase-x".toByteArray(), crypto)
        assertIs<UnlockResult.WrongCredential>(result)
    }

    @Test
    fun `corrupting one ciphertext byte fails cleanly`() {
        val created = createFixtureFile()
        val corrupted = created.fileBytes.copyOf()
        val i = corrupted.size - 20 // inside body ciphertext
        corrupted[i] = (corrupted[i].toInt() xor 0x01).toByte()
        val result = VaultOperations.unlockWithPassphrase(corrupted, FixtureVault.PASSPHRASE.toByteArray(), crypto)
        assertIs<UnlockResult.Corrupt>(result) // UI: "File damaged — restored from backup" using .bak
    }

    @Test
    fun `change passphrase - old fails - new works - recovery still works`() {
        val created = createFixtureFile()
        val newFile = VaultOperations.changePassphrase(
            created.fileBytes, FixtureVault.PASSPHRASE.toByteArray(), "new-passphrase-9876".toByteArray(),
            FixtureVault.DEVICE_ID, FixtureVault.TS + 2000, crypto,
        )!!
        assertIs<UnlockResult.WrongCredential>(VaultOperations.unlockWithPassphrase(newFile, FixtureVault.PASSPHRASE.toByteArray(), crypto))
        assertIs<UnlockResult.Success>(VaultOperations.unlockWithPassphrase(newFile, "new-passphrase-9876".toByteArray(), crypto))
        assertIs<UnlockResult.Success>(VaultOperations.unlockWithRecoveryKey(newFile, created.recoveryKeyFormatted, crypto))
    }

    @Test
    fun `reset passphrase via MasterKey - old fails - new works - recovery still works`() {
        // The quick-unlock path: the Keystore hands back the MasterKey, and the
        // forgotten passphrase is never needed. Same guarantees as the ordinary
        // change, reached without the thing the user has lost.
        val created = createFixtureFile()
        val masterKey = VaultOperations.deriveMasterKey(
            created.fileBytes, FixtureVault.PASSPHRASE.toByteArray(), crypto,
        )
        val newFile = VaultOperations.changePassphraseWithMasterKey(
            created.fileBytes, masterKey, "reset-passphrase-4321".toByteArray(),
            FixtureVault.DEVICE_ID, FixtureVault.TS + 3000, crypto,
        )!!
        assertIs<UnlockResult.WrongCredential>(
            VaultOperations.unlockWithPassphrase(newFile, FixtureVault.PASSPHRASE.toByteArray(), crypto),
        )
        assertIs<UnlockResult.Success>(
            VaultOperations.unlockWithPassphrase(newFile, "reset-passphrase-4321".toByteArray(), crypto),
        )
        // The Recovery Key is wrapped separately, so resetting the passphrase
        // must not invalidate a kit the user saved months ago.
        assertIs<UnlockResult.Success>(
            VaultOperations.unlockWithRecoveryKey(newFile, created.recoveryKeyFormatted, crypto),
        )
    }

    @Test
    fun `reset passphrase rejects the wrong MasterKey`() {
        val created = createFixtureFile()
        val wrong = ByteArray(32) { 7 }
        assertEquals(
            null,
            VaultOperations.changePassphraseWithMasterKey(
                created.fileBytes, wrong, "irrelevant-passphrase".toByteArray(),
                FixtureVault.DEVICE_ID, FixtureVault.TS + 4000, crypto,
            ),
        )
    }

    @Test
    fun `reset passphrase leaves the old MasterKey stale`() {
        // Why the caller must re-enrol quick unlock afterwards: the blob in the
        // Keystore still holds the pre-reset MasterKey, which no longer unwraps
        // anything. Silently leaving it would lock the user out on next launch.
        val created = createFixtureFile()
        val masterKey = VaultOperations.deriveMasterKey(
            created.fileBytes, FixtureVault.PASSPHRASE.toByteArray(), crypto,
        )
        val newFile = VaultOperations.changePassphraseWithMasterKey(
            created.fileBytes, masterKey, "another-passphrase-8888".toByteArray(),
            FixtureVault.DEVICE_ID, FixtureVault.TS + 5000, crypto,
        )!!
        assertIs<UnlockResult.WrongCredential>(
            VaultOperations.unlockWithMasterKey(newFile, masterKey, crypto),
        )
    }

    @Test
    fun `header params are honored by readers`() {
        // create at 32 MiB, reopen — reader must use header params, not defaults
        val created = createFixtureFile()
        val envelope = org.zerokosh.core.vault.VaultFileCodec.decode(created.fileBytes)
        assertEquals(mem, envelope.header.mem)
        assertEquals(3, envelope.header.ops)
    }

    /**
     * §11.2 cross-platform gate: commits fixtures/vault_android.kosh (created
     * once; subsequent runs verify it still opens with zero data loss).
     */
    @Test
    fun `fixture vault_android kosh exists and opens losslessly`() {
        val fixture = File("../fixtures/vault_android.kosh").canonicalFile
        if (!fixture.exists()) {
            // Full 64 MiB default params for the committed artifact.
            val created = VaultOperations.createVault(
                passphrase = FixtureVault.PASSPHRASE.toByteArray(),
                deviceId = FixtureVault.DEVICE_ID,
                nowMs = FixtureVault.TS,
                crypto = crypto,
                initialBody = FixtureVault.body,
            )
            fixture.writeBytes(created.fileBytes)
            println("fixture written: ${fixture.path} — recovery key (fixture only): ${created.recoveryKeyFormatted}")
        }
        val result = VaultOperations.unlockWithPassphrase(fixture.readBytes(), FixtureVault.PASSPHRASE.toByteArray(), crypto)
        assertIs<UnlockResult.Success>(result)
        assertEquals(FixtureVault.body, result.body)
        assertTrue(result.envelope.header.mem >= CryptoProvider.MIN_MEM_BYTES)
    }
}
