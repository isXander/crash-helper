package dev.isxander.crashhelper

import dev.isxander.crashhelper.utils.URLCache
import io.ktor.http.*
import kotlin.time.Duration.Companion.hours

val repository = URLCache(1.hours) {
    add(Url("https://raw.githubusercontent.com/isXander/MinecraftIssues/main/issues.json"))
}