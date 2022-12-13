package dev.isxander.crashhelper.analysis

import com.google.gson.annotations.SerializedName

data class ChecksJson(
    @SerializedName("schema_version") val schemaVersion: Int = 1,
    val categories: List<Category>
) {
    infix fun merge(other: ChecksJson): ChecksJson {
        return ChecksJson(
            schemaVersion,
            categories + other.categories
        )
    }

    data class Category(
        val name: String,
        val checks: List<Check>
    )
}