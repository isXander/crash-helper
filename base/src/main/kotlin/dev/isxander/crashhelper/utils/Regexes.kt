package dev.isxander.crashhelper.utils


val URL_REGEX: Regex = Regex("https?://(www\\\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\\\.[a-zA-Z0-9()]{1,6}\\\\b([-a-zA-Z0-9()@:%_+.~#?&//=]*)", RegexOption.IGNORE_CASE)
val PASTEBIN_REGEX: Regex = Regex("(?:https?://)?(?<domain>paste\\.ee|pastebin\\.com|has?tebin\\.com|hasteb\\.in|hst\\.sh)/(?:raw/|p/)?([\\w-.]+)", RegexOption.IGNORE_CASE)
val CODE_BLOCK_REGEX: Regex = Regex("```(?<language>[a-zA-Z0-9]*)\\n(?<code>.+)\\n```", RegexOption.DOT_MATCHES_ALL)

val LOG_EXTENSIONS = setOf(
    "txt", "log"
)

val LOG_TEXT = setOf(
    Regex("Loading Minecraft .+ with (Fabric|Quilt) Loader"),
    Regex("Loading \\d+ mods:"),
    Regex("SpongePowered MIXIN Subsystem Version"),
    Regex("A mod crashed on startup!"),
    Regex("A fatal error has been detected by the Java Runtime Environment:"),
    Regex("---- Minecraft Crash Report ----")
)
