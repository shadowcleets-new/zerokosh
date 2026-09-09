# Maithili (`b+mai`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-b+mai/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Maithili | ok? |
|---|---|---|---|
| `au_close` | Close | बन्न करू |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | चुनल चित्रमे सही TOTP QR कोड नहि भेटल |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | खाली सँ शुरू क क अपन खानाक नाम अपने राखू — टेम्पलेट खाली लेबल भरैत अछि, डेटा कहियो नहि। |  |
| `hm_close_search` | Close search | ताकब बन्न करू |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | एहि फोन सँ कहियो बाहर नहि जाइत अछि। सहेजैत काल एन्क्रिप्ट कएल जाइत अछि। |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | आब ओ फाइल हटा दिअ जकरा अहाँ आयात कएने रही। से अहाँक पासवर्डक खुजल सूची अछि, आ एखनहु अहाँक Downloads मे पड़ल अछि। |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | सभटा खाली एहि फोने पर डिक्रिप्ट होइत अछि। किछु अपलोड नहि होइत अछि, कारण ई एप नेटवर्क कनेक्शन खोलिये नहि सकैत अछि। |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | बैंक कहियो अहाँक OTP नहि मंगैत अछि। जे मंगय, से ठक अछि। |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | कोनो बैंक अधिकारी अहाँकेँ स्क्रीन साझा करबला एप लगाबय लेल नहि कहत। |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | अहाँक UPI पिन खाली UPI एपक कीपैडक लेल अछि — फोन पर ककरो नहि कहू। |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | KYC एक्के दिनमे नहि खतम होइत अछि। “आइ KYC खतम” बला संदेश ठकी अछि। |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | पाइ पाबय लेल ने पिन देबय पड़ैत अछि, ने QR स्कैन करय पड़ैत अछि। |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | बिजली कटबाक SMS आ ओहिमे ककरो निजी नम्बर? से ठकी अछि। |  |
| `nav_close_menu` | Close menu | मेनू बन्न करू |  |
| `nfc_cannot_read` | Cannot read cards | कार्ड नहि पढ़ल जा सकैत अछि |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | एहि फोनमे NFC नहि अछि, तेँ कार्ड नहि पढ़ल जा सकैत अछि। |  |
| `ob_fact_lost_title` | If you lose your keys | जँ कुंजी हेरा जाय |  |
| `ob_fact_network_note` | The app literally cannot phone home | ई एप कतहु सम्पर्क नहि क सकैत अछि |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | बायोमेट्रिक सुरक्षा चिप सँ बाहर कहियो नहि जाइत अछि |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | अहाँ कुंजी ओही फोल्डरमे राखलहुँ जे अहाँक एन्क्रिप्ट कएल भण्डार सिंक करैत अछि। आब जकरा ओ फोल्डर भेटत, ओकरा दुनू भाग भेटि जाएत। कुंजी कतहु आन ठाम राखू — कागज, आन खाता, वा दराज। |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | ई अहाँक भण्डार फाइलक कातेमे अछि |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH रिकवरी |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | स्कैन करू वा लिखू। फेर इंस्टॉल, फैक्ट्री रिसेट, वा फोन हेरेला पर सेहो काज करैत अछि। |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | एखने सहेजल किटसँ समूह %1$d आ समूह %2$d लिखू। |  |
| `ob_kit_challenge_hint` | Group %1$d | समूह %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | पहिने किट सहेजू, फेर समूह %1$d आ %2$d वापस लिखू। |  |
| `ob_kit_challenge_title` | Check you actually have it | देखू जे किट साँचेमे अहाँक लग अछि |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | ई ऊपरक कुंजीसँ मेल नहि खाइत अछि। |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | केओ — Zerokosh सेहो — एकरा हमरा लेल घुरा नहि सकैत अछि। |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "हम एकरा ऑफलाइन राखि लेलहुँ। " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | एक्के बेर देखाइत अछि, कहियो खुजल रूपमे नहि राखल जाइत अछि। भीतर आबि सकैत छी तँ सेटिंगसँ नव बना लिअ। |  |
| `ob_kit_head_emph` | On paper. | कागज पर। |  |
| `ob_kit_head_lead` | "One key. " | "एकटा कुंजी। " |  |
| `ob_kit_head_tail` | " Never online." | " कहियो ऑनलाइन नहि।" |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | ने Gmail, ने WhatsApp, ने स्क्रीनशॉट। आलमारी, बैंक लॉकर, वा स्टीलक पत्तर। |  |
| `ob_kit_offline_title` | Keep it off the internet | एकरा इंटरनेट सँ दूर राखू |  |
| `ob_kit_print` | Print | छापू |  |
| `ob_kit_print_note` | A printer, or Save as PDF | प्रिंटर, वा PDF रूपमे सहेजू |  |
| `ob_kit_qr` | QR image | QR चित्र |  |
| `ob_kit_qr_cd` | Recovery key QR code | रिकवरी कुंजीक QR कोड |  |
| `ob_kit_qr_note` | To an offline gallery | ऑफलाइन गैलरीमे |  |
| `ob_kit_regenerate` | Regenerate | नव बनाबू |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | ई सहेजल नहि गेल। फेर कोशिश करू, वा आन ठाम चुनू। |  |
| `ob_kit_save_pdf` | Save PDF | PDF सहेजू |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | एक पन्नाक छापय जोग किट |  |
| `ob_kit_saved` | I\'ve saved my kit | हम अपन किट सहेजि लेलहुँ |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s मे सहेजल गेल |  |
| `ob_kit_sent_to_printer` | Sent to the printer | प्रिंटरकेँ पठाओल गेल |  |
| `ob_kit_skip` | I\'ll do this later | ई बादमे करब |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | अहाँक भण्डार चलैत रहत। जा धरि किट सहेजल नहि जाइत, Zerokosh इयाद करबैत रहत। |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | एहि फोने पर बनल, खाली एक बेर देखाएत। पासफ्रेज बिसरला पर भीतर घुरि आयबाक एहे एकटा बाट अछि। |  |
| `ob_kit_working` | Working… | काज चलि रहल अछि… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | भण्डारक लेबल, टेम्पलेट आ चेतावनी तुरन्त बदलि जाएत। सेटिंगमे कहियो बदलि सकैत छी। |  |
| `ob_pass_confirm` | Confirm | फेर लिखू |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | ई हम कहियो नहि देखैत छी। कोनो रिसेट लिंक नहि। |  |
| `ob_pass_head_emph` | held only | खाली अहाँक लग |  |
| `ob_pass_head_lead` | "One secret, " | "एकेटा गुप्त बात, " |  |
| `ob_pass_head_tail` | " by you." | । |  |
| `ob_pass_no_match` | no match | मेल नहि खाइत अछि |  |
| `ob_pass_seal` | Seal the vault | भण्डार बन्न करू |  |
| `ob_pass_sealing` | Sealing… | बन्न क रहल छी… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | बिनु मेल बला तीन-चारि शब्द एकटा चतुर शब्द सँ नीक होइत अछि। एहि परदा सँ किछु बाहर नहि जाइत अछि। |  |
| `ob_pass_tab_passphrase` | Passphrase | पासफ्रेज |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 अंकक पिन |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | अहाँक आङुरक छाप फोनक सुरक्षा चिपक भीतरे रहैत अछि। से एहि फोन सँ कहियो बाहर नहि जाइत अछि। |  |
| `ob_trust_continue` | I understand · Continue | बुझलहुँ · आगू बढ़ू |  |
| `ob_trust_head_emph` | don\'t | नहि बुझल |  |
| `ob_trust_head_lead` | "Exactly what we " | "हमरा साँचेमे की " |  |
| `ob_trust_head_tail` | " know." | । |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | पासफ्रेज बिसरि क रिकवरी किटो हेरा गेल, तँ भण्डार बन्ने रहत — अहाँक लेल, हमर लेल, सभक लेल। |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "कुंजी अहाँक लग। " |  |
| `ob_trust_stat_files` | .kosh file on device | फोन पर .kosh फाइल |  |
| `ob_trust_stat_servers` | servers contacted | सर्वर सँ सम्पर्क |  |
| `ob_trust_stat_trackers` | trackers or SDKs | ट्रैकर वा SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | ई एक बेर पढ़ि लिअ। पूरा सुरक्षा व्यवस्था इहे अछि, सोझ शब्दमे। |  |
| `ob_trust_tag_audited` | Audited build | ऑडिट कएल बिल्ड |  |
| `ob_trust_tag_reproducible` | Reproducible APK | फेर बनाओल जा सकय बला APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | अहाँ आङुरसँ खोलि रहल छी। जँ कहियो आङुर काज करब बन्न क देत, तँ इहे अहाँकेँ भीतर आनत — तेँ देखि लेब नीक। |  |
| `pc_confirm` | Check | जाँचू |  |
| `pc_correct` | Still correct. Nothing to do. | आब सेहो ठीक। किछु करबाक नहि। |  |
| `pc_forgot` | I cannot remember it | हमरा इयाद नहि पड़ि रहल |  |
| `pc_later` | Not now | आब नहि |  |
| `pc_reset_action` | Set new passphrase | नव पासफ्रेज राखू |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | अहाँक आङुर ई भण्डार खोलि सकैत अछि, तेँ ओ नव पासफ्रेजो राखि सकैत अछि — रिकवरी किट नहि चाही। पक्का करबाक लेल एक बेर आर पुछल जाएत। |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | पासफ्रेज बदलि गेल। जल्दी अनलॉक फेर लागि गेल। |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | ई नहि भेल। अहाँक पुरान पासफ्रेजे चलि रहल अछि। |  |
| `pc_reset_title` | Set a new passphrase | नव पासफ्रेज राखू |  |
| `pc_title` | Do you still remember your passphrase? | की अहाँकेँ आब सेहो अपन पासफ्रेज इयाद अछि? |  |
| `pc_wrong` | That is not it. You can set a new one instead. | ई ओ नहि। एकर बदला नव राखि सकैत छी। |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | एहि प्रविष्टिक लेल इयाद राखल %1$d मान हटाओल जाएत। ई वापस नहि भ सकैत अछि। |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh ओ लॉगिन नहि सहेजि सकल। |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | भरय लेल Zerokosh खोलू |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | एहि एकटा पासफ्रेजे सभटा बन्न रखैत अछि। एकटा नमगर वाक्य चुनू जे खाली अहाँ जनैत छी। |  |
| `scr_create_button` | Lock it in | बन्न क दिअ |  |
| `scr_create_confirm_hint` | Type it again | फेर लिखू |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | पासफ्रेज (कम सँ कम 10 अक्षर) |  |
| `scr_create_mismatch` | The two entries don\'t match | दुनू एक्के रंग नहि अछि |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 अंकक पिन |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | एकर बदला 6 अंकक पिन राखू |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | पिनक लेल आङुर वा मुँह अनलॉक बला फोन चाही। कृपया पासफ्रेज चुनू। |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | पिनक अनुमति एहि दुआरे अछि जे ई फोन ओकरा अपन सुरक्षा चिप आ अहाँक आङुर वा मुँह सँ बचबैत अछि। |  |
| `scr_create_strength_fair` | Fair | ठीके |  |
| `scr_create_strength_good` | Good | नीक |  |
| `scr_create_strength_strong` | Strong | मजगूत |  |
| `scr_create_strength_weak` | Weak | कमजोर |  |
| `scr_create_title` | Create your passphrase | अपन पासफ्रेज बनाबू |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | कम सँ कम 10 अक्षर चाही — जतेक नमगर, ततेक मजगूत |  |
| `scr_create_working` | Preparing your vault… | अहाँक भण्डार तैयार भ रहल अछि… |  |
| `scr_detail_delete` | Delete | हटाबू |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | ई 30 दिन “हालेमे हटाओल गेल”मे रहत, आ अहाँक आन फोनक संग सिंक भेला पर चलि जाएत। |  |
| `scr_detail_delete_confirm_title` | Delete this record? | ई प्रविष्टि हटा देल जाए? |  |
| `scr_detail_delete_confirm_yes` | Delete | हटाबू |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | सिक्रेट वा otpauth:// लिंक साटू |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | अपन आङुर वा मुँह प्रयोग करू |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh खोलू |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | बहुत बेर गलत कोशिश भेल। %1$d सेकेण्ड ठहरू। |  |
| `scr_lock_hint` | Passphrase | पासफ्रेज |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | ई रिकवरी कुंजी ठीक नहि — एक-एक अक्षर मिला क देखू |  |
| `scr_lock_title` | Vault is locked | भण्डार बन्न अछि |  |
| `scr_lock_unlock` | Unlock | खोलू |  |
| `scr_lock_use_passphrase` | Use passphrase | पासफ्रेज प्रयोग करू |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | कृपया एक बेर पासफ्रेज सँ खोलू |  |
| `scr_lock_use_recovery` | Use Recovery Key | रिकवरी कुंजी प्रयोग करू |  |
| `scr_lock_wrong` | Wrong passphrase | पासफ्रेज गलत अछि |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | कहियो पासफ्रेज बिसरि गेलहुँ, तँ भीतर घुरि आयबाक एहे एकटा बाट अछि। हम ओकरा रिसेट नहि क सकैत छी — केओ नहि क सकैत अछि। |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | हम एकरा लिखि क सुरक्षित जगह राखि लेलहुँ |  |
| `scr_recovery_done` | Continue | आगू बढ़ू |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | ई कुंजी एक्के बेर देखाइत अछि। जा धरि अहाँ ताला खोलि सकैत छी, सेटिंगसँ कहियो नव बना सकैत छी। |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | ई कागज अपन जमीन-जायदादक कागज वा आन जरूरी कागजातक संग राखू। जकरा लग ई कुंजी अछि, ओ अहाँक भण्डार खोलि सकैत अछि — एकरा लॉकरक चाभी जकाँ सम्हारू। |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | रिकवरी किट PDF सहेजल गेल |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh रिकवरी किट |  |
| `scr_recovery_save_pdf` | Save as PDF | PDF रूपमे सहेजू |  |
| `scr_recovery_title` | Your Recovery Key | अहाँक रिकवरी कुंजी |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | अहाँ जे किछु सहेजैत छी, से अहाँक फोनक एकटा बन्न फाइलमे रहैत अछि। से कहियो हमरा लग नहि अबैत अछि — ओकरा राखबाक जगहे हमरा लग नहि अछि। |  |
| `scr_trust_card1_title` | Your data stays on this device | अहाँक डेटा एहि फोनेमे रहैत अछि |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | Zerokosh खाता नहि, क्लाउड नहि, साइन-अप नहि। एकरा खाली अहीं खोलि सकैत छी। हमहूँ नहि। |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | हमर सर्वर नहि अछि — ने हैक करबाक किछु, ने बेचबाक |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | ने सदस्यता, ने विज्ञापन। केओ हमर कोड पढ़ि क हमर एक-एक बात परखि सकैत अछि। |  |
| `scr_trust_card3_title` | Free forever, open source | सदिखन मुफ्त, ओपन सोर्स |  |
| `scr_trust_continue` | Continue | आगू बढ़ू |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | अहाँक बदलाव सहेजल नहि गेल, तेँ भण्डारमे जे पहिने छल ओहिमे सँ किछु नहि हेरायल। |  |
| `st_recently_deleted` | Recently deleted | हालेमे हटाओल गेल |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | एक बेरक कोड (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | एक बेरक कोड (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | एक बेरक कोड (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA सिक्रेट |  |
| `tr_cannot_undo` | This cannot be undone. | ई वापस नहि भ सकैत अछि। |  |
| `tr_delete_all` | Delete all permanently | सभटा सदा लेल हटाबू |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d प्रविष्टि सदा लेल चलि जाएत। ई वापस नहि भ सकैत अछि, आ घुरा आनय लेल कोनो बैकअपो नहि। |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d प्रविष्टि सदा लेल चलि जाएत। ई वापस नहि भ सकैत अछि, आ घुरा आनय लेल कोनो बैकअपो नहि। |  |
| `tr_delete_all_title` | Delete everything in the trash? | कूड़ाक सभटा हटा देल जाए? |  |
| `tr_delete_now` | Delete now | आबे हटाबू |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | “%1$s” सदा लेल हटा देल जाए? |  |
| `tr_empty` | Nothing deleted. | किछु नहि हटाओल गेल। |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | हटाओल प्रविष्टि एतय %1$d दिन रहैत अछि। |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | भारतीय बैंक, UPI, कार्ड, डीमैट, EPF, आ जे OTP एप अहाँ साँचेमे प्रयोग करैत छी — ओहि सभक लेल फोनेमे रहय बला भण्डार। |  |

## Priority 2 — longer prose

| key | English | Maithili | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | जे एकटा वस्तु सभ सँ बेसी काज आबय, ओहि सँ शुरू करू। नोट्स एपमे पड़ल बारहटा पासवर्ड सँ सहेजल एकटा पासवर्ड बेसी सुरक्षित अछि। |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | पासफ्रेज बिसरि गेलहुँ तँ भीतर आयबाक बाट रिकवरी किटे अछि। अहाँक लेल आन कोनो केओ नहि बना सकैत अछि। |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | ओहि फाइलमे चिन्हय जोग किछु नहि भेटल। Chrome, Google Password Manager, Bitwarden, LastPass आ KeePass क निर्यात बुझल जाइत अछि। |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d एखनुका प्रविष्टि बदलत — साइट आ यूजरनेम मिला क। बदलल पासवर्ड हरेक प्रविष्टिक इतिहासमे भेटत। |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d एखनुका प्रविष्टि बदलत — साइट आ यूजरनेम मिला क। बदलल पासवर्ड हरेक प्रविष्टिक इतिहासमे भेटत। |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d प्रविष्टि दुनू दिस बदलल छल। दुनू रूप सहेजल गेल — “(conflict copy)” ताकू। |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | ओ पासफ्रेज दिअ जे एहि बैकअप फाइलकेँ खोलैत अछि। से अहाँक एखनुका सँ भिन्न भ सकैत अछि। |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | .kosh फाइलक Poly1305 प्रमाणीकरण टैग मेल नहि खाइत अछि। बीचहिमे रुकल सिंक वा खराब स्टोरेजक बाद एहन भ सकैत अछि। |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh भण्डार फाइलक कातेमे एकटा चलैत बैकअप राखैत अछि। ओकरा अपन सिंक फोल्डर सँ घुरा आनू, वा आन फोन पर रिकवरी किट सँ भण्डार खोलू। |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | जा धरि नहि पढ़ाय, कार्ड फोनक पाछू सोझे सटा क राखू। एहि सँ कार्ड नम्बर, मियाद आ नाम भेटैत अछि — CVV चिपमे नहि होइत अछि, से अहाँकेँ अपने लिखय पड़त। |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | भीतर घुरि आयबाक जल्दी बाट चुनू। भण्डारक रखबारी पासफ्रेजे करैत अछि; ई खाली एहि फोने पर कुंजी खोलैत अछि। |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | आब अहाँक भण्डारमे साँच जानकारी अछि। रिकवरी किट बिना पासफ्रेज बिसरला पर केओ अहाँकेँ वापस भीतर नहि आनि सकैत अछि — हमहूँ नहि। |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh मुफ्त आ ओपन सोर्स अछि, आ एकर कोनो सर्वर नहि अछि। अहाँक भण्डार खाली अहीं खोलि सकैत छी। हमहूँ नहि। |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | कैमराक अनुमति खाली QR कोड स्कैन करय लेल चाही। प्रविष्टि जोड़ैत काल सिक्रेट हाथेँ सेहो साटि सकैत छी। |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | ओहि बैंक एप सभक लेल जे ऑटोफिल नहि होमय दैत अछि — बटन दबा क लॉगिनक विवरण एक-एक क कॉपी करू |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | जेना अहाँ फोन खोलैत छी, तहिना भण्डारो। पासफ्रेज सदिखन काज करैत रहत। |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | भण्डारक स्क्रीनशॉट क्लाउड फोटो बैकअप धरि पहुँचि सकैत अछि। बहुत जरूरी होयत तखने चालू करू। |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | भण्डार फाइल लिखल नहि जा सकल। जँ अहाँ बैकअप आ सिंक फोल्डर लगौने छी, तँ भ सकैत अछि Android ओकर अनुमति वापस लेने होअय — सेटिंग खोलू, फोल्डर फेर चुनू, आ फेर कोशिश करू। |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | अहाँक एन्क्रिप्ट कएल .kosh फाइल सोझे एहि फोल्डरेमे सहेजल जाइत अछि। बहुत उपकरण पर अपने-आप बैकअप होबय लेल एहि फोल्डरकेँ Google Drive, Syncthing, Nextcloud वा SD कार्डक संग सिंक करू। |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | नव रिकवरी कुंजी बनबय लेल अपन पासफ्रेज दिअ। पुरान कुंजी काज करब बन्न क देत। |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | एहि पन्नाक बाकी सभटा एकटा लॉगिन कमजोर करैत अछि। ई पूरा भण्डारे लऽ जा सकैत अछि। सेटिंग → नव रिकवरी कुंजी लिअ। |  |

## Priority 3 — short labels

| key | English | Maithili | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | मजगूत पासवर्ड प्रयोग करू |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | खाताक नाम (जेना Google) |  |
| `au_active_many` | %1$d active codes | %1$d सक्रिय कोड |  |
| `au_active_one` | %1$d active code | %1$d सक्रिय कोड |  |
| `au_add_another` | Add another authenticator | आर एकटा प्रमाणक जोड़ू |  |
| `au_add_secret` | Add Secret Key | सिक्रेट कुंजी जोड़ू |  |
| `au_camera_needed` | Camera permission needed to scan QR code | QR कोड स्कैन करय लेल कैमराक अनुमति चाही |  |
| `au_copied` | Copied · clears shortly | कॉपी भ गेल · थोड़बे कालमे मेटा जाएत |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google, GitHub वा अपन ब्रोकरक QR स्कैन करू, वा सिक्रेट कुंजी हाथेँ लिखू। |  |
| `au_enter_key` | Enter Key | कुंजी लिखू |  |
| `au_fallback_name` | Authenticator | प्रमाणक |  |
| `au_flashlight` | Flashlight | टॉर्च |  |
| `au_grant` | Grant Permission | अनुमति दिअ |  |
| `au_image_failed` | Failed to process image | चित्र नहि पढ़ल जा सकल |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | गलत Base32 सिक्रेट कुंजी (खाली A-Z अक्षर आ 2-7 अंक) |  |
| `au_no_match` | No codes match | कोनो कोड नहि भेटल |  |
| `au_none_yet` | No codes yet. | एखन धरि कोनो कोड नहि। |  |
| `au_pick_image` | Pick Image | चित्र चुनू |  |
| `au_rotating` | "Rotating " | "बदलैत रहय बला " |  |
| `au_rotating_emph` | codes. | कोड। |  |
| `au_save_key` | Save Key | कुंजी सहेजू |  |
| `au_scan_qr` | Scan a QR code | QR कोड स्कैन करू |  |
| `au_scan_title` | Scan Authenticator QR | प्रमाणकक QR स्कैन करू |  |
| `au_search_hint` | Search codes, issuers… | कोड वा देनिहार ताकू… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | जेना JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | सिक्रेट कुंजी (Base32) |  |
| `au_tap_to_copy` | Tap to copy | कॉपी करय लेल टैप करू |  |
| `cat_apps` | Apps &amp; Logins | एप आ लॉगिन |  |
| `cat_banks` | Banks &amp; UPI | बैंक आ UPI |  |
| `cat_cards` | Cards | कार्ड |  |
| `cat_govid` | Gov &amp; ID | सरकारी आ पहिचान |  |
| `cat_investments` | Investments | निवेश |  |
| `cat_utilities` | Utilities | बिल आ कनेक्शन |  |
| `cd_mask_hidden` | hidden | नुकाएल |  |
| `cd_shield_high_sensitivity` | extra-protected field | बेसी संवेदनशील जानकारी |  |
| `gl_blank` | Blank template | खाली टेम्पलेट |  |
| `gl_cat_apps` | Apps | एप |  |
| `gl_cat_banks` | Banks | बैंक |  |
| `gl_cat_cards` | Cards | कार्ड |  |
| `gl_cat_demat` | Demat | डीमैट |  |
| `gl_cat_govid` | Gov ID | सरकारी पहिचान |  |
| `gl_cat_popular` | Popular | लोकप्रिय |  |
| `gl_cat_shopping` | Shopping | खरीददारी |  |
| `gl_cat_travel` | Travel | यात्रा |  |
| `gl_cat_upi` | UPI | UPI |  |
| `gl_cat_utilities` | Utilities | बिल |  |
| `gl_head_emph` | storing? | सहेजि रहल छी? |  |
| `gl_head_lead` | "What are we " | "हम की " |  |
| `gl_matches` | %1$d matches | %1$d भेटल |  |
| `gl_most_used` | Most-used first | सभ सँ बेसी प्रयोग होबय बला पहिने |  |
| `gl_not_found` | Can’t find a service? | सेवा नहि भेटि रहल? |  |
| `gl_search` | Search %1$d Indian services… | %1$d भारतीय सेवामे ताकू… |  |
| `gl_suggested` | Suggested for you | अहाँक लेल सुझाव |  |
| `hm_add_first` | Add your first record | अपन पहिल प्रविष्टि जोड़ू |  |
| `hm_all_offline` | all offline. | सभटा ऑफलाइन। |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d वस्तु, " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d वस्तु, " |  |
| `hm_detail_placeholder` | Pick a record to see it here | एतय देखबाक लेल एकटा रेकर्ड चुनू |  |
| `hm_empty_blank` | A blank vault, ready. | खाली भण्डार, तैयार। |  |
| `hm_empty_head_emph` | waiting. | बाट तकैत अछि। |  |
| `hm_empty_head_lead` | "Your vault is " | "अहाँक भण्डार " |  |
| `hm_filter_all` | All | सभटा |  |
| `hm_import_backup` | Import an encrypted backup | एन्क्रिप्ट कएल बैकअप आयात |  |
| `hm_import_backup_note` | Open a .kosh file from this device | एहि फोने सँ .kosh फाइल खोलू |  |
| `hm_inst_many` | %1$d institutions | %1$d संस्था |  |
| `hm_inst_one` | %1$d institution | %1$d संस्था |  |
| `hm_kit_banner_action` | Save one now | आबे सहेजू |  |
| `hm_kit_banner_dismiss` | Remind me later | बादमे इयाद करबू |  |
| `hm_kit_banner_title` | No recovery kit saved | कोनो रिकवरी किट सहेजल नहि अछि |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | खुजल अछि · छोड़ितहि बन्न भ जाएत |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | खुजल अछि · छोड़लाक %1$d मिनट बाद बन्न |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | खुजल अछि · छोड़लाक 1 मिनट बाद बन्न |  |
| `hm_no_match` | Nothing matches “%1$s” | “%1$s” लेल किछु नहि भेटल |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | कोनो संस्था, UPI हैंडल, वा अंतिम चारि अंक आजमाबू। |  |
| `hm_pinned` | Pinned | पिन कएल |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC, priya@upi, PAN… ताकू |  |
| `hm_start_template` | Start with a template | टेम्पलेट सँ शुरू करू |  |
| `ic_could_not` | Could not import | आयात नहि भ सकल |  |
| `ic_done` | Done | भ गेल |  |
| `ic_import` | Import | आयात |  |
| `ic_imported` | Imported | आयात भ गेल |  |
| `ic_importing` | Importing… | आयात भ रहल अछि… |  |
| `ic_new_many` | %1$d new logins. | %1$d नव लॉगिन। |  |
| `ic_new_one` | %1$d new login. | %1$d नव लॉगिन। |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d जोड़ल गेल, %2$d बदलल गेल। |  |
| `ic_title` | Import from %1$s? | %1$s सँ आयात कएल जाए? |  |
| `ic_too_large` | That file is too large to be a credential export. | ई फाइल पासवर्ड निर्यात होबय लेल बहुत पैघ अछि। |  |
| `import_action` | Import | आयात |  |
| `import_locked` | Unlock your vault before importing. | आयात करबा सँ पहिने अपन भण्डार खोलू। |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d जोड़ल गेल, %2$d बदलल गेल। किछु मेटाओल नहि गेल। |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | ओ फाइल Zerokosh भण्डारक रूपमे नहि पढ़ल जा सकल। |  |
| `import_nothing_new` | Everything in that backup was already here. | ओहि बैकअपमे जे छल, से सभटा पहिनहि एतय छल। |  |
| `import_passphrase_label` | Backup passphrase | बैकअपक पासफ्रेज |  |
| `import_title` | Import a backup | बैकअप आयात करू |  |
| `kicker_locked` | Locked | बन्न अछि |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · किछु एहि फोन सँ बाहर नहि गेल |  |
| `lk_touch_unlock` | Touch to unlock | खोलय लेल छुबू |  |
| `lk_welcome_emph` | Your vault is sealed. | अहाँक भण्डार बन्न अछि। |  |
| `lk_welcome_lead` | Welcome back. | फेर स्वागत अछि। |  |
| `msg_auth_needed` | Confirm it\'s you to see this | देखय लेल पक्का करू जे ई अहीं छी |  |
| `msg_back` | Back | पाछू |  |
| `msg_cancel` | Cancel | रद्द |  |
| `msg_file_damaged` | File damaged — restored from backup | फाइल बिगड़ल छल — बैकअप सँ ठीक क देल गेल |  |
| `msg_ok` | OK | ठीक अछि |  |
| `msg_saved` | Saved | सहेजल गेल |  |
| `nav_all_templates` | All templates | सभटा टेम्पलेट |  |
| `nav_damaged_emph` | vault file | भण्डार फाइलमे |  |
| `nav_damaged_kicker` | Damaged state | बिगड़ल हालत |  |
| `nav_damaged_lead` | "Something in the " | "अहाँक " |  |
| `nav_damaged_tail` | " is off." | " किछु गड़बड़ी अछि।" |  |
| `nav_integrity_title` | Integrity check failed | अखण्डताक जाँच विफल भेल |  |
| `nav_scan` | Scan | स्कैन |  |
| `nav_tap_card` | Tap a card | कार्ड टैप करू |  |
| `nav_what_next` | What to do next | आब आगू की |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC बन्न अछि। सेटिंगमे चालू क क फेर कोशिश करू। |  |
| `nfc_hold_card` | Hold your card to the phone | कार्ड फोनमे सटा क राखू |  |
| `nfc_missed` | Did not catch that | पकड़मे नहि आयल |  |
| `nfc_read_failed` | That card could not be read. Try again. | ओ कार्ड नहि पढ़ल जा सकल। फेर कोशिश करू। |  |
| `nfc_reading` | Reading… | पढ़ि रहल छी… |  |
| `nfc_try_again` | Try again | फेर कोशिश करू |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · एहि फोने पर नापल गेल |  |
| `ob_argon_faster` | Faster unlock | जल्दी खुजत |  |
| `ob_argon_harder` | Harder to attack | तोड़नाइ कठिन |  |
| `ob_argon_measuring` | Measuring this device… | ई फोन नापि रहल छी… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id कठोरता |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | शब्दकोशक एकोटा शब्द नहि |  |
| `ob_check_pass_length` | 10 characters or more | 10 वा बेसी अक्षर |  |
| `ob_check_pass_reuse` | Not reused from another app | आन एप सँ फेर प्रयोग कएल नहि |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | ने जन्मदिन, ने सालगिरह |  |
| `ob_check_pin_digits` | All six digits entered | छहो अंक भरल |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | ने लगातार अंक, ने दोहराओल |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~तोड़य लेल %1$d सदी |  |
| `ob_crack_days` | ~%1$d days to crack | ~तोड़य लेल %1$d दिन |  |
| `ob_crack_forever` | longer than the sun | सूर्य सँ सेहो बेसी समय |  |
| `ob_crack_hours` | ~hours to crack | ~तोड़य लेल किछु घण्टा |  |
| `ob_crack_seconds` | ~seconds to crack | ~तोड़य लेल किछु सेकेण्ड |  |
| `ob_crack_years` | ~%1$d years to crack | ~तोड़य लेल %1$d बरख |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | प्रमाणित, हरेक भण्डारक लेल अलग नॉन्स |  |
| `ob_fact_encryption_title` | Encryption | एन्क्रिप्शन |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | सेटअपक समय अहाँक फोन पर नापल गेल |  |
| `ob_fact_kdf_title` | Key stretching | कुंजी स्ट्रेचिंग |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | रिसेट लिंक नहि। सपोर्टक पछुआ दरबज्जो नहि। |  |
| `ob_fact_lost_value` | Nobody can recover it | केओ घुरा नहि सकैत अछि |  |
| `ob_fact_network_title` | Network permission | नेटवर्कक अनुमति |  |
| `ob_fact_network_value` | Not requested | कहियो मंगले नहि गेल |  |
| `ob_fact_quick_title` | Quick unlock | जल्दी अनलॉक |  |
| `ob_fact_quick_value` | Hardware keystore | हार्डवेयर कीस्टोर |  |
| `ob_lang_continue` | Continue in %1$s | %1$s मे आगू बढ़ू |  |
| `ob_lang_head_emph` | language. | भाषा चुनू। |  |
| `ob_lang_head_lead` | "Choose your " | "अपन " |  |
| `ob_lang_search` | Search %1$d languages | %1$d भाषामे ताकू |  |
| `ob_quick_continue_pass` | Continue with passphrase | पासफ्रेजक संग आगू बढ़ू |  |
| `ob_quick_enable` | Enable quick unlock | जल्दी अनलॉक चालू करू |  |
| `ob_quick_fingerprint` | Fingerprint | आङुरक छाप |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | तेज, हार्डवेयर सँ सुरक्षित अनलॉक। |  |
| `ob_quick_head_emph` | Without the cloud. | बिनु क्लाउडक। |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "एक छुअब पर खुजत। " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox। कोनो बायोमेट्रिक जानकारी कहियो Zerokosh धरि नहि पहुँचैत अछि। |  |
| `ob_quick_hw_title` | Hardware-backed | हार्डवेयर सँ सुरक्षित |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | एहि फोनमे हार्डवेयर सेंसर नहि अछि। |  |
| `ob_quick_opening` | Opening your vault… | अहाँक भण्डार खुजि रहल अछि… |  |
| `ob_quick_pass_only` | Passphrase only | खाली पासफ्रेज |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | हर बेर लिखू। सभ सँ सुरक्षित। |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | जल्दी अनलॉक नहि लगल। फेर कोशिश करू, वा पासफ्रेजक संगहि आगू बढ़ू। |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | आब नहि — हम पासफ्रेज लिखब |  |
| `ob_quick_touch_title` | Touch the sensor | सेंसर छुबू |  |
| `ob_recommended` | Recommended | अनुशंसा |  |
| `ob_reveal_hide` | Hide | नुकाबू |  |
| `ob_reveal_show` | Show | देखाबू |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | लपेटय बला कुंजी हार्डवेयर कीस्टोरमे रहत। बायोमेट्रिक अगिला चरणमे। |  |
| `ob_seal_title` | Seal to this device | एहि फोने सँ बान्हू |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | एहि फोनमे हार्डवेयर बायोमेट्रिक नहि अछि। |  |
| `ob_soon` | SOON | जल्दिये |  |
| `ob_step_label` | Step %1$d of 6 | चरण %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | एहन किछु चुनू जे खाली अहीं कहैत छी |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | नीक · %1$d बिट एन्ट्रोपी |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | बहुत छोट · 10 अक्षर चाही |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | मजगूत · %1$d बिट एन्ट्रोपी |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | कमजोर · %1$d बिट एन्ट्रोपी |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | छह अंक जे अहाँक जिनगी देखि क केओ अन्दाज नहि लगा सकय |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | ठीके · %1$d बिट — पिन एहि सँ बेसी मजगूत नहि भ सकैत अछि |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | बहुत छोट · 6 अंक चाही |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | कमजोर · इहे पिन सभ सँ पहिने आजमाओल जाइत अछि |  |
| `ob_try_label` | TRY | आजमाबू |  |
| `qa_aadhaar` | Aadhaar | आधार |  |
| `qa_bank_account` | Bank account | बैंक खाता |  |
| `qa_pan` | PAN | PAN |  |
| `qa_upi_id` | UPI ID | UPI ID |  |
| `rd_copied` | Copied | कॉपी भ गेल |  |
| `rd_forget` | Forget | बिसरि जाउ |  |
| `rd_forget_these` | Forget these | ई सभ बिसरि जाउ |  |
| `rd_forget_title` | Forget previous passwords? | पुरान पासवर्ड बिसरि जाउ? |  |
| `rd_history_hide` | Hide | नुकाबू |  |
| `rd_history_show` | Show %1$d | %1$d देखाबू |  |
| `rd_hold_to_reveal` | Hold to reveal | देखय लेल दाबि क राखू |  |
| `rd_last_edit` | last edit %1$s | अंतिम बेर %1$s बदलल |  |
| `rd_release_to_hide` | Release to hide | नुकाबय लेल छोड़ू |  |
| `re_add_field` | + Add another field | + आर एकटा खाना जोड़ू |  |
| `re_add_field_title` | Add a field | खाना जोड़ू |  |
| `re_field_name` | Field name | खानाक नाम |  |
| `re_pick_date` | Pick a date | तारीख चुनू |  |
| `re_remove` | Remove | हटाबू |  |
| `re_tap_card` | Read the card by tapping it | पढ़य लेल कार्ड टैप करू |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | गुप्त मानू (नुकाएल रहत, देखय लेल दाबि क राखू) |  |
| `re_using_template` | using the %1$s template | %1$s टेम्पलेट प्रयोग क क |  |
| `rem_kit_title` | No recovery kit saved | कोनो रिकवरी किट सहेजल नहि अछि |  |
| `scr_about_license` | License: GPL-3.0 — free forever | लाइसेंस: GPL-3.0 — सदिखन मुफ्त |  |
| `scr_about_source` | Source code | सोर्स कोड |  |
| `scr_about_version` | Version %1$s | संस्करण %1$s |  |
| `scr_auth_add_qr` | Add via QR | QR सँ जोड़ू |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | अहाँक एप आ ब्रोकरक कोड एतय देखाएत |  |
| `scr_auth_scan_title` | Point the camera at the QR code | कैमरा QR कोडक ऊपर राखू |  |
| `scr_detail_copied` | Copied · clears in 30s | कॉपी भ गेल · 30 सेकेण्डमे मेटा जाएत |  |
| `scr_detail_copy` | Copy | कॉपी करू |  |
| `scr_detail_edit` | Edit | बदलू |  |
| `scr_detail_favorite` | Favourite | मनपसन्द |  |
| `scr_detail_hidden` | Hidden | नुकाएल |  |
| `scr_detail_hide` | Hide | नुकाबू |  |
| `scr_detail_history_empty` | Nothing replaced yet. | एखन धरि किछु नहि बदलल। |  |
| `scr_detail_history_title` | Previous passwords | पुरान पासवर्ड |  |
| `scr_detail_reveal` | Show | देखाबू |  |
| `scr_detail_shown` | Shown | देखा रहल अछि |  |
| `scr_edit_cancel` | Cancel | रद्द |  |
| `scr_edit_generate` | Generate | बनाबू |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | बैंक / कम्पनी (एक्के ठाम राखय लेल) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | ई ठीक नहि लागि रहल — एक बेर देखि लिअ |  |
| `scr_edit_link_none` | None | किछु नहि |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | ई कार्ड नम्बर सामान्य जाँचमे पास नहि होइत अछि — ठीक अछि तँ सहेजि लिअ |  |
| `scr_edit_month` | Month | मास |  |
| `scr_edit_picker_other` | Other… | आन… |  |
| `scr_edit_picker_other_hint` | Type your own | अपन लिखू |  |
| `scr_edit_required_title` | Give it a name first | पहिने एकरा एकटा नाम दिअ |  |
| `scr_edit_save` | Save | सहेजू |  |
| `scr_edit_title_hint` | Title | नाम |  |
| `scr_edit_title_new` | New | नव |  |
| `scr_edit_year` | Year | बरख |  |
| `scr_gallery_quick_add` | Quick add | जल्दी जोड़ू |  |
| `scr_gallery_title` | What do you want to save? | हम की सहेजि रहल छी? |  |
| `scr_home_add` | Add | जोड़ू |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | अहाँक बैंक खाता एहन देखाएत |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | अहाँक कार्ड, UPI, एप लॉगिन — सभटा एतहि |  |
| `scr_home_group_other` | Other | आन |  |
| `scr_home_no_results` | Nothing matches your search | अहाँक ताकबसँ किछु नहि भेटल |  |
| `scr_home_search_hint` | Search your vault | अपन भण्डारमे ताकू |  |
| `scr_home_tab_authenticator` | Authenticator | कोड |  |
| `scr_home_tab_home` | Home | घर |  |
| `scr_home_tab_settings` | Settings | सेटिंग |  |
| `scr_home_title` | Home | घर |  |
| `scr_language_continue` | Continue | आगू बढ़ू |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | अपन भाषा चुनू |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | कॉपी करय लेल बटन दबाबू · 30 सेकेण्डमे मेटा जाएत |  |
| `scr_login_helper_channel` | Login helper | लॉगिन सहायक |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s मे लॉगिन भ रहल अछि |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | आबे बैकअप फोल्डर लगा लिअ |  |
| `scr_quickunlock_enable` | Turn on | चालू करू |  |
| `scr_quickunlock_skip` | Not now | आब नहि |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | आङुर वा मुँह सँ खोलू |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s क तारीख लगीच अछि · Zerokosh खोलू |  |
| `scr_reminder_channel` | Renewal reminders | नवीकरणक इयाद |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh इयाद करा रहल अछि |  |
| `scr_settings_about` | About | बारेमे |  |
| `scr_settings_allow_screenshots` | Allow screenshots | स्क्रीनशॉट लेबय दिअ |  |
| `scr_settings_autofill` | Autofill service | ऑटोफिल सेवा |  |
| `scr_settings_autofill_off` | Not set up | लगाओल नहि गेल |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | उपलब्ध नहि |  |
| `scr_settings_autolock` | Lock when I leave the app | एप छोड़ितहि बन्न करू |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 मिनटक बाद |  |
| `scr_settings_autolock_immediately` | Immediately | तुरन्त |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d मिनटक बाद |  |
| `scr_settings_change_passphrase` | Change passphrase | पासफ्रेज बदलू |  |
| `scr_settings_current_passphrase` | Current passphrase | एखनुक पासफ्रेज |  |
| `scr_settings_export` | Export | निर्यात करू |  |
| `scr_settings_import` | Import passwords | पासवर्ड आयात करू |  |
| `scr_settings_language` | Language | भाषा |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | नव पासफ्रेज (कम सँ कम 10 अक्षर) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | नव रिकवरी कुंजी लिअ |  |
| `scr_settings_passphrase_changed` | Passphrase changed | पासफ्रेज बदलि गेल |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | आङुर / मुँह अनलॉक |  |
| `scr_settings_security_info` | How your data is protected | अहाँक डेटा कोना सुरक्षित अछि |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | बैकअप आ सिंक फोल्डर |  |
| `scr_settings_sync_not_set` | Not backed up | बैकअप नहि |  |
| `scr_settings_title` | Settings | सेटिंग |  |
| `se_title` | Not saved | सहेजल नहि गेल |  |
| `st_active_folder` | Active Folder | सक्रिय फोल्डर |  |
| `st_active_value` | Active · %1$s | सक्रिय · %1$s |  |
| `st_backing_up` | Backing up vault… | भण्डारक बैकअप लेल जा रहल अछि… |  |
| `st_backup_now` | Backup Now | आबे बैकअप लिअ |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | बैकअप आ सिंक फोल्डर |  |
| `st_change_folder` | Change Folder | फोल्डर बदलू |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | नव पासफ्रेज फेर लिखू |  |
| `st_connected_folder` | Connected folder: %1$s | जुड़ल फोल्डर: %1$s |  |
| `st_disconnect` | Disconnect | हटाबू |  |
| `st_done` | Done | भ गेल |  |
| `st_export_kosh` | Export encrypted .kosh | एन्क्रिप्ट कएल .kosh निर्यात |  |
| `st_folder_fallback` | Folder | फोल्डर |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | पासफ्रेज बिसरि गेलहुँ? |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | अपन आङुरसँ नव राखू |  |
| `st_generate` | Generate | बनाबू |  |
| `st_group_about` | About | बारेमे |  |
| `st_group_appearance` | Appearance | रूप |  |
| `st_group_security` | Security | सुरक्षा |  |
| `st_group_sync` | Sync | सिंक |  |
| `st_import_kosh` | Import a .kosh backup | .kosh बैकअप आयात |  |
| `st_import_other` | Import from another password manager | आन पासवर्ड मैनेजर सँ आयात |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | लाइसेंस |  |
| `st_logos_by` | Logos provided by | लोगो देनिहार |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | एकरा ऑफलाइन राखू। पुरान रिकवरी कुंजी आब काज नहि करत। |  |
| `st_new_recovery_result` | Your new Recovery Key: | अहाँक नव रिकवरी कुंजी: |  |
| `st_subtitle` | Your rules. | अहाँक नियम। |  |
| `st_theme` | Theme | थीम |  |
| `st_theme_dark` | Dark | अन्हार |  |
| `st_theme_light` | Light | इजोत |  |
| `st_theme_system` | System | सिस्टम |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | भण्डार %1$s मे सहेजल गेल! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | बैकअप नहि भेल — फोल्डरक अनुमति देखू |  |
| `st_toast_disconnected` | Backup folder disconnected | बैकअप फोल्डर हटा देल गेल |  |
| `st_toast_export_failed` | Export failed | निर्यात नहि भेल |  |
| `st_toast_exported` | Encrypted vault exported | एन्क्रिप्ट कएल भण्डार निर्यात भ गेल |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | बैकअप फोल्डर जुड़ि गेल, आ भण्डार %1$s मे सहेजल गेल! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | बैकअप फोल्डर जुड़ि गेल: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | फोल्डर नहि जुड़ि सकल: %1$s |  |
| `st_vault_review` | Vault review | भण्डारक जाँच |  |
| `st_vault_review_detail` | Reused, weak, expiring | फेर प्रयोग कएल, कमजोर, मियाद खतम होइत |  |
| `tab_codes` | Codes | कोड |  |
| `tab_settings` | Settings | सेटिंग |  |
| `tab_templates` | Templates | टेम्पलेट |  |
| `tab_vault` | Vault | भण्डार |  |
| `time_days` | %1$dd ago | %1$d दिन पहिने |  |
| `time_hours` | %1$dh ago | %1$d घण्टा पहिने |  |
| `time_just_now` | just now | एखने |  |
| `time_minutes` | %1$dm ago | %1$d मिनट पहिने |  |
| `time_months` | %1$dmo ago | %1$d मास पहिने |  |
| `time_years` | %1$dy ago | %1$d बरख पहिने |  |
| `tpl_aadhaar_card` | Aadhaar Card | आधार |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | आधार नम्बर |  |
| `tpl_aadhaar_card_address` | Address | आधार पर पता |  |
| `tpl_aadhaar_card_dob` | Dob | जन्म तिथि |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | स्कैन कएल प्रति |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | जुड़ल मोबाइल |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar पासकोड |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | आधार पर नाम |  |
| `tpl_aadhaar_card_notes` | Notes | टिप्पणी |  |
| `tpl_app_profile` | App Profile | एप प्रोफाइल |  |
| `tpl_app_profile_app_name` | App name | एपक नाम |  |
| `tpl_app_profile_gift_cards` | Gift cards | गिफ्ट कार्ड |  |
| `tpl_app_profile_membership` | Membership | सदस्यता |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | सदस्यता नवीकरण |  |
| `tpl_app_profile_notes` | Notes | टिप्पणी |  |
| `tpl_app_profile_password_if_any` | Password (if any) | पासवर्ड (जँ होअय) |  |
| `tpl_app_profile_registered_email` | Registered email | पंजीकृत ईमेल |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | पंजीकृत मोबाइल |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_bank_account` | Bank Account | बैंक खाता |  |
| `tpl_bank_account_account_number` | Account number | खाता नम्बर |  |
| `tpl_bank_account_account_type` | Account type | खाताक प्रकार |  |
| `tpl_bank_account_bank_name` | Bank name | बैंकक नाम |  |
| `tpl_bank_account_branch` | Branch | शाखा |  |
| `tpl_bank_account_customer_id` | Customer id | ग्राहक ID |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC कोड |  |
| `tpl_bank_account_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_bank_account_micr` | MICR code | MICR कोड |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | नेट-बैंकिंग यूजर ID |  |
| `tpl_bank_account_nominee` | Nominee | नामित |  |
| `tpl_bank_account_notes` | Notes | टिप्पणी |  |
| `tpl_bank_account_profile_password` | Profile password | प्रोफाइल पासवर्ड |  |
| `tpl_bank_account_registered_email` | Registered email | पंजीकृत ईमेल |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | पंजीकृत मोबाइल |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | लेनदेनक पासवर्ड |  |
| `tpl_card` | Card | कार्ड |  |
| `tpl_card_atm_pin` | ATM PIN | ATM पिन |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | बिलिंग चक्रक दिन |  |
| `tpl_card_card_network` | Card network | नेटवर्क |  |
| `tpl_card_card_number` | Card number | कार्ड नम्बर |  |
| `tpl_card_card_portal_login` | Card portal login | कार्ड पोर्टल लॉगिन |  |
| `tpl_card_card_portal_password` | Card portal password | कार्ड पोर्टल पासवर्ड |  |
| `tpl_card_card_type` | Card type | कार्डक प्रकार |  |
| `tpl_card_card_variant` | Card variant | कार्ड वेरिएंट |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | मियाद |  |
| `tpl_card_linked_account` | Linked account | जुड़ल खाता |  |
| `tpl_card_name_on_card` | Name on card | कार्ड पर नाम |  |
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
| `tpl_demat_nominee` | Nominee | नामित |  |
| `tpl_demat_notes` | Notes | टिप्पणी |  |
| `tpl_digilocker` | Digilocker | DigiLocker |  |
| `tpl_digilocker_login_username` | Login username | यूजरनेम |  |
| `tpl_digilocker_notes` | Notes | टिप्पणी |  |
| `tpl_digilocker_portal_password` | Portal password | पासवर्ड |  |
| `tpl_digilocker_security_pin` | Security pin | सुरक्षा पिन |  |
| `tpl_driving_license` | Driving License | ड्राइविंग लाइसेंस |  |
| `tpl_driving_license_dl_number` | Dl number | लाइसेंस नम्बर |  |
| `tpl_driving_license_dob` | Dob | जन्म तिथि |  |
| `tpl_driving_license_expiry_date` | Expiry date | एहि तारीख धरि वैध |  |
| `tpl_driving_license_file_copy` | Scanned copy | स्कैन कएल प्रति |  |
| `tpl_driving_license_issue_date` | Issue date | जारी भेलाक तारीख |  |
| `tpl_driving_license_name_on_dl` | Name on dl | लाइसेंस पर नाम |  |
| `tpl_driving_license_notes` | Notes | टिप्पणी |  |
| `tpl_driving_license_rto_location` | Rto location | RTO |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | गाड़ीक श्रेणी |  |
| `tpl_epf_pension` | Epf Pension | EPF / पेंशन |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | जुड़ल मोबाइल |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | EPF पर नाम |  |
| `tpl_epf_pension_nominee` | Nominee | नामित |  |
| `tpl_epf_pension_notes` | Notes | टिप्पणी |  |
| `tpl_epf_pension_password` | Password | पासवर्ड |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | PF सदस्य ID |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO पासवर्ड |  |
| `tpl_epf_pension_scheme` | Scheme | योजना |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | सरकारी पहिचान पत्र |  |
| `tpl_gov_id_expiry` | Expiry | मियाद |  |
| `tpl_gov_id_file_copy` | Scanned copy | स्कैन कएल प्रति |  |
| `tpl_gov_id_id_kind` | ID type | पहिचान पत्रक प्रकार |  |
| `tpl_gov_id_id_number` | ID number | पहिचान नम्बर |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | पत्रक अनुसार नाम |  |
| `tpl_gov_id_notes` | Notes | टिप्पणी |  |
| `tpl_gov_id_portal_login` | Portal login | पोर्टल लॉगिन |  |
| `tpl_gov_id_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_insurance` | Insurance | बीमा |  |
| `tpl_insurance_agent_contact` | Agent contact | एजेंटक सम्पर्क |  |
| `tpl_insurance_commencement_date` | Commencement date | शुरू भेलाक तारीख |  |
| `tpl_insurance_insurer` | Insurer | बीमा कम्पनी |  |
| `tpl_insurance_maturity_date` | Maturity date | मियाद पूरा होबाक तारीख |  |
| `tpl_insurance_nominee` | Nominee | नामित |  |
| `tpl_insurance_notes` | Notes | टिप्पणी |  |
| `tpl_insurance_policy_number` | Policy number | पॉलिसी नम्बर |  |
| `tpl_insurance_policy_term` | Policy term | पॉलिसीक अवधि |  |
| `tpl_insurance_policy_type` | Policy type | पॉलिसीक प्रकार |  |
| `tpl_insurance_portal_login` | Portal login | पोर्टल लॉगिन |  |
| `tpl_insurance_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_insurance_premium_amount` | Premium amount | प्रीमियम राशि |  |
| `tpl_insurance_premium_due_date` | Premium due date | प्रीमियमक तारीख |  |
| `tpl_insurance_premium_mode` | Premium mode | प्रीमियम कोना भरैत छी |  |
| `tpl_insurance_sum_assured` | Sum assured | बीमाक राशि |  |
| `tpl_login` | Login | लॉगिन |  |
| `tpl_login_notes` | Notes | टिप्पणी |  |
| `tpl_login_password` | Password | पासवर्ड |  |
| `tpl_login_recovery_codes` | Recovery codes | रिकवरी कोड |  |
| `tpl_login_username` | Username | यूजरनेम |  |
| `tpl_login_website` | Website | वेबसाइट |  |
| `tpl_pan_card` | Pan Card | PAN कार्ड |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | आधार सँ जुड़ल |  |
| `tpl_pan_card_dob` | Dob | जन्म तिथि |  |
| `tpl_pan_card_e_filing_password` | E filing password | ई-फाइलिंग पासवर्ड |  |
| `tpl_pan_card_fathers_name` | Fathers name | पिताक नाम |  |
| `tpl_pan_card_file_copy` | Scanned copy | स्कैन कएल प्रति |  |
| `tpl_pan_card_name_on_pan` | Name on pan | PAN पर नाम |  |
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
| `tpl_passport_dob` | Dob | जन्म तिथि |  |
| `tpl_passport_expiry_date` | Expiry date | मियाद खतम होबाक तारीख |  |
| `tpl_passport_file_copy` | Scanned copy | स्कैन कएल प्रति |  |
| `tpl_passport_given_names` | Given names | देल नाम |  |
| `tpl_passport_issue_date` | Issue date | जारी भेलाक तारीख |  |
| `tpl_passport_notes` | Notes | टिप्पणी |  |
| `tpl_passport_passport_number` | Passport number | पासपोर्ट नम्बर |  |
| `tpl_passport_place_of_issue` | Place of issue | जारी भेलाक स्थान |  |
| `tpl_passport_portal_login` | Portal login | Passport Seva लॉगिन |  |
| `tpl_passport_portal_password` | Portal password | Passport Seva पासवर्ड |  |
| `tpl_passport_surname` | Surname | उपनाम |  |
| `tpl_secure_note` | Secure Note | सुरक्षित टिप्पणी |  |
| `tpl_secure_note_attachment` | Attachment | संलग्न |  |
| `tpl_secure_note_body` | Note | टिप्पणी |  |
| `tpl_shopping` | Shopping | खरीददारी खाता |  |
| `tpl_shopping_gift_card_code` | Gift card code | गिफ्ट कार्ड कोड |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | गिफ्ट कार्ड पिन |  |
| `tpl_shopping_membership_id` | Membership id | सदस्यता ID |  |
| `tpl_shopping_notes` | Notes | टिप्पणी |  |
| `tpl_shopping_password` | Password | पासवर्ड |  |
| `tpl_shopping_registered_email` | Registered email | पंजीकृत ईमेल |  |
| `tpl_shopping_registered_mobile` | Registered mobile | पंजीकृत मोबाइल |  |
| `tpl_shopping_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_telecom` | Telecom | मोबाइल आ इंटरनेट |  |
| `tpl_telecom_account_number` | Account number | खाता नम्बर |  |
| `tpl_telecom_circle` | Circle | सर्कल |  |
| `tpl_telecom_mobile_number` | Mobile number | मोबाइल नम्बर |  |
| `tpl_telecom_notes` | Notes | टिप्पणी |  |
| `tpl_telecom_operator` | Operator | कम्पनी |  |
| `tpl_telecom_plan_type` | Plan type | प्लानक प्रकार |  |
| `tpl_telecom_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_telecom_puk` | PUK code | PUK कोड |  |
| `tpl_telecom_renewal_date` | Renewal date | रिचार्जक तारीख |  |
| `tpl_telecom_sim_number` | Sim number | सिम नम्बर (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | सिम पिन |  |
| `tpl_transit` | Transit | यात्रा पास |  |
| `tpl_transit_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_transit_notes` | Notes | टिप्पणी |  |
| `tpl_transit_operator_name` | Operator name | कम्पनी |  |
| `tpl_transit_registered_email` | Registered email | पंजीकृत ईमेल |  |
| `tpl_transit_registered_mobile` | Registered mobile | पंजीकृत मोबाइल |  |
| `tpl_transit_smart_card_number` | Smart card number | स्मार्ट कार्ड नम्बर |  |
| `tpl_transit_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_travel_booking` | Travel Booking | यात्रा बुकिंग |  |
| `tpl_travel_booking_account_username` | Account username | यूजरनेम |  |
| `tpl_travel_booking_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_travel_booking_notes` | Notes | टिप्पणी |  |
| `tpl_travel_booking_provider` | Provider | कम्पनी |  |
| `tpl_travel_booking_registered_email` | Registered email | पंजीकृत ईमेल |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | पंजीकृत मोबाइल |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_upi` | UPI | UPI |  |
| `tpl_upi_app_name` | App name | UPI एप |  |
| `tpl_upi_apps_used` | Apps used | कोन एपमे सक्रिय |  |
| `tpl_upi_linked_account` | Linked account | जुड़ल खाता |  |
| `tpl_upi_notes` | Notes | टिप्पणी |  |
| `tpl_upi_upi_id` | UPI ID | UPI ID |  |
| `tpl_upi_upi_pin` | UPI PIN | UPI पिन |  |
| `tpl_utility` | Utility | बिल आ कनेक्शन |  |
| `tpl_utility_account_holder` | Account holder | खाताधारक |  |
| `tpl_utility_consumer_number` | Consumer number | उपभोक्ता नम्बर |  |
| `tpl_utility_due_day` | Bill due day | बिल भरबाक दिन |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag ID |  |
| `tpl_utility_notes` | Notes | टिप्पणी |  |
| `tpl_utility_portal_login` | Portal login | पोर्टल लॉगिन |  |
| `tpl_utility_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_utility_provider` | Provider | सेवा देनिहार कम्पनी |  |
| `tpl_utility_utility_kind` | Utility kind | कोन चीजक बिल |  |
| `tpl_utility_vehicle_number` | Vehicle number | गाड़ी नम्बर |  |
| `tpl_utility_wifi_password` | Wi-Fi password | Wi-Fi पासवर्ड |  |
| `tpl_voter_id` | Voter Id | मतदाता पहिचान पत्र |  |
| `tpl_voter_id_constituency` | Constituency | निर्वाचन क्षेत्र |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC नम्बर |  |
| `tpl_voter_id_file_copy` | Scanned copy | स्कैन कएल प्रति |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | मतदाता कार्ड पर नाम |  |
| `tpl_voter_id_notes` | Notes | टिप्पणी |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP पासवर्ड |  |
| `tr_days_many` | %1$d days left | %1$d दिन बाँकी |  |
| `tr_days_one` | %1$d day left | %1$d दिन बाँकी |  |
| `tr_gone_today` | gone today | आइ चलि जाएत |  |
| `tr_restore` | Restore | घुरा आनू |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | ओकर बाद ओ सदा लेल चलि जाइत अछि — आन कतहु प्रति नहि। |  |
| `ui_hide_passphrase` | Hide passphrase | पासफ्रेज नुकाबू |  |
| `ui_label_count` | %1$s, %2$s | %1$s, %2$s |  |
| `ui_show_passphrase` | Show passphrase | पासफ्रेज देखाबू |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | एहि फोने पर %1$d प्रविष्टि सँ मिलाओल गेल। किछु कतहु नहि पठाओल गेल। |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | एहि फोने पर %1$d प्रविष्टि सँ मिलाओल गेल। किछु कतहु नहि पठाओल गेल। |  |
| `vh_count_many` | %1$d things worth a look. | %1$d बात पर ध्यान देनाइ चाही। |  |
| `vh_count_one` | %1$d thing worth a look. | %1$d बात पर ध्यान देनाइ चाही। |  |
| `vh_empty` | No reused, weak or expiring credentials. | ने फेर प्रयोग कएल, ने कमजोर, ने मियाद खतम होइत किछु अछि। |  |
| `vh_kind_common` | Commonly guessed | आसानी सँ अन्दाज लागय बला |  |
| `vh_kind_expiring` | Expiring | मियाद खतम भ रहल |  |
| `vh_kind_reused` | Reused password | फेर प्रयोग कएल पासवर्ड |  |
| `vh_kind_weak` | Weak | कमजोर |  |
| `vh_no_kit_title` | No recovery kit saved | कोनो रिकवरी किट सहेजल नहि अछि |  |
| `vh_nothing` | Nothing to fix. | ठीक करबाक किछु नहि। |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | ऑफलाइन |  |
| `wl_chip_open` | Open source | ओपन सोर्स |  |
| `wl_create` | Create a new vault | नव भण्डार बनाबू |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | ने ईमेल · ने खाता · किछु एहि फोन सँ बाहर नहि जाइत अछि |  |
| `wl_head_1` | Your keys. | अहाँक कुंजी। |  |
| `wl_head_2` | Your device. | अहाँक फोन। |  |
| `wl_head_3` | No server. | सर्वर नहि। |  |
| `wl_restore` | Restore from Recovery Kit | रिकवरी किट सँ घुरा आनू |  |
| `wl_sr_headline` | Your keys. Your device. No server. | अहाँक कुंजी। अहाँक फोन। सर्वर नहि। |  |
