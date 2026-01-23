package io.github.mfabisiak.darwinworld.ui.config.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.mfabisiak.darwinworld.config.ConfigBuilder

@Composable
fun MapSection(config: ConfigBuilder) {
    Column(modifier = Modifier) {
        Text("Mapa",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        ConfigInput(
            "Szerokość mapy:", config.mapWidth,
            condition = { it in 3..100 }) {
            config.mapWidth = it
        }

        ConfigInput(
            "Wysokość mapy:", config.mapHeight,
            condition = { it in 3..100 }) {
            config.mapHeight = it
        }

        ConfigInput(
            "Energia ze zjedzenia pojedyńczej rośliny:", config.energyFromSinglePlant,
            condition = { it > 0 }) {
            config.energyFromSinglePlant = it
        }
    }
}