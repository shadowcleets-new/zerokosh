package org.zerokosh.core

import org.zerokosh.core.model.Record
import org.zerokosh.core.model.TRASH_TTL_DAYS
import org.zerokosh.core.model.VaultBody
import org.zerokosh.core.vault.VaultMerge
import org.zerokosh.core.vault.captureHistory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TrashAndHistoryTest {

    private val now = 1_787_788_800_000L
    private val day = 86_400_000L

    private fun rec(uuid: String, fields: Map<String, String> = emptyMap(), modified: Long = 0) =
        Record(
            uuid = uuid, template_id = "login", title = "R$uuid", fields = fields,
            created_at = 0, modified_at = modified, device_id = "d",
        )

    // #region Trash
    @Test
    fun `delete keeps the record recoverable and still writes a tombstone`() {
        val body = VaultBody(records = listOf(rec("1")))
        val after = VaultMerge.applyDeletion(body, "1", now)
        assertTrue(after.records.isEmpty())
        assertEquals(1, after.trash.size)
        assertEquals("1", after.trash.first().record.uuid)
        // The tombstone is what stops another device resurrecting it.
        assertEquals(1, after.tombstones.size)
    }

    @Test
    fun `restore brings the record back and clears its tombstone`() {
        val deleted = VaultMerge.applyDeletion(VaultBody(records = listOf(rec("1"))), "1", now)
        val restored = VaultMerge.applyRestore(deleted, "1", now + day)
        assertEquals(listOf("1"), restored.records.map { it.uuid })
        assertTrue(restored.trash.isEmpty())
        assertTrue(restored.tombstones.isEmpty())
    }

    /**
     * The restore has to beat the tombstone by modification time, or a sync
     * from a device that still remembers the deletion undoes the undo.
     */
    @Test
    fun `a restored record outlives a tombstone from another device`() {
        val deleted = VaultMerge.applyDeletion(VaultBody(records = listOf(rec("1"))), "1", now)
        val restored = VaultMerge.applyRestore(deleted, "1", now + day)
        val merged = VaultMerge.merge(local = restored, remote = deleted, nowMs = now + 2 * day)
        assertEquals(listOf("1"), merged.records.map { it.uuid })
    }

    @Test
    fun `purge is permanent but leaves the deletion syncing`() {
        val deleted = VaultMerge.applyDeletion(VaultBody(records = listOf(rec("1"))), "1", now)
        val purged = VaultMerge.applyPurge(deleted, "1")
        assertTrue(purged.trash.isEmpty())
        assertEquals(1, purged.tombstones.size)
    }

    @Test
    fun `trash expires after the ttl`() {
        val old = VaultMerge.applyDeletion(VaultBody(records = listOf(rec("1"))), "1", now)
        val later = VaultMerge.applyDeletion(old.copy(records = listOf(rec("2"))), "2", now + (TRASH_TTL_DAYS + 1) * day)
        assertEquals(listOf("2"), later.trash.map { it.record.uuid })
    }

    /** merge() rebuilds VaultBody field by field; trash must survive that. */
    @Test
    fun `merge does not silently empty the trash`() {
        val deleted = VaultMerge.applyDeletion(VaultBody(records = listOf(rec("1"))), "1", now)
        val merged = VaultMerge.merge(local = deleted, remote = VaultBody(), nowMs = now)
        assertEquals(1, merged.trash.size, "trash was dropped by merge")
    }

    @Test
    fun `a record live on either side leaves the others trash`() {
        val deleted = VaultMerge.applyDeletion(VaultBody(records = listOf(rec("1"))), "1", now)
        val alive = VaultBody(records = listOf(rec("1", modified = now + day)))
        val merged = VaultMerge.merge(local = alive, remote = deleted, nowMs = now + day)
        assertEquals(listOf("1"), merged.records.map { it.uuid })
        assertTrue(merged.trash.isEmpty(), "restored record should not linger in trash")
    }
    // #endregion

    // #region History
    private val secrets = setOf("password")

    @Test
    fun `creating a record records no history`() {
        val next = rec("1", mapOf("password" to "first"))
        assertTrue(captureHistory(null, next, secrets, now).history.isEmpty())
    }

    @Test
    fun `replacing a secret remembers the old value`() {
        val before = rec("1", mapOf("password" to "old-secret"))
        val after = captureHistory(before, rec("1", mapOf("password" to "new-secret")), secrets, now)
        assertEquals(1, after.history.size)
        assertEquals("old-secret", after.history.first().value)
        assertEquals("password", after.history.first().k)
    }

    @Test
    fun `an unrelated edit does not deepen history`() {
        val before = rec("1", mapOf("password" to "same"))
        val after = captureHistory(before, rec("1", mapOf("password" to "same")).copy(title = "renamed"), secrets, now)
        assertTrue(after.history.isEmpty())
    }

    @Test
    fun `non-secret fields are never remembered`() {
        val before = rec("1", mapOf("username" to "old@example.com"))
        val after = captureHistory(before, rec("1", mapOf("username" to "new@example.com")), secrets, now)
        assertTrue(after.history.isEmpty())
    }

    @Test
    fun `history is capped per field and keeps the newest`() {
        var record = rec("1", mapOf("password" to "v0"))
        for (i in 1..8) {
            record = captureHistory(record, record.copy(fields = mapOf("password" to "v$i")), secrets, now + i * 1000L)
        }
        assertEquals(5, record.history.size)
        assertEquals("v7", record.history.first().value, "newest first")
        assertNull(record.history.firstOrNull { it.value == "v0" }, "oldest should have been evicted")
    }

    @Test
    fun `churn on one field cannot evict another fields history`() {
        val keys = setOf("password", "pin")
        var record = rec("1", mapOf("password" to "p0", "pin" to "111111"))
        record = captureHistory(record, record.copy(fields = mapOf("password" to "p1", "pin" to "222222")), keys, now)
        for (i in 2..8) {
            record = captureHistory(record, record.copy(fields = record.fields + ("password" to "p$i")), keys, now + i * 1000L)
        }
        assertNotNull(record.history.firstOrNull { it.k == "pin" }, "pin history was evicted by password churn")
    }
    // #endregion
}
