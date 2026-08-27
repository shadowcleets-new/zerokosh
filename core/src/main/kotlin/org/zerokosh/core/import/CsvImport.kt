/**
 * @file CsvImport.kt
 * @description §5.11 import mapping: Chrome/Google CSV, Bitwarden JSON/CSV,
 *              LastPass CSV, KeePass CSV → login records. Pure JVM, no Android.
 */
package org.zerokosh.core.import

// #region Imports
import org.zerokosh.core.model.Record
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
                // Update the existing record; do not rebuild it from the CSV row.
                // Copying the other way round dropped everything an export format
                // has no column for: the institution (and with it the brand tile
                // and the institution grouping), the template, custom fields,
                // reminders, older password history, and any field the file did
                // not carry — a TOTP secret among them. An import that silently
                // removes a working 2FA code is worse than a duplicate row.
                update += match.copy(
                    title = rec.title.ifBlank { match.title },
                    fields = match.fields + rec.fields.filterValues { it.isNotBlank() },
                    modified_at = rec.modified_at,
                    device_id = rec.device_id,
                )
            } else {
                insert += rec
            }
        }
        return insert to update
    }

    // #region RFC-ish CSV parser (handles quoted fields)
    /**
     * The state a CSV scan carries: the row being built, the field being built,
     * and whether we are inside quotes.
     *
     * Held in a type rather than in seven locals so each step of the scan is a
     * short function instead of one nested `when` — the parser reads the same
     * either way, but only one of them can be followed at a glance.
     */
    private class CsvScan {
        private val rows = mutableListOf<List<String>>()
        private val current = mutableListOf<String>()
        private val field = StringBuilder()
        var inQuotes = false

        fun append(c: Char) {
            field.append(c)
        }

        fun endField() {
            current += field.toString()
            field.clear()
        }

        /** A row of nothing but empty fields is a blank line, not a record. */
        fun endRow() {
            endField()
            if (current.any { it.isNotBlank() }) rows += current.toList()
            current.clear()
        }

        fun finish(): List<List<String>> {
            if (field.isNotEmpty() || current.isNotEmpty()) endRow()
            return rows
        }
    }

    /** Returns the index to continue from — a doubled quote consumes two chars. */
    private fun CsvScan.stepQuoted(s: String, i: Int): Int {
        val c = s[i]
        if (c != '"') {
            append(c)
            return i + 1
        }
        // "" inside quotes is one literal quote; a lone " closes the field.
        if (i + 1 < s.length && s[i + 1] == '"') {
            append('"')
            return i + 2
        }
        inQuotes = false
        return i + 1
    }

    private fun CsvScan.stepPlain(c: Char) {
        when (c) {
            '"' -> inQuotes = true
            ',' -> endField()
            '\n' -> endRow()
            else -> append(c)
        }
    }

    fun parseCsv(text: String): List<List<String>> {
        val scan = CsvScan()
        val s = text.replace("\r\n", "\n").replace('\r', '\n')
        var i = 0
        while (i < s.length) {
            if (scan.inQuotes) {
                i = scan.stepQuoted(s, i)
            } else {
                scan.stepPlain(s[i])
                i++
            }
        }
        return scan.finish()
    }
    // #endregion
}
