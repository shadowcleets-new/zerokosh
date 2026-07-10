/**
 * @file SafVaultStore.kt
 * @description §5.6/§6.6 sync-folder vault store via Storage Access Framework.
 *              Reads/writes ONLY vault.bvlt, vault.bvlt.tmp, vault.bvlt.bak and
 *              conflict siblings. Atomic write uses DocumentFile create +
 *              DocumentsContract.renameDocument (NEVER in-place, §4.4).
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
        get() = DocumentFile.fromTreeUri(context, treeUri)

    private fun findChild(name: String): DocumentFile? =
        tree?.listFiles()?.firstOrNull { it.name == name }

    private fun requireTree(): DocumentFile =
        tree ?: error("sync folder unavailable")

    override fun exists(): Boolean = findChild(LocalVaultStore.VAULT_NAME)?.exists() == true

    override fun read(): ByteArray? {
        val doc = findChild(LocalVaultStore.VAULT_NAME) ?: return null
        return context.contentResolver.openInputStream(doc.uri)?.use { it.readBytes() }
    }

    override fun readBackup(): ByteArray? {
        val doc = findChild("${LocalVaultStore.VAULT_NAME}.bak") ?: return null
        return context.contentResolver.openInputStream(doc.uri)?.use { it.readBytes() }
    }

    override fun backupCurrent() {
        val current = findChild(LocalVaultStore.VAULT_NAME) ?: return
        val bytes = context.contentResolver.openInputStream(current.uri)?.use { it.readBytes() } ?: return
        val bakName = "${LocalVaultStore.VAULT_NAME}.bak"
        findChild(bakName)?.delete()
        val bak = requireTree().createFile("application/octet-stream", bakName) ?: return
        context.contentResolver.openOutputStream(bak.uri)?.use { it.write(bytes); it.flush() }
    }

    override fun writeAtomic(bytes: ByteArray, verify: (ByteArray) -> Boolean) {
        val dir = requireTree()
        val tmpName = "${LocalVaultStore.VAULT_NAME}.tmp"
        findChild(tmpName)?.delete()
        val tmp = dir.createFile("application/octet-stream", tmpName)
            ?: error("cannot create tmp vault file")
        context.contentResolver.openOutputStream(tmp.uri)?.use { out ->
            out.write(bytes)
            out.flush()
        } ?: error("cannot write tmp vault file")

        val reread = context.contentResolver.openInputStream(tmp.uri)?.use { it.readBytes() }
            ?: error("cannot re-read tmp vault file")
        check(reread.contentEquals(bytes) && verify(reread)) { "vault verify failed before rename" }

        // §4.4 + §6.6: rename tmp over vault.bvlt via DocumentsContract
        val vaultName = LocalVaultStore.VAULT_NAME
        findChild(vaultName)?.delete()
        val renamed = try {
            DocumentsContract.renameDocument(context.contentResolver, tmp.uri, vaultName)
        } catch (_: FileNotFoundException) {
            null
        } catch (_: UnsupportedOperationException) {
            null
        }
        if (renamed == null) {
            // Fallback providers that refuse rename: write final file then delete tmp
            findChild(tmpName)?.delete()
            val finalDoc = dir.createFile("application/octet-stream", vaultName)
                ?: error("cannot create vault file")
            context.contentResolver.openOutputStream(finalDoc.uri)?.use { it.write(bytes); it.flush() }
                ?: error("cannot write vault file")
        }
    }

    override fun conflictSiblings(): List<Pair<String, ByteArray>> {
        val dir = tree ?: return emptyList()
        return dir.listFiles()
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
    }

    override fun deleteSibling(name: String) {
        findChild(name)?.delete()
    }

    companion object {
        fun fromUriString(context: Context, uriString: String): SafVaultStore? {
            if (uriString.isBlank()) return null
            return runCatching { SafVaultStore(context, Uri.parse(uriString)) }.getOrNull()
        }
    }
}
