# Malayalam (`ml`) — translation review

766 strings, machine-checked complete with no format-specifier
mismatches. What is missing is a native speaker reading them.

**How to use this.** Work down from Priority 1 and stop wherever you run out
of time — the order is by consequence, not by screen. Correct the value in
`app/src/main/res/values-ml/strings.xml` (or `strings_templates.xml`), keeping the
key and any `%1$s`-style placeholder exactly as it is.

Watch for: an instruction that reads as optional when it is not, a warning
softened into advice, and anything about the recovery key or a passphrase
that suggests support can help — nobody can.


## Priority 1 — security, warnings, destructive actions

| key | English | Malayalam | ok? |
|---|---|---|---|
| `au_close` | Close | അടയ്ക്കുക |  |
| `au_no_qr_found` | No valid TOTP QR code found in selected image | തിരഞ്ഞെടുത്ത ചിത്രത്തിൽ ശരിയായ TOTP QR കോഡ് കിട്ടിയില്ല |  |
| `gl_not_found_body` | Start blank and name your own fields — templates only pre-fill labels, never data. | ഒഴിഞ്ഞതിൽ നിന്ന് തുടങ്ങി നിങ്ങളുടെ ഫീൽഡുകൾക്ക് നിങ്ങൾ തന്നെ പേരിടുക — ടെംപ്ലേറ്റുകൾ ലേബലുകൾ മാത്രമേ നിറയ്ക്കൂ, ഡാറ്റ ഒരിക്കലുമില്ല. |  |
| `hm_close_search` | Close search | തിരച്ചിൽ അടയ്ക്കുക |  |
| `hm_reassurance` | Never leaves this device. Encrypted at rest. | ഈ ഫോണിൽ നിന്ന് ഒരിക്കലും പുറത്തുപോകില്ല. സൂക്ഷിക്കുമ്പോൾ എൻക്രിപ്റ്റ് ചെയ്തത്. |  |
| `ic_delete_file` | Now delete the file you just imported. It is a plain-text list of your passwords and it is still sitting in your Downloads. | ഇപ്പോൾ നിങ്ങൾ ഇറക്കുമതി ചെയ്ത ഫയൽ നീക്കുക. അത് നിങ്ങളുടെ പാസ്‌വേഡുകളുടെ തുറന്ന പട്ടികയാണ്, ഇപ്പോഴും നിങ്ങളുടെ Downloads-ൽ കിടക്കുന്നു. |  |
| `ic_local_only` | Everything is decrypted on this device only. Nothing is uploaded, because this app cannot open a network connection. | എല്ലാം ഈ ഫോണിൽ മാത്രം ഡിക്രിപ്റ്റ് ചെയ്യുന്നു. ഒന്നും അപ്‌ലോഡ് ആകുന്നില്ല, കാരണം ഈ ആപ്പിന് നെറ്റ്‌വർക്ക് കണക്ഷൻ തുറക്കാൻ പോലും കഴിയില്ല. |  |
| `msg_safety_tip_1` | Banks NEVER ask for your OTP. Anyone who does is a fraudster. | ബാങ്കുകൾ ഒരിക്കലും നിങ്ങളുടെ OTP ചോദിക്കില്ല. ചോദിക്കുന്നവൻ തട്ടിപ്പുകാരൻ. |  |
| `msg_safety_tip_2` | No bank official will ever ask you to install a screen-sharing app. | ഒരു ബാങ്ക് ഉദ്യോഗസ്ഥനും സ്ക്രീൻ ഷെയർ ചെയ്യുന്ന ആപ്പ് ഇടാൻ പറയില്ല. |  |
| `msg_safety_tip_3` | Your UPI PIN is only for the UPI app keypad — never tell it to anyone on a call. | നിങ്ങളുടെ UPI പിൻ UPI ആപ്പിന്റെ കീപാഡിന് മാത്രം — കോളിൽ ആരോടും പറയരുത്. |  |
| `msg_safety_tip_4` | KYC never expires in a day. “KYC expiring today” messages are scams. | KYC ഒരു ദിവസംകൊണ്ട് തീരില്ല. “ഇന്ന് KYC തീരുന്നു” എന്ന സന്ദേശങ്ങൾ തട്ടിപ്പാണ്. |  |
| `msg_safety_tip_5` | To RECEIVE money you never need to enter your PIN or scan a QR code. | പണം കിട്ടാൻ പിൻ ഇടേണ്ടതോ QR സ്കാൻ ചെയ്യേണ്ടതോ ഇല്ല. |  |
| `msg_safety_tip_6` | Electricity bill disconnection SMS with a personal number? It\'s a scam. | കറന്റ് കട്ടാകുമെന്ന SMS, അതിൽ ആരുടെയോ സ്വകാര്യ നമ്പർ? അത് തട്ടിപ്പാണ്. |  |
| `nav_close_menu` | Close menu | മെനു അടയ്ക്കുക |  |
| `nfc_cannot_read` | Cannot read cards | കാർഡ് വായിക്കാനാവില്ല |  |
| `nfc_no_hardware` | This phone has no NFC, so it cannot read a card. | ഈ ഫോണിൽ NFC ഇല്ല, അതിനാൽ കാർഡ് വായിക്കാനാവില്ല. |  |
| `ob_fact_lost_title` | If you lose your keys | താക്കോലുകൾ നഷ്ടപ്പെട്ടാൽ |  |
| `ob_fact_network_note` | The app literally cannot phone home | ഈ ആപ്പിന് എവിടേക്കും ബന്ധപ്പെടാൻ കഴിയില്ല |  |
| `ob_fact_quick_note` | Biometric never leaves the enclave | ബയോമെട്രിക് സുരക്ഷാ ചിപ്പിന് പുറത്ത് ഒരിക്കലും പോകില്ല |  |
| `ob_kit_beside_vault_body` | You saved the key into the same folder that syncs your encrypted vault. Anyone who gets that folder now gets both halves. Move the key somewhere else — paper, a different account, a drawer. | നിങ്ങളുടെ എൻക്രിപ്റ്റ് ചെയ്ത കലവറ സിങ്ക് ചെയ്യുന്ന അതേ ഫോൾഡറിലാണ് താക്കോൽ വെച്ചത്. ഇനി ആ ഫോൾഡർ കിട്ടുന്നയാൾക്ക് രണ്ടും കിട്ടും. താക്കോൽ വേറെ എവിടെയെങ്കിലും വെക്കുക — കടലാസ്, മറ്റൊരു അക്കൗണ്ട്, അല്ലെങ്കിൽ ഒരു അലമാര. |  |
| `ob_kit_beside_vault_title` | That is beside your vault file | ഇത് നിങ്ങളുടെ കലവറ ഫയലിന് അരികിലാണ് |  |
| `ob_kit_card_label` | ZEROKOSH RECOVERY | ZEROKOSH റിക്കവറി |  |
| `ob_kit_card_note` | Scan or type it back. Works after a reinstall, a factory reset, or a lost phone. | സ്കാൻ ചെയ്യുക അല്ലെങ്കിൽ ടൈപ്പ് ചെയ്യുക. വീണ്ടും ഇൻസ്റ്റാൾ, ഫാക്ടറി റീസെറ്റ്, ഫോൺ നഷ്ടപ്പെട്ടതിന് ശേഷവും പ്രവർത്തിക്കും. |  |
| `ob_kit_challenge_body` | Type group %1$d and group %2$d from the kit you just saved. | ഇപ്പോൾ സൂക്ഷിച്ച കിറ്റിൽ നിന്ന് കൂട്ടം %1$d, കൂട്ടം %2$d ടൈപ്പ് ചെയ്യുക. |  |
| `ob_kit_challenge_hint` | Group %1$d | കൂട്ടം %1$d |  |
| `ob_kit_challenge_locked` | Save the kit first, then type group %1$d and group %2$d back. | ആദ്യം കിറ്റ് സൂക്ഷിക്കുക, പിന്നെ കൂട്ടം %1$d, %2$d തിരികെ ടൈപ്പ് ചെയ്യുക. |  |
| `ob_kit_challenge_title` | Check you actually have it | കിറ്റ് ശരിക്കും നിങ്ങളുടെ കയ്യിലുണ്ടോ എന്ന് നോക്കുക |  |
| `ob_kit_challenge_wrong` | That does not match the key above. | ഇത് മുകളിലെ താക്കോലുമായി ചേരുന്നില്ല. |  |
| `ob_kit_confirm_body` | No one — including Zerokosh — can recover it for me. | ആർക്കും — Zerokosh-നും — ഇത് എനിക്കായി തിരികെ കൊണ്ടുവരാനാവില്ല. |  |
| `ob_kit_confirm_emph` | "I\'ve stored this offline. " | "ഞാൻ ഇത് ഓഫ്‌ലൈനിൽ വെച്ചു. " |  |
| `ob_kit_footer` | Shown once and never stored in plain text. You can create a new one from Settings whenever you can still get in. | ഒരിക്കൽ മാത്രം കാണും, ഒരിക്കലും തുറന്ന രൂപത്തിൽ സൂക്ഷിക്കില്ല. അകത്ത് വരാൻ കഴിയുമെങ്കിൽ ക്രമീകരണത്തിൽ നിന്ന് പുതിയത് ഉണ്ടാക്കുക. |  |
| `ob_kit_head_emph` | On paper. | കടലാസിൽ. |  |
| `ob_kit_head_lead` | "One key. " | "ഒരു താക്കോൽ. " |  |
| `ob_kit_head_tail` | " Never online." | " ഒരിക്കലും ഓൺലൈനിലല്ല." |  |
| `ob_kit_offline_body` | Not Gmail, not WhatsApp, not a screenshot. A safe, a bank locker, or a steel plate. | Gmail വേണ്ട, WhatsApp വേണ്ട, സ്ക്രീൻഷോട്ട് വേണ്ട. അലമാര, ബാങ്ക് ലോക്കർ, അല്ലെങ്കിൽ ഉരുക്ക് തകിട്. |  |
| `ob_kit_offline_title` | Keep it off the internet | അത് ഇന്റർനെറ്റിൽ നിന്ന് അകറ്റി വെക്കുക |  |
| `ob_kit_print` | Print | അച്ചടിക്കുക |  |
| `ob_kit_print_note` | A printer, or Save as PDF | പ്രിന്റർ, അല്ലെങ്കിൽ PDF ആയി സൂക്ഷിക്കുക |  |
| `ob_kit_qr` | QR image | QR ചിത്രം |  |
| `ob_kit_qr_cd` | Recovery key QR code | റിക്കവറി താക്കോലിന്റെ QR കോഡ് |  |
| `ob_kit_qr_note` | To an offline gallery | ഓഫ്‌ലൈൻ ഗാലറിയിലേക്ക് |  |
| `ob_kit_regenerate` | Regenerate | പുതിയത് ഉണ്ടാക്കുക |  |
| `ob_kit_save_failed` | That did not save. Try again, or pick another place. | ഇത് സൂക്ഷിച്ചില്ല. വീണ്ടും ശ്രമിക്കുക, അല്ലെങ്കിൽ മറ്റൊരു സ്ഥലം തിരഞ്ഞെടുക്കുക. |  |
| `ob_kit_save_pdf` | Save PDF | PDF സൂക്ഷിക്കുക |  |
| `ob_kit_save_pdf_note` | Printable one-page kit | ഒരു താളിന്റെ അച്ചടിക്കാവുന്ന കിറ്റ് |  |
| `ob_kit_saved` | I\'ve saved my kit | ഞാൻ എന്റെ കിറ്റ് സൂക്ഷിച്ചു |  |
| `ob_kit_saved_to` | Saved to %1$s | %1$s-ൽ സൂക്ഷിച്ചു |  |
| `ob_kit_sent_to_printer` | Sent to the printer | പ്രിന്ററിലേക്ക് അയച്ചു |  |
| `ob_kit_skip` | I\'ll do this later | ഇത് പിന്നീട് ചെയ്യാം |  |
| `ob_kit_skip_note` | Your vault still works. Zerokosh will keep reminding you until a kit is saved. | നിങ്ങളുടെ കലവറ പ്രവർത്തിക്കും. കിറ്റ് സൂക്ഷിക്കുന്നത് വരെ Zerokosh ഓർമ്മിപ്പിച്ചുകൊണ്ടിരിക്കും. |  |
| `ob_kit_subhead` | Generated on this device, shown once. It is the only way back in if the passphrase slips away. | ഈ ഫോണിൽ തന്നെ ഉണ്ടാക്കിയത്, ഒരിക്കൽ മാത്രം കാണും. പാസ്‌ഫ്രെയ്സ് മറന്നാൽ അകത്തേക്ക് തിരികെ വരാനുള്ള ഒരേയൊരു വഴി. |  |
| `ob_kit_working` | Working… | ജോലി നടക്കുന്നു… |  |
| `ob_lang_subhead` | Vault labels, templates and warnings adapt instantly. Change it anytime in Settings. | കലവറയുടെ ലേബലുകൾ, ടെംപ്ലേറ്റുകൾ, മുന്നറിയിപ്പുകൾ ഉടനെ മാറും. ക്രമീകരണത്തിൽ എപ്പോൾ വേണമെങ്കിലും മാറ്റാം. |  |
| `ob_pass_confirm` | Confirm | വീണ്ടും ടൈപ്പ് ചെയ്യുക |  |
| `ob_pass_counter` | %1$d / %2$d | %1$d / %2$d |  |
| `ob_pass_footer` | We never see this. There is no reset link. | ഇത് ഞങ്ങൾ ഒരിക്കലും കാണില്ല. റീസെറ്റ് ലിങ്കില്ല. |  |
| `ob_pass_head_emph` | held only | നിങ്ങളുടെ കയ്യിൽ മാത്രം |  |
| `ob_pass_head_lead` | "One secret, " | "ഒരേയൊരു രഹസ്യം, " |  |
| `ob_pass_head_tail` | " by you." | . |  |
| `ob_pass_no_match` | no match | ചേരുന്നില്ല |  |
| `ob_pass_seal` | Seal the vault | കലവറ പൂട്ടുക |  |
| `ob_pass_sealing` | Sealing… | പൂട്ടുന്നു… |  |
| `ob_pass_subhead` | Three or four unrelated words beat one clever word. Nothing leaves this screen. | ബന്ധമില്ലാത്ത മൂന്നോ നാലോ വാക്കുകൾ, ഒരു മിടുക്കൻ വാക്കിനെക്കാൾ നല്ലത്. ഈ സ്ക്രീനിൽ നിന്ന് ഒന്നും പുറത്തുപോകില്ല. |  |
| `ob_pass_tab_passphrase` | Passphrase | പാസ്‌ഫ്രെയ്സ് |  |
| `ob_pass_tab_pin` | 6-digit PIN | 6 അക്ക പിൻ |  |
| `ob_quick_touch_body` | Your fingerprint is stored inside your phone\'s secure enclave. It never leaves this device. | നിങ്ങളുടെ വിരലടയാളം ഫോണിന്റെ സുരക്ഷാ ചിപ്പിനുള്ളിൽ ഇരിക്കും. അത് ഈ ഫോണിൽ നിന്ന് ഒരിക്കലും പുറത്തുപോകില്ല. |  |
| `ob_trust_continue` | I understand · Continue | മനസ്സിലായി · തുടരുക |  |
| `ob_trust_head_emph` | don\'t | അറിയാത്തത് |  |
| `ob_trust_head_lead` | "Exactly what we " | "ഞങ്ങൾക്ക് ശരിക്കും എന്താണ് " |  |
| `ob_trust_head_tail` | " know." | " എന്നത്." |  |
| `ob_trust_keys_body` | Forget your passphrase and lose the Recovery Kit, and the vault stays sealed — for you, for us, for anyone. | പാസ്‌ഫ്രെയ്സ് മറന്ന് റിക്കവറി കിറ്റും നഷ്ടപ്പെട്ടാൽ, കലവറ അടഞ്ഞുതന്നെ കിടക്കും — നിങ്ങൾക്കും, ഞങ്ങൾക്കും, ആർക്കും. |  |
| `ob_trust_keys_emph` | "You hold the keys. " | "താക്കോലുകൾ നിങ്ങളുടെ കയ്യിൽ. " |  |
| `ob_trust_stat_files` | .kosh file on device | ഫോണിൽ .kosh ഫയൽ |  |
| `ob_trust_stat_servers` | servers contacted | സെർവറുമായി ബന്ധം |  |
| `ob_trust_stat_trackers` | trackers or SDKs | ട്രാക്കർ അല്ലെങ്കിൽ SDK |  |
| `ob_trust_subhead` | Read this once. It is the whole security model, in plain words. | ഇത് ഒരിക്കൽ വായിക്കുക. മുഴുവൻ സുരക്ഷാ സംവിധാനവും ഇതാണ്, ലളിതമായ വാക്കുകളിൽ. |  |
| `ob_trust_tag_audited` | Audited build | ഓഡിറ്റ് ചെയ്ത ബിൽഡ് |  |
| `ob_trust_tag_reproducible` | Reproducible APK | വീണ്ടും ഉണ്ടാക്കാവുന്ന APK |  |
| `pc_body` | You have been unlocking with your fingerprint. If your fingerprint ever stops working, this is what gets you in — so it is worth checking you still know it. | നിങ്ങൾ വിരലടയാളം കൊണ്ടാണ് തുറക്കുന്നത്. വിരലടയാളം എപ്പോഴെങ്കിലും പ്രവർത്തിക്കാതായാൽ, ഇതാണ് നിങ്ങളെ അകത്ത് കൊണ്ടുവരിക — അതിനാൽ ഒന്ന് നോക്കുന്നത് നല്ലതാണ്. |  |
| `pc_confirm` | Check | പരിശോധിക്കുക |  |
| `pc_correct` | Still correct. Nothing to do. | ഇപ്പോഴും ശരിയാണ്. ഒന്നും ചെയ്യേണ്ടതില്ല. |  |
| `pc_forgot` | I cannot remember it | എനിക്ക് ഓർമ്മ വരുന്നില്ല |  |
| `pc_later` | Not now | ഇപ്പോൾ വേണ്ട |  |
| `pc_reset_action` | Set new passphrase | പുതിയത് വെക്കുക |  |
| `pc_reset_body` | Your fingerprint can open this vault, so it can also set a new passphrase — no Recovery Kit needed. You will be asked for it once more to confirm. | നിങ്ങളുടെ വിരലടയാളത്തിന് ഈ കലവറ തുറക്കാനാകും, അതിനാൽ അതിന് പുതിയ പാസ്‌ഫ്രെയ്സും വെക്കാനാകും — റിക്കവറി കിറ്റ് വേണ്ട. ഉറപ്പാക്കാൻ ഒരിക്കൽ കൂടി ചോദിക്കും. |  |
| `pc_reset_done` | Passphrase changed. Quick unlock has been set up again. | പാസ്‌ഫ്രെയ്സ് മാറി. വേഗ അൺലോക്ക് വീണ്ടും സെറ്റ് ചെയ്തു. |  |
| `pc_reset_failed` | That did not work. Your old passphrase is still the one in use. | ഇത് നടന്നില്ല. നിങ്ങളുടെ പഴയ പാസ്‌ഫ്രെയ്സ് തന്നെയാണ് ഇപ്പോഴും. |  |
| `pc_reset_title` | Set a new passphrase | പുതിയ പാസ്‌ഫ്രെയ്സ് വെക്കുക |  |
| `pc_title` | Do you still remember your passphrase? | നിങ്ങളുടെ പാസ്‌ഫ്രെയ്സ് ഇപ്പോഴും ഓർമ്മയുണ്ടോ? |  |
| `pc_wrong` | That is not it. You can set a new one instead. | ഇത് അതല്ല. പകരം പുതിയത് വെക്കാം. |  |
| `qa_totp` | TOTP | TOTP |  |
| `rd_forget_body` | The %1$d remembered value(s) for this record are removed. This cannot be undone. | ഈ രേഖയ്ക്കായി ഓർത്തുവെച്ച %1$d മൂല്യങ്ങൾ നീക്കും. ഇത് തിരികെ കൊണ്ടുവരാനാവില്ല. |  |
| `scr_autofill_save_failed` | Zerokosh could not save that login. | Zerokosh ആ ലോഗിൻ സൂക്ഷിക്കാനായില്ല. |  |
| `scr_autofill_unlock_first` | Unlock Zerokosh to fill | നിറയ്ക്കാൻ Zerokosh തുറക്കുക |  |
| `scr_create_body` | This one passphrase locks everything. Choose something long that only you would know. | ഈ ഒറ്റ പാസ്‌ഫ്രെയ്സാണ് എല്ലാം പൂട്ടി വെക്കുന്നത്. നിങ്ങൾക്ക് മാത്രം അറിയാവുന്ന നീളമുള്ള ഒന്ന് തിരഞ്ഞെടുക്കുക. |  |
| `scr_create_button` | Lock it in | പൂട്ടിക്കോളൂ |  |
| `scr_create_confirm_hint` | Type it again | വീണ്ടും ടൈപ്പ് ചെയ്യുക |  |
| `scr_create_hint` | Passphrase (at least 10 characters) | പാസ്‌ഫ്രെയ്സ് (കുറഞ്ഞത് 10 അക്ഷരം) |  |
| `scr_create_mismatch` | The two entries don\'t match | രണ്ടും ഒരുപോലെയല്ല |  |
| `scr_create_pin_hint` | 6-digit PIN | 6 അക്ക പിൻ |  |
| `scr_create_pin_option` | Use a 6-digit PIN instead | പകരം 6 അക്ക പിൻ വെക്കുക |  |
| `scr_create_pin_unavailable` | A PIN needs a phone with fingerprint or face unlock. Please use a passphrase. | പിന്നിന് വിരലടയാളമോ മുഖം അൺലോക്കോ ഉള്ള ഫോൺ വേണം. ദയവായി പാസ്‌ഫ്രെയ്സ് തിരഞ്ഞെടുക്കുക. |  |
| `scr_create_pin_why` | A PIN is allowed because this phone protects it with its own security chip and your fingerprint or face. | പിൻ അനുവദിക്കുന്നത് ഈ ഫോൺ അതിനെ സ്വന്തം സുരക്ഷാ ചിപ്പുകൊണ്ടും നിങ്ങളുടെ വിരലടയാളം അല്ലെങ്കിൽ മുഖംകൊണ്ടും കാക്കുന്നതുകൊണ്ടാണ്. |  |
| `scr_create_strength_fair` | Fair | കുഴപ്പമില്ല |  |
| `scr_create_strength_good` | Good | നല്ലത് |  |
| `scr_create_strength_strong` | Strong | ബലമുള്ളത് |  |
| `scr_create_strength_weak` | Weak | ദുർബലം |  |
| `scr_create_title` | Create your passphrase | നിങ്ങളുടെ പാസ്‌ഫ്രെയ്സ് ഉണ്ടാക്കുക |  |
| `scr_create_too_short` | Use at least 10 characters — longer is stronger | കുറഞ്ഞത് 10 അക്ഷരം വേണം — എത്ര നീളമുണ്ടോ അത്ര ബലം |  |
| `scr_create_working` | Preparing your vault… | നിങ്ങളുടെ കലവറ തയ്യാറാകുന്നു… |  |
| `scr_detail_delete` | Delete | നീക്കുക |  |
| `scr_detail_delete_confirm_body` | It moves to Recently deleted for 30 days, and disappears from your other devices after they sync. | അത് 30 ദിവസം “അടുത്തിടെ നീക്കിയവ”യിൽ ഇരിക്കും, നിങ്ങളുടെ മറ്റ് ഫോണുകളുമായി സിങ്ക് ആയാൽ പോകും. |  |
| `scr_detail_delete_confirm_title` | Delete this record? | ഈ രേഖ നീക്കണോ? |  |
| `scr_detail_delete_confirm_yes` | Delete | നീക്കുക |  |
| `scr_edit_totp_hint` | Paste the secret or otpauth:// link | സീക്രെട്ട് അല്ലെങ്കിൽ otpauth:// ലിങ്ക് ഒട്ടിക്കുക |  |
| `scr_lock_biometric_subtitle` | Use your fingerprint or face | നിങ്ങളുടെ വിരലടയാളമോ മുഖമോ ഉപയോഗിക്കുക |  |
| `scr_lock_biometric_title` | Unlock Zerokosh | Zerokosh തുറക്കുക |  |
| `scr_lock_cooldown` | Too many tries. Wait %1$d seconds. | പലതവണ തെറ്റായി ശ്രമിച്ചു. %1$d സെക്കൻഡ് കാത്തിരിക്കുക. |  |
| `scr_lock_hint` | Passphrase | പാസ്‌ഫ്രെയ്സ് |  |
| `scr_lock_recovery_hint` | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX | KSH-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX |  |
| `scr_lock_recovery_invalid` | That recovery key isn\'t right — check it letter by letter | ഈ റിക്കവറി താക്കോൽ ശരിയല്ല — ഓരോ അക്ഷരവും ഒത്തുനോക്കുക |  |
| `scr_lock_title` | Vault is locked | കലവറ പൂട്ടിയിരിക്കുന്നു |  |
| `scr_lock_unlock` | Unlock | തുറക്കുക |  |
| `scr_lock_use_passphrase` | Use passphrase | പാസ്‌ഫ്രെയ്സ് ഉപയോഗിക്കുക |  |
| `scr_lock_use_passphrase_once` | Please unlock with your passphrase once | ദയവായി ഒരിക്കൽ പാസ്‌ഫ്രെയ്സ് കൊണ്ട് തുറക്കുക |  |
| `scr_lock_use_recovery` | Use Recovery Key | റിക്കവറി താക്കോൽ ഉപയോഗിക്കുക |  |
| `scr_lock_wrong` | Wrong passphrase | പാസ്‌ഫ്രെയ്സ് തെറ്റാണ് |  |
| `scr_recovery_body` | If you ever forget your passphrase, this key is the ONLY way back in. We cannot reset it for you — nobody can. | എപ്പോഴെങ്കിലും പാസ്‌ഫ്രെയ്സ് മറന്നാൽ, അകത്തേക്ക് തിരികെ വരാനുള്ള ഒരേയൊരു വഴി ഇതാണ്. ഞങ്ങൾക്ക് അത് റീസെറ്റ് ചെയ്യാനാവില്ല — ആർക്കും കഴിയില്ല. |  |
| `scr_recovery_confirm_check` | I have written it down and kept it somewhere safe | ഞാൻ അത് എഴുതി സുരക്ഷിതമായ സ്ഥലത്ത് വെച്ചു |  |
| `scr_recovery_done` | Continue | തുടരുക |  |
| `scr_recovery_never_again` | This key is shown once. You can create a fresh one from Settings at any time, as long as you can still unlock. | ഈ താക്കോൽ ഒരിക്കൽ മാത്രമേ കാണൂ. നിങ്ങൾക്ക് ഇപ്പോഴും തുറക്കാൻ കഴിയുന്നിടത്തോളം, ക്രമീകരണത്തിൽ നിന്ന് എപ്പോൾ വേണമെങ്കിലും പുതിയത് ഉണ്ടാക്കാം. |  |
| `scr_recovery_pdf_instructions` | Keep this page with your property papers or other important documents. Anyone with this key can open your vault — treat it like a key to your locker. | ഈ താൾ നിങ്ങളുടെ വസ്തു രേഖകൾക്കോ മറ്റ് പ്രധാന രേഖകൾക്കോ ഒപ്പം വെക്കുക. ഈ താക്കോൽ ആരുടെ കയ്യിലുണ്ടോ അവർക്ക് നിങ്ങളുടെ കലവറ തുറക്കാം — അതിനെ ലോക്കർ താക്കോൽ പോലെ കാക്കുക. |  |
| `scr_recovery_pdf_saved` | Recovery Kit PDF saved | റിക്കവറി കിറ്റ് PDF സൂക്ഷിച്ചു |  |
| `scr_recovery_pdf_title` | Zerokosh Recovery Kit | Zerokosh റിക്കവറി കിറ്റ് |  |
| `scr_recovery_save_pdf` | Save as PDF | PDF ആയി സൂക്ഷിക്കുക |  |
| `scr_recovery_title` | Your Recovery Key | നിങ്ങളുടെ റിക്കവറി താക്കോൽ |  |
| `scr_trust_card1_body` | Everything you save lives in one locked file on your phone. It never goes to us — we have nowhere to put it. | നിങ്ങൾ സൂക്ഷിക്കുന്നതെല്ലാം നിങ്ങളുടെ ഫോണിലെ പൂട്ടിയ ഒരു ഫയലിൽ ഇരിക്കും. അത് ഒരിക്കലും ഞങ്ങളുടെ അടുത്ത് വരില്ല — അത് വെക്കാൻ ഞങ്ങൾക്ക് ഇടം തന്നെയില്ല. |  |
| `scr_trust_card1_title` | Your data stays on this device | നിങ്ങളുടെ ഡാറ്റ ഈ ഫോണിൽ തന്നെ ഇരിക്കും |  |
| `scr_trust_card2_body` | There is no Zerokosh account, no cloud, no sign-up. Only you can open this. Not even we can. | Zerokosh അക്കൗണ്ടില്ല, ക്ലൗഡില്ല, സൈൻ-അപ്പില്ല. ഇത് നിങ്ങൾക്ക് മാത്രമേ തുറക്കാൻ കഴിയൂ. ഞങ്ങൾക്കും കഴിയില്ല. |  |
| `scr_trust_card2_title` | We have no servers — nothing to hack, nothing to sell | ഞങ്ങൾക്ക് സെർവറുകളില്ല — ഹാക്ക് ചെയ്യാൻ ഒന്നുമില്ല, വിൽക്കാനും ഒന്നുമില്ല |  |
| `scr_trust_card3_body` | No subscription, no ads. Anyone can read our code and check every promise we make. | വരിസംഖ്യയില്ല, പരസ്യമില്ല. ആർക്കും ഞങ്ങളുടെ കോഡ് വായിച്ച് ഞങ്ങളുടെ ഓരോ വാക്കും പരിശോധിക്കാം. |  |
| `scr_trust_card3_title` | Free forever, open source | എന്നും സൗജന്യം, ഓപ്പൺ സോഴ്‌സ് |  |
| `scr_trust_continue` | Continue | തുടരുക |  |
| `se_lead` | Your changes were not saved, so nothing has been lost from the vault as it was. | നിങ്ങളുടെ മാറ്റങ്ങൾ സൂക്ഷിച്ചില്ല, അതിനാൽ കലവറയിൽ നേരത്തെ ഉണ്ടായിരുന്നതിൽ നിന്ന് ഒന്നും നഷ്ടപ്പെട്ടിട്ടില്ല. |  |
| `st_recently_deleted` | Recently deleted | അടുത്തിടെ നീക്കിയവ |  |
| `tpl_app_profile_totp` | One-time code (TOTP) | ഒറ്റത്തവണ കോഡ് (TOTP) |  |
| `tpl_demat_totp` | One-time code (TOTP) | ഒറ്റത്തവണ കോഡ് (TOTP) |  |
| `tpl_login_totp` | One-time code (TOTP) | ഒറ്റത്തവണ കോഡ് (TOTP) |  |
| `tpl_travel_booking_totp` | One-time code (TOTP) | 2FA സീക്രെട്ട് |  |
| `tr_cannot_undo` | This cannot be undone. | ഇത് തിരികെ കൊണ്ടുവരാനാവില്ല. |  |
| `tr_delete_all` | Delete all permanently | എല്ലാം എന്നെന്നേക്കുമായി നീക്കുക |  |
| `tr_delete_all_many` | %1$d records will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d രേഖകൾ എന്നെന്നേക്കുമായി പോകും. ഇത് തിരികെ കൊണ്ടുവരാനാവില്ല, പുനഃസ്ഥാപിക്കാൻ ബാക്കപ്പുമില്ല. |  |
| `tr_delete_all_one` | %1$d record will be gone permanently. This cannot be undone and there is no backup to restore from. | %1$d രേഖ എന്നെന്നേക്കുമായി പോകും. ഇത് തിരികെ കൊണ്ടുവരാനാവില്ല, പുനഃസ്ഥാപിക്കാൻ ബാക്കപ്പുമില്ല. |  |
| `tr_delete_all_title` | Delete everything in the trash? | ചവറ്റുകുട്ടയിലുള്ളതെല്ലാം നീക്കണോ? |  |
| `tr_delete_now` | Delete now | ഇപ്പോൾ നീക്കുക |  |
| `tr_delete_one_title` | Delete “%1$s” permanently? | “%1$s” എന്നെന്നേക്കുമായി നീക്കണോ? |  |
| `tr_empty` | Nothing deleted. | ഒന്നും നീക്കിയിട്ടില്ല. |  |
| `tr_ttl_note` | Deleted records wait here for %1$d days. | നീക്കിയ രേഖകൾ ഇവിടെ %1$d ദിവസം ഇരിക്കും. |  |
| `wl_body` | A local-first vault built for Indian banks, UPI, cards, Demat, EPF and the OTP apps you actually use. | ഇന്ത്യൻ ബാങ്കുകൾ, UPI, കാർഡുകൾ, ഡീമാറ്റ്, EPF, നിങ്ങൾ ശരിക്കും ഉപയോഗിക്കുന്ന OTP ആപ്പുകൾ — ഇവയ്ക്കെല്ലാം വേണ്ടി ഫോണിൽ തന്നെ ഇരിക്കുന്ന കലവറ. |  |

## Priority 2 — longer prose

| key | English | Malayalam | ok? |
|---|---|---|---|
| `hm_empty_body` | Start with the one credential you use the most. Even one saved password is safer than twelve in a notes app. | ഏറ്റവും കൂടുതൽ ഉപകാരപ്പെടുന്ന ഒറ്റ വിവരത്തിൽ നിന്ന് തുടങ്ങുക. നോട്ട്സ് ആപ്പിൽ കിടക്കുന്ന പന്ത്രണ്ട് പാസ്‌വേഡിനെക്കാൾ സൂക്ഷിച്ച ഒരു പാസ്‌വേഡ് സുരക്ഷിതമാണ്. |  |
| `hm_kit_banner_body` | If you forget your passphrase, a recovery kit is the only way back in. Nobody can make you another one. | പാസ്‌ഫ്രെയ്സ് മറന്നാൽ അകത്ത് വരാൻ റിക്കവറി കിറ്റ് മാത്രമാണ് വഴി. നിങ്ങൾക്കായി മറ്റൊന്ന് ആർക്കും ഉണ്ടാക്കാനാവില്ല. |  |
| `ic_unrecognised` | Nothing recognisable in that file. Exports from Chrome, Google Password Manager, Bitwarden, LastPass and KeePass are understood. | ആ ഫയലിൽ തിരിച്ചറിയാവുന്നതൊന്നും കിട്ടിയില്ല. Chrome, Google Password Manager, Bitwarden, LastPass, KeePass കയറ്റുമതികൾ മനസ്സിലാകും. |  |
| `ic_update_many` | %1$d existing records will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d നിലവിലുള്ള രേഖകൾ മാറും — സൈറ്റും യൂസർനെയിമും ഒത്തുനോക്കി. മാറിയ പാസ്‌വേഡുകൾ ഓരോ രേഖയുടെയും ചരിത്രത്തിൽ തിരികെ കിട്ടും. |  |
| `ic_update_one` | %1$d existing record will be updated — matched on site and username. The replaced passwords stay recoverable in each record\'s history. | %1$d നിലവിലുള്ള രേഖ മാറും — സൈറ്റും യൂസർനെയിമും ഒത്തുനോക്കി. മാറിയ പാസ്‌വേഡുകൾ ഓരോ രേഖയുടെയും ചരിത്രത്തിൽ തിരികെ കിട്ടും. |  |
| `import_conflicts` | %1$d record(s) had edits on both sides. Both versions were kept — look for “(conflict copy)”. | %1$d രേഖകൾ രണ്ട് ഭാഗത്തും മാറിയിരുന്നു. രണ്ട് രൂപവും സൂക്ഷിച്ചു — “(conflict copy)” തിരയുക. |  |
| `import_passphrase_hint` | Enter the passphrase that unlocks this backup file. It may differ from the one you use now. | ഈ ബാക്കപ്പ് ഫയൽ തുറക്കുന്ന പാസ്‌ഫ്രെയ്സ് നൽകുക. അത് നിങ്ങളുടെ ഇപ്പോഴത്തേതിൽ നിന്ന് വ്യത്യസ്തമാകാം. |  |
| `nav_integrity_body` | The .kosh file\'s Poly1305 authentication tag doesn\'t match. This can happen after an interrupted sync or a bad flash. | .kosh ഫയലിന്റെ Poly1305 സാക്ഷ്യപത്ര ടാഗ് ചേരുന്നില്ല. പാതിവഴിയിൽ നിന്ന സിങ്കിനോ മോശം സ്റ്റോറേജിനോ ശേഷം ഇത് സംഭവിക്കാം. |  |
| `nav_what_next_body` | Zerokosh keeps a rolling backup beside the vault file. Restore it from your sync folder, or reopen the vault with your Recovery Kit on another device. | Zerokosh കലവറ ഫയലിന് അരികിൽ ഒരു ഓടുന്ന ബാക്കപ്പ് സൂക്ഷിക്കുന്നു. അത് നിങ്ങളുടെ സിങ്ക് ഫോൾഡറിൽ നിന്ന് പുനഃസ്ഥാപിക്കുക, അല്ലെങ്കിൽ മറ്റൊരു ഫോണിൽ റിക്കവറി കിറ്റുപയോഗിച്ച് കലവറ തുറക്കുക. |  |
| `nfc_hint` | Rest the card flat against the back of the phone until it reads. This picks up the card number, expiry and name — the CVV is not on the chip, so you will still type that yourself. | വായിക്കുന്നതുവരെ കാർഡ് ഫോണിന്റെ പുറകിൽ നേരെ ചേർത്തുപിടിക്കുക. ഇതിൽ നിന്ന് കാർഡ് നമ്പർ, കാലാവധി, പേര് കിട്ടും — CVV ചിപ്പിൽ ഇല്ല, അത് നിങ്ങൾ തന്നെ ടൈപ്പ് ചെയ്യണം. |  |
| `ob_quick_subhead` | Pick a quick way back in. Your passphrase still guards the vault; this only unlocks the key on this device. | അകത്തേക്ക് തിരികെ വരാൻ വേഗമുള്ള വഴി തിരഞ്ഞെടുക്കുക. കലവറയുടെ കാവൽ പാസ്‌ഫ്രെയ്സ് തന്നെ; ഇത് ഈ ഫോണിൽ മാത്രം താക്കോൽ തുറക്കുന്നു. |  |
| `rem_kit_body` | Your vault has real data in it now. If you forget your passphrase without a recovery kit, nobody can let you back in — not even us. | ഇപ്പോൾ നിങ്ങളുടെ കലവറയിൽ യഥാർത്ഥ വിവരമുണ്ട്. റിക്കവറി കിറ്റ് ഇല്ലാതെ പാസ്‌ഫ്രെയ്സ് മറന്നാൽ ആർക്കും നിങ്ങളെ തിരികെ അകത്ത് കൊണ്ടുവരാനാവില്ല — ഞങ്ങൾക്കും. |  |
| `scr_about_body` | Zerokosh is free, open-source and has no servers. Only you can open your vault. Not even we can. | Zerokosh സൗജന്യവും ഓപ്പൺ സോഴ്‌സും ആണ്, സെർവറുകളേ ഇല്ല. നിങ്ങളുടെ കലവറ നിങ്ങൾക്ക് മാത്രമേ തുറക്കാൻ കഴിയൂ. ഞങ്ങൾക്കും കഴിയില്ല. |  |
| `scr_auth_camera_denied` | Camera permission is needed only to scan the QR code. You can also paste the secret by hand when adding a record. | QR കോഡ് സ്കാൻ ചെയ്യാൻ മാത്രമാണ് ക്യാമറ അനുമതി വേണ്ടത്. രേഖ ചേർക്കുമ്പോൾ സീക്രെട്ട് കൈകൊണ്ടും ഒട്ടിക്കാം. |  |
| `scr_login_helper_channel_desc` | Buttons that copy your login details one by one, for bank apps that block autofill | ഓട്ടോഫിൽ അനുവദിക്കാത്ത ബാങ്ക് ആപ്പുകൾക്ക് — ബട്ടൺ അമർത്തി ലോഗിൻ വിവരങ്ങൾ ഓരോന്നായി പകർത്തുക |  |
| `scr_quickunlock_body` | Open your vault the same way you unlock your phone. Your passphrase still works any time. | നിങ്ങൾ ഫോൺ തുറക്കുന്നതുപോലെ തന്നെ കലവറയും. പാസ്‌ഫ്രെയ്സ് എപ്പോഴും പ്രവർത്തിക്കും. |  |
| `scr_settings_allow_screenshots_warning` | Screenshots of your vault could end up in cloud photo backups. Keep this off unless you really need it. | കലവറയുടെ സ്ക്രീൻഷോട്ടുകൾ ക്ലൗഡ് ഫോട്ടോ ബാക്കപ്പിൽ എത്താം. ശരിക്കും ആവശ്യമെങ്കിൽ മാത്രം ഓണാക്കുക. |  |
| `se_body` | The vault file could not be written. If you have set a backup and sync folder, Android may have withdrawn permission to it — open Settings, pick the folder again, and retry. | കലവറ ഫയൽ എഴുതാനായില്ല. നിങ്ങൾ ബാക്കപ്പ്, സിങ്ക് ഫോൾഡർ സെറ്റ് ചെയ്തിട്ടുണ്ടെങ്കിൽ, Android അതിന്റെ അനുമതി പിൻവലിച്ചിരിക്കാം — ക്രമീകരണം തുറന്ന്, ഫോൾഡർ വീണ്ടും തിരഞ്ഞെടുത്ത്, വീണ്ടും ശ്രമിക്കുക. |  |
| `st_backup_sheet_body` | Your encrypted .kosh vault files are saved directly inside this folder. Sync this directory with Google Drive, Syncthing, Nextcloud, or an SD card for automated multi-device backup. | നിങ്ങളുടെ എൻക്രിപ്റ്റ് ചെയ്ത .kosh ഫയലുകൾ നേരിട്ട് ഈ ഫോൾഡറിൽ തന്നെ സൂക്ഷിക്കുന്നു. പല ഉപകരണങ്ങളിൽ താനേ ബാക്കപ്പ് ആകാൻ ഈ ഫോൾഡർ Google Drive, Syncthing, Nextcloud അല്ലെങ്കിൽ SD കാർഡുമായി സിങ്ക് ചെയ്യുക. |  |
| `st_new_recovery_body` | Confirm your passphrase to generate a new Recovery Key. The previous key will stop working. | പുതിയ റിക്കവറി താക്കോൽ ഉണ്ടാക്കാൻ നിങ്ങളുടെ പാസ്‌ഫ്രെയ്സ് നൽകുക. പഴയ താക്കോൽ പ്രവർത്തിക്കാതാകും. |  |
| `vh_no_kit_body` | Everything else on this page weakens one login. This one can cost you the whole vault. Settings → Get a new recovery key. | ഈ താളിലെ ബാക്കിയെല്ലാം ഒരു ലോഗിൻ ദുർബലമാക്കും. ഇത് കലവറ മുഴുവൻ കൊണ്ടുപോകാം. ക്രമീകരണം → പുതിയ റിക്കവറി താക്കോൽ എടുക്കുക. |  |

## Priority 3 — short labels

| key | English | Malayalam | ok? |
|---|---|---|---|
| `af_generate_title` | Use a strong password | ശക്തമായ പാസ്‌വേഡ് ഉപയോഗിക്കുക |  |
| `app_name` | Zerokosh | Zerokosh |  |
| `au_account_name` | Account Name (e.g. Google) | അക്കൗണ്ടിന്റെ പേര് (ഉദാ. Google) |  |
| `au_active_many` | %1$d active codes | %1$d സജീവ കോഡുകൾ |  |
| `au_active_one` | %1$d active code | %1$d സജീവ കോഡ് |  |
| `au_add_another` | Add another authenticator | മറ്റൊരു ഓതന്റിക്കേറ്റർ ചേർക്കുക |  |
| `au_add_secret` | Add Secret Key | സീക്രെട്ട് കീ ചേർക്കുക |  |
| `au_camera_needed` | Camera permission needed to scan QR code | QR കോഡ് സ്കാൻ ചെയ്യാൻ ക്യാമറ അനുമതി വേണം |  |
| `au_copied` | Copied · clears shortly | പകർത്തി · കുറച്ച് കഴിഞ്ഞ് മായും |  |
| `au_empty_hint` | Scan a QR from Google, GitHub or your broker, or type a secret key by hand. | Google, GitHub അല്ലെങ്കിൽ നിങ്ങളുടെ ബ്രോക്കറുടെ QR സ്കാൻ ചെയ്യുക, അല്ലെങ്കിൽ സീക്രെട്ട് കീ കൈകൊണ്ട് ടൈപ്പ് ചെയ്യുക. |  |
| `au_enter_key` | Enter Key | കീ ടൈപ്പ് ചെയ്യുക |  |
| `au_fallback_name` | Authenticator | ഓതന്റിക്കേറ്റർ |  |
| `au_flashlight` | Flashlight | ടോർച്ച് |  |
| `au_grant` | Grant Permission | അനുമതി നൽകുക |  |
| `au_image_failed` | Failed to process image | ചിത്രം വായിക്കാനായില്ല |  |
| `au_invalid_secret` | Invalid Base32 secret key (letters A-Z and digits 2-7 only) | തെറ്റായ Base32 സീക്രെട്ട് കീ (A-Z അക്ഷരവും 2-7 അക്കവും മാത്രം) |  |
| `au_no_match` | No codes match | ഒരു കോഡും കിട്ടിയില്ല |  |
| `au_none_yet` | No codes yet. | ഇതുവരെ കോഡുകളില്ല. |  |
| `au_pick_image` | Pick Image | ചിത്രം തിരഞ്ഞെടുക്കുക |  |
| `au_rotating` | "Rotating " | "മാറിക്കൊണ്ടിരിക്കുന്ന " |  |
| `au_rotating_emph` | codes. | കോഡുകൾ. |  |
| `au_save_key` | Save Key | കീ സൂക്ഷിക്കുക |  |
| `au_scan_qr` | Scan a QR code | QR കോഡ് സ്കാൻ ചെയ്യുക |  |
| `au_scan_title` | Scan Authenticator QR | ഓതന്റിക്കേറ്റർ QR സ്കാൻ ചെയ്യുക |  |
| `au_search_hint` | Search codes, issuers… | കോഡ്, നൽകിയവരെ തിരയുക… |  |
| `au_secret_example` | e.g. JBSWY3DPEHPK3PXP | ഉദാ. JBSWY3DPEHPK3PXP |  |
| `au_secret_label` | Secret Key (Base32) | സീക്രെട്ട് കീ (Base32) |  |
| `au_tap_to_copy` | Tap to copy | പകർത്താൻ ടാപ്പ് ചെയ്യുക |  |
| `cat_apps` | Apps &amp; Logins | ആപ്പുകളും ലോഗിനും |  |
| `cat_banks` | Banks &amp; UPI | ബാങ്കുകളും UPI-യും |  |
| `cat_cards` | Cards | കാർഡുകൾ |  |
| `cat_govid` | Gov &amp; ID | സർക്കാരും തിരിച്ചറിയലും |  |
| `cat_investments` | Investments | നിക്ഷേപം |  |
| `cat_utilities` | Utilities | ബില്ലുകളും കണക്ഷനുകളും |  |
| `cd_mask_hidden` | hidden | മറഞ്ഞിരിക്കുന്നു |  |
| `cd_shield_high_sensitivity` | extra-protected field | കൂടുതൽ സുരക്ഷിതമായ വിവരം |  |
| `gl_blank` | Blank template | ഒഴിഞ്ഞ ടെംപ്ലേറ്റ് |  |
| `gl_cat_apps` | Apps | ആപ്പുകൾ |  |
| `gl_cat_banks` | Banks | ബാങ്കുകൾ |  |
| `gl_cat_cards` | Cards | കാർഡുകൾ |  |
| `gl_cat_demat` | Demat | ഡീമാറ്റ് |  |
| `gl_cat_govid` | Gov ID | സർക്കാർ തിരിച്ചറിയൽ |  |
| `gl_cat_popular` | Popular | ജനപ്രിയം |  |
| `gl_cat_shopping` | Shopping | ഷോപ്പിംഗ് |  |
| `gl_cat_travel` | Travel | യാത്ര |  |
| `gl_cat_upi` | UPI | UPI |  |
| `gl_cat_utilities` | Utilities | ബില്ലുകൾ |  |
| `gl_head_emph` | storing? | സൂക്ഷിക്കുന്നത്? |  |
| `gl_head_lead` | "What are we " | "നമ്മൾ എന്താണ് " |  |
| `gl_matches` | %1$d matches | %1$d കിട്ടി |  |
| `gl_most_used` | Most-used first | ഏറ്റവും കൂടുതൽ ഉപയോഗിച്ചവ ആദ്യം |  |
| `gl_not_found` | Can’t find a service? | സേവനം കിട്ടുന്നില്ലേ? |  |
| `gl_search` | Search %1$d Indian services… | %1$d ഇന്ത്യൻ സേവനങ്ങളിൽ തിരയുക… |  |
| `gl_suggested` | Suggested for you | നിങ്ങൾക്കായുള്ള നിർദ്ദേശം |  |
| `hm_add_first` | Add your first record | നിങ്ങളുടെ ആദ്യ രേഖ ചേർക്കുക |  |
| `hm_all_offline` | all offline. | എല്ലാം ഓഫ്‌ലൈൻ. |  |
| `hm_count_many` | "%1$d credentials, " | "%1$d വിവരങ്ങൾ, " |  |
| `hm_count_one` | "%1$d credential, " | "%1$d വിവരം, " |  |
| `hm_detail_placeholder` | Pick a record to see it here | ഇവിടെ കാണാൻ ഒരു രേഖ തിരഞ്ഞെടുക്കുക |  |
| `hm_empty_blank` | A blank vault, ready. | ഒഴിഞ്ഞ കലവറ, തയ്യാർ. |  |
| `hm_empty_head_emph` | waiting. | കാത്തിരിക്കുന്നു. |  |
| `hm_empty_head_lead` | "Your vault is " | "നിങ്ങളുടെ കലവറ " |  |
| `hm_filter_all` | All | എല്ലാം |  |
| `hm_import_backup` | Import an encrypted backup | എൻക്രിപ്റ്റ് ചെയ്ത ബാക്കപ്പ് ഇറക്കുമതി |  |
| `hm_import_backup_note` | Open a .kosh file from this device | ഈ ഫോണിൽ നിന്ന് തന്നെ .kosh ഫയൽ തുറക്കുക |  |
| `hm_inst_many` | %1$d institutions | %1$d സ്ഥാപനങ്ങൾ |  |
| `hm_inst_one` | %1$d institution | %1$d സ്ഥാപനം |  |
| `hm_kit_banner_action` | Save one now | ഇപ്പോൾ സൂക്ഷിക്കുക |  |
| `hm_kit_banner_dismiss` | Remind me later | പിന്നീട് ഓർമ്മിപ്പിക്കുക |  |
| `hm_kit_banner_title` | No recovery kit saved | റിക്കവറി കിറ്റ് ഒന്നും സൂക്ഷിച്ചിട്ടില്ല |  |
| `hm_lock_immediate` | Unlocked · locks when you leave | തുറന്നിരിക്കുന്നു · വിട്ടാലുടൻ പൂട്ടും |  |
| `hm_lock_many` | Unlocked · locks %1$d min after you leave | തുറന്നിരിക്കുന്നു · വിട്ട് %1$d മിനിറ്റിൽ പൂട്ടും |  |
| `hm_lock_one` | Unlocked · locks 1 min after you leave | തുറന്നിരിക്കുന്നു · വിട്ട് 1 മിനിറ്റിൽ പൂട്ടും |  |
| `hm_no_match` | Nothing matches “%1$s” | “%1$s”-ന് ഒന്നും കിട്ടിയില്ല |  |
| `hm_no_match_hint` | Try an institution, a UPI handle, or the last four digits. | ഒരു സ്ഥാപനം, UPI ഹാൻഡിൽ, അല്ലെങ്കിൽ അവസാന നാല് അക്കം പരീക്ഷിക്കുക. |  |
| `hm_pinned` | Pinned | പിൻ ചെയ്തവ |  |
| `hm_search_hint` | Search HDFC, priya@upi, PAN… | HDFC, priya@upi, PAN… തിരയുക |  |
| `hm_start_template` | Start with a template | ടെംപ്ലേറ്റിൽ നിന്ന് തുടങ്ങുക |  |
| `ic_could_not` | Could not import | ഇറക്കുമതി ചെയ്യാനായില്ല |  |
| `ic_done` | Done | കഴിഞ്ഞു |  |
| `ic_import` | Import | ഇറക്കുമതി |  |
| `ic_imported` | Imported | ഇറക്കുമതി ചെയ്തു |  |
| `ic_importing` | Importing… | ഇറക്കുമതി ചെയ്യുന്നു… |  |
| `ic_new_many` | %1$d new logins. | %1$d പുതിയ ലോഗിനുകൾ. |  |
| `ic_new_one` | %1$d new login. | %1$d പുതിയ ലോഗിൻ. |  |
| `ic_result` | %1$d added, %2$d updated. | %1$d ചേർത്തു, %2$d മാറ്റി. |  |
| `ic_title` | Import from %1$s? | %1$s-ൽ നിന്ന് ഇറക്കുമതി ചെയ്യണോ? |  |
| `ic_too_large` | That file is too large to be a credential export. | ഈ ഫയൽ പാസ്‌വേഡ് കയറ്റുമതി ആകാൻ കഴിയാത്തത്ര വലുതാണ്. |  |
| `import_action` | Import | ഇറക്കുമതി |  |
| `import_locked` | Unlock your vault before importing. | ഇറക്കുമതി ചെയ്യുന്നതിന് മുമ്പ് നിങ്ങളുടെ കലവറ തുറക്കുക. |  |
| `import_merged` | %1$d added, %2$d updated. Nothing was replaced. | %1$d ചേർത്തു, %2$d മാറ്റി. ഒന്നും മായ്ച്ചിട്ടില്ല. |  |
| `import_not_a_vault` | That file could not be read as a Zerokosh vault. | ആ ഫയൽ Zerokosh കലവറയായി വായിക്കാനായില്ല. |  |
| `import_nothing_new` | Everything in that backup was already here. | ആ ബാക്കപ്പിലുള്ളതെല്ലാം ഇവിടെ നേരത്തെ ഉണ്ടായിരുന്നു. |  |
| `import_passphrase_label` | Backup passphrase | ബാക്കപ്പിന്റെ പാസ്‌ഫ്രെയ്സ് |  |
| `import_title` | Import a backup | ബാക്കപ്പ് ഇറക്കുമതി ചെയ്യുക |  |
| `kicker_locked` | Locked | പൂട്ടിയിരിക്കുന്നു |  |
| `lk_crypto_note` | Argon2id · XChaCha20-Poly1305 · nothing left this device | Argon2id · XChaCha20-Poly1305 · ഒന്നും ഈ ഫോണിന് പുറത്തുപോയിട്ടില്ല |  |
| `lk_touch_unlock` | Touch to unlock | തുറക്കാൻ തൊടുക |  |
| `lk_welcome_emph` | Your vault is sealed. | നിങ്ങളുടെ കലവറ പൂട്ടിയിരിക്കുന്നു. |  |
| `lk_welcome_lead` | Welcome back. | വീണ്ടും സ്വാഗതം. |  |
| `msg_auth_needed` | Confirm it\'s you to see this | കാണാൻ നിങ്ങൾ തന്നെയാണെന്ന് ഉറപ്പാക്കുക |  |
| `msg_back` | Back | പിന്നോട്ട് |  |
| `msg_cancel` | Cancel | റദ്ദാക്കുക |  |
| `msg_file_damaged` | File damaged — restored from backup | ഫയൽ കേടായിരുന്നു — ബാക്കപ്പിൽ നിന്ന് ശരിയാക്കി |  |
| `msg_ok` | OK | ശരി |  |
| `msg_saved` | Saved | സൂക്ഷിച്ചു |  |
| `nav_all_templates` | All templates | എല്ലാ ടെംപ്ലേറ്റും |  |
| `nav_damaged_emph` | vault file | കലവറ ഫയലിൽ |  |
| `nav_damaged_kicker` | Damaged state | കേടായ അവസ്ഥ |  |
| `nav_damaged_lead` | "Something in the " | "നിങ്ങളുടെ " |  |
| `nav_damaged_tail` | " is off." | " എന്തോ കുഴപ്പമുണ്ട്." |  |
| `nav_integrity_title` | Integrity check failed | സമഗ്രതാ പരിശോധന പരാജയപ്പെട്ടു |  |
| `nav_scan` | Scan | സ്കാൻ |  |
| `nav_tap_card` | Tap a card | കാർഡ് ടാപ്പ് ചെയ്യുക |  |
| `nav_what_next` | What to do next | ഇനി എന്ത് ചെയ്യണം |  |
| `nfc_disabled` | NFC is switched off. Turn it on in Settings, then try again. | NFC ഓഫാണ്. ക്രമീകരണത്തിൽ ഓണാക്കി വീണ്ടും ശ്രമിക്കുക. |  |
| `nfc_hold_card` | Hold your card to the phone | കാർഡ് ഫോണിൽ ചേർത്തുപിടിക്കുക |  |
| `nfc_missed` | Did not catch that | പിടികിട്ടിയില്ല |  |
| `nfc_read_failed` | That card could not be read. Try again. | ആ കാർഡ് വായിക്കാനായില്ല. വീണ്ടും ശ്രമിക്കുക. |  |
| `nfc_reading` | Reading… | വായിക്കുന്നു… |  |
| `nfc_try_again` | Try again | വീണ്ടും ശ്രമിക്കുക |  |
| `ob_argon_detail` | %1$d MB · t=%2$d · measured on this device | %1$d MB · t=%2$d · ഈ ഫോണിൽ തന്നെ അളന്നത് |  |
| `ob_argon_faster` | Faster unlock | വേഗം തുറക്കും |  |
| `ob_argon_harder` | Harder to attack | പൊളിക്കാൻ പ്രയാസം |  |
| `ob_argon_measuring` | Measuring this device… | ഈ ഫോൺ അളക്കുന്നു… |  |
| `ob_argon_title` | Argon2id hardness | Argon2id കാഠിന്യം |  |
| `ob_check_pass_dictionary` | Not a single dictionary word | നിഘണ്ടുവിലെ ഒരു വാക്കുപോലുമല്ല |  |
| `ob_check_pass_length` | 10 characters or more | 10 അല്ലെങ്കിൽ അതിലധികം അക്ഷരം |  |
| `ob_check_pass_reuse` | Not reused from another app | മറ്റൊരു ആപ്പിൽ നിന്ന് വീണ്ടും ഉപയോഗിച്ചതല്ല |  |
| `ob_check_pin_birthday` | Not a birthday or anniversary | ജന്മദിനവുമല്ല, വാർഷികവുമല്ല |  |
| `ob_check_pin_digits` | All six digits entered | ആറ് അക്കവും നിറച്ചു |  |
| `ob_check_pin_pattern` | Not a run or a repeated pattern | തുടർച്ചയായ അക്കവുമല്ല, ആവർത്തനവുമല്ല |  |
| `ob_crack_centuries` | ~%1$d centuries to crack | ~പൊളിക്കാൻ %1$d നൂറ്റാണ്ട് |  |
| `ob_crack_days` | ~%1$d days to crack | ~പൊളിക്കാൻ %1$d ദിവസം |  |
| `ob_crack_forever` | longer than the sun | സൂര്യനെക്കാൾ കൂടുതൽ കാലം |  |
| `ob_crack_hours` | ~hours to crack | ~പൊളിക്കാൻ ഏതാനും മണിക്കൂർ |  |
| `ob_crack_seconds` | ~seconds to crack | ~പൊളിക്കാൻ ഏതാനും സെക്കൻഡ് |  |
| `ob_crack_years` | ~%1$d years to crack | ~പൊളിക്കാൻ %1$d വർഷം |  |
| `ob_fact_encryption_note` | Authenticated, per-vault nonce | സാക്ഷ്യപ്പെടുത്തിയത്, ഓരോ കലവറയ്ക്കും വേറെ നോൺസ് |  |
| `ob_fact_encryption_title` | Encryption | എൻക്രിപ്ഷൻ |  |
| `ob_fact_encryption_value` | XChaCha20-Poly1305 | XChaCha20-Poly1305 |  |
| `ob_fact_kdf_note` | Measured on your phone at setup | സെറ്റപ്പ് സമയത്ത് നിങ്ങളുടെ ഫോണിൽ അളന്നത് |  |
| `ob_fact_kdf_title` | Key stretching | കീ സ്ട്രെച്ചിംഗ് |  |
| `ob_fact_kdf_value` | Argon2id · 64 MB · t=3 | Argon2id · 64 MB · t=3 |  |
| `ob_fact_lost_note` | No reset link. No support backdoor. | റീസെറ്റ് ലിങ്കില്ല. സപ്പോർട്ടിന്റെ പിൻവാതിലുമില്ല. |  |
| `ob_fact_lost_value` | Nobody can recover it | ആർക്കും തിരികെ കൊണ്ടുവരാനാവില്ല |  |
| `ob_fact_network_title` | Network permission | നെറ്റ്‌വർക്ക് അനുമതി |  |
| `ob_fact_network_value` | Not requested | ചോദിച്ചിട്ടേയില്ല |  |
| `ob_fact_quick_title` | Quick unlock | വേഗ അൺലോക്ക് |  |
| `ob_fact_quick_value` | Hardware keystore | ഹാർഡ്‌വെയർ കീസ്റ്റോർ |  |
| `ob_lang_continue` | Continue in %1$s | %1$s-ൽ തുടരുക |  |
| `ob_lang_head_emph` | language. | ഭാഷ തിരഞ്ഞെടുക്കുക. |  |
| `ob_lang_head_lead` | "Choose your " | "നിങ്ങളുടെ " |  |
| `ob_lang_search` | Search %1$d languages | %1$d ഭാഷകളിൽ തിരയുക |  |
| `ob_quick_continue_pass` | Continue with passphrase | പാസ്‌ഫ്രെയ്സുമായി തുടരുക |  |
| `ob_quick_enable` | Enable quick unlock | വേഗ അൺലോക്ക് ഓണാക്കുക |  |
| `ob_quick_fingerprint` | Fingerprint | വിരലടയാളം |  |
| `ob_quick_fingerprint_body` | Fast, hardware-backed unlock. | വേഗമുള്ള, ഹാർഡ്‌വെയർ സുരക്ഷിത അൺലോക്ക്. |  |
| `ob_quick_head_emph` | Without the cloud. | ക്ലൗഡില്ലാതെ. |  |
| `ob_quick_head_lead` | "Unlock in a touch. " | "ഒരു തൊടലിൽ തുറക്കും. " |  |
| `ob_quick_hw_body` | Android Keystore / StrongBox. No biometric data ever reaches Zerokosh. | Android Keystore / StrongBox. ഒരു ബയോമെട്രിക് വിവരവും Zerokosh വരെ ഒരിക്കലും എത്തുന്നില്ല. |  |
| `ob_quick_hw_title` | Hardware-backed | ഹാർഡ്‌വെയർ സുരക്ഷിതം |  |
| `ob_quick_no_sensor` | No hardware-backed sensor on this device. | ഈ ഫോണിൽ ഹാർഡ്‌വെയർ സെൻസർ ഇല്ല. |  |
| `ob_quick_opening` | Opening your vault… | നിങ്ങളുടെ കലവറ തുറക്കുന്നു… |  |
| `ob_quick_pass_only` | Passphrase only | പാസ്‌ഫ്രെയ്സ് മാത്രം |  |
| `ob_quick_pass_only_body` | Type it every time. Most secure. | ഓരോ തവണയും ടൈപ്പ് ചെയ്യുക. ഏറ്റവും സുരക്ഷിതം. |  |
| `ob_quick_refused` | Quick unlock was not set up. Try again, or carry on with just your passphrase. | വേഗ അൺലോക്ക് സെറ്റ് ആയില്ല. വീണ്ടും ശ്രമിക്കുക, അല്ലെങ്കിൽ പാസ്‌ഫ്രെയ്സുമായി തന്നെ തുടരുക. |  |
| `ob_quick_skip` | Skip for now — I\'ll type my passphrase | ഇപ്പോൾ വേണ്ട — ഞാൻ പാസ്‌ഫ്രെയ്സ് ടൈപ്പ് ചെയ്യാം |  |
| `ob_quick_touch_title` | Touch the sensor | സെൻസറിൽ തൊടുക |  |
| `ob_recommended` | Recommended | ശുപാർശ |  |
| `ob_reveal_hide` | Hide | മറയ്ക്കുക |  |
| `ob_reveal_show` | Show | കാണിക്കുക |  |
| `ob_seal_body` | Wrapping key lives in the hardware Keystore. Biometrics come next. | പൊതിയുന്ന താക്കോൽ ഹാർഡ്‌വെയർ കീസ്റ്റോറിൽ ഇരിക്കും. ബയോമെട്രിക് അടുത്ത ഘട്ടത്തിൽ. |  |
| `ob_seal_title` | Seal to this device | ഈ ഫോണുമായി തന്നെ ബന്ധിപ്പിക്കുക |  |
| `ob_seal_unavailable` | This device has no hardware-backed biometrics. | ഈ ഫോണിൽ ഹാർഡ്‌വെയർ ബയോമെട്രിക് ഇല്ല. |  |
| `ob_soon` | SOON | ഉടൻ |  |
| `ob_step_label` | Step %1$d of 6 | ഘട്ടം %1$d / 6 |  |
| `ob_strength_pass_empty` | Pick something only you would say | നിങ്ങൾ മാത്രം പറയുന്ന എന്തെങ്കിലും തിരഞ്ഞെടുക്കുക |  |
| `ob_strength_pass_good` | Good · %1$d bits of entropy | നല്ലത് · %1$d ബിറ്റ് എൻട്രോപ്പി |  |
| `ob_strength_pass_short` | Too short · needs 10 characters | വളരെ ചെറുത് · 10 അക്ഷരം വേണം |  |
| `ob_strength_pass_strong` | Strong · %1$d bits of entropy | ബലമുള്ളത് · %1$d ബിറ്റ് എൻട്രോപ്പി |  |
| `ob_strength_pass_weak` | Weak · %1$d bits of entropy | ദുർബലം · %1$d ബിറ്റ് എൻട്രോപ്പി |  |
| `ob_strength_pin_empty` | Six digits nobody could guess from your life | നിങ്ങളുടെ ജീവിതം നോക്കി ആർക്കും ഊഹിക്കാനാവാത്ത ആറ് അക്കങ്ങൾ |  |
| `ob_strength_pin_fair` | Fair · %1$d bits — a PIN can only be so strong | കുഴപ്പമില്ല · %1$d ബിറ്റ് — പിന്നിന് ഇതിലധികം ബലം വരില്ല |  |
| `ob_strength_pin_short` | Too short · needs 6 digits | വളരെ ചെറുത് · 6 അക്കം വേണം |  |
| `ob_strength_pin_weak` | Weak · among the first PINs anyone tries | ദുർബലം · ഈ പിന്നുകളാണ് ആദ്യം ശ്രമിക്കുന്നത് |  |
| `ob_try_label` | TRY | ശ്രമിക്കൂ |  |
| `qa_aadhaar` | Aadhaar | ആധാർ |  |
| `qa_bank_account` | Bank account | ബാങ്ക് അക്കൗണ്ട് |  |
| `qa_pan` | PAN | PAN |  |
| `qa_upi_id` | UPI ID | UPI ID |  |
| `rd_copied` | Copied | പകർത്തി |  |
| `rd_forget` | Forget | മറക്കുക |  |
| `rd_forget_these` | Forget these | ഇവ മറക്കുക |  |
| `rd_forget_title` | Forget previous passwords? | പഴയ പാസ്‌വേഡുകൾ മറക്കണോ? |  |
| `rd_history_hide` | Hide | മറയ്ക്കുക |  |
| `rd_history_show` | Show %1$d | %1$d കാണിക്കുക |  |
| `rd_hold_to_reveal` | Hold to reveal | കാണാൻ അമർത്തിപ്പിടിക്കുക |  |
| `rd_last_edit` | last edit %1$s | അവസാന മാറ്റം %1$s |  |
| `rd_release_to_hide` | Release to hide | മറയ്ക്കാൻ വിടുക |  |
| `re_add_field` | + Add another field | + മറ്റൊരു ഫീൽഡ് ചേർക്കുക |  |
| `re_add_field_title` | Add a field | ഫീൽഡ് ചേർക്കുക |  |
| `re_field_name` | Field name | ഫീൽഡിന്റെ പേര് |  |
| `re_pick_date` | Pick a date | തീയതി തിരഞ്ഞെടുക്കുക |  |
| `re_remove` | Remove | നീക്കുക |  |
| `re_tap_card` | Read the card by tapping it | കാർഡ് ടാപ്പ് ചെയ്ത് വായിക്കുക |  |
| `re_treat_secret` | Treat as a secret (masked, hold to reveal) | രഹസ്യമായി കണക്കാക്കുക (മറഞ്ഞിരിക്കും, കാണാൻ അമർത്തിപ്പിടിക്കുക) |  |
| `re_using_template` | using the %1$s template | %1$s ടെംപ്ലേറ്റ് ഉപയോഗിച്ച് |  |
| `rem_kit_title` | No recovery kit saved | റിക്കവറി കിറ്റ് ഒന്നും സൂക്ഷിച്ചിട്ടില്ല |  |
| `scr_about_license` | License: GPL-3.0 — free forever | ലൈസൻസ്: GPL-3.0 — എന്നും സൗജന്യം |  |
| `scr_about_source` | Source code | സോഴ്‌സ് കോഡ് |  |
| `scr_about_version` | Version %1$s | പതിപ്പ് %1$s |  |
| `scr_auth_add_qr` | Add via QR | QR വഴി ചേർക്കുക |  |
| `scr_auth_empty` | Codes from your apps and brokers will appear here | നിങ്ങളുടെ ആപ്പുകളുടെയും ബ്രോക്കറുടെയും കോഡുകൾ ഇവിടെ കാണും |  |
| `scr_auth_scan_title` | Point the camera at the QR code | ക്യാമറ QR കോഡിന് മുകളിൽ പിടിക്കുക |  |
| `scr_detail_copied` | Copied · clears in 30s | പകർത്തി · 30 സെക്കൻഡിൽ മായും |  |
| `scr_detail_copy` | Copy | പകർത്തുക |  |
| `scr_detail_edit` | Edit | മാറ്റുക |  |
| `scr_detail_favorite` | Favourite | ഇഷ്ടപ്പെട്ടവ |  |
| `scr_detail_hidden` | Hidden | മറഞ്ഞിരിക്കുന്നു |  |
| `scr_detail_hide` | Hide | മറയ്ക്കുക |  |
| `scr_detail_history_empty` | Nothing replaced yet. | ഇതുവരെ ഒന്നും മാറിയിട്ടില്ല. |  |
| `scr_detail_history_title` | Previous passwords | പഴയ പാസ്‌വേഡുകൾ |  |
| `scr_detail_reveal` | Show | കാണിക്കുക |  |
| `scr_detail_shown` | Shown | കാണുന്നു |  |
| `scr_edit_cancel` | Cancel | റദ്ദാക്കുക |  |
| `scr_edit_generate` | Generate | ഉണ്ടാക്കുക |  |
| `scr_edit_institution_hint` | Bank / company (for grouping) | ബാങ്ക് / കമ്പനി (കൂട്ടമാക്കാൻ) |  |
| `scr_edit_invalid` | This doesn\'t look right — please check | ഇത് ശരിയാണെന്ന് തോന്നുന്നില്ല — ഒന്ന് നോക്കൂ |  |
| `scr_edit_link_none` | None | ഒന്നുമില്ല |  |
| `scr_edit_luhn_warning` | This card number doesn\'t pass the usual check — save anyway if it\'s correct | ഈ കാർഡ് നമ്പർ സാധാരണ പരിശോധനയിൽ വിജയിക്കുന്നില്ല — ശരിയാണെങ്കിൽ സൂക്ഷിച്ചോളൂ |  |
| `scr_edit_month` | Month | മാസം |  |
| `scr_edit_picker_other` | Other… | മറ്റുള്ളവ… |  |
| `scr_edit_picker_other_hint` | Type your own | നിങ്ങളുടേത് എഴുതുക |  |
| `scr_edit_required_title` | Give it a name first | ആദ്യം ഇതിന് ഒരു പേര് കൊടുക്കുക |  |
| `scr_edit_save` | Save | സൂക്ഷിക്കുക |  |
| `scr_edit_title_hint` | Title | പേര് |  |
| `scr_edit_title_new` | New | പുതിയത് |  |
| `scr_edit_year` | Year | വർഷം |  |
| `scr_gallery_quick_add` | Quick add | വേഗം ചേർക്കുക |  |
| `scr_gallery_title` | What do you want to save? | നിങ്ങൾക്ക് എന്ത് സൂക്ഷിക്കണം? |  |
| `scr_home_add` | Add | ചേർക്കുക |  |
| `scr_home_empty_sample_bank` | This is how your bank account will look | നിങ്ങളുടെ ബാങ്ക് അക്കൗണ്ട് ഇങ്ങനെ കാണും |  |
| `scr_home_empty_sample_card` | Your cards, UPI and app logins live here too | നിങ്ങളുടെ കാർഡുകൾ, UPI, ആപ്പ് ലോഗിനുകൾ എല്ലാം ഇവിടെത്തന്നെ |  |
| `scr_home_group_other` | Other | മറ്റുള്ളവ |  |
| `scr_home_no_results` | Nothing matches your search | നിങ്ങളുടെ തിരച്ചിലിൽ ഒന്നും കിട്ടിയില്ല |  |
| `scr_home_search_hint` | Search your vault | നിങ്ങളുടെ കലവറയിൽ തിരയുക |  |
| `scr_home_tab_authenticator` | Authenticator | കോഡുകൾ |  |
| `scr_home_tab_home` | Home | ഹോം |  |
| `scr_home_tab_settings` | Settings | ക്രമീകരണം |  |
| `scr_home_title` | Home | ഹോം |  |
| `scr_language_continue` | Continue | തുടരുക |  |
| `scr_language_subtitle` | अपनी भाषा चुनें | Choose your language |  |
| `scr_language_title` | Choose your language | നിങ്ങളുടെ ഭാഷ തിരഞ്ഞെടുക്കുക |  |
| `scr_login_helper_body` | Tap a button to copy it · clears in 30s | പകർത്താൻ ബട്ടൺ അമർത്തുക · 30 സെക്കൻഡിൽ മായും |  |
| `scr_login_helper_channel` | Login helper | ലോഗിൻ സഹായി |  |
| `scr_login_helper_title` | Logging in to %1$s | %1$s-ൽ ലോഗിൻ ചെയ്യുന്നു |  |
| `scr_quickunlock_backup_footer` | Set up backup folder now | ഇപ്പോൾ തന്നെ ബാക്കപ്പ് ഫോൾഡർ സെറ്റ് ചെയ്യുക |  |
| `scr_quickunlock_enable` | Turn on | ഓണാക്കുക |  |
| `scr_quickunlock_skip` | Not now | ഇപ്പോൾ വേണ്ട |  |
| `scr_quickunlock_title` | Unlock with your fingerprint or face | വിരലടയാളമോ മുഖമോ കൊണ്ട് തുറക്കുക |  |
| `scr_reminder_body` | %1$s is due soon · open Zerokosh | %1$s-ന്റെ തീയതി അടുത്തു · Zerokosh തുറക്കുക |  |
| `scr_reminder_channel` | Renewal reminders | പുതുക്കൽ ഓർമ്മപ്പെടുത്തൽ |  |
| `scr_reminder_title` | Zerokosh reminder | Zerokosh ഓർമ്മിപ്പിക്കുന്നു |  |
| `scr_settings_about` | About | വിവരം |  |
| `scr_settings_allow_screenshots` | Allow screenshots | സ്ക്രീൻഷോട്ട് എടുക്കാൻ അനുവദിക്കുക |  |
| `scr_settings_autofill` | Autofill service | ഓട്ടോഫിൽ സേവനം |  |
| `scr_settings_autofill_off` | Not set up | സെറ്റ് ചെയ്തിട്ടില്ല |  |
| `scr_settings_autofill_on` | Zerokosh | Zerokosh |  |
| `scr_settings_autofill_unsupported` | Not available | ലഭ്യമല്ല |  |
| `scr_settings_autolock` | Lock when I leave the app | ആപ്പ് വിട്ടാലുടൻ പൂട്ടുക |  |
| `scr_settings_autolock_1min` | After 1 minute | 1 മിനിറ്റിന് ശേഷം |  |
| `scr_settings_autolock_immediately` | Immediately | ഉടനെ |  |
| `scr_settings_autolock_mins` | After %1$d minutes | %1$d മിനിറ്റിന് ശേഷം |  |
| `scr_settings_change_passphrase` | Change passphrase | പാസ്‌ഫ്രെയ്സ് മാറ്റുക |  |
| `scr_settings_current_passphrase` | Current passphrase | ഇപ്പോഴത്തെ പാസ്‌ഫ്രെയ്സ് |  |
| `scr_settings_export` | Export | കയറ്റുമതി ചെയ്യുക |  |
| `scr_settings_import` | Import passwords | പാസ്‌വേഡുകൾ ഇറക്കുമതി ചെയ്യുക |  |
| `scr_settings_language` | Language | ഭാഷ |  |
| `scr_settings_new_passphrase` | New passphrase (at least 10 characters) | പുതിയ പാസ്‌ഫ്രെയ്സ് (കുറഞ്ഞത് 10 അക്ഷരം) |  |
| `scr_settings_new_recovery` | Get a new Recovery Key | പുതിയ റിക്കവറി താക്കോൽ എടുക്കുക |  |
| `scr_settings_passphrase_changed` | Passphrase changed | പാസ്‌ഫ്രെയ്സ് മാറി |  |
| `scr_settings_quick_unlock` | Fingerprint / face unlock | വിരലടയാളം / മുഖം അൺലോക്ക് |  |
| `scr_settings_security_info` | How your data is protected | നിങ്ങളുടെ ഡാറ്റ എങ്ങനെ സുരക്ഷിതം |  |
| `scr_settings_sync_folder` | Backup &amp; sync folder | ബാക്കപ്പ്, സിങ്ക് ഫോൾഡർ |  |
| `scr_settings_sync_not_set` | Not backed up | ബാക്കപ്പില്ല |  |
| `scr_settings_title` | Settings | ക്രമീകരണം |  |
| `se_title` | Not saved | സൂക്ഷിച്ചില്ല |  |
| `st_active_folder` | Active Folder | സജീവ ഫോൾഡർ |  |
| `st_active_value` | Active · %1$s | സജീവം · %1$s |  |
| `st_backing_up` | Backing up vault… | കലവറ ബാക്കപ്പ് ചെയ്യുന്നു… |  |
| `st_backup_now` | Backup Now | ഇപ്പോൾ ബാക്കപ്പ് എടുക്കുക |  |
| `st_backup_sheet_title` | Backup &amp; Sync Folder | ബാക്കപ്പ്, സിങ്ക് ഫോൾഡർ |  |
| `st_change_folder` | Change Folder | ഫോൾഡർ മാറ്റുക |  |
| `st_confirm_new_passphrase` | Confirm new passphrase | പുതിയ പാസ്‌ഫ്രെയ്സ് വീണ്ടും ടൈപ്പ് ചെയ്യുക |  |
| `st_connected_folder` | Connected folder: %1$s | ചേർത്ത ഫോൾഡർ: %1$s |  |
| `st_disconnect` | Disconnect | നീക്കുക |  |
| `st_done` | Done | കഴിഞ്ഞു |  |
| `st_export_kosh` | Export encrypted .kosh | എൻക്രിപ്റ്റ് ചെയ്ത .kosh കയറ്റുമതി |  |
| `st_folder_fallback` | Folder | ഫോൾഡർ |  |
| `st_forgot_passphrase` | Forgotten your passphrase? | പാസ്‌ഫ്രെയ്സ് മറന്നോ? |  |
| `st_forgot_passphrase_detail` | Set a new one using your fingerprint | നിങ്ങളുടെ വിരലടയാളം കൊണ്ട് പുതിയത് വെക്കുക |  |
| `st_generate` | Generate | ഉണ്ടാക്കുക |  |
| `st_group_about` | About | വിവരം |  |
| `st_group_appearance` | Appearance | രൂപം |  |
| `st_group_security` | Security | സുരക്ഷ |  |
| `st_group_sync` | Sync | സിങ്ക് |  |
| `st_import_kosh` | Import a .kosh backup | .kosh ബാക്കപ്പ് ഇറക്കുമതി |  |
| `st_import_other` | Import from another password manager | മറ്റൊരു പാസ്‌വേഡ് മാനേജറിൽ നിന്ന് ഇറക്കുമതി |  |
| `st_import_other_detail` | Chrome · Google · Bitwarden · LastPass · KeePass | Chrome · Google · Bitwarden · LastPass · KeePass |  |
| `st_license` | License | ലൈസൻസ് |  |
| `st_logos_by` | Logos provided by | ലോഗോ നൽകിയത് |  |
| `st_new_recovery_note` | Store this offline. The old Recovery Key is no longer valid. | അത് ഓഫ്‌ലൈനിൽ വെക്കുക. പഴയ റിക്കവറി താക്കോൽ ഇനി സാധുവല്ല. |  |
| `st_new_recovery_result` | Your new Recovery Key: | നിങ്ങളുടെ പുതിയ റിക്കവറി താക്കോൽ: |  |
| `st_subtitle` | Your rules. | നിങ്ങളുടെ നിയമങ്ങൾ. |  |
| `st_theme` | Theme | തീം |  |
| `st_theme_dark` | Dark | കടും |  |
| `st_theme_light` | Light | ഇളം |  |
| `st_theme_system` | System | സിസ്റ്റം |  |
| `st_toast_backed_up` | Vault backed up to %1$s! | കലവറ %1$s-ൽ സൂക്ഷിച്ചു! |  |
| `st_toast_backup_failed` | Backup failed — check folder permissions | ബാക്കപ്പ് നടന്നില്ല — ഫോൾഡറിന്റെ അനുമതി നോക്കുക |  |
| `st_toast_disconnected` | Backup folder disconnected | ബാക്കപ്പ് ഫോൾഡർ നീക്കി |  |
| `st_toast_export_failed` | Export failed | കയറ്റുമതി നടന്നില്ല |  |
| `st_toast_exported` | Encrypted vault exported | എൻക്രിപ്റ്റ് ചെയ്ത കലവറ കയറ്റുമതി ചെയ്തു |  |
| `st_toast_folder_backed` | Backup folder connected &amp; vault backed up to %1$s! | ബാക്കപ്പ് ഫോൾഡർ ചേർന്നു, കലവറ %1$s-ൽ സൂക്ഷിച്ചു! |  |
| `st_toast_folder_connected` | Backup folder connected: %1$s | ബാക്കപ്പ് ഫോൾഡർ ചേർന്നു: %1$s |  |
| `st_toast_folder_failed` | Failed to bind folder: %1$s | ഫോൾഡർ ചേർക്കാനായില്ല: %1$s |  |
| `st_vault_review` | Vault review | കലവറ പരിശോധന |  |
| `st_vault_review_detail` | Reused, weak, expiring | വീണ്ടും ഉപയോഗിച്ചവ, ദുർബലം, തീരാറായവ |  |
| `tab_codes` | Codes | കോഡുകൾ |  |
| `tab_settings` | Settings | ക്രമീകരണം |  |
| `tab_templates` | Templates | ടെംപ്ലേറ്റ് |  |
| `tab_vault` | Vault | കലവറ |  |
| `time_days` | %1$dd ago | %1$d ദി മുമ്പ് |  |
| `time_hours` | %1$dh ago | %1$d മ മുമ്പ് |  |
| `time_just_now` | just now | ഇപ്പോൾ തന്നെ |  |
| `time_minutes` | %1$dm ago | %1$d മി മുമ്പ് |  |
| `time_months` | %1$dmo ago | %1$d മാസം മുമ്പ് |  |
| `time_years` | %1$dy ago | %1$d വർഷം മുമ്പ് |  |
| `tpl_aadhaar_card` | Aadhaar Card | ആധാർ |  |
| `tpl_aadhaar_card_aadhaar_number` | Aadhaar number | ആധാർ നമ്പർ |  |
| `tpl_aadhaar_card_address` | Address | ആധാറിലെ വിലാസം |  |
| `tpl_aadhaar_card_dob` | Dob | ജനന തീയതി |  |
| `tpl_aadhaar_card_file_copy` | Scanned copy | സ്കാൻ ചെയ്ത പകർപ്പ് |  |
| `tpl_aadhaar_card_linked_mobile` | Linked mobile | ബന്ധിപ്പിച്ച മൊബൈൽ |  |
| `tpl_aadhaar_card_myaadhaar_passcode` | Myaadhaar passcode | myAadhaar പാസ്‌കോഡ് |  |
| `tpl_aadhaar_card_name_on_aadhaar` | Name on aadhaar | ആധാറിലെ പേര് |  |
| `tpl_aadhaar_card_notes` | Notes | കുറിപ്പ് |  |
| `tpl_app_profile` | App Profile | ആപ്പ് പ്രൊഫൈൽ |  |
| `tpl_app_profile_app_name` | App name | ആപ്പിന്റെ പേര് |  |
| `tpl_app_profile_gift_cards` | Gift cards | ഗിഫ്റ്റ് കാർഡ് |  |
| `tpl_app_profile_membership` | Membership | അംഗത്വം |  |
| `tpl_app_profile_membership_renewal` | Membership renewal | അംഗത്വ പുതുക്കൽ |  |
| `tpl_app_profile_notes` | Notes | കുറിപ്പ് |  |
| `tpl_app_profile_password_if_any` | Password (if any) | പാസ്‌വേഡ് (ഉണ്ടെങ്കിൽ) |  |
| `tpl_app_profile_registered_email` | Registered email | രജിസ്റ്റർ ചെയ്ത ഇമെയിൽ |  |
| `tpl_app_profile_registered_mobile` | Registered mobile | രജിസ്റ്റർ ചെയ്ത മൊബൈൽ |  |
| `tpl_app_profile_wallet_pin` | Wallet pin | വാലറ്റ് പിൻ |  |
| `tpl_bank_account` | Bank Account | ബാങ്ക് അക്കൗണ്ട് |  |
| `tpl_bank_account_account_number` | Account number | അക്കൗണ്ട് നമ്പർ |  |
| `tpl_bank_account_account_type` | Account type | അക്കൗണ്ട് തരം |  |
| `tpl_bank_account_bank_name` | Bank name | ബാങ്കിന്റെ പേര് |  |
| `tpl_bank_account_branch` | Branch | ശാഖ |  |
| `tpl_bank_account_customer_id` | Customer id | ഉപഭോക്തൃ ID |  |
| `tpl_bank_account_ifsc` | IFSC code | IFSC കോഡ് |  |
| `tpl_bank_account_login_password` | Login password | ലോഗിൻ പാസ്‌വേഡ് |  |
| `tpl_bank_account_micr` | MICR code | MICR കോഡ് |  |
| `tpl_bank_account_netbanking_user_id` | Net-banking user ID | നെറ്റ്-ബാങ്കിംഗ് യൂസർ ID |  |
| `tpl_bank_account_nominee` | Nominee | നോമിനി |  |
| `tpl_bank_account_notes` | Notes | കുറിപ്പ് |  |
| `tpl_bank_account_profile_password` | Profile password | പ്രൊഫൈൽ പാസ്‌വേഡ് |  |
| `tpl_bank_account_registered_email` | Registered email | രജിസ്റ്റർ ചെയ്ത ഇമെയിൽ |  |
| `tpl_bank_account_registered_mobile` | Registered mobile | രജിസ്റ്റർ ചെയ്ത മൊബൈൽ |  |
| `tpl_bank_account_tpin` | TPIN | TPIN |  |
| `tpl_bank_account_transaction_password` | Transaction password | ഇടപാട് പാസ്‌വേഡ് |  |
| `tpl_card` | Card | കാർഡ് |  |
| `tpl_card_atm_pin` | ATM PIN | ATM പിൻ |  |
| `tpl_card_billing_cycle_day` | Billing cycle day | ബില്ലിംഗ് സൈക്കിൾ ദിവസം |  |
| `tpl_card_card_network` | Card network | നെറ്റ്‌വർക്ക് |  |
| `tpl_card_card_number` | Card number | കാർഡ് നമ്പർ |  |
| `tpl_card_card_portal_login` | Card portal login | കാർഡ് പോർട്ടൽ ലോഗിൻ |  |
| `tpl_card_card_portal_password` | Card portal password | കാർഡ് പോർട്ടൽ പാസ്‌വേഡ് |  |
| `tpl_card_card_type` | Card type | കാർഡ് തരം |  |
| `tpl_card_card_variant` | Card variant | കാർഡ് വേരിയന്റ് |  |
| `tpl_card_cvv` | CVV | CVV |  |
| `tpl_card_expiry` | Expiry | കാലാവധി |  |
| `tpl_card_linked_account` | Linked account | ബന്ധിപ്പിച്ച അക്കൗണ്ട് |  |
| `tpl_card_name_on_card` | Name on card | കാർഡിലെ പേര് |  |
| `tpl_card_notes` | Notes | കുറിപ്പ് |  |
| `tpl_demat` | Demat | ഡീമാറ്റ് |  |
| `tpl_demat_api_key` | API key | API കീ |  |
| `tpl_demat_api_secret` | API secret | API സീക്രെട്ട് |  |
| `tpl_demat_bo_id` | BO ID | BO ID |  |
| `tpl_demat_broker` | Broker | ബ്രോക്കർ |  |
| `tpl_demat_cdsl_tpin` | CDSL TPIN | CDSL TPIN |  |
| `tpl_demat_client_id` | Client id | ക്ലയന്റ് ID |  |
| `tpl_demat_depository` | Depository | ഡിപ്പോസിറ്ററി |  |
| `tpl_demat_dp_id` | DP ID | DP ID |  |
| `tpl_demat_login_password` | Login password | ലോഗിൻ പാസ്‌വേഡ് |  |
| `tpl_demat_mf_folios` | Mutual fund folios | മ്യൂച്വൽ ഫണ്ട് ഫോളിയോ |  |
| `tpl_demat_mpin` | MPIN | MPIN |  |
| `tpl_demat_nominee` | Nominee | നോമിനി |  |
| `tpl_demat_notes` | Notes | കുറിപ്പ് |  |
| `tpl_digilocker` | Digilocker | DigiLocker |  |
| `tpl_digilocker_login_username` | Login username | യൂസർനെയിം |  |
| `tpl_digilocker_notes` | Notes | കുറിപ്പ് |  |
| `tpl_digilocker_portal_password` | Portal password | പാസ്‌വേഡ് |  |
| `tpl_digilocker_security_pin` | Security pin | സുരക്ഷാ പിൻ |  |
| `tpl_driving_license` | Driving License | ഡ്രൈവിംഗ് ലൈസൻസ് |  |
| `tpl_driving_license_dl_number` | Dl number | ലൈസൻസ് നമ്പർ |  |
| `tpl_driving_license_dob` | Dob | ജനന തീയതി |  |
| `tpl_driving_license_expiry_date` | Expiry date | ഈ തീയതി വരെ സാധു |  |
| `tpl_driving_license_file_copy` | Scanned copy | സ്കാൻ ചെയ്ത പകർപ്പ് |  |
| `tpl_driving_license_issue_date` | Issue date | നൽകിയ തീയതി |  |
| `tpl_driving_license_name_on_dl` | Name on dl | ലൈസൻസിലെ പേര് |  |
| `tpl_driving_license_notes` | Notes | കുറിപ്പ് |  |
| `tpl_driving_license_rto_location` | Rto location | RTO |  |
| `tpl_driving_license_vehicle_classes` | Vehicle classes | വാഹന വിഭാഗങ്ങൾ |  |
| `tpl_epf_pension` | Epf Pension | EPF / പെൻഷൻ |  |
| `tpl_epf_pension_ipin` | IPIN | IPIN |  |
| `tpl_epf_pension_linked_mobile` | Linked mobile | ബന്ധിപ്പിച്ച മൊബൈൽ |  |
| `tpl_epf_pension_name_on_epf` | Name on epf | EPF-ലെ പേര് |  |
| `tpl_epf_pension_nominee` | Nominee | നോമിനി |  |
| `tpl_epf_pension_notes` | Notes | കുറിപ്പ് |  |
| `tpl_epf_pension_password` | Password | പാസ്‌വേഡ് |  |
| `tpl_epf_pension_pf_member_id` | Pf member id | PF അംഗ ID |  |
| `tpl_epf_pension_portal_password` | Portal password | EPFO പാസ്‌വേഡ് |  |
| `tpl_epf_pension_scheme` | Scheme | പദ്ധതി |  |
| `tpl_epf_pension_tpin` | TPIN | TPIN |  |
| `tpl_epf_pension_uan_number` | Uan number | UAN |  |
| `tpl_epf_pension_uan_or_pran` | UAN / PRAN | UAN / PRAN |  |
| `tpl_gov_id` | Gov Id | സർക്കാർ തിരിച്ചറിയൽ രേഖ |  |
| `tpl_gov_id_expiry` | Expiry | കാലാവധി |  |
| `tpl_gov_id_file_copy` | Scanned copy | സ്കാൻ ചെയ്ത പകർപ്പ് |  |
| `tpl_gov_id_id_kind` | ID type | തിരിച്ചറിയൽ രേഖയുടെ തരം |  |
| `tpl_gov_id_id_number` | ID number | തിരിച്ചറിയൽ നമ്പർ |  |
| `tpl_gov_id_name_as_per_id` | Name as per ID | രേഖ പ്രകാരമുള്ള പേര് |  |
| `tpl_gov_id_notes` | Notes | കുറിപ്പ് |  |
| `tpl_gov_id_portal_login` | Portal login | പോർട്ടൽ ലോഗിൻ |  |
| `tpl_gov_id_portal_password` | Portal password | പോർട്ടൽ പാസ്‌വേഡ് |  |
| `tpl_insurance` | Insurance | ഇൻഷുറൻസ് |  |
| `tpl_insurance_agent_contact` | Agent contact | ഏജന്റിന്റെ ബന്ധപ്പെടൽ |  |
| `tpl_insurance_commencement_date` | Commencement date | തുടങ്ങിയ തീയതി |  |
| `tpl_insurance_insurer` | Insurer | ഇൻഷുറൻസ് കമ്പനി |  |
| `tpl_insurance_maturity_date` | Maturity date | കാലാവധി തീയതി |  |
| `tpl_insurance_nominee` | Nominee | നോമിനി |  |
| `tpl_insurance_notes` | Notes | കുറിപ്പ് |  |
| `tpl_insurance_policy_number` | Policy number | പോളിസി നമ്പർ |  |
| `tpl_insurance_policy_term` | Policy term | പോളിസി കാലാവധി |  |
| `tpl_insurance_policy_type` | Policy type | പോളിസി തരം |  |
| `tpl_insurance_portal_login` | Portal login | പോർട്ടൽ ലോഗിൻ |  |
| `tpl_insurance_portal_password` | Portal password | പോർട്ടൽ പാസ്‌വേഡ് |  |
| `tpl_insurance_premium_amount` | Premium amount | പ്രീമിയം തുക |  |
| `tpl_insurance_premium_due_date` | Premium due date | പ്രീമിയം തീയതി |  |
| `tpl_insurance_premium_mode` | Premium mode | പ്രീമിയം എങ്ങനെ അടയ്ക്കുന്നു |  |
| `tpl_insurance_sum_assured` | Sum assured | ഇൻഷുർ ചെയ്ത തുക |  |
| `tpl_login` | Login | ലോഗിൻ |  |
| `tpl_login_notes` | Notes | കുറിപ്പ് |  |
| `tpl_login_password` | Password | പാസ്‌വേഡ് |  |
| `tpl_login_recovery_codes` | Recovery codes | റിക്കവറി കോഡുകൾ |  |
| `tpl_login_username` | Username | യൂസർനെയിം |  |
| `tpl_login_website` | Website | വെബ്‌സൈറ്റ് |  |
| `tpl_pan_card` | Pan Card | PAN കാർഡ് |  |
| `tpl_pan_card_aadhaar_linked` | Aadhaar linked | ആധാറുമായി ബന്ധിപ്പിച്ചു |  |
| `tpl_pan_card_dob` | Dob | ജനന തീയതി |  |
| `tpl_pan_card_e_filing_password` | E filing password | ഇ-ഫയലിംഗ് പാസ്‌വേഡ് |  |
| `tpl_pan_card_fathers_name` | Fathers name | അച്ഛന്റെ പേര് |  |
| `tpl_pan_card_file_copy` | Scanned copy | സ്കാൻ ചെയ്ത പകർപ്പ് |  |
| `tpl_pan_card_name_on_pan` | Name on pan | PAN-ലെ പേര് |  |
| `tpl_pan_card_notes` | Notes | കുറിപ്പ് |  |
| `tpl_pan_card_pan_number` | Pan number | PAN |  |
| `tpl_passkey` | Passkey | പാസ്‌കീ |  |
| `tpl_passkey_credential_id` | Credential ID | ക്രെഡൻഷ്യൽ ID |  |
| `tpl_passkey_notes` | Notes | കുറിപ്പ് |  |
| `tpl_passkey_private_key` | Private key | സ്വകാര്യ കീ |  |
| `tpl_passkey_sign_count` | Sign count | സൈൻ കൗണ്ട് |  |
| `tpl_passkey_user_handle` | User handle | യൂസർ ഹാൻഡിൽ |  |
| `tpl_passkey_username` | Username | യൂസർനെയിം |  |
| `tpl_passkey_website` | Website | വെബ്‌സൈറ്റ് |  |
| `tpl_passport` | Passport | പാസ്‌പോർട്ട് |  |
| `tpl_passport_dob` | Dob | ജനന തീയതി |  |
| `tpl_passport_expiry_date` | Expiry date | കാലാവധി തീരുന്ന തീയതി |  |
| `tpl_passport_file_copy` | Scanned copy | സ്കാൻ ചെയ്ത പകർപ്പ് |  |
| `tpl_passport_given_names` | Given names | നൽകിയ പേര് |  |
| `tpl_passport_issue_date` | Issue date | നൽകിയ തീയതി |  |
| `tpl_passport_notes` | Notes | കുറിപ്പ് |  |
| `tpl_passport_passport_number` | Passport number | പാസ്‌പോർട്ട് നമ്പർ |  |
| `tpl_passport_place_of_issue` | Place of issue | നൽകിയ സ്ഥലം |  |
| `tpl_passport_portal_login` | Portal login | Passport Seva ലോഗിൻ |  |
| `tpl_passport_portal_password` | Portal password | Passport Seva പാസ്‌വേഡ് |  |
| `tpl_passport_surname` | Surname | കുടുംബപ്പേര് |  |
| `tpl_secure_note` | Secure Note | സുരക്ഷിത കുറിപ്പ് |  |
| `tpl_secure_note_attachment` | Attachment | അറ്റാച്ച്മെന്റ് |  |
| `tpl_secure_note_body` | Note | കുറിപ്പ് |  |
| `tpl_shopping` | Shopping | ഷോപ്പിംഗ് അക്കൗണ്ട് |  |
| `tpl_shopping_gift_card_code` | Gift card code | ഗിഫ്റ്റ് കാർഡ് കോഡ് |  |
| `tpl_shopping_gift_card_pin` | Gift card pin | ഗിഫ്റ്റ് കാർഡ് പിൻ |  |
| `tpl_shopping_membership_id` | Membership id | അംഗത്വ ID |  |
| `tpl_shopping_notes` | Notes | കുറിപ്പ് |  |
| `tpl_shopping_password` | Password | പാസ്‌വേഡ് |  |
| `tpl_shopping_registered_email` | Registered email | രജിസ്റ്റർ ചെയ്ത ഇമെയിൽ |  |
| `tpl_shopping_registered_mobile` | Registered mobile | രജിസ്റ്റർ ചെയ്ത മൊബൈൽ |  |
| `tpl_shopping_wallet_pin` | Wallet pin | വാലറ്റ് പിൻ |  |
| `tpl_telecom` | Telecom | മൊബൈലും ഇന്റർനെറ്റും |  |
| `tpl_telecom_account_number` | Account number | അക്കൗണ്ട് നമ്പർ |  |
| `tpl_telecom_circle` | Circle | സർക്കിൾ |  |
| `tpl_telecom_mobile_number` | Mobile number | മൊബൈൽ നമ്പർ |  |
| `tpl_telecom_notes` | Notes | കുറിപ്പ് |  |
| `tpl_telecom_operator` | Operator | കമ്പനി |  |
| `tpl_telecom_plan_type` | Plan type | പ്ലാൻ തരം |  |
| `tpl_telecom_portal_password` | Portal password | പോർട്ടൽ പാസ്‌വേഡ് |  |
| `tpl_telecom_puk` | PUK code | PUK കോഡ് |  |
| `tpl_telecom_renewal_date` | Renewal date | റീചാർജ് തീയതി |  |
| `tpl_telecom_sim_number` | Sim number | സിം നമ്പർ (ICCID) |  |
| `tpl_telecom_sim_pin` | SIM PIN | സിം പിൻ |  |
| `tpl_transit` | Transit | യാത്രാ പാസ് |  |
| `tpl_transit_login_password` | Login password | ലോഗിൻ പാസ്‌വേഡ് |  |
| `tpl_transit_notes` | Notes | കുറിപ്പ് |  |
| `tpl_transit_operator_name` | Operator name | കമ്പനി |  |
| `tpl_transit_registered_email` | Registered email | രജിസ്റ്റർ ചെയ്ത ഇമെയിൽ |  |
| `tpl_transit_registered_mobile` | Registered mobile | രജിസ്റ്റർ ചെയ്ത മൊബൈൽ |  |
| `tpl_transit_smart_card_number` | Smart card number | സ്മാർട്ട് കാർഡ് നമ്പർ |  |
| `tpl_transit_wallet_pin` | Wallet pin | വാലറ്റ് പിൻ |  |
| `tpl_travel_booking` | Travel Booking | യാത്രാ ബുക്കിംഗ് |  |
| `tpl_travel_booking_account_username` | Account username | യൂസർനെയിം |  |
| `tpl_travel_booking_login_password` | Login password | ലോഗിൻ പാസ്‌വേഡ് |  |
| `tpl_travel_booking_notes` | Notes | കുറിപ്പ് |  |
| `tpl_travel_booking_provider` | Provider | കമ്പനി |  |
| `tpl_travel_booking_registered_email` | Registered email | രജിസ്റ്റർ ചെയ്ത ഇമെയിൽ |  |
| `tpl_travel_booking_registered_mobile` | Registered mobile | രജിസ്റ്റർ ചെയ്ത മൊബൈൽ |  |
| `tpl_travel_booking_wallet_pin` | Wallet pin | വാലറ്റ് പിൻ |  |
| `tpl_upi` | UPI | UPI |  |
| `tpl_upi_app_name` | App name | UPI ആപ്പ് |  |
| `tpl_upi_apps_used` | Apps used | ഏത് ആപ്പിൽ സജീവം |  |
| `tpl_upi_linked_account` | Linked account | ബന്ധിപ്പിച്ച അക്കൗണ്ട് |  |
| `tpl_upi_notes` | Notes | കുറിപ്പ് |  |
| `tpl_upi_upi_id` | UPI ID | UPI ID |  |
| `tpl_upi_upi_pin` | UPI PIN | UPI പിൻ |  |
| `tpl_utility` | Utility | ബില്ലുകളും കണക്ഷനുകളും |  |
| `tpl_utility_account_holder` | Account holder | അക്കൗണ്ട് ഉടമ |  |
| `tpl_utility_consumer_number` | Consumer number | ഉപഭോക്തൃ നമ്പർ |  |
| `tpl_utility_due_day` | Bill due day | ബിൽ അടയ്ക്കുന്ന ദിവസം |  |
| `tpl_utility_fastag_id` | FASTag ID | FASTag ID |  |
| `tpl_utility_notes` | Notes | കുറിപ്പ് |  |
| `tpl_utility_portal_login` | Portal login | പോർട്ടൽ ലോഗിൻ |  |
| `tpl_utility_portal_password` | Portal password | പോർട്ടൽ പാസ്‌വേഡ് |  |
| `tpl_utility_provider` | Provider | സേവനം നൽകുന്ന കമ്പനി |  |
| `tpl_utility_utility_kind` | Utility kind | എന്തിന്റെ ബിൽ |  |
| `tpl_utility_vehicle_number` | Vehicle number | വാഹന നമ്പർ |  |
| `tpl_utility_wifi_password` | Wi-Fi password | Wi-Fi പാസ്‌വേഡ് |  |
| `tpl_voter_id` | Voter Id | വോട്ടർ തിരിച്ചറിയൽ |  |
| `tpl_voter_id_constituency` | Constituency | മണ്ഡലം |  |
| `tpl_voter_id_epic_number` | Epic number | EPIC നമ്പർ |  |
| `tpl_voter_id_file_copy` | Scanned copy | സ്കാൻ ചെയ്ത പകർപ്പ് |  |
| `tpl_voter_id_name_on_voter_id` | Name on voter id | വോട്ടർ കാർഡിലെ പേര് |  |
| `tpl_voter_id_notes` | Notes | കുറിപ്പ് |  |
| `tpl_voter_id_portal_password` | Portal password | NVSP പാസ്‌വേഡ് |  |
| `tr_days_many` | %1$d days left | %1$d ദിവസം ബാക്കി |  |
| `tr_days_one` | %1$d day left | %1$d ദിവസം ബാക്കി |  |
| `tr_gone_today` | gone today | ഇന്ന് പോകും |  |
| `tr_restore` | Restore | തിരികെ കൊണ്ടുവരിക |  |
| `tr_ttl_warn` | After that they are gone for good — there is no copy anywhere else. | അതിന് ശേഷം അവ എന്നെന്നേക്കുമായി പോകും — മറ്റെവിടെയും പകർപ്പില്ല. |  |
| `ui_hide_passphrase` | Hide passphrase | പാസ്‌ഫ്രെയ്സ് മറയ്ക്കുക |  |
| `ui_label_count` | %1$s, %2$s | %1$s, %2$s |  |
| `ui_show_passphrase` | Show passphrase | പാസ്‌ഫ്രെയ്സ് കാണിക്കുക |  |
| `vh_checked_many` | Checked on this device against %1$d records. Nothing was sent anywhere. | ഈ ഫോണിൽ തന്നെ %1$d രേഖകളുമായി ഒത്തുനോക്കി. ഒന്നും എവിടേക്കും അയച്ചിട്ടില്ല. |  |
| `vh_checked_one` | Checked on this device against %1$d record. Nothing was sent anywhere. | ഈ ഫോണിൽ തന്നെ %1$d രേഖയുമായി ഒത്തുനോക്കി. ഒന്നും എവിടേക്കും അയച്ചിട്ടില്ല. |  |
| `vh_count_many` | %1$d things worth a look. | %1$d കാര്യങ്ങൾ ശ്രദ്ധിക്കേണ്ടതുണ്ട്. |  |
| `vh_count_one` | %1$d thing worth a look. | %1$d കാര്യം ശ്രദ്ധിക്കേണ്ടതുണ്ട്. |  |
| `vh_empty` | No reused, weak or expiring credentials. | വീണ്ടും ഉപയോഗിച്ച, ദുർബലമായ അല്ലെങ്കിൽ തീരാറായ വിവരമില്ല. |  |
| `vh_kind_common` | Commonly guessed | എളുപ്പം ഊഹിക്കാവുന്നത് |  |
| `vh_kind_expiring` | Expiring | തീരാറായി |  |
| `vh_kind_reused` | Reused password | വീണ്ടും ഉപയോഗിച്ച പാസ്‌വേഡ് |  |
| `vh_kind_weak` | Weak | ദുർബലം |  |
| `vh_no_kit_title` | No recovery kit saved | റിക്കവറി കിറ്റ് ഒന്നും സൂക്ഷിച്ചിട്ടില്ല |  |
| `vh_nothing` | Nothing to fix. | ശരിയാക്കാൻ ഒന്നുമില്ല. |  |
| `wl_chip_crypto` | Argon2id + XChaCha20 | Argon2id + XChaCha20 |  |
| `wl_chip_offline` | Offline | ഓഫ്‌ലൈൻ |  |
| `wl_chip_open` | Open source | ഓപ്പൺ സോഴ്‌സ് |  |
| `wl_create` | Create a new vault | പുതിയ കലവറ ഉണ്ടാക്കുക |  |
| `wl_footer` | No email · No account · Nothing leaves this phone | ഇമെയിലില്ല · അക്കൗണ്ടില്ല · ഒന്നും ഈ ഫോണിന് പുറത്തുപോകില്ല |  |
| `wl_head_1` | Your keys. | നിങ്ങളുടെ താക്കോലുകൾ. |  |
| `wl_head_2` | Your device. | നിങ്ങളുടെ ഫോൺ. |  |
| `wl_head_3` | No server. | സെർവറില്ല. |  |
| `wl_restore` | Restore from Recovery Kit | റിക്കവറി കിറ്റിൽ നിന്ന് തിരികെ കൊണ്ടുവരിക |  |
| `wl_sr_headline` | Your keys. Your device. No server. | നിങ്ങളുടെ താക്കോലുകൾ. നിങ്ങളുടെ ഫോൺ. സെർവറില്ല. |  |
