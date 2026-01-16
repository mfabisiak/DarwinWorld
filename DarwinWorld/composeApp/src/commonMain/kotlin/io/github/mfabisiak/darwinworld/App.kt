package io.github.mfabisiak.darwinworld

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import io.github.mfabisiak.darwinworld.config.SimulationConfig
import io.github.mfabisiak.darwinworld.ui.AnimationScreen

import io.github.mfabisiak.darwinworld.ui.ConfigInputScreen


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