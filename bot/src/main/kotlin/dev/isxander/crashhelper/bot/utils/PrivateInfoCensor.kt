package dev.isxander.crashhelper.bot.utils

val SENSITIVE_INFO_REGEX: Regex = Regex("(\"access_key\":\".+\"|api.sk1er.club/auth|LoginPacket|SentryAPI.cpp|\"authHash\":|\"hash\":\"|--accessToken \\S+|\\(Session ID is token:|Logging in with details: |Server-Hash: |Checking license key :|USERNAME=.*|https://api\\.hypixel\\.net/.+(\\?key=|&key=))", RegexOption.IGNORE_CASE)
val EMAIL_REGEX: Regex = Regex("[a-zA-Z0-9_.+-]{1,50}@[a-zA-Z0-9-]{1,50}\\.[a-zA-Z0-9-.]{1,10}", RegexOption.IGNORE_CASE)
val WINDOWS_MAC_USERNAME_REGEX: Regex = Regex("Users[/\\\\](?<username>[^/\\\\]+)(?:[/\\\\]*.)*")
val LINUX_USERNAME_REGEX: Regex = Regex("/home/(?<username>[^/]+)(?:/*[^/])*")

fun removePrivateInfo(_raw: String): String {
    var raw = _raw
    raw = SENSITIVE_INFO_REGEX.replace(raw, "(removed sensitive info)")
    raw = EMAIL_REGEX.replace(raw, "(removed email address)")
    raw = WINDOWS_MAC_USERNAME_REGEX.replace(raw, "(removed username)")
    raw = LINUX_USERNAME_REGEX.replace(raw, "(removed username)")
    return raw
}