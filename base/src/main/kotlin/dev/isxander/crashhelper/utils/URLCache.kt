package dev.isxander.crashhelper.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.isxander.crashhelper.analysis.Check
import dev.isxander.crashhelper.analysis.ChecksJson
import dev.isxander.crashhelper.analysis.method.Method
import dev.isxander.crashhelper.analysis.method.MethodDecoder
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlin.experimental.ExperimentalTypeInference
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.TimeMark
import kotlin.time.TimeSource

@OptIn(ExperimentalTime::class, ExperimentalTypeInference::class)
class URLCache(private val expiration: Duration, @BuilderInference builderAction: MutableSet<Url>.() -> Unit) {
    private val sources = buildSet(builderAction)
    private var cacheEntry = fetch()

    fun get(): ChecksJson {
        return cacheEntry.takeIf { !it.isExpired() }?.value ?: fetch().value
    }

    private fun fetch(): CacheEntry {
        return runBlocking {
            val sourcesData = sources.map {
                async {
                    gson.fromJson(httpClient.get(it).bodyAsText(), ChecksJson::class.java)
                }
            }.awaitAll()

            cacheEntry = CacheEntry(sourcesData.reduce { first, next -> first merge next })
            cacheEntry
        }
    }

    companion object {
        private val gson: Gson = GsonBuilder().apply {
            registerTypeHierarchyAdapter(Method::class.java, MethodDecoder)
            registerTypeHierarchyAdapter(Check::class.java, Check.Decoder)
        }.create()
    }

    private inner class CacheEntry(val value: ChecksJson, val expireTime: TimeMark = TimeSource.Monotonic.markNow() + expiration) {
        fun isExpired(): Boolean = expireTime.hasPassedNow()
    }
}