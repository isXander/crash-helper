package dev.isxander.crashhelper.analysis.method.impl

import com.google.gson.JsonObject
import dev.isxander.crashhelper.analysis.method.Method
import dev.isxander.crashhelper.analysis.method.MethodDecoder
import kotlin.text.Regex

class AllocatedRamAmount(private val conditionString: String) : Method {
    override fun check(crash: String): Boolean {
        val ramAmount = parseRam(crash) ?: return false

        return when {
            conditionString.startsWith(">") -> ramAmount > conditionString.substring(1).toInt()
            conditionString.startsWith(">=") -> ramAmount >= conditionString.substring(2).toInt()
            conditionString.startsWith("<") -> ramAmount < conditionString.substring(1).toInt()
            conditionString.startsWith("<=") -> ramAmount <= conditionString.substring(2).toInt()
            conditionString.startsWith("=") -> ramAmount == conditionString.substring(1).toInt()
            else -> throw IllegalArgumentException("Invalid condition string: $conditionString")
        }
    }

    private fun parseRam(crash: String): Int? {
        val ramAmount = ramAllocationRegex.find(crash)?.groups?.get("ram")?.value?.toInt() ?: return null
        val ramUnit = ramAllocationRegex.find(crash)?.groups?.get("unit")?.value ?: return null

        return when (ramUnit) {
            "G" -> ramAmount * 1024
            "M" -> ramAmount
            "K" -> ramAmount / 1024
            else -> null
        }
    }

    companion object {
        private val ramAllocationRegex: Regex = Regex("-Xmx(?<ram>\\d+)(?<unit>[GMK])", RegexOption.IGNORE_CASE)
    }

    object Decoder : MethodDecoder {
        override fun decode(json: JsonObject): Method {
            return AllocatedRamAmount(json["amount"].asString)
        }
    }
}