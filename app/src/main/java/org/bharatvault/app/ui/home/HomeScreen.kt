/**
 * @file HomeScreen.kt
 * @description S6 Home + S7 search (§5.1): records grouped by institution,
 *              live filter with Hinglish transliteration, teaching empty state
 *              (§10.5). SECRET/PIN/H values are NEVER indexed.
 *
 * [TABLE OF CONTENTS]
 * 1. SEARCH INDEX (S7)
 * 2. HOME SCREEN
 * 3. RECORD ROW / EMPTY STATE
 */
package org.bharatvault.app.ui.home

// #region Imports
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.bharatvault.app.BharatVaultApp
import org.bharatvault.app.R
import org.bharatvault.app.ui.common.FavoriteStar
import org.bharatvault.app.ui.common.InstitutionMonogram
import org.bharatvault.app.ui.common.TemplateIcon
import org.bharatvault.core.model.FieldType
import org.bharatvault.core.model.Record
import org.bharatvault.core.model.Sensitivity
import org.bharatvault.core.model.TemplateCatalog
// #endregion

// #region Search index (S7 — never index SECRET/PIN/H)
/** Latin→Devanagari common-term table so Hinglish queries hit Hindi-titled records (§5.1 S7). */
private val Transliteration = mapOf(
    "bijli" to "बिजली", "bank" to "बैंक", "paisa" to "पैसा", "gas" to "गैस", "pani" to "पानी",
    "bima" to "बीमा", "card" to "कार्ड", "phone" to "फोन", "ghar" to "घर",
)

fun searchableText(record: Record, catalog: TemplateCatalog): String {
    val template = catalog.byId(record.template_id)
    val indexableFields = record.fields.mapNotNull { (k, v) ->
        val field = template?.fields?.firstOrNull { it.k == k } ?: return@mapNotNull null
        val safeType = field.type in setOf(
            FieldType.TEXT, FieldType.EMAIL, FieldType.PHONE, FieldType.URL, FieldType.PICKER, FieldType.IFSC,
        )
        if (safeType && field.sensitivity != Sensitivity.H) v else null // NEVER SECRET/PIN/H
    }
    return (listOf(record.title, record.institution) + record.tags + indexableFields)
        .joinToString(" ")
        .lowercase()
}

fun matchesQuery(haystack: String, query: String): Boolean {
    if (query.isBlank()) return true
    return query.lowercase().split(" ").filter { it.isNotBlank() }.all { token ->
        haystack.contains(token) || (Transliteration[token]?.let { haystack.contains(it) } == true)
    }
}
// #endregion

// #region Home screen (S6)
@Composable
fun HomeScreen(app: BharatVaultApp, onAdd: () -> Unit, onOpen: (String) -> Unit) {
    val body by app.repository.body.collectAsState()
    var query by remember { mutableStateOf("") }
    val records = body?.records.orEmpty()
    val fallbackGroup = stringResource(R.string.scr_home_group_other)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.scr_home_add))
            }
        },
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                placeholder = { Text(stringResource(R.string.scr_home_search_hint)) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                singleLine = true,
            )

            if (records.isEmpty()) {
                EmptyHome()
                return@Column
            }

            val filtered = records.filter { matchesQuery(searchableText(it, app.catalog.templates), query) }
            if (filtered.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.scr_home_no_results), style = MaterialTheme.typography.bodyLarge)
                }
                return@Column
            }

            // §5.1 S6: grouped by institution, fallback to template group
            val groups = filtered
                .groupBy { record ->
                    record.institution.ifBlank {
                        app.catalog.templates.byId(record.template_id)?.id?.let { templateLabel(it) } ?: fallbackGroup
                    }
                }
                .toSortedMap()

            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                groups.forEach { (institution, groupRecords) ->
                    item(key = "header-$institution") {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
                        ) {
                            InstitutionMonogram(institution, modifier = Modifier.size(28.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(institution, style = MaterialTheme.typography.titleMedium)
                        }
                    }
                    items(groupRecords.sortedByDescending { it.favorite }, key = { it.uuid }) { record ->
                        RecordRow(record, app) { onOpen(record.uuid) }
                    }
                }
                item { Spacer(Modifier.height(88.dp)) } // clear the FAB
            }
        }
    }
}

@Composable
private fun templateLabel(templateId: String): String {
    val res = templateStringId(templateId)
    return if (res != 0) stringResource(res) else templateId
}

private fun templateStringId(templateId: String): Int = when (templateId) {
    "bank_account" -> R.string.tpl_bank_account
    "card" -> R.string.tpl_card
    "upi" -> R.string.tpl_upi
    "demat" -> R.string.tpl_demat
    "insurance" -> R.string.tpl_insurance
    "gov_id" -> R.string.tpl_gov_id
    "epf_pension" -> R.string.tpl_epf_pension
    "utility" -> R.string.tpl_utility
    "telecom" -> R.string.tpl_telecom
    "app_profile" -> R.string.tpl_app_profile
    "login" -> R.string.tpl_login
    "secure_note" -> R.string.tpl_secure_note
    else -> 0
}
// #endregion

// #region Record row / empty state
@Composable
private fun RecordRow(record: Record, app: BharatVaultApp, onClick: () -> Unit) {
    val template = app.catalog.templates.byId(record.template_id)
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TemplateIcon(template?.icon ?: "key", modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(record.title, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.width(6.dp))
                    FavoriteStar(record.favorite)
                }
                Text(
                    templateLabel(record.template_id),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

/** §10.5: teaching empty state — sample cards + rotating scam-awareness tips. */
@Composable
private fun EmptyHome() {
    val tips = listOf(
        R.string.msg_safety_tip_1, R.string.msg_safety_tip_2, R.string.msg_safety_tip_3,
        R.string.msg_safety_tip_4, R.string.msg_safety_tip_5, R.string.msg_safety_tip_6,
    )
    val tip = remember { tips.random() }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(top = 24.dp)) {
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                TemplateIcon("bank", modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(16.dp))
                Text(stringResource(R.string.scr_home_empty_sample_bank), style = MaterialTheme.typography.bodyMedium)
            }
        }
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                TemplateIcon("card", modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(16.dp))
                Text(stringResource(R.string.scr_home_empty_sample_card), style = MaterialTheme.typography.bodyMedium)
            }
        }
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Shield,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp),
                )
                Spacer(Modifier.width(16.dp))
                Text(stringResource(tip), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
// #endregion
