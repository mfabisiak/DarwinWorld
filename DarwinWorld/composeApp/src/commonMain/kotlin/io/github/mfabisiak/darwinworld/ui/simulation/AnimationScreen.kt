package io.github.mfabisiak.darwinworld.ui.simulation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig
import io.github.mfabisiak.darwinworld.statistics.CalculateStatistics
import io.github.mfabisiak.darwinworld.statistics.createStatisticsSaver
import io.github.mfabisiak.darwinworld.ui.simulation.components.MapVisualizer
import io.github.mfabisiak.darwinworld.viewmodel.SimulationViewModel

@Composable
fun AnimationScreen(config: SimulationConfig) {

    val saver = remember { createStatisticsSaver() }

    val viewModel = remember { SimulationViewModel(config, saver) }

    var isRunning by remember { mutableStateOf(true) }

    val simulationState by viewModel.simulationState.collectAsState()

    val stats = CalculateStatistics(simulationState)

    LaunchedEffect(Unit) {
        viewModel.start()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
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
        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                MapVisualizer(
                    simulationState.worldMap,
                    config.upperBound.y + 1,
                    config.upperBound.x + 1,
                    stats.topGenotype
                )
            }

            Column(
                modifier = Modifier
                    .width(250.dp)
            ) {
                Text("Dzień: ${stats.currentDay}")
                Text("Zwierzaki: ${stats.totalAnimals}")
                Text("Rośliny: ${stats.totalPlants}")
                Text("Wolne pola: ${stats.freeAreas}")
                Text("Średnia Energia: ${stats.energy}")
                Text("Średnia długość życia: ${stats.age}")
                Text("Średnia ilość Dzieci: ${stats.children}")
                Text("Genotypy: ${stats.popularGenotypes}")
            }
        }
    }

}
