# Gujarati (`gu`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-gu/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Gujarati | ok? |
|---|---|---|---|
| `au_close` | Close | બંધ કરો |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | પસંદ કરેલા ચિત્રમાં કોઈ સાચો TOTP QR કોડ મળ્યો નહીં |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | ખાલીથી શરૂ કરો અને તમારાં ફીલ્ડ જાતે નામ આપો — ટેમ્પલેટ ફક્ત લેબલ ભરે છે, ડેટા ક્યારેય નહીં. |  |
| `hm_close_search` | Close search | શોધ બંધ કરો |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | આ ફોનમાંથી ક્યારેય બહાર જતું નથી. સાચવતી વખતે એન્ક્રિપ્ટેડ. |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | હવે તે ફાઇલ કાઢી નાખો જે તમે હમણાં આયાત કરી. તે તમારા પાસવર્ડની સાદી યાદી છે અને હજી તમારા Downloads માં પડી છે. |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | બધું ફક્ત આ જ ફોન પર ડિક્રિપ્ટ થાય છે. કંઈ પણ અપલોડ થતું નથી, કારણ કે આ ઍપ નેટવર્ક કનેક્શન ખોલી જ શકતી નથી. |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | બેંક ક્યારેય તમારો OTP માંગતી નથી. જે માંગે તે ઠગ છે. |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | કોઈ બેંક અધિકારી તમને સ્ક્રીન શેર કરતી ઍપ ક્યારેય નખાવશે નહીં. |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | તમારો UPI પિન ફક્ત UPI ઍપના કીપેડ માટે છે — કૉલ પર કોઈને કહેશો નહીં. |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | KYC એક દિવસમાં પૂરી થતી નથી. “આજે KYC પૂરી થાય છે” એવા સંદેશા ઠગાઈ છે. |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | પૈસા લેવા માટે ક્યારેય પિન નાખવાની કે QR સ્કૅન કરવાની જરૂર પડતી નથી. |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | વીજળી કપાવાનો SMS, અને તેમાં કોઈનો અંગત નંબર? એ ઠગાઈ છે. |  |
| `nav_close_menu` | Close menu | મેનૂ બંધ કરો |  |
| `nfc_cannot_read` | Cannot read cards | કાર્ડ વાંચી શકાતાં નથી |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | આ ફોનમાં NFC નથી, એટલે તે કાર્ડ વાંચી શકતો નથી. |  |
| `ob_fact_lost_title` | If you lose your keys | જો ચાવીઓ ખોવાઈ જાય |  |
| `ob_fact_network_note` | The app literally cannot phone home | આ ઍપ ક્યાંય સંપર્ક કરી જ શકતી નથી |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | બાયોમેટ્રિક ક્યારેય સુરક્ષિત ચિપની બહાર જતું નથી |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | તમે ચાવી એ જ ફોલ્ડરમાં સાચવી છે જે તમારી એન્ક્રિપ્ટેડ તિજોરી સિંક કરે છે. હવે જેને એ ફોલ્ડર મળશે તેને બંને ભાગ મળી જશે. ચાવી બીજે ક્યાંક રાખો — કાગળ, બીજું ખાતું, અથવા કબાટ. |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | આ તમારી તિજોરી ફાઈલની બાજુમાં જ છે |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH રિકવરી |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | સ્કૅન કરો કે ટાઇપ કરી દો. ફરી ઇન્સ્ટૉલ, ફૅક્ટરી રીસેટ, કે ફોન ખોવાયા પછી પણ કામ કરશે. |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | હમણાં સાચવેલી કિટમાંથી જૂથ %1$d અને જૂથ %2$d ટાઈપ કરો. |  |
| `ob_kit_challenge_hint` | Group %1$d | જૂથ %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | પહેલાં કિટ સાચવો, પછી જૂથ %1$d અને %2$d પાછા ટાઈપ કરો. |  |
| `ob_kit_challenge_title` | Check you actually have it | કિટ ખરેખર તમારી પાસે છે કે નહીં તે તપાસો |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | આ ઉપરની ચાવી સાથે મળતું નથી. |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | કોઈ પણ — Zerokosh પણ — તેને મારા માટે પાછું લાવી શકતું નથી. |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "મેં તેને ઑફલાઇન રાખી લીધું છે. " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | એક જ વાર દેખાય છે, ક્યારેય ખુલ્લા સ્વરૂપે સાચવાતી નથી. અંદર આવી શકતા હો તો સેટિંગ્સમાંથી નવી બનાવો. |  |
| `ob_kit_head_emph` | On paper. | કાગળ પર. |  |
| `ob_kit_head_lead` | "One key. " | "એક ચાવી. " |  |
| `ob_kit_head_tail` | " Never online." | " ક્યારેય ઑનલાઇન નહીં." |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | ન Gmail, ન WhatsApp, ન સ્ક્રીનશૉટ. તિજોરી, બેંક લૉકર, કે સ્ટીલની પ્લેટ. |  |
| `ob_kit_offline_title` | Keep it off the internet | તેને ઇન્ટરનેટથી દૂર રાખો |  |
| `ob_kit_print` | Print | છાપો |  |
| `ob_kit_print_note` | A printer, or Save as PDF | પ્રિન્ટર, અથવા PDF તરીકે સાચવો |  |
| `ob_kit_qr` | QR image | QR ચિત્ર |  |
| `ob_kit_qr_cd` | Recovery key QR code | રિકવરી ચાવીનો QR કોડ |  |
| `ob_kit_qr_note` | To an offline gallery | ઑફલાઇન ગૅલરીમાં |  |
| `ob_kit_regenerate` | Regenerate | નવી બનાવો |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | આ સાચવાયું નહીં. ફરી પ્રયાસ કરો, અથવા બીજી જગ્યા પસંદ કરો. |  |
| `ob_kit_save_pdf` | Save PDF | પીડીએફ સાચવો |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | એક પાનાની છાપવા લાયક કિટ |  |
| `ob_kit_saved` | I\'ve saved my kit | મેં મારી કિટ સાચવી લીધી |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s માં સાચવ્યું |  |
| `ob_kit_sent_to_printer` | Sent to the printer | પ્રિન્ટરને મોકલ્યું |  |
| `ob_kit_skip` | I\'ll do this later | આ પછી કરીશ |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | તમારી તિજોરી ચાલુ રહેશે. કિટ સાચવાય નહીં ત્યાં સુધી Zerokosh યાદ કરાવતું રહેશે. |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | આ જ ફોન પર બની, એક જ વાર દેખાશે. પાસફ્રેઝ ભૂલી જાઓ તો અંદર પાછા આવવાનો આ જ એક રસ્તો છે. |  |
| `ob_kit_working` | Working… | કામ ચાલી રહ્યું છે… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | તિજોરીનાં લેબલ, ટેમ્પલેટ અને ચેતવણીઓ તરત બદલાઈ જશે. સેટિંગમાં ગમે ત્યારે બદલી શકો છો. |  |
| `ob_pass_confirm` | Confirm | ફરીથી લખો |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | આ અમને ક્યારેય દેખાતું નથી. કોઈ રીસેટ લિંક નથી. |  |
| `ob_pass_head_emph` | held only | ફક્ત તમારી |  |
| `ob_pass_head_lead` | "One secret, " | "એક જ રહસ્ય, " |  |
| `ob_pass_head_tail` | " by you." | " પાસે." |  |
| `ob_pass_no_match` | no match | મેળ ખાતું નથી |  |
| `ob_pass_seal` | Seal the vault | તિજોરી બંધ કરો |  |
| `ob_pass_sealing` | Sealing… | બંધ કરાઈ રહ્યું છે… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | ત્રણ-ચાર અસંબંધિત શબ્દો, એક ચતુર શબ્દ કરતાં સારા છે. આ સ્ક્રીનમાંથી કંઈ બહાર જતું નથી. |  |
| `ob_pass_tab_passphrase` | Passphrase | પાસફ્રેઝ |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 અંકનો પિન |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | તમારી આંગળીની છાપ ફોનની સુરક્ષિત ચિપની અંદર રહે છે. તે આ ફોનમાંથી ક્યારેય બહાર જતી નથી. |  |
| `ob_trust_continue` | I understand · Continue | સમજ્યો · આગળ વધો |  |
| `ob_trust_head_emph` | don\'t | નથી |  |
| `ob_trust_head_lead` | "Exactly what we " | "અમે ખરેખર શું " |  |
| `ob_trust_head_tail` | " know." | " જાણતા." |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | પાસફ્રેઝ ભૂલી ગયા અને રિકવરી કિટ પણ ખોવાઈ, તો તિજોરી બંધ જ રહેશે — તમારા માટે પણ, અમારા માટે પણ, કોઈના માટે પણ. |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "ચાવીઓ તમારી પાસે છે. " |  |
| `ob_trust_stat_files` | .kosh file on device | ફોનમાં .kosh ફાઇલ |  |
| `ob_trust_stat_servers` | servers contacted | સર્વર સાથે સંપર્ક |  |
| `ob_trust_stat_trackers` | trackers or SDKs | ટ્રૅકર કે SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | આ એક વાર વાંચી લો. આખી સુરક્ષા વ્યવસ્થા આ જ છે, સીધી ભાષામાં. |  |
| `ob_trust_tag_audited` | Audited build | ઑડિટ થયેલું બિલ્ડ |  |
| `ob_trust_tag_reproducible` | Reproducible APK | ફરીથી બનાવી શકાય એવું APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | તમે આંગળીથી ખોલો છો. આંગળી ક્યારેક કામ કરવાનું બંધ કરે, તો આ જ તમને અંદર લાવશે — એટલે તપાસી લેવું સારું. |  |
| `pc_confirm` | Check | તપાસો |  |
| `pc_correct` | Still correct. Nothing to do. | હજી બરાબર છે. કંઈ કરવાની જરૂર નથી. |  |
| `pc_forgot` | I cannot remember it | મને યાદ નથી આવતું |  |
| `pc_later` | Not now | હમણાં નહીં |  |
| `pc_reset_action` | Set new passphrase | નવો પાસફ્રેઝ મૂકો |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | તમારી આંગળી આ તિજોરી ખોલી શકે છે, એટલે એ જ નવો પાસફ્રેઝ પણ મૂકી શકે છે — રિકવરી કિટની જરૂર નથી. ખાતરી માટે ફરી એક વાર પૂછાશે. |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | પાસફ્રેઝ બદલાયો. ઝડપી અનલોક ફરી ગોઠવાયું. |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | આ થયું નહીં. તમારો જૂનો પાસફ્રેઝ જ ચાલુ છે. |  |
| `pc_reset_title` | Set a new passphrase | નવો પાસફ્રેઝ મૂકો |  |
| `pc_title` | Do you still remember your passphrase? | તમને હજી તમારો પાસફ્રેઝ યાદ છે? |  |
| `pc_wrong` | That is not it. You can set a new one instead. | આ એ નથી. એના બદલે નવો મૂકી શકો છો. |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | આ રેકોર્ડ માટે યાદ રાખેલા %1$d મૂલ્યો કાઢી નાખવામાં આવશે. આ પાછું લાવી શકાતું નથી. |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh તે લૉગિન સાચવી શક્યું નહીં. |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | ભરવા માટે Zerokosh ખોલો |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | આ એક જ પાસફ્રેઝ બધું બંધ રાખે છે. કંઈક લાંબું પસંદ કરો, જે ફક્ત તમે જ જાણતા હો. |  |
| `scr_create_button` | Lock it in | બંધ કરી દો |  |
| `scr_create_confirm_hint` | Type it again | ફરીથી લખો |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | પાસફ્રેઝ (ઓછામાં ઓછા 10 અક્ષર) |  |
| `scr_create_mismatch` | The two entries don\'t match | બંને સરખા નથી |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 અંકનો પિન |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | તેના બદલે 6 અંકનો પિન રાખો |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | પિન માટે ફિંગરપ્રિન્ટ કે ફેસ અનલૉક વાળો ફોન જોઈએ. કૃપા કરીને પાસફ્રેઝ પસંદ કરો. |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | પિન એટલે ચાલે છે કારણ કે આ ફોન તેને પોતાની સુરક્ષા ચિપ અને તમારી આંગળી કે ચહેરાથી બચાવે છે. |  |
| `scr_create_strength_fair` | Fair | ઠીકઠાક |  |
| `scr_create_strength_good` | Good | સારું |  |
| `scr_create_strength_strong` | Strong | મજબૂત |  |
| `scr_create_strength_weak` | Weak | નબળું |  |
| `scr_create_title` | Create your passphrase | તમારો પાસફ્રેઝ બનાવો |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | ઓછામાં ઓછા 10 અક્ષર રાખો — જેટલું લાંબું, એટલું મજબૂત |  |
| `scr_create_working` | Preparing your vault… | તમારી તિજોરી તૈયાર થઈ રહી છે… |  |
| `scr_detail_delete` | Delete | કાઢી નાખો |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | તે 30 દિવસ સુધી “તાજેતરમાં કાઢેલા”માં રહેશે, અને તમારા બીજા ફોન સાથે સિંક થતાં નીકળી જશે. |  |
| `scr_detail_delete_confirm_title` | Delete this record? | આ રેકોર્ડ કાઢી નાખવો? |  |
| `scr_detail_delete_confirm_yes` | Delete | કાઢી નાખો |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | સીક્રેટ કે otpauth:// લિંક ચોંટાડો |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | તમારી આંગળી કે ચહેરો વાપરો |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh ખોલો |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | બહુ વાર ખોટી કોશિશ થઈ. %1$d સેકન્ડ રાહ જુઓ. |  |
| `scr_lock_hint` | Passphrase | પાસફ્રેઝ |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | આ રિકવરી ચાવી બરાબર નથી — એક એક અક્ષર મેળવીને જુઓ |  |
| `scr_lock_title` | Vault is locked | તિજોરી બંધ છે |  |
| `scr_lock_unlock` | Unlock | ખોલો |  |
| `scr_lock_use_passphrase` | Use passphrase | પાસફ્રેઝ વાપરો |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | કૃપા કરીને એક વાર પાસફ્રેઝથી ખોલો |  |
| `scr_lock_use_recovery` | Use Recovery Key | રિકવરી ચાવી વાપરો |  |
| `scr_lock_wrong` | Wrong passphrase | પાસફ્રેઝ ખોટો છે |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | જો ક્યારેય પાસફ્રેઝ ભૂલી જાઓ, તો અંદર પાછા આવવાનો ફક્ત આ જ એક રસ્તો છે. અમે તેને રીસેટ કરી શકતા નથી — કોઈ કરી શકતું નથી. |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | મેં તે લખી લીધું છે અને સલામત જગ્યાએ મૂક્યું છે |  |
| `scr_recovery_done` | Continue | આગળ વધો |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | આ ચાવી એક જ વાર દેખાય છે. જ્યાં સુધી તમે તિજોરી ખોલી શકો છો, ત્યાં સુધી સેટિંગ્સમાંથી ગમે ત્યારે નવી બનાવી શકો છો. |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | આ પાનું તમારા મિલકતના કાગળો કે બીજા જરૂરી દસ્તાવેજો સાથે રાખો. જેની પાસે આ ચાવી હશે તે તમારી તિજોરી ખોલી શકે છે — તેને લૉકરની ચાવીની જેમ સાચવો. |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | રિકવરી કિટ પીડીએફ સાચવાઈ |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh રિકવરી કિટ |  |
| `scr_recovery_save_pdf` | Save as PDF | પીડીએફમાં સાચવો |  |
| `scr_recovery_title` | Your Recovery Key | તમારી રિકવરી ચાવી |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | તમે જે કંઈ સાચવો છો તે તમારા ફોનની એક બંધ ફાઇલમાં રહે છે. તે ક્યારેય અમારા સુધી આવતું નથી — અમારી પાસે તેને રાખવાની જગ્યા જ નથી. |  |
| `scr_trust_card1_title` | Your data stays on this device | તમારો ડેટા આ જ ફોનમાં રહે છે |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | ન કોઈ Zerokosh ખાતું, ન ક્લાઉડ, ન સાઇન-અપ. આ ફક્ત તમે જ ખોલી શકો છો. અમે પણ નહીં. |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | અમારું કોઈ સર્વર નથી — ન હેક થવા જેવું કંઈ, ન વેચવા જેવું |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | ન કોઈ સભ્યપદ ફી, ન જાહેરાત. કોઈ પણ અમારો કોડ વાંચીને અમારું દરેક વચન ચકાસી શકે છે. |  |
| `scr_trust_card3_title` | Free forever, open source | હંમેશાં મફત, ઓપન સોર્સ |  |
| `scr_trust_continue` | Continue | આગળ વધો |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | તમારા ફેરફાર સચવાયા નથી, એટલે તિજોરીમાં જે પહેલાં હતું તેમાંથી કંઈ ખોવાયું નથી. |  |
| `st_recently_deleted` | Recently deleted | તાજેતરમાં કાઢેલા |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | વન-ટાઇમ કોડ (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | વન-ટાઇમ કોડ (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | વન-ટાઇમ કોડ (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA સીક્રેટ |  |
| `tr_cannot_undo` | This cannot be undone. | આ પાછું લાવી શકાતું નથી. |  |
| `tr_delete_all` | Delete all permanently | બધું કાયમ માટે કાઢી નાખો |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d રેકોર્ડ કાયમ માટે જતા રહેશે. આ પાછું લાવી શકાતું નથી અને પુનઃસ્થાપિત કરવા કોઈ બેકઅપ નથી. |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d રેકોર્ડ કાયમ માટે જતો રહેશે. આ પાછું લાવી શકાતું નથી અને પુનઃસ્થાપિત કરવા કોઈ બેકઅપ નથી. |  |
| `tr_delete_all_title` | Delete everything in the trash? | કચરાપેટીનું બધું કાઢી નાખવું? |  |
| `tr_delete_now` | Delete now | અત્યારે કાઢી નાખો |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | “%1$s” ને કાયમ માટે કાઢી નાખવું? |  |
| `tr_empty` | Nothing deleted. | કંઈ પણ કાઢ્યું નથી. |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | કાઢેલા રેકોર્ડ અહીં %1$d દિવસ રહે છે. |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | ભારતીય બેંક, UPI, કાર્ડ, ડીમૅટ, EPF અને જે OTP ઍપ તમે ખરેખર વાપરો છો — એ બધા માટે બનેલી, ફોનમાં જ રહેતી તિજોરી. |  |

## Priority 2 — longer prose

| key | English | Gujarati | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | એ જ એક માહિતીથી શરૂ કરો જે સૌથી વધુ કામ આવે છે. નોટ્સ ઍપમાં પડેલા બાર પાસવર્ડ કરતાં એક સાચવેલો પાસવર્ડ પણ વધુ સુરક્ષિત છે. |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | પાસફ્રેઝ ભૂલી જાઓ તો રિકવરી કિટ જ અંદર આવવાનો રસ્તો છે. તમારા માટે બીજી કોઈ બનાવી શકતું નથી. |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | તે ફાઇલમાં ઓળખવા જેવું કંઈ મળ્યું નહીં. Chrome, Google Password Manager, Bitwarden, LastPass અને KeePass ના એક્સપોર્ટ સમજાય છે. |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d હાલના રેકોર્ડ બદલાશે — સાઇટ અને યુઝરનેમથી મેળવીને. બદલાયેલા પાસવર્ડ દરેક રેકોર્ડના ઇતિહાસમાં પાછા મળી જશે. |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d હાલનો રેકોર્ડ બદલાશે — સાઇટ અને યુઝરનેમથી મેળવીને. બદલાયેલા પાસવર્ડ દરેક રેકોર્ડના ઇતિહાસમાં પાછા મળી જશે. |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d રેકોર્ડ બંને જગ્યાએ બદલાયા હતા. બંને રૂપ રાખ્યાં છે — “(conflict copy)” શોધો. |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | તે પાસફ્રેઝ નાખો જે આ બેકઅપ ફાઇલ ખોલે છે. તે તમારા હાલના પાસફ્રેઝથી અલગ હોઈ શકે છે. |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | .kosh ફાઇલનો Poly1305 પ્રમાણીકરણ ટૅગ મેળ ખાતો નથી. આવું અધૂરા સિંક કે ખરાબ સ્ટોરેજ પછી થઈ શકે છે. |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh તિજોરી ફાઇલની બાજુમાં એક ચાલુ બેકઅપ રાખે છે. તેને તમારા સિંક ફોલ્ડરમાંથી પુનઃસ્થાપિત કરો, કે બીજા ફોન પર રિકવરી કિટથી તિજોરી ખોલો. |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | કાર્ડને ફોનની પાછળ સીધું ટેકવી રાખો જ્યાં સુધી તે વાંચી ન લે. આનાથી કાર્ડ નંબર, મુદત અને નામ આવી જાય છે — CVV ચિપમાં હોતો નથી, તે તમારે જાતે લખવો પડશે. |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | અંદર પાછા આવવાનો ઝડપી રસ્તો પસંદ કરો. તિજોરીની ચોકી પાસફ્રેઝ જ કરતો રહેશે; આ ફક્ત આ ફોન પર ચાવી ખોલે છે. |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | હવે તમારી તિજોરીમાં સાચી માહિતી છે. રિકવરી કિટ વગર પાસફ્રેઝ ભૂલી જાઓ તો કોઈ તમને પાછા અંદર લાવી શકતું નથી — અમે પણ નહીં. |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh મફત અને ઓપન સોર્સ છે, અને તેનું કોઈ સર્વર નથી. તમારી તિજોરી ફક્ત તમે જ ખોલી શકો છો. અમે પણ નહીં. |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | કૅમેરાની પરવાનગી ફક્ત QR કોડ સ્કૅન કરવા માટે જોઈએ. રેકોર્ડ ઉમેરતી વખતે તમે સીક્રેટ હાથે પણ ચોંટાડી શકો છો. |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | જે બેંક ઍપ ઑટોફિલ ચાલવા દેતી નથી તેમના માટે — બટન દબાવીને લૉગિનની વિગત એક એક કરીને કૉપી કરો |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | જેમ તમે તમારો ફોન ખોલો છો, તેમ જ તિજોરી પણ. પાસફ્રેઝ હંમેશાં કામ કરતો રહેશે. |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | તિજોરીના સ્ક્રીનશૉટ ક્લાઉડ ફોટો બેકઅપમાં પહોંચી શકે છે. બહુ જરૂરી હોય તો જ ચાલુ કરો. |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | તિજોરી ફાઇલ લખી શકાઈ નહીં. જો તમે બેકઅપ અને સિંક ફોલ્ડર સેટ કર્યું હોય, તો કદાચ Android એ તેની પરવાનગી પાછી લઈ લીધી હોય — સેટિંગ ખોલો, ફોલ્ડર ફરી પસંદ કરો, અને ફરી કોશિશ કરો. |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | તમારી એન્ક્રિપ્ટેડ .kosh ફાઇલો સીધી આ જ ફોલ્ડરમાં સચવાય છે. ઘણા ડિવાઇસ પર આપોઆપ બેકઅપ માટે આ ફોલ્ડરને Google Drive, Syncthing, Nextcloud કે SD કાર્ડ સાથે સિંક કરો. |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | નવી રિકવરી ચાવી બનાવવા તમારો પાસફ્રેઝ નાખો. જૂની ચાવી કામ કરવાનું બંધ કરી દેશે. |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | આ પાનાની બાકીની બધી બાબતો એક લોગિન નબળું કરે છે. આ આખી તિજોરી લઈ જઈ શકે છે. સેટિંગ્સ → નવી રિકવરી ચાવી લો. |  |

## Priority 3 — short labels

| key | English | Gujarati | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | મજબૂત પાસવર્ડ વાપરો |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | ખાતાનું નામ (જેમ કે Google) |  |
| `au_active_many` | %1$d active codes | %1$d ચાલુ કોડ |  |
| `au_active_one` | %1$d active code | %1$d ચાલુ કોડ |  |
| `au_add_another` | Add another authenticator | બીજું ઑથેન્ટિકેટર ઉમેરો |  |
| `au_add_secret` | Add Secret Key | સીક્રેટ કી ઉમેરો |  |
| `au_camera_needed` | Camera permission needed to scan QR code | QR કોડ સ્કૅન કરવા કૅમેરાની પરવાનગી જોઈએ |  |
| `au_copied` | Copied · clears shortly | કૉપી થયું · થોડી વારમાં ભૂંસાઈ જશે |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google, GitHub કે તમારા બ્રોકરનો QR સ્કૅન કરો, કે સીક્રેટ કી હાથે લખો. |  |
| `au_enter_key` | Enter Key | કી લખો |  |
| `au_fallback_name` | Authenticator | ઑથેન્ટિકેટર |  |
| `au_flashlight` | Flashlight | ટૉર્ચ |  |
| `au_grant` | Grant Permission | પરવાનગી આપો |  |
| `au_image_failed` | Failed to process image | ચિત્ર વાંચી શકાયું નહીં |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | ખોટી Base32 સીક્રેટ કી (ફક્ત A-Z અક્ષર અને 2-7 અંક) |  |
| `au_no_match` | No codes match | કોઈ કોડ મળ્યો નહીં |  |
| `au_none_yet` | No codes yet. | હજી કોઈ કોડ નથી. |  |
| `au_pick_image` | Pick Image | ચિત્ર પસંદ કરો |  |
| `au_rotating` | "Rotating " | "બદલાતા રહે છે " |  |
| `au_rotating_emph` | codes. | કોડ. |  |
| `au_save_key` | Save Key | કી સાચવો |  |
| `au_scan_qr` | Scan a QR code | QR કોડ સ્કૅન કરો |  |
| `au_scan_title` | Scan Authenticator QR | ઑથેન્ટિકેટર QR સ્કૅન કરો |  |
| `au_search_hint` | Search codes, issuers… | કોડ, જારીકર્તા શોધો… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | જેમ કે JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | સીક્રેટ કી (Base32) |  |
| `au_tap_to_copy` | Tap to copy | કૉપી કરવા ટૅપ કરો |  |
| `cat_apps` | Apps &amp; Logins | ઍપ અને લૉગિન |  |
| `cat_banks` | Banks &amp; UPI | બેંક અને UPI |  |
| `cat_cards` | Cards | કાર્ડ |  |
| `cat_govid` | Gov &amp; ID | સરકારી અને ઓળખ |  |
| `cat_investments` | Investments | રોકાણ |  |
| `cat_utilities` | Utilities | બિલ અને જોડાણ |  |
| `cd_mask_hidden` | hidden | છુપાયેલું |  |
| `cd_shield_high_sensitivity` | extra-protected field | વધારાની સુરક્ષિત માહિતી |  |
| `gl_blank` | Blank template | ખાલી ટેમ્પલેટ |  |
| `gl_cat_apps` | Apps | ઍપ |  |
| `gl_cat_banks` | Banks | બેંક |  |
| `gl_cat_cards` | Cards | કાર્ડ |  |
| `gl_cat_demat` | Demat | ડીમૅટ |  |
| `gl_cat_govid` | Gov ID | સરકારી ઓળખ |  |
| `gl_cat_popular` | Popular | લોકપ્રિય |  |
| `gl_cat_shopping` | Shopping | ખરીદી |  |
| `gl_cat_travel` | Travel | મુસાફરી |  |
| `gl_cat_upi` | UPI | UPI |  |
| `gl_cat_utilities` | Utilities | બિલ અને જોડાણ |  |
| `gl_head_emph` | storing? | સાચવીએ છીએ? |  |
| `gl_head_lead` | "What are we " | "આપણે શું " |  |
| `gl_matches` | %1$d matches | %1$d મળ્યાં |  |
| `gl_most_used` | Most-used first | સૌથી વધુ વપરાયેલા પહેલાં |  |
| `gl_not_found` | Can’t find a service? | સેવા મળતી નથી? |  |
| `gl_search` | Search %1$d Indian services… | %1$d ભારતીય સેવાઓમાં શોધો… |  |
| `gl_suggested` | Suggested for you | તમારા માટે સૂચન |  |
| `hm_add_first` | Add your first record | તમારો પહેલો રેકોર્ડ ઉમેરો |  |
| `hm_all_offline` | all offline. | બધું ઑફલાઇન. |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d માહિતીઓ, " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d માહિતી, " |  |
| `hm_detail_placeholder` | Pick a record to see it here | અહીં જોવા માટે એક રેકોર્ડ પસંદ કરો |  |
| `hm_empty_blank` | A blank vault, ready. | ખાલી તિજોરી, તૈયાર છે. |  |
| `hm_empty_head_emph` | waiting. | રાહ જુએ છે. |  |
| `hm_empty_head_lead` | "Your vault is " | "તમારી તિજોરી " |  |
| `hm_filter_all` | All | બધા |  |
| `hm_import_backup` | Import an encrypted backup | એન્ક્રિપ્ટેડ બેકઅપ આયાત કરો |  |
| `hm_import_backup_note` | Open a .kosh file from this device | આ જ ફોનમાંથી .kosh ફાઇલ ખોલો |  |
| `hm_inst_many` | %1$d institutions | %1$d સંસ્થાઓ |  |
| `hm_inst_one` | %1$d institution | %1$d સંસ્થા |  |
| `hm_kit_banner_action` | Save one now | હમણાં સાચવો |  |
| `hm_kit_banner_dismiss` | Remind me later | પછી યાદ કરાવો |  |
| `hm_kit_banner_title` | No recovery kit saved | કોઈ રિકવરી કિટ સાચવેલી નથી |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | ખુલ્લી છે · છોડતાં જ બંધ |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | ખુલ્લી છે · છોડ્યાની %1$d મિનિટ પછી બંધ |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | ખુલ્લી છે · છોડ્યાની 1 મિનિટ પછી બંધ |  |
| `hm_no_match` | Nothing matches “%1$s” | “%1$s” થી કંઈ મળ્યું નહીં |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | કોઈ સંસ્થા, UPI હૅન્ડલ, કે છેલ્લા ચાર અંક અજમાવો. |  |
| `hm_pinned` | Pinned | પિન કરેલા |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC, priya@upi, PAN… શોધો |  |
| `hm_start_template` | Start with a template | ટેમ્પલેટથી શરૂ કરો |  |
| `ic_could_not` | Could not import | આયાત થઈ શક્યું નહીં |  |
| `ic_done` | Done | થઈ ગયું |  |
| `ic_import` | Import | આયાત કરો |  |
| `ic_imported` | Imported | આયાત થઈ ગયું |  |
| `ic_importing` | Importing… | આયાત થઈ રહ્યું છે… |  |
| `ic_new_many` | %1$d new logins. | %1$d નવાં લૉગિન. |  |
| `ic_new_one` | %1$d new login. | %1$d નવું લૉગિન. |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d ઉમેરાયા, %2$d બદલાયા. |  |
| `ic_title` | Import from %1$s? | %1$s માંથી આયાત કરવું? |  |
| `ic_too_large` | That file is too large to be a credential export. | આ ફાઇલ એટલી મોટી છે કે પાસવર્ડ એક્સપોર્ટ હોઈ જ ન શકે. |  |
| `import_action` | Import | આયાત કરો |  |
| `import_locked` | Unlock your vault before importing. | આયાત કરતાં પહેલાં તમારી તિજોરી ખોલો. |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d ઉમેરાયા, %2$d બદલાયા. કંઈ પણ ભૂંસાયું નથી. |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | તે ફાઇલ Zerokosh તિજોરી તરીકે વાંચી શકાઈ નહીં. |  |
| `import_nothing_new` | Everything in that backup was already here. | તે બેકઅપનું બધું પહેલેથી અહીં હતું. |  |
| `import_passphrase_label` | Backup passphrase | બેકઅપનો પાસફ્રેઝ |  |
| `import_title` | Import a backup | બેકઅપ આયાત કરો |  |
| `kicker_locked` | Locked | બંધ છે |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · કંઈ પણ આ ફોનની બહાર ગયું નથી |  |
| `lk_touch_unlock` | Touch to unlock | ખોલવા માટે અડો |  |
| `lk_welcome_emph` | Your vault is sealed. | તમારી તિજોરી બંધ છે. |  |
| `lk_welcome_lead` | Welcome back. | ફરી સ્વાગત છે. |  |
| `msg_auth_needed` | Confirm it\'s you to see this | જોવા માટે ખાતરી કરો કે આ તમે જ છો |  |
| `msg_back` | Back | પાછા |  |
| `msg_cancel` | Cancel | રદ કરો |  |
| `msg_file_damaged` | File damaged — restored from backup | ફાઇલ બગડી હતી — બેકઅપમાંથી ઠીક કરી |  |
| `msg_ok` | OK | ઠીક છે |  |
| `msg_saved` | Saved | સાચવ્યું |  |
| `nav_all_templates` | All templates | બધા ટેમ્પલેટ |  |
| `nav_damaged_emph` | vault file | તિજોરી ફાઇલ |  |
| `nav_damaged_kicker` | Damaged state | ખરાબ સ્થિતિ |  |
| `nav_damaged_lead` | "Something in the " | "તમારી " |  |
| `nav_damaged_tail` | " is off." | " માં કંઈક ગડબડ છે." |  |
| `nav_integrity_title` | Integrity check failed | અખંડિતતા ચકાસણી નિષ્ફળ |  |
| `nav_scan` | Scan | સ્કૅન |  |
| `nav_tap_card` | Tap a card | કાર્ડ ટૅપ કરો |  |
| `nav_what_next` | What to do next | હવે શું કરવું |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC બંધ છે. સેટિંગમાં જઈને ચાલુ કરો, પછી કોશિશ કરો. |  |
| `nfc_hold_card` | Hold your card to the phone | કાર્ડને ફોન સાથે અડાડો |  |
| `nfc_missed` | Did not catch that | પકડમાં આવ્યું નહીં |  |
| `nfc_read_failed` | That card could not be read. Try again. | તે કાર્ડ વાંચી શકાયું નહીં. ફરી કોશિશ કરો. |  |
| `nfc_reading` | Reading… | વંચાઈ રહ્યું છે… |  |
| `nfc_try_again` | Try again | ફરી કોશિશ કરો |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · આ જ ફોન પર માપેલું |  |
| `ob_argon_faster` | Faster unlock | જલદી ખૂલશે |  |
| `ob_argon_harder` | Harder to attack | તોડવું અઘરું |  |
| `ob_argon_measuring` | Measuring this device… | આ ફોન મપાઈ રહ્યો છે… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id ની કઠિનતા |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | કોઈ એક શબ્દકોશનો શબ્દ નહીં |  |
| `ob_check_pass_length` | 10 characters or more | 10 કે વધુ અક્ષર |  |
| `ob_check_pass_reuse` | Not reused from another app | બીજી ઍપમાંથી ફરી વાપરેલું નહીં |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | ન જન્મદિવસ, ન લગ્નતિથિ |  |
| `ob_check_pin_digits` | All six digits entered | છએ છ અંક ભરાયા |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | ન સળંગ અંક, ન પુનરાવર્તન |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~તોડવામાં %1$d સદી |  |
| `ob_crack_days` | ~%1$d days to crack | ~તોડવામાં %1$d દિવસ |  |
| `ob_crack_forever` | longer than the sun | સૂરજ કરતાં પણ વધુ ટકશે |  |
| `ob_crack_hours` | ~hours to crack | ~તોડવામાં થોડા કલાક |  |
| `ob_crack_seconds` | ~seconds to crack | ~તોડવામાં થોડી સેકન્ડ |  |
| `ob_crack_years` | ~%1$d years to crack | ~તોડવામાં %1$d વર્ષ |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | પ્રમાણિત, દરેક તિજોરીનો અલગ નૉન્સ |  |
| `ob_fact_encryption_title` | Encryption | એન્ક્રિપ્શન |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | સેટઅપ વખતે તમારા ફોન પર માપેલું |  |
| `ob_fact_kdf_title` | Key stretching | કી સ્ટ્રેચિંગ |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | ન રીસેટ લિંક, ન સપોર્ટનો પાછલો દરવાજો. |  |
| `ob_fact_lost_value` | Nobody can recover it | કોઈ પાછી લાવી શકતું નથી |  |
| `ob_fact_network_title` | Network permission | નેટવર્કની પરવાનગી |  |
| `ob_fact_network_value` | Not requested | માંગી જ નથી |  |
| `ob_fact_quick_title` | Quick unlock | ઝડપી અનલૉક |  |
| `ob_fact_quick_value` | Hardware keystore | હાર્ડવેર કીસ્ટોર |  |
| `ob_lang_continue` | Continue in %1$s | %1$s માં આગળ વધો |  |
| `ob_lang_head_emph` | language. | ભાષા પસંદ કરો. |  |
| `ob_lang_head_lead` | "Choose your " | "તમારી " |  |
| `ob_lang_search` | Search %1$d languages | %1$d ભાષાઓમાં શોધો |  |
| `ob_quick_continue_pass` | Continue with passphrase | પાસફ્રેઝ સાથે આગળ વધો |  |
| `ob_quick_enable` | Enable quick unlock | ઝડપી અનલૉક ચાલુ કરો |  |
| `ob_quick_fingerprint` | Fingerprint | ફિંગરપ્રિન્ટ |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | ઝડપી, હાર્ડવેરથી સુરક્ષિત અનલૉક. |  |
| `ob_quick_head_emph` | Without the cloud. | ક્લાઉડ વગર. |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "એક સ્પર્શે ખૂલી જાય. " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox. કોઈ બાયોમેટ્રિક માહિતી Zerokosh સુધી ક્યારેય પહોંચતી નથી. |  |
| `ob_quick_hw_title` | Hardware-backed | હાર્ડવેરથી સુરક્ષિત |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | આ ફોનમાં હાર્ડવેર વાળું સેન્સર નથી. |  |
| `ob_quick_opening` | Opening your vault… | તમારી તિજોરી ખૂલી રહી છે… |  |
| `ob_quick_pass_only` | Passphrase only | ફક્ત પાસફ્રેઝ |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | દર વખતે લખો. સૌથી સુરક્ષિત. |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | ઝડપી અનલૉક સેટ થયું નહીં. ફરી કોશિશ કરો, કે ફક્ત પાસફ્રેઝ સાથે આગળ વધો. |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | અત્યારે રહેવા દો — હું પાસફ્રેઝ લખી લઈશ |  |
| `ob_quick_touch_title` | Touch the sensor | સેન્સરને અડો |  |
| `ob_recommended` | Recommended | સૂચવેલું |  |
| `ob_reveal_hide` | Hide | છુપાવો |  |
| `ob_reveal_show` | Show | બતાવો |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | લપેટનારી ચાવી હાર્ડવેર કીસ્ટોરમાં રહે છે. બાયોમેટ્રિક પછીના પગલે. |  |
| `ob_seal_title` | Seal to this device | આ જ ફોન સાથે બાંધી દો |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | આ ફોનમાં હાર્ડવેર વાળું બાયોમેટ્રિક નથી. |  |
| `ob_soon` | SOON | જલદી |  |
| `ob_step_label` | Step %1$d of 6 | પગલું %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | એવું કંઈક પસંદ કરો જે ફક્ત તમે જ કહેતા હો |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | સારું · %1$d બિટ એન્ટ્રોપી |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | બહુ નાનું · 10 અક્ષર જોઈએ |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | મજબૂત · %1$d બિટ એન્ટ્રોપી |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | નબળું · %1$d બિટ એન્ટ્રોપી |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | એવા છ અંક જે તમારી જિંદગી જોઈને કોઈ ધારી ન શકે |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | ઠીકઠાક · %1$d બિટ — પિન આનાથી વધુ મજબૂત થઈ શકતો નથી |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | બહુ નાનું · 6 અંક જોઈએ |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | નબળું · આ જ પિન સૌથી પહેલાં અજમાવાય છે |  |
| `ob_try_label` | TRY | અજમાવો |  |
| `qa_aadhaar` | Aadhaar | આધાર |  |
| `qa_bank_account` | Bank account | બેંક ખાતું |  |
| `qa_pan` | PAN | PAN |  |
| `qa_upi_id` | UPI ID | UPI ID |  |
| `rd_copied` | Copied | કૉપી થયું |  |
| `rd_forget` | Forget | ભૂલી જાઓ |  |
| `rd_forget_these` | Forget these | આ ભૂલી જાઓ |  |
| `rd_forget_title` | Forget previous passwords? | અગાઉના પાસવર્ડ ભૂલી જવા? |  |
| `rd_history_hide` | Hide | છુપાવો |  |
| `rd_history_show` | Show %1$d | %1$d બતાવો |  |
| `rd_hold_to_reveal` | Hold to reveal | જોવા માટે દબાવી રાખો |  |
| `rd_last_edit` | last edit %1$s | છેલ્લો ફેરફાર %1$s |  |
| `rd_release_to_hide` | Release to hide | છુપાવવા છોડી દો |  |
| `re_add_field` | + Add another field | + બીજું ફીલ્ડ ઉમેરો |  |
| `re_add_field_title` | Add a field | ફીલ્ડ ઉમેરો |  |
| `re_field_name` | Field name | ફીલ્ડનું નામ |  |
| `re_pick_date` | Pick a date | તારીખ પસંદ કરો |  |
| `re_remove` | Remove | કાઢી નાખો |  |
| `re_tap_card` | Read the card by tapping it | કાર્ડ ટૅપ કરીને વાંચો |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | ગુપ્ત ગણો (છુપાયેલું રહેશે, જોવા દબાવી રાખો) |  |
| `re_using_template` | using the %1$s template | %1$s ટેમ્પલેટથી |  |
| `rem_kit_title` | No recovery kit saved | કોઈ રિકવરી કિટ સાચવેલી નથી |  |
| `scr_about_license` | License: GPL-3.0 — free forever | લાઇસન્સ: GPL-3.0 — હંમેશાં મફત |  |
| `scr_about_source` | Source code | સોર્સ કોડ |  |
| `scr_about_version` | Version %1$s | આવૃત્તિ %1$s |  |
| `scr_auth_add_qr` | Add via QR | QR થી ઉમેરો |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | તમારી ઍપ અને બ્રોકરના કોડ અહીં દેખાશે |  |
| `scr_auth_scan_title` | Point the camera at the QR code | કૅમેરાને QR કોડ પર રાખો |  |
| `scr_detail_copied` | Copied · clears in 30s | કૉપી થયું · 30 સેકન્ડમાં ભૂંસાઈ જશે |  |
| `scr_detail_copy` | Copy | કૉપી |  |
| `scr_detail_edit` | Edit | બદલો |  |
| `scr_detail_favorite` | Favourite | પસંદીદા |  |
| `scr_detail_hidden` | Hidden | છુપાયેલું |  |
| `scr_detail_hide` | Hide | છુપાવો |  |
| `scr_detail_history_empty` | Nothing replaced yet. | હજી સુધી કંઈ બદલાયું નથી. |  |
| `scr_detail_history_title` | Previous passwords | અગાઉના પાસવર્ડ |  |
| `scr_detail_reveal` | Show | બતાવો |  |
| `scr_detail_shown` | Shown | દેખાય છે |  |
| `scr_edit_cancel` | Cancel | રદ કરો |  |
| `scr_edit_generate` | Generate | બનાવો |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | બેંક / કંપની (જૂથ બનાવવા માટે) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | આ બરાબર લાગતું નથી — એક વાર જોઈ લો |  |
| `scr_edit_link_none` | None | કંઈ નહીં |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | આ કાર્ડ નંબર સામાન્ય ચકાસણીમાં ખરો ઊતરતો નથી — સાચો હોય તો સાચવી લો |  |
| `scr_edit_month` | Month | મહિનો |  |
| `scr_edit_picker_other` | Other… | અન્ય… |  |
| `scr_edit_picker_other_hint` | Type your own | તમારું લખો |  |
| `scr_edit_required_title` | Give it a name first | પહેલાં તેને કોઈ નામ આપો |  |
| `scr_edit_save` | Save | સાચવો |  |
| `scr_edit_title_hint` | Title | નામ |  |
| `scr_edit_title_new` | New | નવું |  |
| `scr_edit_year` | Year | વર્ષ |  |
| `scr_gallery_quick_add` | Quick add | ઝડપથી ઉમેરો |  |
| `scr_gallery_title` | What do you want to save? | તમે શું સાચવવા માંગો છો? |  |
| `scr_home_add` | Add | ઉમેરો |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | તમારું બેંક ખાતું આવું દેખાશે |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | તમારાં કાર્ડ, UPI અને ઍપ લૉગિન પણ અહીં જ રહેશે |  |
| `scr_home_group_other` | Other | અન્ય |  |
| `scr_home_no_results` | Nothing matches your search | તમારી શોધથી કંઈ મળ્યું નહીં |  |
| `scr_home_search_hint` | Search your vault | તમારી તિજોરીમાં શોધો |  |
| `scr_home_tab_authenticator` | Authenticator | કોડ |  |
| `scr_home_tab_home` | Home | હોમ |  |
| `scr_home_tab_settings` | Settings | સેટિંગ |  |
| `scr_home_title` | Home | હોમ |  |
| `scr_language_continue` | Continue | આગળ વધો |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | તમારી ભાષા પસંદ કરો |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | કૉપી કરવા બટન દબાવો · 30 સેકન્ડમાં ભૂંસાઈ જશે |  |
| `scr_login_helper_channel` | Login helper | લૉગિન સહાયક |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s માં લૉગિન થઈ રહ્યું છે |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | અત્યારે બેકઅપ ફોલ્ડર સેટ કરો |  |
| `scr_quickunlock_enable` | Turn on | ચાલુ કરો |  |
| `scr_quickunlock_skip` | Not now | અત્યારે નહીં |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | આંગળી કે ચહેરાથી ખોલો |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s ની તારીખ નજીક છે · Zerokosh ખોલો |  |
| `scr_reminder_channel` | Renewal reminders | નવીકરણ યાદ |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh યાદ કરાવે છે |  |
| `scr_settings_about` | About | પરિચય |  |
| `scr_settings_allow_screenshots` | Allow screenshots | સ્ક્રીનશૉટ લેવા દો |  |
| `scr_settings_autofill` | Autofill service | ઑટોફિલ સેવા |  |
| `scr_settings_autofill_off` | Not set up | સેટ નથી |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | ઉપલબ્ધ નથી |  |
| `scr_settings_autolock` | Lock when I leave the app | ઍપ છોડતાં જ બંધ કરી દો |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 મિનિટ પછી |  |
| `scr_settings_autolock_immediately` | Immediately | તરત જ |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d મિનિટ પછી |  |
| `scr_settings_change_passphrase` | Change passphrase | પાસફ્રેઝ બદલો |  |
| `scr_settings_current_passphrase` | Current passphrase | હાલનો પાસફ્રેઝ |  |
| `scr_settings_export` | Export | નિકાસ કરો |  |
| `scr_settings_import` | Import passwords | પાસવર્ડ આયાત કરો |  |
| `scr_settings_language` | Language | ભાષા |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | નવો પાસફ્રેઝ (ઓછામાં ઓછા 10 અક્ષર) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | નવી રિકવરી ચાવી લો |  |
| `scr_settings_passphrase_changed` | Passphrase changed | પાસફ્રેઝ બદલાયો |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | ફિંગરપ્રિન્ટ / ફેસ અનલૉક |  |
| `scr_settings_security_info` | How your data is protected | તમારો ડેટા કેવી રીતે સુરક્ષિત છે |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | બેકઅપ અને સિંક ફોલ્ડર |  |
| `scr_settings_sync_not_set` | Not backed up | બેકઅપ નથી |  |
| `scr_settings_title` | Settings | સેટિંગ |  |
| `se_title` | Not saved | સાચવ્યું નથી |  |
| `st_active_folder` | Active Folder | ચાલુ ફોલ્ડર |  |
| `st_active_value` | Active · %1$s | ચાલુ · %1$s |  |
| `st_backing_up` | Backing up vault… | તિજોરીનો બેકઅપ થઈ રહ્યો છે… |  |
| `st_backup_now` | Backup Now | અત્યારે બેકઅપ લો |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | બેકઅપ અને સિંક ફોલ્ડર |  |
| `st_change_folder` | Change Folder | ફોલ્ડર બદલો |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | નવો પાસફ્રેઝ ફરીથી લખો |  |
| `st_connected_folder` | Connected folder: %1$s | જોડાયેલું ફોલ્ડર: %1$s |  |
| `st_disconnect` | Disconnect | કાઢી નાખો |  |
| `st_done` | Done | થઈ ગયું |  |
| `st_export_kosh` | Export encrypted .kosh | એન્ક્રિપ્ટેડ .kosh નિકાસ કરો |  |
| `st_folder_fallback` | Folder | ફોલ્ડર |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | પાસફ્રેઝ ભૂલી ગયા? |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | તમારી આંગળીથી નવો મૂકો |  |
| `st_generate` | Generate | બનાવો |  |
| `st_group_about` | About | પરિચય |  |
| `st_group_appearance` | Appearance | દેખાવ |  |
| `st_group_security` | Security | સુરક્ષા |  |
| `st_group_sync` | Sync | સિંક |  |
| `st_import_kosh` | Import a .kosh backup | .kosh બેકઅપ આયાત કરો |  |
| `st_import_other` | Import from another password manager | બીજા પાસવર્ડ મૅનેજરમાંથી આયાત કરો |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | લાઇસન્સ |  |
| `st_logos_by` | Logos provided by | લોગો આપનાર |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | તેને ઑફલાઇન રાખો. જૂની રિકવરી ચાવી હવે માન્ય નથી. |  |
| `st_new_recovery_result` | Your new Recovery Key: | તમારી નવી રિકવરી ચાવી: |  |
| `st_subtitle` | Your rules. | તમારા નિયમો. |  |
| `st_theme` | Theme | થીમ |  |
| `st_theme_dark` | Dark | ઘેરું |  |
| `st_theme_light` | Light | આછું |  |
| `st_theme_system` | System | સિસ્ટમ |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | તિજોરી %1$s માં સચવાઈ! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | બેકઅપ થયું નહીં — ફોલ્ડરની પરવાનગી જોઈ લો |  |
| `st_toast_disconnected` | Backup folder disconnected | બેકઅપ ફોલ્ડર કાઢી નાખ્યું |  |
| `st_toast_export_failed` | Export failed | નિકાસ થઈ શક્યો નહીં |  |
| `st_toast_exported` | Encrypted vault exported | એન્ક્રિપ્ટેડ તિજોરી નિકાસ થઈ |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | બેકઅપ ફોલ્ડર જોડાયું અને તિજોરી %1$s માં સચવાઈ! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | બેકઅપ ફોલ્ડર જોડાયું: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | ફોલ્ડર જોડાયું નહીં: %1$s |  |
| `st_vault_review` | Vault review | તિજોરીની તપાસ |  |
| `st_vault_review_detail` | Reused, weak, expiring | ફરી વપરાયેલા, નબળા, પૂરા થતા |  |
| `tab_codes` | Codes | કોડ |  |
| `tab_settings` | Settings | સેટિંગ |  |
| `tab_templates` | Templates | ટેમ્પલેટ |  |
| `tab_vault` | Vault | તિજોરી |  |
| `time_days` | %1$dd ago | %1$d દિ પહેલાં |  |
| `time_hours` | %1$dh ago | %1$d ક પહેલાં |  |
| `time_just_now` | just now | હમણાં જ |  |
| `time_minutes` | %1$dm ago | %1$d મિ પહેલાં |  |
| `time_months` | %1$dmo ago | %1$d મહિના પહેલાં |  |
| `time_years` | %1$dy ago | %1$d વર્ષ પહેલાં |  |
| `tpl_aadhaar_card` | Aadhaar Card | આધાર |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | આધાર નંબર |  |
| `tpl_aadhaar_card_address` | Address | આધાર પરનું સરનામું |  |
| `tpl_aadhaar_card_dob` | Dob | જન્મ તારીખ |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | સ્કૅન કરેલી નકલ |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | જોડાયેલ મોબાઇલ |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar પાસકોડ |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | આધાર પરનું નામ |  |
| `tpl_aadhaar_card_notes` | Notes | નોંધ |  |
| `tpl_app_profile` | App Profile | ઍપ પ્રોફાઇલ |  |
| `tpl_app_profile_app_name` | App name | ઍપનું નામ |  |
| `tpl_app_profile_gift_cards` | Gift cards | ગિફ્ટ કાર્ડ |  |
| `tpl_app_profile_membership` | Membership | સભ્યપદ |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | સભ્યપદ નવીકરણ |  |
| `tpl_app_profile_notes` | Notes | નોંધ |  |
| `tpl_app_profile_password_if_any` | Password (if any) | પાસવર્ડ (હોય તો) |  |
| `tpl_app_profile_registered_email` | Registered email | નોંધાયેલ ઈમેલ |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | નોંધાયેલ મોબાઇલ |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | વૉલેટ પિન |  |
| `tpl_bank_account` | Bank Account | બેંક ખાતું |  |
| `tpl_bank_account_account_number` | Account number | ખાતા નંબર |  |
| `tpl_bank_account_account_type` | Account type | ખાતાનો પ્રકાર |  |
| `tpl_bank_account_bank_name` | Bank name | બેંકનું નામ |  |
| `tpl_bank_account_branch` | Branch | શાખા |  |
| `tpl_bank_account_customer_id` | Customer id | ગ્રાહક ID |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC કોડ |  |
| `tpl_bank_account_login_password` | Login password | લૉગિન પાસવર્ડ |  |
| `tpl_bank_account_micr` | MICR code | MICR કોડ |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | નેટ-બેંકિંગ યુઝર ID |  |
| `tpl_bank_account_nominee` | Nominee | નૉમિની |  |
| `tpl_bank_account_notes` | Notes | નોંધ |  |
| `tpl_bank_account_profile_password` | Profile password | પ્રોફાઇલ પાસવર્ડ |  |
| `tpl_bank_account_registered_email` | Registered email | નોંધાયેલ ઈમેલ |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | નોંધાયેલ મોબાઇલ |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | વ્યવહાર પાસવર્ડ |  |
| `tpl_card` | Card | કાર્ડ |  |
| `tpl_card_atm_pin` | ATM PIN | ATM પિન |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | બિલિંગ સાઇકલનો દિવસ |  |
| `tpl_card_card_network` | Card network | નેટવર્ક |  |
| `tpl_card_card_number` | Card number | કાર્ડ નંબર |  |
| `tpl_card_card_portal_login` | Card portal login | કાર્ડ પોર્ટલ લૉગિન |  |
| `tpl_card_card_portal_password` | Card portal password | કાર્ડ પોર્ટલ પાસવર્ડ |  |
| `tpl_card_card_type` | Card type | કાર્ડનો પ્રકાર |  |
| `tpl_card_card_variant` | Card variant | કાર્ડ વેરિઅન્ટ |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | મુદત |  |
| `tpl_card_linked_account` | Linked account | જોડાયેલું ખાતું |  |
| `tpl_card_name_on_card` | Name on card | કાર્ડ પરનું નામ |  |
| `tpl_card_notes` | Notes | નોંધ |  |
| `tpl_demat` | Demat | ડીમૅટ |  |
| `tpl_demat_api_key` | API key | API કી |  |
| `tpl_demat_api_secret` | API secret | API સીક્રેટ |  |
| `tpl_demat_bo_id` | BO ID | BO ID |  |
| `tpl_demat_broker` | Broker | બ્રોકર |  |
| `tpl_demat_cdsl_tpin` | CDSL TPIN | CDSL TPIN |  |
| `tpl_demat_client_id` | Client id | ક્લાયન્ટ ID |  |
| `tpl_demat_depository` | Depository | ડિપોઝિટરી |  |
| `tpl_demat_dp_id` | DP ID | DP ID |  |
| `tpl_demat_login_password` | Login password | લૉગિન પાસવર્ડ |  |
| `tpl_demat_mf_folios` | Mutual fund folios | મ્યુચ્યુઅલ ફંડ ફોલિયો |  |
| `tpl_demat_mpin` | MPIN | MPIN |  |
| `tpl_demat_nominee` | Nominee | નૉમિની |  |
| `tpl_demat_notes` | Notes | નોંધ |  |
| `tpl_digilocker` | Digilocker | DigiLocker |  |
| `tpl_digilocker_login_username` | Login username | યુઝરનેમ |  |
| `tpl_digilocker_notes` | Notes | નોંધ |  |
| `tpl_digilocker_portal_password` | Portal password | પાસવર્ડ |  |
| `tpl_digilocker_security_pin` | Security pin | સુરક્ષા પિન |  |
| `tpl_driving_license` | Driving License | ડ્રાઇવિંગ લાઇસન્સ |  |
| `tpl_driving_license_dl_number` | Dl number | લાઇસન્સ નંબર |  |
| `tpl_driving_license_dob` | Dob | જન્મ તારીખ |  |
| `tpl_driving_license_expiry_date` | Expiry date | આ તારીખ સુધી માન્ય |  |
| `tpl_driving_license_file_copy` | Scanned copy | સ્કૅન કરેલી નકલ |  |
| `tpl_driving_license_issue_date` | Issue date | જારી થયાની તારીખ |  |
| `tpl_driving_license_name_on_dl` | Name on dl | લાઇસન્સ પરનું નામ |  |
| `tpl_driving_license_notes` | Notes | નોંધ |  |
| `tpl_driving_license_rto_location` | Rto location | RTO |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | વાહન શ્રેણીઓ |  |
| `tpl_epf_pension` | Epf Pension | EPF / પેન્શન |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | જોડાયેલ મોબાઇલ |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | EPF પરનું નામ |  |
| `tpl_epf_pension_nominee` | Nominee | નૉમિની |  |
| `tpl_epf_pension_notes` | Notes | નોંધ |  |
| `tpl_epf_pension_password` | Password | પાસવર્ડ |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | PF મેમ્બર ID |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO પાસવર્ડ |  |
| `tpl_epf_pension_scheme` | Scheme | યોજના |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | સરકારી ઓળખપત્ર |  |
| `tpl_gov_id_expiry` | Expiry | મુદત |  |
| `tpl_gov_id_file_copy` | Scanned copy | સ્કૅન કરેલી નકલ |  |
| `tpl_gov_id_id_kind` | ID type | ઓળખપત્રનો પ્રકાર |  |
| `tpl_gov_id_id_number` | ID number | ઓળખ નંબર |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | ઓળખપત્ર પ્રમાણે નામ |  |
| `tpl_gov_id_notes` | Notes | નોંધ |  |
| `tpl_gov_id_portal_login` | Portal login | પોર્ટલ લૉગિન |  |
| `tpl_gov_id_portal_password` | Portal password | પોર્ટલ પાસવર્ડ |  |
| `tpl_insurance` | Insurance | વીમો |  |
| `tpl_insurance_agent_contact` | Agent contact | એજન્ટનો સંપર્ક |  |
| `tpl_insurance_commencement_date` | Commencement date | શરૂ થયાની તારીખ |  |
| `tpl_insurance_insurer` | Insurer | વીમા કંપની |  |
| `tpl_insurance_maturity_date` | Maturity date | પાકતી તારીખ |  |
| `tpl_insurance_nominee` | Nominee | નૉમિની |  |
| `tpl_insurance_notes` | Notes | નોંધ |  |
| `tpl_insurance_policy_number` | Policy number | પૉલિસી નંબર |  |
| `tpl_insurance_policy_term` | Policy term | પૉલિસીની મુદત |  |
| `tpl_insurance_policy_type` | Policy type | પૉલિસીનો પ્રકાર |  |
| `tpl_insurance_portal_login` | Portal login | પોર્ટલ લૉગિન |  |
| `tpl_insurance_portal_password` | Portal password | પોર્ટલ પાસવર્ડ |  |
| `tpl_insurance_premium_amount` | Premium amount | પ્રીમિયમ રકમ |  |
| `tpl_insurance_premium_due_date` | Premium due date | પ્રીમિયમની તારીખ |  |
| `tpl_insurance_premium_mode` | Premium mode | પ્રીમિયમ કેવી રીતે ભરો છો |  |
| `tpl_insurance_sum_assured` | Sum assured | વીમાની રકમ |  |
| `tpl_login` | Login | લૉગિન |  |
| `tpl_login_notes` | Notes | નોંધ |  |
| `tpl_login_password` | Password | પાસવર્ડ |  |
| `tpl_login_recovery_codes` | Recovery codes | રિકવરી કોડ |  |
| `tpl_login_username` | Username | યુઝરનેમ |  |
| `tpl_login_website` | Website | વેબસાઇટ |  |
| `tpl_pan_card` | Pan Card | PAN કાર્ડ |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | આધાર સાથે જોડાયેલું |  |
| `tpl_pan_card_dob` | Dob | જન્મ તારીખ |  |
| `tpl_pan_card_e_filing_password` | E filing password | ઈ-ફાઇલિંગ પાસવર્ડ |  |
| `tpl_pan_card_fathers_name` | Fathers name | પિતાનું નામ |  |
| `tpl_pan_card_file_copy` | Scanned copy | સ્કૅન કરેલી નકલ |  |
| `tpl_pan_card_name_on_pan` | Name on pan | PAN પરનું નામ |  |
| `tpl_pan_card_notes` | Notes | નોંધ |  |
| `tpl_pan_card_pan_number` | Pan number | PAN |  |
| `tpl_passkey` | Passkey | પાસકી |  |
| `tpl_passkey_credential_id` | Credential ID | ક્રેડેન્શિયલ ID |  |
| `tpl_passkey_notes` | Notes | નોંધ |  |
| `tpl_passkey_private_key` | Private key | ખાનગી કી |  |
| `tpl_passkey_sign_count` | Sign count | સાઇન કાઉન્ટ |  |
| `tpl_passkey_user_handle` | User handle | યુઝર હેન્ડલ |  |
| `tpl_passkey_username` | Username | યુઝરનેમ |  |
| `tpl_passkey_website` | Website | વેબસાઇટ |  |
| `tpl_passport` | Passport | પાસપોર્ટ |  |
| `tpl_passport_dob` | Dob | જન્મ તારીખ |  |
| `tpl_passport_expiry_date` | Expiry date | પૂરી થયાની તારીખ |  |
| `tpl_passport_file_copy` | Scanned copy | સ્કૅન કરેલી નકલ |  |
| `tpl_passport_given_names` | Given names | આપેલું નામ |  |
| `tpl_passport_issue_date` | Issue date | જારી થયાની તારીખ |  |
| `tpl_passport_notes` | Notes | નોંધ |  |
| `tpl_passport_passport_number` | Passport number | પાસપોર્ટ નંબર |  |
| `tpl_passport_place_of_issue` | Place of issue | જારી કરવાનું સ્થળ |  |
| `tpl_passport_portal_login` | Portal login | Passport Seva લૉગિન |  |
| `tpl_passport_portal_password` | Portal password | Passport Seva પાસવર્ડ |  |
| `tpl_passport_surname` | Surname | અટક |  |
| `tpl_secure_note` | Secure Note | સુરક્ષિત નોંધ |  |
| `tpl_secure_note_attachment` | Attachment | જોડાણ |  |
| `tpl_secure_note_body` | Note | નોંધ |  |
| `tpl_shopping` | Shopping | શૉપિંગ ખાતું |  |
| `tpl_shopping_gift_card_code` | Gift card code | ગિફ્ટ કાર્ડ કોડ |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | ગિફ્ટ કાર્ડ પિન |  |
| `tpl_shopping_membership_id` | Membership id | સભ્યપદ ID |  |
| `tpl_shopping_notes` | Notes | નોંધ |  |
| `tpl_shopping_password` | Password | પાસવર્ડ |  |
| `tpl_shopping_registered_email` | Registered email | નોંધાયેલ ઈમેલ |  |
| `tpl_shopping_registered_mobile` | Registered mobile | નોંધાયેલ મોબાઇલ |  |
| `tpl_shopping_wallet_pin` | Wallet pin | વૉલેટ પિન |  |
| `tpl_telecom` | Telecom | મોબાઇલ અને ઇન્ટરનેટ |  |
| `tpl_telecom_account_number` | Account number | ખાતા નંબર |  |
| `tpl_telecom_circle` | Circle | સર્કલ |  |
| `tpl_telecom_mobile_number` | Mobile number | મોબાઇલ નંબર |  |
| `tpl_telecom_notes` | Notes | નોંધ |  |
| `tpl_telecom_operator` | Operator | કંપની |  |
| `tpl_telecom_plan_type` | Plan type | પ્લાનનો પ્રકાર |  |
| `tpl_telecom_portal_password` | Portal password | પોર્ટલ પાસવર્ડ |  |
| `tpl_telecom_puk` | PUK code | PUK કોડ |  |
| `tpl_telecom_renewal_date` | Renewal date | રિચાર્જની તારીખ |  |
| `tpl_telecom_sim_number` | Sim number | સિમ નંબર (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | સિમ પિન |  |
| `tpl_transit` | Transit | ટ્રાન્ઝિટ પાસ |  |
| `tpl_transit_login_password` | Login password | લૉગિન પાસવર્ડ |  |
| `tpl_transit_notes` | Notes | નોંધ |  |
| `tpl_transit_operator_name` | Operator name | કંપની |  |
| `tpl_transit_registered_email` | Registered email | નોંધાયેલ ઈમેલ |  |
| `tpl_transit_registered_mobile` | Registered mobile | નોંધાયેલ મોબાઇલ |  |
| `tpl_transit_smart_card_number` | Smart card number | સ્માર્ટ કાર્ડ નંબર |  |
| `tpl_transit_wallet_pin` | Wallet pin | વૉલેટ પિન |  |
| `tpl_travel_booking` | Travel Booking | મુસાફરી બુકિંગ |  |
| `tpl_travel_booking_account_username` | Account username | યુઝરનેમ |  |
| `tpl_travel_booking_login_password` | Login password | લૉગિન પાસવર્ડ |  |
| `tpl_travel_booking_notes` | Notes | નોંધ |  |
| `tpl_travel_booking_provider` | Provider | કંપની |  |
| `tpl_travel_booking_registered_email` | Registered email | નોંધાયેલ ઈમેલ |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | નોંધાયેલ મોબાઇલ |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | વૉલેટ પિન |  |
| `tpl_upi` | UPI | UPI |  |
| `tpl_upi_app_name` | App name | UPI ઍપ |  |
| `tpl_upi_apps_used` | Apps used | કઈ ઍપમાં ચાલુ છે |  |
| `tpl_upi_linked_account` | Linked account | જોડાયેલું ખાતું |  |
| `tpl_upi_notes` | Notes | નોંધ |  |
| `tpl_upi_upi_id` | UPI ID | UPI ID |  |
| `tpl_upi_upi_pin` | UPI PIN | UPI પિન |  |
| `tpl_utility` | Utility | બિલ અને જોડાણ |  |
| `tpl_utility_account_holder` | Account holder | ખાતાધારક |  |
| `tpl_utility_consumer_number` | Consumer number | ગ્રાહક નંબર |  |
| `tpl_utility_due_day` | Bill due day | બિલ ભરવાનો દિવસ |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag ID |  |
| `tpl_utility_notes` | Notes | નોંધ |  |
| `tpl_utility_portal_login` | Portal login | પોર્ટલ લૉગિન |  |
| `tpl_utility_portal_password` | Portal password | પોર્ટલ પાસવર્ડ |  |
| `tpl_utility_provider` | Provider | સેવા આપનાર કંપની |  |
| `tpl_utility_utility_kind` | Utility kind | કયી વસ્તુનું બિલ |  |
| `tpl_utility_vehicle_number` | Vehicle number | વાહન નંબર |  |
| `tpl_utility_wifi_password` | Wi-Fi password | Wi-Fi પાસવર્ડ |  |
| `tpl_voter_id` | Voter Id | મતદાર ઓળખપત્ર |  |
| `tpl_voter_id_constituency` | Constituency | મતવિસ્તાર |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC નંબર |  |
| `tpl_voter_id_file_copy` | Scanned copy | સ્કૅન કરેલી નકલ |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | મતદાર ઓળખપત્ર પરનું નામ |  |
| `tpl_voter_id_notes` | Notes | નોંધ |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP પાસવર્ડ |  |
| `tr_days_many` | %1$d days left | %1$d દિવસ બાકી |  |
| `tr_days_one` | %1$d day left | %1$d દિવસ બાકી |  |
| `tr_gone_today` | gone today | આજે જતું રહેશે |  |
| `tr_restore` | Restore | પાછું લાવો |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | તે પછી તે કાયમ માટે જતા રહે છે — બીજે ક્યાંય કોઈ નકલ નથી. |  |
| `ui_hide_passphrase` | Hide passphrase | પાસફ્રેઝ છુપાવો |  |
| `ui_label_count` | %1$s, %2$s | %1$s, %2$s |  |
| `ui_show_passphrase` | Show passphrase | પાસફ્રેઝ બતાવો |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | આ જ ફોન પર %1$d રેકોર્ડ સાથે મેળવ્યું. કંઈ પણ ક્યાંય મોકલાયું નથી. |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | આ જ ફોન પર %1$d રેકોર્ડ સાથે મેળવ્યું. કંઈ પણ ક્યાંય મોકલાયું નથી. |  |
| `vh_count_many` | %1$d things worth a look. | %1$d વાતો જોવા જેવી છે. |  |
| `vh_count_one` | %1$d thing worth a look. | %1$d વાત જોવા જેવી છે. |  |
| `vh_empty` | No reused, weak or expiring credentials. | કોઈ ફરી વપરાયેલી, નબળી કે પૂરી થતી માહિતી નથી. |  |
| `vh_kind_common` | Commonly guessed | સહેલાઈથી ધારી શકાય એવો |  |
| `vh_kind_expiring` | Expiring | પૂરો થતો |  |
| `vh_kind_reused` | Reused password | ફરી વપરાયેલો પાસવર્ડ |  |
| `vh_kind_weak` | Weak | નબળો |  |
| `vh_no_kit_title` | No recovery kit saved | કોઈ રિકવરી કિટ સાચવેલી નથી |  |
| `vh_nothing` | Nothing to fix. | ઠીક કરવા જેવું કંઈ નથી. |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | ઑફલાઇન |  |
| `wl_chip_open` | Open source | ઓપન સોર્સ |  |
| `wl_create` | Create a new vault | નવી તિજોરી બનાવો |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | ન ઈમેલ · ન ખાતું · કંઈ પણ આ ફોનની બહાર નહીં |  |
| `wl_head_1` | Your keys. | તમારી ચાવીઓ. |  |
| `wl_head_2` | Your device. | તમારો ફોન. |  |
| `wl_head_3` | No server. | કોઈ સર્વર નહીં. |  |
| `wl_restore` | Restore from Recovery Kit | રિકવરી કિટમાંથી પાછી લાવો |  |
| `wl_sr_headline` | Your keys. Your device. No server. | તમારી ચાવીઓ. તમારો ફોન. કોઈ સર્વર નહીં. |  |
