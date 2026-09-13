package org.zerokosh.app.data

// #region Imports
import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption
// #endregion

/**
 * Where the encrypted vault file lives. LocalVaultStore = app-private storage
 * (§5.6 default, "Not backed up" badge). The SAF sync-folder store (M3)
 * implements the same contract.
 */
interface VaultStore {
    fun exists(): Boolean
    fun read(): ByteArray?

    /** §4.4 atomic write: tmp → fsync → verify-by-reopen → rename. NEVER in place. */
    fun writeAtomic(bytes: ByteArray, verify: (ByteArray) -> Boolean)

    /**
     * Copy the current file to vault.kosh.bak — before the first write of a
     * session (§4.4).
     *
     * @return whether a backup now exists. The caller records the session's
     *   backup as done only on true: a store that silently failed here used to
     *   be remembered as having succeeded, so the session ran on with none.
     */
    fun backupCurrent(): Boolean

    /** §4.5.3: sibling `vault*.kosh` conflict files (cloud "conflicted copy" artifacts). */
    fun conflictSiblings(): List<Pair<String, ByteArray>>
    fun deleteSibling(name: String)

    fun readBackup(): ByteArray?
}

class LocalVaultStore(context: Context) : VaultStore {

    private val dir: File = context.filesDir
    private val vault = File(dir, VAULT_NAME)
    private val tmp = File(dir, "$VAULT_NAME.tmp")
    private val bak = File(dir, "$VAULT_NAME.bak")

    override fun exists(): Boolean = vault.exists()

    override fun read(): ByteArray? = if (vault.exists()) vault.readBytes() else null

    override fun readBackup(): ByteArray? = if (bak.exists()) bak.readBytes() else null

    override fun backupCurrent(): Boolean {
        if (!vault.exists()) return true // nothing to copy yet
        // Staged and moved, not copied over: REPLACE_EXISTING truncates the old
        // backup first, so a crash mid-copy left a half-written .bak.
        val staging = File(dir, "$VAULT_NAME.bak.tmp")
        return runCatching {
            Files.copy(vault.toPath(), staging.toPath(), StandardCopyOption.REPLACE_EXISTING)
            Files.move(staging.toPath(), bak.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
            true
        }.getOrElse {
            staging.delete()
            false
        }
    }

    override fun writeAtomic(bytes: ByteArray, verify: (ByteArray) -> Boolean) {
        FileOutputStream(tmp).use { out ->
            out.write(bytes)
            out.flush()
            out.fd.sync()
        }
        val reread = tmp.readBytes()
        check(reread.contentEquals(bytes) && verify(reread)) { "vault verify failed before rename" }
        Files.move(tmp.toPath(), vault.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
    }

    override fun conflictSiblings(): List<Pair<String, ByteArray>> =
        dir.listFiles { f ->
            f.name.startsWith("vault") && f.name.endsWith(".kosh") && f.name != VAULT_NAME
        }?.map { it.name to it.readBytes() } ?: emptyList()

    override fun deleteSibling(name: String) {
        // ponytail: names come from our own listing above, not user input
        File(dir, name).delete()
    }

    companion object {
        const val VAULT_NAME = "vault.kosh"
    }
}
