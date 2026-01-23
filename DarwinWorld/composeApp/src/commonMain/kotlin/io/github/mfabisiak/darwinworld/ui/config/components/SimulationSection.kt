package io.github.mfabisiak.darwinworld.ui.config.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.mfabisiak.darwinworld.config.ConfigBuilder


@Composable
fun SimulationSection(config: ConfigBuilder) {
    Column(modifier = Modifier) {
        Text(
            "Symulacja",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        ConfigInput("Liczba roślin:", config.numberOfPlants) {
            config.numberOfPlants = it
        }

        ConfigInput("Liczba roślin rosnących codziennie:", config.plantsGrowingEachDay) {
            config.plantsGrowingEachDay = it
        }

        ConfigInput("Liczba zwierząt:", config.numberOfAnimals) {
            config.numberOfAnimals = it
        }

        ConfigInput("Seed losowania", config.randomSeed) {
            config.randomSeed = it
        }
    }
}

