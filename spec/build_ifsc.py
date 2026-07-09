# Builds assets/ifsc.db from Razorpay's MIT-licensed IFSC dataset (Appendix E).
# Usage: python build_ifsc.py [path/to/IFSC.csv] [output.db]
# Get IFSC.csv from the latest release at https://github.com/razorpay/ifsc/releases
# Run once per app release; the app works fully if the output file is absent.
import csv, sqlite3, sys

src = sys.argv[1] if len(sys.argv) > 1 else "IFSC.csv"
dst = sys.argv[2] if len(sys.argv) > 2 else "ifsc.db"

con = sqlite3.connect(dst)
con.execute("DROP TABLE IF EXISTS ifsc")
con.execute("CREATE TABLE ifsc(code TEXT PRIMARY KEY, bank TEXT, branch TEXT, city TEXT, state TEXT)")
with open(src, newline="", encoding="utf-8") as f:
    rows = ((r["IFSC"], r["BANK"], r["BRANCH"], r.get("CITY", ""), r.get("STATE", ""))
            for r in csv.DictReader(f))
    con.executemany("INSERT OR REPLACE INTO ifsc VALUES (?,?,?,?,?)", rows)
con.commit()
con.execute("VACUUM")
n = con.execute("SELECT COUNT(*) FROM ifsc").fetchone()[0]
con.close()
print(f"wrote {dst}: {n} branches")
assert n > 100000, "dataset suspiciously small — wrong CSV?"
