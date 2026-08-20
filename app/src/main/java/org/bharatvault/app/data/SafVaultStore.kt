/**
 * @file SafVaultStore.kt
 * @description §5.6/§6.6 sync-folder vault store via Storage Access Framework.
 *              Reads/writes ONLY vault.bvlt, vault.bvlt.tmp, vault.bvlt.bak and
 *              conflict siblings. Fully crash-proof with exception isolation.
 */
package org.bharatvault.app.data

// #region Imports
import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import androidx.documentfile.provider.DocumentFile
import java.io.FileNotFoundException
// #endregion

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

    override fun backupCurrent() {
        runCatching {
            val current = findChild(LocalVaultStore.VAULT_NAME) ?: return
            val bytes = context.contentResolver.openInputStream(current.uri)?.use { it.readBytes() } ?: return
            val bakName = "${LocalVaultStore.VAULT_NAME}.bak"
            findChild(bakName)?.delete()
            val bak = requireTree().createFile("application/octet-stream", bakName) ?: return
            context.contentResolver.openOutputStream(bak.uri)?.use { it.write(bytes); it.flush() }
        }
    }

    override fun writeAtomic(bytes: ByteArray, verify: (ByteArray) -> Boolean) {
        val dir = requireTree()
        val tmpName = "${LocalVaultStore.VAULT_NAME}.tmp"
        runCatching { findChild(tmpName)?.delete() }
        val tmp = dir.createFile("application/octet-stream", tmpName)
            ?: error("cannot create tmp vault file")
        context.contentResolver.openOutputStream(tmp.uri)?.use { out ->
            out.write(bytes)
            out.flush()
        } ?: error("cannot write tmp vault file")

        val reread = context.contentResolver.openInputStream(tmp.uri)?.use { it.readBytes() }
            ?: error("cannot re-read tmp vault file")
        check(reread.contentEquals(bytes) && verify(reread)) { "vault verify failed before rename" }

        val vaultName = LocalVaultStore.VAULT_NAME
        runCatching { findChild(vaultName)?.delete() }
        val renamed = try {
            DocumentsContract.renameDocument(context.contentResolver, tmp.uri, vaultName)
        } catch (_: Exception) {
            null
        }
        if (renamed == null) {
            runCatching { findChild(tmpName)?.delete() }
            val finalDoc = dir.createFile("application/octet-stream", vaultName)
                ?: error("cannot create vault file")
            context.contentResolver.openOutputStream(finalDoc.uri)?.use { it.write(bytes); it.flush() }
                ?: error("cannot write vault file")
        }
    }

    override fun conflictSiblings(): List<Pair<String, ByteArray>> = runCatching {
        safeListFiles()
            .filter { f ->
                val n = f.name ?: return@filter false
                n.startsWith("vault") && n.endsWith(".bvlt") && n != LocalVaultStore.VAULT_NAME
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
