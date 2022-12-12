package dev.isxander.crashhelper.analysis

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import dev.isxander.crashhelper.analysis.method.Method
import dev.isxander.crashhelper.analysis.method.MethodDecoder
import java.lang.reflect.Type

data class Check(
    val message: String,
    private val method: Method
) : Method by method {
    companion object Decoder : JsonDeserializer<Check> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): Check {
            val method = MethodDecoder.decode(json.asJsonObject)
            val message = json.asJsonObject["message"].asString
            return Check(message, method)
        }

    }
}