# Hindi (`hi`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-hi/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Hindi | ok? |
|---|---|---|---|
| `au_close` | Close | बंद करें |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | चुनी गई तस्वीर में कोई सही TOTP क्यूआर कोड नहीं मिला |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | खाली से शुरू करें और अपने फ़ील्ड ख़ुद नाम दें — टेम्पलेट सिर्फ़ लेबल भरते हैं, डेटा कभी नहीं। |  |
| `hm_close_search` | Close search | खोज बंद करें |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | इस फ़ोन से कभी बाहर नहीं जाता। सहेजते समय एन्क्रिप्टेड। |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | अब वह फ़ाइल हटा दें जो आपने अभी आयात की। वह आपके पासवर्ड की सादी सूची है और अब भी आपके Downloads में पड़ी है। |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | सब कुछ सिर्फ़ इसी फ़ोन पर डिक्रिप्ट होता है। कुछ भी अपलोड नहीं होता, क्योंकि यह ऐप नेटवर्क कनेक्शन खोल ही नहीं सकता। |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | बैंक कभी आपका ओटीपी नहीं माँगते। जो माँगे, वह ठग है। |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | कोई बैंक अधिकारी आपसे स्क्रीन शेयर करने वाला ऐप कभी नहीं डलवाएगा। |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | आपका यूपीआई पिन सिर्फ़ यूपीआई ऐप के कीपैड के लिए है — कॉल पर किसी को मत बताइए। |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | केवाईसी एक दिन में खत्म नहीं होती। “आज केवाईसी खत्म हो रही है” वाले संदेश ठगी हैं। |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | पैसे लेने के लिए कभी पिन डालने या क्यूआर स्कैन करने की ज़रूरत नहीं होती। |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | बिजली कटने का एसएमएस, और उसमें किसी का निजी नंबर? वह ठगी है। |  |
| `nav_close_menu` | Close menu | मेन्यू बंद करें |  |
| `nfc_cannot_read` | Cannot read cards | कार्ड नहीं पढ़ सकते |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | इस फ़ोन में NFC नहीं है, इसलिए यह कार्ड नहीं पढ़ सकता। |  |
| `ob_fact_lost_title` | If you lose your keys | अगर चाबियाँ खो जाएँ |  |
| `ob_fact_network_note` | The app literally cannot phone home | यह ऐप कहीं संपर्क कर ही नहीं सकता |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | बायोमेट्रिक कभी सुरक्षित चिप से बाहर नहीं जाता |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | आपने कुंजी उसी फ़ोल्डर में रखी है जो आपकी एन्क्रिप्टेड तिजोरी सिंक करता है। अब जिसे वह फ़ोल्डर मिलेगा उसे दोनों हिस्से मिल जाएँगे। कुंजी कहीं और रखें — कागज़, कोई दूसरा खाता, या दराज़। |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | यह आपकी तिजोरी फ़ाइल के बगल में है |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH रिकवरी |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | स्कैन करें या टाइप कर दें। दोबारा इंस्टॉल, फ़ैक्टरी रीसेट, या फ़ोन खोने के बाद भी काम करेगी। |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | अभी सेव की हुई किट से समूह %1$d और समूह %2$d लिखिए। |  |
| `ob_kit_challenge_hint` | Group %1$d | समूह %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | पहले किट सेव करें, फिर समूह %1$d और %2$d वापस लिखें। |  |
| `ob_kit_challenge_title` | Check you actually have it | जाँच लें कि किट सच में आपके पास है |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | यह ऊपर दी कुंजी से मेल नहीं खाता। |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | कोई भी — Zerokosh भी — इसे मेरे लिए वापस नहीं ला सकता। |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "मैंने इसे ऑफ़लाइन रख लिया है। " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | एक बार दिखती है, कभी खुले रूप में नहीं रखी जाती। जब तक आप अंदर आ सकते हैं, सेटिंग्स से नई बना सकते हैं। |  |
| `ob_kit_head_emph` | On paper. | काग़ज़ पर। |  |
| `ob_kit_head_lead` | "One key. " | "एक चाबी। " |  |
| `ob_kit_head_tail` | " Never online." | " कभी ऑनलाइन नहीं।" |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | न जीमेल, न व्हाट्सऐप, न स्क्रीनशॉट। तिजोरी, बैंक लॉकर, या स्टील की प्लेट। |  |
| `ob_kit_offline_title` | Keep it off the internet | इसे इंटरनेट से दूर रखें |  |
| `ob_kit_print` | Print | प्रिंट करें |  |
| `ob_kit_print_note` | A printer, or Save as PDF | प्रिंटर, या PDF के रूप में सेव |  |
| `ob_kit_qr` | QR image | क्यूआर तस्वीर |  |
| `ob_kit_qr_cd` | Recovery key QR code | रिकवरी कुंजी का क्यूआर कोड |  |
| `ob_kit_qr_note` | To an offline gallery | ऑफ़लाइन गैलरी में |  |
| `ob_kit_regenerate` | Regenerate | नई बनाएँ |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | यह सेव नहीं हुआ। दोबारा कोशिश करें, या कोई और जगह चुनें। |  |
| `ob_kit_save_pdf` | Save PDF | पीडीएफ़ सहेजें |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | एक पन्ने की छपाई लायक किट |  |
| `ob_kit_saved` | I\'ve saved my kit | मैंने अपनी किट सहेज ली |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s में सेव हुआ |  |
| `ob_kit_sent_to_printer` | Sent to the printer | प्रिंटर को भेज दिया |  |
| `ob_kit_skip` | I\'ll do this later | यह बाद में करूँगा |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | आपकी तिजोरी चलती रहेगी। जब तक किट सेव नहीं होती, Zerokosh याद दिलाता रहेगा। |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | इसी फ़ोन पर बनी, एक ही बार दिखेगी। पासफ़्रेज़ भूल जाने पर अंदर लौटने का यही एक रास्ता है। |  |
| `ob_kit_working` | Working… | काम चल रहा है… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | तिजोरी के लेबल, टेम्पलेट और चेतावनियाँ तुरंत बदल जाएँगी। सेटिंग में कभी भी बदल सकते हैं। |  |
| `ob_pass_confirm` | Confirm | दोबारा लिखें |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | यह हमें कभी नहीं दिखता। कोई रीसेट लिंक नहीं है। |  |
| `ob_pass_head_emph` | held only | सिर्फ़ आपके |  |
| `ob_pass_head_lead` | "One secret, " | "एक ही राज़, " |  |
| `ob_pass_head_tail` | " by you." | " पास।" |  |
| `ob_pass_no_match` | no match | मेल नहीं खा रहा |  |
| `ob_pass_seal` | Seal the vault | तिजोरी बंद करें |  |
| `ob_pass_sealing` | Sealing… | बंद किया जा रहा है… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | तीन-चार बेमेल शब्द, एक चालाक शब्द से बेहतर हैं। इस स्क्रीन से कुछ बाहर नहीं जाता। |  |
| `ob_pass_tab_passphrase` | Passphrase | पासफ़्रेज़ |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 अंकों का पिन |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | आपका फ़िंगरप्रिंट फ़ोन की सुरक्षित चिप के भीतर रहता है। वह इस फ़ोन से कभी बाहर नहीं जाता। |  |
| `ob_trust_continue` | I understand · Continue | समझ गया · आगे बढ़ें |  |
| `ob_trust_head_emph` | don\'t | नहीं |  |
| `ob_trust_head_lead` | "Exactly what we " | "हम असल में क्या " |  |
| `ob_trust_head_tail` | " know." | " जानते।" |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | पासफ़्रेज़ भूल गए और रिकवरी किट भी खो गई, तो तिजोरी बंद ही रहेगी — आपके लिए भी, हमारे लिए भी, किसी के लिए भी। |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "चाबियाँ आपके पास हैं। " |  |
| `ob_trust_stat_files` | .kosh file on device | फ़ोन में .kosh फ़ाइल |  |
| `ob_trust_stat_servers` | servers contacted | सर्वर से संपर्क |  |
| `ob_trust_stat_trackers` | trackers or SDKs | ट्रैकर या SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | इसे एक बार पढ़ लें। पूरी सुरक्षा व्यवस्था यही है, सीधी भाषा में। |  |
| `ob_trust_tag_audited` | Audited build | ऑडिट किया गया बिल्ड |  |
| `ob_trust_tag_reproducible` | Reproducible APK | दोबारा बनाया जा सकने वाला APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | आप उँगली से खोल रहे हैं। अगर कभी उँगली काम करना बंद कर दे, तो यही अंदर लाएगा — इसलिए जाँच लेना ठीक है। |  |
| `pc_confirm` | Check | जाँचें |  |
| `pc_correct` | Still correct. Nothing to do. | अब भी सही है। कुछ करने की ज़रूरत नहीं। |  |
| `pc_forgot` | I cannot remember it | मुझे याद नहीं आ रहा |  |
| `pc_later` | Not now | अभी नहीं |  |
| `pc_reset_action` | Set new passphrase | नया पासफ़्रेज़ लगाएँ |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | आपकी उँगली यह तिजोरी खोल सकती है, इसलिए वही नया पासफ़्रेज़ भी बना सकती है — रिकवरी किट की ज़रूरत नहीं। पुष्टि के लिए एक बार फिर पूछा जाएगा। |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | पासफ़्रेज़ बदल गया। तेज़ अनलॉक फिर से लग गया। |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | यह नहीं हो पाया। आपका पुराना पासफ़्रेज़ ही चल रहा है। |  |
| `pc_reset_title` | Set a new passphrase | नया पासफ़्रेज़ बनाएँ |  |
| `pc_title` | Do you still remember your passphrase? | क्या आपको अब भी अपना पासफ़्रेज़ याद है? |  |
| `pc_wrong` | That is not it. You can set a new one instead. | यह वह नहीं है। आप इसकी जगह नया बना सकते हैं। |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | इस रिकॉर्ड के लिए याद रखे %1$d मान हटा दिए जाएँगे। यह वापस नहीं लाया जा सकता। |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh वह लॉगिन सहेज नहीं सका। |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | भरने के लिए Zerokosh खोलें |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | यही एक पासफ़्रेज़ सब कुछ बंद रखता है। कुछ लंबा चुनें, जो सिर्फ़ आप जानते हों। |  |
| `scr_create_button` | Lock it in | बंद कर दें |  |
| `scr_create_confirm_hint` | Type it again | दोबारा लिखें |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | पासफ़्रेज़ (कम से कम 10 अक्षर) |  |
| `scr_create_mismatch` | The two entries don\'t match | दोनों एक जैसे नहीं हैं |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 अंकों का पिन |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | इसकी जगह 6 अंकों का पिन रखें |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | पिन के लिए फ़िंगरप्रिंट या फ़ेस अनलॉक वाला फ़ोन चाहिए। कृपया पासफ़्रेज़ चुनें। |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | पिन इसलिए चल जाता है क्योंकि यह फ़ोन उसे अपनी सुरक्षा चिप और आपके फ़िंगरप्रिंट या चेहरे से बचाता है। |  |
| `scr_create_strength_fair` | Fair | ठीक-ठाक |  |
| `scr_create_strength_good` | Good | अच्छा |  |
| `scr_create_strength_strong` | Strong | मज़बूत |  |
| `scr_create_strength_weak` | Weak | कमज़ोर |  |
| `scr_create_title` | Create your passphrase | अपना पासफ़्रेज़ बनाएँ |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | कम से कम 10 अक्षर रखें — जितना लंबा, उतना मज़बूत |  |
| `scr_create_working` | Preparing your vault… | आपकी तिजोरी तैयार हो रही है… |  |
| `scr_detail_delete` | Delete | हटाएँ |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | यह 30 दिन तक “हाल में हटाए गए” में रहेगा, और आपके दूसरे फ़ोनों से सिंक होने पर हट जाएगा। |  |
| `scr_detail_delete_confirm_title` | Delete this record? | यह रिकॉर्ड हटाएँ? |  |
| `scr_detail_delete_confirm_yes` | Delete | हटाएँ |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | सीक्रेट या otpauth:// लिंक चिपकाएँ |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | अपना फ़िंगरप्रिंट या चेहरा इस्तेमाल करें |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh खोलें |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | बहुत बार ग़लत कोशिश हुई। %1$d सेकंड रुकें। |  |
| `scr_lock_hint` | Passphrase | पासफ़्रेज़ |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | यह रिकवरी कुंजी सही नहीं है — एक-एक अक्षर मिलाकर देखें |  |
| `scr_lock_title` | Vault is locked | तिजोरी बंद है |  |
| `scr_lock_unlock` | Unlock | खोलें |  |
| `scr_lock_use_passphrase` | Use passphrase | पासफ़्रेज़ इस्तेमाल करें |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | कृपया एक बार पासफ़्रेज़ से खोलें |  |
| `scr_lock_use_recovery` | Use Recovery Key | रिकवरी कुंजी इस्तेमाल करें |  |
| `scr_lock_wrong` | Wrong passphrase | पासफ़्रेज़ ग़लत है |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | अगर कभी पासफ़्रेज़ भूल जाएँ, तो अंदर लौटने का सिर्फ़ यही एक रास्ता है। हम इसे रीसेट नहीं कर सकते — कोई नहीं कर सकता। |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | मैंने इसे लिख लिया है और सुरक्षित जगह रख दिया है |  |
| `scr_recovery_done` | Continue | आगे बढ़ें |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | यह कुंजी सिर्फ़ एक बार दिखती है। जब तक आप ताला खोल सकते हैं, सेटिंग्स से कभी भी नई बना सकते हैं। |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | इस पन्ने को अपने ज़मीन-जायदाद के कागज़ों या दूसरे ज़रूरी दस्तावेज़ों के साथ रखें। जिसके पास यह कुंजी होगी वह आपकी तिजोरी खोल सकता है — इसे लॉकर की चाबी की तरह सँभालें। |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | रिकवरी किट पीडीएफ़ सहेज दी गई |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh रिकवरी किट |  |
| `scr_recovery_save_pdf` | Save as PDF | पीडीएफ़ में सहेजें |  |
| `scr_recovery_title` | Your Recovery Key | आपकी रिकवरी कुंजी |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | आप जो कुछ सहेजते हैं वह आपके फ़ोन की एक बंद फ़ाइल में रहता है। वह कभी हम तक नहीं आता — हमारे पास उसे रखने की जगह ही नहीं है। |  |
| `scr_trust_card1_title` | Your data stays on this device | आपका डेटा इसी फ़ोन में रहता है |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | न कोई Zerokosh खाता, न क्लाउड, न साइन-अप। इसे सिर्फ़ आप खोल सकते हैं। हम भी नहीं। |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | हमारा कोई सर्वर नहीं — न हैक होने को कुछ, न बेचने को |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | न कोई सदस्यता शुल्क, न विज्ञापन। कोई भी हमारा कोड पढ़कर हमारा हर वादा जाँच सकता है। |  |
| `scr_trust_card3_title` | Free forever, open source | हमेशा मुफ़्त, ओपन सोर्स |  |
| `scr_trust_continue` | Continue | आगे बढ़ें |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | आपके बदलाव सहेजे नहीं गए, इसलिए तिजोरी में जो पहले था उसमें से कुछ नहीं खोया। |  |
| `st_recently_deleted` | Recently deleted | हाल में हटाए गए |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | वन-टाइम कोड (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | वन-टाइम कोड (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | वन-टाइम कोड (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA सीक्रेट |  |
| `tr_cannot_undo` | This cannot be undone. | यह वापस नहीं लाया जा सकता। |  |
| `tr_delete_all` | Delete all permanently | सब कुछ हमेशा के लिए हटाएँ |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d रिकॉर्ड हमेशा के लिए चले जाएँगे। यह वापस नहीं लाया जा सकता और बहाल करने को कोई बैकअप नहीं है। |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d रिकॉर्ड हमेशा के लिए चला जाएगा। यह वापस नहीं लाया जा सकता और बहाल करने को कोई बैकअप नहीं है। |  |
| `tr_delete_all_title` | Delete everything in the trash? | कूड़ेदान का सब कुछ हटा दें? |  |
| `tr_delete_now` | Delete now | अभी हटाएँ |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | “%1$s” को हमेशा के लिए हटाएँ? |  |
| `tr_empty` | Nothing deleted. | कुछ भी हटाया नहीं गया। |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | हटाए गए रिकॉर्ड यहाँ %1$d दिन रुकते हैं। |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | भारतीय बैंक, यूपीआई, कार्ड, डीमैट, ईपीएफ़ और जिन ओटीपी ऐप्स को आप सचमुच इस्तेमाल करते हैं — उन सबके लिए बनी, फ़ोन में ही रहने वाली तिजोरी। |  |

## Priority 2 — longer prose

| key | English | Hindi | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | उसी एक जानकारी से शुरू करें जो सबसे ज़्यादा काम आती है। नोट्स ऐप में पड़े बारह पासवर्ड से एक सहेजा पासवर्ड भी ज़्यादा सुरक्षित है। |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | पासफ़्रेज़ भूल गए तो रिकवरी किट ही अंदर आने का रास्ता है। कोई आपके लिए दूसरी नहीं बना सकता। |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | उस फ़ाइल में पहचानने लायक कुछ नहीं मिला। Chrome, Google Password Manager, Bitwarden, LastPass और KeePass के एक्सपोर्ट समझे जाते हैं। |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d मौजूदा रिकॉर्ड बदले जाएँगे — साइट और यूज़रनेम से मिलान करके। बदले गए पासवर्ड हर रिकॉर्ड के इतिहास में वापस मिल जाएँगे। |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d मौजूदा रिकॉर्ड बदला जाएगा — साइट और यूज़रनेम से मिलान करके। बदले गए पासवर्ड हर रिकॉर्ड के इतिहास में वापस मिल जाएँगे। |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d रिकॉर्ड दोनों जगह बदले गए थे। दोनों रूप रखे गए हैं — “(conflict copy)” खोजें। |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | वह पासफ़्रेज़ डालें जो इस बैकअप फ़ाइल को खोलता है। यह आपके मौजूदा पासफ़्रेज़ से अलग हो सकता है। |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | .kosh फ़ाइल का Poly1305 प्रमाणीकरण टैग मेल नहीं खाता। ऐसा अधूरे सिंक या ख़राब स्टोरेज के बाद हो सकता है। |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh तिजोरी फ़ाइल के साथ एक चलता-फिरता बैकअप रखता है। उसे अपने सिंक फ़ोल्डर से बहाल करें, या किसी दूसरे फ़ोन पर रिकवरी किट से तिजोरी खोलें। |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | कार्ड को फ़ोन के पीछे सीधा टिकाए रखें जब तक वह पढ़ न ले। इससे कार्ड नंबर, वैधता और नाम आ जाते हैं — CVV चिप में नहीं होता, वह आपको ख़ुद लिखना होगा। |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | अंदर लौटने का एक तेज़ रास्ता चुनें। तिजोरी की पहरेदारी पासफ़्रेज़ ही करता रहेगा; यह सिर्फ़ इसी फ़ोन पर चाबी खोलता है। |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | अब आपकी तिजोरी में असली जानकारी है। रिकवरी किट के बिना पासफ़्रेज़ भूलने पर कोई आपको वापस अंदर नहीं ला सकता — हम भी नहीं। |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh मुफ़्त और ओपन सोर्स है, और इसका कोई सर्वर नहीं है। आपकी तिजोरी सिर्फ़ आप खोल सकते हैं। हम भी नहीं। |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | कैमरे की अनुमति सिर्फ़ क्यूआर कोड स्कैन करने के लिए चाहिए। रिकॉर्ड जोड़ते समय आप सीक्रेट हाथ से भी चिपका सकते हैं। |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | उन बैंक ऐप के लिए, जो ऑटोफ़िल नहीं चलने देते — बटन दबाकर लॉगिन की जानकारी एक-एक करके कॉपी करें |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | जैसे आप अपना फ़ोन खोलते हैं, वैसे ही तिजोरी भी। पासफ़्रेज़ हमेशा काम करता रहेगा। |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | तिजोरी के स्क्रीनशॉट क्लाउड फ़ोटो बैकअप में पहुँच सकते हैं। बहुत ज़रूरी हो तभी चालू करें। |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | तिजोरी फ़ाइल लिखी नहीं जा सकी। अगर आपने बैकअप और सिंक फ़ोल्डर सेट किया है, तो हो सकता है Android ने उसकी अनुमति वापस ले ली हो — सेटिंग खोलें, फ़ोल्डर दोबारा चुनें, और फिर कोशिश करें। |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | आपकी एन्क्रिप्टेड .kosh फ़ाइलें सीधे इसी फ़ोल्डर में सहेजी जाती हैं। कई डिवाइस पर अपने-आप बैकअप के लिए इस फ़ोल्डर को Google Drive, Syncthing, Nextcloud या एसडी कार्ड से सिंक करें। |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | नई रिकवरी कुंजी बनाने के लिए अपना पासफ़्रेज़ डालें। पुरानी कुंजी काम करना बंद कर देगी। |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | इस पन्ने की बाकी हर बात एक लॉगिन कमज़ोर करती है। यह पूरी तिजोरी ले जा सकती है। सेटिंग्स → नई रिकवरी कुंजी लें। |  |

## Priority 3 — short labels

| key | English | Hindi | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | मज़बूत पासवर्ड इस्तेमाल करें |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | खाते का नाम (जैसे Google) |  |
| `au_active_many` | %1$d active codes | %1$d चालू कोड |  |
| `au_active_one` | %1$d active code | %1$d चालू कोड |  |
| `au_add_another` | Add another authenticator | एक और ऑथेंटिकेटर जोड़ें |  |
| `au_add_secret` | Add Secret Key | सीक्रेट की जोड़ें |  |
| `au_camera_needed` | Camera permission needed to scan QR code | क्यूआर कोड स्कैन करने के लिए कैमरे की अनुमति चाहिए |  |
| `au_copied` | Copied · clears shortly | कॉपी हो गया · थोड़ी देर में मिट जाएगा |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google, GitHub या अपने ब्रोकर का क्यूआर स्कैन करें, या सीक्रेट की हाथ से लिखें। |  |
| `au_enter_key` | Enter Key | की लिखें |  |
| `au_fallback_name` | Authenticator | ऑथेंटिकेटर |  |
| `au_flashlight` | Flashlight | टॉर्च |  |
| `au_grant` | Grant Permission | अनुमति दें |  |
| `au_image_failed` | Failed to process image | तस्वीर पढ़ी नहीं जा सकी |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | ग़लत Base32 सीक्रेट की (सिर्फ़ A-Z अक्षर और 2-7 अंक) |  |
| `au_no_match` | No codes match | कोई कोड नहीं मिला |  |
| `au_none_yet` | No codes yet. | अभी कोई कोड नहीं। |  |
| `au_pick_image` | Pick Image | तस्वीर चुनें |  |
| `au_rotating` | "Rotating " | "बदलते रहते हैं " |  |
| `au_rotating_emph` | codes. | कोड। |  |
| `au_save_key` | Save Key | की सहेजें |  |
| `au_scan_qr` | Scan a QR code | क्यूआर कोड स्कैन करें |  |
| `au_scan_title` | Scan Authenticator QR | ऑथेंटिकेटर क्यूआर स्कैन करें |  |
| `au_search_hint` | Search codes, issuers… | कोड, जारीकर्ता खोजें… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | जैसे JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | सीक्रेट की (Base32) |  |
| `au_tap_to_copy` | Tap to copy | कॉपी करने के लिए टैप करें |  |
| `cat_apps` | Apps &amp; Logins | ऐप और लॉगिन |  |
| `cat_banks` | Banks &amp; UPI | बैंक और यूपीआई |  |
| `cat_cards` | Cards | कार्ड |  |
| `cat_govid` | Gov &amp; ID | सरकारी और पहचान |  |
| `cat_investments` | Investments | निवेश |  |
| `cat_utilities` | Utilities | बिल और कनेक्शन |  |
| `cd_mask_hidden` | hidden | छिपा हुआ |  |
| `cd_shield_high_sensitivity` | extra-protected field | अतिरिक्त सुरक्षित जानकारी |  |
| `gl_blank` | Blank template | खाली टेम्पलेट |  |
| `gl_cat_apps` | Apps | ऐप |  |
| `gl_cat_banks` | Banks | बैंक |  |
| `gl_cat_cards` | Cards | कार्ड |  |
| `gl_cat_demat` | Demat | डीमैट |  |
| `gl_cat_govid` | Gov ID | सरकारी पहचान |  |
| `gl_cat_popular` | Popular | लोकप्रिय |  |
| `gl_cat_shopping` | Shopping | ख़रीदारी |  |
| `gl_cat_travel` | Travel | यात्रा |  |
| `gl_cat_upi` | UPI | यूपीआई |  |
| `gl_cat_utilities` | Utilities | बिल और कनेक्शन |  |
| `gl_head_emph` | storing? | सहेज रहे हैं? |  |
| `gl_head_lead` | "What are we " | "हम क्या " |  |
| `gl_matches` | %1$d matches | %1$d मिले |  |
| `gl_most_used` | Most-used first | सबसे ज़्यादा इस्तेमाल पहले |  |
| `gl_not_found` | Can’t find a service? | सेवा नहीं मिल रही? |  |
| `gl_search` | Search %1$d Indian services… | %1$d भारतीय सेवाओं में खोजें… |  |
| `gl_suggested` | Suggested for you | आपके लिए सुझाव |  |
| `hm_add_first` | Add your first record | अपना पहला रिकॉर्ड जोड़ें |  |
| `hm_all_offline` | all offline. | सब ऑफ़लाइन। |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d जानकारियाँ, " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d जानकारी, " |  |
| `hm_detail_placeholder` | Pick a record to see it here | यहाँ देखने के लिए कोई रिकॉर्ड चुनें |  |
| `hm_empty_blank` | A blank vault, ready. | खाली तिजोरी, तैयार है। |  |
| `hm_empty_head_emph` | waiting. | इंतज़ार कर रही है। |  |
| `hm_empty_head_lead` | "Your vault is " | "आपकी तिजोरी " |  |
| `hm_filter_all` | All | सभी |  |
| `hm_import_backup` | Import an encrypted backup | एन्क्रिप्टेड बैकअप आयात करें |  |
| `hm_import_backup_note` | Open a .kosh file from this device | इसी फ़ोन से .kosh फ़ाइल खोलें |  |
| `hm_inst_many` | %1$d institutions | %1$d संस्थाएँ |  |
| `hm_inst_one` | %1$d institution | %1$d संस्था |  |
| `hm_kit_banner_action` | Save one now | अभी सेव करें |  |
| `hm_kit_banner_dismiss` | Remind me later | बाद में याद दिलाएँ |  |
| `hm_kit_banner_title` | No recovery kit saved | कोई रिकवरी किट सेव नहीं है |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | खुला है · छोड़ते ही बंद |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | खुला है · छोड़ने के %1$d मिनट बाद बंद |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | खुला है · छोड़ने के 1 मिनट बाद बंद |  |
| `hm_no_match` | Nothing matches “%1$s” | “%1$s” से कुछ नहीं मिला |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | कोई संस्था, यूपीआई हैंडल, या आख़िरी चार अंक आज़माएँ। |  |
| `hm_pinned` | Pinned | पिन किए गए |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC, priya@upi, पैन… खोजें |  |
| `hm_start_template` | Start with a template | टेम्पलेट से शुरू करें |  |
| `ic_could_not` | Could not import | आयात नहीं हो सका |  |
| `ic_done` | Done | हो गया |  |
| `ic_import` | Import | आयात करें |  |
| `ic_imported` | Imported | आयात हो गया |  |
| `ic_importing` | Importing… | आयात हो रहा है… |  |
| `ic_new_many` | %1$d new logins. | %1$d नए लॉगिन। |  |
| `ic_new_one` | %1$d new login. | %1$d नया लॉगिन। |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d जोड़े गए, %2$d बदले गए। |  |
| `ic_title` | Import from %1$s? | %1$s से आयात करें? |  |
| `ic_too_large` | That file is too large to be a credential export. | यह फ़ाइल इतनी बड़ी है कि पासवर्ड एक्सपोर्ट हो ही नहीं सकती। |  |
| `import_action` | Import | आयात करें |  |
| `import_locked` | Unlock your vault before importing. | आयात करने से पहले अपनी तिजोरी खोलें। |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d जोड़े गए, %2$d बदले गए। कुछ भी मिटाया नहीं गया। |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | वह फ़ाइल Zerokosh तिजोरी के रूप में नहीं पढ़ी जा सकी। |  |
| `import_nothing_new` | Everything in that backup was already here. | उस बैकअप का सब कुछ पहले से यहाँ था। |  |
| `import_passphrase_label` | Backup passphrase | बैकअप का पासफ़्रेज़ |  |
| `import_title` | Import a backup | बैकअप आयात करें |  |
| `kicker_locked` | Locked | बंद है |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · कुछ भी इस फ़ोन से बाहर नहीं गया |  |
| `lk_touch_unlock` | Touch to unlock | खोलने के लिए छुएँ |  |
| `lk_welcome_emph` | Your vault is sealed. | आपकी तिजोरी बंद है। |  |
| `lk_welcome_lead` | Welcome back. | वापस स्वागत है। |  |
| `msg_auth_needed` | Confirm it\'s you to see this | देखने के लिए पुष्टि करें कि यह आप ही हैं |  |
| `msg_back` | Back | वापस |  |
| `msg_cancel` | Cancel | रद्द करें |  |
| `msg_file_damaged` | File damaged — restored from backup | फ़ाइल ख़राब थी — बैकअप से ठीक कर दी गई |  |
| `msg_ok` | OK | ठीक है |  |
| `msg_saved` | Saved | सहेज लिया |  |
| `nav_all_templates` | All templates | सभी टेम्पलेट |  |
| `nav_damaged_emph` | vault file | तिजोरी फ़ाइल |  |
| `nav_damaged_kicker` | Damaged state | ख़राब स्थिति |  |
| `nav_damaged_lead` | "Something in the " | "आपकी " |  |
| `nav_damaged_tail` | " is off." | " में कुछ गड़बड़ है।" |  |
| `nav_integrity_title` | Integrity check failed | अखंडता जाँच विफल |  |
| `nav_scan` | Scan | स्कैन |  |
| `nav_tap_card` | Tap a card | कार्ड टैप करें |  |
| `nav_what_next` | What to do next | अब क्या करें |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC बंद है। सेटिंग में जाकर चालू करें, फिर कोशिश करें। |  |
| `nfc_hold_card` | Hold your card to the phone | कार्ड को फ़ोन से लगाएँ |  |
| `nfc_missed` | Did not catch that | पकड़ में नहीं आया |  |
| `nfc_read_failed` | That card could not be read. Try again. | वह कार्ड पढ़ा नहीं जा सका। दोबारा कोशिश करें। |  |
| `nfc_reading` | Reading… | पढ़ा जा रहा है… |  |
| `nfc_try_again` | Try again | दोबारा कोशिश करें |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · इसी फ़ोन पर मापा गया |  |
| `ob_argon_faster` | Faster unlock | जल्दी खुलेगा |  |
| `ob_argon_harder` | Harder to attack | तोड़ना मुश्किल |  |
| `ob_argon_measuring` | Measuring this device… | यह फ़ोन मापा जा रहा है… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id की कठिनाई |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | कोई एक शब्दकोश वाला शब्द नहीं |  |
| `ob_check_pass_length` | 10 characters or more | 10 या उससे ज़्यादा अक्षर |  |
| `ob_check_pass_reuse` | Not reused from another app | किसी और ऐप से दोहराया हुआ नहीं |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | न जन्मदिन, न सालगिरह |  |
| `ob_check_pin_digits` | All six digits entered | छहों अंक भर दिए |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | न लगातार अंक, न दोहराव |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~तोड़ने में %1$d सदियाँ |  |
| `ob_crack_days` | ~%1$d days to crack | ~तोड़ने में %1$d दिन |  |
| `ob_crack_forever` | longer than the sun | सूरज से भी ज़्यादा टिकेगा |  |
| `ob_crack_hours` | ~hours to crack | ~तोड़ने में कुछ घंटे |  |
| `ob_crack_seconds` | ~seconds to crack | ~तोड़ने में कुछ सेकंड |  |
| `ob_crack_years` | ~%1$d years to crack | ~तोड़ने में %1$d साल |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | प्रमाणित, हर तिजोरी का अलग नॉन्स |  |
| `ob_fact_encryption_title` | Encryption | एन्क्रिप्शन |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | सेटअप के समय आपके फ़ोन पर मापा गया |  |
| `ob_fact_kdf_title` | Key stretching | की स्ट्रेचिंग |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | न रीसेट लिंक, न सपोर्ट का पिछला दरवाज़ा। |  |
| `ob_fact_lost_value` | Nobody can recover it | कोई वापस नहीं ला सकता |  |
| `ob_fact_network_title` | Network permission | नेटवर्क की अनुमति |  |
| `ob_fact_network_value` | Not requested | माँगी ही नहीं गई |  |
| `ob_fact_quick_title` | Quick unlock | झटपट अनलॉक |  |
| `ob_fact_quick_value` | Hardware keystore | हार्डवेयर कीस्टोर |  |
| `ob_lang_continue` | Continue in %1$s | %1$s में आगे बढ़ें |  |
| `ob_lang_head_emph` | language. | भाषा चुनें। |  |
| `ob_lang_head_lead` | "Choose your " | "अपनी " |  |
| `ob_lang_search` | Search %1$d languages | %1$d भाषाओं में खोजें |  |
| `ob_quick_continue_pass` | Continue with passphrase | पासफ़्रेज़ के साथ आगे बढ़ें |  |
| `ob_quick_enable` | Enable quick unlock | झटपट अनलॉक चालू करें |  |
| `ob_quick_fingerprint` | Fingerprint | फ़िंगरप्रिंट |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | तेज़, हार्डवेयर से सुरक्षित अनलॉक। |  |
| `ob_quick_head_emph` | Without the cloud. | बिना क्लाउड के। |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "एक छुअन में खुल जाए। " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox. कोई बायोमेट्रिक जानकारी Zerokosh तक कभी नहीं पहुँचती। |  |
| `ob_quick_hw_title` | Hardware-backed | हार्डवेयर से सुरक्षित |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | इस फ़ोन में हार्डवेयर वाला सेंसर नहीं है। |  |
| `ob_quick_opening` | Opening your vault… | आपकी तिजोरी खुल रही है… |  |
| `ob_quick_pass_only` | Passphrase only | सिर्फ़ पासफ़्रेज़ |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | हर बार लिखें। सबसे सुरक्षित। |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | झटपट अनलॉक सेट नहीं हुआ। दोबारा कोशिश करें, या सिर्फ़ पासफ़्रेज़ के साथ आगे बढ़ें। |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | अभी रहने दें — मैं पासफ़्रेज़ लिख लूँगा |  |
| `ob_quick_touch_title` | Touch the sensor | सेंसर को छुएँ |  |
| `ob_recommended` | Recommended | सुझाया गया |  |
| `ob_reveal_hide` | Hide | छिपाएँ |  |
| `ob_reveal_show` | Show | दिखाएँ |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | लपेटने वाली चाबी हार्डवेयर कीस्टोर में रहती है। बायोमेट्रिक अगले चरण में। |  |
| `ob_seal_title` | Seal to this device | इसी फ़ोन से बाँध दें |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | इस फ़ोन में हार्डवेयर वाला बायोमेट्रिक नहीं है। |  |
| `ob_soon` | SOON | जल्द |  |
| `ob_step_label` | Step %1$d of 6 | चरण %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | कुछ ऐसा चुनें जो सिर्फ़ आप कहते हों |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | अच्छा · %1$d बिट एन्ट्रॉपी |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | बहुत छोटा · 10 अक्षर चाहिए |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | मज़बूत · %1$d बिट एन्ट्रॉपी |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | कमज़ोर · %1$d बिट एन्ट्रॉपी |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | ऐसे छह अंक जो आपकी ज़िंदगी देखकर कोई न भाँप सके |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | ठीक-ठाक · %1$d बिट — पिन इससे ज़्यादा मज़बूत हो नहीं सकता |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | बहुत छोटा · 6 अंक चाहिए |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | कमज़ोर · यही पिन सबसे पहले आज़माए जाते हैं |  |
| `ob_try_label` | TRY | आज़माएँ |  |
| `qa_aadhaar` | Aadhaar | आधार |  |
| `qa_bank_account` | Bank account | बैंक खाता |  |
| `qa_pan` | PAN | पैन |  |
| `qa_upi_id` | UPI ID | यूपीआई आईडी |  |
| `rd_copied` | Copied | कॉपी हो गया |  |
| `rd_forget` | Forget | भुला दें |  |
| `rd_forget_these` | Forget these | इन्हें भुला दें |  |
| `rd_forget_title` | Forget previous passwords? | पिछले पासवर्ड भुला दें? |  |
| `rd_history_hide` | Hide | छिपाएँ |  |
| `rd_history_show` | Show %1$d | %1$d दिखाएँ |  |
| `rd_hold_to_reveal` | Hold to reveal | देखने के लिए दबाए रखें |  |
| `rd_last_edit` | last edit %1$s | आख़िरी बदलाव %1$s |  |
| `rd_release_to_hide` | Release to hide | छिपाने के लिए छोड़ें |  |
| `re_add_field` | + Add another field | + एक और फ़ील्ड जोड़ें |  |
| `re_add_field_title` | Add a field | फ़ील्ड जोड़ें |  |
| `re_field_name` | Field name | फ़ील्ड का नाम |  |
| `re_pick_date` | Pick a date | तारीख़ चुनें |  |
| `re_remove` | Remove | हटाएँ |  |
| `re_tap_card` | Read the card by tapping it | कार्ड टैप करके पढ़ें |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | गोपनीय मानें (छिपा रहेगा, देखने के लिए दबाए रखें) |  |
| `re_using_template` | using the %1$s template | %1$s टेम्पलेट से |  |
| `rem_kit_title` | No recovery kit saved | कोई रिकवरी किट सेव नहीं है |  |
| `scr_about_license` | License: GPL-3.0 — free forever | लाइसेंस: GPL-3.0 — हमेशा मुफ़्त |  |
| `scr_about_source` | Source code | सोर्स कोड |  |
| `scr_about_version` | Version %1$s | वर्शन %1$s |  |
| `scr_auth_add_qr` | Add via QR | क्यूआर से जोड़ें |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | आपके ऐप और ब्रोकर के कोड यहाँ दिखेंगे |  |
| `scr_auth_scan_title` | Point the camera at the QR code | कैमरे को क्यूआर कोड पर रखें |  |
| `scr_detail_copied` | Copied · clears in 30s | कॉपी हो गया · 30 सेकंड में मिट जाएगा |  |
| `scr_detail_copy` | Copy | कॉपी |  |
| `scr_detail_edit` | Edit | बदलें |  |
| `scr_detail_favorite` | Favourite | पसंदीदा |  |
| `scr_detail_hidden` | Hidden | छिपा है |  |
| `scr_detail_hide` | Hide | छिपाएँ |  |
| `scr_detail_history_empty` | Nothing replaced yet. | अभी तक कुछ नहीं बदला। |  |
| `scr_detail_history_title` | Previous passwords | पिछले पासवर्ड |  |
| `scr_detail_reveal` | Show | दिखाएँ |  |
| `scr_detail_shown` | Shown | दिख रहा है |  |
| `scr_edit_cancel` | Cancel | रद्द करें |  |
| `scr_edit_generate` | Generate | बनाएँ |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | बैंक / कंपनी (समूह बनाने के लिए) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | यह सही नहीं लग रहा — एक बार देख लें |  |
| `scr_edit_link_none` | None | कोई नहीं |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | यह कार्ड नंबर सामान्य जाँच में खरा नहीं उतरता — अगर सही है तो सहेज दें |  |
| `scr_edit_month` | Month | महीना |  |
| `scr_edit_picker_other` | Other… | अन्य… |  |
| `scr_edit_picker_other_hint` | Type your own | अपना लिखें |  |
| `scr_edit_required_title` | Give it a name first | पहले इसे कोई नाम दें |  |
| `scr_edit_save` | Save | सहेजें |  |
| `scr_edit_title_hint` | Title | नाम |  |
| `scr_edit_title_new` | New | नया |  |
| `scr_edit_year` | Year | साल |  |
| `scr_gallery_quick_add` | Quick add | झटपट जोड़ें |  |
| `scr_gallery_title` | What do you want to save? | आप क्या सहेजना चाहते हैं? |  |
| `scr_home_add` | Add | जोड़ें |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | आपका बैंक खाता ऐसा दिखेगा |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | आपके कार्ड, यूपीआई और ऐप लॉगिन भी यहीं रहेंगे |  |
| `scr_home_group_other` | Other | अन्य |  |
| `scr_home_no_results` | Nothing matches your search | आपकी खोज से कुछ नहीं मिला |  |
| `scr_home_search_hint` | Search your vault | अपनी तिजोरी में खोजें |  |
| `scr_home_tab_authenticator` | Authenticator | कोड |  |
| `scr_home_tab_home` | Home | होम |  |
| `scr_home_tab_settings` | Settings | सेटिंग |  |
| `scr_home_title` | Home | होम |  |
| `scr_language_continue` | Continue | आगे बढ़ें |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | अपनी भाषा चुनें |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | कॉपी करने के लिए बटन दबाएँ · 30 सेकंड में मिट जाएगा |  |
| `scr_login_helper_channel` | Login helper | लॉगिन सहायक |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s में लॉगिन हो रहे हैं |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | अभी बैकअप फ़ोल्डर सेट करें |  |
| `scr_quickunlock_enable` | Turn on | चालू करें |  |
| `scr_quickunlock_skip` | Not now | अभी नहीं |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | फ़िंगरप्रिंट या चेहरे से खोलें |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s की तारीख़ पास है · Zerokosh खोलें |  |
| `scr_reminder_channel` | Renewal reminders | नवीनीकरण याद-दिलावे |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh याद दिला रहा है |  |
| `scr_settings_about` | About | परिचय |  |
| `scr_settings_allow_screenshots` | Allow screenshots | स्क्रीनशॉट लेने दें |  |
| `scr_settings_autofill` | Autofill service | ऑटोफ़िल सेवा |  |
| `scr_settings_autofill_off` | Not set up | सेट नहीं है |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | उपलब्ध नहीं |  |
| `scr_settings_autolock` | Lock when I leave the app | ऐप छोड़ते ही बंद कर दें |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 मिनट बाद |  |
| `scr_settings_autolock_immediately` | Immediately | तुरंत |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d मिनट बाद |  |
| `scr_settings_change_passphrase` | Change passphrase | पासफ़्रेज़ बदलें |  |
| `scr_settings_current_passphrase` | Current passphrase | मौजूदा पासफ़्रेज़ |  |
| `scr_settings_export` | Export | निर्यात करें |  |
| `scr_settings_import` | Import passwords | पासवर्ड आयात करें |  |
| `scr_settings_language` | Language | भाषा |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | नया पासफ़्रेज़ (कम से कम 10 अक्षर) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | नई रिकवरी कुंजी लें |  |
| `scr_settings_passphrase_changed` | Passphrase changed | पासफ़्रेज़ बदल गया |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | फ़िंगरप्रिंट / फ़ेस अनलॉक |  |
| `scr_settings_security_info` | How your data is protected | आपका डेटा कैसे सुरक्षित है |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | बैकअप और सिंक फ़ोल्डर |  |
| `scr_settings_sync_not_set` | Not backed up | बैकअप नहीं है |  |
| `scr_settings_title` | Settings | सेटिंग |  |
| `se_title` | Not saved | सहेजा नहीं गया |  |
| `st_active_folder` | Active Folder | चालू फ़ोल्डर |  |
| `st_active_value` | Active · %1$s | चालू · %1$s |  |
| `st_backing_up` | Backing up vault… | तिजोरी का बैकअप हो रहा है… |  |
| `st_backup_now` | Backup Now | अभी बैकअप लें |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | बैकअप और सिंक फ़ोल्डर |  |
| `st_change_folder` | Change Folder | फ़ोल्डर बदलें |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | नया पासफ़्रेज़ दोबारा लिखें |  |
| `st_connected_folder` | Connected folder: %1$s | जुड़ा फ़ोल्डर: %1$s |  |
| `st_disconnect` | Disconnect | हटा दें |  |
| `st_done` | Done | हो गया |  |
| `st_export_kosh` | Export encrypted .kosh | एन्क्रिप्टेड .kosh निर्यात करें |  |
| `st_folder_fallback` | Folder | फ़ोल्डर |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | पासफ़्रेज़ भूल गए? |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | अपनी उँगली से नया बनाएँ |  |
| `st_generate` | Generate | बनाएँ |  |
| `st_group_about` | About | परिचय |  |
| `st_group_appearance` | Appearance | रंग-रूप |  |
| `st_group_security` | Security | सुरक्षा |  |
| `st_group_sync` | Sync | सिंक |  |
| `st_import_kosh` | Import a .kosh backup | .kosh बैकअप आयात करें |  |
| `st_import_other` | Import from another password manager | किसी और पासवर्ड मैनेजर से आयात करें |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | लाइसेंस |  |
| `st_logos_by` | Logos provided by | लोगो देने वाले |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | इसे ऑफ़लाइन रखें। पुरानी रिकवरी कुंजी अब वैध नहीं है। |  |
| `st_new_recovery_result` | Your new Recovery Key: | आपकी नई रिकवरी कुंजी: |  |
| `st_subtitle` | Your rules. | आपके नियम। |  |
| `st_theme` | Theme | थीम |  |
| `st_theme_dark` | Dark | गहरा |  |
| `st_theme_light` | Light | उजला |  |
| `st_theme_system` | System | सिस्टम |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | तिजोरी %1$s में सहेज दी गई! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | बैकअप नहीं हुआ — फ़ोल्डर की अनुमति देख लें |  |
| `st_toast_disconnected` | Backup folder disconnected | बैकअप फ़ोल्डर हटा दिया गया |  |
| `st_toast_export_failed` | Export failed | निर्यात नहीं हो सका |  |
| `st_toast_exported` | Encrypted vault exported | एन्क्रिप्टेड तिजोरी निर्यात हो गई |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | बैकअप फ़ोल्डर जुड़ गया और तिजोरी %1$s में सहेज दी गई! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | बैकअप फ़ोल्डर जुड़ गया: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | फ़ोल्डर नहीं जुड़ा: %1$s |  |
| `st_vault_review` | Vault review | तिजोरी की जाँच |  |
| `st_vault_review_detail` | Reused, weak, expiring | दोहराए गए, कमज़ोर, खत्म होने वाले |  |
| `tab_codes` | Codes | कोड |  |
| `tab_settings` | Settings | सेटिंग |  |
| `tab_templates` | Templates | टेम्पलेट |  |
| `tab_vault` | Vault | तिजोरी |  |
| `time_days` | %1$dd ago | %1$d दि पहले |  |
| `time_hours` | %1$dh ago | %1$d घं पहले |  |
| `time_just_now` | just now | अभी-अभी |  |
| `time_minutes` | %1$dm ago | %1$d मि पहले |  |
| `time_months` | %1$dmo ago | %1$d माह पहले |  |
| `time_years` | %1$dy ago | %1$d वर्ष पहले |  |
| `tpl_aadhaar_card` | Aadhaar Card | आधार |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | आधार संख्या |  |
| `tpl_aadhaar_card_address` | Address | आधार पर पता |  |
| `tpl_aadhaar_card_dob` | Dob | जन्म तिथि |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | स्कैन की गई कॉपी |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | जुड़ा मोबाइल |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar पासकोड |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | आधार पर नाम |  |
| `tpl_aadhaar_card_notes` | Notes | नोट्स |  |
| `tpl_app_profile` | App Profile | ऐप प्रोफ़ाइल |  |
| `tpl_app_profile_app_name` | App name | ऐप का नाम |  |
| `tpl_app_profile_gift_cards` | Gift cards | गिफ़्ट कार्ड |  |
| `tpl_app_profile_membership` | Membership | सदस्यता |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | सदस्यता नवीनीकरण |  |
| `tpl_app_profile_notes` | Notes | नोट्स |  |
| `tpl_app_profile_password_if_any` | Password (if any) | पासवर्ड (अगर हो) |  |
| `tpl_app_profile_registered_email` | Registered email | रजिस्टर्ड ईमेल |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | रजिस्टर्ड मोबाइल |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_bank_account` | Bank Account | बैंक खाता |  |
| `tpl_bank_account_account_number` | Account number | खाता संख्या |  |
| `tpl_bank_account_account_type` | Account type | खाते का प्रकार |  |
| `tpl_bank_account_bank_name` | Bank name | बैंक का नाम |  |
| `tpl_bank_account_branch` | Branch | शाखा |  |
| `tpl_bank_account_customer_id` | Customer id | ग्राहक आईडी |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC कोड |  |
| `tpl_bank_account_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_bank_account_micr` | MICR code | MICR कोड |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | नेट-बैंकिंग यूज़र आईडी |  |
| `tpl_bank_account_nominee` | Nominee | नॉमिनी |  |
| `tpl_bank_account_notes` | Notes | नोट्स |  |
| `tpl_bank_account_profile_password` | Profile password | प्रोफ़ाइल पासवर्ड |  |
| `tpl_bank_account_registered_email` | Registered email | रजिस्टर्ड ईमेल |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | रजिस्टर्ड मोबाइल |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | लेन-देन पासवर्ड |  |
| `tpl_card` | Card | कार्ड |  |
| `tpl_card_atm_pin` | ATM PIN | एटीएम पिन |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | बिलिंग साइकल का दिन |  |
| `tpl_card_card_network` | Card network | नेटवर्क |  |
| `tpl_card_card_number` | Card number | कार्ड नंबर |  |
| `tpl_card_card_portal_login` | Card portal login | कार्ड पोर्टल लॉगिन |  |
| `tpl_card_card_portal_password` | Card portal password | कार्ड पोर्टल पासवर्ड |  |
| `tpl_card_card_type` | Card type | कार्ड का प्रकार |  |
| `tpl_card_card_variant` | Card variant | कार्ड वैरिएंट |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | वैधता |  |
| `tpl_card_linked_account` | Linked account | जुड़ा खाता |  |
| `tpl_card_name_on_card` | Name on card | कार्ड पर नाम |  |
| `tpl_card_notes` | Notes | नोट्स |  |
| `tpl_demat` | Demat | डीमैट |  |
| `tpl_demat_api_key` | API key | API की |  |
| `tpl_demat_api_secret` | API secret | API सीक्रेट |  |
| `tpl_demat_bo_id` | BO ID | BO आईडी |  |
| `tpl_demat_broker` | Broker | ब्रोकर |  |
| `tpl_demat_cdsl_tpin` | CDSL TPIN | CDSL TPIN |  |
| `tpl_demat_client_id` | Client id | क्लाइंट आईडी |  |
| `tpl_demat_depository` | Depository | डिपॉज़िटरी |  |
| `tpl_demat_dp_id` | DP ID | DP आईडी |  |
| `tpl_demat_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_demat_mf_folios` | Mutual fund folios | म्यूचुअल फ़ंड फ़ोलियो |  |
| `tpl_demat_mpin` | MPIN | MPIN |  |
| `tpl_demat_nominee` | Nominee | नॉमिनी |  |
| `tpl_demat_notes` | Notes | नोट्स |  |
| `tpl_digilocker` | Digilocker | डिजिलॉकर |  |
| `tpl_digilocker_login_username` | Login username | यूज़रनेम |  |
| `tpl_digilocker_notes` | Notes | नोट्स |  |
| `tpl_digilocker_portal_password` | Portal password | पासवर्ड |  |
| `tpl_digilocker_security_pin` | Security pin | सुरक्षा पिन |  |
| `tpl_driving_license` | Driving License | ड्राइविंग लाइसेंस |  |
| `tpl_driving_license_dl_number` | Dl number | लाइसेंस नंबर |  |
| `tpl_driving_license_dob` | Dob | जन्म तिथि |  |
| `tpl_driving_license_expiry_date` | Expiry date | इस तारीख़ तक वैध |  |
| `tpl_driving_license_file_copy` | Scanned copy | स्कैन की गई कॉपी |  |
| `tpl_driving_license_issue_date` | Issue date | जारी होने की तारीख़ |  |
| `tpl_driving_license_name_on_dl` | Name on dl | लाइसेंस पर नाम |  |
| `tpl_driving_license_notes` | Notes | नोट्स |  |
| `tpl_driving_license_rto_location` | Rto location | आरटीओ |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | वाहन श्रेणियाँ |  |
| `tpl_epf_pension` | Epf Pension | ईपीएफ़ / पेंशन |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | जुड़ा मोबाइल |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | ईपीएफ़ पर नाम |  |
| `tpl_epf_pension_nominee` | Nominee | नॉमिनी |  |
| `tpl_epf_pension_notes` | Notes | नोट्स |  |
| `tpl_epf_pension_password` | Password | पासवर्ड |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | पीएफ़ मेंबर आईडी |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO पासवर्ड |  |
| `tpl_epf_pension_scheme` | Scheme | योजना |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | सरकारी पहचान-पत्र |  |
| `tpl_gov_id_expiry` | Expiry | वैधता |  |
| `tpl_gov_id_file_copy` | Scanned copy | स्कैन की गई कॉपी |  |
| `tpl_gov_id_id_kind` | ID type | पहचान-पत्र का प्रकार |  |
| `tpl_gov_id_id_number` | ID number | पहचान संख्या |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | पहचान-पत्र के अनुसार नाम |  |
| `tpl_gov_id_notes` | Notes | नोट्स |  |
| `tpl_gov_id_portal_login` | Portal login | पोर्टल लॉगिन |  |
| `tpl_gov_id_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_insurance` | Insurance | बीमा |  |
| `tpl_insurance_agent_contact` | Agent contact | एजेंट का संपर्क |  |
| `tpl_insurance_commencement_date` | Commencement date | शुरू होने की तारीख़ |  |
| `tpl_insurance_insurer` | Insurer | बीमा कंपनी |  |
| `tpl_insurance_maturity_date` | Maturity date | मैच्योरिटी की तारीख़ |  |
| `tpl_insurance_nominee` | Nominee | नॉमिनी |  |
| `tpl_insurance_notes` | Notes | नोट्स |  |
| `tpl_insurance_policy_number` | Policy number | पॉलिसी नंबर |  |
| `tpl_insurance_policy_term` | Policy term | पॉलिसी की अवधि |  |
| `tpl_insurance_policy_type` | Policy type | पॉलिसी का प्रकार |  |
| `tpl_insurance_portal_login` | Portal login | पोर्टल लॉगिन |  |
| `tpl_insurance_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_insurance_premium_amount` | Premium amount | प्रीमियम राशि |  |
| `tpl_insurance_premium_due_date` | Premium due date | प्रीमियम की तारीख़ |  |
| `tpl_insurance_premium_mode` | Premium mode | प्रीमियम कैसे भरते हैं |  |
| `tpl_insurance_sum_assured` | Sum assured | बीमित राशि |  |
| `tpl_login` | Login | लॉगिन |  |
| `tpl_login_notes` | Notes | नोट्स |  |
| `tpl_login_password` | Password | पासवर्ड |  |
| `tpl_login_recovery_codes` | Recovery codes | रिकवरी कोड |  |
| `tpl_login_username` | Username | यूज़रनेम |  |
| `tpl_login_website` | Website | वेबसाइट |  |
| `tpl_pan_card` | Pan Card | पैन कार्ड |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | आधार से जुड़ा है |  |
| `tpl_pan_card_dob` | Dob | जन्म तिथि |  |
| `tpl_pan_card_e_filing_password` | E filing password | ई-फ़ाइलिंग पासवर्ड |  |
| `tpl_pan_card_fathers_name` | Fathers name | पिता का नाम |  |
| `tpl_pan_card_file_copy` | Scanned copy | स्कैन की गई कॉपी |  |
| `tpl_pan_card_name_on_pan` | Name on pan | पैन पर नाम |  |
| `tpl_pan_card_notes` | Notes | नोट्स |  |
| `tpl_pan_card_pan_number` | Pan number | पैन |  |
| `tpl_passkey` | Passkey | पासकी |  |
| `tpl_passkey_credential_id` | Credential ID | क्रेडेंशियल ID |  |
| `tpl_passkey_notes` | Notes | नोट्स |  |
| `tpl_passkey_private_key` | Private key | निजी कुंजी |  |
| `tpl_passkey_sign_count` | Sign count | साइन काउंट |  |
| `tpl_passkey_user_handle` | User handle | यूज़र हैंडल |  |
| `tpl_passkey_username` | Username | यूज़रनेम |  |
| `tpl_passkey_website` | Website | वेबसाइट |  |
| `tpl_passport` | Passport | पासपोर्ट |  |
| `tpl_passport_dob` | Dob | जन्म तिथि |  |
| `tpl_passport_expiry_date` | Expiry date | समाप्ति की तारीख़ |  |
| `tpl_passport_file_copy` | Scanned copy | स्कैन की गई कॉपी |  |
| `tpl_passport_given_names` | Given names | दिया गया नाम |  |
| `tpl_passport_issue_date` | Issue date | जारी होने की तारीख़ |  |
| `tpl_passport_notes` | Notes | नोट्स |  |
| `tpl_passport_passport_number` | Passport number | पासपोर्ट नंबर |  |
| `tpl_passport_place_of_issue` | Place of issue | जारी करने का स्थान |  |
| `tpl_passport_portal_login` | Portal login | पासपोर्ट सेवा लॉगिन |  |
| `tpl_passport_portal_password` | Portal password | पासपोर्ट सेवा पासवर्ड |  |
| `tpl_passport_surname` | Surname | उपनाम |  |
| `tpl_secure_note` | Secure Note | सुरक्षित नोट |  |
| `tpl_secure_note_attachment` | Attachment | अटैचमेंट |  |
| `tpl_secure_note_body` | Note | नोट |  |
| `tpl_shopping` | Shopping | शॉपिंग खाता |  |
| `tpl_shopping_gift_card_code` | Gift card code | गिफ़्ट कार्ड कोड |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | गिफ़्ट कार्ड पिन |  |
| `tpl_shopping_membership_id` | Membership id | सदस्यता आईडी |  |
| `tpl_shopping_notes` | Notes | नोट्स |  |
| `tpl_shopping_password` | Password | पासवर्ड |  |
| `tpl_shopping_registered_email` | Registered email | रजिस्टर्ड ईमेल |  |
| `tpl_shopping_registered_mobile` | Registered mobile | रजिस्टर्ड मोबाइल |  |
| `tpl_shopping_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_telecom` | Telecom | मोबाइल और इंटरनेट |  |
| `tpl_telecom_account_number` | Account number | खाता संख्या |  |
| `tpl_telecom_circle` | Circle | सर्कल |  |
| `tpl_telecom_mobile_number` | Mobile number | मोबाइल नंबर |  |
| `tpl_telecom_notes` | Notes | नोट्स |  |
| `tpl_telecom_operator` | Operator | कंपनी |  |
| `tpl_telecom_plan_type` | Plan type | प्लान का प्रकार |  |
| `tpl_telecom_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_telecom_puk` | PUK code | PUK कोड |  |
| `tpl_telecom_renewal_date` | Renewal date | रिचार्ज की तारीख़ |  |
| `tpl_telecom_sim_number` | Sim number | सिम नंबर (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | सिम पिन |  |
| `tpl_transit` | Transit | ट्रांज़िट पास |  |
| `tpl_transit_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_transit_notes` | Notes | नोट्स |  |
| `tpl_transit_operator_name` | Operator name | कंपनी |  |
| `tpl_transit_registered_email` | Registered email | रजिस्टर्ड ईमेल |  |
| `tpl_transit_registered_mobile` | Registered mobile | रजिस्टर्ड मोबाइल |  |
| `tpl_transit_smart_card_number` | Smart card number | स्मार्ट कार्ड नंबर |  |
| `tpl_transit_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_travel_booking` | Travel Booking | यात्रा बुकिंग |  |
| `tpl_travel_booking_account_username` | Account username | यूज़रनेम |  |
| `tpl_travel_booking_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_travel_booking_notes` | Notes | नोट्स |  |
| `tpl_travel_booking_provider` | Provider | कंपनी |  |
| `tpl_travel_booking_registered_email` | Registered email | रजिस्टर्ड ईमेल |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | रजिस्टर्ड मोबाइल |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_upi` | UPI | यूपीआई |  |
| `tpl_upi_app_name` | App name | यूपीआई ऐप |  |
| `tpl_upi_apps_used` | Apps used | कौन-से ऐप में चालू है |  |
| `tpl_upi_linked_account` | Linked account | जुड़ा खाता |  |
| `tpl_upi_notes` | Notes | नोट्स |  |
| `tpl_upi_upi_id` | UPI ID | यूपीआई आईडी |  |
| `tpl_upi_upi_pin` | UPI PIN | यूपीआई पिन |  |
| `tpl_utility` | Utility | बिल और कनेक्शन |  |
| `tpl_utility_account_holder` | Account holder | खाताधारक |  |
| `tpl_utility_consumer_number` | Consumer number | उपभोक्ता संख्या |  |
| `tpl_utility_due_day` | Bill due day | बिल जमा करने का दिन |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag आईडी |  |
| `tpl_utility_notes` | Notes | नोट्स |  |
| `tpl_utility_portal_login` | Portal login | पोर्टल लॉगिन |  |
| `tpl_utility_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_utility_provider` | Provider | सेवा देने वाली कंपनी |  |
| `tpl_utility_utility_kind` | Utility kind | किस चीज़ का बिल |  |
| `tpl_utility_vehicle_number` | Vehicle number | गाड़ी नंबर |  |
| `tpl_utility_wifi_password` | Wi-Fi password | वाई-फ़ाई पासवर्ड |  |
| `tpl_voter_id` | Voter Id | वोटर आईडी |  |
| `tpl_voter_id_constituency` | Constituency | निर्वाचन क्षेत्र |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC नंबर |  |
| `tpl_voter_id_file_copy` | Scanned copy | स्कैन की गई कॉपी |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | वोटर आईडी पर नाम |  |
| `tpl_voter_id_notes` | Notes | नोट्स |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP पासवर्ड |  |
| `tr_days_many` | %1$d days left | %1$d दिन बाक़ी |  |
| `tr_days_one` | %1$d day left | %1$d दिन बाक़ी |  |
| `tr_gone_today` | gone today | आज चला जाएगा |  |
| `tr_restore` | Restore | वापस लाएँ |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | उसके बाद वे हमेशा के लिए चले जाते हैं — और कहीं कोई नकल नहीं है। |  |
| `ui_hide_passphrase` | Hide passphrase | पासफ़्रेज़ छिपाएँ |  |
| `ui_label_count` | %1$s, %2$s | %1$s, %2$s |  |
| `ui_show_passphrase` | Show passphrase | पासफ़्रेज़ दिखाएँ |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | इसी फ़ोन पर %1$d रिकॉर्ड से मिलान किया गया। कुछ भी कहीं नहीं भेजा गया। |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | इसी फ़ोन पर %1$d रिकॉर्ड से मिलान किया गया। कुछ भी कहीं नहीं भेजा गया। |  |
| `vh_count_many` | %1$d things worth a look. | %1$d बातें देखने लायक हैं। |  |
| `vh_count_one` | %1$d thing worth a look. | %1$d बात देखने लायक है। |  |
| `vh_empty` | No reused, weak or expiring credentials. | कोई दोहराई गई, कमज़ोर या खत्म होने वाली जानकारी नहीं। |  |
| `vh_kind_common` | Commonly guessed | आम तौर पर ताड़ लिया जाने वाला |  |
| `vh_kind_expiring` | Expiring | खत्म होने वाला |  |
| `vh_kind_reused` | Reused password | दोहराया गया पासवर्ड |  |
| `vh_kind_weak` | Weak | कमज़ोर |  |
| `vh_no_kit_title` | No recovery kit saved | कोई रिकवरी किट सेव नहीं है |  |
| `vh_nothing` | Nothing to fix. | ठीक करने को कुछ नहीं। |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | ऑफ़लाइन |  |
| `wl_chip_open` | Open source | ओपन सोर्स |  |
| `wl_create` | Create a new vault | नई तिजोरी बनाएँ |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | न ईमेल · न खाता · कुछ भी इस फ़ोन से बाहर नहीं |  |
| `wl_head_1` | Your keys. | आपकी चाबियाँ। |  |
| `wl_head_2` | Your device. | आपका फ़ोन। |  |
| `wl_head_3` | No server. | कोई सर्वर नहीं। |  |
| `wl_restore` | Restore from Recovery Kit | रिकवरी किट से वापस लाएँ |  |
| `wl_sr_headline` | Your keys. Your device. No server. | आपकी चाबियाँ। आपका फ़ोन। कोई सर्वर नहीं। |  |
