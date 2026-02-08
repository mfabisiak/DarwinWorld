package io.github.mfabisiak.darwinworld.ui.simulation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.mfabisiak.darwinworld.files.FileSaver
import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig
import io.github.mfabisiak.darwinworld.statistics.DayStatistics
import io.github.mfabisiak.darwinworld.viewmodel.SimulationViewModel

@Composable
fun StatisticsContent(
    stats: DayStatistics,
    config: SimulationConfig,
    fileSaver: FileSaver,
    viewModel: SimulationViewModel
) {
    Text("Dzień: ${stats.currentDay}")
    Text("Zwierzaki: ${stats.totalAnimals}")
    Text("Rośliny: ${stats.totalPlants}")
    Text("Wolne pola: ${stats.freeAreas}")
    Text("Średnia Energia: ${stats.energy}")
    Text("Średnia długość życia: ${stats.age}")
    Text("Średnia ilość Dzieci: ${stats.children}")
    Text("Genotypy: ${stats.popularGenotypes}")

    Spacer(modifier = Modifier.height(8.dp))

    Button(
        onClick = {
            fileSaver.save(
                "Statistics-${config.id}",
                "csv",
                viewModel.getStatisticsCsv()
            )
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Zapisz statystyki do CSV")
    }
}