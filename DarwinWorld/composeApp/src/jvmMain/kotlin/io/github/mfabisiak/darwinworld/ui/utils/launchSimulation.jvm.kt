package io.github.mfabisiak.darwinworld.ui.utils

import androidx.compose.runtime.mutableStateSetOf
import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig


object WindowManager {
    val openWindows = mutableStateSetOf<SimulationConfig>()

    fun launchSimulation(config: SimulationConfig) {
        openWindows.add(config)
    }

    fun closeSimulation(config: SimulationConfig) {
        openWindows.remove(config)
    }
}

actual fun launchSimulation(config: SimulationConfig) {
    WindowManager.launchSimulation(config)
}