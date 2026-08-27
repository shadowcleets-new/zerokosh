/**
 * @file PendingSave.kt
 * @description A credential Android asked us to save while the vault was shut.
 *
 *              Same reasoning as PendingCard: a password must not travel as an
 *              Intent extra. Extras survive in the recents task description and
 *              in whatever the platform logs about activity starts, which is the
 *              last place a password should be. This keeps it in memory, hands
 *              it over once, and forgets it.
 *
 *              Unlike PendingCard this deliberately outlives a lock — it is
 *              created *because* the vault is locked, so clearing it on lock
 *              would destroy the very thing it exists to carry. A TTL bounds
 *              that instead: a credential nobody came back for within a few
 *              minutes is dropped rather than held indefinitely.
 */
package org.zerokosh.app.autofill

object PendingSave {

    /** Long enough to unlock, short enough that a forgotten prompt expires. */
    private const val TTL_MS = 5 * 60 * 1000L

    data class Credential(
        val username: String,
        val password: String,
        val webDomain: String?,
        val packageName: String,
    )

    @Volatile
    private var held: Credential? = null

    @Volatile
    private var offeredAtMs = 0L

    fun offer(credential: Credential, nowMs: Long = System.currentTimeMillis()) {
        held = credential
        offeredAtMs = nowMs
    }

    /** Returns it exactly once, and never after the TTL. */
    fun take(nowMs: Long = System.currentTimeMillis()): Credential? {
        val credential = held ?: return null
        held = null
        return credential.takeIf { nowMs - offeredAtMs < TTL_MS }
    }

    fun clear() {
        held = null
        offeredAtMs = 0L
    }
}
