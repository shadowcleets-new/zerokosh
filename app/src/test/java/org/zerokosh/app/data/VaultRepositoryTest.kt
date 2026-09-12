/**
 * @file VaultRepositoryTest.kt
 * @description The app module's first tests, on the class that most needed them.
 *
 *              Every defect found in this app over the last month was found by
 *              opening the app, never by a build: autofill reporting a save it
 *              discarded, R8 deleting 593 drawables, a template offering
 *              WhatsApp a gift card. The correlation worth acting on is that
 *              :core has ~130 tests and produced none of them, while :app had
 *              none and produced all of them.
 */
package org.zerokosh.app.data

import kotlinx.coroutines.test.runTest
import org.zerokosh.core.model.Record
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class VaultRepositoryTest {

    private val passphrase = "correct-horse-battery-staple".toByteArray()

    private fun repo(store: FakeVaultStore = FakeVaultStore()): Pair<VaultRepository, FakeVaultStore> {
        val r = VaultRepository(FakeCrypto(), store, FakeVaultPrefs())
        return r to store
    }

    private suspend fun openVault(): Pair<VaultRepository, FakeVaultStore> {
        val (r, store) = repo()
        r.createVault(passphrase.copyOf())
        assertEquals(UnlockOutcome.SUCCESS, r.unlockWithPassphrase(passphrase.copyOf()))
        return r to store
    }

    /**
     * Reported from a phone whose vault opens with six digits: every prompt on
     * the lock screen said "passphrase". Nothing recorded which kind of secret
     * had been set, so a vault made before that flag existed has to learn it
     * here — the one moment the app holds the secret and knows it was right.
     */
    @Test
    fun `a successful unlock records whether the secret was a PIN`() = runTest {
        val prefs = FakeVaultPrefs()
        val pin = "241000".toByteArray()
        val r = VaultRepository(FakeCrypto(), FakeVaultStore(), prefs)
        r.createVault(pin.copyOf())
        assertFalse(prefs.secretIsPin, "nothing is claimed before an unlock proves it")

        assertEquals(UnlockOutcome.SUCCESS, r.unlockWithPassphrase(pin.copyOf()))
        assertTrue(prefs.secretIsPin)

        // And a wrong guess that happens to look like a PIN claims nothing.
        val otherPrefs = FakeVaultPrefs()
        val other = VaultRepository(FakeCrypto(), FakeVaultStore(), otherPrefs)
        other.createVault(passphrase.copyOf())
        assertEquals(UnlockOutcome.WRONG_CREDENTIAL, other.unlockWithPassphrase("999999".toByteArray()))
        assertFalse(otherPrefs.secretIsPin)
    }

    /** Changing to a passphrase has to take the label back off again. */
    @Test
    fun `unlocking with a passphrase clears the PIN label`() = runTest {
        val prefs = FakeVaultPrefs()
        prefs.secretIsPin = true
        val r = VaultRepository(FakeCrypto(), FakeVaultStore(), prefs)
        r.createVault(passphrase.copyOf())
        assertEquals(UnlockOutcome.SUCCESS, r.unlockWithPassphrase(passphrase.copyOf()))
        assertFalse(prefs.secretIsPin)
    }

    private fun record(uuid: String = "u1", password: String = "first-secret") = Record(
        uuid = uuid,
        template_id = "login",
        title = "Bank",
        fields = mapOf("username" to "priya", "password" to password),
        created_at = 0,
        modified_at = 0,
        device_id = "",
    )

    // #region The reason this file exists
    /**
     * The defect this covers: a sync folder's permission grant lapses, the write
     * throws, and the caller navigates away believing the record was saved.
     */
    @Test
    fun `a failed write is reported, not swallowed`() = runTest {
        val (r, store) = openVault()
        store.failWrites = IllegalStateException("cannot write tmp vault file")

        val result = r.upsertRecord(record())

        assertTrue(result.isFailure, "a store that throws must produce a failed SaveResult")
        assertEquals("cannot write tmp vault file", result.exceptionOrNull()?.message)
    }

    @Test
    fun `a failed write does not leave the record looking saved`() = runTest {
        val (r, store) = openVault()
        store.failWrites = IllegalStateException("nope")

        r.upsertRecord(record())

        assertTrue(r.body.value?.records.orEmpty().isEmpty(), "in-memory state must not claim a write that failed")
    }

    @Test
    fun `the vault survives a failed write and saves once the store recovers`() = runTest {
        val (r, store) = openVault()
        store.failWrites = IllegalStateException("transient")
        assertTrue(r.upsertRecord(record()).isFailure)

        store.failWrites = null
        assertTrue(r.upsertRecord(record()).isSuccess)
        assertEquals(1, r.body.value?.records?.size)
    }

    @Test
    fun `deleting reports failure too`() = runTest {
        val (r, store) = openVault()
        r.upsertRecord(record())
        store.failWrites = IllegalStateException("gone")

        assertTrue(r.deleteRecord("u1").isFailure)
        assertEquals(1, r.body.value?.records?.size, "the record must still be there")
    }

    /** A mutation with nothing to do is not an error the user can act on. */
    @Test
    fun `a missing record reports success rather than a failure nobody can fix`() = runTest {
        val (r, _) = openVault()
        assertTrue(r.restoreRecord("does-not-exist").isSuccess)
        assertTrue(r.toggleFavorite("does-not-exist").isSuccess)
    }
    // #endregion

    // #region Behaviour that reaches disk
    @Test
    fun `a saved record round-trips through the file`() = runTest {
        val (r, store) = openVault()
        assertTrue(r.upsertRecord(record()).isSuccess)

        val reopened = VaultRepository(FakeCrypto(), store, FakeVaultPrefs())
        assertEquals(UnlockOutcome.SUCCESS, reopened.unlockWithPassphrase(passphrase.copyOf()))
        assertEquals("Bank", reopened.body.value?.records?.single()?.title)
    }

    @Test
    fun `replacing a password files the old one in history`() = runTest {
        val (r, _) = openVault()
        r.secretKeysFor = { setOf("password") }
        r.upsertRecord(record(password = "first-secret"))

        val stored = r.body.value!!.records.single()
        r.upsertRecord(stored.copy(fields = stored.fields + ("password" to "second-secret")))

        val history = r.body.value!!.records.single().history
        assertEquals(1, history.size)
        assertEquals("first-secret", history.single().value)
    }

    @Test
    fun `deleting moves the record to trash and restoring brings it back`() = runTest {
        val (r, _) = openVault()
        r.upsertRecord(record())

        assertTrue(r.deleteRecord("u1").isSuccess)
        assertTrue(r.body.value!!.records.isEmpty())
        assertEquals("u1", r.body.value!!.trash.single().record.uuid)

        assertTrue(r.restoreRecord("u1").isSuccess)
        assertEquals("u1", r.body.value!!.records.single().uuid)
        assertTrue(r.body.value!!.trash.isEmpty())
    }

    @Test
    fun `a wrong passphrase does not open the vault`() = runTest {
        val (_, store) = openVault()
        val other = VaultRepository(FakeCrypto(), store, FakeVaultPrefs())
        assertEquals(UnlockOutcome.WRONG_CREDENTIAL, other.unlockWithPassphrase("wrong-one".toByteArray()))
        assertFalse(other.state.value == VaultState.Unlocked)
    }

    /**
     * §4.4: the .bak is taken once per session, before the first write — so it
     * holds the vault as it was when the session opened, not as of the last edit.
     */
    @Test
    fun `the backup is taken once per session, before the first write`() = runTest {
        val (r, store) = openVault()
        r.upsertRecord(record("u1"))
        r.upsertRecord(record("u2"))
        r.upsertRecord(record("u3"))
        assertEquals(1, store.backups)
    }
    // #endregion
}
