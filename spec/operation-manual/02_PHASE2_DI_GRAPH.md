# Phase 2 — Dependency Injection & Core Object Graph

## 2.1 The law: manual composition root, no DI framework

Hilt/Dagger/Koin are PROHIBITED (Rules §0.3). The entire object graph is constructed once in `ZerokoshApp.onCreate()` and reached via `application as ZerokoshApp`. This is the whole DI system; do not add lazy service locators, `object` singletons holding Context, or companion factories.

## 2.2 The graph (application-scoped, constructed in this order)

```
ZerokoshApp.onCreate()
 ├── prefs      = Prefs(this)                                   [needs Context]
 ├── catalog    = AssetCatalog(this)                            [needs Context]
 └── repository = VaultRepository(
        crypto = AndroidCrypto(),                               [no deps]
        store  = SafVaultStore.fromUriString(this, prefs.syncFolderUri)
                     ?: LocalVaultStore(this),                  [Context (+ tree uri)]
        prefs  = prefs)
```
(The `SafVaultStore` fallback line is installed by TASK-303; baseline uses `LocalVaultStore(this)` only.)

## 2.3 Constructor & scope table (exact signatures — never change them)

| Class | Constructor | Scope | Held by |
|---|---|---|---|
| `Prefs` | `(context: Context)` | app-singleton | `ZerokoshApp.prefs` |
| `AssetCatalog` | `(context: Context)` | app-singleton | `ZerokoshApp.catalog` |
| `AndroidCrypto` | `()` | app-singleton | `VaultRepository.crypto` (public val) |
| `LocalVaultStore` | `(context: Context)` | app-singleton | `VaultRepository.store` (`@Volatile var`) |
| `SafVaultStore` | `(context: Context, treeUri: Uri)` | app-singleton | same slot, swapped by `switchStore` (TASK-302) |
| `VaultRepository` | `(crypto: AndroidCrypto, store: VaultStore, prefs: Prefs)` | app-singleton | `ZerokoshApp.repository` |
| `ReminderWorker` | `(context: Context, params: WorkerParameters)` | WorkManager-instantiated | — |
| `ClipboardHelper.ClipboardClearWorker` | `(context: Context, params: WorkerParameters)` | WorkManager-instantiated | — |
| `ZerokoshAutofillService` | `()` (system) | system service | reaches graph via `application as ZerokoshApp` |
| `MainActivity` | `()` (system) | activity | `private val app get() = application as ZerokoshApp` |

Stateless helpers stay `object`s **only** because they hold no Context/state between calls: `QuickUnlockManager`, `ClipboardHelper` (Handler + timestamp only), `RevealAuth` (grace timestamp), `LoginHelper`, `BreachCheck`, `RecoveryKitPdf`, `VaultOperations`, `VaultFileCodec`, `VaultMerge`, `Totp`, `CardUtils`, `PasswordGenerator`, `RecoveryKey`, `CsvImport`. Do not convert them to classes; do not add new stateful objects.

## 2.4 Composables receive the graph as a single `app: ZerokoshApp` parameter

Every screen composable's first parameter is `app: ZerokoshApp` (see the frozen screens). New screens in Phase 8 follow the identical convention. ViewModels are NOT introduced — state lives in `VaultRepository` StateFlows + `remember` (Phase 4 documents why this is the frozen architecture).

## 2.5 Threading contract

- All KDF/AEAD/file IO runs inside `VaultRepository` methods that internally `withContext(Dispatchers.Default)` and serialize writes with `ioMutex` (a `kotlinx.coroutines.sync.Mutex`). UI code NEVER calls `store` or `crypto` directly for vault data.
- Composables launch repository calls with `rememberCoroutineScope().launch { … }`.
- `ClipboardHelper` posts on the main-looper `Handler` plus a WorkManager fallback; keep both paths.
