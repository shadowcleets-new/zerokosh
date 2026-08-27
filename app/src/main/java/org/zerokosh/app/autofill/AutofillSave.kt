/**
 * @file AutofillSave.kt
 * @description Turning a credential Android offered us into a login record.
 *
 *              Shared by the two routes into saving — the service, when the
 *              vault is already open, and the app, when it had to be unlocked
 *              first — so both produce the same record rather than two shapes
 *              of "saved from autofill".
 */
package org.zerokosh.app.autofill

import org.zerokosh.app.ZerokoshApp
import org.zerokosh.core.model.Record
import java.util.UUID

/**
 * The name the user will see in their vault. A package id is a terrible title,
 * so the bundled app map is asked first, then the site, and the package is only
 * ever the last resort.
 */
private fun titleFor(app: ZerokoshApp, credential: PendingSave.Credential): String {
    app.catalog.appMap[credential.packageName]?.takeIf { it.isNotBlank() }?.let { return it }
    credential.webDomain
        ?.removePrefix("www.")
        ?.takeIf { it.isNotBlank() }
        ?.let { return it.replaceFirstChar(Char::titlecase) }
    // "com.example.shopping" reads better as "Shopping" than as itself.
    return credential.packageName.substringAfterLast('.')
        .ifBlank { credential.packageName }
        .replaceFirstChar(Char::titlecase)
}

/**
 * An existing login for the same site and user, if there is one. Saving the
 * same credential twice would leave the user with duplicates to reconcile by
 * hand — and when the password changed, the previous value is worth keeping,
 * which an update does and an insert does not.
 */
private fun existingMatch(app: ZerokoshApp, credential: PendingSave.Credential): Record? {
    val site = (credential.webDomain ?: credential.packageName).removePrefix("www.").lowercase()
    if (site.isBlank()) return null
    return app.repository.body.value?.records?.firstOrNull { record ->
        record.template_id == "login" &&
            record.fields["website"].orEmpty().removePrefix("www.").lowercase() == site &&
            record.fields["username"].orEmpty().equals(credential.username, ignoreCase = true)
    }
}

/**
 * The record to write. Updating an existing match rather than inserting keeps
 * the rotation in that record's history, which is exactly the case history was
 * added for.
 */
fun loginRecordFor(app: ZerokoshApp, credential: PendingSave.Credential): Record {
    val existing = existingMatch(app, credential)
    if (existing != null) {
        return existing.copy(fields = existing.fields + ("password" to credential.password))
    }
    val now = System.currentTimeMillis()
    return Record(
        uuid = UUID.randomUUID().toString(),
        template_id = "login",
        title = titleFor(app, credential),
        fields = buildMap {
            credential.webDomain?.takeIf { it.isNotBlank() }?.let { put("website", it) }
            if (credential.username.isNotBlank()) put("username", credential.username)
            put("password", credential.password)
        },
        created_at = now,
        modified_at = now,
        device_id = app.prefs.deviceId,
    )
}
