package dev.isxander.crashhelper.bot.extensions

import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.event
import com.kotlindiscord.kord.extensions.utils.download
import com.kotlindiscord.kord.extensions.utils.isNullOrBot
import com.kotlindiscord.kord.extensions.utils.runSuspended
import dev.isxander.crashhelper.CrashAnalyser
import dev.isxander.crashhelper.bot.utils.removePrivateInfo
import dev.isxander.crashhelper.bot.utils.uploadToMcLogs
import dev.isxander.crashhelper.utils.CODE_BLOCK_REGEX
import dev.kord.core.event.message.MessageCreateEvent
import kotlinx.coroutines.newFixedThreadPoolContext
import dev.isxander.crashhelper.utils.LOG_EXTENSIONS
import dev.isxander.crashhelper.utils.LOG_TEXT
import dev.isxander.crashhelper.utils.PASTEBIN_REGEX
import dev.kord.common.Color
import dev.kord.core.behavior.channel.createEmbed

object TextScanner : Extension() {
    override val name: String = "Text Scanner"
    private val dispatcher = newFixedThreadPoolContext(8, "Text Scanner")

    override suspend fun setup() {
        event<MessageCreateEvent> {
            check { failIf(event.member.isNullOrBot()) }
            check { passIf(event.message.webhookId == null) }

            action {
                runSuspended(dispatcher) {
                    val content = event.message.content
                    val texts = mutableListOf<String>()

                    event.message.attachments
                        .filter { LOG_EXTENSIONS.any { extension -> it.filename.endsWith(".$extension") }.also { println(it) } }
                        .filter { it.size / 1000 / 1000 < 5 }
                        .map { it.download().decodeToString() }
                        .filter { LOG_TEXT.any { regex -> regex.containsMatchIn(it) } }
                        .forEach { texts.add(it) }

                    PASTEBIN_REGEX
                        .findAll(content)
                        .map { it.groups[0]!!.value }
                        .filter { LOG_TEXT.any { regex -> regex.containsMatchIn(it) } }
                        .forEach { texts.add(it) }

                    CODE_BLOCK_REGEX
                        .findAll(content)
                        .map { it.groups["code"]!!.value }
                        .filter { LOG_TEXT.any { regex -> regex.containsMatchIn(it) } }
                        .forEach { texts.add(it) }

                    if (texts.isNotEmpty()) event.message.delete("Replaced by CrashHelper")
                    else return@runSuspended

                    event.message.channel.createEmbed {
                        title = "A log was posted!"
                        color = Color(0xff4747)

                        val filteredContent = removePrivateInfo(content)
                            .replace(CODE_BLOCK_REGEX, "(code block)")
                            .replace(PASTEBIN_REGEX, "(pastebin link)")
                            .replace(" +".toRegex(), " ")
                        if (filteredContent.isNotBlank())
                            description = "**${event.member?.displayName} said:** *$filteredContent*"

                        author {
                            name = event.member?.displayName
                            url = "https://short.isxander.dev/crashhelper-bot"
                            icon = event.member?.avatar?.url
                        }

                        footer {
                            text = "Powered by isXander/MinecraftIssues"
                            icon = "https://dl.isxander.dev/logos/github/mark/normal.png"
                        }

                        for (text in texts) {
                            field {
                                name = uploadToMcLogs(text) ?: "*failed to upload log*"

                                value = try {
                                    CrashAnalyser.analyse(text).toMessage()
                                } catch (e: Exception) {
                                    "Failed to analyse log: ${e.message}"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}