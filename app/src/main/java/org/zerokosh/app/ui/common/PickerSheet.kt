/**
 * @file PickerSheet.kt
 * @description The one list used for every choice in the app — banks, brokers,
 *              insurers, states, linked records.
 *
 *              A dropdown of 80 banks meant a user whose bank starts with Y
 *              scrolled the whole alphabet to reach it. This is a modal sheet
 *              with a search field and Material 3 Expressive segmented list
 *              items: 2dp gaps, grouped corner treatment on the first and last
 *              row, and a shape that morphs on press and on selection.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS
 * 2. MATCHING (contains + the acronyms Indians actually type)
 * 3. THE SHEET
 */
@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package org.zerokosh.app.ui.common

// #region Imports
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zerokosh.core.search.matchesPickerQuery
import org.zerokosh.app.ui.theme.VaultTheme
// #endregion

// #region Matching
/** Pickers shorter than this fit on one screen; a search field would be noise. */
private const val SearchThreshold = 8

/** Picker keys whose entries are brands, so a logo helps the eye find the row. */
private val BrandPickers = setOf("banks", "brokers", "insurers", "telecom", "card_networks")

fun pickerHasLogos(typeParam: String?): Boolean = typeParam in BrandPickers

// #endregion

// #region The sheet
/**
 * @param pinned an entry always shown at the bottom, past the filter — the
 *   "Other" escape hatch, which must stay reachable when nothing matches.
 */
@Composable
fun VaultPickerSheet(
    title: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    onDismiss: () -> Unit,
    logos: Boolean = false,
    pinned: String? = null,
) {
    val c = VaultTheme.colors
    // Hidden + Expanded only: a half-open state on a searchable list just means
    // the user drags before they can type.
    val sheetState = rememberBottomSheetState(
        initialValue = SheetValue.Hidden,
        enabledValues = setOf(SheetValue.Hidden, SheetValue.Expanded),
    )
    var query by remember { mutableStateOf("") }
    val searchable = options.size >= SearchThreshold
    val shown = remember(query, options) {
        if (query.isBlank()) options else options.filter { matchesPickerQuery(it, query) }
    }
    // Open on the current choice rather than at the top; scrolling back to your
    // own bank to confirm it is the same chore as finding it the first time.
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = options.indexOf(selected).coerceAtLeast(0),
    )
    val filteredState = rememberLazyListState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = c.paper,
    ) {
        // A searchable sheet gets a fixed height rather than one derived from its
        // content. Sized to content, raising the keyboard translated the whole
        // sheet off the bottom of the screen instead of shrinking the list.
        // ModalBottomSheet already consumes the IME and navigation-bar insets.
        Column(
            Modifier
                .fillMaxWidth()
                .then(if (searchable) Modifier.fillMaxHeight(0.9f) else Modifier),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = c.ink,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 12.dp),
            )
            if (searchable) {
                PickerSearchField(
                    query = query,
                    onQueryChange = { query = it },
                    modifier = Modifier.padding(horizontal = 20.dp),
                )
                Spacer(Modifier.height(12.dp))
            }
            if (shown.isEmpty()) {
                Text(
                    text = "No match for \"$query\"",
                    style = MaterialTheme.typography.bodyMedium,
                    color = c.mute,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
                )
            }
            LazyColumn(
                state = if (query.isBlank()) listState else filteredState,
                modifier = Modifier.weight(1f, fill = false),
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
            ) {
                itemsIndexed(shown, key = { i, o -> "$i:$o" }) { index, option ->
                    PickerRow(
                        label = option,
                        selected = option == selected,
                        index = index,
                        count = shown.size,
                        logos = logos,
                        onClick = { onSelect(option); onDismiss() },
                    )
                }
                if (pinned != null) {
                    item(key = " pinned") {
                        Spacer(Modifier.height(8.dp))
                        PickerRow(
                            label = pinned,
                            selected = false,
                            index = 0,
                            count = 1,
                            logos = false,
                            onClick = { onSelect(pinned); onDismiss() },
                        )
                    }
                }
            }
        }
    }
}

/**
 * One M3 Expressive segmented list item. [ListItemDefaults.segmentedShapes]
 * rounds the outer corners of the first and last rows so the group reads as one
 * block, and swaps in a squarer shape while pressed.
 */
@Composable
private fun PickerRow(
    label: String,
    selected: Boolean,
    index: Int,
    count: Int,
    logos: Boolean,
    onClick: () -> Unit,
) {
    val c = VaultTheme.colors
    SegmentedListItem(
        selected = selected,
        onClick = onClick,
        shapes = ListItemDefaults.segmentedShapes(index = index, count = count),
        colors = ListItemDefaults.segmentedColors(
            containerColor = c.card,
            contentColor = c.ink,
            selectedContainerColor = c.primary.copy(alpha = 0.16f),
            selectedContentColor = c.ink,
            selectedTrailingContentColor = c.primary,
        ),
        leadingContent = if (logos) {
            { BrandTile(code = label, size = 36.dp) }
        } else {
            null
        },
        trailingContent = if (selected) {
            { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(20.dp)) }
        } else {
            null
        },
    ) {
        Text(label, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

/** The [SearchDock] pill, made editable. */
@Composable
private fun PickerSearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = VaultTheme.colors
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(c.surface, CircleShape)
            .border(1.dp, c.line, CircleShape)
            .padding(start = 16.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(Icons.Outlined.Search, null, tint = c.ink(0.5f), modifier = Modifier.size(20.dp))
        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            if (query.isEmpty()) {
                Text("Search", style = MaterialTheme.typography.bodyLarge, fontSize = 14.sp, color = c.mute)
            }
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp, color = c.ink),
                cursorBrush = SolidColor(c.primary),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                modifier = Modifier.fillMaxWidth(),
            )
        }
        if (query.isNotEmpty()) {
            IconButton(onClick = { onQueryChange("") }, modifier = Modifier.size(44.dp)) {
                Icon(Icons.Outlined.Close, "Clear search", tint = c.ink(0.55f), modifier = Modifier.size(18.dp))
            }
        }
    }
}
// #endregion
