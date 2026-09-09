# Telugu (`te`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-te/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Telugu | ok? |
|---|---|---|---|
| `au_close` | Close | మూసివేయి |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | ఎంచుకున్న చిత్రంలో సరైన TOTP QR కోడ్ దొరకలేదు |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | ఖాళీగా మొదలుపెట్టి మీ ఫీల్డ్‌లకు మీరే పేర్లు పెట్టండి — టెంప్లేట్‌లు లేబుళ్ళను మాత్రమే నింపుతాయి, డేటాను ఎప్పుడూ కాదు. |  |
| `hm_close_search` | Close search | వెతుకులాట మూసివేయి |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | ఈ ఫోన్ నుండి ఎప్పుడూ బయటకు వెళ్ళదు. దాచేటప్పుడు ఎన్‌క్రిప్ట్. |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | ఇప్పుడు మీరు దిగుమతి చేసిన ఫైల్‌ను తొలగించండి. అది మీ పాస్‌వర్డ్‌ల బహిరంగ జాబితా, ఇంకా మీ Downloads లోనే పడి ఉంది. |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | అంతా ఈ ఫోన్‌లోనే డీక్రిప్ట్ అవుతుంది. ఏదీ అప్‌లోడ్ కాదు, ఎందుకంటే ఈ యాప్ నెట్‌వర్క్ కనెక్షన్ తెరవనే లేదు. |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | బ్యాంకులు మీ OTP ఎప్పుడూ అడగవు. అడిగినవాడు మోసగాడు. |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | ఏ బ్యాంక్ అధికారీ స్క్రీన్ షేర్ చేసే యాప్ పెట్టమని అడగడు. |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | మీ UPI పిన్ UPI యాప్ కీప్యాడ్ కోసమే — కాల్‌లో ఎవరికీ చెప్పకండి. |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | KYC ఒక్క రోజులో అయిపోదు. “ఈరోజు KYC అయిపోతోంది” అనే సందేశాలు మోసం. |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | డబ్బు తీసుకోవడానికి పిన్ వేయాల్సిన లేదా QR స్కాన్ చేయాల్సిన అవసరమే లేదు. |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | కరెంట్ కట్ అవుతుందనే SMS, అందులో ఎవరిదో వ్యక్తిగత నంబర్? అది మోసం. |  |
| `nav_close_menu` | Close menu | మెనూ మూసివేయి |  |
| `nfc_cannot_read` | Cannot read cards | కార్డులు చదవలేము |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | ఈ ఫోన్‌లో NFC లేదు, కాబట్టి కార్డును చదవలేదు. |  |
| `ob_fact_lost_title` | If you lose your keys | తాళంచెవులు పోతే |  |
| `ob_fact_network_note` | The app literally cannot phone home | ఈ యాప్ ఎక్కడికీ సంప్రదించనే లేదు |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | బయోమెట్రిక్ భద్రతా చిప్ నుండి ఎప్పుడూ బయటకు రాదు |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | మీ ఎన్‌క్రిప్ట్ చేసిన ఖజానాను సింక్ చేసే అదే ఫోల్డర్‌లో కీని ఉంచారు. ఇప్పుడు ఆ ఫోల్డర్ దొరికినవారికి రెండూ దొరుకుతాయి. కీని వేరే చోట ఉంచండి — కాగితం, వేరే ఖాతా, లేదా బీరువా. |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | ఇది మీ ఖజానా ఫైల్ పక్కనే ఉంది |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH రికవరీ |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | స్కాన్ చేయండి లేదా టైప్ చేయండి. మళ్ళీ ఇన్‌స్టాల్, ఫ్యాక్టరీ రీసెట్, ఫోన్ పోయిన తర్వాత కూడా పని చేస్తుంది. |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | ఇప్పుడే సేవ్ చేసిన కిట్ నుండి %1$d, %2$d గుంపులను టైప్ చేయండి. |  |
| `ob_kit_challenge_hint` | Group %1$d | గుంపు %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | ముందు కిట్ సేవ్ చేయండి, తర్వాత %1$d, %2$d గుంపులు తిరిగి టైప్ చేయండి. |  |
| `ob_kit_challenge_title` | Check you actually have it | కిట్ నిజంగా మీ దగ్గర ఉందో చూసుకోండి |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | ఇది పైన ఉన్న కీతో సరిపోలడం లేదు. |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | ఎవరూ — Zerokosh కూడా — దీన్ని నా కోసం తిరిగి తేలేరు. |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "దీన్ని ఆఫ్‌లైన్‌లో పెట్టాను. " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | ఒకసారే కనిపిస్తుంది, ఎప్పుడూ బహిరంగంగా నిల్వ చేయబడదు. లోపలికి రాగలిగితే సెట్టింగ్స్ నుండి కొత్తది తయారు చేసుకోండి. |  |
| `ob_kit_head_emph` | On paper. | కాగితం మీద. |  |
| `ob_kit_head_lead` | "One key. " | "ఒక తాళంచెవి. " |  |
| `ob_kit_head_tail` | " Never online." | " ఎప్పుడూ ఆన్‌లైన్‌లో కాదు." |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | Gmail వద్దు, WhatsApp వద్దు, స్క్రీన్‌షాట్ వద్దు. ఇనప్పెట్టె, బ్యాంక్ లాకర్, లేదా స్టీల్ పలక. |  |
| `ob_kit_offline_title` | Keep it off the internet | దాన్ని ఇంటర్నెట్‌కు దూరంగా ఉంచండి |  |
| `ob_kit_print` | Print | ముద్రించండి |  |
| `ob_kit_print_note` | A printer, or Save as PDF | ప్రింటర్, లేదా PDF గా సేవ్ |  |
| `ob_kit_qr` | QR image | QR చిత్రం |  |
| `ob_kit_qr_cd` | Recovery key QR code | రికవరీ కీ QR కోడ్ |  |
| `ob_kit_qr_note` | To an offline gallery | ఆఫ్‌లైన్ గ్యాలరీకి |  |
| `ob_kit_regenerate` | Regenerate | కొత్తది తయారు చేయి |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | ఇది సేవ్ కాలేదు. మళ్ళీ ప్రయత్నించండి, లేదా వేరే చోటు ఎంచుకోండి. |  |
| `ob_kit_save_pdf` | Save PDF | PDF దాచు |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | ఒక పేజీ ముద్రించదగిన కిట్ |  |
| `ob_kit_saved` | I\'ve saved my kit | నా కిట్‌ను దాచుకున్నాను |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s లో సేవ్ అయ్యింది |  |
| `ob_kit_sent_to_printer` | Sent to the printer | ప్రింటర్‌కు పంపబడింది |  |
| `ob_kit_skip` | I\'ll do this later | ఇది తర్వాత చేస్తాను |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | మీ ఖజానా పని చేస్తూనే ఉంటుంది. కిట్ సేవ్ అయ్యే వరకు Zerokosh గుర్తు చేస్తూనే ఉంటుంది. |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | ఈ ఫోన్‌లోనే తయారైంది, ఒక్కసారే కనిపిస్తుంది. పాస్‌ఫ్రేజ్ మర్చిపోతే లోపలికి తిరిగి రావడానికి ఇదొక్కటే దారి. |  |
| `ob_kit_working` | Working… | పని జరుగుతోంది… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | ఖజానా లేబుళ్ళు, టెంప్లేట్‌లు, హెచ్చరికలు వెంటనే మారతాయి. సెట్టింగ్‌లలో ఎప్పుడైనా మార్చుకోవచ్చు. |  |
| `ob_pass_confirm` | Confirm | మళ్ళీ టైప్ చేయండి |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | ఇది మాకు ఎప్పుడూ కనిపించదు. రీసెట్ లింక్ లేదు. |  |
| `ob_pass_head_emph` | held only | మీ దగ్గర మాత్రమే |  |
| `ob_pass_head_lead` | "One secret, " | "ఒకే రహస్యం, " |  |
| `ob_pass_head_tail` | " by you." | . |  |
| `ob_pass_no_match` | no match | సరిపోలడం లేదు |  |
| `ob_pass_seal` | Seal the vault | ఖజానాకు తాళం వేయండి |  |
| `ob_pass_sealing` | Sealing… | తాళం వేస్తోంది… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | సంబంధం లేని మూడు నాలుగు పదాలు, ఒక తెలివైన పదం కంటే మేలు. ఈ స్క్రీన్ నుండి ఏదీ బయటకు వెళ్ళదు. |  |
| `ob_pass_tab_passphrase` | Passphrase | పాస్‌ఫ్రేజ్ |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 అంకెల పిన్ |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | మీ వేలిముద్ర ఫోన్‌లోని భద్రతా చిప్ లోపల ఉంటుంది. అది ఈ ఫోన్ నుండి ఎప్పుడూ బయటకు రాదు. |  |
| `ob_trust_continue` | I understand · Continue | అర్థమైంది · కొనసాగించండి |  |
| `ob_trust_head_emph` | don\'t | తెలియదో |  |
| `ob_trust_head_lead` | "Exactly what we " | "మాకు నిజంగా ఏమి " |  |
| `ob_trust_head_tail` | " know." | " అది." |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | పాస్‌ఫ్రేజ్ మర్చిపోయి రికవరీ కిట్ కూడా పోగొట్టుకుంటే, ఖజానా మూసుకునే ఉంటుంది — మీకూ, మాకూ, ఎవరికీ. |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "తాళంచెవులు మీ దగ్గరే. " |  |
| `ob_trust_stat_files` | .kosh file on device | ఫోన్‌లో .kosh ఫైల్ |  |
| `ob_trust_stat_servers` | servers contacted | సర్వర్‌తో సంబంధం |  |
| `ob_trust_stat_trackers` | trackers or SDKs | ట్రాకర్ లేదా SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | దీన్ని ఒకసారి చదవండి. మొత్తం భద్రతా వ్యవస్థ ఇదే, సూటిగా. |  |
| `ob_trust_tag_audited` | Audited build | ఆడిట్ చేసిన బిల్డ్ |  |
| `ob_trust_tag_reproducible` | Reproducible APK | మళ్ళీ తయారు చేయగల APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | మీరు వేలిముద్రతో తెరుస్తున్నారు. ఎప్పుడైనా వేలిముద్ర పని చేయకపోతే, ఇదే మిమ్మల్ని లోపలికి తెస్తుంది — కాబట్టి చూసుకోవడం మంచిది. |  |
| `pc_confirm` | Check | చూడండి |  |
| `pc_correct` | Still correct. Nothing to do. | ఇంకా సరైనదే. చేయాల్సింది ఏమీ లేదు. |  |
| `pc_forgot` | I cannot remember it | నాకు గుర్తు రావట్లేదు |  |
| `pc_later` | Not now | ఇప్పుడు వద్దు |  |
| `pc_reset_action` | Set new passphrase | కొత్త పాస్‌ఫ్రేజ్ పెట్టు |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | మీ వేలిముద్ర ఈ ఖజానాను తెరవగలదు, కాబట్టి అదే కొత్త పాస్‌ఫ్రేజ్ కూడా పెట్టగలదు — రికవరీ కిట్ అవసరం లేదు. నిర్ధారణ కోసం మరోసారి అడుగుతుంది. |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | పాస్‌ఫ్రేజ్ మారింది. త్వరిత అన్‌లాక్ మళ్ళీ అమర్చబడింది. |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | ఇది పని చేయలేదు. మీ పాత పాస్‌ఫ్రేజే ఇంకా వాడుకలో ఉంది. |  |
| `pc_reset_title` | Set a new passphrase | కొత్త పాస్‌ఫ్రేజ్ పెట్టండి |  |
| `pc_title` | Do you still remember your passphrase? | మీ పాస్‌ఫ్రేజ్ ఇంకా గుర్తుందా? |  |
| `pc_wrong` | That is not it. You can set a new one instead. | ఇది అది కాదు. బదులుగా కొత్తది పెట్టుకోవచ్చు. |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | ఈ రికార్డు కోసం గుర్తుంచుకున్న %1$d విలువలు తొలగించబడతాయి. దీన్ని తిరిగి తేలేము. |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh ఆ లాగిన్‌ను దాచలేకపోయింది. |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | నింపడానికి Zerokosh తెరవండి |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | ఈ ఒక్క పాస్‌ఫ్రేజే అన్నింటినీ తాళం వేసి ఉంచుతుంది. మీకు మాత్రమే తెలిసిన పొడవైనది ఎంచుకోండి. |  |
| `scr_create_button` | Lock it in | తాళం వేయండి |  |
| `scr_create_confirm_hint` | Type it again | మళ్ళీ టైప్ చేయండి |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | పాస్‌ఫ్రేజ్ (కనీసం 10 అక్షరాలు) |  |
| `scr_create_mismatch` | The two entries don\'t match | రెండూ ఒకేలా లేవు |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 అంకెల పిన్ |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | బదులుగా 6 అంకెల పిన్ పెట్టండి |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | పిన్ కోసం వేలిముద్ర లేదా ముఖ అన్‌లాక్ ఉన్న ఫోన్ కావాలి. దయచేసి పాస్‌ఫ్రేజ్ ఎంచుకోండి. |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | పిన్ కుదురుతుంది ఎందుకంటే ఈ ఫోన్ దాన్ని తన భద్రతా చిప్‌తో, మీ వేలిముద్ర లేదా ముఖంతో కాపాడుతుంది. |  |
| `scr_create_strength_fair` | Fair | ఫర్వాలేదు |  |
| `scr_create_strength_good` | Good | మంచిది |  |
| `scr_create_strength_strong` | Strong | బలమైనది |  |
| `scr_create_strength_weak` | Weak | బలహీనం |  |
| `scr_create_title` | Create your passphrase | మీ పాస్‌ఫ్రేజ్ తయారు చేయండి |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | కనీసం 10 అక్షరాలు ఉంచండి — ఎంత పొడవైతే అంత బలం |  |
| `scr_create_working` | Preparing your vault… | మీ ఖజానా సిద్ధమవుతోంది… |  |
| `scr_detail_delete` | Delete | తొలగించు |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | అది 30 రోజులు “ఇటీవల తొలగించినవి”లో ఉంటుంది, మీ ఇతర ఫోన్‌లతో సింక్ అయ్యాక పోతుంది. |  |
| `scr_detail_delete_confirm_title` | Delete this record? | ఈ రికార్డును తొలగించాలా? |  |
| `scr_detail_delete_confirm_yes` | Delete | తొలగించు |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | సీక్రెట్ లేదా otpauth:// లింక్ అతికించండి |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | మీ వేలిముద్ర లేదా ముఖం వాడండి |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh తెరవండి |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | చాలాసార్లు తప్పుగా ప్రయత్నించారు. %1$d సెకన్లు ఆగండి. |  |
| `scr_lock_hint` | Passphrase | పాస్‌ఫ్రేజ్ |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | ఈ రికవరీ కీ సరైనది కాదు — ఒక్కో అక్షరం సరిచూడండి |  |
| `scr_lock_title` | Vault is locked | ఖజానా తాళం వేసి ఉంది |  |
| `scr_lock_unlock` | Unlock | తెరవండి |  |
| `scr_lock_use_passphrase` | Use passphrase | పాస్‌ఫ్రేజ్ వాడండి |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | దయచేసి ఒకసారి పాస్‌ఫ్రేజ్‌తో తెరవండి |  |
| `scr_lock_use_recovery` | Use Recovery Key | రికవరీ కీ వాడండి |  |
| `scr_lock_wrong` | Wrong passphrase | పాస్‌ఫ్రేజ్ తప్పు |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | ఎప్పుడైనా పాస్‌ఫ్రేజ్ మర్చిపోతే, లోపలికి తిరిగి రావడానికి ఇదొక్కటే దారి. మేము దాన్ని రీసెట్ చేయలేము — ఎవరూ చేయలేరు. |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | నేను దాన్ని రాసుకుని సురక్షితమైన చోట పెట్టాను |  |
| `scr_recovery_done` | Continue | కొనసాగించండి |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | ఈ కీ ఒకసారే కనిపిస్తుంది. మీరు ఇంకా తెరవగలిగినంత కాలం, సెట్టింగ్స్ నుండి ఎప్పుడైనా కొత్తది తయారు చేసుకోవచ్చు. |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | ఈ పేజీని మీ ఆస్తి పత్రాలతో లేదా ఇతర ముఖ్యమైన పత్రాలతో పెట్టండి. ఈ కీ ఎవరి దగ్గర ఉంటే వారు మీ ఖజానా తెరవగలరు — దాన్ని లాకర్ తాళంచెవిలా కాపాడండి. |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | రికవరీ కిట్ PDF దాచబడింది |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh రికవరీ కిట్ |  |
| `scr_recovery_save_pdf` | Save as PDF | PDF గా దాచండి |  |
| `scr_recovery_title` | Your Recovery Key | మీ రికవరీ కీ |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | మీరు దాచేదంతా మీ ఫోన్‌లోని ఒక తాళం వేసిన ఫైల్‌లో ఉంటుంది. అది ఎప్పుడూ మా దగ్గరకు రాదు — దాన్ని పెట్టడానికి మా దగ్గర చోటే లేదు. |  |
| `scr_trust_card1_title` | Your data stays on this device | మీ డేటా ఈ ఫోన్‌లోనే ఉంటుంది |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | Zerokosh ఖాతా లేదు, క్లౌడ్ లేదు, సైన్-అప్ లేదు. దీన్ని మీరు మాత్రమే తెరవగలరు. మేమూ తెరవలేము. |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | మాకు సర్వర్లు లేవు — హ్యాక్ చేయడానికీ ఏమీ లేదు, అమ్మడానికీ ఏమీ లేదు |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | చందా లేదు, ప్రకటనలు లేవు. ఎవరైనా మా కోడ్ చదివి మా ప్రతి మాటనూ సరిచూసుకోవచ్చు. |  |
| `scr_trust_card3_title` | Free forever, open source | ఎప్పటికీ ఉచితం, ఓపెన్ సోర్స్ |  |
| `scr_trust_continue` | Continue | కొనసాగించండి |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | మీ మార్పులు దాచబడలేదు, కాబట్టి ఖజానాలో ఇంతకుముందు ఉన్నదాంట్లో ఏదీ పోలేదు. |  |
| `st_recently_deleted` | Recently deleted | ఇటీవల తొలగించినవి |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | వన్-టైమ్ కోడ్ (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | వన్-టైమ్ కోడ్ (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | వన్-టైమ్ కోడ్ (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA సీక్రెట్ |  |
| `tr_cannot_undo` | This cannot be undone. | దీన్ని తిరిగి తేలేము. |  |
| `tr_delete_all` | Delete all permanently | అన్నీ శాశ్వతంగా తొలగించు |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d రికార్డులు శాశ్వతంగా పోతాయి. దీన్ని తిరిగి తేలేము, పునరుద్ధరించడానికి బ్యాకప్ కూడా లేదు. |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d రికార్డు శాశ్వతంగా పోతుంది. దీన్ని తిరిగి తేలేము, పునరుద్ధరించడానికి బ్యాకప్ కూడా లేదు. |  |
| `tr_delete_all_title` | Delete everything in the trash? | చెత్తబుట్టలోని అన్నీ తొలగించాలా? |  |
| `tr_delete_now` | Delete now | ఇప్పుడే తొలగించు |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | “%1$s” ను శాశ్వతంగా తొలగించాలా? |  |
| `tr_empty` | Nothing deleted. | ఏదీ తొలగించలేదు. |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | తొలగించిన రికార్డులు ఇక్కడ %1$d రోజులు ఉంటాయి. |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | భారతీయ బ్యాంకులు, UPI, కార్డులు, డీమ్యాట్, EPF, మీరు నిజంగా వాడే OTP యాప్‌లు — వీటన్నిటి కోసం ఫోన్‌లోనే ఉండే ఖజానా. |  |

## Priority 2 — longer prose

| key | English | Telugu | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | ఎక్కువగా పనికొచ్చే ఒక్క సమాచారంతో మొదలుపెట్టండి. నోట్స్ యాప్‌లో పడి ఉన్న పన్నెండు పాస్‌వర్డ్‌ల కంటే దాచిన ఒక్క పాస్‌వర్డ్ సురక్షితం. |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | పాస్‌ఫ్రేజ్ మర్చిపోతే లోపలికి రావడానికి రికవరీ కిట్ ఒక్కటే దారి. మీ కోసం ఇంకొకటి ఎవరూ తయారు చేయలేరు. |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | ఆ ఫైల్‌లో గుర్తించదగినది ఏమీ దొరకలేదు. Chrome, Google Password Manager, Bitwarden, LastPass, KeePass ఎగుమతులు అర్థమవుతాయి. |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d ఉన్న రికార్డులు మారతాయి — సైట్, యూజర్‌నేమ్‌తో సరిపోల్చి. మారిన పాస్‌వర్డ్‌లు ప్రతి రికార్డు చరిత్రలో తిరిగి దొరుకుతాయి. |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d ఉన్న రికార్డు మారుతుంది — సైట్, యూజర్‌నేమ్‌తో సరిపోల్చి. మారిన పాస్‌వర్డ్‌లు ప్రతి రికార్డు చరిత్రలో తిరిగి దొరుకుతాయి. |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d రికార్డులు రెండు వైపులా మారాయి. రెండు రూపాలూ ఉంచబడ్డాయి — “(conflict copy)” వెతకండి. |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | ఈ బ్యాకప్ ఫైల్‌ను తెరిచే పాస్‌ఫ్రేజ్ ఇవ్వండి. అది మీ ప్రస్తుతదాని కంటే వేరుగా ఉండవచ్చు. |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | .kosh ఫైల్ Poly1305 ధృవీకరణ ట్యాగ్ సరిపోలడం లేదు. మధ్యలో ఆగిన సింక్ లేదా చెడిపోయిన స్టోరేజ్ తర్వాత ఇలా జరగవచ్చు. |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh ఖజానా ఫైల్ పక్కనే ఒక నడుస్తున్న బ్యాకప్ ఉంచుతుంది. దాన్ని మీ సింక్ ఫోల్డర్ నుండి పునరుద్ధరించండి, లేదా వేరే ఫోన్‌లో రికవరీ కిట్‌తో ఖజానా తెరవండి. |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | చదివే వరకు కార్డును ఫోన్ వెనుక సరిగ్గా ఆనించి ఉంచండి. దీనితో కార్డ్ నంబర్, గడువు, పేరు వస్తాయి — CVV చిప్‌లో ఉండదు, దాన్ని మీరే టైప్ చేయాలి. |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | లోపలికి తిరిగి రావడానికి వేగవంతమైన దారి ఎంచుకోండి. ఖజానాకు కాపలా పాస్‌ఫ్రేజే; ఇది ఈ ఫోన్‌లో మాత్రమే తాళంచెవిని తెరుస్తుంది. |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | ఇప్పుడు మీ ఖజానాలో నిజమైన సమాచారం ఉంది. రికవరీ కిట్ లేకుండా పాస్‌ఫ్రేజ్ మర్చిపోతే మిమ్మల్ని ఎవరూ తిరిగి లోపలికి రానివ్వలేరు — మేము కూడా. |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh ఉచితం, ఓపెన్ సోర్స్, సర్వర్లే లేవు. మీ ఖజానాను మీరు మాత్రమే తెరవగలరు. మేమూ తెరవలేము. |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | QR కోడ్ స్కాన్ చేయడానికే కెమెరా అనుమతి కావాలి. రికార్డ్ జోడించేటప్పుడు సీక్రెట్‌ను చేతితో కూడా అతికించవచ్చు. |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | ఆటోఫిల్ కుదరని బ్యాంక్ యాప్‌ల కోసం — బటన్ నొక్కి లాగిన్ వివరాలను ఒక్కొక్కటిగా కాపీ చేయండి |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | మీ ఫోన్ తెరిచినట్టే ఖజానానూ. పాస్‌ఫ్రేజ్ ఎప్పుడూ పని చేస్తూనే ఉంటుంది. |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | ఖజానా స్క్రీన్‌షాట్‌లు క్లౌడ్ ఫోటో బ్యాకప్‌లోకి వెళ్ళిపోవచ్చు. నిజంగా అవసరమైతేనే ఆన్ చేయండి. |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | ఖజానా ఫైల్‌ను రాయలేకపోయాము. మీరు బ్యాకప్ మరియు సింక్ ఫోల్డర్ సెట్ చేసి ఉంటే, Android దాని అనుమతిని వెనక్కి తీసుకుని ఉండవచ్చు — సెట్టింగ్‌లు తెరిచి, ఫోల్డర్‌ను మళ్ళీ ఎంచుకుని, మళ్ళీ ప్రయత్నించండి. |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | మీ ఎన్‌క్రిప్ట్ చేసిన .kosh ఫైళ్ళు నేరుగా ఈ ఫోల్డర్‌లోనే దాచబడతాయి. అనేక పరికరాల్లో ఆటోమేటిక్ బ్యాకప్ కోసం ఈ ఫోల్డర్‌ను Google Drive, Syncthing, Nextcloud లేదా SD కార్డుతో సింక్ చేయండి. |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | కొత్త రికవరీ కీ తయారు చేయడానికి మీ పాస్‌ఫ్రేజ్ ఇవ్వండి. పాత కీ పని చేయడం ఆగిపోతుంది. |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | ఈ పేజీలో మిగతావన్నీ ఒక లాగిన్‌ను బలహీనం చేస్తాయి. ఇది మొత్తం ఖజానాను తీసుకుపోగలదు. సెట్టింగ్స్ → కొత్త రికవరీ కీ తీసుకోండి. |  |

## Priority 3 — short labels

| key | English | Telugu | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | బలమైన పాస్‌వర్డ్ వాడండి |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | ఖాతా పేరు (ఉదా. Google) |  |
| `au_active_many` | %1$d active codes | %1$d నడుస్తున్న కోడ్‌లు |  |
| `au_active_one` | %1$d active code | %1$d నడుస్తున్న కోడ్ |  |
| `au_add_another` | Add another authenticator | మరో ఆథెంటికేటర్ జోడించండి |  |
| `au_add_secret` | Add Secret Key | సీక్రెట్ కీ జోడించండి |  |
| `au_camera_needed` | Camera permission needed to scan QR code | QR కోడ్ స్కాన్ చేయడానికి కెమెరా అనుమతి కావాలి |  |
| `au_copied` | Copied · clears shortly | కాపీ అయ్యింది · కొద్దిసేపట్లో చెరిగిపోతుంది |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google, GitHub లేదా మీ బ్రోకర్ QR ను స్కాన్ చేయండి, లేదా సీక్రెట్ కీని చేతితో టైప్ చేయండి. |  |
| `au_enter_key` | Enter Key | కీ టైప్ చేయండి |  |
| `au_fallback_name` | Authenticator | ఆథెంటికేటర్ |  |
| `au_flashlight` | Flashlight | టార్చ్ |  |
| `au_grant` | Grant Permission | అనుమతి ఇవ్వండి |  |
| `au_image_failed` | Failed to process image | చిత్రాన్ని చదవలేకపోయాము |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | తప్పు Base32 సీక్రెట్ కీ (A-Z అక్షరాలు, 2-7 అంకెలు మాత్రమే) |  |
| `au_no_match` | No codes match | ఏ కోడ్ దొరకలేదు |  |
| `au_none_yet` | No codes yet. | ఇంకా కోడ్‌లు లేవు. |  |
| `au_pick_image` | Pick Image | చిత్రం ఎంచుకోండి |  |
| `au_rotating` | "Rotating " | "మారుతూ ఉండే " |  |
| `au_rotating_emph` | codes. | కోడ్‌లు. |  |
| `au_save_key` | Save Key | కీ దాచు |  |
| `au_scan_qr` | Scan a QR code | QR కోడ్ స్కాన్ చేయండి |  |
| `au_scan_title` | Scan Authenticator QR | ఆథెంటికేటర్ QR స్కాన్ చేయండి |  |
| `au_search_hint` | Search codes, issuers… | కోడ్‌లు, జారీచేసినవారిని వెతకండి… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | ఉదా. JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | సీక్రెట్ కీ (Base32) |  |
| `au_tap_to_copy` | Tap to copy | కాపీ చేయడానికి తాకండి |  |
| `cat_apps` | Apps &amp; Logins | యాప్‌లు మరియు లాగిన్ |  |
| `cat_banks` | Banks &amp; UPI | బ్యాంకులు మరియు UPI |  |
| `cat_cards` | Cards | కార్డులు |  |
| `cat_govid` | Gov &amp; ID | ప్రభుత్వం మరియు గుర్తింపు |  |
| `cat_investments` | Investments | పెట్టుబడులు |  |
| `cat_utilities` | Utilities | బిల్లులు మరియు కనెక్షన్లు |  |
| `cd_mask_hidden` | hidden | దాచబడింది |  |
| `cd_shield_high_sensitivity` | extra-protected field | అదనపు రక్షణ ఉన్న సమాచారం |  |
| `gl_blank` | Blank template | ఖాళీ టెంప్లేట్ |  |
| `gl_cat_apps` | Apps | యాప్‌లు |  |
| `gl_cat_banks` | Banks | బ్యాంకులు |  |
| `gl_cat_cards` | Cards | కార్డులు |  |
| `gl_cat_demat` | Demat | డీమ్యాట్ |  |
| `gl_cat_govid` | Gov ID | ప్రభుత్వ గుర్తింపు |  |
| `gl_cat_popular` | Popular | ప్రసిద్ధం |  |
| `gl_cat_shopping` | Shopping | షాపింగ్ |  |
| `gl_cat_travel` | Travel | ప్రయాణం |  |
| `gl_cat_upi` | UPI | UPI |  |
| `gl_cat_utilities` | Utilities | బిల్లులు |  |
| `gl_head_emph` | storing? | దాచుతున్నాము? |  |
| `gl_head_lead` | "What are we " | "మనం ఏమి " |  |
| `gl_matches` | %1$d matches | %1$d దొరికాయి |  |
| `gl_most_used` | Most-used first | ఎక్కువగా వాడినవి ముందు |  |
| `gl_not_found` | Can’t find a service? | సేవ దొరకడం లేదా? |  |
| `gl_search` | Search %1$d Indian services… | %1$d భారతీయ సేవల్లో వెతకండి… |  |
| `gl_suggested` | Suggested for you | మీ కోసం సూచన |  |
| `hm_add_first` | Add your first record | మీ మొదటి రికార్డును జోడించండి |  |
| `hm_all_offline` | all offline. | అంతా ఆఫ్‌లైన్. |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d సమాచారాలు, " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d సమాచారం, " |  |
| `hm_detail_placeholder` | Pick a record to see it here | ఇక్కడ చూడటానికి ఒక రికార్డును ఎంచుకోండి |  |
| `hm_empty_blank` | A blank vault, ready. | ఖాళీ ఖజానా, సిద్ధం. |  |
| `hm_empty_head_emph` | waiting. | ఎదురుచూస్తోంది. |  |
| `hm_empty_head_lead` | "Your vault is " | "మీ ఖజానా " |  |
| `hm_filter_all` | All | అన్నీ |  |
| `hm_import_backup` | Import an encrypted backup | ఎన్‌క్రిప్ట్ చేసిన బ్యాకప్ దిగుమతి |  |
| `hm_import_backup_note` | Open a .kosh file from this device | ఈ ఫోన్ నుండే .kosh ఫైల్ తెరవండి |  |
| `hm_inst_many` | %1$d institutions | %1$d సంస్థలు |  |
| `hm_inst_one` | %1$d institution | %1$d సంస్థ |  |
| `hm_kit_banner_action` | Save one now | ఇప్పుడే సేవ్ చేయండి |  |
| `hm_kit_banner_dismiss` | Remind me later | తర్వాత గుర్తు చేయండి |  |
| `hm_kit_banner_title` | No recovery kit saved | రికవరీ కిట్ సేవ్ చేయలేదు |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | తెరిచి ఉంది · వదిలిన వెంటనే తాళం |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | తెరిచి ఉంది · వదిలిన %1$d నిమిషాలకు తాళం |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | తెరిచి ఉంది · వదిలిన 1 నిమిషానికి తాళం |  |
| `hm_no_match` | Nothing matches “%1$s” | “%1$s” కు ఏమీ దొరకలేదు |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | ఏదైనా సంస్థ, UPI హ్యాండిల్, లేదా చివరి నాలుగు అంకెలు ప్రయత్నించండి. |  |
| `hm_pinned` | Pinned | పిన్ చేసినవి |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC, priya@upi, PAN… వెతకండి |  |
| `hm_start_template` | Start with a template | టెంప్లేట్‌తో మొదలుపెట్టండి |  |
| `ic_could_not` | Could not import | దిగుమతి కాలేదు |  |
| `ic_done` | Done | అయ్యింది |  |
| `ic_import` | Import | దిగుమతి |  |
| `ic_imported` | Imported | దిగుమతి అయ్యింది |  |
| `ic_importing` | Importing… | దిగుమతి అవుతోంది… |  |
| `ic_new_many` | %1$d new logins. | %1$d కొత్త లాగిన్‌లు. |  |
| `ic_new_one` | %1$d new login. | %1$d కొత్త లాగిన్. |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d జోడించబడ్డాయి, %2$d మార్చబడ్డాయి. |  |
| `ic_title` | Import from %1$s? | %1$s నుండి దిగుమతి చేయాలా? |  |
| `ic_too_large` | That file is too large to be a credential export. | ఈ ఫైల్ పాస్‌వర్డ్ ఎగుమతి కాలేనంత పెద్దది. |  |
| `import_action` | Import | దిగుమతి |  |
| `import_locked` | Unlock your vault before importing. | దిగుమతి చేసే ముందు మీ ఖజానాను తెరవండి. |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d జోడించబడ్డాయి, %2$d మార్చబడ్డాయి. ఏదీ చెరిపివేయబడలేదు. |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | ఆ ఫైల్‌ను Zerokosh ఖజానాగా చదవలేకపోయాము. |  |
| `import_nothing_new` | Everything in that backup was already here. | ఆ బ్యాకప్‌లోని అంతా ఇక్కడ ఇప్పటికే ఉంది. |  |
| `import_passphrase_label` | Backup passphrase | బ్యాకప్ పాస్‌ఫ్రేజ్ |  |
| `import_title` | Import a backup | బ్యాకప్ దిగుమతి చేయి |  |
| `kicker_locked` | Locked | తాళం వేసి ఉంది |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · ఏదీ ఈ ఫోన్ దాటి వెళ్ళలేదు |  |
| `lk_touch_unlock` | Touch to unlock | తెరవడానికి తాకండి |  |
| `lk_welcome_emph` | Your vault is sealed. | మీ ఖజానా తాళం వేసి ఉంది. |  |
| `lk_welcome_lead` | Welcome back. | మళ్ళీ స్వాగతం. |  |
| `msg_auth_needed` | Confirm it\'s you to see this | చూడటానికి మీరేనని నిర్ధారించండి |  |
| `msg_back` | Back | వెనక్కి |  |
| `msg_cancel` | Cancel | రద్దు |  |
| `msg_file_damaged` | File damaged — restored from backup | ఫైల్ పాడైంది — బ్యాకప్ నుండి సరిచేయబడింది |  |
| `msg_ok` | OK | సరే |  |
| `msg_saved` | Saved | దాచబడింది |  |
| `nav_all_templates` | All templates | అన్ని టెంప్లేట్‌లు |  |
| `nav_damaged_emph` | vault file | ఖజానా ఫైల్‌లో |  |
| `nav_damaged_kicker` | Damaged state | పాడైన స్థితి |  |
| `nav_damaged_lead` | "Something in the " | "మీ " |  |
| `nav_damaged_tail` | " is off." | " ఏదో తేడా ఉంది." |  |
| `nav_integrity_title` | Integrity check failed | సమగ్రత తనిఖీ విఫలం |  |
| `nav_scan` | Scan | స్కాన్ |  |
| `nav_tap_card` | Tap a card | కార్డును తాకండి |  |
| `nav_what_next` | What to do next | ఇప్పుడు ఏమి చేయాలి |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC ఆఫ్‌లో ఉంది. సెట్టింగ్‌లలో ఆన్ చేసి మళ్ళీ ప్రయత్నించండి. |  |
| `nfc_hold_card` | Hold your card to the phone | కార్డును ఫోన్‌కు ఆనించండి |  |
| `nfc_missed` | Did not catch that | అందలేదు |  |
| `nfc_read_failed` | That card could not be read. Try again. | ఆ కార్డును చదవలేకపోయాము. మళ్ళీ ప్రయత్నించండి. |  |
| `nfc_reading` | Reading… | చదువుతోంది… |  |
| `nfc_try_again` | Try again | మళ్ళీ ప్రయత్నించండి |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · ఈ ఫోన్‌లోనే కొలిచినది |  |
| `ob_argon_faster` | Faster unlock | త్వరగా తెరుచుకుంటుంది |  |
| `ob_argon_harder` | Harder to attack | పగలగొట్టడం కష్టం |  |
| `ob_argon_measuring` | Measuring this device… | ఈ ఫోన్ కొలవబడుతోంది… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id కఠినత్వం |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | నిఘంటువులోని ఒక్క పదమూ కాదు |  |
| `ob_check_pass_length` | 10 characters or more | 10 లేదా అంతకంటే ఎక్కువ అక్షరాలు |  |
| `ob_check_pass_reuse` | Not reused from another app | వేరే యాప్ నుండి మళ్ళీ వాడినది కాదు |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | పుట్టినరోజూ కాదు, వార్షికోత్సవమూ కాదు |  |
| `ob_check_pin_digits` | All six digits entered | ఆరు అంకెలూ నింపారు |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | వరుస అంకెలూ కాదు, పునరావృతమూ కాదు |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~పగలగొట్టడానికి %1$d శతాబ్దాలు |  |
| `ob_crack_days` | ~%1$d days to crack | ~పగలగొట్టడానికి %1$d రోజులు |  |
| `ob_crack_forever` | longer than the sun | సూర్యుడి కంటే ఎక్కువ కాలం |  |
| `ob_crack_hours` | ~hours to crack | ~పగలగొట్టడానికి కొన్ని గంటలు |  |
| `ob_crack_seconds` | ~seconds to crack | ~పగలగొట్టడానికి కొన్ని సెకన్లు |  |
| `ob_crack_years` | ~%1$d years to crack | ~పగలగొట్టడానికి %1$d సంవత్సరాలు |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | ధృవీకరించిన, ప్రతి ఖజానాకూ వేరే నాన్స్ |  |
| `ob_fact_encryption_title` | Encryption | ఎన్‌క్రిప్షన్ |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | సెటప్ సమయంలో మీ ఫోన్‌లో కొలిచినది |  |
| `ob_fact_kdf_title` | Key stretching | కీ స్ట్రెచింగ్ |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | రీసెట్ లింక్ లేదు. సపోర్ట్ వెనుక తలుపూ లేదు. |  |
| `ob_fact_lost_value` | Nobody can recover it | ఎవరూ తిరిగి తేలేరు |  |
| `ob_fact_network_title` | Network permission | నెట్‌వర్క్ అనుమతి |  |
| `ob_fact_network_value` | Not requested | అడగనే లేదు |  |
| `ob_fact_quick_title` | Quick unlock | వేగవంతమైన అన్‌లాక్ |  |
| `ob_fact_quick_value` | Hardware keystore | హార్డ్‌వేర్ కీస్టోర్ |  |
| `ob_lang_continue` | Continue in %1$s | %1$s లో కొనసాగించండి |  |
| `ob_lang_head_emph` | language. | భాషను ఎంచుకోండి. |  |
| `ob_lang_head_lead` | "Choose your " | "మీ " |  |
| `ob_lang_search` | Search %1$d languages | %1$d భాషల్లో వెతకండి |  |
| `ob_quick_continue_pass` | Continue with passphrase | పాస్‌ఫ్రేజ్‌తో కొనసాగించండి |  |
| `ob_quick_enable` | Enable quick unlock | వేగవంతమైన అన్‌లాక్ ఆన్ చేయండి |  |
| `ob_quick_fingerprint` | Fingerprint | వేలిముద్ర |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | వేగవంతమైన, హార్డ్‌వేర్ రక్షణ ఉన్న అన్‌లాక్. |  |
| `ob_quick_head_emph` | Without the cloud. | క్లౌడ్ లేకుండా. |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "ఒక్క స్పర్శకే తెరుచుకుంటుంది. " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox. ఏ బయోమెట్రిక్ సమాచారమూ Zerokosh వరకు ఎప్పుడూ చేరదు. |  |
| `ob_quick_hw_title` | Hardware-backed | హార్డ్‌వేర్ రక్షణ |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | ఈ ఫోన్‌లో హార్డ్‌వేర్ సెన్సార్ లేదు. |  |
| `ob_quick_opening` | Opening your vault… | మీ ఖజానా తెరుచుకుంటోంది… |  |
| `ob_quick_pass_only` | Passphrase only | పాస్‌ఫ్రేజ్ మాత్రమే |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | ప్రతిసారీ టైప్ చేయండి. అత్యంత సురక్షితం. |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | వేగవంతమైన అన్‌లాక్ సెట్ కాలేదు. మళ్ళీ ప్రయత్నించండి, లేదా పాస్‌ఫ్రేజ్‌తోనే కొనసాగండి. |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | ఇప్పుడు వద్దు — నేను పాస్‌ఫ్రేజ్ టైప్ చేస్తాను |  |
| `ob_quick_touch_title` | Touch the sensor | సెన్సార్‌ను తాకండి |  |
| `ob_recommended` | Recommended | సూచించినది |  |
| `ob_reveal_hide` | Hide | దాచు |  |
| `ob_reveal_show` | Show | చూపించు |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | చుట్టే తాళంచెవి హార్డ్‌వేర్ కీస్టోర్‌లో ఉంటుంది. బయోమెట్రిక్ తర్వాతి అడుగులో. |  |
| `ob_seal_title` | Seal to this device | ఈ ఫోన్‌కే కట్టేయండి |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | ఈ ఫోన్‌లో హార్డ్‌వేర్ బయోమెట్రిక్ లేదు. |  |
| `ob_soon` | SOON | త్వరలో |  |
| `ob_step_label` | Step %1$d of 6 | అడుగు %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | మీరు మాత్రమే చెప్పే ఏదో ఒకటి ఎంచుకోండి |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | మంచిది · %1$d బిట్ ఎంట్రోపీ |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | చాలా చిన్నది · 10 అక్షరాలు కావాలి |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | బలమైనది · %1$d బిట్ ఎంట్రోపీ |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | బలహీనం · %1$d బిట్ ఎంట్రోపీ |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | మీ జీవితం చూసి ఎవరూ ఊహించలేని ఆరు అంకెలు |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | ఫర్వాలేదు · %1$d బిట్ — పిన్ దీని కంటే బలంగా ఉండలేదు |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | చాలా చిన్నది · 6 అంకెలు కావాలి |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | బలహీనం · ఈ పిన్‌లనే మొదట ప్రయత్నిస్తారు |  |
| `ob_try_label` | TRY | ప్రయత్నించండి |  |
| `qa_aadhaar` | Aadhaar | ఆధార్ |  |
| `qa_bank_account` | Bank account | బ్యాంక్ ఖాతా |  |
| `qa_pan` | PAN | PAN |  |
| `qa_upi_id` | UPI ID | UPI ID |  |
| `rd_copied` | Copied | కాపీ అయ్యింది |  |
| `rd_forget` | Forget | మర్చిపో |  |
| `rd_forget_these` | Forget these | వీటిని మర్చిపో |  |
| `rd_forget_title` | Forget previous passwords? | పాత పాస్‌వర్డ్‌లను మర్చిపోవాలా? |  |
| `rd_history_hide` | Hide | దాచు |  |
| `rd_history_show` | Show %1$d | %1$d చూపించు |  |
| `rd_hold_to_reveal` | Hold to reveal | చూడటానికి నొక్కి పట్టుకోండి |  |
| `rd_last_edit` | last edit %1$s | చివరి మార్పు %1$s |  |
| `rd_release_to_hide` | Release to hide | దాచడానికి వదిలేయండి |  |
| `re_add_field` | + Add another field | + మరో ఫీల్డ్ జోడించండి |  |
| `re_add_field_title` | Add a field | ఫీల్డ్ జోడించండి |  |
| `re_field_name` | Field name | ఫీల్డ్ పేరు |  |
| `re_pick_date` | Pick a date | తేదీ ఎంచుకోండి |  |
| `re_remove` | Remove | తీసివేయి |  |
| `re_tap_card` | Read the card by tapping it | కార్డును తాకి చదవండి |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | రహస్యంగా చూడు (దాచబడుతుంది, చూడటానికి నొక్కి పట్టుకోండి) |  |
| `re_using_template` | using the %1$s template | %1$s టెంప్లేట్‌తో |  |
| `rem_kit_title` | No recovery kit saved | రికవరీ కిట్ సేవ్ చేయలేదు |  |
| `scr_about_license` | License: GPL-3.0 — free forever | లైసెన్స్: GPL-3.0 — ఎప్పటికీ ఉచితం |  |
| `scr_about_source` | Source code | సోర్స్ కోడ్ |  |
| `scr_about_version` | Version %1$s | వెర్షన్ %1$s |  |
| `scr_auth_add_qr` | Add via QR | QR తో జోడించండి |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | మీ యాప్‌లు, బ్రోకర్ కోడ్‌లు ఇక్కడ కనిపిస్తాయి |  |
| `scr_auth_scan_title` | Point the camera at the QR code | కెమెరాను QR కోడ్ మీద పెట్టండి |  |
| `scr_detail_copied` | Copied · clears in 30s | కాపీ అయ్యింది · 30 సెకన్లలో చెరిగిపోతుంది |  |
| `scr_detail_copy` | Copy | కాపీ |  |
| `scr_detail_edit` | Edit | మార్చు |  |
| `scr_detail_favorite` | Favourite | ఇష్టమైనవి |  |
| `scr_detail_hidden` | Hidden | దాచబడింది |  |
| `scr_detail_hide` | Hide | దాచు |  |
| `scr_detail_history_empty` | Nothing replaced yet. | ఇంకా ఏమీ మారలేదు. |  |
| `scr_detail_history_title` | Previous passwords | పాత పాస్‌వర్డ్‌లు |  |
| `scr_detail_reveal` | Show | చూపించు |  |
| `scr_detail_shown` | Shown | కనిపిస్తోంది |  |
| `scr_edit_cancel` | Cancel | రద్దు |  |
| `scr_edit_generate` | Generate | తయారు చేయి |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | బ్యాంక్ / కంపెనీ (గ్రూప్ చేయడానికి) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | ఇది సరిగ్గా అనిపించట్లేదు — ఒకసారి చూడండి |  |
| `scr_edit_link_none` | None | ఏదీ లేదు |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | ఈ కార్డ్ నంబర్ మామూలు తనిఖీలో నెగ్గలేదు — సరైనదైతే దాచేయండి |  |
| `scr_edit_month` | Month | నెల |  |
| `scr_edit_picker_other` | Other… | ఇతర… |  |
| `scr_edit_picker_other_hint` | Type your own | మీది రాయండి |  |
| `scr_edit_required_title` | Give it a name first | ముందు దీనికి ఒక పేరు పెట్టండి |  |
| `scr_edit_save` | Save | దాచు |  |
| `scr_edit_title_hint` | Title | పేరు |  |
| `scr_edit_title_new` | New | కొత్తది |  |
| `scr_edit_year` | Year | సంవత్సరం |  |
| `scr_gallery_quick_add` | Quick add | వెంటనే జోడించండి |  |
| `scr_gallery_title` | What do you want to save? | మీరు ఏమి దాచాలనుకుంటున్నారు? |  |
| `scr_home_add` | Add | జోడించండి |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | మీ బ్యాంక్ ఖాతా ఇలా కనిపిస్తుంది |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | మీ కార్డులు, UPI, యాప్ లాగిన్‌లు కూడా ఇక్కడే ఉంటాయి |  |
| `scr_home_group_other` | Other | ఇతరాలు |  |
| `scr_home_no_results` | Nothing matches your search | మీ వెతుకులాటకు ఏమీ దొరకలేదు |  |
| `scr_home_search_hint` | Search your vault | మీ ఖజానాలో వెతకండి |  |
| `scr_home_tab_authenticator` | Authenticator | కోడ్‌లు |  |
| `scr_home_tab_home` | Home | హోమ్ |  |
| `scr_home_tab_settings` | Settings | సెట్టింగ్‌లు |  |
| `scr_home_title` | Home | హోమ్ |  |
| `scr_language_continue` | Continue | కొనసాగించండి |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | మీ భాషను ఎంచుకోండి |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | కాపీ చేయడానికి బటన్ నొక్కండి · 30 సెకన్లలో చెరిగిపోతుంది |  |
| `scr_login_helper_channel` | Login helper | లాగిన్ సహాయకుడు |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s లో లాగిన్ అవుతోంది |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | ఇప్పుడే బ్యాకప్ ఫోల్డర్ సెట్ చేయండి |  |
| `scr_quickunlock_enable` | Turn on | ఆన్ చేయండి |  |
| `scr_quickunlock_skip` | Not now | ఇప్పుడు వద్దు |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | వేలిముద్ర లేదా ముఖంతో తెరవండి |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s తేదీ దగ్గరపడింది · Zerokosh తెరవండి |  |
| `scr_reminder_channel` | Renewal reminders | పునరుద్ధరణ గుర్తుచేత |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh గుర్తు చేస్తోంది |  |
| `scr_settings_about` | About | గురించి |  |
| `scr_settings_allow_screenshots` | Allow screenshots | స్క్రీన్‌షాట్‌లు తీయనివ్వు |  |
| `scr_settings_autofill` | Autofill service | ఆటోఫిల్ సేవ |  |
| `scr_settings_autofill_off` | Not set up | సెట్ చేయలేదు |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | అందుబాటులో లేదు |  |
| `scr_settings_autolock` | Lock when I leave the app | యాప్ వదిలిన వెంటనే తాళం వేయి |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 నిమిషం తర్వాత |  |
| `scr_settings_autolock_immediately` | Immediately | వెంటనే |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d నిమిషాల తర్వాత |  |
| `scr_settings_change_passphrase` | Change passphrase | పాస్‌ఫ్రేజ్ మార్చండి |  |
| `scr_settings_current_passphrase` | Current passphrase | ప్రస్తుత పాస్‌ఫ్రేజ్ |  |
| `scr_settings_export` | Export | ఎగుమతి చేయి |  |
| `scr_settings_import` | Import passwords | పాస్‌వర్డ్‌లు దిగుమతి చేయి |  |
| `scr_settings_language` | Language | భాష |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | కొత్త పాస్‌ఫ్రేజ్ (కనీసం 10 అక్షరాలు) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | కొత్త రికవరీ కీ తీసుకోండి |  |
| `scr_settings_passphrase_changed` | Passphrase changed | పాస్‌ఫ్రేజ్ మారింది |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | వేలిముద్ర / ముఖ అన్‌లాక్ |  |
| `scr_settings_security_info` | How your data is protected | మీ డేటా ఎలా రక్షించబడుతుంది |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | బ్యాకప్ మరియు సింక్ ఫోల్డర్ |  |
| `scr_settings_sync_not_set` | Not backed up | బ్యాకప్ లేదు |  |
| `scr_settings_title` | Settings | సెట్టింగ్‌లు |  |
| `se_title` | Not saved | దాచబడలేదు |  |
| `st_active_folder` | Active Folder | నడుస్తున్న ఫోల్డర్ |  |
| `st_active_value` | Active · %1$s | నడుస్తోంది · %1$s |  |
| `st_backing_up` | Backing up vault… | ఖజానా బ్యాకప్ అవుతోంది… |  |
| `st_backup_now` | Backup Now | ఇప్పుడే బ్యాకప్ తీయి |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | బ్యాకప్ మరియు సింక్ ఫోల్డర్ |  |
| `st_change_folder` | Change Folder | ఫోల్డర్ మార్చు |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | కొత్త పాస్‌ఫ్రేజ్ మళ్ళీ టైప్ చేయండి |  |
| `st_connected_folder` | Connected folder: %1$s | కలిసిన ఫోల్డర్: %1$s |  |
| `st_disconnect` | Disconnect | తీసివేయి |  |
| `st_done` | Done | అయ్యింది |  |
| `st_export_kosh` | Export encrypted .kosh | ఎన్‌క్రిప్ట్ చేసిన .kosh ఎగుమతి |  |
| `st_folder_fallback` | Folder | ఫోల్డర్ |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | పాస్‌ఫ్రేజ్ మర్చిపోయారా? |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | మీ వేలిముద్రతో కొత్తది పెట్టండి |  |
| `st_generate` | Generate | తయారు చేయి |  |
| `st_group_about` | About | గురించి |  |
| `st_group_appearance` | Appearance | రూపం |  |
| `st_group_security` | Security | భద్రత |  |
| `st_group_sync` | Sync | సింక్ |  |
| `st_import_kosh` | Import a .kosh backup | .kosh బ్యాకప్ దిగుమతి |  |
| `st_import_other` | Import from another password manager | వేరే పాస్‌వర్డ్ మేనేజర్ నుండి దిగుమతి |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | లైసెన్స్ |  |
| `st_logos_by` | Logos provided by | లోగోలు ఇచ్చినవారు |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | దాన్ని ఆఫ్‌లైన్‌లో ఉంచండి. పాత రికవరీ కీ ఇక చెల్లదు. |  |
| `st_new_recovery_result` | Your new Recovery Key: | మీ కొత్త రికవరీ కీ: |  |
| `st_subtitle` | Your rules. | మీ నియమాలు. |  |
| `st_theme` | Theme | థీమ్ |  |
| `st_theme_dark` | Dark | ముదురు |  |
| `st_theme_light` | Light | లేత |  |
| `st_theme_system` | System | సిస్టమ్ |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | ఖజానా %1$s లో దాచబడింది! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | బ్యాకప్ కాలేదు — ఫోల్డర్ అనుమతి చూడండి |  |
| `st_toast_disconnected` | Backup folder disconnected | బ్యాకప్ ఫోల్డర్ తీసివేయబడింది |  |
| `st_toast_export_failed` | Export failed | ఎగుమతి కాలేదు |  |
| `st_toast_exported` | Encrypted vault exported | ఎన్‌క్రిప్ట్ చేసిన ఖజానా ఎగుమతి అయ్యింది |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | బ్యాకప్ ఫోల్డర్ కలిసింది, ఖజానా %1$s లో దాచబడింది! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | బ్యాకప్ ఫోల్డర్ కలిసింది: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | ఫోల్డర్ కలపలేకపోయాము: %1$s |  |
| `st_vault_review` | Vault review | ఖజానా పరిశీలన |  |
| `st_vault_review_detail` | Reused, weak, expiring | మళ్ళీ వాడినవి, బలహీనమైనవి, అయిపోతున్నవి |  |
| `tab_codes` | Codes | కోడ్‌లు |  |
| `tab_settings` | Settings | సెట్టింగ్‌లు |  |
| `tab_templates` | Templates | టెంప్లేట్ |  |
| `tab_vault` | Vault | ఖజానా |  |
| `time_days` | %1$dd ago | %1$d రో క్రితం |  |
| `time_hours` | %1$dh ago | %1$d గం క్రితం |  |
| `time_just_now` | just now | ఇప్పుడే |  |
| `time_minutes` | %1$dm ago | %1$d ని క్రితం |  |
| `time_months` | %1$dmo ago | %1$d నెలల క్రితం |  |
| `time_years` | %1$dy ago | %1$d సంవత్సరాల క్రితం |  |
| `tpl_aadhaar_card` | Aadhaar Card | ఆధార్ |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | ఆధార్ నంబర్ |  |
| `tpl_aadhaar_card_address` | Address | ఆధార్‌పై చిరునామా |  |
| `tpl_aadhaar_card_dob` | Dob | పుట్టిన తేదీ |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | స్కాన్ చేసిన కాపీ |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | అనుసంధాన మొబైల్ |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar పాస్‌కోడ్ |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | ఆధార్‌పై పేరు |  |
| `tpl_aadhaar_card_notes` | Notes | గమనికలు |  |
| `tpl_app_profile` | App Profile | యాప్ ప్రొఫైల్ |  |
| `tpl_app_profile_app_name` | App name | యాప్ పేరు |  |
| `tpl_app_profile_gift_cards` | Gift cards | గిఫ్ట్ కార్డులు |  |
| `tpl_app_profile_membership` | Membership | సభ్యత్వం |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | సభ్యత్వ పునరుద్ధరణ |  |
| `tpl_app_profile_notes` | Notes | గమనికలు |  |
| `tpl_app_profile_password_if_any` | Password (if any) | పాస్‌వర్డ్ (ఉంటే) |  |
| `tpl_app_profile_registered_email` | Registered email | నమోదైన ఈమెయిల్ |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | నమోదైన మొబైల్ |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | వాలెట్ పిన్ |  |
| `tpl_bank_account` | Bank Account | బ్యాంక్ ఖాతా |  |
| `tpl_bank_account_account_number` | Account number | ఖాతా నంబర్ |  |
| `tpl_bank_account_account_type` | Account type | ఖాతా రకం |  |
| `tpl_bank_account_bank_name` | Bank name | బ్యాంక్ పేరు |  |
| `tpl_bank_account_branch` | Branch | శాఖ |  |
| `tpl_bank_account_customer_id` | Customer id | కస్టమర్ ID |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC కోడ్ |  |
| `tpl_bank_account_login_password` | Login password | లాగిన్ పాస్‌వర్డ్ |  |
| `tpl_bank_account_micr` | MICR code | MICR కోడ్ |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | నెట్-బ్యాంకింగ్ యూజర్ ID |  |
| `tpl_bank_account_nominee` | Nominee | నామినీ |  |
| `tpl_bank_account_notes` | Notes | గమనికలు |  |
| `tpl_bank_account_profile_password` | Profile password | ప్రొఫైల్ పాస్‌వర్డ్ |  |
| `tpl_bank_account_registered_email` | Registered email | నమోదైన ఈమెయిల్ |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | నమోదైన మొబైల్ |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | లావాదేవీ పాస్‌వర్డ్ |  |
| `tpl_card` | Card | కార్డు |  |
| `tpl_card_atm_pin` | ATM PIN | ATM పిన్ |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | బిల్లింగ్ సైకిల్ రోజు |  |
| `tpl_card_card_network` | Card network | నెట్‌వర్క్ |  |
| `tpl_card_card_number` | Card number | కార్డు నంబర్ |  |
| `tpl_card_card_portal_login` | Card portal login | కార్డు పోర్టల్ లాగిన్ |  |
| `tpl_card_card_portal_password` | Card portal password | కార్డు పోర్టల్ పాస్‌వర్డ్ |  |
| `tpl_card_card_type` | Card type | కార్డు రకం |  |
| `tpl_card_card_variant` | Card variant | కార్డు వేరియంట్ |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | గడువు |  |
| `tpl_card_linked_account` | Linked account | అనుసంధాన ఖాతా |  |
| `tpl_card_name_on_card` | Name on card | కార్డుపై పేరు |  |
| `tpl_card_notes` | Notes | గమనికలు |  |
| `tpl_demat` | Demat | డీమ్యాట్ |  |
| `tpl_demat_api_key` | API key | API కీ |  |
| `tpl_demat_api_secret` | API secret | API సీక్రెట్ |  |
| `tpl_demat_bo_id` | BO ID | BO ID |  |
| `tpl_demat_broker` | Broker | బ్రోకర్ |  |
| `tpl_demat_cdsl_tpin` | CDSL TPIN | CDSL TPIN |  |
| `tpl_demat_client_id` | Client id | క్లయింట్ ID |  |
| `tpl_demat_depository` | Depository | డిపాజిటరీ |  |
| `tpl_demat_dp_id` | DP ID | DP ID |  |
| `tpl_demat_login_password` | Login password | లాగిన్ పాస్‌వర్డ్ |  |
| `tpl_demat_mf_folios` | Mutual fund folios | మ్యూచువల్ ఫండ్ ఫోలియో |  |
| `tpl_demat_mpin` | MPIN | MPIN |  |
| `tpl_demat_nominee` | Nominee | నామినీ |  |
| `tpl_demat_notes` | Notes | గమనికలు |  |
| `tpl_digilocker` | Digilocker | DigiLocker |  |
| `tpl_digilocker_login_username` | Login username | యూజర్‌నేమ్ |  |
| `tpl_digilocker_notes` | Notes | గమనికలు |  |
| `tpl_digilocker_portal_password` | Portal password | పాస్‌వర్డ్ |  |
| `tpl_digilocker_security_pin` | Security pin | భద్రతా పిన్ |  |
| `tpl_driving_license` | Driving License | డ్రైవింగ్ లైసెన్స్ |  |
| `tpl_driving_license_dl_number` | Dl number | లైసెన్స్ నంబర్ |  |
| `tpl_driving_license_dob` | Dob | పుట్టిన తేదీ |  |
| `tpl_driving_license_expiry_date` | Expiry date | ఈ తేదీ వరకు చెల్లుతుంది |  |
| `tpl_driving_license_file_copy` | Scanned copy | స్కాన్ చేసిన కాపీ |  |
| `tpl_driving_license_issue_date` | Issue date | జారీ తేదీ |  |
| `tpl_driving_license_name_on_dl` | Name on dl | లైసెన్స్‌పై పేరు |  |
| `tpl_driving_license_notes` | Notes | గమనికలు |  |
| `tpl_driving_license_rto_location` | Rto location | RTO |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | వాహన తరగతులు |  |
| `tpl_epf_pension` | Epf Pension | EPF / పెన్షన్ |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | అనుసంధాన మొబైల్ |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | EPF పై పేరు |  |
| `tpl_epf_pension_nominee` | Nominee | నామినీ |  |
| `tpl_epf_pension_notes` | Notes | గమనికలు |  |
| `tpl_epf_pension_password` | Password | పాస్‌వర్డ్ |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | PF సభ్యుడి ID |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO పాస్‌వర్డ్ |  |
| `tpl_epf_pension_scheme` | Scheme | పథకం |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | ప్రభుత్వ గుర్తింపు కార్డు |  |
| `tpl_gov_id_expiry` | Expiry | గడువు |  |
| `tpl_gov_id_file_copy` | Scanned copy | స్కాన్ చేసిన కాపీ |  |
| `tpl_gov_id_id_kind` | ID type | గుర్తింపు కార్డు రకం |  |
| `tpl_gov_id_id_number` | ID number | గుర్తింపు నంబర్ |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | కార్డు ప్రకారం పేరు |  |
| `tpl_gov_id_notes` | Notes | గమనికలు |  |
| `tpl_gov_id_portal_login` | Portal login | పోర్టల్ లాగిన్ |  |
| `tpl_gov_id_portal_password` | Portal password | పోర్టల్ పాస్‌వర్డ్ |  |
| `tpl_insurance` | Insurance | బీమా |  |
| `tpl_insurance_agent_contact` | Agent contact | ఏజెంట్ సంప్రదింపు |  |
| `tpl_insurance_commencement_date` | Commencement date | మొదలైన తేదీ |  |
| `tpl_insurance_insurer` | Insurer | బీమా సంస్థ |  |
| `tpl_insurance_maturity_date` | Maturity date | మెచ్యూరిటీ తేదీ |  |
| `tpl_insurance_nominee` | Nominee | నామినీ |  |
| `tpl_insurance_notes` | Notes | గమనికలు |  |
| `tpl_insurance_policy_number` | Policy number | పాలసీ నంబర్ |  |
| `tpl_insurance_policy_term` | Policy term | పాలసీ కాలం |  |
| `tpl_insurance_policy_type` | Policy type | పాలసీ రకం |  |
| `tpl_insurance_portal_login` | Portal login | పోర్టల్ లాగిన్ |  |
| `tpl_insurance_portal_password` | Portal password | పోర్టల్ పాస్‌వర్డ్ |  |
| `tpl_insurance_premium_amount` | Premium amount | ప్రీమియం మొత్తం |  |
| `tpl_insurance_premium_due_date` | Premium due date | ప్రీమియం తేదీ |  |
| `tpl_insurance_premium_mode` | Premium mode | ప్రీమియం ఎలా కడతారు |  |
| `tpl_insurance_sum_assured` | Sum assured | బీమా మొత్తం |  |
| `tpl_login` | Login | లాగిన్ |  |
| `tpl_login_notes` | Notes | గమనికలు |  |
| `tpl_login_password` | Password | పాస్‌వర్డ్ |  |
| `tpl_login_recovery_codes` | Recovery codes | రికవరీ కోడ్‌లు |  |
| `tpl_login_username` | Username | యూజర్‌నేమ్ |  |
| `tpl_login_website` | Website | వెబ్‌సైట్ |  |
| `tpl_pan_card` | Pan Card | PAN కార్డు |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | ఆధార్‌తో అనుసంధానం |  |
| `tpl_pan_card_dob` | Dob | పుట్టిన తేదీ |  |
| `tpl_pan_card_e_filing_password` | E filing password | ఇ-ఫైలింగ్ పాస్‌వర్డ్ |  |
| `tpl_pan_card_fathers_name` | Fathers name | తండ్రి పేరు |  |
| `tpl_pan_card_file_copy` | Scanned copy | స్కాన్ చేసిన కాపీ |  |
| `tpl_pan_card_name_on_pan` | Name on pan | PAN పై పేరు |  |
| `tpl_pan_card_notes` | Notes | గమనికలు |  |
| `tpl_pan_card_pan_number` | Pan number | PAN |  |
| `tpl_passkey` | Passkey | పాస్‌కీ |  |
| `tpl_passkey_credential_id` | Credential ID | క్రెడెన్షియల్ ID |  |
| `tpl_passkey_notes` | Notes | గమనికలు |  |
| `tpl_passkey_private_key` | Private key | ప్రైవేట్ కీ |  |
| `tpl_passkey_sign_count` | Sign count | సైన్ కౌంట్ |  |
| `tpl_passkey_user_handle` | User handle | యూజర్ హ్యాండిల్ |  |
| `tpl_passkey_username` | Username | యూజర్‌నేమ్ |  |
| `tpl_passkey_website` | Website | వెబ్‌సైట్ |  |
| `tpl_passport` | Passport | పాస్‌పోర్ట్ |  |
| `tpl_passport_dob` | Dob | పుట్టిన తేదీ |  |
| `tpl_passport_expiry_date` | Expiry date | గడువు తేదీ |  |
| `tpl_passport_file_copy` | Scanned copy | స్కాన్ చేసిన కాపీ |  |
| `tpl_passport_given_names` | Given names | పెట్టిన పేరు |  |
| `tpl_passport_issue_date` | Issue date | జారీ తేదీ |  |
| `tpl_passport_notes` | Notes | గమనికలు |  |
| `tpl_passport_passport_number` | Passport number | పాస్‌పోర్ట్ నంబర్ |  |
| `tpl_passport_place_of_issue` | Place of issue | జారీ చేసిన స్థలం |  |
| `tpl_passport_portal_login` | Portal login | Passport Seva లాగిన్ |  |
| `tpl_passport_portal_password` | Portal password | Passport Seva పాస్‌వర్డ్ |  |
| `tpl_passport_surname` | Surname | ఇంటిపేరు |  |
| `tpl_secure_note` | Secure Note | సురక్షిత గమనిక |  |
| `tpl_secure_note_attachment` | Attachment | జతపరిచినది |  |
| `tpl_secure_note_body` | Note | గమనిక |  |
| `tpl_shopping` | Shopping | షాపింగ్ ఖాతా |  |
| `tpl_shopping_gift_card_code` | Gift card code | గిఫ్ట్ కార్డు కోడ్ |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | గిఫ్ట్ కార్డు పిన్ |  |
| `tpl_shopping_membership_id` | Membership id | సభ్యత్వ ID |  |
| `tpl_shopping_notes` | Notes | గమనికలు |  |
| `tpl_shopping_password` | Password | పాస్‌వర్డ్ |  |
| `tpl_shopping_registered_email` | Registered email | నమోదైన ఈమెయిల్ |  |
| `tpl_shopping_registered_mobile` | Registered mobile | నమోదైన మొబైల్ |  |
| `tpl_shopping_wallet_pin` | Wallet pin | వాలెట్ పిన్ |  |
| `tpl_telecom` | Telecom | మొబైల్ మరియు ఇంటర్నెట్ |  |
| `tpl_telecom_account_number` | Account number | ఖాతా నంబర్ |  |
| `tpl_telecom_circle` | Circle | సర్కిల్ |  |
| `tpl_telecom_mobile_number` | Mobile number | మొబైల్ నంబర్ |  |
| `tpl_telecom_notes` | Notes | గమనికలు |  |
| `tpl_telecom_operator` | Operator | సంస్థ |  |
| `tpl_telecom_plan_type` | Plan type | ప్లాన్ రకం |  |
| `tpl_telecom_portal_password` | Portal password | పోర్టల్ పాస్‌వర్డ్ |  |
| `tpl_telecom_puk` | PUK code | PUK కోడ్ |  |
| `tpl_telecom_renewal_date` | Renewal date | రీచార్జ్ తేదీ |  |
| `tpl_telecom_sim_number` | Sim number | సిమ్ నంబర్ (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | సిమ్ పిన్ |  |
| `tpl_transit` | Transit | ట్రాన్సిట్ పాస్ |  |
| `tpl_transit_login_password` | Login password | లాగిన్ పాస్‌వర్డ్ |  |
| `tpl_transit_notes` | Notes | గమనికలు |  |
| `tpl_transit_operator_name` | Operator name | సంస్థ |  |
| `tpl_transit_registered_email` | Registered email | నమోదైన ఈమెయిల్ |  |
| `tpl_transit_registered_mobile` | Registered mobile | నమోదైన మొబైల్ |  |
| `tpl_transit_smart_card_number` | Smart card number | స్మార్ట్ కార్డు నంబర్ |  |
| `tpl_transit_wallet_pin` | Wallet pin | వాలెట్ పిన్ |  |
| `tpl_travel_booking` | Travel Booking | ప్రయాణ బుకింగ్ |  |
| `tpl_travel_booking_account_username` | Account username | యూజర్‌నేమ్ |  |
| `tpl_travel_booking_login_password` | Login password | లాగిన్ పాస్‌వర్డ్ |  |
| `tpl_travel_booking_notes` | Notes | గమనికలు |  |
| `tpl_travel_booking_provider` | Provider | సంస్థ |  |
| `tpl_travel_booking_registered_email` | Registered email | నమోదైన ఈమెయిల్ |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | నమోదైన మొబైల్ |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | వాలెట్ పిన్ |  |
| `tpl_upi` | UPI | UPI |  |
| `tpl_upi_app_name` | App name | UPI యాప్ |  |
| `tpl_upi_apps_used` | Apps used | ఏ యాప్‌లో నడుస్తోంది |  |
| `tpl_upi_linked_account` | Linked account | అనుసంధాన ఖాతా |  |
| `tpl_upi_notes` | Notes | గమనికలు |  |
| `tpl_upi_upi_id` | UPI ID | UPI ID |  |
| `tpl_upi_upi_pin` | UPI PIN | UPI పిన్ |  |
| `tpl_utility` | Utility | బిల్లులు మరియు కనెక్షన్లు |  |
| `tpl_utility_account_holder` | Account holder | ఖాతాదారు |  |
| `tpl_utility_consumer_number` | Consumer number | వినియోగదారు నంబర్ |  |
| `tpl_utility_due_day` | Bill due day | బిల్లు కట్టే రోజు |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag ID |  |
| `tpl_utility_notes` | Notes | గమనికలు |  |
| `tpl_utility_portal_login` | Portal login | పోర్టల్ లాగిన్ |  |
| `tpl_utility_portal_password` | Portal password | పోర్టల్ పాస్‌వర్డ్ |  |
| `tpl_utility_provider` | Provider | సేవ అందించే సంస్థ |  |
| `tpl_utility_utility_kind` | Utility kind | దేని బిల్లు |  |
| `tpl_utility_vehicle_number` | Vehicle number | వాహన నంబర్ |  |
| `tpl_utility_wifi_password` | Wi-Fi password | Wi-Fi పాస్‌వర్డ్ |  |
| `tpl_voter_id` | Voter Id | ఓటరు గుర్తింపు |  |
| `tpl_voter_id_constituency` | Constituency | నియోజకవర్గం |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC నంబర్ |  |
| `tpl_voter_id_file_copy` | Scanned copy | స్కాన్ చేసిన కాపీ |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | ఓటరు కార్డుపై పేరు |  |
| `tpl_voter_id_notes` | Notes | గమనికలు |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP పాస్‌వర్డ్ |  |
| `tr_days_many` | %1$d days left | %1$d రోజులు మిగిలాయి |  |
| `tr_days_one` | %1$d day left | %1$d రోజు మిగిలింది |  |
| `tr_gone_today` | gone today | ఈరోజు పోతుంది |  |
| `tr_restore` | Restore | తిరిగి తేండి |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | ఆ తర్వాత అవి శాశ్వతంగా పోతాయి — వేరే ఎక్కడా కాపీ లేదు. |  |
| `ui_hide_passphrase` | Hide passphrase | పాస్‌ఫ్రేజ్ దాచు |  |
| `ui_label_count` | %1$s, %2$s | %1$s, %2$s |  |
| `ui_show_passphrase` | Show passphrase | పాస్‌ఫ్రేజ్ చూపించు |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | ఈ ఫోన్‌లోనే %1$d రికార్డులతో సరిపోల్చాము. ఏదీ ఎక్కడికీ పంపబడలేదు. |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | ఈ ఫోన్‌లోనే %1$d రికార్డుతో సరిపోల్చాము. ఏదీ ఎక్కడికీ పంపబడలేదు. |  |
| `vh_count_many` | %1$d things worth a look. | %1$d విషయాలు చూడదగినవి. |  |
| `vh_count_one` | %1$d thing worth a look. | %1$d విషయం చూడదగినది. |  |
| `vh_empty` | No reused, weak or expiring credentials. | మళ్ళీ వాడిన, బలహీనమైన లేదా అయిపోతున్న సమాచారం లేదు. |  |
| `vh_kind_common` | Commonly guessed | సులభంగా ఊహించగలిగేది |  |
| `vh_kind_expiring` | Expiring | అయిపోతోంది |  |
| `vh_kind_reused` | Reused password | మళ్ళీ వాడిన పాస్‌వర్డ్ |  |
| `vh_kind_weak` | Weak | బలహీనం |  |
| `vh_no_kit_title` | No recovery kit saved | రికవరీ కిట్ సేవ్ చేయలేదు |  |
| `vh_nothing` | Nothing to fix. | సరిచేయాల్సింది ఏమీ లేదు. |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | ఆఫ్‌లైన్ |  |
| `wl_chip_open` | Open source | ఓపెన్ సోర్స్ |  |
| `wl_create` | Create a new vault | కొత్త ఖజానా తయారు చేయండి |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | ఈమెయిల్ లేదు · ఖాతా లేదు · ఏదీ ఈ ఫోన్ దాటి వెళ్ళదు |  |
| `wl_head_1` | Your keys. | మీ తాళంచెవులు. |  |
| `wl_head_2` | Your device. | మీ ఫోన్. |  |
| `wl_head_3` | No server. | సర్వర్ లేదు. |  |
| `wl_restore` | Restore from Recovery Kit | రికవరీ కిట్ నుండి తిరిగి తేండి |  |
| `wl_sr_headline` | Your keys. Your device. No server. | మీ తాళంచెవులు. మీ ఫోన్. సర్వర్ లేదు. |  |
