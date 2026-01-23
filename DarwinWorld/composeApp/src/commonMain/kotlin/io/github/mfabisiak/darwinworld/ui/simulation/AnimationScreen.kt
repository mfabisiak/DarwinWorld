package io.github.mfabisiak.darwinworld.ui.simulation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.mfabisiak.darwinworld.files.rememberFileSaver
import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig
import io.github.mfabisiak.darwinworld.statistics.getDayStatistics
import io.github.mfabisiak.darwinworld.ui.simulation.components.MapVisualizer
import io.github.mfabisiak.darwinworld.ui.simulation.components.StatisticsContent
import io.github.mfabisiak.darwinworld.viewmodel.SimulationViewModel

@Composable
fun AnimationScreen(config: SimulationConfig) {

    val fileSaver = rememberFileSaver()

    val viewModel = remember { SimulationViewModel(config) }

    var isRunning by remember { mutableStateOf(true) }

    val simulationState by viewModel.simulationState.collectAsState()

    val stats = getDayStatistics(simulationState)



    LaunchedEffect(isRunning) {
        if (isRunning) {
            viewModel.simulate()
        }
    }


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row {
            Button(
                enabled = !isRunning && viewModel.hasPreviousState(),
                onClick = { viewModel.previous() }) {
                Text("Poprzedni")
            }

            Button(onClick = { isRunning = !isRunning }) {
                Text(if (isRunning) "STOP" else "START")
            }
            Button(enabled = !isRunning, onClick = { viewModel.next() }) { Text("Następny") }
        }
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val isWideScreen = maxWidth > 600.dp

            if (isWideScreen) {
                Row(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                        MapVisualizer(
                            simulationState.worldMap,
                            config.upperBound.y + 1,
                            config.upperBound.x + 1
                        )
                    }

                    Column(
                        modifier = Modifier
                            .width(250.dp)
                            .fillMaxHeight()
                            .padding(start = 16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center
                    ) {
                        StatisticsContent(stats, config, fileSaver, viewModel)
                    }
                }
            } else {
                Column(
                    Modifier.fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 16.dp)
                ) {
                    Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                        MapVisualizer(
                            simulationState.worldMap,
                            config.upperBound.y + 1,
                            config.upperBound.x + 1
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                    ) {
                        StatisticsContent(stats, config, fileSaver, viewModel)
                    }
                }
            }
        }
    }

}


