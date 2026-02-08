package io.github.mfabisiak.darwinworld

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.github.mfabisiak.darwinworld.config.ConfigBuilder
import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig

object SimulationNavigation {

    var currentConfig by mutableStateOf<SimulationConfig?>(null)
        private set

    fun open(builder: ConfigBuilder) {
        currentConfig = builder.build()
    }

    fun close() {
        currentConfig = null
    }
}