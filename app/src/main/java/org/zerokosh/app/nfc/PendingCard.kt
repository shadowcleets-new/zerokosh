/**
 * @file PendingCard.kt
 * @description A card read by NFC, handed to the edit screen that is about to
 *              open for it.
 *
 *              Deliberately NOT a navigation argument. A PAN in a route ends up
 *              in the back stack's saved state and in whatever logs the platform
 *              keeps of navigation, which is exactly where a card number must
 *              never be. This keeps it in memory, hands it over once, and drops
 *              it — and the repository clears it when the vault locks.
 */
package org.zerokosh.app.nfc

import org.zerokosh.core.emv.EmvCard

object PendingCard {

    @Volatile
    private var card: EmvCard? = null

    fun offer(read: EmvCard) {
        card = read
    }

    /** Returns the pending card exactly once; a second caller gets null. */
    fun take(): EmvCard? {
        val held = card
        card = null
        return held
    }

    /** Locking the vault must not leave a card number behind in memory. */
    fun clear() {
        card = null
    }
}
