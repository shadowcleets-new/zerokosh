/**
 * @file InlineSuggestions.kt
 * @description Inline autofill presentations — the suggestion chips that appear
 *              in the keyboard strip (§6.7). API 30+.
 *
 * The service already returned datasets, but only with a RemoteViews
 * presentation, which Android renders as a dropdown the user has to summon.
 * Google Password Manager's suggestions instead ride in the keyboard's own
 * suggestion strip, and that is the difference people actually notice. It is a
 * second presentation on the same Dataset, not a second code path: build both,
 * and the platform picks whichever the current IME supports.
 */
package org.zerokosh.app.autofill

// #region Imports
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.service.autofill.FillRequest
import android.service.autofill.InlinePresentation
import android.widget.inline.InlinePresentationSpec
import androidx.annotation.RequiresApi
import androidx.autofill.inline.UiVersions
import androidx.autofill.inline.v1.InlineSuggestionUi
import org.zerokosh.app.MainActivity
import org.zerokosh.app.R
// #endregion

object InlineSuggestions {

    /**
     * How many chips the current IME will actually show. Zero means either an
     * older platform or a keyboard with no suggestion strip, and in both cases
     * the RemoteViews dropdown remains the only presentation worth building.
     */
    fun maxSuggestions(request: FillRequest): Int {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) return 0
        val inline = request.inlineSuggestionsRequest ?: return 0
        if (inline.inlinePresentationSpecs.isEmpty()) return 0
        return inline.maxSuggestionCount
    }

    /**
     * A chip for one dataset, or null when this IME cannot render v1 inline UI.
     *
     * `index` selects the spec: the platform supplies a list, one per slot, and
     * the last one repeats for any slot beyond it — so clamping rather than
     * indexing directly is what keeps a sixth suggestion from throwing.
     */
    @RequiresApi(Build.VERSION_CODES.R)
    fun build(
        context: Context,
        request: FillRequest,
        index: Int,
        title: String,
        subtitle: String?,
    ): InlinePresentation? {
        val inline = request.inlineSuggestionsRequest ?: return null
        val specs: List<InlinePresentationSpec> = inline.inlinePresentationSpecs
        if (specs.isEmpty()) return null
        val spec = specs[index.coerceAtMost(specs.size - 1)]

        // The IME declares which inline UI versions it can draw. Handing it a
        // slice it does not understand is a crash in the *keyboard's* process,
        // so this check is not optional politeness.
        if (!UiVersions.getVersions(spec.style).contains(UiVersions.INLINE_UI_VERSION_1)) {
            return null
        }

        val content = InlineSuggestionUi.newContentBuilder(attribution(context))
            .setTitle(title)
            .apply { if (!subtitle.isNullOrBlank()) setSubtitle(subtitle) }
            .setStartIcon(Icon.createWithResource(context, R.mipmap.ic_launcher))
            .setContentDescription(title)
            .build()

        return InlinePresentation(content.slice, spec, /* pinned = */ false)
    }

    /**
     * Where a long-press on the chip goes. Required and non-null by contract —
     * it is the user's route to "what is this thing filling my password?", so it
     * opens the app rather than pointing at nothing.
     *
     * FLAG_IMMUTABLE because the receiving side must never be able to rewrite
     * the intent, and Android 12+ rejects a PendingIntent that declares neither
     * mutability anyway.
     */
    private fun attribution(context: Context): PendingIntent =
        PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
}
