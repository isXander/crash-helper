package dev.isxander.crashhelper.analysis.method.impl

import com.google.gson.JsonObject
import dev.isxander.crashhelper.analysis.method.Method
import dev.isxander.crashhelper.analysis.method.MethodDecoder
import kotlin.text.Regex

class ModLoaded(private val modId: String) : Method {
    override fun check(crash: String): Boolean {
        return when {
            crash.contains("---- Minecraft Crash Report ----") ->
                analyseInCrashReport(crash) || analyseInLog(crash)

            else -> analyseInLog(crash)
        }
    }

    private fun analyseInLog(crash: String): Boolean {
        return logModRegex.findAll(crash).map { it.groups["modId"]?.value }.any { it == modId }
    }

    private fun analyseInCrashReport(crash: String): Boolean {
        var printingMods = false
        for (line in crash.split("\n")) {
            if (line.startsWith("\tFabric Mods:")) {
                printingMods = true
                continue
            }

            if (!printingMods) continue

            if (!line.startsWith("\t\t"))
                break // indentation not enough, found end of printing mods

            val modInfo = crashModRegex.matchEntire(line) ?: error("'mod_loaded' method error: invalid mod info line: $line")
            val modId = modInfo.groups["modId"]?.value ?: continue
            if (modId == this.modId)
                return true
        }

        return false
    }

    companion object {
        private val logModRegex = Regex("^\\t- (?<modId>[a-z0-9_-]+) (?<version>.+)(?: via (?<viaModId>[a-z0-9_-]+))?\$")
        private val crashModRegex = Regex("^\t{2,}(?<modId>[a-z0-9_-]+): (?<modName>.+) (?<version>.+)$")
    }

    object Decoder : MethodDecoder {
        override fun decode(json: JsonObject): Method {
            return ModLoaded(json["mod_id"].asString)
        }
    }
}