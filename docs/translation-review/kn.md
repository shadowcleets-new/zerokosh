# Kannada (`kn`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-kn/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Kannada | ok? |
|---|---|---|---|
| `au_close` | Close | ಮುಚ್ಚಿ |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | ಆರಿಸಿದ ಚಿತ್ರದಲ್ಲಿ ಸರಿಯಾದ TOTP QR ಕೋಡ್ ಸಿಗಲಿಲ್ಲ |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | ಖಾಲಿಯಿಂದ ಶುರು ಮಾಡಿ ನಿಮ್ಮ ಫೀಲ್ಡ್‌ಗಳಿಗೆ ನೀವೇ ಹೆಸರಿಡಿ — ಟೆಂಪ್ಲೇಟ್ ಲೇಬಲ್ ಮಾತ್ರ ತುಂಬುತ್ತದೆ, ಡೇಟಾ ಎಂದೂ ಅಲ್ಲ. |  |
| `hm_close_search` | Close search | ಹುಡುಕಾಟ ಮುಚ್ಚಿ |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | ಈ ಫೋನಿನಿಂದ ಎಂದೂ ಹೊರಗೆ ಹೋಗುವುದಿಲ್ಲ. ಉಳಿಸುವಾಗ ಎನ್‌ಕ್ರಿಪ್ಟ್. |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | ಈಗ ನೀವು ಆಮದು ಮಾಡಿದ ಫೈಲ್ ಅನ್ನು ಅಳಿಸಿ. ಅದು ನಿಮ್ಮ ಪಾಸ್‌ವರ್ಡ್‌ಗಳ ಬಿಚ್ಚಿಟ್ಟ ಪಟ್ಟಿ, ಇನ್ನೂ ನಿಮ್ಮ Downloads ನಲ್ಲಿ ಬಿದ್ದಿದೆ. |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | ಎಲ್ಲವೂ ಇದೇ ಫೋನಿನಲ್ಲಿ ಮಾತ್ರ ಡೀಕ್ರಿಪ್ಟ್ ಆಗುತ್ತದೆ. ಏನೂ ಅಪ್‌ಲೋಡ್ ಆಗುವುದಿಲ್ಲ, ಏಕೆಂದರೆ ಈ ಆಪ್ ನೆಟ್‌ವರ್ಕ್ ಸಂಪರ್ಕ ತೆರೆಯಲೂ ಸಾಧ್ಯವಿಲ್ಲ. |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | ಬ್ಯಾಂಕುಗಳು ನಿಮ್ಮ OTP ಎಂದೂ ಕೇಳುವುದಿಲ್ಲ. ಕೇಳಿದವನು ಮೋಸಗಾರ. |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | ಯಾವ ಬ್ಯಾಂಕ್ ಅಧಿಕಾರಿಯೂ ಪರದೆ ಹಂಚುವ ಆಪ್ ಹಾಕಿಸುವುದಿಲ್ಲ. |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | ನಿಮ್ಮ UPI ಪಿನ್ UPI ಆಪ್ ಕೀಪ್ಯಾಡ್‌ಗೆ ಮಾತ್ರ — ಕರೆಯಲ್ಲಿ ಯಾರಿಗೂ ಹೇಳಬೇಡಿ. |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | KYC ಒಂದೇ ದಿನದಲ್ಲಿ ಮುಗಿಯುವುದಿಲ್ಲ. “ಇಂದು KYC ಮುಗಿಯುತ್ತಿದೆ” ಎಂಬ ಸಂದೇಶಗಳು ಮೋಸ. |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | ಹಣ ಪಡೆಯಲು ಪಿನ್ ಹಾಕುವ ಅಥವಾ QR ಸ್ಕ್ಯಾನ್ ಮಾಡುವ ಅಗತ್ಯವೇ ಇಲ್ಲ. |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | ವಿದ್ಯುತ್ ಕಡಿತದ SMS, ಅದರಲ್ಲಿ ಯಾರದೋ ಖಾಸಗಿ ನಂಬರ್? ಅದು ಮೋಸ. |  |
| `nav_close_menu` | Close menu | ಮೆನು ಮುಚ್ಚಿ |  |
| `nfc_cannot_read` | Cannot read cards | ಕಾರ್ಡ್ ಓದಲಾಗದು |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | ಈ ಫೋನಿನಲ್ಲಿ NFC ಇಲ್ಲ, ಹಾಗಾಗಿ ಕಾರ್ಡ್ ಓದಲಾಗದು. |  |
| `ob_fact_lost_title` | If you lose your keys | ಕೀಲಿಗಳು ಕಳೆದುಹೋದರೆ |  |
| `ob_fact_network_note` | The app literally cannot phone home | ಈ ಆಪ್ ಎಲ್ಲಿಗೂ ಸಂಪರ್ಕಿಸಲು ಸಾಧ್ಯವೇ ಇಲ್ಲ |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | ಬಯೋಮೆಟ್ರಿಕ್ ಭದ್ರತಾ ಚಿಪ್‌ನಿಂದ ಎಂದೂ ಹೊರಗೆ ಹೋಗುವುದಿಲ್ಲ |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | ನಿಮ್ಮ ಎನ್‌ಕ್ರಿಪ್ಟ್ ಮಾಡಿದ ಖಜಾನೆಯನ್ನು ಸಿಂಕ್ ಮಾಡುವ ಅದೇ ಫೋಲ್ಡರ್‌ನಲ್ಲಿ ಕೀಯನ್ನು ಇಟ್ಟಿದ್ದೀರಿ. ಈಗ ಆ ಫೋಲ್ಡರ್ ಸಿಕ್ಕವರಿಗೆ ಎರಡೂ ಸಿಗುತ್ತವೆ. ಕೀಯನ್ನು ಬೇರೆ ಕಡೆ ಇಡಿ — ಕಾಗದ, ಬೇರೆ ಖಾತೆ, ಅಥವಾ ಒಂದು ಡ್ರಾಯರ್. |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | ಇದು ನಿಮ್ಮ ಖಜಾನೆ ಫೈಲ್ ಪಕ್ಕದಲ್ಲೇ ಇದೆ |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH ರಿಕವರಿ |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | ಸ್ಕ್ಯಾನ್ ಮಾಡಿ ಅಥವಾ ಟೈಪ್ ಮಾಡಿ. ಮತ್ತೆ ಇನ್‌ಸ್ಟಾಲ್, ಫ್ಯಾಕ್ಟರಿ ರೀಸೆಟ್, ಫೋನ್ ಕಳೆದುಹೋದ ಮೇಲೂ ಕೆಲಸ ಮಾಡುತ್ತದೆ. |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | ಈಗ ಉಳಿಸಿದ ಕಿಟ್‌ನಿಂದ ಗುಂಪು %1$d ಮತ್ತು ಗುಂಪು %2$d ಟೈಪ್ ಮಾಡಿ. |  |
| `ob_kit_challenge_hint` | Group %1$d | ಗುಂಪು %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | ಮೊದಲು ಕಿಟ್ ಉಳಿಸಿ, ನಂತರ ಗುಂಪು %1$d ಮತ್ತು %2$d ಮತ್ತೆ ಟೈಪ್ ಮಾಡಿ. |  |
| `ob_kit_challenge_title` | Check you actually have it | ಕಿಟ್ ನಿಜವಾಗಿಯೂ ನಿಮ್ಮ ಬಳಿ ಇದೆಯೇ ನೋಡಿ |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | ಇದು ಮೇಲಿನ ಕೀಗೆ ಹೊಂದುತ್ತಿಲ್ಲ. |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | ಯಾರೂ — Zerokosh ಕೂಡ — ಇದನ್ನು ನನಗಾಗಿ ಮರಳಿ ತರಲಾರರು. |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "ಇದನ್ನು ಆಫ್‌ಲೈನ್‌ನಲ್ಲಿ ಇಟ್ಟಿದ್ದೇನೆ. " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | ಒಮ್ಮೆ ಮಾತ್ರ ಕಾಣುತ್ತದೆ, ಎಂದಿಗೂ ತೆರೆದ ರೂಪದಲ್ಲಿ ಇಡುವುದಿಲ್ಲ. ಒಳಗೆ ಬರಲು ಸಾಧ್ಯವಾದರೆ ಸೆಟ್ಟಿಂಗ್‌ಗಳಿಂದ ಹೊಸದನ್ನು ಮಾಡಿ. |  |
| `ob_kit_head_emph` | On paper. | ಕಾಗದದ ಮೇಲೆ. |  |
| `ob_kit_head_lead` | "One key. " | "ಒಂದು ಕೀಲಿ. " |  |
| `ob_kit_head_tail` | " Never online." | " ಎಂದೂ ಆನ್‌ಲೈನ್‌ನಲ್ಲಿ ಅಲ್ಲ." |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | Gmail ಬೇಡ, WhatsApp ಬೇಡ, ಸ್ಕ್ರೀನ್‌ಶಾಟ್ ಬೇಡ. ತಿಜೋರಿ, ಬ್ಯಾಂಕ್ ಲಾಕರ್, ಅಥವಾ ಉಕ್ಕಿನ ತಗಡು. |  |
| `ob_kit_offline_title` | Keep it off the internet | ಅದನ್ನು ಇಂಟರ್ನೆಟ್‌ನಿಂದ ದೂರ ಇಡಿ |  |
| `ob_kit_print` | Print | ಮುದ್ರಿಸಿ |  |
| `ob_kit_print_note` | A printer, or Save as PDF | ಪ್ರಿಂಟರ್, ಅಥವಾ PDF ಆಗಿ ಉಳಿಸಿ |  |
| `ob_kit_qr` | QR image | QR ಚಿತ್ರ |  |
| `ob_kit_qr_cd` | Recovery key QR code | ರಿಕವರಿ ಕೀ QR ಕೋಡ್ |  |
| `ob_kit_qr_note` | To an offline gallery | ಆಫ್‌ಲೈನ್ ಗ್ಯಾಲರಿಗೆ |  |
| `ob_kit_regenerate` | Regenerate | ಹೊಸದು ರಚಿಸಿ |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | ಇದು ಉಳಿಯಲಿಲ್ಲ. ಮತ್ತೆ ಪ್ರಯತ್ನಿಸಿ, ಅಥವಾ ಬೇರೆ ಜಾಗ ಆರಿಸಿ. |  |
| `ob_kit_save_pdf` | Save PDF | PDF ಉಳಿಸಿ |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | ಒಂದು ಪುಟದ ಮುದ್ರಿಸಬಹುದಾದ ಕಿಟ್ |  |
| `ob_kit_saved` | I\'ve saved my kit | ನನ್ನ ಕಿಟ್ ಉಳಿಸಿಕೊಂಡಿದ್ದೇನೆ |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s ನಲ್ಲಿ ಉಳಿಸಲಾಗಿದೆ |  |
| `ob_kit_sent_to_printer` | Sent to the printer | ಪ್ರಿಂಟರ್‌ಗೆ ಕಳುಹಿಸಲಾಗಿದೆ |  |
| `ob_kit_skip` | I\'ll do this later | ಇದನ್ನು ನಂತರ ಮಾಡುತ್ತೇನೆ |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | ನಿಮ್ಮ ಖಜಾನೆ ಕೆಲಸ ಮಾಡುತ್ತಲೇ ಇರುತ್ತದೆ. ಕಿಟ್ ಉಳಿಸುವವರೆಗೂ Zerokosh ನೆನಪಿಸುತ್ತಲೇ ಇರುತ್ತದೆ. |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | ಇದೇ ಫೋನಿನಲ್ಲಿ ರಚನೆಯಾಗಿದೆ, ಒಮ್ಮೆ ಮಾತ್ರ ಕಾಣಿಸುತ್ತದೆ. ಪಾಸ್‌ಫ್ರೇಸ್ ಮರೆತರೆ ಒಳಗೆ ಮರಳಲು ಇದೊಂದೇ ದಾರಿ. |  |
| `ob_kit_working` | Working… | ಕೆಲಸ ನಡೆಯುತ್ತಿದೆ… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | ತಿಜೋರಿಯ ಲೇಬಲ್, ಟೆಂಪ್ಲೇಟ್ ಮತ್ತು ಎಚ್ಚರಿಕೆಗಳು ತಕ್ಷಣ ಬದಲಾಗುತ್ತವೆ. ಸೆಟ್ಟಿಂಗ್‌ನಲ್ಲಿ ಯಾವಾಗ ಬೇಕಾದರೂ ಬದಲಿಸಬಹುದು. |  |
| `ob_pass_confirm` | Confirm | ಮತ್ತೆ ಟೈಪ್ ಮಾಡಿ |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | ಇದು ನಮಗೆ ಎಂದೂ ಕಾಣಿಸುವುದಿಲ್ಲ. ರೀಸೆಟ್ ಲಿಂಕ್ ಇಲ್ಲ. |  |
| `ob_pass_head_emph` | held only | ನಿಮ್ಮ ಬಳಿ ಮಾತ್ರ |  |
| `ob_pass_head_lead` | "One secret, " | "ಒಂದೇ ರಹಸ್ಯ, " |  |
| `ob_pass_head_tail` | " by you." | . |  |
| `ob_pass_no_match` | no match | ಹೊಂದಾಣಿಕೆಯಾಗಿಲ್ಲ |  |
| `ob_pass_seal` | Seal the vault | ತಿಜೋರಿಗೆ ಬೀಗ ಹಾಕಿ |  |
| `ob_pass_sealing` | Sealing… | ಬೀಗ ಹಾಕಲಾಗುತ್ತಿದೆ… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | ಸಂಬಂಧವಿಲ್ಲದ ಮೂರು ನಾಲ್ಕು ಪದಗಳು, ಒಂದು ಜಾಣ ಪದಕ್ಕಿಂತ ಮೇಲು. ಈ ಪರದೆಯಿಂದ ಏನೂ ಹೊರಗೆ ಹೋಗುವುದಿಲ್ಲ. |  |
| `ob_pass_tab_passphrase` | Passphrase | ಪಾಸ್‌ಫ್ರೇಸ್ |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 ಅಂಕಿಯ ಪಿನ್ |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | ನಿಮ್ಮ ಬೆರಳಚ್ಚು ಫೋನಿನ ಭದ್ರತಾ ಚಿಪ್ ಒಳಗೆ ಇರುತ್ತದೆ. ಅದು ಈ ಫೋನಿನಿಂದ ಎಂದೂ ಹೊರಗೆ ಹೋಗುವುದಿಲ್ಲ. |  |
| `ob_trust_continue` | I understand · Continue | ಅರ್ಥವಾಯಿತು · ಮುಂದುವರಿಸಿ |  |
| `ob_trust_head_emph` | don\'t | ಗೊತ್ತಿಲ್ಲ |  |
| `ob_trust_head_lead` | "Exactly what we " | "ನಮಗೆ ನಿಜವಾಗಿ ಏನು " |  |
| `ob_trust_head_tail` | " know." | " ಎಂಬುದು." |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | ಪಾಸ್‌ಫ್ರೇಸ್ ಮರೆತು ರಿಕವರಿ ಕಿಟ್ ಕೂಡ ಕಳೆದುಕೊಂಡರೆ, ತಿಜೋರಿ ಮುಚ್ಚಿಯೇ ಇರುತ್ತದೆ — ನಿಮಗೂ, ನಮಗೂ, ಯಾರಿಗೂ. |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "ಕೀಲಿಗಳು ನಿಮ್ಮ ಬಳಿಯೇ. " |  |
| `ob_trust_stat_files` | .kosh file on device | ಫೋನಿನಲ್ಲಿ .kosh ಫೈಲ್ |  |
| `ob_trust_stat_servers` | servers contacted | ಸರ್ವರ್ ಸಂಪರ್ಕ |  |
| `ob_trust_stat_trackers` | trackers or SDKs | ಟ್ರ್ಯಾಕರ್ ಅಥವಾ SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | ಇದನ್ನು ಒಮ್ಮೆ ಓದಿ. ಇಡೀ ಭದ್ರತಾ ವ್ಯವಸ್ಥೆ ಇದೇ, ಸರಳ ಮಾತಿನಲ್ಲಿ. |  |
| `ob_trust_tag_audited` | Audited build | ಆಡಿಟ್ ಆದ ಬಿಲ್ಡ್ |  |
| `ob_trust_tag_reproducible` | Reproducible APK | ಮತ್ತೆ ರಚಿಸಬಹುದಾದ APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | ನೀವು ಬೆರಳಚ್ಚಿನಿಂದ ತೆರೆಯುತ್ತಿದ್ದೀರಿ. ಬೆರಳಚ್ಚು ಎಂದಾದರೂ ಕೆಲಸ ಮಾಡದಿದ್ದರೆ, ಇದೇ ನಿಮ್ಮನ್ನು ಒಳಗೆ ತರುತ್ತದೆ — ಹಾಗಾಗಿ ನೋಡಿಕೊಳ್ಳುವುದು ಒಳ್ಳೆಯದು. |  |
| `pc_confirm` | Check | ಪರಿಶೀಲಿಸಿ |  |
| `pc_correct` | Still correct. Nothing to do. | ಇನ್ನೂ ಸರಿಯಾಗಿದೆ. ಮಾಡುವುದೇನೂ ಇಲ್ಲ. |  |
| `pc_forgot` | I cannot remember it | ನನಗೆ ನೆನಪಾಗುತ್ತಿಲ್ಲ |  |
| `pc_later` | Not now | ಈಗ ಬೇಡ |  |
| `pc_reset_action` | Set new passphrase | ಹೊಸ ಪಾಸ್‌ಫ್ರೇಸ್ ಇಡಿ |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | ನಿಮ್ಮ ಬೆರಳಚ್ಚು ಈ ಖಜಾನೆಯನ್ನು ತೆರೆಯಬಲ್ಲದು, ಹಾಗಾಗಿ ಅದೇ ಹೊಸ ಪಾಸ್‌ಫ್ರೇಸನ್ನೂ ಇಡಬಲ್ಲದು — ರಿಕವರಿ ಕಿಟ್ ಬೇಕಿಲ್ಲ. ಖಚಿತಪಡಿಸಲು ಇನ್ನೊಮ್ಮೆ ಕೇಳಲಾಗುತ್ತದೆ. |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | ಪಾಸ್‌ಫ್ರೇಸ್ ಬದಲಾಯಿತು. ತ್ವರಿತ ಅನ್‌ಲಾಕ್ ಮತ್ತೆ ಹೊಂದಿಸಲಾಗಿದೆ. |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | ಇದು ಆಗಲಿಲ್ಲ. ನಿಮ್ಮ ಹಳೆಯ ಪಾಸ್‌ಫ್ರೇಸೇ ಇನ್ನೂ ಬಳಕೆಯಲ್ಲಿದೆ. |  |
| `pc_reset_title` | Set a new passphrase | ಹೊಸ ಪಾಸ್‌ಫ್ರೇಸ್ ಇಡಿ |  |
| `pc_title` | Do you still remember your passphrase? | ನಿಮ್ಮ ಪಾಸ್‌ಫ್ರೇಸ್ ಇನ್ನೂ ನೆನಪಿದೆಯೇ? |  |
| `pc_wrong` | That is not it. You can set a new one instead. | ಇದು ಅದಲ್ಲ. ಬದಲಿಗೆ ಹೊಸದನ್ನು ಇಡಬಹುದು. |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | ಈ ದಾಖಲೆಗಾಗಿ ನೆನಪಿಟ್ಟ %1$d ಮೌಲ್ಯಗಳು ಅಳಿಸಲ್ಪಡುತ್ತವೆ. ಇದನ್ನು ಮರಳಿ ತರಲಾಗದು. |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh ಆ ಲಾಗಿನ್ ಉಳಿಸಲಾಗಲಿಲ್ಲ. |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | ತುಂಬಲು Zerokosh ತೆರೆಯಿರಿ |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | ಈ ಒಂದೇ ಪಾಸ್‌ಫ್ರೇಸ್ ಎಲ್ಲವನ್ನೂ ಬೀಗ ಹಾಕಿ ಇಡುತ್ತದೆ. ನಿಮಗೆ ಮಾತ್ರ ಗೊತ್ತಿರುವ ಉದ್ದವಾದದ್ದನ್ನು ಆರಿಸಿ. |  |
| `scr_create_button` | Lock it in | ಬೀಗ ಹಾಕಿ |  |
| `scr_create_confirm_hint` | Type it again | ಮತ್ತೆ ಟೈಪ್ ಮಾಡಿ |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | ಪಾಸ್‌ಫ್ರೇಸ್ (ಕನಿಷ್ಠ 10 ಅಕ್ಷರ) |  |
| `scr_create_mismatch` | The two entries don\'t match | ಎರಡೂ ಒಂದೇ ಆಗಿಲ್ಲ |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 ಅಂಕಿಯ ಪಿನ್ |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | ಬದಲಿಗೆ 6 ಅಂಕಿಯ ಪಿನ್ ಇಡಿ |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | ಪಿನ್‌ಗೆ ಬೆರಳಚ್ಚು ಅಥವಾ ಮುಖ ಅನ್‌ಲಾಕ್ ಇರುವ ಫೋನ್ ಬೇಕು. ದಯವಿಟ್ಟು ಪಾಸ್‌ಫ್ರೇಸ್ ಆರಿಸಿ. |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | ಪಿನ್ ನಡೆಯುತ್ತದೆ ಏಕೆಂದರೆ ಈ ಫೋನ್ ಅದನ್ನು ತನ್ನ ಭದ್ರತಾ ಚಿಪ್‌ನಿಂದ ಮತ್ತು ನಿಮ್ಮ ಬೆರಳಚ್ಚು ಅಥವಾ ಮುಖದಿಂದ ಕಾಪಾಡುತ್ತದೆ. |  |
| `scr_create_strength_fair` | Fair | ಪರವಾಗಿಲ್ಲ |  |
| `scr_create_strength_good` | Good | ಒಳ್ಳೆಯದು |  |
| `scr_create_strength_strong` | Strong | ಬಲವಾದದ್ದು |  |
| `scr_create_strength_weak` | Weak | ದುರ್ಬಲ |  |
| `scr_create_title` | Create your passphrase | ನಿಮ್ಮ ಪಾಸ್‌ಫ್ರೇಸ್ ರಚಿಸಿ |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | ಕನಿಷ್ಠ 10 ಅಕ್ಷರ ಇರಲಿ — ಎಷ್ಟು ಉದ್ದವೋ ಅಷ್ಟು ಬಲ |  |
| `scr_create_working` | Preparing your vault… | ನಿಮ್ಮ ತಿಜೋರಿ ಸಿದ್ಧವಾಗುತ್ತಿದೆ… |  |
| `scr_detail_delete` | Delete | ಅಳಿಸಿ |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | ಅದು 30 ದಿನ “ಇತ್ತೀಚೆಗೆ ಅಳಿಸಿದವು”ನಲ್ಲಿ ಇರುತ್ತದೆ, ನಿಮ್ಮ ಬೇರೆ ಫೋನ್‌ಗಳ ಜೊತೆ ಸಿಂಕ್ ಆದ ಮೇಲೆ ಹೋಗುತ್ತದೆ. |  |
| `scr_detail_delete_confirm_title` | Delete this record? | ಈ ದಾಖಲೆಯನ್ನು ಅಳಿಸಬೇಕೇ? |  |
| `scr_detail_delete_confirm_yes` | Delete | ಅಳಿಸಿ |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | ಸೀಕ್ರೆಟ್ ಅಥವಾ otpauth:// ಲಿಂಕ್ ಅಂಟಿಸಿ |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | ನಿಮ್ಮ ಬೆರಳಚ್ಚು ಅಥವಾ ಮುಖ ಬಳಸಿ |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh ತೆರೆಯಿರಿ |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | ಬಹಳ ಸಲ ತಪ್ಪಾಗಿ ಪ್ರಯತ್ನಿಸಿದ್ದೀರಿ. %1$d ಸೆಕೆಂಡ್ ಕಾಯಿರಿ. |  |
| `scr_lock_hint` | Passphrase | ಪಾಸ್‌ಫ್ರೇಸ್ |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | ಈ ರಿಕವರಿ ಕೀ ಸರಿಯಿಲ್ಲ — ಒಂದೊಂದೇ ಅಕ್ಷರ ಹೋಲಿಸಿ ನೋಡಿ |  |
| `scr_lock_title` | Vault is locked | ತಿಜೋರಿಗೆ ಬೀಗ ಹಾಕಿದೆ |  |
| `scr_lock_unlock` | Unlock | ತೆರೆಯಿರಿ |  |
| `scr_lock_use_passphrase` | Use passphrase | ಪಾಸ್‌ಫ್ರೇಸ್ ಬಳಸಿ |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | ದಯವಿಟ್ಟು ಒಮ್ಮೆ ಪಾಸ್‌ಫ್ರೇಸ್‌ನಿಂದ ತೆರೆಯಿರಿ |  |
| `scr_lock_use_recovery` | Use Recovery Key | ರಿಕವರಿ ಕೀ ಬಳಸಿ |  |
| `scr_lock_wrong` | Wrong passphrase | ಪಾಸ್‌ಫ್ರೇಸ್ ತಪ್ಪಾಗಿದೆ |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | ಎಂದಾದರೂ ಪಾಸ್‌ಫ್ರೇಸ್ ಮರೆತರೆ, ಒಳಗೆ ಮರಳಲು ಇದೊಂದೇ ದಾರಿ. ನಾವು ಅದನ್ನು ರೀಸೆಟ್ ಮಾಡಲಾರೆವು — ಯಾರೂ ಮಾಡಲಾರರು. |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | ನಾನು ಅದನ್ನು ಬರೆದಿಟ್ಟು ಸುರಕ್ಷಿತ ಜಾಗದಲ್ಲಿ ಇಟ್ಟಿದ್ದೇನೆ |  |
| `scr_recovery_done` | Continue | ಮುಂದುವರಿಸಿ |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | ಈ ಕೀ ಒಮ್ಮೆ ಮಾತ್ರ ಕಾಣುತ್ತದೆ. ನೀವು ಇನ್ನೂ ತೆರೆಯಲು ಸಾಧ್ಯವಿರುವವರೆಗೆ, ಸೆಟ್ಟಿಂಗ್‌ಗಳಿಂದ ಯಾವಾಗ ಬೇಕಾದರೂ ಹೊಸದನ್ನು ಮಾಡಬಹುದು. |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | ಈ ಪುಟವನ್ನು ನಿಮ್ಮ ಆಸ್ತಿ ದಾಖಲೆಗಳ ಜೊತೆ ಅಥವಾ ಇತರ ಮುಖ್ಯ ದಾಖಲೆಗಳ ಜೊತೆ ಇಡಿ. ಈ ಕೀ ಯಾರ ಬಳಿ ಇರುತ್ತದೋ ಅವರು ನಿಮ್ಮ ತಿಜೋರಿ ತೆರೆಯಬಹುದು — ಅದನ್ನು ಲಾಕರ್ ಕೀಲಿಯಂತೆ ಕಾಪಾಡಿ. |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | ರಿಕವರಿ ಕಿಟ್ PDF ಉಳಿಸಲಾಗಿದೆ |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh ರಿಕವರಿ ಕಿಟ್ |  |
| `scr_recovery_save_pdf` | Save as PDF | PDF ಆಗಿ ಉಳಿಸಿ |  |
| `scr_recovery_title` | Your Recovery Key | ನಿಮ್ಮ ರಿಕವರಿ ಕೀ |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | ನೀವು ಉಳಿಸುವುದೆಲ್ಲ ನಿಮ್ಮ ಫೋನಿನ ಒಂದು ಬೀಗ ಹಾಕಿದ ಫೈಲಿನಲ್ಲಿ ಇರುತ್ತದೆ. ಅದು ಎಂದೂ ನಮ್ಮ ಬಳಿಗೆ ಬರುವುದಿಲ್ಲ — ಅದನ್ನು ಇಡಲು ನಮ್ಮ ಬಳಿ ಜಾಗವೇ ಇಲ್ಲ. |  |
| `scr_trust_card1_title` | Your data stays on this device | ನಿಮ್ಮ ಡೇಟಾ ಇದೇ ಫೋನಿನಲ್ಲಿ ಇರುತ್ತದೆ |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | Zerokosh ಖಾತೆ ಇಲ್ಲ, ಕ್ಲೌಡ್ ಇಲ್ಲ, ಸೈನ್-ಅಪ್ ಇಲ್ಲ. ಇದನ್ನು ನೀವು ಮಾತ್ರ ತೆರೆಯಬಹುದು. ನಾವೂ ಸಾಧ್ಯವಿಲ್ಲ. |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | ನಮಗೆ ಸರ್ವರ್‌ಗಳಿಲ್ಲ — ಹ್ಯಾಕ್ ಮಾಡಲೂ ಏನಿಲ್ಲ, ಮಾರಲೂ ಏನಿಲ್ಲ |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | ಚಂದಾ ಇಲ್ಲ, ಜಾಹೀರಾತು ಇಲ್ಲ. ಯಾರು ಬೇಕಾದರೂ ನಮ್ಮ ಕೋಡ್ ಓದಿ ನಮ್ಮ ಪ್ರತಿ ಮಾತನ್ನೂ ಪರಿಶೀಲಿಸಬಹುದು. |  |
| `scr_trust_card3_title` | Free forever, open source | ಯಾವಾಗಲೂ ಉಚಿತ, ಓಪನ್ ಸೋರ್ಸ್ |  |
| `scr_trust_continue` | Continue | ಮುಂದುವರಿಸಿ |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | ನಿಮ್ಮ ಬದಲಾವಣೆಗಳು ಉಳಿಯಲಿಲ್ಲ, ಹಾಗಾಗಿ ತಿಜೋರಿಯಲ್ಲಿ ಮೊದಲಿದ್ದದ್ದರಲ್ಲಿ ಏನೂ ಕಳೆದುಹೋಗಿಲ್ಲ. |  |
| `st_recently_deleted` | Recently deleted | ಇತ್ತೀಚೆಗೆ ಅಳಿಸಿದವು |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | ಒಮ್ಮೆ ಬಳಸುವ ಕೋಡ್ (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | ಒಮ್ಮೆ ಬಳಸುವ ಕೋಡ್ (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | ಒಮ್ಮೆ ಬಳಸುವ ಕೋಡ್ (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA ಸೀಕ್ರೆಟ್ |  |
| `tr_cannot_undo` | This cannot be undone. | ಇದನ್ನು ಮರಳಿ ತರಲಾಗದು. |  |
| `tr_delete_all` | Delete all permanently | ಎಲ್ಲವನ್ನೂ ಶಾಶ್ವತವಾಗಿ ಅಳಿಸಿ |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d ದಾಖಲೆಗಳು ಶಾಶ್ವತವಾಗಿ ಹೋಗುತ್ತವೆ. ಇದನ್ನು ಮರಳಿ ತರಲಾಗದು ಮತ್ತು ಮರುಸ್ಥಾಪಿಸಲು ಬ್ಯಾಕಪ್ ಇಲ್ಲ. |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d ದಾಖಲೆ ಶಾಶ್ವತವಾಗಿ ಹೋಗುತ್ತದೆ. ಇದನ್ನು ಮರಳಿ ತರಲಾಗದು ಮತ್ತು ಮರುಸ್ಥಾಪಿಸಲು ಬ್ಯಾಕಪ್ ಇಲ್ಲ. |  |
| `tr_delete_all_title` | Delete everything in the trash? | ಕಸದ ಬುಟ್ಟಿಯಲ್ಲಿರುವ ಎಲ್ಲವನ್ನೂ ಅಳಿಸಬೇಕೇ? |  |
| `tr_delete_now` | Delete now | ಈಗಲೇ ಅಳಿಸಿ |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | “%1$s” ಅನ್ನು ಶಾಶ್ವತವಾಗಿ ಅಳಿಸಬೇಕೇ? |  |
| `tr_empty` | Nothing deleted. | ಏನೂ ಅಳಿಸಿಲ್ಲ. |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | ಅಳಿಸಿದ ದಾಖಲೆಗಳು ಇಲ್ಲಿ %1$d ದಿನ ಇರುತ್ತವೆ. |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | ಭಾರತೀಯ ಬ್ಯಾಂಕುಗಳು, UPI, ಕಾರ್ಡ್, ಡೀಮ್ಯಾಟ್, EPF ಮತ್ತು ನೀವು ನಿಜವಾಗಿ ಬಳಸುವ OTP ಆಪ್‌ಗಳಿಗಾಗಿ ಫೋನಿನಲ್ಲೇ ಇರುವ ತಿಜೋರಿ. |  |

## Priority 2 — longer prose

| key | English | Kannada | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | ಹೆಚ್ಚು ಬಳಕೆಗೆ ಬರುವ ಒಂದೇ ಮಾಹಿತಿಯಿಂದ ಶುರು ಮಾಡಿ. ನೋಟ್ಸ್ ಆಪ್‌ನಲ್ಲಿ ಬಿದ್ದಿರುವ ಹನ್ನೆರಡು ಪಾಸ್‌ವರ್ಡ್‌ಗಿಂತ ಉಳಿಸಿದ ಒಂದು ಪಾಸ್‌ವರ್ಡ್ ಸುರಕ್ಷಿತ. |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | ಪಾಸ್‌ಫ್ರೇಸ್ ಮರೆತರೆ ಒಳಗೆ ಬರಲು ರಿಕವರಿ ಕಿಟ್ ಒಂದೇ ದಾರಿ. ನಿಮಗಾಗಿ ಇನ್ನೊಂದನ್ನು ಯಾರೂ ಮಾಡಲಾರರು. |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | ಆ ಫೈಲಿನಲ್ಲಿ ಗುರುತಿಸಬಹುದಾದದ್ದು ಏನೂ ಸಿಗಲಿಲ್ಲ. Chrome, Google Password Manager, Bitwarden, LastPass, KeePass ರಫ್ತುಗಳು ಅರ್ಥವಾಗುತ್ತವೆ. |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d ಇರುವ ದಾಖಲೆಗಳು ಬದಲಾಗುತ್ತವೆ — ಸೈಟ್ ಮತ್ತು ಬಳಕೆದಾರ ಹೆಸರಿನಿಂದ ಹೊಂದಿಸಿ. ಬದಲಾದ ಪಾಸ್‌ವರ್ಡ್‌ಗಳು ಪ್ರತಿ ದಾಖಲೆಯ ಇತಿಹಾಸದಲ್ಲಿ ಮರಳಿ ಸಿಗುತ್ತವೆ. |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d ಇರುವ ದಾಖಲೆ ಬದಲಾಗುತ್ತದೆ — ಸೈಟ್ ಮತ್ತು ಬಳಕೆದಾರ ಹೆಸರಿನಿಂದ ಹೊಂದಿಸಿ. ಬದಲಾದ ಪಾಸ್‌ವರ್ಡ್‌ಗಳು ಪ್ರತಿ ದಾಖಲೆಯ ಇತಿಹಾಸದಲ್ಲಿ ಮರಳಿ ಸಿಗುತ್ತವೆ. |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d ದಾಖಲೆಗಳು ಎರಡೂ ಕಡೆ ಬದಲಾಗಿದ್ದವು. ಎರಡೂ ರೂಪಗಳನ್ನು ಇಡಲಾಗಿದೆ — “(conflict copy)” ಹುಡುಕಿ. |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | ಈ ಬ್ಯಾಕಪ್ ಫೈಲ್ ತೆರೆಯುವ ಪಾಸ್‌ಫ್ರೇಸ್ ಹಾಕಿ. ಅದು ನಿಮ್ಮ ಈಗಿನದಕ್ಕಿಂತ ಬೇರೆ ಇರಬಹುದು. |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | .kosh ಫೈಲಿನ Poly1305 ದೃಢೀಕರಣ ಟ್ಯಾಗ್ ಹೊಂದಾಣಿಕೆಯಾಗುತ್ತಿಲ್ಲ. ಅರ್ಧದಲ್ಲಿ ನಿಂತ ಸಿಂಕ್ ಅಥವಾ ಕೆಟ್ಟ ಸ್ಟೋರೇಜ್ ನಂತರ ಹೀಗಾಗಬಹುದು. |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh ತಿಜೋರಿ ಫೈಲಿನ ಪಕ್ಕದಲ್ಲೇ ಒಂದು ಚಾಲ್ತಿಯ ಬ್ಯಾಕಪ್ ಇಡುತ್ತದೆ. ಅದನ್ನು ನಿಮ್ಮ ಸಿಂಕ್ ಫೋಲ್ಡರ್‌ನಿಂದ ಮರುಸ್ಥಾಪಿಸಿ, ಅಥವಾ ಬೇರೆ ಫೋನಿನಲ್ಲಿ ರಿಕವರಿ ಕಿಟ್‌ನಿಂದ ತಿಜೋರಿ ತೆರೆಯಿರಿ. |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | ಓದುವವರೆಗೆ ಕಾರ್ಡ್ ಅನ್ನು ಫೋನಿನ ಹಿಂಬದಿಗೆ ನೇರವಾಗಿ ಒತ್ತಿ ಹಿಡಿಯಿರಿ. ಇದರಿಂದ ಕಾರ್ಡ್ ನಂಬರ್, ಅವಧಿ, ಹೆಸರು ಸಿಗುತ್ತವೆ — CVV ಚಿಪ್‌ನಲ್ಲಿ ಇರುವುದಿಲ್ಲ, ಅದನ್ನು ನೀವೇ ಟೈಪ್ ಮಾಡಬೇಕು. |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | ಒಳಗೆ ಮರಳಲು ವೇಗದ ದಾರಿ ಆರಿಸಿ. ತಿಜೋರಿಯ ಕಾವಲು ಪಾಸ್‌ಫ್ರೇಸ್‌ದೇ; ಇದು ಈ ಫೋನಿನಲ್ಲಿ ಮಾತ್ರ ಕೀಲಿ ತೆರೆಯುತ್ತದೆ. |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | ಈಗ ನಿಮ್ಮ ಖಜಾನೆಯಲ್ಲಿ ನಿಜವಾದ ಮಾಹಿತಿ ಇದೆ. ರಿಕವರಿ ಕಿಟ್ ಇಲ್ಲದೆ ಪಾಸ್‌ಫ್ರೇಸ್ ಮರೆತರೆ ಯಾರೂ ನಿಮ್ಮನ್ನು ಮತ್ತೆ ಒಳಗೆ ಬಿಡಲಾರರು — ನಾವೂ ಸಹ. |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh ಉಚಿತ, ಓಪನ್ ಸೋರ್ಸ್, ಮತ್ತು ಯಾವುದೇ ಸರ್ವರ್ ಇಲ್ಲ. ನಿಮ್ಮ ತಿಜೋರಿಯನ್ನು ನೀವು ಮಾತ್ರ ತೆರೆಯಬಹುದು. ನಾವೂ ಸಾಧ್ಯವಿಲ್ಲ. |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | QR ಕೋಡ್ ಸ್ಕ್ಯಾನ್ ಮಾಡಲಷ್ಟೇ ಕ್ಯಾಮೆರಾ ಅನುಮತಿ ಬೇಕು. ದಾಖಲೆ ಸೇರಿಸುವಾಗ ಸೀಕ್ರೆಟ್ ಅನ್ನು ಕೈಯಿಂದಲೂ ಅಂಟಿಸಬಹುದು. |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | ಆಟೋಫಿಲ್ ಬಿಡದ ಬ್ಯಾಂಕ್ ಆಪ್‌ಗಳಿಗಾಗಿ — ಬಟನ್ ಒತ್ತಿ ಲಾಗಿನ್ ವಿವರಗಳನ್ನು ಒಂದೊಂದಾಗಿ ನಕಲಿಸಿ |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | ನಿಮ್ಮ ಫೋನ್ ತೆರೆಯುವಂತೆಯೇ ತಿಜೋರಿಯನ್ನೂ. ಪಾಸ್‌ಫ್ರೇಸ್ ಯಾವಾಗಲೂ ಕೆಲಸ ಮಾಡುತ್ತಿರುತ್ತದೆ. |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | ತಿಜೋರಿಯ ಸ್ಕ್ರೀನ್‌ಶಾಟ್‌ಗಳು ಕ್ಲೌಡ್ ಫೋಟೋ ಬ್ಯಾಕಪ್‌ಗೆ ಹೋಗಬಹುದು. ನಿಜವಾಗಿ ಬೇಕಿದ್ದರೆ ಮಾತ್ರ ಆನ್ ಮಾಡಿ. |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | ತಿಜೋರಿ ಫೈಲ್ ಬರೆಯಲಾಗಲಿಲ್ಲ. ನೀವು ಬ್ಯಾಕಪ್ ಮತ್ತು ಸಿಂಕ್ ಫೋಲ್ಡರ್ ಹೊಂದಿಸಿದ್ದರೆ, Android ಅದರ ಅನುಮತಿಯನ್ನು ಹಿಂಪಡೆದಿರಬಹುದು — ಸೆಟ್ಟಿಂಗ್ ತೆರೆದು, ಫೋಲ್ಡರ್ ಮತ್ತೆ ಆರಿಸಿ, ಮತ್ತೆ ಪ್ರಯತ್ನಿಸಿ. |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | ನಿಮ್ಮ ಎನ್‌ಕ್ರಿಪ್ಟ್ ಆದ .kosh ಫೈಲುಗಳು ನೇರವಾಗಿ ಇದೇ ಫೋಲ್ಡರ್‌ನಲ್ಲಿ ಉಳಿಯುತ್ತವೆ. ಹಲವು ಸಾಧನಗಳಲ್ಲಿ ತಾನಾಗಿ ಬ್ಯಾಕಪ್ ಆಗಲು ಈ ಫೋಲ್ಡರ್ ಅನ್ನು Google Drive, Syncthing, Nextcloud ಅಥವಾ SD ಕಾರ್ಡ್‌ನೊಂದಿಗೆ ಸಿಂಕ್ ಮಾಡಿ. |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | ಹೊಸ ರಿಕವರಿ ಕೀ ರಚಿಸಲು ನಿಮ್ಮ ಪಾಸ್‌ಫ್ರೇಸ್ ಹಾಕಿ. ಹಳೆಯ ಕೀ ಕೆಲಸ ಮಾಡುವುದು ನಿಲ್ಲುತ್ತದೆ. |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | ಈ ಪುಟದ ಉಳಿದೆಲ್ಲವೂ ಒಂದು ಲಾಗಿನ್ ದುರ್ಬಲಗೊಳಿಸುತ್ತದೆ. ಇದು ಇಡೀ ಖಜಾನೆಯನ್ನೇ ತೆಗೆದುಕೊಂಡು ಹೋಗಬಹುದು. ಸೆಟ್ಟಿಂಗ್‌ಗಳು → ಹೊಸ ರಿಕವರಿ ಕೀ ಪಡೆಯಿರಿ. |  |

## Priority 3 — short labels

| key | English | Kannada | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | ಬಲವಾದ ಪಾಸ್‌ವರ್ಡ್ ಬಳಸಿ |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | ಖಾತೆಯ ಹೆಸರು (ಉದಾ. Google) |  |
| `au_active_many` | %1$d active codes | %1$d ಚಾಲ್ತಿಯ ಕೋಡ್‌ಗಳು |  |
| `au_active_one` | %1$d active code | %1$d ಚಾಲ್ತಿಯ ಕೋಡ್ |  |
| `au_add_another` | Add another authenticator | ಇನ್ನೊಂದು ಆಥೆಂಟಿಕೇಟರ್ ಸೇರಿಸಿ |  |
| `au_add_secret` | Add Secret Key | ಸೀಕ್ರೆಟ್ ಕೀ ಸೇರಿಸಿ |  |
| `au_camera_needed` | Camera permission needed to scan QR code | QR ಕೋಡ್ ಸ್ಕ್ಯಾನ್ ಮಾಡಲು ಕ್ಯಾಮೆರಾ ಅನುಮತಿ ಬೇಕು |  |
| `au_copied` | Copied · clears shortly | ನಕಲಾಗಿದೆ · ಸ್ವಲ್ಪ ಹೊತ್ತಿನಲ್ಲಿ ಅಳಿಸಿಹೋಗುತ್ತದೆ |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google, GitHub ಅಥವಾ ನಿಮ್ಮ ಬ್ರೋಕರ್ QR ಸ್ಕ್ಯಾನ್ ಮಾಡಿ, ಅಥವಾ ಸೀಕ್ರೆಟ್ ಕೀ ಕೈಯಿಂದ ಟೈಪ್ ಮಾಡಿ. |  |
| `au_enter_key` | Enter Key | ಕೀ ಟೈಪ್ ಮಾಡಿ |  |
| `au_fallback_name` | Authenticator | ಆಥೆಂಟಿಕೇಟರ್ |  |
| `au_flashlight` | Flashlight | ಟಾರ್ಚ್ |  |
| `au_grant` | Grant Permission | ಅನುಮತಿ ಕೊಡಿ |  |
| `au_image_failed` | Failed to process image | ಚಿತ್ರ ಓದಲಾಗಲಿಲ್ಲ |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | ತಪ್ಪಾದ Base32 ಸೀಕ್ರೆಟ್ ಕೀ (A-Z ಅಕ್ಷರ ಮತ್ತು 2-7 ಅಂಕಿ ಮಾತ್ರ) |  |
| `au_no_match` | No codes match | ಯಾವ ಕೋಡ್ ಸಿಗಲಿಲ್ಲ |  |
| `au_none_yet` | No codes yet. | ಇನ್ನೂ ಕೋಡ್ ಇಲ್ಲ. |  |
| `au_pick_image` | Pick Image | ಚಿತ್ರ ಆರಿಸಿ |  |
| `au_rotating` | "Rotating " | "ಬದಲಾಗುತ್ತಿರುವ " |  |
| `au_rotating_emph` | codes. | ಕೋಡ್‌ಗಳು. |  |
| `au_save_key` | Save Key | ಕೀ ಉಳಿಸಿ |  |
| `au_scan_qr` | Scan a QR code | QR ಕೋಡ್ ಸ್ಕ್ಯಾನ್ ಮಾಡಿ |  |
| `au_scan_title` | Scan Authenticator QR | ಆಥೆಂಟಿಕೇಟರ್ QR ಸ್ಕ್ಯಾನ್ ಮಾಡಿ |  |
| `au_search_hint` | Search codes, issuers… | ಕೋಡ್, ನೀಡಿದವರನ್ನು ಹುಡುಕಿ… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | ಉದಾ. JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | ಸೀಕ್ರೆಟ್ ಕೀ (Base32) |  |
| `au_tap_to_copy` | Tap to copy | ನಕಲಿಸಲು ಟ್ಯಾಪ್ ಮಾಡಿ |  |
| `cat_apps` | Apps &amp; Logins | ಆಪ್ ಮತ್ತು ಲಾಗಿನ್ |  |
| `cat_banks` | Banks &amp; UPI | ಬ್ಯಾಂಕುಗಳು ಮತ್ತು UPI |  |
| `cat_cards` | Cards | ಕಾರ್ಡ್‌ಗಳು |  |
| `cat_govid` | Gov &amp; ID | ಸರ್ಕಾರಿ ಮತ್ತು ಗುರುತು |  |
| `cat_investments` | Investments | ಹೂಡಿಕೆಗಳು |  |
| `cat_utilities` | Utilities | ಬಿಲ್ ಮತ್ತು ಸಂಪರ್ಕ |  |
| `cd_mask_hidden` | hidden | ಮರೆಯಾಗಿದೆ |  |
| `cd_shield_high_sensitivity` | extra-protected field | ಹೆಚ್ಚುವರಿ ರಕ್ಷಿತ ಮಾಹಿತಿ |  |
| `gl_blank` | Blank template | ಖಾಲಿ ಟೆಂಪ್ಲೇಟ್ |  |
| `gl_cat_apps` | Apps | ಆಪ್ |  |
| `gl_cat_banks` | Banks | ಬ್ಯಾಂಕುಗಳು |  |
| `gl_cat_cards` | Cards | ಕಾರ್ಡ್ |  |
| `gl_cat_demat` | Demat | ಡೀಮ್ಯಾಟ್ |  |
| `gl_cat_govid` | Gov ID | ಸರ್ಕಾರಿ ಗುರುತು |  |
| `gl_cat_popular` | Popular | ಜನಪ್ರಿಯ |  |
| `gl_cat_shopping` | Shopping | ಶಾಪಿಂಗ್ |  |
| `gl_cat_travel` | Travel | ಪ್ರಯಾಣ |  |
| `gl_cat_upi` | UPI | UPI |  |
| `gl_cat_utilities` | Utilities | ಬಿಲ್ |  |
| `gl_head_emph` | storing? | ಉಳಿಸುತ್ತಿದ್ದೇವೆ? |  |
| `gl_head_lead` | "What are we " | "ನಾವು ಏನನ್ನು " |  |
| `gl_matches` | %1$d matches | %1$d ಸಿಕ್ಕಿವೆ |  |
| `gl_most_used` | Most-used first | ಹೆಚ್ಚು ಬಳಸಿದವು ಮೊದಲು |  |
| `gl_not_found` | Can’t find a service? | ಸೇವೆ ಸಿಗುತ್ತಿಲ್ಲವೇ? |  |
| `gl_search` | Search %1$d Indian services… | %1$d ಭಾರತೀಯ ಸೇವೆಗಳಲ್ಲಿ ಹುಡುಕಿ… |  |
| `gl_suggested` | Suggested for you | ನಿಮಗಾಗಿ ಸಲಹೆ |  |
| `hm_add_first` | Add your first record | ನಿಮ್ಮ ಮೊದಲ ದಾಖಲೆ ಸೇರಿಸಿ |  |
| `hm_all_offline` | all offline. | ಎಲ್ಲವೂ ಆಫ್‌ಲೈನ್. |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d ಮಾಹಿತಿಗಳು, " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d ಮಾಹಿತಿ, " |  |
| `hm_detail_placeholder` | Pick a record to see it here | ಇಲ್ಲಿ ನೋಡಲು ಒಂದು ದಾಖಲೆ ಆರಿಸಿ |  |
| `hm_empty_blank` | A blank vault, ready. | ಖಾಲಿ ತಿಜೋರಿ, ಸಿದ್ಧವಿದೆ. |  |
| `hm_empty_head_emph` | waiting. | ಕಾಯುತ್ತಿದೆ. |  |
| `hm_empty_head_lead` | "Your vault is " | "ನಿಮ್ಮ ತಿಜೋರಿ " |  |
| `hm_filter_all` | All | ಎಲ್ಲಾ |  |
| `hm_import_backup` | Import an encrypted backup | ಎನ್‌ಕ್ರಿಪ್ಟ್ ಆದ ಬ್ಯಾಕಪ್ ಆಮದು |  |
| `hm_import_backup_note` | Open a .kosh file from this device | ಇದೇ ಫೋನಿನಿಂದ .kosh ಫೈಲ್ ತೆರೆಯಿರಿ |  |
| `hm_inst_many` | %1$d institutions | %1$d ಸಂಸ್ಥೆಗಳು |  |
| `hm_inst_one` | %1$d institution | %1$d ಸಂಸ್ಥೆ |  |
| `hm_kit_banner_action` | Save one now | ಈಗಲೇ ಉಳಿಸಿ |  |
| `hm_kit_banner_dismiss` | Remind me later | ನಂತರ ನೆನಪಿಸಿ |  |
| `hm_kit_banner_title` | No recovery kit saved | ಯಾವುದೇ ರಿಕವರಿ ಕಿಟ್ ಉಳಿಸಿಲ್ಲ |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | ತೆರೆದಿದೆ · ಬಿಟ್ಟ ಕೂಡಲೇ ಬೀಗ |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | ತೆರೆದಿದೆ · ಬಿಟ್ಟ %1$d ನಿಮಿಷಕ್ಕೆ ಬೀಗ |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | ತೆರೆದಿದೆ · ಬಿಟ್ಟ 1 ನಿಮಿಷಕ್ಕೆ ಬೀಗ |  |
| `hm_no_match` | Nothing matches “%1$s” | “%1$s” ಗೆ ಏನೂ ಸಿಗಲಿಲ್ಲ |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | ಒಂದು ಸಂಸ್ಥೆ, UPI ಹ್ಯಾಂಡಲ್, ಅಥವಾ ಕೊನೆಯ ನಾಲ್ಕು ಅಂಕಿ ಪ್ರಯತ್ನಿಸಿ. |  |
| `hm_pinned` | Pinned | ಪಿನ್ ಮಾಡಿದವು |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC, priya@upi, PAN… ಹುಡುಕಿ |  |
| `hm_start_template` | Start with a template | ಟೆಂಪ್ಲೇಟ್‌ನಿಂದ ಶುರು ಮಾಡಿ |  |
| `ic_could_not` | Could not import | ಆಮದು ಆಗಲಿಲ್ಲ |  |
| `ic_done` | Done | ಆಯಿತು |  |
| `ic_import` | Import | ಆಮದು |  |
| `ic_imported` | Imported | ಆಮದಾಯಿತು |  |
| `ic_importing` | Importing… | ಆಮದಾಗುತ್ತಿದೆ… |  |
| `ic_new_many` | %1$d new logins. | %1$d ಹೊಸ ಲಾಗಿನ್‌ಗಳು. |  |
| `ic_new_one` | %1$d new login. | %1$d ಹೊಸ ಲಾಗಿನ್. |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d ಸೇರಿಸಲಾಗಿದೆ, %2$d ಬದಲಾಗಿದೆ. |  |
| `ic_title` | Import from %1$s? | %1$s ನಿಂದ ಆಮದು ಮಾಡಬೇಕೇ? |  |
| `ic_too_large` | That file is too large to be a credential export. | ಈ ಫೈಲ್ ಪಾಸ್‌ವರ್ಡ್ ರಫ್ತಾಗಿರಲು ಸಾಧ್ಯವಾಗದಷ್ಟು ದೊಡ್ಡದು. |  |
| `import_action` | Import | ಆಮದು |  |
| `import_locked` | Unlock your vault before importing. | ಆಮದು ಮಾಡುವ ಮೊದಲು ನಿಮ್ಮ ತಿಜೋರಿ ತೆರೆಯಿರಿ. |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d ಸೇರಿಸಲಾಗಿದೆ, %2$d ಬದಲಾಗಿದೆ. ಏನೂ ಅಳಿಸಲಾಗಿಲ್ಲ. |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | ಆ ಫೈಲ್ ಅನ್ನು Zerokosh ತಿಜೋರಿಯಾಗಿ ಓದಲಾಗಲಿಲ್ಲ. |  |
| `import_nothing_new` | Everything in that backup was already here. | ಆ ಬ್ಯಾಕಪ್‌ನಲ್ಲಿದ್ದದ್ದೆಲ್ಲ ಈಗಾಗಲೇ ಇಲ್ಲಿತ್ತು. |  |
| `import_passphrase_label` | Backup passphrase | ಬ್ಯಾಕಪ್ ಪಾಸ್‌ಫ್ರೇಸ್ |  |
| `import_title` | Import a backup | ಬ್ಯಾಕಪ್ ಆಮದು ಮಾಡಿ |  |
| `kicker_locked` | Locked | ಬೀಗ ಹಾಕಿದೆ |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · ಏನೂ ಈ ಫೋನಿನಿಂದ ಹೊರಗೆ ಹೋಗಿಲ್ಲ |  |
| `lk_touch_unlock` | Touch to unlock | ತೆರೆಯಲು ಮುಟ್ಟಿ |  |
| `lk_welcome_emph` | Your vault is sealed. | ನಿಮ್ಮ ತಿಜೋರಿಗೆ ಬೀಗ ಹಾಕಿದೆ. |  |
| `lk_welcome_lead` | Welcome back. | ಮತ್ತೆ ಸ್ವಾಗತ. |  |
| `msg_auth_needed` | Confirm it\'s you to see this | ನೋಡಲು ನೀವೇ ಎಂದು ಖಚಿತಪಡಿಸಿ |  |
| `msg_back` | Back | ಹಿಂದೆ |  |
| `msg_cancel` | Cancel | ರದ್ದು |  |
| `msg_file_damaged` | File damaged — restored from backup | ಫೈಲ್ ಹಾಳಾಗಿತ್ತು — ಬ್ಯಾಕಪ್‌ನಿಂದ ಸರಿಪಡಿಸಲಾಗಿದೆ |  |
| `msg_ok` | OK | ಸರಿ |  |
| `msg_saved` | Saved | ಉಳಿಸಲಾಗಿದೆ |  |
| `nav_all_templates` | All templates | ಎಲ್ಲಾ ಟೆಂಪ್ಲೇಟ್ |  |
| `nav_damaged_emph` | vault file | ತಿಜೋರಿ ಫೈಲಿನಲ್ಲಿ |  |
| `nav_damaged_kicker` | Damaged state | ಹಾಳಾದ ಸ್ಥಿತಿ |  |
| `nav_damaged_lead` | "Something in the " | "ನಿಮ್ಮ " |  |
| `nav_damaged_tail` | " is off." | " ಏನೋ ತೊಂದರೆ ಇದೆ." |  |
| `nav_integrity_title` | Integrity check failed | ಸಮಗ್ರತೆ ಪರಿಶೀಲನೆ ವಿಫಲ |  |
| `nav_scan` | Scan | ಸ್ಕ್ಯಾನ್ |  |
| `nav_tap_card` | Tap a card | ಕಾರ್ಡ್ ಟ್ಯಾಪ್ ಮಾಡಿ |  |
| `nav_what_next` | What to do next | ಈಗ ಏನು ಮಾಡಬೇಕು |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC ಆಫ್ ಆಗಿದೆ. ಸೆಟ್ಟಿಂಗ್‌ನಲ್ಲಿ ಆನ್ ಮಾಡಿ ಮತ್ತೆ ಪ್ರಯತ್ನಿಸಿ. |  |
| `nfc_hold_card` | Hold your card to the phone | ಕಾರ್ಡ್ ಅನ್ನು ಫೋನಿಗೆ ಹಿಡಿಯಿರಿ |  |
| `nfc_missed` | Did not catch that | ಸಿಗಲಿಲ್ಲ |  |
| `nfc_read_failed` | That card could not be read. Try again. | ಆ ಕಾರ್ಡ್ ಓದಲಾಗಲಿಲ್ಲ. ಮತ್ತೆ ಪ್ರಯತ್ನಿಸಿ. |  |
| `nfc_reading` | Reading… | ಓದಲಾಗುತ್ತಿದೆ… |  |
| `nfc_try_again` | Try again | ಮತ್ತೆ ಪ್ರಯತ್ನಿಸಿ |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · ಇದೇ ಫೋನಿನಲ್ಲಿ ಅಳೆದದ್ದು |  |
| `ob_argon_faster` | Faster unlock | ಬೇಗ ತೆರೆಯುತ್ತದೆ |  |
| `ob_argon_harder` | Harder to attack | ಒಡೆಯಲು ಕಷ್ಟ |  |
| `ob_argon_measuring` | Measuring this device… | ಈ ಫೋನ್ ಅಳೆಯಲಾಗುತ್ತಿದೆ… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id ಕಠಿಣತೆ |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | ನಿಘಂಟಿನ ಒಂದೇ ಒಂದು ಪದವೂ ಅಲ್ಲ |  |
| `ob_check_pass_length` | 10 characters or more | 10 ಅಥವಾ ಹೆಚ್ಚು ಅಕ್ಷರ |  |
| `ob_check_pass_reuse` | Not reused from another app | ಬೇರೆ ಆಪ್‌ನಿಂದ ಮತ್ತೆ ಬಳಸಿದ್ದಲ್ಲ |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | ಹುಟ್ಟುಹಬ್ಬವೂ ಅಲ್ಲ, ವಾರ್ಷಿಕೋತ್ಸವವೂ ಅಲ್ಲ |  |
| `ob_check_pin_digits` | All six digits entered | ಆರೂ ಅಂಕಿ ತುಂಬಿದೆ |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | ಸಾಲಾಗಿ ಬರುವ ಅಂಕಿಯೂ ಅಲ್ಲ, ಪುನರಾವರ್ತನೆಯೂ ಅಲ್ಲ |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~ಒಡೆಯಲು %1$d ಶತಮಾನ |  |
| `ob_crack_days` | ~%1$d days to crack | ~ಒಡೆಯಲು %1$d ದಿನ |  |
| `ob_crack_forever` | longer than the sun | ಸೂರ್ಯನಿಗಿಂತಲೂ ಹೆಚ್ಚು ಕಾಲ |  |
| `ob_crack_hours` | ~hours to crack | ~ಒಡೆಯಲು ಕೆಲವು ಗಂಟೆ |  |
| `ob_crack_seconds` | ~seconds to crack | ~ಒಡೆಯಲು ಕೆಲವು ಸೆಕೆಂಡ್ |  |
| `ob_crack_years` | ~%1$d years to crack | ~ಒಡೆಯಲು %1$d ವರ್ಷ |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | ದೃಢೀಕೃತ, ಪ್ರತಿ ತಿಜೋರಿಗೂ ಬೇರೆ ನಾನ್ಸ್ |  |
| `ob_fact_encryption_title` | Encryption | ಎನ್‌ಕ್ರಿಪ್ಷನ್ |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | ಸೆಟಪ್ ಸಮಯದಲ್ಲಿ ನಿಮ್ಮ ಫೋನಿನಲ್ಲಿ ಅಳೆದದ್ದು |  |
| `ob_fact_kdf_title` | Key stretching | ಕೀ ಸ್ಟ್ರೆಚಿಂಗ್ |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | ರೀಸೆಟ್ ಲಿಂಕ್ ಇಲ್ಲ. ಸಪೋರ್ಟ್ ಹಿಂಬಾಗಿಲೂ ಇಲ್ಲ. |  |
| `ob_fact_lost_value` | Nobody can recover it | ಯಾರೂ ಮರಳಿ ತರಲಾರರು |  |
| `ob_fact_network_title` | Network permission | ನೆಟ್‌ವರ್ಕ್ ಅನುಮತಿ |  |
| `ob_fact_network_value` | Not requested | ಕೇಳಿಯೇ ಇಲ್ಲ |  |
| `ob_fact_quick_title` | Quick unlock | ತ್ವರಿತ ಅನ್‌ಲಾಕ್ |  |
| `ob_fact_quick_value` | Hardware keystore | ಹಾರ್ಡ್‌ವೇರ್ ಕೀಸ್ಟೋರ್ |  |
| `ob_lang_continue` | Continue in %1$s | %1$s ನಲ್ಲಿ ಮುಂದುವರಿಸಿ |  |
| `ob_lang_head_emph` | language. | ಭಾಷೆಯನ್ನು ಆರಿಸಿ. |  |
| `ob_lang_head_lead` | "Choose your " | "ನಿಮ್ಮ " |  |
| `ob_lang_search` | Search %1$d languages | %1$d ಭಾಷೆಗಳಲ್ಲಿ ಹುಡುಕಿ |  |
| `ob_quick_continue_pass` | Continue with passphrase | ಪಾಸ್‌ಫ್ರೇಸ್‌ನೊಂದಿಗೆ ಮುಂದುವರಿಸಿ |  |
| `ob_quick_enable` | Enable quick unlock | ತ್ವರಿತ ಅನ್‌ಲಾಕ್ ಆನ್ ಮಾಡಿ |  |
| `ob_quick_fingerprint` | Fingerprint | ಬೆರಳಚ್ಚು |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | ವೇಗದ, ಹಾರ್ಡ್‌ವೇರ್ ರಕ್ಷಿತ ಅನ್‌ಲಾಕ್. |  |
| `ob_quick_head_emph` | Without the cloud. | ಕ್ಲೌಡ್ ಇಲ್ಲದೆ. |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "ಒಂದೇ ಸ್ಪರ್ಶಕ್ಕೆ ತೆರೆಯುತ್ತದೆ. " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox. ಯಾವುದೇ ಬಯೋಮೆಟ್ರಿಕ್ ಮಾಹಿತಿ Zerokosh ವರೆಗೆ ಎಂದೂ ತಲುಪುವುದಿಲ್ಲ. |  |
| `ob_quick_hw_title` | Hardware-backed | ಹಾರ್ಡ್‌ವೇರ್ ರಕ್ಷಿತ |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | ಈ ಫೋನಿನಲ್ಲಿ ಹಾರ್ಡ್‌ವೇರ್ ಸೆನ್ಸರ್ ಇಲ್ಲ. |  |
| `ob_quick_opening` | Opening your vault… | ನಿಮ್ಮ ತಿಜೋರಿ ತೆರೆಯುತ್ತಿದೆ… |  |
| `ob_quick_pass_only` | Passphrase only | ಪಾಸ್‌ಫ್ರೇಸ್ ಮಾತ್ರ |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | ಪ್ರತಿ ಸಲ ಟೈಪ್ ಮಾಡಿ. ಅತ್ಯಂತ ಸುರಕ್ಷಿತ. |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | ತ್ವರಿತ ಅನ್‌ಲಾಕ್ ಹೊಂದಿಸಲಾಗಲಿಲ್ಲ. ಮತ್ತೆ ಪ್ರಯತ್ನಿಸಿ, ಅಥವಾ ಪಾಸ್‌ಫ್ರೇಸ್‌ನೊಂದಿಗೇ ಮುಂದುವರಿಯಿರಿ. |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | ಈಗ ಬೇಡ — ನಾನು ಪಾಸ್‌ಫ್ರೇಸ್ ಟೈಪ್ ಮಾಡುತ್ತೇನೆ |  |
| `ob_quick_touch_title` | Touch the sensor | ಸೆನ್ಸರ್ ಮುಟ್ಟಿ |  |
| `ob_recommended` | Recommended | ಶಿಫಾರಸು |  |
| `ob_reveal_hide` | Hide | ಮರೆಮಾಡಿ |  |
| `ob_reveal_show` | Show | ತೋರಿಸಿ |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | ಸುತ್ತುವ ಕೀಲಿ ಹಾರ್ಡ್‌ವೇರ್ ಕೀಸ್ಟೋರ್‌ನಲ್ಲಿ ಇರುತ್ತದೆ. ಬಯೋಮೆಟ್ರಿಕ್ ಮುಂದಿನ ಹಂತದಲ್ಲಿ. |  |
| `ob_seal_title` | Seal to this device | ಇದೇ ಫೋನಿಗೆ ಕಟ್ಟಿಬಿಡಿ |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | ಈ ಫೋನಿನಲ್ಲಿ ಹಾರ್ಡ್‌ವೇರ್ ಬಯೋಮೆಟ್ರಿಕ್ ಇಲ್ಲ. |  |
| `ob_soon` | SOON | ಶೀಘ್ರದಲ್ಲಿ |  |
| `ob_step_label` | Step %1$d of 6 | ಹಂತ %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | ನೀವು ಮಾತ್ರ ಹೇಳುವಂಥದ್ದನ್ನು ಆರಿಸಿ |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | ಒಳ್ಳೆಯದು · %1$d ಬಿಟ್ ಎಂಟ್ರೋಪಿ |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | ತುಂಬಾ ಚಿಕ್ಕದು · 10 ಅಕ್ಷರ ಬೇಕು |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | ಬಲವಾದದ್ದು · %1$d ಬಿಟ್ ಎಂಟ್ರೋಪಿ |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | ದುರ್ಬಲ · %1$d ಬಿಟ್ ಎಂಟ್ರೋಪಿ |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | ನಿಮ್ಮ ಬದುಕನ್ನು ನೋಡಿ ಯಾರೂ ಊಹಿಸಲಾಗದ ಆರು ಅಂಕಿ |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | ಪರವಾಗಿಲ್ಲ · %1$d ಬಿಟ್ — ಪಿನ್ ಇದಕ್ಕಿಂತ ಬಲವಾಗಲು ಸಾಧ್ಯವಿಲ್ಲ |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | ತುಂಬಾ ಚಿಕ್ಕದು · 6 ಅಂಕಿ ಬೇಕು |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | ದುರ್ಬಲ · ಇದೇ ಪಿನ್‌ಗಳನ್ನು ಮೊದಲು ಪ್ರಯತ್ನಿಸುತ್ತಾರೆ |  |
| `ob_try_label` | TRY | ಪ್ರಯತ್ನಿಸಿ |  |
| `qa_aadhaar` | Aadhaar | ಆಧಾರ್ |  |
| `qa_bank_account` | Bank account | ಬ್ಯಾಂಕ್ ಖಾತೆ |  |
| `qa_pan` | PAN | PAN |  |
| `qa_upi_id` | UPI ID | UPI ID |  |
| `rd_copied` | Copied | ನಕಲಾಗಿದೆ |  |
| `rd_forget` | Forget | ಮರೆಯಿರಿ |  |
| `rd_forget_these` | Forget these | ಇವನ್ನು ಮರೆತುಬಿಡಿ |  |
| `rd_forget_title` | Forget previous passwords? | ಹಿಂದಿನ ಪಾಸ್‌ವರ್ಡ್‌ಗಳನ್ನು ಮರೆಯಬೇಕೇ? |  |
| `rd_history_hide` | Hide | ಮರೆಮಾಡಿ |  |
| `rd_history_show` | Show %1$d | %1$d ತೋರಿಸಿ |  |
| `rd_hold_to_reveal` | Hold to reveal | ನೋಡಲು ಒತ್ತಿ ಹಿಡಿಯಿರಿ |  |
| `rd_last_edit` | last edit %1$s | ಕೊನೆಯ ಬದಲಾವಣೆ %1$s |  |
| `rd_release_to_hide` | Release to hide | ಮರೆಮಾಡಲು ಬಿಡಿ |  |
| `re_add_field` | + Add another field | + ಇನ್ನೊಂದು ಫೀಲ್ಡ್ ಸೇರಿಸಿ |  |
| `re_add_field_title` | Add a field | ಫೀಲ್ಡ್ ಸೇರಿಸಿ |  |
| `re_field_name` | Field name | ಫೀಲ್ಡ್ ಹೆಸರು |  |
| `re_pick_date` | Pick a date | ದಿನಾಂಕ ಆರಿಸಿ |  |
| `re_remove` | Remove | ತೆಗೆದುಹಾಕಿ |  |
| `re_tap_card` | Read the card by tapping it | ಕಾರ್ಡ್ ಟ್ಯಾಪ್ ಮಾಡಿ ಓದಿ |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | ರಹಸ್ಯವೆಂದು ಪರಿಗಣಿಸಿ (ಮರೆಯಾಗಿರುತ್ತದೆ, ನೋಡಲು ಒತ್ತಿ ಹಿಡಿಯಿರಿ) |  |
| `re_using_template` | using the %1$s template | %1$s ಟೆಂಪ್ಲೇಟ್ ಬಳಸಿ |  |
| `rem_kit_title` | No recovery kit saved | ಯಾವುದೇ ರಿಕವರಿ ಕಿಟ್ ಉಳಿಸಿಲ್ಲ |  |
| `scr_about_license` | License: GPL-3.0 — free forever | ಪರವಾನಗಿ: GPL-3.0 — ಯಾವಾಗಲೂ ಉಚಿತ |  |
| `scr_about_source` | Source code | ಸೋರ್ಸ್ ಕೋಡ್ |  |
| `scr_about_version` | Version %1$s | ಆವೃತ್ತಿ %1$s |  |
| `scr_auth_add_qr` | Add via QR | QR ಮೂಲಕ ಸೇರಿಸಿ |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | ನಿಮ್ಮ ಆಪ್ ಮತ್ತು ಬ್ರೋಕರ್ ಕೋಡ್‌ಗಳು ಇಲ್ಲಿ ಕಾಣಿಸುತ್ತವೆ |  |
| `scr_auth_scan_title` | Point the camera at the QR code | ಕ್ಯಾಮೆರಾವನ್ನು QR ಕೋಡ್ ಮೇಲೆ ಇಡಿ |  |
| `scr_detail_copied` | Copied · clears in 30s | ನಕಲಾಗಿದೆ · 30 ಸೆಕೆಂಡಿನಲ್ಲಿ ಅಳಿಸಿಹೋಗುತ್ತದೆ |  |
| `scr_detail_copy` | Copy | ನಕಲಿಸಿ |  |
| `scr_detail_edit` | Edit | ಬದಲಿಸಿ |  |
| `scr_detail_favorite` | Favourite | ಇಷ್ಟದವು |  |
| `scr_detail_hidden` | Hidden | ಮರೆಯಾಗಿದೆ |  |
| `scr_detail_hide` | Hide | ಮರೆಮಾಡಿ |  |
| `scr_detail_history_empty` | Nothing replaced yet. | ಇನ್ನೂ ಏನೂ ಬದಲಾಗಿಲ್ಲ. |  |
| `scr_detail_history_title` | Previous passwords | ಹಿಂದಿನ ಪಾಸ್‌ವರ್ಡ್‌ಗಳು |  |
| `scr_detail_reveal` | Show | ತೋರಿಸಿ |  |
| `scr_detail_shown` | Shown | ಕಾಣಿಸುತ್ತಿದೆ |  |
| `scr_edit_cancel` | Cancel | ರದ್ದು |  |
| `scr_edit_generate` | Generate | ರಚಿಸಿ |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | ಬ್ಯಾಂಕ್ / ಕಂಪನಿ (ಗುಂಪು ಮಾಡಲು) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | ಇದು ಸರಿ ಅನಿಸುತ್ತಿಲ್ಲ — ಒಮ್ಮೆ ನೋಡಿ |  |
| `scr_edit_link_none` | None | ಯಾವುದೂ ಇಲ್ಲ |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | ಈ ಕಾರ್ಡ್ ನಂಬರ್ ಸಾಮಾನ್ಯ ಪರಿಶೀಲನೆಯಲ್ಲಿ ಪಾಸಾಗಿಲ್ಲ — ಸರಿಯಿದ್ದರೆ ಉಳಿಸಿಬಿಡಿ |  |
| `scr_edit_month` | Month | ತಿಂಗಳು |  |
| `scr_edit_picker_other` | Other… | ಇತರೆ… |  |
| `scr_edit_picker_other_hint` | Type your own | ನಿಮ್ಮದನ್ನು ಬರೆಯಿರಿ |  |
| `scr_edit_required_title` | Give it a name first | ಮೊದಲು ಇದಕ್ಕೊಂದು ಹೆಸರು ಕೊಡಿ |  |
| `scr_edit_save` | Save | ಉಳಿಸಿ |  |
| `scr_edit_title_hint` | Title | ಹೆಸರು |  |
| `scr_edit_title_new` | New | ಹೊಸದು |  |
| `scr_edit_year` | Year | ವರ್ಷ |  |
| `scr_gallery_quick_add` | Quick add | ಬೇಗ ಸೇರಿಸಿ |  |
| `scr_gallery_title` | What do you want to save? | ನೀವು ಏನನ್ನು ಉಳಿಸಲು ಬಯಸುತ್ತೀರಿ? |  |
| `scr_home_add` | Add | ಸೇರಿಸಿ |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | ನಿಮ್ಮ ಬ್ಯಾಂಕ್ ಖಾತೆ ಹೀಗೆ ಕಾಣಿಸುತ್ತದೆ |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | ನಿಮ್ಮ ಕಾರ್ಡ್‌ಗಳು, UPI, ಆಪ್ ಲಾಗಿನ್‌ಗಳೂ ಇಲ್ಲೇ ಇರುತ್ತವೆ |  |
| `scr_home_group_other` | Other | ಇತರೆ |  |
| `scr_home_no_results` | Nothing matches your search | ನಿಮ್ಮ ಹುಡುಕಾಟಕ್ಕೆ ಏನೂ ಸಿಗಲಿಲ್ಲ |  |
| `scr_home_search_hint` | Search your vault | ನಿಮ್ಮ ತಿಜೋರಿಯಲ್ಲಿ ಹುಡುಕಿ |  |
| `scr_home_tab_authenticator` | Authenticator | ಕೋಡ್‌ಗಳು |  |
| `scr_home_tab_home` | Home | ಮುಖಪುಟ |  |
| `scr_home_tab_settings` | Settings | ಸೆಟ್ಟಿಂಗ್‌ಗಳು |  |
| `scr_home_title` | Home | ಮುಖಪುಟ |  |
| `scr_language_continue` | Continue | ಮುಂದುವರಿಸಿ |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | ನಿಮ್ಮ ಭಾಷೆಯನ್ನು ಆರಿಸಿ |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | ನಕಲಿಸಲು ಬಟನ್ ಒತ್ತಿ · 30 ಸೆಕೆಂಡಿನಲ್ಲಿ ಅಳಿಸಿಹೋಗುತ್ತದೆ |  |
| `scr_login_helper_channel` | Login helper | ಲಾಗಿನ್ ಸಹಾಯಕ |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s ನಲ್ಲಿ ಲಾಗಿನ್ ಆಗುತ್ತಿದೆ |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | ಈಗಲೇ ಬ್ಯಾಕಪ್ ಫೋಲ್ಡರ್ ಹೊಂದಿಸಿ |  |
| `scr_quickunlock_enable` | Turn on | ಆನ್ ಮಾಡಿ |  |
| `scr_quickunlock_skip` | Not now | ಈಗ ಬೇಡ |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | ಬೆರಳಚ್ಚು ಅಥವಾ ಮುಖದಿಂದ ತೆರೆಯಿರಿ |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s ದಿನಾಂಕ ಹತ್ತಿರವಾಗಿದೆ · Zerokosh ತೆರೆಯಿರಿ |  |
| `scr_reminder_channel` | Renewal reminders | ನವೀಕರಣ ನೆನಪು |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh ನೆನಪಿಸುತ್ತಿದೆ |  |
| `scr_settings_about` | About | ಕುರಿತು |  |
| `scr_settings_allow_screenshots` | Allow screenshots | ಸ್ಕ್ರೀನ್‌ಶಾಟ್ ತೆಗೆಯಲು ಅನುಮತಿಸಿ |  |
| `scr_settings_autofill` | Autofill service | ಆಟೋಫಿಲ್ ಸೇವೆ |  |
| `scr_settings_autofill_off` | Not set up | ಹೊಂದಿಸಿಲ್ಲ |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | ಲಭ್ಯವಿಲ್ಲ |  |
| `scr_settings_autolock` | Lock when I leave the app | ಆಪ್ ಬಿಟ್ಟ ಕೂಡಲೇ ಬೀಗ ಹಾಕು |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 ನಿಮಿಷದ ನಂತರ |  |
| `scr_settings_autolock_immediately` | Immediately | ತಕ್ಷಣ |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d ನಿಮಿಷದ ನಂತರ |  |
| `scr_settings_change_passphrase` | Change passphrase | ಪಾಸ್‌ಫ್ರೇಸ್ ಬದಲಿಸಿ |  |
| `scr_settings_current_passphrase` | Current passphrase | ಈಗಿನ ಪಾಸ್‌ಫ್ರೇಸ್ |  |
| `scr_settings_export` | Export | ರಫ್ತು ಮಾಡಿ |  |
| `scr_settings_import` | Import passwords | ಪಾಸ್‌ವರ್ಡ್‌ಗಳನ್ನು ಆಮದು ಮಾಡಿ |  |
| `scr_settings_language` | Language | ಭಾಷೆ |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | ಹೊಸ ಪಾಸ್‌ಫ್ರೇಸ್ (ಕನಿಷ್ಠ 10 ಅಕ್ಷರ) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | ಹೊಸ ರಿಕವರಿ ಕೀ ಪಡೆಯಿರಿ |  |
| `scr_settings_passphrase_changed` | Passphrase changed | ಪಾಸ್‌ಫ್ರೇಸ್ ಬದಲಾಯಿತು |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | ಬೆರಳಚ್ಚು / ಮುಖ ಅನ್‌ಲಾಕ್ |  |
| `scr_settings_security_info` | How your data is protected | ನಿಮ್ಮ ಡೇಟಾ ಹೇಗೆ ರಕ್ಷಿತವಾಗಿದೆ |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | ಬ್ಯಾಕಪ್ ಮತ್ತು ಸಿಂಕ್ ಫೋಲ್ಡರ್ |  |
| `scr_settings_sync_not_set` | Not backed up | ಬ್ಯಾಕಪ್ ಇಲ್ಲ |  |
| `scr_settings_title` | Settings | ಸೆಟ್ಟಿಂಗ್‌ಗಳು |  |
| `se_title` | Not saved | ಉಳಿಸಲಾಗಿಲ್ಲ |  |
| `st_active_folder` | Active Folder | ಚಾಲ್ತಿಯ ಫೋಲ್ಡರ್ |  |
| `st_active_value` | Active · %1$s | ಚಾಲ್ತಿಯಲ್ಲಿದೆ · %1$s |  |
| `st_backing_up` | Backing up vault… | ತಿಜೋರಿ ಬ್ಯಾಕಪ್ ಆಗುತ್ತಿದೆ… |  |
| `st_backup_now` | Backup Now | ಈಗಲೇ ಬ್ಯಾಕಪ್ ತೆಗೆ |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | ಬ್ಯಾಕಪ್ ಮತ್ತು ಸಿಂಕ್ ಫೋಲ್ಡರ್ |  |
| `st_change_folder` | Change Folder | ಫೋಲ್ಡರ್ ಬದಲಿಸಿ |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | ಹೊಸ ಪಾಸ್‌ಫ್ರೇಸ್ ಮತ್ತೆ ಟೈಪ್ ಮಾಡಿ |  |
| `st_connected_folder` | Connected folder: %1$s | ಸೇರಿದ ಫೋಲ್ಡರ್: %1$s |  |
| `st_disconnect` | Disconnect | ತೆಗೆದುಹಾಕಿ |  |
| `st_done` | Done | ಆಯಿತು |  |
| `st_export_kosh` | Export encrypted .kosh | ಎನ್‌ಕ್ರಿಪ್ಟ್ ಆದ .kosh ರಫ್ತು |  |
| `st_folder_fallback` | Folder | ಫೋಲ್ಡರ್ |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | ಪಾಸ್‌ಫ್ರೇಸ್ ಮರೆತಿರಾ? |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | ನಿಮ್ಮ ಬೆರಳಚ್ಚಿನಿಂದ ಹೊಸದನ್ನು ಇಡಿ |  |
| `st_generate` | Generate | ರಚಿಸಿ |  |
| `st_group_about` | About | ಕುರಿತು |  |
| `st_group_appearance` | Appearance | ನೋಟ |  |
| `st_group_security` | Security | ಭದ್ರತೆ |  |
| `st_group_sync` | Sync | ಸಿಂಕ್ |  |
| `st_import_kosh` | Import a .kosh backup | .kosh ಬ್ಯಾಕಪ್ ಆಮದು |  |
| `st_import_other` | Import from another password manager | ಬೇರೆ ಪಾಸ್‌ವರ್ಡ್ ಮ್ಯಾನೇಜರ್‌ನಿಂದ ಆಮದು |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | ಪರವಾನಗಿ |  |
| `st_logos_by` | Logos provided by | ಲೋಗೊ ಕೊಟ್ಟವರು |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | ಅದನ್ನು ಆಫ್‌ಲೈನ್‌ನಲ್ಲಿ ಇಡಿ. ಹಳೆಯ ರಿಕವರಿ ಕೀ ಇನ್ನು ಚಾಲ್ತಿಯಲ್ಲಿಲ್ಲ. |  |
| `st_new_recovery_result` | Your new Recovery Key: | ನಿಮ್ಮ ಹೊಸ ರಿಕವರಿ ಕೀ: |  |
| `st_subtitle` | Your rules. | ನಿಮ್ಮ ನಿಯಮಗಳು. |  |
| `st_theme` | Theme | ಥೀಮ್ |  |
| `st_theme_dark` | Dark | ಗಾಢ |  |
| `st_theme_light` | Light | ತಿಳಿ |  |
| `st_theme_system` | System | ಸಿಸ್ಟಂ |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | ತಿಜೋರಿ %1$s ನಲ್ಲಿ ಉಳಿಯಿತು! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | ಬ್ಯಾಕಪ್ ಆಗಲಿಲ್ಲ — ಫೋಲ್ಡರ್ ಅನುಮತಿ ನೋಡಿ |  |
| `st_toast_disconnected` | Backup folder disconnected | ಬ್ಯಾಕಪ್ ಫೋಲ್ಡರ್ ತೆಗೆಯಲಾಗಿದೆ |  |
| `st_toast_export_failed` | Export failed | ರಫ್ತಾಗಲಿಲ್ಲ |  |
| `st_toast_exported` | Encrypted vault exported | ಎನ್‌ಕ್ರಿಪ್ಟ್ ಆದ ತಿಜೋರಿ ರಫ್ತಾಯಿತು |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | ಬ್ಯಾಕಪ್ ಫೋಲ್ಡರ್ ಸೇರಿತು ಮತ್ತು ತಿಜೋರಿ %1$s ನಲ್ಲಿ ಉಳಿಯಿತು! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | ಬ್ಯಾಕಪ್ ಫೋಲ್ಡರ್ ಸೇರಿತು: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | ಫೋಲ್ಡರ್ ಸೇರಿಸಲಾಗಲಿಲ್ಲ: %1$s |  |
| `st_vault_review` | Vault review | ತಿಜೋರಿ ಪರಿಶೀಲನೆ |  |
| `st_vault_review_detail` | Reused, weak, expiring | ಮತ್ತೆ ಬಳಸಿದವು, ದುರ್ಬಲ, ಮುಗಿಯುತ್ತಿರುವವು |  |
| `tab_codes` | Codes | ಕೋಡ್ |  |
| `tab_settings` | Settings | ಸೆಟ್ಟಿಂಗ್ |  |
| `tab_templates` | Templates | ಟೆಂಪ್ಲೇಟ್ |  |
| `tab_vault` | Vault | ತಿಜೋರಿ |  |
| `time_days` | %1$dd ago | %1$d ದಿ ಹಿಂದೆ |  |
| `time_hours` | %1$dh ago | %1$d ಗಂ ಹಿಂದೆ |  |
| `time_just_now` | just now | ಈಗಷ್ಟೇ |  |
| `time_minutes` | %1$dm ago | %1$d ನಿ ಹಿಂದೆ |  |
| `time_months` | %1$dmo ago | %1$d ತಿಂಗಳ ಹಿಂದೆ |  |
| `time_years` | %1$dy ago | %1$d ವರ್ಷದ ಹಿಂದೆ |  |
| `tpl_aadhaar_card` | Aadhaar Card | ಆಧಾರ್ |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | ಆಧಾರ್ ಸಂಖ್ಯೆ |  |
| `tpl_aadhaar_card_address` | Address | ಆಧಾರ್ ಮೇಲಿನ ವಿಳಾಸ |  |
| `tpl_aadhaar_card_dob` | Dob | ಹುಟ್ಟಿದ ದಿನಾಂಕ |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | ಸ್ಕ್ಯಾನ್ ಮಾಡಿದ ಪ್ರತಿ |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | ಜೋಡಿಸಿದ ಮೊಬೈಲ್ |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar ಪಾಸ್‌ಕೋಡ್ |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | ಆಧಾರ್ ಮೇಲಿನ ಹೆಸರು |  |
| `tpl_aadhaar_card_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_app_profile` | App Profile | ಆಪ್ ಪ್ರೊಫೈಲ್ |  |
| `tpl_app_profile_app_name` | App name | ಆಪ್ ಹೆಸರು |  |
| `tpl_app_profile_gift_cards` | Gift cards | ಗಿಫ್ಟ್ ಕಾರ್ಡ್ |  |
| `tpl_app_profile_membership` | Membership | ಸದಸ್ಯತ್ವ |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | ಸದಸ್ಯತ್ವ ನವೀಕರಣ |  |
| `tpl_app_profile_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_app_profile_password_if_any` | Password (if any) | ಪಾಸ್‌ವರ್ಡ್ (ಇದ್ದರೆ) |  |
| `tpl_app_profile_registered_email` | Registered email | ನೋಂದಾಯಿತ ಈಮೇಲ್ |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | ನೋಂದಾಯಿತ ಮೊಬೈಲ್ |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | ವಾಲೆಟ್ ಪಿನ್ |  |
| `tpl_bank_account` | Bank Account | ಬ್ಯಾಂಕ್ ಖಾತೆ |  |
| `tpl_bank_account_account_number` | Account number | ಖಾತೆ ಸಂಖ್ಯೆ |  |
| `tpl_bank_account_account_type` | Account type | ಖಾತೆಯ ಬಗೆ |  |
| `tpl_bank_account_bank_name` | Bank name | ಬ್ಯಾಂಕ್ ಹೆಸರು |  |
| `tpl_bank_account_branch` | Branch | ಶಾಖೆ |  |
| `tpl_bank_account_customer_id` | Customer id | ಗ್ರಾಹಕ ID |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC ಕೋಡ್ |  |
| `tpl_bank_account_login_password` | Login password | ಲಾಗಿನ್ ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_bank_account_micr` | MICR code | MICR ಕೋಡ್ |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | ನೆಟ್-ಬ್ಯಾಂಕಿಂಗ್ ಬಳಕೆದಾರ ID |  |
| `tpl_bank_account_nominee` | Nominee | ನಾಮಿನಿ |  |
| `tpl_bank_account_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_bank_account_profile_password` | Profile password | ಪ್ರೊಫೈಲ್ ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_bank_account_registered_email` | Registered email | ನೋಂದಾಯಿತ ಈಮೇಲ್ |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | ನೋಂದಾಯಿತ ಮೊಬೈಲ್ |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | ವಹಿವಾಟು ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_card` | Card | ಕಾರ್ಡ್ |  |
| `tpl_card_atm_pin` | ATM PIN | ATM ಪಿನ್ |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | ಬಿಲ್ಲಿಂಗ್ ಸೈಕಲ್ ದಿನ |  |
| `tpl_card_card_network` | Card network | ನೆಟ್‌ವರ್ಕ್ |  |
| `tpl_card_card_number` | Card number | ಕಾರ್ಡ್ ಸಂಖ್ಯೆ |  |
| `tpl_card_card_portal_login` | Card portal login | ಕಾರ್ಡ್ ಪೋರ್ಟಲ್ ಲಾಗಿನ್ |  |
| `tpl_card_card_portal_password` | Card portal password | ಕಾರ್ಡ್ ಪೋರ್ಟಲ್ ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_card_card_type` | Card type | ಕಾರ್ಡ್ ಬಗೆ |  |
| `tpl_card_card_variant` | Card variant | ಕಾರ್ಡ್ ವೇರಿಯಂಟ್ |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | ಅವಧಿ |  |
| `tpl_card_linked_account` | Linked account | ಜೋಡಿಸಿದ ಖಾತೆ |  |
| `tpl_card_name_on_card` | Name on card | ಕಾರ್ಡ್ ಮೇಲಿನ ಹೆಸರು |  |
| `tpl_card_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_demat` | Demat | ಡೀಮ್ಯಾಟ್ |  |
| `tpl_demat_api_key` | API key | API ಕೀ |  |
| `tpl_demat_api_secret` | API secret | API ಸೀಕ್ರೆಟ್ |  |
| `tpl_demat_bo_id` | BO ID | BO ID |  |
| `tpl_demat_broker` | Broker | ಬ್ರೋಕರ್ |  |
| `tpl_demat_cdsl_tpin` | CDSL TPIN | CDSL TPIN |  |
| `tpl_demat_client_id` | Client id | ಕ್ಲೈಂಟ್ ID |  |
| `tpl_demat_depository` | Depository | ಡಿಪಾಸಿಟರಿ |  |
| `tpl_demat_dp_id` | DP ID | DP ID |  |
| `tpl_demat_login_password` | Login password | ಲಾಗಿನ್ ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_demat_mf_folios` | Mutual fund folios | ಮ್ಯೂಚುವಲ್ ಫಂಡ್ ಫೋಲಿಯೊ |  |
| `tpl_demat_mpin` | MPIN | MPIN |  |
| `tpl_demat_nominee` | Nominee | ನಾಮಿನಿ |  |
| `tpl_demat_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_digilocker` | Digilocker | DigiLocker |  |
| `tpl_digilocker_login_username` | Login username | ಬಳಕೆದಾರ ಹೆಸರು |  |
| `tpl_digilocker_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_digilocker_portal_password` | Portal password | ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_digilocker_security_pin` | Security pin | ಭದ್ರತಾ ಪಿನ್ |  |
| `tpl_driving_license` | Driving License | ಚಾಲನಾ ಪರವಾನಗಿ |  |
| `tpl_driving_license_dl_number` | Dl number | ಪರವಾನಗಿ ಸಂಖ್ಯೆ |  |
| `tpl_driving_license_dob` | Dob | ಹುಟ್ಟಿದ ದಿನಾಂಕ |  |
| `tpl_driving_license_expiry_date` | Expiry date | ಈ ದಿನಾಂಕದವರೆಗೆ ಮಾನ್ಯ |  |
| `tpl_driving_license_file_copy` | Scanned copy | ಸ್ಕ್ಯಾನ್ ಮಾಡಿದ ಪ್ರತಿ |  |
| `tpl_driving_license_issue_date` | Issue date | ನೀಡಿದ ದಿನಾಂಕ |  |
| `tpl_driving_license_name_on_dl` | Name on dl | ಪರವಾನಗಿ ಮೇಲಿನ ಹೆಸರು |  |
| `tpl_driving_license_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_driving_license_rto_location` | Rto location | RTO |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | ವಾಹನ ವರ್ಗಗಳು |  |
| `tpl_epf_pension` | Epf Pension | EPF / ಪಿಂಚಣಿ |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | ಜೋಡಿಸಿದ ಮೊಬೈಲ್ |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | EPF ಮೇಲಿನ ಹೆಸರು |  |
| `tpl_epf_pension_nominee` | Nominee | ನಾಮಿನಿ |  |
| `tpl_epf_pension_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_epf_pension_password` | Password | ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | PF ಸದಸ್ಯ ID |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_epf_pension_scheme` | Scheme | ಯೋಜನೆ |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | ಸರ್ಕಾರಿ ಗುರುತಿನ ಚೀಟಿ |  |
| `tpl_gov_id_expiry` | Expiry | ಅವಧಿ |  |
| `tpl_gov_id_file_copy` | Scanned copy | ಸ್ಕ್ಯಾನ್ ಮಾಡಿದ ಪ್ರತಿ |  |
| `tpl_gov_id_id_kind` | ID type | ಗುರುತಿನ ಚೀಟಿ ಬಗೆ |  |
| `tpl_gov_id_id_number` | ID number | ಗುರುತಿನ ಸಂಖ್ಯೆ |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | ಚೀಟಿಯ ಪ್ರಕಾರ ಹೆಸರು |  |
| `tpl_gov_id_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_gov_id_portal_login` | Portal login | ಪೋರ್ಟಲ್ ಲಾಗಿನ್ |  |
| `tpl_gov_id_portal_password` | Portal password | ಪೋರ್ಟಲ್ ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_insurance` | Insurance | ವಿಮೆ |  |
| `tpl_insurance_agent_contact` | Agent contact | ಏಜೆಂಟ್ ಸಂಪರ್ಕ |  |
| `tpl_insurance_commencement_date` | Commencement date | ಆರಂಭವಾದ ದಿನಾಂಕ |  |
| `tpl_insurance_insurer` | Insurer | ವಿಮಾ ಸಂಸ್ಥೆ |  |
| `tpl_insurance_maturity_date` | Maturity date | ಮೆಚ್ಯುರಿಟಿ ದಿನಾಂಕ |  |
| `tpl_insurance_nominee` | Nominee | ನಾಮಿನಿ |  |
| `tpl_insurance_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_insurance_policy_number` | Policy number | ಪಾಲಿಸಿ ಸಂಖ್ಯೆ |  |
| `tpl_insurance_policy_term` | Policy term | ಪಾಲಿಸಿ ಅವಧಿ |  |
| `tpl_insurance_policy_type` | Policy type | ಪಾಲಿಸಿ ಬಗೆ |  |
| `tpl_insurance_portal_login` | Portal login | ಪೋರ್ಟಲ್ ಲಾಗಿನ್ |  |
| `tpl_insurance_portal_password` | Portal password | ಪೋರ್ಟಲ್ ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_insurance_premium_amount` | Premium amount | ಪ್ರೀಮಿಯಂ ಮೊತ್ತ |  |
| `tpl_insurance_premium_due_date` | Premium due date | ಪ್ರೀಮಿಯಂ ದಿನಾಂಕ |  |
| `tpl_insurance_premium_mode` | Premium mode | ಪ್ರೀಮಿಯಂ ಹೇಗೆ ಕಟ್ಟುತ್ತೀರಿ |  |
| `tpl_insurance_sum_assured` | Sum assured | ವಿಮಾ ಮೊತ್ತ |  |
| `tpl_login` | Login | ಲಾಗಿನ್ |  |
| `tpl_login_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_login_password` | Password | ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_login_recovery_codes` | Recovery codes | ರಿಕವರಿ ಕೋಡ್ |  |
| `tpl_login_username` | Username | ಬಳಕೆದಾರ ಹೆಸರು |  |
| `tpl_login_website` | Website | ವೆಬ್‌ಸೈಟ್ |  |
| `tpl_pan_card` | Pan Card | PAN ಕಾರ್ಡ್ |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | ಆಧಾರ್ ಜೋಡಣೆಯಾಗಿದೆ |  |
| `tpl_pan_card_dob` | Dob | ಹುಟ್ಟಿದ ದಿನಾಂಕ |  |
| `tpl_pan_card_e_filing_password` | E filing password | ಇ-ಫೈಲಿಂಗ್ ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_pan_card_fathers_name` | Fathers name | ತಂದೆಯ ಹೆಸರು |  |
| `tpl_pan_card_file_copy` | Scanned copy | ಸ್ಕ್ಯಾನ್ ಮಾಡಿದ ಪ್ರತಿ |  |
| `tpl_pan_card_name_on_pan` | Name on pan | PAN ಮೇಲಿನ ಹೆಸರು |  |
| `tpl_pan_card_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_pan_card_pan_number` | Pan number | PAN |  |
| `tpl_passkey` | Passkey | ಪಾಸ್‌ಕೀ |  |
| `tpl_passkey_credential_id` | Credential ID | ಕ್ರೆಡೆನ್ಷಿಯಲ್ ID |  |
| `tpl_passkey_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_passkey_private_key` | Private key | ಖಾಸಗಿ ಕೀ |  |
| `tpl_passkey_sign_count` | Sign count | ಸೈನ್ ಕೌಂಟ್ |  |
| `tpl_passkey_user_handle` | User handle | ಬಳಕೆದಾರ ಹ್ಯಾಂಡಲ್ |  |
| `tpl_passkey_username` | Username | ಬಳಕೆದಾರ ಹೆಸರು |  |
| `tpl_passkey_website` | Website | ವೆಬ್‌ಸೈಟ್ |  |
| `tpl_passport` | Passport | ಪಾಸ್‌ಪೋರ್ಟ್ |  |
| `tpl_passport_dob` | Dob | ಹುಟ್ಟಿದ ದಿನಾಂಕ |  |
| `tpl_passport_expiry_date` | Expiry date | ಅವಧಿ ಮುಗಿಯುವ ದಿನಾಂಕ |  |
| `tpl_passport_file_copy` | Scanned copy | ಸ್ಕ್ಯಾನ್ ಮಾಡಿದ ಪ್ರತಿ |  |
| `tpl_passport_given_names` | Given names | ಇಟ್ಟ ಹೆಸರು |  |
| `tpl_passport_issue_date` | Issue date | ನೀಡಿದ ದಿನಾಂಕ |  |
| `tpl_passport_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_passport_passport_number` | Passport number | ಪಾಸ್‌ಪೋರ್ಟ್ ಸಂಖ್ಯೆ |  |
| `tpl_passport_place_of_issue` | Place of issue | ನೀಡಿದ ಸ್ಥಳ |  |
| `tpl_passport_portal_login` | Portal login | Passport Seva ಲಾಗಿನ್ |  |
| `tpl_passport_portal_password` | Portal password | Passport Seva ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_passport_surname` | Surname | ಮನೆತನದ ಹೆಸರು |  |
| `tpl_secure_note` | Secure Note | ಸುರಕ್ಷಿತ ಟಿಪ್ಪಣಿ |  |
| `tpl_secure_note_attachment` | Attachment | ಲಗತ್ತು |  |
| `tpl_secure_note_body` | Note | ಟಿಪ್ಪಣಿ |  |
| `tpl_shopping` | Shopping | ಶಾಪಿಂಗ್ ಖಾತೆ |  |
| `tpl_shopping_gift_card_code` | Gift card code | ಗಿಫ್ಟ್ ಕಾರ್ಡ್ ಕೋಡ್ |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | ಗಿಫ್ಟ್ ಕಾರ್ಡ್ ಪಿನ್ |  |
| `tpl_shopping_membership_id` | Membership id | ಸದಸ್ಯತ್ವ ID |  |
| `tpl_shopping_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_shopping_password` | Password | ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_shopping_registered_email` | Registered email | ನೋಂದಾಯಿತ ಈಮೇಲ್ |  |
| `tpl_shopping_registered_mobile` | Registered mobile | ನೋಂದಾಯಿತ ಮೊಬೈಲ್ |  |
| `tpl_shopping_wallet_pin` | Wallet pin | ವಾಲೆಟ್ ಪಿನ್ |  |
| `tpl_telecom` | Telecom | ಮೊಬೈಲ್ ಮತ್ತು ಇಂಟರ್ನೆಟ್ |  |
| `tpl_telecom_account_number` | Account number | ಖಾತೆ ಸಂಖ್ಯೆ |  |
| `tpl_telecom_circle` | Circle | ಸರ್ಕಲ್ |  |
| `tpl_telecom_mobile_number` | Mobile number | ಮೊಬೈಲ್ ಸಂಖ್ಯೆ |  |
| `tpl_telecom_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_telecom_operator` | Operator | ಸಂಸ್ಥೆ |  |
| `tpl_telecom_plan_type` | Plan type | ಪ್ಲಾನ್ ಬಗೆ |  |
| `tpl_telecom_portal_password` | Portal password | ಪೋರ್ಟಲ್ ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_telecom_puk` | PUK code | PUK ಕೋಡ್ |  |
| `tpl_telecom_renewal_date` | Renewal date | ರೀಚಾರ್ಜ್ ದಿನಾಂಕ |  |
| `tpl_telecom_sim_number` | Sim number | ಸಿಮ್ ಸಂಖ್ಯೆ (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | ಸಿಮ್ ಪಿನ್ |  |
| `tpl_transit` | Transit | ಪ್ರಯಾಣ ಪಾಸ್ |  |
| `tpl_transit_login_password` | Login password | ಲಾಗಿನ್ ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_transit_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_transit_operator_name` | Operator name | ಸಂಸ್ಥೆ |  |
| `tpl_transit_registered_email` | Registered email | ನೋಂದಾಯಿತ ಈಮೇಲ್ |  |
| `tpl_transit_registered_mobile` | Registered mobile | ನೋಂದಾಯಿತ ಮೊಬೈಲ್ |  |
| `tpl_transit_smart_card_number` | Smart card number | ಸ್ಮಾರ್ಟ್ ಕಾರ್ಡ್ ಸಂಖ್ಯೆ |  |
| `tpl_transit_wallet_pin` | Wallet pin | ವಾಲೆಟ್ ಪಿನ್ |  |
| `tpl_travel_booking` | Travel Booking | ಪ್ರಯಾಣ ಬುಕಿಂಗ್ |  |
| `tpl_travel_booking_account_username` | Account username | ಬಳಕೆದಾರ ಹೆಸರು |  |
| `tpl_travel_booking_login_password` | Login password | ಲಾಗಿನ್ ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_travel_booking_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_travel_booking_provider` | Provider | ಸಂಸ್ಥೆ |  |
| `tpl_travel_booking_registered_email` | Registered email | ನೋಂದಾಯಿತ ಈಮೇಲ್ |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | ನೋಂದಾಯಿತ ಮೊಬೈಲ್ |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | ವಾಲೆಟ್ ಪಿನ್ |  |
| `tpl_upi` | UPI | UPI |  |
| `tpl_upi_app_name` | App name | UPI ಆಪ್ |  |
| `tpl_upi_apps_used` | Apps used | ಯಾವ ಆಪ್‌ನಲ್ಲಿ ಚಾಲ್ತಿಯಲ್ಲಿದೆ |  |
| `tpl_upi_linked_account` | Linked account | ಜೋಡಿಸಿದ ಖಾತೆ |  |
| `tpl_upi_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_upi_upi_id` | UPI ID | UPI ID |  |
| `tpl_upi_upi_pin` | UPI PIN | UPI ಪಿನ್ |  |
| `tpl_utility` | Utility | ಬಿಲ್ ಮತ್ತು ಸಂಪರ್ಕ |  |
| `tpl_utility_account_holder` | Account holder | ಖಾತೆದಾರ |  |
| `tpl_utility_consumer_number` | Consumer number | ಗ್ರಾಹಕ ಸಂಖ್ಯೆ |  |
| `tpl_utility_due_day` | Bill due day | ಬಿಲ್ ಕಟ್ಟುವ ದಿನ |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag ID |  |
| `tpl_utility_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_utility_portal_login` | Portal login | ಪೋರ್ಟಲ್ ಲಾಗಿನ್ |  |
| `tpl_utility_portal_password` | Portal password | ಪೋರ್ಟಲ್ ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_utility_provider` | Provider | ಸೇವೆ ಕೊಡುವ ಸಂಸ್ಥೆ |  |
| `tpl_utility_utility_kind` | Utility kind | ಯಾವುದರ ಬಿಲ್ |  |
| `tpl_utility_vehicle_number` | Vehicle number | ವಾಹನ ಸಂಖ್ಯೆ |  |
| `tpl_utility_wifi_password` | Wi-Fi password | Wi-Fi ಪಾಸ್‌ವರ್ಡ್ |  |
| `tpl_voter_id` | Voter Id | ಮತದಾರ ಗುರುತು |  |
| `tpl_voter_id_constituency` | Constituency | ಕ್ಷೇತ್ರ |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC ಸಂಖ್ಯೆ |  |
| `tpl_voter_id_file_copy` | Scanned copy | ಸ್ಕ್ಯಾನ್ ಮಾಡಿದ ಪ್ರತಿ |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | ಮತದಾರ ಚೀಟಿ ಮೇಲಿನ ಹೆಸರು |  |
| `tpl_voter_id_notes` | Notes | ಟಿಪ್ಪಣಿ |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP ಪಾಸ್‌ವರ್ಡ್ |  |
| `tr_days_many` | %1$d days left | %1$d ದಿನ ಉಳಿದಿವೆ |  |
| `tr_days_one` | %1$d day left | %1$d ದಿನ ಉಳಿದಿದೆ |  |
| `tr_gone_today` | gone today | ಇಂದು ಹೋಗುತ್ತದೆ |  |
| `tr_restore` | Restore | ಮರಳಿ ತನ್ನಿ |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | ಆಮೇಲೆ ಅವು ಶಾಶ್ವತವಾಗಿ ಹೋಗುತ್ತವೆ — ಬೇರೆಲ್ಲೂ ಪ್ರತಿ ಇಲ್ಲ. |  |
| `ui_hide_passphrase` | Hide passphrase | ಪಾಸ್‌ಫ್ರೇಸ್ ಮರೆಮಾಡಿ |  |
| `ui_label_count` | %1$s, %2$s | %1$s, %2$s |  |
| `ui_show_passphrase` | Show passphrase | ಪಾಸ್‌ಫ್ರೇಸ್ ತೋರಿಸಿ |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | ಇದೇ ಫೋನಿನಲ್ಲಿ %1$d ದಾಖಲೆಗಳೊಂದಿಗೆ ಹೋಲಿಸಲಾಗಿದೆ. ಏನೂ ಎಲ್ಲಿಗೂ ಕಳಿಸಿಲ್ಲ. |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | ಇದೇ ಫೋನಿನಲ್ಲಿ %1$d ದಾಖಲೆಯೊಂದಿಗೆ ಹೋಲಿಸಲಾಗಿದೆ. ಏನೂ ಎಲ್ಲಿಗೂ ಕಳಿಸಿಲ್ಲ. |  |
| `vh_count_many` | %1$d things worth a look. | %1$d ವಿಷಯಗಳು ಗಮನಿಸಬೇಕಾದವು. |  |
| `vh_count_one` | %1$d thing worth a look. | %1$d ವಿಷಯ ಗಮನಿಸಬೇಕಾದದ್ದು. |  |
| `vh_empty` | No reused, weak or expiring credentials. | ಮತ್ತೆ ಬಳಸಿದ, ದುರ್ಬಲ ಅಥವಾ ಮುಗಿಯುತ್ತಿರುವ ಮಾಹಿತಿ ಇಲ್ಲ. |  |
| `vh_kind_common` | Commonly guessed | ಸುಲಭವಾಗಿ ಊಹಿಸಬಹುದಾದದ್ದು |  |
| `vh_kind_expiring` | Expiring | ಮುಗಿಯುತ್ತಿದೆ |  |
| `vh_kind_reused` | Reused password | ಮತ್ತೆ ಬಳಸಿದ ಪಾಸ್‌ವರ್ಡ್ |  |
| `vh_kind_weak` | Weak | ದುರ್ಬಲ |  |
| `vh_no_kit_title` | No recovery kit saved | ಯಾವುದೇ ರಿಕವರಿ ಕಿಟ್ ಉಳಿಸಿಲ್ಲ |  |
| `vh_nothing` | Nothing to fix. | ಸರಿಪಡಿಸಲು ಏನೂ ಇಲ್ಲ. |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | ಆಫ್‌ಲೈನ್ |  |
| `wl_chip_open` | Open source | ಓಪನ್ ಸೋರ್ಸ್ |  |
| `wl_create` | Create a new vault | ಹೊಸ ತಿಜೋರಿ ರಚಿಸಿ |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | ಈಮೇಲ್ ಇಲ್ಲ · ಖಾತೆ ಇಲ್ಲ · ಏನೂ ಈ ಫೋನಿನಿಂದ ಹೊರಗೆ ಹೋಗದು |  |
| `wl_head_1` | Your keys. | ನಿಮ್ಮ ಕೀಲಿಗಳು. |  |
| `wl_head_2` | Your device. | ನಿಮ್ಮ ಫೋನ್. |  |
| `wl_head_3` | No server. | ಸರ್ವರ್ ಇಲ್ಲ. |  |
| `wl_restore` | Restore from Recovery Kit | ರಿಕವರಿ ಕಿಟ್‌ನಿಂದ ಮರಳಿ ತನ್ನಿ |  |
| `wl_sr_headline` | Your keys. Your device. No server. | ನಿಮ್ಮ ಕೀಲಿಗಳು. ನಿಮ್ಮ ಫೋನ್. ಸರ್ವರ್ ಇಲ್ಲ. |  |
