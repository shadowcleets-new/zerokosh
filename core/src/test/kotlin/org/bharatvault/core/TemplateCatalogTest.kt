package org.bharatvault.core

// #region Imports
import org.bharatvault.core.model.FieldType
import org.bharatvault.core.model.TemplateCatalog
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
// #endregion

/** Guards R0.2 item 5: templates.json is verbatim; field IDs are never renamed. */
class TemplateCatalogTest {

    private val catalog = TemplateCatalog.parse(File("../spec/templates.json").canonicalFile.readText())

    @Test
    fun `the 12 spec templates are all present, in spec order`() {
        val spec = listOf(
            "bank_account", "card", "upi", "demat", "insurance", "gov_id",
            "epf_pension", "utility", "telecom", "app_profile", "login", "secure_note",
        )
        val ids = catalog.templates.map { it.id }
        // R0.2 item 5 forbids renaming or removing a template — that would orphan
        // data in existing vaults. Adding new ones (pan_card, passport, transit …)
        // is safe and the app ships several, so this asserts the original twelve
        // still exist in their spec order rather than that the catalogue never grows.
        assertTrue(ids.containsAll(spec), "removed or renamed: ${spec - ids.toSet()}")
        assertEquals(spec, ids.filter { it in spec })
        assertEquals(1, catalog.format)
    }

    @Test
    fun `every field type and sensitivity parses`() {
        for (t in catalog.templates) {
            for (f in t.fields) {
                f.type // throws on unknown
                f.sensitivity
                if (f.type == FieldType.PICKER || f.type == FieldType.LINK) {
                    assertTrue(!f.typeParam.isNullOrEmpty(), "${t.id}.${f.k} missing type param")
                }
            }
        }
    }

    @Test
    fun `bank_account carries the locked 16 fields`() {
        val bank = catalog.byId("bank_account")!!
        assertEquals(16, bank.fields.size)
        assertEquals(
            listOf(
                "bank_name", "account_number", "account_type", "ifsc", "micr", "branch",
                "customer_id", "netbanking_user_id", "login_password", "transaction_password",
                "profile_password", "tpin", "registered_mobile", "registered_email", "nominee", "notes",
            ),
            bank.fields.map { it.k },
        )
    }

    @Test
    fun `link fields reference existing templates`() {
        for (t in catalog.templates) {
            t.fields.filter { it.type == FieldType.LINK }.forEach { f ->
                assertTrue(catalog.byId(f.typeParam!!) != null, "${t.id}.${f.k} links to unknown template ${f.typeParam}")
            }
        }
    }
}
