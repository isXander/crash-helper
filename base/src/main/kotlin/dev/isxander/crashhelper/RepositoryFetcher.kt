package dev.isxander.crashhelper

import com.google.gson.*
import dev.isxander.crashhelper.analysis.CrashFixJson
import dev.isxander.crashhelper.analysis.Check
import dev.isxander.crashhelper.analysis.method.Method
import dev.isxander.crashhelper.analysis.method.MethodDecoder

object RepositoryFetcher {
    private val gson: Gson = GsonBuilder().apply {
        registerTypeHierarchyAdapter(Method::class.java, MethodDecoder)
        registerTypeHierarchyAdapter(Check::class.java, Check.Decoder)
    }.create()

    fun parseRepository(json: String): CrashFixJson {
        return gson.fromJson(json, CrashFixJson::class.java)
    }

    // TODO: fetch from another module
    val fixes: CrashFixJson by lazy {
        parseRepository(RepositoryFetcher::class.java.getResourceAsStream("/fixes.json")!!.readAllBytes().decodeToString())
    }
}