package dev.isxander.crashhelper.analysis.method

import com.google.gson.*
import java.lang.reflect.Type

interface MethodDecoder {
    fun decode(json: JsonObject): Method

    companion object : JsonDeserializer<Method> {
        fun decode(json: JsonObject): Method {
            return findDecoder(json).decode(json);
        }

        fun findDecoder(json: JsonObject): MethodDecoder {
            val methodId = json["method"] as? JsonPrimitive? ?: error("Every method must contain 'method' element")
            return Method.ALL[methodId.asString] ?: error("Unknown method id: $methodId")
        }

        fun getConditions(json: JsonObject): List<Method> {
            val conditionsArr = (json["conditions"] as? JsonArray ?: error("'conditions' must be of type 'array'"))
            val conditions = mutableListOf<Method>()
            for (element in conditionsArr) {
                if (element !is JsonObject) error("All conditions must be of type 'object'")
                val decoder = findDecoder(element)
                conditions.add(decoder.decode(element))
            }
            return conditions
        }

        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Method {
            if (json !is JsonObject) error("Method must be of type 'object'")
            return decode(json)
        }
    }
}