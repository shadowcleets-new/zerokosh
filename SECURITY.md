# Security Policy

## Reporting a vulnerability

Please report vulnerabilities privately — do not open a public issue.

- Email: **security@bharatvault.org** (interim: open a GitHub Security Advisory on this repository via *Security → Report a vulnerability*)
- You will receive an acknowledgement within **72 hours**.

## Coordinated disclosure

We follow a **90-day disclosure window**: we ask that you give us up to 90 days from your report to ship a fix before public disclosure. We will credit you in the release notes unless you prefer otherwise. If we ship a fix earlier, you are free to disclose as soon as the fixed release is out.

## Scope

In scope: the BharatVault applications, the `.bvlt` vault format, the crypto scheme in `spec/` §3, autofill integration, and the build/release pipeline.

Out of scope (see [THREAT_MODEL.md](THREAT_MODEL.md)): compromised or rooted operating systems, targeted state-level attackers, and vulnerabilities in the underlying OS or in libsodium itself (report those upstream).

## What BharatVault promises

- No servers, no accounts, no telemetry — there is no backend to breach.
- All vault data is encrypted with libsodium (Argon2id + XChaCha20-Poly1305) before it touches any disk.
- The only network calls the app can ever make are user-initiated and opt-in: the k-anonymity breach check (§5.9) and, on desktop, a manual update-tag check (§13).

## Supported versions

The latest release on each platform receives security fixes. Older releases are not patched — update.
