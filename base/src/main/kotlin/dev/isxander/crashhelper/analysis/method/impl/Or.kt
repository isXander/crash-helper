package dev.isxander.crashhelper.analysis.method.impl

import com.google.gson.JsonObject
import dev.isxander.crashhelper.analysis.method.Method
import dev.isxander.crashhelper.analysis.method.MethodDecoder

class Or(private val methods: List<Method>) : Method {
    override fun check(crash: String): Boolean {
        return methods.any { it.check(crash) }
    }

    object Decoder : MethodDecoder {
        override fun decode(json: JsonObject): Method {
            return Or(MethodDecoder.getConditions(json))
        }
    }
}