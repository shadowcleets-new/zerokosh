/**
 * @file WelcomeScreen.kt
 * @description S1 — the cover. Rebuilt against the Lovable mockup "00 · Welcome":
 *              an M3 Expressive shape hero (three morphed blobs over a warm tonal
 *              gradient), a difference-blended serif display headline, feature
 *              chips, and the two pill actions.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS & DEPENDENCIES
 * 2. LOCAL CONSTANTS
 * 3. MAIN SCREEN
 * 4. SHAPE HERO
 * 5. DIFFERENCE-BLENDED HEADLINE
 * 6. HELPER WIDGETS
 */
package org.zerokosh.app.ui.onboarding

// #region Imports
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import org.zerokosh.app.R
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.ui.common.OutlinedPillButton
import org.zerokosh.app.ui.common.PrimaryPillButton
import org.zerokosh.app.ui.common.VaultBlobs
import org.zerokosh.app.ui.theme.VaultTheme
// #endregion

// #region Local constants
/**
 * BlendMode.Difference outputs |ground − source| per channel, so a fixed source
 * colour only lands on the intended result against one ground. The mockup's
 * hard-coded values were tuned for paper; in dark mode the emphasis line came out
 * cyan instead of burnt orange (BV-18). Deriving the source from the ground and
 * the colour we actually want makes the blend land correctly in both themes and
 * still invert over the blobs.
 */
private fun differenceSource(ground: Color, target: Color) = Color(
    red = abs(ground.red - target.red),
    green = abs(ground.green - target.green),
    blue = abs(ground.blue - target.blue),
)

/** Spoken form of the painted hero headline. */


private val FeatureChips = listOf(
    R.string.wl_chip_crypto,
    R.string.wl_chip_offline,
    R.string.wl_chip_open,
)
// #endregion

// #region Main screen
@Composable
fun WelcomeScreen(
    @Suppress("UNUSED_PARAMETER") app: ZerokoshApp,
    onGetStarted: () -> Unit,
    onLogin: () -> Unit,
) {
    val c = VaultTheme.colors
    // BoxWithConstraints, not Box: the layout below has to know whether the
    // viewport is tall enough for the designed spacing before it commits to it.
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(c.paper)
            // The whole screen composites offscreen so the headline's difference
            // blend has the shape hero — and the paper — to blend against.
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen },
    ) {
        ShapeHero(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.62f)
                .align(Alignment.TopStart),
        )

        // The designed layout pushes everything below the shape hero with a
        // weighted spacer, which is right until the viewport is shorter than the
        // content. Then weight(1f) resolves to zero, the buttons land past the
        // bottom edge, and there is no scroll to reach them — on a landscape
        // phone that made "Create a new vault" unreachable and first run a dead
        // end. Short viewports scroll instead; tall ones are untouched.
        val roomForDesignedLayout = maxHeight >= 640.dp
        Column(
            modifier = Modifier
                // BV-23: cap before filling (see Onboarding.kt).
                .widthIn(max = 560.dp)
                .then(
                    if (roomForDesignedLayout) Modifier.fillMaxSize()
                    else Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                )
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
                .navigationBarsPadding()
                .padding(top = 16.dp, bottom = 16.dp),
        ) {
            TopBar()

            // weight() needs a bounded height, which a scrolling column does not
            // have, so the short path gets a fixed gap rather than a silent zero.
            if (roomForDesignedLayout) Spacer(Modifier.weight(1f))
            else Spacer(Modifier.height(28.dp))

            DifferenceHeadline()

            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.wl_body),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = c.ink(0.65f),
                modifier = Modifier.widthIn(max = 280.dp),
            )

            Spacer(Modifier.height(20.dp))
            FeatureChipRow()

            Spacer(Modifier.height(24.dp))
            PrimaryPillButton(stringResource(R.string.wl_create), onGetStarted)
            Spacer(Modifier.height(10.dp))
            OutlinedPillButton(stringResource(R.string.wl_restore), onLogin)

            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.wl_footer),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 11.sp,
                color = c.mute,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(8.dp))
        }
    }
}
// #endregion

// #region Shape hero
/**
 * M3 Expressive "shape hero": a blurred two-stop tonal gradient with three
 * cookie-cut morphed shapes and one satellite dot on top. Offsets are the
 * mockup's, which is drawn on a 340dp-wide compact phone.
 */
@Composable
private fun ShapeHero(modifier: Modifier = Modifier) {
    val c = VaultTheme.colors
    Box(modifier) {
        // Warm tonal gradient wash.
        Box(
            Modifier
                .size(440.dp)
                .align(Alignment.TopCenter)
                .offset(y = 40.dp)
                .blur(6.dp, BlurredEdgeTreatment.Unbounded)
                .alpha(0.9f)
                .drawBehind {
                    drawRect(
                        Brush.radialGradient(
                            colors = listOf(c.primary.copy(alpha = 0.55f), Color.Transparent),
                            center = Offset(size.width * 0.30f, size.height * 0.35f),
                            radius = size.width * 0.60f,
                        ),
                    )
                    drawRect(
                        Brush.radialGradient(
                            colors = listOf(c.accent.copy(alpha = 0.45f), Color.Transparent),
                            center = Offset(size.width * 0.75f, size.height * 0.65f),
                            radius = size.width * 0.50f,
                        ),
                    )
                },
        )

        // Cookie-cut shape stack.
        Box(
            Modifier
                .offset(x = 32.dp, y = 64.dp)
                .size(160.dp)
                .rotate(-6f)
                .clip(VaultBlobs.Primary)
                .background(c.primary.copy(alpha = 0.95f)),
        )
        Box(
            Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-24).dp, y = 96.dp)
                .size(112.dp)
                .clip(VaultBlobs.Accent)
                .background(c.accent),
        )
        Box(
            Modifier
                .offset(x = 96.dp, y = 208.dp)
                .size(80.dp)
                .clip(VaultBlobs.Ink)
                .background(c.ink),
        )
        // Satellite dot.
        Box(
            Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-64).dp, y = 64.dp)
                .size(12.dp)
                .background(c.ink, CircleShape),
        )
    }
}
// #endregion

// #region Difference-blended headline
@Composable
private fun DifferenceHeadline(modifier: Modifier = Modifier) {
    val c = VaultTheme.colors
    val measurer = rememberTextMeasurer()
    val style = MaterialTheme.typography.displayLarge
        .copy(color = differenceSource(c.paper, c.ink))
    val emphasis = differenceSource(c.paper, c.primary)
    val text = buildAnnotatedString {
        append(stringResource(R.string.wl_head_1) + "\n")
        withStyle(SpanStyle(fontStyle = FontStyle.Italic, color = emphasis)) {
            append(stringResource(R.string.wl_head_2))
        }
        append("\n" + stringResource(R.string.wl_head_3))
    }
    BoxWithConstraints(modifier.fillMaxWidth()) {
        val layout = measurer.measure(
            text = text,
            style = style,
            constraints = Constraints(maxWidth = constraints.maxWidth),
        )
        val heightDp = with(LocalDensity.current) { layout.size.height.toDp() }
        // The headline is painted, so its spoken form is a resource read here.
        val spokenHeadline = stringResource(R.string.wl_sr_headline)
        Box(
            Modifier
                .fillMaxWidth()
                .height(heightDp)
                // The headline is painted, not laid out as a Text node, so it
                // carries no semantics of its own — state them explicitly or a
                // screen reader skips the screen's primary message entirely.
                .semantics { contentDescription = spokenHeadline }
                .drawBehind { drawText(layout, blendMode = BlendMode.Difference) },
        )
    }
}
// #endregion

// #region Helper widgets
/** M3 tiny top app bar: wordmark on the left, a version assist chip on the right. */
@Composable
private fun TopBar() {
    val c = VaultTheme.colors
    Row(
        modifier = Modifier.fillMaxWidth().height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            "Zerokosh",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = c.ink,
        )
        Row(
            modifier = Modifier
                .height(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(c.surface)
                .border(1.dp, c.line, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Icon(
                Icons.Outlined.Schedule,
                contentDescription = null,
                tint = c.ink(0.7f),
                modifier = Modifier.size(14.dp),
            )
            Text(
                "v1.0",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = c.ink(0.7f),
            )
        }
    }
}

/** M3 filter-chip row stating the three claims the cover makes. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FeatureChipRow() {
    val c = VaultTheme.colors
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FeatureChips.forEach { labelRes ->
            val label = stringResource(labelRes)
            Row(
                modifier = Modifier
                    .height(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(c.surface)
                    .border(1.dp, c.line, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Box(Modifier.size(6.dp).background(c.primary, CircleShape))
                Text(
                    label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = c.ink(0.75f),
                    maxLines = 1,
                )
            }
        }
    }
}
// #endregion
