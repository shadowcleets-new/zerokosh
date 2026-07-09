package org.bharatvault.core

// #region Imports
import org.bharatvault.core.model.Attachment
import org.bharatvault.core.model.Record
import org.bharatvault.core.model.VaultBody
import org.bharatvault.core.model.VaultMeta
// #endregion

/**
 * The canonical §11.2 fixture content: SAME 3 sample records + 1 attachment on
 * every platform (passphrase "test-vault-1234"). The Swift and C# cores must
 * reproduce these records exactly for the cross-platform matrix.
 */
object FixtureVault {

    const val PASSPHRASE = "test-vault-1234"
    const val DEVICE_ID = "00000000-0000-4000-8000-000000000000"
    const val TS = 1_751_875_200_000L

    val body = VaultBody(
        format = 1,
        records = listOf(
            Record(
                uuid = "11111111-1111-4111-8111-111111111111",
                template_id = "bank_account",
                title = "SBI Salary Account",
                institution = "State Bank of India",
                fields = mapOf(
                    "bank_name" to "State Bank of India",
                    "account_number" to "12345678901",
                    "ifsc" to "SBIN0001234",
                    "netbanking_user_id" to "rahul123",
                    "login_password" to "S@mplePass1",
                    "registered_mobile" to "+919876543210",
                ),
                tags = listOf("work"),
                created_at = TS, modified_at = TS, rev = 1, device_id = DEVICE_ID,
            ),
            Record(
                uuid = "22222222-2222-4222-8222-222222222222",
                template_id = "login",
                title = "Zomato",
                institution = "Zomato",
                fields = mapOf(
                    "website" to "https://www.zomato.com",
                    "username" to "rahul@example.com",
                    "password" to "Zomato@123",
                ),
                created_at = TS, modified_at = TS, rev = 1, device_id = DEVICE_ID,
            ),
            Record(
                uuid = "33333333-3333-4333-8333-333333333333",
                template_id = "upi",
                title = "Personal UPI",
                institution = "State Bank of India",
                fields = mapOf(
                    "upi_id" to "rahul@oksbi",
                    "linked_account" to "11111111-1111-4111-8111-111111111111",
                    "upi_pin" to "1234",
                ),
                created_at = TS, modified_at = TS, rev = 1, device_id = DEVICE_ID,
            ),
        ),
        attachments = mapOf(
            "44444444-4444-4444-8444-444444444444" to Attachment(
                name = "note.txt",
                mime = "text/plain",
                data = "QmhhcmF0VmF1bHQgZml4dHVyZSBhdHRhY2htZW50", // "BharatVault fixture attachment"
            ),
        ),
        meta = VaultMeta(device_names = mapOf(DEVICE_ID to "Fixture Device")),
    )
}
