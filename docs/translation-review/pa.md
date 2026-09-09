# Punjabi (`pa`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-pa/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Punjabi | ok? |
|---|---|---|---|
| `au_close` | Close | ਬੰਦ ਕਰੋ |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | ਚੁਣੀ ਤਸਵੀਰ ਵਿੱਚ ਸਹੀ TOTP QR ਕੋਡ ਨਹੀਂ ਮਿਲਿਆ |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | ਖਾਲੀ ਤੋਂ ਸ਼ੁਰੂ ਕਰਕੇ ਆਪਣੇ ਖਾਨਿਆਂ ਦੇ ਨਾਂ ਆਪ ਰੱਖੋ — ਟੈਂਪਲੇਟ ਸਿਰਫ਼ ਲੇਬਲ ਭਰਦੇ ਹਨ, ਡਾਟਾ ਕਦੇ ਨਹੀਂ। |  |
| `hm_close_search` | Close search | ਖੋਜ ਬੰਦ ਕਰੋ |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | ਇਸ ਫ਼ੋਨ ਤੋਂ ਕਦੇ ਬਾਹਰ ਨਹੀਂ ਜਾਂਦਾ। ਸਾਂਭਣ ਵੇਲੇ ਏਨਕ੍ਰਿਪਟ ਕੀਤਾ ਜਾਂਦਾ ਹੈ। |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | ਹੁਣ ਉਹ ਫ਼ਾਈਲ ਹਟਾ ਦਿਓ ਜੋ ਤੁਸੀਂ ਦਰਾਮਦ ਕੀਤੀ ਸੀ। ਉਹ ਤੁਹਾਡੇ ਪਾਸਵਰਡਾਂ ਦੀ ਖੁੱਲ੍ਹੀ ਸੂਚੀ ਹੈ, ਤੇ ਹਾਲੇ ਵੀ ਤੁਹਾਡੇ Downloads ਵਿੱਚ ਪਈ ਹੈ। |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | ਸਭ ਕੁਝ ਸਿਰਫ਼ ਇਸੇ ਫ਼ੋਨ ਉੱਤੇ ਖੋਲ੍ਹਿਆ ਜਾਂਦਾ ਹੈ। ਕੁਝ ਵੀ ਅੱਪਲੋਡ ਨਹੀਂ ਹੁੰਦਾ, ਕਿਉਂਕਿ ਇਹ ਐਪ ਨੈੱਟਵਰਕ ਕੁਨੈਕਸ਼ਨ ਖੋਲ੍ਹ ਹੀ ਨਹੀਂ ਸਕਦੀ। |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | ਬੈਂਕ ਕਦੇ ਤੁਹਾਡਾ OTP ਨਹੀਂ ਮੰਗਦੇ। ਜੋ ਮੰਗੇ, ਉਹ ਠੱਗ ਹੈ। |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | ਕੋਈ ਵੀ ਬੈਂਕ ਅਫ਼ਸਰ ਤੁਹਾਨੂੰ ਸਕਰੀਨ ਸਾਂਝੀ ਕਰਨ ਵਾਲੀ ਐਪ ਲਾਉਣ ਲਈ ਨਹੀਂ ਕਹੇਗਾ। |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | ਤੁਹਾਡਾ UPI ਪਿੰਨ ਸਿਰਫ਼ UPI ਐਪ ਦੇ ਕੀਪੈਡ ਲਈ ਹੈ — ਫ਼ੋਨ \'ਤੇ ਕਿਸੇ ਨੂੰ ਨਾ ਦੱਸੋ। |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | KYC ਇੱਕ ਦਿਨ ਵਿੱਚ ਖ਼ਤਮ ਨਹੀਂ ਹੁੰਦੀ। “ਅੱਜ KYC ਖ਼ਤਮ” ਵਾਲੇ ਸੁਨੇਹੇ ਠੱਗੀ ਹਨ। |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | ਪੈਸੇ ਲੈਣ ਲਈ ਨਾ ਪਿੰਨ ਪਾਉਣਾ ਪੈਂਦਾ ਹੈ, ਨਾ QR ਸਕੈਨ ਕਰਨਾ। |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | ਬਿਜਲੀ ਕੱਟਣ ਦਾ SMS ਤੇ ਵਿੱਚ ਕਿਸੇ ਦਾ ਨਿੱਜੀ ਨੰਬਰ? ਉਹ ਠੱਗੀ ਹੈ। |  |
| `nav_close_menu` | Close menu | ਮੀਨੂ ਬੰਦ ਕਰੋ |  |
| `nfc_cannot_read` | Cannot read cards | ਕਾਰਡ ਪੜ੍ਹਿਆ ਨਹੀਂ ਜਾ ਸਕਦਾ |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | ਇਸ ਫ਼ੋਨ ਵਿੱਚ NFC ਨਹੀਂ ਹੈ, ਇਸ ਲਈ ਕਾਰਡ ਪੜ੍ਹਿਆ ਨਹੀਂ ਜਾ ਸਕਦਾ। |  |
| `ob_fact_lost_title` | If you lose your keys | ਜੇ ਕੁੰਜੀਆਂ ਗੁਆਚ ਜਾਣ |  |
| `ob_fact_network_note` | The app literally cannot phone home | ਇਹ ਐਪ ਕਿਤੇ ਵੀ ਸੰਪਰਕ ਨਹੀਂ ਕਰ ਸਕਦੀ |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | ਬਾਇਓਮੈਟ੍ਰਿਕ ਸੁਰੱਖਿਆ ਚਿੱਪ ਤੋਂ ਬਾਹਰ ਕਦੇ ਨਹੀਂ ਜਾਂਦਾ |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | ਤੁਸੀਂ ਕੁੰਜੀ ਉਸੇ ਫ਼ੋਲਡਰ ਵਿੱਚ ਸਾਂਭੀ ਹੈ ਜੋ ਤੁਹਾਡੀ ਏਨਕ੍ਰਿਪਟ ਕੀਤੀ ਤਿਜੌਰੀ ਸਿੰਕ ਕਰਦਾ ਹੈ। ਹੁਣ ਜਿਸ ਨੂੰ ਉਹ ਫ਼ੋਲਡਰ ਮਿਲੇਗਾ, ਉਹਨੂੰ ਦੋਵੇਂ ਹਿੱਸੇ ਮਿਲ ਜਾਣਗੇ। ਕੁੰਜੀ ਕਿਤੇ ਹੋਰ ਰੱਖੋ — ਕਾਗ਼ਜ਼, ਕੋਈ ਹੋਰ ਖਾਤਾ, ਜਾਂ ਦਰਾਜ਼। |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | ਇਹ ਤੁਹਾਡੀ ਤਿਜੌਰੀ ਫ਼ਾਈਲ ਦੇ ਨਾਲ ਹੀ ਹੈ |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH ਰਿਕਵਰੀ |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | ਸਕੈਨ ਕਰੋ ਜਾਂ ਲਿਖੋ। ਦੁਬਾਰਾ ਇੰਸਟਾਲ, ਫ਼ੈਕਟਰੀ ਰੀਸੈੱਟ, ਜਾਂ ਫ਼ੋਨ ਗੁਆਚਣ ਤੋਂ ਬਾਅਦ ਵੀ ਚੱਲਦੀ ਹੈ। |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | ਹੁਣੇ ਸਾਂਭੀ ਕਿੱਟ ਵਿੱਚੋਂ ਸਮੂਹ %1$d ਤੇ ਸਮੂਹ %2$d ਲਿਖੋ। |  |
| `ob_kit_challenge_hint` | Group %1$d | ਸਮੂਹ %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | ਪਹਿਲਾਂ ਕਿੱਟ ਸਾਂਭੋ, ਫੇਰ ਸਮੂਹ %1$d ਤੇ %2$d ਵਾਪਸ ਲਿਖੋ। |  |
| `ob_kit_challenge_title` | Check you actually have it | ਵੇਖੋ ਕਿ ਕਿੱਟ ਸੱਚਮੁੱਚ ਤੁਹਾਡੇ ਕੋਲ ਹੈ |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | ਇਹ ਉੱਪਰਲੀ ਕੁੰਜੀ ਨਾਲ ਮੇਲ ਨਹੀਂ ਖਾਂਦਾ। |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | ਕੋਈ ਵੀ — Zerokosh ਵੀ — ਇਹ ਮੇਰੇ ਲਈ ਵਾਪਸ ਨਹੀਂ ਲਿਆ ਸਕਦਾ। |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "ਮੈਂ ਇਹ ਆਫ਼ਲਾਈਨ ਰੱਖ ਲਈ ਹੈ। " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | ਇੱਕੋ ਵਾਰ ਦਿਸਦੀ ਹੈ, ਕਦੇ ਖੁੱਲ੍ਹੇ ਰੂਪ ਵਿੱਚ ਨਹੀਂ ਸਾਂਭੀ ਜਾਂਦੀ। ਅੰਦਰ ਆ ਸਕਦੇ ਹੋ ਤਾਂ ਸੈਟਿੰਗਾਂ ਵਿੱਚੋਂ ਨਵੀਂ ਬਣਾ ਲਓ। |  |
| `ob_kit_head_emph` | On paper. | ਕਾਗ਼ਜ਼ ਉੱਤੇ। |  |
| `ob_kit_head_lead` | "One key. " | "ਇੱਕ ਕੁੰਜੀ। " |  |
| `ob_kit_head_tail` | " Never online." | " ਕਦੇ ਆਨਲਾਈਨ ਨਹੀਂ।" |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | ਨਾ Gmail, ਨਾ WhatsApp, ਨਾ ਸਕਰੀਨਸ਼ਾਟ। ਅਲਮਾਰੀ, ਬੈਂਕ ਲਾਕਰ, ਜਾਂ ਸਟੀਲ ਦੀ ਪਲੇਟ। |  |
| `ob_kit_offline_title` | Keep it off the internet | ਇਹਨੂੰ ਇੰਟਰਨੈੱਟ ਤੋਂ ਦੂਰ ਰੱਖੋ |  |
| `ob_kit_print` | Print | ਛਾਪੋ |  |
| `ob_kit_print_note` | A printer, or Save as PDF | ਪ੍ਰਿੰਟਰ, ਜਾਂ PDF ਵਜੋਂ ਸਾਂਭੋ |  |
| `ob_kit_qr` | QR image | QR ਤਸਵੀਰ |  |
| `ob_kit_qr_cd` | Recovery key QR code | ਰਿਕਵਰੀ ਕੁੰਜੀ ਦਾ QR ਕੋਡ |  |
| `ob_kit_qr_note` | To an offline gallery | ਆਫ਼ਲਾਈਨ ਗੈਲਰੀ ਵਿੱਚ |  |
| `ob_kit_regenerate` | Regenerate | ਨਵੀਂ ਬਣਾਓ |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | ਇਹ ਸਾਂਭਿਆ ਨਹੀਂ ਗਿਆ। ਦੁਬਾਰਾ ਕੋਸ਼ਿਸ਼ ਕਰੋ, ਜਾਂ ਕੋਈ ਹੋਰ ਥਾਂ ਚੁਣੋ। |  |
| `ob_kit_save_pdf` | Save PDF | PDF ਸਾਂਭੋ |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | ਇੱਕ ਸਫ਼ੇ ਦੀ ਛਪਣਯੋਗ ਕਿੱਟ |  |
| `ob_kit_saved` | I\'ve saved my kit | ਮੈਂ ਆਪਣੀ ਕਿੱਟ ਸਾਂਭ ਲਈ ਹੈ |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s ਵਿੱਚ ਸਾਂਭਿਆ |  |
| `ob_kit_sent_to_printer` | Sent to the printer | ਪ੍ਰਿੰਟਰ ਨੂੰ ਭੇਜ ਦਿੱਤਾ |  |
| `ob_kit_skip` | I\'ll do this later | ਇਹ ਬਾਅਦ ਵਿੱਚ ਕਰਾਂਗਾ |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | ਤੁਹਾਡੀ ਤਿਜੌਰੀ ਚੱਲਦੀ ਰਹੇਗੀ। ਜਦ ਤੱਕ ਕਿੱਟ ਨਹੀਂ ਸਾਂਭੀ ਜਾਂਦੀ, Zerokosh ਯਾਦ ਕਰਾਉਂਦਾ ਰਹੇਗਾ। |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | ਇਸੇ ਫ਼ੋਨ ਉੱਤੇ ਬਣੀ, ਸਿਰਫ਼ ਇੱਕ ਵਾਰ ਦਿਸੇਗੀ। ਪਾਸਫ਼੍ਰੇਜ਼ ਭੁੱਲਣ \'ਤੇ ਅੰਦਰ ਵਾਪਸ ਆਉਣ ਦਾ ਇਹੀ ਇੱਕੋ ਰਾਹ ਹੈ। |  |
| `ob_kit_working` | Working… | ਕੰਮ ਚੱਲ ਰਿਹਾ ਹੈ… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | ਤਿਜੌਰੀ ਦੇ ਲੇਬਲ, ਟੈਂਪਲੇਟ ਤੇ ਚੇਤਾਵਨੀਆਂ ਤੁਰੰਤ ਬਦਲ ਜਾਣਗੀਆਂ। ਸੈਟਿੰਗਾਂ ਵਿੱਚ ਕਦੇ ਵੀ ਬਦਲ ਸਕਦੇ ਹੋ। |  |
| `ob_pass_confirm` | Confirm | ਦੁਬਾਰਾ ਲਿਖੋ |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | ਇਹ ਅਸੀਂ ਕਦੇ ਨਹੀਂ ਵੇਖਦੇ। ਕੋਈ ਰੀਸੈੱਟ ਲਿੰਕ ਨਹੀਂ। |  |
| `ob_pass_head_emph` | held only | ਸਿਰਫ਼ ਤੁਹਾਡੇ ਕੋਲ |  |
| `ob_pass_head_lead` | "One secret, " | "ਇੱਕੋ ਭੇਤ, " |  |
| `ob_pass_head_tail` | " by you." | । |  |
| `ob_pass_no_match` | no match | ਮੇਲ ਨਹੀਂ ਖਾਂਦਾ |  |
| `ob_pass_seal` | Seal the vault | ਤਿਜੌਰੀ ਬੰਦ ਕਰੋ |  |
| `ob_pass_sealing` | Sealing… | ਬੰਦ ਕਰ ਰਹੇ ਹਾਂ… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | ਬੇਮੇਲ ਤਿੰਨ-ਚਾਰ ਸ਼ਬਦ, ਇੱਕ ਚਲਾਕ ਸ਼ਬਦ ਨਾਲੋਂ ਵਧੀਆ ਹਨ। ਇਸ ਸਕਰੀਨ ਤੋਂ ਕੁਝ ਵੀ ਬਾਹਰ ਨਹੀਂ ਜਾਂਦਾ। |  |
| `ob_pass_tab_passphrase` | Passphrase | ਪਾਸਫ਼੍ਰੇਜ਼ |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 ਅੰਕਾਂ ਦਾ ਪਿੰਨ |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | ਤੁਹਾਡੀ ਉਂਗਲ ਦਾ ਨਿਸ਼ਾਨ ਫ਼ੋਨ ਦੀ ਸੁਰੱਖਿਆ ਚਿੱਪ ਦੇ ਅੰਦਰ ਰਹਿੰਦਾ ਹੈ। ਉਹ ਇਸ ਫ਼ੋਨ ਤੋਂ ਕਦੇ ਬਾਹਰ ਨਹੀਂ ਜਾਂਦਾ। |  |
| `ob_trust_continue` | I understand · Continue | ਸਮਝ ਗਿਆ · ਅੱਗੇ ਚੱਲੋ |  |
| `ob_trust_head_emph` | don\'t | ਨਹੀਂ ਪਤਾ |  |
| `ob_trust_head_lead` | "Exactly what we " | "ਸਾਨੂੰ ਅਸਲ ਵਿੱਚ ਕੀ " |  |
| `ob_trust_head_tail` | " know." | । |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | ਜੇ ਪਾਸਫ਼੍ਰੇਜ਼ ਭੁੱਲ ਗਏ ਤੇ ਰਿਕਵਰੀ ਕਿੱਟ ਵੀ ਗੁਆਚ ਗਈ, ਤਾਂ ਤਿਜੌਰੀ ਬੰਦ ਹੀ ਰਹੇਗੀ — ਤੁਹਾਡੇ ਲਈ, ਸਾਡੇ ਲਈ, ਸਭ ਲਈ। |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "ਕੁੰਜੀਆਂ ਤੁਹਾਡੇ ਕੋਲ। " |  |
| `ob_trust_stat_files` | .kosh file on device | ਫ਼ੋਨ ਉੱਤੇ .kosh ਫ਼ਾਈਲ |  |
| `ob_trust_stat_servers` | servers contacted | ਸਰਵਰ ਨਾਲ ਸੰਪਰਕ |  |
| `ob_trust_stat_trackers` | trackers or SDKs | ਟਰੈਕਰ ਜਾਂ SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | ਇਹ ਇੱਕ ਵਾਰ ਪੜ੍ਹ ਲਓ। ਪੂਰੀ ਸੁਰੱਖਿਆ ਦਾ ਢਾਂਚਾ ਇਹੀ ਹੈ, ਸਿੱਧੇ ਸ਼ਬਦਾਂ ਵਿੱਚ। |  |
| `ob_trust_tag_audited` | Audited build | ਆਡਿਟ ਕੀਤੀ ਬਿਲਡ |  |
| `ob_trust_tag_reproducible` | Reproducible APK | ਦੁਬਾਰਾ ਬਣਾਈ ਜਾ ਸਕਣ ਵਾਲੀ APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | ਤੁਸੀਂ ਉਂਗਲ ਨਾਲ ਖੋਲ੍ਹ ਰਹੇ ਹੋ। ਜੇ ਕਦੇ ਉਂਗਲ ਕੰਮ ਕਰਨਾ ਬੰਦ ਕਰ ਦੇਵੇ, ਤਾਂ ਇਹੀ ਤੁਹਾਨੂੰ ਅੰਦਰ ਲਿਆਵੇਗਾ — ਇਸ ਲਈ ਵੇਖ ਲੈਣਾ ਚੰਗਾ ਹੈ। |  |
| `pc_confirm` | Check | ਜਾਂਚੋ |  |
| `pc_correct` | Still correct. Nothing to do. | ਹੁਣ ਵੀ ਸਹੀ ਹੈ। ਕੁਝ ਕਰਨ ਦੀ ਲੋੜ ਨਹੀਂ। |  |
| `pc_forgot` | I cannot remember it | ਮੈਨੂੰ ਯਾਦ ਨਹੀਂ ਆ ਰਿਹਾ |  |
| `pc_later` | Not now | ਹੁਣੇ ਨਹੀਂ |  |
| `pc_reset_action` | Set new passphrase | ਨਵਾਂ ਪਾਸਫ਼੍ਰੇਜ਼ ਰੱਖੋ |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | ਤੁਹਾਡੀ ਉਂਗਲ ਇਹ ਤਿਜੌਰੀ ਖੋਲ੍ਹ ਸਕਦੀ ਹੈ, ਇਸ ਲਈ ਉਹੀ ਨਵਾਂ ਪਾਸਫ਼੍ਰੇਜ਼ ਵੀ ਰੱਖ ਸਕਦੀ ਹੈ — ਰਿਕਵਰੀ ਕਿੱਟ ਦੀ ਲੋੜ ਨਹੀਂ। ਪੱਕਾ ਕਰਨ ਲਈ ਇੱਕ ਵਾਰ ਹੋਰ ਪੁੱਛਿਆ ਜਾਵੇਗਾ। |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | ਪਾਸਫ਼੍ਰੇਜ਼ ਬਦਲ ਗਿਆ। ਛੇਤੀ ਅਨਲੌਕ ਦੁਬਾਰਾ ਲੱਗ ਗਿਆ। |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | ਇਹ ਨਹੀਂ ਹੋਇਆ। ਤੁਹਾਡਾ ਪੁਰਾਣਾ ਪਾਸਫ਼੍ਰੇਜ਼ ਹੀ ਚੱਲ ਰਿਹਾ ਹੈ। |  |
| `pc_reset_title` | Set a new passphrase | ਨਵਾਂ ਪਾਸਫ਼੍ਰੇਜ਼ ਰੱਖੋ |  |
| `pc_title` | Do you still remember your passphrase? | ਕੀ ਤੁਹਾਨੂੰ ਹੁਣ ਵੀ ਆਪਣਾ ਪਾਸਫ਼੍ਰੇਜ਼ ਯਾਦ ਹੈ? |  |
| `pc_wrong` | That is not it. You can set a new one instead. | ਇਹ ਉਹ ਨਹੀਂ। ਇਸ ਦੀ ਥਾਂ ਨਵਾਂ ਰੱਖ ਸਕਦੇ ਹੋ। |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | ਇਸ ਇੰਦਰਾਜ ਲਈ ਯਾਦ ਰੱਖੀਆਂ %1$d ਕੀਮਤਾਂ ਹਟਾ ਦਿੱਤੀਆਂ ਜਾਣਗੀਆਂ। ਇਹ ਵਾਪਸ ਨਹੀਂ ਹੋ ਸਕਦਾ। |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh ਉਹ ਲੌਗਿਨ ਸਾਂਭ ਨਹੀਂ ਸਕਿਆ। |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | ਭਰਨ ਲਈ Zerokosh ਖੋਲ੍ਹੋ |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | ਇਹੀ ਇੱਕ ਪਾਸਫ਼੍ਰੇਜ਼ ਸਭ ਕੁਝ ਬੰਦ ਰੱਖਦਾ ਹੈ। ਕੋਈ ਲੰਮਾ ਜਿਹਾ ਚੁਣੋ ਜੋ ਸਿਰਫ਼ ਤੁਸੀਂ ਜਾਣਦੇ ਹੋ। |  |
| `scr_create_button` | Lock it in | ਬੰਦ ਕਰ ਦਿਓ |  |
| `scr_create_confirm_hint` | Type it again | ਦੁਬਾਰਾ ਲਿਖੋ |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | ਪਾਸਫ਼੍ਰੇਜ਼ (ਘੱਟੋ-ਘੱਟ 10 ਅੱਖਰ) |  |
| `scr_create_mismatch` | The two entries don\'t match | ਦੋਵੇਂ ਇੱਕੋ ਜਿਹੇ ਨਹੀਂ ਹਨ |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 ਅੰਕਾਂ ਦਾ ਪਿੰਨ |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | ਇਸ ਦੀ ਥਾਂ 6 ਅੰਕਾਂ ਦਾ ਪਿੰਨ ਰੱਖੋ |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | ਪਿੰਨ ਲਈ ਉਂਗਲ ਜਾਂ ਚਿਹਰਾ ਅਨਲੌਕ ਵਾਲਾ ਫ਼ੋਨ ਚਾਹੀਦਾ ਹੈ। ਕਿਰਪਾ ਕਰਕੇ ਪਾਸਫ਼੍ਰੇਜ਼ ਚੁਣੋ। |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | ਪਿੰਨ ਦੀ ਇਜਾਜ਼ਤ ਇਸ ਲਈ ਹੈ ਕਿਉਂਕਿ ਇਹ ਫ਼ੋਨ ਉਹਨੂੰ ਆਪਣੀ ਸੁਰੱਖਿਆ ਚਿੱਪ ਅਤੇ ਤੁਹਾਡੀ ਉਂਗਲ ਜਾਂ ਚਿਹਰੇ ਨਾਲ ਬਚਾਉਂਦਾ ਹੈ। |  |
| `scr_create_strength_fair` | Fair | ਠੀਕ-ਠਾਕ |  |
| `scr_create_strength_good` | Good | ਵਧੀਆ |  |
| `scr_create_strength_strong` | Strong | ਮਜ਼ਬੂਤ |  |
| `scr_create_strength_weak` | Weak | ਕਮਜ਼ੋਰ |  |
| `scr_create_title` | Create your passphrase | ਆਪਣਾ ਪਾਸਫ਼੍ਰੇਜ਼ ਬਣਾਓ |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | ਘੱਟੋ-ਘੱਟ 10 ਅੱਖਰ ਚਾਹੀਦੇ ਹਨ — ਜਿੰਨਾ ਲੰਮਾ, ਓਨਾ ਮਜ਼ਬੂਤ |  |
| `scr_create_working` | Preparing your vault… | ਤੁਹਾਡੀ ਤਿਜੌਰੀ ਤਿਆਰ ਹੋ ਰਹੀ ਹੈ… |  |
| `scr_detail_delete` | Delete | ਹਟਾਓ |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | ਇਹ 30 ਦਿਨ “ਹਾਲ ਹੀ ਵਿੱਚ ਹਟਾਏ” ਵਿੱਚ ਰਹੇਗਾ, ਤੇ ਤੁਹਾਡੇ ਹੋਰ ਫ਼ੋਨਾਂ ਨਾਲ ਸਿੰਕ ਹੋਣ \'ਤੇ ਚਲਾ ਜਾਵੇਗਾ। |  |
| `scr_detail_delete_confirm_title` | Delete this record? | ਇਹ ਇੰਦਰਾਜ ਹਟਾ ਦੇਈਏ? |  |
| `scr_detail_delete_confirm_yes` | Delete | ਹਟਾਓ |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | ਸੀਕ੍ਰੇਟ ਜਾਂ otpauth:// ਲਿੰਕ ਚਿਪਕਾਓ |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | ਆਪਣੀ ਉਂਗਲ ਜਾਂ ਚਿਹਰਾ ਵਰਤੋ |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh ਖੋਲ੍ਹੋ |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | ਕਈ ਵਾਰ ਗ਼ਲਤ ਕੋਸ਼ਿਸ਼ ਹੋਈ। %1$d ਸਕਿੰਟ ਉਡੀਕੋ। |  |
| `scr_lock_hint` | Passphrase | ਪਾਸਫ਼੍ਰੇਜ਼ |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | ਇਹ ਰਿਕਵਰੀ ਕੁੰਜੀ ਸਹੀ ਨਹੀਂ — ਹਰ ਅੱਖਰ ਮਿਲਾ ਕੇ ਵੇਖੋ |  |
| `scr_lock_title` | Vault is locked | ਤਿਜੌਰੀ ਬੰਦ ਹੈ |  |
| `scr_lock_unlock` | Unlock | ਖੋਲ੍ਹੋ |  |
| `scr_lock_use_passphrase` | Use passphrase | ਪਾਸਫ਼੍ਰੇਜ਼ ਵਰਤੋ |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | ਕਿਰਪਾ ਕਰਕੇ ਇੱਕ ਵਾਰ ਪਾਸਫ਼੍ਰੇਜ਼ ਨਾਲ ਖੋਲ੍ਹੋ |  |
| `scr_lock_use_recovery` | Use Recovery Key | ਰਿਕਵਰੀ ਕੁੰਜੀ ਵਰਤੋ |  |
| `scr_lock_wrong` | Wrong passphrase | ਪਾਸਫ਼੍ਰੇਜ਼ ਗ਼ਲਤ ਹੈ |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | ਜੇ ਕਦੇ ਪਾਸਫ਼੍ਰੇਜ਼ ਭੁੱਲ ਗਏ, ਤਾਂ ਅੰਦਰ ਵਾਪਸ ਆਉਣ ਦਾ ਇਹੀ ਇੱਕੋ ਰਾਹ ਹੈ। ਅਸੀਂ ਉਹਨੂੰ ਰੀਸੈੱਟ ਨਹੀਂ ਕਰ ਸਕਦੇ — ਕੋਈ ਵੀ ਨਹੀਂ ਕਰ ਸਕਦਾ। |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | ਮੈਂ ਇਹ ਲਿਖ ਕੇ ਸੁਰੱਖਿਅਤ ਥਾਂ ਰੱਖ ਲਈ ਹੈ |  |
| `scr_recovery_done` | Continue | ਅੱਗੇ ਚੱਲੋ |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | ਇਹ ਕੁੰਜੀ ਇੱਕੋ ਵਾਰ ਦਿਸਦੀ ਹੈ। ਜਦ ਤੱਕ ਤੁਸੀਂ ਤਿਜੌਰੀ ਖੋਲ੍ਹ ਸਕਦੇ ਹੋ, ਸੈਟਿੰਗਾਂ ਵਿੱਚੋਂ ਕਦੇ ਵੀ ਨਵੀਂ ਬਣਾ ਸਕਦੇ ਹੋ। |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | ਇਹ ਕਾਗ਼ਜ਼ ਆਪਣੇ ਜ਼ਮੀਨ-ਜਾਇਦਾਦ ਦੇ ਕਾਗ਼ਜ਼ਾਂ ਜਾਂ ਹੋਰ ਜ਼ਰੂਰੀ ਦਸਤਾਵੇਜ਼ਾਂ ਨਾਲ ਰੱਖੋ। ਜਿਸ ਕੋਲ ਇਹ ਕੁੰਜੀ ਹੈ, ਉਹ ਤੁਹਾਡੀ ਤਿਜੌਰੀ ਖੋਲ੍ਹ ਸਕਦਾ ਹੈ — ਇਹਨੂੰ ਲਾਕਰ ਦੀ ਚਾਬੀ ਵਾਂਗ ਸੰਭਾਲੋ। |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | ਰਿਕਵਰੀ ਕਿੱਟ PDF ਸਾਂਭ ਲਈ |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh ਰਿਕਵਰੀ ਕਿੱਟ |  |
| `scr_recovery_save_pdf` | Save as PDF | PDF ਵਜੋਂ ਸਾਂਭੋ |  |
| `scr_recovery_title` | Your Recovery Key | ਤੁਹਾਡੀ ਰਿਕਵਰੀ ਕੁੰਜੀ |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | ਜੋ ਵੀ ਤੁਸੀਂ ਸਾਂਭਦੇ ਹੋ, ਉਹ ਤੁਹਾਡੇ ਫ਼ੋਨ ਦੀ ਇੱਕ ਬੰਦ ਫ਼ਾਈਲ ਵਿੱਚ ਰਹਿੰਦਾ ਹੈ। ਉਹ ਕਦੇ ਸਾਡੇ ਤੱਕ ਨਹੀਂ ਆਉਂਦਾ — ਸਾਡੇ ਕੋਲ ਉਹਨੂੰ ਰੱਖਣ ਦੀ ਥਾਂ ਹੀ ਨਹੀਂ। |  |
| `scr_trust_card1_title` | Your data stays on this device | ਤੁਹਾਡਾ ਡਾਟਾ ਇਸੇ ਫ਼ੋਨ ਵਿੱਚ ਰਹਿੰਦਾ ਹੈ |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | ਨਾ ਕੋਈ Zerokosh ਖਾਤਾ, ਨਾ ਕਲਾਉਡ, ਨਾ ਸਾਈਨ-ਅੱਪ। ਇਹਨੂੰ ਸਿਰਫ਼ ਤੁਸੀਂ ਖੋਲ੍ਹ ਸਕਦੇ ਹੋ। ਅਸੀਂ ਵੀ ਨਹੀਂ। |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | ਸਾਡੇ ਕੋਲ ਸਰਵਰ ਨਹੀਂ — ਨਾ ਹੈਕ ਕਰਨ ਲਈ ਕੁਝ, ਨਾ ਵੇਚਣ ਲਈ |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | ਨਾ ਕੋਈ ਸਬਸਕ੍ਰਿਪਸ਼ਨ, ਨਾ ਇਸ਼ਤਿਹਾਰ। ਕੋਈ ਵੀ ਸਾਡਾ ਕੋਡ ਪੜ੍ਹ ਕੇ ਸਾਡੀ ਹਰ ਗੱਲ ਪਰਖ ਸਕਦਾ ਹੈ। |  |
| `scr_trust_card3_title` | Free forever, open source | ਹਮੇਸ਼ਾ ਮੁਫ਼ਤ, ਓਪਨ ਸੋਰਸ |  |
| `scr_trust_continue` | Continue | ਅੱਗੇ ਚੱਲੋ |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | ਤੁਹਾਡੇ ਬਦਲਾਅ ਸਾਂਭੇ ਨਹੀਂ ਗਏ, ਇਸ ਲਈ ਤਿਜੌਰੀ ਵਿੱਚ ਜੋ ਪਹਿਲਾਂ ਸੀ, ਉਹ ਕੁਝ ਵੀ ਗੁਆਚਿਆ ਨਹੀਂ। |  |
| `st_recently_deleted` | Recently deleted | ਹਾਲ ਹੀ ਵਿੱਚ ਹਟਾਏ |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | ਇੱਕ ਵਾਰੀ ਦਾ ਕੋਡ (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | ਇੱਕ ਵਾਰੀ ਦਾ ਕੋਡ (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | ਇੱਕ ਵਾਰੀ ਦਾ ਕੋਡ (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA ਸੀਕ੍ਰੇਟ |  |
| `tr_cannot_undo` | This cannot be undone. | ਇਹ ਵਾਪਸ ਨਹੀਂ ਹੋ ਸਕਦਾ। |  |
| `tr_delete_all` | Delete all permanently | ਸਭ ਹਮੇਸ਼ਾ ਲਈ ਹਟਾਓ |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d ਇੰਦਰਾਜ ਹਮੇਸ਼ਾ ਲਈ ਚਲੇ ਜਾਣਗੇ। ਇਹ ਵਾਪਸ ਨਹੀਂ ਹੋ ਸਕਦਾ, ਤੇ ਵਾਪਸ ਲਿਆਉਣ ਲਈ ਕੋਈ ਬੈਕਅੱਪ ਨਹੀਂ। |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d ਇੰਦਰਾਜ ਹਮੇਸ਼ਾ ਲਈ ਚਲਾ ਜਾਵੇਗਾ। ਇਹ ਵਾਪਸ ਨਹੀਂ ਹੋ ਸਕਦਾ, ਤੇ ਵਾਪਸ ਲਿਆਉਣ ਲਈ ਕੋਈ ਬੈਕਅੱਪ ਨਹੀਂ। |  |
| `tr_delete_all_title` | Delete everything in the trash? | ਰੱਦੀ ਵਿੱਚੋਂ ਸਭ ਕੁਝ ਹਟਾ ਦੇਈਏ? |  |
| `tr_delete_now` | Delete now | ਹੁਣੇ ਹਟਾਓ |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | “%1$s” ਹਮੇਸ਼ਾ ਲਈ ਹਟਾ ਦੇਈਏ? |  |
| `tr_empty` | Nothing deleted. | ਕੁਝ ਵੀ ਹਟਾਇਆ ਨਹੀਂ ਗਿਆ। |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | ਹਟਾਏ ਇੰਦਰਾਜ ਇੱਥੇ %1$d ਦਿਨ ਰਹਿੰਦੇ ਹਨ। |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | ਭਾਰਤੀ ਬੈਂਕਾਂ, UPI, ਕਾਰਡਾਂ, ਡੀਮੈਟ, EPF, ਤੇ ਉਹਨਾਂ OTP ਐਪਾਂ ਲਈ ਜੋ ਤੁਸੀਂ ਸੱਚੀਓਂ ਵਰਤਦੇ ਹੋ — ਫ਼ੋਨ ਵਿੱਚ ਹੀ ਰਹਿਣ ਵਾਲੀ ਤਿਜੌਰੀ। |  |

## Priority 2 — longer prose

| key | English | Punjabi | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | ਉਸ ਇੱਕ ਚੀਜ਼ ਤੋਂ ਸ਼ੁਰੂ ਕਰੋ ਜੋ ਸਭ ਤੋਂ ਵੱਧ ਕੰਮ ਆਵੇ। ਨੋਟਸ ਐਪ ਵਿੱਚ ਪਏ ਬਾਰਾਂ ਪਾਸਵਰਡਾਂ ਨਾਲੋਂ ਇੱਕ ਸਾਂਭਿਆ ਪਾਸਵਰਡ ਵੱਧ ਸੁਰੱਖਿਅਤ ਹੈ। |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | ਪਾਸਫ਼੍ਰੇਜ਼ ਭੁੱਲ ਗਏ ਤਾਂ ਅੰਦਰ ਆਉਣ ਦਾ ਰਾਹ ਰਿਕਵਰੀ ਕਿੱਟ ਹੀ ਹੈ। ਤੁਹਾਡੇ ਲਈ ਹੋਰ ਕੋਈ ਨਹੀਂ ਬਣਾ ਸਕਦਾ। |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | ਉਸ ਫ਼ਾਈਲ ਵਿੱਚ ਪਛਾਣਨ ਯੋਗ ਕੁਝ ਨਹੀਂ ਮਿਲਿਆ। Chrome, Google Password Manager, Bitwarden, LastPass ਤੇ KeePass ਦੀਆਂ ਬਰਾਮਦਾਂ ਸਮਝ ਆਉਂਦੀਆਂ ਹਨ। |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d ਮੌਜੂਦਾ ਇੰਦਰਾਜ ਬਦਲਣਗੇ — ਸਾਈਟ ਤੇ ਯੂਜ਼ਰਨੇਮ ਮਿਲਾ ਕੇ। ਬਦਲੇ ਪਾਸਵਰਡ ਹਰ ਇੰਦਰਾਜ ਦੇ ਇਤਿਹਾਸ ਵਿੱਚ ਮਿਲ ਜਾਣਗੇ। |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d ਮੌਜੂਦਾ ਇੰਦਰਾਜ ਬਦਲੇਗਾ — ਸਾਈਟ ਤੇ ਯੂਜ਼ਰਨੇਮ ਮਿਲਾ ਕੇ। ਬਦਲੇ ਪਾਸਵਰਡ ਹਰ ਇੰਦਰਾਜ ਦੇ ਇਤਿਹਾਸ ਵਿੱਚ ਮਿਲ ਜਾਣਗੇ। |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d ਇੰਦਰਾਜ ਦੋਹਾਂ ਪਾਸੇ ਬਦਲੇ ਹੋਏ ਸਨ। ਦੋਵੇਂ ਰੂਪ ਸਾਂਭ ਲਏ — “(conflict copy)” ਲੱਭੋ। |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | ਉਹ ਪਾਸਫ਼੍ਰੇਜ਼ ਪਾਓ ਜੋ ਇਸ ਬੈਕਅੱਪ ਫ਼ਾਈਲ ਨੂੰ ਖੋਲ੍ਹਦਾ ਹੈ। ਉਹ ਤੁਹਾਡੇ ਮੌਜੂਦਾ ਨਾਲੋਂ ਵੱਖਰਾ ਹੋ ਸਕਦਾ ਹੈ। |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | .kosh ਫ਼ਾਈਲ ਦਾ Poly1305 ਤਸਦੀਕ ਟੈਗ ਮੇਲ ਨਹੀਂ ਖਾਂਦਾ। ਇਹ ਵਿਚਾਲੇ ਰੁਕੇ ਸਿੰਕ ਜਾਂ ਖ਼ਰਾਬ ਸਟੋਰੇਜ ਤੋਂ ਬਾਅਦ ਹੋ ਸਕਦਾ ਹੈ। |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh ਤਿਜੌਰੀ ਫ਼ਾਈਲ ਦੇ ਨਾਲ ਇੱਕ ਚੱਲਦਾ ਬੈਕਅੱਪ ਰੱਖਦਾ ਹੈ। ਉਹਨੂੰ ਆਪਣੇ ਸਿੰਕ ਫ਼ੋਲਡਰ ਤੋਂ ਵਾਪਸ ਲਿਆਓ, ਜਾਂ ਕਿਸੇ ਹੋਰ ਫ਼ੋਨ ਉੱਤੇ ਰਿਕਵਰੀ ਕਿੱਟ ਨਾਲ ਤਿਜੌਰੀ ਖੋਲ੍ਹੋ। |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | ਜਦ ਤੱਕ ਪੜ੍ਹਿਆ ਨਾ ਜਾਵੇ, ਕਾਰਡ ਫ਼ੋਨ ਦੀ ਪਿੱਠ ਨਾਲ ਸਿੱਧਾ ਲਾ ਕੇ ਰੱਖੋ। ਇਸ ਤੋਂ ਕਾਰਡ ਨੰਬਰ, ਮਿਆਦ ਤੇ ਨਾਂ ਮਿਲਦਾ ਹੈ — CVV ਚਿੱਪ ਵਿੱਚ ਨਹੀਂ ਹੁੰਦਾ, ਉਹ ਤੁਹਾਨੂੰ ਆਪ ਲਿਖਣਾ ਪਵੇਗਾ। |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | ਅੰਦਰ ਵਾਪਸ ਆਉਣ ਦਾ ਛੇਤੀ ਰਾਹ ਚੁਣੋ। ਤਿਜੌਰੀ ਦੀ ਰਾਖੀ ਪਾਸਫ਼੍ਰੇਜ਼ ਹੀ ਕਰਦਾ ਹੈ; ਇਹ ਸਿਰਫ਼ ਇਸੇ ਫ਼ੋਨ ਉੱਤੇ ਕੁੰਜੀ ਖੋਲ੍ਹਦਾ ਹੈ। |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | ਹੁਣ ਤੁਹਾਡੀ ਤਿਜੌਰੀ ਵਿੱਚ ਅਸਲ ਜਾਣਕਾਰੀ ਹੈ। ਰਿਕਵਰੀ ਕਿੱਟ ਬਿਨਾਂ ਪਾਸਫ਼੍ਰੇਜ਼ ਭੁੱਲਣ \'ਤੇ ਕੋਈ ਤੁਹਾਨੂੰ ਵਾਪਸ ਅੰਦਰ ਨਹੀਂ ਲਿਆ ਸਕਦਾ — ਅਸੀਂ ਵੀ ਨਹੀਂ। |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh ਮੁਫ਼ਤ ਤੇ ਓਪਨ ਸੋਰਸ ਹੈ, ਤੇ ਇਸ ਦੇ ਕੋਈ ਸਰਵਰ ਨਹੀਂ। ਤੁਹਾਡੀ ਤਿਜੌਰੀ ਸਿਰਫ਼ ਤੁਸੀਂ ਖੋਲ੍ਹ ਸਕਦੇ ਹੋ। ਅਸੀਂ ਵੀ ਨਹੀਂ। |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | ਕੈਮਰੇ ਦੀ ਇਜਾਜ਼ਤ ਸਿਰਫ਼ QR ਕੋਡ ਸਕੈਨ ਕਰਨ ਲਈ ਚਾਹੀਦੀ ਹੈ। ਇੰਦਰਾਜ ਜੋੜਦੇ ਵੇਲੇ ਸੀਕ੍ਰੇਟ ਹੱਥੀਂ ਵੀ ਚਿਪਕਾ ਸਕਦੇ ਹੋ। |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | ਉਹਨਾਂ ਬੈਂਕ ਐਪਾਂ ਲਈ ਜੋ ਆਟੋਫ਼ਿਲ ਨਹੀਂ ਹੋਣ ਦਿੰਦੀਆਂ — ਬਟਨ ਦੱਬ ਕੇ ਲੌਗਿਨ ਵੇਰਵੇ ਇੱਕ-ਇੱਕ ਕਰਕੇ ਕਾਪੀ ਕਰੋ |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | ਜਿਵੇਂ ਤੁਸੀਂ ਫ਼ੋਨ ਖੋਲ੍ਹਦੇ ਹੋ, ਓਵੇਂ ਹੀ ਤਿਜੌਰੀ। ਪਾਸਫ਼੍ਰੇਜ਼ ਹਮੇਸ਼ਾ ਚੱਲਦਾ ਰਹੇਗਾ। |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | ਤਿਜੌਰੀ ਦੇ ਸਕਰੀਨਸ਼ਾਟ ਕਲਾਉਡ ਫ਼ੋਟੋ ਬੈਕਅੱਪ ਵਿੱਚ ਪਹੁੰਚ ਸਕਦੇ ਹਨ। ਸਿਰਫ਼ ਬਹੁਤ ਲੋੜ ਹੋਣ \'ਤੇ ਚਾਲੂ ਕਰੋ। |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | ਤਿਜੌਰੀ ਫ਼ਾਈਲ ਲਿਖੀ ਨਹੀਂ ਜਾ ਸਕੀ। ਜੇ ਤੁਸੀਂ ਬੈਕਅੱਪ ਤੇ ਸਿੰਕ ਫ਼ੋਲਡਰ ਲਾਇਆ ਹੋਇਆ ਹੈ, ਤਾਂ ਹੋ ਸਕਦਾ ਹੈ Android ਨੇ ਉਸ ਦੀ ਇਜਾਜ਼ਤ ਵਾਪਸ ਲੈ ਲਈ ਹੋਵੇ — ਸੈਟਿੰਗਾਂ ਖੋਲ੍ਹੋ, ਫ਼ੋਲਡਰ ਦੁਬਾਰਾ ਚੁਣੋ, ਤੇ ਫਿਰ ਕੋਸ਼ਿਸ਼ ਕਰੋ। |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | ਤੁਹਾਡੀਆਂ ਏਨਕ੍ਰਿਪਟ ਕੀਤੀਆਂ .kosh ਫ਼ਾਈਲਾਂ ਸਿੱਧਾ ਇਸੇ ਫ਼ੋਲਡਰ ਵਿੱਚ ਸਾਂਭੀਆਂ ਜਾਂਦੀਆਂ ਹਨ। ਕਈ ਡਿਵਾਈਸਾਂ ਉੱਤੇ ਆਪਣੇ-ਆਪ ਬੈਕਅੱਪ ਲਈ ਇਸ ਫ਼ੋਲਡਰ ਨੂੰ Google Drive, Syncthing, Nextcloud ਜਾਂ SD ਕਾਰਡ ਨਾਲ ਸਿੰਕ ਕਰੋ। |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | ਨਵੀਂ ਰਿਕਵਰੀ ਕੁੰਜੀ ਬਣਾਉਣ ਲਈ ਆਪਣਾ ਪਾਸਫ਼੍ਰੇਜ਼ ਪਾਓ। ਪੁਰਾਣੀ ਕੁੰਜੀ ਚੱਲਣੀ ਬੰਦ ਹੋ ਜਾਵੇਗੀ। |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | ਇਸ ਸਫ਼ੇ ਦੀ ਬਾਕੀ ਹਰ ਗੱਲ ਇੱਕ ਲੌਗਿਨ ਕਮਜ਼ੋਰ ਕਰਦੀ ਹੈ। ਇਹ ਪੂਰੀ ਤਿਜੌਰੀ ਲੈ ਜਾ ਸਕਦੀ ਹੈ। ਸੈਟਿੰਗਾਂ → ਨਵੀਂ ਰਿਕਵਰੀ ਕੁੰਜੀ ਲਓ। |  |

## Priority 3 — short labels

| key | English | Punjabi | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | ਮਜ਼ਬੂਤ ਪਾਸਵਰਡ ਵਰਤੋ |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | ਖਾਤੇ ਦਾ ਨਾਂ (ਜਿਵੇਂ Google) |  |
| `au_active_many` | %1$d active codes | %1$d ਚਾਲੂ ਕੋਡ |  |
| `au_active_one` | %1$d active code | %1$d ਚਾਲੂ ਕੋਡ |  |
| `au_add_another` | Add another authenticator | ਇੱਕ ਹੋਰ ਪ੍ਰਮਾਣਕ ਜੋੜੋ |  |
| `au_add_secret` | Add Secret Key | ਸੀਕ੍ਰੇਟ ਕੁੰਜੀ ਜੋੜੋ |  |
| `au_camera_needed` | Camera permission needed to scan QR code | QR ਕੋਡ ਸਕੈਨ ਕਰਨ ਲਈ ਕੈਮਰੇ ਦੀ ਇਜਾਜ਼ਤ ਚਾਹੀਦੀ ਹੈ |  |
| `au_copied` | Copied · clears shortly | ਕਾਪੀ ਹੋ ਗਿਆ · ਥੋੜ੍ਹੀ ਦੇਰ ਵਿੱਚ ਮਿਟ ਜਾਵੇਗਾ |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google, GitHub ਜਾਂ ਆਪਣੇ ਬ੍ਰੋਕਰ ਦਾ QR ਸਕੈਨ ਕਰੋ, ਜਾਂ ਸੀਕ੍ਰੇਟ ਕੁੰਜੀ ਹੱਥੀਂ ਲਿਖੋ। |  |
| `au_enter_key` | Enter Key | ਕੁੰਜੀ ਲਿਖੋ |  |
| `au_fallback_name` | Authenticator | ਪ੍ਰਮਾਣਕ |  |
| `au_flashlight` | Flashlight | ਟਾਰਚ |  |
| `au_grant` | Grant Permission | ਇਜਾਜ਼ਤ ਦਿਓ |  |
| `au_image_failed` | Failed to process image | ਤਸਵੀਰ ਪੜ੍ਹੀ ਨਹੀਂ ਜਾ ਸਕੀ |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | ਗ਼ਲਤ Base32 ਸੀਕ੍ਰੇਟ ਕੁੰਜੀ (ਸਿਰਫ਼ A-Z ਅੱਖਰ ਤੇ 2-7 ਅੰਕ) |  |
| `au_no_match` | No codes match | ਕੋਈ ਕੋਡ ਨਹੀਂ ਮਿਲਿਆ |  |
| `au_none_yet` | No codes yet. | ਹਾਲੇ ਕੋਈ ਕੋਡ ਨਹੀਂ। |  |
| `au_pick_image` | Pick Image | ਤਸਵੀਰ ਚੁਣੋ |  |
| `au_rotating` | "Rotating " | "ਬਦਲਦੇ ਰਹਿਣ ਵਾਲੇ " |  |
| `au_rotating_emph` | codes. | ਕੋਡ। |  |
| `au_save_key` | Save Key | ਕੁੰਜੀ ਸਾਂਭੋ |  |
| `au_scan_qr` | Scan a QR code | QR ਕੋਡ ਸਕੈਨ ਕਰੋ |  |
| `au_scan_title` | Scan Authenticator QR | ਪ੍ਰਮਾਣਕ QR ਸਕੈਨ ਕਰੋ |  |
| `au_search_hint` | Search codes, issuers… | ਕੋਡ ਜਾਂ ਦੇਣ ਵਾਲੇ ਲੱਭੋ… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | ਜਿਵੇਂ JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | ਸੀਕ੍ਰੇਟ ਕੁੰਜੀ (Base32) |  |
| `au_tap_to_copy` | Tap to copy | ਕਾਪੀ ਕਰਨ ਲਈ ਟੈਪ ਕਰੋ |  |
| `cat_apps` | Apps &amp; Logins | ਐਪਾਂ ਤੇ ਲੌਗਿਨ |  |
| `cat_banks` | Banks &amp; UPI | ਬੈਂਕ ਤੇ UPI |  |
| `cat_cards` | Cards | ਕਾਰਡ |  |
| `cat_govid` | Gov &amp; ID | ਸਰਕਾਰੀ ਤੇ ਪਛਾਣ |  |
| `cat_investments` | Investments | ਨਿਵੇਸ਼ |  |
| `cat_utilities` | Utilities | ਬਿੱਲ ਤੇ ਕੁਨੈਕਸ਼ਨ |  |
| `cd_mask_hidden` | hidden | ਲੁਕਿਆ ਹੋਇਆ |  |
| `cd_shield_high_sensitivity` | extra-protected field | ਬਹੁਤ ਸੰਵੇਦਨਸ਼ੀਲ ਜਾਣਕਾਰੀ |  |
| `gl_blank` | Blank template | ਖਾਲੀ ਟੈਂਪਲੇਟ |  |
| `gl_cat_apps` | Apps | ਐਪਾਂ |  |
| `gl_cat_banks` | Banks | ਬੈਂਕ |  |
| `gl_cat_cards` | Cards | ਕਾਰਡ |  |
| `gl_cat_demat` | Demat | ਡੀਮੈਟ |  |
| `gl_cat_govid` | Gov ID | ਸਰਕਾਰੀ ਪਛਾਣ |  |
| `gl_cat_popular` | Popular | ਹਰਮਨ-ਪਿਆਰੇ |  |
| `gl_cat_shopping` | Shopping | ਖ਼ਰੀਦਦਾਰੀ |  |
| `gl_cat_travel` | Travel | ਸਫ਼ਰ |  |
| `gl_cat_upi` | UPI | UPI |  |
| `gl_cat_utilities` | Utilities | ਬਿੱਲ |  |
| `gl_head_emph` | storing? | ਸਾਂਭ ਰਹੇ ਹਾਂ? |  |
| `gl_head_lead` | "What are we " | "ਅਸੀਂ ਕੀ " |  |
| `gl_matches` | %1$d matches | %1$d ਮਿਲੇ |  |
| `gl_most_used` | Most-used first | ਸਭ ਤੋਂ ਵੱਧ ਵਰਤੇ ਪਹਿਲਾਂ |  |
| `gl_not_found` | Can’t find a service? | ਸੇਵਾ ਨਹੀਂ ਲੱਭ ਰਹੀ? |  |
| `gl_search` | Search %1$d Indian services… | %1$d ਭਾਰਤੀ ਸੇਵਾਵਾਂ ਵਿੱਚ ਲੱਭੋ… |  |
| `gl_suggested` | Suggested for you | ਤੁਹਾਡੇ ਲਈ ਸੁਝਾਅ |  |
| `hm_add_first` | Add your first record | ਆਪਣਾ ਪਹਿਲਾ ਇੰਦਰਾਜ ਜੋੜੋ |  |
| `hm_all_offline` | all offline. | ਸਭ ਆਫ਼ਲਾਈਨ। |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d ਚੀਜ਼ਾਂ, " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d ਚੀਜ਼, " |  |
| `hm_detail_placeholder` | Pick a record to see it here | ਇੱਥੇ ਵੇਖਣ ਲਈ ਇੱਕ ਰਿਕਾਰਡ ਚੁਣੋ |  |
| `hm_empty_blank` | A blank vault, ready. | ਖਾਲੀ ਤਿਜੌਰੀ, ਤਿਆਰ। |  |
| `hm_empty_head_emph` | waiting. | ਉਡੀਕ ਰਹੀ ਹੈ। |  |
| `hm_empty_head_lead` | "Your vault is " | "ਤੁਹਾਡੀ ਤਿਜੌਰੀ " |  |
| `hm_filter_all` | All | ਸਾਰੇ |  |
| `hm_import_backup` | Import an encrypted backup | ਏਨਕ੍ਰਿਪਟ ਕੀਤਾ ਬੈਕਅੱਪ ਦਰਾਮਦ |  |
| `hm_import_backup_note` | Open a .kosh file from this device | ਇਸੇ ਫ਼ੋਨ ਤੋਂ .kosh ਫ਼ਾਈਲ ਖੋਲ੍ਹੋ |  |
| `hm_inst_many` | %1$d institutions | %1$d ਸੰਸਥਾਵਾਂ |  |
| `hm_inst_one` | %1$d institution | %1$d ਸੰਸਥਾ |  |
| `hm_kit_banner_action` | Save one now | ਹੁਣੇ ਸਾਂਭੋ |  |
| `hm_kit_banner_dismiss` | Remind me later | ਬਾਅਦ ਵਿੱਚ ਯਾਦ ਕਰਾਓ |  |
| `hm_kit_banner_title` | No recovery kit saved | ਕੋਈ ਰਿਕਵਰੀ ਕਿੱਟ ਨਹੀਂ ਸਾਂਭੀ |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | ਖੁੱਲ੍ਹੀ ਹੈ · ਛੱਡਦੇ ਹੀ ਬੰਦ ਹੋਵੇਗੀ |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | ਖੁੱਲ੍ਹੀ ਹੈ · ਛੱਡਣ ਤੋਂ %1$d ਮਿੰਟ ਬਾਅਦ ਬੰਦ |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | ਖੁੱਲ੍ਹੀ ਹੈ · ਛੱਡਣ ਤੋਂ 1 ਮਿੰਟ ਬਾਅਦ ਬੰਦ |  |
| `hm_no_match` | Nothing matches “%1$s” | “%1$s” ਲਈ ਕੁਝ ਨਹੀਂ ਮਿਲਿਆ |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | ਕੋਈ ਸੰਸਥਾ, UPI ਹੈਂਡਲ, ਜਾਂ ਆਖ਼ਰੀ ਚਾਰ ਅੰਕ ਅਜ਼ਮਾਓ। |  |
| `hm_pinned` | Pinned | ਪਿੰਨ ਕੀਤੇ |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC, priya@upi, PAN… ਲੱਭੋ |  |
| `hm_start_template` | Start with a template | ਟੈਂਪਲੇਟ ਤੋਂ ਸ਼ੁਰੂ ਕਰੋ |  |
| `ic_could_not` | Could not import | ਦਰਾਮਦ ਨਹੀਂ ਹੋ ਸਕੀ |  |
| `ic_done` | Done | ਹੋ ਗਿਆ |  |
| `ic_import` | Import | ਦਰਾਮਦ |  |
| `ic_imported` | Imported | ਦਰਾਮਦ ਹੋ ਗਿਆ |  |
| `ic_importing` | Importing… | ਦਰਾਮਦ ਹੋ ਰਿਹਾ ਹੈ… |  |
| `ic_new_many` | %1$d new logins. | %1$d ਨਵੇਂ ਲੌਗਿਨ। |  |
| `ic_new_one` | %1$d new login. | %1$d ਨਵਾਂ ਲੌਗਿਨ। |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d ਜੋੜੇ, %2$d ਬਦਲੇ। |  |
| `ic_title` | Import from %1$s? | %1$s ਤੋਂ ਦਰਾਮਦ ਕਰੀਏ? |  |
| `ic_too_large` | That file is too large to be a credential export. | ਇਹ ਫ਼ਾਈਲ ਪਾਸਵਰਡ ਬਰਾਮਦ ਹੋਣ ਲਈ ਬਹੁਤ ਵੱਡੀ ਹੈ। |  |
| `import_action` | Import | ਦਰਾਮਦ |  |
| `import_locked` | Unlock your vault before importing. | ਦਰਾਮਦ ਕਰਨ ਤੋਂ ਪਹਿਲਾਂ ਆਪਣੀ ਤਿਜੌਰੀ ਖੋਲ੍ਹੋ। |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d ਜੋੜੇ, %2$d ਬਦਲੇ। ਕੁਝ ਵੀ ਮਿਟਾਇਆ ਨਹੀਂ ਗਿਆ। |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | ਉਹ ਫ਼ਾਈਲ Zerokosh ਤਿਜੌਰੀ ਵਜੋਂ ਪੜ੍ਹੀ ਨਹੀਂ ਜਾ ਸਕੀ। |  |
| `import_nothing_new` | Everything in that backup was already here. | ਉਸ ਬੈਕਅੱਪ ਵਿੱਚ ਜੋ ਸੀ, ਉਹ ਸਭ ਪਹਿਲਾਂ ਹੀ ਇੱਥੇ ਸੀ। |  |
| `import_passphrase_label` | Backup passphrase | ਬੈਕਅੱਪ ਦਾ ਪਾਸਫ਼੍ਰੇਜ਼ |  |
| `import_title` | Import a backup | ਬੈਕਅੱਪ ਦਰਾਮਦ ਕਰੋ |  |
| `kicker_locked` | Locked | ਬੰਦ ਹੈ |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · ਕੁਝ ਵੀ ਇਸ ਫ਼ੋਨ ਤੋਂ ਬਾਹਰ ਨਹੀਂ ਗਿਆ |  |
| `lk_touch_unlock` | Touch to unlock | ਖੋਲ੍ਹਣ ਲਈ ਛੋਹੋ |  |
| `lk_welcome_emph` | Your vault is sealed. | ਤੁਹਾਡੀ ਤਿਜੌਰੀ ਬੰਦ ਹੈ। |  |
| `lk_welcome_lead` | Welcome back. | ਜੀ ਆਇਆਂ ਨੂੰ। |  |
| `msg_auth_needed` | Confirm it\'s you to see this | ਵੇਖਣ ਲਈ ਪੱਕਾ ਕਰੋ ਕਿ ਇਹ ਤੁਸੀਂ ਹੀ ਹੋ |  |
| `msg_back` | Back | ਪਿੱਛੇ |  |
| `msg_cancel` | Cancel | ਰੱਦ ਕਰੋ |  |
| `msg_file_damaged` | File damaged — restored from backup | ਫ਼ਾਈਲ ਖ਼ਰਾਬ ਸੀ — ਬੈਕਅੱਪ ਤੋਂ ਠੀਕ ਕਰ ਦਿੱਤੀ |  |
| `msg_ok` | OK | ਠੀਕ ਹੈ |  |
| `msg_saved` | Saved | ਸਾਂਭ ਲਿਆ |  |
| `nav_all_templates` | All templates | ਸਾਰੇ ਟੈਂਪਲੇਟ |  |
| `nav_damaged_emph` | vault file | ਤਿਜੌਰੀ ਫ਼ਾਈਲ ਵਿੱਚ |  |
| `nav_damaged_kicker` | Damaged state | ਖ਼ਰਾਬ ਹਾਲਤ |  |
| `nav_damaged_lead` | "Something in the " | "ਤੁਹਾਡੀ " |  |
| `nav_damaged_tail` | " is off." | " ਕੁਝ ਗੜਬੜ ਹੈ।" |  |
| `nav_integrity_title` | Integrity check failed | ਸਹੀ-ਸਲਾਮਤੀ ਦੀ ਜਾਂਚ ਫ਼ੇਲ੍ਹ ਹੋਈ |  |
| `nav_scan` | Scan | ਸਕੈਨ |  |
| `nav_tap_card` | Tap a card | ਕਾਰਡ ਟੈਪ ਕਰੋ |  |
| `nav_what_next` | What to do next | ਹੁਣ ਅੱਗੇ ਕੀ |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC ਬੰਦ ਹੈ। ਸੈਟਿੰਗਾਂ ਵਿੱਚ ਚਾਲੂ ਕਰਕੇ ਦੁਬਾਰਾ ਕੋਸ਼ਿਸ਼ ਕਰੋ। |  |
| `nfc_hold_card` | Hold your card to the phone | ਕਾਰਡ ਫ਼ੋਨ ਨਾਲ ਲਾ ਕੇ ਰੱਖੋ |  |
| `nfc_missed` | Did not catch that | ਫੜਿਆ ਨਹੀਂ ਗਿਆ |  |
| `nfc_read_failed` | That card could not be read. Try again. | ਉਹ ਕਾਰਡ ਪੜ੍ਹਿਆ ਨਹੀਂ ਗਿਆ। ਦੁਬਾਰਾ ਕੋਸ਼ਿਸ਼ ਕਰੋ। |  |
| `nfc_reading` | Reading… | ਪੜ੍ਹ ਰਹੇ ਹਾਂ… |  |
| `nfc_try_again` | Try again | ਦੁਬਾਰਾ ਕੋਸ਼ਿਸ਼ ਕਰੋ |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · ਇਸੇ ਫ਼ੋਨ ਉੱਤੇ ਮਾਪਿਆ |  |
| `ob_argon_faster` | Faster unlock | ਛੇਤੀ ਖੁੱਲ੍ਹੇਗੀ |  |
| `ob_argon_harder` | Harder to attack | ਤੋੜਨੀ ਔਖੀ |  |
| `ob_argon_measuring` | Measuring this device… | ਇਹ ਫ਼ੋਨ ਮਾਪ ਰਹੇ ਹਾਂ… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id ਸਖ਼ਤੀ |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | ਸ਼ਬਦਕੋਸ਼ ਦਾ ਇੱਕੋ ਸ਼ਬਦ ਨਹੀਂ |  |
| `ob_check_pass_length` | 10 characters or more | 10 ਜਾਂ ਵੱਧ ਅੱਖਰ |  |
| `ob_check_pass_reuse` | Not reused from another app | ਕਿਸੇ ਹੋਰ ਐਪ ਤੋਂ ਦੁਬਾਰਾ ਨਹੀਂ ਵਰਤਿਆ |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | ਨਾ ਜਨਮ ਦਿਨ, ਨਾ ਵਰ੍ਹੇਗੰਢ |  |
| `ob_check_pin_digits` | All six digits entered | ਛੇਵੇਂ ਅੰਕ ਤੱਕ ਭਰਿਆ |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | ਨਾ ਲਗਾਤਾਰ ਅੰਕ, ਨਾ ਦੁਹਰਾਓ |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~ਤੋੜਨ ਲਈ %1$d ਸਦੀਆਂ |  |
| `ob_crack_days` | ~%1$d days to crack | ~ਤੋੜਨ ਲਈ %1$d ਦਿਨ |  |
| `ob_crack_forever` | longer than the sun | ਸੂਰਜ ਨਾਲੋਂ ਵੀ ਵੱਧ ਸਮਾਂ |  |
| `ob_crack_hours` | ~hours to crack | ~ਤੋੜਨ ਲਈ ਕੁਝ ਘੰਟੇ |  |
| `ob_crack_seconds` | ~seconds to crack | ~ਤੋੜਨ ਲਈ ਕੁਝ ਸਕਿੰਟ |  |
| `ob_crack_years` | ~%1$d years to crack | ~ਤੋੜਨ ਲਈ %1$d ਸਾਲ |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | ਤਸਦੀਕਸ਼ੁਦਾ, ਹਰ ਤਿਜੌਰੀ ਲਈ ਵੱਖਰਾ ਨੌਂਸ |  |
| `ob_fact_encryption_title` | Encryption | ਏਨਕ੍ਰਿਪਸ਼ਨ |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | ਸੈੱਟਅੱਪ ਵੇਲੇ ਤੁਹਾਡੇ ਫ਼ੋਨ ਉੱਤੇ ਮਾਪਿਆ |  |
| `ob_fact_kdf_title` | Key stretching | ਕੁੰਜੀ ਸਟ੍ਰੈਚਿੰਗ |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | ਨਾ ਰੀਸੈੱਟ ਲਿੰਕ। ਨਾ ਸਪੋਰਟ ਦਾ ਪਿਛਲਾ ਦਰਵਾਜ਼ਾ। |  |
| `ob_fact_lost_value` | Nobody can recover it | ਕੋਈ ਵਾਪਸ ਨਹੀਂ ਲਿਆ ਸਕਦਾ |  |
| `ob_fact_network_title` | Network permission | ਨੈੱਟਵਰਕ ਦੀ ਇਜਾਜ਼ਤ |  |
| `ob_fact_network_value` | Not requested | ਕਦੇ ਮੰਗੀ ਹੀ ਨਹੀਂ |  |
| `ob_fact_quick_title` | Quick unlock | ਛੇਤੀ ਅਨਲੌਕ |  |
| `ob_fact_quick_value` | Hardware keystore | ਹਾਰਡਵੇਅਰ ਕੀਸਟੋਰ |  |
| `ob_lang_continue` | Continue in %1$s | %1$s ਵਿੱਚ ਅੱਗੇ ਚੱਲੋ |  |
| `ob_lang_head_emph` | language. | ਭਾਸ਼ਾ ਚੁਣੋ। |  |
| `ob_lang_head_lead` | "Choose your " | "ਆਪਣੀ " |  |
| `ob_lang_search` | Search %1$d languages | %1$d ਭਾਸ਼ਾਵਾਂ ਵਿੱਚ ਲੱਭੋ |  |
| `ob_quick_continue_pass` | Continue with passphrase | ਪਾਸਫ਼੍ਰੇਜ਼ ਨਾਲ ਅੱਗੇ ਚੱਲੋ |  |
| `ob_quick_enable` | Enable quick unlock | ਛੇਤੀ ਅਨਲੌਕ ਚਾਲੂ ਕਰੋ |  |
| `ob_quick_fingerprint` | Fingerprint | ਉਂਗਲ ਦਾ ਨਿਸ਼ਾਨ |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | ਛੇਤੀ, ਹਾਰਡਵੇਅਰ ਨਾਲ ਸੁਰੱਖਿਅਤ ਅਨਲੌਕ। |  |
| `ob_quick_head_emph` | Without the cloud. | ਬਿਨਾਂ ਕਲਾਉਡ। |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "ਇੱਕ ਛੋਹ ਨਾਲ ਖੁੱਲ੍ਹੇ। " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox। ਕੋਈ ਵੀ ਬਾਇਓਮੈਟ੍ਰਿਕ ਜਾਣਕਾਰੀ ਕਦੇ Zerokosh ਤੱਕ ਨਹੀਂ ਪਹੁੰਚਦੀ। |  |
| `ob_quick_hw_title` | Hardware-backed | ਹਾਰਡਵੇਅਰ ਨਾਲ ਸੁਰੱਖਿਅਤ |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | ਇਸ ਫ਼ੋਨ ਵਿੱਚ ਹਾਰਡਵੇਅਰ ਸੈਂਸਰ ਨਹੀਂ ਹੈ। |  |
| `ob_quick_opening` | Opening your vault… | ਤੁਹਾਡੀ ਤਿਜੌਰੀ ਖੁੱਲ੍ਹ ਰਹੀ ਹੈ… |  |
| `ob_quick_pass_only` | Passphrase only | ਸਿਰਫ਼ ਪਾਸਫ਼੍ਰੇਜ਼ |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | ਹਰ ਵਾਰ ਲਿਖੋ। ਸਭ ਤੋਂ ਸੁਰੱਖਿਅਤ। |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | ਛੇਤੀ ਅਨਲੌਕ ਸੈੱਟ ਨਹੀਂ ਹੋਇਆ। ਦੁਬਾਰਾ ਕੋਸ਼ਿਸ਼ ਕਰੋ, ਜਾਂ ਪਾਸਫ਼੍ਰੇਜ਼ ਨਾਲ ਹੀ ਅੱਗੇ ਚੱਲੋ। |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | ਹੁਣੇ ਨਹੀਂ — ਮੈਂ ਪਾਸਫ਼੍ਰੇਜ਼ ਲਿਖ ਲਵਾਂਗਾ |  |
| `ob_quick_touch_title` | Touch the sensor | ਸੈਂਸਰ ਨੂੰ ਛੋਹੋ |  |
| `ob_recommended` | Recommended | ਸਿਫ਼ਾਰਸ਼ |  |
| `ob_reveal_hide` | Hide | ਲੁਕਾਓ |  |
| `ob_reveal_show` | Show | ਵਿਖਾਓ |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | ਲਪੇਟਣ ਵਾਲੀ ਕੁੰਜੀ ਹਾਰਡਵੇਅਰ ਕੀਸਟੋਰ ਵਿੱਚ ਰਹੇਗੀ। ਬਾਇਓਮੈਟ੍ਰਿਕ ਅਗਲੇ ਪੜਾਅ ਵਿੱਚ। |  |
| `ob_seal_title` | Seal to this device | ਇਸੇ ਫ਼ੋਨ ਨਾਲ ਬੰਨ੍ਹੋ |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | ਇਸ ਫ਼ੋਨ ਵਿੱਚ ਹਾਰਡਵੇਅਰ ਬਾਇਓਮੈਟ੍ਰਿਕ ਨਹੀਂ ਹੈ। |  |
| `ob_soon` | SOON | ਜਲਦੀ |  |
| `ob_step_label` | Step %1$d of 6 | ਪੜਾਅ %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | ਕੁਝ ਅਜਿਹਾ ਚੁਣੋ ਜੋ ਸਿਰਫ਼ ਤੁਸੀਂ ਹੀ ਕਹਿੰਦੇ ਹੋ |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | ਵਧੀਆ · %1$d ਬਿੱਟ ਐਂਟਰੌਪੀ |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | ਬਹੁਤ ਛੋਟਾ · 10 ਅੱਖਰ ਚਾਹੀਦੇ ਹਨ |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | ਮਜ਼ਬੂਤ · %1$d ਬਿੱਟ ਐਂਟਰੌਪੀ |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | ਕਮਜ਼ੋਰ · %1$d ਬਿੱਟ ਐਂਟਰੌਪੀ |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | ਛੇ ਅੰਕ ਜੋ ਤੁਹਾਡੀ ਜ਼ਿੰਦਗੀ ਵੇਖ ਕੇ ਕੋਈ ਅੰਦਾਜ਼ਾ ਨਾ ਲਾ ਸਕੇ |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | ਠੀਕ-ਠਾਕ · %1$d ਬਿੱਟ — ਪਿੰਨ ਇਸ ਤੋਂ ਮਜ਼ਬੂਤ ਨਹੀਂ ਹੋ ਸਕਦਾ |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | ਬਹੁਤ ਛੋਟਾ · 6 ਅੰਕ ਚਾਹੀਦੇ ਹਨ |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | ਕਮਜ਼ੋਰ · ਇਹੀ ਪਿੰਨ ਸਭ ਤੋਂ ਪਹਿਲਾਂ ਅਜ਼ਮਾਏ ਜਾਂਦੇ ਹਨ |  |
| `ob_try_label` | TRY | ਅਜ਼ਮਾਓ |  |
| `qa_aadhaar` | Aadhaar | ਆਧਾਰ |  |
| `qa_bank_account` | Bank account | ਬੈਂਕ ਖਾਤਾ |  |
| `qa_pan` | PAN | PAN |  |
| `qa_upi_id` | UPI ID | UPI ID |  |
| `rd_copied` | Copied | ਕਾਪੀ ਹੋ ਗਿਆ |  |
| `rd_forget` | Forget | ਭੁੱਲ ਜਾਓ |  |
| `rd_forget_these` | Forget these | ਇਹ ਭੁੱਲ ਜਾਓ |  |
| `rd_forget_title` | Forget previous passwords? | ਪੁਰਾਣੇ ਪਾਸਵਰਡ ਭੁੱਲ ਜਾਈਏ? |  |
| `rd_history_hide` | Hide | ਲੁਕਾਓ |  |
| `rd_history_show` | Show %1$d | %1$d ਵਿਖਾਓ |  |
| `rd_hold_to_reveal` | Hold to reveal | ਵੇਖਣ ਲਈ ਦੱਬੀ ਰੱਖੋ |  |
| `rd_last_edit` | last edit %1$s | ਆਖ਼ਰੀ ਵਾਰ %1$s ਬਦਲਿਆ |  |
| `rd_release_to_hide` | Release to hide | ਲੁਕਾਉਣ ਲਈ ਛੱਡੋ |  |
| `re_add_field` | + Add another field | + ਇੱਕ ਹੋਰ ਖਾਨਾ ਜੋੜੋ |  |
| `re_add_field_title` | Add a field | ਖਾਨਾ ਜੋੜੋ |  |
| `re_field_name` | Field name | ਖਾਨੇ ਦਾ ਨਾਂ |  |
| `re_pick_date` | Pick a date | ਤਰੀਕ ਚੁਣੋ |  |
| `re_remove` | Remove | ਹਟਾਓ |  |
| `re_tap_card` | Read the card by tapping it | ਪੜ੍ਹਨ ਲਈ ਕਾਰਡ ਟੈਪ ਕਰੋ |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | ਭੇਤ ਵਾਂਗ ਸਮਝੋ (ਲੁਕਿਆ ਰਹੇਗਾ, ਵੇਖਣ ਲਈ ਦੱਬੀ ਰੱਖੋ) |  |
| `re_using_template` | using the %1$s template | %1$s ਟੈਂਪਲੇਟ ਵਰਤ ਕੇ |  |
| `rem_kit_title` | No recovery kit saved | ਕੋਈ ਰਿਕਵਰੀ ਕਿੱਟ ਨਹੀਂ ਸਾਂਭੀ |  |
| `scr_about_license` | License: GPL-3.0 — free forever | ਲਾਇਸੰਸ: GPL-3.0 — ਹਮੇਸ਼ਾ ਮੁਫ਼ਤ |  |
| `scr_about_source` | Source code | ਸੋਰਸ ਕੋਡ |  |
| `scr_about_version` | Version %1$s | ਵਰਜਨ %1$s |  |
| `scr_auth_add_qr` | Add via QR | QR ਨਾਲ ਜੋੜੋ |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | ਤੁਹਾਡੀਆਂ ਐਪਾਂ ਤੇ ਬ੍ਰੋਕਰ ਦੇ ਕੋਡ ਇੱਥੇ ਦਿਸਣਗੇ |  |
| `scr_auth_scan_title` | Point the camera at the QR code | ਕੈਮਰਾ QR ਕੋਡ ਉੱਤੇ ਰੱਖੋ |  |
| `scr_detail_copied` | Copied · clears in 30s | ਕਾਪੀ ਹੋ ਗਿਆ · 30 ਸਕਿੰਟ ਵਿੱਚ ਮਿਟ ਜਾਵੇਗਾ |  |
| `scr_detail_copy` | Copy | ਕਾਪੀ ਕਰੋ |  |
| `scr_detail_edit` | Edit | ਬਦਲੋ |  |
| `scr_detail_favorite` | Favourite | ਪਸੰਦੀਦਾ |  |
| `scr_detail_hidden` | Hidden | ਲੁਕਿਆ ਹੋਇਆ |  |
| `scr_detail_hide` | Hide | ਲੁਕਾਓ |  |
| `scr_detail_history_empty` | Nothing replaced yet. | ਹਾਲੇ ਤੱਕ ਕੁਝ ਨਹੀਂ ਬਦਲਿਆ। |  |
| `scr_detail_history_title` | Previous passwords | ਪੁਰਾਣੇ ਪਾਸਵਰਡ |  |
| `scr_detail_reveal` | Show | ਵਿਖਾਓ |  |
| `scr_detail_shown` | Shown | ਦਿਸ ਰਿਹਾ ਹੈ |  |
| `scr_edit_cancel` | Cancel | ਰੱਦ ਕਰੋ |  |
| `scr_edit_generate` | Generate | ਬਣਾਓ |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | ਬੈਂਕ / ਕੰਪਨੀ (ਇਕੱਠੇ ਕਰਨ ਲਈ) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | ਇਹ ਸਹੀ ਨਹੀਂ ਜਾਪਦਾ — ਇੱਕ ਵਾਰ ਵੇਖ ਲਓ |  |
| `scr_edit_link_none` | None | ਕੁਝ ਨਹੀਂ |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | ਇਹ ਕਾਰਡ ਨੰਬਰ ਆਮ ਜਾਂਚ ਵਿੱਚ ਪਾਸ ਨਹੀਂ ਹੁੰਦਾ — ਜੇ ਸਹੀ ਹੈ ਤਾਂ ਸਾਂਭ ਲਓ |  |
| `scr_edit_month` | Month | ਮਹੀਨਾ |  |
| `scr_edit_picker_other` | Other… | ਹੋਰ… |  |
| `scr_edit_picker_other_hint` | Type your own | ਆਪਣਾ ਲਿਖੋ |  |
| `scr_edit_required_title` | Give it a name first | ਪਹਿਲਾਂ ਇਸ ਨੂੰ ਕੋਈ ਨਾਂ ਦਿਓ |  |
| `scr_edit_save` | Save | ਸਾਂਭੋ |  |
| `scr_edit_title_hint` | Title | ਨਾਂ |  |
| `scr_edit_title_new` | New | ਨਵਾਂ |  |
| `scr_edit_year` | Year | ਸਾਲ |  |
| `scr_gallery_quick_add` | Quick add | ਛੇਤੀ ਜੋੜੋ |  |
| `scr_gallery_title` | What do you want to save? | ਅਸੀਂ ਕੀ ਸਾਂਭ ਰਹੇ ਹਾਂ? |  |
| `scr_home_add` | Add | ਜੋੜੋ |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | ਤੁਹਾਡਾ ਬੈਂਕ ਖਾਤਾ ਇੰਝ ਦਿਸੇਗਾ |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | ਤੁਹਾਡੇ ਕਾਰਡ, UPI, ਐਪ ਲੌਗਿਨ — ਸਭ ਇੱਥੇ ਹੀ |  |
| `scr_home_group_other` | Other | ਹੋਰ |  |
| `scr_home_no_results` | Nothing matches your search | ਤੁਹਾਡੀ ਖੋਜ ਨਾਲ ਕੁਝ ਨਹੀਂ ਮਿਲਿਆ |  |
| `scr_home_search_hint` | Search your vault | ਆਪਣੀ ਤਿਜੌਰੀ ਵਿੱਚ ਲੱਭੋ |  |
| `scr_home_tab_authenticator` | Authenticator | ਕੋਡ |  |
| `scr_home_tab_home` | Home | ਘਰ |  |
| `scr_home_tab_settings` | Settings | ਸੈਟਿੰਗਾਂ |  |
| `scr_home_title` | Home | ਘਰ |  |
| `scr_language_continue` | Continue | ਅੱਗੇ ਚੱਲੋ |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | ਆਪਣੀ ਭਾਸ਼ਾ ਚੁਣੋ |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | ਕਾਪੀ ਕਰਨ ਲਈ ਬਟਨ ਦੱਬੋ · 30 ਸਕਿੰਟ ਵਿੱਚ ਮਿਟ ਜਾਵੇਗਾ |  |
| `scr_login_helper_channel` | Login helper | ਲੌਗਿਨ ਮਦਦਗਾਰ |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s ਵਿੱਚ ਲੌਗਿਨ ਕਰ ਰਹੇ ਹੋ |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | ਹੁਣੇ ਬੈਕਅੱਪ ਫ਼ੋਲਡਰ ਲਾ ਲਓ |  |
| `scr_quickunlock_enable` | Turn on | ਚਾਲੂ ਕਰੋ |  |
| `scr_quickunlock_skip` | Not now | ਹੁਣੇ ਨਹੀਂ |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | ਉਂਗਲ ਜਾਂ ਚਿਹਰੇ ਨਾਲ ਖੋਲ੍ਹੋ |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s ਦੀ ਤਰੀਕ ਨੇੜੇ ਹੈ · Zerokosh ਖੋਲ੍ਹੋ |  |
| `scr_reminder_channel` | Renewal reminders | ਨਵਿਆਉਣ ਦੀ ਯਾਦ |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh ਯਾਦ ਕਰਾ ਰਿਹਾ ਹੈ |  |
| `scr_settings_about` | About | ਬਾਰੇ |  |
| `scr_settings_allow_screenshots` | Allow screenshots | ਸਕਰੀਨਸ਼ਾਟ ਲੈਣ ਦਿਓ |  |
| `scr_settings_autofill` | Autofill service | ਆਟੋਫ਼ਿਲ ਸੇਵਾ |  |
| `scr_settings_autofill_off` | Not set up | ਸੈੱਟ ਨਹੀਂ ਕੀਤਾ |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | ਉਪਲਬਧ ਨਹੀਂ |  |
| `scr_settings_autolock` | Lock when I leave the app | ਐਪ ਛੱਡਦੇ ਹੀ ਬੰਦ ਕਰੋ |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 ਮਿੰਟ ਬਾਅਦ |  |
| `scr_settings_autolock_immediately` | Immediately | ਤੁਰੰਤ |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d ਮਿੰਟ ਬਾਅਦ |  |
| `scr_settings_change_passphrase` | Change passphrase | ਪਾਸਫ਼੍ਰੇਜ਼ ਬਦਲੋ |  |
| `scr_settings_current_passphrase` | Current passphrase | ਮੌਜੂਦਾ ਪਾਸਫ਼੍ਰੇਜ਼ |  |
| `scr_settings_export` | Export | ਬਰਾਮਦ ਕਰੋ |  |
| `scr_settings_import` | Import passwords | ਪਾਸਵਰਡ ਦਰਾਮਦ ਕਰੋ |  |
| `scr_settings_language` | Language | ਭਾਸ਼ਾ |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | ਨਵਾਂ ਪਾਸਫ਼੍ਰੇਜ਼ (ਘੱਟੋ-ਘੱਟ 10 ਅੱਖਰ) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | ਨਵੀਂ ਰਿਕਵਰੀ ਕੁੰਜੀ ਲਓ |  |
| `scr_settings_passphrase_changed` | Passphrase changed | ਪਾਸਫ਼੍ਰੇਜ਼ ਬਦਲ ਗਿਆ |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | ਉਂਗਲ / ਚਿਹਰਾ ਅਨਲੌਕ |  |
| `scr_settings_security_info` | How your data is protected | ਤੁਹਾਡਾ ਡਾਟਾ ਕਿਵੇਂ ਸੁਰੱਖਿਅਤ ਹੈ |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | ਬੈਕਅੱਪ ਤੇ ਸਿੰਕ ਫ਼ੋਲਡਰ |  |
| `scr_settings_sync_not_set` | Not backed up | ਕੋਈ ਬੈਕਅੱਪ ਨਹੀਂ |  |
| `scr_settings_title` | Settings | ਸੈਟਿੰਗਾਂ |  |
| `se_title` | Not saved | ਸਾਂਭਿਆ ਨਹੀਂ ਗਿਆ |  |
| `st_active_folder` | Active Folder | ਚਾਲੂ ਫ਼ੋਲਡਰ |  |
| `st_active_value` | Active · %1$s | ਚਾਲੂ · %1$s |  |
| `st_backing_up` | Backing up vault… | ਤਿਜੌਰੀ ਦਾ ਬੈਕਅੱਪ ਲੈ ਰਹੇ ਹਾਂ… |  |
| `st_backup_now` | Backup Now | ਹੁਣੇ ਬੈਕਅੱਪ ਲਓ |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | ਬੈਕਅੱਪ ਤੇ ਸਿੰਕ ਫ਼ੋਲਡਰ |  |
| `st_change_folder` | Change Folder | ਫ਼ੋਲਡਰ ਬਦਲੋ |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | ਨਵਾਂ ਪਾਸਫ਼੍ਰੇਜ਼ ਦੁਬਾਰਾ ਲਿਖੋ |  |
| `st_connected_folder` | Connected folder: %1$s | ਜੁੜਿਆ ਫ਼ੋਲਡਰ: %1$s |  |
| `st_disconnect` | Disconnect | ਹਟਾਓ |  |
| `st_done` | Done | ਹੋ ਗਿਆ |  |
| `st_export_kosh` | Export encrypted .kosh | ਏਨਕ੍ਰਿਪਟ ਕੀਤੀ .kosh ਬਰਾਮਦ |  |
| `st_folder_fallback` | Folder | ਫ਼ੋਲਡਰ |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | ਪਾਸਫ਼੍ਰੇਜ਼ ਭੁੱਲ ਗਏ? |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | ਆਪਣੀ ਉਂਗਲ ਨਾਲ ਨਵਾਂ ਰੱਖੋ |  |
| `st_generate` | Generate | ਬਣਾਓ |  |
| `st_group_about` | About | ਬਾਰੇ |  |
| `st_group_appearance` | Appearance | ਦਿੱਖ |  |
| `st_group_security` | Security | ਸੁਰੱਖਿਆ |  |
| `st_group_sync` | Sync | ਸਿੰਕ |  |
| `st_import_kosh` | Import a .kosh backup | .kosh ਬੈਕਅੱਪ ਦਰਾਮਦ |  |
| `st_import_other` | Import from another password manager | ਕਿਸੇ ਹੋਰ ਪਾਸਵਰਡ ਮੈਨੇਜਰ ਤੋਂ ਦਰਾਮਦ |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | ਲਾਇਸੰਸ |  |
| `st_logos_by` | Logos provided by | ਲੋਗੋ ਵੱਲੋਂ |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | ਇਹਨੂੰ ਆਫ਼ਲਾਈਨ ਰੱਖੋ। ਪੁਰਾਣੀ ਰਿਕਵਰੀ ਕੁੰਜੀ ਹੁਣ ਕੰਮ ਨਹੀਂ ਕਰਦੀ। |  |
| `st_new_recovery_result` | Your new Recovery Key: | ਤੁਹਾਡੀ ਨਵੀਂ ਰਿਕਵਰੀ ਕੁੰਜੀ: |  |
| `st_subtitle` | Your rules. | ਤੁਹਾਡੇ ਨਿਯਮ। |  |
| `st_theme` | Theme | ਥੀਮ |  |
| `st_theme_dark` | Dark | ਗੂੜ੍ਹਾ |  |
| `st_theme_light` | Light | ਹਲਕਾ |  |
| `st_theme_system` | System | ਸਿਸਟਮ |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | ਤਿਜੌਰੀ %1$s ਵਿੱਚ ਸਾਂਭ ਲਈ! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | ਬੈਕਅੱਪ ਨਹੀਂ ਹੋਇਆ — ਫ਼ੋਲਡਰ ਦੀ ਇਜਾਜ਼ਤ ਵੇਖੋ |  |
| `st_toast_disconnected` | Backup folder disconnected | ਬੈਕਅੱਪ ਫ਼ੋਲਡਰ ਹਟਾ ਦਿੱਤਾ |  |
| `st_toast_export_failed` | Export failed | ਬਰਾਮਦ ਨਹੀਂ ਹੋ ਸਕੀ |  |
| `st_toast_exported` | Encrypted vault exported | ਏਨਕ੍ਰਿਪਟ ਕੀਤੀ ਤਿਜੌਰੀ ਬਰਾਮਦ ਹੋ ਗਈ |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | ਬੈਕਅੱਪ ਫ਼ੋਲਡਰ ਜੁੜ ਗਿਆ, ਤੇ ਤਿਜੌਰੀ %1$s ਵਿੱਚ ਸਾਂਭ ਲਈ! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | ਬੈਕਅੱਪ ਫ਼ੋਲਡਰ ਜੁੜ ਗਿਆ: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | ਫ਼ੋਲਡਰ ਨਹੀਂ ਜੁੜ ਸਕਿਆ: %1$s |  |
| `st_vault_review` | Vault review | ਤਿਜੌਰੀ ਦੀ ਜਾਂਚ |  |
| `st_vault_review_detail` | Reused, weak, expiring | ਦੁਬਾਰਾ ਵਰਤੇ, ਕਮਜ਼ੋਰ, ਮਿਆਦ ਮੁੱਕਦੇ |  |
| `tab_codes` | Codes | ਕੋਡ |  |
| `tab_settings` | Settings | ਸੈਟਿੰਗਾਂ |  |
| `tab_templates` | Templates | ਟੈਂਪਲੇਟ |  |
| `tab_vault` | Vault | ਤਿਜੌਰੀ |  |
| `time_days` | %1$dd ago | %1$d ਦਿਨ ਪਹਿਲਾਂ |  |
| `time_hours` | %1$dh ago | %1$d ਘੰਟੇ ਪਹਿਲਾਂ |  |
| `time_just_now` | just now | ਹੁਣੇ ਹੀ |  |
| `time_minutes` | %1$dm ago | %1$d ਮਿੰਟ ਪਹਿਲਾਂ |  |
| `time_months` | %1$dmo ago | %1$d ਮਹੀਨੇ ਪਹਿਲਾਂ |  |
| `time_years` | %1$dy ago | %1$d ਸਾਲ ਪਹਿਲਾਂ |  |
| `tpl_aadhaar_card` | Aadhaar Card | ਆਧਾਰ |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | ਆਧਾਰ ਨੰਬਰ |  |
| `tpl_aadhaar_card_address` | Address | ਆਧਾਰ ਉੱਤੇ ਪਤਾ |  |
| `tpl_aadhaar_card_dob` | Dob | ਜਨਮ ਤਰੀਕ |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | ਸਕੈਨ ਕੀਤੀ ਕਾਪੀ |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | ਜੁੜਿਆ ਮੋਬਾਈਲ |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar ਪਾਸਕੋਡ |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | ਆਧਾਰ ਉੱਤੇ ਨਾਂ |  |
| `tpl_aadhaar_card_notes` | Notes | ਨੋਟ |  |
| `tpl_app_profile` | App Profile | ਐਪ ਪ੍ਰੋਫ਼ਾਈਲ |  |
| `tpl_app_profile_app_name` | App name | ਐਪ ਦਾ ਨਾਂ |  |
| `tpl_app_profile_gift_cards` | Gift cards | ਗਿਫ਼ਟ ਕਾਰਡ |  |
| `tpl_app_profile_membership` | Membership | ਮੈਂਬਰਸ਼ਿਪ |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | ਮੈਂਬਰਸ਼ਿਪ ਨਵਿਆਉਣਾ |  |
| `tpl_app_profile_notes` | Notes | ਨੋਟ |  |
| `tpl_app_profile_password_if_any` | Password (if any) | ਪਾਸਵਰਡ (ਜੇ ਹੈ) |  |
| `tpl_app_profile_registered_email` | Registered email | ਰਜਿਸਟਰ ਈਮੇਲ |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | ਰਜਿਸਟਰ ਮੋਬਾਈਲ |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | ਵਾਲਿਟ ਪਿੰਨ |  |
| `tpl_bank_account` | Bank Account | ਬੈਂਕ ਖਾਤਾ |  |
| `tpl_bank_account_account_number` | Account number | ਖਾਤਾ ਨੰਬਰ |  |
| `tpl_bank_account_account_type` | Account type | ਖਾਤੇ ਦੀ ਕਿਸਮ |  |
| `tpl_bank_account_bank_name` | Bank name | ਬੈਂਕ ਦਾ ਨਾਂ |  |
| `tpl_bank_account_branch` | Branch | ਸ਼ਾਖਾ |  |
| `tpl_bank_account_customer_id` | Customer id | ਗਾਹਕ ID |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC ਕੋਡ |  |
| `tpl_bank_account_login_password` | Login password | ਲੌਗਿਨ ਪਾਸਵਰਡ |  |
| `tpl_bank_account_micr` | MICR code | MICR ਕੋਡ |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | ਨੈੱਟ-ਬੈਂਕਿੰਗ ਯੂਜ਼ਰ ID |  |
| `tpl_bank_account_nominee` | Nominee | ਨਾਮਜ਼ਦ |  |
| `tpl_bank_account_notes` | Notes | ਨੋਟ |  |
| `tpl_bank_account_profile_password` | Profile password | ਪ੍ਰੋਫ਼ਾਈਲ ਪਾਸਵਰਡ |  |
| `tpl_bank_account_registered_email` | Registered email | ਰਜਿਸਟਰ ਈਮੇਲ |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | ਰਜਿਸਟਰ ਮੋਬਾਈਲ |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | ਲੈਣ-ਦੇਣ ਦਾ ਪਾਸਵਰਡ |  |
| `tpl_card` | Card | ਕਾਰਡ |  |
| `tpl_card_atm_pin` | ATM PIN | ATM ਪਿੰਨ |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | ਬਿਲਿੰਗ ਚੱਕਰ ਦਾ ਦਿਨ |  |
| `tpl_card_card_network` | Card network | ਨੈੱਟਵਰਕ |  |
| `tpl_card_card_number` | Card number | ਕਾਰਡ ਨੰਬਰ |  |
| `tpl_card_card_portal_login` | Card portal login | ਕਾਰਡ ਪੋਰਟਲ ਲੌਗਿਨ |  |
| `tpl_card_card_portal_password` | Card portal password | ਕਾਰਡ ਪੋਰਟਲ ਪਾਸਵਰਡ |  |
| `tpl_card_card_type` | Card type | ਕਾਰਡ ਦੀ ਕਿਸਮ |  |
| `tpl_card_card_variant` | Card variant | ਕਾਰਡ ਵੇਰੀਐਂਟ |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | ਮਿਆਦ |  |
| `tpl_card_linked_account` | Linked account | ਜੁੜਿਆ ਖਾਤਾ |  |
| `tpl_card_name_on_card` | Name on card | ਕਾਰਡ ਉੱਤੇ ਨਾਂ |  |
| `tpl_card_notes` | Notes | ਨੋਟ |  |
| `tpl_demat` | Demat | ਡੀਮੈਟ |  |
| `tpl_demat_api_key` | API key | API ਕੁੰਜੀ |  |
| `tpl_demat_api_secret` | API secret | API ਸੀਕ੍ਰੇਟ |  |
| `tpl_demat_bo_id` | BO ID | BO ID |  |
| `tpl_demat_broker` | Broker | ਬ੍ਰੋਕਰ |  |
| `tpl_demat_cdsl_tpin` | CDSL TPIN | CDSL TPIN |  |
| `tpl_demat_client_id` | Client id | ਕਲਾਇੰਟ ID |  |
| `tpl_demat_depository` | Depository | ਡਿਪਾਜ਼ਟਰੀ |  |
| `tpl_demat_dp_id` | DP ID | DP ID |  |
| `tpl_demat_login_password` | Login password | ਲੌਗਿਨ ਪਾਸਵਰਡ |  |
| `tpl_demat_mf_folios` | Mutual fund folios | ਮਿਊਚੁਅਲ ਫ਼ੰਡ ਫੋਲੀਓ |  |
| `tpl_demat_mpin` | MPIN | MPIN |  |
| `tpl_demat_nominee` | Nominee | ਨਾਮਜ਼ਦ |  |
| `tpl_demat_notes` | Notes | ਨੋਟ |  |
| `tpl_digilocker` | Digilocker | DigiLocker |  |
| `tpl_digilocker_login_username` | Login username | ਯੂਜ਼ਰਨੇਮ |  |
| `tpl_digilocker_notes` | Notes | ਨੋਟ |  |
| `tpl_digilocker_portal_password` | Portal password | ਪਾਸਵਰਡ |  |
| `tpl_digilocker_security_pin` | Security pin | ਸੁਰੱਖਿਆ ਪਿੰਨ |  |
| `tpl_driving_license` | Driving License | ਡਰਾਈਵਿੰਗ ਲਾਇਸੰਸ |  |
| `tpl_driving_license_dl_number` | Dl number | ਲਾਇਸੰਸ ਨੰਬਰ |  |
| `tpl_driving_license_dob` | Dob | ਜਨਮ ਤਰੀਕ |  |
| `tpl_driving_license_expiry_date` | Expiry date | ਇਸ ਤਰੀਕ ਤੱਕ ਵੈਧ |  |
| `tpl_driving_license_file_copy` | Scanned copy | ਸਕੈਨ ਕੀਤੀ ਕਾਪੀ |  |
| `tpl_driving_license_issue_date` | Issue date | ਜਾਰੀ ਹੋਣ ਦੀ ਤਰੀਕ |  |
| `tpl_driving_license_name_on_dl` | Name on dl | ਲਾਇਸੰਸ ਉੱਤੇ ਨਾਂ |  |
| `tpl_driving_license_notes` | Notes | ਨੋਟ |  |
| `tpl_driving_license_rto_location` | Rto location | RTO |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | ਗੱਡੀਆਂ ਦੀਆਂ ਸ਼੍ਰੇਣੀਆਂ |  |
| `tpl_epf_pension` | Epf Pension | EPF / ਪੈਨਸ਼ਨ |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | ਜੁੜਿਆ ਮੋਬਾਈਲ |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | EPF ਉੱਤੇ ਨਾਂ |  |
| `tpl_epf_pension_nominee` | Nominee | ਨਾਮਜ਼ਦ |  |
| `tpl_epf_pension_notes` | Notes | ਨੋਟ |  |
| `tpl_epf_pension_password` | Password | ਪਾਸਵਰਡ |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | PF ਮੈਂਬਰ ID |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO ਪਾਸਵਰਡ |  |
| `tpl_epf_pension_scheme` | Scheme | ਸਕੀਮ |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | ਸਰਕਾਰੀ ਪਛਾਣ ਪੱਤਰ |  |
| `tpl_gov_id_expiry` | Expiry | ਮਿਆਦ |  |
| `tpl_gov_id_file_copy` | Scanned copy | ਸਕੈਨ ਕੀਤੀ ਕਾਪੀ |  |
| `tpl_gov_id_id_kind` | ID type | ਪਛਾਣ ਪੱਤਰ ਦੀ ਕਿਸਮ |  |
| `tpl_gov_id_id_number` | ID number | ਪਛਾਣ ਨੰਬਰ |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | ਪੱਤਰ ਮੁਤਾਬਕ ਨਾਂ |  |
| `tpl_gov_id_notes` | Notes | ਨੋਟ |  |
| `tpl_gov_id_portal_login` | Portal login | ਪੋਰਟਲ ਲੌਗਿਨ |  |
| `tpl_gov_id_portal_password` | Portal password | ਪੋਰਟਲ ਪਾਸਵਰਡ |  |
| `tpl_insurance` | Insurance | ਬੀਮਾ |  |
| `tpl_insurance_agent_contact` | Agent contact | ਏਜੰਟ ਦਾ ਸੰਪਰਕ |  |
| `tpl_insurance_commencement_date` | Commencement date | ਸ਼ੁਰੂ ਹੋਣ ਦੀ ਤਰੀਕ |  |
| `tpl_insurance_insurer` | Insurer | ਬੀਮਾ ਕੰਪਨੀ |  |
| `tpl_insurance_maturity_date` | Maturity date | ਪੂਰੀ ਹੋਣ ਦੀ ਤਰੀਕ |  |
| `tpl_insurance_nominee` | Nominee | ਨਾਮਜ਼ਦ |  |
| `tpl_insurance_notes` | Notes | ਨੋਟ |  |
| `tpl_insurance_policy_number` | Policy number | ਪਾਲਿਸੀ ਨੰਬਰ |  |
| `tpl_insurance_policy_term` | Policy term | ਪਾਲਿਸੀ ਦੀ ਮਿਆਦ |  |
| `tpl_insurance_policy_type` | Policy type | ਪਾਲਿਸੀ ਦੀ ਕਿਸਮ |  |
| `tpl_insurance_portal_login` | Portal login | ਪੋਰਟਲ ਲੌਗਿਨ |  |
| `tpl_insurance_portal_password` | Portal password | ਪੋਰਟਲ ਪਾਸਵਰਡ |  |
| `tpl_insurance_premium_amount` | Premium amount | ਪ੍ਰੀਮੀਅਮ ਰਕਮ |  |
| `tpl_insurance_premium_due_date` | Premium due date | ਪ੍ਰੀਮੀਅਮ ਦੀ ਤਰੀਕ |  |
| `tpl_insurance_premium_mode` | Premium mode | ਪ੍ਰੀਮੀਅਮ ਕਿਵੇਂ ਭਰਦੇ ਹੋ |  |
| `tpl_insurance_sum_assured` | Sum assured | ਬੀਮੇ ਦੀ ਰਕਮ |  |
| `tpl_login` | Login | ਲੌਗਿਨ |  |
| `tpl_login_notes` | Notes | ਨੋਟ |  |
| `tpl_login_password` | Password | ਪਾਸਵਰਡ |  |
| `tpl_login_recovery_codes` | Recovery codes | ਰਿਕਵਰੀ ਕੋਡ |  |
| `tpl_login_username` | Username | ਯੂਜ਼ਰਨੇਮ |  |
| `tpl_login_website` | Website | ਵੈੱਬਸਾਈਟ |  |
| `tpl_pan_card` | Pan Card | PAN ਕਾਰਡ |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | ਆਧਾਰ ਨਾਲ ਜੁੜਿਆ |  |
| `tpl_pan_card_dob` | Dob | ਜਨਮ ਤਰੀਕ |  |
| `tpl_pan_card_e_filing_password` | E filing password | ਈ-ਫ਼ਾਈਲਿੰਗ ਪਾਸਵਰਡ |  |
| `tpl_pan_card_fathers_name` | Fathers name | ਪਿਤਾ ਦਾ ਨਾਂ |  |
| `tpl_pan_card_file_copy` | Scanned copy | ਸਕੈਨ ਕੀਤੀ ਕਾਪੀ |  |
| `tpl_pan_card_name_on_pan` | Name on pan | PAN ਉੱਤੇ ਨਾਂ |  |
| `tpl_pan_card_notes` | Notes | ਨੋਟ |  |
| `tpl_pan_card_pan_number` | Pan number | PAN |  |
| `tpl_passkey` | Passkey | ਪਾਸਕੀ |  |
| `tpl_passkey_credential_id` | Credential ID | ਕ੍ਰੈਡੈਂਸ਼ੀਅਲ ID |  |
| `tpl_passkey_notes` | Notes | ਨੋਟ |  |
| `tpl_passkey_private_key` | Private key | ਨਿੱਜੀ ਕੁੰਜੀ |  |
| `tpl_passkey_sign_count` | Sign count | ਸਾਈਨ ਕਾਊਂਟ |  |
| `tpl_passkey_user_handle` | User handle | ਯੂਜ਼ਰ ਹੈਂਡਲ |  |
| `tpl_passkey_username` | Username | ਯੂਜ਼ਰਨੇਮ |  |
| `tpl_passkey_website` | Website | ਵੈੱਬਸਾਈਟ |  |
| `tpl_passport` | Passport | ਪਾਸਪੋਰਟ |  |
| `tpl_passport_dob` | Dob | ਜਨਮ ਤਰੀਕ |  |
| `tpl_passport_expiry_date` | Expiry date | ਮਿਆਦ ਮੁੱਕਣ ਦੀ ਤਰੀਕ |  |
| `tpl_passport_file_copy` | Scanned copy | ਸਕੈਨ ਕੀਤੀ ਕਾਪੀ |  |
| `tpl_passport_given_names` | Given names | ਦਿੱਤਾ ਨਾਂ |  |
| `tpl_passport_issue_date` | Issue date | ਜਾਰੀ ਹੋਣ ਦੀ ਤਰੀਕ |  |
| `tpl_passport_notes` | Notes | ਨੋਟ |  |
| `tpl_passport_passport_number` | Passport number | ਪਾਸਪੋਰਟ ਨੰਬਰ |  |
| `tpl_passport_place_of_issue` | Place of issue | ਜਾਰੀ ਹੋਣ ਦੀ ਥਾਂ |  |
| `tpl_passport_portal_login` | Portal login | Passport Seva ਲੌਗਿਨ |  |
| `tpl_passport_portal_password` | Portal password | Passport Seva ਪਾਸਵਰਡ |  |
| `tpl_passport_surname` | Surname | ਗੋਤ |  |
| `tpl_secure_note` | Secure Note | ਸੁਰੱਖਿਅਤ ਨੋਟ |  |
| `tpl_secure_note_attachment` | Attachment | ਨੱਥੀ |  |
| `tpl_secure_note_body` | Note | ਨੋਟ |  |
| `tpl_shopping` | Shopping | ਖ਼ਰੀਦਦਾਰੀ ਖਾਤਾ |  |
| `tpl_shopping_gift_card_code` | Gift card code | ਗਿਫ਼ਟ ਕਾਰਡ ਕੋਡ |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | ਗਿਫ਼ਟ ਕਾਰਡ ਪਿੰਨ |  |
| `tpl_shopping_membership_id` | Membership id | ਮੈਂਬਰਸ਼ਿਪ ID |  |
| `tpl_shopping_notes` | Notes | ਨੋਟ |  |
| `tpl_shopping_password` | Password | ਪਾਸਵਰਡ |  |
| `tpl_shopping_registered_email` | Registered email | ਰਜਿਸਟਰ ਈਮੇਲ |  |
| `tpl_shopping_registered_mobile` | Registered mobile | ਰਜਿਸਟਰ ਮੋਬਾਈਲ |  |
| `tpl_shopping_wallet_pin` | Wallet pin | ਵਾਲਿਟ ਪਿੰਨ |  |
| `tpl_telecom` | Telecom | ਮੋਬਾਈਲ ਤੇ ਇੰਟਰਨੈੱਟ |  |
| `tpl_telecom_account_number` | Account number | ਖਾਤਾ ਨੰਬਰ |  |
| `tpl_telecom_circle` | Circle | ਸਰਕਲ |  |
| `tpl_telecom_mobile_number` | Mobile number | ਮੋਬਾਈਲ ਨੰਬਰ |  |
| `tpl_telecom_notes` | Notes | ਨੋਟ |  |
| `tpl_telecom_operator` | Operator | ਕੰਪਨੀ |  |
| `tpl_telecom_plan_type` | Plan type | ਪਲਾਨ ਦੀ ਕਿਸਮ |  |
| `tpl_telecom_portal_password` | Portal password | ਪੋਰਟਲ ਪਾਸਵਰਡ |  |
| `tpl_telecom_puk` | PUK code | PUK ਕੋਡ |  |
| `tpl_telecom_renewal_date` | Renewal date | ਰੀਚਾਰਜ ਦੀ ਤਰੀਕ |  |
| `tpl_telecom_sim_number` | Sim number | ਸਿਮ ਨੰਬਰ (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | ਸਿਮ ਪਿੰਨ |  |
| `tpl_transit` | Transit | ਸਫ਼ਰ ਪਾਸ |  |
| `tpl_transit_login_password` | Login password | ਲੌਗਿਨ ਪਾਸਵਰਡ |  |
| `tpl_transit_notes` | Notes | ਨੋਟ |  |
| `tpl_transit_operator_name` | Operator name | ਕੰਪਨੀ |  |
| `tpl_transit_registered_email` | Registered email | ਰਜਿਸਟਰ ਈਮੇਲ |  |
| `tpl_transit_registered_mobile` | Registered mobile | ਰਜਿਸਟਰ ਮੋਬਾਈਲ |  |
| `tpl_transit_smart_card_number` | Smart card number | ਸਮਾਰਟ ਕਾਰਡ ਨੰਬਰ |  |
| `tpl_transit_wallet_pin` | Wallet pin | ਵਾਲਿਟ ਪਿੰਨ |  |
| `tpl_travel_booking` | Travel Booking | ਸਫ਼ਰ ਦੀ ਬੁਕਿੰਗ |  |
| `tpl_travel_booking_account_username` | Account username | ਯੂਜ਼ਰਨੇਮ |  |
| `tpl_travel_booking_login_password` | Login password | ਲੌਗਿਨ ਪਾਸਵਰਡ |  |
| `tpl_travel_booking_notes` | Notes | ਨੋਟ |  |
| `tpl_travel_booking_provider` | Provider | ਕੰਪਨੀ |  |
| `tpl_travel_booking_registered_email` | Registered email | ਰਜਿਸਟਰ ਈਮੇਲ |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | ਰਜਿਸਟਰ ਮੋਬਾਈਲ |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | ਵਾਲਿਟ ਪਿੰਨ |  |
| `tpl_upi` | UPI | UPI |  |
| `tpl_upi_app_name` | App name | UPI ਐਪ |  |
| `tpl_upi_apps_used` | Apps used | ਕਿਹੜੀਆਂ ਐਪਾਂ ਵਿੱਚ ਚਾਲੂ |  |
| `tpl_upi_linked_account` | Linked account | ਜੁੜਿਆ ਖਾਤਾ |  |
| `tpl_upi_notes` | Notes | ਨੋਟ |  |
| `tpl_upi_upi_id` | UPI ID | UPI ID |  |
| `tpl_upi_upi_pin` | UPI PIN | UPI ਪਿੰਨ |  |
| `tpl_utility` | Utility | ਬਿੱਲ ਤੇ ਕੁਨੈਕਸ਼ਨ |  |
| `tpl_utility_account_holder` | Account holder | ਖਾਤਾਧਾਰਕ |  |
| `tpl_utility_consumer_number` | Consumer number | ਖਪਤਕਾਰ ਨੰਬਰ |  |
| `tpl_utility_due_day` | Bill due day | ਬਿੱਲ ਭਰਨ ਦਾ ਦਿਨ |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag ID |  |
| `tpl_utility_notes` | Notes | ਨੋਟ |  |
| `tpl_utility_portal_login` | Portal login | ਪੋਰਟਲ ਲੌਗਿਨ |  |
| `tpl_utility_portal_password` | Portal password | ਪੋਰਟਲ ਪਾਸਵਰਡ |  |
| `tpl_utility_provider` | Provider | ਸੇਵਾ ਦੇਣ ਵਾਲੀ ਕੰਪਨੀ |  |
| `tpl_utility_utility_kind` | Utility kind | ਕਿਸ ਚੀਜ਼ ਦਾ ਬਿੱਲ |  |
| `tpl_utility_vehicle_number` | Vehicle number | ਗੱਡੀ ਨੰਬਰ |  |
| `tpl_utility_wifi_password` | Wi-Fi password | Wi-Fi ਪਾਸਵਰਡ |  |
| `tpl_voter_id` | Voter Id | ਵੋਟਰ ਪਛਾਣ ਪੱਤਰ |  |
| `tpl_voter_id_constituency` | Constituency | ਹਲਕਾ |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC ਨੰਬਰ |  |
| `tpl_voter_id_file_copy` | Scanned copy | ਸਕੈਨ ਕੀਤੀ ਕਾਪੀ |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | ਵੋਟਰ ਕਾਰਡ ਉੱਤੇ ਨਾਂ |  |
| `tpl_voter_id_notes` | Notes | ਨੋਟ |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP ਪਾਸਵਰਡ |  |
| `tr_days_many` | %1$d days left | %1$d ਦਿਨ ਬਾਕੀ |  |
| `tr_days_one` | %1$d day left | %1$d ਦਿਨ ਬਾਕੀ |  |
| `tr_gone_today` | gone today | ਅੱਜ ਚਲਾ ਜਾਵੇਗਾ |  |
| `tr_restore` | Restore | ਵਾਪਸ ਲਿਆਓ |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | ਉਸ ਤੋਂ ਬਾਅਦ ਉਹ ਹਮੇਸ਼ਾ ਲਈ ਚਲੇ ਜਾਂਦੇ ਹਨ — ਕਿਤੇ ਹੋਰ ਕੋਈ ਕਾਪੀ ਨਹੀਂ। |  |
| `ui_hide_passphrase` | Hide passphrase | ਪਾਸਫ਼੍ਰੇਜ਼ ਲੁਕਾਓ |  |
| `ui_label_count` | %1$s, %2$s | %1$s, %2$s |  |
| `ui_show_passphrase` | Show passphrase | ਪਾਸਫ਼੍ਰੇਜ਼ ਵਿਖਾਓ |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | ਇਸੇ ਫ਼ੋਨ ਉੱਤੇ %1$d ਇੰਦਰਾਜਾਂ ਨਾਲ ਮਿਲਾਨ ਕੀਤਾ। ਕੁਝ ਵੀ ਕਿਤੇ ਨਹੀਂ ਭੇਜਿਆ ਗਿਆ। |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | ਇਸੇ ਫ਼ੋਨ ਉੱਤੇ %1$d ਇੰਦਰਾਜ ਨਾਲ ਮਿਲਾਨ ਕੀਤਾ। ਕੁਝ ਵੀ ਕਿਤੇ ਨਹੀਂ ਭੇਜਿਆ ਗਿਆ। |  |
| `vh_count_many` | %1$d things worth a look. | %1$d ਗੱਲਾਂ ਵੱਲ ਧਿਆਨ ਦੇਣਾ ਬਣਦਾ ਹੈ। |  |
| `vh_count_one` | %1$d thing worth a look. | %1$d ਗੱਲ ਵੱਲ ਧਿਆਨ ਦੇਣਾ ਬਣਦਾ ਹੈ। |  |
| `vh_empty` | No reused, weak or expiring credentials. | ਨਾ ਦੁਬਾਰਾ ਵਰਤਿਆ, ਨਾ ਕਮਜ਼ੋਰ, ਨਾ ਮਿਆਦ ਮੁੱਕਦਾ ਕੁਝ ਹੈ। |  |
| `vh_kind_common` | Commonly guessed | ਸੌਖਾ ਅੰਦਾਜ਼ਾ ਲੱਗਣ ਵਾਲਾ |  |
| `vh_kind_expiring` | Expiring | ਮਿਆਦ ਮੁੱਕ ਰਹੀ ਹੈ |  |
| `vh_kind_reused` | Reused password | ਦੁਬਾਰਾ ਵਰਤਿਆ ਪਾਸਵਰਡ |  |
| `vh_kind_weak` | Weak | ਕਮਜ਼ੋਰ |  |
| `vh_no_kit_title` | No recovery kit saved | ਕੋਈ ਰਿਕਵਰੀ ਕਿੱਟ ਨਹੀਂ ਸਾਂਭੀ |  |
| `vh_nothing` | Nothing to fix. | ਠੀਕ ਕਰਨ ਲਈ ਕੁਝ ਨਹੀਂ। |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | ਆਫ਼ਲਾਈਨ |  |
| `wl_chip_open` | Open source | ਓਪਨ ਸੋਰਸ |  |
| `wl_create` | Create a new vault | ਨਵੀਂ ਤਿਜੌਰੀ ਬਣਾਓ |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | ਨਾ ਈਮੇਲ · ਨਾ ਖਾਤਾ · ਕੁਝ ਵੀ ਇਸ ਫ਼ੋਨ ਤੋਂ ਬਾਹਰ ਨਹੀਂ ਜਾਂਦਾ |  |
| `wl_head_1` | Your keys. | ਤੁਹਾਡੀਆਂ ਕੁੰਜੀਆਂ। |  |
| `wl_head_2` | Your device. | ਤੁਹਾਡਾ ਫ਼ੋਨ। |  |
| `wl_head_3` | No server. | ਕੋਈ ਸਰਵਰ ਨਹੀਂ। |  |
| `wl_restore` | Restore from Recovery Kit | ਰਿਕਵਰੀ ਕਿੱਟ ਤੋਂ ਵਾਪਸ ਲਿਆਓ |  |
| `wl_sr_headline` | Your keys. Your device. No server. | ਤੁਹਾਡੀਆਂ ਕੁੰਜੀਆਂ। ਤੁਹਾਡਾ ਫ਼ੋਨ। ਕੋਈ ਸਰਵਰ ਨਹੀਂ। |  |
