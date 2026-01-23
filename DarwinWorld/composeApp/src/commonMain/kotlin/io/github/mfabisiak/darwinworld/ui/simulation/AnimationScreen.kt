package io.github.mfabisiak.darwinworld.ui.simulation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig
import io.github.mfabisiak.darwinworld.ui.simulation.components.MapVisualizer
import io.github.mfabisiak.darwinworld.viewmodel.SimulationViewModel

@Composable
fun AnimationScreen(config : SimulationConfig){

    val viewModel = remember { SimulationViewModel(config) }

    var isRunning by remember { mutableStateOf(true) }

    val simulationState by viewModel.simulationState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.start()
    }

    Column {
        Row {
            Button(enabled = !isRunning, onClick = { viewModel.previous() }) { Text("Poprzedni") }
            Button(onClick = {
                if (isRunning) viewModel.stop() else viewModel.start()
                isRunning = !isRunning
            }) {
                Text(if (isRunning) "STOP" else "START")
            }
            Button(enabled = !isRunning, onClick = { viewModel.next() }) { Text("Następny") }
        }
        MapVisualizer(simulationState.worldMap, config.upperBound.y + 1, config.upperBound.x + 1)
    }

}
