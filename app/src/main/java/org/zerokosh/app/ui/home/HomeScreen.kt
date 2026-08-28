/**
 * @file HomeScreen.kt
 * @description S7 "My Vault", rebuilt against the Lovable mockups
 *              "Home · Populated" and "Home · Empty": docked search bar, serif
 *              count headline, live state pills, M3 filter chips, and records
 *              grouped into connected 26dp list cards.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS & DEPENDENCIES
 * 2. MAIN SCREEN
 * 3. HEADER (headline, state pills, filter chips)
 * 4. GROUPED RECORD LIST
 * 5. EMPTY STATE
 */
package org.zerokosh.app.ui.home

// #region Imports
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zerokosh.app.R
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.ui.backup.rememberImportBackup
import org.zerokosh.app.ui.common.BrandTile
import org.zerokosh.app.ui.common.EmphasisSpan
import org.zerokosh.app.ui.common.GroupCard
import org.zerokosh.app.ui.common.NoticeTone
import org.zerokosh.app.ui.common.RecordCategory
import org.zerokosh.app.ui.common.RowDivider
import org.zerokosh.app.ui.common.SearchDock
import org.zerokosh.app.ui.common.SectionLabel
import org.zerokosh.app.ui.common.StatusPill
import org.zerokosh.app.ui.common.VaultBlobs
import org.zerokosh.app.ui.common.VaultFilterChip
import org.zerokosh.app.ui.common.VaultListRow
import org.zerokosh.app.ui.common.category
import org.zerokosh.app.ui.common.recordBadge
import org.zerokosh.app.ui.common.recordMeta
import org.zerokosh.app.ui.motion.LocalAnimatedVisibilityScope
import org.zerokosh.app.ui.motion.LocalSharedTransitionScope
import org.zerokosh.app.ui.theme.CornerGroup
import org.zerokosh.app.ui.theme.VaultTheme
import org.zerokosh.core.model.Record
// #endregion

// #region Main screen
@Composable
fun HomeScreen(
    app: ZerokoshApp,
    onScrollHideFab: (Boolean) -> Unit = {},
    onOpen: (String) -> Unit,
    onAdd: () -> Unit,
    onQuickAdd: (templateId: String, preset: String?, brand: String?) -> Unit,
) {
    val c = VaultTheme.colors
    val importBackup = rememberImportBackup(app)
    val body by app.repository.body.collectAsState()
    val records = body?.records.orEmpty()

    // BV-11: rememberSaveable so a rotation doesn't drop the query and the
    // selected chip. The category is stored by name — an enum needs no Saver then.
    var searching by rememberSaveable { mutableStateOf(false) }
    var query by rememberSaveable { mutableStateOf("") }
    var filterName by rememberSaveable { mutableStateOf<String?>(null) }
    val filter = filterName?.let { name -> RecordCategory.entries.firstOrNull { it.name == name } }

    val matching = remember(records, query) {
        if (query.isBlank()) records
        else records.filter {
            it.title.contains(query, true) ||
                it.institution.contains(query, true) ||
                it.fields.values.any { v -> v.contains(query, true) }
        }
    }
    val visible = remember(matching, filter) {
        if (filter == null) matching else matching.filter { it.category == filter }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(c.paper)
            .statusBarsPadding()
            .imePadding(),
    ) {
        Box(Modifier.padding(horizontal = 16.dp).padding(top = 12.dp, bottom = 8.dp)) {
            if (searching) {
                ActiveSearchBar(
                    query = query,
                    onQueryChange = { query = it },
                    onClose = { searching = false; query = "" },
                )
            } else {
                SearchDock(
                    placeholder = stringResource(R.string.hm_search_hint),
                    initials = vaultInitials(records),
                    onClick = { searching = true },
                )
            }
        }

        if (records.isEmpty()) {
            EmptyVault(
                onAdd = onAdd,
                onQuickAdd = onQuickAdd,
                // The row says "Open a .kosh file from this device" and used to
                // open the template gallery.
                onImport = importBackup,
                modifier = Modifier.weight(1f),
            )
            return@Column
        }

        // The Add FAB sits over this list. lastScrolledForward is Compose's own
        // direction flag, so no manual offset bookkeeping is needed; the
        // canScrollBackward guard stops the FAB hiding on an overscroll bounce
        // while already at the top.
        val listState = rememberLazyListState()
        // lastScrolledForward did not flip reliably here, so the direction is
        // derived from the first visible item instead. The 12px deadband stops
        // a jitter at rest from flapping the FAB.
        LaunchedEffect(listState) {
            var lastIndex = listState.firstVisibleItemIndex
            var lastOffset = listState.firstVisibleItemScrollOffset
            snapshotFlow {
                listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset
            }.collect { (index, offset) ->
                val down = index > lastIndex || (index == lastIndex && offset > lastOffset + 12)
                val up = index < lastIndex || (index == lastIndex && offset < lastOffset - 12)
                if (down) onScrollHideFab(true) else if (up) onScrollHideFab(false)
                lastIndex = index
                lastOffset = offset
            }
        }

        // Read outside the list builder: LazyListScope is not a composable
        // context, and recordSection writes into it.
        val pinnedLabel = stringResource(R.string.hm_pinned)
        val categoryLabels = RecordCategory.entries.associateWith { stringResource(it.labelRes) }
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f).fillMaxWidth(),
            // Clears the floating FAB and the bottom dock.
            contentPadding = PaddingValues(bottom = 132.dp),
        ) {
            item {
                VaultHeader(
                    total = records.size,
                    institutions = records.map { it.institution }.filter { it.isNotBlank() }.distinct().size,
                    autoLockMinutes = app.prefs.autoLockMinutes,
                )
            }
            item {
                FilterChipRow(
                    records = matching,
                    selected = filter,
                    onSelect = { filterName = it?.name },
                )
            }

            val pinned = visible.filter { it.favorite }
            if (pinned.isNotEmpty()) {
                recordSection(title = pinnedLabel, rows = pinned, app = app, onOpen = onOpen)
            }
            RecordCategory.entries.forEach { cat ->
                val rows = visible.filter { it.category == cat && !it.favorite }
                if (rows.isNotEmpty()) {
                    recordSection(
                        title = categoryLabels.getValue(cat),
                        rows = rows,
                        app = app,
                        onOpen = onOpen,
                    )
                }
            }

            if (visible.isEmpty()) {
                item { NoMatches(query) }
            }
            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

/** Initials for the account chip — derived from the vault, never a real name. */
private fun vaultInitials(records: List<Record>): String =
    records.firstOrNull { it.institution.isNotBlank() }
        ?.institution?.take(2)?.uppercase()
        ?: "ZK"
// #endregion

// #region Header
@Composable
private fun VaultHeader(total: Int, institutions: Int, autoLockMinutes: Int) {
    val c = VaultTheme.colors
    Column(Modifier.padding(horizontal = 24.dp).padding(top = 12.dp)) {
        Text(
            buildAnnotatedString {
                append(
                    stringResource(
                        if (total == 1) R.string.hm_count_one else R.string.hm_count_many,
                        total,
                    ),
                )
                withStyle(EmphasisSpan) { append(stringResource(R.string.hm_all_offline)) }
            },
            style = MaterialTheme.typography.displaySmall,
            color = c.ink,
        )
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatusPill(
                // BV-17: "auto-lock 1:00" read as a running MM:SS timer. There is
                // no foreground idle timeout — it locks N minutes after you leave.
                text = when (autoLockMinutes) {
                    0 -> stringResource(R.string.hm_lock_immediate)
                    1 -> stringResource(R.string.hm_lock_one)
                    else -> stringResource(R.string.hm_lock_many, autoLockMinutes)
                },
                tone = NoticeTone.Positive,
                showDot = true,
            )
            if (institutions > 0) {
                StatusPill(
                    stringResource(
                        if (institutions == 1) R.string.hm_inst_one else R.string.hm_inst_many,
                        institutions,
                    ),
                )
            }
        }
    }
}

@Composable
private fun FilterChipRow(
    records: List<Record>,
    selected: RecordCategory?,
    onSelect: (RecordCategory?) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(top = 16.dp)
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        VaultFilterChip(
            label = "All",
            selected = selected == null,
            count = records.size,
            onClick = { onSelect(null) },
        )
        RecordCategory.entries.forEach { cat ->
            val n = records.count { it.category == cat }
            if (n > 0) {
                VaultFilterChip(
                    label = stringResource(cat.labelRes),
                    selected = selected == cat,
                    count = n,
                    onClick = { onSelect(if (selected == cat) null else cat) },
                )
            }
        }
    }
}

/** The docked search bar in its editing state. */
@Composable
private fun ActiveSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClose: () -> Unit,
) {
    val c = VaultTheme.colors
    val focus = remember { FocusRequester() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(CircleShape)
            .background(c.surface)
            .border(1.dp, c.line, CircleShape)
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
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            modifier = Modifier.weight(1f).focusRequester(focus),
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp, color = c.ink),
            cursorBrush = SolidColor(c.primary),
        )
        Box(
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable(onClick = onClose),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Filled.Close,
                contentDescription = stringResource(R.string.hm_close_search),
                tint = c.ink(0.55f),
                modifier = Modifier.size(20.dp),
            )
        }
    }
    androidx.compose.runtime.LaunchedEffect(Unit) { focus.requestFocus() }
}
// #endregion

// #region Grouped record list
private fun androidx.compose.foundation.lazy.LazyListScope.recordSection(
    title: String,
    rows: List<Record>,
    app: ZerokoshApp,
    onOpen: (String) -> Unit,
) {
    item(key = "header-$title") {
        SectionLabel(
            text = title,
            trailing = rows.size.toString(),
            modifier = Modifier
                .padding(top = 20.dp, bottom = 8.dp)
                .padding(horizontal = 24.dp),
        )
    }
    // BV-07: these used to be one item{} holding the whole category so the group
    // could be drawn as a single card — which meant a 500-record shelf composed
    // every row on the first frame. Each row is its own lazy item now and the
    // connected-card look is rebuilt per row from its position in the group.
    itemsIndexed(rows, key = { _, record -> record.uuid }) { index, record ->
        GroupedRow(
            isFirst = index == 0,
            isLast = index == rows.lastIndex,
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            if (index > 0) RowDivider()
            RecordRow(app = app, record = record, onClick = { onOpen(record.uuid) })
        }
    }
}

/**
 * One row of a connected list card: fills and rings like [GroupCard], but caps
 * only the corners that sit at the ends of the group, so consecutive rows read as
 * a single card while remaining independent lazy items.
 */
@Composable
private fun GroupedRow(
    isFirst: Boolean,
    isLast: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit,
) {
    val c = VaultTheme.colors
    val shape = RoundedCornerShape(
        topStart = if (isFirst) CornerGroup else 0.dp,
        topEnd = if (isFirst) CornerGroup else 0.dp,
        bottomStart = if (isLast) CornerGroup else 0.dp,
        bottomEnd = if (isLast) CornerGroup else 0.dp,
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(c.surface)
            .drawBehind {
                // Side rails on every row; the horizontal caps only at the ends.
                // clip(shape) above trims these to the rounded corners.
                val w = 1.dp.toPx()
                drawLine(c.line, Offset(0f, 0f), Offset(0f, size.height), w)
                drawLine(c.line, Offset(size.width, 0f), Offset(size.width, size.height), w)
                if (isFirst) drawLine(c.line, Offset(0f, 0f), Offset(size.width, 0f), w)
                if (isLast) {
                    drawLine(c.line, Offset(0f, size.height), Offset(size.width, size.height), w)
                }
            },
        content = content,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun RecordRow(app: ZerokoshApp, record: Record, onClick: () -> Unit) {
    val sharedScope = LocalSharedTransitionScope.current
    val animScope = LocalAnimatedVisibilityScope.current
    val brand = record.institution.ifBlank { record.title }

    VaultListRow(
        title = record.title,
        meta = recordMeta(app.catalog, record).ifBlank { null },
        badge = recordBadge(record),
        onClick = onClick,
        modifier = if (sharedScope != null && animScope != null) {
            with(sharedScope) {
                Modifier.sharedBounds(
                    rememberSharedContentState(key = "container-${record.uuid}"),
                    animatedVisibilityScope = animScope,
                )
            }
        } else Modifier,
        leading = {
            val tileModifier = if (sharedScope != null && animScope != null) {
                with(sharedScope) {
                    Modifier.sharedElement(
                        rememberSharedContentState(key = "logo-${record.uuid}"),
                        animatedVisibilityScope = animScope,
                    )
                }
            } else Modifier
            BrandTile(code = brand, modifier = tileModifier)
        },
    )
}

@Composable
private fun NoMatches(query: String) {
    val c = VaultTheme.colors
    Column(
        Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            stringResource(R.string.hm_no_match, query),
            style = MaterialTheme.typography.headlineSmall,
            color = c.ink,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            stringResource(R.string.hm_no_match_hint),
            style = MaterialTheme.typography.bodyMedium,
            color = c.mute,
            textAlign = TextAlign.Center,
        )
    }
}
// #endregion

// #region Empty state
/**
 * A quick add names its template outright. Every one of these used to open the
 * 315-entry gallery, so tapping "PAN" asked the user to go and find PAN — the
 * shortcut cost more taps than the thing it shortcut.
 *
 * preset and brand mirror the matching gallery entry so both routes produce the
 * same record.
 */
private data class QuickAdd(
    val label: String,
    val templateId: String,
    val preset: String? = null,
    val brand: String? = null,
)

private val QuickAdds = listOf(
    QuickAdd("Bank account", "bank_account"),
    QuickAdd("UPI ID", "upi", brand = "UPI"),
    QuickAdd("Aadhaar", "aadhaar_card", preset = "Aadhaar", brand = "Aadhaar"),
    QuickAdd("PAN", "pan_card", preset = "PAN Card", brand = "PAN"),
    // A TOTP secret lives on the login template's totp field. There is no
    // authenticator template of its own to send this to.
    QuickAdd("TOTP", "login"),
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun EmptyVault(
    onAdd: () -> Unit,
    onQuickAdd: (templateId: String, preset: String?, brand: String?) -> Unit,
    onImport: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = VaultTheme.colors
    Column(
        modifier = modifier
            // BV-23: cap before filling (see Onboarding.kt).
            .widthIn(max = 560.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .padding(bottom = 132.dp),
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            buildAnnotatedString {
                append(stringResource(R.string.hm_empty_head_lead))
                withStyle(EmphasisSpan) { append(stringResource(R.string.hm_empty_head_emph)) }
            },
            style = MaterialTheme.typography.displaySmall,
            color = c.ink,
        )

        Spacer(Modifier.height(32.dp))
        // M3 expressive illustration: tonal wash + two morphed shapes + shield.
        Box(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(c.surface)
                .border(1.dp, c.line, RoundedCornerShape(32.dp)),
        ) {
            Box(
                Modifier
                    .matchParentSize()
                    .drawBehind {
                        drawRect(
                            Brush.radialGradient(
                                listOf(c.primary.copy(alpha = 0.18f), Color.Transparent),
                                center = Offset(size.width * 0.70f, size.height * 0.30f),
                                radius = size.width * 0.60f,
                            ),
                        )
                        drawRect(
                            Brush.radialGradient(
                                listOf(c.accent.copy(alpha = 0.16f), Color.Transparent),
                                center = Offset(size.width * 0.20f, size.height * 0.80f),
                                radius = size.width * 0.50f,
                            ),
                        )
                    },
            )
            Box(
                Modifier
                    .padding(start = 24.dp, top = 32.dp)
                    .size(112.dp)
                    .clip(VaultBlobs.Primary)
                    .background(c.primary.copy(alpha = 0.15f)),
            )
            Box(
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 32.dp, bottom = 40.dp)
                    .size(80.dp)
                    .clip(VaultBlobs.Accent)
                    .background(c.accent.copy(alpha = 0.18f)),
            )
            Column(
                Modifier.fillMaxWidth().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(c.primary),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        Icons.Outlined.Shield,
                        contentDescription = null,
                        tint = c.paper,
                        modifier = Modifier.size(32.dp),
                    )
                }
                Spacer(Modifier.height(20.dp))
                Text(
                    stringResource(R.string.hm_empty_blank),
                    style = MaterialTheme.typography.headlineSmall,
                    color = c.ink,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    stringResource(R.string.hm_empty_body),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = c.mute,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.widthIn(max = 240.dp),
                )
                Spacer(Modifier.height(24.dp))
                Row(
                    Modifier
                        .height(44.dp)
                        .clip(CircleShape)
                        .background(c.ink)
                        .clickable(onClick = onAdd)
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        stringResource(R.string.hm_add_first),
                        style = MaterialTheme.typography.titleSmall,
                        color = c.paper,
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        SectionLabel(stringResource(R.string.hm_start_template))
        Spacer(Modifier.height(12.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            QuickAdds.forEach { quick ->
                Row(
                    Modifier
                        .height(36.dp)
                        .clip(CircleShape)
                        .background(c.card)
                        .border(1.dp, c.line, CircleShape)
                        .clickable(role = Role.Button) {
                            onQuickAdd(quick.templateId, quick.preset, quick.brand)
                        }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = null,
                        tint = c.primary,
                        modifier = Modifier.size(14.dp),
                    )
                    Text(
                        quick.label,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = c.ink(0.8f),
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(c.card)
                .border(1.dp, c.line, RoundedCornerShape(16.dp))
                .clickable(onClick = onImport, role = Role.Button)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Box(
                Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(c.accent.copy(alpha = 0.10f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Outlined.FileDownload,
                    contentDescription = null,
                    tint = c.accent,
                    modifier = Modifier.size(20.dp),
                )
            }
            Column(Modifier.weight(1f)) {
                Text(
                    stringResource(R.string.hm_import_backup),
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 13.sp,
                    color = c.ink,
                )
                Text(
                    stringResource(R.string.hm_import_backup_note),
                    fontSize = 11.sp,
                    color = c.mute,
                )
            }
        }

        Spacer(Modifier.height(20.dp))
        TrustStrip()
    }
}

/** `Never leaves this device. Encrypted at rest.` — the teal reassurance strip. */
@Composable
fun TrustStrip(modifier: Modifier = Modifier) {
    val c = VaultTheme.colors
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(c.accent.copy(alpha = 0.08f))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(Modifier.size(6.dp).background(c.accent, CircleShape))
        Text(
            stringResource(R.string.hm_reassurance),
            fontSize = 11.sp,
            color = c.ink,
        )
    }
}
// #endregion
