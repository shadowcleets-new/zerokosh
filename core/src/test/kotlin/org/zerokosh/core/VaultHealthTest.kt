package org.zerokosh.core

import org.zerokosh.core.health.HealthKind
import org.zerokosh.core.health.reviewVault
import org.zerokosh.core.model.Record
import org.zerokosh.core.model.Template
import org.zerokosh.core.model.TemplateField
import org.zerokosh.core.passphrase.CommonPasswords
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class VaultHealthTest {

    private val login = Template(
        id = "login",
        icon = "key",
        fields = listOf(
            TemplateField(k = "username", t = "TEXT", s = "L"),
            TemplateField(k = "password", t = "SECRET", s = "H"),
        ),
    )
    private val card = Template(
        id = "card",
        icon = "card",
        fields = listOf(
            TemplateField(k = "atm_pin", t = "SECRET", s = "H"),
            TemplateField(k = "expiry", t = "DATE", s = "L"),
        ),
    )
    private val templates = mapOf("login" to login, "card" to card)

    // 2026-08-27T00:00:00Z
    private val now = 1_787_788_800_000L

    private fun rec(uuid: String, tpl: String, title: String, fields: Map<String, String>) =
        Record(
            uuid = uuid, template_id = tpl, title = title, fields = fields,
            created_at = 0, modified_at = 0, device_id = "d",
        )

    @Test
    fun `the same secret on two records is reported on both`() {
        val out = reviewVault(
            listOf(
                rec("1", "login", "Bank", mapOf("password" to "correct-horse-battery")),
                rec("2", "login", "Shop", mapOf("password" to "correct-horse-battery")),
            ),
            templates, now,
        )
        val reused = out.filter { it.kind == HealthKind.REUSED }
        assertEquals(2, reused.size)
        assertTrue(reused.all { it.detail.contains("1 other record") }, reused.map { it.detail }.toString())
    }

    @Test
    fun `distinct secrets are not reported as reuse`() {
        val out = reviewVault(
            listOf(
                rec("1", "login", "Bank", mapOf("password" to "correct-horse-battery")),
                rec("2", "login", "Shop", mapOf("password" to "another-long-secret")),
            ),
            templates, now,
        )
        assertTrue(out.none { it.kind == HealthKind.REUSED })
    }

    /** A shared expiry date must not be mistaken for a shared secret. */
    @Test
    fun `reuse only counts high-sensitivity fields`() {
        val out = reviewVault(
            listOf(
                rec("1", "card", "Visa", mapOf("atm_pin" to "419372", "expiry" to "2030-08")),
                rec("2", "card", "Amex", mapOf("atm_pin" to "805164", "expiry" to "2030-08")),
            ),
            templates, now,
        )
        assertTrue(out.none { it.kind == HealthKind.REUSED }, out.toString())
    }

    @Test
    fun `common passwords are flagged`() {
        assertTrue(CommonPasswords.isCommon("Password123"), "lowercasing should match")
        val out = reviewVault(
            listOf(rec("1", "login", "Mail", mapOf("password" to "Password123"))),
            templates, now,
        )
        assertEquals(1, out.count { it.kind == HealthKind.COMMON })
    }

    /**
     * A six-digit PIN is shorter than the ten characters the length rule wants,
     * so without the PIN branch every PIN in the vault would be reported weak.
     * "234567" is a run but not on the common list, which is what isolates the
     * pattern check from the commonness check.
     */
    @Test
    fun `pin fields are scored as pins, not by length`() {
        val out = reviewVault(
            listOf(
                rec("1", "card", "Run", mapOf("atm_pin" to "234567")),
                rec("2", "card", "Fine", mapOf("atm_pin" to "419372")),
            ),
            templates, now,
        )
        val weak = out.filter { it.kind == HealthKind.WEAK }
        assertEquals(1, weak.size, weak.toString())
        assertEquals("Run", weak.first().recordTitle)
    }

    /** A PIN that is both common and a run is one finding, not two. */
    @Test
    fun `commonness wins over pattern so a pin is reported once`() {
        val out = reviewVault(
            listOf(rec("1", "card", "Both", mapOf("atm_pin" to "123456"))),
            templates, now,
        )
        assertEquals(1, out.size, out.toString())
        assertEquals(HealthKind.COMMON, out.first().kind)
    }

    @Test
    fun `expiry is reported when past or near, and not when far off`() {
        val out = reviewVault(
            listOf(
                rec("1", "card", "Old", mapOf("expiry" to "2024-01")),
                rec("2", "card", "Soon", mapOf("expiry" to "2026-09")),
                rec("3", "card", "Later", mapOf("expiry" to "2030-08")),
            ),
            templates, now,
        )
        val exp = out.filter { it.kind == HealthKind.EXPIRING }.associateBy { it.recordTitle }
        assertEquals(setOf("Old", "Soon"), exp.keys, exp.keys.toString())
        assertEquals("expired", exp.getValue("Old").detail)
    }

    @Test
    fun `a record whose template is unknown is skipped rather than guessed at`() {
        val out = reviewVault(
            listOf(rec("1", "mystery", "Unknown", mapOf("password" to "123456"))),
            templates, now,
        )
        assertTrue(out.isEmpty(), out.toString())
    }

    @Test
    fun `findings come back worst-first`() {
        val out = reviewVault(
            listOf(
                rec("1", "card", "Old", mapOf("expiry" to "2024-01")),
                rec("2", "login", "A", mapOf("password" to "shared-secret-value")),
                rec("3", "login", "B", mapOf("password" to "shared-secret-value")),
            ),
            templates, now,
        )
        assertEquals(HealthKind.REUSED, out.first().kind)
        assertEquals(HealthKind.EXPIRING, out.last().kind)
    }
}
