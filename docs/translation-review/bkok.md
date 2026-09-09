# Konkani (`b+kok`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-b+kok/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Konkani | ok? |
|---|---|---|---|
| `au_close` | Close | बंद करात |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | वेंचिल्ल्या प्रतिमेंत सारको TOTP QR कोड मेळ्ळो ना |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | रिकाम्यांतल्यान सुरवात करून तुमच्या खणांचीं नांवां तुमीच दियात — टेंप्लेटां फकत लेबलां भरतात, डेटा केन्नाच न्हय. |  |
| `hm_close_search` | Close search | सोद बंद करात |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | ह्या फोनांतल्यान केन्नाच भायर वचना. सांबाळटना एन्क्रिप्ट करतात. |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | आतां तुमी आयात केल्ली फायल काडात. ती तुमच्या पासवर्डांची उक्ती वळेरी आसा, आनी अजून तुमच्या Downloads-न पडल्या. |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | सगळें फकत ह्याच फोनाचेर डिक्रिप्ट जाता. कितेंच अपलोड जायना, कारण ह्या ॲपाक नेटवर्क जोडणी उगडूंकूच येना. |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | बँको केन्नाच तुमचो OTP मागनात. जो मागता, तो फटोवपी. |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | खंयचोच बँक अधिकारी तुमकां पड्दो वांटपी ॲप घालूंक सांगचो ना. |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | तुमचो UPI पिन फकत UPI ॲपाच्या कीपॅडाखातीर — फोनाचेर कोणाकूच सांगू नाकात. |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | KYC एका दिसान सोंपना. “आयज KYC सोंपता” अशीं संदेशां फटोवणी. |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | पयशे मेळपाक ना पिन घालचो पडटा, ना QR स्कॅन करचो पडटा. |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | वीज कापपाचो SMS आनी तातूंत कोणाचोय खासगी नंबर? ती फटोवणी. |  |
| `nav_close_menu` | Close menu | मेनू बंद करात |  |
| `nfc_cannot_read` | Cannot read cards | कार्ड वाचूंक येना |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | ह्या फोनांत NFC ना, देखून कार्ड वाचूंक येना. |  |
| `ob_fact_lost_title` | If you lose your keys | चावयो हारवल्यार |  |
| `ob_fact_network_note` | The app literally cannot phone home | हें ॲप खंयच संपर्क करूंक शकना |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | बायोमेट्रिक सुरक्षा चिपाभायर केन्नाच वचना |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | तुमी चावी त्याच फोल्डरांत दवरल्या जो तुमचो एन्क्रिप्ट केल्लो भंडार सिंक करता. आतां जाका तो फोल्डर मेळटलो ताका दोनूय वांटे मेळटले. चावी हेर खंय तरी दवरात — कागद, हेर खातें, वा कपाट. |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | हें तुमच्या भंडार फायलीच्या देगेर आसा |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH रिकवरी |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | स्कॅन करात वा बरयात. परत इन्स्टॉल, फॅक्टरी रिसेट, वा फोन हारवल्या उपरांत लेगीत चलता. |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | आतां सांबाळिल्ल्या किटांतल्यान गट %1$d आनी गट %2$d बरयात. |  |
| `ob_kit_challenge_hint` | Group %1$d | गट %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | पयलीं किट सांबाळात, मागीर गट %1$d आनी %2$d परत बरयात. |  |
| `ob_kit_challenge_title` | Check you actually have it | किट खरेंच तुमचेकडेन आसा काय पळयात |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | हें वयल्या चावयेकडेन जुळना. |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | कोणूच — Zerokosh लेगीत — ही म्हजेखातीर परत हाडूंक शकना. |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "हांवें ही ऑफलायन दवरल्या. " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | एकदाच दिसता, केन्नाच उक्त्या रुपान सांबाळिनात. भितर येवंक शकतात जाल्यार सेटिंगांतल्यान नवी करात. |  |
| `ob_kit_head_emph` | On paper. | कागदाचेर. |  |
| `ob_kit_head_lead` | "One key. " | "एक चावी. " |  |
| `ob_kit_head_tail` | " Never online." | " केन्नाच ऑनलायन न्हय." |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | Gmail न्हय, WhatsApp न्हय, स्क्रीनशॉट लेगीत न्हय. कपाट, बँक लॉकर, वा स्टीलाची पट्टी. |  |
| `ob_kit_offline_title` | Keep it off the internet | तिका इंटरनॅटापासून पयस दवरात |  |
| `ob_kit_print` | Print | छापात |  |
| `ob_kit_print_note` | A printer, or Save as PDF | प्रिंटर, वा PDF म्हूण सांबाळात |  |
| `ob_kit_qr` | QR image | QR प्रतिमा |  |
| `ob_kit_qr_cd` | Recovery key QR code | रिकवरी चावयेचो QR कोड |  |
| `ob_kit_qr_note` | To an offline gallery | ऑफलायन गॅलरींत |  |
| `ob_kit_regenerate` | Regenerate | नवी तयार करात |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | हें सांबाळ्ळें ना. परत यत्न करात, वा हेर सुवात वेंचात. |  |
| `ob_kit_save_pdf` | Save PDF | PDF सांबाळात |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | एका पानाची छापपाजोगी किट |  |
| `ob_kit_saved` | I\'ve saved my kit | हांवें म्हजी किट सांबाळ्ळ्या |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s-न सांबाळ्ळें |  |
| `ob_kit_sent_to_printer` | Sent to the printer | प्रिंटराक धाडलें |  |
| `ob_kit_skip` | I\'ll do this later | हें उपरांत करतलों |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | तुमचो भंडार चलत रावतलो. किट सांबाळ्ळेय मेरेन Zerokosh याद करून दितलें. |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | ह्याच फोनाचेर तयार जाल्ली, फकत एक फावट दिसतली. पासफ्रेज विसरल्यार भितर परत येवपाची हीच एक वाट. |  |
| `ob_kit_working` | Working… | काम चलता… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | भंडाराचीं लेबलां, टेंप्लेटां आनी शिटकावण्यो तेच घडये बदलतल्यो. सेटिंगांत खंयच्याय वेळार बदलूंक शकतात. |  |
| `ob_pass_confirm` | Confirm | परत बरयात |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | हें आमी केन्नाच पळयनात. खंयचोच रिसेट लिंक ना. |  |
| `ob_pass_head_emph` | held only | फकत तुमच्या हातांत |  |
| `ob_pass_head_lead` | "One secret, " | "एकूच गुपीत, " |  |
| `ob_pass_head_tail` | " by you." | . |  |
| `ob_pass_no_match` | no match | जुळना |  |
| `ob_pass_seal` | Seal the vault | भंडार बंद करात |  |
| `ob_pass_sealing` | Sealing… | बंद करता… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | एकामेकांक न जुळपी तीन-चार उतरां एका हुशार उतरापरस बरीं. ह्या पड्ध्यांतल्यान कितेंच भायर वचना. |  |
| `ob_pass_tab_passphrase` | Passphrase | पासफ्रेज |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 आंकड्यांचो पिन |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | तुमच्या बोटाचो ठसो फोनाच्या सुरक्षा चिपाभितरूच रावता. तो ह्या फोनांतल्यान केन्नाच भायर वचना. |  |
| `ob_trust_continue` | I understand · Continue | समजलें · फुडें वचात |  |
| `ob_trust_head_emph` | don\'t | कळना |  |
| `ob_trust_head_lead` | "Exactly what we " | "आमकां खरेंच कितें " |  |
| `ob_trust_head_tail` | " know." | . |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | पासफ्रेज विसरून रिकवरी किट लेगीत हारवल्यार, भंडार बंदच उरतलो — तुमच्याखातीर, आमच्याखातीर, सगळ्यांखातीर. |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "चावयो तुमच्या हातांत. " |  |
| `ob_trust_stat_files` | .kosh file on device | फोनाचेर .kosh फायल |  |
| `ob_trust_stat_servers` | servers contacted | सर्वरावांगडा संपर्क |  |
| `ob_trust_stat_trackers` | trackers or SDKs | ट्रॅकर वा SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | हें एक फावट वाचात. सगळी सुरक्षा वेवस्था हीच, सोंप्या उतरांनी. |  |
| `ob_trust_tag_audited` | Audited build | तपासिल्लो बिल्ड |  |
| `ob_trust_tag_reproducible` | Reproducible APK | परत तयार करूंक येवपी APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | तुमी बोटान उगडटात. बोट केन्नाय काम करप बंद जाल्यार, हेंच तुमकां भितर हाडटलें — देखून पळोवन घेवप बरें. |  |
| `pc_confirm` | Check | तपासात |  |
| `pc_correct` | Still correct. Nothing to do. | आतांय बरोबर. कितेंच करपाची गरज ना. |  |
| `pc_forgot` | I cannot remember it | म्हाका याद येना |  |
| `pc_later` | Not now | आतां न्हय |  |
| `pc_reset_action` | Set new passphrase | नवो पासफ्रेज दवरात |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | तुमचें बोट हो भंडार उगडूंक शकता, देखून तेंच नवो पासफ्रेजय दवरूंक शकता — रिकवरी किट जाय ना. खात्री करपाक परत एक फावट विचारतले. |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | पासफ्रेज बदल्लो. बेगीन अनलॉक परत लागलो. |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | हें जालें ना. तुमचो पोन्नो पासफ्रेजूच चलता. |  |
| `pc_reset_title` | Set a new passphrase | नवो पासफ्रेज दवरात |  |
| `pc_title` | Do you still remember your passphrase? | तुमकां आतांय तुमचो पासफ्रेज याद आसा? |  |
| `pc_wrong` | That is not it. You can set a new one instead. | हो तो न्हय. ताचे बदला नवो दवरूंक शकतात. |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | ह्या नोंदीखातीर याद दवरिल्लीं %1$d मोलां काडटलीं. हें परत करूंक येना. |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh ताका तो लॉगिन सांबाळूंक जालो ना. |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | भरपाक Zerokosh उगडात |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | होच एक पासफ्रेज सगळें बंद दवरता. फकत तुमकां कळपी एक लांब वाक्य वेंचात. |  |
| `scr_create_button` | Lock it in | बंद करात |  |
| `scr_create_confirm_hint` | Type it again | परत बरयात |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | पासफ्रेज (उणीं 10 अक्षरां) |  |
| `scr_create_mismatch` | The two entries don\'t match | दोनूय एकसारकीं नात |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 आंकड्यांचो पिन |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | ह्या बदलाक 6 आंकड्यांचो पिन दवरात |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | पिनाखातीर बोट वा तोंड अनलॉक आशिल्लो फोन जाय. उपकार करून पासफ्रेज वेंचात. |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | पिनाक परवानगी आसा कारण हो फोन ताका आपल्या सुरक्षा चिपान आनी तुमच्या बोटान वा तोंडान राखता. |  |
| `scr_create_strength_fair` | Fair | बरो |  |
| `scr_create_strength_good` | Good | बरो |  |
| `scr_create_strength_strong` | Strong | बळिश्ट |  |
| `scr_create_strength_weak` | Weak | दुबळो |  |
| `scr_create_title` | Create your passphrase | तुमचो पासफ्रेज तयार करात |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | उणीं 10 अक्षरां जाय — जितलें लांब, तितलें बळिश्ट |  |
| `scr_create_working` | Preparing your vault… | तुमचो भंडार तयार जाता… |  |
| `scr_detail_delete` | Delete | काडात |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | ती 30 दीस “सद्यां काडिल्लीं”-त रावतली, आनी तुमच्या हेर फोनांवांगडा सिंक जाल्यार वतली. |  |
| `scr_detail_delete_confirm_title` | Delete this record? | ही नोंद काडची? |  |
| `scr_detail_delete_confirm_yes` | Delete | काडात |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | सिक्रेट वा otpauth:// लिंक चिकटयात |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | तुमचें बोट वा तोंड वापरात |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh उगडात |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | जायते फावट चुकीचो यत्न जालो. %1$d सेकंद रावात. |  |
| `scr_lock_hint` | Passphrase | पासफ्रेज |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | ही रिकवरी चावी बरोबर ना — दर एक अक्षर जुळोवन पळयात |  |
| `scr_lock_title` | Vault is locked | भंडार बंद आसा |  |
| `scr_lock_unlock` | Unlock | उगडात |  |
| `scr_lock_use_passphrase` | Use passphrase | पासफ्रेज वापरात |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | उपकार करून एक फावट पासफ्रेजान उगडात |  |
| `scr_lock_use_recovery` | Use Recovery Key | रिकवरी चावी वापरात |  |
| `scr_lock_wrong` | Wrong passphrase | पासफ्रेज चुकीचो आसा |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | केन्नाय पासफ्रेज विसरल्यार, भितर परत येवपाची हीच एक वाट. आमी ती रिसेट करूंक शकनात — कोणूच शकना. |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | हांवें ती बरोवन सुरक्षीत सुवातेर दवरल्या |  |
| `scr_recovery_done` | Continue | फुडें वचात |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | ही चावी एकदाच दिसता. जोंय मेरेन तुमी उगडूंक शकतात, तोंय मेरेन सेटिंगांतल्यान केन्नाय नवी करूंक शकतात. |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | हो कागद तुमच्या जमीन-जुमल्याच्या कागदांवांगडा वा हेर म्हत्वाच्या दस्तावेजांवांगडा दवरात. जाच्याकडेन ही चावी आसा तो तुमचो भंडार उगडूंक शकता — तिका लॉकराच्या चावेवरी सांबाळात. |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | रिकवरी किट PDF सांबाळ्ळी |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh रिकवरी किट |  |
| `scr_recovery_save_pdf` | Save as PDF | PDF म्हूण सांबाळात |  |
| `scr_recovery_title` | Your Recovery Key | तुमची रिकवरी चावी |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | तुमी जें कितें सांबाळटात तें तुमच्या फोनाच्या एका बंद फायलींत रावता. तें केन्नाच आमच्या हातांत येना — तें दवरपाक आमचेकडेन सुवातूच ना. |  |
| `scr_trust_card1_title` | Your data stays on this device | तुमचो डेटा ह्याच फोनांत रावता |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | Zerokosh खातें ना, क्लाउड ना, साइन-अप ना. हें फकत तुमीच उगडूंक शकतात. आमी लेगीत ना. |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | आमचे सर्वर नात — ना हॅक करपाक कितें, ना विकपाक |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | वर्गणी ना, जायरात ना. कोणूय आमचो कोड वाचून आमची दर एक गजाल तपासूंक शकता. |  |
| `scr_trust_card3_title` | Free forever, open source | सदांच फुकट, उक्तें स्रोत |  |
| `scr_trust_continue` | Continue | फुडें वचात |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | तुमचे बदल सांबाळूंक ना, देखून भंडारांत आदीं जें आशिल्लें तातूंतलें कितेंच हारवूंक ना. |  |
| `st_recently_deleted` | Recently deleted | सद्यां काडिल्लीं |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | एक फावटीचो कोड (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | एक फावटीचो कोड (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | एक फावटीचो कोड (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA सिक्रेट |  |
| `tr_cannot_undo` | This cannot be undone. | हें परत करूंक येना. |  |
| `tr_delete_all` | Delete all permanently | सगळें सदांखातीर काडात |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d नोंदी सदांखातीर वतल्यो. हें परत करूंक येना, आनी परत हाडपाक खंयचोच बॅकअप ना. |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d नोंद सदांखातीर वतली. हें परत करूंक येना, आनी परत हाडपाक खंयचोच बॅकअप ना. |  |
| `tr_delete_all_title` | Delete everything in the trash? | कोयराचें सगळें काडचें? |  |
| `tr_delete_now` | Delete now | आतांच काडात |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | “%1$s” सदांखातीर काडची? |  |
| `tr_empty` | Nothing deleted. | कितेंच काडूंक ना. |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | काडिल्ल्यो नोंदी हांगा %1$d दीस रावतात. |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | भारतीय बँको, UPI, कार्डां, डिमॅट, EPF, आनी तुमी खरेंच वापरतात त्या OTP ॲपांखातीर — फोनांतूच रावपी भंडार. |  |

## Priority 2 — longer prose

| key | English | Konkani | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | सगळ्यांत चड उपेगाक पडपी त्या एका वस्तेपासून सुरवात करात. नोट्स ॲपांत पडिल्ल्या बारा पासवर्डांपरस सांबाळिल्लो एक पासवर्ड चड सुरक्षीत. |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | पासफ्रेज विसरल्यार भितर येवपाची वाट रिकवरी किटूच. तुमच्याखातीर दुसरी कोणूच करूंक शकना. |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | त्या फायलींत वळखुपाजोगें कितेंच मेळ्ळें ना. Chrome, Google Password Manager, Bitwarden, LastPass आनी KeePass-चे निर्यात समजतात. |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d सद्याच्यो नोंदी बदलतल्यो — साइट आनी युजरनेम जुळोवन. बदल्ले पासवर्ड दर एका नोंदीच्या इतिहासांत मेळटले. |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d सद्याची नोंद बदलतली — साइट आनी युजरनेम जुळोवन. बदल्ले पासवर्ड दर एका नोंदीच्या इतिहासांत मेळटले. |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d नोंदी दोनूय वटांनी बदल्ल्यो. दोनूय रूपां सांबाळ्ळीं — “(conflict copy)” सोदात. |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | ही बॅकअप फायल उगडपी पासफ्रेज घालात. तो तुमच्या सद्याच्यापरस वेगळो आसूंक शकता. |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | .kosh फायलीचो Poly1305 प्रमाणीकरण टॅग जुळना. मदींच राविल्ल्या सिंका उपरांत वा वायट स्टोरेजा उपरांत अशें जावंक शकता. |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh भंडार फायलीच्या देगेर एक चलपी बॅकअप दवरता. तो तुमच्या सिंक फोल्डरांतल्यान परत हाडात, वा दुसऱ्या फोनाचेर रिकवरी किटान भंडार उगडात. |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | वाचसर मेरेन कार्ड फोनाच्या फाटल्यान सरळ लावन धरात. ह्यातल्यान कार्ड नंबर, मुदत आनी नांव मेळटा — CVV चिपांत आसना, तो तुमीच बरोवंक जाय. |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | भितर परत येवपाची बेगीन वाट वेंचात. भंडाराची राखण पासफ्रेजूच करता; हें फकत ह्याच फोनाचेर चावी उगडटा. |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | आतां तुमच्या भंडारांत खरी म्हायती आसा. रिकवरी किट बगर पासफ्रेज विसरल्यार कोणूच तुमकां परत भितर हाडूंक शकना — आमीय ना. |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh फुकट आनी उक्तें स्रोत आसा, आनी ताचे खंयचेच सर्वर नात. तुमचो भंडार फकत तुमीच उगडूंक शकतात. आमी लेगीत ना. |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | कॅमेऱ्याची परवानगी फकत QR कोड स्कॅन करपाक जाय. नोंद जोडटना सिक्रेट हातान लेगीत चिकटोवंक शकतात. |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | जीं बँक ॲपां ऑटोफिल करूंक दिनात तांकां — बटण दामून लॉगिनाची म्हायती एक एक करून प्रत करात |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | तुमी जशें फोन उगडटात, तशेंच भंडार लेगीत. पासफ्रेज सदांच चलतलो. |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | भंडाराचे स्क्रीनशॉट क्लाउड फोटो बॅकअपांत पावूंक शकतात. खूब गरज आसल्यारूच चालू करात. |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | भंडार फायल बरोवंक जाली ना. तुमी बॅकअप आनी सिंक फोल्डर लायला जाल्यार, Android न ताची परवानगी परत घेतिल्ली आसूंक शकता — सेटिंग उगडात, फोल्डर परत वेंचात, आनी परत यत्न करात. |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | तुमच्यो एन्क्रिप्ट केल्ल्यो .kosh फायलीं सरळ ह्याच फोल्डरांत सांबाळटात. जायत्या उपकरणांचेर आपशीच बॅकअप जावपाक हो फोल्डर Google Drive, Syncthing, Nextcloud वा SD कार्डावांगडा सिंक करात. |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | नवी रिकवरी चावी तयार करपाक तुमचो पासफ्रेज घालात. पोन्नी चावी चलपाची बंद जातली. |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | ह्या पानाचें हेर सगळें एक लॉगिन दुबळें करता. हें पुराय भंडार व्हरूंक शकता. सेटिंग → नवी रिकवरी चावी घेयात. |  |

## Priority 3 — short labels

| key | English | Konkani | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | बळिश्ट पासवर्ड वापरात |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | खात्याचें नांव (देखीक Google) |  |
| `au_active_many` | %1$d active codes | %1$d सक्रिय कोड |  |
| `au_active_one` | %1$d active code | %1$d सक्रिय कोड |  |
| `au_add_another` | Add another authenticator | आनीक एक प्रमाणक जोडात |  |
| `au_add_secret` | Add Secret Key | सिक्रेट चावी जोडात |  |
| `au_camera_needed` | Camera permission needed to scan QR code | QR कोड स्कॅन करपाक कॅमेऱ्याची परवानगी जाय |  |
| `au_copied` | Copied · clears shortly | प्रत जाली · थोड्या वेळान पुसतली |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google, GitHub वा तुमच्या ब्रोकराचो QR स्कॅन करात, वा सिक्रेट चावी हातान बरयात. |  |
| `au_enter_key` | Enter Key | चावी बरयात |  |
| `au_fallback_name` | Authenticator | प्रमाणक |  |
| `au_flashlight` | Flashlight | बॅटरी |  |
| `au_grant` | Grant Permission | परवानगी दियात |  |
| `au_image_failed` | Failed to process image | प्रतिमा वाचूंक जाली ना |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | चुकीची Base32 सिक्रेट चावी (फकत A-Z अक्षरां आनी 2-7 आंकडे) |  |
| `au_no_match` | No codes match | खंयचोच कोड मेळ्ळो ना |  |
| `au_none_yet` | No codes yet. | आतां मेरेन कोड नात. |  |
| `au_pick_image` | Pick Image | प्रतिमा वेंचात |  |
| `au_rotating` | "Rotating " | "बदलत रावपी " |  |
| `au_rotating_emph` | codes. | कोड. |  |
| `au_save_key` | Save Key | चावी सांबाळात |  |
| `au_scan_qr` | Scan a QR code | QR कोड स्कॅन करात |  |
| `au_scan_title` | Scan Authenticator QR | प्रमाणकाचो QR स्कॅन करात |  |
| `au_search_hint` | Search codes, issuers… | कोड वा दिवपी सोदात… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | देखीक JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | सिक्रेट चावी (Base32) |  |
| `au_tap_to_copy` | Tap to copy | प्रत करपाक टॅप करात |  |
| `cat_apps` | Apps &amp; Logins | ॲपां आनी लॉगिन |  |
| `cat_banks` | Banks &amp; UPI | बँको आनी UPI |  |
| `cat_cards` | Cards | कार्डां |  |
| `cat_govid` | Gov &amp; ID | सरकारी आनी वळख |  |
| `cat_investments` | Investments | गुंतवणूक |  |
| `cat_utilities` | Utilities | बिलां आनी जोडण्यो |  |
| `cd_mask_hidden` | hidden | लिपिल्लें |  |
| `cd_shield_high_sensitivity` | extra-protected field | चड संवेदनशील म्हायती |  |
| `gl_blank` | Blank template | रिकामें टेंप्लेट |  |
| `gl_cat_apps` | Apps | ॲपां |  |
| `gl_cat_banks` | Banks | बँको |  |
| `gl_cat_cards` | Cards | कार्डां |  |
| `gl_cat_demat` | Demat | डिमॅट |  |
| `gl_cat_govid` | Gov ID | सरकारी वळख |  |
| `gl_cat_popular` | Popular | लोकप्रिय |  |
| `gl_cat_shopping` | Shopping | खरेदी |  |
| `gl_cat_travel` | Travel | प्रवास |  |
| `gl_cat_upi` | UPI | UPI |  |
| `gl_cat_utilities` | Utilities | बिलां |  |
| `gl_head_emph` | storing? | सांबाळटात? |  |
| `gl_head_lead` | "What are we " | "आमी कितें " |  |
| `gl_matches` | %1$d matches | %1$d मेळ्ळीं |  |
| `gl_most_used` | Most-used first | सगळ्यांत चड वापरिल्लीं पयलीं |  |
| `gl_not_found` | Can’t find a service? | सेवा मेळना? |  |
| `gl_search` | Search %1$d Indian services… | %1$d भारतीय सेवांनी सोदात… |  |
| `gl_suggested` | Suggested for you | तुमच्याखातीर सुचोवणी |  |
| `hm_add_first` | Add your first record | तुमची पयली नोंद जोडात |  |
| `hm_all_offline` | all offline. | सगळें ऑफलायन. |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d वस्तू, " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d वस्त, " |  |
| `hm_detail_placeholder` | Pick a record to see it here | हांगा पळोवपाक एक रेकॉर्ड वेंचात |  |
| `hm_empty_blank` | A blank vault, ready. | रिकामो भंडार, तयार. |  |
| `hm_empty_head_emph` | waiting. | रावता. |  |
| `hm_empty_head_lead` | "Your vault is " | "तुमचो भंडार " |  |
| `hm_filter_all` | All | सगळें |  |
| `hm_import_backup` | Import an encrypted backup | एन्क्रिप्ट केल्लो बॅकअप आयात |  |
| `hm_import_backup_note` | Open a .kosh file from this device | ह्याच फोनांतल्यान .kosh फायल उगडात |  |
| `hm_inst_many` | %1$d institutions | %1$d संस्था |  |
| `hm_inst_one` | %1$d institution | %1$d संस्था |  |
| `hm_kit_banner_action` | Save one now | आतांच सांबाळात |  |
| `hm_kit_banner_dismiss` | Remind me later | उपरांत याद करात |  |
| `hm_kit_banner_title` | No recovery kit saved | खंयचीच रिकवरी किट सांबाळ्ळी ना |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | उगडिल्लो · सोडटकच बंद जातलो |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | उगडिल्लो · सोडून %1$d मिनटांनी बंद |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | उगडिल्लो · सोडून 1 मिनटान बंद |  |
| `hm_no_match` | Nothing matches “%1$s” | “%1$s”-खातीर कितेंच मेळ्ळें ना |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | खंयचीय संस्था, UPI हॅन्डल, वा निमाणे चार आंकडे यत्न करात. |  |
| `hm_pinned` | Pinned | पिन केल्लीं |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC, priya@upi, PAN… सोदात |  |
| `hm_start_template` | Start with a template | टेंप्लेटांतल्यान सुरवात करात |  |
| `ic_could_not` | Could not import | आयात करूंक जालें ना |  |
| `ic_done` | Done | जालें |  |
| `ic_import` | Import | आयात |  |
| `ic_imported` | Imported | आयात जालें |  |
| `ic_importing` | Importing… | आयात जाता… |  |
| `ic_new_many` | %1$d new logins. | %1$d नवे लॉगिन. |  |
| `ic_new_one` | %1$d new login. | %1$d नवो लॉगिन. |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d जोडलीं, %2$d बदललीं. |  |
| `ic_title` | Import from %1$s? | %1$s-तल्यान आयात करचें? |  |
| `ic_too_large` | That file is too large to be a credential export. | ही फायल पासवर्ड निर्यात जावपाक खूब व्हड आसा. |  |
| `import_action` | Import | आयात |  |
| `import_locked` | Unlock your vault before importing. | आयात करचेपयलीं तुमचो भंडार उगडात. |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d जोडलीं, %2$d बदललीं. कितेंच पुसूंक ना. |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | ती फायल Zerokosh भंडार म्हूण वाचूंक जाली ना. |  |
| `import_nothing_new` | Everything in that backup was already here. | त्या बॅकअपांत जें आशिल्लें, तें सगळें आदींच हांगा आशिल्लें. |  |
| `import_passphrase_label` | Backup passphrase | बॅकअपाचो पासफ्रेज |  |
| `import_title` | Import a backup | बॅकअप आयात करात |  |
| `kicker_locked` | Locked | बंद आसा |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · कितेंच ह्या फोनांतल्यान भायर गेलां ना |  |
| `lk_touch_unlock` | Touch to unlock | उगडपाक शिवात |  |
| `lk_welcome_emph` | Your vault is sealed. | तुमचो भंडार बंद आसा. |  |
| `lk_welcome_lead` | Welcome back. | परत येवकार. |  |
| `msg_auth_needed` | Confirm it\'s you to see this | पळोवपाक खात्री करात की हें तुमीच आसात |  |
| `msg_back` | Back | फाटीं |  |
| `msg_cancel` | Cancel | रद्द |  |
| `msg_file_damaged` | File damaged — restored from backup | फायल बिघडिल्ली — बॅकअपांतल्यान सारकी केली |  |
| `msg_ok` | OK | बरें |  |
| `msg_saved` | Saved | सांबाळ्ळें |  |
| `nav_all_templates` | All templates | सगळीं टेंप्लेटां |  |
| `nav_damaged_emph` | vault file | भंडार फायलींत |  |
| `nav_damaged_kicker` | Damaged state | बिघडिल्ली अवस्था |  |
| `nav_damaged_lead` | "Something in the " | "तुमच्या " |  |
| `nav_damaged_tail` | " is off." | " कितें तरी गडबड आसा." |  |
| `nav_integrity_title` | Integrity check failed | अखंडताय तपासणी सपली ना |  |
| `nav_scan` | Scan | स्कॅन |  |
| `nav_tap_card` | Tap a card | कार्ड टॅप करात |  |
| `nav_what_next` | What to do next | आतां फुडें कितें |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC बंद आसा. सेटिंगांत चालू करून परत यत्न करात. |  |
| `nfc_hold_card` | Hold your card to the phone | कार्ड फोनाक लावन धरात |  |
| `nfc_missed` | Did not catch that | धरूंक जालें ना |  |
| `nfc_read_failed` | That card could not be read. Try again. | तें कार्ड वाचूंक जालें ना. परत यत्न करात. |  |
| `nfc_reading` | Reading… | वाचता… |  |
| `nfc_try_again` | Try again | परत यत्न करात |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · ह्याच फोनाचेर मेजिल्लें |  |
| `ob_argon_faster` | Faster unlock | बेगीन उगडटलो |  |
| `ob_argon_harder` | Harder to attack | फोडूंक कठीण |  |
| `ob_argon_measuring` | Measuring this device… | हो फोन मेजता… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id कठीणाय |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | शब्दकोशांतलें एक उतर लेगीत न्हय |  |
| `ob_check_pass_length` | 10 characters or more | 10 वा चड अक्षरां |  |
| `ob_check_pass_reuse` | Not reused from another app | हेर ॲपांतल्यान परत वापरिल्लें न्हय |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | ना जल्मदीस, ना वर्सुकी |  |
| `ob_check_pin_digits` | All six digits entered | सव्वूय आंकडे भरले |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | ना सलग आंकडे, ना परतून परत |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~फोडपाक %1$d शतमानां |  |
| `ob_crack_days` | ~%1$d days to crack | ~फोडपाक %1$d दीस |  |
| `ob_crack_forever` | longer than the sun | सुर्यापरस लेगीत चड काळ |  |
| `ob_crack_hours` | ~hours to crack | ~फोडपाक थोडे वरां |  |
| `ob_crack_seconds` | ~seconds to crack | ~फोडपाक थोडे सेकंद |  |
| `ob_crack_years` | ~%1$d years to crack | ~फोडपाक %1$d वर्सां |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | प्रमाणीत, दर एका भंडाराखातीर वेगळो नॉन्स |  |
| `ob_fact_encryption_title` | Encryption | एन्क्रिप्शन |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | सेटअपाच्या वेळार तुमच्या फोनाचेर मेजिल्लें |  |
| `ob_fact_kdf_title` | Key stretching | चावी स्ट्रेचिंग |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | रिसेट लिंक ना. सपोर्टाचें फाटलें दारूय ना. |  |
| `ob_fact_lost_value` | Nobody can recover it | कोणूच परत हाडूंक शकना |  |
| `ob_fact_network_title` | Network permission | नेटवर्क परवानगी |  |
| `ob_fact_network_value` | Not requested | केन्नाच मागूंक ना |  |
| `ob_fact_quick_title` | Quick unlock | बेगीन अनलॉक |  |
| `ob_fact_quick_value` | Hardware keystore | हार्डवेअर कीस्टोर |  |
| `ob_lang_continue` | Continue in %1$s | %1$s-न फुडें वचात |  |
| `ob_lang_head_emph` | language. | भास वेंचात. |  |
| `ob_lang_head_lead` | "Choose your " | "तुमची " |  |
| `ob_lang_search` | Search %1$d languages | %1$d भासांनी सोदात |  |
| `ob_quick_continue_pass` | Continue with passphrase | पासफ्रेजावांगडा फुडें वचात |  |
| `ob_quick_enable` | Enable quick unlock | बेगीन अनलॉक चालू करात |  |
| `ob_quick_fingerprint` | Fingerprint | बोटाचो ठसो |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | बेगीन, हार्डवेअरान सुरक्षीत अनलॉक. |  |
| `ob_quick_head_emph` | Without the cloud. | क्लाउडाबगर. |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "एका सुर्शीन उगडटलो. " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox. खंयचीच बायोमेट्रिक म्हायती केन्नाच Zerokosh मेरेन पावना. |  |
| `ob_quick_hw_title` | Hardware-backed | हार्डवेअरान सुरक्षीत |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | ह्या फोनांत हार्डवेअर सेन्सर ना. |  |
| `ob_quick_opening` | Opening your vault… | तुमचो भंडार उगडटा… |  |
| `ob_quick_pass_only` | Passphrase only | फकत पासफ्रेज |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | दर फावट बरयात. सगळ्यांत सुरक्षीत. |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | बेगीन अनलॉक लागलो ना. परत यत्न करात, वा पासफ्रेजावांगडाच फुडें वचात. |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | आतां न्हय — हांव पासफ्रेज बरयतलों |  |
| `ob_quick_touch_title` | Touch the sensor | सेन्सराक शिवात |  |
| `ob_recommended` | Recommended | शिफारस |  |
| `ob_reveal_hide` | Hide | लिपयात |  |
| `ob_reveal_show` | Show | दाखयात |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | गुठलावपी चावी हार्डवेअर कीस्टोरांत रावतली. बायोमेट्रिक फुडल्या पावलार. |  |
| `ob_seal_title` | Seal to this device | ह्याच फोनाकडेन बांदात |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | ह्या फोनांत हार्डवेअर बायोमेट्रिक ना. |  |
| `ob_soon` | SOON | बेगीन |  |
| `ob_step_label` | Step %1$d of 6 | पावल %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | अशें कितें वेंचात जें फकत तुमीच सांगतात |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | बरो · %1$d बिट एन्ट्रोपी |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | खूब ल्हान · 10 अक्षरां जाय |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | बळिश्ट · %1$d बिट एन्ट्रोपी |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | दुबळो · %1$d बिट एन्ट्रोपी |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | स आंकडे जे तुमची जीण पळोवन कोणाकूच अदमास करूंक येवचे नात |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | बरो · %1$d बिट — पिन ह्यापरस बळिश्ट जावंक शकना |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | खूब ल्हान · 6 आंकडे जाय |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | दुबळो · हेच पिन सगळ्यांत पयलीं यत्न करतात |  |
| `ob_try_label` | TRY | यत्न करात |  |
| `qa_aadhaar` | Aadhaar | आधार |  |
| `qa_bank_account` | Bank account | बँक खातें |  |
| `qa_pan` | PAN | PAN |  |
| `qa_upi_id` | UPI ID | UPI ID |  |
| `rd_copied` | Copied | प्रत जाली |  |
| `rd_forget` | Forget | विसरात |  |
| `rd_forget_these` | Forget these | हीं विसरात |  |
| `rd_forget_title` | Forget previous passwords? | पोन्ने पासवर्ड विसरचे? |  |
| `rd_history_hide` | Hide | लिपयात |  |
| `rd_history_show` | Show %1$d | %1$d दाखयात |  |
| `rd_hold_to_reveal` | Hold to reveal | पळोवपाक दामून धरात |  |
| `rd_last_edit` | last edit %1$s | निमाणें %1$s बदल्लें |  |
| `rd_release_to_hide` | Release to hide | लिपोवपाक सोडात |  |
| `re_add_field` | + Add another field | + आनीक एक खण जोडात |  |
| `re_add_field_title` | Add a field | खण जोडात |  |
| `re_field_name` | Field name | खणाचें नांव |  |
| `re_pick_date` | Pick a date | तारीख वेंचात |  |
| `re_remove` | Remove | काडात |  |
| `re_tap_card` | Read the card by tapping it | वाचपाक कार्ड टॅप करात |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | गुपीत म्हूण मानात (लिपिल्लें उरतलें, पळोवपाक दामून धरात) |  |
| `re_using_template` | using the %1$s template | %1$s टेंप्लेट वापरून |  |
| `rem_kit_title` | No recovery kit saved | खंयचीच रिकवरी किट सांबाळ्ळी ना |  |
| `scr_about_license` | License: GPL-3.0 — free forever | परवानो: GPL-3.0 — सदांच फुकट |  |
| `scr_about_source` | Source code | स्रोत कोड |  |
| `scr_about_version` | Version %1$s | आवृत्ती %1$s |  |
| `scr_auth_add_qr` | Add via QR | QR-न जोडात |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | तुमच्या ॲपांचे आनी ब्रोकराचे कोड हांगा दिसतले |  |
| `scr_auth_scan_title` | Point the camera at the QR code | कॅमेरा QR कोडाचेर धरात |  |
| `scr_detail_copied` | Copied · clears in 30s | प्रत जाली · 30 सेकंदांनी पुसतली |  |
| `scr_detail_copy` | Copy | प्रत करात |  |
| `scr_detail_edit` | Edit | बदलात |  |
| `scr_detail_favorite` | Favourite | आवडीचें |  |
| `scr_detail_hidden` | Hidden | लिपिल्लें |  |
| `scr_detail_hide` | Hide | लिपयात |  |
| `scr_detail_history_empty` | Nothing replaced yet. | आतां मेरेन कितेंच बदल्लां ना. |  |
| `scr_detail_history_title` | Previous passwords | पोन्ने पासवर्ड |  |
| `scr_detail_reveal` | Show | दाखयात |  |
| `scr_detail_shown` | Shown | दिसता |  |
| `scr_edit_cancel` | Cancel | रद्द |  |
| `scr_edit_generate` | Generate | तयार करात |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | बँक / कंपनी (एकठांय दवरपाक) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | हें बरोबर दिसना — एक फावट पळयात |  |
| `scr_edit_link_none` | None | कितेंच ना |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | हो कार्ड नंबर सामान्य तपासणेंत पास जायना — बरोबर आसल्यार सांबाळात |  |
| `scr_edit_month` | Month | म्हयनो |  |
| `scr_edit_picker_other` | Other… | हेर… |  |
| `scr_edit_picker_other_hint` | Type your own | तुमचें बरयात |  |
| `scr_edit_required_title` | Give it a name first | पयलीं हाका एक नांव दियात |  |
| `scr_edit_save` | Save | सांबाळात |  |
| `scr_edit_title_hint` | Title | नांव |  |
| `scr_edit_title_new` | New | नवें |  |
| `scr_edit_year` | Year | वर्स |  |
| `scr_gallery_quick_add` | Quick add | बेगीन जोडात |  |
| `scr_gallery_title` | What do you want to save? | आमी कितें सांबाळटात? |  |
| `scr_home_add` | Add | जोडात |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | तुमचें बँक खातें अशें दिसतलें |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | तुमचीं कार्डां, UPI, ॲप लॉगिन — सगळें हांगाच |  |
| `scr_home_group_other` | Other | हेर |  |
| `scr_home_no_results` | Nothing matches your search | तुमच्या सोदांत कितेंच मेळ्ळें ना |  |
| `scr_home_search_hint` | Search your vault | तुमच्या भंडारांत सोदात |  |
| `scr_home_tab_authenticator` | Authenticator | कोड |  |
| `scr_home_tab_home` | Home | घर |  |
| `scr_home_tab_settings` | Settings | सेटिंग |  |
| `scr_home_title` | Home | घर |  |
| `scr_language_continue` | Continue | फुडें वचात |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | तुमची भास वेंचात |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | प्रत करपाक बटण दामात · 30 सेकंदांनी पुसतली |  |
| `scr_login_helper_channel` | Login helper | लॉगिन आदार |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s-न लॉगिन जाता |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | आतांच बॅकअप फोल्डर लावन घेयात |  |
| `scr_quickunlock_enable` | Turn on | चालू करात |  |
| `scr_quickunlock_skip` | Not now | आतां न्हय |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | बोटान वा तोंडान उगडात |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s-ची तारीख लागीं आयल्या · Zerokosh उगडात |  |
| `scr_reminder_channel` | Renewal reminders | नवीकरणाची याद |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh याद करून दिता |  |
| `scr_settings_about` | About | विशीं |  |
| `scr_settings_allow_screenshots` | Allow screenshots | स्क्रीनशॉट घेवंक दियात |  |
| `scr_settings_autofill` | Autofill service | ऑटोफिल सेवा |  |
| `scr_settings_autofill_off` | Not set up | लायल्लें ना |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | उपलब्ध ना |  |
| `scr_settings_autolock` | Lock when I leave the app | ॲप सोडटकच बंद करात |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 मिनटा उपरांत |  |
| `scr_settings_autolock_immediately` | Immediately | तेच घडये |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d मिनटां उपरांत |  |
| `scr_settings_change_passphrase` | Change passphrase | पासफ्रेज बदलात |  |
| `scr_settings_current_passphrase` | Current passphrase | सद्याचो पासफ्रेज |  |
| `scr_settings_export` | Export | निर्यात करात |  |
| `scr_settings_import` | Import passwords | पासवर्ड आयात करात |  |
| `scr_settings_language` | Language | भास |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | नवो पासफ्रेज (उणीं 10 अक्षरां) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | नवी रिकवरी चावी घेयात |  |
| `scr_settings_passphrase_changed` | Passphrase changed | पासफ्रेज बदल्लो |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | बोट / तोंड अनलॉक |  |
| `scr_settings_security_info` | How your data is protected | तुमचो डेटा कशें सुरक्षीत आसा |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | बॅकअप आनी सिंक फोल्डर |  |
| `scr_settings_sync_not_set` | Not backed up | बॅकअप ना |  |
| `scr_settings_title` | Settings | सेटिंग |  |
| `se_title` | Not saved | सांबाळूंक ना |  |
| `st_active_folder` | Active Folder | सक्रिय फोल्डर |  |
| `st_active_value` | Active · %1$s | सक्रिय · %1$s |  |
| `st_backing_up` | Backing up vault… | भंडाराचो बॅकअप घेता… |  |
| `st_backup_now` | Backup Now | आतांच बॅकअप घेयात |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | बॅकअप आनी सिंक फोल्डर |  |
| `st_change_folder` | Change Folder | फोल्डर बदलात |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | नवो पासफ्रेज परत बरयात |  |
| `st_connected_folder` | Connected folder: %1$s | जोडिल्लो फोल्डर: %1$s |  |
| `st_disconnect` | Disconnect | काडात |  |
| `st_done` | Done | जालें |  |
| `st_export_kosh` | Export encrypted .kosh | एन्क्रिप्ट केल्लो .kosh निर्यात |  |
| `st_folder_fallback` | Folder | फोल्डर |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | पासफ्रेज विसरलात? |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | तुमच्या बोटान नवो दवरात |  |
| `st_generate` | Generate | तयार करात |  |
| `st_group_about` | About | विशीं |  |
| `st_group_appearance` | Appearance | रूप |  |
| `st_group_security` | Security | सुरक्षा |  |
| `st_group_sync` | Sync | सिंक |  |
| `st_import_kosh` | Import a .kosh backup | .kosh बॅकअप आयात |  |
| `st_import_other` | Import from another password manager | हेर पासवर्ड मॅनेजरांतल्यान आयात |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | परवानो |  |
| `st_logos_by` | Logos provided by | लोगो दिवपी |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | ती ऑफलायन दवरात. पोन्नी रिकवरी चावी आतां चलना. |  |
| `st_new_recovery_result` | Your new Recovery Key: | तुमची नवी रिकवरी चावी: |  |
| `st_subtitle` | Your rules. | तुमचे नेम. |  |
| `st_theme` | Theme | थीम |  |
| `st_theme_dark` | Dark | काळोख |  |
| `st_theme_light` | Light | उजवाड |  |
| `st_theme_system` | System | प्रणाली |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | भंडार %1$s-न सांबाळ्ळो! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | बॅकअप जालो ना — फोल्डराची परवानगी पळयात |  |
| `st_toast_disconnected` | Backup folder disconnected | बॅकअप फोल्डर काडलो |  |
| `st_toast_export_failed` | Export failed | निर्यात जालो ना |  |
| `st_toast_exported` | Encrypted vault exported | एन्क्रिप्ट केल्लो भंडार निर्यात जालो |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | बॅकअप फोल्डर जोडलो, आनी भंडार %1$s-न सांबाळ्ळो! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | बॅकअप फोल्डर जोडलो: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | फोल्डर जोडूंक जालो ना: %1$s |  |
| `st_vault_review` | Vault review | भंडाराची तपासणी |  |
| `st_vault_review_detail` | Reused, weak, expiring | परत वापरिल्ले, दुबळे, मुदत सोंपपी |  |
| `tab_codes` | Codes | कोड |  |
| `tab_settings` | Settings | सेटिंग |  |
| `tab_templates` | Templates | टेंप्लेट |  |
| `tab_vault` | Vault | भंडार |  |
| `time_days` | %1$dd ago | %1$d दीस आदीं |  |
| `time_hours` | %1$dh ago | %1$d वरां आदीं |  |
| `time_just_now` | just now | आतांच |  |
| `time_minutes` | %1$dm ago | %1$d मिनटां आदीं |  |
| `time_months` | %1$dmo ago | %1$d म्हयने आदीं |  |
| `time_years` | %1$dy ago | %1$d वर्सां आदीं |  |
| `tpl_aadhaar_card` | Aadhaar Card | आधार |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | आधार नंबर |  |
| `tpl_aadhaar_card_address` | Address | आधाराचेर नामो |  |
| `tpl_aadhaar_card_dob` | Dob | जल्म तारीख |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | स्कॅन केल्ली प्रत |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | जोडिल्लो मोबायल |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar पासकोड |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | आधाराचेर नांव |  |
| `tpl_aadhaar_card_notes` | Notes | टिप |  |
| `tpl_app_profile` | App Profile | ॲप प्रोफायल |  |
| `tpl_app_profile_app_name` | App name | ॲपाचें नांव |  |
| `tpl_app_profile_gift_cards` | Gift cards | गिफ्ट कार्ड |  |
| `tpl_app_profile_membership` | Membership | सदस्यत्व |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | सदस्यत्व नवीकरण |  |
| `tpl_app_profile_notes` | Notes | टिप |  |
| `tpl_app_profile_password_if_any` | Password (if any) | पासवर्ड (आसल्यार) |  |
| `tpl_app_profile_registered_email` | Registered email | नोंदणी केल्लो ईमेल |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | नोंदणी केल्लो मोबायल |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_bank_account` | Bank Account | बँक खातें |  |
| `tpl_bank_account_account_number` | Account number | खातें नंबर |  |
| `tpl_bank_account_account_type` | Account type | खात्याचो प्रकार |  |
| `tpl_bank_account_bank_name` | Bank name | बँकेचें नांव |  |
| `tpl_bank_account_branch` | Branch | शाखा |  |
| `tpl_bank_account_customer_id` | Customer id | गिरायक ID |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC कोड |  |
| `tpl_bank_account_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_bank_account_micr` | MICR code | MICR कोड |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | नेट-बँकिंग युजर ID |  |
| `tpl_bank_account_nominee` | Nominee | वारसदार |  |
| `tpl_bank_account_notes` | Notes | टिप |  |
| `tpl_bank_account_profile_password` | Profile password | प्रोफायल पासवर्ड |  |
| `tpl_bank_account_registered_email` | Registered email | नोंदणी केल्लो ईमेल |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | नोंदणी केल्लो मोबायल |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | वेव्हार पासवर्ड |  |
| `tpl_card` | Card | कार्ड |  |
| `tpl_card_atm_pin` | ATM PIN | ATM पिन |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | बिलिंग चक्राचो दीस |  |
| `tpl_card_card_network` | Card network | नेटवर्क |  |
| `tpl_card_card_number` | Card number | कार्ड नंबर |  |
| `tpl_card_card_portal_login` | Card portal login | कार्ड पोर्टल लॉगिन |  |
| `tpl_card_card_portal_password` | Card portal password | कार्ड पोर्टल पासवर्ड |  |
| `tpl_card_card_type` | Card type | कार्डाचो प्रकार |  |
| `tpl_card_card_variant` | Card variant | कार्ड व्हेरिअंट |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | मुदत |  |
| `tpl_card_linked_account` | Linked account | जोडिल्लें खातें |  |
| `tpl_card_name_on_card` | Name on card | कार्डाचेर नांव |  |
| `tpl_card_notes` | Notes | टिप |  |
| `tpl_demat` | Demat | डिमॅट |  |
| `tpl_demat_api_key` | API key | API चावी |  |
| `tpl_demat_api_secret` | API secret | API सिक्रेट |  |
| `tpl_demat_bo_id` | BO ID | BO ID |  |
| `tpl_demat_broker` | Broker | ब्रोकर |  |
| `tpl_demat_cdsl_tpin` | CDSL TPIN | CDSL TPIN |  |
| `tpl_demat_client_id` | Client id | क्लायंट ID |  |
| `tpl_demat_depository` | Depository | डिपॉझिटरी |  |
| `tpl_demat_dp_id` | DP ID | DP ID |  |
| `tpl_demat_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_demat_mf_folios` | Mutual fund folios | म्युच्युअल फंड फोलियो |  |
| `tpl_demat_mpin` | MPIN | MPIN |  |
| `tpl_demat_nominee` | Nominee | वारसदार |  |
| `tpl_demat_notes` | Notes | टिप |  |
| `tpl_digilocker` | Digilocker | DigiLocker |  |
| `tpl_digilocker_login_username` | Login username | युजरनेम |  |
| `tpl_digilocker_notes` | Notes | टिप |  |
| `tpl_digilocker_portal_password` | Portal password | पासवर्ड |  |
| `tpl_digilocker_security_pin` | Security pin | सुरक्षा पिन |  |
| `tpl_driving_license` | Driving License | ड्रायव्हिंग लायसन्स |  |
| `tpl_driving_license_dl_number` | Dl number | लायसन्स नंबर |  |
| `tpl_driving_license_dob` | Dob | जल्म तारीख |  |
| `tpl_driving_license_expiry_date` | Expiry date | ह्या तारखे मेरेन वैध |  |
| `tpl_driving_license_file_copy` | Scanned copy | स्कॅन केल्ली प्रत |  |
| `tpl_driving_license_issue_date` | Issue date | दिल्ली तारीख |  |
| `tpl_driving_license_name_on_dl` | Name on dl | लायसन्साचेर नांव |  |
| `tpl_driving_license_notes` | Notes | टिप |  |
| `tpl_driving_license_rto_location` | Rto location | RTO |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | वाहनांचे वर्ग |  |
| `tpl_epf_pension` | Epf Pension | EPF / निवृत्तीवेतन |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | जोडिल्लो मोबायल |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | EPF-चेर नांव |  |
| `tpl_epf_pension_nominee` | Nominee | वारसदार |  |
| `tpl_epf_pension_notes` | Notes | टिप |  |
| `tpl_epf_pension_password` | Password | पासवर्ड |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | PF सदस्य ID |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO पासवर्ड |  |
| `tpl_epf_pension_scheme` | Scheme | येवजण |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | सरकारी वळख पत्र |  |
| `tpl_gov_id_expiry` | Expiry | मुदत |  |
| `tpl_gov_id_file_copy` | Scanned copy | स्कॅन केल्ली प्रत |  |
| `tpl_gov_id_id_kind` | ID type | वळख पत्राचो प्रकार |  |
| `tpl_gov_id_id_number` | ID number | वळख नंबर |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | पत्राप्रमाणें नांव |  |
| `tpl_gov_id_notes` | Notes | टिप |  |
| `tpl_gov_id_portal_login` | Portal login | पोर्टल लॉगिन |  |
| `tpl_gov_id_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_insurance` | Insurance | विमो |  |
| `tpl_insurance_agent_contact` | Agent contact | एजंटाचो संपर्क |  |
| `tpl_insurance_commencement_date` | Commencement date | सुरू जाल्ली तारीख |  |
| `tpl_insurance_insurer` | Insurer | विमो कंपनी |  |
| `tpl_insurance_maturity_date` | Maturity date | मुदत सोंपपाची तारीख |  |
| `tpl_insurance_nominee` | Nominee | वारसदार |  |
| `tpl_insurance_notes` | Notes | टिप |  |
| `tpl_insurance_policy_number` | Policy number | पॉलिसी नंबर |  |
| `tpl_insurance_policy_term` | Policy term | पॉलिसीची मुदत |  |
| `tpl_insurance_policy_type` | Policy type | पॉलिसीचो प्रकार |  |
| `tpl_insurance_portal_login` | Portal login | पोर्टल लॉगिन |  |
| `tpl_insurance_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_insurance_premium_amount` | Premium amount | प्रिमियम रक्कम |  |
| `tpl_insurance_premium_due_date` | Premium due date | प्रिमियमाची तारीख |  |
| `tpl_insurance_premium_mode` | Premium mode | प्रिमियम कशें भरतात |  |
| `tpl_insurance_sum_assured` | Sum assured | विम्याची रक्कम |  |
| `tpl_login` | Login | लॉगिन |  |
| `tpl_login_notes` | Notes | टिप |  |
| `tpl_login_password` | Password | पासवर्ड |  |
| `tpl_login_recovery_codes` | Recovery codes | रिकवरी कोड |  |
| `tpl_login_username` | Username | युजरनेम |  |
| `tpl_login_website` | Website | वेबसायट |  |
| `tpl_pan_card` | Pan Card | PAN कार्ड |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | आधारावांगडा जोडिल्लें |  |
| `tpl_pan_card_dob` | Dob | जल्म तारीख |  |
| `tpl_pan_card_e_filing_password` | E filing password | ई-फायलिंग पासवर्ड |  |
| `tpl_pan_card_fathers_name` | Fathers name | बापायचें नांव |  |
| `tpl_pan_card_file_copy` | Scanned copy | स्कॅन केल्ली प्रत |  |
| `tpl_pan_card_name_on_pan` | Name on pan | PAN-चेर नांव |  |
| `tpl_pan_card_notes` | Notes | टिप |  |
| `tpl_pan_card_pan_number` | Pan number | PAN |  |
| `tpl_passkey` | Passkey | पासकी |  |
| `tpl_passkey_credential_id` | Credential ID | क्रेडेन्शियल ID |  |
| `tpl_passkey_notes` | Notes | टिप |  |
| `tpl_passkey_private_key` | Private key | खाजगी की |  |
| `tpl_passkey_sign_count` | Sign count | साइन काउंट |  |
| `tpl_passkey_user_handle` | User handle | वापरपी हँडल |  |
| `tpl_passkey_username` | Username | युजरनेम |  |
| `tpl_passkey_website` | Website | वेबसायट |  |
| `tpl_passport` | Passport | पासपोर्ट |  |
| `tpl_passport_dob` | Dob | जल्म तारीख |  |
| `tpl_passport_expiry_date` | Expiry date | मुदत सोंपपाची तारीख |  |
| `tpl_passport_file_copy` | Scanned copy | स्कॅन केल्ली प्रत |  |
| `tpl_passport_given_names` | Given names | दिल्लें नांव |  |
| `tpl_passport_issue_date` | Issue date | दिल्ली तारीख |  |
| `tpl_passport_notes` | Notes | टिप |  |
| `tpl_passport_passport_number` | Passport number | पासपोर्ट नंबर |  |
| `tpl_passport_place_of_issue` | Place of issue | दिल्ली सुवात |  |
| `tpl_passport_portal_login` | Portal login | Passport Seva लॉगिन |  |
| `tpl_passport_portal_password` | Portal password | Passport Seva पासवर्ड |  |
| `tpl_passport_surname` | Surname | आडनांव |  |
| `tpl_secure_note` | Secure Note | सुरक्षीत टिप |  |
| `tpl_secure_note_attachment` | Attachment | जोडणी |  |
| `tpl_secure_note_body` | Note | टिप |  |
| `tpl_shopping` | Shopping | खरेदी खातें |  |
| `tpl_shopping_gift_card_code` | Gift card code | गिफ्ट कार्ड कोड |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | गिफ्ट कार्ड पिन |  |
| `tpl_shopping_membership_id` | Membership id | सदस्यत्व ID |  |
| `tpl_shopping_notes` | Notes | टिप |  |
| `tpl_shopping_password` | Password | पासवर्ड |  |
| `tpl_shopping_registered_email` | Registered email | नोंदणी केल्लो ईमेल |  |
| `tpl_shopping_registered_mobile` | Registered mobile | नोंदणी केल्लो मोबायल |  |
| `tpl_shopping_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_telecom` | Telecom | मोबायल आनी इंटरनॅट |  |
| `tpl_telecom_account_number` | Account number | खातें नंबर |  |
| `tpl_telecom_circle` | Circle | सर्कल |  |
| `tpl_telecom_mobile_number` | Mobile number | मोबायल नंबर |  |
| `tpl_telecom_notes` | Notes | टिप |  |
| `tpl_telecom_operator` | Operator | कंपनी |  |
| `tpl_telecom_plan_type` | Plan type | प्लॅनाचो प्रकार |  |
| `tpl_telecom_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_telecom_puk` | PUK code | PUK कोड |  |
| `tpl_telecom_renewal_date` | Renewal date | रिचार्जाची तारीख |  |
| `tpl_telecom_sim_number` | Sim number | सिम नंबर (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | सिम पिन |  |
| `tpl_transit` | Transit | प्रवास पास |  |
| `tpl_transit_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_transit_notes` | Notes | टिप |  |
| `tpl_transit_operator_name` | Operator name | कंपनी |  |
| `tpl_transit_registered_email` | Registered email | नोंदणी केल्लो ईमेल |  |
| `tpl_transit_registered_mobile` | Registered mobile | नोंदणी केल्लो मोबायल |  |
| `tpl_transit_smart_card_number` | Smart card number | स्मार्ट कार्ड नंबर |  |
| `tpl_transit_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_travel_booking` | Travel Booking | प्रवास बुकिंग |  |
| `tpl_travel_booking_account_username` | Account username | युजरनेम |  |
| `tpl_travel_booking_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_travel_booking_notes` | Notes | टिप |  |
| `tpl_travel_booking_provider` | Provider | कंपनी |  |
| `tpl_travel_booking_registered_email` | Registered email | नोंदणी केल्लो ईमेल |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | नोंदणी केल्लो मोबायल |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_upi` | UPI | UPI |  |
| `tpl_upi_app_name` | App name | UPI ॲप |  |
| `tpl_upi_apps_used` | Apps used | खंयच्या ॲपांनी सक्रिय |  |
| `tpl_upi_linked_account` | Linked account | जोडिल्लें खातें |  |
| `tpl_upi_notes` | Notes | टिप |  |
| `tpl_upi_upi_id` | UPI ID | UPI ID |  |
| `tpl_upi_upi_pin` | UPI PIN | UPI पिन |  |
| `tpl_utility` | Utility | बिलां आनी जोडण्यो |  |
| `tpl_utility_account_holder` | Account holder | खातेदार |  |
| `tpl_utility_consumer_number` | Consumer number | गिरायक नंबर |  |
| `tpl_utility_due_day` | Bill due day | बिल भरपाचो दीस |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag ID |  |
| `tpl_utility_notes` | Notes | टिप |  |
| `tpl_utility_portal_login` | Portal login | पोर्टल लॉगिन |  |
| `tpl_utility_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_utility_provider` | Provider | सेवा दिवपी कंपनी |  |
| `tpl_utility_utility_kind` | Utility kind | कसलें बिल |  |
| `tpl_utility_vehicle_number` | Vehicle number | वाहन नंबर |  |
| `tpl_utility_wifi_password` | Wi-Fi password | Wi-Fi पासवर्ड |  |
| `tpl_voter_id` | Voter Id | मतदार वळख पत्र |  |
| `tpl_voter_id_constituency` | Constituency | मतदारसंघ |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC नंबर |  |
| `tpl_voter_id_file_copy` | Scanned copy | स्कॅन केल्ली प्रत |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | मतदार कार्डाचेर नांव |  |
| `tpl_voter_id_notes` | Notes | टिप |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP पासवर्ड |  |
| `tr_days_many` | %1$d days left | %1$d दीस उरले |  |
| `tr_days_one` | %1$d day left | %1$d दीस उरले |  |
| `tr_gone_today` | gone today | आयज वतली |  |
| `tr_restore` | Restore | परत हाडात |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | ते उपरांत त्यो सदांखातीर वतात — हेर खंयच प्रत ना. |  |
| `ui_hide_passphrase` | Hide passphrase | पासफ्रेज लिपयात |  |
| `ui_label_count` | %1$s, %2$s | %1$s, %2$s |  |
| `ui_show_passphrase` | Show passphrase | पासफ्रेज दाखयात |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | ह्याच फोनाचेर %1$d नोंदींवांगडा जुळयलीं. कितेंच खंयच धाडूंक ना. |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | ह्याच फोनाचेर %1$d नोंदीवांगडा जुळयली. कितेंच खंयच धाडूंक ना. |  |
| `vh_count_many` | %1$d things worth a look. | %1$d गजालींचेर लक्ष दिवंक जाय. |  |
| `vh_count_one` | %1$d thing worth a look. | %1$d गजालीचेर लक्ष दिवंक जाय. |  |
| `vh_empty` | No reused, weak or expiring credentials. | ना परत वापरिल्लें, ना दुबळें, ना मुदत सोंपपी कितें आसा. |  |
| `vh_kind_common` | Commonly guessed | सोंपेपणान अदमास येवपी |  |
| `vh_kind_expiring` | Expiring | मुदत सोंपता |  |
| `vh_kind_reused` | Reused password | परत वापरिल्लो पासवर्ड |  |
| `vh_kind_weak` | Weak | दुबळो |  |
| `vh_no_kit_title` | No recovery kit saved | खंयचीच रिकवरी किट सांबाळ्ळी ना |  |
| `vh_nothing` | Nothing to fix. | सारकें करपाक कितेंच ना. |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | ऑफलायन |  |
| `wl_chip_open` | Open source | उक्तें स्रोत |  |
| `wl_create` | Create a new vault | नवो भंडार तयार करात |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | ईमेल ना · खातें ना · कितेंच ह्या फोनांतल्यान भायर वचना |  |
| `wl_head_1` | Your keys. | तुमच्यो चावयो. |  |
| `wl_head_2` | Your device. | तुमचो फोन. |  |
| `wl_head_3` | No server. | सर्वर ना. |  |
| `wl_restore` | Restore from Recovery Kit | रिकवरी किटांतल्यान परत हाडात |  |
| `wl_sr_headline` | Your keys. Your device. No server. | तुमच्यो चावयो. तुमचो फोन. सर्वर ना. |  |
