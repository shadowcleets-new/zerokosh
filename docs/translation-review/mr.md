# Marathi (`mr`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-mr/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Marathi | ok? |
|---|---|---|---|
| `au_close` | Close | बंद करा |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | निवडलेल्या चित्रात कोणताही योग्य TOTP QR कोड मिळाला नाही |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | रिकाम्यापासून सुरुवात करा आणि तुमची फील्डे स्वतः नावे द्या — टेम्पलेट फक्त लेबले भरतात, डेटा कधीच नाही. |  |
| `hm_close_search` | Close search | शोध बंद करा |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | या फोनमधून कधीही बाहेर जात नाही. साठवताना एन्क्रिप्टेड. |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | आता तुम्ही नुकतीच आयात केलेली फाइल काढून टाका. ती तुमच्या पासवर्डांची उघडी यादी आहे आणि अजूनही तुमच्या Downloads मध्ये पडली आहे. |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | सगळे फक्त याच फोनवर डिक्रिप्ट होते. काहीही अपलोड होत नाही, कारण हे ॲप नेटवर्क कनेक्शन उघडूच शकत नाही. |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | बँक कधीही तुमचा OTP मागत नाही. जो मागेल तो फसवणूक करणारा आहे. |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | कोणताही बँक अधिकारी तुम्हाला स्क्रीन शेअर करणारे ॲप कधीच लावायला सांगणार नाही. |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | तुमचा UPI पिन फक्त UPI ॲपच्या कीपॅडसाठी आहे — फोनवर कोणालाही सांगू नका. |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | KYC एका दिवसात संपत नाही. “आज KYC संपत आहे” असे संदेश फसवणूक असतात. |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | पैसे घेण्यासाठी कधीही पिन टाकावा लागत नाही किंवा QR स्कॅन करावा लागत नाही. |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | वीज कापण्याचा SMS, आणि त्यात कोणाचा तरी खासगी नंबर? ती फसवणूक आहे. |  |
| `nav_close_menu` | Close menu | मेनू बंद करा |  |
| `nfc_cannot_read` | Cannot read cards | कार्डे वाचता येत नाहीत |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | या फोनमध्ये NFC नाही, त्यामुळे तो कार्ड वाचू शकत नाही. |  |
| `ob_fact_lost_title` | If you lose your keys | किल्ल्या हरवल्या तर |  |
| `ob_fact_network_note` | The app literally cannot phone home | हे ॲप कुठेही संपर्क करूच शकत नाही |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | बायोमेट्रिक कधीही सुरक्षित चिपच्या बाहेर जात नाही |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | तुम्ही किल्ली त्याच फोल्डरमध्ये ठेवली आहे जे तुमची एन्क्रिप्टेड तिजोरी सिंक करते. आता ज्याला ते फोल्डर मिळेल त्याला दोन्ही भाग मिळतील. किल्ली दुसरीकडे ठेवा — कागद, दुसरे खाते, किंवा कपाट. |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | हे तुमच्या तिजोरी फाइलच्या शेजारीच आहे |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH रिकव्हरी |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | स्कॅन करा किंवा टाइप करा. पुन्हा इन्स्टॉल, फॅक्टरी रीसेट, किंवा फोन हरवल्यावरही चालेल. |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | आत्ता सेव्ह केलेल्या किटमधला गट %1$d आणि गट %2$d लिहा. |  |
| `ob_kit_challenge_hint` | Group %1$d | गट %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | आधी किट सेव्ह करा, मग गट %1$d आणि %2$d परत लिहा. |  |
| `ob_kit_challenge_title` | Check you actually have it | किट खरंच तुमच्याकडे आहे का ते तपासा |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | हे वरच्या किल्लीशी जुळत नाही. |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | कोणीही — Zerokosh सुद्धा — ती माझ्यासाठी परत आणू शकत नाही. |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "मी ती ऑफलाइन ठेवली आहे. " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | एकदाच दिसते, कधीच उघड्या स्वरूपात ठेवली जात नाही. आत येऊ शकत असाल तर सेटिंगमधून नवी बनवा. |  |
| `ob_kit_head_emph` | On paper. | कागदावर. |  |
| `ob_kit_head_lead` | "One key. " | "एक किल्ली. " |  |
| `ob_kit_head_tail` | " Never online." | " कधीही ऑनलाइन नाही." |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | ना Gmail, ना WhatsApp, ना स्क्रीनशॉट. तिजोरी, बँक लॉकर, किंवा स्टीलची पाटी. |  |
| `ob_kit_offline_title` | Keep it off the internet | ती इंटरनेटपासून दूर ठेवा |  |
| `ob_kit_print` | Print | छापा |  |
| `ob_kit_print_note` | A printer, or Save as PDF | प्रिंटर, किंवा PDF म्हणून सेव्ह |  |
| `ob_kit_qr` | QR image | QR चित्र |  |
| `ob_kit_qr_cd` | Recovery key QR code | रिकव्हरी किल्लीचा QR कोड |  |
| `ob_kit_qr_note` | To an offline gallery | ऑफलाइन गॅलरीत |  |
| `ob_kit_regenerate` | Regenerate | नवी तयार करा |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | हे सेव्ह झाले नाही. पुन्हा प्रयत्न करा, किंवा दुसरी जागा निवडा. |  |
| `ob_kit_save_pdf` | Save PDF | पीडीएफ साठवा |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | एका पानाची छापण्याजोगी किट |  |
| `ob_kit_saved` | I\'ve saved my kit | मी माझी किट साठवली |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s मध्ये सेव्ह झाले |  |
| `ob_kit_sent_to_printer` | Sent to the printer | प्रिंटरकडे पाठवले |  |
| `ob_kit_skip` | I\'ll do this later | हे नंतर करेन |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | तुमची तिजोरी चालूच राहील. किट सेव्ह होईपर्यंत Zerokosh आठवण करून देत राहील. |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | याच फोनवर तयार झाली, एकदाच दिसेल. पासफ्रेज विसरलात तर आत परत येण्याचा हाच एकमेव मार्ग. |  |
| `ob_kit_working` | Working… | काम चालू आहे… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | तिजोरीची लेबले, टेम्पलेट आणि सूचना लगेच बदलतील. सेटिंगमध्ये कधीही बदलू शकता. |  |
| `ob_pass_confirm` | Confirm | पुन्हा लिहा |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | हे आम्हाला कधीच दिसत नाही. कोणतीही रीसेट लिंक नाही. |  |
| `ob_pass_head_emph` | held only | फक्त तुमच्याच |  |
| `ob_pass_head_lead` | "One secret, " | "एकच रहस्य, " |  |
| `ob_pass_head_tail` | " by you." | " जवळ." |  |
| `ob_pass_no_match` | no match | जुळत नाही |  |
| `ob_pass_seal` | Seal the vault | तिजोरी बंद करा |  |
| `ob_pass_sealing` | Sealing… | बंद केले जात आहे… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | तीन-चार असंबंधित शब्द, एका हुशार शब्दापेक्षा चांगले. या स्क्रीनमधून काहीही बाहेर जात नाही. |  |
| `ob_pass_tab_passphrase` | Passphrase | पासफ्रेज |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 अंकी पिन |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | तुमच्या बोटाचा ठसा फोनच्या सुरक्षित चिपमध्ये राहतो. तो या फोनमधून कधीही बाहेर जात नाही. |  |
| `ob_trust_continue` | I understand · Continue | समजले · पुढे चला |  |
| `ob_trust_head_emph` | don\'t | माहीत नाही |  |
| `ob_trust_head_lead` | "Exactly what we " | "आम्हाला खरेच काय " |  |
| `ob_trust_head_tail` | " know." | " ते हे." |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | पासफ्रेज विसरलात आणि रिकव्हरी किटही हरवली, तर तिजोरी बंदच राहील — तुमच्यासाठीही, आमच्यासाठीही, कोणासाठीही. |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "किल्ल्या तुमच्याकडे आहेत. " |  |
| `ob_trust_stat_files` | .kosh file on device | फोनमध्ये .kosh फाइल |  |
| `ob_trust_stat_servers` | servers contacted | सर्व्हरशी संपर्क |  |
| `ob_trust_stat_trackers` | trackers or SDKs | ट्रॅकर किंवा SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | हे एकदा वाचून घ्या. संपूर्ण सुरक्षा व्यवस्था हीच आहे, सोप्या भाषेत. |  |
| `ob_trust_tag_audited` | Audited build | ऑडिट केलेले बिल्ड |  |
| `ob_trust_tag_reproducible` | Reproducible APK | पुन्हा तयार करता येणारे APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | तुम्ही बोटाने उघडत आहात. बोट कधी काम करेनासे झाले, तर हाच तुम्हाला आत आणेल — म्हणून तपासून घेणे बरे. |  |
| `pc_confirm` | Check | तपासा |  |
| `pc_correct` | Still correct. Nothing to do. | अजूनही बरोबर. काही करायची गरज नाही. |  |
| `pc_forgot` | I cannot remember it | मला आठवत नाही |  |
| `pc_later` | Not now | आत्ता नको |  |
| `pc_reset_action` | Set new passphrase | नवा पासफ्रेज ठेवा |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | तुमचे बोट ही तिजोरी उघडू शकते, म्हणून तेच नवा पासफ्रेजही ठेवू शकते — रिकव्हरी किटची गरज नाही. खात्रीसाठी पुन्हा एकदा विचारले जाईल. |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | पासफ्रेज बदलला. जलद अनलॉक पुन्हा लावले. |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | हे झाले नाही. तुमचा जुनाच पासफ्रेज चालू आहे. |  |
| `pc_reset_title` | Set a new passphrase | नवा पासफ्रेज ठेवा |  |
| `pc_title` | Do you still remember your passphrase? | तुम्हाला अजून तुमचा पासफ्रेज आठवतो का? |  |
| `pc_wrong` | That is not it. You can set a new one instead. | हा तो नाही. त्याऐवजी नवा ठेवू शकता. |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | या नोंदीसाठी लक्षात ठेवलेली %1$d मूल्ये काढून टाकली जातील. हे परत आणता येत नाही. |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh ते लॉगिन साठवू शकले नाही. |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | भरण्यासाठी Zerokosh उघडा |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | हाच एक पासफ्रेज सगळे बंद ठेवतो. काहीतरी लांब निवडा, जे फक्त तुम्हालाच माहीत असेल. |  |
| `scr_create_button` | Lock it in | बंद करून टाका |  |
| `scr_create_confirm_hint` | Type it again | पुन्हा लिहा |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | पासफ्रेज (किमान 10 अक्षरे) |  |
| `scr_create_mismatch` | The two entries don\'t match | दोन्ही सारखे नाहीत |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 अंकी पिन |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | त्याऐवजी 6 अंकी पिन ठेवा |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | पिनसाठी फिंगरप्रिंट किंवा फेस अनलॉक असलेला फोन लागतो. कृपया पासफ्रेज निवडा. |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | पिन चालतो कारण हा फोन त्याला स्वतःच्या सुरक्षा चिपने आणि तुमच्या बोटाने किंवा चेहऱ्याने जपतो. |  |
| `scr_create_strength_fair` | Fair | बरा |  |
| `scr_create_strength_good` | Good | चांगला |  |
| `scr_create_strength_strong` | Strong | मजबूत |  |
| `scr_create_strength_weak` | Weak | कमकुवत |  |
| `scr_create_title` | Create your passphrase | तुमचा पासफ्रेज तयार करा |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | किमान 10 अक्षरे ठेवा — जितके लांब, तितके मजबूत |  |
| `scr_create_working` | Preparing your vault… | तुमची तिजोरी तयार होत आहे… |  |
| `scr_detail_delete` | Delete | काढून टाका |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | ती 30 दिवस “अलीकडे काढलेल्या”मध्ये राहील, आणि तुमच्या इतर फोनांशी सिंक झाल्यावर निघून जाईल. |  |
| `scr_detail_delete_confirm_title` | Delete this record? | ही नोंद काढून टाकायची? |  |
| `scr_detail_delete_confirm_yes` | Delete | काढून टाका |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | सीक्रेट किंवा otpauth:// लिंक चिकटवा |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | तुमचे बोट किंवा चेहरा वापरा |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh उघडा |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | खूप वेळा चुकीचा प्रयत्न झाला. %1$d सेकंद थांबा. |  |
| `scr_lock_hint` | Passphrase | पासफ्रेज |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | ही रिकव्हरी किल्ली बरोबर नाही — एक एक अक्षर जुळवून बघा |  |
| `scr_lock_title` | Vault is locked | तिजोरी बंद आहे |  |
| `scr_lock_unlock` | Unlock | उघडा |  |
| `scr_lock_use_passphrase` | Use passphrase | पासफ्रेज वापरा |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | कृपया एकदा पासफ्रेजने उघडा |  |
| `scr_lock_use_recovery` | Use Recovery Key | रिकव्हरी किल्ली वापरा |  |
| `scr_lock_wrong` | Wrong passphrase | पासफ्रेज चुकीचा आहे |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | कधी पासफ्रेज विसरलात, तर आत परत येण्याचा हाच एकमेव मार्ग आहे. आम्ही तो रीसेट करू शकत नाही — कोणीच करू शकत नाही. |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | मी ते लिहून घेतले आहे आणि सुरक्षित जागी ठेवले आहे |  |
| `scr_recovery_done` | Continue | पुढे चला |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | ही किल्ली एकदाच दिसते. जोपर्यंत तुम्ही तिजोरी उघडू शकता, तोपर्यंत सेटिंगमधून केव्हाही नवी बनवता येते. |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | हे पान तुमच्या मालमत्तेच्या कागदपत्रांसोबत किंवा इतर महत्त्वाच्या कागदांसोबत ठेवा. ही किल्ली ज्याच्याकडे असेल तो तुमची तिजोरी उघडू शकतो — तिला लॉकरच्या किल्लीसारखे जपा. |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | रिकव्हरी किट पीडीएफ साठवली |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh रिकव्हरी किट |  |
| `scr_recovery_save_pdf` | Save as PDF | पीडीएफमध्ये साठवा |  |
| `scr_recovery_title` | Your Recovery Key | तुमची रिकव्हरी किल्ली |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | तुम्ही जे काही साठवता ते तुमच्या फोनमधल्या एका बंद फाइलमध्ये राहते. ते कधीही आमच्यापर्यंत येत नाही — ते ठेवायला आमच्याकडे जागाच नाही. |  |
| `scr_trust_card1_title` | Your data stays on this device | तुमचा डेटा याच फोनमध्ये राहतो |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | ना Zerokosh खाते, ना क्लाउड, ना साइन-अप. हे फक्त तुम्हीच उघडू शकता. आम्हीही नाही. |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | आमचा कोणताही सर्व्हर नाही — ना हॅक व्हायला काही, ना विकायला |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | ना वर्गणी, ना जाहिराती. कोणीही आमचा कोड वाचून आमचे प्रत्येक वचन तपासू शकतो. |  |
| `scr_trust_card3_title` | Free forever, open source | कायम मोफत, ओपन सोर्स |  |
| `scr_trust_continue` | Continue | पुढे चला |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | तुमचे बदल साठवले गेले नाहीत, त्यामुळे तिजोरीत आधी जे होते त्यातले काहीही हरवलेले नाही. |  |
| `st_recently_deleted` | Recently deleted | अलीकडे काढलेले |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | वन-टाइम कोड (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | वन-टाइम कोड (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | वन-टाइम कोड (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA सीक्रेट |  |
| `tr_cannot_undo` | This cannot be undone. | हे परत आणता येत नाही. |  |
| `tr_delete_all` | Delete all permanently | सगळे कायमचे काढून टाका |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d नोंदी कायमच्या जातील. हे परत आणता येत नाही आणि पुनर्स्थापनेसाठी बॅकअप नाही. |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d नोंद कायमची जाईल. हे परत आणता येत नाही आणि पुनर्स्थापनेसाठी बॅकअप नाही. |  |
| `tr_delete_all_title` | Delete everything in the trash? | कचऱ्यातले सगळे काढून टाकायचे? |  |
| `tr_delete_now` | Delete now | आत्ताच काढून टाका |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | “%1$s” कायमची काढून टाकायची? |  |
| `tr_empty` | Nothing deleted. | काहीही काढलेले नाही. |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | काढलेल्या नोंदी इथे %1$d दिवस थांबतात. |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | भारतीय बँका, UPI, कार्डे, डीमॅट, EPF आणि तुम्ही खरोखर वापरता त्या OTP ॲपसाठी बनवलेली, फोनमध्येच राहणारी तिजोरी. |  |

## Priority 2 — longer prose

| key | English | Marathi | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | जी माहिती सर्वात जास्त कामी येते तिच्यापासून सुरुवात करा. नोट्स ॲपमध्ये पडलेल्या बारा पासवर्डांपेक्षा एक साठवलेला पासवर्डही जास्त सुरक्षित असतो. |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | पासफ्रेज विसरलात तर रिकव्हरी किट हाच आत येण्याचा मार्ग. कोणीही तुमच्यासाठी दुसरी बनवू शकत नाही. |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | त्या फाइलमध्ये ओळखण्यासारखे काही मिळाले नाही. Chrome, Google Password Manager, Bitwarden, LastPass आणि KeePass चे एक्सपोर्ट समजतात. |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d सध्याच्या नोंदी बदलतील — साइट आणि युजरनेमशी जुळवून. बदललेले पासवर्ड प्रत्येक नोंदीच्या इतिहासात परत मिळतील. |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d सध्याची नोंद बदलेल — साइट आणि युजरनेमशी जुळवून. बदललेले पासवर्ड प्रत्येक नोंदीच्या इतिहासात परत मिळतील. |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d नोंदी दोन्हीकडे बदलल्या होत्या. दोन्ही रूपे ठेवली आहेत — “(conflict copy)” शोधा. |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | ही बॅकअप फाइल उघडणारा पासफ्रेज टाका. तो तुमच्या सध्याच्या पासफ्रेजपेक्षा वेगळा असू शकतो. |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | .kosh फाइलचा Poly1305 प्रमाणीकरण टॅग जुळत नाही. असे अर्धवट सिंक किंवा खराब स्टोरेजनंतर होऊ शकते. |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh तिजोरी फाइलच्या शेजारी एक चालू बॅकअप ठेवते. तो तुमच्या सिंक फोल्डरमधून पुनर्स्थापित करा, किंवा दुसऱ्या फोनवर रिकव्हरी किटने तिजोरी उघडा. |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | कार्ड फोनच्या मागे सरळ टेकवून ठेवा जोपर्यंत ते वाचले जात नाही. यातून कार्ड नंबर, मुदत आणि नाव मिळते — CVV चिपमध्ये नसतो, तो तुम्हालाच लिहावा लागेल. |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | आत परत येण्याचा झटपट मार्ग निवडा. तिजोरीचा पहारा पासफ्रेजच करत राहील; हे फक्त याच फोनवर किल्ली उघडते. |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | आता तुमच्या तिजोरीत खरी माहिती आहे. रिकव्हरी किटशिवाय पासफ्रेज विसरलात तर कोणीही तुम्हाला परत आत आणू शकत नाही — आम्हीही नाही. |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh मोफत आणि ओपन सोर्स आहे, आणि त्याचा कोणताही सर्व्हर नाही. तुमची तिजोरी फक्त तुम्हीच उघडू शकता. आम्हीही नाही. |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | कॅमेऱ्याची परवानगी फक्त QR कोड स्कॅन करण्यासाठी लागते. नोंद जोडताना तुम्ही सीक्रेट हातानेही चिकटवू शकता. |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | ज्या बँक ॲप ऑटोफिल चालू देत नाहीत त्यांच्यासाठी — बटण दाबून लॉगिनची माहिती एक एक करून कॉपी करा |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | जसा तुम्ही तुमचा फोन उघडता, तशीच तिजोरीही. पासफ्रेज नेहमी चालतच राहील. |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | तिजोरीचे स्क्रीनशॉट क्लाउड फोटो बॅकअपमध्ये जाऊ शकतात. खरोखर गरज असेल तरच सुरू करा. |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | तिजोरी फाइल लिहिता आली नाही. तुम्ही बॅकअप आणि सिंक फोल्डर सेट केले असेल, तर कदाचित Android ने त्याची परवानगी काढून घेतली असेल — सेटिंग उघडा, फोल्डर पुन्हा निवडा, आणि पुन्हा प्रयत्न करा. |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | तुमच्या एन्क्रिप्टेड .kosh फाइली थेट याच फोल्डरमध्ये साठवल्या जातात. अनेक उपकरणांवर आपोआप बॅकअपसाठी हे फोल्डर Google Drive, Syncthing, Nextcloud किंवा SD कार्डशी सिंक करा. |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | नवी रिकव्हरी किल्ली तयार करण्यासाठी तुमचा पासफ्रेज टाका. जुनी किल्ली चालणे बंद होईल. |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | या पानावरचे बाकी सगळे एक लॉगिन कमकुवत करते. हे पूर्ण तिजोरी नेऊ शकते. सेटिंग → नवी रिकव्हरी किल्ली घ्या. |  |

## Priority 3 — short labels

| key | English | Marathi | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | मजबूत पासवर्ड वापरा |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | खात्याचे नाव (उदा. Google) |  |
| `au_active_many` | %1$d active codes | %1$d चालू कोड |  |
| `au_active_one` | %1$d active code | %1$d चालू कोड |  |
| `au_add_another` | Add another authenticator | आणखी एक ऑथेंटिकेटर जोडा |  |
| `au_add_secret` | Add Secret Key | सीक्रेट की जोडा |  |
| `au_camera_needed` | Camera permission needed to scan QR code | QR कोड स्कॅन करण्यासाठी कॅमेऱ्याची परवानगी हवी |  |
| `au_copied` | Copied · clears shortly | कॉपी झाले · थोड्या वेळात पुसले जाईल |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google, GitHub किंवा तुमच्या ब्रोकरचा QR स्कॅन करा, किंवा सीक्रेट की हाताने लिहा. |  |
| `au_enter_key` | Enter Key | की लिहा |  |
| `au_fallback_name` | Authenticator | ऑथेंटिकेटर |  |
| `au_flashlight` | Flashlight | बॅटरी |  |
| `au_grant` | Grant Permission | परवानगी द्या |  |
| `au_image_failed` | Failed to process image | चित्र वाचता आले नाही |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | चुकीची Base32 सीक्रेट की (फक्त A-Z अक्षरे आणि 2-7 अंक) |  |
| `au_no_match` | No codes match | कोणताही कोड मिळाला नाही |  |
| `au_none_yet` | No codes yet. | अजून कोणताही कोड नाही. |  |
| `au_pick_image` | Pick Image | चित्र निवडा |  |
| `au_rotating` | "Rotating " | "बदलत राहणारे " |  |
| `au_rotating_emph` | codes. | कोड. |  |
| `au_save_key` | Save Key | की साठवा |  |
| `au_scan_qr` | Scan a QR code | QR कोड स्कॅन करा |  |
| `au_scan_title` | Scan Authenticator QR | ऑथेंटिकेटर QR स्कॅन करा |  |
| `au_search_hint` | Search codes, issuers… | कोड, जारीकर्ते शोधा… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | उदा. JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | सीक्रेट की (Base32) |  |
| `au_tap_to_copy` | Tap to copy | कॉपी करण्यासाठी टॅप करा |  |
| `cat_apps` | Apps &amp; Logins | ॲप आणि लॉगिन |  |
| `cat_banks` | Banks &amp; UPI | बँका आणि UPI |  |
| `cat_cards` | Cards | कार्डे |  |
| `cat_govid` | Gov &amp; ID | सरकारी आणि ओळख |  |
| `cat_investments` | Investments | गुंतवणूक |  |
| `cat_utilities` | Utilities | बिले आणि जोडण्या |  |
| `cd_mask_hidden` | hidden | लपवलेले |  |
| `cd_shield_high_sensitivity` | extra-protected field | जास्त सुरक्षित माहिती |  |
| `gl_blank` | Blank template | रिकामे टेम्पलेट |  |
| `gl_cat_apps` | Apps | ॲप |  |
| `gl_cat_banks` | Banks | बँका |  |
| `gl_cat_cards` | Cards | कार्डे |  |
| `gl_cat_demat` | Demat | डीमॅट |  |
| `gl_cat_govid` | Gov ID | सरकारी ओळख |  |
| `gl_cat_popular` | Popular | लोकप्रिय |  |
| `gl_cat_shopping` | Shopping | खरेदी |  |
| `gl_cat_travel` | Travel | प्रवास |  |
| `gl_cat_upi` | UPI | UPI |  |
| `gl_cat_utilities` | Utilities | बिले आणि जोडण्या |  |
| `gl_head_emph` | storing? | साठवतो आहोत? |  |
| `gl_head_lead` | "What are we " | "आपण काय " |  |
| `gl_matches` | %1$d matches | %1$d मिळाले |  |
| `gl_most_used` | Most-used first | सर्वात जास्त वापरलेले आधी |  |
| `gl_not_found` | Can’t find a service? | सेवा सापडत नाही? |  |
| `gl_search` | Search %1$d Indian services… | %1$d भारतीय सेवांमध्ये शोधा… |  |
| `gl_suggested` | Suggested for you | तुमच्यासाठी सुचवलेले |  |
| `hm_add_first` | Add your first record | तुमची पहिली नोंद जोडा |  |
| `hm_all_offline` | all offline. | सगळे ऑफलाइन. |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d माहिती, " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d माहिती, " |  |
| `hm_detail_placeholder` | Pick a record to see it here | इथे पाहण्यासाठी एक नोंद निवडा |  |
| `hm_empty_blank` | A blank vault, ready. | रिकामी तिजोरी, तयार आहे. |  |
| `hm_empty_head_emph` | waiting. | वाट बघते आहे. |  |
| `hm_empty_head_lead` | "Your vault is " | "तुमची तिजोरी " |  |
| `hm_filter_all` | All | सर्व |  |
| `hm_import_backup` | Import an encrypted backup | एन्क्रिप्टेड बॅकअप आयात करा |  |
| `hm_import_backup_note` | Open a .kosh file from this device | याच फोनमधून .kosh फाइल उघडा |  |
| `hm_inst_many` | %1$d institutions | %1$d संस्था |  |
| `hm_inst_one` | %1$d institution | %1$d संस्था |  |
| `hm_kit_banner_action` | Save one now | आत्ताच सेव्ह करा |  |
| `hm_kit_banner_dismiss` | Remind me later | नंतर आठवण करा |  |
| `hm_kit_banner_title` | No recovery kit saved | कोणतीही रिकव्हरी किट सेव्ह केलेली नाही |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | उघडी आहे · सोडताच बंद |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | उघडी आहे · सोडल्यावर %1$d मिनिटांनी बंद |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | उघडी आहे · सोडल्यावर 1 मिनिटाने बंद |  |
| `hm_no_match` | Nothing matches “%1$s” | “%1$s” ने काहीच मिळाले नाही |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | एखादी संस्था, UPI हँडल, किंवा शेवटचे चार अंक वापरून बघा. |  |
| `hm_pinned` | Pinned | पिन केलेले |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC, priya@upi, PAN… शोधा |  |
| `hm_start_template` | Start with a template | टेम्पलेटपासून सुरुवात करा |  |
| `ic_could_not` | Could not import | आयात होऊ शकले नाही |  |
| `ic_done` | Done | झाले |  |
| `ic_import` | Import | आयात करा |  |
| `ic_imported` | Imported | आयात झाले |  |
| `ic_importing` | Importing… | आयात होत आहे… |  |
| `ic_new_many` | %1$d new logins. | %1$d नवी लॉगिन. |  |
| `ic_new_one` | %1$d new login. | %1$d नवे लॉगिन. |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d जोडले, %2$d बदलले. |  |
| `ic_title` | Import from %1$s? | %1$s मधून आयात करायचे? |  |
| `ic_too_large` | That file is too large to be a credential export. | ही फाइल इतकी मोठी आहे की ती पासवर्ड एक्सपोर्ट असूच शकत नाही. |  |
| `import_action` | Import | आयात करा |  |
| `import_locked` | Unlock your vault before importing. | आयात करण्याआधी तुमची तिजोरी उघडा. |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d जोडले, %2$d बदलले. काहीही पुसले गेले नाही. |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | ती फाइल Zerokosh तिजोरी म्हणून वाचता आली नाही. |  |
| `import_nothing_new` | Everything in that backup was already here. | त्या बॅकअपमधले सगळे आधीच इथे होते. |  |
| `import_passphrase_label` | Backup passphrase | बॅकअपचा पासफ्रेज |  |
| `import_title` | Import a backup | बॅकअप आयात करा |  |
| `kicker_locked` | Locked | बंद आहे |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · काहीही या फोनबाहेर गेलेले नाही |  |
| `lk_touch_unlock` | Touch to unlock | उघडण्यासाठी स्पर्श करा |  |
| `lk_welcome_emph` | Your vault is sealed. | तुमची तिजोरी बंद आहे. |  |
| `lk_welcome_lead` | Welcome back. | पुन्हा स्वागत आहे. |  |
| `msg_auth_needed` | Confirm it\'s you to see this | बघण्यासाठी तुम्हीच आहात याची खात्री करा |  |
| `msg_back` | Back | मागे |  |
| `msg_cancel` | Cancel | रद्द करा |  |
| `msg_file_damaged` | File damaged — restored from backup | फाइल खराब झाली होती — बॅकअपमधून दुरुस्त केली |  |
| `msg_ok` | OK | ठीक आहे |  |
| `msg_saved` | Saved | साठवले |  |
| `nav_all_templates` | All templates | सर्व टेम्पलेट |  |
| `nav_damaged_emph` | vault file | तिजोरी फाइलमध्ये |  |
| `nav_damaged_kicker` | Damaged state | खराब स्थिती |  |
| `nav_damaged_lead` | "Something in the " | "तुमच्या " |  |
| `nav_damaged_tail` | " is off." | " काहीतरी बिघडले आहे." |  |
| `nav_integrity_title` | Integrity check failed | अखंडता तपासणी अयशस्वी |  |
| `nav_scan` | Scan | स्कॅन |  |
| `nav_tap_card` | Tap a card | कार्ड टॅप करा |  |
| `nav_what_next` | What to do next | आता काय करायचे |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC बंद आहे. सेटिंगमध्ये जाऊन सुरू करा, मग प्रयत्न करा. |  |
| `nfc_hold_card` | Hold your card to the phone | कार्ड फोनला लावा |  |
| `nfc_missed` | Did not catch that | पकडता आले नाही |  |
| `nfc_read_failed` | That card could not be read. Try again. | ते कार्ड वाचता आले नाही. पुन्हा प्रयत्न करा. |  |
| `nfc_reading` | Reading… | वाचले जात आहे… |  |
| `nfc_try_again` | Try again | पुन्हा प्रयत्न करा |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · याच फोनवर मोजलेले |  |
| `ob_argon_faster` | Faster unlock | लवकर उघडेल |  |
| `ob_argon_harder` | Harder to attack | तोडायला अवघड |  |
| `ob_argon_measuring` | Measuring this device… | हा फोन मोजला जात आहे… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id ची कठीणता |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | एकही शब्दकोशातला शब्द नाही |  |
| `ob_check_pass_length` | 10 characters or more | 10 किंवा अधिक अक्षरे |  |
| `ob_check_pass_reuse` | Not reused from another app | दुसऱ्या ॲपमधून पुन्हा वापरलेला नाही |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | ना वाढदिवस, ना वाढदिवसाची तारीख |  |
| `ob_check_pin_digits` | All six digits entered | सहाही अंक भरले |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | ना सलग अंक, ना पुनरावृत्ती |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~तोडायला %1$d शतके |  |
| `ob_crack_days` | ~%1$d days to crack | ~तोडायला %1$d दिवस |  |
| `ob_crack_forever` | longer than the sun | सूर्यापेक्षाही जास्त टिकेल |  |
| `ob_crack_hours` | ~hours to crack | ~तोडायला काही तास |  |
| `ob_crack_seconds` | ~seconds to crack | ~तोडायला काही सेकंद |  |
| `ob_crack_years` | ~%1$d years to crack | ~तोडायला %1$d वर्षे |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | प्रमाणित, प्रत्येक तिजोरीचा वेगळा नॉन्स |  |
| `ob_fact_encryption_title` | Encryption | एन्क्रिप्शन |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | सेटअपच्या वेळी तुमच्या फोनवर मोजलेले |  |
| `ob_fact_kdf_title` | Key stretching | की स्ट्रेचिंग |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | ना रीसेट लिंक, ना सपोर्टचा मागचा दरवाजा. |  |
| `ob_fact_lost_value` | Nobody can recover it | कोणीही परत आणू शकत नाही |  |
| `ob_fact_network_title` | Network permission | नेटवर्कची परवानगी |  |
| `ob_fact_network_value` | Not requested | मागितलीच नाही |  |
| `ob_fact_quick_title` | Quick unlock | झटपट अनलॉक |  |
| `ob_fact_quick_value` | Hardware keystore | हार्डवेअर कीस्टोअर |  |
| `ob_lang_continue` | Continue in %1$s | %1$s मध्ये पुढे चला |  |
| `ob_lang_head_emph` | language. | भाषा निवडा. |  |
| `ob_lang_head_lead` | "Choose your " | "तुमची " |  |
| `ob_lang_search` | Search %1$d languages | %1$d भाषांमध्ये शोधा |  |
| `ob_quick_continue_pass` | Continue with passphrase | पासफ्रेजसह पुढे चला |  |
| `ob_quick_enable` | Enable quick unlock | झटपट अनलॉक सुरू करा |  |
| `ob_quick_fingerprint` | Fingerprint | फिंगरप्रिंट |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | जलद, हार्डवेअरने सुरक्षित अनलॉक. |  |
| `ob_quick_head_emph` | Without the cloud. | क्लाउडशिवाय. |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "एका स्पर्शात उघडेल. " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox. कोणतीही बायोमेट्रिक माहिती Zerokosh पर्यंत कधीच पोहोचत नाही. |  |
| `ob_quick_hw_title` | Hardware-backed | हार्डवेअरने सुरक्षित |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | या फोनमध्ये हार्डवेअरचा सेन्सर नाही. |  |
| `ob_quick_opening` | Opening your vault… | तुमची तिजोरी उघडत आहे… |  |
| `ob_quick_pass_only` | Passphrase only | फक्त पासफ्रेज |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | प्रत्येक वेळी लिहा. सर्वात सुरक्षित. |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | झटपट अनलॉक सेट झाले नाही. पुन्हा प्रयत्न करा, किंवा फक्त पासफ्रेजसह पुढे चला. |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | आत्ता नको — मी पासफ्रेज लिहीन |  |
| `ob_quick_touch_title` | Touch the sensor | सेन्सरला स्पर्श करा |  |
| `ob_recommended` | Recommended | सुचवलेले |  |
| `ob_reveal_hide` | Hide | लपवा |  |
| `ob_reveal_show` | Show | दाखवा |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | गुंडाळणारी किल्ली हार्डवेअर कीस्टोअरमध्ये राहते. बायोमेट्रिक पुढच्या टप्प्यात. |  |
| `ob_seal_title` | Seal to this device | याच फोनशी बांधून टाका |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | या फोनमध्ये हार्डवेअरचे बायोमेट्रिक नाही. |  |
| `ob_soon` | SOON | लवकरच |  |
| `ob_step_label` | Step %1$d of 6 | टप्पा %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | असे काहीतरी निवडा जे फक्त तुम्हीच म्हणाल |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | चांगला · %1$d बिट एन्ट्रॉपी |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | खूप लहान · 10 अक्षरे हवीत |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | मजबूत · %1$d बिट एन्ट्रॉपी |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | कमकुवत · %1$d बिट एन्ट्रॉपी |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | असे सहा अंक जे तुमचे आयुष्य बघून कोणालाही ओळखता येणार नाहीत |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | बरा · %1$d बिट — पिन याहून जास्त मजबूत होऊच शकत नाही |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | खूप लहान · 6 अंक हवेत |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | कमकुवत · हेच पिन सर्वात आधी वापरून बघतात |  |
| `ob_try_label` | TRY | बघा |  |
| `qa_aadhaar` | Aadhaar | आधार |  |
| `qa_bank_account` | Bank account | बँक खाते |  |
| `qa_pan` | PAN | PAN |  |
| `qa_upi_id` | UPI ID | UPI ID |  |
| `rd_copied` | Copied | कॉपी झाले |  |
| `rd_forget` | Forget | विसरा |  |
| `rd_forget_these` | Forget these | हे विसरून जा |  |
| `rd_forget_title` | Forget previous passwords? | आधीचे पासवर्ड विसरायचे? |  |
| `rd_history_hide` | Hide | लपवा |  |
| `rd_history_show` | Show %1$d | %1$d दाखवा |  |
| `rd_hold_to_reveal` | Hold to reveal | बघण्यासाठी दाबून धरा |  |
| `rd_last_edit` | last edit %1$s | शेवटचा बदल %1$s |  |
| `rd_release_to_hide` | Release to hide | लपवण्यासाठी सोडा |  |
| `re_add_field` | + Add another field | + आणखी एक फील्ड जोडा |  |
| `re_add_field_title` | Add a field | फील्ड जोडा |  |
| `re_field_name` | Field name | फील्डचे नाव |  |
| `re_pick_date` | Pick a date | तारीख निवडा |  |
| `re_remove` | Remove | काढून टाका |  |
| `re_tap_card` | Read the card by tapping it | कार्ड टॅप करून वाचा |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | गुप्त माना (लपवलेले राहील, बघण्यासाठी दाबून धरा) |  |
| `re_using_template` | using the %1$s template | %1$s टेम्पलेटने |  |
| `rem_kit_title` | No recovery kit saved | कोणतीही रिकव्हरी किट सेव्ह केलेली नाही |  |
| `scr_about_license` | License: GPL-3.0 — free forever | परवाना: GPL-3.0 — कायम मोफत |  |
| `scr_about_source` | Source code | सोर्स कोड |  |
| `scr_about_version` | Version %1$s | आवृत्ती %1$s |  |
| `scr_auth_add_qr` | Add via QR | QR ने जोडा |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | तुमच्या ॲप आणि ब्रोकरचे कोड इथे दिसतील |  |
| `scr_auth_scan_title` | Point the camera at the QR code | कॅमेरा QR कोडवर धरा |  |
| `scr_detail_copied` | Copied · clears in 30s | कॉपी झाले · 30 सेकंदांत पुसले जाईल |  |
| `scr_detail_copy` | Copy | कॉपी |  |
| `scr_detail_edit` | Edit | बदला |  |
| `scr_detail_favorite` | Favourite | आवडते |  |
| `scr_detail_hidden` | Hidden | लपवलेले |  |
| `scr_detail_hide` | Hide | लपवा |  |
| `scr_detail_history_empty` | Nothing replaced yet. | अजून काहीच बदललेले नाही. |  |
| `scr_detail_history_title` | Previous passwords | आधीचे पासवर्ड |  |
| `scr_detail_reveal` | Show | दाखवा |  |
| `scr_detail_shown` | Shown | दिसत आहे |  |
| `scr_edit_cancel` | Cancel | रद्द करा |  |
| `scr_edit_generate` | Generate | तयार करा |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | बँक / कंपनी (गट करण्यासाठी) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | हे बरोबर वाटत नाही — एकदा बघून घ्या |  |
| `scr_edit_link_none` | None | काहीही नाही |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | हा कार्ड नंबर नेहमीच्या तपासणीत उतरत नाही — बरोबर असेल तर साठवून टाका |  |
| `scr_edit_month` | Month | महिना |  |
| `scr_edit_picker_other` | Other… | इतर… |  |
| `scr_edit_picker_other_hint` | Type your own | तुमचे लिहा |  |
| `scr_edit_required_title` | Give it a name first | आधी याला काहीतरी नाव द्या |  |
| `scr_edit_save` | Save | साठवा |  |
| `scr_edit_title_hint` | Title | नाव |  |
| `scr_edit_title_new` | New | नवीन |  |
| `scr_edit_year` | Year | वर्ष |  |
| `scr_gallery_quick_add` | Quick add | पटकन जोडा |  |
| `scr_gallery_title` | What do you want to save? | तुम्हाला काय साठवायचे आहे? |  |
| `scr_home_add` | Add | जोडा |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | तुमचे बँक खाते असे दिसेल |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | तुमची कार्डे, UPI आणि ॲप लॉगिनही इथेच राहतील |  |
| `scr_home_group_other` | Other | इतर |  |
| `scr_home_no_results` | Nothing matches your search | तुमच्या शोधाने काहीच मिळाले नाही |  |
| `scr_home_search_hint` | Search your vault | तुमच्या तिजोरीत शोधा |  |
| `scr_home_tab_authenticator` | Authenticator | कोड |  |
| `scr_home_tab_home` | Home | होम |  |
| `scr_home_tab_settings` | Settings | सेटिंग |  |
| `scr_home_title` | Home | होम |  |
| `scr_language_continue` | Continue | पुढे चला |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | तुमची भाषा निवडा |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | कॉपी करण्यासाठी बटण दाबा · 30 सेकंदांत पुसले जाईल |  |
| `scr_login_helper_channel` | Login helper | लॉगिन मदतनीस |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s मध्ये लॉगिन होत आहे |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | आत्ताच बॅकअप फोल्डर सेट करा |  |
| `scr_quickunlock_enable` | Turn on | सुरू करा |  |
| `scr_quickunlock_skip` | Not now | आत्ता नको |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | बोटाने किंवा चेहऱ्याने उघडा |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s ची तारीख जवळ आली आहे · Zerokosh उघडा |  |
| `scr_reminder_channel` | Renewal reminders | नूतनीकरणाची आठवण |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh आठवण करून देत आहे |  |
| `scr_settings_about` | About | ओळख |  |
| `scr_settings_allow_screenshots` | Allow screenshots | स्क्रीनशॉट घेऊ द्या |  |
| `scr_settings_autofill` | Autofill service | ऑटोफिल सेवा |  |
| `scr_settings_autofill_off` | Not set up | सेट केलेले नाही |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | उपलब्ध नाही |  |
| `scr_settings_autolock` | Lock when I leave the app | ॲप सोडताच बंद करा |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 मिनिटानंतर |  |
| `scr_settings_autolock_immediately` | Immediately | लगेच |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d मिनिटांनंतर |  |
| `scr_settings_change_passphrase` | Change passphrase | पासफ्रेज बदला |  |
| `scr_settings_current_passphrase` | Current passphrase | सध्याचा पासफ्रेज |  |
| `scr_settings_export` | Export | निर्यात करा |  |
| `scr_settings_import` | Import passwords | पासवर्ड आयात करा |  |
| `scr_settings_language` | Language | भाषा |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | नवा पासफ्रेज (किमान 10 अक्षरे) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | नवी रिकव्हरी किल्ली घ्या |  |
| `scr_settings_passphrase_changed` | Passphrase changed | पासफ्रेज बदलला |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | फिंगरप्रिंट / फेस अनलॉक |  |
| `scr_settings_security_info` | How your data is protected | तुमचा डेटा कसा सुरक्षित आहे |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | बॅकअप आणि सिंक फोल्डर |  |
| `scr_settings_sync_not_set` | Not backed up | बॅकअप नाही |  |
| `scr_settings_title` | Settings | सेटिंग |  |
| `se_title` | Not saved | साठवले नाही |  |
| `st_active_folder` | Active Folder | चालू फोल्डर |  |
| `st_active_value` | Active · %1$s | चालू · %1$s |  |
| `st_backing_up` | Backing up vault… | तिजोरीचा बॅकअप होत आहे… |  |
| `st_backup_now` | Backup Now | आत्ताच बॅकअप घ्या |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | बॅकअप आणि सिंक फोल्डर |  |
| `st_change_folder` | Change Folder | फोल्डर बदला |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | नवा पासफ्रेज पुन्हा लिहा |  |
| `st_connected_folder` | Connected folder: %1$s | जोडलेले फोल्डर: %1$s |  |
| `st_disconnect` | Disconnect | काढून टाका |  |
| `st_done` | Done | झाले |  |
| `st_export_kosh` | Export encrypted .kosh | एन्क्रिप्टेड .kosh निर्यात करा |  |
| `st_folder_fallback` | Folder | फोल्डर |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | पासफ्रेज विसरलात? |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | तुमच्या बोटाने नवा ठेवा |  |
| `st_generate` | Generate | तयार करा |  |
| `st_group_about` | About | ओळख |  |
| `st_group_appearance` | Appearance | रूप |  |
| `st_group_security` | Security | सुरक्षा |  |
| `st_group_sync` | Sync | सिंक |  |
| `st_import_kosh` | Import a .kosh backup | .kosh बॅकअप आयात करा |  |
| `st_import_other` | Import from another password manager | दुसऱ्या पासवर्ड मॅनेजरमधून आयात करा |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | परवाना |  |
| `st_logos_by` | Logos provided by | लोगो देणारे |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | ती ऑफलाइन ठेवा. जुनी रिकव्हरी किल्ली आता वैध नाही. |  |
| `st_new_recovery_result` | Your new Recovery Key: | तुमची नवी रिकव्हरी किल्ली: |  |
| `st_subtitle` | Your rules. | तुमचे नियम. |  |
| `st_theme` | Theme | थीम |  |
| `st_theme_dark` | Dark | गडद |  |
| `st_theme_light` | Light | फिकट |  |
| `st_theme_system` | System | सिस्टम |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | तिजोरी %1$s मध्ये साठवली! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | बॅकअप झाला नाही — फोल्डरची परवानगी बघून घ्या |  |
| `st_toast_disconnected` | Backup folder disconnected | बॅकअप फोल्डर काढून टाकले |  |
| `st_toast_export_failed` | Export failed | निर्यात होऊ शकली नाही |  |
| `st_toast_exported` | Encrypted vault exported | एन्क्रिप्टेड तिजोरी निर्यात झाली |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | बॅकअप फोल्डर जोडले आणि तिजोरी %1$s मध्ये साठवली! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | बॅकअप फोल्डर जोडले: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | फोल्डर जोडता आले नाही: %1$s |  |
| `st_vault_review` | Vault review | तिजोरीची तपासणी |  |
| `st_vault_review_detail` | Reused, weak, expiring | पुन्हा वापरलेले, कमकुवत, संपणारे |  |
| `tab_codes` | Codes | कोड |  |
| `tab_settings` | Settings | सेटिंग |  |
| `tab_templates` | Templates | टेम्पलेट |  |
| `tab_vault` | Vault | तिजोरी |  |
| `time_days` | %1$dd ago | %1$d दि पूर्वी |  |
| `time_hours` | %1$dh ago | %1$d ता पूर्वी |  |
| `time_just_now` | just now | आत्ताच |  |
| `time_minutes` | %1$dm ago | %1$d मि पूर्वी |  |
| `time_months` | %1$dmo ago | %1$d महिन्यांपूर्वी |  |
| `time_years` | %1$dy ago | %1$d वर्षांपूर्वी |  |
| `tpl_aadhaar_card` | Aadhaar Card | आधार |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | आधार क्रमांक |  |
| `tpl_aadhaar_card_address` | Address | आधारवरील पत्ता |  |
| `tpl_aadhaar_card_dob` | Dob | जन्मतारीख |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | स्कॅन केलेली प्रत |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | जोडलेला मोबाइल |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar पासकोड |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | आधारवरील नाव |  |
| `tpl_aadhaar_card_notes` | Notes | टिपा |  |
| `tpl_app_profile` | App Profile | ॲप प्रोफाइल |  |
| `tpl_app_profile_app_name` | App name | ॲपचे नाव |  |
| `tpl_app_profile_gift_cards` | Gift cards | गिफ्ट कार्ड |  |
| `tpl_app_profile_membership` | Membership | सदस्यत्व |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | सदस्यत्व नूतनीकरण |  |
| `tpl_app_profile_notes` | Notes | टिपा |  |
| `tpl_app_profile_password_if_any` | Password (if any) | पासवर्ड (असल्यास) |  |
| `tpl_app_profile_registered_email` | Registered email | नोंदणीकृत ईमेल |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | नोंदणीकृत मोबाइल |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_bank_account` | Bank Account | बँक खाते |  |
| `tpl_bank_account_account_number` | Account number | खाते क्रमांक |  |
| `tpl_bank_account_account_type` | Account type | खात्याचा प्रकार |  |
| `tpl_bank_account_bank_name` | Bank name | बँकेचे नाव |  |
| `tpl_bank_account_branch` | Branch | शाखा |  |
| `tpl_bank_account_customer_id` | Customer id | ग्राहक ID |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC कोड |  |
| `tpl_bank_account_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_bank_account_micr` | MICR code | MICR कोड |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | नेट-बँकिंग युजर ID |  |
| `tpl_bank_account_nominee` | Nominee | नॉमिनी |  |
| `tpl_bank_account_notes` | Notes | टिपा |  |
| `tpl_bank_account_profile_password` | Profile password | प्रोफाइल पासवर्ड |  |
| `tpl_bank_account_registered_email` | Registered email | नोंदणीकृत ईमेल |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | नोंदणीकृत मोबाइल |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | व्यवहार पासवर्ड |  |
| `tpl_card` | Card | कार्ड |  |
| `tpl_card_atm_pin` | ATM PIN | ATM पिन |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | बिलिंग सायकलचा दिवस |  |
| `tpl_card_card_network` | Card network | नेटवर्क |  |
| `tpl_card_card_number` | Card number | कार्ड क्रमांक |  |
| `tpl_card_card_portal_login` | Card portal login | कार्ड पोर्टल लॉगिन |  |
| `tpl_card_card_portal_password` | Card portal password | कार्ड पोर्टल पासवर्ड |  |
| `tpl_card_card_type` | Card type | कार्डाचा प्रकार |  |
| `tpl_card_card_variant` | Card variant | कार्ड व्हेरिअंट |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | मुदत |  |
| `tpl_card_linked_account` | Linked account | जोडलेले खाते |  |
| `tpl_card_name_on_card` | Name on card | कार्डवरील नाव |  |
| `tpl_card_notes` | Notes | टिपा |  |
| `tpl_demat` | Demat | डीमॅट |  |
| `tpl_demat_api_key` | API key | API की |  |
| `tpl_demat_api_secret` | API secret | API सीक्रेट |  |
| `tpl_demat_bo_id` | BO ID | BO ID |  |
| `tpl_demat_broker` | Broker | ब्रोकर |  |
| `tpl_demat_cdsl_tpin` | CDSL TPIN | CDSL TPIN |  |
| `tpl_demat_client_id` | Client id | क्लायंट ID |  |
| `tpl_demat_depository` | Depository | डिपॉझिटरी |  |
| `tpl_demat_dp_id` | DP ID | DP ID |  |
| `tpl_demat_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_demat_mf_folios` | Mutual fund folios | म्युच्युअल फंड फोलिओ |  |
| `tpl_demat_mpin` | MPIN | MPIN |  |
| `tpl_demat_nominee` | Nominee | नॉमिनी |  |
| `tpl_demat_notes` | Notes | टिपा |  |
| `tpl_digilocker` | Digilocker | DigiLocker |  |
| `tpl_digilocker_login_username` | Login username | युजरनेम |  |
| `tpl_digilocker_notes` | Notes | टिपा |  |
| `tpl_digilocker_portal_password` | Portal password | पासवर्ड |  |
| `tpl_digilocker_security_pin` | Security pin | सुरक्षा पिन |  |
| `tpl_driving_license` | Driving License | ड्रायव्हिंग लायसन्स |  |
| `tpl_driving_license_dl_number` | Dl number | लायसन्स क्रमांक |  |
| `tpl_driving_license_dob` | Dob | जन्मतारीख |  |
| `tpl_driving_license_expiry_date` | Expiry date | या तारखेपर्यंत वैध |  |
| `tpl_driving_license_file_copy` | Scanned copy | स्कॅन केलेली प्रत |  |
| `tpl_driving_license_issue_date` | Issue date | दिल्याची तारीख |  |
| `tpl_driving_license_name_on_dl` | Name on dl | लायसन्सवरील नाव |  |
| `tpl_driving_license_notes` | Notes | टिपा |  |
| `tpl_driving_license_rto_location` | Rto location | RTO |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | वाहन श्रेणी |  |
| `tpl_epf_pension` | Epf Pension | EPF / पेन्शन |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | जोडलेला मोबाइल |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | EPF वरील नाव |  |
| `tpl_epf_pension_nominee` | Nominee | नॉमिनी |  |
| `tpl_epf_pension_notes` | Notes | टिपा |  |
| `tpl_epf_pension_password` | Password | पासवर्ड |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | PF मेंबर ID |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO पासवर्ड |  |
| `tpl_epf_pension_scheme` | Scheme | योजना |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | सरकारी ओळखपत्र |  |
| `tpl_gov_id_expiry` | Expiry | मुदत |  |
| `tpl_gov_id_file_copy` | Scanned copy | स्कॅन केलेली प्रत |  |
| `tpl_gov_id_id_kind` | ID type | ओळखपत्राचा प्रकार |  |
| `tpl_gov_id_id_number` | ID number | ओळख क्रमांक |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | ओळखपत्रानुसार नाव |  |
| `tpl_gov_id_notes` | Notes | टिपा |  |
| `tpl_gov_id_portal_login` | Portal login | पोर्टल लॉगिन |  |
| `tpl_gov_id_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_insurance` | Insurance | विमा |  |
| `tpl_insurance_agent_contact` | Agent contact | एजंटचा संपर्क |  |
| `tpl_insurance_commencement_date` | Commencement date | सुरू झाल्याची तारीख |  |
| `tpl_insurance_insurer` | Insurer | विमा कंपनी |  |
| `tpl_insurance_maturity_date` | Maturity date | मुदतपूर्तीची तारीख |  |
| `tpl_insurance_nominee` | Nominee | नॉमिनी |  |
| `tpl_insurance_notes` | Notes | टिपा |  |
| `tpl_insurance_policy_number` | Policy number | पॉलिसी क्रमांक |  |
| `tpl_insurance_policy_term` | Policy term | पॉलिसीची मुदत |  |
| `tpl_insurance_policy_type` | Policy type | पॉलिसीचा प्रकार |  |
| `tpl_insurance_portal_login` | Portal login | पोर्टल लॉगिन |  |
| `tpl_insurance_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_insurance_premium_amount` | Premium amount | प्रीमियम रक्कम |  |
| `tpl_insurance_premium_due_date` | Premium due date | प्रीमियमची तारीख |  |
| `tpl_insurance_premium_mode` | Premium mode | प्रीमियम कसा भरता |  |
| `tpl_insurance_sum_assured` | Sum assured | विमा रक्कम |  |
| `tpl_login` | Login | लॉगिन |  |
| `tpl_login_notes` | Notes | टिपा |  |
| `tpl_login_password` | Password | पासवर्ड |  |
| `tpl_login_recovery_codes` | Recovery codes | रिकव्हरी कोड |  |
| `tpl_login_username` | Username | युजरनेम |  |
| `tpl_login_website` | Website | वेबसाइट |  |
| `tpl_pan_card` | Pan Card | PAN कार्ड |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | आधारशी जोडलेले |  |
| `tpl_pan_card_dob` | Dob | जन्मतारीख |  |
| `tpl_pan_card_e_filing_password` | E filing password | ई-फायलिंग पासवर्ड |  |
| `tpl_pan_card_fathers_name` | Fathers name | वडिलांचे नाव |  |
| `tpl_pan_card_file_copy` | Scanned copy | स्कॅन केलेली प्रत |  |
| `tpl_pan_card_name_on_pan` | Name on pan | PAN वरील नाव |  |
| `tpl_pan_card_notes` | Notes | टिपा |  |
| `tpl_pan_card_pan_number` | Pan number | PAN |  |
| `tpl_passkey` | Passkey | पासकी |  |
| `tpl_passkey_credential_id` | Credential ID | क्रेडेन्शियल ID |  |
| `tpl_passkey_notes` | Notes | टिपा |  |
| `tpl_passkey_private_key` | Private key | खासगी की |  |
| `tpl_passkey_sign_count` | Sign count | साइन काउंट |  |
| `tpl_passkey_user_handle` | User handle | यूजर हँडल |  |
| `tpl_passkey_username` | Username | युजरनेम |  |
| `tpl_passkey_website` | Website | वेबसाइट |  |
| `tpl_passport` | Passport | पासपोर्ट |  |
| `tpl_passport_dob` | Dob | जन्मतारीख |  |
| `tpl_passport_expiry_date` | Expiry date | संपण्याची तारीख |  |
| `tpl_passport_file_copy` | Scanned copy | स्कॅन केलेली प्रत |  |
| `tpl_passport_given_names` | Given names | दिलेले नाव |  |
| `tpl_passport_issue_date` | Issue date | दिल्याची तारीख |  |
| `tpl_passport_notes` | Notes | टिपा |  |
| `tpl_passport_passport_number` | Passport number | पासपोर्ट क्रमांक |  |
| `tpl_passport_place_of_issue` | Place of issue | दिल्याचे ठिकाण |  |
| `tpl_passport_portal_login` | Portal login | Passport Seva लॉगिन |  |
| `tpl_passport_portal_password` | Portal password | Passport Seva पासवर्ड |  |
| `tpl_passport_surname` | Surname | आडनाव |  |
| `tpl_secure_note` | Secure Note | सुरक्षित टीप |  |
| `tpl_secure_note_attachment` | Attachment | जोडपत्र |  |
| `tpl_secure_note_body` | Note | टीप |  |
| `tpl_shopping` | Shopping | शॉपिंग खाते |  |
| `tpl_shopping_gift_card_code` | Gift card code | गिफ्ट कार्ड कोड |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | गिफ्ट कार्ड पिन |  |
| `tpl_shopping_membership_id` | Membership id | सदस्यत्व ID |  |
| `tpl_shopping_notes` | Notes | टिपा |  |
| `tpl_shopping_password` | Password | पासवर्ड |  |
| `tpl_shopping_registered_email` | Registered email | नोंदणीकृत ईमेल |  |
| `tpl_shopping_registered_mobile` | Registered mobile | नोंदणीकृत मोबाइल |  |
| `tpl_shopping_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_telecom` | Telecom | मोबाइल आणि इंटरनेट |  |
| `tpl_telecom_account_number` | Account number | खाते क्रमांक |  |
| `tpl_telecom_circle` | Circle | सर्कल |  |
| `tpl_telecom_mobile_number` | Mobile number | मोबाइल क्रमांक |  |
| `tpl_telecom_notes` | Notes | टिपा |  |
| `tpl_telecom_operator` | Operator | कंपनी |  |
| `tpl_telecom_plan_type` | Plan type | प्लॅनचा प्रकार |  |
| `tpl_telecom_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_telecom_puk` | PUK code | PUK कोड |  |
| `tpl_telecom_renewal_date` | Renewal date | रिचार्जची तारीख |  |
| `tpl_telecom_sim_number` | Sim number | सिम क्रमांक (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | सिम पिन |  |
| `tpl_transit` | Transit | ट्रान्झिट पास |  |
| `tpl_transit_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_transit_notes` | Notes | टिपा |  |
| `tpl_transit_operator_name` | Operator name | कंपनी |  |
| `tpl_transit_registered_email` | Registered email | नोंदणीकृत ईमेल |  |
| `tpl_transit_registered_mobile` | Registered mobile | नोंदणीकृत मोबाइल |  |
| `tpl_transit_smart_card_number` | Smart card number | स्मार्ट कार्ड क्रमांक |  |
| `tpl_transit_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_travel_booking` | Travel Booking | प्रवास बुकिंग |  |
| `tpl_travel_booking_account_username` | Account username | युजरनेम |  |
| `tpl_travel_booking_login_password` | Login password | लॉगिन पासवर्ड |  |
| `tpl_travel_booking_notes` | Notes | टिपा |  |
| `tpl_travel_booking_provider` | Provider | कंपनी |  |
| `tpl_travel_booking_registered_email` | Registered email | नोंदणीकृत ईमेल |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | नोंदणीकृत मोबाइल |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | वॉलेट पिन |  |
| `tpl_upi` | UPI | UPI |  |
| `tpl_upi_app_name` | App name | UPI ॲप |  |
| `tpl_upi_apps_used` | Apps used | कोणत्या ॲपमध्ये चालू आहे |  |
| `tpl_upi_linked_account` | Linked account | जोडलेले खाते |  |
| `tpl_upi_notes` | Notes | टिपा |  |
| `tpl_upi_upi_id` | UPI ID | UPI ID |  |
| `tpl_upi_upi_pin` | UPI PIN | UPI पिन |  |
| `tpl_utility` | Utility | बिले आणि जोडण्या |  |
| `tpl_utility_account_holder` | Account holder | खातेदार |  |
| `tpl_utility_consumer_number` | Consumer number | ग्राहक क्रमांक |  |
| `tpl_utility_due_day` | Bill due day | बिल भरण्याचा दिवस |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag ID |  |
| `tpl_utility_notes` | Notes | टिपा |  |
| `tpl_utility_portal_login` | Portal login | पोर्टल लॉगिन |  |
| `tpl_utility_portal_password` | Portal password | पोर्टल पासवर्ड |  |
| `tpl_utility_provider` | Provider | सेवा देणारी कंपनी |  |
| `tpl_utility_utility_kind` | Utility kind | कशाचे बिल |  |
| `tpl_utility_vehicle_number` | Vehicle number | वाहन क्रमांक |  |
| `tpl_utility_wifi_password` | Wi-Fi password | Wi-Fi पासवर्ड |  |
| `tpl_voter_id` | Voter Id | मतदार ओळखपत्र |  |
| `tpl_voter_id_constituency` | Constituency | मतदारसंघ |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC क्रमांक |  |
| `tpl_voter_id_file_copy` | Scanned copy | स्कॅन केलेली प्रत |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | मतदार ओळखपत्रावरील नाव |  |
| `tpl_voter_id_notes` | Notes | टिपा |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP पासवर्ड |  |
| `tr_days_many` | %1$d days left | %1$d दिवस बाकी |  |
| `tr_days_one` | %1$d day left | %1$d दिवस बाकी |  |
| `tr_gone_today` | gone today | आज जाईल |  |
| `tr_restore` | Restore | परत आणा |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | त्यानंतर त्या कायमच्या जातात — दुसरीकडे कुठेही प्रत नाही. |  |
| `ui_hide_passphrase` | Hide passphrase | पासफ्रेज लपवा |  |
| `ui_label_count` | %1$s, %2$s | %1$s, %2$s |  |
| `ui_show_passphrase` | Show passphrase | पासफ्रेज दाखवा |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | याच फोनवर %1$d नोंदींशी ताळमेळ केला. काहीही कुठेही पाठवले गेले नाही. |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | याच फोनवर %1$d नोंदीशी ताळमेळ केला. काहीही कुठेही पाठवले गेले नाही. |  |
| `vh_count_many` | %1$d things worth a look. | %1$d गोष्टी बघण्यासारख्या आहेत. |  |
| `vh_count_one` | %1$d thing worth a look. | %1$d गोष्ट बघण्यासारखी आहे. |  |
| `vh_empty` | No reused, weak or expiring credentials. | पुन्हा वापरलेली, कमकुवत किंवा संपणारी माहिती नाही. |  |
| `vh_kind_common` | Commonly guessed | सहज ओळखता येणारा |  |
| `vh_kind_expiring` | Expiring | संपणारा |  |
| `vh_kind_reused` | Reused password | पुन्हा वापरलेला पासवर्ड |  |
| `vh_kind_weak` | Weak | कमकुवत |  |
| `vh_no_kit_title` | No recovery kit saved | कोणतीही रिकव्हरी किट सेव्ह केलेली नाही |  |
| `vh_nothing` | Nothing to fix. | दुरुस्त करण्यासारखे काही नाही. |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | ऑफलाइन |  |
| `wl_chip_open` | Open source | ओपन सोर्स |  |
| `wl_create` | Create a new vault | नवी तिजोरी तयार करा |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | ना ईमेल · ना खाते · काहीही या फोनबाहेर नाही |  |
| `wl_head_1` | Your keys. | तुमच्या किल्ल्या. |  |
| `wl_head_2` | Your device. | तुमचा फोन. |  |
| `wl_head_3` | No server. | कोणताही सर्व्हर नाही. |  |
| `wl_restore` | Restore from Recovery Kit | रिकव्हरी किटमधून परत आणा |  |
| `wl_sr_headline` | Your keys. Your device. No server. | तुमच्या किल्ल्या. तुमचा फोन. कोणताही सर्व्हर नाही. |  |
