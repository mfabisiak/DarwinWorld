package io.github.mfabisiak.darwinworld

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig
import io.github.mfabisiak.darwinworld.ui.config.ConfigInputScreen
import io.github.mfabisiak.darwinworld.ui.simulation.AnimationScreen


@Composable
fun App() {
    MaterialTheme{
        var currentConfig by remember { mutableStateOf<SimulationConfig?>(null)}
        if (currentConfig == null){
            ConfigInputScreen(
                onSimulationStart = { newConfig ->
                    currentConfig = newConfig
                }
            )
        }
        else{
            AnimationScreen(config = currentConfig!!)
        }
    }
}