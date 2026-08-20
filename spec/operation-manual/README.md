# Zerokosh Android — Operation Manual for the Executor Model

**Author:** Principal Android Software Architect (Fable 5)
**Executor:** Claude Haiku 4.5 — reads this manual, writes code mechanically.
**Repo:** `C:\Users\acer\zerokosh` · frozen baseline commit `6982e9c`
**Supreme law:** `spec/zerokosh_master_build_manual_v2.md` (the "Master Spec"). If this manual ever appears to contradict the Master Spec, STOP and report — do not resolve it yourself.

## Reading order (load ONLY the file you are executing — token discipline)

| File | Contents |
|---|---|
| `00_EXECUTOR_RULES.md` | Ground rules. Read FIRST, every session. |
| `01_PHASE1_SCAFFOLDING_BUILD.md` | Module tree, version catalog, build types, R8 |
| `02_PHASE2_DI_GRAPH.md` | Object graph, every constructor signature |
| `03_PHASE3_DATA_ENGINE.md` | Vault file engine, stores, repository truth, the one network call |
| `04_PHASE4_STATE_UI.md` | Per-screen state/event/effect tables, UI manifest |
| `05_PHASE5_ERRORS.md` | Error taxonomy, retries, exact failure strings |
| `06_PHASE6_PERMISSIONS_BACKGROUND.md` | Final manifest, permission flows, WorkManager |
| `07_PHASE7_SECURITY.md` | Keystore, encrypted prefs, network security config |
| `08_PHASE8_OVERVIEW.md` | How to run the queue: order, verification, commits |
| `08A_QUEUE_M3_M4.md` | TASK-101…TASK-406 (build infra, sync folder, attachments, reminders, IFSC) |
| `08B_QUEUE_M5_M6.md` | TASK-501…TASK-605 (import/export, autofill wiring) |
| `08C_QUEUE_M7_M8.md` | TASK-701…TASK-808 (Hindi, a11y, breach check, hardening, release) |

## What is already DONE (frozen — never redesign, never rewrite)

Milestones M0 and M1 are committed and gate-verified. Commit `6982e9c` additionally froze all M2 screens and partial M3–M6 implementations, validated by `:app:assembleDebug` and 42 green `:core` tests. The remaining work is exactly the Phase 8 queue — wiring, additions, and hardening around the frozen baseline.

## Execution loop (per task)

1. Open the task's queue file section. 2. Write exactly the code given. 3. Run the task's `VERIFY` command. 4. If green → next task. If red → `00_EXECUTOR_RULES.md` §Circuit-Breaker. 5. At each `CHECKPOINT` box → commit with the given message template.
