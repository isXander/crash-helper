package dev.isxander.crashhelper.bot.utils

import dev.isxander.crashhelper.utils.httpClient
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

suspend fun uploadToMcLogs(content: String): String? {
    val response: McLogsResponse = httpClient.submitForm(
        url = "https://api.mclo.gs/1/log",
        formParameters = Parameters.build {
            append("content", content)
        }
    ).body()

    return response.takeIf { it.success }?.url
}

@Serializable
data class McLogsResponse(
    val success: Boolean,
    val id: String,
    val url: String,
    val raw: String
)