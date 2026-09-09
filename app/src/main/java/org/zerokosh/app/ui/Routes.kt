/**
 * @file Routes.kt
 * @description Every navigable destination, as a type rather than a string.
 *
 * Routes used to be strings assembled by hand — `"edit/$templateId?preset=$p"` —
 * which put three separate burdens on every call site: spell the path right,
 * encode the arguments, and decode them again at the other end. The encoding in
 * particular was not theoretical. "Punjab & Sind Bank" and "L&T Finance" split a
 * query string on the ampersand and arrived truncated to "Punjab ", so the
 * builder had to Uri.encode on the way out and the destination Uri.decode on the
 * way in. Serialization does that correctly and invisibly, so both halves go
 * away with the strings.
 *
 * These are also the shape Navigation 3 wants. Migrating to it needs typed
 * routes first, so this is the prerequisite done on its own where it can be
 * verified on its own.
 */
package org.zerokosh.app.ui

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

// #region Onboarding (S1 to S6)
@Serializable
data object WelcomeRoute : NavKey

@Serializable
data object LanguageRoute : NavKey

@Serializable
data object TrustRoute : NavKey

@Serializable
data object CreateVaultRoute : NavKey

@Serializable
data object RecoveryKitRoute : NavKey

@Serializable
data object QuickUnlockRoute : NavKey
// #endregion

// #region Main app
@Serializable
data object HomeRoute : NavKey

@Serializable
data object CodesRoute : NavKey

@Serializable
data object SettingsRoute : NavKey

@Serializable
data object TrashRoute : NavKey

@Serializable
data object HealthRoute : NavKey

@Serializable
data object GalleryRoute : NavKey

@Serializable
data class DetailRoute(val uuid: String) : NavKey

/**
 * The record editor.
 *
 * [templateId] doubles as the mode: a real template id starts a new record of
 * that kind, and the sentinel [BY_ID] means "edit the record named by [uuid]".
 * That was already how the string route worked; naming the sentinel is the only
 * change.
 */
@Serializable
data class EditRoute(
    val templateId: String,
    val uuid: String? = null,
    val preset: String? = null,
    val brand: String? = null,
) : NavKey {
    companion object {
        const val BY_ID = "byid"

        /** Open an existing record for editing. */
        fun forRecord(uuid: String) = EditRoute(templateId = BY_ID, uuid = uuid)
    }
}
// #endregion
