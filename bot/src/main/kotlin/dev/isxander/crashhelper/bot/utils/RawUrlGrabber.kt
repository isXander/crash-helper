package dev.isxander.crashhelper.bot.utils

private val PASTE_RAW_REGEXES = listOf(
    Regex("https?://paste\\.ee/r/[\\w-.]+"),
    Regex("https?://pastebin\\.com/raw/[\\w-.]+"),
    Regex("https?://hastebin\\.com/raw/[\\w-.]+"),
    Regex("https?://hst\\.sh/raw/[\\w-.]+"),
    Regex("https?://api.mclo\\.gs/1/raw/[\\w-.]+"),
)

val PASTE_RAW_MAP = mapOf(
    Regex("https?://paste\\.ee/p/([\\w-.]+)") to "https://paste.ee/r/%s",
    Regex("https?://pastebin\\.com/([\\w-.]+)") to "https://pastebin.com/raw/%s",
    Regex("https?://hastebin\\.com/([\\w-.]+)") to "https://hastebin.com/raw/%s",
    Regex("https?://hst\\.sh/([\\w-.]+)") to "https://hst.sh/raw/%s",
    Regex("https?://mclo\\.gs/([\\w-.]+)") to "https://api.mclo.gs/1/raw/%s"
)

val PASTE_REGEXES = PASTE_RAW_MAP.keys + PASTE_RAW_REGEXES

fun findPasteUrls(text: String): List<String> {
    val urls = mutableListOf<String>()
    for ((regex, format) in PASTE_RAW_MAP) {
        val match = regex.findAll(text)
        for (result in match) {
            val url = format.format(result.groupValues[1])
            urls.add(url)
        }
    }
    for (regex in PASTE_RAW_REGEXES) {
        val match = regex.findAll(text)
        for (result in match) {
            urls.add(result.value)
        }
    }

    return urls
}