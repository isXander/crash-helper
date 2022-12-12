package dev.isxander.crashhelper.bot.utils

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable

val httpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }
}

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