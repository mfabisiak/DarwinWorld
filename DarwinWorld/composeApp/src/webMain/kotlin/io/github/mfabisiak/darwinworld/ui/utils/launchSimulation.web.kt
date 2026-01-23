package io.github.mfabisiak.darwinworld.ui.utils

import io.github.mfabisiak.darwinworld.config.ConfigBuilder
import kotlinx.browser.window
import kotlinx.serialization.json.Json

actual fun launchSimulation(config: ConfigBuilder) {
    val jsonString = Json.encodeToString(config.toSerializableConfig())
    println(jsonString)

    val encodedConfig = window.btoa(jsonString)

    val currentUrl = window.location.href.split("?")[0]
    val newUrl = "$currentUrl?config=$encodedConfig"

    window.open(newUrl, "_blank")
}