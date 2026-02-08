package io.github.mfabisiak.darwinworld.ui.utils

import androidx.compose.runtime.mutableStateMapOf
import io.github.mfabisiak.darwinworld.config.ConfigBuilder
import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig


object WindowManager {
    val openWindows = mutableStateMapOf<String, SimulationConfig>()

    fun launchSimulation(config: SimulationConfig) {
        openWindows[config.id] = config
    }

    fun closeSimulation(id: String) {
        openWindows.remove(id)
    }
}

actual fun launchSimulation(config: ConfigBuilder) {
    WindowManager.launchSimulation(config.build())
}