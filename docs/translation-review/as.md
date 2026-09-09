# Assamese (`as`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-as/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Assamese | ok? |
|---|---|---|---|
| `au_close` | Close | বন্ধ কৰক |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | বাছনি কৰা ছবিত সঠিক TOTP QR ক\'ড পোৱা নগ\'ল |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | খালীৰ পৰা আৰম্ভ কৰি নিজৰ ঘৰবোৰৰ নাম নিজেই দিয়ক — টেমপ্লেটে কেৱল লেবেলহে ভৰায়, তথ্য কেতিয়াও নহয়। |  |
| `hm_close_search` | Close search | সন্ধান বন্ধ কৰক |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | এই ফোনৰ পৰা কেতিয়াও বাহিৰলৈ নাযায়। সাঁচি থোৱাৰ সময়ত এনক্ৰিপ্ট কৰা হয়। |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | এতিয়া আপুনি আমদানি কৰা ফাইলটো আঁতৰাই দিয়ক। সেয়া আপোনাৰ পাছৱৰ্ডৰ খোলা তালিকা, আৰু এতিয়াও আপোনাৰ Downloads-ত পৰি আছে। |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | সকলো কেৱল এই ফোনতে ডিক্ৰিপ্ট কৰা হয়। একো আপল\'ড নহয়, কাৰণ এই এপে নেটৱৰ্ক সংযোগ খুলিবই নোৱাৰে। |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | বেংকে কেতিয়াও আপোনাৰ OTP নিবিচাৰে। যিয়ে বিচাৰে, তেওঁ প্ৰতাৰক। |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | কোনো বেংক বিষয়াই আপোনাক স্ক্ৰীন শ্বেয়াৰ কৰা এপ ইনষ্টল কৰিবলৈ নকয়। |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | আপোনাৰ UPI পিন কেৱল UPI এপৰ কীপেডৰ বাবে — ফোনত কাকো নকব। |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | KYC এদিনতে শেষ নহয়। “আজি KYC শেষ” বুলি অহা বাৰ্তা প্ৰতাৰণা। |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | ধন পাবলৈ পিন দিব নালাগে, QR স্কেনো কৰিব নালাগে। |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | বিদ্যুৎ কাটিব বুলি SMS, আৰু তাত কাৰোবাৰ ব্যক্তিগত নম্বৰ? সেয়া প্ৰতাৰণা। |  |
| `nav_close_menu` | Close menu | মেনু বন্ধ কৰক |  |
| `nfc_cannot_read` | Cannot read cards | কাৰ্ড পঢ়িব নোৱাৰি |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | এই ফোনত NFC নাই, গতিকে কাৰ্ড পঢ়িব নোৱাৰি। |  |
| `ob_fact_lost_title` | If you lose your keys | চাবি হেৰুৱালে |  |
| `ob_fact_network_note` | The app literally cannot phone home | এই এপে ক\'তো সংযোগ কৰিব নোৱাৰে |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | বায়\'মেট্ৰিক সুৰক্ষা চিপৰ বাহিৰলৈ কেতিয়াও নাযায় |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | আপোনাৰ এনক্ৰিপ্ট কৰা ভঁৰাল ছিংক কৰা সেই একেটা ফোল্ডাৰতে চাবিটো ৰাখিছে। এতিয়া যিয়ে সেই ফোল্ডাৰ পাব, তেওঁ দুয়োটাই পাব। চাবিটো আন ক\'ৰবাত ৰাখক — কাগজ, আন এটা একাউণ্ট, বা এটা দেৰাজ। |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | এইটো আপোনাৰ ভঁৰাল ফাইলৰ কাষতে আছে |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH ৰিকভাৰী |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | স্কেন কৰক বা লিখক। পুনৰ ইনষ্টল, ফেক্টৰী ৰিছেট, বা ফোন হেৰুওৱাৰ পিছতো কাম কৰে। |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | এইমাত্ৰ সাঁচি থোৱা কিটৰ পৰা গোট %1$d আৰু গোট %2$d লিখক। |  |
| `ob_kit_challenge_hint` | Group %1$d | গোট %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | প্ৰথমে কিট সাঁচক, তাৰ পিছত গোট %1$d আৰু %2$d ঘূৰাই লিখক। |  |
| `ob_kit_challenge_title` | Check you actually have it | কিট সঁচাকৈ আপোনাৰ ওচৰত আছে নে চাওক |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | এইটো ওপৰৰ চাবিৰ লগত মিলা নাই। |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | কোনেও — Zerokosh-এও — এইটো মোৰ বাবে ঘূৰাই আনিব নোৱাৰে। |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "মই ইয়াক অফলাইনত ৰাখিছোঁ। " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | এবাৰ মাত্ৰ দেখা যায়, কেতিয়াও খোলা ৰূপত সাঁচি থোৱা নহয়। ভিতৰলৈ আহিব পাৰিলে ছেটিংছৰ পৰা নতুন এটা বনাওক। |  |
| `ob_kit_head_emph` | On paper. | কাগজত। |  |
| `ob_kit_head_lead` | "One key. " | "এটা চাবি। " |  |
| `ob_kit_head_tail` | " Never online." | " কেতিয়াও অনলাইনত নহয়।" |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | Gmail নহয়, WhatsApp নহয়, স্ক্ৰীনশ্বটো নহয়। আলমাৰী, বেংক লকাৰ, বা ষ্টিলৰ পাত। |  |
| `ob_kit_offline_title` | Keep it off the internet | ইয়াক ইণ্টাৰনেটৰ পৰা আঁতৰাই ৰাখক |  |
| `ob_kit_print` | Print | ছপা কৰক |  |
| `ob_kit_print_note` | A printer, or Save as PDF | প্ৰিণ্টাৰ, বা PDF হিচাপে সাঁচক |  |
| `ob_kit_qr` | QR image | QR ছবি |  |
| `ob_kit_qr_cd` | Recovery key QR code | ৰিকভাৰী চাবিৰ QR ক\'ড |  |
| `ob_kit_qr_note` | To an offline gallery | অফলাইন গেলাৰীলৈ |  |
| `ob_kit_regenerate` | Regenerate | নতুনকৈ বনাওক |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | এইটো সাঁচি থোৱা নহ\'ল। আকৌ চেষ্টা কৰক, বা আন ঠাই বাছনি কৰক। |  |
| `ob_kit_save_pdf` | Save PDF | PDF সাঁচক |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | এপৃষ্ঠীয়া ছপাব পৰা কিট |  |
| `ob_kit_saved` | I\'ve saved my kit | মই মোৰ কিট সাঁচি থৈছোঁ |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s-ত সাঁচি থোৱা হ\'ল |  |
| `ob_kit_sent_to_printer` | Sent to the printer | প্ৰিণ্টাৰলৈ পঠিওৱা হ\'ল |  |
| `ob_kit_skip` | I\'ll do this later | এইটো পিছত কৰিম |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | আপোনাৰ ভঁৰাল চলি থাকিব। কিট সাঁচি নথোৱালৈকে Zerokosh মনত পেলাই থাকিব। |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | এই ফোনতে বনোৱা, কেৱল এবাৰহে দেখা যাব। পাছফ্ৰেজ পাহৰিলে ভিতৰলৈ ঘূৰি অহাৰ এইটোৱেই একমাত্ৰ বাট। |  |
| `ob_kit_working` | Working… | কাম চলি আছে… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | ভঁৰালৰ লেবেল, টেমপ্লেট আৰু সতৰ্কবাণী লগে লগে সলনি হ\'ব। ছেটিংছত যিকোনো সময়তে সলনি কৰিব পাৰে। |  |
| `ob_pass_confirm` | Confirm | আকৌ লিখক |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | এইটো আমি কেতিয়াও নেদেখোঁ। কোনো ৰিছেট লিংক নাই। |  |
| `ob_pass_head_emph` | held only | কেৱল আপোনাৰ হাতত |  |
| `ob_pass_head_lead` | "One secret, " | "এটাই গোপন কথা, " |  |
| `ob_pass_head_tail` | " by you." | । |  |
| `ob_pass_no_match` | no match | মিলা নাই |  |
| `ob_pass_seal` | Seal the vault | ভঁৰাল বন্ধ কৰক |  |
| `ob_pass_sealing` | Sealing… | বন্ধ কৰি আছোঁ… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | সম্পৰ্কহীন তিনি-চাৰিটা শব্দ, এটা চতুৰ শব্দতকৈ ভাল। এই পৰ্দাৰ পৰা একো বাহিৰলৈ নাযায়। |  |
| `ob_pass_tab_passphrase` | Passphrase | পাছফ্ৰেজ |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 সংখ্যাৰ পিন |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | আপোনাৰ আঙুলিৰ ছাপ ফোনৰ সুৰক্ষা চিপৰ ভিতৰতে থাকে। সেয়া এই ফোনৰ পৰা কেতিয়াও বাহিৰলৈ নাযায়। |  |
| `ob_trust_continue` | I understand · Continue | বুজি পালোঁ · আগবাঢ়ক |  |
| `ob_trust_head_emph` | don\'t | নাজানোঁ |  |
| `ob_trust_head_lead` | "Exactly what we " | "আমি সঁচাকৈ কি " |  |
| `ob_trust_head_tail` | " know." | । |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | পাছফ্ৰেজ পাহৰি আৰু ৰিকভাৰী কিটো হেৰুৱালে, ভঁৰাল বন্ধ হৈয়ে থাকিব — আপোনাৰ বাবে, আমাৰ বাবে, সকলোৰে বাবে। |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "চাবিবোৰ আপোনাৰ হাতত। " |  |
| `ob_trust_stat_files` | .kosh file on device | ফোনত .kosh ফাইল |  |
| `ob_trust_stat_servers` | servers contacted | চাৰ্ভাৰৰ লগত সংযোগ |  |
| `ob_trust_stat_trackers` | trackers or SDKs | ট্ৰেকাৰ বা SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | এইখিনি এবাৰ পঢ়ি লওক। গোটেই সুৰক্ষা ব্যৱস্থা এইটোৱেই, সৰল কথাত। |  |
| `ob_trust_tag_audited` | Audited build | অডিট কৰা বিল্ড |  |
| `ob_trust_tag_reproducible` | Reproducible APK | পুনৰ বনাব পৰা APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | আপুনি আঙুলিৰ ছাপেৰে খুলি আছে। আঙুলিৰ ছাপে কেতিয়াবা কাম নকৰিলে, এইটোৱেই আপোনাক ভিতৰলৈ আনিব — গতিকে চাই লোৱাটো ভাল। |  |
| `pc_confirm` | Check | চাওক |  |
| `pc_correct` | Still correct. Nothing to do. | এতিয়াও শুদ্ধ। একো কৰিবলগীয়া নাই। |  |
| `pc_forgot` | I cannot remember it | মোৰ মনত পৰা নাই |  |
| `pc_later` | Not now | এতিয়া নালাগে |  |
| `pc_reset_action` | Set new passphrase | নতুন পাছফ্ৰেজ দিয়ক |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | আপোনাৰ আঙুলিৰ ছাপে এই ভঁৰাল খুলিব পাৰে, গতিকে সেইটোৱেই নতুন পাছফ্ৰেজো দিব পাৰে — ৰিকভাৰী কিট নালাগে। নিশ্চিত কৰিবলৈ আকৌ এবাৰ সোধা হ\'ব। |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | পাছফ্ৰেজ সলনি হ\'ল। সোনকালে আনলক আকৌ ঠিক কৰা হ\'ল। |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | এইটো হোৱা নাই। আপোনাৰ পুৰণি পাছফ্ৰেজেই এতিয়াও চলি আছে। |  |
| `pc_reset_title` | Set a new passphrase | নতুন পাছফ্ৰেজ দিয়ক |  |
| `pc_title` | Do you still remember your passphrase? | আপোনাৰ পাছফ্ৰেজ এতিয়াও মনত আছে নেকি? |  |
| `pc_wrong` | That is not it. You can set a new one instead. | এইটো সেইটো নহয়। ইয়াৰ সলনি নতুন এটা দিব পাৰে। |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | এই এণ্ট্ৰীৰ বাবে মনত ৰখা %1$d টা মান আঁতৰোৱা হ\'ব। এইটো ঘূৰাই আনিব নোৱাৰি। |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh-এ সেই লগইন সাঁচিব নোৱাৰিলে। |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | ভৰাবলৈ Zerokosh খোলক |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | এই এটা পাছফ্ৰেজেই সকলো বন্ধ কৰি ৰাখে। কেৱল আপুনি জনা এটা দীঘল বাক্য বাছনি কৰক। |  |
| `scr_create_button` | Lock it in | বন্ধ কৰি দিয়ক |  |
| `scr_create_confirm_hint` | Type it again | আকৌ লিখক |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | পাছফ্ৰেজ (কমেও 10 আখৰ) |  |
| `scr_create_mismatch` | The two entries don\'t match | দুয়োটা একে নহয় |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 সংখ্যাৰ পিন |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | ইয়াৰ সলনি 6 সংখ্যাৰ পিন ৰাখক |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | পিনৰ বাবে আঙুলি বা মুখ আনলক থকা ফোন লাগে। অনুগ্ৰহ কৰি পাছফ্ৰেজ বাছনি কৰক। |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | পিন অনুমোদিত কাৰণ এই ফোনে ইয়াক নিজৰ সুৰক্ষা চিপ আৰু আপোনাৰ আঙুলি বা মুখেৰে ৰক্ষা কৰে। |  |
| `scr_create_strength_fair` | Fair | মোটামুটি |  |
| `scr_create_strength_good` | Good | ভাল |  |
| `scr_create_strength_strong` | Strong | শক্তিশালী |  |
| `scr_create_strength_weak` | Weak | দুৰ্বল |  |
| `scr_create_title` | Create your passphrase | আপোনাৰ পাছফ্ৰেজ বনাওক |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | কমেও 10 আখৰ লাগে — যিমান দীঘল, সিমান শক্তিশালী |  |
| `scr_create_working` | Preparing your vault… | আপোনাৰ ভঁৰাল সাজু হৈ আছে… |  |
| `scr_detail_delete` | Delete | আঁতৰাওক |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | সেয়া 30 দিন “শেহতীয়াকৈ আঁতৰোৱা”ত থাকিব, আৰু আপোনাৰ আন ফোনৰ লগত ছিংক হ\'লে গুচি যাব। |  |
| `scr_detail_delete_confirm_title` | Delete this record? | এই এণ্ট্ৰী আঁতৰাম নেকি? |  |
| `scr_detail_delete_confirm_yes` | Delete | আঁতৰাওক |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | চিক্ৰেট বা otpauth:// লিংক পেষ্ট কৰক |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | আপোনাৰ আঙুলি বা মুখ ব্যৱহাৰ কৰক |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh খোলক |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | বহুবাৰ ভুল চেষ্টা হ\'ল। %1$d চেকেণ্ড অপেক্ষা কৰক। |  |
| `scr_lock_hint` | Passphrase | পাছফ্ৰেজ |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | এই ৰিকভাৰী চাবি ঠিক নহয় — প্ৰতিটো আখৰ মিলাই চাওক |  |
| `scr_lock_title` | Vault is locked | ভঁৰাল বন্ধ আছে |  |
| `scr_lock_unlock` | Unlock | খোলক |  |
| `scr_lock_use_passphrase` | Use passphrase | পাছফ্ৰেজ ব্যৱহাৰ কৰক |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | অনুগ্ৰহ কৰি এবাৰ পাছফ্ৰেজেৰে খোলক |  |
| `scr_lock_use_recovery` | Use Recovery Key | ৰিকভাৰী চাবি ব্যৱহাৰ কৰক |  |
| `scr_lock_wrong` | Wrong passphrase | পাছফ্ৰেজ ভুল |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | কেতিয়াবা পাছফ্ৰেজ পাহৰি গ\'লে, ভিতৰলৈ ঘূৰি অহাৰ এইটোৱেই একমাত্ৰ বাট। আমি ইয়াক ৰিছেট কৰিব নোৱাৰোঁ — কোনেও নোৱাৰে। |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | মই ইয়াক লিখি সুৰক্ষিত ঠাইত ৰাখিছোঁ |  |
| `scr_recovery_done` | Continue | আগবাঢ়ক |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | এই চাবি এবাৰ মাত্ৰ দেখা যায়। আপুনি যেতিয়ালৈকে খুলিব পাৰে, ছেটিংছৰ পৰা যিকোনো সময়ত নতুন এটা বনাব পাৰে। |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | এই কাগজখন আপোনাৰ মাটি-বাৰীৰ কাগজ বা আন গুৰুত্বপূৰ্ণ নথিৰ লগত ৰাখক। যাৰ হাতত এই চাবি আছে, তেওঁ আপোনাৰ ভঁৰাল খুলিব পাৰে — ইয়াক লকাৰৰ চাবিৰ দৰে ৰক্ষা কৰক। |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | ৰিকভাৰী কিট PDF সাঁচি থোৱা হ\'ল |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh ৰিকভাৰী কিট |  |
| `scr_recovery_save_pdf` | Save as PDF | PDF হিচাপে সাঁচক |  |
| `scr_recovery_title` | Your Recovery Key | আপোনাৰ ৰিকভাৰী চাবি |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | আপুনি যি সাঁচি থয়, সেয়া আপোনাৰ ফোনৰ এটা বন্ধ ফাইলত থাকে। সেয়া কেতিয়াও আমাৰ ওচৰলৈ নাহে — সেয়া ৰাখিবলৈ আমাৰ ঠাইয়েই নাই। |  |
| `scr_trust_card1_title` | Your data stays on this device | আপোনাৰ তথ্য এই ফোনতে থাকে |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | Zerokosh একাউণ্ট নাই, ক্লাউড নাই, ছাইন-আপ নাই। ইয়াক কেৱল আপুনিয়েই খুলিব পাৰে। আমিও নোৱাৰোঁ। |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | আমাৰ চাৰ্ভাৰ নাই — হেক কৰিবলৈ একো নাই, বেচিবলৈও নাই |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | চাবস্ক্ৰিপচন নাই, বিজ্ঞাপন নাই। যিকোনোৱে আমাৰ ক\'ড পঢ়ি আমাৰ প্ৰতিটো কথা পৰীক্ষা কৰিব পাৰে। |  |
| `scr_trust_card3_title` | Free forever, open source | সদায় বিনামূলীয়া, মুকলি ছ\'ৰ্চ |  |
| `scr_trust_continue` | Continue | আগবাঢ়ক |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | আপোনাৰ সলনিবোৰ সাঁচি থোৱা নহ\'ল, গতিকে ভঁৰালত আগতে যি আছিল তাৰ একোৱেই হেৰোৱা নাই। |  |
| `st_recently_deleted` | Recently deleted | শেহতীয়াকৈ আঁতৰোৱা |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | এবাৰৰ ক\'ড (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | এবাৰৰ ক\'ড (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | এবাৰৰ ক\'ড (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA চিক্ৰেট |  |
| `tr_cannot_undo` | This cannot be undone. | এইটো ঘূৰাই আনিব নোৱাৰি। |  |
| `tr_delete_all` | Delete all permanently | সকলো চিৰদিনৰ বাবে আঁতৰাওক |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d টা এণ্ট্ৰী চিৰদিনৰ বাবে গুচি যাব। এইটো ঘূৰাই আনিব নোৱাৰি, আৰু ঘূৰাই আনিবলৈ কোনো বেকআপো নাই। |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d টা এণ্ট্ৰী চিৰদিনৰ বাবে গুচি যাব। এইটো ঘূৰাই আনিব নোৱাৰি, আৰু ঘূৰাই আনিবলৈ কোনো বেকআপো নাই। |  |
| `tr_delete_all_title` | Delete everything in the trash? | আৱৰ্জনাৰ সকলো আঁতৰাম নেকি? |  |
| `tr_delete_now` | Delete now | এতিয়াই আঁতৰাওক |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | “%1$s” চিৰদিনৰ বাবে আঁতৰাম নেকি? |  |
| `tr_empty` | Nothing deleted. | একো আঁতৰোৱা হোৱা নাই। |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | আঁতৰোৱা এণ্ট্ৰীবোৰ ইয়াত %1$d দিন থাকে। |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | ভাৰতীয় বেংক, UPI, কাৰ্ড, ডিমেট, EPF, আৰু আপুনি সঁচাকৈ ব্যৱহাৰ কৰা OTP এপবোৰৰ বাবে — ফোনতে থকা ভঁৰাল। |  |

## Priority 2 — longer prose

| key | English | Assamese | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | যিটো বস্তু আটাইতকৈ বেছি কামত আহে, তাৰ পৰাই আৰম্ভ কৰক। নোটছ এপত পৰি থকা বাৰটা পাছৱৰ্ডতকৈ সাঁচি থোৱা এটা পাছৱৰ্ড বেছি সুৰক্ষিত। |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | পাছফ্ৰেজ পাহৰিলে ভিতৰলৈ অহাৰ একমাত্ৰ বাট হৈছে ৰিকভাৰী কিট। আপোনাৰ বাবে আন এটা কোনেও বনাব নোৱাৰে। |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | সেই ফাইলত চিনি পোৱাৰ দৰে একো পোৱা নগ\'ল। Chrome, Google Password Manager, Bitwarden, LastPass আৰু KeePass-ৰ ৰপ্তানি বুজি পোৱা যায়। |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d টা বৰ্তমানৰ এণ্ট্ৰী সলনি হ\'ব — ছাইট আৰু ইউজাৰনেম মিলাই। সলনি হোৱা পাছৱৰ্ডবোৰ প্ৰতিটো এণ্ট্ৰীৰ ইতিহাসত পোৱা যাব। |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d টা বৰ্তমানৰ এণ্ট্ৰী সলনি হ\'ব — ছাইট আৰু ইউজাৰনেম মিলাই। সলনি হোৱা পাছৱৰ্ডবোৰ প্ৰতিটো এণ্ট্ৰীৰ ইতিহাসত পোৱা যাব। |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d এণ্ট্ৰী দুয়োফালে সলনি হৈছিল। দুয়োটা ৰূপ সাঁচি থোৱা হ\'ল — “(conflict copy)” বিচাৰক। |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | এই বেকআপ ফাইল খোলা পাছফ্ৰেজটো দিয়ক। সেয়া আপোনাৰ বৰ্তমানৰটোতকৈ পৃথক হ\'ব পাৰে। |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | .kosh ফাইলৰ Poly1305 প্ৰমাণীকৰণ টেগ মিলা নাই। মাজতে ৰৈ যোৱা ছিংক বা বেয়া ষ্ট\'ৰেজৰ পিছত এইটো হ\'ব পাৰে। |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh-এ ভঁৰাল ফাইলৰ কাষতে এটা চলি থকা বেকআপ ৰাখে। সেইটো আপোনাৰ ছিংক ফোল্ডাৰৰ পৰা ঘূৰাই আনক, বা আন এটা ফোনত ৰিকভাৰী কিটেৰে ভঁৰাল খোলক। |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | নপঢ়ালৈকে কাৰ্ডখন ফোনৰ পিছফালে পোনে পোনে লগাই ধৰি থাওক। ইয়াৰ পৰা কাৰ্ড নম্বৰ, ম্যাদ আৰু নাম পোৱা যায় — CVV চিপত নাথাকে, সেয়া আপুনিয়েই লিখিব লাগিব। |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | ভিতৰলৈ ঘূৰি অহাৰ সোনকালে বাট বাছনি কৰক। ভঁৰালৰ পহৰা পাছফ্ৰেজেই দিয়ে; এইটোৱে কেৱল এই ফোনতে চাবিটো খোলে। |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | এতিয়া আপোনাৰ ভঁৰালত সঁচা তথ্য আছে। ৰিকভাৰী কিট নোহোৱাকৈ পাছফ্ৰেজ পাহৰিলে কোনেও আপোনাক ঘূৰাই ভিতৰলৈ আনিব নোৱাৰে — আমিও নোৱাৰোঁ। |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh বিনামূলীয়া আৰু মুকলি ছ\'ৰ্চ, আৰু ইয়াৰ কোনো চাৰ্ভাৰ নাই। আপোনাৰ ভঁৰাল কেৱল আপুনিয়েই খুলিব পাৰে। আমিও নোৱাৰোঁ। |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | কেমেৰাৰ অনুমতি কেৱল QR ক\'ড স্কেন কৰিবলৈহে লাগে। এণ্ট্ৰী যোগ কৰোঁতে চিক্ৰেট হাতেৰেও পেষ্ট কৰিব পাৰে। |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | যিবোৰ বেংক এপে অ\'ট\'ফিল নিদিয়ে সেইবোৰৰ বাবে — বুটাম টিপি লগইনৰ তথ্য এটা এটাকৈ কপি কৰক |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | আপুনি যিদৰে ফোন খোলে, ভঁৰালো তেনেদৰে। পাছফ্ৰেজ সদায় কাম কৰি থাকিব। |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | ভঁৰালৰ স্ক্ৰীনশ্বট ক্লাউড ফট\' বেকআপলৈ যাব পাৰে। বহুত প্ৰয়োজন হ\'লেহে চলু কৰক। |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | ভঁৰাল ফাইলটো লিখিব পৰা নগ\'ল। আপুনি যদি বেকআপ আৰু ছিংক ফোল্ডাৰ ঠিক কৰি থৈছে, হয়তো Android-এ তাৰ অনুমতি ঘূৰাই লৈছে — ছেটিংছ খোলক, ফোল্ডাৰটো আকৌ বাছনি কৰক, আৰু পুনৰ চেষ্টা কৰক। |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | আপোনাৰ এনক্ৰিপ্ট কৰা .kosh ফাইলবোৰ পোনপটীয়াকৈ এই ফোল্ডাৰতে সাঁচি থোৱা হয়। বহু ডিভাইচত নিজে নিজে বেকআপ হ\'বলৈ এই ফোল্ডাৰটো Google Drive, Syncthing, Nextcloud বা SD কাৰ্ডৰ লগত ছিংক কৰক। |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | নতুন ৰিকভাৰী চাবি বনাবলৈ আপোনাৰ পাছফ্ৰেজ দিয়ক। পুৰণি চাবিটোৱে কাম কৰা বন্ধ কৰিব। |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | এই পৃষ্ঠাৰ বাকী সকলোৱে এটা লগইন দুৰ্বল কৰে। এইটোৱে গোটেই ভঁৰালটোৱেই লৈ যাব পাৰে। ছেটিংছ → নতুন ৰিকভাৰী চাবি লওক। |  |

## Priority 3 — short labels

| key | English | Assamese | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | শক্তিশালী পাছৱৰ্ড ব্যৱহাৰ কৰক |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | একাউণ্টৰ নাম (যেনে Google) |  |
| `au_active_many` | %1$d active codes | %1$d টা সক্ৰিয় ক\'ড |  |
| `au_active_one` | %1$d active code | %1$d টা সক্ৰিয় ক\'ড |  |
| `au_add_another` | Add another authenticator | আন এটা প্ৰমাণক যোগ কৰক |  |
| `au_add_secret` | Add Secret Key | চিক্ৰেট চাবি যোগ কৰক |  |
| `au_camera_needed` | Camera permission needed to scan QR code | QR ক\'ড স্কেন কৰিবলৈ কেমেৰাৰ অনুমতি লাগে |  |
| `au_copied` | Copied · clears shortly | কপি হ\'ল · অলপ পিছতে মচি যাব |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google, GitHub বা আপোনাৰ ব্ৰোকাৰৰ QR স্কেন কৰক, বা চিক্ৰেট চাবি হাতেৰে লিখক। |  |
| `au_enter_key` | Enter Key | চাবি লিখক |  |
| `au_fallback_name` | Authenticator | প্ৰমাণক |  |
| `au_flashlight` | Flashlight | টৰ্চ |  |
| `au_grant` | Grant Permission | অনুমতি দিয়ক |  |
| `au_image_failed` | Failed to process image | ছবিখন পঢ়িব পৰা নগ\'ল |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | ভুল Base32 চিক্ৰেট চাবি (কেৱল A-Z আখৰ আৰু 2-7 সংখ্যা) |  |
| `au_no_match` | No codes match | কোনো ক\'ড পোৱা নগ\'ল |  |
| `au_none_yet` | No codes yet. | এতিয়ালৈকে ক\'ড নাই। |  |
| `au_pick_image` | Pick Image | ছবি বাছনি কৰক |  |
| `au_rotating` | "Rotating " | "সলনি হৈ থকা " |  |
| `au_rotating_emph` | codes. | ক\'ড। |  |
| `au_save_key` | Save Key | চাবি সাঁচক |  |
| `au_scan_qr` | Scan a QR code | QR ক\'ড স্কেন কৰক |  |
| `au_scan_title` | Scan Authenticator QR | প্ৰমাণকৰ QR স্কেন কৰক |  |
| `au_search_hint` | Search codes, issuers… | ক\'ড বা দিওঁতা বিচাৰক… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | যেনে JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | চিক্ৰেট চাবি (Base32) |  |
| `au_tap_to_copy` | Tap to copy | কপি কৰিবলৈ টেপ কৰক |  |
| `cat_apps` | Apps &amp; Logins | এপ আৰু লগইন |  |
| `cat_banks` | Banks &amp; UPI | বেংক আৰু UPI |  |
| `cat_cards` | Cards | কাৰ্ড |  |
| `cat_govid` | Gov &amp; ID | চৰকাৰী আৰু পৰিচয় |  |
| `cat_investments` | Investments | বিনিয়োগ |  |
| `cat_utilities` | Utilities | বিল আৰু সংযোগ |  |
| `cd_mask_hidden` | hidden | লুকাই আছে |  |
| `cd_shield_high_sensitivity` | extra-protected field | অতি সংবেদনশীল তথ্য |  |
| `gl_blank` | Blank template | খালী টেমপ্লেট |  |
| `gl_cat_apps` | Apps | এপ |  |
| `gl_cat_banks` | Banks | বেংক |  |
| `gl_cat_cards` | Cards | কাৰ্ড |  |
| `gl_cat_demat` | Demat | ডিমেট |  |
| `gl_cat_govid` | Gov ID | চৰকাৰী পৰিচয় |  |
| `gl_cat_popular` | Popular | জনপ্ৰিয় |  |
| `gl_cat_shopping` | Shopping | কিনা-বেচা |  |
| `gl_cat_travel` | Travel | ভ্ৰমণ |  |
| `gl_cat_upi` | UPI | UPI |  |
| `gl_cat_utilities` | Utilities | বিল |  |
| `gl_head_emph` | storing? | সাঁচি আছোঁ? |  |
| `gl_head_lead` | "What are we " | "আমি কি " |  |
| `gl_matches` | %1$d matches | %1$d টা পোৱা গ\'ল |  |
| `gl_most_used` | Most-used first | আটাইতকৈ বেছি ব্যৱহৃত আগতে |  |
| `gl_not_found` | Can’t find a service? | সেৱাটো বিচাৰি পোৱা নাই? |  |
| `gl_search` | Search %1$d Indian services… | %1$d টা ভাৰতীয় সেৱাৰ মাজত বিচাৰক… |  |
| `gl_suggested` | Suggested for you | আপোনাৰ বাবে পৰামৰ্শ |  |
| `hm_add_first` | Add your first record | আপোনাৰ প্ৰথম এণ্ট্ৰী যোগ কৰক |  |
| `hm_all_offline` | all offline. | সকলো অফলাইন। |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d টা বস্তু, " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d টা বস্তু, " |  |
| `hm_detail_placeholder` | Pick a record to see it here | ইয়াত চাবলৈ এটা ৰেকৰ্ড বাছনি কৰক |  |
| `hm_empty_blank` | A blank vault, ready. | খালী ভঁৰাল, সাজু। |  |
| `hm_empty_head_emph` | waiting. | অপেক্ষাত আছে। |  |
| `hm_empty_head_lead` | "Your vault is " | "আপোনাৰ ভঁৰাল " |  |
| `hm_filter_all` | All | সকলো |  |
| `hm_import_backup` | Import an encrypted backup | এনক্ৰিপ্ট কৰা বেকআপ আমদানি |  |
| `hm_import_backup_note` | Open a .kosh file from this device | এই ফোনৰ পৰাই .kosh ফাইল খোলক |  |
| `hm_inst_many` | %1$d institutions | %1$d টা প্ৰতিষ্ঠান |  |
| `hm_inst_one` | %1$d institution | %1$d টা প্ৰতিষ্ঠান |  |
| `hm_kit_banner_action` | Save one now | এতিয়াই সাঁচক |  |
| `hm_kit_banner_dismiss` | Remind me later | পিছত মনত পেলাব |  |
| `hm_kit_banner_title` | No recovery kit saved | কোনো ৰিকভাৰী কিট সাঁচি থোৱা হোৱা নাই |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | খোলা আছে · এৰিলেই বন্ধ হ\'ব |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | খোলা আছে · এৰাৰ %1$d মিনিটৰ পিছত বন্ধ |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | খোলা আছে · এৰাৰ 1 মিনিটৰ পিছত বন্ধ |  |
| `hm_no_match` | Nothing matches “%1$s” | “%1$s”-ৰ বাবে একো পোৱা নগ\'ল |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | কোনো প্ৰতিষ্ঠান, UPI হেণ্ডেল, বা শেষৰ চাৰিটা সংখ্যা চেষ্টা কৰক। |  |
| `hm_pinned` | Pinned | পিন কৰা |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC, priya@upi, PAN… বিচাৰক |  |
| `hm_start_template` | Start with a template | টেমপ্লেটৰ পৰা আৰম্ভ কৰক |  |
| `ic_could_not` | Could not import | আমদানি কৰিব পৰা নগ\'ল |  |
| `ic_done` | Done | হ\'ল |  |
| `ic_import` | Import | আমদানি |  |
| `ic_imported` | Imported | আমদানি হ\'ল |  |
| `ic_importing` | Importing… | আমদানি হৈ আছে… |  |
| `ic_new_many` | %1$d new logins. | %1$d টা নতুন লগইন। |  |
| `ic_new_one` | %1$d new login. | %1$d টা নতুন লগইন। |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d যোগ হ\'ল, %2$d সলনি হ\'ল। |  |
| `ic_title` | Import from %1$s? | %1$s-ৰ পৰা আমদানি কৰিম নেকি? |  |
| `ic_too_large` | That file is too large to be a credential export. | এই ফাইলটো পাছৱৰ্ড ৰপ্তানি হ\'বলৈ বহুত ডাঙৰ। |  |
| `import_action` | Import | আমদানি |  |
| `import_locked` | Unlock your vault before importing. | আমদানি কৰাৰ আগতে আপোনাৰ ভঁৰাল খোলক। |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d যোগ হ\'ল, %2$d সলনি হ\'ল। একো মচা হোৱা নাই। |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | সেই ফাইলটো Zerokosh ভঁৰাল হিচাপে পঢ়িব পৰা নগ\'ল। |  |
| `import_nothing_new` | Everything in that backup was already here. | সেই বেকআপত যি আছিল, সেয়া আগতেই ইয়াত আছিল। |  |
| `import_passphrase_label` | Backup passphrase | বেকআপৰ পাছফ্ৰেজ |  |
| `import_title` | Import a backup | বেকআপ আমদানি কৰক |  |
| `kicker_locked` | Locked | বন্ধ আছে |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · একোৱেই এই ফোনৰ পৰা বাহিৰলৈ যোৱা নাই |  |
| `lk_touch_unlock` | Touch to unlock | খুলিবলৈ চুওক |  |
| `lk_welcome_emph` | Your vault is sealed. | আপোনাৰ ভঁৰাল বন্ধ আছে। |  |
| `lk_welcome_lead` | Welcome back. | আকৌ স্বাগতম। |  |
| `msg_auth_needed` | Confirm it\'s you to see this | চাবলৈ নিশ্চিত কৰক যে এইজন আপুনিয়েই |  |
| `msg_back` | Back | পিছলৈ |  |
| `msg_cancel` | Cancel | বাতিল |  |
| `msg_file_damaged` | File damaged — restored from backup | ফাইলটো নষ্ট হৈছিল — বেকআপৰ পৰা ঠিক কৰা হ\'ল |  |
| `msg_ok` | OK | ঠিক আছে |  |
| `msg_saved` | Saved | সাঁচি থোৱা হ\'ল |  |
| `nav_all_templates` | All templates | সকলো টেমপ্লেট |  |
| `nav_damaged_emph` | vault file | ভঁৰাল ফাইলত |  |
| `nav_damaged_kicker` | Damaged state | নষ্ট অৱস্থা |  |
| `nav_damaged_lead` | "Something in the " | "আপোনাৰ " |  |
| `nav_damaged_tail` | " is off." | " কিবা এটা গোলমাল হৈছে।" |  |
| `nav_integrity_title` | Integrity check failed | অখণ্ডতা পৰীক্ষা বিফল হ\'ল |  |
| `nav_scan` | Scan | স্কেন |  |
| `nav_tap_card` | Tap a card | কাৰ্ড টেপ কৰক |  |
| `nav_what_next` | What to do next | এতিয়া কি কৰিব |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC বন্ধ আছে। ছেটিংছত চলু কৰি আকৌ চেষ্টা কৰক। |  |
| `nfc_hold_card` | Hold your card to the phone | কাৰ্ডখন ফোনত লগাই ধৰক |  |
| `nfc_missed` | Did not catch that | ধৰিব পৰা নগ\'ল |  |
| `nfc_read_failed` | That card could not be read. Try again. | সেই কাৰ্ডখন পঢ়িব পৰা নগ\'ল। আকৌ চেষ্টা কৰক। |  |
| `nfc_reading` | Reading… | পঢ়ি আছোঁ… |  |
| `nfc_try_again` | Try again | আকৌ চেষ্টা কৰক |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · এই ফোনতে জোখা |  |
| `ob_argon_faster` | Faster unlock | সোনকালে খুলিব |  |
| `ob_argon_harder` | Harder to attack | ভাঙিবলৈ টান |  |
| `ob_argon_measuring` | Measuring this device… | এই ফোনটো জুখি আছোঁ… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id কঠোৰতা |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | অভিধানৰ এটা শব্দও নহয় |  |
| `ob_check_pass_length` | 10 characters or more | 10 বা তাতকৈ বেছি আখৰ |  |
| `ob_check_pass_reuse` | Not reused from another app | আন এপৰ পৰা পুনৰ ব্যৱহাৰ কৰা নহয় |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | জন্মদিনো নহয়, বাৰ্ষিকীও নহয় |  |
| `ob_check_pin_digits` | All six digits entered | ছয়টা সংখ্যাই ভৰোৱা হ\'ল |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | একেৰাহে সংখ্যাও নহয়, পুনৰাবৃত্তিও নহয় |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~ভাঙিবলৈ %1$d শতিকা |  |
| `ob_crack_days` | ~%1$d days to crack | ~ভাঙিবলৈ %1$d দিন |  |
| `ob_crack_forever` | longer than the sun | সূৰ্যতকৈও বেছি দিন |  |
| `ob_crack_hours` | ~hours to crack | ~ভাঙিবলৈ কেইঘণ্টামান |  |
| `ob_crack_seconds` | ~seconds to crack | ~ভাঙিবলৈ কেইছেকেণ্ডমান |  |
| `ob_crack_years` | ~%1$d years to crack | ~ভাঙিবলৈ %1$d বছৰ |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | প্ৰমাণীকৃত, প্ৰতিটো ভঁৰালৰ বাবে পৃথক নন্স |  |
| `ob_fact_encryption_title` | Encryption | এনক্ৰিপচন |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | ছেটআপৰ সময়ত আপোনাৰ ফোনত জোখা |  |
| `ob_fact_kdf_title` | Key stretching | চাবি স্ট্ৰেচিং |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | ৰিছেট লিংক নাই। ছাপ\'ৰ্টৰ পিছদুৱাৰো নাই। |  |
| `ob_fact_lost_value` | Nobody can recover it | কোনেও ঘূৰাই আনিব নোৱাৰে |  |
| `ob_fact_network_title` | Network permission | নেটৱৰ্কৰ অনুমতি |  |
| `ob_fact_network_value` | Not requested | কেতিয়াও বিচৰা হোৱা নাই |  |
| `ob_fact_quick_title` | Quick unlock | সোনকালে আনলক |  |
| `ob_fact_quick_value` | Hardware keystore | হাৰ্ডৱেৰ কীষ্ট\'ৰ |  |
| `ob_lang_continue` | Continue in %1$s | %1$s-ত আগবাঢ়ক |  |
| `ob_lang_head_emph` | language. | ভাষা বাছনি কৰক। |  |
| `ob_lang_head_lead` | "Choose your " | "আপোনাৰ " |  |
| `ob_lang_search` | Search %1$d languages | %1$d ভাষাৰ মাজত বিচাৰক |  |
| `ob_quick_continue_pass` | Continue with passphrase | পাছফ্ৰেজেৰে আগবাঢ়ক |  |
| `ob_quick_enable` | Enable quick unlock | সোনকালে আনলক চলু কৰক |  |
| `ob_quick_fingerprint` | Fingerprint | আঙুলিৰ ছাপ |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | সোনকালে, হাৰ্ডৱেৰেৰে সুৰক্ষিত আনলক। |  |
| `ob_quick_head_emph` | Without the cloud. | ক্লাউড অবিহনে। |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "এটা চুৱাতে খুলিব। " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox। কোনো বায়\'মেট্ৰিক তথ্য কেতিয়াও Zerokosh-লৈ নাহে। |  |
| `ob_quick_hw_title` | Hardware-backed | হাৰ্ডৱেৰেৰে সুৰক্ষিত |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | এই ফোনত হাৰ্ডৱেৰ চেন্সৰ নাই। |  |
| `ob_quick_opening` | Opening your vault… | আপোনাৰ ভঁৰাল খুলি আছে… |  |
| `ob_quick_pass_only` | Passphrase only | কেৱল পাছফ্ৰেজ |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | প্ৰতিবাৰ লিখক। আটাইতকৈ সুৰক্ষিত। |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | সোনকালে আনলক ঠিক নহ\'ল। আকৌ চেষ্টা কৰক, বা পাছফ্ৰেজেৰেই আগবাঢ়ক। |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | এতিয়া নালাগে — মই পাছফ্ৰেজ লিখিম |  |
| `ob_quick_touch_title` | Touch the sensor | চেন্সৰত চুওক |  |
| `ob_recommended` | Recommended | চুপাৰিছ |  |
| `ob_reveal_hide` | Hide | লুকুৱাওক |  |
| `ob_reveal_show` | Show | দেখুৱাওক |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | মেৰিওৱা চাবি হাৰ্ডৱেৰ কীষ্ট\'ৰত থাকিব। বায়\'মেট্ৰিক পিছৰ পদক্ষেপত। |  |
| `ob_seal_title` | Seal to this device | এই ফোনতে বান্ধি থওক |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | এই ফোনত হাৰ্ডৱেৰ বায়\'মেট্ৰিক নাই। |  |
| `ob_soon` | SOON | সোনকালে |  |
| `ob_step_label` | Step %1$d of 6 | পদক্ষেপ %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | এনে কিবা বাছনি কৰক যিটো কেৱল আপুনিয়েই কয় |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | ভাল · %1$d বিট এণ্ট্ৰপি |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | বহুত চুটি · 10 আখৰ লাগে |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | শক্তিশালী · %1$d বিট এণ্ট্ৰপি |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | দুৰ্বল · %1$d বিট এণ্ট্ৰপি |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | ছয়টা সংখ্যা যিটো আপোনাৰ জীৱন চাই কোনেও অনুমান কৰিব নোৱাৰে |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | মোটামুটি · %1$d বিট — পিন ইয়াতকৈ শক্তিশালী নহয় |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | বহুত চুটি · 6 সংখ্যা লাগে |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | দুৰ্বল · এই পিনবোৰেই আটাইতকৈ আগতে চেষ্টা কৰা হয় |  |
| `ob_try_label` | TRY | চেষ্টা কৰক |  |
| `qa_aadhaar` | Aadhaar | আধাৰ |  |
| `qa_bank_account` | Bank account | বেংক একাউণ্ট |  |
| `qa_pan` | PAN | PAN |  |
| `qa_upi_id` | UPI ID | UPI ID |  |
| `rd_copied` | Copied | কপি হ\'ল |  |
| `rd_forget` | Forget | পাহৰি যাওক |  |
| `rd_forget_these` | Forget these | এইবোৰ পাহৰি যাওক |  |
| `rd_forget_title` | Forget previous passwords? | পুৰণি পাছৱৰ্ডবোৰ পাহৰি যাম নেকি? |  |
| `rd_history_hide` | Hide | লুকুৱাওক |  |
| `rd_history_show` | Show %1$d | %1$d দেখুৱাওক |  |
| `rd_hold_to_reveal` | Hold to reveal | চাবলৈ টিপি ধৰি থাওক |  |
| `rd_last_edit` | last edit %1$s | শেষবাৰ %1$s সলনি কৰা হৈছিল |  |
| `rd_release_to_hide` | Release to hide | লুকুৱাবলৈ এৰি দিয়ক |  |
| `re_add_field` | + Add another field | + আন এটা ঘৰ যোগ কৰক |  |
| `re_add_field_title` | Add a field | ঘৰ যোগ কৰক |  |
| `re_field_name` | Field name | ঘৰৰ নাম |  |
| `re_pick_date` | Pick a date | তাৰিখ বাছনি কৰক |  |
| `re_remove` | Remove | আঁতৰাওক |  |
| `re_tap_card` | Read the card by tapping it | পঢ়িবলৈ কাৰ্ড টেপ কৰক |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | গোপন বুলি গণ্য কৰক (লুকাই থাকিব, চাবলৈ টিপি ধৰক) |  |
| `re_using_template` | using the %1$s template | %1$s টেমপ্লেট ব্যৱহাৰ কৰি |  |
| `rem_kit_title` | No recovery kit saved | কোনো ৰিকভাৰী কিট সাঁচি থোৱা হোৱা নাই |  |
| `scr_about_license` | License: GPL-3.0 — free forever | অনুজ্ঞাপত্ৰ: GPL-3.0 — সদায় বিনামূলীয়া |  |
| `scr_about_source` | Source code | ছ\'ৰ্চ ক\'ড |  |
| `scr_about_version` | Version %1$s | সংস্কৰণ %1$s |  |
| `scr_auth_add_qr` | Add via QR | QR-ৰে যোগ কৰক |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | আপোনাৰ এপ আৰু ব্ৰোকাৰৰ ক\'ড ইয়াত দেখা যাব |  |
| `scr_auth_scan_title` | Point the camera at the QR code | কেমেৰা QR ক\'ডৰ ওপৰত ধৰক |  |
| `scr_detail_copied` | Copied · clears in 30s | কপি হ\'ল · 30 চেকেণ্ডত মচি যাব |  |
| `scr_detail_copy` | Copy | কপি কৰক |  |
| `scr_detail_edit` | Edit | সলনি কৰক |  |
| `scr_detail_favorite` | Favourite | পছন্দৰ |  |
| `scr_detail_hidden` | Hidden | লুকাই আছে |  |
| `scr_detail_hide` | Hide | লুকুৱাওক |  |
| `scr_detail_history_empty` | Nothing replaced yet. | এতিয়ালৈকে একো সলনি হোৱা নাই। |  |
| `scr_detail_history_title` | Previous passwords | পুৰণি পাছৱৰ্ড |  |
| `scr_detail_reveal` | Show | দেখুৱাওক |  |
| `scr_detail_shown` | Shown | দেখা গৈছে |  |
| `scr_edit_cancel` | Cancel | বাতিল |  |
| `scr_edit_generate` | Generate | বনাওক |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | বেংক / কোম্পানী (একেলগে ৰাখিবলৈ) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | এইটো ঠিক যেন নালাগে — এবাৰ চাই লওক |  |
| `scr_edit_link_none` | None | একো নাই |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | এই কাৰ্ড নম্বৰ সাধাৰণ পৰীক্ষাত উত্তীৰ্ণ নহয় — ঠিক হ\'লে সাঁচি থওক |  |
| `scr_edit_month` | Month | মাহ |  |
| `scr_edit_picker_other` | Other… | আন… |  |
| `scr_edit_picker_other_hint` | Type your own | নিজৰটো লিখক |  |
| `scr_edit_required_title` | Give it a name first | প্ৰথমে ইয়াক এটা নাম দিয়ক |  |
| `scr_edit_save` | Save | সাঁচক |  |
| `scr_edit_title_hint` | Title | নাম |  |
| `scr_edit_title_new` | New | নতুন |  |
| `scr_edit_year` | Year | বছৰ |  |
| `scr_gallery_quick_add` | Quick add | সোনকালে যোগ কৰক |  |
| `scr_gallery_title` | What do you want to save? | আমি কি সাঁচি আছোঁ? |  |
| `scr_home_add` | Add | যোগ কৰক |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | আপোনাৰ বেংক একাউণ্ট এনেকৈ দেখা যাব |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | আপোনাৰ কাৰ্ড, UPI, এপ লগইন — সকলো ইয়াতেই |  |
| `scr_home_group_other` | Other | আন |  |
| `scr_home_no_results` | Nothing matches your search | আপোনাৰ সন্ধানত একো পোৱা নগ\'ল |  |
| `scr_home_search_hint` | Search your vault | আপোনাৰ ভঁৰালত বিচাৰক |  |
| `scr_home_tab_authenticator` | Authenticator | ক\'ড |  |
| `scr_home_tab_home` | Home | ঘৰ |  |
| `scr_home_tab_settings` | Settings | ছেটিংছ |  |
| `scr_home_title` | Home | ঘৰ |  |
| `scr_language_continue` | Continue | আগবাঢ়ক |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | আপোনাৰ ভাষা বাছনি কৰক |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | কপি কৰিবলৈ বুটাম টিপক · 30 চেকেণ্ডত মচি যাব |  |
| `scr_login_helper_channel` | Login helper | লগইন সহায়ক |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s-ত লগইন কৰি আছে |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | এতিয়াই বেকআপ ফোল্ডাৰ ঠিক কৰি লওক |  |
| `scr_quickunlock_enable` | Turn on | চলু কৰক |  |
| `scr_quickunlock_skip` | Not now | এতিয়া নালাগে |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | আঙুলি বা মুখেৰে খোলক |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s-ৰ তাৰিখ ওচৰ চাপিছে · Zerokosh খোলক |  |
| `scr_reminder_channel` | Renewal reminders | নবীকৰণৰ মনোযোগ |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh-এ মনত পেলাই দিছে |  |
| `scr_settings_about` | About | বিষয়ে |  |
| `scr_settings_allow_screenshots` | Allow screenshots | স্ক্ৰীনশ্বট ল\'বলৈ দিয়ক |  |
| `scr_settings_autofill` | Autofill service | অ\'ট\'ফিল সেৱা |  |
| `scr_settings_autofill_off` | Not set up | ঠিক কৰা হোৱা নাই |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | উপলব্ধ নহয় |  |
| `scr_settings_autolock` | Lock when I leave the app | এপ এৰিলেই বন্ধ কৰক |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 মিনিটৰ পিছত |  |
| `scr_settings_autolock_immediately` | Immediately | লগে লগে |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d মিনিটৰ পিছত |  |
| `scr_settings_change_passphrase` | Change passphrase | পাছফ্ৰেজ সলনি কৰক |  |
| `scr_settings_current_passphrase` | Current passphrase | বৰ্তমানৰ পাছফ্ৰেজ |  |
| `scr_settings_export` | Export | ৰপ্তানি কৰক |  |
| `scr_settings_import` | Import passwords | পাছৱৰ্ড আমদানি কৰক |  |
| `scr_settings_language` | Language | ভাষা |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | নতুন পাছফ্ৰেজ (কমেও 10 আখৰ) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | নতুন ৰিকভাৰী চাবি লওক |  |
| `scr_settings_passphrase_changed` | Passphrase changed | পাছফ্ৰেজ সলনি হ\'ল |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | আঙুলি / মুখ আনলক |  |
| `scr_settings_security_info` | How your data is protected | আপোনাৰ তথ্য কেনেকৈ সুৰক্ষিত |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | বেকআপ আৰু ছিংক ফোল্ডাৰ |  |
| `scr_settings_sync_not_set` | Not backed up | বেকআপ নাই |  |
| `scr_settings_title` | Settings | ছেটিংছ |  |
| `se_title` | Not saved | সাঁচি থোৱা নহ\'ল |  |
| `st_active_folder` | Active Folder | সক্ৰিয় ফোল্ডাৰ |  |
| `st_active_value` | Active · %1$s | সক্ৰিয় · %1$s |  |
| `st_backing_up` | Backing up vault… | ভঁৰালৰ বেকআপ লৈ আছোঁ… |  |
| `st_backup_now` | Backup Now | এতিয়াই বেকআপ লওক |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | বেকআপ আৰু ছিংক ফোল্ডাৰ |  |
| `st_change_folder` | Change Folder | ফোল্ডাৰ সলনি কৰক |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | নতুন পাছফ্ৰেজ আকৌ লিখক |  |
| `st_connected_folder` | Connected folder: %1$s | সংযোগ কৰা ফোল্ডাৰ: %1$s |  |
| `st_disconnect` | Disconnect | আঁতৰাওক |  |
| `st_done` | Done | হ\'ল |  |
| `st_export_kosh` | Export encrypted .kosh | এনক্ৰিপ্ট কৰা .kosh ৰপ্তানি |  |
| `st_folder_fallback` | Folder | ফোল্ডাৰ |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | পাছফ্ৰেজ পাহৰিলে নেকি? |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | আপোনাৰ আঙুলিৰ ছাপেৰে নতুন এটা দিয়ক |  |
| `st_generate` | Generate | বনাওক |  |
| `st_group_about` | About | বিষয়ে |  |
| `st_group_appearance` | Appearance | ৰূপ |  |
| `st_group_security` | Security | সুৰক্ষা |  |
| `st_group_sync` | Sync | ছিংক |  |
| `st_import_kosh` | Import a .kosh backup | .kosh বেকআপ আমদানি |  |
| `st_import_other` | Import from another password manager | আন পাছৱৰ্ড মেনেজাৰৰ পৰা আমদানি |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | অনুজ্ঞাপত্ৰ |  |
| `st_logos_by` | Logos provided by | লগ\' দিছে |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | ইয়াক অফলাইনত ৰাখক। পুৰণি ৰিকভাৰী চাবি এতিয়া অচল। |  |
| `st_new_recovery_result` | Your new Recovery Key: | আপোনাৰ নতুন ৰিকভাৰী চাবি: |  |
| `st_subtitle` | Your rules. | আপোনাৰ নিয়ম। |  |
| `st_theme` | Theme | থীম |  |
| `st_theme_dark` | Dark | গাঢ় |  |
| `st_theme_light` | Light | পোহৰ |  |
| `st_theme_system` | System | চিষ্টেম |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | ভঁৰাল %1$s-ত সাঁচি থোৱা হ\'ল! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | বেকআপ হোৱা নাই — ফোল্ডাৰৰ অনুমতি চাওক |  |
| `st_toast_disconnected` | Backup folder disconnected | বেকআপ ফোল্ডাৰ আঁতৰোৱা হ\'ল |  |
| `st_toast_export_failed` | Export failed | ৰপ্তানি হোৱা নাই |  |
| `st_toast_exported` | Encrypted vault exported | এনক্ৰিপ্ট কৰা ভঁৰাল ৰপ্তানি হ\'ল |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | বেকআপ ফোল্ডাৰ সংযোগ হ\'ল, আৰু ভঁৰাল %1$s-ত সাঁচি থোৱা হ\'ল! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | বেকআপ ফোল্ডাৰ সংযোগ হ\'ল: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | ফোল্ডাৰ সংযোগ কৰিব পৰা নগ\'ল: %1$s |  |
| `st_vault_review` | Vault review | ভঁৰালৰ পৰীক্ষা |  |
| `st_vault_review_detail` | Reused, weak, expiring | পুনৰ ব্যৱহৃত, দুৰ্বল, ম্যাদ শেষ হোৱা |  |
| `tab_codes` | Codes | ক\'ড |  |
| `tab_settings` | Settings | ছেটিংছ |  |
| `tab_templates` | Templates | টেমপ্লেট |  |
| `tab_vault` | Vault | ভঁৰাল |  |
| `time_days` | %1$dd ago | %1$d দিন আগতে |  |
| `time_hours` | %1$dh ago | %1$d ঘণ্টা আগতে |  |
| `time_just_now` | just now | এইমাত্ৰ |  |
| `time_minutes` | %1$dm ago | %1$d মিনিট আগতে |  |
| `time_months` | %1$dmo ago | %1$d মাহ আগতে |  |
| `time_years` | %1$dy ago | %1$d বছৰ আগতে |  |
| `tpl_aadhaar_card` | Aadhaar Card | আধাৰ |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | আধাৰ নম্বৰ |  |
| `tpl_aadhaar_card_address` | Address | আধাৰত থকা ঠিকনা |  |
| `tpl_aadhaar_card_dob` | Dob | জন্ম তাৰিখ |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | স্কেন কৰা কপি |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | সংযুক্ত ম\'বাইল |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar পাছক\'ড |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | আধাৰত থকা নাম |  |
| `tpl_aadhaar_card_notes` | Notes | টোকা |  |
| `tpl_app_profile` | App Profile | এপ প্ৰ\'ফাইল |  |
| `tpl_app_profile_app_name` | App name | এপৰ নাম |  |
| `tpl_app_profile_gift_cards` | Gift cards | গিফ্ট কাৰ্ড |  |
| `tpl_app_profile_membership` | Membership | সদস্যপদ |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | সদস্যপদ নবীকৰণ |  |
| `tpl_app_profile_notes` | Notes | টোকা |  |
| `tpl_app_profile_password_if_any` | Password (if any) | পাছৱৰ্ড (যদি আছে) |  |
| `tpl_app_profile_registered_email` | Registered email | পঞ্জীভুক্ত ইমেইল |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | পঞ্জীভুক্ত ম\'বাইল |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | ৱালেট পিন |  |
| `tpl_bank_account` | Bank Account | বেংক একাউণ্ট |  |
| `tpl_bank_account_account_number` | Account number | একাউণ্ট নম্বৰ |  |
| `tpl_bank_account_account_type` | Account type | একাউণ্টৰ ধৰণ |  |
| `tpl_bank_account_bank_name` | Bank name | বেংকৰ নাম |  |
| `tpl_bank_account_branch` | Branch | শাখা |  |
| `tpl_bank_account_customer_id` | Customer id | গ্ৰাহক ID |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC ক\'ড |  |
| `tpl_bank_account_login_password` | Login password | লগইন পাছৱৰ্ড |  |
| `tpl_bank_account_micr` | MICR code | MICR ক\'ড |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | নেট-বেংকিং ইউজাৰ ID |  |
| `tpl_bank_account_nominee` | Nominee | নমিনী |  |
| `tpl_bank_account_notes` | Notes | টোকা |  |
| `tpl_bank_account_profile_password` | Profile password | প্ৰ\'ফাইল পাছৱৰ্ড |  |
| `tpl_bank_account_registered_email` | Registered email | পঞ্জীভুক্ত ইমেইল |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | পঞ্জীভুক্ত ম\'বাইল |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | লেনদেনৰ পাছৱৰ্ড |  |
| `tpl_card` | Card | কাৰ্ড |  |
| `tpl_card_atm_pin` | ATM PIN | ATM পিন |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | বিলিং চক্ৰৰ দিন |  |
| `tpl_card_card_network` | Card network | নেটৱৰ্ক |  |
| `tpl_card_card_number` | Card number | কাৰ্ড নম্বৰ |  |
| `tpl_card_card_portal_login` | Card portal login | কাৰ্ড প\'ৰ্টেল লগইন |  |
| `tpl_card_card_portal_password` | Card portal password | কাৰ্ড প\'ৰ্টেল পাছৱৰ্ড |  |
| `tpl_card_card_type` | Card type | কাৰ্ডৰ ধৰণ |  |
| `tpl_card_card_variant` | Card variant | কাৰ্ড ভেৰিয়েণ্ট |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | ম্যাদ |  |
| `tpl_card_linked_account` | Linked account | সংযুক্ত একাউণ্ট |  |
| `tpl_card_name_on_card` | Name on card | কাৰ্ডত থকা নাম |  |
| `tpl_card_notes` | Notes | টোকা |  |
| `tpl_demat` | Demat | ডিমেট |  |
| `tpl_demat_api_key` | API key | API চাবি |  |
| `tpl_demat_api_secret` | API secret | API চিক্ৰেট |  |
| `tpl_demat_bo_id` | BO ID | BO ID |  |
| `tpl_demat_broker` | Broker | ব্ৰোকাৰ |  |
| `tpl_demat_cdsl_tpin` | CDSL TPIN | CDSL TPIN |  |
| `tpl_demat_client_id` | Client id | ক্লায়েণ্ট ID |  |
| `tpl_demat_depository` | Depository | ডিপজিটৰী |  |
| `tpl_demat_dp_id` | DP ID | DP ID |  |
| `tpl_demat_login_password` | Login password | লগইন পাছৱৰ্ড |  |
| `tpl_demat_mf_folios` | Mutual fund folios | মিউচুৱেল ফাণ্ড ফলিঅ\' |  |
| `tpl_demat_mpin` | MPIN | MPIN |  |
| `tpl_demat_nominee` | Nominee | নমিনী |  |
| `tpl_demat_notes` | Notes | টোকা |  |
| `tpl_digilocker` | Digilocker | DigiLocker |  |
| `tpl_digilocker_login_username` | Login username | ইউজাৰনেম |  |
| `tpl_digilocker_notes` | Notes | টোকা |  |
| `tpl_digilocker_portal_password` | Portal password | পাছৱৰ্ড |  |
| `tpl_digilocker_security_pin` | Security pin | সুৰক্ষা পিন |  |
| `tpl_driving_license` | Driving License | ড্ৰাইভিং লাইচেন্স |  |
| `tpl_driving_license_dl_number` | Dl number | লাইচেন্স নম্বৰ |  |
| `tpl_driving_license_dob` | Dob | জন্ম তাৰিখ |  |
| `tpl_driving_license_expiry_date` | Expiry date | এই তাৰিখলৈকে বৈধ |  |
| `tpl_driving_license_file_copy` | Scanned copy | স্কেন কৰা কপি |  |
| `tpl_driving_license_issue_date` | Issue date | প্ৰদানৰ তাৰিখ |  |
| `tpl_driving_license_name_on_dl` | Name on dl | লাইচেন্সত থকা নাম |  |
| `tpl_driving_license_notes` | Notes | টোকা |  |
| `tpl_driving_license_rto_location` | Rto location | RTO |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | গাড়ীৰ শ্ৰেণী |  |
| `tpl_epf_pension` | Epf Pension | EPF / পেঞ্চন |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | সংযুক্ত ম\'বাইল |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | EPF-ত থকা নাম |  |
| `tpl_epf_pension_nominee` | Nominee | নমিনী |  |
| `tpl_epf_pension_notes` | Notes | টোকা |  |
| `tpl_epf_pension_password` | Password | পাছৱৰ্ড |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | PF সদস্য ID |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO পাছৱৰ্ড |  |
| `tpl_epf_pension_scheme` | Scheme | আঁচনি |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | চৰকাৰী পৰিচয়পত্ৰ |  |
| `tpl_gov_id_expiry` | Expiry | ম্যাদ |  |
| `tpl_gov_id_file_copy` | Scanned copy | স্কেন কৰা কপি |  |
| `tpl_gov_id_id_kind` | ID type | পৰিচয়পত্ৰৰ ধৰণ |  |
| `tpl_gov_id_id_number` | ID number | পৰিচয় নম্বৰ |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | পত্ৰ অনুসৰি নাম |  |
| `tpl_gov_id_notes` | Notes | টোকা |  |
| `tpl_gov_id_portal_login` | Portal login | প\'ৰ্টেল লগইন |  |
| `tpl_gov_id_portal_password` | Portal password | প\'ৰ্টেল পাছৱৰ্ড |  |
| `tpl_insurance` | Insurance | বীমা |  |
| `tpl_insurance_agent_contact` | Agent contact | এজেণ্টৰ যোগাযোগ |  |
| `tpl_insurance_commencement_date` | Commencement date | আৰম্ভণিৰ তাৰিখ |  |
| `tpl_insurance_insurer` | Insurer | বীমা কোম্পানী |  |
| `tpl_insurance_maturity_date` | Maturity date | পূৰ্ণ হোৱাৰ তাৰিখ |  |
| `tpl_insurance_nominee` | Nominee | নমিনী |  |
| `tpl_insurance_notes` | Notes | টোকা |  |
| `tpl_insurance_policy_number` | Policy number | পলিচী নম্বৰ |  |
| `tpl_insurance_policy_term` | Policy term | পলিচীৰ ম্যাদ |  |
| `tpl_insurance_policy_type` | Policy type | পলিচীৰ ধৰণ |  |
| `tpl_insurance_portal_login` | Portal login | প\'ৰ্টেল লগইন |  |
| `tpl_insurance_portal_password` | Portal password | প\'ৰ্টেল পাছৱৰ্ড |  |
| `tpl_insurance_premium_amount` | Premium amount | প্ৰিমিয়ামৰ ধন |  |
| `tpl_insurance_premium_due_date` | Premium due date | প্ৰিমিয়ামৰ তাৰিখ |  |
| `tpl_insurance_premium_mode` | Premium mode | প্ৰিমিয়াম কেনেকৈ দিয়ে |  |
| `tpl_insurance_sum_assured` | Sum assured | বীমাৰ ধন |  |
| `tpl_login` | Login | লগইন |  |
| `tpl_login_notes` | Notes | টোকা |  |
| `tpl_login_password` | Password | পাছৱৰ্ড |  |
| `tpl_login_recovery_codes` | Recovery codes | ৰিকভাৰী ক\'ড |  |
| `tpl_login_username` | Username | ইউজাৰনেম |  |
| `tpl_login_website` | Website | ৱেবছাইট |  |
| `tpl_pan_card` | Pan Card | PAN কাৰ্ড |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | আধাৰৰ লগত সংযুক্ত |  |
| `tpl_pan_card_dob` | Dob | জন্ম তাৰিখ |  |
| `tpl_pan_card_e_filing_password` | E filing password | ই-ফাইলিং পাছৱৰ্ড |  |
| `tpl_pan_card_fathers_name` | Fathers name | দেউতাকৰ নাম |  |
| `tpl_pan_card_file_copy` | Scanned copy | স্কেন কৰা কপি |  |
| `tpl_pan_card_name_on_pan` | Name on pan | PAN-ত থকা নাম |  |
| `tpl_pan_card_notes` | Notes | টোকা |  |
| `tpl_pan_card_pan_number` | Pan number | PAN |  |
| `tpl_passkey` | Passkey | পাছকী |  |
| `tpl_passkey_credential_id` | Credential ID | ক্ৰেডেনচিয়েল ID |  |
| `tpl_passkey_notes` | Notes | টোকা |  |
| `tpl_passkey_private_key` | Private key | ব্যক্তিগত কী |  |
| `tpl_passkey_sign_count` | Sign count | ছাইন কাউণ্ট |  |
| `tpl_passkey_user_handle` | User handle | ইউজাৰ হেণ্ডেল |  |
| `tpl_passkey_username` | Username | ইউজাৰনেম |  |
| `tpl_passkey_website` | Website | ৱেবছাইট |  |
| `tpl_passport` | Passport | পাছপ\'ৰ্ট |  |
| `tpl_passport_dob` | Dob | জন্ম তাৰিখ |  |
| `tpl_passport_expiry_date` | Expiry date | ম্যাদ শেষ হোৱাৰ তাৰিখ |  |
| `tpl_passport_file_copy` | Scanned copy | স্কেন কৰা কপি |  |
| `tpl_passport_given_names` | Given names | দিয়া নাম |  |
| `tpl_passport_issue_date` | Issue date | প্ৰদানৰ তাৰিখ |  |
| `tpl_passport_notes` | Notes | টোকা |  |
| `tpl_passport_passport_number` | Passport number | পাছপ\'ৰ্ট নম্বৰ |  |
| `tpl_passport_place_of_issue` | Place of issue | প্ৰদানৰ ঠাই |  |
| `tpl_passport_portal_login` | Portal login | Passport Seva লগইন |  |
| `tpl_passport_portal_password` | Portal password | Passport Seva পাছৱৰ্ড |  |
| `tpl_passport_surname` | Surname | উপাধি |  |
| `tpl_secure_note` | Secure Note | সুৰক্ষিত টোকা |  |
| `tpl_secure_note_attachment` | Attachment | সংলগ্ন |  |
| `tpl_secure_note_body` | Note | টোকা |  |
| `tpl_shopping` | Shopping | কিনা-বেচাৰ একাউণ্ট |  |
| `tpl_shopping_gift_card_code` | Gift card code | গিফ্ট কাৰ্ড ক\'ড |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | গিফ্ট কাৰ্ড পিন |  |
| `tpl_shopping_membership_id` | Membership id | সদস্যপদ ID |  |
| `tpl_shopping_notes` | Notes | টোকা |  |
| `tpl_shopping_password` | Password | পাছৱৰ্ড |  |
| `tpl_shopping_registered_email` | Registered email | পঞ্জীভুক্ত ইমেইল |  |
| `tpl_shopping_registered_mobile` | Registered mobile | পঞ্জীভুক্ত ম\'বাইল |  |
| `tpl_shopping_wallet_pin` | Wallet pin | ৱালেট পিন |  |
| `tpl_telecom` | Telecom | ম\'বাইল আৰু ইণ্টাৰনেট |  |
| `tpl_telecom_account_number` | Account number | একাউণ্ট নম্বৰ |  |
| `tpl_telecom_circle` | Circle | চাৰ্কল |  |
| `tpl_telecom_mobile_number` | Mobile number | ম\'বাইল নম্বৰ |  |
| `tpl_telecom_notes` | Notes | টোকা |  |
| `tpl_telecom_operator` | Operator | কোম্পানী |  |
| `tpl_telecom_plan_type` | Plan type | প্লেনৰ ধৰণ |  |
| `tpl_telecom_portal_password` | Portal password | প\'ৰ্টেল পাছৱৰ্ড |  |
| `tpl_telecom_puk` | PUK code | PUK ক\'ড |  |
| `tpl_telecom_renewal_date` | Renewal date | ৰিচাৰ্জৰ তাৰিখ |  |
| `tpl_telecom_sim_number` | Sim number | ছিম নম্বৰ (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | ছিম পিন |  |
| `tpl_transit` | Transit | ভ্ৰমণ পাছ |  |
| `tpl_transit_login_password` | Login password | লগইন পাছৱৰ্ড |  |
| `tpl_transit_notes` | Notes | টোকা |  |
| `tpl_transit_operator_name` | Operator name | কোম্পানী |  |
| `tpl_transit_registered_email` | Registered email | পঞ্জীভুক্ত ইমেইল |  |
| `tpl_transit_registered_mobile` | Registered mobile | পঞ্জীভুক্ত ম\'বাইল |  |
| `tpl_transit_smart_card_number` | Smart card number | স্মাৰ্ট কাৰ্ড নম্বৰ |  |
| `tpl_transit_wallet_pin` | Wallet pin | ৱালেট পিন |  |
| `tpl_travel_booking` | Travel Booking | ভ্ৰমণৰ বুকিং |  |
| `tpl_travel_booking_account_username` | Account username | ইউজাৰনেম |  |
| `tpl_travel_booking_login_password` | Login password | লগইন পাছৱৰ্ড |  |
| `tpl_travel_booking_notes` | Notes | টোকা |  |
| `tpl_travel_booking_provider` | Provider | কোম্পানী |  |
| `tpl_travel_booking_registered_email` | Registered email | পঞ্জীভুক্ত ইমেইল |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | পঞ্জীভুক্ত ম\'বাইল |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | ৱালেট পিন |  |
| `tpl_upi` | UPI | UPI |  |
| `tpl_upi_app_name` | App name | UPI এপ |  |
| `tpl_upi_apps_used` | Apps used | কোনবোৰ এপত সক্ৰিয় |  |
| `tpl_upi_linked_account` | Linked account | সংযুক্ত একাউণ্ট |  |
| `tpl_upi_notes` | Notes | টোকা |  |
| `tpl_upi_upi_id` | UPI ID | UPI ID |  |
| `tpl_upi_upi_pin` | UPI PIN | UPI পিন |  |
| `tpl_utility` | Utility | বিল আৰু সংযোগ |  |
| `tpl_utility_account_holder` | Account holder | একাউণ্টৰ গৰাকী |  |
| `tpl_utility_consumer_number` | Consumer number | গ্ৰাহক নম্বৰ |  |
| `tpl_utility_due_day` | Bill due day | বিল দিয়াৰ দিন |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag ID |  |
| `tpl_utility_notes` | Notes | টোকা |  |
| `tpl_utility_portal_login` | Portal login | প\'ৰ্টেল লগইন |  |
| `tpl_utility_portal_password` | Portal password | প\'ৰ্টেল পাছৱৰ্ড |  |
| `tpl_utility_provider` | Provider | সেৱা দিয়া কোম্পানী |  |
| `tpl_utility_utility_kind` | Utility kind | কিহৰ বিল |  |
| `tpl_utility_vehicle_number` | Vehicle number | গাড়ীৰ নম্বৰ |  |
| `tpl_utility_wifi_password` | Wi-Fi password | Wi-Fi পাছৱৰ্ড |  |
| `tpl_voter_id` | Voter Id | ভোটাৰ পৰিচয়পত্ৰ |  |
| `tpl_voter_id_constituency` | Constituency | সমষ্টি |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC নম্বৰ |  |
| `tpl_voter_id_file_copy` | Scanned copy | স্কেন কৰা কপি |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | ভোটাৰ কাৰ্ডত থকা নাম |  |
| `tpl_voter_id_notes` | Notes | টোকা |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP পাছৱৰ্ড |  |
| `tr_days_many` | %1$d days left | %1$d দিন বাকী |  |
| `tr_days_one` | %1$d day left | %1$d দিন বাকী |  |
| `tr_gone_today` | gone today | আজি গুচি যাব |  |
| `tr_restore` | Restore | ঘূৰাই আনক |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | তাৰ পিছত সেইবোৰ চিৰদিনৰ বাবে গুচি যায় — আন ক\'তো কপি নাই। |  |
| `ui_hide_passphrase` | Hide passphrase | পাছফ্ৰেজ লুকুৱাওক |  |
| `ui_label_count` | %1$s, %2$s | %1$s, %2$s |  |
| `ui_show_passphrase` | Show passphrase | পাছফ্ৰেজ দেখুৱাওক |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | এই ফোনতে %1$d টা এণ্ট্ৰীৰ লগত মিলোৱা হ\'ল। একো ক\'তো পঠোৱা হোৱা নাই। |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | এই ফোনতে %1$d টা এণ্ট্ৰীৰ লগত মিলোৱা হ\'ল। একো ক\'তো পঠোৱা হোৱা নাই। |  |
| `vh_count_many` | %1$d things worth a look. | %1$d টা কথালৈ মনোযোগ দিয়া উচিত। |  |
| `vh_count_one` | %1$d thing worth a look. | %1$d টা কথালৈ মনোযোগ দিয়া উচিত। |  |
| `vh_empty` | No reused, weak or expiring credentials. | পুনৰ ব্যৱহৃত, দুৰ্বল বা ম্যাদ শেষ হোৱা একো নাই। |  |
| `vh_kind_common` | Commonly guessed | সহজে অনুমান কৰিব পৰা |  |
| `vh_kind_expiring` | Expiring | ম্যাদ শেষ হৈ আহিছে |  |
| `vh_kind_reused` | Reused password | পুনৰ ব্যৱহৃত পাছৱৰ্ড |  |
| `vh_kind_weak` | Weak | দুৰ্বল |  |
| `vh_no_kit_title` | No recovery kit saved | কোনো ৰিকভাৰী কিট সাঁচি থোৱা হোৱা নাই |  |
| `vh_nothing` | Nothing to fix. | ঠিক কৰিবলগীয়া একো নাই। |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | অফলাইন |  |
| `wl_chip_open` | Open source | মুকলি ছ\'ৰ্চ |  |
| `wl_create` | Create a new vault | নতুন ভঁৰাল বনাওক |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | ইমেইল নাই · একাউণ্ট নাই · একোৱেই এই ফোনৰ পৰা বাহিৰলৈ নাযায় |  |
| `wl_head_1` | Your keys. | আপোনাৰ চাবি। |  |
| `wl_head_2` | Your device. | আপোনাৰ ফোন। |  |
| `wl_head_3` | No server. | চাৰ্ভাৰ নাই। |  |
| `wl_restore` | Restore from Recovery Kit | ৰিকভাৰী কিটৰ পৰা ঘূৰাই আনক |  |
| `wl_sr_headline` | Your keys. Your device. No server. | আপোনাৰ চাবি। আপোনাৰ ফোন। চাৰ্ভাৰ নাই। |  |
