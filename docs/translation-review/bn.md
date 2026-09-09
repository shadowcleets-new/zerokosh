# Bengali (`bn`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-bn/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Bengali | ok? |
|---|---|---|---|
| `au_close` | Close | বন্ধ করুন |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | বেছে নেওয়া ছবিতে সঠিক TOTP QR কোড মেলেনি |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | ফাঁকা থেকে শুরু করে নিজের ফিল্ডের নাম নিজেই দিন — টেমপ্লেট কেবল লেবেল ভরে, তথ্য কখনও নয়। |  |
| `hm_close_search` | Close search | খোঁজা বন্ধ করুন |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | এই ফোন থেকে কখনও বাইরে যায় না। সংরক্ষণের সময় এনক্রিপ্ট করা। |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | এখন আপনি যে ফাইলটি আমদানি করলেন সেটি মুছে দিন। ওটা আপনার পাসওয়ার্ডের খোলা তালিকা, আর এখনও আপনার Downloads-এ পড়ে আছে। |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | সব কিছু কেবল এই ফোনেই ডিক্রিপ্ট হয়। কিছুই আপলোড হয় না, কারণ এই অ্যাপ নেটওয়ার্ক সংযোগ খুলতেই পারে না। |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | ব্যাঙ্ক কখনও আপনার OTP চায় না। যে চায়, সে প্রতারক। |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | কোনও ব্যাঙ্ক আধিকারিক স্ক্রিন শেয়ার করার অ্যাপ বসাতে বলবেন না। |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | আপনার UPI পিন কেবল UPI অ্যাপের কীপ্যাডের জন্য — ফোনে কাউকে বলবেন না। |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | KYC এক দিনে শেষ হয় না। “আজ KYC শেষ হচ্ছে” এমন বার্তা প্রতারণা। |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | টাকা পাওয়ার জন্য কখনও পিন দিতে বা QR স্ক্যান করতে হয় না। |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | বিদ্যুৎ কেটে যাওয়ার SMS, আর তাতে কারও ব্যক্তিগত নম্বর? সেটা প্রতারণা। |  |
| `nav_close_menu` | Close menu | মেনু বন্ধ করুন |  |
| `nfc_cannot_read` | Cannot read cards | কার্ড পড়া যাচ্ছে না |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | এই ফোনে NFC নেই, তাই কার্ড পড়া যাবে না। |  |
| `ob_fact_lost_title` | If you lose your keys | চাবি হারালে |  |
| `ob_fact_network_note` | The app literally cannot phone home | এই অ্যাপ কোথাও যোগাযোগ করতেই পারে না |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | বায়োমেট্রিক নিরাপত্তা চিপের বাইরে কখনও যায় না |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | আপনি চাবিটা সেই ফোল্ডারেই রেখেছেন যেটা আপনার এনক্রিপ্ট করা সিন্দুক সিংক করে। এখন যে ওই ফোল্ডার পাবে, সে দুটোই পাবে। চাবিটা অন্য কোথাও রাখুন — কাগজ, অন্য অ্যাকাউন্ট, বা দেরাজ। |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | এটা আপনার সিন্দুক ফাইলের পাশেই |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH রিকভারি |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | স্ক্যান করুন বা টাইপ করুন। আবার ইনস্টল, ফ্যাক্টরি রিসেট, বা ফোন হারানোর পরেও কাজ করবে। |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | এইমাত্র সংরক্ষণ করা কিট থেকে %1$d আর %2$d নম্বর গুচ্ছ লিখুন। |  |
| `ob_kit_challenge_hint` | Group %1$d | গুচ্ছ %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | আগে কিট সংরক্ষণ করুন, তারপর %1$d আর %2$d নম্বর গুচ্ছ লিখুন। |  |
| `ob_kit_challenge_title` | Check you actually have it | কিটটা সত্যিই আছে কি না দেখে নিন |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | উপরের চাবির সঙ্গে মিলছে না। |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | কেউই — Zerokosh-ও — এটা আমার জন্য ফিরিয়ে আনতে পারবে না। |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "আমি এটা অফলাইনে রেখেছি। " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | একবারই দেখা যায়, কখনও খোলা অবস্থায় রাখা হয় না। ভিতরে ঢুকতে পারলে সেটিংস থেকে নতুন বানাতে পারবেন। |  |
| `ob_kit_head_emph` | On paper. | কাগজে। |  |
| `ob_kit_head_lead` | "One key. " | "একটি চাবি। " |  |
| `ob_kit_head_tail` | " Never online." | " কখনও অনলাইনে নয়।" |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | Gmail নয়, WhatsApp নয়, স্ক্রিনশট নয়। সিন্দুক, ব্যাঙ্ক লকার, বা ইস্পাতের পাত। |  |
| `ob_kit_offline_title` | Keep it off the internet | এটা ইন্টারনেট থেকে দূরে রাখুন |  |
| `ob_kit_print` | Print | ছাপুন |  |
| `ob_kit_print_note` | A printer, or Save as PDF | প্রিন্টার, বা PDF হিসেবে সংরক্ষণ |  |
| `ob_kit_qr` | QR image | QR ছবি |  |
| `ob_kit_qr_cd` | Recovery key QR code | রিকভারি চাবির QR কোড |  |
| `ob_kit_qr_note` | To an offline gallery | অফলাইন গ্যালারিতে |  |
| `ob_kit_regenerate` | Regenerate | নতুন তৈরি করুন |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | এটা সংরক্ষণ হয়নি। আবার চেষ্টা করুন, বা অন্য জায়গা বাছুন। |  |
| `ob_kit_save_pdf` | Save PDF | PDF সংরক্ষণ করুন |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | এক পাতার ছাপার উপযোগী কিট |  |
| `ob_kit_saved` | I\'ve saved my kit | আমি আমার কিট সংরক্ষণ করেছি |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s-এ সংরক্ষিত |  |
| `ob_kit_sent_to_printer` | Sent to the printer | প্রিন্টারে পাঠানো হয়েছে |  |
| `ob_kit_skip` | I\'ll do this later | এটা পরে করব |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | আপনার সিন্দুক চলতে থাকবে। কিট সংরক্ষণ না হওয়া পর্যন্ত Zerokosh মনে করিয়ে দেবে। |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | এই ফোনেই তৈরি, একবারই দেখা যাবে। পাসফ্রেজ ভুলে গেলে ভিতরে ফেরার এটাই একমাত্র পথ। |  |
| `ob_kit_working` | Working… | কাজ চলছে… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | সিন্দুকের লেবেল, টেমপ্লেট আর সতর্কবার্তা সঙ্গে সঙ্গে বদলে যাবে। সেটিংয়ে যখন খুশি বদলাতে পারবেন। |  |
| `ob_pass_confirm` | Confirm | আবার লিখুন |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | এটা আমরা কখনও দেখি না। কোনও রিসেট লিঙ্ক নেই। |  |
| `ob_pass_head_emph` | held only | কেবল আপনারই |  |
| `ob_pass_head_lead` | "One secret, " | "একটাই গোপন কথা, " |  |
| `ob_pass_head_tail` | " by you." | " কাছে।" |  |
| `ob_pass_no_match` | no match | মিলছে না |  |
| `ob_pass_seal` | Seal the vault | সিন্দুকে তালা লাগান |  |
| `ob_pass_sealing` | Sealing… | তালা লাগানো হচ্ছে… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | সম্পর্কহীন তিন-চারটি শব্দ, একটি চালাক শব্দের চেয়ে ভালো। এই পর্দা থেকে কিছু বাইরে যায় না। |  |
| `ob_pass_tab_passphrase` | Passphrase | পাসফ্রেজ |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 সংখ্যার পিন |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | আপনার আঙুলের ছাপ ফোনের নিরাপত্তা চিপের ভিতরে থাকে। সেটা এই ফোন থেকে কখনও বাইরে যায় না। |  |
| `ob_trust_continue` | I understand · Continue | বুঝলাম · এগিয়ে যান |  |
| `ob_trust_head_emph` | don\'t | জানি না |  |
| `ob_trust_head_lead` | "Exactly what we " | "আমরা আসলে কী " |  |
| `ob_trust_head_tail` | " know." | " সেটাই।" |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | পাসফ্রেজ ভুলে গিয়ে রিকভারি কিটও হারালে, সিন্দুক বন্ধই থাকবে — আপনার জন্যও, আমাদের জন্যও, কারও জন্যই। |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "চাবি আপনার কাছেই। " |  |
| `ob_trust_stat_files` | .kosh file on device | ফোনে .kosh ফাইল |  |
| `ob_trust_stat_servers` | servers contacted | সার্ভারের সঙ্গে যোগাযোগ |  |
| `ob_trust_stat_trackers` | trackers or SDKs | ট্র্যাকার বা SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | এটা একবার পড়ে নিন। পুরো নিরাপত্তা ব্যবস্থা এটাই, সহজ কথায়। |  |
| `ob_trust_tag_audited` | Audited build | অডিট করা বিল্ড |  |
| `ob_trust_tag_reproducible` | Reproducible APK | আবার তৈরি করা যায় এমন APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | আপনি আঙুলের ছাপ দিয়ে খুলছেন। আঙুলের ছাপ কোনওদিন কাজ করা বন্ধ করলে এটাই আপনাকে ভিতরে আনবে — তাই দেখে নেওয়া ভালো। |  |
| `pc_confirm` | Check | দেখুন |  |
| `pc_correct` | Still correct. Nothing to do. | এখনও ঠিক আছে। কিছু করার নেই। |  |
| `pc_forgot` | I cannot remember it | আমার মনে পড়ছে না |  |
| `pc_later` | Not now | এখন নয় |  |
| `pc_reset_action` | Set new passphrase | নতুন পাসফ্রেজ বসান |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | আপনার আঙুলের ছাপ এই সিন্দুক খুলতে পারে, তাই সেটাই নতুন পাসফ্রেজও বসাতে পারে — রিকভারি কিট লাগবে না। নিশ্চিত করতে আরেকবার চাওয়া হবে। |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | পাসফ্রেজ বদলে গেছে। চটজলদি আনলক আবার বসানো হয়েছে। |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | এটা হয়নি। আপনার পুরনো পাসফ্রেজই চালু আছে। |  |
| `pc_reset_title` | Set a new passphrase | নতুন পাসফ্রেজ বসান |  |
| `pc_title` | Do you still remember your passphrase? | আপনার পাসফ্রেজ কি এখনও মনে আছে? |  |
| `pc_wrong` | That is not it. You can set a new one instead. | এটা নয়। বদলে নতুন একটা বসাতে পারেন। |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | এই রেকর্ডের জন্য মনে রাখা %1$dটি মান মুছে ফেলা হবে। এটা ফেরানো যায় না। |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh সেই লগইন সংরক্ষণ করতে পারেনি। |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | ভরার জন্য Zerokosh খুলুন |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | এই একটি পাসফ্রেজই সব কিছু তালাবন্ধ রাখে। লম্বা কিছু বেছে নিন, যা কেবল আপনিই জানেন। |  |
| `scr_create_button` | Lock it in | তালা লাগিয়ে দিন |  |
| `scr_create_confirm_hint` | Type it again | আবার লিখুন |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | পাসফ্রেজ (অন্তত 10টি অক্ষর) |  |
| `scr_create_mismatch` | The two entries don\'t match | দুটো এক নয় |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 সংখ্যার পিন |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | তার বদলে 6 সংখ্যার পিন রাখুন |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | পিনের জন্য আঙুলের ছাপ বা মুখ আনলক আছে এমন ফোন দরকার। অনুগ্রহ করে পাসফ্রেজ বেছে নিন। |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | পিন চলে কারণ এই ফোন সেটাকে নিজের নিরাপত্তা চিপ আর আপনার আঙুলের ছাপ বা মুখ দিয়ে আগলে রাখে। |  |
| `scr_create_strength_fair` | Fair | মোটামুটি |  |
| `scr_create_strength_good` | Good | ভালো |  |
| `scr_create_strength_strong` | Strong | শক্ত |  |
| `scr_create_strength_weak` | Weak | দুর্বল |  |
| `scr_create_title` | Create your passphrase | আপনার পাসফ্রেজ তৈরি করুন |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | অন্তত 10টি অক্ষর রাখুন — যত লম্বা, তত শক্ত |  |
| `scr_create_working` | Preparing your vault… | আপনার সিন্দুক তৈরি হচ্ছে… |  |
| `scr_detail_delete` | Delete | মুছুন |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | সেটা 30 দিন “সম্প্রতি মোছা”-য় থাকবে, আর আপনার অন্য ফোনের সঙ্গে সিঙ্ক হলে চলে যাবে। |  |
| `scr_detail_delete_confirm_title` | Delete this record? | এই রেকর্ড মুছবেন? |  |
| `scr_detail_delete_confirm_yes` | Delete | মুছুন |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | সিক্রেট বা otpauth:// লিঙ্ক পেস্ট করুন |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | আপনার আঙুলের ছাপ বা মুখ ব্যবহার করুন |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh খুলুন |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | অনেকবার ভুল চেষ্টা হয়েছে। %1$d সেকেন্ড অপেক্ষা করুন। |  |
| `scr_lock_hint` | Passphrase | পাসফ্রেজ |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | এই রিকভারি চাবি ঠিক নয় — একটি একটি অক্ষর মিলিয়ে দেখুন |  |
| `scr_lock_title` | Vault is locked | সিন্দুক তালাবন্ধ |  |
| `scr_lock_unlock` | Unlock | খুলুন |  |
| `scr_lock_use_passphrase` | Use passphrase | পাসফ্রেজ ব্যবহার করুন |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | অনুগ্রহ করে একবার পাসফ্রেজ দিয়ে খুলুন |  |
| `scr_lock_use_recovery` | Use Recovery Key | রিকভারি চাবি ব্যবহার করুন |  |
| `scr_lock_wrong` | Wrong passphrase | পাসফ্রেজ ভুল |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | কখনও পাসফ্রেজ ভুলে গেলে, ভিতরে ফেরার এটাই একমাত্র পথ। আমরা এটা রিসেট করতে পারি না — কেউই পারে না। |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | আমি এটা লিখে নিরাপদ জায়গায় রেখেছি |  |
| `scr_recovery_done` | Continue | এগিয়ে যান |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | এই চাবি একবারই দেখা যায়। যতক্ষণ আপনি সিন্দুক খুলতে পারছেন, সেটিংস থেকে যেকোনো সময় নতুন বানাতে পারবেন। |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | এই পাতাটি আপনার সম্পত্তির কাগজপত্র বা অন্য জরুরি নথির সঙ্গে রাখুন। এই চাবি যার কাছে থাকবে সে আপনার সিন্দুক খুলতে পারবে — একে লকারের চাবির মতো সামলান। |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | রিকভারি কিট PDF সংরক্ষিত হয়েছে |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh রিকভারি কিট |  |
| `scr_recovery_save_pdf` | Save as PDF | PDF হিসেবে সংরক্ষণ করুন |  |
| `scr_recovery_title` | Your Recovery Key | আপনার রিকভারি চাবি |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | আপনি যা কিছু সংরক্ষণ করেন তা আপনার ফোনের একটি তালাবন্ধ ফাইলে থাকে। সেটা কখনও আমাদের কাছে আসে না — রাখার জায়গাই আমাদের নেই। |  |
| `scr_trust_card1_title` | Your data stays on this device | আপনার তথ্য এই ফোনেই থাকে |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | Zerokosh অ্যাকাউন্ট নেই, ক্লাউড নেই, সাইন-আপ নেই। এটা কেবল আপনিই খুলতে পারবেন। আমরাও পারি না। |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | আমাদের কোনও সার্ভার নেই — হ্যাক করার কিছু নেই, বেচারও কিছু নেই |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | কোনও চাঁদা নেই, বিজ্ঞাপন নেই। যে কেউ আমাদের কোড পড়ে আমাদের প্রতিটি প্রতিশ্রুতি যাচাই করতে পারেন। |  |
| `scr_trust_card3_title` | Free forever, open source | চিরকাল বিনামূল্যে, ওপেন সোর্স |  |
| `scr_trust_continue` | Continue | এগিয়ে যান |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | আপনার বদলগুলো সংরক্ষিত হয়নি, তাই সিন্দুকে আগে যা ছিল তার কিছুই হারায়নি। |  |
| `st_recently_deleted` | Recently deleted | সম্প্রতি মোছা |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | এক-বারের কোড (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | এক-বারের কোড (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | এক-বারের কোড (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA সিক্রেট |  |
| `tr_cannot_undo` | This cannot be undone. | এটা ফেরানো যায় না। |  |
| `tr_delete_all` | Delete all permanently | সব চিরতরে মুছুন |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$dটি রেকর্ড চিরতরে চলে যাবে। এটা ফেরানো যায় না আর ফিরিয়ে আনার মতো ব্যাকআপও নেই। |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$dটি রেকর্ড চিরতরে চলে যাবে। এটা ফেরানো যায় না আর ফিরিয়ে আনার মতো ব্যাকআপও নেই। |  |
| `tr_delete_all_title` | Delete everything in the trash? | আবর্জনার সব কিছু মুছে দেবেন? |  |
| `tr_delete_now` | Delete now | এখনই মুছুন |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | “%1$s” চিরতরে মুছে দেবেন? |  |
| `tr_empty` | Nothing deleted. | কিছুই মোছা হয়নি। |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | মোছা রেকর্ড এখানে %1$d দিন থাকে। |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | ভারতীয় ব্যাঙ্ক, UPI, কার্ড, ডিম্যাট, EPF আর আপনি সত্যিই যে OTP অ্যাপগুলো ব্যবহার করেন — এসবের জন্য ফোনেই থাকা সিন্দুক। |  |

## Priority 2 — longer prose

| key | English | Bengali | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | যেটা সবচেয়ে বেশি কাজে লাগে সেই একটি তথ্য দিয়ে শুরু করুন। নোটস অ্যাপে পড়ে থাকা বারোটা পাসওয়ার্ডের চেয়ে সংরক্ষণ করা একটি পাসওয়ার্ডও নিরাপদ। |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | পাসফ্রেজ ভুলে গেলে রিকভারি কিটই ভিতরে ঢোকার একমাত্র পথ। কেউ আপনার জন্য আরেকটা বানাতে পারবে না। |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | সেই ফাইলে চেনার মতো কিছু মেলেনি। Chrome, Google Password Manager, Bitwarden, LastPass আর KeePass-এর এক্সপোর্ট বোঝা যায়। |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$dটি বর্তমান রেকর্ড বদলাবে — সাইট আর ইউজারনেম মিলিয়ে। বদলে যাওয়া পাসওয়ার্ড প্রতিটি রেকর্ডের ইতিহাসে ফিরে পাওয়া যাবে। |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$dটি বর্তমান রেকর্ড বদলাবে — সাইট আর ইউজারনেম মিলিয়ে। বদলে যাওয়া পাসওয়ার্ড প্রতিটি রেকর্ডের ইতিহাসে ফিরে পাওয়া যাবে। |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d রেকর্ড দুই দিকেই বদলেছিল। দুটো রূপই রাখা হয়েছে — “(conflict copy)” খুঁজুন। |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | এই ব্যাকআপ ফাইল খোলে এমন পাসফ্রেজ দিন। সেটা আপনার এখনকারটার থেকে আলাদা হতে পারে। |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | .kosh ফাইলের Poly1305 প্রমাণীকরণ ট্যাগ মিলছে না। অসম্পূর্ণ সিঙ্ক বা খারাপ স্টোরেজের পরে এমন হতে পারে। |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh সিন্দুক ফাইলের পাশেই একটি চলমান ব্যাকআপ রাখে। সেটা আপনার সিঙ্ক ফোল্ডার থেকে ফিরিয়ে আনুন, বা অন্য ফোনে রিকভারি কিট দিয়ে সিন্দুক খুলুন। |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | পড়া না হওয়া পর্যন্ত কার্ডটা ফোনের পিছনে সোজা ঠেকিয়ে রাখুন। এতে কার্ড নম্বর, মেয়াদ আর নাম পাওয়া যায় — CVV চিপে থাকে না, সেটা আপনাকেই লিখতে হবে। |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | ভিতরে ফেরার একটা দ্রুত পথ বেছে নিন। সিন্দুকের পাহারা পাসফ্রেজই দেবে; এটা কেবল এই ফোনে চাবি খোলে। |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | আপনার সিন্দুকে এখন সত্যিকারের তথ্য আছে। রিকভারি কিট ছাড়া পাসফ্রেজ ভুলে গেলে কেউ আপনাকে ফেরাতে পারবে না — আমরাও না। |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh বিনামূল্যের, ওপেন সোর্স, আর এর কোনও সার্ভার নেই। আপনার সিন্দুক কেবল আপনিই খুলতে পারবেন। আমরাও পারি না। |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | QR কোড স্ক্যান করতেই কেবল ক্যামেরার অনুমতি লাগে। রেকর্ড যোগ করার সময় সিক্রেট হাতেও পেস্ট করতে পারেন। |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | যে ব্যাঙ্ক অ্যাপ অটোফিল চলতে দেয় না তাদের জন্য — বোতাম টিপে লগইনের তথ্য একটি একটি করে কপি করুন |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | যেভাবে ফোন খোলেন, সিন্দুকও সেভাবেই। পাসফ্রেজ সবসময় কাজ করতে থাকবে। |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | সিন্দুকের স্ক্রিনশট ক্লাউড ফোটো ব্যাকআপে চলে যেতে পারে। সত্যিই দরকার হলে তবেই চালু করুন। |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | সিন্দুক ফাইলটি লেখা গেল না। আপনি ব্যাকআপ ও সিঙ্ক ফোল্ডার সেট করে থাকলে, Android হয়তো তার অনুমতি ফিরিয়ে নিয়েছে — সেটিং খুলুন, ফোল্ডারটি আবার বেছে নিন, আর আবার চেষ্টা করুন। |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | আপনার এনক্রিপ্ট করা .kosh ফাইল সরাসরি এই ফোল্ডারেই সংরক্ষিত হয়। অনেক ডিভাইসে নিজে থেকে ব্যাকআপের জন্য এই ফোল্ডারটি Google Drive, Syncthing, Nextcloud বা SD কার্ডের সঙ্গে সিঙ্ক করুন। |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | নতুন রিকভারি চাবি তৈরি করতে আপনার পাসফ্রেজ দিন। পুরনো চাবি কাজ করা বন্ধ করে দেবে। |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | এই পাতার বাকি সব একটা লগইন দুর্বল করে। এটা গোটা সিন্দুক কেড়ে নিতে পারে। সেটিংস → নতুন রিকভারি চাবি নিন। |  |

## Priority 3 — short labels

| key | English | Bengali | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | শক্তিশালী পাসওয়ার্ড ব্যবহার করুন |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | অ্যাকাউন্টের নাম (যেমন Google) |  |
| `au_active_many` | %1$d active codes | %1$dটি চালু কোড |  |
| `au_active_one` | %1$d active code | %1$dটি চালু কোড |  |
| `au_add_another` | Add another authenticator | আরেকটি অথেন্টিকেটর যোগ করুন |  |
| `au_add_secret` | Add Secret Key | সিক্রেট কী যোগ করুন |  |
| `au_camera_needed` | Camera permission needed to scan QR code | QR কোড স্ক্যান করতে ক্যামেরার অনুমতি লাগে |  |
| `au_copied` | Copied · clears shortly | কপি হয়েছে · একটু পরেই মুছে যাবে |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google, GitHub বা আপনার ব্রোকারের QR স্ক্যান করুন, বা সিক্রেট কী হাতে লিখুন। |  |
| `au_enter_key` | Enter Key | কী লিখুন |  |
| `au_fallback_name` | Authenticator | অথেন্টিকেটর |  |
| `au_flashlight` | Flashlight | টর্চ |  |
| `au_grant` | Grant Permission | অনুমতি দিন |  |
| `au_image_failed` | Failed to process image | ছবি পড়া গেল না |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | ভুল Base32 সিক্রেট কী (কেবল A-Z অক্ষর আর 2-7 সংখ্যা) |  |
| `au_no_match` | No codes match | কোনও কোড মেলেনি |  |
| `au_none_yet` | No codes yet. | এখনও কোনও কোড নেই। |  |
| `au_pick_image` | Pick Image | ছবি বেছে নিন |  |
| `au_rotating` | "Rotating " | "বদলাতে থাকা " |  |
| `au_rotating_emph` | codes. | কোড। |  |
| `au_save_key` | Save Key | কী সংরক্ষণ করুন |  |
| `au_scan_qr` | Scan a QR code | QR কোড স্ক্যান করুন |  |
| `au_scan_title` | Scan Authenticator QR | অথেন্টিকেটর QR স্ক্যান করুন |  |
| `au_search_hint` | Search codes, issuers… | কোড, প্রদানকারী খুঁজুন… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | যেমন JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | সিক্রেট কী (Base32) |  |
| `au_tap_to_copy` | Tap to copy | কপি করতে ট্যাপ করুন |  |
| `cat_apps` | Apps &amp; Logins | অ্যাপ ও লগইন |  |
| `cat_banks` | Banks &amp; UPI | ব্যাঙ্ক ও UPI |  |
| `cat_cards` | Cards | কার্ড |  |
| `cat_govid` | Gov &amp; ID | সরকারি ও পরিচয় |  |
| `cat_investments` | Investments | বিনিয়োগ |  |
| `cat_utilities` | Utilities | বিল ও সংযোগ |  |
| `cd_mask_hidden` | hidden | লুকানো |  |
| `cd_shield_high_sensitivity` | extra-protected field | বাড়তি সুরক্ষিত তথ্য |  |
| `gl_blank` | Blank template | ফাঁকা টেমপ্লেট |  |
| `gl_cat_apps` | Apps | অ্যাপ |  |
| `gl_cat_banks` | Banks | ব্যাঙ্ক |  |
| `gl_cat_cards` | Cards | কার্ড |  |
| `gl_cat_demat` | Demat | ডিম্যাট |  |
| `gl_cat_govid` | Gov ID | সরকারি পরিচয় |  |
| `gl_cat_popular` | Popular | জনপ্রিয় |  |
| `gl_cat_shopping` | Shopping | কেনাকাটা |  |
| `gl_cat_travel` | Travel | ভ্রমণ |  |
| `gl_cat_upi` | UPI | UPI |  |
| `gl_cat_utilities` | Utilities | বিল |  |
| `gl_head_emph` | storing? | সংরক্ষণ করছি? |  |
| `gl_head_lead` | "What are we " | "আমরা কী " |  |
| `gl_matches` | %1$d matches | %1$dটি মিলেছে |  |
| `gl_most_used` | Most-used first | সবচেয়ে বেশি ব্যবহৃত আগে |  |
| `gl_not_found` | Can’t find a service? | পরিষেবা মিলছে না? |  |
| `gl_search` | Search %1$d Indian services… | %1$dটি ভারতীয় পরিষেবায় খুঁজুন… |  |
| `gl_suggested` | Suggested for you | আপনার জন্য পরামর্শ |  |
| `hm_add_first` | Add your first record | আপনার প্রথম রেকর্ড যোগ করুন |  |
| `hm_all_offline` | all offline. | সব অফলাইন। |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d তথ্য, " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d তথ্য, " |  |
| `hm_detail_placeholder` | Pick a record to see it here | এখানে দেখতে একটি রেকর্ড বেছে নিন |  |
| `hm_empty_blank` | A blank vault, ready. | ফাঁকা সিন্দুক, তৈরি। |  |
| `hm_empty_head_emph` | waiting. | অপেক্ষা করছে। |  |
| `hm_empty_head_lead` | "Your vault is " | "আপনার সিন্দুক " |  |
| `hm_filter_all` | All | সব |  |
| `hm_import_backup` | Import an encrypted backup | এনক্রিপ্ট করা ব্যাকআপ আমদানি |  |
| `hm_import_backup_note` | Open a .kosh file from this device | এই ফোন থেকেই .kosh ফাইল খুলুন |  |
| `hm_inst_many` | %1$d institutions | %1$d প্রতিষ্ঠান |  |
| `hm_inst_one` | %1$d institution | %1$d প্রতিষ্ঠান |  |
| `hm_kit_banner_action` | Save one now | এখনই সংরক্ষণ করুন |  |
| `hm_kit_banner_dismiss` | Remind me later | পরে মনে করাবেন |  |
| `hm_kit_banner_title` | No recovery kit saved | কোনও রিকভারি কিট সংরক্ষিত নেই |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | খোলা আছে · ছাড়লেই তালা |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | খোলা আছে · ছাড়ার %1$d মিনিট পরে তালা |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | খোলা আছে · ছাড়ার 1 মিনিট পরে তালা |  |
| `hm_no_match` | Nothing matches “%1$s” | “%1$s”-এ কিছু মেলেনি |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | কোনও প্রতিষ্ঠান, UPI হ্যান্ডেল, বা শেষ চারটি সংখ্যা দিয়ে দেখুন। |  |
| `hm_pinned` | Pinned | পিন করা |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC, priya@upi, PAN… খুঁজুন |  |
| `hm_start_template` | Start with a template | টেমপ্লেট দিয়ে শুরু করুন |  |
| `ic_could_not` | Could not import | আমদানি করা গেল না |  |
| `ic_done` | Done | হয়ে গেছে |  |
| `ic_import` | Import | আমদানি |  |
| `ic_imported` | Imported | আমদানি হয়েছে |  |
| `ic_importing` | Importing… | আমদানি হচ্ছে… |  |
| `ic_new_many` | %1$d new logins. | %1$dটি নতুন লগইন। |  |
| `ic_new_one` | %1$d new login. | %1$dটি নতুন লগইন। |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d যোগ হয়েছে, %2$d বদলেছে। |  |
| `ic_title` | Import from %1$s? | %1$s থেকে আমদানি করবেন? |  |
| `ic_too_large` | That file is too large to be a credential export. | এই ফাইলটি এত বড় যে পাসওয়ার্ড এক্সপোর্ট হতেই পারে না। |  |
| `import_action` | Import | আমদানি |  |
| `import_locked` | Unlock your vault before importing. | আমদানি করার আগে আপনার সিন্দুক খুলুন। |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d যোগ হয়েছে, %2$d বদলেছে। কিছু মোছা হয়নি। |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | সেই ফাইলটি Zerokosh সিন্দুক হিসেবে পড়া গেল না। |  |
| `import_nothing_new` | Everything in that backup was already here. | সেই ব্যাকআপের সব কিছু আগে থেকেই এখানে ছিল। |  |
| `import_passphrase_label` | Backup passphrase | ব্যাকআপের পাসফ্রেজ |  |
| `import_title` | Import a backup | ব্যাকআপ আমদানি করুন |  |
| `kicker_locked` | Locked | তালাবন্ধ |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · কিছুই এই ফোনের বাইরে যায়নি |  |
| `lk_touch_unlock` | Touch to unlock | খুলতে ছোঁয়ান |  |
| `lk_welcome_emph` | Your vault is sealed. | আপনার সিন্দুক তালাবন্ধ। |  |
| `lk_welcome_lead` | Welcome back. | আবার স্বাগতম। |  |
| `msg_auth_needed` | Confirm it\'s you to see this | দেখতে হলে নিশ্চিত করুন যে এটা আপনিই |  |
| `msg_back` | Back | পিছনে |  |
| `msg_cancel` | Cancel | বাতিল |  |
| `msg_file_damaged` | File damaged — restored from backup | ফাইল নষ্ট হয়েছিল — ব্যাকআপ থেকে ঠিক করা হয়েছে |  |
| `msg_ok` | OK | ঠিক আছে |  |
| `msg_saved` | Saved | সংরক্ষিত |  |
| `nav_all_templates` | All templates | সব টেমপ্লেট |  |
| `nav_damaged_emph` | vault file | সিন্দুক ফাইলে |  |
| `nav_damaged_kicker` | Damaged state | খারাপ অবস্থা |  |
| `nav_damaged_lead` | "Something in the " | "আপনার " |  |
| `nav_damaged_tail` | " is off." | " কিছু গোলমাল আছে।" |  |
| `nav_integrity_title` | Integrity check failed | অখণ্ডতা যাচাই ব্যর্থ |  |
| `nav_scan` | Scan | স্ক্যান |  |
| `nav_tap_card` | Tap a card | কার্ড ট্যাপ করুন |  |
| `nav_what_next` | What to do next | এখন কী করবেন |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC বন্ধ আছে। সেটিংয়ে গিয়ে চালু করে আবার চেষ্টা করুন। |  |
| `nfc_hold_card` | Hold your card to the phone | কার্ডটা ফোনে ঠেকান |  |
| `nfc_missed` | Did not catch that | ধরা পড়ল না |  |
| `nfc_read_failed` | That card could not be read. Try again. | সেই কার্ড পড়া গেল না। আবার চেষ্টা করুন। |  |
| `nfc_reading` | Reading… | পড়া হচ্ছে… |  |
| `nfc_try_again` | Try again | আবার চেষ্টা করুন |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · এই ফোনেই মাপা |  |
| `ob_argon_faster` | Faster unlock | তাড়াতাড়ি খুলবে |  |
| `ob_argon_harder` | Harder to attack | ভাঙা কঠিন |  |
| `ob_argon_measuring` | Measuring this device… | এই ফোন মাপা হচ্ছে… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id-এর কাঠিন্য |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | অভিধানের একটিও শব্দ নয় |  |
| `ob_check_pass_length` | 10 characters or more | 10 বা তার বেশি অক্ষর |  |
| `ob_check_pass_reuse` | Not reused from another app | অন্য অ্যাপ থেকে আবার ব্যবহার করা নয় |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | জন্মদিনও নয়, বিবাহবার্ষিকীও নয় |  |
| `ob_check_pin_digits` | All six digits entered | ছয়টি সংখ্যাই ভরা হয়েছে |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | পরপর সংখ্যাও নয়, পুনরাবৃত্তিও নয় |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~ভাঙতে %1$d শতাব্দী |  |
| `ob_crack_days` | ~%1$d days to crack | ~ভাঙতে %1$d দিন |  |
| `ob_crack_forever` | longer than the sun | সূর্যের চেয়েও বেশি সময় |  |
| `ob_crack_hours` | ~hours to crack | ~ভাঙতে কয়েক ঘণ্টা |  |
| `ob_crack_seconds` | ~seconds to crack | ~ভাঙতে কয়েক সেকেন্ড |  |
| `ob_crack_years` | ~%1$d years to crack | ~ভাঙতে %1$d বছর |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | প্রমাণিত, প্রতিটি সিন্দুকের আলাদা নন্স |  |
| `ob_fact_encryption_title` | Encryption | এনক্রিপশন |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | সেটআপের সময় আপনার ফোনে মাপা |  |
| `ob_fact_kdf_title` | Key stretching | কী স্ট্রেচিং |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | রিসেট লিঙ্ক নেই। সাপোর্টের পিছনের দরজাও নেই। |  |
| `ob_fact_lost_value` | Nobody can recover it | কেউ ফিরিয়ে আনতে পারবে না |  |
| `ob_fact_network_title` | Network permission | নেটওয়ার্কের অনুমতি |  |
| `ob_fact_network_value` | Not requested | চাওয়াই হয়নি |  |
| `ob_fact_quick_title` | Quick unlock | চটজলদি আনলক |  |
| `ob_fact_quick_value` | Hardware keystore | হার্ডওয়্যার কীস্টোর |  |
| `ob_lang_continue` | Continue in %1$s | %1$s-এ এগিয়ে যান |  |
| `ob_lang_head_emph` | language. | ভাষা বেছে নিন। |  |
| `ob_lang_head_lead` | "Choose your " | "আপনার " |  |
| `ob_lang_search` | Search %1$d languages | %1$dটি ভাষায় খুঁজুন |  |
| `ob_quick_continue_pass` | Continue with passphrase | পাসফ্রেজ নিয়ে এগিয়ে যান |  |
| `ob_quick_enable` | Enable quick unlock | চটজলদি আনলক চালু করুন |  |
| `ob_quick_fingerprint` | Fingerprint | আঙুলের ছাপ |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | দ্রুত, হার্ডওয়্যারে সুরক্ষিত আনলক। |  |
| `ob_quick_head_emph` | Without the cloud. | ক্লাউড ছাড়াই। |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "এক ছোঁয়ায় খুলবে। " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox। কোনও বায়োমেট্রিক তথ্য Zerokosh পর্যন্ত কখনও পৌঁছায় না। |  |
| `ob_quick_hw_title` | Hardware-backed | হার্ডওয়্যারে সুরক্ষিত |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | এই ফোনে হার্ডওয়্যার সেন্সর নেই। |  |
| `ob_quick_opening` | Opening your vault… | আপনার সিন্দুক খুলছে… |  |
| `ob_quick_pass_only` | Passphrase only | কেবল পাসফ্রেজ |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | প্রতিবার লিখুন। সবচেয়ে নিরাপদ। |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | চটজলদি আনলক সেট হয়নি। আবার চেষ্টা করুন, বা কেবল পাসফ্রেজ নিয়েই এগিয়ে যান। |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | এখন থাক — আমি পাসফ্রেজ লিখে নেব |  |
| `ob_quick_touch_title` | Touch the sensor | সেন্সরে ছোঁয়ান |  |
| `ob_recommended` | Recommended | সুপারিশ |  |
| `ob_reveal_hide` | Hide | লুকান |  |
| `ob_reveal_show` | Show | দেখান |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | মোড়ানোর চাবি হার্ডওয়্যার কীস্টোরে থাকে। বায়োমেট্রিক পরের ধাপে। |  |
| `ob_seal_title` | Seal to this device | এই ফোনের সঙ্গেই বেঁধে দিন |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | এই ফোনে হার্ডওয়্যার বায়োমেট্রিক নেই। |  |
| `ob_soon` | SOON | শীঘ্রই |  |
| `ob_step_label` | Step %1$d of 6 | ধাপ %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | এমন কিছু বেছে নিন যা কেবল আপনিই বলেন |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | ভালো · %1$d বিট এনট্রপি |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | খুব ছোট · 10টি অক্ষর চাই |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | শক্ত · %1$d বিট এনট্রপি |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | দুর্বল · %1$d বিট এনট্রপি |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | এমন ছয়টি সংখ্যা যা আপনার জীবন দেখে কেউ আন্দাজ করতে পারবে না |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | মোটামুটি · %1$d বিট — পিন এর চেয়ে শক্ত হতে পারে না |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | খুব ছোট · 6টি সংখ্যা চাই |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | দুর্বল · এই পিনগুলোই সবার আগে চেষ্টা করা হয় |  |
| `ob_try_label` | TRY | চেষ্টা |  |
| `qa_aadhaar` | Aadhaar | আধার |  |
| `qa_bank_account` | Bank account | ব্যাঙ্ক অ্যাকাউন্ট |  |
| `qa_pan` | PAN | PAN |  |
| `qa_upi_id` | UPI ID | UPI ID |  |
| `rd_copied` | Copied | কপি হয়েছে |  |
| `rd_forget` | Forget | ভুলে যান |  |
| `rd_forget_these` | Forget these | এগুলো ভুলে যান |  |
| `rd_forget_title` | Forget previous passwords? | আগের পাসওয়ার্ড ভুলে যাবেন? |  |
| `rd_history_hide` | Hide | লুকান |  |
| `rd_history_show` | Show %1$d | %1$d দেখান |  |
| `rd_hold_to_reveal` | Hold to reveal | দেখতে চেপে ধরুন |  |
| `rd_last_edit` | last edit %1$s | শেষ বদল %1$s |  |
| `rd_release_to_hide` | Release to hide | লুকাতে ছেড়ে দিন |  |
| `re_add_field` | + Add another field | + আরেকটি ফিল্ড যোগ করুন |  |
| `re_add_field_title` | Add a field | ফিল্ড যোগ করুন |  |
| `re_field_name` | Field name | ফিল্ডের নাম |  |
| `re_pick_date` | Pick a date | তারিখ বেছে নিন |  |
| `re_remove` | Remove | সরিয়ে দিন |  |
| `re_tap_card` | Read the card by tapping it | কার্ড ট্যাপ করে পড়ুন |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | গোপন হিসেবে ধরুন (লুকানো থাকবে, দেখতে চেপে ধরুন) |  |
| `re_using_template` | using the %1$s template | %1$s টেমপ্লেট দিয়ে |  |
| `rem_kit_title` | No recovery kit saved | কোনও রিকভারি কিট সংরক্ষিত নেই |  |
| `scr_about_license` | License: GPL-3.0 — free forever | লাইসেন্স: GPL-3.0 — চিরকাল বিনামূল্যে |  |
| `scr_about_source` | Source code | সোর্স কোড |  |
| `scr_about_version` | Version %1$s | সংস্করণ %1$s |  |
| `scr_auth_add_qr` | Add via QR | QR দিয়ে যোগ করুন |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | আপনার অ্যাপ আর ব্রোকারের কোড এখানে দেখা যাবে |  |
| `scr_auth_scan_title` | Point the camera at the QR code | ক্যামেরাটা QR কোডের উপর ধরুন |  |
| `scr_detail_copied` | Copied · clears in 30s | কপি হয়েছে · 30 সেকেন্ডে মুছে যাবে |  |
| `scr_detail_copy` | Copy | কপি |  |
| `scr_detail_edit` | Edit | বদলান |  |
| `scr_detail_favorite` | Favourite | পছন্দের |  |
| `scr_detail_hidden` | Hidden | লুকানো |  |
| `scr_detail_hide` | Hide | লুকান |  |
| `scr_detail_history_empty` | Nothing replaced yet. | এখনও কিছু বদলায়নি। |  |
| `scr_detail_history_title` | Previous passwords | আগের পাসওয়ার্ড |  |
| `scr_detail_reveal` | Show | দেখান |  |
| `scr_detail_shown` | Shown | দেখা যাচ্ছে |  |
| `scr_edit_cancel` | Cancel | বাতিল |  |
| `scr_edit_generate` | Generate | তৈরি করুন |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | ব্যাঙ্ক / কোম্পানি (দল করার জন্য) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | এটা ঠিক লাগছে না — একবার দেখে নিন |  |
| `scr_edit_link_none` | None | কিছু নেই |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | এই কার্ড নম্বর সাধারণ যাচাইয়ে উতরোয়নি — ঠিক থাকলে সংরক্ষণ করে দিন |  |
| `scr_edit_month` | Month | মাস |  |
| `scr_edit_picker_other` | Other… | অন্যান্য… |  |
| `scr_edit_picker_other_hint` | Type your own | নিজেরটা লিখুন |  |
| `scr_edit_required_title` | Give it a name first | আগে এটার একটা নাম দিন |  |
| `scr_edit_save` | Save | সংরক্ষণ |  |
| `scr_edit_title_hint` | Title | নাম |  |
| `scr_edit_title_new` | New | নতুন |  |
| `scr_edit_year` | Year | বছর |  |
| `scr_gallery_quick_add` | Quick add | চটপট যোগ করুন |  |
| `scr_gallery_title` | What do you want to save? | আপনি কী সংরক্ষণ করতে চান? |  |
| `scr_home_add` | Add | যোগ করুন |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | আপনার ব্যাঙ্ক অ্যাকাউন্ট এমন দেখাবে |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | আপনার কার্ড, UPI আর অ্যাপ লগইনও এখানেই থাকবে |  |
| `scr_home_group_other` | Other | অন্যান্য |  |
| `scr_home_no_results` | Nothing matches your search | আপনার খোঁজে কিছু মেলেনি |  |
| `scr_home_search_hint` | Search your vault | আপনার সিন্দুকে খুঁজুন |  |
| `scr_home_tab_authenticator` | Authenticator | কোড |  |
| `scr_home_tab_home` | Home | হোম |  |
| `scr_home_tab_settings` | Settings | সেটিং |  |
| `scr_home_title` | Home | হোম |  |
| `scr_language_continue` | Continue | এগিয়ে যান |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | আপনার ভাষা বেছে নিন |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | কপি করতে বোতাম টিপুন · 30 সেকেন্ডে মুছে যাবে |  |
| `scr_login_helper_channel` | Login helper | লগইন সহায়ক |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s-এ লগইন হচ্ছে |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | এখনই ব্যাকআপ ফোল্ডার সেট করুন |  |
| `scr_quickunlock_enable` | Turn on | চালু করুন |  |
| `scr_quickunlock_skip` | Not now | এখন নয় |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | আঙুলের ছাপ বা মুখ দিয়ে খুলুন |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s-এর তারিখ কাছে এসেছে · Zerokosh খুলুন |  |
| `scr_reminder_channel` | Renewal reminders | নবীকরণের মনে করানো |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh মনে করাচ্ছে |  |
| `scr_settings_about` | About | পরিচয় |  |
| `scr_settings_allow_screenshots` | Allow screenshots | স্ক্রিনশট নিতে দিন |  |
| `scr_settings_autofill` | Autofill service | অটোফিল পরিষেবা |  |
| `scr_settings_autofill_off` | Not set up | সেট করা নেই |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | পাওয়া যাচ্ছে না |  |
| `scr_settings_autolock` | Lock when I leave the app | অ্যাপ ছাড়লেই তালা দিন |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 মিনিট পরে |  |
| `scr_settings_autolock_immediately` | Immediately | সঙ্গে সঙ্গে |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d মিনিট পরে |  |
| `scr_settings_change_passphrase` | Change passphrase | পাসফ্রেজ বদলান |  |
| `scr_settings_current_passphrase` | Current passphrase | এখনকার পাসফ্রেজ |  |
| `scr_settings_export` | Export | রপ্তানি করুন |  |
| `scr_settings_import` | Import passwords | পাসওয়ার্ড আমদানি করুন |  |
| `scr_settings_language` | Language | ভাষা |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | নতুন পাসফ্রেজ (অন্তত 10টি অক্ষর) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | নতুন রিকভারি চাবি নিন |  |
| `scr_settings_passphrase_changed` | Passphrase changed | পাসফ্রেজ বদলেছে |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | আঙুলের ছাপ / মুখ আনলক |  |
| `scr_settings_security_info` | How your data is protected | আপনার তথ্য কীভাবে সুরক্ষিত |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | ব্যাকআপ ও সিঙ্ক ফোল্ডার |  |
| `scr_settings_sync_not_set` | Not backed up | ব্যাকআপ নেই |  |
| `scr_settings_title` | Settings | সেটিং |  |
| `se_title` | Not saved | সংরক্ষণ হয়নি |  |
| `st_active_folder` | Active Folder | চালু ফোল্ডার |  |
| `st_active_value` | Active · %1$s | চালু · %1$s |  |
| `st_backing_up` | Backing up vault… | সিন্দুকের ব্যাকআপ হচ্ছে… |  |
| `st_backup_now` | Backup Now | এখনই ব্যাকআপ নিন |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | ব্যাকআপ ও সিঙ্ক ফোল্ডার |  |
| `st_change_folder` | Change Folder | ফোল্ডার বদলান |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | নতুন পাসফ্রেজ আবার লিখুন |  |
| `st_connected_folder` | Connected folder: %1$s | যুক্ত ফোল্ডার: %1$s |  |
| `st_disconnect` | Disconnect | সরিয়ে দিন |  |
| `st_done` | Done | হয়ে গেছে |  |
| `st_export_kosh` | Export encrypted .kosh | এনক্রিপ্ট করা .kosh রপ্তানি |  |
| `st_folder_fallback` | Folder | ফোল্ডার |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | পাসফ্রেজ ভুলে গেছেন? |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | আঙুলের ছাপ দিয়ে নতুন বসান |  |
| `st_generate` | Generate | তৈরি করুন |  |
| `st_group_about` | About | পরিচয় |  |
| `st_group_appearance` | Appearance | চেহারা |  |
| `st_group_security` | Security | নিরাপত্তা |  |
| `st_group_sync` | Sync | সিঙ্ক |  |
| `st_import_kosh` | Import a .kosh backup | .kosh ব্যাকআপ আমদানি |  |
| `st_import_other` | Import from another password manager | অন্য পাসওয়ার্ড ম্যানেজার থেকে আমদানি |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | লাইসেন্স |  |
| `st_logos_by` | Logos provided by | লোগো দিয়েছে |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | এটা অফলাইনে রাখুন। পুরনো রিকভারি চাবি আর বৈধ নয়। |  |
| `st_new_recovery_result` | Your new Recovery Key: | আপনার নতুন রিকভারি চাবি: |  |
| `st_subtitle` | Your rules. | আপনার নিয়ম। |  |
| `st_theme` | Theme | থিম |  |
| `st_theme_dark` | Dark | গাঢ় |  |
| `st_theme_light` | Light | হালকা |  |
| `st_theme_system` | System | সিস্টেম |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | সিন্দুক %1$s-এ সংরক্ষিত! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | ব্যাকআপ হয়নি — ফোল্ডারের অনুমতি দেখে নিন |  |
| `st_toast_disconnected` | Backup folder disconnected | ব্যাকআপ ফোল্ডার সরানো হয়েছে |  |
| `st_toast_export_failed` | Export failed | রপ্তানি হয়নি |  |
| `st_toast_exported` | Encrypted vault exported | এনক্রিপ্ট করা সিন্দুক রপ্তানি হয়েছে |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | ব্যাকআপ ফোল্ডার যুক্ত হয়েছে আর সিন্দুক %1$s-এ সংরক্ষিত! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | ব্যাকআপ ফোল্ডার যুক্ত হয়েছে: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | ফোল্ডার যুক্ত করা গেল না: %1$s |  |
| `st_vault_review` | Vault review | সিন্দুক পরীক্ষা |  |
| `st_vault_review_detail` | Reused, weak, expiring | আবার ব্যবহার করা, দুর্বল, শেষ হয়ে আসছে |  |
| `tab_codes` | Codes | কোড |  |
| `tab_settings` | Settings | সেটিং |  |
| `tab_templates` | Templates | টেমপ্লেট |  |
| `tab_vault` | Vault | সিন্দুক |  |
| `time_days` | %1$dd ago | %1$d দি আগে |  |
| `time_hours` | %1$dh ago | %1$d ঘ আগে |  |
| `time_just_now` | just now | এইমাত্র |  |
| `time_minutes` | %1$dm ago | %1$d মি আগে |  |
| `time_months` | %1$dmo ago | %1$d মাস আগে |  |
| `time_years` | %1$dy ago | %1$d বছর আগে |  |
| `tpl_aadhaar_card` | Aadhaar Card | আধার |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | আধার নম্বর |  |
| `tpl_aadhaar_card_address` | Address | আধারে ঠিকানা |  |
| `tpl_aadhaar_card_dob` | Dob | জন্ম তারিখ |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | স্ক্যান করা নকল |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | যুক্ত মোবাইল |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar পাসকোড |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | আধারে নাম |  |
| `tpl_aadhaar_card_notes` | Notes | নোট |  |
| `tpl_app_profile` | App Profile | অ্যাপ প্রোফাইল |  |
| `tpl_app_profile_app_name` | App name | অ্যাপের নাম |  |
| `tpl_app_profile_gift_cards` | Gift cards | গিফট কার্ড |  |
| `tpl_app_profile_membership` | Membership | সদস্যপদ |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | সদস্যপদ নবীকরণ |  |
| `tpl_app_profile_notes` | Notes | নোট |  |
| `tpl_app_profile_password_if_any` | Password (if any) | পাসওয়ার্ড (থাকলে) |  |
| `tpl_app_profile_registered_email` | Registered email | নথিভুক্ত ইমেল |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | নথিভুক্ত মোবাইল |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | ওয়ালেট পিন |  |
| `tpl_bank_account` | Bank Account | ব্যাঙ্ক অ্যাকাউন্ট |  |
| `tpl_bank_account_account_number` | Account number | অ্যাকাউন্ট নম্বর |  |
| `tpl_bank_account_account_type` | Account type | অ্যাকাউন্টের ধরন |  |
| `tpl_bank_account_bank_name` | Bank name | ব্যাঙ্কের নাম |  |
| `tpl_bank_account_branch` | Branch | শাখা |  |
| `tpl_bank_account_customer_id` | Customer id | গ্রাহক ID |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC কোড |  |
| `tpl_bank_account_login_password` | Login password | লগইন পাসওয়ার্ড |  |
| `tpl_bank_account_micr` | MICR code | MICR কোড |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | নেট-ব্যাঙ্কিং ইউজার ID |  |
| `tpl_bank_account_nominee` | Nominee | নমিনি |  |
| `tpl_bank_account_notes` | Notes | নোট |  |
| `tpl_bank_account_profile_password` | Profile password | প্রোফাইল পাসওয়ার্ড |  |
| `tpl_bank_account_registered_email` | Registered email | নথিভুক্ত ইমেল |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | নথিভুক্ত মোবাইল |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | লেনদেন পাসওয়ার্ড |  |
| `tpl_card` | Card | কার্ড |  |
| `tpl_card_atm_pin` | ATM PIN | ATM পিন |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | বিলিং সাইকেলের দিন |  |
| `tpl_card_card_network` | Card network | নেটওয়ার্ক |  |
| `tpl_card_card_number` | Card number | কার্ড নম্বর |  |
| `tpl_card_card_portal_login` | Card portal login | কার্ড পোর্টাল লগইন |  |
| `tpl_card_card_portal_password` | Card portal password | কার্ড পোর্টাল পাসওয়ার্ড |  |
| `tpl_card_card_type` | Card type | কার্ডের ধরন |  |
| `tpl_card_card_variant` | Card variant | কার্ড ভ্যারিয়েন্ট |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | মেয়াদ |  |
| `tpl_card_linked_account` | Linked account | যুক্ত অ্যাকাউন্ট |  |
| `tpl_card_name_on_card` | Name on card | কার্ডে নাম |  |
| `tpl_card_notes` | Notes | নোট |  |
| `tpl_demat` | Demat | ডিম্যাট |  |
| `tpl_demat_api_key` | API key | API কী |  |
| `tpl_demat_api_secret` | API secret | API সিক্রেট |  |
| `tpl_demat_bo_id` | BO ID | BO ID |  |
| `tpl_demat_broker` | Broker | ব্রোকার |  |
| `tpl_demat_cdsl_tpin` | CDSL TPIN | CDSL TPIN |  |
| `tpl_demat_client_id` | Client id | ক্লায়েন্ট ID |  |
| `tpl_demat_depository` | Depository | ডিপোজিটরি |  |
| `tpl_demat_dp_id` | DP ID | DP ID |  |
| `tpl_demat_login_password` | Login password | লগইন পাসওয়ার্ড |  |
| `tpl_demat_mf_folios` | Mutual fund folios | মিউচুয়াল ফান্ড ফোলিও |  |
| `tpl_demat_mpin` | MPIN | MPIN |  |
| `tpl_demat_nominee` | Nominee | নমিনি |  |
| `tpl_demat_notes` | Notes | নোট |  |
| `tpl_digilocker` | Digilocker | DigiLocker |  |
| `tpl_digilocker_login_username` | Login username | ইউজারনেম |  |
| `tpl_digilocker_notes` | Notes | নোট |  |
| `tpl_digilocker_portal_password` | Portal password | পাসওয়ার্ড |  |
| `tpl_digilocker_security_pin` | Security pin | নিরাপত্তা পিন |  |
| `tpl_driving_license` | Driving License | ড্রাইভিং লাইসেন্স |  |
| `tpl_driving_license_dl_number` | Dl number | লাইসেন্স নম্বর |  |
| `tpl_driving_license_dob` | Dob | জন্ম তারিখ |  |
| `tpl_driving_license_expiry_date` | Expiry date | এই তারিখ পর্যন্ত বৈধ |  |
| `tpl_driving_license_file_copy` | Scanned copy | স্ক্যান করা নকল |  |
| `tpl_driving_license_issue_date` | Issue date | ইস্যুর তারিখ |  |
| `tpl_driving_license_name_on_dl` | Name on dl | লাইসেন্সে নাম |  |
| `tpl_driving_license_notes` | Notes | নোট |  |
| `tpl_driving_license_rto_location` | Rto location | RTO |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | গাড়ির শ্রেণি |  |
| `tpl_epf_pension` | Epf Pension | EPF / পেনশন |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | যুক্ত মোবাইল |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | EPF-এ নাম |  |
| `tpl_epf_pension_nominee` | Nominee | নমিনি |  |
| `tpl_epf_pension_notes` | Notes | নোট |  |
| `tpl_epf_pension_password` | Password | পাসওয়ার্ড |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | PF সদস্য ID |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO পাসওয়ার্ড |  |
| `tpl_epf_pension_scheme` | Scheme | প্রকল্প |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | সরকারি পরিচয়পত্র |  |
| `tpl_gov_id_expiry` | Expiry | মেয়াদ |  |
| `tpl_gov_id_file_copy` | Scanned copy | স্ক্যান করা নকল |  |
| `tpl_gov_id_id_kind` | ID type | পরিচয়পত্রের ধরন |  |
| `tpl_gov_id_id_number` | ID number | পরিচয় নম্বর |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | পত্র অনুযায়ী নাম |  |
| `tpl_gov_id_notes` | Notes | নোট |  |
| `tpl_gov_id_portal_login` | Portal login | পোর্টাল লগইন |  |
| `tpl_gov_id_portal_password` | Portal password | পোর্টাল পাসওয়ার্ড |  |
| `tpl_insurance` | Insurance | বিমা |  |
| `tpl_insurance_agent_contact` | Agent contact | এজেন্টের যোগাযোগ |  |
| `tpl_insurance_commencement_date` | Commencement date | শুরুর তারিখ |  |
| `tpl_insurance_insurer` | Insurer | বিমা কোম্পানি |  |
| `tpl_insurance_maturity_date` | Maturity date | মেয়াদপূর্তির তারিখ |  |
| `tpl_insurance_nominee` | Nominee | নমিনি |  |
| `tpl_insurance_notes` | Notes | নোট |  |
| `tpl_insurance_policy_number` | Policy number | পলিসি নম্বর |  |
| `tpl_insurance_policy_term` | Policy term | পলিসির মেয়াদ |  |
| `tpl_insurance_policy_type` | Policy type | পলিসির ধরন |  |
| `tpl_insurance_portal_login` | Portal login | পোর্টাল লগইন |  |
| `tpl_insurance_portal_password` | Portal password | পোর্টাল পাসওয়ার্ড |  |
| `tpl_insurance_premium_amount` | Premium amount | প্রিমিয়ামের অঙ্ক |  |
| `tpl_insurance_premium_due_date` | Premium due date | প্রিমিয়ামের তারিখ |  |
| `tpl_insurance_premium_mode` | Premium mode | প্রিমিয়াম কীভাবে দেন |  |
| `tpl_insurance_sum_assured` | Sum assured | বিমার অঙ্ক |  |
| `tpl_login` | Login | লগইন |  |
| `tpl_login_notes` | Notes | নোট |  |
| `tpl_login_password` | Password | পাসওয়ার্ড |  |
| `tpl_login_recovery_codes` | Recovery codes | রিকভারি কোড |  |
| `tpl_login_username` | Username | ইউজারনেম |  |
| `tpl_login_website` | Website | ওয়েবসাইট |  |
| `tpl_pan_card` | Pan Card | PAN কার্ড |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | আধারের সঙ্গে যুক্ত |  |
| `tpl_pan_card_dob` | Dob | জন্ম তারিখ |  |
| `tpl_pan_card_e_filing_password` | E filing password | ই-ফাইলিং পাসওয়ার্ড |  |
| `tpl_pan_card_fathers_name` | Fathers name | বাবার নাম |  |
| `tpl_pan_card_file_copy` | Scanned copy | স্ক্যান করা নকল |  |
| `tpl_pan_card_name_on_pan` | Name on pan | PAN-এ নাম |  |
| `tpl_pan_card_notes` | Notes | নোট |  |
| `tpl_pan_card_pan_number` | Pan number | PAN |  |
| `tpl_passkey` | Passkey | পাসকি |  |
| `tpl_passkey_credential_id` | Credential ID | ক্রেডেনশিয়াল ID |  |
| `tpl_passkey_notes` | Notes | নোট |  |
| `tpl_passkey_private_key` | Private key | ব্যক্তিগত কী |  |
| `tpl_passkey_sign_count` | Sign count | সাইন কাউন্ট |  |
| `tpl_passkey_user_handle` | User handle | ইউজার হ্যান্ডেল |  |
| `tpl_passkey_username` | Username | ইউজারনেম |  |
| `tpl_passkey_website` | Website | ওয়েবসাইট |  |
| `tpl_passport` | Passport | পাসপোর্ট |  |
| `tpl_passport_dob` | Dob | জন্ম তারিখ |  |
| `tpl_passport_expiry_date` | Expiry date | মেয়াদ শেষের তারিখ |  |
| `tpl_passport_file_copy` | Scanned copy | স্ক্যান করা নকল |  |
| `tpl_passport_given_names` | Given names | দেওয়া নাম |  |
| `tpl_passport_issue_date` | Issue date | ইস্যুর তারিখ |  |
| `tpl_passport_notes` | Notes | নোট |  |
| `tpl_passport_passport_number` | Passport number | পাসপোর্ট নম্বর |  |
| `tpl_passport_place_of_issue` | Place of issue | ইস্যুর স্থান |  |
| `tpl_passport_portal_login` | Portal login | Passport Seva লগইন |  |
| `tpl_passport_portal_password` | Portal password | Passport Seva পাসওয়ার্ড |  |
| `tpl_passport_surname` | Surname | পদবি |  |
| `tpl_secure_note` | Secure Note | সুরক্ষিত নোট |  |
| `tpl_secure_note_attachment` | Attachment | সংযুক্তি |  |
| `tpl_secure_note_body` | Note | নোট |  |
| `tpl_shopping` | Shopping | শপিং অ্যাকাউন্ট |  |
| `tpl_shopping_gift_card_code` | Gift card code | গিফট কার্ড কোড |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | গিফট কার্ড পিন |  |
| `tpl_shopping_membership_id` | Membership id | সদস্যপদ ID |  |
| `tpl_shopping_notes` | Notes | নোট |  |
| `tpl_shopping_password` | Password | পাসওয়ার্ড |  |
| `tpl_shopping_registered_email` | Registered email | নথিভুক্ত ইমেল |  |
| `tpl_shopping_registered_mobile` | Registered mobile | নথিভুক্ত মোবাইল |  |
| `tpl_shopping_wallet_pin` | Wallet pin | ওয়ালেট পিন |  |
| `tpl_telecom` | Telecom | মোবাইল ও ইন্টারনেট |  |
| `tpl_telecom_account_number` | Account number | অ্যাকাউন্ট নম্বর |  |
| `tpl_telecom_circle` | Circle | সার্কেল |  |
| `tpl_telecom_mobile_number` | Mobile number | মোবাইল নম্বর |  |
| `tpl_telecom_notes` | Notes | নোট |  |
| `tpl_telecom_operator` | Operator | কোম্পানি |  |
| `tpl_telecom_plan_type` | Plan type | প্ল্যানের ধরন |  |
| `tpl_telecom_portal_password` | Portal password | পোর্টাল পাসওয়ার্ড |  |
| `tpl_telecom_puk` | PUK code | PUK কোড |  |
| `tpl_telecom_renewal_date` | Renewal date | রিচার্জের তারিখ |  |
| `tpl_telecom_sim_number` | Sim number | সিম নম্বর (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | সিম পিন |  |
| `tpl_transit` | Transit | ট্রানজিট পাস |  |
| `tpl_transit_login_password` | Login password | লগইন পাসওয়ার্ড |  |
| `tpl_transit_notes` | Notes | নোট |  |
| `tpl_transit_operator_name` | Operator name | কোম্পানি |  |
| `tpl_transit_registered_email` | Registered email | নথিভুক্ত ইমেল |  |
| `tpl_transit_registered_mobile` | Registered mobile | নথিভুক্ত মোবাইল |  |
| `tpl_transit_smart_card_number` | Smart card number | স্মার্ট কার্ড নম্বর |  |
| `tpl_transit_wallet_pin` | Wallet pin | ওয়ালেট পিন |  |
| `tpl_travel_booking` | Travel Booking | ভ্রমণ বুকিং |  |
| `tpl_travel_booking_account_username` | Account username | ইউজারনেম |  |
| `tpl_travel_booking_login_password` | Login password | লগইন পাসওয়ার্ড |  |
| `tpl_travel_booking_notes` | Notes | নোট |  |
| `tpl_travel_booking_provider` | Provider | কোম্পানি |  |
| `tpl_travel_booking_registered_email` | Registered email | নথিভুক্ত ইমেল |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | নথিভুক্ত মোবাইল |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | ওয়ালেট পিন |  |
| `tpl_upi` | UPI | UPI |  |
| `tpl_upi_app_name` | App name | UPI অ্যাপ |  |
| `tpl_upi_apps_used` | Apps used | কোন অ্যাপে চালু আছে |  |
| `tpl_upi_linked_account` | Linked account | যুক্ত অ্যাকাউন্ট |  |
| `tpl_upi_notes` | Notes | নোট |  |
| `tpl_upi_upi_id` | UPI ID | UPI ID |  |
| `tpl_upi_upi_pin` | UPI PIN | UPI পিন |  |
| `tpl_utility` | Utility | বিল ও সংযোগ |  |
| `tpl_utility_account_holder` | Account holder | অ্যাকাউন্টধারী |  |
| `tpl_utility_consumer_number` | Consumer number | গ্রাহক নম্বর |  |
| `tpl_utility_due_day` | Bill due day | বিল দেওয়ার দিন |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag ID |  |
| `tpl_utility_notes` | Notes | নোট |  |
| `tpl_utility_portal_login` | Portal login | পোর্টাল লগইন |  |
| `tpl_utility_portal_password` | Portal password | পোর্টাল পাসওয়ার্ড |  |
| `tpl_utility_provider` | Provider | পরিষেবা দেওয়া কোম্পানি |  |
| `tpl_utility_utility_kind` | Utility kind | কীসের বিল |  |
| `tpl_utility_vehicle_number` | Vehicle number | গাড়ির নম্বর |  |
| `tpl_utility_wifi_password` | Wi-Fi password | Wi-Fi পাসওয়ার্ড |  |
| `tpl_voter_id` | Voter Id | ভোটার পরিচয় |  |
| `tpl_voter_id_constituency` | Constituency | কেন্দ্র |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC নম্বর |  |
| `tpl_voter_id_file_copy` | Scanned copy | স্ক্যান করা নকল |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | ভোটার কার্ডে নাম |  |
| `tpl_voter_id_notes` | Notes | নোট |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP পাসওয়ার্ড |  |
| `tr_days_many` | %1$d days left | %1$d দিন বাকি |  |
| `tr_days_one` | %1$d day left | %1$d দিন বাকি |  |
| `tr_gone_today` | gone today | আজ চলে যাবে |  |
| `tr_restore` | Restore | ফিরিয়ে আনুন |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | তারপর সেগুলো চিরতরে চলে যায় — আর কোথাও কোনও নকল নেই। |  |
| `ui_hide_passphrase` | Hide passphrase | পাসফ্রেজ লুকান |  |
| `ui_label_count` | %1$s, %2$s | %1$s, %2$s |  |
| `ui_show_passphrase` | Show passphrase | পাসফ্রেজ দেখান |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | এই ফোনেই %1$dটি রেকর্ডের সঙ্গে মিলিয়ে দেখা হয়েছে। কিছুই কোথাও পাঠানো হয়নি। |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | এই ফোনেই %1$dটি রেকর্ডের সঙ্গে মিলিয়ে দেখা হয়েছে। কিছুই কোথাও পাঠানো হয়নি। |  |
| `vh_count_many` | %1$d things worth a look. | %1$dটি বিষয় দেখার মতো। |  |
| `vh_count_one` | %1$d thing worth a look. | %1$dটি বিষয় দেখার মতো। |  |
| `vh_empty` | No reused, weak or expiring credentials. | আবার ব্যবহার করা, দুর্বল বা শেষ হয়ে আসা তথ্য নেই। |  |
| `vh_kind_common` | Commonly guessed | সহজে আন্দাজ করা যায় |  |
| `vh_kind_expiring` | Expiring | শেষ হয়ে আসছে |  |
| `vh_kind_reused` | Reused password | আবার ব্যবহার করা পাসওয়ার্ড |  |
| `vh_kind_weak` | Weak | দুর্বল |  |
| `vh_no_kit_title` | No recovery kit saved | কোনও রিকভারি কিট সংরক্ষিত নেই |  |
| `vh_nothing` | Nothing to fix. | ঠিক করার কিছু নেই। |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | অফলাইন |  |
| `wl_chip_open` | Open source | ওপেন সোর্স |  |
| `wl_create` | Create a new vault | নতুন সিন্দুক তৈরি করুন |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | ইমেল নেই · অ্যাকাউন্ট নেই · কিছুই এই ফোনের বাইরে যায় না |  |
| `wl_head_1` | Your keys. | আপনার চাবি। |  |
| `wl_head_2` | Your device. | আপনার ফোন। |  |
| `wl_head_3` | No server. | কোনও সার্ভার নেই। |  |
| `wl_restore` | Restore from Recovery Kit | রিকভারি কিট থেকে ফিরিয়ে আনুন |  |
| `wl_sr_headline` | Your keys. Your device. No server. | আপনার চাবি। আপনার ফোন। কোনও সার্ভার নেই। |  |
