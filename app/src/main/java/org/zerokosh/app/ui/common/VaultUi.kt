/**
 * @file VaultUi.kt
 * @description The Zerokosh design system, transcribed from the Lovable
 *              mockup pack's shared primitive layer. Every screen composes from
 *              here so the mockup's geometry (26dp grouped cards, 56dp pill
 *              buttons, 44dp brand tiles, the M3 dock) stays consistent.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS & DEPENDENCIES
 * 2. TEXT ATOMS (kicker, section label, emphasis span)
 * 3. CHROME (gesture handle, onboarding top bar, step progress)
 * 4. BUTTONS (ink pill, outlined pill, subtle text)
 * 5. SURFACES (group card, white card, ink card, notice card, divider)
 * 6. CHIPS & TILES (brand tile, copy chip, filter chip, status pill)
 * 7. ROWS (list row, key/value row)
 * 8. SEARCH DOCK
 * 9. NAVIGATION (M3 dock + extended FAB)
 * 10. SHAPES (organic M3 blob)
 */
@file:OptIn(androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class)

package org.zerokosh.app.ui.common

// #region Imports
import android.provider.Settings
import androidx.annotation.StringRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShortNavigationBar
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.ShortNavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.WideNavigationRail
import androidx.compose.material3.WideNavigationRailDefaults
import androidx.compose.material3.WideNavigationRailItem
import androidx.compose.material3.WideNavigationRailItemDefaults
import androidx.compose.material3.toPath
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import org.zerokosh.app.R
import org.zerokosh.app.ui.theme.CornerCard
import org.zerokosh.app.ui.theme.CornerGroup
import org.zerokosh.app.ui.theme.CornerTile
import org.zerokosh.app.ui.theme.FieldLabelStyle
import org.zerokosh.app.ui.theme.JetBrainsMono
import org.zerokosh.app.ui.theme.MetaTextStyle
import org.zerokosh.app.ui.theme.Newsreader
import org.zerokosh.app.ui.theme.SectionLabelStyle
import org.zerokosh.app.ui.theme.VaultTheme
// #endregion

// #region Text atoms
/**
 * The mockup's `italic text-vault-primary` run inside a serif headline. Used
 * with buildAnnotatedString so one headline can mix roman and emphasised text.
 */
val EmphasisSpan: SpanStyle
    @Composable get() = SpanStyle(
        fontFamily = Newsreader,
        fontStyle = FontStyle.Italic,
        color = VaultTheme.colors.primary,
    )

/** `text-[10px] font-mono uppercase tracking-[0.24em] text-vault-primary` */
@Composable
fun Kicker(text: String, modifier: Modifier = Modifier, color: Color = VaultTheme.colors.primary) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = color,
        modifier = modifier,
    )
}

/** `text-[11px] font-semibold uppercase tracking-[0.18em] text-vault-ink/45` */
@Composable
fun SectionLabel(
    text: String,
    modifier: Modifier = Modifier,
    trailing: String? = null,
) {
    val c = VaultTheme.colors
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text.uppercase(), style = SectionLabelStyle, color = c.mute)
        if (trailing != null) {
            Text(
                trailing,
                fontFamily = JetBrainsMono,
                fontSize = 11.sp,
                color = c.mute,
            )
        }
    }
}
// #endregion

// #region Chrome

/** Bottom-sheet drag handle — `w-8 h-1 bg-vault-ink/20`. */
@Composable
fun SheetHandle(modifier: Modifier = Modifier) {
    Box(
        modifier
            .width(32.dp)
            .height(4.dp)
            .background(VaultTheme.colors.ink(0.2f), CircleShape),
    )
}

/**
 * M3 small top app bar used across onboarding: 48dp tall, 40dp back target,
 * "Step N of 6" caption, optional right-hand text action ("Why?", "Regenerate").
 */
@Composable
fun OnboardingTopBar(
    stepLabel: String,
    onBack: (() -> Unit)?,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    val c = VaultTheme.colors
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (onBack != null) {
            Box(
                Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.msg_back),
                    tint = c.ink,
                    modifier = Modifier.size(20.dp),
                )
            }
        } else {
            Spacer(Modifier.width(8.dp))
        }
        Text(
            stepLabel,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = c.ink(0.6f),
        )
        if (actionLabel != null && onAction != null) {
            Spacer(Modifier.weight(1f))
            Box(
                Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onAction)
                    .padding(horizontal = 14.dp, vertical = 8.dp),
            ) {
                Text(
                    actionLabel,
                    style = MaterialTheme.typography.labelMedium,
                    color = c.primary,
                )
            }
        }
    }
}

/**
 * Onboarding progress. Was six hand-drawn 4dp bars; the Expressive wavy
 * indicator carries the same information with motion the static bars could not,
 * and animates between steps on the theme's own spatial spring.
 */
@Composable
fun StepProgress(current: Int, total: Int = 6, modifier: Modifier = Modifier) {
    val c = VaultTheme.colors
    val progress by animateFloatAsState(
        targetValue = (current.toFloat() / total).coerceIn(0f, 1f),
        animationSpec = MaterialTheme.motionScheme.slowSpatialSpec(),
        label = "stepProgress",
    )
    LinearWavyProgressIndicator(
        progress = { progress },
        modifier = modifier.fillMaxWidth().padding(horizontal = 24.dp),
        color = c.primary,
        trackColor = c.ink(0.12f),
    )
}

/**
 * The pinned bottom action area: hairline top rule over paper, 24dp side
 * padding, and the Android gesture handle beneath the buttons.
 */
@Composable
fun BottomActionBar(
    modifier: Modifier = Modifier,
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit,
) {
    val c = VaultTheme.colors
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(c.paper)
            .topHairline(c.line)
            // BV-05: enableEdgeToEdge() means the window no longer resizes for the
            // IME on Android 15+, so without this the action bar sits behind the
            // keyboard — on Create Passphrase, 865px behind it.
            .imePadding()
            .navigationBarsPadding()
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 16.dp),
        content = content,
    )
}

/** `border-t border-vault-line` — a true 1px rule, not a 1dp box. */
private fun Modifier.topHairline(color: Color) = this.drawBehind {
    drawLine(color, Offset(0f, 0f), Offset(size.width, 0f), strokeWidth = 1f)
}
// #endregion

// #region Buttons
/** `w-full h-14 rounded-full bg-vault-ink text-vault-paper` + optional arrow. */
@Composable
fun PrimaryPillButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showArrow: Boolean = true,
    loading: Boolean = false,
    container: Color = VaultTheme.colors.ink,
    contentColor: Color = VaultTheme.colors.paper,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth().height(56.dp),
        shape = CircleShape,
        elevation = null,
        colors = ButtonDefaults.buttonColors(
            containerColor = container,
            contentColor = contentColor,
            disabledContainerColor = container.copy(alpha = 0.35f),
            disabledContentColor = contentColor.copy(alpha = 0.6f),
        ),
        contentPadding = PaddingValues(horizontal = 24.dp),
    ) {
        // LoadingIndicator is a morphing sequence of MaterialShapes rather than
        // a spinner, so a busy button stays in the same design language.
        if (loading) {
            LoadingIndicator(modifier = Modifier.size(24.dp), color = contentColor)
            Spacer(Modifier.width(12.dp))
        }
        Text(label, style = MaterialTheme.typography.labelLarge)
        if (showArrow && !loading) {
            Spacer(Modifier.width(8.dp))
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

/**
 * Two mutually exclusive options as Expressive toggle buttons. ToggleButton
 * morphs its own corner shape on check -- the behaviour the mockup's flat
 * segmented control was approximating with a background colour swap.
 */
@Composable
fun VaultToggleRow(
    options: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    // ButtonGroup is what actually connects these two: it owns the shared
    // geometry and the press squish, where a plain Row of ToggleButtons only
    // gives the per-button corner morph. No overflow indicator — two fixed
    // options never overflow.
    ButtonGroup(
        overflowIndicator = {},
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        options.forEachIndexed { index, label ->
            toggleableItem(
                checked = index == selectedIndex,
                label = label,
                onCheckedChange = { onSelect(index) },
                weight = 1f,
            )
        }
    }
}

/** `w-full h-14 rounded-full ring-[1.5px] ring-vault-line` — the secondary action. */
@Composable
fun OutlinedPillButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val c = VaultTheme.colors
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth().height(56.dp),
        shape = CircleShape,
        border = BorderStroke(1.5.dp, c.line),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = c.ink),
        contentPadding = PaddingValues(horizontal = 24.dp),
    ) {
        Text(label, style = MaterialTheme.typography.labelLarge)
    }
}

/** `w-full h-11 rounded-full text-vault-ink/60` — the "skip" tier. */
@Composable
fun SubtleTextButton(label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val c = VaultTheme.colors
    TextButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(44.dp),
        shape = CircleShape,
        colors = ButtonDefaults.textButtonColors(contentColor = c.mute),
    ) {
        Text(label, style = MaterialTheme.typography.titleSmall)
    }
}
// #endregion

// #region Surfaces
/** `rounded-[26px] bg-vault-surface ring-1 ring-vault-line overflow-hidden` */
@Composable
fun GroupCard(
    modifier: Modifier = Modifier,
    corner: Dp = CornerGroup,
    container: Color = VaultTheme.colors.surface,
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(corner)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(container)
            .border(1.dp, VaultTheme.colors.line, shape),
        content = content,
    )
}

/** `bg-white rounded-3xl ring-1 ring-black/5` — the raised card face. */
@Composable
fun WhiteCard(
    modifier: Modifier = Modifier,
    corner: Dp = CornerCard,
    onClick: (() -> Unit)? = null,
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit,
) {
    val c = VaultTheme.colors
    val shape = RoundedCornerShape(corner)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(c.card)
            .border(1.dp, c.ink(0.05f), shape)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        content = content,
    )
}

/** `bg-vault-ink text-vault-paper rounded-3xl` — the dark statement block. */
@Composable
fun InkCard(
    modifier: Modifier = Modifier,
    corner: Dp = CornerCard,
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit,
) {
    val c = VaultTheme.colors
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(corner))
            .background(c.ink),
    ) {
        CompositionLocalProvider(LocalContentColor provides c.paper, content = { content() })
    }
}

enum class NoticeTone { Neutral, Positive, Warn }

/**
 * The tinted advisory blocks: teal for reassurance, burnt-orange for the
 * "there is no reset link" warnings, surface for neutral facts.
 */
@Composable
fun NoticeCard(
    title: String,
    body: String,
    icon: ImageVector,
    tone: NoticeTone = NoticeTone.Neutral,
    modifier: Modifier = Modifier,
) {
    val c = VaultTheme.colors
    val (container, ring, iconBg, iconTint) = when (tone) {
        NoticeTone.Positive -> Quad(
            c.accent.copy(alpha = 0.12f), c.accent.copy(alpha = 0.22f),
            c.accent.copy(alpha = 0.20f), c.accent,
        )
        NoticeTone.Warn -> Quad(
            c.primary.copy(alpha = 0.08f), c.primary.copy(alpha = 0.18f),
            c.primary.copy(alpha = 0.16f), c.primary,
        )
        NoticeTone.Neutral -> Quad(c.surface, c.line, c.ink(0.08f), c.ink(0.7f))
    }
    val shape = RoundedCornerShape(CornerCard)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(container)
            .border(1.dp, ring, shape)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(18.dp))
        }
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleSmall, color = c.ink)
            Spacer(Modifier.height(4.dp))
            Text(body, style = MaterialTheme.typography.bodySmall, color = c.ink(0.6f))
        }
    }
}

private data class Quad(val a: Color, val b: Color, val c: Color, val d: Color)

/** `divide-y divide-vault-line` between rows of a grouped card. */
@Composable
fun RowDivider(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth().height(1.dp).background(VaultTheme.colors.line))
}
// #endregion

// #region Chips & tiles
/**
 * `size-11 rounded-2xl` brand mark. Falls back to the mockup's tinted monogram
 * when no vector logo ships for that institution.
 */
@Composable
fun BrandTile(
    code: String,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
) {
    val c = VaultTheme.colors
    Box(
        modifier
            .size(size)
            .clip(RoundedCornerShape(CornerTile))
            .background(c.card)
            .border(1.dp, c.ink(0.05f), RoundedCornerShape(CornerTile)),
        contentAlignment = Alignment.Center,
    ) {
        CompanyLogo(name = code, size = size - 8.dp)
    }
}

/**
 * Reveal toggle for a masked field. Every place the user types a secret needs
 * one — typing a long passphrase blind is the single biggest accessibility
 * barrier in the app, and it is worst exactly where it matters most, on the
 * lock screen. 48dp because this sits next to the field's own tap target.
 */
@Composable
fun RevealToggle(
    visible: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = VaultTheme.colors.ink(0.55f),
) {
    IconButton(onClick = onToggle, modifier = modifier.size(48.dp)) {
        Icon(
            if (visible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
            contentDescription = stringResource(
                if (visible) R.string.ui_hide_passphrase else R.string.ui_show_passphrase,
            ),
            tint = tint,
            modifier = Modifier.size(20.dp),
        )
    }
}

/**
 * The mockup's edit field, exactly: `p-3.5 bg-white rounded-2xl ring-1
 * ring-black/5`, a 10px uppercase label, and the value 4dp under it.
 *
 * Not an OutlinedTextField. The mockup has no outline, no floating label and no
 * grouping — each field is its own card in a flat 8dp-spaced list. Callers put a
 * bare BasicTextField in [content] so the value reads as text rather than as a
 * form control.
 */
@Composable
fun VaultFieldCard(
    label: String,
    modifier: Modifier = Modifier,
    supporting: String? = null,
    supportingColor: Color? = null,
    trailing: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val c = VaultTheme.colors
    Column(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CornerTile))
            .background(c.card)
            .border(1.dp, c.ink(0.05f), RoundedCornerShape(CornerTile))
            .padding(14.dp),
    ) {
        Text(
            text = label.uppercase(),
            style = FieldLabelStyle,
            color = c.mute,
        )
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.weight(1f)) { content() }
            if (trailing != null) {
                Spacer(Modifier.width(8.dp))
                trailing()
            }
        }
        if (supporting != null) {
            Spacer(Modifier.height(6.dp))
            Text(
                text = supporting,
                style = MaterialTheme.typography.bodySmall,
                color = supportingColor ?: c.mute,
            )
        }
    }
}

/** `text-vault-accent bg-vault-accent-soft rounded-lg` — one-tap copy affordance. */
@Composable
fun CopyChip(label: String = "Copy", modifier: Modifier = Modifier, onClick: (() -> Unit)? = null) {
    val c = VaultTheme.colors
    // BV-12: the painted chip stays small, but the tap target underneath it meets
    // the 48dp minimum — this is the most-tapped control in the app.
    Box(
        modifier
            .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
            .then(
                if (onClick != null) {
                    Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = onClick, role = Role.Button)
                        .semantics(mergeDescendants = true) { contentDescription = label }
                } else Modifier,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(c.accent.copy(alpha = 0.10f))
                .padding(horizontal = 10.dp, vertical = 4.dp),
        ) {
            Text(
                label,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = c.accent,
            )
        }
    }
}

/** M3 filter chip — 36dp pill, check icon and count when selected. */
@Composable
fun VaultFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    count: Int? = null,
) {
    val c = VaultTheme.colors
    // Built here: a semantics{} block is not a composable context.
    val description = if (count != null) {
        stringResource(R.string.ui_label_count, label, count)
    } else {
        label
    }
    // The corner morph used to be a hand-rolled animateDpAsState. Expressive
    // ships it as a first-class overload, which also gives a pressed shape the
    // hand-rolled version never had.
    FilterChip(
        selected = selected,
        onClick = onClick,
        shapes = FilterChipDefaults.shapes(
            shape = RoundedCornerShape(18.dp),
            selectedShape = RoundedCornerShape(12.dp),
            pressedShape = RoundedCornerShape(8.dp),
        ),
        modifier = modifier.semantics(mergeDescendants = true) {
            contentDescription = description
        },
        leadingIcon = if (selected) {
            {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                )
            }
        } else {
            null
        },
        trailingIcon = count?.let {
            {
                Text(
                    it.toString(),
                    fontFamily = JetBrainsMono,
                    fontSize = 10.5.sp,
                    color = if (selected) c.paper.copy(alpha = 0.7f) else c.ink(0.4f),
                )
            }
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.Transparent,
            labelColor = c.ink(0.7f),
            selectedContainerColor = c.primary,
            selectedLabelColor = c.paper,
            selectedLeadingIconColor = c.paper,
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = c.line,
            selectedBorderColor = c.primary,
        ),
        label = { Text(label, style = MaterialTheme.typography.labelMedium) },
    )
}

/** The state pills under a headline: "Unlocked · auto-lock 2:00", "9 institutions". */
@Composable
fun StatusPill(
    text: String,
    modifier: Modifier = Modifier,
    tone: NoticeTone = NoticeTone.Neutral,
    showDot: Boolean = false,
) {
    val c = VaultTheme.colors
    val container = if (tone == NoticeTone.Positive) c.accent.copy(alpha = 0.10f) else c.ink(0.05f)
    val content = if (tone == NoticeTone.Positive) c.accent else c.ink(0.6f)
    Row(
        modifier = modifier
            .height(28.dp)
            .clip(CircleShape)
            .background(container)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        if (showDot) Box(Modifier.size(6.dp).background(content, CircleShape))
        Text(
            text,
            fontSize = 11.sp,
            fontWeight = if (tone == NoticeTone.Positive) FontWeight.SemiBold else FontWeight.Medium,
            color = content,
        )
    }
}

/** `h-[18px] px-1.5 rounded-md` micro-badge beside a record title (UPI / ID / TOTP). */
@Composable
fun MicroBadge(text: String, modifier: Modifier = Modifier) {
    val c = VaultTheme.colors
    Box(
        modifier
            .height(18.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(c.ink(0.07f))
            .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text,
            fontSize = 9.5.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.4.sp,
            color = c.mute,
        )
    }
}
// #endregion

// #region Rows
/**
 * The vault's canonical list row: 44dp brand tile, title with optional badge,
 * mono metadata line, trailing chevron.
 */
@Composable
fun VaultListRow(
    title: String,
    meta: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    badge: String? = null,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    val c = VaultTheme.colors
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick, role = Role.Button)
            .semantics(mergeDescendants = true) {
                contentDescription = listOfNotNull(title, badge, meta).joinToString(", ")
            }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        leading?.invoke()
        Column(Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    color = c.ink,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false),
                )
                if (badge != null) MicroBadge(badge)
            }
            if (meta != null) {
                Spacer(Modifier.height(2.dp))
                Text(
                    meta,
                    style = MetaTextStyle,
                    color = c.mute,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        if (trailing != null) {
            trailing()
        } else {
            Icon(
                Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = c.ink(0.3f),
                modifier = Modifier.size(18.dp),
            )
        }
    }
}

// #endregion

// #region Search dock
/** `h-14 rounded-full bg-vault-surface ring-1` search bar with trailing avatar. */
@Composable
fun SearchDock(
    placeholder: String,
    initials: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onAvatarClick: (() -> Unit)? = null,
) {
    val c = VaultTheme.colors
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(CircleShape)
            .background(c.surface)
            .border(1.dp, c.line, CircleShape)
            .clickable(onClick = onClick, role = Role.Button)
            .semantics(mergeDescendants = true) { contentDescription = placeholder }
            .padding(start = 16.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(
            Icons.Outlined.Search,
            contentDescription = null,
            tint = c.ink(0.5f),
            modifier = Modifier.size(20.dp),
        )
        Text(
            placeholder,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 14.sp,
            color = c.mute,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        Box(
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(c.primary.copy(alpha = 0.15f))
                .then(if (onAvatarClick != null) Modifier.clickable(onClick = onAvatarClick) else Modifier),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                initials,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = c.primary,
            )
        }
    }
}
// #endregion

// #region Navigation
enum class VaultTab(@StringRes val labelRes: Int) {
    Vault(R.string.tab_vault),
    Codes(R.string.tab_codes),
    Templates(R.string.tab_templates),
    Settings(R.string.tab_settings),
}

private fun VaultTab.icon(active: Boolean): ImageVector = when (this) {
    VaultTab.Vault -> if (active) Icons.Filled.Home else Icons.Outlined.Home
    VaultTab.Codes -> if (active) Icons.Filled.Schedule else Icons.Outlined.Schedule
    VaultTab.Templates -> if (active) Icons.Filled.GridView else Icons.Outlined.GridView
    VaultTab.Settings -> if (active) Icons.Filled.Settings else Icons.Outlined.Settings
}

/**
 * M3 Expressive bottom navigation, edge-to-edge and flush to the bottom, with
 * the 64x32dp pill indicator behind the active icon and the gesture handle
 * sitting in the system inset below.
 */
@Composable
fun VaultNavBar(
    active: VaultTab,
    onSelect: (VaultTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = VaultTheme.colors
    // Was ~65 lines of hand-built Row/Column with a manually drawn pill
    // indicator. ShortNavigationBar is the M3 Expressive bottom bar: it animates
    // the indicator, handles insets, and carries Role.Tab semantics itself.
    ShortNavigationBar(
        modifier = modifier.topHairline(c.line),
        containerColor = c.paper,
        contentColor = c.ink,
    ) {
        VaultTab.entries.forEach { tab ->
            val isActive = tab == active
            ShortNavigationBarItem(
                selected = isActive,
                onClick = { onSelect(tab) },
                icon = {
                    Icon(
                        tab.icon(isActive),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                },
                label = {
                    Text(
                        stringResource(tab.labelRes),
                        fontSize = 11.sp,
                        fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Medium,
                    )
                },
                colors = ShortNavigationBarItemDefaults.colors(
                    selectedIconColor = c.primary,
                    selectedTextColor = c.ink,
                    selectedIndicatorColor = c.primary.copy(alpha = 0.15f),
                    unselectedIconColor = c.ink(0.55f),
                    unselectedTextColor = c.ink(0.55f),
                ),
            )
        }
    }
}

/**
 * The same four destinations as a side rail, for windows at least 600dp wide
 * (landscape phones, tablets, foldables). Bottom bars waste vertical space in
 * landscape and put the targets under the user's thumbs-off-screen; M3's
 * adaptive guidance switches to a rail at the medium breakpoint.
 */
@Composable
fun VaultNavRail(
    active: VaultTab,
    onSelect: (VaultTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = VaultTheme.colors
    WideNavigationRail(
        modifier = modifier,
        colors = WideNavigationRailDefaults.colors(containerColor = c.paper),
    ) {
        VaultTab.entries.forEach { tab ->
            val isActive = tab == active
            WideNavigationRailItem(
                selected = isActive,
                onClick = { onSelect(tab) },
                railExpanded = false,
                icon = {
                    Icon(
                        tab.icon(isActive),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                },
                label = {
                    Text(
                        stringResource(tab.labelRes),
                        fontSize = 11.sp,
                        fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Medium,
                    )
                },
                colors = WideNavigationRailItemDefaults.colors(
                    selectedIconColor = c.primary,
                    selectedTextColor = c.ink,
                    selectedIndicatorColor = c.primary.copy(alpha = 0.15f),
                    unselectedIconColor = c.ink(0.55f),
                    unselectedTextColor = c.ink(0.55f),
                ),
            )
        }
    }
}

/**
 * M3 extended FAB floating above the nav bar — 56dp tall, 16dp corner, burnt
 * orange with a coloured shadow.
 */
@Composable
fun VaultExtendedFab(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.Add,
) {
    val c = VaultTheme.colors
    Row(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(CornerTile))
            .background(c.primary)
            .clickable(onClick = onClick, role = Role.Button)
            .semantics(mergeDescendants = true) { contentDescription = label }
            .padding(start = 16.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(icon, contentDescription = null, tint = c.paper, modifier = Modifier.size(20.dp))
        Text(
            label,
            style = MaterialTheme.typography.labelLarge,
            fontSize = 14.sp,
            color = c.paper,
        )
    }
}
// #endregion

// #region Shapes
/**
 * The mockup drew its hero blobs as CSS elliptical `border-radius` values, which
 * this file used to reproduce with a hand-rolled Shape (and a hand-rolled port of
 * the CSS radius-overlap normalisation). M3 Expressive ships the real thing:
 * MaterialShapes is a set of 35 RoundedPolygons designed for exactly this, backed
 * by androidx.graphics.shapes — so they can also morph, which a static Path could
 * never do.
 *
 * Read as composables because toShape() resolves against the current density.
 */
/**
 * A [Shape] that interpolates between two MaterialShapes. Morph gives the path
 * at a progress value; the scale-and-centre step is the same one
 * RoundedPolygon.toShape() performs, so a morphing blob sits exactly where a
 * static one did.
 */
private class MorphShape(private val morph: Morph, private val progress: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val path = morph.toPath(progress)
        path.transform(Matrix().apply { scale(x = size.width, y = size.height) })
        path.translate(size.center - path.getBounds().center)
        return Outline.Generic(path)
    }
}

/**
 * Two MaterialShapes, breathed between. The blobs are decorative, so this is
 * exactly the kind of motion that has to stop when the user has asked the system
 * to reduce it — at animator scale 0 the shape is pinned at rest instead.
 */
@Composable
private fun breathingBlob(
    from: RoundedPolygon,
    to: RoundedPolygon,
    durationMillis: Int,
): Shape {
    val morph = remember(from, to) { Morph(from, to) }
    val context = LocalContext.current
    val animated = remember(context) {
        Settings.Global.getFloat(
            context.contentResolver,
            Settings.Global.ANIMATOR_DURATION_SCALE,
            1f,
        ) > 0f
    }
    if (!animated) return remember(morph) { MorphShape(morph, 0f) }

    val transition = rememberInfiniteTransition(label = "blob")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "blobProgress",
    )
    // A new Shape per progress value is what makes clip() re-read the outline.
    return remember(morph, progress) { MorphShape(morph, progress) }
}

/**
 * The hero blobs. Each is a morph between two MaterialShapes rather than a
 * single static one, on deliberately mismatched periods so the composition never
 * repeats a pose.
 */
object VaultBlobs {
    /** Large soft organic mass — the hero's primary blob. */
    val Primary: Shape
        @Composable get() = breathingBlob(MaterialShapes.Puffy, MaterialShapes.Cookie9Sided, 9000)

    /** Petalled counterweight — the accent satellite. */
    val Accent: Shape
        @Composable get() = breathingBlob(MaterialShapes.Clover4Leaf, MaterialShapes.Flower, 11000)

    /** Small dark pebble anchoring the composition. */
    val Ink: Shape
        @Composable get() = breathingBlob(MaterialShapes.Cookie9Sided, MaterialShapes.Pill, 13000)
}
// #endregion
