/**
 * @file BuildRecordTest.kt
 * @description What an edit must carry over from the record it replaces.
 *
 * buildRecord names every field the form does not show — tags, favourite,
 * created_at, rev, reminders — and omitted history, which defaults to empty.
 * History is only rebuilt when a secret changes, so editing anything else
 * silently dropped every password the record remembered.
 */
package org.zerokosh.app.ui.record

import kotlin.test.Test
import kotlin.test.assertEquals
import org.zerokosh.core.model.PastSecret
import org.zerokosh.core.model.Record

class BuildRecordTest {

    private val past = listOf(
        PastSecret(k = "password", value = "old-one", replaced_at = 1_700_000_000_000L),
        PastSecret(k = "password", value = "older-still", replaced_at = 1_600_000_000_000L),
    )

    private val existing = Record(
        uuid = "u1",
        template_id = "login",
        title = "Bank",
        fields = mapOf("password" to "current"),
        history = past,
        tags = listOf("money"),
        favorite = true,
        created_at = 1_500_000_000_000L,
        modified_at = 1_700_000_000_000L,
        rev = 7,
        device_id = "d1",
    )

    @Test
    fun `editing the title keeps the remembered passwords`() {
        val edited = buildRecord(
            existing = existing,
            templateId = "login",
            title = "Bank (main)",
            institution = "",
            values = mapOf("password" to "current"),
            customFields = emptyList(),
        )
        assertEquals(past, edited.history)
        // and the rest of what the form never showed, which already worked
        assertEquals(listOf("money"), edited.tags)
        assertEquals(true, edited.favorite)
        assertEquals(7, edited.rev)
    }

    @Test
    fun `a new record starts with no history`() {
        val fresh = buildRecord(
            existing = null,
            templateId = "login",
            title = "New",
            institution = "",
            values = mapOf("password" to "p"),
            customFields = emptyList(),
        )
        assertEquals(emptyList(), fresh.history)
    }
}
