package dev.isxander.crashhelper.utils

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

val httpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }
}