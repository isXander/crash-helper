package dev.isxander.crashhelper.bot

import com.kotlindiscord.kord.extensions.ExtensibleBot
import com.kotlindiscord.kord.extensions.utils.env
import com.kotlindiscord.kord.extensions.utils.envOrNull
import dev.isxander.crashhelper.bot.extensions.TextScanner
import dev.kord.common.entity.Snowflake
import dev.kord.gateway.Intent

val TEST_SERVER_ID = Snowflake(env("TEST_SERVER_ID"))

private val TOKEN = envOrNull("TOKEN")
    ?: error("Token not found in environment.")

suspend fun main() {
    val bot = ExtensibleBot(TOKEN) {
        intents {
            +Intent.GuildMessages
        }

        extensions {
            add { TextScanner }
        }
    }
    bot.start()
}
