package io.github.mfabisiak.darwinworld.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.mfabisiak.darwinworld.config.ProductionConfig


@Composable
fun SimulationSection(config: ProductionConfig, onConfigChange: (ProductionConfig) -> Unit) {
    Column(
        modifier = Modifier

    ) {
        Text(
            "Symulacja",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        ConfigInput("Liczba roślin:", config.numberOfPlants) {
            onConfigChange(config.copy(numberOfPlants = it))
        }

        ConfigInput("Liczba roślin rosnących codziennie:", config.plantsGrowingEachDay) {
            onConfigChange(config.copy(plantsGrowingEachDay = it))
        }

        ConfigInput("Liczba zwierząt:", config.numberOfAnimals) {
            onConfigChange(config.copy(numberOfAnimals = it))
        }
    }
}

