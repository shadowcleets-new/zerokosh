package org.zerokosh.core

// #region Imports
import org.zerokosh.core.model.Record
import org.zerokosh.core.model.Tombstone
import org.zerokosh.core.model.VaultBody
import org.zerokosh.core.vault.VaultMerge
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
// #endregion

/** §11.1 merge cases: both-modified, delete-vs-edit, conflict copy, tombstone purge. */
class MergeTest {

    private val now = 1_751_875_200_000L
    private var uuidCounter = 0
    private fun freshUuid() = "fresh-${uuidCounter++}"

    private fun record(
        uuid: String,
        title: String = "T",
        modified: Long = now,
        rev: Int = 1,
        fields: Map<String, String> = emptyMap(),
    ) = Record(
        uuid = uuid, template_id = "login", title = title,
        fields = fields, created_at = now - 10_000, modified_at = modified,
        rev = rev, device_id = "device-a",
    )

    @Test
    fun `union of disjoint records`() {
        val a = VaultBody(records = listOf(record("r1")))
        val b = VaultBody(records = listOf(record("r2")))
        val merged = VaultMerge.merge(a, b, now, ::freshUuid)
        assertEquals(setOf("r1", "r2"), merged.records.map { it.uuid }.toSet())
    }

    @Test
    fun `both modified - newer wins and older survives as conflict copy`() {
        val older = record("r1", title = "SBI old", modified = now - 5_000, rev = 2, fields = mapOf("username" to "old"))
        val newer = record("r1", title = "SBI new", modified = now, rev = 2, fields = mapOf("username" to "new"))
        val merged = VaultMerge.merge(VaultBody(records = listOf(older)), VaultBody(records = listOf(newer)), now, ::freshUuid)

        assertEquals(2, merged.records.size)
        val winner = merged.records.first { it.uuid == "r1" }
        assertEquals("SBI new", winner.title)
        val copy = merged.records.first { it.uuid != "r1" }
        assertEquals("SBI old (conflict copy)", copy.title)
        assertEquals("old", copy.fields["username"])
    }

    @Test
    fun `metadata-only divergence makes no conflict copy`() {
        val a = record("r1", modified = now - 5_000, rev = 1)
        val b = record("r1", modified = now, rev = 2) // same content, only rev/timestamp moved
        val merged = VaultMerge.merge(VaultBody(records = listOf(a)), VaultBody(records = listOf(b)), now, ::freshUuid)
        assertEquals(1, merged.records.size)
        assertEquals(2, merged.records[0].rev)
    }

    @Test
    fun `delete beats older edit`() {
        val a = VaultBody(records = listOf(record("r1", modified = now - 10_000)))
        val b = VaultBody(tombstones = listOf(Tombstone("r1", deleted_at = now - 5_000)))
        val merged = VaultMerge.merge(a, b, now, ::freshUuid)
        assertTrue(merged.records.isEmpty())
        assertEquals(1, merged.tombstones.size)
    }

    @Test
    fun `edit after delete resurrects the record`() {
        val a = VaultBody(records = listOf(record("r1", modified = now)))
        val b = VaultBody(tombstones = listOf(Tombstone("r1", deleted_at = now - 5_000)))
        val merged = VaultMerge.merge(a, b, now, ::freshUuid)
        assertEquals(listOf("r1"), merged.records.map { it.uuid })
        assertTrue(merged.tombstones.isEmpty())
    }

    @Test
    fun `tombstones purge after 90 days`() {
        val fresh = Tombstone("r1", deleted_at = now - VaultMerge.TOMBSTONE_TTL_MS + 60_000)
        val stale = Tombstone("r2", deleted_at = now - VaultMerge.TOMBSTONE_TTL_MS - 60_000)
        val merged = VaultMerge.merge(VaultBody(tombstones = listOf(fresh, stale)), VaultBody(), now, ::freshUuid)
        assertEquals(listOf("r1"), merged.tombstones.map { it.uuid })
    }

    @Test
    fun `attachments union by uuid`() {
        val a = VaultBody(attachments = mapOf("att1" to org.zerokosh.core.model.Attachment("a.txt", "text/plain", "QQ==")))
        val b = VaultBody(attachments = mapOf("att2" to org.zerokosh.core.model.Attachment("b.txt", "text/plain", "Qg==")))
        val merged = VaultMerge.merge(a, b, now, ::freshUuid)
        assertEquals(setOf("att1", "att2"), merged.attachments.keys)
    }

    @Test
    fun `applyDeletion removes record and adds tombstone`() {
        val body = VaultBody(records = listOf(record("r1")))
        val after = VaultMerge.applyDeletion(body, "r1", now)
        assertTrue(after.records.isEmpty())
        assertEquals("r1", after.tombstones.single().uuid)
    }
}
