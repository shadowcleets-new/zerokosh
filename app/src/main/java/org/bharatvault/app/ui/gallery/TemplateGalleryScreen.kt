package org.bharatvault.app.ui.gallery

// #region Imports
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items as rowItems
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.bharatvault.app.BharatVaultApp
import org.bharatvault.app.R
import org.bharatvault.app.ui.common.InstitutionMonogram
import org.bharatvault.app.ui.common.TemplateIcon
// #endregion

/** S8 (§5.1): grid of the 12 templates + app_profile quick-add presets row (§2.4). */
@Composable
fun TemplateGalleryScreen(app: BharatVaultApp, onPick: (templateId: String, presetName: String?) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(stringResource(R.string.scr_gallery_title), style = MaterialTheme.typography.displaySmall)
        Spacer(Modifier.height(16.dp))

        Text(stringResource(R.string.scr_gallery_quick_add), style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        LazyRow {
            rowItems(app.catalog.appPresets) { preset ->
                Card(
                    onClick = { onPick("app_profile", preset) },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(12.dp).width(64.dp),
                    ) {
                        InstitutionMonogram(preset, modifier = Modifier.size(40.dp))
                        Spacer(Modifier.height(6.dp))
                        Text(
                            preset,
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1,
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            items(app.catalog.templates.templates) { template ->
                Card(
                    onClick = { onPick(template.id, null) },
                    modifier = Modifier.padding(6.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                    ) {
                        TemplateIcon(template.icon, modifier = Modifier.size(32.dp))
                        Spacer(Modifier.height(8.dp))
                        Text(
                            templateName(template.id),
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun templateName(templateId: String): String = stringResource(
    when (templateId) {
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
        else -> R.string.tpl_secure_note
    },
)
