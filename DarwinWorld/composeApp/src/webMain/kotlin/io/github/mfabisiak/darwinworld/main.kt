package io.github.mfabisiak.darwinworld

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import io.github.mfabisiak.darwinworld.config.SerializableConfig
import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig
import io.github.mfabisiak.darwinworld.ui.simulation.AnimationScreen
import kotlinx.browser.window
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.w3c.dom.url.URLSearchParams
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.toJsString

@OptIn(ExperimentalComposeUiApi::class, ExperimentalWasmJsInterop::class)
fun main() {
    val urlParams = URLSearchParams(window.location.search.toJsString())
    val encodedConfig = urlParams.get("config")

    ComposeViewport {
        if (encodedConfig != null) {
            val config: SimulationConfig? = try {
                val jsonString = window.atob(encodedConfig)
                Json.decodeFromString<SerializableConfig>(jsonString).toSimulationConfig()
            } catch (_: IllegalArgumentException) {
                null
            } catch (_: SerializationException) {
                null
            }

            if (config != null) {
                AnimationScreen(config)
            } else App()
        } else {
            App()
        }
    }
}