# Nepali (`ne`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-ne/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Nepali | ok? |
|---|---|---|---|
| `au_close` | Close | बन्द गर्नुहोस् |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | छानिएको तस्बिरमा सही TOTP QR कोड भेटिएन |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | खालीबाट सुरु गरेर आफ्ना खानाहरूको नाम आफैं राख्नुहोस् — टेम्प्लेटले लेबल मात्र भर्छन्, डेटा कहिल्यै होइन। |  |
| `hm_close_search` | Close search | खोज बन्द गर्नुहोस् |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | यो फोनबाट कहिल्यै बाहिर जाँदैन। सुरक्षित गर्दा इन्क्रिप्ट गरिन्छ। |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | अब तपाईंले आयात गरेको फाइल हटाउनुहोस्। त्यो तपाईंका पासवर्डको खुला सूची हो, र अझै तपाईंको Downloads मा छ। |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | सबै कुरा यही फोनमा मात्र डिक्रिप्ट हुन्छ। केही पनि अपलोड हुँदैन, किनभने यो एपले नेटवर्क जडान खोल्नै सक्दैन। |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | बैंकहरूले कहिल्यै तपाईंको OTP माग्दैनन्। माग्ने ठग हो। |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | कुनै बैंक अधिकारीले तपाईंलाई स्क्रिन सेयर गर्ने एप हाल्न भन्दैन। |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | तपाईंको UPI पिन UPI एपको किप्याडका लागि मात्र हो — फोनमा कसैलाई नभन्नुहोस्। |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | KYC एकै दिनमा सकिँदैन। “आज KYC सकिन्छ” भन्ने सन्देश ठगी हुन्। |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | पैसा पाउन न पिन हाल्नुपर्छ, न QR स्क्यान गर्नुपर्छ। |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | बिजुली काटिने SMS र त्यसमा कसैको निजी नम्बर? त्यो ठगी हो। |  |
| `nav_close_menu` | Close menu | मेनु बन्द गर्नुहोस् |  |
| `nfc_cannot_read` | Cannot read cards | कार्ड पढ्न सकिँदैन |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | यो फोनमा NFC छैन, त्यसैले कार्ड पढ्न सकिँदैन। |  |
| `ob_fact_lost_title` | If you lose your keys | साँचाहरू हराए भने |  |
| `ob_fact_network_note` | The app literally cannot phone home | यो एपले कतै पनि सम्पर्क गर्न सक्दैन |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | बायोमेट्रिक सुरक्षा चिपबाहिर कहिल्यै जाँदैन |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | तपाईंले साँचो त्यही फोल्डरमा राख्नुभयो जसले तपाईंको इन्क्रिप्ट गरिएको भण्डार सिंक गर्छ। अब त्यो फोल्डर पाउनेले दुवै पाउँछ। साँचो अन्तै राख्नुहोस् — कागज, अर्को खाता, वा दराज। |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | यो तपाईंको भण्डार फाइलकै छेउमा छ |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH रिकभरी |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | स्क्यान गर्नुहोस् वा लेख्नुहोस्। पुनः इन्स्टल, फ्याक्ट्री रिसेट, वा फोन हराएपछि पनि काम गर्छ। |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | भर्खरै सुरक्षित गरेको किटबाट समूह %1$d र समूह %2$d टाइप गर्नुहोस्। |  |
| `ob_kit_challenge_hint` | Group %1$d | समूह %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | पहिले किट सुरक्षित गर्नुहोस्, अनि समूह %1$d र %2$d फेरि टाइप गर्नुहोस्। |  |
| `ob_kit_challenge_title` | Check you actually have it | किट साँच्चै तपाईंसँग छ कि छैन हेर्नुहोस् |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | यो माथिको साँचोसँग मिल्दैन। |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | कसैले — Zerokosh ले पनि — यो मेरा लागि फर्काउन सक्दैन। |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "मैले यो अफलाइन राखेँ। " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | एकपटक मात्र देखिन्छ, कहिल्यै खुला रूपमा राखिँदैन। भित्र आउन सक्नुहुन्छ भने सेटिङबाट नयाँ बनाउनुहोस्। |  |
| `ob_kit_head_emph` | On paper. | कागजमा। |  |
| `ob_kit_head_lead` | "One key. " | "एउटा साँचो। " |  |
| `ob_kit_head_tail` | " Never online." | " कहिल्यै अनलाइन होइन।" |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | Gmail होइन, WhatsApp होइन, स्क्रिनसट पनि होइन। दराज, बैंक लकर, वा स्टिलको पाता। |  |
| `ob_kit_offline_title` | Keep it off the internet | यसलाई इन्टरनेटबाट टाढा राख्नुहोस् |  |
| `ob_kit_print` | Print | छाप्नुहोस् |  |
| `ob_kit_print_note` | A printer, or Save as PDF | प्रिन्टर, वा PDF का रूपमा सुरक्षित |  |
| `ob_kit_qr` | QR image | QR तस्बिर |  |
| `ob_kit_qr_cd` | Recovery key QR code | रिकभरी साँचोको QR कोड |  |
| `ob_kit_qr_note` | To an offline gallery | अफलाइन ग्यालरीमा |  |
| `ob_kit_regenerate` | Regenerate | नयाँ बनाउनुहोस् |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | यो सुरक्षित भएन। फेरि प्रयास गर्नुहोस्, वा अर्को ठाउँ छान्नुहोस्। |  |
| `ob_kit_save_pdf` | Save PDF | PDF सुरक्षित गर्नुहोस् |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | एक पानाको छाप्न मिल्ने किट |  |
| `ob_kit_saved` | I\'ve saved my kit | मैले आफ्नो किट सुरक्षित गरेँ |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s मा सुरक्षित भयो |  |
| `ob_kit_sent_to_printer` | Sent to the printer | प्रिन्टरमा पठाइयो |  |
| `ob_kit_skip` | I\'ll do this later | यो पछि गर्छु |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | तपाईंको भण्डार चलिरहनेछ। किट सुरक्षित नहुँदासम्म Zerokosh सम्झाइरहनेछ। |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | यही फोनमा बनेको, एकपटक मात्र देखिनेछ। पासफ्रेज बिर्सिँदा भित्र फर्किने यही एउटै बाटो हो। |  |
| `ob_kit_working` | Working… | काम भइरहेको छ… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | भण्डारका लेबल, टेम्प्लेट र चेतावनी तुरुन्तै बदलिनेछन्। सेटिङमा जुनसुकै बेला बदल्न सकिन्छ। |  |
| `ob_pass_confirm` | Confirm | फेरि लेख्नुहोस् |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | यो हामीले कहिल्यै देख्दैनौं। कुनै रिसेट लिंक छैन। |  |
| `ob_pass_head_emph` | held only | तपाईंसँग मात्र |  |
| `ob_pass_head_lead` | "One secret, " | "एउटै गोप्य कुरा, " |  |
| `ob_pass_head_tail` | " by you." | । |  |
| `ob_pass_no_match` | no match | मिलेन |  |
| `ob_pass_seal` | Seal the vault | भण्डार बन्द गर्नुहोस् |  |
| `ob_pass_sealing` | Sealing… | बन्द गर्दै… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | नमिल्ने तीन-चार शब्द एउटा चलाख शब्दभन्दा राम्रो हुन्छ। यो पर्दाबाट केही बाहिर जाँदैन। |  |
| `ob_pass_tab_passphrase` | Passphrase | पासफ्रेज |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 अंकको पिन |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | तपाईंको औंलाको छाप फोनको सुरक्षा चिपभित्रै बस्छ। त्यो यो फोनबाट कहिल्यै बाहिर जाँदैन। |  |
| `ob_trust_continue` | I understand · Continue | बुझें · अगाडि बढ्नुहोस् |  |
| `ob_trust_head_emph` | don\'t | थाहा छैन |  |
| `ob_trust_head_lead` | "Exactly what we " | "हामीलाई साँच्चै के " |  |
| `ob_trust_head_tail` | " know." | । |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | पासफ्रेज बिर्सिएर रिकभरी किट पनि हरायो भने, भण्डार बन्दै रहनेछ — तपाईंका लागि, हाम्रा लागि, सबैका लागि। |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "साँचाहरू तपाईंसँग। " |  |
| `ob_trust_stat_files` | .kosh file on device | फोनमा .kosh फाइल |  |
| `ob_trust_stat_servers` | servers contacted | सर्भरसँग सम्पर्क |  |
| `ob_trust_stat_trackers` | trackers or SDKs | ट्र्याकर वा SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | यो एकपटक पढ्नुहोस्। सम्पूर्ण सुरक्षा प्रणाली यही हो, सरल शब्दमा। |  |
| `ob_trust_tag_audited` | Audited build | अडिट गरिएको बिल्ड |  |
| `ob_trust_tag_reproducible` | Reproducible APK | पुनः बनाउन सकिने APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | तपाईं औंलाले खोल्दै हुनुहुन्छ। कुनै दिन औंलाले काम गर्न छोड्यो भने, यसैले तपाईंलाई भित्र ल्याउँछ — त्यसैले हेर्नु राम्रो। |  |
| `pc_confirm` | Check | जाँच्नुहोस् |  |
| `pc_correct` | Still correct. Nothing to do. | अझै सही छ। केही गर्नुपर्दैन। |  |
| `pc_forgot` | I cannot remember it | मलाई सम्झना आउँदैन |  |
| `pc_later` | Not now | अहिले होइन |  |
| `pc_reset_action` | Set new passphrase | नयाँ पासफ्रेज राख्नुहोस् |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | तपाईंको औंलाले यो भण्डार खोल्न सक्छ, त्यसैले त्यसैले नयाँ पासफ्रेज पनि राख्न सक्छ — रिकभरी किट चाहिँदैन। पक्का गर्न फेरि एकपटक सोधिनेछ। |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | पासफ्रेज बदलियो। छिटो अनलक फेरि मिलाइयो। |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | यो भएन। तपाईंको पुरानै पासफ्रेज चलिरहेको छ। |  |
| `pc_reset_title` | Set a new passphrase | नयाँ पासफ्रेज राख्नुहोस् |  |
| `pc_title` | Do you still remember your passphrase? | के तपाईंलाई अझै आफ्नो पासफ्रेज सम्झना छ? |  |
| `pc_wrong` | That is not it. You can set a new one instead. | यो त्यो होइन। यसको सट्टा नयाँ राख्न सक्नुहुन्छ। |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | यो प्रविष्टिका लागि सम्झिएका %1$d मान हटाइनेछन्। यो फर्काउन सकिँदैन। |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh ले त्यो लगइन सुरक्षित गर्न सकेन। |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | भर्न Zerokosh खोल्नुहोस् |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | यही एउटा पासफ्रेजले सबै थोक बन्द राख्छ। तपाईंले मात्र जान्ने लामो वाक्य छान्नुहोस्। |  |
| `scr_create_button` | Lock it in | बन्द गरिदिनुहोस् |  |
| `scr_create_confirm_hint` | Type it again | फेरि लेख्नुहोस् |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | पासफ्रेज (कम्तीमा 10 अक्षर) |  |
| `scr_create_mismatch` | The two entries don\'t match | दुवै उस्तै छैनन् |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 अंकको पिन |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | यसको सट्टा 6 अंकको पिन राख्नुहोस् |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | पिनका लागि औंला वा अनुहार अनलक भएको फोन चाहिन्छ। कृपया पासफ्रेज छान्नुहोस्। |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | पिन अनुमति दिइएको छ किनभने यो फोनले त्यसलाई आफ्नो सुरक्षा चिप र तपाईंको औंला वा अनुहारले जोगाउँछ। |  |
| `scr_create_strength_fair` | Fair | ठीकै |  |
| `scr_create_strength_good` | Good | राम्रो |  |
| `scr_create_strength_strong` | Strong | बलियो |  |
| `scr_create_strength_weak` | Weak | कमजोर |  |
| `scr_create_title` | Create your passphrase | आफ्नो पासफ्रेज बनाउनुहोस् |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | कम्तीमा 10 अक्षर चाहिन्छ — जति लामो, त्यति बलियो |  |
| `scr_create_working` | Preparing your vault… | तपाईंको भण्डार तयार हुँदैछ… |  |
| `scr_detail_delete` | Delete | हटाउनुहोस् |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | यो 30 दिन “भर्खरै हटाइएका”मा बस्नेछ, र तपाईंका अरू फोनसँग सिंक हुँदा जानेछ। |  |
| `scr_detail_delete_confirm_title` | Delete this record? | यो प्रविष्टि हटाउने? |  |
| `scr_detail_delete_confirm_yes` | Delete | हटाउनुहोस् |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | सिक्रेट वा otpauth:// लिंक टाँस्नुहोस् |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | आफ्नो औंला वा अनुहार प्रयोग गर्नुहोस् |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh खोल्नुहोस् |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | धेरै पटक गलत प्रयास भयो। %1$d सेकेन्ड पर्खनुहोस्। |  |
| `scr_lock_hint` | Passphrase | पासफ्रेज |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | यो रिकभरी साँचो ठीक छैन — हरेक अक्षर मिलाएर हेर्नुहोस् |  |
| `scr_lock_title` | Vault is locked | भण्डार बन्द छ |  |
| `scr_lock_unlock` | Unlock | खोल्नुहोस् |  |
| `scr_lock_use_passphrase` | Use passphrase | पासफ्रेज प्रयोग गर्नुहोस् |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | कृपया एकपटक पासफ्रेजले खोल्नुहोस् |  |
| `scr_lock_use_recovery` | Use Recovery Key | रिकभरी साँचो प्रयोग गर्नुहोस् |  |
| `scr_lock_wrong` | Wrong passphrase | पासफ्रेज गलत छ |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | कहिल्यै पासफ्रेज बिर्सनुभयो भने, भित्र फर्किने यही एउटै बाटो हो। हामीले त्यो रिसेट गर्न सक्दैनौं — कसैले सक्दैन। |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | मैले यो लेखेर सुरक्षित ठाउँमा राखेँ |  |
| `scr_recovery_done` | Continue | अगाडि बढ्नुहोस् |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | यो साँचो एकपटक मात्र देखिन्छ। तपाईं खोल्न सक्ने भएसम्म, सेटिङबाट जुनसुकै बेला नयाँ बनाउन सक्नुहुन्छ। |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | यो कागज आफ्नो जग्गाजमिनका कागजात वा अरू महत्त्वपूर्ण कागजातसँगै राख्नुहोस्। जससँग यो साँचो छ, उसले तपाईंको भण्डार खोल्न सक्छ — यसलाई लकरको चाबीजस्तै जोगाउनुहोस्। |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | रिकभरी किट PDF सुरक्षित भयो |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh रिकभरी किट |  |
| `scr_recovery_save_pdf` | Save as PDF | PDF रूपमा सुरक्षित गर्नुहोस् |  |
| `scr_recovery_title` | Your Recovery Key | तपाईंको रिकभरी साँचो |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | तपाईंले जे सुरक्षित गर्नुहुन्छ, त्यो तपाईंको फोनको एउटा बन्द फाइलमा बस्छ। त्यो कहिल्यै हामीकहाँ आउँदैन — त्यो राख्ने ठाउँ नै हामीसँग छैन। |  |
| `scr_trust_card1_title` | Your data stays on this device | तपाईंको डेटा यही फोनमै बस्छ |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | Zerokosh खाता छैन, क्लाउड छैन, साइन-अप छैन। यो तपाईंले मात्र खोल्न सक्नुहुन्छ। हामीले पनि सक्दैनौं। |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | हाम्रा सर्भर छैनन् — न ह्याक गर्न केही, न बेच्न |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | सदस्यता छैन, विज्ञापन छैन। जोसुकैले हाम्रो कोड पढेर हाम्रो हरेक कुरा जाँच्न सक्छ। |  |
| `scr_trust_card3_title` | Free forever, open source | सधैं नि:शुल्क, खुला स्रोत |  |
| `scr_trust_continue` | Continue | अगाडि बढ्नुहोस् |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | तपाईंका परिवर्तन सुरक्षित भएनन्, त्यसैले भण्डारमा पहिले जे थियो त्यसमध्ये केही हराएको छैन। |  |
| `st_recently_deleted` | Recently deleted | भर्खरै हटाइएका |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | एकपटकको कोड (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | एकपटकको कोड (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | एकपटकको कोड (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA सिक्रेट |  |
| `tr_cannot_undo` | This cannot be undone. | यो फर्काउन सकिँदैन। |  |
| `tr_delete_all` | Delete all permanently | सबै सधैंका लागि हटाउनुहोस् |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d प्रविष्टि सधैंका लागि जानेछन्। यो फर्काउन सकिँदैन, र फर्काउन कुनै ब्याकअप पनि छैन। |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d प्रविष्टि सधैंका लागि जानेछ। यो फर्काउन सकिँदैन, र फर्काउन कुनै ब्याकअप पनि छैन। |  |
| `tr_delete_all_title` | Delete everything in the trash? | रद्दीका सबै हटाउने? |  |
| `tr_delete_now` | Delete now | अहिल्यै हटाउनुहोस् |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | “%1$s” सधैंका लागि हटाउने? |  |
| `tr_empty` | Nothing deleted. | केही हटाइएको छैन। |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | हटाइएका प्रविष्टि यहाँ %1$d दिन बस्छन्। |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | भारतीय बैंक, UPI, कार्ड, डिम्याट, EPF, र तपाईंले साँच्चै प्रयोग गर्ने OTP एपहरूका लागि — फोनमै बस्ने भण्डार। |  |

## Priority 2 — longer prose

| key | English | Nepali | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | सबभन्दा बढी काम लाग्ने एउटै कुराबाट सुरु गर्नुहोस्। नोट्स एपमा रहेका बाह्रवटा पासवर्डभन्दा सुरक्षित गरिएको एउटा पासवर्ड बढी सुरक्षित हुन्छ। |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | पासफ्रेज बिर्सनुभयो भने भित्र आउने बाटो रिकभरी किट मात्र हो। तपाईंका लागि अर्को कसैले बनाउन सक्दैन। |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | त्यो फाइलमा चिन्न सकिने केही भेटिएन। Chrome, Google Password Manager, Bitwarden, LastPass र KeePass का निर्यात बुझिन्छन्। |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d वर्तमान प्रविष्टि बदलिनेछन् — साइट र युजरनेम मिलाएर। बदलिएका पासवर्ड हरेक प्रविष्टिको इतिहासमा भेटिनेछन्। |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d वर्तमान प्रविष्टि बदलिनेछ — साइट र युजरनेम मिलाएर। बदलिएका पासवर्ड हरेक प्रविष्टिको इतिहासमा भेटिनेछन्। |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d प्रविष्टि दुवैतिर बदलिएका थिए। दुवै रूप सुरक्षित गरियो — “(conflict copy)” खोज्नुहोस्। |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | यो ब्याकअप फाइल खोल्ने पासफ्रेज हाल्नुहोस्। त्यो तपाईंको अहिलेकोभन्दा फरक हुन सक्छ। |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | .kosh फाइलको Poly1305 प्रमाणीकरण ट्याग मिल्दैन। बीचमै रोकिएको सिंक वा बिग्रिएको स्टोरेजपछि यस्तो हुन सक्छ। |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh ले भण्डार फाइलको छेउमै एउटा चलिरहेको ब्याकअप राख्छ। त्यो आफ्नो सिंक फोल्डरबाट फर्काउनुहोस्, वा अर्को फोनमा रिकभरी किटले भण्डार खोल्नुहोस्। |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | नपढेसम्म कार्ड फोनको पछाडि सिधै टाँसेर राख्नुहोस्। यसबाट कार्ड नम्बर, म्याद र नाम पाइन्छ — CVV चिपमा हुँदैन, त्यो तपाईंले आफैं लेख्नुपर्छ। |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | भित्र फर्किने छिटो बाटो छान्नुहोस्। भण्डारको रक्षा पासफ्रेजले नै गर्छ; यसले यही फोनमा मात्र साँचो खोल्छ। |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | अब तपाईंको भण्डारमा साँचो जानकारी छ। रिकभरी किट बिना पासफ्रेज बिर्सनुभयो भने कसैले तपाईंलाई फर्काउन सक्दैन — हामीले पनि होइन। |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh नि:शुल्क र खुला स्रोत हो, र यसका कुनै सर्भर छैनन्। तपाईंको भण्डार तपाईंले मात्र खोल्न सक्नुहुन्छ। हामीले पनि सक्दैनौं। |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | क्यामेराको अनुमति QR कोड स्क्यान गर्न मात्र चाहिन्छ। प्रविष्टि थप्दा सिक्रेट हातले पनि टाँस्न सकिन्छ। |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | अटोफिल नदिने बैंक एपहरूका लागि — बटन थिचेर लगइन विवरण एक-एक गरी प्रतिलिपि गर्नुहोस् |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | तपाईंले फोन खोलेजस्तै भण्डार पनि। पासफ्रेजले सधैं काम गरिरहनेछ। |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | भण्डारका स्क्रिनसट क्लाउड फोटो ब्याकअपमा पुग्न सक्छन्। धेरै आवश्यक भएमा मात्र चालू गर्नुहोस्। |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | भण्डार फाइल लेख्न सकिएन। तपाईंले ब्याकअप र सिंक फोल्डर मिलाउनुभएको छ भने, Android ले त्यसको अनुमति फिर्ता लिएको हुन सक्छ — सेटिङ खोल्नुहोस्, फोल्डर फेरि छान्नुहोस्, र फेरि प्रयास गर्नुहोस्। |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | तपाईंका इन्क्रिप्ट गरिएका .kosh फाइल सिधै यही फोल्डरमा सुरक्षित हुन्छन्। धेरै उपकरणमा आफैं ब्याकअप हुन यो फोल्डरलाई Google Drive, Syncthing, Nextcloud वा SD कार्डसँग सिंक गर्नुहोस्। |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | नयाँ रिकभरी साँचो बनाउन आफ्नो पासफ्रेज हाल्नुहोस्। पुरानो साँचोले काम गर्न छोड्नेछ। |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | यो पानाका अरू सबैले एउटा लगइन कमजोर बनाउँछन्। यसले पूरै भण्डार लैजान सक्छ। सेटिङ → नयाँ रिकभरी साँचो लिनुहोस्। |  |

## Priority 3 — short labels

| key | English | Nepali | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | बलियो पासवर्ड प्रयोग गर्नुहोस् |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | खाताको नाम (जस्तै Google) |  |
| `au_active_many` | %1$d active codes | %1$d सक्रिय कोड |  |
| `au_active_one` | %1$d active code | %1$d सक्रिय कोड |  |
| `au_add_another` | Add another authenticator | अर्को प्रमाणक थप्नुहोस् |  |
| `au_add_secret` | Add Secret Key | सिक्रेट साँचो थप्नुहोस् |  |
| `au_camera_needed` | Camera permission needed to scan QR code | QR कोड स्क्यान गर्न क्यामेराको अनुमति चाहिन्छ |  |
| `au_copied` | Copied · clears shortly | प्रतिलिपि भयो · केही बेरमा मेटिनेछ |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google, GitHub वा आफ्नो ब्रोकरको QR स्क्यान गर्नुहोस्, वा सिक्रेट साँचो हातले लेख्नुहोस्। |  |
| `au_enter_key` | Enter Key | साँचो लेख्नुहोस् |  |
| `au_fallback_name` | Authenticator | प्रमाणक |  |
| `au_flashlight` | Flashlight | टर्च |  |
| `au_grant` | Grant Permission | अनुमति दिनुहोस् |  |
| `au_image_failed` | Failed to process image | तस्बिर पढ्न सकिएन |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | गलत Base32 सिक्रेट साँचो (केवल A-Z अक्षर र 2-7 अंक) |  |
| `au_no_match` | No codes match | कुनै कोड भेटिएन |  |
| `au_none_yet` | No codes yet. | अहिलेसम्म कुनै कोड छैन। |  |
| `au_pick_image` | Pick Image | तस्बिर छान्नुहोस् |  |
| `au_rotating` | "Rotating " | "बदलिरहने " |  |
| `au_rotating_emph` | codes. | कोड। |  |
| `au_save_key` | Save Key | साँचो सुरक्षित गर्नुहोस् |  |
| `au_scan_qr` | Scan a QR code | QR कोड स्क्यान गर्नुहोस् |  |
| `au_scan_title` | Scan Authenticator QR | प्रमाणकको QR स्क्यान गर्नुहोस् |  |
| `au_search_hint` | Search codes, issuers… | कोड वा दिने खोज्नुहोस्… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | जस्तै JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | सिक्रेट साँचो (Base32) |  |
| `au_tap_to_copy` | Tap to copy | प्रतिलिपि गर्न ट्याप गर्नुहोस् |  |
| `cat_apps` | Apps &amp; Logins | एप र लगइन |  |
| `cat_banks` | Banks &amp; UPI | बैंक र UPI |  |
| `cat_cards` | Cards | कार्ड |  |
| `cat_govid` | Gov &amp; ID | सरकारी र परिचय |  |
| `cat_investments` | Investments | लगानी |  |
| `cat_utilities` | Utilities | बिल र जडान |  |
| `cd_mask_hidden` | hidden | लुकेको |  |
| `cd_shield_high_sensitivity` | extra-protected field | बढी संवेदनशील जानकारी |  |
| `gl_blank` | Blank template | खाली टेम्प्लेट |  |
| `gl_cat_apps` | Apps | एप |  |
| `gl_cat_banks` | Banks | बैंक |  |
| `gl_cat_cards` | Cards | कार्ड |  |
| `gl_cat_demat` | Demat | डिम्याट |  |
| `gl_cat_govid` | Gov ID | सरकारी परिचय |  |
| `gl_cat_popular` | Popular | लोकप्रिय |  |
| `gl_cat_shopping` | Shopping | किनमेल |  |
| `gl_cat_travel` | Travel | यात्रा |  |
| `gl_cat_upi` | UPI | UPI |  |
| `gl_cat_utilities` | Utilities | बिल |  |
| `gl_head_emph` | storing? | सुरक्षित गर्दैछौं? |  |
| `gl_head_lead` | "What are we " | "हामी के " |  |
| `gl_matches` | %1$d matches | %1$d भेटिए |  |
| `gl_most_used` | Most-used first | सबभन्दा बढी प्रयोग हुनेहरू पहिले |  |
| `gl_not_found` | Can’t find a service? | सेवा भेटिएन? |  |
| `gl_search` | Search %1$d Indian services… | %1$d भारतीय सेवामा खोज्नुहोस्… |  |
| `gl_suggested` | Suggested for you | तपाईंका लागि सुझाव |  |
| `hm_add_first` | Add your first record | आफ्नो पहिलो प्रविष्टि थप्नुहोस् |  |
| `hm_all_offline` | all offline. | सबै अफलाइन। |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d कुरा, " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d कुरा, " |  |
| `hm_detail_placeholder` | Pick a record to see it here | यहाँ हेर्न एउटा रेकर्ड छान्नुहोस् |  |
| `hm_empty_blank` | A blank vault, ready. | खाली भण्डार, तयार। |  |
| `hm_empty_head_emph` | waiting. | पर्खिरहेको छ। |  |
| `hm_empty_head_lead` | "Your vault is " | "तपाईंको भण्डार " |  |
| `hm_filter_all` | All | सबै |  |
| `hm_import_backup` | Import an encrypted backup | इन्क्रिप्ट गरिएको ब्याकअप आयात |  |
| `hm_import_backup_note` | Open a .kosh file from this device | यही फोनबाट .kosh फाइल खोल्नुहोस् |  |
| `hm_inst_many` | %1$d institutions | %1$d संस्था |  |
| `hm_inst_one` | %1$d institution | %1$d संस्था |  |
| `hm_kit_banner_action` | Save one now | अहिल्यै सुरक्षित गर्नुहोस् |  |
| `hm_kit_banner_dismiss` | Remind me later | पछि सम्झाउनुहोस् |  |
| `hm_kit_banner_title` | No recovery kit saved | कुनै रिकभरी किट सुरक्षित छैन |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | खुला छ · छाड्नेबित्तिकै बन्द हुनेछ |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | खुला छ · छाडेको %1$d मिनेटपछि बन्द |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | खुला छ · छाडेको 1 मिनेटपछि बन्द |  |
| `hm_no_match` | Nothing matches “%1$s” | “%1$s” का लागि केही भेटिएन |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | कुनै संस्था, UPI ह्यान्डल, वा अन्तिम चार अंक प्रयास गर्नुहोस्। |  |
| `hm_pinned` | Pinned | पिन गरिएका |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC, priya@upi, PAN… खोज्नुहोस् |  |
| `hm_start_template` | Start with a template | टेम्प्लेटबाट सुरु गर्नुहोस् |  |
| `ic_could_not` | Could not import | आयात गर्न सकिएन |  |
| `ic_done` | Done | भयो |  |
| `ic_import` | Import | आयात |  |
| `ic_imported` | Imported | आयात भयो |  |
| `ic_importing` | Importing… | आयात हुँदै… |  |
| `ic_new_many` | %1$d new logins. | %1$d नयाँ लगइन। |  |
| `ic_new_one` | %1$d new login. | %1$d नयाँ लगइन। |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d थपिए, %2$d बदलिए। |  |
| `ic_title` | Import from %1$s? | %1$s बाट आयात गर्ने? |  |
| `ic_too_large` | That file is too large to be a credential export. | यो फाइल पासवर्ड निर्यात हुनका लागि धेरै ठूलो छ। |  |
| `import_action` | Import | आयात |  |
| `import_locked` | Unlock your vault before importing. | आयात गर्नुअघि आफ्नो भण्डार खोल्नुहोस्। |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d थपिए, %2$d बदलिए। केही मेटिएको छैन। |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | त्यो फाइल Zerokosh भण्डारका रूपमा पढ्न सकिएन। |  |
| `import_nothing_new` | Everything in that backup was already here. | त्यो ब्याकअपमा जे थियो, त्यो सबै पहिल्यै यहाँ थियो। |  |
| `import_passphrase_label` | Backup passphrase | ब्याकअपको पासफ्रेज |  |
| `import_title` | Import a backup | ब्याकअप आयात गर्नुहोस् |  |
| `kicker_locked` | Locked | बन्द छ |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · केही पनि यो फोनबाट बाहिर गएको छैन |  |
| `lk_touch_unlock` | Touch to unlock | खोल्न छुनुहोस् |  |
| `lk_welcome_emph` | Your vault is sealed. | तपाईंको भण्डार बन्द छ। |  |
| `lk_welcome_lead` | Welcome back. | फेरि स्वागत छ। |  |
| `msg_auth_needed` | Confirm it\'s you to see this | हेर्न पुष्टि गर्नुहोस् कि यो तपाईं नै हो |  |
| `msg_back` | Back | पछाडि |  |
| `msg_cancel` | Cancel | रद्द |  |
| `msg_file_damaged` | File damaged — restored from backup | फाइल बिग्रिएको थियो — ब्याकअपबाट मिलाइयो |  |
| `msg_ok` | OK | ठीक छ |  |
| `msg_saved` | Saved | सुरक्षित भयो |  |
| `nav_all_templates` | All templates | सबै टेम्प्लेट |  |
| `nav_damaged_emph` | vault file | भण्डार फाइलमा |  |
| `nav_damaged_kicker` | Damaged state | बिग्रिएको अवस्था |  |
| `nav_damaged_lead` | "Something in the " | "तपाईंको " |  |
| `nav_damaged_tail` | " is off." | " केही गडबडी छ।" |  |
| `nav_integrity_title` | Integrity check failed | अखण्डता जाँच असफल भयो |  |
| `nav_scan` | Scan | स्क्यान |  |
| `nav_tap_card` | Tap a card | कार्ड ट्याप गर्नुहोस् |  |
| `nav_what_next` | What to do next | अब के गर्ने |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC बन्द छ। सेटिङमा चालू गरेर फेरि प्रयास गर्नुहोस्। |  |
| `nfc_hold_card` | Hold your card to the phone | कार्ड फोनमा टाँसेर राख्नुहोस् |  |
| `nfc_missed` | Did not catch that | समात्न सकिएन |  |
| `nfc_read_failed` | That card could not be read. Try again. | त्यो कार्ड पढ्न सकिएन। फेरि प्रयास गर्नुहोस्। |  |
| `nfc_reading` | Reading… | पढ्दै… |  |
| `nfc_try_again` | Try again | फेरि प्रयास गर्नुहोस् |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · यही फोनमा नापिएको |  |
| `ob_argon_faster` | Faster unlock | छिटो खुल्नेछ |  |
| `ob_argon_harder` | Harder to attack | फुटाउन गाह्रो |  |
| `ob_argon_measuring` | Measuring this device… | यो फोन नाप्दै… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id कठोरता |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | शब्दकोशको एउटा शब्द पनि होइन |  |
| `ob_check_pass_length` | 10 characters or more | 10 वा बढी अक्षर |  |
| `ob_check_pass_reuse` | Not reused from another app | अर्को एपबाट पुनः प्रयोग गरिएको होइन |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | न जन्मदिन, न वार्षिकोत्सव |  |
| `ob_check_pin_digits` | All six digits entered | छवटै अंक भरिए |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | न लगातार अंक, न दोहोरिएका |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~फुटाउन %1$d शताब्दी |  |
| `ob_crack_days` | ~%1$d days to crack | ~फुटाउन %1$d दिन |  |
| `ob_crack_forever` | longer than the sun | सूर्यभन्दा पनि लामो समय |  |
| `ob_crack_hours` | ~hours to crack | ~फुटाउन केही घण्टा |  |
| `ob_crack_seconds` | ~seconds to crack | ~फुटाउन केही सेकेन्ड |  |
| `ob_crack_years` | ~%1$d years to crack | ~फुटाउन %1$d वर्ष |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | प्रमाणित, हरेक भण्डारका लागि छुट्टै नन्स |  |
| `ob_fact_encryption_title` | Encryption | इन्क्रिप्सन |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | सेटअपका बेला तपाईंको फोनमा नापिएको |  |
| `ob_fact_kdf_title` | Key stretching | साँचो स्ट्रेचिङ |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | रिसेट लिंक छैन। सपोर्टको पछिल्लो ढोका पनि छैन। |  |
| `ob_fact_lost_value` | Nobody can recover it | कसैले फर्काउन सक्दैन |  |
| `ob_fact_network_title` | Network permission | नेटवर्क अनुमति |  |
| `ob_fact_network_value` | Not requested | कहिल्यै मागिएको छैन |  |
| `ob_fact_quick_title` | Quick unlock | छिटो अनलक |  |
| `ob_fact_quick_value` | Hardware keystore | हार्डवेयर किस्टोर |  |
| `ob_lang_continue` | Continue in %1$s | %1$s मा अगाडि बढ्नुहोस् |  |
| `ob_lang_head_emph` | language. | भाषा छान्नुहोस्। |  |
| `ob_lang_head_lead` | "Choose your " | "आफ्नो " |  |
| `ob_lang_search` | Search %1$d languages | %1$d भाषामा खोज्नुहोस् |  |
| `ob_quick_continue_pass` | Continue with passphrase | पासफ्रेजसँगै अगाडि बढ्नुहोस् |  |
| `ob_quick_enable` | Enable quick unlock | छिटो अनलक चालू गर्नुहोस् |  |
| `ob_quick_fingerprint` | Fingerprint | औंलाको छाप |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | छिटो, हार्डवेयरले सुरक्षित अनलक। |  |
| `ob_quick_head_emph` | Without the cloud. | क्लाउडबिना। |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "एउटै छुवाइमा खुल्छ। " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox। कुनै बायोमेट्रिक जानकारी कहिल्यै Zerokosh सम्म पुग्दैन। |  |
| `ob_quick_hw_title` | Hardware-backed | हार्डवेयरले सुरक्षित |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | यो फोनमा हार्डवेयर सेन्सर छैन। |  |
| `ob_quick_opening` | Opening your vault… | तपाईंको भण्डार खुल्दैछ… |  |
| `ob_quick_pass_only` | Passphrase only | पासफ्रेज मात्र |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | हरेकपटक लेख्नुहोस्। सबभन्दा सुरक्षित। |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | छिटो अनलक मिलेन। फेरि प्रयास गर्नुहोस्, वा पासफ्रेजसँगै अगाडि बढ्नुहोस्। |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | अहिले होइन — म पासफ्रेज लेख्छु |  |
| `ob_quick_touch_title` | Touch the sensor | सेन्सरमा छुनुहोस् |  |
| `ob_recommended` | Recommended | सिफारिस |  |
| `ob_reveal_hide` | Hide | लुकाउनुहोस् |  |
| `ob_reveal_show` | Show | देखाउनुहोस् |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | बेर्ने साँचो हार्डवेयर किस्टोरमा बस्नेछ। बायोमेट्रिक अर्को चरणमा। |  |
| `ob_seal_title` | Seal to this device | यही फोनसँग बाँध्नुहोस् |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | यो फोनमा हार्डवेयर बायोमेट्रिक छैन। |  |
| `ob_soon` | SOON | चाँडै |  |
| `ob_step_label` | Step %1$d of 6 | चरण %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | यस्तो केही छान्नुहोस् जुन तपाईंले मात्र भन्नुहुन्छ |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | राम्रो · %1$d बिट एन्ट्रोपी |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | धेरै छोटो · 10 अक्षर चाहिन्छ |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | बलियो · %1$d बिट एन्ट्रोपी |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | कमजोर · %1$d बिट एन्ट्रोपी |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | छ अंक जुन तपाईंको जीवन हेरेर कसैले अनुमान गर्न नसकोस् |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | ठीकै · %1$d बिट — पिन यसभन्दा बलियो हुन सक्दैन |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | धेरै छोटो · 6 अंक चाहिन्छ |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | कमजोर · यिनै पिन सबभन्दा पहिले प्रयास गरिन्छन् |  |
| `ob_try_label` | TRY | प्रयास गर्नुहोस् |  |
| `qa_aadhaar` | Aadhaar | आधार |  |
| `qa_bank_account` | Bank account | बैंक खाता |  |
| `qa_pan` | PAN | PAN |  |
| `qa_upi_id` | UPI ID | UPI ID |  |
| `rd_copied` | Copied | प्रतिलिपि भयो |  |
| `rd_forget` | Forget | बिर्सनुहोस् |  |
| `rd_forget_these` | Forget these | यी बिर्सनुहोस् |  |
| `rd_forget_title` | Forget previous passwords? | पुराना पासवर्ड बिर्सने? |  |
| `rd_history_hide` | Hide | लुकाउनुहोस् |  |
| `rd_history_show` | Show %1$d | %1$d देखाउनुहोस् |  |
| `rd_hold_to_reveal` | Hold to reveal | हेर्न थिचिराख्नुहोस् |  |
| `rd_last_edit` | last edit %1$s | अन्तिमपटक %1$s बदलियो |  |
| `rd_release_to_hide` | Release to hide | लुकाउन छाड्नुहोस् |  |
| `re_add_field` | + Add another field | + अर्को खाना थप्नुहोस् |  |
| `re_add_field_title` | Add a field | खाना थप्नुहोस् |  |
| `re_field_name` | Field name | खानाको नाम |  |
| `re_pick_date` | Pick a date | मिति छान्नुहोस् |  |
| `re_remove` | Remove | हटाउनुहोस् |  |
| `re_tap_card` | Read the card by tapping it | पढ्न कार्ड ट्याप गर्नुहोस् |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | गोप्य मान्नुहोस् (लुकेको रहनेछ, हेर्न थिचिराख्नुहोस्) |  |
| `re_using_template` | using the %1$s template | %1$s टेम्प्लेट प्रयोग गर्दै |  |
| `rem_kit_title` | No recovery kit saved | कुनै रिकभरी किट सुरक्षित छैन |  |
| `scr_about_license` | License: GPL-3.0 — free forever | इजाजतपत्र: GPL-3.0 — सधैं नि:शुल्क |  |
| `scr_about_source` | Source code | स्रोत कोड |  |
| `scr_about_version` | Version %1$s | संस्करण %1$s |  |
| `scr_auth_add_qr` | Add via QR | QR ले थप्नुहोस् |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | तपाईंका एप र ब्रोकरका कोड यहाँ देखिनेछन् |  |
| `scr_auth_scan_title` | Point the camera at the QR code | क्यामेरा QR कोडमाथि राख्नुहोस् |  |
| `scr_detail_copied` | Copied · clears in 30s | प्रतिलिपि भयो · 30 सेकेन्डमा मेटिनेछ |  |
| `scr_detail_copy` | Copy | प्रतिलिपि गर्नुहोस् |  |
| `scr_detail_edit` | Edit | बदल्नुहोस् |  |
| `scr_detail_favorite` | Favourite | मनपर्ने |  |
| `scr_detail_hidden` | Hidden | लुकेको |  |
| `scr_detail_hide` | Hide | लुकाउनुहोस् |  |
| `scr_detail_history_empty` | Nothing replaced yet. | अहिलेसम्म केही बदलिएको छैन। |  |
| `scr_detail_history_title` | Previous passwords | पुराना पासवर्ड |  |
| `scr_detail_reveal` | Show | देखाउनुहोस् |  |
| `scr_detail_shown` | Shown | देखिएको |  |
| `scr_edit_cancel` | Cancel | रद्द |  |
| `scr_edit_generate` | Generate | बनाउनुहोस् |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | बैंक / कम्पनी (सँगै राख्न) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | यो ठीक लागेन — एकपटक हेर्नुहोस् |  |
| `scr_edit_link_none` | None | केही छैन |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | यो कार्ड नम्बर सामान्य जाँचमा पास हुँदैन — ठीक छ भने सुरक्षित गर्नुहोस् |  |
| `scr_edit_month` | Month | महिना |  |
| `scr_edit_picker_other` | Other… | अन्य… |  |
| `scr_edit_picker_other_hint` | Type your own | आफ्नो लेख्नुहोस् |  |
| `scr_edit_required_title` | Give it a name first | पहिले यसलाई एउटा नाम दिनुहोस् |  |
| `scr_edit_save` | Save | सुरक्षित गर्नुहोस् |  |
| `scr_edit_title_hint` | Title | नाम |  |
| `scr_edit_title_new` | New | नयाँ |  |
| `scr_edit_year` | Year | वर्ष |  |
| `scr_gallery_quick_add` | Quick add | छिटो थप्नुहोस् |  |
| `scr_gallery_title` | What do you want to save? | हामी के सुरक्षित गर्दैछौं? |  |
| `scr_home_add` | Add | थप्नुहोस् |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | तपाईंको बैंक खाता यस्तो देखिनेछ |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | तपाईंका कार्ड, UPI, एप लगइन — सबै यहीं |  |
| `scr_home_group_other` | Other | अन्य |  |
| `scr_home_no_results` | Nothing matches your search | तपाईंको खोजीले केही भेट्टाएन |  |
| `scr_home_search_hint` | Search your vault | आफ्नो भण्डारमा खोज्नुहोस् |  |
| `scr_home_tab_authenticator` | Authenticator | कोड |  |
| `scr_home_tab_home` | Home | गृह |  |
| `scr_home_tab_settings` | Settings | सेटिङ |  |
| `scr_home_title` | Home | गृह |  |
| `scr_language_continue` | Continue | अगाडि बढ्नुहोस् |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | आफ्नो भाषा छान्नुहोस् |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | प्रतिलिपि गर्न बटन थिच्नुहोस् · 30 सेकेन्डमा मेटिनेछ |  |
| `scr_login_helper_channel` | Login helper | लगइन सहयोगी |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s मा लगइन गर्दै |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | अहिल्यै ब्याकअप फोल्डर मिलाउनुहोस् |  |
| `scr_quickunlock_enable` | Turn on | चालू गर्नुहोस् |  |
| `scr_quickunlock_skip` | Not now | अहिले होइन |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | औंला वा अनुहारले खोल्नुहोस् |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s को मिति नजिकिँदैछ · Zerokosh खोल्नुहोस् |  |
| `scr_reminder_channel` | Renewal reminders | नवीकरण सम्झना |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh ले सम्झाउँदैछ |  |
| `scr_settings_about` | About | बारेमा |  |
| `scr_settings_allow_screenshots` | Allow screenshots | स्क्रिनसट लिन दिनुहोस् |  |
| `scr_settings_autofill` | Autofill service | अटोफिल सेवा |  |
| `scr_settings_autofill_off` | Not set up | मिलाइएको छैन |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | उपलब्ध छैन |  |
| `scr_settings_autolock` | Lock when I leave the app | एप छाड्नेबित्तिकै बन्द गर्नुहोस् |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 मिनेटपछि |  |
| `scr_settings_autolock_immediately` | Immediately | तुरुन्तै |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d मिनेटपछि |  |
| `scr_settings_change_passphrase` | Change passphrase | पासफ्रेज बदल्नुहोस् |  |
| `scr_settings_current_passphrase` | Current passphrase | अहिलेको पासफ्रेज |  |
| `scr_settings_export` | Export | निर्यात गर्नुहोस् |  |
| `scr_settings_import` | Import passwords | पासवर्ड आयात गर्नुहोस् |  |
| `scr_settings_language` | Language | भाषा |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | नयाँ पासफ्रेज (कम्तीमा 10 अक्षर) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | नयाँ रिकभरी साँचो लिनुहोस् |  |
| `scr_settings_passphrase_changed` | Passphrase changed | पासफ्रेज बदलियो |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | औंला / अनुहार अनलक |  |
| `scr_settings_security_info` | How your data is protected | तपाईंको डेटा कसरी सुरक्षित छ |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | ब्याकअप र सिंक फोल्डर |  |
| `scr_settings_sync_not_set` | Not backed up | ब्याकअप छैन |  |
| `scr_settings_title` | Settings | सेटिङ |  |
| `se_title` | Not saved | सुरक्षित भएन |  |
| `st_active_folder` | Active Folder | सक्रिय फोल्डर |  |
| `st_active_value` | Active · %1$s | सक्रिय · %1$s |  |
| `st_backing_up` | Backing up vault… | भण्डारको ब्याकअप लिँदै… |  |
| `st_backup_now` | Backup Now | अहिल्यै ब्याकअप लिनुहोस् |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | ब्याकअप र सिंक फोल्डर |  |
| `st_change_folder` | Change Folder | फोल्डर बदल्नुहोस् |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | नयाँ पासफ्रेज फेरि लेख्नुहोस् |  |
| `st_connected_folder` | Connected folder: %1$s | जोडिएको फोल्डर: %1$s |  |
| `st_disconnect` | Disconnect | हटाउनुहोस् |  |
| `st_done` | Done | भयो |  |
| `st_export_kosh` | Export encrypted .kosh | इन्क्रिप्ट गरिएको .kosh निर्यात |  |
| `st_folder_fallback` | Folder | फोल्डर |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | पासफ्रेज बिर्सनुभयो? |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | आफ्नो औंलाले नयाँ राख्नुहोस् |  |
| `st_generate` | Generate | बनाउनुहोस् |  |
| `st_group_about` | About | बारेमा |  |
| `st_group_appearance` | Appearance | रूप |  |
| `st_group_security` | Security | सुरक्षा |  |
| `st_group_sync` | Sync | सिंक |  |
| `st_import_kosh` | Import a .kosh backup | .kosh ब्याकअप आयात |  |
| `st_import_other` | Import from another password manager | अर्को पासवर्ड म्यानेजरबाट आयात |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | इजाजतपत्र |  |
| `st_logos_by` | Logos provided by | लोगो दिने |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | यसलाई अफलाइन राख्नुहोस्। पुरानो रिकभरी साँचो अब मान्य छैन। |  |
| `st_new_recovery_result` | Your new Recovery Key: | तपाईंको नयाँ रिकभरी साँचो: |  |
| `st_subtitle` | Your rules. | तपाईंका नियम। |  |
| `st_theme` | Theme | थिम |  |
| `st_theme_dark` | Dark | अँध्यारो |  |
| `st_theme_light` | Light | उज्यालो |  |
| `st_theme_system` | System | प्रणाली |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | भण्डार %1$s मा सुरक्षित भयो! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | ब्याकअप भएन — फोल्डरको अनुमति हेर्नुहोस् |  |
| `st_toast_disconnected` | Backup folder disconnected | ब्याकअप फोल्डर हटाइयो |  |
| `st_toast_export_failed` | Export failed | निर्यात भएन |  |
| `st_toast_exported` | Encrypted vault exported | इन्क्रिप्ट गरिएको भण्डार निर्यात भयो |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | ब्याकअप फोल्डर जोडियो, र भण्डार %1$s मा सुरक्षित भयो! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | ब्याकअप फोल्डर जोडियो: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | फोल्डर जोड्न सकिएन: %1$s |  |
| `st_vault_review` | Vault review | भण्डारको जाँच |  |
| `st_vault_review_detail` | Reused, weak, expiring | पुनः प्रयोग गरिएका, कमजोर, म्याद सकिँदै |  |
| `tab_codes` | Codes | कोड |  |
| `tab_settings` | Settings | सेटिङ |  |
| `tab_templates` | Templates | टेम्प्लेट |  |
| `tab_vault` | Vault | भण्डार |  |
| `time_days` | %1$dd ago | %1$d दिन अघि |  |
| `time_hours` | %1$dh ago | %1$d घण्टा अघि |  |
| `time_just_now` | just now | भर्खरै |  |
| `time_minutes` | %1$dm ago | %1$d मिनेट अघि |  |
| `time_months` | %1$dmo ago | %1$d महिना अघि |  |
| `time_years` | %1$dy ago | %1$d वर्ष अघि |  |
| `tpl_aadhaar_card` | Aadhaar Card | आधार |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | आधार नम्बर |  |
| `tpl_aadhaar_card_address` | Address | आधारमा ठेगाना |  |
| `tpl_aadhaar_card_dob` | Dob | जन्म मिति |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | स्क्यान गरिएको प्रति |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | जोडिएको मोबाइल |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar पासकोड |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | आधारमा नाम |  |
| `tpl_aadhaar_card_notes` | Notes | टिपोट |  |
| `tpl_app_profile` | App Profile | एप प्रोफाइल |  |
| `tpl_app_profile_app_name` | App name | एपको नाम |  |
| `tpl_app_profile_gift_cards` | Gift cards | उपहार कार्ड |  |
| `tpl_app_profile_membership` | Membership | सदस्यता |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | सदस्यता नवीकरण |  |
| `tpl_app_profile_notes` | Notes | टिपोट |  |
| `tpl_app_profile_password_if_any` | Password (if any) | पासवर्ड (भए) |  |
| `tpl_app_profile_registered_email` | Registered email | दर्ता इमेल |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | दर्ता मोबाइल |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | वालेट पिन |  |
| `tpl_bank_account` | Bank Account | बैंक खाता |  |
| `tpl_bank_account_account_number` | Account number | खाता नम्बर |  |
| `tpl_bank_account_account_type` | Account type | खाताको किसिम |  |
| `tpl_bank_account_bank_name` | Bank name | बैंकको नाम |  |
| `tpl_bank_account_branch` | Branch | शाखा |  |
| `tpl_bank_account_customer_id` | Customer id | ग्राहक ID |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC कोड |  |
| `tpl_bank_account_login_password` | Login password | लगइन पासवर्ड |  |
| `tpl_bank_account_micr` | MICR code | MICR कोड |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | नेट-बैंकिङ युजर ID |  |
| `tpl_bank_account_nominee` | Nominee | इच्छाइएको व्यक्ति |  |
| `tpl_bank_account_notes` | Notes | टिपोट |  |
| `tpl_bank_account_profile_password` | Profile password | प्रोफाइल पासवर्ड |  |
| `tpl_bank_account_registered_email` | Registered email | दर्ता इमेल |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | दर्ता मोबाइल |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | कारोबार पासवर्ड |  |
| `tpl_card` | Card | कार्ड |  |
| `tpl_card_atm_pin` | ATM PIN | ATM पिन |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | बिलिङ चक्रको दिन |  |
| `tpl_card_card_network` | Card network | नेटवर्क |  |
| `tpl_card_card_number` | Card number | कार्ड नम्बर |  |
| `tpl_card_card_portal_login` | Card portal login | कार्ड पोर्टल लगइन |  |
| `tpl_card_card_portal_password` | Card portal password | कार्ड पोर्टल पासवर्ड |  |
| `tpl_card_card_type` | Card type | कार्डको किसिम |  |
| `tpl_card_card_variant` | Card variant | कार्ड भेरियन्ट |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | म्याद |  |
| `tpl_card_linked_account` | Linked account | जोडिएको खाता |  |
| `tpl_card_name_on_card` | Name on card | कार्डमा नाम |  |
| `tpl_card_notes` | Notes | टिपोट |  |
| `tpl_demat` | Demat | डिम्याट |  |
| `tpl_demat_api_key` | API key | API साँचो |  |
| `tpl_demat_api_secret` | API secret | API सिक्रेट |  |
| `tpl_demat_bo_id` | BO ID | BO ID |  |
| `tpl_demat_broker` | Broker | ब्रोकर |  |
| `tpl_demat_cdsl_tpin` | CDSL TPIN | CDSL TPIN |  |
| `tpl_demat_client_id` | Client id | क्लाइन्ट ID |  |
| `tpl_demat_depository` | Depository | डिपोजिटरी |  |
| `tpl_demat_dp_id` | DP ID | DP ID |  |
| `tpl_demat_login_password` | Login password | लगइन पासवर्ड |  |
| `tpl_demat_mf_folios` | Mutual fund folios | म्युचुअल फन्ड फोलियो |  |
| `tpl_demat_mpin` | MPIN | MPIN |  |
| `tpl_demat_nominee` | Nominee | इच्छाइएको व्यक्ति |  |
| `tpl_demat_notes` | Notes | टिपोट |  |
| `tpl_digilocker` | Digilocker | DigiLocker |  |
| `tpl_digilocker_login_username` | Login username | युजरनेम |  |
| `tpl_digilocker_notes` | Notes | टिपोट |  |
| `tpl_digilocker_portal_password` | Portal password | पासवर्ड |  |
| `tpl_digilocker_security_pin` | Security pin | सुरक्षा पिन |  |
| `tpl_driving_license` | Driving License | सवारी चालक अनुमतिपत्र |  |
| `tpl_driving_license_dl_number` | Dl number | अनुमतिपत्र नम्बर |  |
| `tpl_driving_license_dob` | Dob | जन्म मिति |  |
| `tpl_driving_license_expiry_date` | Expiry date | यो मितिसम्म मान्य |  |
| `tpl_driving_license_file_copy` | Scanned copy | स्क्यान गरिएको प्रति |  |
| `tpl_driving_license_issue_date` | Issue date | जारी मिति |  |
| `tpl_driving_license_name_on_dl` | Name on dl | अनुमतिपत्रमा नाम |  |
| `tpl_driving_license_notes` | Notes | टिपोट |  |
| `tpl_driving_license_rto_location` | Rto location | RTO |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | सवारीका वर्ग |  |
| `tpl_epf_pension` | Epf Pension | EPF / पेन्सन |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | जोडिएको मोबाइल |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | EPF मा नाम |  |
| `tpl_epf_pension_nominee` | Nominee | इच्छाइएको व्यक्ति |  |
| `tpl_epf_pension_notes` | Notes | टिपोट |  |
| `tpl_epf_pension_password` | Password | पासवर्ड |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | PF सदस्य ID |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO पासवर्ड |  |
| `tpl_epf_pension_scheme` | Scheme | योजना |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | सरकारी परिचयपत्र |  |
| `tpl_gov_id_expiry` | Expiry | म्याद |  |
| `tpl_gov_id_file_copy` | Scanned copy | स्क्यान गरिएको प्रति |  |
| `tpl_gov_id_id_kind` | ID type | परिचयपत्रको किसिम |  |
| `tpl_gov_id_id_number` | ID number | परिचय नम्बर |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | कागजअनुसार नाम |  |
| `tpl_gov_id_notes` | Notes | टिपोट |  |
| `tpl_gov_id_portal_login` | Portal login | पोर्टल लगइन |  |
| `tpl_gov_id_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_insurance` | Insurance | बिमा |  |
| `tpl_insurance_agent_contact` | Agent contact | एजेन्टको सम्पर्क |  |
| `tpl_insurance_commencement_date` | Commencement date | सुरु भएको मिति |  |
| `tpl_insurance_insurer` | Insurer | बिमा कम्पनी |  |
| `tpl_insurance_maturity_date` | Maturity date | म्याद पुग्ने मिति |  |
| `tpl_insurance_nominee` | Nominee | इच्छाइएको व्यक्ति |  |
| `tpl_insurance_notes` | Notes | टिपोट |  |
| `tpl_insurance_policy_number` | Policy number | पोलिसी नम्बर |  |
| `tpl_insurance_policy_term` | Policy term | पोलिसीको अवधि |  |
| `tpl_insurance_policy_type` | Policy type | पोलिसीको किसिम |  |
| `tpl_insurance_portal_login` | Portal login | पोर्टल लगइन |  |
| `tpl_insurance_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_insurance_premium_amount` | Premium amount | प्रिमियम रकम |  |
| `tpl_insurance_premium_due_date` | Premium due date | प्रिमियमको मिति |  |
| `tpl_insurance_premium_mode` | Premium mode | प्रिमियम कसरी तिर्ने |  |
| `tpl_insurance_sum_assured` | Sum assured | बिमाको रकम |  |
| `tpl_login` | Login | लगइन |  |
| `tpl_login_notes` | Notes | टिपोट |  |
| `tpl_login_password` | Password | पासवर्ड |  |
| `tpl_login_recovery_codes` | Recovery codes | रिकभरी कोड |  |
| `tpl_login_username` | Username | युजरनेम |  |
| `tpl_login_website` | Website | वेबसाइट |  |
| `tpl_pan_card` | Pan Card | PAN कार्ड |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | आधारसँग जोडिएको |  |
| `tpl_pan_card_dob` | Dob | जन्म मिति |  |
| `tpl_pan_card_e_filing_password` | E filing password | ई-फाइलिङ पासवर्ड |  |
| `tpl_pan_card_fathers_name` | Fathers name | बुबाको नाम |  |
| `tpl_pan_card_file_copy` | Scanned copy | स्क्यान गरिएको प्रति |  |
| `tpl_pan_card_name_on_pan` | Name on pan | PAN मा नाम |  |
| `tpl_pan_card_notes` | Notes | टिपोट |  |
| `tpl_pan_card_pan_number` | Pan number | PAN |  |
| `tpl_passkey` | Passkey | पासकी |  |
| `tpl_passkey_credential_id` | Credential ID | क्रेडेन्सियल ID |  |
| `tpl_passkey_notes` | Notes | टिपोट |  |
| `tpl_passkey_private_key` | Private key | निजी कुञ्जी |  |
| `tpl_passkey_sign_count` | Sign count | साइन काउन्ट |  |
| `tpl_passkey_user_handle` | User handle | युजर ह्यान्डल |  |
| `tpl_passkey_username` | Username | युजरनेम |  |
| `tpl_passkey_website` | Website | वेबसाइट |  |
| `tpl_passport` | Passport | राहदानी |  |
| `tpl_passport_dob` | Dob | जन्म मिति |  |
| `tpl_passport_expiry_date` | Expiry date | म्याद सकिने मिति |  |
| `tpl_passport_file_copy` | Scanned copy | स्क्यान गरिएको प्रति |  |
| `tpl_passport_given_names` | Given names | दिइएको नाम |  |
| `tpl_passport_issue_date` | Issue date | जारी मिति |  |
| `tpl_passport_notes` | Notes | टिपोट |  |
| `tpl_passport_passport_number` | Passport number | राहदानी नम्बर |  |
| `tpl_passport_place_of_issue` | Place of issue | जारी भएको ठाउँ |  |
| `tpl_passport_portal_login` | Portal login | Passport Seva लगइन |  |
| `tpl_passport_portal_password` | Portal password | Passport Seva पासवर्ड |  |
| `tpl_passport_surname` | Surname | थर |  |
| `tpl_secure_note` | Secure Note | सुरक्षित टिपोट |  |
| `tpl_secure_note_attachment` | Attachment | संलग्न |  |
| `tpl_secure_note_body` | Note | टिपोट |  |
| `tpl_shopping` | Shopping | किनमेल खाता |  |
| `tpl_shopping_gift_card_code` | Gift card code | उपहार कार्ड कोड |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | उपहार कार्ड पिन |  |
| `tpl_shopping_membership_id` | Membership id | सदस्यता ID |  |
| `tpl_shopping_notes` | Notes | टिपोट |  |
| `tpl_shopping_password` | Password | पासवर्ड |  |
| `tpl_shopping_registered_email` | Registered email | दर्ता इमेल |  |
| `tpl_shopping_registered_mobile` | Registered mobile | दर्ता मोबाइल |  |
| `tpl_shopping_wallet_pin` | Wallet pin | वालेट पिन |  |
| `tpl_telecom` | Telecom | मोबाइल र इन्टरनेट |  |
| `tpl_telecom_account_number` | Account number | खाता नम्बर |  |
| `tpl_telecom_circle` | Circle | सर्कल |  |
| `tpl_telecom_mobile_number` | Mobile number | मोबाइल नम्बर |  |
| `tpl_telecom_notes` | Notes | टिपोट |  |
| `tpl_telecom_operator` | Operator | कम्पनी |  |
| `tpl_telecom_plan_type` | Plan type | प्लानको किसिम |  |
| `tpl_telecom_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_telecom_puk` | PUK code | PUK कोड |  |
| `tpl_telecom_renewal_date` | Renewal date | रिचार्जको मिति |  |
| `tpl_telecom_sim_number` | Sim number | सिम नम्बर (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | सिम पिन |  |
| `tpl_transit` | Transit | यात्रा पास |  |
| `tpl_transit_login_password` | Login password | लगइन पासवर्ड |  |
| `tpl_transit_notes` | Notes | टिपोट |  |
| `tpl_transit_operator_name` | Operator name | कम्पनी |  |
| `tpl_transit_registered_email` | Registered email | दर्ता इमेल |  |
| `tpl_transit_registered_mobile` | Registered mobile | दर्ता मोबाइल |  |
| `tpl_transit_smart_card_number` | Smart card number | स्मार्ट कार्ड नम्बर |  |
| `tpl_transit_wallet_pin` | Wallet pin | वालेट पिन |  |
| `tpl_travel_booking` | Travel Booking | यात्रा बुकिङ |  |
| `tpl_travel_booking_account_username` | Account username | युजरनेम |  |
| `tpl_travel_booking_login_password` | Login password | लगइन पासवर्ड |  |
| `tpl_travel_booking_notes` | Notes | टिपोट |  |
| `tpl_travel_booking_provider` | Provider | कम्पनी |  |
| `tpl_travel_booking_registered_email` | Registered email | दर्ता इमेल |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | दर्ता मोबाइल |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | वालेट पिन |  |
| `tpl_upi` | UPI | UPI |  |
| `tpl_upi_app_name` | App name | UPI एप |  |
| `tpl_upi_apps_used` | Apps used | कुन एपमा सक्रिय |  |
| `tpl_upi_linked_account` | Linked account | जोडिएको खाता |  |
| `tpl_upi_notes` | Notes | टिपोट |  |
| `tpl_upi_upi_id` | UPI ID | UPI ID |  |
| `tpl_upi_upi_pin` | UPI PIN | UPI पिन |  |
| `tpl_utility` | Utility | बिल र जडान |  |
| `tpl_utility_account_holder` | Account holder | खातावाला |  |
| `tpl_utility_consumer_number` | Consumer number | उपभोक्ता नम्बर |  |
| `tpl_utility_due_day` | Bill due day | बिल तिर्ने दिन |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag ID |  |
| `tpl_utility_notes` | Notes | टिपोट |  |
| `tpl_utility_portal_login` | Portal login | पोर्टल लगइन |  |
| `tpl_utility_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_utility_provider` | Provider | सेवा दिने कम्पनी |  |
| `tpl_utility_utility_kind` | Utility kind | केको बिल |  |
| `tpl_utility_vehicle_number` | Vehicle number | गाडी नम्बर |  |
| `tpl_utility_wifi_password` | Wi-Fi password | Wi-Fi पासवर्ड |  |
| `tpl_voter_id` | Voter Id | मतदाता परिचयपत्र |  |
| `tpl_voter_id_constituency` | Constituency | निर्वाचन क्षेत्र |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC नम्बर |  |
| `tpl_voter_id_file_copy` | Scanned copy | स्क्यान गरिएको प्रति |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | मतदाता कार्डमा नाम |  |
| `tpl_voter_id_notes` | Notes | टिपोट |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP पासवर्ड |  |
| `tr_days_many` | %1$d days left | %1$d दिन बाँकी |  |
| `tr_days_one` | %1$d day left | %1$d दिन बाँकी |  |
| `tr_gone_today` | gone today | आज जानेछ |  |
| `tr_restore` | Restore | फर्काउनुहोस् |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | त्यसपछि ती सधैंका लागि जान्छन् — अन्त कतै प्रतिलिपि छैन। |  |
| `ui_hide_passphrase` | Hide passphrase | पासफ्रेज लुकाउनुहोस् |  |
| `ui_label_count` | %1$s, %2$s | %1$s, %2$s |  |
| `ui_show_passphrase` | Show passphrase | पासफ्रेज देखाउनुहोस् |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | यही फोनमा %1$d प्रविष्टिसँग मिलाइयो। केही पनि कतै पठाइएको छैन। |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | यही फोनमा %1$d प्रविष्टिसँग मिलाइयो। केही पनि कतै पठाइएको छैन। |  |
| `vh_count_many` | %1$d things worth a look. | %1$d कुरामा ध्यान दिनुपर्छ। |  |
| `vh_count_one` | %1$d thing worth a look. | %1$d कुरामा ध्यान दिनुपर्छ। |  |
| `vh_empty` | No reused, weak or expiring credentials. | न पुनः प्रयोग गरिएको, न कमजोर, न म्याद सकिँदै गरेको केही छ। |  |
| `vh_kind_common` | Commonly guessed | सजिलै अनुमान लाग्ने |  |
| `vh_kind_expiring` | Expiring | म्याद सकिँदै |  |
| `vh_kind_reused` | Reused password | पुनः प्रयोग गरिएको पासवर्ड |  |
| `vh_kind_weak` | Weak | कमजोर |  |
| `vh_no_kit_title` | No recovery kit saved | कुनै रिकभरी किट सुरक्षित छैन |  |
| `vh_nothing` | Nothing to fix. | मिलाउनुपर्ने केही छैन। |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | अफलाइन |  |
| `wl_chip_open` | Open source | खुला स्रोत |  |
| `wl_create` | Create a new vault | नयाँ भण्डार बनाउनुहोस् |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | इमेल छैन · खाता छैन · केही पनि यो फोनबाट बाहिर जाँदैन |  |
| `wl_head_1` | Your keys. | तपाईंका साँचा। |  |
| `wl_head_2` | Your device. | तपाईंको फोन। |  |
| `wl_head_3` | No server. | सर्भर छैन। |  |
| `wl_restore` | Restore from Recovery Kit | रिकभरी किटबाट फर्काउनुहोस् |  |
| `wl_sr_headline` | Your keys. Your device. No server. | तपाईंका साँचा। तपाईंको फोन। सर्भर छैन। |  |
