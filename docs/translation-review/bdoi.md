# Dogri (`b+doi`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-b+doi/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Dogri | ok? |
|---|---|---|---|
| `au_close` | Close | बंद करो |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | चुनी दी तस्वीर च ठीक TOTP QR कोड नेईं लब्भा |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | खाली थमां शुरू करियै अपने खानें दे नां आपै रक्खो — टेम्पलेट सिर्फ लेबल भरदे न, डेटा कदें नेईं। |  |
| `hm_close_search` | Close search | तोपा-तोपी बंद करो |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | इस फोनै थमां कदें बाह्र नेईं जंदा। संभालदे बेल्लै एन्क्रिप्ट कीता जंदा ऐ। |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | हुन ओह् फाइल हटाई देओ जेह्ड़ी तुसें आयात कीती ही। ओह् तुंदे पासवर्डें दी खुल्ली सूची ऐ, ते हजें बी तुंदे Downloads च पेई ऐ। |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | सारा कुछ सिर्फ इसै फोन उप्पर डिक्रिप्ट होंदा ऐ। कुछ बी अपलोड नेईं होंदा, क्योंकि एह् एप नेटवर्क कनेक्शन खोह्ली गै नेईं सकदी। |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | बैंक कदें तुंदा OTP नेईं मंगदे। जेह्ड़ा मंगे, ओह् ठग ऐ। |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | कोई बी बैंक अफसर तुसें गी स्क्रीन सांझी करने आह्ली एप लाने आस्तै नेईं आखग। |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | तुंदा UPI पिन सिर्फ UPI एप दे कीपैड आस्तै ऐ — फोन उप्पर कुसै गी नेईं दस्सो। |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | KYC इक्को दिनै च खत्म नेईं होंदी। “अज्ज KYC खत्म” आह्ले संदेश ठगी न। |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | पैसे लैने आस्तै ना पिन पाना पौंदा ऐ, ना QR स्कैन करना पौंदा ऐ। |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | बिजली कटने दा SMS ते ओस च कुसै दा निजी नंबर? ओह् ठगी ऐ। |  |
| `nav_close_menu` | Close menu | मेनू बंद करो |  |
| `nfc_cannot_read` | Cannot read cards | कार्ड नेईं पढ़ेआ जाई सकदा |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | इस फोन च NFC नेईं ऐ, इस करी कार्ड नेईं पढ़ेआ जाई सकदा। |  |
| `ob_fact_lost_title` | If you lose your keys | जेकर कुंजियां गवाची जान |  |
| `ob_fact_network_note` | The app literally cannot phone home | एह् एप कुतै बी संपर्क नेईं करी सकदी |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | बायोमेट्रिक सुरक्षा चिप थमां बाह्र कदें नेईं जंदा |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | तुसें कुंजी ओसै फोल्डर च रक्खी ऐ जेह्ड़ा तुंदा एन्क्रिप्ट कीता भंडार सिंक करदा ऐ। हुन जिसी ओह् फोल्डर मिलग, ओसी दौनें हिस्से मिली जांगे। कुंजी होर कुतै रक्खो — कागज, होर खाता, जां अलमारी। |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | एह् तुंदी भंडार फाइलै दे कोल गै ऐ |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH रिकवरी |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | स्कैन करो जां लिखो। फ्ही इंस्टॉल, फैक्ट्री रिसेट, जां फोन गवाचने दे बाद बी कम्म करदी ऐ। |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | हुनै संभाली दी किट थमां समूह %1$d ते समूह %2$d लिखो। |  |
| `ob_kit_challenge_hint` | Group %1$d | समूह %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | पैह्लें किट संभालो, फ्ही समूह %1$d ते %2$d वापस लिखो। |  |
| `ob_kit_challenge_title` | Check you actually have it | दिक्खो जे किट सच्चीं तुंदे कोल ऐ |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | एह् उप्परली कुंजी कन्नै मेल नेईं खंदा। |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | कोई बी — Zerokosh बी — एह् मेरे आस्तै वापस नेईं आनी सकदा। |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "मैं एह् ऑफलाइन रक्खी लेई ऐ। " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | इक्को बारी दिखदी ऐ, कदें खुल्ले रूप च नेईं संभाली जंदी। अंदर आई सकदे ओ तां सेटिंग थमां नमीं बनाई लैओ। |  |
| `ob_kit_head_emph` | On paper. | कागजै उप्पर। |  |
| `ob_kit_head_lead` | "One key. " | "इक कुंजी। " |  |
| `ob_kit_head_tail` | " Never online." | " कदें ऑनलाइन नेईं।" |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | ना Gmail, ना WhatsApp, ना स्क्रीनशॉट। अलमारी, बैंक लॉकर, जां स्टील दी पत्तरी। |  |
| `ob_kit_offline_title` | Keep it off the internet | इसनूं इंटरनेट थमां दूर रक्खो |  |
| `ob_kit_print` | Print | छापो |  |
| `ob_kit_print_note` | A printer, or Save as PDF | प्रिंटर, जां PDF दे रूप च संभालो |  |
| `ob_kit_qr` | QR image | QR तस्वीर |  |
| `ob_kit_qr_cd` | Recovery key QR code | रिकवरी कुंजी दा QR कोड |  |
| `ob_kit_qr_note` | To an offline gallery | ऑफलाइन गैलरी च |  |
| `ob_kit_regenerate` | Regenerate | नमीं बनाओ |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | एह् संभाली नेईं गेदा। फ्ही कोशश करो, जां होर थाहर चुनो। |  |
| `ob_kit_save_pdf` | Save PDF | PDF संभालो |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | इक पन्ने दी छापने जोगी किट |  |
| `ob_kit_saved` | I\'ve saved my kit | मैं अपनी किट संभाली लेई ऐ |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s च संभाली लेदा |  |
| `ob_kit_sent_to_printer` | Sent to the printer | प्रिंटर गी भेजी दित्ता |  |
| `ob_kit_skip` | I\'ll do this later | एह् बाद च करगा |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | तुंदा भंडार चलदा रौह्ग। जदूं तकर किट नेईं संभाली जंदी, Zerokosh याद करांदा रौह्ग। |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | इसै फोन उप्पर बनी दी, सिर्फ इक बारी दिक्खनी। पासफ्रेज भुल्लने पर अंदर वापस औने दा एह्यो इक्को रस्ता ऐ। |  |
| `ob_kit_working` | Working… | कम्म चला करदा ऐ… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | भंडार दे लेबल, टेम्पलेट ते चेतावनियां तुरत बदली जांगियां। सेटिंग च कदें बी बदली सकदे ओ। |  |
| `ob_pass_confirm` | Confirm | फ्ही लिखो |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | एह् अस कदें नेईं दिक्खदे। कोई रिसेट लिंक नेईं। |  |
| `ob_pass_head_emph` | held only | सिर्फ तुंदे कोल |  |
| `ob_pass_head_lead` | "One secret, " | "इक्को भेद, " |  |
| `ob_pass_head_tail` | " by you." | । |  |
| `ob_pass_no_match` | no match | मेल नेईं खंदा |  |
| `ob_pass_seal` | Seal the vault | भंडार बंद करो |  |
| `ob_pass_sealing` | Sealing… | बंद करा करने आं… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | इक-दूए कन्नै ना मिलदे त्रै-चार शब्द इक चलाक शब्दै थमां खरे न। इस परदे थमां कुछ बाह्र नेईं जंदा। |  |
| `ob_pass_tab_passphrase` | Passphrase | पासफ्रेज |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 अंकें दा पिन |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | तुंदी उंगली दा निशान फोनै दी सुरक्षा चिप दे अंदर गै रौंहदा ऐ। ओह् इस फोनै थमां कदें बाह्र नेईं जंदा। |  |
| `ob_trust_continue` | I understand · Continue | समझी गेआ · अग्गें चलो |  |
| `ob_trust_head_emph` | don\'t | नेईं पता |  |
| `ob_trust_head_lead` | "Exactly what we " | "साढ़ें गी सच्चीं केह् " |  |
| `ob_trust_head_tail` | " know." | । |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | पासफ्रेज भुल्लियै रिकवरी किट बी गवाची गेई, तां भंडार बंद गै रौह्ग — तुंदे आस्तै, साढ़े आस्तै, सारें आस्तै। |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "कुंजियां तुंदे कोल। " |  |
| `ob_trust_stat_files` | .kosh file on device | फोन उप्पर .kosh फाइल |  |
| `ob_trust_stat_servers` | servers contacted | सर्वर कन्नै संपर्क |  |
| `ob_trust_stat_trackers` | trackers or SDKs | ट्रैकर जां SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | एह् इक बारी पढ़ी लैओ। सारी सुरक्षा दा ढांचा एह्यो ऐ, सिद्धे शब्दें च। |  |
| `ob_trust_tag_audited` | Audited build | ऑडिट कीता बिल्ड |  |
| `ob_trust_tag_reproducible` | Reproducible APK | फ्ही बनाई सकने आह्ला APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | तुस उंगली कन्नै खोह्ला करदे ओ। जेकर कदें उंगली कम्म करना बंद करी देग, तां एह्यै तुसें गी अंदर आनग — इस करी दिक्खी लैना खरा। |  |
| `pc_confirm` | Check | जांचो |  |
| `pc_correct` | Still correct. Nothing to do. | हजें बी ठीक ऐ। कुछ करने दी लोड़ नेईं। |  |
| `pc_forgot` | I cannot remember it | मिगी याद नेईं औंदा |  |
| `pc_later` | Not now | हुन नेईं |  |
| `pc_reset_action` | Set new passphrase | नमां पासफ्रेज रक्खो |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | तुंदी उंगली एह् भंडार खोह्ल सकदी ऐ, इस करी ओह्यै नमां पासफ्रेज बी रक्खी सकदी ऐ — रिकवरी किट दी लोड़ नेईं। पक्का करने आस्तै इक बारी होर पुच्छेआ जाग। |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | पासफ्रेज बदली गेआ। छेती अनलॉक फ्ही लगी गेआ। |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | एह् नेईं होआ। तुंदा पराणा पासफ्रेज गै चलदा ऐ। |  |
| `pc_reset_title` | Set a new passphrase | नमां पासफ्रेज रक्खो |  |
| `pc_title` | Do you still remember your passphrase? | क्या तुसें गी हजें बी अपना पासफ्रेज याद ऐ? |  |
| `pc_wrong` | That is not it. You can set a new one instead. | एह् ओह् नेईं। इसदी थाहर नमां रक्खी सकदे ओ। |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | इस एंट्री आस्तै याद रक्खे दे %1$d मुल्ल हटाए जांगे। एह् वापस नेईं होई सकदा। |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh ओह् लॉगिन नेईं संभाली सकेआ। |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | भरने आस्तै Zerokosh खोह्लो |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | एह् इक्को पासफ्रेज सारा कुछ बंद रखदा ऐ। इक लम्मा जेहा वाक्य चुनो जेह्ड़ा सिर्फ तुसें गै जानदे ओ। |  |
| `scr_create_button` | Lock it in | बंद करी देओ |  |
| `scr_create_confirm_hint` | Type it again | फ्ही लिखो |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | पासफ्रेज (घट्टोघट्ट 10 अक्षर) |  |
| `scr_create_mismatch` | The two entries don\'t match | दौनें इक जनेह् नेईं न |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 अंकें दा पिन |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | इसदी थाहर 6 अंकें दा पिन रक्खो |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | पिन आस्तै उंगली जां मुंह अनलॉक आह्ला फोन चाहिदा। किरपा करियै पासफ्रेज चुनो। |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | पिन दी इजाजत इस आस्तै ऐ क्योंकि एह् फोन इसनूं अपनी सुरक्षा चिप ते तुंदी उंगली जां मुंह कन्नै बचांदा ऐ। |  |
| `scr_create_strength_fair` | Fair | ठीक-ठाक |  |
| `scr_create_strength_good` | Good | खरा |  |
| `scr_create_strength_strong` | Strong | मजबूत |  |
| `scr_create_strength_weak` | Weak | कमजोर |  |
| `scr_create_title` | Create your passphrase | अपना पासफ्रेज बनाओ |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | घट्टोघट्ट 10 अक्षर चाहिदे — जितना लम्मा, ओतना मजबूत |  |
| `scr_create_working` | Preparing your vault… | तुंदा भंडार तैयार होआ करदा ऐ… |  |
| `scr_detail_delete` | Delete | हटाओ |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | एह् 30 दिन “हाल च हटाए गे” च रौह्ग, ते तुंदे होर फोनें कन्नै सिंक होने पर चली जाग। |  |
| `scr_detail_delete_confirm_title` | Delete this record? | एह् एंट्री हटाई देचै? |  |
| `scr_detail_delete_confirm_yes` | Delete | हटाओ |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | सिक्रेट जां otpauth:// लिंक चिपकाओ |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | अपनी उंगली जां मुंह बरतो |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh खोह्लो |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | मते बारी गलत कोशश होई। %1$d सैकेंड रुको। |  |
| `scr_lock_hint` | Passphrase | पासफ्रेज |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | एह् रिकवरी कुंजी ठीक नेईं — इक-इक अक्षर मलाइयै दिक्खो |  |
| `scr_lock_title` | Vault is locked | भंडार बंद ऐ |  |
| `scr_lock_unlock` | Unlock | खोह्लो |  |
| `scr_lock_use_passphrase` | Use passphrase | पासफ्रेज बरतो |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | किरपा करियै इक बारी पासफ्रेज कन्नै खोह्लो |  |
| `scr_lock_use_recovery` | Use Recovery Key | रिकवरी कुंजी बरतो |  |
| `scr_lock_wrong` | Wrong passphrase | पासफ्रेज गलत ऐ |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | कदें पासफ्रेज भुल्ली गे, तां अंदर वापस औने दा एह्यो इक्को रस्ता ऐ। अस इसनूं रिसेट नेईं करी सकदे — कोई बी नेईं करी सकदा। |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | मैं इसनूं लिखियै सुरक्षित थाहर रक्खी लेया ऐ |  |
| `scr_recovery_done` | Continue | अग्गें चलो |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | एह् कुंजी इक्को बारी दिखदी ऐ। जदूं तकर तुस तिजोरी खोह्ल सकदे ओ, सेटिंग थमां कदें बी नमीं बनाई सकदे ओ। |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | एह् कागज अपनी जमीन-जायदाद दे कागजें जां होर जरूरी कागजातें कन्नै रक्खो। जिसदे कोल एह् कुंजी ऐ, ओह् तुंदा भंडार खोह्ल सकदा ऐ — इसनूं लॉकर दी चाबी दे नमें संभालो। |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | रिकवरी किट PDF संभाली गेई |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh रिकवरी किट |  |
| `scr_recovery_save_pdf` | Save as PDF | PDF दे रूप च संभालो |  |
| `scr_recovery_title` | Your Recovery Key | तुंदी रिकवरी कुंजी |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | तुस जे कुछ संभालदे ओ, ओह् तुंदे फोनै दी इक बंद फाइलै च रौंहदा ऐ। ओह् कदें साढ़े कोल नेईं औंदा — ओह् रखने आस्तै साढ़े कोल थाहर गै नेईं। |  |
| `scr_trust_card1_title` | Your data stays on this device | तुंदा डेटा इसै फोनै च रौंहदा ऐ |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | Zerokosh खाता नेईं, क्लाउड नेईं, साइन-अप नेईं। इसनूं सिर्फ तुस गै खोह्ल सकदे ओ। अस बी नेईं। |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | साढ़े सर्वर नेईं न — ना हैक करने आस्तै कुछ, ना बेचने आस्तै |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | ना सदस्यता, ना इश्तिहार। कोई बी साढ़ा कोड पढ़ियै साढ़ी इक-इक गल्ल परख सकदा ऐ। |  |
| `scr_trust_card3_title` | Free forever, open source | हमेशा मुफ्त, खुल्ला स्रोत |  |
| `scr_trust_continue` | Continue | अग्गें चलो |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | तुंदे बदलाव संभाले नेईं गे, इस करी भंडारै च जे पैह्लें हा ओस च कुछ बी नेईं गवाचा। |  |
| `st_recently_deleted` | Recently deleted | हाल च हटाए गे |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | इक बारी दा कोड (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | इक बारी दा कोड (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | इक बारी दा कोड (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA सिक्रेट |  |
| `tr_cannot_undo` | This cannot be undone. | एह् वापस नेईं होई सकदा। |  |
| `tr_delete_all` | Delete all permanently | सारा कुछ हमेशा आस्तै हटाओ |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d एंट्रियां हमेशा आस्तै चली जांगियां। एह् वापस नेईं होई सकदा, ते वापस आने आस्तै कोई बैकअप बी नेईं। |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d एंट्री हमेशा आस्तै चली जाग। एह् वापस नेईं होई सकदा, ते वापस आने आस्तै कोई बैकअप बी नेईं। |  |
| `tr_delete_all_title` | Delete everything in the trash? | कूड़े दा सारा कुछ हटाई देचै? |  |
| `tr_delete_now` | Delete now | हुनै हटाओ |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | “%1$s” हमेशा आस्तै हटाई देचै? |  |
| `tr_empty` | Nothing deleted. | कुछ नेईं हटाया गेदा। |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | हटाइयां एंट्रियां इत्थै %1$d दिन रौंहदियां न। |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | भारती बैंकें, UPI, कार्डें, डीमैट, EPF, ते जेह्ड़ियां OTP एपां तुस सच्चीं बरतदे ओ — उंदे आस्तै फोनै च गै रौह्ने आह्ला भंडार। |  |

## Priority 2 — longer prose

| key | English | Dogri | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | जेह्ड़ी इक चीज सारें थमां मता कम्म आवै, ओथून शुरू करो। नोट्स एपै च पेदे बारां पासवर्डें थमां संभाली दा इक पासवर्ड मता सुरक्षित ऐ। |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | पासफ्रेज भुल्ली गे तां अंदर औने दा रस्ता रिकवरी किट गै ऐ। तुंदे आस्तै होर कोई नेईं बनाई सकदा। |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | ओस फाइलै च पछानने जोगा कुछ नेईं लब्भा। Chrome, Google Password Manager, Bitwarden, LastPass ते KeePass दे निर्यात समझ औंदे न। |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d हुनै दियां एंट्रियां बदलङियां — साइट ते यूजरनेम मलाइयै। बदले दे पासवर्ड हर एंट्री दे इतिहासै च लब्भङे। |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d हुनै दी एंट्री बदलग — साइट ते यूजरनेम मलाइयै। बदले दे पासवर्ड हर एंट्री दे इतिहासै च लब्भङे। |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d एंट्रियां दौनें पासें बदली दियां हियां। दौनें रूप संभाले गे — “(conflict copy)” तोपो। |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | ओह् पासफ्रेज पाओ जेह्ड़ा इस बैकअप फाइलै गी खोह्लदा ऐ। ओह् तुंदे हुनै आह्ले थमां बक्खरा होई सकदा ऐ। |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | .kosh फाइलै दा Poly1305 प्रमाणीकरण टैग मेल नेईं खंदा। बिच्चै गै रुके दे सिंक जां खराब स्टोरेजै दे बाद इय्यां होई सकदा ऐ। |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh भंडार फाइलै दे कोल इक चलदा बैकअप रखदा ऐ। ओसनूं अपने सिंक फोल्डर थमां वापस आनो, जां होर फोन उप्पर रिकवरी किट कन्नै भंडार खोह्लो। |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | जदूं तकर ना पढ़ै, कार्ड फोनै दे पिच्छें सिद्धा लाइयै रक्खो। इस थमां कार्ड नंबर, मियाद ते नां लब्भदा ऐ — CVV चिपै च नेईं होंदा, ओह् तुसें आपै लिखना पौग। |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | अंदर वापस औने दा छेती रस्ता चुनो। भंडार दी रक्षा पासफ्रेज गै करदा ऐ; एह् सिर्फ इसै फोन उप्पर कुंजी खोह्लदा ऐ। |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | हुन तुंदे भंडार च सच्ची जानकारी ऐ। रिकवरी किट बगैर पासफ्रेज भुल्लने पर कोई तुसें गी वापस अंदर नेईं आनी सकदा — अस बी नेईं। |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh मुफ्त ते खुल्ला स्रोत ऐ, ते इसदे कोई सर्वर नेईं न। तुंदा भंडार सिर्फ तुस गै खोह्ल सकदे ओ। अस बी नेईं। |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | कैमरे दी इजाजत सिर्फ QR कोड स्कैन करने आस्तै चाहिदी। एंट्री जोड़दे बेल्लै सिक्रेट हत्थें कन्नै बी चिपकाई सकदे ओ। |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | उऐं बैंक एपें आस्तै जेह्ड़ियां ऑटोफिल नेईं होने दिंदियां — बटन दबाइयै लॉगिन दा ब्यौरा इक-इक करियै कॉपी करो |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | जिय्यां तुस फोन खोह्लदे ओ, तिय्यां गै भंडार बी। पासफ्रेज हमेशा कम्म करदा रौह्ग। |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | भंडार दे स्क्रीनशॉट क्लाउड फोटो बैकअप तकर पुज्जी सकदे न। मता जरूरी होए तां गै चालू करो। |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | भंडार फाइल नेईं लिखी जाई सकी। जेकर तुसें बैकअप ते सिंक फोल्डर लाया दा ऐ, तां होई सकदा ऐ Android ने ओसदी इजाजत वापस लेई लेई होए — सेटिंग खोह्लो, फोल्डर फ्ही चुनो, ते फ्ही कोशश करो। |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | तुंदियां एन्क्रिप्ट कीतियां .kosh फाइलां सिद्धा इसै फोल्डरै च संभालियां जंदियां न। मते उपकरणें उप्पर आपै-आप बैकअप होने आस्तै इस फोल्डर गी Google Drive, Syncthing, Nextcloud जां SD कार्ड कन्नै सिंक करो। |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | नमीं रिकवरी कुंजी बनाने आस्तै अपना पासफ्रेज पाओ। पराणी कुंजी कम्म करना बंद करी देग। |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | इस पन्ने दी बाकी हर गल्ल इक लॉगिन कमजोर करदी ऐ। एह् सारा भंडार लेई जाई सकदी ऐ। सेटिंग → नमीं रिकवरी कुंजी लैओ। |  |

## Priority 3 — short labels

| key | English | Dogri | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | मजबूत पासवर्ड बरतो |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | खाते दा नां (जिय्यां Google) |  |
| `au_active_many` | %1$d active codes | %1$d चालू कोड |  |
| `au_active_one` | %1$d active code | %1$d चालू कोड |  |
| `au_add_another` | Add another authenticator | होर इक प्रमाणक जोड़ो |  |
| `au_add_secret` | Add Secret Key | सिक्रेट कुंजी जोड़ो |  |
| `au_camera_needed` | Camera permission needed to scan QR code | QR कोड स्कैन करने आस्तै कैमरे दी इजाजत चाहिदी |  |
| `au_copied` | Copied · clears shortly | कॉपी होई गेई · थोह्ड़े बेल्लै च मिटी जाग |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google, GitHub जां अपने ब्रोकरै दा QR स्कैन करो, जां सिक्रेट कुंजी हत्थें कन्नै लिखो। |  |
| `au_enter_key` | Enter Key | कुंजी लिखो |  |
| `au_fallback_name` | Authenticator | प्रमाणक |  |
| `au_flashlight` | Flashlight | टॉर्च |  |
| `au_grant` | Grant Permission | इजाजत देओ |  |
| `au_image_failed` | Failed to process image | तस्वीर नेईं पढ़ी जाई सकी |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | गलत Base32 सिक्रेट कुंजी (सिर्फ A-Z अक्षर ते 2-7 अंक) |  |
| `au_no_match` | No codes match | कोई कोड नेईं लब्भा |  |
| `au_none_yet` | No codes yet. | हजें तकर कोई कोड नेईं। |  |
| `au_pick_image` | Pick Image | तस्वीर चुनो |  |
| `au_rotating` | "Rotating " | "बदलदे रौह्ने आह्ले " |  |
| `au_rotating_emph` | codes. | कोड। |  |
| `au_save_key` | Save Key | कुंजी संभालो |  |
| `au_scan_qr` | Scan a QR code | QR कोड स्कैन करो |  |
| `au_scan_title` | Scan Authenticator QR | प्रमाणक दा QR स्कैन करो |  |
| `au_search_hint` | Search codes, issuers… | कोड जां देने आह्ले तोपो… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | जिय्यां JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | सिक्रेट कुंजी (Base32) |  |
| `au_tap_to_copy` | Tap to copy | कॉपी करने आस्तै टैप करो |  |
| `cat_apps` | Apps &amp; Logins | एपां ते लॉगिन |  |
| `cat_banks` | Banks &amp; UPI | बैंक ते UPI |  |
| `cat_cards` | Cards | कार्ड |  |
| `cat_govid` | Gov &amp; ID | सरकारी ते पछान |  |
| `cat_investments` | Investments | निवेश |  |
| `cat_utilities` | Utilities | बिल ते कनेक्शन |  |
| `cd_mask_hidden` | hidden | लकोया दा |  |
| `cd_shield_high_sensitivity` | extra-protected field | मती संवेदनशील जानकारी |  |
| `gl_blank` | Blank template | खाली टेम्पलेट |  |
| `gl_cat_apps` | Apps | एपां |  |
| `gl_cat_banks` | Banks | बैंक |  |
| `gl_cat_cards` | Cards | कार्ड |  |
| `gl_cat_demat` | Demat | डीमैट |  |
| `gl_cat_govid` | Gov ID | सरकारी पछान |  |
| `gl_cat_popular` | Popular | मशहूर |  |
| `gl_cat_shopping` | Shopping | खरीददारी |  |
| `gl_cat_travel` | Travel | सफर |  |
| `gl_cat_upi` | UPI | UPI |  |
| `gl_cat_utilities` | Utilities | बिल |  |
| `gl_head_emph` | storing? | संभालने आं? |  |
| `gl_head_lead` | "What are we " | "अस केह् " |  |
| `gl_matches` | %1$d matches | %1$d लब्भे |  |
| `gl_most_used` | Most-used first | सारें थमां मते बरते दे पैह्लें |  |
| `gl_not_found` | Can’t find a service? | सेवा नेईं लब्भा करदी? |  |
| `gl_search` | Search %1$d Indian services… | %1$d भारती सेवाएं च तोपो… |  |
| `gl_suggested` | Suggested for you | तुंदे आस्तै सलाह |  |
| `hm_add_first` | Add your first record | अपनी पैह्ली एंट्री जोड़ो |  |
| `hm_all_offline` | all offline. | सारा कुछ ऑफलाइन। |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d चीजां, " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d चीज, " |  |
| `hm_detail_placeholder` | Pick a record to see it here | इत्थें दिक्खने आस्तै इक रिकार्ड चुनो |  |
| `hm_empty_blank` | A blank vault, ready. | खाली भंडार, तैयार। |  |
| `hm_empty_head_emph` | waiting. | उडीका करदा ऐ। |  |
| `hm_empty_head_lead` | "Your vault is " | "तुंदा भंडार " |  |
| `hm_filter_all` | All | सारे |  |
| `hm_import_backup` | Import an encrypted backup | एन्क्रिप्ट कीता बैकअप आयात |  |
| `hm_import_backup_note` | Open a .kosh file from this device | इसै फोनै थमां .kosh फाइल खोह्लो |  |
| `hm_inst_many` | %1$d institutions | %1$d संस्थां |  |
| `hm_inst_one` | %1$d institution | %1$d संस्था |  |
| `hm_kit_banner_action` | Save one now | हुनै संभालो |  |
| `hm_kit_banner_dismiss` | Remind me later | बाद च याद करांदे रौह् |  |
| `hm_kit_banner_title` | No recovery kit saved | कोई रिकवरी किट नेईं संभाली दी |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | खुल्ला ऐ · छड्डदे गै बंद होई जाग |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | खुल्ला ऐ · छड्डने दे %1$d मिंट बाद बंद |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | खुल्ला ऐ · छड्डने दे 1 मिंट बाद बंद |  |
| `hm_no_match` | Nothing matches “%1$s” | “%1$s” आस्तै कुछ नेईं लब्भा |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | कोई संस्था, UPI हैंडल, जां खीरी चार अंक अजमाओ। |  |
| `hm_pinned` | Pinned | पिन कीते दे |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC, priya@upi, PAN… तोपो |  |
| `hm_start_template` | Start with a template | टेम्पलेट थमां शुरू करो |  |
| `ic_could_not` | Could not import | आयात नेईं होई सकेआ |  |
| `ic_done` | Done | होई गेआ |  |
| `ic_import` | Import | आयात |  |
| `ic_imported` | Imported | आयात होई गेआ |  |
| `ic_importing` | Importing… | आयात होआ करदा ऐ… |  |
| `ic_new_many` | %1$d new logins. | %1$d नमें लॉगिन। |  |
| `ic_new_one` | %1$d new login. | %1$d नमां लॉगिन। |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d जुड़े, %2$d बदले। |  |
| `ic_title` | Import from %1$s? | %1$s थमां आयात करचै? |  |
| `ic_too_large` | That file is too large to be a credential export. | एह् फाइल पासवर्ड निर्यात होने आस्तै मती बड्डी ऐ। |  |
| `import_action` | Import | आयात |  |
| `import_locked` | Unlock your vault before importing. | आयात करने थमां पैह्लें अपना भंडार खोह्लो। |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d जुड़े, %2$d बदले। कुछ बी मिटाया नेईं गेदा। |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | ओह् फाइल Zerokosh भंडार दे रूप च नेईं पढ़ी जाई सकी। |  |
| `import_nothing_new` | Everything in that backup was already here. | ओस बैकअपै च जे हा, ओह् सारा पैह्लें गै इत्थै हा। |  |
| `import_passphrase_label` | Backup passphrase | बैकअप दा पासफ्रेज |  |
| `import_title` | Import a backup | बैकअप आयात करो |  |
| `kicker_locked` | Locked | बंद ऐ |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · कुछ बी इस फोनै थमां बाह्र नेईं गेआ |  |
| `lk_touch_unlock` | Touch to unlock | खोह्लने आस्तै छूहो |  |
| `lk_welcome_emph` | Your vault is sealed. | तुंदा भंडार बंद ऐ। |  |
| `lk_welcome_lead` | Welcome back. | फ्ही जी आया नूं। |  |
| `msg_auth_needed` | Confirm it\'s you to see this | दिक्खने आस्तै पक्का करो जे एह् तुस गै ओ |  |
| `msg_back` | Back | पिच्छें |  |
| `msg_cancel` | Cancel | रद्द |  |
| `msg_file_damaged` | File damaged — restored from backup | फाइल खराब होई दी ही — बैकअपै थमां ठीक करी दित्ती |  |
| `msg_ok` | OK | ठीक ऐ |  |
| `msg_saved` | Saved | संभाली लेदा |  |
| `nav_all_templates` | All templates | सारे टेम्पलेट |  |
| `nav_damaged_emph` | vault file | भंडार फाइलै च |  |
| `nav_damaged_kicker` | Damaged state | खराब हालत |  |
| `nav_damaged_lead` | "Something in the " | "तुंदी " |  |
| `nav_damaged_tail` | " is off." | " कुछ गड़बड़ी ऐ।" |  |
| `nav_integrity_title` | Integrity check failed | अखंडता दी जांच नाकाम होई |  |
| `nav_scan` | Scan | स्कैन |  |
| `nav_tap_card` | Tap a card | कार्ड टैप करो |  |
| `nav_what_next` | What to do next | हुन अग्गें केह् |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC बंद ऐ। सेटिंग च चालू करियै फ्ही कोशश करो। |  |
| `nfc_hold_card` | Hold your card to the phone | कार्ड फोनै कन्नै लाइयै रक्खो |  |
| `nfc_missed` | Did not catch that | पकड़ च नेईं आया |  |
| `nfc_read_failed` | That card could not be read. Try again. | ओह् कार्ड नेईं पढ़ेआ जाई सकेआ। फ्ही कोशश करो। |  |
| `nfc_reading` | Reading… | पढ़ा करने आं… |  |
| `nfc_try_again` | Try again | फ्ही कोशश करो |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · इसै फोन उप्पर मापेआ |  |
| `ob_argon_faster` | Faster unlock | छेती खुल्लग |  |
| `ob_argon_harder` | Harder to attack | तोड़ना औखा |  |
| `ob_argon_measuring` | Measuring this device… | एह् फोन मापा करने आं… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id कठोरता |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | शब्दकोश दा इक्को शब्द नेईं |  |
| `ob_check_pass_length` | 10 characters or more | 10 जां मते अक्षर |  |
| `ob_check_pass_reuse` | Not reused from another app | होर एपै थमां फ्ही नेईं बरतेआ |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | ना जन्मदिन, ना सालगिरह |  |
| `ob_check_pin_digits` | All six digits entered | छेई अंक भरे गे |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | ना लगातार अंक, ना दोहराए दे |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~तोड़ने आस्तै %1$d सदियां |  |
| `ob_crack_days` | ~%1$d days to crack | ~तोड़ने आस्तै %1$d दिन |  |
| `ob_crack_forever` | longer than the sun | सूरजै थमां बी मता बेल्ला |  |
| `ob_crack_hours` | ~hours to crack | ~तोड़ने आस्तै किश घंटे |  |
| `ob_crack_seconds` | ~seconds to crack | ~तोड़ने आस्तै किश सैकेंड |  |
| `ob_crack_years` | ~%1$d years to crack | ~तोड़ने आस्तै %1$d बरे |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | प्रमाणित, हर भंडार आस्तै बक्खरा नॉन्स |  |
| `ob_fact_encryption_title` | Encryption | एन्क्रिप्शन |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | सेटअप दे बेल्लै तुंदे फोन उप्पर मापेआ |  |
| `ob_fact_kdf_title` | Key stretching | कुंजी स्ट्रेचिंग |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | रिसेट लिंक नेईं। सपोर्ट दा पिछला दरवाजा बी नेईं। |  |
| `ob_fact_lost_value` | Nobody can recover it | कोई वापस नेईं आनी सकदा |  |
| `ob_fact_network_title` | Network permission | नेटवर्क दी इजाजत |  |
| `ob_fact_network_value` | Not requested | कदें मंगी गै नेईं |  |
| `ob_fact_quick_title` | Quick unlock | छेती अनलॉक |  |
| `ob_fact_quick_value` | Hardware keystore | हार्डवेयर कीस्टोर |  |
| `ob_lang_continue` | Continue in %1$s | %1$s च अग्गें चलो |  |
| `ob_lang_head_emph` | language. | बोली चुनो। |  |
| `ob_lang_head_lead` | "Choose your " | "अपनी " |  |
| `ob_lang_search` | Search %1$d languages | %1$d बोलियें च तोपो |  |
| `ob_quick_continue_pass` | Continue with passphrase | पासफ्रेज कन्नै अग्गें चलो |  |
| `ob_quick_enable` | Enable quick unlock | छेती अनलॉक चालू करो |  |
| `ob_quick_fingerprint` | Fingerprint | उंगली दा निशान |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | तेज, हार्डवेयर कन्नै सुरक्षित अनलॉक। |  |
| `ob_quick_head_emph` | Without the cloud. | बिना क्लाउडै दे। |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "इक छूह् कन्नै खुल्लग। " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox। कोई बी बायोमेट्रिक जानकारी कदें Zerokosh तकर नेईं पुज्जदी। |  |
| `ob_quick_hw_title` | Hardware-backed | हार्डवेयर कन्नै सुरक्षित |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | इस फोन च हार्डवेयर सेंसर नेईं ऐ। |  |
| `ob_quick_opening` | Opening your vault… | तुंदा भंडार खुल्ला करदा ऐ… |  |
| `ob_quick_pass_only` | Passphrase only | सिर्फ पासफ्रेज |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | हर बारी लिखो। सारें थमां सुरक्षित। |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | छेती अनलॉक नेईं लग्गा। फ्ही कोशश करो, जां पासफ्रेज कन्नै गै अग्गें चलो। |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | हुन नेईं — मैं पासफ्रेज लिखगा |  |
| `ob_quick_touch_title` | Touch the sensor | सेंसर गी छूहो |  |
| `ob_recommended` | Recommended | सलाह |  |
| `ob_reveal_hide` | Hide | लकाओ |  |
| `ob_reveal_show` | Show | दस्सो |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | लपेटने आह्ली कुंजी हार्डवेयर कीस्टोरै च रौह्ग। बायोमेट्रिक अगले चरण च। |  |
| `ob_seal_title` | Seal to this device | इसै फोन कन्नै बन्नो |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | इस फोन च हार्डवेयर बायोमेट्रिक नेईं ऐ। |  |
| `ob_soon` | SOON | छेती |  |
| `ob_step_label` | Step %1$d of 6 | चरण %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | कुछ इस चाल्ली चुनो जेह्ड़ा सिर्फ तुस गै आखदे ओ |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | खरा · %1$d बिट एन्ट्रोपी |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | मता छोटा · 10 अक्षर चाहिदे |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | मजबूत · %1$d बिट एन्ट्रोपी |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | कमजोर · %1$d बिट एन्ट्रोपी |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | छे अंक जेह्ड़े तुंदी जिंदगी दिक्खियै कोई अंदाजा नेईं लाई सकै |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | ठीक-ठाक · %1$d बिट — पिन इस थमां मता मजबूत नेईं होई सकदा |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | मता छोटा · 6 अंक चाहिदे |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | कमजोर · एह्यो पिन सारें थमां पैह्लें अजमाए जंदे न |  |
| `ob_try_label` | TRY | अजमाओ |  |
| `qa_aadhaar` | Aadhaar | आधार |  |
| `qa_bank_account` | Bank account | बैंक खाता |  |
| `qa_pan` | PAN | PAN |  |
| `qa_upi_id` | UPI ID | UPI ID |  |
| `rd_copied` | Copied | कॉपी होई गेई |  |
| `rd_forget` | Forget | भुल्ली जाओ |  |
| `rd_forget_these` | Forget these | एह् भुल्ली जाओ |  |
| `rd_forget_title` | Forget previous passwords? | पराने पासवर्ड भुल्ली जाचै? |  |
| `rd_history_hide` | Hide | लकाओ |  |
| `rd_history_show` | Show %1$d | %1$d दस्सो |  |
| `rd_hold_to_reveal` | Hold to reveal | दिक्खने आस्तै दबाइयै रक्खो |  |
| `rd_last_edit` | last edit %1$s | खीरी बारी %1$s बदलेआ |  |
| `rd_release_to_hide` | Release to hide | लकाने आस्तै छड्डो |  |
| `re_add_field` | + Add another field | + होर इक खाना जोड़ो |  |
| `re_add_field_title` | Add a field | खाना जोड़ो |  |
| `re_field_name` | Field name | खाने दा नां |  |
| `re_pick_date` | Pick a date | तरीक चुनो |  |
| `re_remove` | Remove | हटाओ |  |
| `re_tap_card` | Read the card by tapping it | पढ़ने आस्तै कार्ड टैप करो |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | भेद मन्नो (लकोया दा रौह्ग, दिक्खने आस्तै दबाइयै रक्खो) |  |
| `re_using_template` | using the %1$s template | %1$s टेम्पलेट बरतियै |  |
| `rem_kit_title` | No recovery kit saved | कोई रिकवरी किट नेईं संभाली दी |  |
| `scr_about_license` | License: GPL-3.0 — free forever | लाइसेंस: GPL-3.0 — हमेशा मुफ्त |  |
| `scr_about_source` | Source code | स्रोत कोड |  |
| `scr_about_version` | Version %1$s | संस्करण %1$s |  |
| `scr_auth_add_qr` | Add via QR | QR कन्नै जोड़ो |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | तुंदे एपें ते ब्रोकरै दे कोड इत्थै दिक्खने |  |
| `scr_auth_scan_title` | Point the camera at the QR code | कैमरा QR कोडै दे उप्पर रक्खो |  |
| `scr_detail_copied` | Copied · clears in 30s | कॉपी होई गेई · 30 सैकेंडें च मिटी जाग |  |
| `scr_detail_copy` | Copy | कॉपी करो |  |
| `scr_detail_edit` | Edit | बदलो |  |
| `scr_detail_favorite` | Favourite | पसंदीदा |  |
| `scr_detail_hidden` | Hidden | लकोया दा |  |
| `scr_detail_hide` | Hide | लकाओ |  |
| `scr_detail_history_empty` | Nothing replaced yet. | हजें तकर कुछ नेईं बदलेआ। |  |
| `scr_detail_history_title` | Previous passwords | पराने पासवर्ड |  |
| `scr_detail_reveal` | Show | दस्सो |  |
| `scr_detail_shown` | Shown | दिक्खा करदा ऐ |  |
| `scr_edit_cancel` | Cancel | रद्द |  |
| `scr_edit_generate` | Generate | बनाओ |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | बैंक / कंपनी (इकट्ठा रखने आस्तै) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | एह् ठीक नेईं लगदा — इक बारी दिक्खी लैओ |  |
| `scr_edit_link_none` | None | कुछ नेईं |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | एह् कार्ड नंबर आम जांच च पास नेईं होंदा — ठीक ऐ तां संभाली लैओ |  |
| `scr_edit_month` | Month | म्हीना |  |
| `scr_edit_picker_other` | Other… | होर… |  |
| `scr_edit_picker_other_hint` | Type your own | अपना लिखो |  |
| `scr_edit_required_title` | Give it a name first | पैह्लें इसनूं इक नां देओ |  |
| `scr_edit_save` | Save | संभालो |  |
| `scr_edit_title_hint` | Title | नां |  |
| `scr_edit_title_new` | New | नमां |  |
| `scr_edit_year` | Year | बरा |  |
| `scr_gallery_quick_add` | Quick add | छेती जोड़ो |  |
| `scr_gallery_title` | What do you want to save? | अस केह् संभालने आं? |  |
| `scr_home_add` | Add | जोड़ो |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | तुंदा बैंक खाता इस चाल्ली दिक्खग |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | तुंदे कार्ड, UPI, एप लॉगिन — सारा कुछ इत्थै गै |  |
| `scr_home_group_other` | Other | होर |  |
| `scr_home_no_results` | Nothing matches your search | तुंदी तोपा-तोपी च कुछ नेईं लब्भा |  |
| `scr_home_search_hint` | Search your vault | अपने भंडारै च तोपो |  |
| `scr_home_tab_authenticator` | Authenticator | कोड |  |
| `scr_home_tab_home` | Home | घर |  |
| `scr_home_tab_settings` | Settings | सेटिंग |  |
| `scr_home_title` | Home | घर |  |
| `scr_language_continue` | Continue | अग्गें चलो |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | अपनी बोली चुनो |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | कॉपी करने आस्तै बटन दबाओ · 30 सैकेंडें च मिटी जाग |  |
| `scr_login_helper_channel` | Login helper | लॉगिन मददगार |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s च लॉगिन होआ करदा ऐ |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | हुनै बैकअप फोल्डर लाई लैओ |  |
| `scr_quickunlock_enable` | Turn on | चालू करो |  |
| `scr_quickunlock_skip` | Not now | हुन नेईं |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | उंगली जां मुंह कन्नै खोह्लो |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s दी तरीक नेड़े ऐ · Zerokosh खोह्लो |  |
| `scr_reminder_channel` | Renewal reminders | नवीकरण दी याद |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh याद करांदा ऐ |  |
| `scr_settings_about` | About | बारे च |  |
| `scr_settings_allow_screenshots` | Allow screenshots | स्क्रीनशॉट लैने देओ |  |
| `scr_settings_autofill` | Autofill service | ऑटोफिल सेवा |  |
| `scr_settings_autofill_off` | Not set up | लाया नेईं गेदा |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | उपलब्ध नेईं |  |
| `scr_settings_autolock` | Lock when I leave the app | एप छड्डदे गै बंद करो |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 मिंट बाद |  |
| `scr_settings_autolock_immediately` | Immediately | तुरत |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d मिंट बाद |  |
| `scr_settings_change_passphrase` | Change passphrase | पासफ्रेज बदलो |  |
| `scr_settings_current_passphrase` | Current passphrase | हुनै दा पासफ्रेज |  |
| `scr_settings_export` | Export | निर्यात करो |  |
| `scr_settings_import` | Import passwords | पासवर्ड आयात करो |  |
| `scr_settings_language` | Language | बोली |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | नमां पासफ्रेज (घट्टोघट्ट 10 अक्षर) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | नमीं रिकवरी कुंजी लैओ |  |
| `scr_settings_passphrase_changed` | Passphrase changed | पासफ्रेज बदली गेआ |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | उंगली / मुंह अनलॉक |  |
| `scr_settings_security_info` | How your data is protected | तुंदा डेटा किय्यां सुरक्षित ऐ |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | बैकअप ते सिंक फोल्डर |  |
| `scr_settings_sync_not_set` | Not backed up | बैकअप नेईं |  |
| `scr_settings_title` | Settings | सेटिंग |  |
| `se_title` | Not saved | संभाली नेईं गेदा |  |
| `st_active_folder` | Active Folder | चालू फोल्डर |  |
| `st_active_value` | Active · %1$s | चालू · %1$s |  |
| `st_backing_up` | Backing up vault… | भंडार दा बैकअप लैया करने आं… |  |
| `st_backup_now` | Backup Now | हुनै बैकअप लैओ |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | बैकअप ते सिंक फोल्डर |  |
| `st_change_folder` | Change Folder | फोल्डर बदलो |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | नमां पासफ्रेज फ्ही लिखो |  |
| `st_connected_folder` | Connected folder: %1$s | जुड़े दा फोल्डर: %1$s |  |
| `st_disconnect` | Disconnect | हटाओ |  |
| `st_done` | Done | होई गेआ |  |
| `st_export_kosh` | Export encrypted .kosh | एन्क्रिप्ट कीता .kosh निर्यात |  |
| `st_folder_fallback` | Folder | फोल्डर |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | पासफ्रेज भुल्ली गे? |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | अपनी उंगली कन्नै नमां रक्खो |  |
| `st_generate` | Generate | बनाओ |  |
| `st_group_about` | About | बारे च |  |
| `st_group_appearance` | Appearance | रूप |  |
| `st_group_security` | Security | सुरक्षा |  |
| `st_group_sync` | Sync | सिंक |  |
| `st_import_kosh` | Import a .kosh backup | .kosh बैकअप आयात |  |
| `st_import_other` | Import from another password manager | होर पासवर्ड मैनेजर थमां आयात |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | लाइसेंस |  |
| `st_logos_by` | Logos provided by | लोगो देने आह्ले |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | इसनूं ऑफलाइन रक्खो। पराणी रिकवरी कुंजी हुन नेईं चलदी। |  |
| `st_new_recovery_result` | Your new Recovery Key: | तुंदी नमीं रिकवरी कुंजी: |  |
| `st_subtitle` | Your rules. | तुंदे नियम। |  |
| `st_theme` | Theme | थीम |  |
| `st_theme_dark` | Dark | न्हेरा |  |
| `st_theme_light` | Light | चानण |  |
| `st_theme_system` | System | सिस्टम |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | भंडार %1$s च संभाली लेदा! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | बैकअप नेईं होआ — फोल्डर दी इजाजत दिक्खो |  |
| `st_toast_disconnected` | Backup folder disconnected | बैकअप फोल्डर हटाई दित्ता |  |
| `st_toast_export_failed` | Export failed | निर्यात नेईं होआ |  |
| `st_toast_exported` | Encrypted vault exported | एन्क्रिप्ट कीता भंडार निर्यात होई गेआ |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | बैकअप फोल्डर जुड़ी गेआ, ते भंडार %1$s च संभाली लेदा! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | बैकअप फोल्डर जुड़ी गेआ: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | फोल्डर नेईं जुड़ी सकेआ: %1$s |  |
| `st_vault_review` | Vault review | भंडार दी जांच |  |
| `st_vault_review_detail` | Reused, weak, expiring | फ्ही बरते दे, कमजोर, मियाद खत्म होंदे |  |
| `tab_codes` | Codes | कोड |  |
| `tab_settings` | Settings | सेटिंग |  |
| `tab_templates` | Templates | टेम्पलेट |  |
| `tab_vault` | Vault | भंडार |  |
| `time_days` | %1$dd ago | %1$d दिन पैह्लें |  |
| `time_hours` | %1$dh ago | %1$d घंटे पैह्लें |  |
| `time_just_now` | just now | हुनै |  |
| `time_minutes` | %1$dm ago | %1$d मिंट पैह्लें |  |
| `time_months` | %1$dmo ago | %1$d म्हीने पैह्लें |  |
| `time_years` | %1$dy ago | %1$d बरे पैह्लें |  |
| `tpl_aadhaar_card` | Aadhaar Card | आधार |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | आधार नंबर |  |
| `tpl_aadhaar_card_address` | Address | आधार उप्पर पता |  |
| `tpl_aadhaar_card_dob` | Dob | जन्म तरीक |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | स्कैन कीती नकल |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | जुड़े दा मोबाइल |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar पासकोड |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | आधार उप्पर नां |  |
| `tpl_aadhaar_card_notes` | Notes | टिप्पणी |  |
| `tpl_app_profile` | App Profile | एप प्रोफाइल |  |
| `tpl_app_profile_app_name` | App name | एप दा नां |  |
| `tpl_app_profile_gift_cards` | Gift cards | गिफ्ट कार्ड |  |
| `tpl_app_profile_membership` | Membership | सदस्यता |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | सदस्यता नवीकरण |  |
| `tpl_app_profile_notes` | Notes | टिप्पणी |  |
| `tpl_app_profile_password_if_any` | Password (if any) | पासवर्ड (जेकर होए) |  |
| `tpl_app_profile_registered_email` | Registered email | दर्ज ईमेल |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | दर्ज मोबाइल |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_bank_account` | Bank Account | बैंक खाता |  |
| `tpl_bank_account_account_number` | Account number | खाता नंबर |  |
| `tpl_bank_account_account_type` | Account type | खाते दी किस्म |  |
| `tpl_bank_account_bank_name` | Bank name | बैंक दा नां |  |
| `tpl_bank_account_branch` | Branch | शाखा |  |
| `tpl_bank_account_customer_id` | Customer id | ग्राहक ID |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC कोड |  |
| `tpl_bank_account_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_bank_account_micr` | MICR code | MICR कोड |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | नेट-बैंकिंग यूजर ID |  |
| `tpl_bank_account_nominee` | Nominee | नामजद |  |
| `tpl_bank_account_notes` | Notes | टिप्पणी |  |
| `tpl_bank_account_profile_password` | Profile password | प्रोफाइल पासवर्ड |  |
| `tpl_bank_account_registered_email` | Registered email | दर्ज ईमेल |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | दर्ज मोबाइल |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | लैन-देन दा पासवर्ड |  |
| `tpl_card` | Card | कार्ड |  |
| `tpl_card_atm_pin` | ATM PIN | ATM पिन |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | बिलिंग चक्कर दा दिन |  |
| `tpl_card_card_network` | Card network | नेटवर्क |  |
| `tpl_card_card_number` | Card number | कार्ड नंबर |  |
| `tpl_card_card_portal_login` | Card portal login | कार्ड पोर्टल लॉगिन |  |
| `tpl_card_card_portal_password` | Card portal password | कार्ड पोर्टल पासवर्ड |  |
| `tpl_card_card_type` | Card type | कार्ड दी किस्म |  |
| `tpl_card_card_variant` | Card variant | कार्ड वेरिएंट |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | मियाद |  |
| `tpl_card_linked_account` | Linked account | जुड़े दा खाता |  |
| `tpl_card_name_on_card` | Name on card | कार्ड उप्पर नां |  |
| `tpl_card_notes` | Notes | टिप्पणी |  |
| `tpl_demat` | Demat | डीमैट |  |
| `tpl_demat_api_key` | API key | API कुंजी |  |
| `tpl_demat_api_secret` | API secret | API सिक्रेट |  |
| `tpl_demat_bo_id` | BO ID | BO ID |  |
| `tpl_demat_broker` | Broker | ब्रोकर |  |
| `tpl_demat_cdsl_tpin` | CDSL TPIN | CDSL TPIN |  |
| `tpl_demat_client_id` | Client id | क्लाइंट ID |  |
| `tpl_demat_depository` | Depository | डिपॉजिटरी |  |
| `tpl_demat_dp_id` | DP ID | DP ID |  |
| `tpl_demat_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_demat_mf_folios` | Mutual fund folios | म्यूचुअल फंड फोलियो |  |
| `tpl_demat_mpin` | MPIN | MPIN |  |
| `tpl_demat_nominee` | Nominee | नामजद |  |
| `tpl_demat_notes` | Notes | टिप्पणी |  |
| `tpl_digilocker` | Digilocker | DigiLocker |  |
| `tpl_digilocker_login_username` | Login username | यूजरनेम |  |
| `tpl_digilocker_notes` | Notes | टिप्पणी |  |
| `tpl_digilocker_portal_password` | Portal password | पासवर्ड |  |
| `tpl_digilocker_security_pin` | Security pin | सुरक्षा पिन |  |
| `tpl_driving_license` | Driving License | ड्राइविंग लाइसेंस |  |
| `tpl_driving_license_dl_number` | Dl number | लाइसेंस नंबर |  |
| `tpl_driving_license_dob` | Dob | जन्म तरीक |  |
| `tpl_driving_license_expiry_date` | Expiry date | इस तरीक तकर वैध |  |
| `tpl_driving_license_file_copy` | Scanned copy | स्कैन कीती नकल |  |
| `tpl_driving_license_issue_date` | Issue date | जारी होने दी तरीक |  |
| `tpl_driving_license_name_on_dl` | Name on dl | लाइसेंस उप्पर नां |  |
| `tpl_driving_license_notes` | Notes | टिप्पणी |  |
| `tpl_driving_license_rto_location` | Rto location | RTO |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | गड्डियें दी श्रेणी |  |
| `tpl_epf_pension` | Epf Pension | EPF / पेंशन |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | जुड़े दा मोबाइल |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | EPF उप्पर नां |  |
| `tpl_epf_pension_nominee` | Nominee | नामजद |  |
| `tpl_epf_pension_notes` | Notes | टिप्पणी |  |
| `tpl_epf_pension_password` | Password | पासवर्ड |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | PF सदस्य ID |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO पासवर्ड |  |
| `tpl_epf_pension_scheme` | Scheme | योजना |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | सरकारी पछान पत्तर |  |
| `tpl_gov_id_expiry` | Expiry | मियाद |  |
| `tpl_gov_id_file_copy` | Scanned copy | स्कैन कीती नकल |  |
| `tpl_gov_id_id_kind` | ID type | पछान पत्तर दी किस्म |  |
| `tpl_gov_id_id_number` | ID number | पछान नंबर |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | पत्तर मताबक नां |  |
| `tpl_gov_id_notes` | Notes | टिप्पणी |  |
| `tpl_gov_id_portal_login` | Portal login | पोर्टल लॉगिन |  |
| `tpl_gov_id_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_insurance` | Insurance | बीमा |  |
| `tpl_insurance_agent_contact` | Agent contact | एजेंट दा संपर्क |  |
| `tpl_insurance_commencement_date` | Commencement date | शुरू होने दी तरीक |  |
| `tpl_insurance_insurer` | Insurer | बीमा कंपनी |  |
| `tpl_insurance_maturity_date` | Maturity date | मियाद पूरी होने दी तरीक |  |
| `tpl_insurance_nominee` | Nominee | नामजद |  |
| `tpl_insurance_notes` | Notes | टिप्पणी |  |
| `tpl_insurance_policy_number` | Policy number | पॉलिसी नंबर |  |
| `tpl_insurance_policy_term` | Policy term | पॉलिसी दी मियाद |  |
| `tpl_insurance_policy_type` | Policy type | पॉलिसी दी किस्म |  |
| `tpl_insurance_portal_login` | Portal login | पोर्टल लॉगिन |  |
| `tpl_insurance_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_insurance_premium_amount` | Premium amount | प्रीमियम रकम |  |
| `tpl_insurance_premium_due_date` | Premium due date | प्रीमियम दी तरीक |  |
| `tpl_insurance_premium_mode` | Premium mode | प्रीमियम किय्यां भरदे ओ |  |
| `tpl_insurance_sum_assured` | Sum assured | बीमे दी रकम |  |
| `tpl_login` | Login | लॉगिन |  |
| `tpl_login_notes` | Notes | टिप्पणी |  |
| `tpl_login_password` | Password | पासवर्ड |  |
| `tpl_login_recovery_codes` | Recovery codes | रिकवरी कोड |  |
| `tpl_login_username` | Username | यूजरनेम |  |
| `tpl_login_website` | Website | वेबसाइट |  |
| `tpl_pan_card` | Pan Card | PAN कार्ड |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | आधार कन्नै जुड़े दा |  |
| `tpl_pan_card_dob` | Dob | जन्म तरीक |  |
| `tpl_pan_card_e_filing_password` | E filing password | ई-फाइलिंग पासवर्ड |  |
| `tpl_pan_card_fathers_name` | Fathers name | पिता दा नां |  |
| `tpl_pan_card_file_copy` | Scanned copy | स्कैन कीती नकल |  |
| `tpl_pan_card_name_on_pan` | Name on pan | PAN उप्पर नां |  |
| `tpl_pan_card_notes` | Notes | टिप्पणी |  |
| `tpl_pan_card_pan_number` | Pan number | PAN |  |
| `tpl_passkey` | Passkey | पासकी |  |
| `tpl_passkey_credential_id` | Credential ID | क्रेडेंशियल ID |  |
| `tpl_passkey_notes` | Notes | टिप्पणी |  |
| `tpl_passkey_private_key` | Private key | निजी कुंजी |  |
| `tpl_passkey_sign_count` | Sign count | साइन काउंट |  |
| `tpl_passkey_user_handle` | User handle | यूजर हैंडल |  |
| `tpl_passkey_username` | Username | यूजरनेम |  |
| `tpl_passkey_website` | Website | वेबसाइट |  |
| `tpl_passport` | Passport | पासपोर्ट |  |
| `tpl_passport_dob` | Dob | जन्म तरीक |  |
| `tpl_passport_expiry_date` | Expiry date | मियाद खत्म होने दी तरीक |  |
| `tpl_passport_file_copy` | Scanned copy | स्कैन कीती नकल |  |
| `tpl_passport_given_names` | Given names | दित्ता नां |  |
| `tpl_passport_issue_date` | Issue date | जारी होने दी तरीक |  |
| `tpl_passport_notes` | Notes | टिप्पणी |  |
| `tpl_passport_passport_number` | Passport number | पासपोर्ट नंबर |  |
| `tpl_passport_place_of_issue` | Place of issue | जारी होने दी थाहर |  |
| `tpl_passport_portal_login` | Portal login | Passport Seva लॉगिन |  |
| `tpl_passport_portal_password` | Portal password | Passport Seva पासवर्ड |  |
| `tpl_passport_surname` | Surname | गोत |  |
| `tpl_secure_note` | Secure Note | सुरक्षित टिप्पणी |  |
| `tpl_secure_note_attachment` | Attachment | नत्थी |  |
| `tpl_secure_note_body` | Note | टिप्पणी |  |
| `tpl_shopping` | Shopping | खरीददारी खाता |  |
| `tpl_shopping_gift_card_code` | Gift card code | गिफ्ट कार्ड कोड |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | गिफ्ट कार्ड पिन |  |
| `tpl_shopping_membership_id` | Membership id | सदस्यता ID |  |
| `tpl_shopping_notes` | Notes | टिप्पणी |  |
| `tpl_shopping_password` | Password | पासवर्ड |  |
| `tpl_shopping_registered_email` | Registered email | दर्ज ईमेल |  |
| `tpl_shopping_registered_mobile` | Registered mobile | दर्ज मोबाइल |  |
| `tpl_shopping_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_telecom` | Telecom | मोबाइल ते इंटरनेट |  |
| `tpl_telecom_account_number` | Account number | खाता नंबर |  |
| `tpl_telecom_circle` | Circle | सर्कल |  |
| `tpl_telecom_mobile_number` | Mobile number | मोबाइल नंबर |  |
| `tpl_telecom_notes` | Notes | टिप्पणी |  |
| `tpl_telecom_operator` | Operator | कंपनी |  |
| `tpl_telecom_plan_type` | Plan type | प्लान दी किस्म |  |
| `tpl_telecom_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_telecom_puk` | PUK code | PUK कोड |  |
| `tpl_telecom_renewal_date` | Renewal date | रिचार्ज दी तरीक |  |
| `tpl_telecom_sim_number` | Sim number | सिम नंबर (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | सिम पिन |  |
| `tpl_transit` | Transit | सफर पास |  |
| `tpl_transit_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_transit_notes` | Notes | टिप्पणी |  |
| `tpl_transit_operator_name` | Operator name | कंपनी |  |
| `tpl_transit_registered_email` | Registered email | दर्ज ईमेल |  |
| `tpl_transit_registered_mobile` | Registered mobile | दर्ज मोबाइल |  |
| `tpl_transit_smart_card_number` | Smart card number | स्मार्ट कार्ड नंबर |  |
| `tpl_transit_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_travel_booking` | Travel Booking | सफर दी बुकिंग |  |
| `tpl_travel_booking_account_username` | Account username | यूजरनेम |  |
| `tpl_travel_booking_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_travel_booking_notes` | Notes | टिप्पणी |  |
| `tpl_travel_booking_provider` | Provider | कंपनी |  |
| `tpl_travel_booking_registered_email` | Registered email | दर्ज ईमेल |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | दर्ज मोबाइल |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_upi` | UPI | UPI |  |
| `tpl_upi_app_name` | App name | UPI एप |  |
| `tpl_upi_apps_used` | Apps used | कुस एपै च चालू |  |
| `tpl_upi_linked_account` | Linked account | जुड़े दा खाता |  |
| `tpl_upi_notes` | Notes | टिप्पणी |  |
| `tpl_upi_upi_id` | UPI ID | UPI ID |  |
| `tpl_upi_upi_pin` | UPI PIN | UPI पिन |  |
| `tpl_utility` | Utility | बिल ते कनेक्शन |  |
| `tpl_utility_account_holder` | Account holder | खाताधारक |  |
| `tpl_utility_consumer_number` | Consumer number | उपभोक्ता नंबर |  |
| `tpl_utility_due_day` | Bill due day | बिल भरने दा दिन |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag ID |  |
| `tpl_utility_notes` | Notes | टिप्पणी |  |
| `tpl_utility_portal_login` | Portal login | पोर्टल लॉगिन |  |
| `tpl_utility_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_utility_provider` | Provider | सेवा देने आह्ली कंपनी |  |
| `tpl_utility_utility_kind` | Utility kind | कुस चीजै दा बिल |  |
| `tpl_utility_vehicle_number` | Vehicle number | गड्डी नंबर |  |
| `tpl_utility_wifi_password` | Wi-Fi password | Wi-Fi पासवर्ड |  |
| `tpl_voter_id` | Voter Id | वोटर पछान पत्तर |  |
| `tpl_voter_id_constituency` | Constituency | हलका |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC नंबर |  |
| `tpl_voter_id_file_copy` | Scanned copy | स्कैन कीती नकल |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | वोटर कार्ड उप्पर नां |  |
| `tpl_voter_id_notes` | Notes | टिप्पणी |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP पासवर्ड |  |
| `tr_days_many` | %1$d days left | %1$d दिन बचे दे |  |
| `tr_days_one` | %1$d day left | %1$d दिन बचे दे |  |
| `tr_gone_today` | gone today | अज्ज चली जाग |  |
| `tr_restore` | Restore | वापस आनो |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | ओस दे बाद ओह् हमेशा आस्तै चली जंदियां न — होर कुतै कोई नकल नेईं। |  |
| `ui_hide_passphrase` | Hide passphrase | पासफ्रेज लकाओ |  |
| `ui_label_count` | %1$s, %2$s | %1$s, %2$s |  |
| `ui_show_passphrase` | Show passphrase | पासफ्रेज दस्सो |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | इसै फोन उप्पर %1$d एंट्रियें कन्नै मलाया गेआ। कुछ बी कुतै नेईं भेजेआ गेदा। |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | इसै फोन उप्पर %1$d एंट्री कन्नै मलाया गेआ। कुछ बी कुतै नेईं भेजेआ गेदा। |  |
| `vh_count_many` | %1$d things worth a look. | %1$d गल्लें उप्पर ध्यान देना चाहिदा। |  |
| `vh_count_one` | %1$d thing worth a look. | %1$d गल्ल उप्पर ध्यान देना चाहिदा। |  |
| `vh_empty` | No reused, weak or expiring credentials. | ना फ्ही बरते दा, ना कमजोर, ना मियाद खत्म होंदा कुछ ऐ। |  |
| `vh_kind_common` | Commonly guessed | सौखे अंदाजा लगने आह्ला |  |
| `vh_kind_expiring` | Expiring | मियाद खत्म होआ करदी ऐ |  |
| `vh_kind_reused` | Reused password | फ्ही बरतेआ पासवर्ड |  |
| `vh_kind_weak` | Weak | कमजोर |  |
| `vh_no_kit_title` | No recovery kit saved | कोई रिकवरी किट नेईं संभाली दी |  |
| `vh_nothing` | Nothing to fix. | ठीक करने आस्तै कुछ नेईं। |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | ऑफलाइन |  |
| `wl_chip_open` | Open source | खुल्ला स्रोत |  |
| `wl_create` | Create a new vault | नमां भंडार बनाओ |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | ना ईमेल · ना खाता · कुछ बी इस फोनै थमां बाह्र नेईं जंदा |  |
| `wl_head_1` | Your keys. | तुंदियां कुंजियां। |  |
| `wl_head_2` | Your device. | तुंदा फोन। |  |
| `wl_head_3` | No server. | सर्वर नेईं। |  |
| `wl_restore` | Restore from Recovery Kit | रिकवरी किट थमां वापस आनो |  |
| `wl_sr_headline` | Your keys. Your device. No server. | तुंदियां कुंजियां। तुंदा फोन। सर्वर नेईं। |  |
