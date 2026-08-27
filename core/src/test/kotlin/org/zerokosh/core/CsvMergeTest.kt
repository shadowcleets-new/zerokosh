package org.zerokosh.core

import kotlin.test.Test
import kotlin.test.assertEquals
import org.zerokosh.core.import.CsvImport
import org.zerokosh.core.model.PastSecret
import org.zerokosh.core.model.Record

/**
 * §5.11 says a duplicate row updates the existing record instead of adding a
 * second one. It said nothing about what "update" keeps, and the first
 * implementation rebuilt the record from the CSV row — so re-importing a Chrome
 * export stripped the institution, the template, and every field the export has
 * no column for. On device that turned "WhatsApp" into "Login" and would have
 * deleted a TOTP secret the user still needed.
 */
class CsvMergeTest {

    private val now = 1_751_875_200_000L

    private val existing = Record(
        uuid = "keep-me",
        template_id = "login",
        title = "WhatsApp",
        institution = "WhatsApp",
        fields = mapOf(
            "website" to "whatsapp.com",
            "username" to "arjun@example.com",
            "password" to "old-secret",
            "totp" to "JBSWY3DPEHPK3PXP",
        ),
        tags = listOf("chat"),
        favorite = true,
        created_at = now - 1000,
        modified_at = now - 1000,
        rev = 4,
        device_id = "old-device",
        history = listOf(PastSecret(k = "password", value = "older-secret", replaced_at = now - 2000)),
    )

    private val csv = """
        name,url,username,password
        WhatsApp,whatsapp.com,arjun@example.com,new-secret
        Zomato,zomato.com,arjun@example.com,zomato-secret
    """.trimIndent()

    private fun merged(): Record {
        val incoming = CsvImport.parseChromeCsv(csv, deviceId = "this-device", nowMs = now).records
        val (insert, update) = CsvImport.mergeWithExisting(incoming, listOf(existing))
        assertEquals(1, insert.size, "the row with no match is a new record")
        assertEquals(1, update.size, "site+username match updates instead of duplicating")
        return update.single()
    }

    @Test
    fun `an update rotates the password`() {
        assertEquals("new-secret", merged().fields["password"])
    }

    @Test
    fun `an update keeps what the export format cannot carry`() {
        val r = merged()
        assertEquals("keep-me", r.uuid)
        assertEquals("WhatsApp", r.institution, "institution is not a CSV column, so it must survive")
        assertEquals("JBSWY3DPEHPK3PXP", r.fields["totp"], "a 2FA secret must not be dropped")
        assertEquals(listOf("chat"), r.tags)
        assertEquals(true, r.favorite)
        assertEquals(4, r.rev)
        assertEquals(now - 1000, r.created_at)
        assertEquals(1, r.history.size, "older rotations stay recoverable")
    }

    @Test
    fun `an update stamps the importing device and time`() {
        val r = merged()
        assertEquals(now, r.modified_at)
        assertEquals("this-device", r.device_id)
    }
}
