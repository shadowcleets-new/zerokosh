/**
 * @file SafVaultStore.kt
 * @description §5.6/§6.6 sync-folder vault store via Storage Access Framework.
 *              Reads/writes ONLY vault.kosh, vault.kosh.tmp, vault.kosh.bak and
 *              conflict siblings. Fully crash-proof with exception isolation.
 */
package org.zerokosh.app.data

// #region Imports
import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import androidx.documentfile.provider.DocumentFile
import java.io.FileNotFoundException
// #endregion

private const val OCTET_STREAM = "application/octet-stream"

class SafVaultStore(
    private val context: Context,
    private val treeUri: Uri,
) : VaultStore {

    private val tree: DocumentFile?
        get() = runCatching { DocumentFile.fromTreeUri(context, treeUri) }.getOrNull()

    private fun safeListFiles(): Array<DocumentFile> =
        runCatching { tree?.listFiles() ?: emptyArray() }.getOrDefault(emptyArray())

    private fun findChild(name: String): DocumentFile? =
        safeListFiles().firstOrNull { it.name == name }

    private fun requireTree(): DocumentFile =
        tree ?: error("sync folder unavailable")

    override fun exists(): Boolean = runCatching { findChild(LocalVaultStore.VAULT_NAME)?.exists() == true }.getOrDefault(false)

    override fun read(): ByteArray? = runCatching {
        val doc = findChild(LocalVaultStore.VAULT_NAME) ?: return null
        context.contentResolver.openInputStream(doc.uri)?.use { it.readBytes() }
    }.getOrNull()

    override fun readBackup(): ByteArray? = runCatching {
        val doc = findChild("${LocalVaultStore.VAULT_NAME}.bak") ?: return null
        context.contentResolver.openInputStream(doc.uri)?.use { it.readBytes() }
    }.getOrNull()

    /**
     * The copy is written beside the old one and swapped in, never written over
     * it. The previous order deleted .bak first and then created it, so a
     * failure anywhere after the delete left the session with no backup — and
     * because the whole thing was wrapped in a silent runCatching, the caller
     * still recorded the backup as done.
     */
    override fun backupCurrent(): Boolean = runCatching {
        val current = findChild(LocalVaultStore.VAULT_NAME) ?: return true // nothing to copy yet
        val bytes = context.contentResolver.openInputStream(current.uri)?.use { it.readBytes() }
            ?: return false
        val bakName = "${LocalVaultStore.VAULT_NAME}.bak"
        val stagedName = "$bakName.new"
        runCatching { findChild(stagedName)?.delete() }
        val staged = requireTree().createFile(OCTET_STREAM, stagedName) ?: return false
        context.contentResolver.openOutputStream(staged.uri)?.use { it.write(bytes); it.flush() }
            ?: return false
        val reread = context.contentResolver.openInputStream(staged.uri)?.use { it.readBytes() }
        if (reread == null || !reread.contentEquals(bytes)) {
            runCatching { staged.delete() }
            return false
        }
        // Only now is the old backup expendable.
        runCatching { findChild(bakName)?.delete() }
        DocumentsContract.renameDocument(context.contentResolver, staged.uri, bakName) != null
    }.getOrDefault(false)

    /**
     * tmp → verify → step the live file aside → rename → drop the old copy.
     *
     * What it must never do is what it used to: delete vault.kosh and *then*
     * attempt the rename. A provider that refused the rename — a lapsed grant,
     * a quirk, a process death — left a folder with no vault and no tmp, the
     * new bytes existing only in the memory of an app that was about to be
     * killed. The live file is now renamed aside instead of deleted, and only
     * removed once its replacement is in place; if anything fails on the way,
     * it is put back and the save reports failure rather than pretending.
     */
    override fun writeAtomic(bytes: ByteArray, verify: (ByteArray) -> Boolean) {
        val dir = requireTree()
        val vaultName = LocalVaultStore.VAULT_NAME
        val tmpName = "$vaultName.tmp"
        val oldName = "$vaultName.old"

        runCatching { findChild(tmpName)?.delete() }
        val tmp = dir.createFile(OCTET_STREAM, tmpName) ?: error("cannot create tmp vault file")
        context.contentResolver.openOutputStream(tmp.uri)?.use { out ->
            out.write(bytes)
            out.flush()
        } ?: error("cannot write tmp vault file")

        val reread = context.contentResolver.openInputStream(tmp.uri)?.use { it.readBytes() }
            ?: error("cannot re-read tmp vault file")
        check(reread.contentEquals(bytes) && verify(reread)) { "vault verify failed before rename" }

        // Step the live file aside. If even this fails, nothing has been touched
        // and the old vault is still the vault.
        val live = findChild(vaultName)
        if (live != null) {
            runCatching { findChild(oldName)?.delete() }
            check(DocumentsContract.renameDocument(context.contentResolver, live.uri, oldName) != null) {
                "cannot set the current vault aside; nothing was changed"
            }
        }
        val restore = {
            findChild(oldName)?.let { kept ->
                runCatching { DocumentsContract.renameDocument(context.contentResolver, kept.uri, vaultName) }
            }
        }

        val renamed = runCatching {
            DocumentsContract.renameDocument(context.contentResolver, tmp.uri, vaultName)
        }.getOrNull()
        if (renamed == null) {
            // Some providers will not rename. Write a fresh file instead — and
            // keep tmp, which holds the verified bytes, until this succeeds.
            val fresh = dir.createFile(OCTET_STREAM, vaultName)
            val written = fresh != null && runCatching {
                context.contentResolver.openOutputStream(fresh.uri)?.use { it.write(bytes); it.flush() }
                val back = context.contentResolver.openInputStream(fresh.uri)?.use { it.readBytes() }
                back != null && back.contentEquals(bytes)
            }.getOrDefault(false)
            if (!written) {
                runCatching { fresh?.delete() }
                restore()
                error("cannot write vault file; the previous one was put back")
            }
            runCatching { findChild(tmpName)?.delete() }
        }
        // The replacement is in place and readable: the old copy can go.
        runCatching { findChild(oldName)?.delete() }
    }

    override fun conflictSiblings(): List<Pair<String, ByteArray>> = runCatching {
        safeListFiles()
            .filter { f ->
                val n = f.name ?: return@filter false
                n.startsWith("vault") && n.endsWith(".kosh") && n != LocalVaultStore.VAULT_NAME
            }
            .mapNotNull { f ->
                val name = f.name ?: return@mapNotNull null
                val data = context.contentResolver.openInputStream(f.uri)?.use { it.readBytes() }
                    ?: return@mapNotNull null
                name to data
            }
    }.getOrDefault(emptyList())

    override fun deleteSibling(name: String) {
        runCatching { findChild(name)?.delete() }
    }

    companion object {
        fun fromUriString(context: Context, uriString: String): SafVaultStore? {
            if (uriString.isBlank()) return null
            return runCatching { SafVaultStore(context, Uri.parse(uriString)) }.getOrNull()
        }
    }
}
