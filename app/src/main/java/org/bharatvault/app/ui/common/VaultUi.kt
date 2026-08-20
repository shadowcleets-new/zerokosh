/**
 * @file VaultUi.kt
 * @description The BharatVault design system, transcribed from the Lovable
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
package org.bharatvault.app.ui.common

// #region Imports
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.bharatvault.app.ui.theme.CornerCard
import org.bharatvault.app.ui.theme.CornerGroup
import org.bharatvault.app.ui.theme.CornerTile
import org.bharatvault.app.ui.theme.JetBrainsMono
import org.bharatvault.app.ui.theme.MetaTextStyle
import org.bharatvault.app.ui.theme.Newsreader
import org.bharatvault.app.ui.theme.SectionLabelStyle
import org.bharatvault.app.ui.theme.VaultTheme
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
/** Android gesture handle — `h-[3px] w-24 rounded-full bg-vault-ink/80`. */
@Composable
fun GestureHandle(modifier: Modifier = Modifier, color: Color = VaultTheme.colors.ink(0.8f)) {
    Box(
        modifier
            .width(96.dp)
            .height(3.dp)
            .background(color, CircleShape),
    )
}

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
                    contentDescription = "Back",
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

/** M3 segmented linear progress — one 4dp-tall bar per onboarding step. */
@Composable
fun StepProgress(current: Int, total: Int = 6, modifier: Modifier = Modifier) {
    val c = VaultTheme.colors
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        repeat(total) { i ->
            Box(
                Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(
                        if (i < current) c.primary else c.ink(0.12f),
                        CircleShape,
                    ),
            )
        }
    }
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
        Text(label, style = MaterialTheme.typography.labelLarge)
        if (showArrow) {
            Spacer(Modifier.width(8.dp))
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
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
    Row(
        modifier = modifier
            .height(36.dp)
            .clip(CircleShape)
            .background(if (selected) c.primary else Color.Transparent)
            .border(1.dp, if (selected) c.primary else c.line, CircleShape)
            .clickable(onClick = onClick, role = Role.Button)
            .semantics(mergeDescendants = true) {
                contentDescription = if (count != null) "$label, $count" else label
                if (selected) stateDescription = "Selected"
            }
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        if (selected) {
            Icon(
                Icons.Filled.Check,
                contentDescription = null,
                tint = c.paper,
                modifier = Modifier.size(14.dp),
            )
        }
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) c.paper else c.ink(0.7f),
        )
        if (count != null) {
            Text(
                count.toString(),
                fontFamily = JetBrainsMono,
                fontSize = 10.5.sp,
                color = if (selected) c.paper.copy(alpha = 0.7f) else c.ink(0.4f),
            )
        }
    }
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

/** Settings-style row: label left, value right, teal value when it is "on". */
@Composable
fun SettingRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    accent: Boolean = false,
    onClick: (() -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    val c = VaultTheme.colors
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) {
                    Modifier
                        .clickable(onClick = onClick, role = Role.Button)
                        .semantics(mergeDescendants = true) {
                            contentDescription = "$label, $value"
                        }
                } else Modifier,
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = c.ink)
        if (trailing != null) {
            trailing()
        } else {
            Text(
                value,
                fontSize = 12.sp,
                fontWeight = if (accent) FontWeight.SemiBold else FontWeight.Normal,
                color = if (accent) c.accent else c.mute,
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
enum class VaultTab(val label: String) {
    Vault("Vault"),
    Codes("Codes"),
    Templates("Templates"),
    Settings("Settings"),
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
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(c.paper)
            .topHairline(c.line)
            .navigationBarsPadding()
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp, bottom = 12.dp),
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            VaultTab.entries.forEach { tab ->
                val isActive = tab == active
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onSelect(tab) },
                        )
                        .semantics(mergeDescendants = true) {
                            role = Role.Tab
                            contentDescription = tab.label
                            if (isActive) stateDescription = "Selected"
                        }
                        .padding(top = 6.dp, bottom = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        Modifier
                            .height(32.dp)
                            .width(64.dp)
                            .clip(CircleShape)
                            .background(if (isActive) c.primary.copy(alpha = 0.15f) else Color.Transparent),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            tab.icon(isActive),
                            contentDescription = tab.label,
                            tint = if (isActive) c.primary else c.ink(0.55f),
                            modifier = Modifier.size(24.dp),
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        tab.label,
                        fontSize = 11.sp,
                        fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Medium,
                        color = if (isActive) c.ink else c.ink(0.55f),
                    )
                }
            }
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
 * CSS `border-radius: a% b% c% d% / e% f% g% h%` — the M3 "morphed shape" blobs
 * on the Welcome hero and the empty-state illustration. Each corner gets an
 * independent horizontal and vertical radius, which is what makes the silhouette
 * read as organic rather than as a rounded rectangle.
 */
class BlobShape(
    private val hTopLeft: Float, private val hTopRight: Float,
    private val hBottomRight: Float, private val hBottomLeft: Float,
    private val vTopLeft: Float, private val vTopRight: Float,
    private val vBottomRight: Float, private val vBottomLeft: Float,
) : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val w = size.width
        val h = size.height
        if (w <= 0f || h <= 0f) return Outline.Generic(Path())

        // BV-19: CSS scales every radius by a common factor when the pair on any
        // one side exceeds that side's length (CSS Backgrounds §5.1). Without it
        // BlobPrimary's right side — 45% + 60% = 105% — drew a short backwards
        // segment where the two corner arcs overlapped.
        fun ratio(sum: Float, side: Float) = if (sum > side) side / sum else 1f
        val f = minOf(
            ratio(w * (hTopLeft + hTopRight) / 100f, w),
            ratio(w * (hBottomLeft + hBottomRight) / 100f, w),
            ratio(h * (vTopLeft + vBottomLeft) / 100f, h),
            ratio(h * (vTopRight + vBottomRight) / 100f, h),
        )
        fun hx(p: Float) = w * p / 100f * f
        fun vy(p: Float) = h * p / 100f * f

        val path = Path().apply {
            moveTo(hx(hTopLeft), 0f)
            lineTo(w - hx(hTopRight), 0f)
            arcTo(Rect(w - 2 * hx(hTopRight), 0f, w, 2 * vy(vTopRight)), 270f, 90f, false)
            lineTo(w, h - vy(vBottomRight))
            arcTo(Rect(w - 2 * hx(hBottomRight), h - 2 * vy(vBottomRight), w, h), 0f, 90f, false)
            lineTo(hx(hBottomLeft), h)
            arcTo(Rect(0f, h - 2 * vy(vBottomLeft), 2 * hx(hBottomLeft), h), 90f, 90f, false)
            lineTo(0f, vy(vTopLeft))
            arcTo(Rect(0f, 0f, 2 * hx(hTopLeft), 2 * vy(vTopLeft)), 180f, 90f, false)
            close()
        }
        return Outline.Generic(path)
    }
}

/** `border-radius: 48% 52% 62% 38% / 55% 45% 60% 40%` — the large primary blob. */
val BlobPrimary = BlobShape(48f, 52f, 62f, 38f, 55f, 45f, 60f, 40f)

/** `border-radius: 60% 40% 45% 55% / 50% 60% 40% 50%` — the accent satellite. */
val BlobAccent = BlobShape(60f, 40f, 45f, 55f, 50f, 60f, 40f, 50f)

/** `border-radius: 38% 62% 55% 45% / 60% 40% 60% 40%` — the small ink pebble. */
val BlobInk = BlobShape(38f, 62f, 55f, 45f, 60f, 40f, 60f, 40f)
// #endregion
