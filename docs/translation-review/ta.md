# Tamil (`ta`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-ta/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Tamil | ok? |
|---|---|---|---|
| `au_close` | Close | மூடு |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | தேர்ந்தெடுத்த படத்தில் சரியான TOTP QR குறியீடு இல்லை |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | காலியாகத் தொடங்கி உங்கள் புலங்களுக்கு நீங்களே பெயரிடுங்கள் — டெம்ப்ளேட்டுகள் லேபிள்களை மட்டுமே நிரப்பும், தரவை ஒருபோதும் இல்லை. |  |
| `hm_close_search` | Close search | தேடலை மூடு |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | இந்த ஃபோனை விட்டு வெளியே வருவதே இல்லை. சேமிக்கும்போது குறியாக்கம். |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | இப்போது நீங்கள் இறக்குமதி செய்த கோப்பை நீக்குங்கள். அது உங்கள் கடவுச்சொற்களின் வெளிப்படையான பட்டியல், இன்னும் உங்கள் Downloads-இல் கிடக்கிறது. |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | எல்லாம் இந்த ஃபோனில் மட்டுமே மறைநீக்கம் ஆகிறது. எதுவும் பதிவேற்றப்படுவதில்லை, ஏனெனில் இந்த ஆப் நெட்வொர்க் இணைப்பையே திறக்க முடியாது. |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | வங்கிகள் உங்கள் OTP-ஐ ஒருபோதும் கேட்பதில்லை. கேட்பவர் மோசடிக்காரர். |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | எந்த வங்கி அதிகாரியும் திரையைப் பகிரும் ஆப்-ஐ நிறுவச் சொல்ல மாட்டார். |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | உங்கள் UPI பின் UPI ஆப் கீபேடுக்கு மட்டுமே — அழைப்பில் யாரிடமும் சொல்லாதீர்கள். |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | KYC ஒரே நாளில் முடிவதில்லை. “இன்று KYC முடிகிறது” என்ற செய்திகள் மோசடி. |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | பணம் பெற பின் போடவோ QR ஸ்கேன் செய்யவோ தேவையே இல்லை. |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | மின்சாரம் துண்டிக்கப்படும் SMS, அதில் ஒருவரின் தனிப்பட்ட எண்? அது மோசடி. |  |
| `nav_close_menu` | Close menu | மெனுவை மூடு |  |
| `nfc_cannot_read` | Cannot read cards | கார்டுகளைப் படிக்க முடியாது |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | இந்த ஃபோனில் NFC இல்லை, அதனால் கார்டைப் படிக்க முடியாது. |  |
| `ob_fact_lost_title` | If you lose your keys | சாவிகளைத் தொலைத்தால் |  |
| `ob_fact_network_note` | The app literally cannot phone home | இந்த ஆப் எங்கும் தொடர்பு கொள்ளவே முடியாது |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | பயோமெட்ரிக் பாதுகாப்பு சிப்பை விட்டு வெளியே வருவதே இல்லை |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | உங்கள் மறையாக்கப்பட்ட பெட்டகத்தை ஒத்திசைக்கும் அதே கோப்புறையில் சாவியைச் சேமித்துள்ளீர்கள். இப்போது அந்தக் கோப்புறை கிடைப்பவருக்கு இரண்டுமே கிடைக்கும். சாவியை வேறு இடத்தில் வையுங்கள் — காகிதம், வேறு கணக்கு, அல்லது ஒரு அலமாரி. |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | இது உங்கள் பெட்டக கோப்பின் அருகிலேயே உள்ளது |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH மீட்பு |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | ஸ்கேன் செய்யுங்கள் அல்லது தட்டச்சு செய்யுங்கள். மறுநிறுவல், ஃபேக்டரி ரீசெட், ஃபோன் தொலைந்த பிறகும் வேலை செய்யும். |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | இப்போது சேமித்த கிட்டிலிருந்து %1$d, %2$d குழுக்களைத் தட்டச்சு செய்யுங்கள். |  |
| `ob_kit_challenge_hint` | Group %1$d | குழு %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | முதலில் கிட்டைச் சேமியுங்கள், பிறகு %1$d, %2$d குழுக்களைத் திரும்பத் தட்டச்சு செய்யுங்கள். |  |
| `ob_kit_challenge_title` | Check you actually have it | கிட் உண்மையில் உங்களிடம் உள்ளதா என்று பாருங்கள் |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | இது மேலே உள்ள சாவியுடன் பொருந்தவில்லை. |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | யாராலும் — Zerokosh-ஆலும் — அதை எனக்காக மீட்க முடியாது. |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "அதை ஆஃப்லைனில் வைத்துவிட்டேன். " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | ஒருமுறை மட்டுமே தெரியும், திறந்த நிலையில் ஒருபோதும் சேமிக்கப்படுவதில்லை. உள்ளே வர முடிந்தால் அமைப்புகளில் புதிதாக உருவாக்கலாம். |  |
| `ob_kit_head_emph` | On paper. | காகிதத்தில். |  |
| `ob_kit_head_lead` | "One key. " | "ஒரு சாவி. " |  |
| `ob_kit_head_tail` | " Never online." | " ஒருபோதும் ஆன்லைனில் இல்லை." |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | Gmail வேண்டாம், WhatsApp வேண்டாம், ஸ்கிரீன்ஷாட் வேண்டாம். பெட்டகம், வங்கி லாக்கர், அல்லது எஃகுத் தகடு. |  |
| `ob_kit_offline_title` | Keep it off the internet | அதை இணையத்திலிருந்து விலக்கி வையுங்கள் |  |
| `ob_kit_print` | Print | அச்சிடு |  |
| `ob_kit_print_note` | A printer, or Save as PDF | அச்சுப்பொறி, அல்லது PDF ஆக சேமி |  |
| `ob_kit_qr` | QR image | QR படம் |  |
| `ob_kit_qr_cd` | Recovery key QR code | மீட்புச் சாவியின் QR குறியீடு |  |
| `ob_kit_qr_note` | To an offline gallery | ஆஃப்லைன் கேலரிக்கு |  |
| `ob_kit_regenerate` | Regenerate | புதிதாக உருவாக்கு |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | இது சேமிக்கப்படவில்லை. மீண்டும் முயலுங்கள், அல்லது வேறு இடத்தைத் தேர்வு செய்யுங்கள். |  |
| `ob_kit_save_pdf` | Save PDF | PDF சேமி |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | ஒரு பக்க அச்சிடக்கூடிய கிட் |  |
| `ob_kit_saved` | I\'ve saved my kit | என் கிட்டை சேமித்துவிட்டேன் |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s இல் சேமிக்கப்பட்டது |  |
| `ob_kit_sent_to_printer` | Sent to the printer | அச்சுப்பொறிக்கு அனுப்பப்பட்டது |  |
| `ob_kit_skip` | I\'ll do this later | இதை பிறகு செய்கிறேன் |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | உங்கள் பெட்டகம் வேலை செய்யும். கிட் சேமிக்கும் வரை Zerokosh நினைவூட்டிக்கொண்டே இருக்கும். |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | இதே ஃபோனில் உருவானது, ஒருமுறை மட்டும் தெரியும். கடவுச்சொற்றொடரை மறந்தால் உள்ளே திரும்ப வர இதுவே ஒரே வழி. |  |
| `ob_kit_working` | Working… | வேலை நடக்கிறது… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | பெட்டகத்தின் லேபிள்கள், டெம்ப்ளேட்டுகள், எச்சரிக்கைகள் உடனே மாறும். அமைப்புகளில் எப்போது வேண்டுமானாலும் மாற்றலாம். |  |
| `ob_pass_confirm` | Confirm | மீண்டும் தட்டச்சு |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | இது எங்களுக்குத் தெரிவதே இல்லை. மீட்டமைப்பு இணைப்பு இல்லை. |  |
| `ob_pass_head_emph` | held only | உங்களிடம் மட்டும் |  |
| `ob_pass_head_lead` | "One secret, " | "ஒரே ரகசியம், " |  |
| `ob_pass_head_tail` | " by you." | . |  |
| `ob_pass_no_match` | no match | பொருந்தவில்லை |  |
| `ob_pass_seal` | Seal the vault | பெட்டகத்தைப் பூட்டு |  |
| `ob_pass_sealing` | Sealing… | பூட்டப்படுகிறது… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | தொடர்பில்லாத மூன்று நான்கு வார்த்தைகள், ஒரு புத்திசாலி வார்த்தையை விட மேல். இந்தத் திரையிலிருந்து எதுவும் வெளியே போவதில்லை. |  |
| `ob_pass_tab_passphrase` | Passphrase | கடவுச்சொற்றொடர் |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 இலக்க பின் |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | உங்கள் கைரேகை ஃபோனின் பாதுகாப்பு சிப்புக்குள் இருக்கும். அது இந்த ஃபோனை விட்டு வெளியே வருவதே இல்லை. |  |
| `ob_trust_continue` | I understand · Continue | புரிந்தது · தொடரவும் |  |
| `ob_trust_head_emph` | don\'t | தெரியாது |  |
| `ob_trust_head_lead` | "Exactly what we " | "எங்களுக்கு உண்மையில் என்ன " |  |
| `ob_trust_head_tail` | " know." | " என்பது." |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | கடவுச்சொற்றொடரை மறந்து மீட்புக் கிட்டையும் தொலைத்தால், பெட்டகம் பூட்டியே இருக்கும் — உங்களுக்கும், எங்களுக்கும், யாருக்கும். |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "சாவிகள் உங்களிடம். " |  |
| `ob_trust_stat_files` | .kosh file on device | ஃபோனில் .kosh கோப்பு |  |
| `ob_trust_stat_servers` | servers contacted | சர்வருடன் தொடர்பு |  |
| `ob_trust_stat_trackers` | trackers or SDKs | டிராக்கர் அல்லது SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | இதை ஒருமுறை படியுங்கள். முழு பாதுகாப்பு அமைப்பும் இதுதான், எளிய வார்த்தைகளில். |  |
| `ob_trust_tag_audited` | Audited build | தணிக்கை செய்யப்பட்ட பில்ட் |  |
| `ob_trust_tag_reproducible` | Reproducible APK | மீண்டும் உருவாக்கக்கூடிய APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | நீங்கள் கைரேகையால் திறக்கிறீர்கள். கைரேகை எப்போதாவது வேலை செய்யாமல் போனால், இதுவே உங்களை உள்ளே கொண்டுவரும் — எனவே பார்த்துக்கொள்வது நல்லது. |  |
| `pc_confirm` | Check | சரிபார் |  |
| `pc_correct` | Still correct. Nothing to do. | இன்னும் சரியாக உள்ளது. எதுவும் செய்யத் தேவையில்லை. |  |
| `pc_forgot` | I cannot remember it | எனக்கு நினைவில்லை |  |
| `pc_later` | Not now | இப்போது வேண்டாம் |  |
| `pc_reset_action` | Set new passphrase | புதியதை வை |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | உங்கள் கைரேகை இந்தப் பெட்டகத்தைத் திறக்க முடியும், எனவே அதுவே புதிய கடவுச்சொற்றொடரையும் வைக்க முடியும் — மீட்பு கிட் தேவையில்லை. உறுதிப்படுத்த மீண்டும் ஒருமுறை கேட்கப்படும். |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | கடவுச்சொற்றொடர் மாறியது. விரைவு திறப்பு மீண்டும் அமைக்கப்பட்டது. |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | இது வேலை செய்யவில்லை. உங்கள் பழைய கடவுச்சொற்றொடரே இன்னும் பயன்பாட்டில் உள்ளது. |  |
| `pc_reset_title` | Set a new passphrase | புதிய கடவுச்சொற்றொடர் வையுங்கள் |  |
| `pc_title` | Do you still remember your passphrase? | உங்கள் கடவுச்சொற்றொடர் இன்னும் நினைவிருக்கிறதா? |  |
| `pc_wrong` | That is not it. You can set a new one instead. | இது அது இல்லை. அதற்குப் பதிலாக புதியது வைக்கலாம். |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | இந்தப் பதிவுக்காக நினைவில் வைத்த %1$d மதிப்புகள் நீக்கப்படும். இதை மீட்க முடியாது. |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh அந்த லாகினைச் சேமிக்க முடியவில்லை. |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | நிரப்ப Zerokosh-ஐத் திறங்கள் |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | இந்த ஒரு கடவுச்சொற்றொடர்தான் எல்லாவற்றையும் பூட்டி வைக்கிறது. நீளமான, உங்களுக்கு மட்டுமே தெரிந்த ஒன்றைத் தேர்ந்தெடுங்கள். |  |
| `scr_create_button` | Lock it in | பூட்டி விடுங்கள் |  |
| `scr_create_confirm_hint` | Type it again | மீண்டும் தட்டச்சு செய்யுங்கள் |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | கடவுச்சொற்றொடர் (குறைந்தது 10 எழுத்துகள்) |  |
| `scr_create_mismatch` | The two entries don\'t match | இரண்டும் ஒன்றாக இல்லை |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 இலக்க பின் |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | அதற்குப் பதிலாக 6 இலக்க பின் வையுங்கள் |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | பின்னுக்கு கைரேகை அல்லது முக அன்லாக் உள்ள ஃபோன் வேண்டும். கடவுச்சொற்றொடரைத் தேர்ந்தெடுங்கள். |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | பின் அனுமதிக்கப்படுகிறது, ஏனெனில் இந்த ஃபோன் அதை தன் பாதுகாப்பு சிப்பாலும் உங்கள் கைரேகை அல்லது முகத்தாலும் காக்கிறது. |  |
| `scr_create_strength_fair` | Fair | பரவாயில்லை |  |
| `scr_create_strength_good` | Good | நல்லது |  |
| `scr_create_strength_strong` | Strong | வலிமையானது |  |
| `scr_create_strength_weak` | Weak | பலவீனம் |  |
| `scr_create_title` | Create your passphrase | உங்கள் கடவுச்சொற்றொடரை உருவாக்குங்கள் |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | குறைந்தது 10 எழுத்துகள் வையுங்கள் — நீளமானது எவ்வளவோ வலிமையானது |  |
| `scr_create_working` | Preparing your vault… | உங்கள் பெட்டகம் தயாராகிறது… |  |
| `scr_detail_delete` | Delete | நீக்கு |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | அது 30 நாட்களுக்கு “சமீபத்தில் நீக்கியவை”-யில் இருக்கும், உங்கள் மற்ற ஃபோன்களுடன் ஒத்திசைந்ததும் மறையும். |  |
| `scr_detail_delete_confirm_title` | Delete this record? | இந்தப் பதிவை நீக்கவா? |  |
| `scr_detail_delete_confirm_yes` | Delete | நீக்கு |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | சீக்ரெட் அல்லது otpauth:// இணைப்பை ஒட்டவும் |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | உங்கள் கைரேகை அல்லது முகத்தைப் பயன்படுத்துங்கள் |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh திற |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | பல முறை தவறாக முயன்றுவிட்டீர்கள். %1$d வினாடிகள் காத்திருங்கள். |  |
| `scr_lock_hint` | Passphrase | கடவுச்சொற்றொடர் |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | இந்த மீட்புச் சாவி சரியில்லை — ஒவ்வொரு எழுத்தாகச் சரிபாருங்கள் |  |
| `scr_lock_title` | Vault is locked | பெட்டகம் பூட்டப்பட்டுள்ளது |  |
| `scr_lock_unlock` | Unlock | திற |  |
| `scr_lock_use_passphrase` | Use passphrase | கடவுச்சொற்றொடரைப் பயன்படுத்து |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | ஒருமுறை கடவுச்சொற்றொடரால் திறக்கவும் |  |
| `scr_lock_use_recovery` | Use Recovery Key | மீட்புச் சாவியைப் பயன்படுத்து |  |
| `scr_lock_wrong` | Wrong passphrase | கடவுச்சொற்றொடர் தவறு |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | எப்போதாவது கடவுச்சொற்றொடரை மறந்தால், உள்ளே திரும்ப வர இதுவே ஒரே வழி. நாங்கள் அதை மீட்டமைக்க முடியாது — யாராலும் முடியாது. |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | நான் அதை எழுதி வைத்து பாதுகாப்பான இடத்தில் வைத்துவிட்டேன் |  |
| `scr_recovery_done` | Continue | தொடரவும் |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | இந்த சாவி ஒருமுறை மட்டுமே தெரியும். நீங்கள் திறக்க முடிகிற வரை, அமைப்புகளில் இருந்து எப்போது வேண்டுமானாலும் புதிதாக உருவாக்கலாம். |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | இந்தப் பக்கத்தை உங்கள் சொத்து ஆவணங்களுடன் அல்லது மற்ற முக்கிய ஆவணங்களுடன் வையுங்கள். இந்தச் சாவி யாரிடம் இருக்கிறதோ அவர் உங்கள் பெட்டகத்தைத் திறக்க முடியும் — அதை லாக்கர் சாவி போலப் பாதுகாக்கவும். |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | மீட்புக் கிட் PDF சேமிக்கப்பட்டது |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh மீட்புக் கிட் |  |
| `scr_recovery_save_pdf` | Save as PDF | PDF ஆக சேமிக்கவும் |  |
| `scr_recovery_title` | Your Recovery Key | உங்கள் மீட்புச் சாவி |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | நீங்கள் சேமிப்பது எல்லாம் உங்கள் ஃபோனில் உள்ள ஒரு பூட்டிய கோப்பில் இருக்கும். அது எங்களிடம் வருவதே இல்லை — அதை வைக்க எங்களிடம் இடமே இல்லை. |  |
| `scr_trust_card1_title` | Your data stays on this device | உங்கள் தரவு இந்த ஃபோனிலேயே இருக்கும் |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | Zerokosh கணக்கு இல்லை, கிளவுட் இல்லை, பதிவு இல்லை. இதை நீங்கள் மட்டுமே திறக்க முடியும். நாங்களும் முடியாது. |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | எங்களுக்கு சர்வர் இல்லை — ஹேக் செய்ய எதுவும் இல்லை, விற்கவும் எதுவும் இல்லை |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | சந்தா இல்லை, விளம்பரம் இல்லை. யார் வேண்டுமானாலும் எங்கள் கோடைப் படித்து ஒவ்வொரு வாக்குறுதியையும் சரிபார்க்கலாம். |  |
| `scr_trust_card3_title` | Free forever, open source | எப்போதும் இலவசம், ஓபன் சோர்ஸ் |  |
| `scr_trust_continue` | Continue | தொடரவும் |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | உங்கள் மாற்றங்கள் சேமிக்கப்படவில்லை, எனவே பெட்டகத்தில் முன்பு இருந்ததில் எதுவும் இழக்கப்படவில்லை. |  |
| `st_recently_deleted` | Recently deleted | சமீபத்தில் நீக்கியவை |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | ஒரு முறை குறியீடு (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | ஒரு முறை குறியீடு (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | ஒரு முறை குறியீடு (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA சீக்ரெட் |  |
| `tr_cannot_undo` | This cannot be undone. | இதை மீட்க முடியாது. |  |
| `tr_delete_all` | Delete all permanently | எல்லாவற்றையும் நிரந்தரமாக நீக்கு |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d பதிவுகள் நிரந்தரமாகப் போய்விடும். இதை மீட்க முடியாது, மீட்டெடுக்க காப்புப் பிரதியும் இல்லை. |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d பதிவு நிரந்தரமாகப் போய்விடும். இதை மீட்க முடியாது, மீட்டெடுக்க காப்புப் பிரதியும் இல்லை. |  |
| `tr_delete_all_title` | Delete everything in the trash? | குப்பையில் உள்ள எல்லாவற்றையும் நீக்கவா? |  |
| `tr_delete_now` | Delete now | இப்போதே நீக்கு |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | “%1$s”-ஐ நிரந்தரமாக நீக்கவா? |  |
| `tr_empty` | Nothing deleted. | எதுவும் நீக்கப்படவில்லை. |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | நீக்கிய பதிவுகள் இங்கே %1$d நாட்கள் இருக்கும். |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | இந்திய வங்கிகள், UPI, கார்டுகள், டீமேட், EPF, நீங்கள் உண்மையில் பயன்படுத்தும் OTP ஆப்கள் — இவை அனைத்துக்குமாக ஃபோனிலேயே இருக்கும் பெட்டகம். |  |

## Priority 2 — longer prose

| key | English | Tamil | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | அதிகம் பயன்படும் ஒரு தகவலிலிருந்து தொடங்குங்கள். நோட்ஸ் ஆப்-இல் கிடக்கும் பன்னிரண்டு கடவுச்சொற்களை விட சேமித்த ஒரு கடவுச்சொல்லே பாதுகாப்பானது. |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | கடவுச்சொற்றொடரை மறந்தால் மீட்பு கிட் மட்டுமே உள்ளே வரும் வழி. உங்களுக்காக இன்னொன்றை யாராலும் உருவாக்க முடியாது. |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | அந்தக் கோப்பில் அடையாளம் காணக்கூடியது எதுவும் இல்லை. Chrome, Google Password Manager, Bitwarden, LastPass, KeePass ஏற்றுமதிகள் புரியும். |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d ஏற்கனவே உள்ள பதிவுகள் மாற்றப்படும் — தளம் மற்றும் பயனர்பெயரால் பொருத்தப்பட்டு. மாற்றப்பட்ட கடவுச்சொற்கள் ஒவ்வொரு பதிவின் வரலாற்றிலும் மீண்டும் கிடைக்கும். |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d ஏற்கனவே உள்ள பதிவு மாற்றப்படும் — தளம் மற்றும் பயனர்பெயரால் பொருத்தப்பட்டு. மாற்றப்பட்ட கடவுச்சொற்கள் ஒவ்வொரு பதிவின் வரலாற்றிலும் மீண்டும் கிடைக்கும். |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d பதிவுகள் இரு பக்கமும் மாற்றப்பட்டிருந்தன. இரு வடிவங்களும் வைக்கப்பட்டுள்ளன — “(conflict copy)” தேடுங்கள். |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | இந்தக் காப்புக் கோப்பைத் திறக்கும் கடவுச்சொற்றொடரை உள்ளிடுங்கள். அது உங்கள் தற்போதையதிலிருந்து வேறாக இருக்கலாம். |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | .kosh கோப்பின் Poly1305 சான்று டேக் பொருந்தவில்லை. பாதியில் நின்ற ஒத்திசைவு அல்லது மோசமான சேமிப்புக்குப் பிறகு இது நடக்கலாம். |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh பெட்டகக் கோப்புக்கு அருகில் ஒரு நகரும் காப்புப் பிரதியை வைத்திருக்கும். அதை உங்கள் ஒத்திசைவு கோப்புறையிலிருந்து மீட்டெடுங்கள், அல்லது வேறு ஃபோனில் மீட்புக் கிட்டால் பெட்டகத்தைத் திறங்கள். |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | படிக்கும் வரை கார்டை ஃபோனின் பின்பக்கம் நேராக வைத்திருங்கள். இதில் கார்டு எண், காலாவதி, பெயர் கிடைக்கும் — CVV சிப்பில் இல்லை, அதை நீங்களே தட்டச்சு செய்ய வேண்டும். |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | உள்ளே திரும்ப வர விரைவான வழியைத் தேர்ந்தெடுங்கள். பெட்டகத்தைக் காப்பது கடவுச்சொற்றொடர்தான்; இது இந்த ஃபோனில் மட்டும் சாவியைத் திறக்கிறது. |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | இப்போது உங்கள் பெட்டகத்தில் உண்மையான தகவல் உள்ளது. மீட்பு கிட் இல்லாமல் கடவுச்சொற்றொடரை மறந்தால் யாராலும் உங்களை மீண்டும் உள்ளே கொண்டுவர முடியாது — எங்களாலும் முடியாது. |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh இலவசமானது, ஓபன் சோர்ஸ், சர்வரே இல்லாதது. உங்கள் பெட்டகத்தை நீங்கள் மட்டுமே திறக்க முடியும். நாங்களும் முடியாது. |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | QR குறியீட்டை ஸ்கேன் செய்யவே கேமரா அனுமதி தேவை. பதிவு சேர்க்கும்போது சீக்ரெட்டை கையால் ஒட்டவும் முடியும். |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | தானியங்கி நிரப்பை அனுமதிக்காத வங்கி ஆப்களுக்கு — பட்டனை அழுத்தி லாகின் விவரங்களை ஒவ்வொன்றாக நகலெடுங்கள் |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | உங்கள் ஃபோனைத் திறப்பது போலவே பெட்டகத்தையும். கடவுச்சொற்றொடர் எப்போதும் வேலை செய்யும். |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | பெட்டகத்தின் ஸ்கிரீன்ஷாட்கள் கிளவுட் புகைப்பட காப்புப் பிரதிக்குச் சென்றுவிடலாம். மிகவும் தேவைப்பட்டால் மட்டும் இயக்குங்கள். |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | பெட்டகக் கோப்பை எழுத முடியவில்லை. நீங்கள் காப்பு மற்றும் ஒத்திசைவு கோப்புறையை அமைத்திருந்தால், Android அதன் அனுமதியைத் திரும்பப் பெற்றிருக்கலாம் — அமைப்புகளைத் திறந்து, கோப்புறையை மீண்டும் தேர்ந்தெடுத்து, மீண்டும் முயலுங்கள். |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | உங்கள் குறியாக்கப்பட்ட .kosh கோப்புகள் நேரடியாக இந்தக் கோப்புறையிலேயே சேமிக்கப்படுகின்றன. பல சாதனங்களில் தானாகக் காப்பு எடுக்க இந்தக் கோப்புறையை Google Drive, Syncthing, Nextcloud அல்லது SD கார்டுடன் ஒத்திசையுங்கள். |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | புதிய மீட்புச் சாவி உருவாக்க உங்கள் கடவுச்சொற்றொடரை உள்ளிடுங்கள். பழைய சாவி வேலை செய்யாது. |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | இந்தப் பக்கத்தில் மற்ற அனைத்தும் ஒரு உள்நுழைவை பலவீனப்படுத்தும். இது முழு பெட்டகத்தையும் இழக்கச் செய்யும். அமைப்புகள் → புதிய மீட்பு சாவி பெறுங்கள். |  |

## Priority 3 — short labels

| key | English | Tamil | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | வலுவான கடவுச்சொல் பயன்படுத்து |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | கணக்கின் பெயர் (எ.கா. Google) |  |
| `au_active_many` | %1$d active codes | %1$d இயங்கும் குறியீடுகள் |  |
| `au_active_one` | %1$d active code | %1$d இயங்கும் குறியீடு |  |
| `au_add_another` | Add another authenticator | இன்னொரு ஆத்தென்டிகேட்டரைச் சேர் |  |
| `au_add_secret` | Add Secret Key | சீக்ரெட் கீயைச் சேர் |  |
| `au_camera_needed` | Camera permission needed to scan QR code | QR குறியீட்டை ஸ்கேன் செய்ய கேமரா அனுமதி தேவை |  |
| `au_copied` | Copied · clears shortly | நகலெடுக்கப்பட்டது · சிறிது நேரத்தில் அழியும் |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google, GitHub அல்லது உங்கள் புரோக்கரின் QR-ஐ ஸ்கேன் செய்யுங்கள், அல்லது சீக்ரெட் கீயை கையால் தட்டச்சு செய்யுங்கள். |  |
| `au_enter_key` | Enter Key | கீயை உள்ளிடு |  |
| `au_fallback_name` | Authenticator | ஆத்தென்டிகேட்டர் |  |
| `au_flashlight` | Flashlight | டார்ச் |  |
| `au_grant` | Grant Permission | அனுமதி வழங்கு |  |
| `au_image_failed` | Failed to process image | படத்தைப் படிக்க முடியவில்லை |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | தவறான Base32 சீக்ரெட் கீ (A-Z எழுத்துகள், 2-7 இலக்கங்கள் மட்டும்) |  |
| `au_no_match` | No codes match | எந்தக் குறியீடும் கிடைக்கவில்லை |  |
| `au_none_yet` | No codes yet. | இதுவரை குறியீடு இல்லை. |  |
| `au_pick_image` | Pick Image | படத்தைத் தேர்ந்தெடு |  |
| `au_rotating` | "Rotating " | "மாறிக்கொண்டே இருக்கும் " |  |
| `au_rotating_emph` | codes. | குறியீடுகள். |  |
| `au_save_key` | Save Key | கீயைச் சேமி |  |
| `au_scan_qr` | Scan a QR code | QR குறியீட்டை ஸ்கேன் செய் |  |
| `au_scan_title` | Scan Authenticator QR | ஆத்தென்டிகேட்டர் QR-ஐ ஸ்கேன் செய் |  |
| `au_search_hint` | Search codes, issuers… | குறியீடு, வழங்குநரைத் தேடு… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | எ.கா. JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | சீக்ரெட் கீ (Base32) |  |
| `au_tap_to_copy` | Tap to copy | நகலெடுக்கத் தட்டுங்கள் |  |
| `cat_apps` | Apps &amp; Logins | ஆப்கள் மற்றும் லாகின் |  |
| `cat_banks` | Banks &amp; UPI | வங்கிகள் மற்றும் UPI |  |
| `cat_cards` | Cards | கார்டுகள் |  |
| `cat_govid` | Gov &amp; ID | அரசு மற்றும் அடையாளம் |  |
| `cat_investments` | Investments | முதலீடுகள் |  |
| `cat_utilities` | Utilities | பில்கள் மற்றும் இணைப்புகள் |  |
| `cd_mask_hidden` | hidden | மறைந்துள்ளது |  |
| `cd_shield_high_sensitivity` | extra-protected field | கூடுதல் பாதுகாப்பான தகவல் |  |
| `gl_blank` | Blank template | காலி டெம்ப்ளேட் |  |
| `gl_cat_apps` | Apps | ஆப்கள் |  |
| `gl_cat_banks` | Banks | வங்கிகள் |  |
| `gl_cat_cards` | Cards | கார்டுகள் |  |
| `gl_cat_demat` | Demat | டீமேட் |  |
| `gl_cat_govid` | Gov ID | அரசு அடையாளம் |  |
| `gl_cat_popular` | Popular | பிரபலம் |  |
| `gl_cat_shopping` | Shopping | ஷாப்பிங் |  |
| `gl_cat_travel` | Travel | பயணம் |  |
| `gl_cat_upi` | UPI | UPI |  |
| `gl_cat_utilities` | Utilities | பில்கள் |  |
| `gl_head_emph` | storing? | சேமிக்கிறோம்? |  |
| `gl_head_lead` | "What are we " | "நாம் எதைச் " |  |
| `gl_matches` | %1$d matches | %1$d கிடைத்தன |  |
| `gl_most_used` | Most-used first | அதிகம் பயன்படுத்தியவை முதலில் |  |
| `gl_not_found` | Can’t find a service? | சேவை கிடைக்கவில்லையா? |  |
| `gl_search` | Search %1$d Indian services… | %1$d இந்திய சேவைகளில் தேடு… |  |
| `gl_suggested` | Suggested for you | உங்களுக்கான பரிந்துரை |  |
| `hm_add_first` | Add your first record | உங்கள் முதல் பதிவைச் சேருங்கள் |  |
| `hm_all_offline` | all offline. | எல்லாம் ஆஃப்லைன். |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d தகவல்கள், " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d தகவல், " |  |
| `hm_detail_placeholder` | Pick a record to see it here | இங்கே காண ஒரு பதிவைத் தேர்ந்தெடுக்கவும் |  |
| `hm_empty_blank` | A blank vault, ready. | காலியான பெட்டகம், தயார். |  |
| `hm_empty_head_emph` | waiting. | காத்திருக்கிறது. |  |
| `hm_empty_head_lead` | "Your vault is " | "உங்கள் பெட்டகம் " |  |
| `hm_filter_all` | All | அனைத்தும் |  |
| `hm_import_backup` | Import an encrypted backup | குறியாக்கப்பட்ட காப்புப் பிரதியை இறக்குமதி |  |
| `hm_import_backup_note` | Open a .kosh file from this device | இதே ஃபோனிலிருந்து .kosh கோப்பைத் திறங்கள் |  |
| `hm_inst_many` | %1$d institutions | %1$d நிறுவனங்கள் |  |
| `hm_inst_one` | %1$d institution | %1$d நிறுவனம் |  |
| `hm_kit_banner_action` | Save one now | இப்போதே சேமி |  |
| `hm_kit_banner_dismiss` | Remind me later | பிறகு நினைவூட்டு |  |
| `hm_kit_banner_title` | No recovery kit saved | மீட்பு கிட் எதுவும் சேமிக்கப்படவில்லை |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | திறந்துள்ளது · வெளியேறியதும் பூட்டும் |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | திறந்துள்ளது · வெளியேறி %1$d நிமிடங்களில் பூட்டும் |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | திறந்துள்ளது · வெளியேறி 1 நிமிடத்தில் பூட்டும் |  |
| `hm_no_match` | Nothing matches “%1$s” | “%1$s”-க்கு எதுவும் கிடைக்கவில்லை |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | ஒரு நிறுவனம், UPI ஹேண்டில், அல்லது கடைசி நான்கு இலக்கங்களை முயலுங்கள். |  |
| `hm_pinned` | Pinned | பின் செய்யப்பட்டவை |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC, priya@upi, PAN… தேடு |  |
| `hm_start_template` | Start with a template | டெம்ப்ளேட்டிலிருந்து தொடங்குங்கள் |  |
| `ic_could_not` | Could not import | இறக்குமதி செய்ய முடியவில்லை |  |
| `ic_done` | Done | முடிந்தது |  |
| `ic_import` | Import | இறக்குமதி |  |
| `ic_imported` | Imported | இறக்குமதி ஆனது |  |
| `ic_importing` | Importing… | இறக்குமதி ஆகிறது… |  |
| `ic_new_many` | %1$d new logins. | %1$d புதிய லாகின்கள். |  |
| `ic_new_one` | %1$d new login. | %1$d புதிய லாகின். |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d சேர்க்கப்பட்டது, %2$d மாற்றப்பட்டது. |  |
| `ic_title` | Import from %1$s? | %1$s-இலிருந்து இறக்குமதி செய்யவா? |  |
| `ic_too_large` | That file is too large to be a credential export. | இந்தக் கோப்பு கடவுச்சொல் ஏற்றுமதியாக இருக்க முடியாத அளவு பெரியது. |  |
| `import_action` | Import | இறக்குமதி |  |
| `import_locked` | Unlock your vault before importing. | இறக்குமதி செய்வதற்கு முன் உங்கள் பெட்டகத்தைத் திறங்கள். |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d சேர்க்கப்பட்டது, %2$d மாற்றப்பட்டது. எதுவும் அழிக்கப்படவில்லை. |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | அந்தக் கோப்பை Zerokosh பெட்டகமாகப் படிக்க முடியவில்லை. |  |
| `import_nothing_new` | Everything in that backup was already here. | அந்தக் காப்புப் பிரதியில் இருந்த எல்லாம் ஏற்கனவே இங்கே இருந்தது. |  |
| `import_passphrase_label` | Backup passphrase | காப்புப் பிரதியின் கடவுச்சொற்றொடர் |  |
| `import_title` | Import a backup | காப்புப் பிரதியை இறக்குமதி செய் |  |
| `kicker_locked` | Locked | பூட்டியுள்ளது |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · எதுவும் இந்த ஃபோனை விட்டு வெளியே போகவில்லை |  |
| `lk_touch_unlock` | Touch to unlock | திறக்கத் தொடுங்கள் |  |
| `lk_welcome_emph` | Your vault is sealed. | உங்கள் பெட்டகம் பூட்டியுள்ளது. |  |
| `lk_welcome_lead` | Welcome back. | மீண்டும் வருக. |  |
| `msg_auth_needed` | Confirm it\'s you to see this | பார்க்க நீங்கள்தான் என்று உறுதிப்படுத்துங்கள் |  |
| `msg_back` | Back | பின் |  |
| `msg_cancel` | Cancel | ரத்து |  |
| `msg_file_damaged` | File damaged — restored from backup | கோப்பு சேதமடைந்திருந்தது — காப்புப் பிரதியிலிருந்து சரிசெய்யப்பட்டது |  |
| `msg_ok` | OK | சரி |  |
| `msg_saved` | Saved | சேமிக்கப்பட்டது |  |
| `nav_all_templates` | All templates | எல்லா டெம்ப்ளேட்டுகளும் |  |
| `nav_damaged_emph` | vault file | பெட்டகக் கோப்பில் |  |
| `nav_damaged_kicker` | Damaged state | சேதமான நிலை |  |
| `nav_damaged_lead` | "Something in the " | "உங்கள் " |  |
| `nav_damaged_tail` | " is off." | " ஏதோ சரியில்லை." |  |
| `nav_integrity_title` | Integrity check failed | ஒருமைப்பாடு சோதனை தோல்வி |  |
| `nav_scan` | Scan | ஸ்கேன் |  |
| `nav_tap_card` | Tap a card | கார்டைத் தட்டுங்கள் |  |
| `nav_what_next` | What to do next | இனி என்ன செய்வது |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC அணைந்துள்ளது. அமைப்புகளில் இயக்கிவிட்டு மீண்டும் முயலுங்கள். |  |
| `nfc_hold_card` | Hold your card to the phone | கார்டை ஃபோனில் வையுங்கள் |  |
| `nfc_missed` | Did not catch that | பிடிபடவில்லை |  |
| `nfc_read_failed` | That card could not be read. Try again. | அந்தக் கார்டைப் படிக்க முடியவில்லை. மீண்டும் முயலுங்கள். |  |
| `nfc_reading` | Reading… | படிக்கப்படுகிறது… |  |
| `nfc_try_again` | Try again | மீண்டும் முயலுங்கள் |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · இதே ஃபோனில் அளக்கப்பட்டது |  |
| `ob_argon_faster` | Faster unlock | விரைவாகத் திறக்கும் |  |
| `ob_argon_harder` | Harder to attack | உடைக்கக் கடினம் |  |
| `ob_argon_measuring` | Measuring this device… | இந்த ஃபோன் அளக்கப்படுகிறது… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id கடினத்தன்மை |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | அகராதி வார்த்தை ஒன்று கூட இல்லை |  |
| `ob_check_pass_length` | 10 characters or more | 10 அல்லது அதற்கு மேல் எழுத்துகள் |  |
| `ob_check_pass_reuse` | Not reused from another app | வேறு ஆப்-இலிருந்து மீண்டும் பயன்படுத்தியது இல்லை |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | பிறந்தநாளும் இல்லை, ஆண்டுவிழாவும் இல்லை |  |
| `ob_check_pin_digits` | All six digits entered | ஆறு இலக்கங்களும் நிரப்பப்பட்டன |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | தொடர் இலக்கமும் இல்லை, மீள்வதும் இல்லை |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~உடைக்க %1$d நூற்றாண்டுகள் |  |
| `ob_crack_days` | ~%1$d days to crack | ~உடைக்க %1$d நாட்கள் |  |
| `ob_crack_forever` | longer than the sun | சூரியனை விட நீண்ட காலம் |  |
| `ob_crack_hours` | ~hours to crack | ~உடைக்க சில மணி நேரம் |  |
| `ob_crack_seconds` | ~seconds to crack | ~உடைக்க சில வினாடிகள் |  |
| `ob_crack_years` | ~%1$d years to crack | ~உடைக்க %1$d ஆண்டுகள் |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | சான்றளிக்கப்பட்ட, ஒவ்வொரு பெட்டகத்துக்கும் தனி நான்ஸ் |  |
| `ob_fact_encryption_title` | Encryption | குறியாக்கம் |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | அமைக்கும்போது உங்கள் ஃபோனில் அளக்கப்பட்டது |  |
| `ob_fact_kdf_title` | Key stretching | கீ ஸ்ட்ரெச்சிங் |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | மீட்டமைப்பு இணைப்பு இல்லை. சப்போர்ட் பின்கதவும் இல்லை. |  |
| `ob_fact_lost_value` | Nobody can recover it | யாராலும் மீட்க முடியாது |  |
| `ob_fact_network_title` | Network permission | நெட்வொர்க் அனுமதி |  |
| `ob_fact_network_value` | Not requested | கேட்கவே இல்லை |  |
| `ob_fact_quick_title` | Quick unlock | விரைவு அன்லாக் |  |
| `ob_fact_quick_value` | Hardware keystore | ஹார்ட்வேர் கீஸ்டோர் |  |
| `ob_lang_continue` | Continue in %1$s | %1$s-இல் தொடரவும் |  |
| `ob_lang_head_emph` | language. | மொழியைத் தேர்ந்தெடுங்கள். |  |
| `ob_lang_head_lead` | "Choose your " | "உங்கள் " |  |
| `ob_lang_search` | Search %1$d languages | %1$d மொழிகளில் தேடு |  |
| `ob_quick_continue_pass` | Continue with passphrase | கடவுச்சொற்றொடருடன் தொடரவும் |  |
| `ob_quick_enable` | Enable quick unlock | விரைவு அன்லாக்-ஐ இயக்கு |  |
| `ob_quick_fingerprint` | Fingerprint | கைரேகை |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | வேகமான, ஹார்ட்வேர் பாதுகாப்புள்ள அன்லாக். |  |
| `ob_quick_head_emph` | Without the cloud. | கிளவுட் இல்லாமல். |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "ஒரு தொடுதலில் திறக்கும். " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox. எந்த பயோமெட்ரிக் தகவலும் Zerokosh வரை வருவதே இல்லை. |  |
| `ob_quick_hw_title` | Hardware-backed | ஹார்ட்வேர் பாதுகாப்பு |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | இந்த ஃபோனில் ஹார்ட்வேர் சென்சார் இல்லை. |  |
| `ob_quick_opening` | Opening your vault… | உங்கள் பெட்டகம் திறக்கிறது… |  |
| `ob_quick_pass_only` | Passphrase only | கடவுச்சொற்றொடர் மட்டும் |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | ஒவ்வொரு முறையும் தட்டச்சு செய்யுங்கள். மிகவும் பாதுகாப்பானது. |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | விரைவு அன்லாக் அமையவில்லை. மீண்டும் முயலுங்கள், அல்லது கடவுச்சொற்றொடருடன் மட்டும் தொடருங்கள். |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | இப்போது வேண்டாம் — நான் கடவுச்சொற்றொடரைத் தட்டச்சு செய்கிறேன் |  |
| `ob_quick_touch_title` | Touch the sensor | சென்சாரைத் தொடுங்கள் |  |
| `ob_recommended` | Recommended | பரிந்துரை |  |
| `ob_reveal_hide` | Hide | மறை |  |
| `ob_reveal_show` | Show | காட்டு |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | மடிக்கும் சாவி ஹார்ட்வேர் கீஸ்டோரில் இருக்கும். பயோமெட்ரிக் அடுத்த படியில். |  |
| `ob_seal_title` | Seal to this device | இதே ஃபோனுடன் இணைத்து விடு |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | இந்த ஃபோனில் ஹார்ட்வேர் பயோமெட்ரிக் இல்லை. |  |
| `ob_soon` | SOON | விரைவில் |  |
| `ob_step_label` | Step %1$d of 6 | படி %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | நீங்கள் மட்டுமே சொல்லும் ஒன்றைத் தேர்ந்தெடுங்கள் |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | நல்லது · %1$d பிட் என்ட்ரோபி |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | மிகச் சிறியது · 10 எழுத்துகள் வேண்டும் |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | வலிமையானது · %1$d பிட் என்ட்ரோபி |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | பலவீனம் · %1$d பிட் என்ட்ரோபி |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | உங்கள் வாழ்க்கையைப் பார்த்து யாரும் ஊகிக்க முடியாத ஆறு இலக்கங்கள் |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | பரவாயில்லை · %1$d பிட் — பின் இதற்கு மேல் வலுவாக முடியாது |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | மிகச் சிறியது · 6 இலக்கங்கள் வேண்டும் |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | பலவீனம் · இந்தப் பின்களைத்தான் முதலில் முயல்கிறார்கள் |  |
| `ob_try_label` | TRY | முயற்சி |  |
| `qa_aadhaar` | Aadhaar | ஆதார் |  |
| `qa_bank_account` | Bank account | வங்கிக் கணக்கு |  |
| `qa_pan` | PAN | PAN |  |
| `qa_upi_id` | UPI ID | UPI ID |  |
| `rd_copied` | Copied | நகலெடுக்கப்பட்டது |  |
| `rd_forget` | Forget | மற |  |
| `rd_forget_these` | Forget these | இவற்றை மறந்துவிடு |  |
| `rd_forget_title` | Forget previous passwords? | முந்தைய கடவுச்சொற்களை மறக்கவா? |  |
| `rd_history_hide` | Hide | மறை |  |
| `rd_history_show` | Show %1$d | %1$d காட்டு |  |
| `rd_hold_to_reveal` | Hold to reveal | பார்க்க அழுத்திப் பிடியுங்கள் |  |
| `rd_last_edit` | last edit %1$s | கடைசி மாற்றம் %1$s |  |
| `rd_release_to_hide` | Release to hide | மறைக்க விடுங்கள் |  |
| `re_add_field` | + Add another field | + இன்னொரு புலம் சேர் |  |
| `re_add_field_title` | Add a field | புலம் சேர் |  |
| `re_field_name` | Field name | புலத்தின் பெயர் |  |
| `re_pick_date` | Pick a date | தேதியைத் தேர்ந்தெடு |  |
| `re_remove` | Remove | நீக்கு |  |
| `re_tap_card` | Read the card by tapping it | கார்டைத் தட்டிப் படி |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | ரகசியமாகக் கருது (மறைந்திருக்கும், பார்க்க அழுத்திப் பிடி) |  |
| `re_using_template` | using the %1$s template | %1$s டெம்ப்ளேட்டைப் பயன்படுத்தி |  |
| `rem_kit_title` | No recovery kit saved | மீட்பு கிட் எதுவும் சேமிக்கப்படவில்லை |  |
| `scr_about_license` | License: GPL-3.0 — free forever | உரிமம்: GPL-3.0 — எப்போதும் இலவசம் |  |
| `scr_about_source` | Source code | சோர்ஸ் கோட் |  |
| `scr_about_version` | Version %1$s | பதிப்பு %1$s |  |
| `scr_auth_add_qr` | Add via QR | QR மூலம் சேர் |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | உங்கள் ஆப் மற்றும் புரோக்கர் குறியீடுகள் இங்கே தெரியும் |  |
| `scr_auth_scan_title` | Point the camera at the QR code | கேமராவை QR குறியீட்டின் மேல் வையுங்கள் |  |
| `scr_detail_copied` | Copied · clears in 30s | நகலெடுக்கப்பட்டது · 30 வினாடிகளில் அழியும் |  |
| `scr_detail_copy` | Copy | நகலெடு |  |
| `scr_detail_edit` | Edit | மாற்று |  |
| `scr_detail_favorite` | Favourite | பிடித்தது |  |
| `scr_detail_hidden` | Hidden | மறைந்துள்ளது |  |
| `scr_detail_hide` | Hide | மறை |  |
| `scr_detail_history_empty` | Nothing replaced yet. | இதுவரை எதுவும் மாற்றப்படவில்லை. |  |
| `scr_detail_history_title` | Previous passwords | முந்தைய கடவுச்சொற்கள் |  |
| `scr_detail_reveal` | Show | காட்டு |  |
| `scr_detail_shown` | Shown | தெரிகிறது |  |
| `scr_edit_cancel` | Cancel | ரத்து |  |
| `scr_edit_generate` | Generate | உருவாக்கு |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | வங்கி / நிறுவனம் (குழுவாக்க) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | இது சரியாகத் தெரியவில்லை — ஒருமுறை பாருங்கள் |  |
| `scr_edit_link_none` | None | எதுவும் இல்லை |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | இந்த கார்டு எண் வழக்கமான சோதனையில் தேறவில்லை — சரியென்றால் சேமித்து விடுங்கள் |  |
| `scr_edit_month` | Month | மாதம் |  |
| `scr_edit_picker_other` | Other… | மற்றவை… |  |
| `scr_edit_picker_other_hint` | Type your own | உங்களுடையதை எழுதுங்கள் |  |
| `scr_edit_required_title` | Give it a name first | முதலில் இதற்கு ஒரு பெயர் கொடுங்கள் |  |
| `scr_edit_save` | Save | சேமி |  |
| `scr_edit_title_hint` | Title | பெயர் |  |
| `scr_edit_title_new` | New | புதியது |  |
| `scr_edit_year` | Year | ஆண்டு |  |
| `scr_gallery_quick_add` | Quick add | விரைவாகச் சேர்க்கவும் |  |
| `scr_gallery_title` | What do you want to save? | நீங்கள் எதைச் சேமிக்க விரும்புகிறீர்கள்? |  |
| `scr_home_add` | Add | சேர்க்கவும் |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | உங்கள் வங்கிக் கணக்கு இப்படித் தெரியும் |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | உங்கள் கார்டுகள், UPI, ஆப் லாகின் எல்லாம் இங்கேதான் இருக்கும் |  |
| `scr_home_group_other` | Other | மற்றவை |  |
| `scr_home_no_results` | Nothing matches your search | உங்கள் தேடலுக்கு எதுவும் கிடைக்கவில்லை |  |
| `scr_home_search_hint` | Search your vault | உங்கள் பெட்டகத்தில் தேடுங்கள் |  |
| `scr_home_tab_authenticator` | Authenticator | குறியீடுகள் |  |
| `scr_home_tab_home` | Home | முகப்பு |  |
| `scr_home_tab_settings` | Settings | அமைப்புகள் |  |
| `scr_home_title` | Home | முகப்பு |  |
| `scr_language_continue` | Continue | தொடரவும் |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | உங்கள் மொழியைத் தேர்ந்தெடுங்கள் |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | நகலெடுக்க பட்டனை அழுத்துங்கள் · 30 வினாடிகளில் அழியும் |  |
| `scr_login_helper_channel` | Login helper | லாகின் உதவியாளர் |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s-இல் லாகின் ஆகிறது |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | இப்போதே காப்புப் பிரதி கோப்புறையை அமைக்கவும் |  |
| `scr_quickunlock_enable` | Turn on | இயக்கவும் |  |
| `scr_quickunlock_skip` | Not now | இப்போது வேண்டாம் |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | கைரேகை அல்லது முகத்தால் திறங்கள் |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s-இன் தேதி நெருங்குகிறது · Zerokosh-ஐத் திறங்கள் |  |
| `scr_reminder_channel` | Renewal reminders | புதுப்பித்தல் நினைவூட்டல் |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh நினைவூட்டுகிறது |  |
| `scr_settings_about` | About | பற்றி |  |
| `scr_settings_allow_screenshots` | Allow screenshots | ஸ்கிரீன்ஷாட் எடுக்க அனுமதி |  |
| `scr_settings_autofill` | Autofill service | தானியங்கி நிரப்பு சேவை |  |
| `scr_settings_autofill_off` | Not set up | அமைக்கப்படவில்லை |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | கிடைக்கவில்லை |  |
| `scr_settings_autolock` | Lock when I leave the app | ஆப்-ஐ விட்டு வெளியேறியதும் பூட்டு |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 நிமிடம் கழித்து |  |
| `scr_settings_autolock_immediately` | Immediately | உடனே |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d நிமிடங்கள் கழித்து |  |
| `scr_settings_change_passphrase` | Change passphrase | கடவுச்சொற்றொடரை மாற்று |  |
| `scr_settings_current_passphrase` | Current passphrase | தற்போதைய கடவுச்சொற்றொடர் |  |
| `scr_settings_export` | Export | ஏற்றுமதி செய் |  |
| `scr_settings_import` | Import passwords | கடவுச்சொற்களை இறக்குமதி செய் |  |
| `scr_settings_language` | Language | மொழி |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | புதிய கடவுச்சொற்றொடர் (குறைந்தது 10 எழுத்துகள்) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | புதிய மீட்புச் சாவி பெறு |  |
| `scr_settings_passphrase_changed` | Passphrase changed | கடவுச்சொற்றொடர் மாற்றப்பட்டது |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | கைரேகை / முக அன்லாக் |  |
| `scr_settings_security_info` | How your data is protected | உங்கள் தரவு எப்படிப் பாதுகாக்கப்படுகிறது |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | காப்புப் பிரதி மற்றும் ஒத்திசைவு கோப்புறை |  |
| `scr_settings_sync_not_set` | Not backed up | காப்புப் பிரதி இல்லை |  |
| `scr_settings_title` | Settings | அமைப்புகள் |  |
| `se_title` | Not saved | சேமிக்கப்படவில்லை |  |
| `st_active_folder` | Active Folder | இயங்கும் கோப்புறை |  |
| `st_active_value` | Active · %1$s | இயங்குகிறது · %1$s |  |
| `st_backing_up` | Backing up vault… | பெட்டகம் காப்பு எடுக்கப்படுகிறது… |  |
| `st_backup_now` | Backup Now | இப்போதே காப்பு எடு |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | காப்புப் பிரதி மற்றும் ஒத்திசைவு கோப்புறை |  |
| `st_change_folder` | Change Folder | கோப்புறையை மாற்று |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | புதிய கடவுச்சொற்றொடரை மீண்டும் தட்டச்சு |  |
| `st_connected_folder` | Connected folder: %1$s | இணைக்கப்பட்ட கோப்புறை: %1$s |  |
| `st_disconnect` | Disconnect | நீக்கு |  |
| `st_done` | Done | முடிந்தது |  |
| `st_export_kosh` | Export encrypted .kosh | குறியாக்கப்பட்ட .kosh ஏற்றுமதி |  |
| `st_folder_fallback` | Folder | கோப்புறை |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | கடவுச்சொற்றொடரை மறந்துவிட்டீர்களா? |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | உங்கள் கைரேகையால் புதியது வையுங்கள் |  |
| `st_generate` | Generate | உருவாக்கு |  |
| `st_group_about` | About | பற்றி |  |
| `st_group_appearance` | Appearance | தோற்றம் |  |
| `st_group_security` | Security | பாதுகாப்பு |  |
| `st_group_sync` | Sync | ஒத்திசைவு |  |
| `st_import_kosh` | Import a .kosh backup | .kosh காப்புப் பிரதியை இறக்குமதி |  |
| `st_import_other` | Import from another password manager | வேறு கடவுச்சொல் மேலாளரிலிருந்து இறக்குமதி |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | உரிமம் |  |
| `st_logos_by` | Logos provided by | லோகோ வழங்கியவர் |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | அதை ஆஃப்லைனில் வையுங்கள். பழைய மீட்புச் சாவி இனி செல்லாது. |  |
| `st_new_recovery_result` | Your new Recovery Key: | உங்கள் புதிய மீட்புச் சாவி: |  |
| `st_subtitle` | Your rules. | உங்கள் விதிகள். |  |
| `st_theme` | Theme | தீம் |  |
| `st_theme_dark` | Dark | கரும் |  |
| `st_theme_light` | Light | வெளிர் |  |
| `st_theme_system` | System | சிஸ்டம் |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | பெட்டகம் %1$s-இல் சேமிக்கப்பட்டது! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | காப்புப் பிரதி தோல்வி — கோப்புறை அனுமதியைப் பாருங்கள் |  |
| `st_toast_disconnected` | Backup folder disconnected | காப்புக் கோப்புறை நீக்கப்பட்டது |  |
| `st_toast_export_failed` | Export failed | ஏற்றுமதி தோல்வி |  |
| `st_toast_exported` | Encrypted vault exported | குறியாக்கப்பட்ட பெட்டகம் ஏற்றுமதி ஆனது |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | காப்புக் கோப்புறை இணைக்கப்பட்டு பெட்டகம் %1$s-இல் சேமிக்கப்பட்டது! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | காப்புக் கோப்புறை இணைக்கப்பட்டது: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | கோப்புறை இணைக்க முடியவில்லை: %1$s |  |
| `st_vault_review` | Vault review | பெட்டக சோதனை |  |
| `st_vault_review_detail` | Reused, weak, expiring | மீண்டும் பயன்படுத்தியவை, பலவீனமானவை, முடியப் போகின்றவை |  |
| `tab_codes` | Codes | குறியீடுகள் |  |
| `tab_settings` | Settings | அமைப்புகள் |  |
| `tab_templates` | Templates | டெம்ப்ளேட் |  |
| `tab_vault` | Vault | பெட்டகம் |  |
| `time_days` | %1$dd ago | %1$d நா முன் |  |
| `time_hours` | %1$dh ago | %1$d ம முன் |  |
| `time_just_now` | just now | இப்போதுதான் |  |
| `time_minutes` | %1$dm ago | %1$d நி முன் |  |
| `time_months` | %1$dmo ago | %1$d மாதம் முன் |  |
| `time_years` | %1$dy ago | %1$d ஆண்டு முன் |  |
| `tpl_aadhaar_card` | Aadhaar Card | ஆதார் |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | ஆதார் எண் |  |
| `tpl_aadhaar_card_address` | Address | ஆதாரில் உள்ள முகவரி |  |
| `tpl_aadhaar_card_dob` | Dob | பிறந்த தேதி |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | ஸ்கேன் செய்த நகல் |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | இணைந்த மொபைல் |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar பாஸ்கோட் |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | ஆதாரில் உள்ள பெயர் |  |
| `tpl_aadhaar_card_notes` | Notes | குறிப்புகள் |  |
| `tpl_app_profile` | App Profile | ஆப் சுயவிவரம் |  |
| `tpl_app_profile_app_name` | App name | ஆப் பெயர் |  |
| `tpl_app_profile_gift_cards` | Gift cards | பரிசு அட்டைகள் |  |
| `tpl_app_profile_membership` | Membership | உறுப்பினர் |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | உறுப்பினர் புதுப்பித்தல் |  |
| `tpl_app_profile_notes` | Notes | குறிப்புகள் |  |
| `tpl_app_profile_password_if_any` | Password (if any) | கடவுச்சொல் (இருந்தால்) |  |
| `tpl_app_profile_registered_email` | Registered email | பதிவு செய்த ஈமெயில் |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | பதிவு செய்த மொபைல் |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | வாலட் பின் |  |
| `tpl_bank_account` | Bank Account | வங்கிக் கணக்கு |  |
| `tpl_bank_account_account_number` | Account number | கணக்கு எண் |  |
| `tpl_bank_account_account_type` | Account type | கணக்கு வகை |  |
| `tpl_bank_account_bank_name` | Bank name | வங்கியின் பெயர் |  |
| `tpl_bank_account_branch` | Branch | கிளை |  |
| `tpl_bank_account_customer_id` | Customer id | வாடிக்கையாளர் ID |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC குறியீடு |  |
| `tpl_bank_account_login_password` | Login password | லாகின் கடவுச்சொல் |  |
| `tpl_bank_account_micr` | MICR code | MICR குறியீடு |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | நெட்-பேங்கிங் பயனர் ID |  |
| `tpl_bank_account_nominee` | Nominee | நாமினி |  |
| `tpl_bank_account_notes` | Notes | குறிப்புகள் |  |
| `tpl_bank_account_profile_password` | Profile password | சுயவிவர கடவுச்சொல் |  |
| `tpl_bank_account_registered_email` | Registered email | பதிவு செய்த ஈமெயில் |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | பதிவு செய்த மொபைல் |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | பரிவர்த்தனை கடவுச்சொல் |  |
| `tpl_card` | Card | கார்டு |  |
| `tpl_card_atm_pin` | ATM PIN | ATM பின் |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | பில்லிங் சுழற்சி நாள் |  |
| `tpl_card_card_network` | Card network | நெட்வொர்க் |  |
| `tpl_card_card_number` | Card number | கார்டு எண் |  |
| `tpl_card_card_portal_login` | Card portal login | கார்டு போர்டல் லாகின் |  |
| `tpl_card_card_portal_password` | Card portal password | கார்டு போர்டல் கடவுச்சொல் |  |
| `tpl_card_card_type` | Card type | கார்டு வகை |  |
| `tpl_card_card_variant` | Card variant | கார்டு வேரியன்ட் |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | காலாவதி |  |
| `tpl_card_linked_account` | Linked account | இணைந்த கணக்கு |  |
| `tpl_card_name_on_card` | Name on card | கார்டில் உள்ள பெயர் |  |
| `tpl_card_notes` | Notes | குறிப்புகள் |  |
| `tpl_demat` | Demat | டீமேட் |  |
| `tpl_demat_api_key` | API key | API கீ |  |
| `tpl_demat_api_secret` | API secret | API சீக்ரெட் |  |
| `tpl_demat_bo_id` | BO ID | BO ID |  |
| `tpl_demat_broker` | Broker | புரோக்கர் |  |
| `tpl_demat_cdsl_tpin` | CDSL TPIN | CDSL TPIN |  |
| `tpl_demat_client_id` | Client id | கிளையன்ட் ID |  |
| `tpl_demat_depository` | Depository | டெபாசிட்டரி |  |
| `tpl_demat_dp_id` | DP ID | DP ID |  |
| `tpl_demat_login_password` | Login password | லாகின் கடவுச்சொல் |  |
| `tpl_demat_mf_folios` | Mutual fund folios | மியூச்சுவல் ஃபண்ட் ஃபோலியோ |  |
| `tpl_demat_mpin` | MPIN | MPIN |  |
| `tpl_demat_nominee` | Nominee | நாமினி |  |
| `tpl_demat_notes` | Notes | குறிப்புகள் |  |
| `tpl_digilocker` | Digilocker | DigiLocker |  |
| `tpl_digilocker_login_username` | Login username | பயனர்பெயர் |  |
| `tpl_digilocker_notes` | Notes | குறிப்புகள் |  |
| `tpl_digilocker_portal_password` | Portal password | கடவுச்சொல் |  |
| `tpl_digilocker_security_pin` | Security pin | பாதுகாப்பு பின் |  |
| `tpl_driving_license` | Driving License | ஓட்டுநர் உரிமம் |  |
| `tpl_driving_license_dl_number` | Dl number | உரிம எண் |  |
| `tpl_driving_license_dob` | Dob | பிறந்த தேதி |  |
| `tpl_driving_license_expiry_date` | Expiry date | இந்தத் தேதி வரை செல்லும் |  |
| `tpl_driving_license_file_copy` | Scanned copy | ஸ்கேன் செய்த நகல் |  |
| `tpl_driving_license_issue_date` | Issue date | வழங்கிய தேதி |  |
| `tpl_driving_license_name_on_dl` | Name on dl | உரிமத்தில் உள்ள பெயர் |  |
| `tpl_driving_license_notes` | Notes | குறிப்புகள் |  |
| `tpl_driving_license_rto_location` | Rto location | RTO |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | வாகன வகைகள் |  |
| `tpl_epf_pension` | Epf Pension | EPF / ஓய்வூதியம் |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | இணைந்த மொபைல் |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | EPF-இல் உள்ள பெயர் |  |
| `tpl_epf_pension_nominee` | Nominee | நாமினி |  |
| `tpl_epf_pension_notes` | Notes | குறிப்புகள் |  |
| `tpl_epf_pension_password` | Password | கடவுச்சொல் |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | PF உறுப்பினர் ID |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO கடவுச்சொல் |  |
| `tpl_epf_pension_scheme` | Scheme | திட்டம் |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | அரசு அடையாள அட்டை |  |
| `tpl_gov_id_expiry` | Expiry | காலாவதி |  |
| `tpl_gov_id_file_copy` | Scanned copy | ஸ்கேன் செய்த நகல் |  |
| `tpl_gov_id_id_kind` | ID type | அடையாள அட்டை வகை |  |
| `tpl_gov_id_id_number` | ID number | அடையாள எண் |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | அட்டையின்படி பெயர் |  |
| `tpl_gov_id_notes` | Notes | குறிப்புகள் |  |
| `tpl_gov_id_portal_login` | Portal login | போர்டல் லாகின் |  |
| `tpl_gov_id_portal_password` | Portal password | போர்டல் கடவுச்சொல் |  |
| `tpl_insurance` | Insurance | காப்பீடு |  |
| `tpl_insurance_agent_contact` | Agent contact | ஏஜென்ட் தொடர்பு |  |
| `tpl_insurance_commencement_date` | Commencement date | தொடங்கிய தேதி |  |
| `tpl_insurance_insurer` | Insurer | காப்பீட்டு நிறுவனம் |  |
| `tpl_insurance_maturity_date` | Maturity date | முதிர்வு தேதி |  |
| `tpl_insurance_nominee` | Nominee | நாமினி |  |
| `tpl_insurance_notes` | Notes | குறிப்புகள் |  |
| `tpl_insurance_policy_number` | Policy number | பாலிசி எண் |  |
| `tpl_insurance_policy_term` | Policy term | பாலிசி காலம் |  |
| `tpl_insurance_policy_type` | Policy type | பாலிசி வகை |  |
| `tpl_insurance_portal_login` | Portal login | போர்டல் லாகின் |  |
| `tpl_insurance_portal_password` | Portal password | போர்டல் கடவுச்சொல் |  |
| `tpl_insurance_premium_amount` | Premium amount | பிரீமியம் தொகை |  |
| `tpl_insurance_premium_due_date` | Premium due date | பிரீமியம் தேதி |  |
| `tpl_insurance_premium_mode` | Premium mode | பிரீமியம் எப்படிக் கட்டுகிறீர்கள் |  |
| `tpl_insurance_sum_assured` | Sum assured | காப்பீட்டுத் தொகை |  |
| `tpl_login` | Login | லாகின் |  |
| `tpl_login_notes` | Notes | குறிப்புகள் |  |
| `tpl_login_password` | Password | கடவுச்சொல் |  |
| `tpl_login_recovery_codes` | Recovery codes | மீட்புக் குறியீடுகள் |  |
| `tpl_login_username` | Username | பயனர்பெயர் |  |
| `tpl_login_website` | Website | வலைத்தளம் |  |
| `tpl_pan_card` | Pan Card | PAN கார்டு |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | ஆதாருடன் இணைக்கப்பட்டது |  |
| `tpl_pan_card_dob` | Dob | பிறந்த தேதி |  |
| `tpl_pan_card_e_filing_password` | E filing password | இ-ஃபைலிங் கடவுச்சொல் |  |
| `tpl_pan_card_fathers_name` | Fathers name | தந்தையின் பெயர் |  |
| `tpl_pan_card_file_copy` | Scanned copy | ஸ்கேன் செய்த நகல் |  |
| `tpl_pan_card_name_on_pan` | Name on pan | PAN-இல் உள்ள பெயர் |  |
| `tpl_pan_card_notes` | Notes | குறிப்புகள் |  |
| `tpl_pan_card_pan_number` | Pan number | PAN |  |
| `tpl_passkey` | Passkey | பாஸ்கீ |  |
| `tpl_passkey_credential_id` | Credential ID | கிரெடென்ஷியல் ID |  |
| `tpl_passkey_notes` | Notes | குறிப்புகள் |  |
| `tpl_passkey_private_key` | Private key | தனிப்பட்ட விசை |  |
| `tpl_passkey_sign_count` | Sign count | சைன் கவுண்ட் |  |
| `tpl_passkey_user_handle` | User handle | பயனர் ஹேண்டில் |  |
| `tpl_passkey_username` | Username | பயனர்பெயர் |  |
| `tpl_passkey_website` | Website | வலைத்தளம் |  |
| `tpl_passport` | Passport | பாஸ்போர்ட் |  |
| `tpl_passport_dob` | Dob | பிறந்த தேதி |  |
| `tpl_passport_expiry_date` | Expiry date | காலாவதி தேதி |  |
| `tpl_passport_file_copy` | Scanned copy | ஸ்கேன் செய்த நகல் |  |
| `tpl_passport_given_names` | Given names | இட்ட பெயர் |  |
| `tpl_passport_issue_date` | Issue date | வழங்கிய தேதி |  |
| `tpl_passport_notes` | Notes | குறிப்புகள் |  |
| `tpl_passport_passport_number` | Passport number | பாஸ்போர்ட் எண் |  |
| `tpl_passport_place_of_issue` | Place of issue | வழங்கிய இடம் |  |
| `tpl_passport_portal_login` | Portal login | Passport Seva லாகின் |  |
| `tpl_passport_portal_password` | Portal password | Passport Seva கடவுச்சொல் |  |
| `tpl_passport_surname` | Surname | குடும்பப் பெயர் |  |
| `tpl_secure_note` | Secure Note | பாதுகாப்பான குறிப்பு |  |
| `tpl_secure_note_attachment` | Attachment | இணைப்பு |  |
| `tpl_secure_note_body` | Note | குறிப்பு |  |
| `tpl_shopping` | Shopping | ஷாப்பிங் கணக்கு |  |
| `tpl_shopping_gift_card_code` | Gift card code | பரிசு அட்டை குறியீடு |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | பரிசு அட்டை பின் |  |
| `tpl_shopping_membership_id` | Membership id | உறுப்பினர் ID |  |
| `tpl_shopping_notes` | Notes | குறிப்புகள் |  |
| `tpl_shopping_password` | Password | கடவுச்சொல் |  |
| `tpl_shopping_registered_email` | Registered email | பதிவு செய்த ஈமெயில் |  |
| `tpl_shopping_registered_mobile` | Registered mobile | பதிவு செய்த மொபைல் |  |
| `tpl_shopping_wallet_pin` | Wallet pin | வாலட் பின் |  |
| `tpl_telecom` | Telecom | மொபைல் மற்றும் இணையம் |  |
| `tpl_telecom_account_number` | Account number | கணக்கு எண் |  |
| `tpl_telecom_circle` | Circle | சர்க்கிள் |  |
| `tpl_telecom_mobile_number` | Mobile number | மொபைல் எண் |  |
| `tpl_telecom_notes` | Notes | குறிப்புகள் |  |
| `tpl_telecom_operator` | Operator | நிறுவனம் |  |
| `tpl_telecom_plan_type` | Plan type | திட்ட வகை |  |
| `tpl_telecom_portal_password` | Portal password | போர்டல் கடவுச்சொல் |  |
| `tpl_telecom_puk` | PUK code | PUK குறியீடு |  |
| `tpl_telecom_renewal_date` | Renewal date | ரீசார்ஜ் தேதி |  |
| `tpl_telecom_sim_number` | Sim number | சிம் எண் (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | சிம் பின் |  |
| `tpl_transit` | Transit | பயண அட்டை |  |
| `tpl_transit_login_password` | Login password | லாகின் கடவுச்சொல் |  |
| `tpl_transit_notes` | Notes | குறிப்புகள் |  |
| `tpl_transit_operator_name` | Operator name | நிறுவனம் |  |
| `tpl_transit_registered_email` | Registered email | பதிவு செய்த ஈமெயில் |  |
| `tpl_transit_registered_mobile` | Registered mobile | பதிவு செய்த மொபைல் |  |
| `tpl_transit_smart_card_number` | Smart card number | ஸ்மார்ட் கார்டு எண் |  |
| `tpl_transit_wallet_pin` | Wallet pin | வாலட் பின் |  |
| `tpl_travel_booking` | Travel Booking | பயண முன்பதிவு |  |
| `tpl_travel_booking_account_username` | Account username | பயனர்பெயர் |  |
| `tpl_travel_booking_login_password` | Login password | லாகின் கடவுச்சொல் |  |
| `tpl_travel_booking_notes` | Notes | குறிப்புகள் |  |
| `tpl_travel_booking_provider` | Provider | நிறுவனம் |  |
| `tpl_travel_booking_registered_email` | Registered email | பதிவு செய்த ஈமெயில் |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | பதிவு செய்த மொபைல் |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | வாலட் பின் |  |
| `tpl_upi` | UPI | UPI |  |
| `tpl_upi_app_name` | App name | UPI ஆப் |  |
| `tpl_upi_apps_used` | Apps used | எந்த ஆப்-இல் இயங்குகிறது |  |
| `tpl_upi_linked_account` | Linked account | இணைந்த கணக்கு |  |
| `tpl_upi_notes` | Notes | குறிப்புகள் |  |
| `tpl_upi_upi_id` | UPI ID | UPI ID |  |
| `tpl_upi_upi_pin` | UPI PIN | UPI பின் |  |
| `tpl_utility` | Utility | பில்கள் மற்றும் இணைப்புகள் |  |
| `tpl_utility_account_holder` | Account holder | கணக்குதாரர் |  |
| `tpl_utility_consumer_number` | Consumer number | நுகர்வோர் எண் |  |
| `tpl_utility_due_day` | Bill due day | பில் கட்டும் நாள் |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag ID |  |
| `tpl_utility_notes` | Notes | குறிப்புகள் |  |
| `tpl_utility_portal_login` | Portal login | போர்டல் லாகின் |  |
| `tpl_utility_portal_password` | Portal password | போர்டல் கடவுச்சொல் |  |
| `tpl_utility_provider` | Provider | சேவை நிறுவனம் |  |
| `tpl_utility_utility_kind` | Utility kind | எதற்கான பில் |  |
| `tpl_utility_vehicle_number` | Vehicle number | வாகன எண் |  |
| `tpl_utility_wifi_password` | Wi-Fi password | Wi-Fi கடவுச்சொல் |  |
| `tpl_voter_id` | Voter Id | வாக்காளர் அடையாளம் |  |
| `tpl_voter_id_constituency` | Constituency | தொகுதி |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC எண் |  |
| `tpl_voter_id_file_copy` | Scanned copy | ஸ்கேன் செய்த நகல் |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | வாக்காளர் அட்டையில் உள்ள பெயர் |  |
| `tpl_voter_id_notes` | Notes | குறிப்புகள் |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP கடவுச்சொல் |  |
| `tr_days_many` | %1$d days left | %1$d நாட்கள் மீதம் |  |
| `tr_days_one` | %1$d day left | %1$d நாள் மீதம் |  |
| `tr_gone_today` | gone today | இன்று போய்விடும் |  |
| `tr_restore` | Restore | மீட்டெடு |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | அதற்குப் பிறகு அவை நிரந்தரமாகப் போய்விடும் — வேறெங்கும் நகல் இல்லை. |  |
| `ui_hide_passphrase` | Hide passphrase | கடவுச்சொற்றொடரை மறை |  |
| `ui_label_count` | %1$s, %2$s | %1$s, %2$s |  |
| `ui_show_passphrase` | Show passphrase | கடவுச்சொற்றொடரைக் காட்டு |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | இதே ஃபோனில் %1$d பதிவுகளுடன் ஒப்பிடப்பட்டது. எதுவும் எங்கும் அனுப்பப்படவில்லை. |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | இதே ஃபோனில் %1$d பதிவுடன் ஒப்பிடப்பட்டது. எதுவும் எங்கும் அனுப்பப்படவில்லை. |  |
| `vh_count_many` | %1$d things worth a look. | %1$d விஷயங்கள் கவனிக்க வேண்டியவை. |  |
| `vh_count_one` | %1$d thing worth a look. | %1$d விஷயம் கவனிக்க வேண்டியது. |  |
| `vh_empty` | No reused, weak or expiring credentials. | மீண்டும் பயன்படுத்திய, பலவீனமான அல்லது முடியப் போகும் தகவல் இல்லை. |  |
| `vh_kind_common` | Commonly guessed | எளிதில் ஊகிக்கக்கூடியது |  |
| `vh_kind_expiring` | Expiring | முடியப் போகிறது |  |
| `vh_kind_reused` | Reused password | மீண்டும் பயன்படுத்திய கடவுச்சொல் |  |
| `vh_kind_weak` | Weak | பலவீனம் |  |
| `vh_no_kit_title` | No recovery kit saved | மீட்பு கிட் எதுவும் சேமிக்கப்படவில்லை |  |
| `vh_nothing` | Nothing to fix. | சரிசெய்ய எதுவும் இல்லை. |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | ஆஃப்லைன் |  |
| `wl_chip_open` | Open source | ஓபன் சோர்ஸ் |  |
| `wl_create` | Create a new vault | புதிய பெட்டகத்தை உருவாக்கு |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | ஈமெயில் இல்லை · கணக்கு இல்லை · எதுவும் இந்த ஃபோனை விட்டு வெளியே போகாது |  |
| `wl_head_1` | Your keys. | உங்கள் சாவிகள். |  |
| `wl_head_2` | Your device. | உங்கள் ஃபோன். |  |
| `wl_head_3` | No server. | சர்வர் இல்லை. |  |
| `wl_restore` | Restore from Recovery Kit | மீட்புக் கிட்டிலிருந்து மீட்டெடு |  |
| `wl_sr_headline` | Your keys. Your device. No server. | உங்கள் சாவிகள். உங்கள் ஃபோன். சர்வர் இல்லை. |  |
