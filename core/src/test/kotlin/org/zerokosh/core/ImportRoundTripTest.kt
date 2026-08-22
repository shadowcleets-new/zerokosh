package org.zerokosh.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.zerokosh.core.model.Record
import org.zerokosh.core.model.VaultBody
import org.zerokosh.core.vault.UnlockResult
import org.zerokosh.core.vault.VaultMerge
import org.zerokosh.core.vault.VaultOperations

/**
 * The import path end to end: a .kosh written by one vault, opened with its own
 * passphrase, and merged into a different vault. The repository only wires these
 * three calls together, so this is where the behaviour is pinned.
 *
 * Argon2 is deliberately run at its cheapest here — this exercises the plumbing,
 * not the KDF, which ConformanceVectorsTest already covers at full strength.
 */
class ImportRoundTripTest {

    private val crypto = LazySodiumTestCrypto()
    private val now = 1_751_875_200_000L
    private val cheapOps = 1L
    private val cheapMem = 8L * 1024 * 1024

    private fun record(
        uuid: String,
        title: String = "T",
        modified: Long = now,
        fields: Map<String, String> = emptyMap(),
    ) = Record(
        uuid = uuid, template_id = "login", title = title, fields = fields,
        created_at = now - 10_000, modified_at = modified, device_id = "device-b",
    )

    private fun koshOf(body: VaultBody, passphrase: String): ByteArray =
        VaultOperations.createVault(
            passphrase = passphrase.toByteArray(),
            deviceId = "device-b",
            nowMs = now,
            crypto = crypto,
            ops = cheapOps,
            memBytes = cheapMem,
            initialBody = body,
        ).fileBytes

    private fun open(file: ByteArray, passphrase: String) =
        VaultOperations.unlockWithPassphrase(file, passphrase.toByteArray(), crypto)

    @Test
    fun `a backup opens with its own passphrase and its records merge in`() {
        val backup = koshOf(VaultBody(records = listOf(record("r2", "From backup"))), "backup-pass")
        val local = VaultBody(records = listOf(record("r1", "Already here")))

        val opened = open(backup, "backup-pass")
        assertTrue(opened is UnlockResult.Success)

        val merged = VaultMerge.merge(local, opened.body, now)
        assertEquals(setOf("r1", "r2"), merged.records.map { it.uuid }.toSet())
    }

    @Test
    fun `the current vault's passphrase does not open someone else's backup`() {
        // the case the prompt exists for: a backup predating a passphrase change
        val backup = koshOf(VaultBody(records = listOf(record("r2"))), "old-pass")
        assertTrue(open(backup, "current-pass") is UnlockResult.WrongCredential)
    }

    @Test
    fun `a file that is not a vault is rejected, not thrown on`() {
        val junk = ByteArray(4096) { it.toByte() }
        assertTrue(open(junk, "anything") is UnlockResult.Corrupt)
    }

    @Test
    fun `an empty file is rejected`() {
        assertTrue(open(ByteArray(0), "anything") is UnlockResult.Corrupt)
    }

    @Test
    fun `importing the same backup twice adds nothing the second time`() {
        val backup = koshOf(VaultBody(records = listOf(record("r2"))), "p")
        val opened = open(backup, "p") as UnlockResult.Success

        val once = VaultMerge.merge(VaultBody(records = listOf(record("r1"))), opened.body, now)
        val twice = VaultMerge.merge(once, opened.body, now)
        assertEquals(once.records.map { it.uuid }.sorted(), twice.records.map { it.uuid }.sorted())
    }

    @Test
    fun `an edit on both sides keeps both, flagged by the shared suffix`() {
        // what the import dialog counts as a conflict
        val backup = koshOf(
            VaultBody(records = listOf(record("r1", "Backup title", modified = now - 1, fields = mapOf("a" to "b")))),
            "p",
        )
        val opened = open(backup, "p") as UnlockResult.Success
        val local = VaultBody(records = listOf(record("r1", "Local title", modified = now)))

        val merged = VaultMerge.merge(local, opened.body, now)
        assertEquals(2, merged.records.size)
        assertTrue(merged.records.any { it.title.endsWith(VaultMerge.CONFLICT_SUFFIX) })
    }
}
