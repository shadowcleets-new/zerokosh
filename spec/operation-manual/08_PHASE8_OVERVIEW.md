# Phase 8 — The Production Execution Queue (how to run it)

## 8.1 Strict order

Execute task groups in this exact sequence; tasks inside a group in listed order. Never start a group before the previous group's CHECKPOINT commit exists.

| Group | Tasks | File | Milestone gate (Master Spec §0.7) |
|---|---|---|---|
| INFRA | TASK-101…104 | 08A | build green after version-catalog migration |
| M3 | TASK-301…306 | 08A | sync folder + lifecycle; §11.3 steps 15–22 pending-device |
| M4 | TASK-401…405 | 08A | attachments, custom fields, reminders, IFSC lookup |
| M5 | TASK-501…504 | 08B | import tests green incl. 100-row Chrome lossless |
| M6 | TASK-601…605 | 08B | autofill registered; §11.5 pending-device |
| M7 | TASK-701…704 | 08C | en+hi 100 %; a11y pass |
| M8 | TASK-801…808 | 08C | §11.6 checklist; signed-artifact procedure; STOP before store |

## 8.2 Task anatomy

Every task gives: **Task ID · Target path(s) · Complete code** (full file, or PATCH with verbatim `OLD →` / `NEW →` blocks) · **Strings** (exact `<string>` lines + insertion anchor) · **VERIFY** command. PATCH rule: locate the OLD text verbatim; not found → STOP (Rules §0.2/0.4). Full-file tasks: replace the entire file with the given content.

## 8.3 Checkpoint commit template

```
git add -A
git commit -m "<MILESTONE>: <one-line summary>

Gates: <verify commands run + result>
Pending human-device verification: <list §11.3/§11.5 step numbers, or 'none'>

Executed-by: Claude Haiku 4.5 (Operation Manual vX)
Co-Authored-By: Claude Fable 5 <noreply@anthropic.com>"
```

## 8.4 Standard VERIFY commands

- `BUILD` / `CORETEST` — defined in `00_EXECUTOR_RULES.md` §0.6.
- `RELEASE` — `.\gradlew.bat :app:assembleRelease --console=plain` (used only in M8).
All Gradle commands run from `C:\Users\acer\bharatvault` with `$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"`.
