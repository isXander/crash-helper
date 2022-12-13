package dev.isxander.crashhelper.bot.utils

val IP_REGEX: Regex = Regex("([0-9]{1,3})\\\\.([0-9]{1,3})\\\\.([0-9]{1,3})\\\\.([0-9]{1,3})", RegexOption.IGNORE_CASE)
val SENSITIVE_INFO_REGEX: Regex = Regex("(\"access_key\":\".+\"|api.sk1er.club/auth|LoginPacket|SentryAPI.cpp|\"authHash\":|\"hash\":\"|--accessToken \\S+|\\(Session ID is token:|Logging in with details: |Server-Hash: |Checking license key :|USERNAME=.*|https://api\\.hypixel\\.net/.+(\\?key=|&key=))", RegexOption.IGNORE_CASE)
val EMAIL_REGEX: Regex = Regex("[a-zA-Z0-9_.+-]{1,50}@[a-zA-Z0-9-]{1,50}\\.[a-zA-Z0-9-.]{1,10}", RegexOption.IGNORE_CASE)
val WINDOWS_MAC_USERNAME_REGEX: Regex = Regex("Users[/\\\\](?<username>[^/\\\\]+)(?:[/\\\\]*.)*")
val LINUX_USERNAME_REGEX: Regex = Regex("/home/(?<username>[^/]+)(?:/*[^/])*")

fun removePrivateInfo(raw: String): String {
    return raw
        .replace(IP_REGEX, "[removed ip address]")
        .replace(SENSITIVE_INFO_REGEX, "[removed sensitive info]")
        .replace(EMAIL_REGEX, "[removed email]")
        .replace(WINDOWS_MAC_USERNAME_REGEX, "[removed username]")
        .replace(LINUX_USERNAME_REGEX, "[removed username]")
}