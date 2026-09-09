# Urdu (`ur`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-ur/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Urdu | ok? |
|---|---|---|---|
| `au_close` | Close | بند کریں |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | چنی گئی تصویر میں درست TOTP QR کوڈ نہیں ملا |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | خالی سے شروع کر کے اپنے خانوں کے نام خود رکھیں — ٹیمپلیٹ صرف لیبل بھرتے ہیں، ڈیٹا کبھی نہیں۔ |  |
| `hm_close_search` | Close search | تلاش بند کریں |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | اس فون سے کبھی باہر نہیں جاتا۔ محفوظ کرتے وقت انکرپٹ کیا جاتا ہے۔ |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | اب وہ فائل ہٹا دیں جو آپ نے درآمد کی تھی۔ وہ آپ کے پاس ورڈوں کی کھلی فہرست ہے، اور اب بھی آپ کے Downloads میں پڑی ہے۔ |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | سب کچھ صرف اسی فون پر ڈی کرپٹ ہوتا ہے۔ کچھ اپ لوڈ نہیں ہوتا، کیونکہ یہ ایپ نیٹ ورک کنکشن کھول ہی نہیں سکتی۔ |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | بینک کبھی آپ کا OTP نہیں مانگتے۔ جو مانگے، وہ ٹھگ ہے۔ |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | کوئی بینک افسر آپ سے اسکرین شیئر کرنے والی ایپ لگانے کو نہیں کہے گا۔ |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | آپ کا UPI پن صرف UPI ایپ کے کی پیڈ کے لیے ہے — فون پر کسی کو نہ بتائیں۔ |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | KYC ایک دن میں ختم نہیں ہوتی۔ ”آج KYC ختم“ والے پیغام ٹھگی ہیں۔ |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | پیسے لینے کے لیے نہ پن ڈالنا پڑتا ہے، نہ QR اسکین کرنا۔ |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | بجلی کٹنے کا SMS اور اس میں کسی کا ذاتی نمبر؟ وہ ٹھگی ہے۔ |  |
| `nav_close_menu` | Close menu | مینو بند کریں |  |
| `nfc_cannot_read` | Cannot read cards | کارڈ نہیں پڑھا جا سکتا |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | اس فون میں NFC نہیں ہے، اس لیے کارڈ نہیں پڑھا جا سکتا۔ |  |
| `ob_fact_lost_title` | If you lose your keys | اگر کنجیاں کھو جائیں |  |
| `ob_fact_network_note` | The app literally cannot phone home | یہ ایپ کہیں بھی رابطہ نہیں کر سکتی |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | بایو میٹرک سیکیورٹی چپ سے باہر کبھی نہیں جاتا |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | آپ نے کنجی اسی فولڈر میں رکھی ہے جو آپ کی انکرپٹڈ تجوری سنک کرتا ہے۔ اب جسے وہ فولڈر ملے گا اسے دونوں حصے مل جائیں گے۔ کنجی کہیں اور رکھیں — کاغذ، کوئی دوسرا کھاتہ، یا دراز۔ |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | یہ آپ کی تجوری فائل کے ساتھ ہی ہے |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH ریکوری |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | اسکین کریں یا لکھیں۔ دوبارہ انسٹال، فیکٹری ری سیٹ، یا فون کھونے کے بعد بھی کام کرتی ہے۔ |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | ابھی محفوظ کی گئی کٹ سے گروپ %1$d اور گروپ %2$d لکھیں۔ |  |
| `ob_kit_challenge_hint` | Group %1$d | گروپ %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | پہلے کٹ محفوظ کریں، پھر گروپ %1$d اور %2$d واپس لکھیں۔ |  |
| `ob_kit_challenge_title` | Check you actually have it | دیکھ لیں کہ کٹ واقعی آپ کے پاس ہے |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | یہ اوپر دی گئی کنجی سے میل نہیں کھاتا۔ |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | کوئی بھی — Zerokosh بھی — یہ میرے لیے واپس نہیں لا سکتا۔ |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "میں نے یہ آف لائن رکھ لی ہے۔ " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | ایک بار دکھتی ہے، کبھی کھلی شکل میں محفوظ نہیں کی جاتی۔ اندر آ سکتے ہوں تو سیٹنگز سے نئی بنا لیں۔ |  |
| `ob_kit_head_emph` | On paper. | کاغذ پر۔ |  |
| `ob_kit_head_lead` | "One key. " | "ایک کنجی۔ " |  |
| `ob_kit_head_tail` | " Never online." | " کبھی آن لائن نہیں۔" |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | نہ Gmail، نہ WhatsApp، نہ اسکرین شاٹ۔ الماری، بینک لاکر، یا اسٹیل کی پلیٹ۔ |  |
| `ob_kit_offline_title` | Keep it off the internet | اسے انٹرنیٹ سے دور رکھیں |  |
| `ob_kit_print` | Print | پرنٹ کریں |  |
| `ob_kit_print_note` | A printer, or Save as PDF | پرنٹر، یا PDF کے طور پر محفوظ |  |
| `ob_kit_qr` | QR image | QR تصویر |  |
| `ob_kit_qr_cd` | Recovery key QR code | ریکوری کنجی کا QR کوڈ |  |
| `ob_kit_qr_note` | To an offline gallery | آف لائن گیلری میں |  |
| `ob_kit_regenerate` | Regenerate | نئی بنائیں |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | یہ محفوظ نہیں ہوا۔ دوبارہ کوشش کریں، یا کوئی اور جگہ چنیں۔ |  |
| `ob_kit_save_pdf` | Save PDF | PDF محفوظ کریں |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | ایک صفحے کی چھپنے کے قابل کٹ |  |
| `ob_kit_saved` | I\'ve saved my kit | میں نے اپنی کٹ محفوظ کر لی ہے |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s میں محفوظ ہوا |  |
| `ob_kit_sent_to_printer` | Sent to the printer | پرنٹر کو بھیج دیا |  |
| `ob_kit_skip` | I\'ll do this later | یہ بعد میں کروں گا |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | آپ کی تجوری چلتی رہے گی۔ جب تک کٹ محفوظ نہیں ہوتی، Zerokosh یاد دلاتا رہے گا۔ |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | اسی فون پر بنی، صرف ایک بار نظر آئے گی۔ پاس فریز بھولنے پر اندر واپس آنے کا یہی ایک راستہ ہے۔ |  |
| `ob_kit_working` | Working… | کام جاری ہے… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | تجوری کے لیبل، ٹیمپلیٹ اور تنبیہات فوراً بدل جائیں گی۔ سیٹنگز میں کبھی بھی بدل سکتے ہیں۔ |  |
| `ob_pass_confirm` | Confirm | دوبارہ لکھیں |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | یہ ہم کبھی نہیں دیکھتے۔ کوئی ری سیٹ لنک نہیں۔ |  |
| `ob_pass_head_emph` | held only | صرف آپ کے پاس |  |
| `ob_pass_head_lead` | "One secret, " | "ایک ہی راز، " |  |
| `ob_pass_head_tail` | " by you." | ۔ |  |
| `ob_pass_no_match` | no match | میل نہیں کھاتا |  |
| `ob_pass_seal` | Seal the vault | تجوری بند کریں |  |
| `ob_pass_sealing` | Sealing… | بند کر رہے ہیں… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | بے جوڑ تین چار الفاظ ایک چالاک لفظ سے بہتر ہیں۔ اس اسکرین سے کچھ باہر نہیں جاتا۔ |  |
| `ob_pass_tab_passphrase` | Passphrase | پاس فریز |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 ہندسوں کا پن |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | آپ کی انگلی کا نشان فون کی سیکیورٹی چپ کے اندر رہتا ہے۔ وہ اس فون سے کبھی باہر نہیں جاتا۔ |  |
| `ob_trust_continue` | I understand · Continue | سمجھ گیا · آگے بڑھیں |  |
| `ob_trust_head_emph` | don\'t | نہیں معلوم |  |
| `ob_trust_head_lead` | "Exactly what we " | "ہمیں اصل میں کیا " |  |
| `ob_trust_head_tail` | " know." | ۔ |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | اگر پاس فریز بھول جائیں اور ریکوری کٹ بھی کھو جائے، تو تجوری بند ہی رہے گی — آپ کے لیے، ہمارے لیے، سب کے لیے۔ |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "کنجیاں آپ کے پاس۔ " |  |
| `ob_trust_stat_files` | .kosh file on device | فون پر ‎.kosh‎ فائل |  |
| `ob_trust_stat_servers` | servers contacted | سرور سے رابطہ |  |
| `ob_trust_stat_trackers` | trackers or SDKs | ٹریکر یا SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | یہ ایک بار پڑھ لیں۔ پوری سیکیورٹی کا ڈھانچہ یہی ہے، سیدھے الفاظ میں۔ |  |
| `ob_trust_tag_audited` | Audited build | آڈٹ شدہ بلڈ |  |
| `ob_trust_tag_reproducible` | Reproducible APK | دوبارہ بنائی جا سکنے والی APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | آپ انگلی سے کھول رہے ہیں۔ اگر کبھی انگلی کام کرنا بند کر دے، تو یہی آپ کو اندر لائے گا — اس لیے جانچ لینا بہتر ہے۔ |  |
| `pc_confirm` | Check | جانچیں |  |
| `pc_correct` | Still correct. Nothing to do. | اب بھی درست ہے۔ کچھ کرنے کی ضرورت نہیں۔ |  |
| `pc_forgot` | I cannot remember it | مجھے یاد نہیں آ رہا |  |
| `pc_later` | Not now | ابھی نہیں |  |
| `pc_reset_action` | Set new passphrase | نیا پاس فریز رکھیں |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | آپ کی انگلی یہ تجوری کھول سکتی ہے، اس لیے وہی نیا پاس فریز بھی رکھ سکتی ہے — ریکوری کٹ کی ضرورت نہیں۔ تصدیق کے لیے ایک بار پھر پوچھا جائے گا۔ |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | پاس فریز بدل گیا۔ جلدی ان لاک دوبارہ لگ گیا۔ |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | یہ نہیں ہو سکا۔ آپ کا پرانا پاس فریز ہی چل رہا ہے۔ |  |
| `pc_reset_title` | Set a new passphrase | نیا پاس فریز رکھیں |  |
| `pc_title` | Do you still remember your passphrase? | کیا آپ کو اب بھی اپنا پاس فریز یاد ہے؟ |  |
| `pc_wrong` | That is not it. You can set a new one instead. | یہ وہ نہیں۔ اس کے بجائے نیا رکھ سکتے ہیں۔ |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | اس اندراج کے لیے یاد رکھی گئی %1$d قدریں ہٹا دی جائیں گی۔ یہ واپس نہیں ہو سکتا۔ |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh وہ لاگ اِن محفوظ نہ کر سکا۔ |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | بھرنے کے لیے Zerokosh کھولیں |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | یہی ایک پاس فریز سب کچھ بند رکھتا ہے۔ کوئی لمبا سا جملہ چنیں جو صرف آپ جانتے ہوں۔ |  |
| `scr_create_button` | Lock it in | بند کر دیں |  |
| `scr_create_confirm_hint` | Type it again | دوبارہ لکھیں |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | پاس فریز (کم از کم 10 حروف) |  |
| `scr_create_mismatch` | The two entries don\'t match | دونوں ایک جیسے نہیں ہیں |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 ہندسوں کا پن |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | اس کے بجائے 6 ہندسوں کا پن رکھیں |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | پن کے لیے انگلی یا چہرہ ان لاک والا فون چاہیے۔ براہِ کرم پاس فریز چنیں۔ |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | پن کی اجازت اس لیے ہے کہ یہ فون اسے اپنی سیکیورٹی چپ اور آپ کی انگلی یا چہرے سے محفوظ رکھتا ہے۔ |  |
| `scr_create_strength_fair` | Fair | ٹھیک ٹھاک |  |
| `scr_create_strength_good` | Good | اچھا |  |
| `scr_create_strength_strong` | Strong | مضبوط |  |
| `scr_create_strength_weak` | Weak | کمزور |  |
| `scr_create_title` | Create your passphrase | اپنا پاس فریز بنائیں |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | کم از کم 10 حروف چاہییں — جتنا لمبا، اتنا مضبوط |  |
| `scr_create_working` | Preparing your vault… | آپ کی تجوری تیار ہو رہی ہے… |  |
| `scr_detail_delete` | Delete | ہٹائیں |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | یہ 30 دن ”حال ہی میں ہٹائے گئے“ میں رہے گا، اور آپ کے دوسرے فونوں سے سنک ہونے پر چلا جائے گا۔ |  |
| `scr_detail_delete_confirm_title` | Delete this record? | یہ اندراج ہٹا دیں؟ |  |
| `scr_detail_delete_confirm_yes` | Delete | ہٹائیں |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | سیکریٹ یا otpauth:// لنک پیسٹ کریں |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | اپنی انگلی یا چہرہ استعمال کریں |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh کھولیں |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | کئی بار غلط کوشش ہوئی۔ %1$d سیکنڈ انتظار کریں۔ |  |
| `scr_lock_hint` | Passphrase | پاس فریز |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | یہ ریکوری کنجی ٹھیک نہیں — ہر حرف ملا کر دیکھیں |  |
| `scr_lock_title` | Vault is locked | تجوری بند ہے |  |
| `scr_lock_unlock` | Unlock | کھولیں |  |
| `scr_lock_use_passphrase` | Use passphrase | پاس فریز استعمال کریں |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | براہِ کرم ایک بار پاس فریز سے کھولیں |  |
| `scr_lock_use_recovery` | Use Recovery Key | ریکوری کنجی استعمال کریں |  |
| `scr_lock_wrong` | Wrong passphrase | پاس فریز غلط ہے |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | اگر کبھی پاس فریز بھول جائیں تو اندر واپس آنے کا یہی ایک راستہ ہے۔ ہم اسے ری سیٹ نہیں کر سکتے — کوئی بھی نہیں کر سکتا۔ |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | میں نے اسے لکھ کر محفوظ جگہ رکھ لیا ہے |  |
| `scr_recovery_done` | Continue | آگے بڑھیں |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | یہ کنجی ایک ہی بار دکھتی ہے۔ جب تک آپ تجوری کھول سکتے ہیں، سیٹنگز سے کبھی بھی نئی بنا سکتے ہیں۔ |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | یہ کاغذ اپنی جائیداد کے کاغذات یا دوسری اہم دستاویزات کے ساتھ رکھیں۔ جس کے پاس یہ کنجی ہے وہ آپ کی تجوری کھول سکتا ہے — اسے لاکر کی چابی کی طرح سنبھالیں۔ |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | ریکوری کٹ PDF محفوظ ہو گئی |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh ریکوری کٹ |  |
| `scr_recovery_save_pdf` | Save as PDF | PDF کے طور پر محفوظ کریں |  |
| `scr_recovery_title` | Your Recovery Key | آپ کی ریکوری کنجی |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | آپ جو کچھ محفوظ کرتے ہیں وہ آپ کے فون کی ایک بند فائل میں رہتا ہے۔ وہ کبھی ہم تک نہیں آتا — اسے رکھنے کی ہمارے پاس جگہ ہی نہیں۔ |  |
| `scr_trust_card1_title` | Your data stays on this device | آپ کا ڈیٹا اسی فون میں رہتا ہے |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | نہ کوئی Zerokosh اکاؤنٹ، نہ کلاؤڈ، نہ سائن اپ۔ اسے صرف آپ کھول سکتے ہیں۔ ہم بھی نہیں۔ |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | ہمارے سرور نہیں — نہ ہیک کرنے کو کچھ، نہ بیچنے کو |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | نہ سبسکرپشن، نہ اشتہار۔ کوئی بھی ہمارا کوڈ پڑھ کر ہماری ہر بات پرکھ سکتا ہے۔ |  |
| `scr_trust_card3_title` | Free forever, open source | ہمیشہ مفت، اوپن سورس |  |
| `scr_trust_continue` | Continue | آگے بڑھیں |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | آپ کی تبدیلیاں محفوظ نہیں ہوئیں، اس لیے تجوری میں جو پہلے تھا اس میں سے کچھ نہیں کھویا۔ |  |
| `st_recently_deleted` | Recently deleted | حال ہی میں ہٹائے گئے |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | ایک بار کا کوڈ (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | ایک بار کا کوڈ (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | ایک بار کا کوڈ (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA سیکریٹ |  |
| `tr_cannot_undo` | This cannot be undone. | یہ واپس نہیں ہو سکتا۔ |  |
| `tr_delete_all` | Delete all permanently | سب ہمیشہ کے لیے ہٹائیں |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d اندراجات ہمیشہ کے لیے چلے جائیں گے۔ یہ واپس نہیں ہو سکتا، اور واپس لانے کے لیے کوئی بیک اپ نہیں۔ |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d اندراج ہمیشہ کے لیے چلا جائے گا۔ یہ واپس نہیں ہو سکتا، اور واپس لانے کے لیے کوئی بیک اپ نہیں۔ |  |
| `tr_delete_all_title` | Delete everything in the trash? | ردی کا سب کچھ ہٹا دیں؟ |  |
| `tr_delete_now` | Delete now | ابھی ہٹائیں |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | ”%1$s“ ہمیشہ کے لیے ہٹا دیں؟ |  |
| `tr_empty` | Nothing deleted. | کچھ نہیں ہٹایا گیا۔ |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | ہٹائے گئے اندراج یہاں %1$d دن رہتے ہیں۔ |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | بھارتی بینکوں، UPI، کارڈوں، ڈی میٹ، EPF، اور ان OTP ایپس کے لیے جو آپ واقعی استعمال کرتے ہیں — فون میں ہی رہنے والی تجوری۔ |  |

## Priority 2 — longer prose

| key | English | Urdu | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | اس ایک چیز سے شروع کریں جو سب سے زیادہ کام آئے۔ نوٹس ایپ میں پڑے بارہ پاس ورڈوں سے ایک محفوظ کیا ہوا پاس ورڈ زیادہ محفوظ ہے۔ |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | پاس فریز بھول جائیں تو ریکوری کٹ ہی اندر آنے کا راستہ ہے۔ کوئی آپ کے لیے دوسری نہیں بنا سکتا۔ |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | اس فائل میں پہچاننے کے قابل کچھ نہیں ملا۔ Chrome، Google Password Manager، Bitwarden، LastPass اور KeePass کی برآمدات سمجھ آتی ہیں۔ |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d موجودہ اندراجات بدلیں گے — سائٹ اور یوزر نیم ملا کر۔ بدلے ہوئے پاس ورڈ ہر اندراج کی تاریخ میں مل جائیں گے۔ |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d موجودہ اندراج بدلے گا — سائٹ اور یوزر نیم ملا کر۔ بدلے ہوئے پاس ورڈ ہر اندراج کی تاریخ میں مل جائیں گے۔ |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d اندراج دونوں طرف بدلے ہوئے تھے۔ دونوں شکلیں محفوظ کر لی گئیں — ”(conflict copy)“ تلاش کریں۔ |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | وہ پاس فریز ڈالیں جو اس بیک اپ فائل کو کھولتا ہے۔ وہ آپ کے موجودہ سے مختلف ہو سکتا ہے۔ |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | ‎.kosh‎ فائل کا Poly1305 تصدیقی ٹیگ میل نہیں کھاتا۔ یہ بیچ میں رکے سنک یا خراب اسٹوریج کے بعد ہو سکتا ہے۔ |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh تجوری فائل کے ساتھ ایک چلتا ہوا بیک اپ رکھتا ہے۔ اسے اپنے سنک فولڈر سے واپس لائیں، یا کسی دوسرے فون پر ریکوری کٹ سے تجوری کھولیں۔ |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | جب تک پڑھا نہ جائے، کارڈ فون کی پشت سے سیدھا لگا کر رکھیں۔ اس سے کارڈ نمبر، میعاد اور نام ملتا ہے — CVV چپ میں نہیں ہوتا، وہ آپ کو خود لکھنا پڑے گا۔ |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | اندر واپس آنے کا جلدی راستہ چنیں۔ تجوری کی حفاظت پاس فریز ہی کرتا ہے؛ یہ صرف اسی فون پر کنجی کھولتا ہے۔ |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | اب آپ کی تجوری میں اصل معلومات ہیں۔ ریکوری کٹ کے بغیر پاس فریز بھولنے پر کوئی آپ کو واپس اندر نہیں لا سکتا — ہم بھی نہیں۔ |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh مفت اور اوپن سورس ہے، اور اس کے کوئی سرور نہیں۔ آپ کی تجوری صرف آپ کھول سکتے ہیں۔ ہم بھی نہیں۔ |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | کیمرے کی اجازت صرف QR کوڈ اسکین کرنے کے لیے چاہیے۔ اندراج شامل کرتے وقت سیکریٹ ہاتھ سے بھی پیسٹ کر سکتے ہیں۔ |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | ان بینک ایپس کے لیے جو آٹو فِل نہیں ہونے دیتیں — بٹن دبا کر لاگ اِن کی تفصیلات ایک ایک کر کے کاپی کریں |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | جیسے آپ فون کھولتے ہیں، ویسے ہی تجوری۔ پاس فریز ہمیشہ کام کرتا رہے گا۔ |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | تجوری کے اسکرین شاٹ کلاؤڈ فوٹو بیک اپ تک پہنچ سکتے ہیں۔ صرف بہت ضرورت ہو تو چالو کریں۔ |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | تجوری فائل لکھی نہ جا سکی۔ اگر آپ نے بیک اپ اور سنک فولڈر لگایا ہوا ہے، تو ہو سکتا ہے Android نے اس کی اجازت واپس لے لی ہو — سیٹنگز کھولیں، فولڈر دوبارہ چنیں، اور پھر کوشش کریں۔ |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | آپ کی انکرپٹ شدہ ‎.kosh‎ فائلیں سیدھے اسی فولڈر میں محفوظ ہوتی ہیں۔ کئی ڈیوائسوں پر خود بخود بیک اپ کے لیے اس فولڈر کو Google Drive، Syncthing، Nextcloud یا SD کارڈ سے سنک کریں۔ |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | نئی ریکوری کنجی بنانے کے لیے اپنا پاس فریز ڈالیں۔ پرانی کنجی کام کرنا بند کر دے گی۔ |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | اس صفحے کی باقی ہر بات ایک لاگ اِن کمزور کرتی ہے۔ یہ پوری تجوری لے جا سکتی ہے۔ سیٹنگز ← نئی ریکوری کنجی لیں۔ |  |

## Priority 3 — short labels

| key | English | Urdu | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | مضبوط پاس ورڈ استعمال کریں |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | اکاؤنٹ کا نام (جیسے Google) |  |
| `au_active_many` | %1$d active codes | %1$d فعال کوڈ |  |
| `au_active_one` | %1$d active code | %1$d فعال کوڈ |  |
| `au_add_another` | Add another authenticator | ایک اور تصدیق کار شامل کریں |  |
| `au_add_secret` | Add Secret Key | سیکریٹ کنجی شامل کریں |  |
| `au_camera_needed` | Camera permission needed to scan QR code | QR کوڈ اسکین کرنے کے لیے کیمرے کی اجازت چاہیے |  |
| `au_copied` | Copied · clears shortly | کاپی ہو گیا · تھوڑی دیر میں مٹ جائے گا |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google، GitHub یا اپنے بروکر کا QR اسکین کریں، یا سیکریٹ کنجی ہاتھ سے لکھیں۔ |  |
| `au_enter_key` | Enter Key | کنجی لکھیں |  |
| `au_fallback_name` | Authenticator | تصدیق کار |  |
| `au_flashlight` | Flashlight | ٹارچ |  |
| `au_grant` | Grant Permission | اجازت دیں |  |
| `au_image_failed` | Failed to process image | تصویر نہ پڑھی جا سکی |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | غلط Base32 سیکریٹ کنجی (صرف A-Z حروف اور 2-7 ہندسے) |  |
| `au_no_match` | No codes match | کوئی کوڈ نہیں ملا |  |
| `au_none_yet` | No codes yet. | ابھی کوئی کوڈ نہیں۔ |  |
| `au_pick_image` | Pick Image | تصویر چنیں |  |
| `au_rotating` | "Rotating " | "بدلتے رہنے والے " |  |
| `au_rotating_emph` | codes. | کوڈ۔ |  |
| `au_save_key` | Save Key | کنجی محفوظ کریں |  |
| `au_scan_qr` | Scan a QR code | QR کوڈ اسکین کریں |  |
| `au_scan_title` | Scan Authenticator QR | تصدیق کار کا QR اسکین کریں |  |
| `au_search_hint` | Search codes, issuers… | کوڈ یا دینے والے تلاش کریں… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | جیسے JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | سیکریٹ کنجی (Base32) |  |
| `au_tap_to_copy` | Tap to copy | کاپی کرنے کے لیے ٹیپ کریں |  |
| `cat_apps` | Apps &amp; Logins | ایپس اور لاگ اِن |  |
| `cat_banks` | Banks &amp; UPI | بینک اور UPI |  |
| `cat_cards` | Cards | کارڈ |  |
| `cat_govid` | Gov &amp; ID | سرکاری اور شناخت |  |
| `cat_investments` | Investments | سرمایہ کاری |  |
| `cat_utilities` | Utilities | بل اور کنکشن |  |
| `cd_mask_hidden` | hidden | چھپا ہوا |  |
| `cd_shield_high_sensitivity` | extra-protected field | زیادہ حساس معلومات |  |
| `gl_blank` | Blank template | خالی ٹیمپلیٹ |  |
| `gl_cat_apps` | Apps | ایپس |  |
| `gl_cat_banks` | Banks | بینک |  |
| `gl_cat_cards` | Cards | کارڈ |  |
| `gl_cat_demat` | Demat | ڈی میٹ |  |
| `gl_cat_govid` | Gov ID | سرکاری شناخت |  |
| `gl_cat_popular` | Popular | مقبول |  |
| `gl_cat_shopping` | Shopping | خریداری |  |
| `gl_cat_travel` | Travel | سفر |  |
| `gl_cat_upi` | UPI | UPI |  |
| `gl_cat_utilities` | Utilities | بل |  |
| `gl_head_emph` | storing? | محفوظ کر رہے ہیں؟ |  |
| `gl_head_lead` | "What are we " | "ہم کیا " |  |
| `gl_matches` | %1$d matches | %1$d ملے |  |
| `gl_most_used` | Most-used first | سب سے زیادہ استعمال ہونے والے پہلے |  |
| `gl_not_found` | Can’t find a service? | خدمت نہیں مل رہی؟ |  |
| `gl_search` | Search %1$d Indian services… | %1$d بھارتی خدمات میں تلاش کریں… |  |
| `gl_suggested` | Suggested for you | آپ کے لیے تجویز |  |
| `hm_add_first` | Add your first record | اپنا پہلا اندراج شامل کریں |  |
| `hm_all_offline` | all offline. | سب آف لائن۔ |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d چیزیں، " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d چیز، " |  |
| `hm_detail_placeholder` | Pick a record to see it here | یہاں دیکھنے کے لیے ایک ریکارڈ منتخب کریں |  |
| `hm_empty_blank` | A blank vault, ready. | خالی تجوری، تیار۔ |  |
| `hm_empty_head_emph` | waiting. | انتظار میں ہے۔ |  |
| `hm_empty_head_lead` | "Your vault is " | "آپ کی تجوری " |  |
| `hm_filter_all` | All | سب |  |
| `hm_import_backup` | Import an encrypted backup | انکرپٹ شدہ بیک اپ درآمد |  |
| `hm_import_backup_note` | Open a .kosh file from this device | اسی فون سے ‎.kosh‎ فائل کھولیں |  |
| `hm_inst_many` | %1$d institutions | %1$d ادارے |  |
| `hm_inst_one` | %1$d institution | %1$d ادارہ |  |
| `hm_kit_banner_action` | Save one now | ابھی محفوظ کریں |  |
| `hm_kit_banner_dismiss` | Remind me later | بعد میں یاد دلائیں |  |
| `hm_kit_banner_title` | No recovery kit saved | کوئی ریکوری کٹ محفوظ نہیں |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | کھلی ہے · چھوڑتے ہی بند ہو گی |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | کھلی ہے · چھوڑنے کے %1$d منٹ بعد بند |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | کھلی ہے · چھوڑنے کے 1 منٹ بعد بند |  |
| `hm_no_match` | Nothing matches “%1$s” | ”%1$s“ کے لیے کچھ نہیں ملا |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | کوئی ادارہ، UPI ہینڈل، یا آخری چار ہندسے آزمائیں۔ |  |
| `hm_pinned` | Pinned | پن کیے گئے |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC، priya@upi، PAN… تلاش کریں |  |
| `hm_start_template` | Start with a template | ٹیمپلیٹ سے شروع کریں |  |
| `ic_could_not` | Could not import | درآمد نہ ہو سکا |  |
| `ic_done` | Done | ہو گیا |  |
| `ic_import` | Import | درآمد |  |
| `ic_imported` | Imported | درآمد ہو گیا |  |
| `ic_importing` | Importing… | درآمد ہو رہا ہے… |  |
| `ic_new_many` | %1$d new logins. | %1$d نئے لاگ اِن۔ |  |
| `ic_new_one` | %1$d new login. | %1$d نیا لاگ اِن۔ |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d شامل ہوئے، %2$d بدلے۔ |  |
| `ic_title` | Import from %1$s? | %1$s سے درآمد کریں؟ |  |
| `ic_too_large` | That file is too large to be a credential export. | یہ فائل پاس ورڈ برآمد ہونے کے لیے بہت بڑی ہے۔ |  |
| `import_action` | Import | درآمد |  |
| `import_locked` | Unlock your vault before importing. | درآمد کرنے سے پہلے اپنی تجوری کھولیں۔ |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d شامل ہوئے، %2$d بدلے۔ کچھ مٹایا نہیں گیا۔ |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | وہ فائل Zerokosh تجوری کے طور پر پڑھی نہ جا سکی۔ |  |
| `import_nothing_new` | Everything in that backup was already here. | اس بیک اپ میں جو تھا، وہ سب پہلے ہی یہاں تھا۔ |  |
| `import_passphrase_label` | Backup passphrase | بیک اپ کا پاس فریز |  |
| `import_title` | Import a backup | بیک اپ درآمد کریں |  |
| `kicker_locked` | Locked | بند ہے |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · کچھ بھی اس فون سے باہر نہیں گیا |  |
| `lk_touch_unlock` | Touch to unlock | کھولنے کے لیے چھوئیں |  |
| `lk_welcome_emph` | Your vault is sealed. | آپ کی تجوری بند ہے۔ |  |
| `lk_welcome_lead` | Welcome back. | خوش آمدید۔ |  |
| `msg_auth_needed` | Confirm it\'s you to see this | دیکھنے کے لیے تصدیق کریں کہ یہ آپ ہی ہیں |  |
| `msg_back` | Back | پیچھے |  |
| `msg_cancel` | Cancel | منسوخ |  |
| `msg_file_damaged` | File damaged — restored from backup | فائل خراب تھی — بیک اپ سے ٹھیک کر دی گئی |  |
| `msg_ok` | OK | ٹھیک ہے |  |
| `msg_saved` | Saved | محفوظ ہو گیا |  |
| `nav_all_templates` | All templates | سارے ٹیمپلیٹ |  |
| `nav_damaged_emph` | vault file | تجوری فائل میں |  |
| `nav_damaged_kicker` | Damaged state | خراب حالت |  |
| `nav_damaged_lead` | "Something in the " | "آپ کی " |  |
| `nav_damaged_tail` | " is off." | " کچھ گڑبڑ ہے۔" |  |
| `nav_integrity_title` | Integrity check failed | سالمیت کی جانچ ناکام ہوئی |  |
| `nav_scan` | Scan | اسکین |  |
| `nav_tap_card` | Tap a card | کارڈ ٹیپ کریں |  |
| `nav_what_next` | What to do next | اب آگے کیا |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC بند ہے۔ سیٹنگز میں چالو کر کے دوبارہ کوشش کریں۔ |  |
| `nfc_hold_card` | Hold your card to the phone | کارڈ فون سے لگا کر رکھیں |  |
| `nfc_missed` | Did not catch that | پکڑ میں نہیں آیا |  |
| `nfc_read_failed` | That card could not be read. Try again. | وہ کارڈ نہ پڑھا جا سکا۔ دوبارہ کوشش کریں۔ |  |
| `nfc_reading` | Reading… | پڑھ رہے ہیں… |  |
| `nfc_try_again` | Try again | دوبارہ کوشش کریں |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · اسی فون پر ناپا گیا |  |
| `ob_argon_faster` | Faster unlock | جلدی کھلے گی |  |
| `ob_argon_harder` | Harder to attack | توڑنا مشکل |  |
| `ob_argon_measuring` | Measuring this device… | یہ فون ناپ رہے ہیں… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id سختی |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | لغت کا ایک لفظ بھی نہیں |  |
| `ob_check_pass_length` | 10 characters or more | 10 یا زیادہ حروف |  |
| `ob_check_pass_reuse` | Not reused from another app | کسی اور ایپ سے دوبارہ استعمال نہیں کیا |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | نہ سالگرہ، نہ سالانہ تاریخ |  |
| `ob_check_pin_digits` | All six digits entered | چھ ہندسے پورے بھرے |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | نہ لگاتار ہندسے، نہ دہرائے ہوئے |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~توڑنے میں %1$d صدیاں |  |
| `ob_crack_days` | ~%1$d days to crack | ~توڑنے میں %1$d دن |  |
| `ob_crack_forever` | longer than the sun | سورج سے بھی زیادہ عرصہ |  |
| `ob_crack_hours` | ~hours to crack | ~توڑنے میں چند گھنٹے |  |
| `ob_crack_seconds` | ~seconds to crack | ~توڑنے میں چند سیکنڈ |  |
| `ob_crack_years` | ~%1$d years to crack | ~توڑنے میں %1$d سال |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | تصدیق شدہ، ہر تجوری کے لیے الگ نونس |  |
| `ob_fact_encryption_title` | Encryption | انکرپشن |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | سیٹ اپ کے وقت آپ کے فون پر ناپا گیا |  |
| `ob_fact_kdf_title` | Key stretching | کنجی اسٹریچنگ |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | نہ ری سیٹ لنک۔ نہ سپورٹ کا پچھلا دروازہ۔ |  |
| `ob_fact_lost_value` | Nobody can recover it | کوئی واپس نہیں لا سکتا |  |
| `ob_fact_network_title` | Network permission | نیٹ ورک کی اجازت |  |
| `ob_fact_network_value` | Not requested | کبھی مانگی ہی نہیں |  |
| `ob_fact_quick_title` | Quick unlock | جلدی ان لاک |  |
| `ob_fact_quick_value` | Hardware keystore | ہارڈ ویئر کی اسٹور |  |
| `ob_lang_continue` | Continue in %1$s | %1$s میں آگے بڑھیں |  |
| `ob_lang_head_emph` | language. | زبان چنیں۔ |  |
| `ob_lang_head_lead` | "Choose your " | "اپنی " |  |
| `ob_lang_search` | Search %1$d languages | %1$d زبانوں میں تلاش کریں |  |
| `ob_quick_continue_pass` | Continue with passphrase | پاس فریز کے ساتھ آگے بڑھیں |  |
| `ob_quick_enable` | Enable quick unlock | جلدی ان لاک چالو کریں |  |
| `ob_quick_fingerprint` | Fingerprint | انگلی کا نشان |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | تیز، ہارڈ ویئر سے محفوظ ان لاک۔ |  |
| `ob_quick_head_emph` | Without the cloud. | بغیر کلاؤڈ کے۔ |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "ایک چھو سے کھلے۔ " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox۔ کوئی بایو میٹرک معلومات کبھی Zerokosh تک نہیں پہنچتی۔ |  |
| `ob_quick_hw_title` | Hardware-backed | ہارڈ ویئر سے محفوظ |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | اس فون میں ہارڈ ویئر سینسر نہیں ہے۔ |  |
| `ob_quick_opening` | Opening your vault… | آپ کی تجوری کھل رہی ہے… |  |
| `ob_quick_pass_only` | Passphrase only | صرف پاس فریز |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | ہر بار لکھیں۔ سب سے محفوظ۔ |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | جلدی ان لاک نہیں لگ سکا۔ دوبارہ کوشش کریں، یا پاس فریز کے ساتھ ہی آگے بڑھیں۔ |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | ابھی نہیں — میں پاس فریز لکھ لوں گا |  |
| `ob_quick_touch_title` | Touch the sensor | سینسر کو چھوئیں |  |
| `ob_recommended` | Recommended | تجویز |  |
| `ob_reveal_hide` | Hide | چھپائیں |  |
| `ob_reveal_show` | Show | دکھائیں |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | لپیٹنے والی کنجی ہارڈ ویئر کی اسٹور میں رہے گی۔ بایو میٹرک اگلے مرحلے میں۔ |  |
| `ob_seal_title` | Seal to this device | اسی فون سے باندھیں |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | اس فون میں ہارڈ ویئر بایو میٹرک نہیں ہے۔ |  |
| `ob_soon` | SOON | جلد |  |
| `ob_step_label` | Step %1$d of 6 | مرحلہ %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | کچھ ایسا چنیں جو صرف آپ ہی کہتے ہیں |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | اچھا · %1$d بٹ اینٹروپی |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | بہت چھوٹا · 10 حروف چاہییں |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | مضبوط · %1$d بٹ اینٹروپی |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | کمزور · %1$d بٹ اینٹروپی |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | چھ ہندسے جو آپ کی زندگی دیکھ کر کوئی اندازہ نہ لگا سکے |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | ٹھیک ٹھاک · %1$d بٹ — پن اس سے زیادہ مضبوط نہیں ہو سکتا |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | بہت چھوٹا · 6 ہندسے چاہییں |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | کمزور · یہی پن سب سے پہلے آزمائے جاتے ہیں |  |
| `ob_try_label` | TRY | آزمائیں |  |
| `qa_aadhaar` | Aadhaar | آدھار |  |
| `qa_bank_account` | Bank account | بینک اکاؤنٹ |  |
| `qa_pan` | PAN | PAN |  |
| `qa_upi_id` | UPI ID | UPI ID |  |
| `rd_copied` | Copied | کاپی ہو گیا |  |
| `rd_forget` | Forget | بھول جائیں |  |
| `rd_forget_these` | Forget these | یہ بھول جائیں |  |
| `rd_forget_title` | Forget previous passwords? | پرانے پاس ورڈ بھول جائیں؟ |  |
| `rd_history_hide` | Hide | چھپائیں |  |
| `rd_history_show` | Show %1$d | %1$d دکھائیں |  |
| `rd_hold_to_reveal` | Hold to reveal | دیکھنے کے لیے دبائے رکھیں |  |
| `rd_last_edit` | last edit %1$s | آخری بار %1$s بدلا گیا |  |
| `rd_release_to_hide` | Release to hide | چھپانے کے لیے چھوڑ دیں |  |
| `re_add_field` | + Add another field | + ایک اور خانہ شامل کریں |  |
| `re_add_field_title` | Add a field | خانہ شامل کریں |  |
| `re_field_name` | Field name | خانے کا نام |  |
| `re_pick_date` | Pick a date | تاریخ چنیں |  |
| `re_remove` | Remove | ہٹائیں |  |
| `re_tap_card` | Read the card by tapping it | پڑھنے کے لیے کارڈ ٹیپ کریں |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | راز سمجھیں (چھپا رہے گا، دیکھنے کے لیے دبائے رکھیں) |  |
| `re_using_template` | using the %1$s template | %1$s ٹیمپلیٹ استعمال کر کے |  |
| `rem_kit_title` | No recovery kit saved | کوئی ریکوری کٹ محفوظ نہیں |  |
| `scr_about_license` | License: GPL-3.0 — free forever | لائسنس: GPL-3.0 — ہمیشہ مفت |  |
| `scr_about_source` | Source code | سورس کوڈ |  |
| `scr_about_version` | Version %1$s | ورژن %1$s |  |
| `scr_auth_add_qr` | Add via QR | QR سے شامل کریں |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | آپ کی ایپس اور بروکر کے کوڈ یہاں نظر آئیں گے |  |
| `scr_auth_scan_title` | Point the camera at the QR code | کیمرہ QR کوڈ کے اوپر رکھیں |  |
| `scr_detail_copied` | Copied · clears in 30s | کاپی ہو گیا · 30 سیکنڈ میں مٹ جائے گا |  |
| `scr_detail_copy` | Copy | کاپی کریں |  |
| `scr_detail_edit` | Edit | بدلیں |  |
| `scr_detail_favorite` | Favourite | پسندیدہ |  |
| `scr_detail_hidden` | Hidden | چھپا ہوا |  |
| `scr_detail_hide` | Hide | چھپائیں |  |
| `scr_detail_history_empty` | Nothing replaced yet. | ابھی تک کچھ نہیں بدلا۔ |  |
| `scr_detail_history_title` | Previous passwords | پرانے پاس ورڈ |  |
| `scr_detail_reveal` | Show | دکھائیں |  |
| `scr_detail_shown` | Shown | نظر آ رہا ہے |  |
| `scr_edit_cancel` | Cancel | منسوخ |  |
| `scr_edit_generate` | Generate | بنائیں |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | بینک / کمپنی (اکٹھا رکھنے کے لیے) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | یہ ٹھیک نہیں لگ رہا — ایک بار دیکھ لیں |  |
| `scr_edit_link_none` | None | کچھ نہیں |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | یہ کارڈ نمبر عام جانچ میں پاس نہیں ہوتا — اگر ٹھیک ہے تو محفوظ کر لیں |  |
| `scr_edit_month` | Month | مہینہ |  |
| `scr_edit_picker_other` | Other… | دیگر… |  |
| `scr_edit_picker_other_hint` | Type your own | اپنا لکھیں |  |
| `scr_edit_required_title` | Give it a name first | پہلے اسے کوئی نام دیں |  |
| `scr_edit_save` | Save | محفوظ کریں |  |
| `scr_edit_title_hint` | Title | نام |  |
| `scr_edit_title_new` | New | نیا |  |
| `scr_edit_year` | Year | سال |  |
| `scr_gallery_quick_add` | Quick add | جلدی شامل کریں |  |
| `scr_gallery_title` | What do you want to save? | ہم کیا محفوظ کر رہے ہیں؟ |  |
| `scr_home_add` | Add | شامل کریں |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | آپ کا بینک اکاؤنٹ ایسا دکھے گا |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | آپ کے کارڈ، UPI، ایپ لاگ اِن — سب یہیں |  |
| `scr_home_group_other` | Other | دیگر |  |
| `scr_home_no_results` | Nothing matches your search | آپ کی تلاش سے کچھ نہیں ملا |  |
| `scr_home_search_hint` | Search your vault | اپنی تجوری میں تلاش کریں |  |
| `scr_home_tab_authenticator` | Authenticator | کوڈ |  |
| `scr_home_tab_home` | Home | گھر |  |
| `scr_home_tab_settings` | Settings | سیٹنگز |  |
| `scr_home_title` | Home | گھر |  |
| `scr_language_continue` | Continue | آگے بڑھیں |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | اپنی زبان چنیں |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | کاپی کرنے کے لیے بٹن دبائیں · 30 سیکنڈ میں مٹ جائے گا |  |
| `scr_login_helper_channel` | Login helper | لاگ اِن مددگار |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s میں لاگ اِن ہو رہے ہیں |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | ابھی بیک اپ فولڈر لگا لیں |  |
| `scr_quickunlock_enable` | Turn on | چالو کریں |  |
| `scr_quickunlock_skip` | Not now | ابھی نہیں |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | انگلی یا چہرے سے کھولیں |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s کی تاریخ قریب ہے · Zerokosh کھولیں |  |
| `scr_reminder_channel` | Renewal reminders | تجدید کی یاد دہانی |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh یاد دلا رہا ہے |  |
| `scr_settings_about` | About | بارے میں |  |
| `scr_settings_allow_screenshots` | Allow screenshots | اسکرین شاٹ لینے دیں |  |
| `scr_settings_autofill` | Autofill service | آٹو فِل سروس |  |
| `scr_settings_autofill_off` | Not set up | لگایا نہیں گیا |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | دستیاب نہیں |  |
| `scr_settings_autolock` | Lock when I leave the app | ایپ چھوڑتے ہی بند کریں |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 منٹ بعد |  |
| `scr_settings_autolock_immediately` | Immediately | فوراً |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d منٹ بعد |  |
| `scr_settings_change_passphrase` | Change passphrase | پاس فریز بدلیں |  |
| `scr_settings_current_passphrase` | Current passphrase | موجودہ پاس فریز |  |
| `scr_settings_export` | Export | برآمد کریں |  |
| `scr_settings_import` | Import passwords | پاس ورڈ درآمد کریں |  |
| `scr_settings_language` | Language | زبان |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | نیا پاس فریز (کم از کم 10 حروف) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | نئی ریکوری کنجی لیں |  |
| `scr_settings_passphrase_changed` | Passphrase changed | پاس فریز بدل گیا |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | انگلی / چہرہ ان لاک |  |
| `scr_settings_security_info` | How your data is protected | آپ کا ڈیٹا کیسے محفوظ ہے |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | بیک اپ اور سنک فولڈر |  |
| `scr_settings_sync_not_set` | Not backed up | کوئی بیک اپ نہیں |  |
| `scr_settings_title` | Settings | سیٹنگز |  |
| `se_title` | Not saved | محفوظ نہیں ہوا |  |
| `st_active_folder` | Active Folder | فعال فولڈر |  |
| `st_active_value` | Active · %1$s | فعال · %1$s |  |
| `st_backing_up` | Backing up vault… | تجوری کا بیک اپ لے رہے ہیں… |  |
| `st_backup_now` | Backup Now | ابھی بیک اپ لیں |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | بیک اپ اور سنک فولڈر |  |
| `st_change_folder` | Change Folder | فولڈر بدلیں |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | نیا پاس فریز دوبارہ لکھیں |  |
| `st_connected_folder` | Connected folder: %1$s | جڑا ہوا فولڈر: %1$s |  |
| `st_disconnect` | Disconnect | ہٹائیں |  |
| `st_done` | Done | ہو گیا |  |
| `st_export_kosh` | Export encrypted .kosh | انکرپٹ شدہ ‎.kosh‎ برآمد |  |
| `st_folder_fallback` | Folder | فولڈر |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | پاس فریز بھول گئے؟ |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | اپنی انگلی سے نیا رکھیں |  |
| `st_generate` | Generate | بنائیں |  |
| `st_group_about` | About | بارے میں |  |
| `st_group_appearance` | Appearance | شکل |  |
| `st_group_security` | Security | سیکیورٹی |  |
| `st_group_sync` | Sync | سنک |  |
| `st_import_kosh` | Import a .kosh backup | ‎.kosh‎ بیک اپ درآمد |  |
| `st_import_other` | Import from another password manager | کسی اور پاس ورڈ مینیجر سے درآمد |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | لائسنس |  |
| `st_logos_by` | Logos provided by | لوگو از |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | اسے آف لائن رکھیں۔ پرانی ریکوری کنجی اب کارآمد نہیں۔ |  |
| `st_new_recovery_result` | Your new Recovery Key: | آپ کی نئی ریکوری کنجی: |  |
| `st_subtitle` | Your rules. | آپ کے اصول۔ |  |
| `st_theme` | Theme | تھیم |  |
| `st_theme_dark` | Dark | گہرا |  |
| `st_theme_light` | Light | ہلکا |  |
| `st_theme_system` | System | سسٹم |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | تجوری %1$s میں محفوظ ہو گئی! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | بیک اپ نہ ہو سکا — فولڈر کی اجازت دیکھیں |  |
| `st_toast_disconnected` | Backup folder disconnected | بیک اپ فولڈر ہٹا دیا گیا |  |
| `st_toast_export_failed` | Export failed | برآمد نہ ہو سکی |  |
| `st_toast_exported` | Encrypted vault exported | انکرپٹ شدہ تجوری برآمد ہو گئی |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | بیک اپ فولڈر جڑ گیا، اور تجوری %1$s میں محفوظ ہو گئی! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | بیک اپ فولڈر جڑ گیا: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | فولڈر نہ جڑ سکا: %1$s |  |
| `st_vault_review` | Vault review | تجوری کی جانچ |  |
| `st_vault_review_detail` | Reused, weak, expiring | دوبارہ استعمال شدہ، کمزور، میعاد ختم ہوتے |  |
| `tab_codes` | Codes | کوڈ |  |
| `tab_settings` | Settings | سیٹنگز |  |
| `tab_templates` | Templates | ٹیمپلیٹ |  |
| `tab_vault` | Vault | تجوری |  |
| `time_days` | %1$dd ago | %1$d دن پہلے |  |
| `time_hours` | %1$dh ago | %1$d گھنٹے پہلے |  |
| `time_just_now` | just now | ابھی ابھی |  |
| `time_minutes` | %1$dm ago | %1$d منٹ پہلے |  |
| `time_months` | %1$dmo ago | %1$d مہینے پہلے |  |
| `time_years` | %1$dy ago | %1$d سال پہلے |  |
| `tpl_aadhaar_card` | Aadhaar Card | آدھار |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | آدھار نمبر |  |
| `tpl_aadhaar_card_address` | Address | آدھار پر پتہ |  |
| `tpl_aadhaar_card_dob` | Dob | تاریخ پیدائش |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | اسکین شدہ نقل |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | جڑا ہوا موبائل |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar پاس کوڈ |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | آدھار پر نام |  |
| `tpl_aadhaar_card_notes` | Notes | نوٹ |  |
| `tpl_app_profile` | App Profile | ایپ پروفائل |  |
| `tpl_app_profile_app_name` | App name | ایپ کا نام |  |
| `tpl_app_profile_gift_cards` | Gift cards | گفٹ کارڈ |  |
| `tpl_app_profile_membership` | Membership | ممبرشپ |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | ممبرشپ کی تجدید |  |
| `tpl_app_profile_notes` | Notes | نوٹ |  |
| `tpl_app_profile_password_if_any` | Password (if any) | پاس ورڈ (اگر ہو) |  |
| `tpl_app_profile_registered_email` | Registered email | رجسٹرڈ ای میل |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | رجسٹرڈ موبائل |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | والٹ پن |  |
| `tpl_bank_account` | Bank Account | بینک اکاؤنٹ |  |
| `tpl_bank_account_account_number` | Account number | اکاؤنٹ نمبر |  |
| `tpl_bank_account_account_type` | Account type | اکاؤنٹ کی قسم |  |
| `tpl_bank_account_bank_name` | Bank name | بینک کا نام |  |
| `tpl_bank_account_branch` | Branch | شاخ |  |
| `tpl_bank_account_customer_id` | Customer id | کسٹمر ID |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC کوڈ |  |
| `tpl_bank_account_login_password` | Login password | لاگ اِن پاس ورڈ |  |
| `tpl_bank_account_micr` | MICR code | MICR کوڈ |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | نیٹ بینکنگ یوزر ID |  |
| `tpl_bank_account_nominee` | Nominee | نامزد |  |
| `tpl_bank_account_notes` | Notes | نوٹ |  |
| `tpl_bank_account_profile_password` | Profile password | پروفائل پاس ورڈ |  |
| `tpl_bank_account_registered_email` | Registered email | رجسٹرڈ ای میل |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | رجسٹرڈ موبائل |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | لین دین کا پاس ورڈ |  |
| `tpl_card` | Card | کارڈ |  |
| `tpl_card_atm_pin` | ATM PIN | ATM پن |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | بلنگ سائیکل کا دن |  |
| `tpl_card_card_network` | Card network | نیٹ ورک |  |
| `tpl_card_card_number` | Card number | کارڈ نمبر |  |
| `tpl_card_card_portal_login` | Card portal login | کارڈ پورٹل لاگ اِن |  |
| `tpl_card_card_portal_password` | Card portal password | کارڈ پورٹل پاس ورڈ |  |
| `tpl_card_card_type` | Card type | کارڈ کی قسم |  |
| `tpl_card_card_variant` | Card variant | کارڈ ویریئنٹ |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | میعاد |  |
| `tpl_card_linked_account` | Linked account | جڑا ہوا اکاؤنٹ |  |
| `tpl_card_name_on_card` | Name on card | کارڈ پر نام |  |
| `tpl_card_notes` | Notes | نوٹ |  |
| `tpl_demat` | Demat | ڈی میٹ |  |
| `tpl_demat_api_key` | API key | API کنجی |  |
| `tpl_demat_api_secret` | API secret | API سیکریٹ |  |
| `tpl_demat_bo_id` | BO ID | BO ID |  |
| `tpl_demat_broker` | Broker | بروکر |  |
| `tpl_demat_cdsl_tpin` | CDSL TPIN | CDSL TPIN |  |
| `tpl_demat_client_id` | Client id | کلائنٹ ID |  |
| `tpl_demat_depository` | Depository | ڈپازٹری |  |
| `tpl_demat_dp_id` | DP ID | DP ID |  |
| `tpl_demat_login_password` | Login password | لاگ اِن پاس ورڈ |  |
| `tpl_demat_mf_folios` | Mutual fund folios | میوچل فنڈ فولیو |  |
| `tpl_demat_mpin` | MPIN | MPIN |  |
| `tpl_demat_nominee` | Nominee | نامزد |  |
| `tpl_demat_notes` | Notes | نوٹ |  |
| `tpl_digilocker` | Digilocker | DigiLocker |  |
| `tpl_digilocker_login_username` | Login username | یوزر نیم |  |
| `tpl_digilocker_notes` | Notes | نوٹ |  |
| `tpl_digilocker_portal_password` | Portal password | پاس ورڈ |  |
| `tpl_digilocker_security_pin` | Security pin | سیکیورٹی پن |  |
| `tpl_driving_license` | Driving License | ڈرائیونگ لائسنس |  |
| `tpl_driving_license_dl_number` | Dl number | لائسنس نمبر |  |
| `tpl_driving_license_dob` | Dob | تاریخ پیدائش |  |
| `tpl_driving_license_expiry_date` | Expiry date | اس تاریخ تک درست |  |
| `tpl_driving_license_file_copy` | Scanned copy | اسکین شدہ نقل |  |
| `tpl_driving_license_issue_date` | Issue date | جاری ہونے کی تاریخ |  |
| `tpl_driving_license_name_on_dl` | Name on dl | لائسنس پر نام |  |
| `tpl_driving_license_notes` | Notes | نوٹ |  |
| `tpl_driving_license_rto_location` | Rto location | RTO |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | گاڑیوں کی اقسام |  |
| `tpl_epf_pension` | Epf Pension | EPF / پنشن |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | جڑا ہوا موبائل |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | EPF پر نام |  |
| `tpl_epf_pension_nominee` | Nominee | نامزد |  |
| `tpl_epf_pension_notes` | Notes | نوٹ |  |
| `tpl_epf_pension_password` | Password | پاس ورڈ |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | PF ممبر ID |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO پاس ورڈ |  |
| `tpl_epf_pension_scheme` | Scheme | اسکیم |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | سرکاری شناختی دستاویز |  |
| `tpl_gov_id_expiry` | Expiry | میعاد |  |
| `tpl_gov_id_file_copy` | Scanned copy | اسکین شدہ نقل |  |
| `tpl_gov_id_id_kind` | ID type | شناختی دستاویز کی قسم |  |
| `tpl_gov_id_id_number` | ID number | شناختی نمبر |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | دستاویز کے مطابق نام |  |
| `tpl_gov_id_notes` | Notes | نوٹ |  |
| `tpl_gov_id_portal_login` | Portal login | پورٹل لاگ اِن |  |
| `tpl_gov_id_portal_password` | Portal password | پورٹل پاس ورڈ |  |
| `tpl_insurance` | Insurance | بیمہ |  |
| `tpl_insurance_agent_contact` | Agent contact | ایجنٹ کا رابطہ |  |
| `tpl_insurance_commencement_date` | Commencement date | شروع ہونے کی تاریخ |  |
| `tpl_insurance_insurer` | Insurer | بیمہ کمپنی |  |
| `tpl_insurance_maturity_date` | Maturity date | میچورٹی کی تاریخ |  |
| `tpl_insurance_nominee` | Nominee | نامزد |  |
| `tpl_insurance_notes` | Notes | نوٹ |  |
| `tpl_insurance_policy_number` | Policy number | پالیسی نمبر |  |
| `tpl_insurance_policy_term` | Policy term | پالیسی کی مدت |  |
| `tpl_insurance_policy_type` | Policy type | پالیسی کی قسم |  |
| `tpl_insurance_portal_login` | Portal login | پورٹل لاگ اِن |  |
| `tpl_insurance_portal_password` | Portal password | پورٹل پاس ورڈ |  |
| `tpl_insurance_premium_amount` | Premium amount | پریمیم رقم |  |
| `tpl_insurance_premium_due_date` | Premium due date | پریمیم کی تاریخ |  |
| `tpl_insurance_premium_mode` | Premium mode | پریمیم کیسے بھرتے ہیں |  |
| `tpl_insurance_sum_assured` | Sum assured | بیمہ کی رقم |  |
| `tpl_login` | Login | لاگ اِن |  |
| `tpl_login_notes` | Notes | نوٹ |  |
| `tpl_login_password` | Password | پاس ورڈ |  |
| `tpl_login_recovery_codes` | Recovery codes | ریکوری کوڈ |  |
| `tpl_login_username` | Username | یوزر نیم |  |
| `tpl_login_website` | Website | ویب سائٹ |  |
| `tpl_pan_card` | Pan Card | PAN کارڈ |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | آدھار سے جڑا |  |
| `tpl_pan_card_dob` | Dob | تاریخ پیدائش |  |
| `tpl_pan_card_e_filing_password` | E filing password | ای فائلنگ پاس ورڈ |  |
| `tpl_pan_card_fathers_name` | Fathers name | والد کا نام |  |
| `tpl_pan_card_file_copy` | Scanned copy | اسکین شدہ نقل |  |
| `tpl_pan_card_name_on_pan` | Name on pan | PAN پر نام |  |
| `tpl_pan_card_notes` | Notes | نوٹ |  |
| `tpl_pan_card_pan_number` | Pan number | PAN |  |
| `tpl_passkey` | Passkey | پاس کی |  |
| `tpl_passkey_credential_id` | Credential ID | کریڈینشل ID |  |
| `tpl_passkey_notes` | Notes | نوٹ |  |
| `tpl_passkey_private_key` | Private key | نجی کلید |  |
| `tpl_passkey_sign_count` | Sign count | سائن کاؤنٹ |  |
| `tpl_passkey_user_handle` | User handle | یوزر ہینڈل |  |
| `tpl_passkey_username` | Username | یوزر نیم |  |
| `tpl_passkey_website` | Website | ویب سائٹ |  |
| `tpl_passport` | Passport | پاسپورٹ |  |
| `tpl_passport_dob` | Dob | تاریخ پیدائش |  |
| `tpl_passport_expiry_date` | Expiry date | میعاد ختم ہونے کی تاریخ |  |
| `tpl_passport_file_copy` | Scanned copy | اسکین شدہ نقل |  |
| `tpl_passport_given_names` | Given names | دیا گیا نام |  |
| `tpl_passport_issue_date` | Issue date | جاری ہونے کی تاریخ |  |
| `tpl_passport_notes` | Notes | نوٹ |  |
| `tpl_passport_passport_number` | Passport number | پاسپورٹ نمبر |  |
| `tpl_passport_place_of_issue` | Place of issue | جاری ہونے کی جگہ |  |
| `tpl_passport_portal_login` | Portal login | Passport Seva لاگ اِن |  |
| `tpl_passport_portal_password` | Portal password | Passport Seva پاس ورڈ |  |
| `tpl_passport_surname` | Surname | خاندانی نام |  |
| `tpl_secure_note` | Secure Note | محفوظ نوٹ |  |
| `tpl_secure_note_attachment` | Attachment | منسلک |  |
| `tpl_secure_note_body` | Note | نوٹ |  |
| `tpl_shopping` | Shopping | خریداری اکاؤنٹ |  |
| `tpl_shopping_gift_card_code` | Gift card code | گفٹ کارڈ کوڈ |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | گفٹ کارڈ پن |  |
| `tpl_shopping_membership_id` | Membership id | ممبرشپ ID |  |
| `tpl_shopping_notes` | Notes | نوٹ |  |
| `tpl_shopping_password` | Password | پاس ورڈ |  |
| `tpl_shopping_registered_email` | Registered email | رجسٹرڈ ای میل |  |
| `tpl_shopping_registered_mobile` | Registered mobile | رجسٹرڈ موبائل |  |
| `tpl_shopping_wallet_pin` | Wallet pin | والٹ پن |  |
| `tpl_telecom` | Telecom | موبائل اور انٹرنیٹ |  |
| `tpl_telecom_account_number` | Account number | اکاؤنٹ نمبر |  |
| `tpl_telecom_circle` | Circle | سرکل |  |
| `tpl_telecom_mobile_number` | Mobile number | موبائل نمبر |  |
| `tpl_telecom_notes` | Notes | نوٹ |  |
| `tpl_telecom_operator` | Operator | کمپنی |  |
| `tpl_telecom_plan_type` | Plan type | پلان کی قسم |  |
| `tpl_telecom_portal_password` | Portal password | پورٹل پاس ورڈ |  |
| `tpl_telecom_puk` | PUK code | PUK کوڈ |  |
| `tpl_telecom_renewal_date` | Renewal date | ری چارج کی تاریخ |  |
| `tpl_telecom_sim_number` | Sim number | سم نمبر (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | سم پن |  |
| `tpl_transit` | Transit | سفری پاس |  |
| `tpl_transit_login_password` | Login password | لاگ اِن پاس ورڈ |  |
| `tpl_transit_notes` | Notes | نوٹ |  |
| `tpl_transit_operator_name` | Operator name | کمپنی |  |
| `tpl_transit_registered_email` | Registered email | رجسٹرڈ ای میل |  |
| `tpl_transit_registered_mobile` | Registered mobile | رجسٹرڈ موبائل |  |
| `tpl_transit_smart_card_number` | Smart card number | اسمارٹ کارڈ نمبر |  |
| `tpl_transit_wallet_pin` | Wallet pin | والٹ پن |  |
| `tpl_travel_booking` | Travel Booking | سفر کی بکنگ |  |
| `tpl_travel_booking_account_username` | Account username | یوزر نیم |  |
| `tpl_travel_booking_login_password` | Login password | لاگ اِن پاس ورڈ |  |
| `tpl_travel_booking_notes` | Notes | نوٹ |  |
| `tpl_travel_booking_provider` | Provider | کمپنی |  |
| `tpl_travel_booking_registered_email` | Registered email | رجسٹرڈ ای میل |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | رجسٹرڈ موبائل |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | والٹ پن |  |
| `tpl_upi` | UPI | UPI |  |
| `tpl_upi_app_name` | App name | UPI ایپ |  |
| `tpl_upi_apps_used` | Apps used | کن ایپس میں فعال |  |
| `tpl_upi_linked_account` | Linked account | جڑا ہوا اکاؤنٹ |  |
| `tpl_upi_notes` | Notes | نوٹ |  |
| `tpl_upi_upi_id` | UPI ID | UPI ID |  |
| `tpl_upi_upi_pin` | UPI PIN | UPI پن |  |
| `tpl_utility` | Utility | بل اور کنکشن |  |
| `tpl_utility_account_holder` | Account holder | اکاؤنٹ ہولڈر |  |
| `tpl_utility_consumer_number` | Consumer number | صارف نمبر |  |
| `tpl_utility_due_day` | Bill due day | بل بھرنے کا دن |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag ID |  |
| `tpl_utility_notes` | Notes | نوٹ |  |
| `tpl_utility_portal_login` | Portal login | پورٹل لاگ اِن |  |
| `tpl_utility_portal_password` | Portal password | پورٹل پاس ورڈ |  |
| `tpl_utility_provider` | Provider | خدمت دینے والی کمپنی |  |
| `tpl_utility_utility_kind` | Utility kind | کس چیز کا بل |  |
| `tpl_utility_vehicle_number` | Vehicle number | گاڑی نمبر |  |
| `tpl_utility_wifi_password` | Wi-Fi password | Wi-Fi پاس ورڈ |  |
| `tpl_voter_id` | Voter Id | ووٹر شناختی کارڈ |  |
| `tpl_voter_id_constituency` | Constituency | حلقہ |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC نمبر |  |
| `tpl_voter_id_file_copy` | Scanned copy | اسکین شدہ نقل |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | ووٹر کارڈ پر نام |  |
| `tpl_voter_id_notes` | Notes | نوٹ |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP پاس ورڈ |  |
| `tr_days_many` | %1$d days left | %1$d دن باقی |  |
| `tr_days_one` | %1$d day left | %1$d دن باقی |  |
| `tr_gone_today` | gone today | آج چلا جائے گا |  |
| `tr_restore` | Restore | واپس لائیں |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | اس کے بعد وہ ہمیشہ کے لیے چلے جاتے ہیں — کہیں اور کوئی نقل نہیں۔ |  |
| `ui_hide_passphrase` | Hide passphrase | پاس فریز چھپائیں |  |
| `ui_label_count` | %1$s, %2$s | %1$s، %2$s |  |
| `ui_show_passphrase` | Show passphrase | پاس فریز دکھائیں |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | اسی فون پر %1$d اندراجات سے ملایا گیا۔ کچھ بھی کہیں نہیں بھیجا گیا۔ |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | اسی فون پر %1$d اندراج سے ملایا گیا۔ کچھ بھی کہیں نہیں بھیجا گیا۔ |  |
| `vh_count_many` | %1$d things worth a look. | %1$d باتوں پر دھیان دینا چاہیے۔ |  |
| `vh_count_one` | %1$d thing worth a look. | %1$d بات پر دھیان دینا چاہیے۔ |  |
| `vh_empty` | No reused, weak or expiring credentials. | نہ دوبارہ استعمال شدہ، نہ کمزور، نہ میعاد ختم ہوتا کچھ ہے۔ |  |
| `vh_kind_common` | Commonly guessed | آسانی سے اندازہ لگ جانے والا |  |
| `vh_kind_expiring` | Expiring | میعاد ختم ہو رہی ہے |  |
| `vh_kind_reused` | Reused password | دوبارہ استعمال شدہ پاس ورڈ |  |
| `vh_kind_weak` | Weak | کمزور |  |
| `vh_no_kit_title` | No recovery kit saved | کوئی ریکوری کٹ محفوظ نہیں |  |
| `vh_nothing` | Nothing to fix. | ٹھیک کرنے کو کچھ نہیں۔ |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | آف لائن |  |
| `wl_chip_open` | Open source | اوپن سورس |  |
| `wl_create` | Create a new vault | نئی تجوری بنائیں |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | نہ ای میل · نہ اکاؤنٹ · کچھ بھی اس فون سے باہر نہیں جاتا |  |
| `wl_head_1` | Your keys. | آپ کی کنجیاں۔ |  |
| `wl_head_2` | Your device. | آپ کا فون۔ |  |
| `wl_head_3` | No server. | کوئی سرور نہیں۔ |  |
| `wl_restore` | Restore from Recovery Kit | ریکوری کٹ سے واپس لائیں |  |
| `wl_sr_headline` | Your keys. Your device. No server. | آپ کی کنجیاں۔ آپ کا فون۔ کوئی سرور نہیں۔ |  |
