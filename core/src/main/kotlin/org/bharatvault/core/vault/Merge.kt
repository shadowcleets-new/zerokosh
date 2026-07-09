package org.bharatvault.core.vault

// #region Imports
import org.bharatvault.core.model.Record
import org.bharatvault.core.model.Tombstone
import org.bharatvault.core.model.VaultBody
import org.bharatvault.core.model.VaultMeta
import org.bharatvault.core.model.contentDiffersFrom
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
            val a = localByUuid[uuid]
            val b = remoteByUuid[uuid]
            val winner: Record = when {
                a != null && b != null && (a.rev != b.rev || a.contentDiffersFrom(b) || a.modified_at != b.modified_at) -> {
                    val newer = if (a.modified_at >= b.modified_at) a else b
                    val older = if (newer === a) b else a
                    if (older.contentDiffersFrom(newer)) {
                        conflictCopies += older.copy(
                            uuid = freshUuid(),
                            title = "${older.title} (conflict copy)",
                        )
                    }
                    newer
                }
                a != null && b != null -> a // identical
                else -> a ?: b!!
            }
            val tomb = tombstones[uuid]
            if (tomb != null && tomb.deleted_at > winner.modified_at) {
                continue // tombstone beats record
            }
            if (tomb != null) tombstones.remove(uuid) // record edited after deletion — record wins
            merged += winner
        }

        return VaultBody(
            format = 1,
            records = merged + conflictCopies,
            tombstones = tombstones.values.filter { nowMs - it.deleted_at < TOMBSTONE_TTL_MS }.sortedBy { it.uuid },
            attachments = remote.attachments + local.attachments, // union by uuid
            meta = VaultMeta(device_names = remote.meta.device_names + local.meta.device_names),
        )
    }

    /** Delete = tombstone + remove record (§4.5); never hard-delete without one. */
    fun applyDeletion(body: VaultBody, uuid: String, nowMs: Long): VaultBody = body.copy(
        records = body.records.filterNot { it.uuid == uuid },
        tombstones = body.tombstones.filterNot { it.uuid == uuid } + Tombstone(uuid, nowMs),
    )
}
