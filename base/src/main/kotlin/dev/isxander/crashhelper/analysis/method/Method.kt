package dev.isxander.crashhelper.analysis.method

import dev.isxander.crashhelper.analysis.method.impl.*

interface Method {
    fun check(crash: String): Boolean

    companion object {
        val ALL = mapOf(
            "and" to And.Decoder,
            "or" to Or.Decoder,
            "not" to Not.Decoder,
            "none" to None.Decoder,
            "contains" to Contains.Decoder,
            "regex" to Regex.Decoder,
            "allocated_ram" to AllocatedRamAmount.Decoder,
        )
    }
}