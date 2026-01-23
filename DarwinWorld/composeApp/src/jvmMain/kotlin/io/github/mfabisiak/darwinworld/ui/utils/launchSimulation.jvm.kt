package io.github.mfabisiak.darwinworld.ui.utils

import androidx.compose.runtime.mutableStateMapOf
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

actual fun launchSimulation(config: SimulationConfig) {
    WindowManager.launchSimulation(config)
}