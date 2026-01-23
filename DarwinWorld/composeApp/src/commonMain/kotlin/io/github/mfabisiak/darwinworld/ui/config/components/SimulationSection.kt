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
        ConfigInput(
            "Liczba roślin:", config.numberOfPlants,
            condition = { it >= 0 }) {
            config.numberOfPlants = it
        }

        ConfigInput(
            "Liczba roślin rosnących codziennie:", config.plantsGrowingEachDay,
            condition = { it >= 0 }) {
            config.plantsGrowingEachDay = it
        }

        ConfigInput(
            "Liczba zwierząt:", config.numberOfAnimals,
            condition = { it >= 0 && it <= config.mapWidth * config.mapHeight }) {
            config.numberOfAnimals = it
        }

        ConfigInput(
            "Seed losowania", config.randomSeed,
            condition = { it > 0 }) {
            config.randomSeed = it
        }
    }
}

