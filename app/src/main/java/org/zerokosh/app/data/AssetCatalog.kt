package org.zerokosh.app.data

// #region Imports
import android.content.Context
import kotlinx.serialization.Serializable
import org.zerokosh.core.generator.BankRules
import org.zerokosh.core.model.TemplateCatalog
import org.zerokosh.core.model.VaultJson
import org.zerokosh.core.card.CardIins
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

    /**
     * IIN table for card prefill. Lazy on purpose: 12k rows are only needed the
     * first time a card is tapped or typed, and parsing them at construction
     * would tax every cold start for a feature most launches never touch.
     * A missing or damaged asset costs the prefill, not the app.
     */
    val cardIins: CardIins by lazy {
        runCatching {
            CardIins.parse(context.assets.open("card_iins.txt").readBytes().decodeToString())
        }.getOrDefault(CardIins.EMPTY)
    }

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
