# Odia (`or`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-or/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Odia | ok? |
|---|---|---|---|
| `au_close` | Close | ବନ୍ଦ କରନ୍ତୁ |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | ବଛାଯାଇଥିବା ଛବିରେ ସଠିକ୍ TOTP QR କୋଡ୍ ମିଳିଲା ନାହିଁ |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | ଖାଲିରୁ ଆରମ୍ଭ କରି ନିଜ ଫିଲ୍ଡକୁ ନିଜେ ନାମ ଦିଅନ୍ତୁ — ଟେମ୍ପ୍ଲେଟ୍ କେବଳ ଲେବଲ୍ ଭରେ, ତଥ୍ୟ କେବେ ନୁହେଁ। |  |
| `hm_close_search` | Close search | ଖୋଜା ବନ୍ଦ କରନ୍ତୁ |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | ଏହି ଫୋନରୁ କେବେ ବାହାରକୁ ଯାଏ ନାହିଁ। ସଞ୍ଚୟ ସମୟରେ ଏନ୍‌କ୍ରିପ୍ଟ। |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | ଏବେ ଆପଣ ଆମଦାନୀ କରିଥିବା ଫାଇଲ୍ ହଟାନ୍ତୁ। ତାହା ଆପଣଙ୍କ ପାସୱାର୍ଡର ଖୋଲା ତାଲିକା, ଆଉ ଏବେ ବି ଆପଣଙ୍କ Downloads ରେ ପଡ଼ିଛି। |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | ସବୁକିଛି କେବଳ ଏହି ଫୋନରେ ଡିକ୍ରିପ୍ଟ ହୁଏ। କିଛି ଅପଲୋଡ୍ ହୁଏ ନାହିଁ, କାରଣ ଏହି ଆପ୍ ନେଟୱାର୍କ ସଂଯୋଗ ଖୋଲି ହିଁ ପାରିବ ନାହିଁ। |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | ବ୍ୟାଙ୍କ କେବେ ଆପଣଙ୍କ OTP ମାଗେ ନାହିଁ। ଯିଏ ମାଗେ, ସେ ଠକ। |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | କୌଣସି ବ୍ୟାଙ୍କ ଅଧିକାରୀ ଆପଣଙ୍କୁ ସ୍କ୍ରିନ୍ ସେୟାର୍ ଆପ୍ ଲଗାଇବାକୁ କହିବେ ନାହିଁ। |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | ଆପଣଙ୍କ UPI ପିନ୍ କେବଳ UPI ଆପ୍ କୀପ୍ୟାଡ୍ ପାଇଁ — କଲରେ କାହାକୁ କୁହନ୍ତୁ ନାହିଁ। |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | KYC ଗୋଟିଏ ଦିନରେ ସରେ ନାହିଁ। “ଆଜି KYC ସରୁଛି” ଏଭଳି ବାର୍ତ୍ତା ଠକାମି। |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | ଟଙ୍କା ପାଇବା ପାଇଁ କେବେ ପିନ୍ ଦେବାକୁ କିମ୍ବା QR ସ୍କାନ୍ କରିବାକୁ ପଡ଼େ ନାହିଁ। |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | ବିଦ୍ୟୁତ୍ କଟିବାର SMS, ଆଉ ସେଥିରେ କାହାର ବ୍ୟକ୍ତିଗତ ନମ୍ବର? ସେଇଆ ଠକାମି। |  |
| `nav_close_menu` | Close menu | ମେନୁ ବନ୍ଦ କରନ୍ତୁ |  |
| `nfc_cannot_read` | Cannot read cards | କାର୍ଡ ପଢ଼ିପାରିବ ନାହିଁ |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | ଏହି ଫୋନରେ NFC ନାହିଁ, ତେଣୁ କାର୍ଡ ପଢ଼ିପାରିବ ନାହିଁ। |  |
| `ob_fact_lost_title` | If you lose your keys | ଚାବି ହଜିଗଲେ |  |
| `ob_fact_network_note` | The app literally cannot phone home | ଏହି ଆପ୍ କେଉଁଠାକୁ ସମ୍ପର୍କ କରିପାରିବ ନାହିଁ |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | ବାୟୋମେଟ୍ରିକ୍ ସୁରକ୍ଷା ଚିପ୍ ବାହାରକୁ କେବେ ଯାଏ ନାହିଁ |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | ଆପଣ ଚାବିକୁ ସେହି ଫୋଲ୍ଡରରେ ରଖିଛନ୍ତି ଯାହା ଆପଣଙ୍କ ଏନକ୍ରିପ୍ଟ ଭଣ୍ଡାର ସିଙ୍କ କରେ। ଏବେ ଯିଏ ସେହି ଫୋଲ୍ଡର ପାଇବ ତାକୁ ଦୁଇଟି ଯାକ ମିଳିବ। ଚାବିକୁ ଅନ୍ୟ କୋଉଠି ରଖନ୍ତୁ — କାଗଜ, ଅନ୍ୟ ଖାତା, କିମ୍ବା ଡ୍ରୟାର। |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | ଏହା ଆପଣଙ୍କ ଭଣ୍ଡାର ଫାଇଲ ପାଖରେ ଅଛି |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH ରିକଭରି |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | ସ୍କାନ୍ କରନ୍ତୁ କିମ୍ବା ଟାଇପ୍ କରନ୍ତୁ। ପୁଣି ଇନଷ୍ଟଲ୍, ଫ୍ୟାକ୍ଟରୀ ରିସେଟ୍, କିମ୍ବା ଫୋନ୍ ହଜିଲା ପରେ ବି କାମ କରିବ। |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | ଏବେ ସେଭ କରିଥିବା କିଟରୁ ଗୋଷ୍ଠୀ %1$d ଓ ଗୋଷ୍ଠୀ %2$d ଲେଖନ୍ତୁ। |  |
| `ob_kit_challenge_hint` | Group %1$d | ଗୋଷ୍ଠୀ %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | ପ୍ରଥମେ କିଟ ସେଭ କରନ୍ତୁ, ତା\'ପରେ ଗୋଷ୍ଠୀ %1$d ଓ %2$d ଫେରି ଲେଖନ୍ତୁ। |  |
| `ob_kit_challenge_title` | Check you actually have it | କିଟ ପ୍ରକୃତରେ ଆପଣଙ୍କ ପାଖରେ ଅଛି କି ଦେଖନ୍ତୁ |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | ଏହା ଉପରର ଚାବି ସହ ମେଳ ଖାଉନାହିଁ। |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | କେହି ବି — Zerokosh ବି — ଏହାକୁ ମୋ ପାଇଁ ଫେରାଇ ଆଣିପାରିବେ ନାହିଁ। |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "ମୁଁ ଏହାକୁ ଅଫଲାଇନ୍ ରଖିଛି। " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | ଥରେ ଦେଖାଯାଏ, କେବେ ଖୋଲା ରୂପରେ ରଖାଯାଏ ନାହିଁ। ଭିତରକୁ ଆସିପାରିଲେ ସେଟିଂସରୁ ନୂଆ ତିଆରି କରନ୍ତୁ। |  |
| `ob_kit_head_emph` | On paper. | କାଗଜ ଉପରେ। |  |
| `ob_kit_head_lead` | "One key. " | "ଗୋଟିଏ ଚାବି. " |  |
| `ob_kit_head_tail` | " Never online." | " କେବେ ଅନଲାଇନ୍ ନୁହେଁ।" |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | Gmail ନୁହେଁ, WhatsApp ନୁହେଁ, ସ୍କ୍ରିନସଟ୍ ନୁହେଁ। ସିନ୍ଦୁକ, ବ୍ୟାଙ୍କ ଲକର, କିମ୍ବା ଇସ୍ପାତ ପ୍ଲେଟ୍। |  |
| `ob_kit_offline_title` | Keep it off the internet | ଏହାକୁ ଇଣ୍ଟରନେଟଠାରୁ ଦୂରରେ ରଖନ୍ତୁ |  |
| `ob_kit_print` | Print | ଛାପନ୍ତୁ |  |
| `ob_kit_print_note` | A printer, or Save as PDF | ପ୍ରିଣ୍ଟର, କିମ୍ବା PDF ଭାବରେ ସେଭ |  |
| `ob_kit_qr` | QR image | QR ଛବି |  |
| `ob_kit_qr_cd` | Recovery key QR code | ରିକଭରି ଚାବିର QR କୋଡ୍ |  |
| `ob_kit_qr_note` | To an offline gallery | ଅଫଲାଇନ୍ ଗ୍ୟାଲେରୀକୁ |  |
| `ob_kit_regenerate` | Regenerate | ନୂଆ ତିଆରି କରନ୍ତୁ |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | ଏହା ସେଭ ହେଲା ନାହିଁ। ପୁଣି ଚେଷ୍ଟା କରନ୍ତୁ, କିମ୍ବା ଅନ୍ୟ ଜାଗା ବାଛନ୍ତୁ। |  |
| `ob_kit_save_pdf` | Save PDF | PDF ସଞ୍ଚୟ କରନ୍ତୁ |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | ଗୋଟିଏ ପୃଷ୍ଠାର ଛପାଯୋଗ୍ୟ କିଟ୍ |  |
| `ob_kit_saved` | I\'ve saved my kit | ମୁଁ ମୋ କିଟ୍ ସଞ୍ଚୟ କରିନେଲି |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s ରେ ସେଭ ହେଲା |  |
| `ob_kit_sent_to_printer` | Sent to the printer | ପ୍ରିଣ୍ଟରକୁ ପଠାଗଲା |  |
| `ob_kit_skip` | I\'ll do this later | ଏହା ପରେ କରିବି |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | ଆପଣଙ୍କ ଭଣ୍ଡାର ଚାଲିବ। କିଟ ସେଭ ନହେବା ପର୍ଯ୍ୟନ୍ତ Zerokosh ମନେ ପକାଇଦେବ। |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | ଏହି ଫୋନରେ ହିଁ ତିଆରି, ଥରେ ମାତ୍ର ଦେଖାଯିବ। ପାସ୍‌ଫ୍ରେଜ୍ ଭୁଲିଗଲେ ଭିତରକୁ ଫେରିବାର ଏହା ହିଁ ଏକମାତ୍ର ରାସ୍ତା। |  |
| `ob_kit_working` | Working… | କାମ ଚାଲିଛି… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | ସିନ୍ଦୁକର ଲେବଲ୍, ଟେମ୍ପ୍ଲେଟ୍ ଓ ଚେତାବନୀ ସଙ୍ଗେ ସଙ୍ଗେ ବଦଳିଯିବ। ସେଟିଂରେ ଯେକୌଣସି ସମୟରେ ବଦଳାଇପାରିବେ। |  |
| `ob_pass_confirm` | Confirm | ପୁଣି ଲେଖନ୍ତୁ |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | ଏହା ଆମକୁ କେବେ ଦେଖାଯାଏ ନାହିଁ। କୌଣସି ରିସେଟ୍ ଲିଙ୍କ ନାହିଁ। |  |
| `ob_pass_head_emph` | held only | କେବଳ ଆପଣଙ୍କ |  |
| `ob_pass_head_lead` | "One secret, " | "ଗୋଟିଏ ହିଁ ଗୁପ୍ତ କଥା, " |  |
| `ob_pass_head_tail` | " by you." | " ପାଖରେ।" |  |
| `ob_pass_no_match` | no match | ମେଳ ଖାଉନାହିଁ |  |
| `ob_pass_seal` | Seal the vault | ସିନ୍ଦୁକରେ ତାଲା ପକାନ୍ତୁ |  |
| `ob_pass_sealing` | Sealing… | ତାଲା ପଡ଼ୁଛି… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | ସମ୍ପର୍କହୀନ ତିନି ଚାରିଟି ଶବ୍ଦ, ଗୋଟିଏ ଚତୁର ଶବ୍ଦଠାରୁ ଭଲ। ଏହି ସ୍କ୍ରିନରୁ କିଛି ବାହାରକୁ ଯାଏ ନାହିଁ। |  |
| `ob_pass_tab_passphrase` | Passphrase | ପାସ୍‌ଫ୍ରେଜ୍ |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 ଅଙ୍କର ପିନ୍ |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | ଆପଣଙ୍କ ଆଙ୍ଗୁଠି ଚିହ୍ନ ଫୋନର ସୁରକ୍ଷା ଚିପ୍ ଭିତରେ ରହେ। ତାହା ଏହି ଫୋନରୁ କେବେ ବାହାରକୁ ଯାଏ ନାହିଁ। |  |
| `ob_trust_continue` | I understand · Continue | ବୁଝିଗଲି · ଆଗକୁ ଯାଆନ୍ତୁ |  |
| `ob_trust_head_emph` | don\'t | ଜାଣୁ ନାହିଁ |  |
| `ob_trust_head_lead` | "Exactly what we " | "ଆମେ ପ୍ରକୃତରେ କଣ " |  |
| `ob_trust_head_tail` | " know." | " ତାହା।" |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | ପାସ୍‌ଫ୍ରେଜ୍ ଭୁଲିଗଲେ ଆଉ ରିକଭରି କିଟ୍ ବି ହରାଇଲେ, ସିନ୍ଦୁକ ବନ୍ଦ ହୋଇ ରହିବ — ଆପଣଙ୍କ ପାଇଁ ବି, ଆମ ପାଇଁ ବି, କାହା ପାଇଁ ବି। |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "ଚାବି ଆପଣଙ୍କ ପାଖରେ। " |  |
| `ob_trust_stat_files` | .kosh file on device | ଫୋନରେ .kosh ଫାଇଲ୍ |  |
| `ob_trust_stat_servers` | servers contacted | ସର୍ଭର ସହ ସମ୍ପର୍କ |  |
| `ob_trust_stat_trackers` | trackers or SDKs | ଟ୍ରାକର୍ କିମ୍ବା SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | ଏହା ଥରେ ପଢ଼ିନିଅନ୍ତୁ। ପୂରା ସୁରକ୍ଷା ବ୍ୟବସ୍ଥା ଏହା ହିଁ, ସରଳ ଭାଷାରେ। |  |
| `ob_trust_tag_audited` | Audited build | ଅଡିଟ୍ ହୋଇଥିବା ବିଲ୍ଡ୍ |  |
| `ob_trust_tag_reproducible` | Reproducible APK | ପୁଣି ତିଆରି କରାଯାଉଥିବା APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | ଆପଣ ଆଙ୍ଗୁଠି ଚିହ୍ନରେ ଖୋଲୁଛନ୍ତି। ଆଙ୍ଗୁଠି ଚିହ୍ନ କେବେ କାମ ନକଲେ, ଏହା ହିଁ ଆପଣଙ୍କୁ ଭିତରକୁ ଆଣିବ — ତେଣୁ ଦେଖିନେବା ଭଲ। |  |
| `pc_confirm` | Check | ଯାଞ୍ଚ କରନ୍ତୁ |  |
| `pc_correct` | Still correct. Nothing to do. | ଏବେ ବି ଠିକ ଅଛି। କିଛି କରିବାର ନାହିଁ। |  |
| `pc_forgot` | I cannot remember it | ମୋର ମନେ ପଡ଼ୁନାହିଁ |  |
| `pc_later` | Not now | ଏବେ ନୁହେଁ |  |
| `pc_reset_action` | Set new passphrase | ନୂଆ ପାସଫ୍ରେଜ ରଖ |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | ଆପଣଙ୍କ ଆଙ୍ଗୁଠି ଚିହ୍ନ ଏହି ଭଣ୍ଡାର ଖୋଲିପାରେ, ତେଣୁ ସେହି ନୂଆ ପାସଫ୍ରେଜ ମଧ୍ୟ ରଖିପାରେ — ରିକଭରି କିଟ ଦରକାର ନାହିଁ। ନିଶ୍ଚିତ କରିବାକୁ ଆଉ ଥରେ ପଚରାଯିବ। |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | ପାସଫ୍ରେଜ ବଦଳିଗଲା। ଶୀଘ୍ର ଅନଲକ ପୁଣି ଲାଗିଗଲା। |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | ଏହା ହେଲା ନାହିଁ। ଆପଣଙ୍କ ପୁରୁଣା ପାସଫ୍ରେଜ ହିଁ ଚାଲୁଛି। |  |
| `pc_reset_title` | Set a new passphrase | ନୂଆ ପାସଫ୍ରେଜ ରଖନ୍ତୁ |  |
| `pc_title` | Do you still remember your passphrase? | ଆପଣଙ୍କୁ ଏବେ ବି ପାସଫ୍ରେଜ ମନେ ଅଛି କି? |  |
| `pc_wrong` | That is not it. You can set a new one instead. | ଏହା ସେଇଟି ନୁହେଁ। ଏହା ବଦଳରେ ନୂଆ ରଖିପାରିବେ। |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | ଏହି ରେକର୍ଡ ପାଇଁ ମନେ ରଖାଯାଇଥିବା %1$d ମୂଲ୍ୟ ହଟାଇଦିଆଯିବ। ଏହା ଫେରାଇ ଅଣାଯାଇପାରିବ ନାହିଁ। |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh ସେହି ଲଗଇନ୍ ସଞ୍ଚୟ କରିପାରିଲା ନାହିଁ। |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | ଭରିବା ପାଇଁ Zerokosh ଖୋଲନ୍ତୁ |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | ଏହି ଗୋଟିଏ ପାସ୍‌ଫ୍ରେଜ୍ ସବୁକିଛି ତାଲା ପକାଇ ରଖେ। କେବଳ ଆପଣ ଜାଣିଥିବା ଲମ୍ବା କିଛି ବାଛନ୍ତୁ। |  |
| `scr_create_button` | Lock it in | ତାଲା ପକାଇ ଦିଅନ୍ତୁ |  |
| `scr_create_confirm_hint` | Type it again | ପୁଣି ଲେଖନ୍ତୁ |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | ପାସ୍‌ଫ୍ରେଜ୍ (ଅତି କମ୍‌ରେ 10 ଅକ୍ଷର) |  |
| `scr_create_mismatch` | The two entries don\'t match | ଦୁଇଟି ସମାନ ନୁହେଁ |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 ଅଙ୍କର ପିନ୍ |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | ତା ବଦଳରେ 6 ଅଙ୍କର ପିନ୍ ରଖନ୍ତୁ |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | ପିନ୍ ପାଇଁ ଆଙ୍ଗୁଠି ଚିହ୍ନ କିମ୍ବା ମୁହଁ ଅନ୍‌ଲକ୍ ଥିବା ଫୋନ୍ ଦରକାର। ଦୟାକରି ପାସ୍‌ଫ୍ରେଜ୍ ବାଛନ୍ତୁ। |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | ପିନ୍ ଚଳେ କାରଣ ଏହି ଫୋନ୍ ତାହାକୁ ନିଜର ସୁରକ୍ଷା ଚିପ୍ ଓ ଆପଣଙ୍କ ଆଙ୍ଗୁଠି ଚିହ୍ନ କିମ୍ବା ମୁହଁରେ ରକ୍ଷା କରେ। |  |
| `scr_create_strength_fair` | Fair | ଠିକ୍‌ଠାକ୍ |  |
| `scr_create_strength_good` | Good | ଭଲ |  |
| `scr_create_strength_strong` | Strong | ଶକ୍ତ |  |
| `scr_create_strength_weak` | Weak | ଦୁର୍ବଳ |  |
| `scr_create_title` | Create your passphrase | ଆପଣଙ୍କ ପାସ୍‌ଫ୍ରେଜ୍ ତିଆରି କରନ୍ତୁ |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | ଅତି କମ୍‌ରେ 10 ଅକ୍ଷର ରଖନ୍ତୁ — ଯେତେ ଲମ୍ବା, ସେତେ ଶକ୍ତ |  |
| `scr_create_working` | Preparing your vault… | ଆପଣଙ୍କ ସିନ୍ଦୁକ ପ୍ରସ୍ତୁତ ହେଉଛି… |  |
| `scr_detail_delete` | Delete | ହଟାନ୍ତୁ |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | ତାହା 30 ଦିନ “ନିକଟରେ ହଟାଯାଇଥିବା”ରେ ରହିବ, ଆଉ ଆପଣଙ୍କ ଅନ୍ୟ ଫୋନ ସହ ସିଙ୍କ ହେଲାପରେ ଚାଲିଯିବ। |  |
| `scr_detail_delete_confirm_title` | Delete this record? | ଏହି ରେକର୍ଡ ହଟାଇବେ? |  |
| `scr_detail_delete_confirm_yes` | Delete | ହଟାନ୍ତୁ |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | ସିକ୍ରେଟ୍ କିମ୍ବା otpauth:// ଲିଙ୍କ ଲଗାନ୍ତୁ |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | ଆପଣଙ୍କ ଆଙ୍ଗୁଠି ଚିହ୍ନ କିମ୍ବା ମୁହଁ ବ୍ୟବହାର କରନ୍ତୁ |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh ଖୋଲନ୍ତୁ |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | ବହୁତ ଥର ଭୁଲ୍ ଚେଷ୍ଟା ହେଲା। %1$d ସେକେଣ୍ଡ ଅପେକ୍ଷା କରନ୍ତୁ। |  |
| `scr_lock_hint` | Passphrase | ପାସ୍‌ଫ୍ରେଜ୍ |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | ଏହି ରିକଭରି ଚାବି ଠିକ୍ ନାହିଁ — ଗୋଟି ଗୋଟି ଅକ୍ଷର ମିଳାଇ ଦେଖନ୍ତୁ |  |
| `scr_lock_title` | Vault is locked | ସିନ୍ଦୁକରେ ତାଲା ପଡ଼ିଛି |  |
| `scr_lock_unlock` | Unlock | ଖୋଲନ୍ତୁ |  |
| `scr_lock_use_passphrase` | Use passphrase | ପାସ୍‌ଫ୍ରେଜ୍ ବ୍ୟବହାର କରନ୍ତୁ |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | ଦୟାକରି ଥରେ ପାସ୍‌ଫ୍ରେଜ୍‌ରେ ଖୋଲନ୍ତୁ |  |
| `scr_lock_use_recovery` | Use Recovery Key | ରିକଭରି ଚାବି ବ୍ୟବହାର କରନ୍ତୁ |  |
| `scr_lock_wrong` | Wrong passphrase | ପାସ୍‌ଫ୍ରେଜ୍ ଭୁଲ୍ |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | କେବେ ପାସ୍‌ଫ୍ରେଜ୍ ଭୁଲିଗଲେ, ଭିତରକୁ ଫେରିବାର ଏହା ହିଁ ଏକମାତ୍ର ରାସ୍ତା। ଆମେ ତାହା ରିସେଟ୍ କରିପାରିବୁ ନାହିଁ — କେହି ପାରିବେ ନାହିଁ। |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | ମୁଁ ତାହା ଲେଖି ସୁରକ୍ଷିତ ଜାଗାରେ ରଖିଛି |  |
| `scr_recovery_done` | Continue | ଆଗକୁ ଯାଆନ୍ତୁ |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | ଏହି ଚାବି ଥରେ ମାତ୍ର ଦେଖାଯାଏ। ଆପଣ ଯେତେବେଳ ପର୍ଯ୍ୟନ୍ତ ଖୋଲିପାରୁଛନ୍ତି, ସେଟିଂସରୁ ଯେକୌଣସି ସମୟରେ ନୂଆ ତିଆରି କରିପାରିବେ। |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | ଏହି ପୃଷ୍ଠାକୁ ଆପଣଙ୍କ ସମ୍ପତ୍ତି କାଗଜପତ୍ର କିମ୍ବା ଅନ୍ୟ ଜରୁରୀ ଦଲିଲ ସହିତ ରଖନ୍ତୁ। ଏହି ଚାବି ଯାହା ପାଖରେ ଥିବ ସେ ଆପଣଙ୍କ ସିନ୍ଦୁକ ଖୋଲିପାରିବେ — ଏହାକୁ ଲକରର ଚାବି ପରି ସମ୍ଭାଳନ୍ତୁ। |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | ରିକଭରି କିଟ୍ PDF ସଞ୍ଚିତ ହେଲା |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh ରିକଭରି କିଟ୍ |  |
| `scr_recovery_save_pdf` | Save as PDF | PDF ଭାବରେ ସଞ୍ଚୟ କରନ୍ତୁ |  |
| `scr_recovery_title` | Your Recovery Key | ଆପଣଙ୍କ ରିକଭରି ଚାବି |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | ଆପଣ ଯାହା ସଞ୍ଚୟ କରନ୍ତି ସବୁ ଆପଣଙ୍କ ଫୋନର ଗୋଟିଏ ତାଲା ପଡ଼ିଥିବା ଫାଇଲରେ ରହେ। ତାହା କେବେ ଆମ ପାଖକୁ ଆସେ ନାହିଁ — ତାହା ରଖିବାକୁ ଆମ ପାଖରେ ଜାଗା ହିଁ ନାହିଁ। |  |
| `scr_trust_card1_title` | Your data stays on this device | ଆପଣଙ୍କ ତଥ୍ୟ ଏହି ଫୋନରେ ହିଁ ରହେ |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | Zerokosh ଖାତା ନାହିଁ, କ୍ଲାଉଡ୍ ନାହିଁ, ସାଇନ୍-ଅପ୍ ନାହିଁ। ଏହାକୁ କେବଳ ଆପଣ ଖୋଲିପାରିବେ। ଆମେ ବି ପାରିବୁ ନାହିଁ। |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | ଆମର କୌଣସି ସର୍ଭର ନାହିଁ — ହ୍ୟାକ୍ କରିବାକୁ କିଛି ନାହିଁ, ବିକ୍ରି କରିବାକୁ ବି ନାହିଁ |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | ଚାନ୍ଦା ନାହିଁ, ବିଜ୍ଞାପନ ନାହିଁ। ଯେ କେହି ଆମ କୋଡ୍ ପଢ଼ି ଆମର ପ୍ରତିଟି ପ୍ରତିଶ୍ରୁତି ଯାଞ୍ଚ କରିପାରିବେ। |  |
| `scr_trust_card3_title` | Free forever, open source | ସବୁବେଳେ ମାଗଣା, ଓପନ୍ ସୋର୍ସ |  |
| `scr_trust_continue` | Continue | ଆଗକୁ ଯାଆନ୍ତୁ |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | ଆପଣଙ୍କ ପରିବର୍ତ୍ତନ ସଞ୍ଚିତ ହେଲା ନାହିଁ, ତେଣୁ ସିନ୍ଦୁକରେ ଆଗରୁ ଯାହା ଥିଲା ସେଥିରୁ କିଛି ହଜିନାହିଁ। |  |
| `st_recently_deleted` | Recently deleted | ନିକଟରେ ହଟାଯାଇଥିବା |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | ଏକ-ଥର କୋଡ୍ (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | ଏକ-ଥର କୋଡ୍ (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | ଏକ-ଥର କୋଡ୍ (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA ସିକ୍ରେଟ୍ |  |
| `tr_cannot_undo` | This cannot be undone. | ଏହା ଫେରାଇ ଅଣାଯାଇପାରିବ ନାହିଁ। |  |
| `tr_delete_all` | Delete all permanently | ସବୁକିଛି ସବୁଦିନ ପାଇଁ ହଟାନ୍ତୁ |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d ରେକର୍ଡ ସବୁଦିନ ପାଇଁ ଚାଲିଯିବ। ଏହା ଫେରାଇ ଅଣାଯାଇପାରିବ ନାହିଁ ଆଉ ପୁନଃସ୍ଥାପନ ପାଇଁ ବ୍ୟାକଅପ୍ ବି ନାହିଁ। |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d ରେକର୍ଡ ସବୁଦିନ ପାଇଁ ଚାଲିଯିବ। ଏହା ଫେରାଇ ଅଣାଯାଇପାରିବ ନାହିଁ ଆଉ ପୁନଃସ୍ଥାପନ ପାଇଁ ବ୍ୟାକଅପ୍ ବି ନାହିଁ। |  |
| `tr_delete_all_title` | Delete everything in the trash? | ଆବର୍ଜନା ପାତ୍ରର ସବୁକିଛି ହଟାଇଦେବେ? |  |
| `tr_delete_now` | Delete now | ଏବେ ହଟାନ୍ତୁ |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | “%1$s” କୁ ସବୁଦିନ ପାଇଁ ହଟାଇଦେବେ? |  |
| `tr_empty` | Nothing deleted. | କିଛି ହଟାଯାଇ ନାହିଁ। |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | ହଟାଯାଇଥିବା ରେକର୍ଡ ଏଠାରେ %1$d ଦିନ ରହେ। |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | ଭାରତୀୟ ବ୍ୟାଙ୍କ, UPI, କାର୍ଡ, ଡିମ୍ୟାଟ୍, EPF ଆଉ ଆପଣ ପ୍ରକୃତରେ ବ୍ୟବହାର କରୁଥିବା OTP ଆପ୍ — ଏ ସବୁ ପାଇଁ ଫୋନରେ ହିଁ ରହୁଥିବା ସିନ୍ଦୁକ। |  |

## Priority 2 — longer prose

| key | English | Odia | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | ଯାହା ସବୁଠାରୁ ବେଶୀ କାମରେ ଆସେ ସେହି ଗୋଟିଏ ସୂଚନାରୁ ଆରମ୍ଭ କରନ୍ତୁ। ନୋଟ୍ସ ଆପ୍‌ରେ ପଡ଼ିଥିବା ବାରଟି ପାସୱାର୍ଡଠାରୁ ସଞ୍ଚିତ ଗୋଟିଏ ପାସୱାର୍ଡ ବି ଅଧିକ ସୁରକ୍ଷିତ। |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | ପାସଫ୍ରେଜ ଭୁଲିଗଲେ ରିକଭରି କିଟ ହିଁ ଭିତରକୁ ଆସିବାର ଏକମାତ୍ର ବାଟ। ଆପଣଙ୍କ ପାଇଁ ଆଉ ଗୋଟିଏ କେହି ତିଆରି କରିପାରିବେ ନାହିଁ। |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | ସେହି ଫାଇଲରେ ଚିହ୍ନଟ ଯୋଗ୍ୟ କିଛି ମିଳିଲା ନାହିଁ। Chrome, Google Password Manager, Bitwarden, LastPass ଓ KeePass ର ଏକ୍ସପୋର୍ଟ ବୁଝାଯାଏ। |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d ବର୍ତ୍ତମାନର ରେକର୍ଡ ବଦଳିବ — ସାଇଟ୍ ଓ ୟୁଜରନେମ୍ ମିଳାଇ। ବଦଳିଥିବା ପାସୱାର୍ଡ ପ୍ରତି ରେକର୍ଡର ଇତିହାସରେ ଫେରି ମିଳିବ। |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d ବର୍ତ୍ତମାନର ରେକର୍ଡ ବଦଳିବ — ସାଇଟ୍ ଓ ୟୁଜରନେମ୍ ମିଳାଇ। ବଦଳିଥିବା ପାସୱାର୍ଡ ପ୍ରତି ରେକର୍ଡର ଇତିହାସରେ ଫେରି ମିଳିବ। |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d ରେକର୍ଡ ଦୁଇ ପଟେ ବଦଳିଥିଲା। ଦୁଇଟି ରୂପ ରଖାଯାଇଛି — “(conflict copy)” ଖୋଜନ୍ତୁ। |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | ଏହି ବ୍ୟାକଅପ୍ ଫାଇଲ୍ ଖୋଲୁଥିବା ପାସ୍‌ଫ୍ରେଜ୍ ଦିଅନ୍ତୁ। ତାହା ଆପଣଙ୍କ ବର୍ତ୍ତମାନର ଠାରୁ ଅଲଗା ହୋଇପାରେ। |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | .kosh ଫାଇଲର Poly1305 ପ୍ରମାଣୀକରଣ ଟ୍ୟାଗ୍ ମେଳ ଖାଉନାହିଁ। ଅଧାରୁ ବନ୍ଦ ହୋଇଥିବା ସିଙ୍କ କିମ୍ବା ଖରାପ ଷ୍ଟୋରେଜ୍ ପରେ ଏମିତି ହୋଇପାରେ। |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh ସିନ୍ଦୁକ ଫାଇଲ ପାଖରେ ଗୋଟିଏ ଚାଲୁ ବ୍ୟାକଅପ୍ ରଖେ। ତାହାକୁ ଆପଣଙ୍କ ସିଙ୍କ ଫୋଲ୍ଡରରୁ ପୁନଃସ୍ଥାପନ କରନ୍ତୁ, କିମ୍ବା ଅନ୍ୟ ଫୋନରେ ରିକଭରି କିଟ୍‌ରେ ସିନ୍ଦୁକ ଖୋଲନ୍ତୁ। |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | ପଢ଼ିବା ପର୍ଯ୍ୟନ୍ତ କାର୍ଡକୁ ଫୋନର ପଛପଟେ ସିଧା ଲଗାଇ ରଖନ୍ତୁ। ଏଥିରୁ କାର୍ଡ ନମ୍ବର, ମିଆଦ ଓ ନାମ ମିଳେ — CVV ଚିପ୍‌ରେ ନଥାଏ, ତାହା ଆପଣ ନିଜେ ଲେଖିବେ। |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | ଭିତରକୁ ଫେରିବାର ଶୀଘ୍ର ରାସ୍ତା ବାଛନ୍ତୁ। ସିନ୍ଦୁକର ପାହରା ପାସ୍‌ଫ୍ରେଜ୍ ହିଁ କରୁଥିବ; ଏହା କେବଳ ଏହି ଫୋନରେ ଚାବି ଖୋଲେ। |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | ଏବେ ଆପଣଙ୍କ ଭଣ୍ଡାରରେ ପ୍ରକୃତ ତଥ୍ୟ ଅଛି। ରିକଭରି କିଟ ବିନା ପାସଫ୍ରେଜ ଭୁଲିଗଲେ କେହି ଆପଣଙ୍କୁ ଫେରାଇ ଆଣିପାରିବେ ନାହିଁ — ଆମେ ମଧ୍ୟ ନୁହେଁ। |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh ମାଗଣା, ଓପନ୍ ସୋର୍ସ, ଆଉ ଏହାର କୌଣସି ସର୍ଭର ନାହିଁ। ଆପଣଙ୍କ ସିନ୍ଦୁକ କେବଳ ଆପଣ ଖୋଲିପାରିବେ। ଆମେ ବି ପାରିବୁ ନାହିଁ। |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | QR କୋଡ୍ ସ୍କାନ୍ କରିବାକୁ ହିଁ କ୍ୟାମେରା ଅନୁମତି ଦରକାର। ରେକର୍ଡ ଯୋଡ଼ିବା ସମୟରେ ସିକ୍ରେଟ୍ ହାତରେ ବି ଲଗାଇପାରିବେ। |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | ଅଟୋଫିଲ୍ ଚାଲିବାକୁ ନଦେଉଥିବା ବ୍ୟାଙ୍କ ଆପ୍ ପାଇଁ — ବଟନ୍ ଦବାଇ ଲଗଇନ୍ ବିବରଣୀ ଗୋଟି ଗୋଟି କରି କପି କରନ୍ତୁ |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | ଆପଣ ଯେମିତି ଫୋନ୍ ଖୋଲନ୍ତି, ସିନ୍ଦୁକ ବି ସେମିତି। ପାସ୍‌ଫ୍ରେଜ୍ ସବୁବେଳେ କାମ କରୁଥିବ। |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | ସିନ୍ଦୁକର ସ୍କ୍ରିନସଟ୍ କ୍ଲାଉଡ୍ ଫଟୋ ବ୍ୟାକଅପ୍‌କୁ ଯାଇପାରେ। ପ୍ରକୃତରେ ଦରକାର ହେଲେ ହିଁ ଚାଲୁ କରନ୍ତୁ। |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | ସିନ୍ଦୁକ ଫାଇଲ୍ ଲେଖିହେଲା ନାହିଁ। ଆପଣ ବ୍ୟାକଅପ୍ ଓ ସିଙ୍କ ଫୋଲ୍ଡର ସେଟ୍ କରିଥିଲେ, ହୁଏତ Android ତାହାର ଅନୁମତି ଫେରାଇ ନେଇଛି — ସେଟିଂ ଖୋଲନ୍ତୁ, ଫୋଲ୍ଡର ପୁଣି ବାଛନ୍ତୁ, ଆଉ ପୁଣି ଚେଷ୍ଟା କରନ୍ତୁ। |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | ଆପଣଙ୍କ ଏନ୍‌କ୍ରିପ୍ଟ .kosh ଫାଇଲ୍ ସିଧା ଏହି ଫୋଲ୍ଡରରେ ହିଁ ସଞ୍ଚିତ ହୁଏ। ଅନେକ ଡିଭାଇସରେ ଆପେ ଆପେ ବ୍ୟାକଅପ୍ ପାଇଁ ଏହି ଫୋଲ୍ଡରକୁ Google Drive, Syncthing, Nextcloud କିମ୍ବା SD କାର୍ଡ ସହ ସିଙ୍କ କରନ୍ତୁ। |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | ନୂଆ ରିକଭରି ଚାବି ତିଆରି କରିବାକୁ ଆପଣଙ୍କ ପାସ୍‌ଫ୍ରେଜ୍ ଦିଅନ୍ତୁ। ପୁରୁଣା ଚାବି କାମ କରିବା ବନ୍ଦ କରିଦେବ। |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | ଏହି ପୃଷ୍ଠାର ବାକି ସବୁ ଗୋଟିଏ ଲଗଇନ ଦୁର୍ବଳ କରେ। ଏହା ପୁରା ଭଣ୍ଡାର ନେଇଯାଇପାରେ। ସେଟିଂସ → ନୂଆ ରିକଭରି ଚାବି ନିଅନ୍ତୁ। |  |

## Priority 3 — short labels

| key | English | Odia | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | ଶକ୍ତିଶାଳୀ ପାସୱାର୍ଡ ବ୍ୟବହାର କରନ୍ତୁ |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | ଖାତାର ନାମ (ଯଥା Google) |  |
| `au_active_many` | %1$d active codes | %1$d ଚାଲୁ କୋଡ୍ |  |
| `au_active_one` | %1$d active code | %1$d ଚାଲୁ କୋଡ୍ |  |
| `au_add_another` | Add another authenticator | ଆଉ ଗୋଟିଏ ଅଥେଣ୍ଟିକେଟର୍ ଯୋଡ଼ନ୍ତୁ |  |
| `au_add_secret` | Add Secret Key | ସିକ୍ରେଟ୍ କୀ ଯୋଡ଼ନ୍ତୁ |  |
| `au_camera_needed` | Camera permission needed to scan QR code | QR କୋଡ୍ ସ୍କାନ୍ କରିବାକୁ କ୍ୟାମେରା ଅନୁମତି ଦରକାର |  |
| `au_copied` | Copied · clears shortly | କପି ହେଲା · ଅଳ୍ପ ସମୟରେ ମିଳାଇଯିବ |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google, GitHub କିମ୍ବା ଆପଣଙ୍କ ବ୍ରୋକରର QR ସ୍କାନ୍ କରନ୍ତୁ, କିମ୍ବା ସିକ୍ରେଟ୍ କୀ ହାତରେ ଲେଖନ୍ତୁ। |  |
| `au_enter_key` | Enter Key | କୀ ଲେଖନ୍ତୁ |  |
| `au_fallback_name` | Authenticator | ଅଥେଣ୍ଟିକେଟର୍ |  |
| `au_flashlight` | Flashlight | ଟର୍ଚ୍ଚ |  |
| `au_grant` | Grant Permission | ଅନୁମତି ଦିଅନ୍ତୁ |  |
| `au_image_failed` | Failed to process image | ଛବି ପଢ଼ିହେଲା ନାହିଁ |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | ଭୁଲ୍ Base32 ସିକ୍ରେଟ୍ କୀ (କେବଳ A-Z ଅକ୍ଷର ଓ 2-7 ଅଙ୍କ) |  |
| `au_no_match` | No codes match | କୌଣସି କୋଡ୍ ମିଳିଲା ନାହିଁ |  |
| `au_none_yet` | No codes yet. | ଏପର୍ଯ୍ୟନ୍ତ କୌଣସି କୋଡ୍ ନାହିଁ। |  |
| `au_pick_image` | Pick Image | ଛବି ବାଛନ୍ତୁ |  |
| `au_rotating` | "Rotating " | "ବଦଳୁଥିବା " |  |
| `au_rotating_emph` | codes. | କୋଡ୍। |  |
| `au_save_key` | Save Key | କୀ ସଞ୍ଚୟ କରନ୍ତୁ |  |
| `au_scan_qr` | Scan a QR code | QR କୋଡ୍ ସ୍କାନ୍ କରନ୍ତୁ |  |
| `au_scan_title` | Scan Authenticator QR | ଅଥେଣ୍ଟିକେଟର୍ QR ସ୍କାନ୍ କରନ୍ତୁ |  |
| `au_search_hint` | Search codes, issuers… | କୋଡ୍, ଜାରିକର୍ତ୍ତା ଖୋଜନ୍ତୁ… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | ଯଥା JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | ସିକ୍ରେଟ୍ କୀ (Base32) |  |
| `au_tap_to_copy` | Tap to copy | କପି କରିବାକୁ ଟ୍ୟାପ୍ କରନ୍ତୁ |  |
| `cat_apps` | Apps &amp; Logins | ଆପ୍ ଓ ଲଗଇନ୍ |  |
| `cat_banks` | Banks &amp; UPI | ବ୍ୟାଙ୍କ ଓ UPI |  |
| `cat_cards` | Cards | କାର୍ଡ |  |
| `cat_govid` | Gov &amp; ID | ସରକାରୀ ଓ ପରିଚୟ |  |
| `cat_investments` | Investments | ନିବେଶ |  |
| `cat_utilities` | Utilities | ବିଲ୍ ଓ ସଂଯୋଗ |  |
| `cd_mask_hidden` | hidden | ଲୁଚିଛି |  |
| `cd_shield_high_sensitivity` | extra-protected field | ଅତିରିକ୍ତ ସୁରକ୍ଷିତ ସୂଚନା |  |
| `gl_blank` | Blank template | ଖାଲି ଟେମ୍ପ୍ଲେଟ୍ |  |
| `gl_cat_apps` | Apps | ଆପ୍ |  |
| `gl_cat_banks` | Banks | ବ୍ୟାଙ୍କ |  |
| `gl_cat_cards` | Cards | କାର୍ଡ |  |
| `gl_cat_demat` | Demat | ଡିମ୍ୟାଟ୍ |  |
| `gl_cat_govid` | Gov ID | ସରକାରୀ ପରିଚୟ |  |
| `gl_cat_popular` | Popular | ଲୋକପ୍ରିୟ |  |
| `gl_cat_shopping` | Shopping | କିଣାକିଣି |  |
| `gl_cat_travel` | Travel | ଯାତ୍ରା |  |
| `gl_cat_upi` | UPI | UPI |  |
| `gl_cat_utilities` | Utilities | ବିଲ୍ |  |
| `gl_head_emph` | storing? | ସଞ୍ଚୟ କରୁଛୁ? |  |
| `gl_head_lead` | "What are we " | "ଆମେ କଣ " |  |
| `gl_matches` | %1$d matches | %1$d ମିଳିଲା |  |
| `gl_most_used` | Most-used first | ସବୁଠାରୁ ବେଶୀ ବ୍ୟବହୃତ ଆଗେ |  |
| `gl_not_found` | Can’t find a service? | ସେବା ମିଳୁନାହିଁ? |  |
| `gl_search` | Search %1$d Indian services… | %1$d ଭାରତୀୟ ସେବାରେ ଖୋଜନ୍ତୁ… |  |
| `gl_suggested` | Suggested for you | ଆପଣଙ୍କ ପାଇଁ ପରାମର୍ଶ |  |
| `hm_add_first` | Add your first record | ଆପଣଙ୍କ ପ୍ରଥମ ରେକର୍ଡ ଯୋଡ଼ନ୍ତୁ |  |
| `hm_all_offline` | all offline. | ସବୁ ଅଫଲାଇନ୍। |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d ସୂଚନା, " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d ସୂଚନା, " |  |
| `hm_detail_placeholder` | Pick a record to see it here | ଏଠାରେ ଦେଖିବାକୁ ଏକ ରେକର୍ଡ ବାଛନ୍ତୁ |  |
| `hm_empty_blank` | A blank vault, ready. | ଖାଲି ସିନ୍ଦୁକ, ପ୍ରସ୍ତୁତ। |  |
| `hm_empty_head_emph` | waiting. | ଅପେକ୍ଷା କରୁଛି। |  |
| `hm_empty_head_lead` | "Your vault is " | "ଆପଣଙ୍କ ସିନ୍ଦୁକ " |  |
| `hm_filter_all` | All | ସବୁ |  |
| `hm_import_backup` | Import an encrypted backup | ଏନ୍‌କ୍ରିପ୍ଟ ବ୍ୟାକଅପ୍ ଆମଦାନୀ |  |
| `hm_import_backup_note` | Open a .kosh file from this device | ଏହି ଫୋନରୁ .kosh ଫାଇଲ୍ ଖୋଲନ୍ତୁ |  |
| `hm_inst_many` | %1$d institutions | %1$d ସଂସ୍ଥା |  |
| `hm_inst_one` | %1$d institution | %1$d ସଂସ୍ଥା |  |
| `hm_kit_banner_action` | Save one now | ଏବେ ସେଭ କରନ୍ତୁ |  |
| `hm_kit_banner_dismiss` | Remind me later | ପରେ ମନେ ପକାନ୍ତୁ |  |
| `hm_kit_banner_title` | No recovery kit saved | କୌଣସି ରିକଭରି କିଟ ସେଭ ହୋଇନାହିଁ |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | ଖୋଲା ଅଛି · ଛାଡ଼ିବା ମାତ୍ରେ ତାଲା |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | ଖୋଲା ଅଛି · ଛାଡ଼ିବାର %1$d ମିନିଟ୍ ପରେ ତାଲା |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | ଖୋଲା ଅଛି · ଛାଡ଼ିବାର 1 ମିନିଟ୍ ପରେ ତାଲା |  |
| `hm_no_match` | Nothing matches “%1$s” | “%1$s” ରେ କିଛି ମିଳିଲା ନାହିଁ |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | କୌଣସି ସଂସ୍ଥା, UPI ହ୍ୟାଣ୍ଡଲ୍, କିମ୍ବା ଶେଷ ଚାରି ଅଙ୍କ ଚେଷ୍ଟା କରନ୍ତୁ। |  |
| `hm_pinned` | Pinned | ପିନ୍ କରାଯାଇଥିବା |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC, priya@upi, PAN… ଖୋଜନ୍ତୁ |  |
| `hm_start_template` | Start with a template | ଟେମ୍ପ୍ଲେଟ୍‌ରୁ ଆରମ୍ଭ କରନ୍ତୁ |  |
| `ic_could_not` | Could not import | ଆମଦାନୀ ହୋଇପାରିଲା ନାହିଁ |  |
| `ic_done` | Done | ହୋଇଗଲା |  |
| `ic_import` | Import | ଆମଦାନୀ |  |
| `ic_imported` | Imported | ଆମଦାନୀ ହେଲା |  |
| `ic_importing` | Importing… | ଆମଦାନୀ ହେଉଛି… |  |
| `ic_new_many` | %1$d new logins. | %1$d ନୂଆ ଲଗଇନ୍। |  |
| `ic_new_one` | %1$d new login. | %1$d ନୂଆ ଲଗଇନ୍। |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d ଯୋଡ଼ାଗଲା, %2$d ବଦଳିଲା। |  |
| `ic_title` | Import from %1$s? | %1$s ରୁ ଆମଦାନୀ କରିବେ? |  |
| `ic_too_large` | That file is too large to be a credential export. | ଏହି ଫାଇଲ୍ ଏତେ ବଡ଼ ଯେ ପାସୱାର୍ଡ ଏକ୍ସପୋର୍ଟ ହୋଇପାରିବ ନାହିଁ। |  |
| `import_action` | Import | ଆମଦାନୀ |  |
| `import_locked` | Unlock your vault before importing. | ଆମଦାନୀ କରିବା ପୂର୍ବରୁ ଆପଣଙ୍କ ସିନ୍ଦୁକ ଖୋଲନ୍ତୁ। |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d ଯୋଡ଼ାଗଲା, %2$d ବଦଳିଲା। କିଛି ମିଟାଯାଇ ନାହିଁ। |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | ସେହି ଫାଇଲ୍‌କୁ Zerokosh ସିନ୍ଦୁକ ଭାବରେ ପଢ଼ିହେଲା ନାହିଁ। |  |
| `import_nothing_new` | Everything in that backup was already here. | ସେହି ବ୍ୟାକଅପ୍‌ର ସବୁକିଛି ପୂର୍ବରୁ ଏଠାରେ ଥିଲା। |  |
| `import_passphrase_label` | Backup passphrase | ବ୍ୟାକଅପ୍‌ର ପାସ୍‌ଫ୍ରେଜ୍ |  |
| `import_title` | Import a backup | ବ୍ୟାକଅପ୍ ଆମଦାନୀ କରନ୍ତୁ |  |
| `kicker_locked` | Locked | ତାଲା ପଡ଼ିଛି |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · କିଛି ଏହି ଫୋନ ବାହାରକୁ ଯାଇନାହିଁ |  |
| `lk_touch_unlock` | Touch to unlock | ଖୋଲିବାକୁ ଛୁଅଁନ୍ତୁ |  |
| `lk_welcome_emph` | Your vault is sealed. | ଆପଣଙ୍କ ସିନ୍ଦୁକରେ ତାଲା ପଡ଼ିଛି। |  |
| `lk_welcome_lead` | Welcome back. | ପୁଣି ସ୍ୱାଗତ। |  |
| `msg_auth_needed` | Confirm it\'s you to see this | ଦେଖିବାକୁ ନିଶ୍ଚିତ କରନ୍ତୁ ଯେ ଏହା ଆପଣ ହିଁ |  |
| `msg_back` | Back | ପଛକୁ |  |
| `msg_cancel` | Cancel | ବାତିଲ୍ |  |
| `msg_file_damaged` | File damaged — restored from backup | ଫାଇଲ୍ ଖରାପ ହୋଇଥିଲା — ବ୍ୟାକଅପ୍‌ରୁ ଠିକ୍ କରାଗଲା |  |
| `msg_ok` | OK | ଠିକ୍ ଅଛି |  |
| `msg_saved` | Saved | ସଞ୍ଚିତ ହେଲା |  |
| `nav_all_templates` | All templates | ସବୁ ଟେମ୍ପ୍ଲେଟ୍ |  |
| `nav_damaged_emph` | vault file | ସିନ୍ଦୁକ ଫାଇଲରେ |  |
| `nav_damaged_kicker` | Damaged state | ଖରାପ ଅବସ୍ଥା |  |
| `nav_damaged_lead` | "Something in the " | "ଆପଣଙ୍କ " |  |
| `nav_damaged_tail` | " is off." | " କିଛି ଗଡ଼ବଡ଼ ଅଛି।" |  |
| `nav_integrity_title` | Integrity check failed | ଅଖଣ୍ଡତା ଯାଞ୍ଚ ବିଫଳ |  |
| `nav_scan` | Scan | ସ୍କାନ୍ |  |
| `nav_tap_card` | Tap a card | କାର୍ଡ ଟ୍ୟାପ୍ କରନ୍ତୁ |  |
| `nav_what_next` | What to do next | ଏବେ କଣ କରିବେ |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC ବନ୍ଦ ଅଛି। ସେଟିଂରେ ଚାଲୁ କରି ପୁଣି ଚେଷ୍ଟା କରନ୍ତୁ। |  |
| `nfc_hold_card` | Hold your card to the phone | କାର୍ଡକୁ ଫୋନରେ ଲଗାନ୍ତୁ |  |
| `nfc_missed` | Did not catch that | ଧରାପଡ଼ିଲା ନାହିଁ |  |
| `nfc_read_failed` | That card could not be read. Try again. | ସେହି କାର୍ଡ ପଢ଼ିହେଲା ନାହିଁ। ପୁଣି ଚେଷ୍ଟା କରନ୍ତୁ। |  |
| `nfc_reading` | Reading… | ପଢ଼ାଯାଉଛି… |  |
| `nfc_try_again` | Try again | ପୁଣି ଚେଷ୍ଟା କରନ୍ତୁ |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · ଏହି ଫୋନରେ ହିଁ ମପାଯାଇଛି |  |
| `ob_argon_faster` | Faster unlock | ଶୀଘ୍ର ଖୋଲିବ |  |
| `ob_argon_harder` | Harder to attack | ଭାଙ୍ଗିବା କଷ୍ଟ |  |
| `ob_argon_measuring` | Measuring this device… | ଏହି ଫୋନ୍ ମପାଯାଉଛି… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id କଠିନତା |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | ଅଭିଧାନର ଗୋଟିଏ ବି ଶବ୍ଦ ନୁହେଁ |  |
| `ob_check_pass_length` | 10 characters or more | 10 କିମ୍ବା ତା ଠାରୁ ଅଧିକ ଅକ୍ଷର |  |
| `ob_check_pass_reuse` | Not reused from another app | ଅନ୍ୟ ଆପ୍‌ରୁ ପୁଣି ବ୍ୟବହାର କରାଯାଇନାହିଁ |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | ଜନ୍ମଦିନ ବି ନୁହେଁ, ବାର୍ଷିକୀ ବି ନୁହେଁ |  |
| `ob_check_pin_digits` | All six digits entered | ଛଅଟି ଯାକ ଅଙ୍କ ଭରିଗଲା |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | କ୍ରମାଗତ ଅଙ୍କ ବି ନୁହେଁ, ପୁନରାବୃତ୍ତି ବି ନୁହେଁ |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~ଭାଙ୍ଗିବାକୁ %1$d ଶତାବ୍ଦୀ |  |
| `ob_crack_days` | ~%1$d days to crack | ~ଭାଙ୍ଗିବାକୁ %1$d ଦିନ |  |
| `ob_crack_forever` | longer than the sun | ସୂର୍ଯ୍ୟଙ୍କ ଠାରୁ ବି ଅଧିକ ସମୟ |  |
| `ob_crack_hours` | ~hours to crack | ~ଭାଙ୍ଗିବାକୁ କିଛି ଘଣ୍ଟା |  |
| `ob_crack_seconds` | ~seconds to crack | ~ଭାଙ୍ଗିବାକୁ କିଛି ସେକେଣ୍ଡ |  |
| `ob_crack_years` | ~%1$d years to crack | ~ଭାଙ୍ଗିବାକୁ %1$d ବର୍ଷ |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | ପ୍ରମାଣିତ, ପ୍ରତି ସିନ୍ଦୁକ ପାଇଁ ଅଲଗା ନନ୍ସ |  |
| `ob_fact_encryption_title` | Encryption | ଏନ୍‌କ୍ରିପ୍ସନ୍ |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | ସେଟଅପ୍ ସମୟରେ ଆପଣଙ୍କ ଫୋନରେ ମପାଯାଇଥିଲା |  |
| `ob_fact_kdf_title` | Key stretching | କୀ ଷ୍ଟ୍ରେଚିଂ |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | ରିସେଟ୍ ଲିଙ୍କ ନାହିଁ। ସପୋର୍ଟ ପଛ ଦୁଆର ବି ନାହିଁ। |  |
| `ob_fact_lost_value` | Nobody can recover it | କେହି ଫେରାଇ ଆଣିପାରିବେ ନାହିଁ |  |
| `ob_fact_network_title` | Network permission | ନେଟୱାର୍କ ଅନୁମତି |  |
| `ob_fact_network_value` | Not requested | ମାଗିହିଁ ନାହିଁ |  |
| `ob_fact_quick_title` | Quick unlock | ଶୀଘ୍ର ଅନ୍‌ଲକ୍ |  |
| `ob_fact_quick_value` | Hardware keystore | ହାର୍ଡୱେର୍ କୀଷ୍ଟୋର୍ |  |
| `ob_lang_continue` | Continue in %1$s | %1$s ରେ ଆଗକୁ ଯାଆନ୍ତୁ |  |
| `ob_lang_head_emph` | language. | ଭାଷା ବାଛନ୍ତୁ। |  |
| `ob_lang_head_lead` | "Choose your " | "ଆପଣଙ୍କ " |  |
| `ob_lang_search` | Search %1$d languages | %1$d ଭାଷାରେ ଖୋଜନ୍ତୁ |  |
| `ob_quick_continue_pass` | Continue with passphrase | ପାସ୍‌ଫ୍ରେଜ୍ ସହ ଆଗକୁ ଯାଆନ୍ତୁ |  |
| `ob_quick_enable` | Enable quick unlock | ଶୀଘ୍ର ଅନ୍‌ଲକ୍ ଚାଲୁ କରନ୍ତୁ |  |
| `ob_quick_fingerprint` | Fingerprint | ଆଙ୍ଗୁଠି ଚିହ୍ନ |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | ଦ୍ରୁତ, ହାର୍ଡୱେର୍ ସୁରକ୍ଷିତ ଅନ୍‌ଲକ୍। |  |
| `ob_quick_head_emph` | Without the cloud. | କ୍ଲାଉଡ୍ ବିନା। |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "ଗୋଟିଏ ସ୍ପର୍ଶରେ ଖୋଲିଯିବ। " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox. କୌଣସି ବାୟୋମେଟ୍ରିକ୍ ସୂଚନା Zerokosh ପର୍ଯ୍ୟନ୍ତ କେବେ ପହଞ୍ଚେ ନାହିଁ। |  |
| `ob_quick_hw_title` | Hardware-backed | ହାର୍ଡୱେର୍ ସୁରକ୍ଷିତ |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | ଏହି ଫୋନରେ ହାର୍ଡୱେର୍ ସେନସର୍ ନାହିଁ। |  |
| `ob_quick_opening` | Opening your vault… | ଆପଣଙ୍କ ସିନ୍ଦୁକ ଖୋଲୁଛି… |  |
| `ob_quick_pass_only` | Passphrase only | କେବଳ ପାସ୍‌ଫ୍ରେଜ୍ |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | ପ୍ରତି ଥର ଲେଖନ୍ତୁ। ସବୁଠାରୁ ସୁରକ୍ଷିତ। |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | ଶୀଘ୍ର ଅନ୍‌ଲକ୍ ସେଟ୍ ହେଲା ନାହିଁ। ପୁଣି ଚେଷ୍ଟା କରନ୍ତୁ, କିମ୍ବା କେବଳ ପାସ୍‌ଫ୍ରେଜ୍ ସହ ଆଗକୁ ଯାଆନ୍ତୁ। |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | ଏବେ ରହୁ — ମୁଁ ପାସ୍‌ଫ୍ରେଜ୍ ଲେଖିବି |  |
| `ob_quick_touch_title` | Touch the sensor | ସେନସର୍ ଛୁଅଁନ୍ତୁ |  |
| `ob_recommended` | Recommended | ସୁପାରିଶ |  |
| `ob_reveal_hide` | Hide | ଲୁଚାନ୍ତୁ |  |
| `ob_reveal_show` | Show | ଦେଖାନ୍ତୁ |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | ଗୁଡ଼ାଉଥିବା ଚାବି ହାର୍ଡୱେର୍ କୀଷ୍ଟୋର୍‌ରେ ରହେ। ବାୟୋମେଟ୍ରିକ୍ ପରବର୍ତ୍ତୀ ପାଦରେ। |  |
| `ob_seal_title` | Seal to this device | ଏହି ଫୋନ ସହ ବାନ୍ଧି ଦିଅନ୍ତୁ |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | ଏହି ଫୋନରେ ହାର୍ଡୱେର୍ ବାୟୋମେଟ୍ରିକ୍ ନାହିଁ। |  |
| `ob_soon` | SOON | ଶୀଘ୍ର |  |
| `ob_step_label` | Step %1$d of 6 | ପାଦ %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | ଏମିତି କିଛି ବାଛନ୍ତୁ ଯାହା କେବଳ ଆପଣ କୁହନ୍ତି |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | ଭଲ · %1$d ବିଟ୍ ଏଣ୍ଟ୍ରୋପି |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | ବହୁତ ଛୋଟ · 10 ଅକ୍ଷର ଦରକାର |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | ଶକ୍ତ · %1$d ବିଟ୍ ଏଣ୍ଟ୍ରୋପି |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | ଦୁର୍ବଳ · %1$d ବିଟ୍ ଏଣ୍ଟ୍ରୋପି |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | ଏମିତି ଛଅଟି ଅଙ୍କ ଯାହା ଆପଣଙ୍କ ଜୀବନ ଦେଖି କେହି ଅନୁମାନ କରିପାରିବେ ନାହିଁ |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | ଠିକ୍‌ଠାକ୍ · %1$d ବିଟ୍ — ପିନ୍ ଏଥିରୁ ଅଧିକ ଶକ୍ତ ହୋଇପାରିବ ନାହିଁ |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | ବହୁତ ଛୋଟ · 6 ଅଙ୍କ ଦରକାର |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | ଦୁର୍ବଳ · ଏହି ପିନ୍ ହିଁ ସବୁଠାରୁ ଆଗେ ଚେଷ୍ଟା କରାଯାଏ |  |
| `ob_try_label` | TRY | ଚେଷ୍ଟା |  |
| `qa_aadhaar` | Aadhaar | ଆଧାର |  |
| `qa_bank_account` | Bank account | ବ୍ୟାଙ୍କ ଖାତା |  |
| `qa_pan` | PAN | PAN |  |
| `qa_upi_id` | UPI ID | UPI ID |  |
| `rd_copied` | Copied | କପି ହେଲା |  |
| `rd_forget` | Forget | ଭୁଲିଯାଆନ୍ତୁ |  |
| `rd_forget_these` | Forget these | ଏଗୁଡ଼ିକ ଭୁଲିଯାଆନ୍ତୁ |  |
| `rd_forget_title` | Forget previous passwords? | ପୂର୍ବ ପାସୱାର୍ଡ ଭୁଲିଯିବେ? |  |
| `rd_history_hide` | Hide | ଲୁଚାନ୍ତୁ |  |
| `rd_history_show` | Show %1$d | %1$d ଦେଖାନ୍ତୁ |  |
| `rd_hold_to_reveal` | Hold to reveal | ଦେଖିବାକୁ ଦବାଇ ଧରନ୍ତୁ |  |
| `rd_last_edit` | last edit %1$s | ଶେଷ ପରିବର୍ତ୍ତନ %1$s |  |
| `rd_release_to_hide` | Release to hide | ଲୁଚାଇବାକୁ ଛାଡ଼ନ୍ତୁ |  |
| `re_add_field` | + Add another field | + ଆଉ ଗୋଟିଏ ଫିଲ୍ଡ ଯୋଡ଼ନ୍ତୁ |  |
| `re_add_field_title` | Add a field | ଫିଲ୍ଡ ଯୋଡ଼ନ୍ତୁ |  |
| `re_field_name` | Field name | ଫିଲ୍ଡର ନାମ |  |
| `re_pick_date` | Pick a date | ତାରିଖ ବାଛନ୍ତୁ |  |
| `re_remove` | Remove | ହଟାନ୍ତୁ |  |
| `re_tap_card` | Read the card by tapping it | କାର୍ଡ ଟ୍ୟାପ୍ କରି ପଢ଼ନ୍ତୁ |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | ଗୁପ୍ତ ଭାବରେ ଗଣନ୍ତୁ (ଲୁଚି ରହିବ, ଦେଖିବାକୁ ଦବାଇ ଧରନ୍ତୁ) |  |
| `re_using_template` | using the %1$s template | %1$s ଟେମ୍ପ୍ଲେଟ୍ ବ୍ୟବହାର କରି |  |
| `rem_kit_title` | No recovery kit saved | କୌଣସି ରିକଭରି କିଟ ସେଭ ହୋଇନାହିଁ |  |
| `scr_about_license` | License: GPL-3.0 — free forever | ଲାଇସେନ୍ସ: GPL-3.0 — ସବୁବେଳେ ମାଗଣା |  |
| `scr_about_source` | Source code | ସୋର୍ସ କୋଡ୍ |  |
| `scr_about_version` | Version %1$s | ସଂସ୍କରଣ %1$s |  |
| `scr_auth_add_qr` | Add via QR | QR ଦ୍ୱାରା ଯୋଡ଼ନ୍ତୁ |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | ଆପଣଙ୍କ ଆପ୍ ଓ ବ୍ରୋକରର କୋଡ୍ ଏଠାରେ ଦେଖାଯିବ |  |
| `scr_auth_scan_title` | Point the camera at the QR code | କ୍ୟାମେରାକୁ QR କୋଡ୍ ଉପରେ ରଖନ୍ତୁ |  |
| `scr_detail_copied` | Copied · clears in 30s | କପି ହେଲା · 30 ସେକେଣ୍ଡରେ ମିଳାଇଯିବ |  |
| `scr_detail_copy` | Copy | କପି |  |
| `scr_detail_edit` | Edit | ବଦଳାନ୍ତୁ |  |
| `scr_detail_favorite` | Favourite | ପସନ୍ଦ |  |
| `scr_detail_hidden` | Hidden | ଲୁଚିଛି |  |
| `scr_detail_hide` | Hide | ଲୁଚାନ୍ତୁ |  |
| `scr_detail_history_empty` | Nothing replaced yet. | ଏପର୍ଯ୍ୟନ୍ତ କିଛି ବଦଳି ନାହିଁ। |  |
| `scr_detail_history_title` | Previous passwords | ପୂର୍ବ ପାସୱାର୍ଡ |  |
| `scr_detail_reveal` | Show | ଦେଖାନ୍ତୁ |  |
| `scr_detail_shown` | Shown | ଦେଖାଯାଉଛି |  |
| `scr_edit_cancel` | Cancel | ବାତିଲ୍ |  |
| `scr_edit_generate` | Generate | ତିଆରି କରନ୍ତୁ |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | ବ୍ୟାଙ୍କ / କମ୍ପାନୀ (ଗୋଷ୍ଠୀ କରିବା ପାଇଁ) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | ଏହା ଠିକ୍ ଲାଗୁନାହିଁ — ଥରେ ଦେଖନ୍ତୁ |  |
| `scr_edit_link_none` | None | କିଛି ନାହିଁ |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | ଏହି କାର୍ଡ ନମ୍ବର ସାଧାରଣ ଯାଞ୍ଚରେ ଉତ୍ତୀର୍ଣ୍ଣ ହେଉନାହିଁ — ଠିକ୍ ଥିଲେ ସଞ୍ଚୟ କରିଦିଅନ୍ତୁ |  |
| `scr_edit_month` | Month | ମାସ |  |
| `scr_edit_picker_other` | Other… | ଅନ୍ୟାନ୍ୟ… |  |
| `scr_edit_picker_other_hint` | Type your own | ନିଜର ଲେଖନ୍ତୁ |  |
| `scr_edit_required_title` | Give it a name first | ପ୍ରଥମେ ଏହାକୁ ଗୋଟିଏ ନାମ ଦିଅନ୍ତୁ |  |
| `scr_edit_save` | Save | ସଞ୍ଚୟ |  |
| `scr_edit_title_hint` | Title | ନାମ |  |
| `scr_edit_title_new` | New | ନୂଆ |  |
| `scr_edit_year` | Year | ବର୍ଷ |  |
| `scr_gallery_quick_add` | Quick add | ଶୀଘ୍ର ଯୋଡ଼ନ୍ତୁ |  |
| `scr_gallery_title` | What do you want to save? | ଆପଣ କଣ ସଞ୍ଚୟ କରିବାକୁ ଚାହାନ୍ତି? |  |
| `scr_home_add` | Add | ଯୋଡ଼ନ୍ତୁ |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | ଆପଣଙ୍କ ବ୍ୟାଙ୍କ ଖାତା ଏମିତି ଦେଖାଯିବ |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | ଆପଣଙ୍କ କାର୍ଡ, UPI ଓ ଆପ୍ ଲଗଇନ୍ ବି ଏଠାରେ ହିଁ ରହିବ |  |
| `scr_home_group_other` | Other | ଅନ୍ୟାନ୍ୟ |  |
| `scr_home_no_results` | Nothing matches your search | ଆପଣଙ୍କ ଖୋଜାରେ କିଛି ମିଳିଲା ନାହିଁ |  |
| `scr_home_search_hint` | Search your vault | ଆପଣଙ୍କ ସିନ୍ଦୁକରେ ଖୋଜନ୍ତୁ |  |
| `scr_home_tab_authenticator` | Authenticator | କୋଡ୍ |  |
| `scr_home_tab_home` | Home | ମୂଳପୃଷ୍ଠା |  |
| `scr_home_tab_settings` | Settings | ସେଟିଂ |  |
| `scr_home_title` | Home | ମୂଳପୃଷ୍ଠା |  |
| `scr_language_continue` | Continue | ଆଗକୁ ଯାଆନ୍ତୁ |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | ଆପଣଙ୍କ ଭାଷା ବାଛନ୍ତୁ |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | କପି କରିବାକୁ ବଟନ୍ ଦବାନ୍ତୁ · 30 ସେକେଣ୍ଡରେ ମିଳାଇଯିବ |  |
| `scr_login_helper_channel` | Login helper | ଲଗଇନ୍ ସହାୟକ |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s ରେ ଲଗଇନ୍ ହେଉଛି |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | ଏବେ ହିଁ ବ୍ୟାକଅପ୍ ଫୋଲ୍ଡର ସେଟ୍ କରନ୍ତୁ |  |
| `scr_quickunlock_enable` | Turn on | ଚାଲୁ କରନ୍ତୁ |  |
| `scr_quickunlock_skip` | Not now | ଏବେ ନୁହେଁ |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | ଆଙ୍ଗୁଠି ଚିହ୍ନ କିମ୍ବା ମୁହଁରେ ଖୋଲନ୍ତୁ |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s ର ତାରିଖ ପାଖେଇ ଆସିଛି · Zerokosh ଖୋଲନ୍ତୁ |  |
| `scr_reminder_channel` | Renewal reminders | ନବୀକରଣ ମନେପକାଇବା |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh ମନେ ପକାଉଛି |  |
| `scr_settings_about` | About | ପରିଚୟ |  |
| `scr_settings_allow_screenshots` | Allow screenshots | ସ୍କ୍ରିନସଟ୍ ନେବାକୁ ଦିଅନ୍ତୁ |  |
| `scr_settings_autofill` | Autofill service | ଅଟୋଫିଲ୍ ସେବା |  |
| `scr_settings_autofill_off` | Not set up | ସେଟ୍ ହୋଇନାହିଁ |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | ଉପଲବ୍ଧ ନାହିଁ |  |
| `scr_settings_autolock` | Lock when I leave the app | ଆପ୍ ଛାଡ଼ିବା ମାତ୍ରେ ତାଲା ପକାଅ |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 ମିନିଟ୍ ପରେ |  |
| `scr_settings_autolock_immediately` | Immediately | ସଙ୍ଗେ ସଙ୍ଗେ |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d ମିନିଟ୍ ପରେ |  |
| `scr_settings_change_passphrase` | Change passphrase | ପାସ୍‌ଫ୍ରେଜ୍ ବଦଳାନ୍ତୁ |  |
| `scr_settings_current_passphrase` | Current passphrase | ବର୍ତ୍ତମାନର ପାସ୍‌ଫ୍ରେଜ୍ |  |
| `scr_settings_export` | Export | ରପ୍ତାନୀ କରନ୍ତୁ |  |
| `scr_settings_import` | Import passwords | ପାସୱାର୍ଡ ଆମଦାନୀ କରନ୍ତୁ |  |
| `scr_settings_language` | Language | ଭାଷା |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | ନୂଆ ପାସ୍‌ଫ୍ରେଜ୍ (ଅତି କମ୍‌ରେ 10 ଅକ୍ଷର) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | ନୂଆ ରିକଭରି ଚାବି ନିଅନ୍ତୁ |  |
| `scr_settings_passphrase_changed` | Passphrase changed | ପାସ୍‌ଫ୍ରେଜ୍ ବଦଳିଲା |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | ଆଙ୍ଗୁଠି ଚିହ୍ନ / ମୁହଁ ଅନ୍‌ଲକ୍ |  |
| `scr_settings_security_info` | How your data is protected | ଆପଣଙ୍କ ତଥ୍ୟ କିପରି ସୁରକ୍ଷିତ |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | ବ୍ୟାକଅପ୍ ଓ ସିଙ୍କ ଫୋଲ୍ଡର |  |
| `scr_settings_sync_not_set` | Not backed up | ବ୍ୟାକଅପ୍ ନାହିଁ |  |
| `scr_settings_title` | Settings | ସେଟିଂ |  |
| `se_title` | Not saved | ସଞ୍ଚିତ ହେଲା ନାହିଁ |  |
| `st_active_folder` | Active Folder | ଚାଲୁ ଫୋଲ୍ଡର |  |
| `st_active_value` | Active · %1$s | ଚାଲୁ · %1$s |  |
| `st_backing_up` | Backing up vault… | ସିନ୍ଦୁକର ବ୍ୟାକଅପ୍ ହେଉଛି… |  |
| `st_backup_now` | Backup Now | ଏବେ ବ୍ୟାକଅପ୍ ନିଅନ୍ତୁ |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | ବ୍ୟାକଅପ୍ ଓ ସିଙ୍କ ଫୋଲ୍ଡର |  |
| `st_change_folder` | Change Folder | ଫୋଲ୍ଡର ବଦଳାନ୍ତୁ |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | ନୂଆ ପାସ୍‌ଫ୍ରେଜ୍ ପୁଣି ଲେଖନ୍ତୁ |  |
| `st_connected_folder` | Connected folder: %1$s | ଯୋଡ଼ାଯାଇଥିବା ଫୋଲ୍ଡର: %1$s |  |
| `st_disconnect` | Disconnect | ହଟାନ୍ତୁ |  |
| `st_done` | Done | ହୋଇଗଲା |  |
| `st_export_kosh` | Export encrypted .kosh | ଏନ୍‌କ୍ରିପ୍ଟ .kosh ରପ୍ତାନୀ |  |
| `st_folder_fallback` | Folder | ଫୋଲ୍ଡର |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | ପାସଫ୍ରେଜ ଭୁଲିଗଲେ? |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | ଆପଣଙ୍କ ଆଙ୍ଗୁଠି ଚିହ୍ନରେ ନୂଆ ରଖନ୍ତୁ |  |
| `st_generate` | Generate | ତିଆରି କରନ୍ତୁ |  |
| `st_group_about` | About | ପରିଚୟ |  |
| `st_group_appearance` | Appearance | ରୂପ |  |
| `st_group_security` | Security | ସୁରକ୍ଷା |  |
| `st_group_sync` | Sync | ସିଙ୍କ |  |
| `st_import_kosh` | Import a .kosh backup | .kosh ବ୍ୟାକଅପ୍ ଆମଦାନୀ |  |
| `st_import_other` | Import from another password manager | ଅନ୍ୟ ପାସୱାର୍ଡ ମ୍ୟାନେଜରରୁ ଆମଦାନୀ |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | ଲାଇସେନ୍ସ |  |
| `st_logos_by` | Logos provided by | ଲୋଗୋ ଦେଇଥିବା |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | ଏହାକୁ ଅଫଲାଇନ୍ ରଖନ୍ତୁ। ପୁରୁଣା ରିକଭରି ଚାବି ଆଉ ବୈଧ ନୁହେଁ। |  |
| `st_new_recovery_result` | Your new Recovery Key: | ଆପଣଙ୍କ ନୂଆ ରିକଭରି ଚାବି: |  |
| `st_subtitle` | Your rules. | ଆପଣଙ୍କ ନିୟମ। |  |
| `st_theme` | Theme | ଥିମ୍ |  |
| `st_theme_dark` | Dark | ଗାଢ଼ |  |
| `st_theme_light` | Light | ହାଲୁକା |  |
| `st_theme_system` | System | ସିଷ୍ଟମ୍ |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | ସିନ୍ଦୁକ %1$s ରେ ସଞ୍ଚିତ ହେଲା! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | ବ୍ୟାକଅପ୍ ହେଲା ନାହିଁ — ଫୋଲ୍ଡରର ଅନୁମତି ଦେଖନ୍ତୁ |  |
| `st_toast_disconnected` | Backup folder disconnected | ବ୍ୟାକଅପ୍ ଫୋଲ୍ଡର ହଟାଗଲା |  |
| `st_toast_export_failed` | Export failed | ରପ୍ତାନୀ ହେଲା ନାହିଁ |  |
| `st_toast_exported` | Encrypted vault exported | ଏନ୍‌କ୍ରିପ୍ଟ ସିନ୍ଦୁକ ରପ୍ତାନୀ ହେଲା |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | ବ୍ୟାକଅପ୍ ଫୋଲ୍ଡର ଯୋଡ଼ାଗଲା ଆଉ ସିନ୍ଦୁକ %1$s ରେ ସଞ୍ଚିତ ହେଲା! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | ବ୍ୟାକଅପ୍ ଫୋଲ୍ଡର ଯୋଡ଼ାଗଲା: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | ଫୋଲ୍ଡର ଯୋଡ଼ିହେଲା ନାହିଁ: %1$s |  |
| `st_vault_review` | Vault review | ସିନ୍ଦୁକ ଯାଞ୍ଚ |  |
| `st_vault_review_detail` | Reused, weak, expiring | ପୁଣି ବ୍ୟବହାର ହୋଇଥିବା, ଦୁର୍ବଳ, ସରିଯାଉଥିବା |  |
| `tab_codes` | Codes | କୋଡ୍ |  |
| `tab_settings` | Settings | ସେଟିଂ |  |
| `tab_templates` | Templates | ଟେମ୍ପ୍ଲେଟ୍ |  |
| `tab_vault` | Vault | ସିନ୍ଦୁକ |  |
| `time_days` | %1$dd ago | %1$d ଦି ପୂର୍ବେ |  |
| `time_hours` | %1$dh ago | %1$d ଘ ପୂର୍ବେ |  |
| `time_just_now` | just now | ଏବେ ହିଁ |  |
| `time_minutes` | %1$dm ago | %1$d ମି ପୂର୍ବେ |  |
| `time_months` | %1$dmo ago | %1$d ମାସ ପୂର୍ବେ |  |
| `time_years` | %1$dy ago | %1$d ବର୍ଷ ପୂର୍ବେ |  |
| `tpl_aadhaar_card` | Aadhaar Card | ଆଧାର |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | ଆଧାର ସଂଖ୍ୟା |  |
| `tpl_aadhaar_card_address` | Address | ଆଧାରରେ ଠିକଣା |  |
| `tpl_aadhaar_card_dob` | Dob | ଜନ୍ମ ତାରିଖ |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | ସ୍କାନ୍ କରାଯାଇଥିବା ନକଲ |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | ଯୋଡ଼ାଯାଇଥିବା ମୋବାଇଲ୍ |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar ପାସକୋଡ୍ |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | ଆଧାରରେ ନାମ |  |
| `tpl_aadhaar_card_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_app_profile` | App Profile | ଆପ୍ ପ୍ରୋଫାଇଲ୍ |  |
| `tpl_app_profile_app_name` | App name | ଆପ୍‌ର ନାମ |  |
| `tpl_app_profile_gift_cards` | Gift cards | ଗିଫ୍ଟ କାର୍ଡ |  |
| `tpl_app_profile_membership` | Membership | ସଦସ୍ୟତା |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | ସଦସ୍ୟତା ନବୀକରଣ |  |
| `tpl_app_profile_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_app_profile_password_if_any` | Password (if any) | ପାସୱାର୍ଡ (ଥିଲେ) |  |
| `tpl_app_profile_registered_email` | Registered email | ପଞ୍ଜୀକୃତ ଇମେଲ୍ |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | ପଞ୍ଜୀକୃତ ମୋବାଇଲ୍ |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | ୱାଲେଟ୍ ପିନ୍ |  |
| `tpl_bank_account` | Bank Account | ବ୍ୟାଙ୍କ ଖାତା |  |
| `tpl_bank_account_account_number` | Account number | ଖାତା ସଂଖ୍ୟା |  |
| `tpl_bank_account_account_type` | Account type | ଖାତାର ପ୍ରକାର |  |
| `tpl_bank_account_bank_name` | Bank name | ବ୍ୟାଙ୍କର ନାମ |  |
| `tpl_bank_account_branch` | Branch | ଶାଖା |  |
| `tpl_bank_account_customer_id` | Customer id | ଗ୍ରାହକ ID |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC କୋଡ୍ |  |
| `tpl_bank_account_login_password` | Login password | ଲଗଇନ୍ ପାସୱାର୍ଡ |  |
| `tpl_bank_account_micr` | MICR code | MICR କୋଡ୍ |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | ନେଟ୍-ବ୍ୟାଙ୍କିଂ ୟୁଜର ID |  |
| `tpl_bank_account_nominee` | Nominee | ନାମିନୀ |  |
| `tpl_bank_account_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_bank_account_profile_password` | Profile password | ପ୍ରୋଫାଇଲ୍ ପାସୱାର୍ଡ |  |
| `tpl_bank_account_registered_email` | Registered email | ପଞ୍ଜୀକୃତ ଇମେଲ୍ |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | ପଞ୍ଜୀକୃତ ମୋବାଇଲ୍ |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | କାରବାର ପାସୱାର୍ଡ |  |
| `tpl_card` | Card | କାର୍ଡ |  |
| `tpl_card_atm_pin` | ATM PIN | ATM ପିନ୍ |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | ବିଲିଂ ସାଇକଲ୍ ଦିନ |  |
| `tpl_card_card_network` | Card network | ନେଟୱାର୍କ |  |
| `tpl_card_card_number` | Card number | କାର୍ଡ ନମ୍ବର |  |
| `tpl_card_card_portal_login` | Card portal login | କାର୍ଡ ପୋର୍ଟାଲ୍ ଲଗଇନ୍ |  |
| `tpl_card_card_portal_password` | Card portal password | କାର୍ଡ ପୋର୍ଟାଲ୍ ପାସୱାର୍ଡ |  |
| `tpl_card_card_type` | Card type | କାର୍ଡର ପ୍ରକାର |  |
| `tpl_card_card_variant` | Card variant | କାର୍ଡ ଭାରିଏଣ୍ଟ |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | ମିଆଦ |  |
| `tpl_card_linked_account` | Linked account | ଯୋଡ଼ାଯାଇଥିବା ଖାତା |  |
| `tpl_card_name_on_card` | Name on card | କାର୍ଡରେ ନାମ |  |
| `tpl_card_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_demat` | Demat | ଡିମ୍ୟାଟ୍ |  |
| `tpl_demat_api_key` | API key | API କୀ |  |
| `tpl_demat_api_secret` | API secret | API ସିକ୍ରେଟ୍ |  |
| `tpl_demat_bo_id` | BO ID | BO ID |  |
| `tpl_demat_broker` | Broker | ବ୍ରୋକର୍ |  |
| `tpl_demat_cdsl_tpin` | CDSL TPIN | CDSL TPIN |  |
| `tpl_demat_client_id` | Client id | କ୍ଲାଏଣ୍ଟ ID |  |
| `tpl_demat_depository` | Depository | ଡିପୋଜିଟରୀ |  |
| `tpl_demat_dp_id` | DP ID | DP ID |  |
| `tpl_demat_login_password` | Login password | ଲଗଇନ୍ ପାସୱାର୍ଡ |  |
| `tpl_demat_mf_folios` | Mutual fund folios | ମ୍ୟୁଚୁଆଲ୍ ଫଣ୍ଡ ଫୋଲିଓ |  |
| `tpl_demat_mpin` | MPIN | MPIN |  |
| `tpl_demat_nominee` | Nominee | ନାମିନୀ |  |
| `tpl_demat_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_digilocker` | Digilocker | DigiLocker |  |
| `tpl_digilocker_login_username` | Login username | ୟୁଜରନେମ୍ |  |
| `tpl_digilocker_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_digilocker_portal_password` | Portal password | ପାସୱାର୍ଡ |  |
| `tpl_digilocker_security_pin` | Security pin | ସୁରକ୍ଷା ପିନ୍ |  |
| `tpl_driving_license` | Driving License | ଡ୍ରାଇଭିଂ ଲାଇସେନ୍ସ |  |
| `tpl_driving_license_dl_number` | Dl number | ଲାଇସେନ୍ସ ନମ୍ବର |  |
| `tpl_driving_license_dob` | Dob | ଜନ୍ମ ତାରିଖ |  |
| `tpl_driving_license_expiry_date` | Expiry date | ଏହି ତାରିଖ ପର୍ଯ୍ୟନ୍ତ ବୈଧ |  |
| `tpl_driving_license_file_copy` | Scanned copy | ସ୍କାନ୍ କରାଯାଇଥିବା ନକଲ |  |
| `tpl_driving_license_issue_date` | Issue date | ଜାରି ତାରିଖ |  |
| `tpl_driving_license_name_on_dl` | Name on dl | ଲାଇସେନ୍ସରେ ନାମ |  |
| `tpl_driving_license_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_driving_license_rto_location` | Rto location | RTO |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | ଯାନ ଶ୍ରେଣୀ |  |
| `tpl_epf_pension` | Epf Pension | EPF / ପେନସନ୍ |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | ଯୋଡ଼ାଯାଇଥିବା ମୋବାଇଲ୍ |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | EPF ରେ ନାମ |  |
| `tpl_epf_pension_nominee` | Nominee | ନାମିନୀ |  |
| `tpl_epf_pension_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_epf_pension_password` | Password | ପାସୱାର୍ଡ |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | PF ସଦସ୍ୟ ID |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO ପାସୱାର୍ଡ |  |
| `tpl_epf_pension_scheme` | Scheme | ଯୋଜନା |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | ସରକାରୀ ପରିଚୟ ପତ୍ର |  |
| `tpl_gov_id_expiry` | Expiry | ମିଆଦ |  |
| `tpl_gov_id_file_copy` | Scanned copy | ସ୍କାନ୍ କରାଯାଇଥିବା ନକଲ |  |
| `tpl_gov_id_id_kind` | ID type | ପରିଚୟ ପତ୍ରର ପ୍ରକାର |  |
| `tpl_gov_id_id_number` | ID number | ପରିଚୟ ସଂଖ୍ୟା |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | ପତ୍ର ଅନୁସାରେ ନାମ |  |
| `tpl_gov_id_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_gov_id_portal_login` | Portal login | ପୋର୍ଟାଲ୍ ଲଗଇନ୍ |  |
| `tpl_gov_id_portal_password` | Portal password | ପୋର୍ଟାଲ୍ ପାସୱାର୍ଡ |  |
| `tpl_insurance` | Insurance | ବୀମା |  |
| `tpl_insurance_agent_contact` | Agent contact | ଏଜେଣ୍ଟ ସମ୍ପର୍କ |  |
| `tpl_insurance_commencement_date` | Commencement date | ଆରମ୍ଭ ତାରିଖ |  |
| `tpl_insurance_insurer` | Insurer | ବୀମା କମ୍ପାନୀ |  |
| `tpl_insurance_maturity_date` | Maturity date | ମିଆଦପୂର୍ତ୍ତି ତାରିଖ |  |
| `tpl_insurance_nominee` | Nominee | ନାମିନୀ |  |
| `tpl_insurance_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_insurance_policy_number` | Policy number | ପଲିସି ନମ୍ବର |  |
| `tpl_insurance_policy_term` | Policy term | ପଲିସିର ଅବଧି |  |
| `tpl_insurance_policy_type` | Policy type | ପଲିସିର ପ୍ରକାର |  |
| `tpl_insurance_portal_login` | Portal login | ପୋର୍ଟାଲ୍ ଲଗଇନ୍ |  |
| `tpl_insurance_portal_password` | Portal password | ପୋର୍ଟାଲ୍ ପାସୱାର୍ଡ |  |
| `tpl_insurance_premium_amount` | Premium amount | ପ୍ରିମିୟମ୍ ରାଶି |  |
| `tpl_insurance_premium_due_date` | Premium due date | ପ୍ରିମିୟମ୍ ତାରିଖ |  |
| `tpl_insurance_premium_mode` | Premium mode | ପ୍ରିମିୟମ୍ କିପରି ଦିଅନ୍ତି |  |
| `tpl_insurance_sum_assured` | Sum assured | ବୀମା ରାଶି |  |
| `tpl_login` | Login | ଲଗଇନ୍ |  |
| `tpl_login_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_login_password` | Password | ପାସୱାର୍ଡ |  |
| `tpl_login_recovery_codes` | Recovery codes | ରିକଭରି କୋଡ୍ |  |
| `tpl_login_username` | Username | ୟୁଜରନେମ୍ |  |
| `tpl_login_website` | Website | ୱେବସାଇଟ୍ |  |
| `tpl_pan_card` | Pan Card | PAN କାର୍ଡ |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | ଆଧାର ସହ ଯୋଡ଼ାଯାଇଛି |  |
| `tpl_pan_card_dob` | Dob | ଜନ୍ମ ତାରିଖ |  |
| `tpl_pan_card_e_filing_password` | E filing password | ଇ-ଫାଇଲିଂ ପାସୱାର୍ଡ |  |
| `tpl_pan_card_fathers_name` | Fathers name | ପିତାଙ୍କ ନାମ |  |
| `tpl_pan_card_file_copy` | Scanned copy | ସ୍କାନ୍ କରାଯାଇଥିବା ନକଲ |  |
| `tpl_pan_card_name_on_pan` | Name on pan | PAN ରେ ନାମ |  |
| `tpl_pan_card_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_pan_card_pan_number` | Pan number | PAN |  |
| `tpl_passkey` | Passkey | ପାସକୀ |  |
| `tpl_passkey_credential_id` | Credential ID | କ୍ରେଡେନସିଆଲ ID |  |
| `tpl_passkey_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_passkey_private_key` | Private key | ବ୍ୟକ୍ତିଗତ କୀ |  |
| `tpl_passkey_sign_count` | Sign count | ସାଇନ କାଉଣ୍ଟ |  |
| `tpl_passkey_user_handle` | User handle | ୟୁଜର ହାଣ୍ଡଲ |  |
| `tpl_passkey_username` | Username | ୟୁଜରନେମ୍ |  |
| `tpl_passkey_website` | Website | ୱେବସାଇଟ୍ |  |
| `tpl_passport` | Passport | ପାସପୋର୍ଟ |  |
| `tpl_passport_dob` | Dob | ଜନ୍ମ ତାରିଖ |  |
| `tpl_passport_expiry_date` | Expiry date | ମିଆଦ ସରିବା ତାରିଖ |  |
| `tpl_passport_file_copy` | Scanned copy | ସ୍କାନ୍ କରାଯାଇଥିବା ନକଲ |  |
| `tpl_passport_given_names` | Given names | ଦିଆଯାଇଥିବା ନାମ |  |
| `tpl_passport_issue_date` | Issue date | ଜାରି ତାରିଖ |  |
| `tpl_passport_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_passport_passport_number` | Passport number | ପାସପୋର୍ଟ ନମ୍ବର |  |
| `tpl_passport_place_of_issue` | Place of issue | ଜାରି ସ୍ଥାନ |  |
| `tpl_passport_portal_login` | Portal login | Passport Seva ଲଗଇନ୍ |  |
| `tpl_passport_portal_password` | Portal password | Passport Seva ପାସୱାର୍ଡ |  |
| `tpl_passport_surname` | Surname | ପଦବୀ |  |
| `tpl_secure_note` | Secure Note | ସୁରକ୍ଷିତ ଟିପ୍ପଣୀ |  |
| `tpl_secure_note_attachment` | Attachment | ସଂଲଗ୍ନକ |  |
| `tpl_secure_note_body` | Note | ଟିପ୍ପଣୀ |  |
| `tpl_shopping` | Shopping | ଶପିଂ ଖାତା |  |
| `tpl_shopping_gift_card_code` | Gift card code | ଗିଫ୍ଟ କାର୍ଡ କୋଡ୍ |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | ଗିଫ୍ଟ କାର୍ଡ ପିନ୍ |  |
| `tpl_shopping_membership_id` | Membership id | ସଦସ୍ୟତା ID |  |
| `tpl_shopping_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_shopping_password` | Password | ପାସୱାର୍ଡ |  |
| `tpl_shopping_registered_email` | Registered email | ପଞ୍ଜୀକୃତ ଇମେଲ୍ |  |
| `tpl_shopping_registered_mobile` | Registered mobile | ପଞ୍ଜୀକୃତ ମୋବାଇଲ୍ |  |
| `tpl_shopping_wallet_pin` | Wallet pin | ୱାଲେଟ୍ ପିନ୍ |  |
| `tpl_telecom` | Telecom | ମୋବାଇଲ୍ ଓ ଇଣ୍ଟରନେଟ୍ |  |
| `tpl_telecom_account_number` | Account number | ଖାତା ସଂଖ୍ୟା |  |
| `tpl_telecom_circle` | Circle | ସର୍କଲ୍ |  |
| `tpl_telecom_mobile_number` | Mobile number | ମୋବାଇଲ୍ ନମ୍ବର |  |
| `tpl_telecom_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_telecom_operator` | Operator | କମ୍ପାନୀ |  |
| `tpl_telecom_plan_type` | Plan type | ପ୍ଲାନର ପ୍ରକାର |  |
| `tpl_telecom_portal_password` | Portal password | ପୋର୍ଟାଲ୍ ପାସୱାର୍ଡ |  |
| `tpl_telecom_puk` | PUK code | PUK କୋଡ୍ |  |
| `tpl_telecom_renewal_date` | Renewal date | ରିଚାର୍ଜ ତାରିଖ |  |
| `tpl_telecom_sim_number` | Sim number | ସିମ୍ ନମ୍ବର (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | ସିମ୍ ପିନ୍ |  |
| `tpl_transit` | Transit | ଯାତ୍ରା ପାସ୍ |  |
| `tpl_transit_login_password` | Login password | ଲଗଇନ୍ ପାସୱାର୍ଡ |  |
| `tpl_transit_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_transit_operator_name` | Operator name | କମ୍ପାନୀ |  |
| `tpl_transit_registered_email` | Registered email | ପଞ୍ଜୀକୃତ ଇମେଲ୍ |  |
| `tpl_transit_registered_mobile` | Registered mobile | ପଞ୍ଜୀକୃତ ମୋବାଇଲ୍ |  |
| `tpl_transit_smart_card_number` | Smart card number | ସ୍ମାର୍ଟ କାର୍ଡ ନମ୍ବର |  |
| `tpl_transit_wallet_pin` | Wallet pin | ୱାଲେଟ୍ ପିନ୍ |  |
| `tpl_travel_booking` | Travel Booking | ଯାତ୍ରା ବୁକିଂ |  |
| `tpl_travel_booking_account_username` | Account username | ୟୁଜରନେମ୍ |  |
| `tpl_travel_booking_login_password` | Login password | ଲଗଇନ୍ ପାସୱାର୍ଡ |  |
| `tpl_travel_booking_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_travel_booking_provider` | Provider | କମ୍ପାନୀ |  |
| `tpl_travel_booking_registered_email` | Registered email | ପଞ୍ଜୀକୃତ ଇମେଲ୍ |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | ପଞ୍ଜୀକୃତ ମୋବାଇଲ୍ |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | ୱାଲେଟ୍ ପିନ୍ |  |
| `tpl_upi` | UPI | UPI |  |
| `tpl_upi_app_name` | App name | UPI ଆପ୍ |  |
| `tpl_upi_apps_used` | Apps used | କେଉଁ ଆପ୍‌ରେ ଚାଲୁ ଅଛି |  |
| `tpl_upi_linked_account` | Linked account | ଯୋଡ଼ାଯାଇଥିବା ଖାତା |  |
| `tpl_upi_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_upi_upi_id` | UPI ID | UPI ID |  |
| `tpl_upi_upi_pin` | UPI PIN | UPI ପିନ୍ |  |
| `tpl_utility` | Utility | ବିଲ୍ ଓ ସଂଯୋଗ |  |
| `tpl_utility_account_holder` | Account holder | ଖାତାଧାରୀ |  |
| `tpl_utility_consumer_number` | Consumer number | ଉପଭୋକ୍ତା ସଂଖ୍ୟା |  |
| `tpl_utility_due_day` | Bill due day | ବିଲ୍ ଦେବା ଦିନ |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag ID |  |
| `tpl_utility_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_utility_portal_login` | Portal login | ପୋର୍ଟାଲ୍ ଲଗଇନ୍ |  |
| `tpl_utility_portal_password` | Portal password | ପୋର୍ଟାଲ୍ ପାସୱାର୍ଡ |  |
| `tpl_utility_provider` | Provider | ସେବା ଦେଉଥିବା କମ୍ପାନୀ |  |
| `tpl_utility_utility_kind` | Utility kind | କେଉଁଥିର ବିଲ୍ |  |
| `tpl_utility_vehicle_number` | Vehicle number | ଗାଡ଼ି ନମ୍ବର |  |
| `tpl_utility_wifi_password` | Wi-Fi password | Wi-Fi ପାସୱାର୍ଡ |  |
| `tpl_voter_id` | Voter Id | ଭୋଟର ପରିଚୟ |  |
| `tpl_voter_id_constituency` | Constituency | ନିର୍ବାଚନ ମଣ୍ଡଳୀ |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC ନମ୍ବର |  |
| `tpl_voter_id_file_copy` | Scanned copy | ସ୍କାନ୍ କରାଯାଇଥିବା ନକଲ |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | ଭୋଟର ପତ୍ରରେ ନାମ |  |
| `tpl_voter_id_notes` | Notes | ଟିପ୍ପଣୀ |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP ପାସୱାର୍ଡ |  |
| `tr_days_many` | %1$d days left | %1$d ଦିନ ବାକି |  |
| `tr_days_one` | %1$d day left | %1$d ଦିନ ବାକି |  |
| `tr_gone_today` | gone today | ଆଜି ଚାଲିଯିବ |  |
| `tr_restore` | Restore | ଫେରାଇ ଆଣନ୍ତୁ |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | ତା ପରେ ସେଗୁଡ଼ିକ ସବୁଦିନ ପାଇଁ ଚାଲିଯାଏ — ଆଉ କେଉଁଠି ନକଲ ନାହିଁ। |  |
| `ui_hide_passphrase` | Hide passphrase | ପାସ୍‌ଫ୍ରେଜ୍ ଲୁଚାନ୍ତୁ |  |
| `ui_label_count` | %1$s, %2$s | %1$s, %2$s |  |
| `ui_show_passphrase` | Show passphrase | ପାସ୍‌ଫ୍ରେଜ୍ ଦେଖାନ୍ତୁ |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | ଏହି ଫୋନରେ ହିଁ %1$d ରେକର୍ଡ ସହ ମିଳାଯାଇଛି। କିଛି କେଉଁଠାକୁ ପଠାଯାଇ ନାହିଁ। |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | ଏହି ଫୋନରେ ହିଁ %1$d ରେକର୍ଡ ସହ ମିଳାଯାଇଛି। କିଛି କେଉଁଠାକୁ ପଠାଯାଇ ନାହିଁ। |  |
| `vh_count_many` | %1$d things worth a look. | %1$d କଥା ଦେଖିବା ଯୋଗ୍ୟ। |  |
| `vh_count_one` | %1$d thing worth a look. | %1$d କଥା ଦେଖିବା ଯୋଗ୍ୟ। |  |
| `vh_empty` | No reused, weak or expiring credentials. | ପୁଣି ବ୍ୟବହାର ହୋଇଥିବା, ଦୁର୍ବଳ କିମ୍ବା ସରିଯାଉଥିବା ସୂଚନା ନାହିଁ। |  |
| `vh_kind_common` | Commonly guessed | ସହଜରେ ଅନୁମାନ କରାଯାଉଥିବା |  |
| `vh_kind_expiring` | Expiring | ସରିଯାଉଛି |  |
| `vh_kind_reused` | Reused password | ପୁଣି ବ୍ୟବହାର ହୋଇଥିବା ପାସୱାର୍ଡ |  |
| `vh_kind_weak` | Weak | ଦୁର୍ବଳ |  |
| `vh_no_kit_title` | No recovery kit saved | କୌଣସି ରିକଭରି କିଟ ସେଭ ହୋଇନାହିଁ |  |
| `vh_nothing` | Nothing to fix. | ଠିକ୍ କରିବାକୁ କିଛି ନାହିଁ। |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | ଅଫଲାଇନ୍ |  |
| `wl_chip_open` | Open source | ଓପନ୍ ସୋର୍ସ |  |
| `wl_create` | Create a new vault | ନୂଆ ସିନ୍ଦୁକ ତିଆରି କରନ୍ତୁ |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | ଇମେଲ୍ ନାହିଁ · ଖାତା ନାହିଁ · କିଛି ଏହି ଫୋନ ବାହାରକୁ ଯାଏ ନାହିଁ |  |
| `wl_head_1` | Your keys. | ଆପଣଙ୍କ ଚାବି। |  |
| `wl_head_2` | Your device. | ଆପଣଙ୍କ ଫୋନ୍। |  |
| `wl_head_3` | No server. | କୌଣସି ସର୍ଭର ନାହିଁ। |  |
| `wl_restore` | Restore from Recovery Kit | ରିକଭରି କିଟ୍‌ରୁ ଫେରାଇ ଆଣନ୍ତୁ |  |
| `wl_sr_headline` | Your keys. Your device. No server. | ଆପଣଙ୍କ ଚାବି। ଆପଣଙ୍କ ଫୋନ୍। କୌଣସି ସର୍ଭର ନାହିଁ। |  |
