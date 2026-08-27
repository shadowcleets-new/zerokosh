/**
 * @file VaultHealthScreen.kt
 * @description Findings across the whole vault (§5.12).
 *
 *              Every per-record check already existed. What no screen did was
 *              compare records to each other, which is where the finding that
 *              matters most lives: the same password on a bank and a shopping
 *              site is invisible to anything that sees one record at a time.
 *
 *              Deliberately not a score out of 100. A number invites people to
 *              optimise the number; a list of specific records invites them to
 *              fix the records.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS
 * 2. SCREEN
 * 3. ROW
 */
package org.zerokosh.app.ui.health

// #region Imports
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.ui.common.OnboardingTopBar
import org.zerokosh.app.ui.common.fieldLabel
import org.zerokosh.app.ui.theme.VaultTheme
import org.zerokosh.core.health.HealthFinding
import org.zerokosh.core.health.HealthKind
import org.zerokosh.core.health.reviewVault
// #endregion

// #region Screen
@Composable
fun VaultHealthScreen(app: ZerokoshApp, onBack: () -> Unit, onOpen: (String) -> Unit) {
    val c = VaultTheme.colors
    val body by app.repository.body.collectAsState()
    val records = body?.records.orEmpty()

    // Recomputed only when the records change: it walks every field of every
    // record, which is cheap for a vault but not free on every recomposition.
    val findings = remember(records) {
        reviewVault(
            records = records,
            templates = app.catalog.templates.templates.associateBy { it.id },
            nowMs = System.currentTimeMillis(),
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(c.paper)
            .statusBarsPadding(),
    ) {
        OnboardingTopBar(stepLabel = "Vault review", onBack = onBack)

        Column(Modifier.padding(horizontal = 24.dp)) {
            Text(
                if (findings.isEmpty()) "Nothing to fix." else "${findings.size} thing${if (findings.size == 1) "" else "s"} worth a look.",
                style = MaterialTheme.typography.titleMedium,
                color = c.ink,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Checked on this device against ${records.size} record${if (records.size == 1) "" else "s"}. " +
                    "Nothing was sent anywhere.",
                fontSize = 12.sp,
                color = c.ink(0.45f),
            )
        }
        Spacer(Modifier.height(16.dp))

        if (findings.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No reused, weak or expiring credentials.", color = c.ink(0.45f))
            }
            return@Column
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth().navigationBarsPadding(),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(findings, key = { "${it.kind}:${it.recordUuid}:${it.fieldKey}" }) { finding ->
                FindingRow(app, finding) { onOpen(finding.recordUuid) }
            }
            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}
// #endregion

// #region Row
@Composable
private fun FindingRow(app: ZerokoshApp, finding: HealthFinding, onClick: () -> Unit) {
    val c = VaultTheme.colors
    val context = androidx.compose.ui.platform.LocalContext.current
    val templateId = remember(finding.recordUuid) {
        app.repository.body.value?.records?.firstOrNull { it.uuid == finding.recordUuid }?.template_id.orEmpty()
    }

    val (headline, tint) = when (finding.kind) {
        HealthKind.REUSED -> "Reused password" to MaterialTheme.colorScheme.error
        HealthKind.COMMON -> "Commonly guessed" to MaterialTheme.colorScheme.error
        HealthKind.WEAK -> "Weak" to c.primary
        HealthKind.EXPIRING -> "Expiring" to c.primary
    }

    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(c.surface)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(Modifier.size(8.dp).clip(CircleShape).background(tint))
        Column(Modifier.fillMaxWidth()) {
            Text(
                finding.recordTitle,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = c.ink,
            )
            Text(
                buildString {
                    append(headline)
                    if (finding.fieldKey.isNotEmpty() && templateId.isNotEmpty()) {
                        append(" · ").append(fieldLabel(context, templateId, finding.fieldKey))
                    }
                    if (finding.detail.isNotEmpty()) append(" · ").append(finding.detail)
                },
                fontSize = 11.5.sp,
                color = if (tint == Color.Unspecified) c.ink(0.5f) else tint.copy(alpha = 0.9f),
            )
        }
    }
}
// #endregion
