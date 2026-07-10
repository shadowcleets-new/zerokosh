/**
 * @file CsvImport.kt
 * @description §5.11 import mapping: Chrome/Google CSV, Bitwarden JSON/CSV,
 *              LastPass CSV, KeePass CSV → login records. Pure JVM, no Android.
 */
package org.bharatvault.core.import

// #region Imports
import org.bharatvault.core.model.Record
import java.util.UUID
// #endregion

data class ImportPreview(
    val records: List<Record>,
    val sourceLabel: String,
)

object CsvImport {

    fun parseChromeCsv(text: String, deviceId: String, nowMs: Long): ImportPreview {
        val rows = parseCsv(text)
        if (rows.isEmpty()) return ImportPreview(emptyList(), "Chrome CSV")
        val header = rows.first().map { it.lowercase().trim() }
        val nameIdx = header.indexOfFirst { it in listOf("name", "title") }
        val urlIdx = header.indexOfFirst { it in listOf("url", "login_uri", "website") }
        val userIdx = header.indexOfFirst { it in listOf("username", "login_username", "user") }
        val passIdx = header.indexOfFirst { it in listOf("password", "login_password") }
        val noteIdx = header.indexOfFirst { it in listOf("note", "notes", "extra") }
        val records = rows.drop(1).mapNotNull { cols ->
            fun col(i: Int) = cols.getOrNull(i).orEmpty()
            val title = col(nameIdx).ifBlank { col(urlIdx).ifBlank { "Imported login" } }
            val fields = buildMap {
                col(urlIdx).takeIf { it.isNotBlank() }?.let { put("website", it) }
                col(userIdx).takeIf { it.isNotBlank() }?.let { put("username", it) }
                col(passIdx).takeIf { it.isNotBlank() }?.let { put("password", it) }
                col(noteIdx).takeIf { it.isNotBlank() }?.let { put("notes", it) }
            }
            if (fields.isEmpty() && title == "Imported login") return@mapNotNull null
            Record(
                uuid = UUID.randomUUID().toString(),
                template_id = "login",
                title = title,
                fields = fields,
                created_at = nowMs,
                modified_at = nowMs,
                device_id = deviceId,
            )
        }
        return ImportPreview(records, "Chrome / Google CSV")
    }

    fun parseBitwardenCsv(text: String, deviceId: String, nowMs: Long): ImportPreview {
        // Bitwarden CSV columns: folder,favorite,type,name,notes,fields,reprompt,login_uri,login_username,login_password,login_totp
        val rows = parseCsv(text)
        if (rows.isEmpty()) return ImportPreview(emptyList(), "Bitwarden CSV")
        val header = rows.first().map { it.lowercase().trim() }
        fun idx(vararg names: String) = header.indexOfFirst { it in names }
        val nameIdx = idx("name")
        val notesIdx = idx("notes")
        val uriIdx = idx("login_uri", "uri")
        val userIdx = idx("login_username", "username")
        val passIdx = idx("login_password", "password")
        val totpIdx = idx("login_totp", "totp")
        val records = rows.drop(1).mapNotNull { cols ->
            fun col(i: Int) = if (i >= 0) cols.getOrNull(i).orEmpty() else ""
            val title = col(nameIdx).ifBlank { return@mapNotNull null }
            val fields = buildMap {
                col(uriIdx).takeIf { it.isNotBlank() }?.let { put("website", it) }
                col(userIdx).takeIf { it.isNotBlank() }?.let { put("username", it) }
                col(passIdx).takeIf { it.isNotBlank() }?.let { put("password", it) }
                col(totpIdx).takeIf { it.isNotBlank() }?.let { put("totp", it) }
                col(notesIdx).takeIf { it.isNotBlank() }?.let { put("notes", it) }
            }
            Record(
                uuid = UUID.randomUUID().toString(),
                template_id = "login",
                title = title,
                fields = fields,
                created_at = nowMs,
                modified_at = nowMs,
                device_id = deviceId,
            )
        }
        return ImportPreview(records, "Bitwarden CSV")
    }

    fun parseLastPassCsv(text: String, deviceId: String, nowMs: Long): ImportPreview {
        // url,username,password,totp,extra,name,grouping,fav
        val rows = parseCsv(text)
        if (rows.isEmpty()) return ImportPreview(emptyList(), "LastPass CSV")
        val header = rows.first().map { it.lowercase().trim() }
        fun idx(vararg names: String) = header.indexOfFirst { it in names }
        val urlIdx = idx("url")
        val userIdx = idx("username")
        val passIdx = idx("password")
        val totpIdx = idx("totp")
        val extraIdx = idx("extra", "notes")
        val nameIdx = idx("name")
        val records = rows.drop(1).mapNotNull { cols ->
            fun col(i: Int) = if (i >= 0) cols.getOrNull(i).orEmpty() else ""
            val title = col(nameIdx).ifBlank { col(urlIdx).ifBlank { return@mapNotNull null } }
            val fields = buildMap {
                col(urlIdx).takeIf { it.isNotBlank() }?.let { put("website", it) }
                col(userIdx).takeIf { it.isNotBlank() }?.let { put("username", it) }
                col(passIdx).takeIf { it.isNotBlank() }?.let { put("password", it) }
                col(totpIdx).takeIf { it.isNotBlank() }?.let { put("totp", it) }
                col(extraIdx).takeIf { it.isNotBlank() }?.let { put("notes", it) }
            }
            Record(
                uuid = UUID.randomUUID().toString(),
                template_id = "login",
                title = title,
                fields = fields,
                created_at = nowMs,
                modified_at = nowMs,
                device_id = deviceId,
            )
        }
        return ImportPreview(records, "LastPass CSV")
    }

    fun parseKeePassCsv(text: String, deviceId: String, nowMs: Long): ImportPreview {
        // Account,Login Name,Password,Web Site,Comments
        val rows = parseCsv(text)
        if (rows.isEmpty()) return ImportPreview(emptyList(), "KeePass CSV")
        val header = rows.first().map { it.lowercase().trim() }
        fun idx(vararg names: String) = header.indexOfFirst { it in names }
        val nameIdx = idx("account", "title", "name")
        val userIdx = idx("login name", "username", "user name")
        val passIdx = idx("password")
        val urlIdx = idx("web site", "url", "website")
        val notesIdx = idx("comments", "notes")
        val records = rows.drop(1).mapNotNull { cols ->
            fun col(i: Int) = if (i >= 0) cols.getOrNull(i).orEmpty() else ""
            val title = col(nameIdx).ifBlank { col(urlIdx).ifBlank { return@mapNotNull null } }
            val fields = buildMap {
                col(urlIdx).takeIf { it.isNotBlank() }?.let { put("website", it) }
                col(userIdx).takeIf { it.isNotBlank() }?.let { put("username", it) }
                col(passIdx).takeIf { it.isNotBlank() }?.let { put("password", it) }
                col(notesIdx).takeIf { it.isNotBlank() }?.let { put("notes", it) }
            }
            Record(
                uuid = UUID.randomUUID().toString(),
                template_id = "login",
                title = title,
                fields = fields,
                created_at = nowMs,
                modified_at = nowMs,
                device_id = deviceId,
            )
        }
        return ImportPreview(records, "KeePass CSV")
    }

    /** Auto-detect format from header row and parse. */
    fun parseAuto(text: String, deviceId: String, nowMs: Long): ImportPreview {
        val first = text.lineSequence().firstOrNull()?.lowercase().orEmpty()
        return when {
            "login_uri" in first || "login_username" in first -> parseBitwardenCsv(text, deviceId, nowMs)
            first.startsWith("url,username,password") || "grouping" in first -> parseLastPassCsv(text, deviceId, nowMs)
            "login name" in first || "web site" in first || "account," in first -> parseKeePassCsv(text, deviceId, nowMs)
            else -> parseChromeCsv(text, deviceId, nowMs)
        }
    }

    /**
     * §5.11: duplicates (same url+username) update instead of duplicate.
     * Returns (toInsert, toUpdateExistingUuid→newRecord).
     */
    fun mergeWithExisting(
        incoming: List<Record>,
        existing: List<Record>,
    ): Pair<List<Record>, List<Record>> {
        val existingLogins = existing.filter { it.template_id == "login" }
        val insert = mutableListOf<Record>()
        val update = mutableListOf<Record>()
        for (rec in incoming) {
            val url = rec.fields["website"].orEmpty()
            val user = rec.fields["username"].orEmpty()
            val match = existingLogins.firstOrNull {
                it.fields["website"].orEmpty() == url && it.fields["username"].orEmpty() == user &&
                    (url.isNotBlank() || user.isNotBlank())
            }
            if (match != null) {
                update += rec.copy(
                    uuid = match.uuid,
                    created_at = match.created_at,
                    rev = match.rev,
                    favorite = match.favorite,
                    tags = match.tags,
                )
            } else {
                insert += rec
            }
        }
        return insert to update
    }

    // #region RFC-ish CSV parser (handles quoted fields)
    fun parseCsv(text: String): List<List<String>> {
        val rows = mutableListOf<List<String>>()
        val current = mutableListOf<String>()
        val field = StringBuilder()
        var inQuotes = false
        var i = 0
        val s = text.replace("\r\n", "\n").replace('\r', '\n')
        while (i < s.length) {
            val c = s[i]
            when {
                inQuotes -> when (c) {
                    '"' -> if (i + 1 < s.length && s[i + 1] == '"') {
                        field.append('"'); i++
                    } else {
                        inQuotes = false
                    }
                    else -> field.append(c)
                }
                c == '"' -> inQuotes = true
                c == ',' -> {
                    current += field.toString(); field.clear()
                }
                c == '\n' -> {
                    current += field.toString(); field.clear()
                    if (current.any { it.isNotBlank() }) rows += current.toList()
                    current.clear()
                }
                else -> field.append(c)
            }
            i++
        }
        if (field.isNotEmpty() || current.isNotEmpty()) {
            current += field.toString()
            if (current.any { it.isNotBlank() }) rows += current
        }
        return rows
    }
    // #endregion
}
