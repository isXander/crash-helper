package dev.isxander.crashhelper.analysis

import com.google.gson.annotations.SerializedName

data class CrashFixJson(
    @SerializedName("schema_version") val schemaVersion: Int,
    val categories: List<Category>
) {
    data class Category(
        val name: String,
        val checks: List<Check>
    )
}