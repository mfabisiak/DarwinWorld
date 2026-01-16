package io.github.mfabisiak.darwinworld.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import io.github.mfabisiak.darwinworld.Simulation
import io.github.mfabisiak.darwinworld.config.SimulationConfig
import io.github.mfabisiak.darwinworld.ui.components.MapVisualizer
import kotlinx.coroutines.delay

@Composable
fun AnimationScreen(config : SimulationConfig){

    val simulation = remember { Simulation(config)}

    val currentMap by simulation.worldMap.collectAsState()

    LaunchedEffect(Unit){
        while(true) {
            delay(3000)
            simulation.simulateDay()
        }
    }
    Column(){
        MapVisualizer(currentMap, config.upperBound.y + 1, config.upperBound.x + 1)
    }

}
