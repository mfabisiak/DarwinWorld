package io.github.mfabisiak.darwinworld.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import io.github.mfabisiak.darwinworld.Simulation
import io.github.mfabisiak.darwinworld.config.SimulationConfig
import io.github.mfabisiak.darwinworld.ui.components.MapVisualizer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

@Composable
fun AnimationScreen(config : SimulationConfig){

    val simulation = remember { Simulation(config)}

    val currentMap by simulation.worldMap.collectAsState()

    LaunchedEffect(Unit){
        withContext(Dispatchers.Default) {
            while (isActive) {
                delay(300)
                simulation.simulateDay()
            }
        }
    }

    Column {
        MapVisualizer(currentMap, config.upperBound.y + 1, config.upperBound.x + 1)
    }

}
