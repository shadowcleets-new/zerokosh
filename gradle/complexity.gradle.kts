// #region Cyclomatic complexity guard
//
// McCabe complexity per function: 1, plus one for every if, when-entry, loop,
// catch, elvis and boolean operator. Anything above THRESHOLD fails the build.
//
// Deliberately conservative — it may over-count slightly, never under-count, so
// a function that passes here passes a stricter tool. Two earlier versions of
// this counter were wrong in opposite directions: one could not bound
// expression-bodied functions, so a one-line `ByteArray.body()` scored 17; the
// fix then broke multi-line signatures, so a genuinely 46-point function scored
// 1. Under-counting is the dangerous direction, which is why both shapes are
// handled explicitly below and why the sanity numbers are in the tests.
//
// Known offenders live in config/complexity-baseline.txt. The baseline is a
// ratchet, not an amnesty: a function may not get worse, a new one may not be
// added, and the target is an empty file.
// #endregion

val complexityThreshold = 10
val complexityBaseline = rootProject.file("config/complexity-baseline.txt")
val complexitySources = listOf(
    rootProject.file("app/src/main"),
    rootProject.file("core/src/main"),
)

val verifyComplexity by tasks.registering {
    group = "verification"
    description = "Fails if any function exceeds a cyclomatic complexity of $complexityThreshold."
    val threshold = complexityThreshold
    val baselineFile = complexityBaseline
    val roots = complexitySources
    inputs.files(roots.map { rootProject.fileTree(it) { include("**/*.kt") } })
    inputs.file(baselineFile)
    outputs.upToDateWhen { false }

    doLast {
        /** Blank out strings and comments so they contribute no decision points. */
        fun stripNoise(src: String): String {
            val out = StringBuilder(src.length)
            var i = 0
            while (i < src.length) {
                when {
                    src.startsWith("\"\"\"", i) -> {
                        val j = src.indexOf("\"\"\"", i + 3)
                        i = if (j < 0) src.length else j + 3
                        out.append("\"\"")
                    }
                    src[i] == '"' -> {
                        var j = i + 1
                        while (j < src.length && src[j] != '"') j += if (src[j] == '\\') 2 else 1
                        i = j + 1
                        out.append("\"\"")
                    }
                    src.startsWith("//", i) -> {
                        val j = src.indexOf('\n', i)
                        i = if (j < 0) src.length else j
                    }
                    src.startsWith("/*", i) -> {
                        val j = src.indexOf("*/", i)
                        i = if (j < 0) src.length else j + 2
                    }
                    else -> {
                        out.append(src[i])
                        i++
                    }
                }
            }
            return out.toString()
        }

        /** The brace-balanced block beginning at [from], which must be a '{'. */
        fun blockAt(src: String, from: Int): String {
            var depth = 0
            for (j in from until src.length) {
                if (src[j] == '{') depth++
                if (src[j] == '}') {
                    depth--
                    if (depth == 0) return src.substring(from, j + 1)
                }
            }
            return src.substring(from)
        }

        /**
         * The body of the function whose parameter list opens at [afterParen].
         *
         * Skips the parameter list first — it is routinely multi-line, and scanning for
         * '{' from inside it stops at the first newline. Then takes a brace-balanced
         * block, or for an expression body everything up to the first line indented no
         * deeper than the declaration itself.
         */
        fun bodyOf(src: String, afterParen: Int, declIndent: Int): String {
            var depth = 1
            var i = afterParen
            while (i < src.length && depth > 0) {
                if (src[i] == '(') depth++
                if (src[i] == ')') depth--
                i++
            }
            if (depth > 0) return ""

            var angle = 0
            while (i < src.length) {
                val ch = src[i]
                if (ch == '<') angle++
                else if (ch == '>') angle = maxOf(0, angle - 1)
                else if (angle == 0 && (ch == '{' || ch == '=')) break
                i++
            }
            if (i >= src.length) return ""
            if (src[i] == '{') return blockAt(src, i)

            val lines = src.substring(i).split("\n")
            val kept = StringBuilder(lines.first())
            for (line in lines.drop(1)) {
                if (line.isNotBlank() && line.takeWhile { it == ' ' }.length <= declIndent) break
                kept.append('\n').append(line)
            }
            return kept.toString()
        }

        /** Arrows inside `when` blocks. A lambda arrow is not a branch. */
        fun whenArrows(body: String): Int {
            var total = 0
            Regex("""\bwhen\s*[({]""").findAll(body).forEach { m ->
                val open = body.indexOf('{', m.range.first)
                if (open >= 0) total += blockAt(body, open).split("->").size - 1
            }
            return total
        }

        val decisionPatterns = listOf(
            """\bif\s*\(""", """\bfor\s*\(""", """\bwhile\s*\(""", """\bcatch\s*\(""",
            """\?:""", """&&""", """\|\|""",
        ).map(::Regex)

        fun complexityOf(body: String): Int =
            1 + decisionPatterns.sumOf { it.findAll(body).count() } + whenArrows(body)

        val funRe = Regex("""\bfun\s+(?:<[^>]*>\s*)?([A-Za-z_][\w.]*)\s*\(""")
        val found = mutableMapOf<String, Int>()
        var scanned = 0
        roots.forEach { root ->
            root.walkTopDown().filter { it.extension == "kt" }.forEach { file ->
                scanned++
                val src = stripNoise(file.readText())
                funRe.findAll(src).forEach { m ->
                    val lineStart = src.lastIndexOf('\n', m.range.first) + 1
                    val indent = src.substring(lineStart).takeWhile { it == ' ' }.length
                    val score = complexityOf(bodyOf(src, m.range.last + 1, indent))
                    val key = "${file.name}:${m.groupValues[1]}"
                    if (score > (found[key] ?: 0)) found[key] = score
                }
            }
        }

        val baseline = if (baselineFile.exists()) {
            baselineFile.readLines()
                .filter { it.isNotBlank() && !it.startsWith("#") }
                .associate { line ->
                    val (name, score) = line.split("=")
                    name.trim() to score.trim().toInt()
                }
        } else {
            emptyMap()
        }

        val over = found.filterValues { it > threshold }
        val problems = mutableListOf<String>()
        over.forEach { (key, score) ->
            val allowed = baseline[key]
            when {
                allowed == null -> problems += "$key is $score, over the limit of $threshold"
                score > allowed -> problems += "$key grew from $allowed to $score"
            }
        }
        val fixed = baseline.keys.filter { (found[it] ?: 0) <= threshold }

        if (problems.isNotEmpty()) {
            throw GradleException(
                "Cyclomatic complexity (${problems.size} problem(s)):" + System.lineSeparator() +
                    problems.sorted().joinToString(System.lineSeparator()) { "  $it" } +
                    System.lineSeparator() +
                    "Split the function. The baseline records existing debt and may only shrink.",
            )
        }
        if (fixed.isNotEmpty()) {
            throw GradleException(
                "${fixed.size} baselined function(s) are now under $threshold — " +
                    "remove them from ${baselineFile.name} so they cannot regress:" +
                    System.lineSeparator() + fixed.sorted().joinToString(System.lineSeparator()) { "  $it" },
            )
        }
        logger.lifecycle(
            "verifyComplexity: ok ($scanned files, ${found.size} functions, " +
                "${over.size} baselined, 0 new over $threshold)",
        )
    }
}

// The root project has no `check` task, so matching on this project's tasks
// silently wired the guard to nothing — a lint nobody runs. It hangs off every
// module's check instead.
subprojects {
    tasks.matching { it.name == "check" }.configureEach { dependsOn(verifyComplexity) }
}
