/**
 * @file Model.kt
 * @description Canonical data model (§2) and vault body (§4.3). JSON property
 *              names match the spec byte-for-byte — do not rename.
 *
 * [TABLE OF CONTENTS]
 * 1. RECORD & SUB-OBJECTS (§2.1)
 * 2. VAULT BODY (§4.3)
 * 3. JSON CONFIGURATION
 * 4. CONTENT EQUALITY (merge support, §4.5)
 */
package org.zerokosh.core.model

// #region Imports
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
// #endregion

// #region Record & sub-objects (§2.1)
@Serializable
data class Reminder(
    val field_id: String,
    val days_before: Int,
)

@Serializable
data class CustomField(
    val label: String,
    val type: String, // TEXT | SECRET | DATE | NUMBER
    val value: String,
)

@Serializable
data class Record(
    val uuid: String,
    val template_id: String,
    val title: String,
    val institution: String = "",
    val fields: Map<String, String> = emptyMap(),
    val custom_fields: List<CustomField> = emptyList(),
    val tags: List<String> = emptyList(),
    val favorite: Boolean = false,
    val created_at: Long,
    val modified_at: Long,
    val rev: Int = 1,
    val device_id: String,
    val reminders: List<Reminder> = emptyList(),
    /**
     * Previous values of high-sensitivity fields, newest first (§5.13). Exists
     * for one scenario that happens often: you rotate a password, the site did
     * not actually accept it, and the old one is gone. Defaulted so older
     * writers stay readable and other cores can ignore it.
     */
    val history: List<PastSecret> = emptyList(),
)

@Serializable
data class PastSecret(
    val k: String,
    val value: String,
    val replaced_at: Long,
)
// #endregion

// #region Vault body (§4.3)
/**
 * A deleted record kept for [TRASH_TTL_DAYS] so a mis-tap is recoverable. The
 * tombstone is still written alongside it: the tombstone is what stops another
 * device resurrecting the record, and trash is only the local undo buffer.
 */
@Serializable
data class TrashedRecord(
    val record: Record,
    val deleted_at: Long,
)

const val TRASH_TTL_DAYS: Int = 30

@Serializable
data class Tombstone(
    val uuid: String,
    val deleted_at: Long,
)

@Serializable
data class Attachment(
    val name: String,
    val mime: String,
    val data: String, // base64
)

@Serializable
data class VaultMeta(
    val device_names: Map<String, String> = emptyMap(),
)

@Serializable
data class VaultBody(
    val format: Int = 1,
    val records: List<Record> = emptyList(),
    val tombstones: List<Tombstone> = emptyList(),
    val trash: List<TrashedRecord> = emptyList(),
    val attachments: Map<String, Attachment> = emptyMap(),
    val meta: VaultMeta = VaultMeta(),
)
// #endregion

// #region JSON configuration
/** Shared lenient-read/strict-write JSON codec for all vault payloads. */
val VaultJson: Json = Json {
    ignoreUnknownKeys = true // forward compatibility: newer writers may add keys
    encodeDefaults = true
}
// #endregion

// #region Content equality (merge support, §4.5)
/**
 * True when the user-visible content differs — used by the merge algorithm to
 * decide whether the losing side must survive as a "(conflict copy)".
 * Deliberately ignores uuid/rev/timestamps/device_id.
 */
fun Record.contentDiffersFrom(other: Record): Boolean =
    title != other.title ||
        institution != other.institution ||
        fields != other.fields ||
        custom_fields != other.custom_fields ||
        tags != other.tags ||
        favorite != other.favorite ||
        reminders != other.reminders
// #endregion
