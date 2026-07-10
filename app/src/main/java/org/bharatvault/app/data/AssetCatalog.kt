package org.bharatvault.app.data

// #region Imports
import android.content.Context
import kotlinx.serialization.Serializable
import org.bharatvault.core.generator.BankRules
import org.bharatvault.core.model.TemplateCatalog
import org.bharatvault.core.model.VaultJson
// #endregion

// #region pickers.json / app_map.json schemas
@Serializable
data class Pickers(val format: Int = 1, val pickers: Map<String, List<String>> = emptyMap())

@Serializable
data class AppMap(val format: Int = 1, val apps: Map<String, String> = emptyMap())
// #endregion

/** Bundled spec resources (§6.3): templates.json (verbatim), pickers.json, bank_rules.json. */
class AssetCatalog(context: Context) {

    val templates: TemplateCatalog =
        TemplateCatalog.parse(context.assets.open("templates.json").readBytes().decodeToString())

    val pickers: Map<String, List<String>> =
        VaultJson.decodeFromString<Pickers>(context.assets.open("pickers.json").readBytes().decodeToString()).pickers

    val bankRules: BankRules =
        VaultJson.decodeFromString(context.assets.open("bank_rules.json").readBytes().decodeToString())

    /** §6.7: community packageName → app-name hints for autofill matching. Optional asset. */
    val appMap: Map<String, String> = runCatching {
        VaultJson.decodeFromString<AppMap>(context.assets.open("app_map.json").readBytes().decodeToString()).apps
    }.getOrDefault(emptyMap())

    /** §2.4: app_profile quick-add presets. */
    val appPresets: List<String> = listOf(
        "Zepto", "Blinkit", "Swiggy", "Zomato", "Amazon",
        "Flipkart", "Myntra", "BigBasket", "JioMart", "Instamart",
    )
}
