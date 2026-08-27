package org.zerokosh.core.vault

// #region Imports
import org.zerokosh.core.model.Record
import org.zerokosh.core.model.Tombstone
import org.zerokosh.core.model.TrashedRecord
import org.zerokosh.core.model.TRASH_TTL_DAYS
import org.zerokosh.core.model.VaultBody
import org.zerokosh.core.model.VaultMeta
import org.zerokosh.core.model.contentDiffersFrom
import java.util.UUID
// #endregion

/**
 * §4.5 merge: record-level union by uuid. Newer `modified_at` wins; a losing
 * side whose content differs survives as a "(conflict copy)" with a fresh
 * uuid. Tombstone beats record when `deleted_at > modified_at`; tombstones
 * purge after 90 days. NEVER silently discard data.
 */
object VaultMerge {

    const val TOMBSTONE_TTL_MS: Long = 90L * 24 * 60 * 60 * 1000

    /**
     * Marks the losing side of a content conflict. Exposed so a caller can count
     * them and tell the user, rather than re-typing the literal and drifting.
     */
    const val CONFLICT_SUFFIX = " (conflict copy)"

    /** The surviving record, plus a conflict copy when both sides changed. */
    private class Resolution(val winner: Record, val conflictCopy: Record?)

    /**
     * Last write wins by modified_at — but only after checking whether the
     * loser said something different. A losing edit with different content is
     * preserved as a copy rather than discarded, because the alternative is
     * silently throwing away something a user typed.
     */
    private fun resolve(a: Record?, b: Record?, freshUuid: () -> String): Resolution {
        if (a == null || b == null) return Resolution(a ?: b!!, null)
        val unchanged = a.rev == b.rev && a.modified_at == b.modified_at && !a.contentDiffersFrom(b)
        if (unchanged) return Resolution(a, null)

        val newer = if (a.modified_at >= b.modified_at) a else b
        val older = if (newer === a) b else a
        val copy = if (older.contentDiffersFrom(newer)) {
            older.copy(uuid = freshUuid(), title = older.title + CONFLICT_SUFFIX)
        } else {
            null
        }
        return Resolution(newer, copy)
    }

    fun merge(
        local: VaultBody,
        remote: VaultBody,
        nowMs: Long,
        freshUuid: () -> String = { UUID.randomUUID().toString() },
    ): VaultBody {
        // Tombstones: union by uuid keeping the newest deletion; purge expired.
        val tombstones = (local.tombstones + remote.tombstones)
            .groupBy { it.uuid }
            .mapValues { (_, ts) -> ts.maxBy { it.deleted_at } }
            .toMutableMap()

        val localByUuid = local.records.associateBy { it.uuid }
        val remoteByUuid = remote.records.associateBy { it.uuid }
        val merged = mutableListOf<Record>()
        val conflictCopies = mutableListOf<Record>()

        for (uuid in localByUuid.keys + remoteByUuid.keys) {
            val resolution = resolve(localByUuid[uuid], remoteByUuid[uuid], freshUuid)
            resolution.conflictCopy?.let { conflictCopies += it }
            val winner = resolution.winner
            val tomb = tombstones[uuid]
            if (tomb != null && tomb.deleted_at > winner.modified_at) {
                continue // tombstone beats record
            }
            if (tomb != null) tombstones.remove(uuid) // record edited after deletion — record wins
            merged += winner
        }

        val survivors = merged + conflictCopies
        return VaultBody(
            format = 1,
            records = survivors,
            tombstones = tombstones.values.filter { nowMs - it.deleted_at < TOMBSTONE_TTL_MS }.sortedBy { it.uuid },
            // Named explicitly because this constructor is exhaustive: a field
            // left out here is silently dropped on every sync, which for trash
            // would mean the undo buffer quietly emptying itself.
            trash = mergeTrash(local.trash, remote.trash, survivors, nowMs),
            attachments = remote.attachments + local.attachments, // union by uuid
            meta = VaultMeta(device_names = remote.meta.device_names + local.meta.device_names),
        )
    }

    private const val TRASH_TTL_MS = TRASH_TTL_DAYS * 86_400_000L

    /**
     * Union by uuid keeping the later deletion, minus anything expired and
     * anything that is a live record again — a restore on one device must not
     * leave the entry sitting in the other device's trash.
     */
    private fun mergeTrash(
        local: List<TrashedRecord>,
        remote: List<TrashedRecord>,
        live: List<Record>,
        nowMs: Long,
    ): List<TrashedRecord> {
        val liveUuids = live.mapTo(HashSet()) { it.uuid }
        val byUuid = LinkedHashMap<String, TrashedRecord>()
        for (entry in local + remote) {
            val existing = byUuid[entry.record.uuid]
            if (existing == null || entry.deleted_at > existing.deleted_at) {
                byUuid[entry.record.uuid] = entry
            }
        }
        return byUuid.values
            .filter { it.record.uuid !in liveUuids && nowMs - it.deleted_at < TRASH_TTL_MS }
            .sortedByDescending { it.deleted_at }
    }

    /** Delete = tombstone + remove record (§4.5); never hard-delete without one. */
    fun applyDeletion(body: VaultBody, uuid: String, nowMs: Long): VaultBody {
        val removed = body.records.firstOrNull { it.uuid == uuid }
        return body.copy(
            records = body.records.filterNot { it.uuid == uuid },
            tombstones = body.tombstones.filterNot { it.uuid == uuid } + Tombstone(uuid, nowMs),
            trash = purgeTrash(
                body.trash.filterNot { it.record.uuid == uuid } +
                    listOfNotNull(removed?.let { TrashedRecord(it, nowMs) }),
                nowMs,
            ),
        )
    }

    /**
     * Restore beats the tombstone by modification time rather than by deleting
     * it: merge already prefers a record edited after its deletion, so bumping
     * modified_at is what makes the restore survive a sync from a device that
     * still remembers the deletion.
     */
    fun applyRestore(body: VaultBody, uuid: String, nowMs: Long): VaultBody {
        val entry = body.trash.firstOrNull { it.record.uuid == uuid } ?: return body
        val restored = entry.record.copy(modified_at = nowMs, rev = entry.record.rev + 1)
        return body.copy(
            records = body.records.filterNot { it.uuid == uuid } + restored,
            tombstones = body.tombstones.filterNot { it.uuid == uuid },
            trash = body.trash.filterNot { it.record.uuid == uuid },
        )
    }

    /** Permanent. The tombstone stays, so the deletion still syncs. */
    fun applyPurge(body: VaultBody, uuid: String): VaultBody =
        body.copy(trash = body.trash.filterNot { it.record.uuid == uuid })

    fun purgeTrash(trash: List<TrashedRecord>, nowMs: Long): List<TrashedRecord> =
        trash.filter { nowMs - it.deleted_at < TRASH_TTL_MS }.sortedByDescending { it.deleted_at }
}
