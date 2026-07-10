package org.bharatvault.app.data

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

    /** Copy current file to vault.bvlt.bak — called before the first write of a session (§4.4). */
    fun backupCurrent()

    /** §4.5.3: sibling `vault*.bvlt` conflict files (cloud "conflicted copy" artifacts). */
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

    override fun backupCurrent() {
        if (vault.exists()) {
            Files.copy(vault.toPath(), bak.toPath(), StandardCopyOption.REPLACE_EXISTING)
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
            f.name.startsWith("vault") && f.name.endsWith(".bvlt") && f.name != VAULT_NAME
        }?.map { it.name to it.readBytes() } ?: emptyList()

    override fun deleteSibling(name: String) {
        // ponytail: names come from our own listing above, not user input
        File(dir, name).delete()
    }

    companion object {
        const val VAULT_NAME = "vault.bvlt"
    }
}
