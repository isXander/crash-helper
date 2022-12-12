package dev.isxander.crashhelper

object CrashAnalyser {
    fun analyse(crash: String): Analysis {
        val fixes = RepositoryFetcher.fixes

        val categories = mutableListOf<Analysis.AnalysisCategory>()
        for (category in fixes.categories) {
            val messages = mutableListOf<String>()

            for (check in category.checks) {
                if (check.check(crash))
                    messages.add(check.message)
            }

            if (messages.isNotEmpty())
                categories.add(Analysis.AnalysisCategory(category.name, messages))
        }

        return Analysis(categories)
    }

    data class Analysis(val categories: List<AnalysisCategory>) {
        data class AnalysisCategory(val name: String, val messages: List<String>)

        fun toMessage(): String {
            if (categories.isEmpty()) return "No insights found."

            val builder = StringBuilder()
            for (category in categories) {
                builder.append("**${category.name}**\n")
                for (message in category.messages) {
                    builder.append("- $message\n")
                }
            }
            return builder.toString()
        }
    }
}