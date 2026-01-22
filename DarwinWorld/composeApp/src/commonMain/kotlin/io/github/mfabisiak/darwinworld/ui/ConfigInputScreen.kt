package io.github.mfabisiak.darwinworld.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.mfabisiak.darwinworld.config.ProductionConfig
import io.github.mfabisiak.darwinworld.logic.config.SimulationConfig
import io.github.mfabisiak.darwinworld.ui.components.AnimalSection
import io.github.mfabisiak.darwinworld.ui.components.MapSection
import io.github.mfabisiak.darwinworld.ui.components.SimulationSection

@Composable
fun ConfigInputScreen(
    onSimulationStart: (SimulationConfig) -> Unit
) {
    var config by remember { mutableStateOf(ProductionConfig()) }

    BoxWithConstraints(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 32.dp, vertical = 16.dp),
        ){
        val isWideScreen = this.maxWidth > 750.dp

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.weight(1f)) {
                if (isWideScreen) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),

                        ) {
                        Box(modifier = Modifier.weight(1f)) { MapSection(config) { config = it } }

                        Box(modifier = Modifier.weight(1f)) { SimulationSection(config) { config = it } }

                        Box(modifier = Modifier.weight(1f)) { AnimalSection(config) { config = it } }
                    }

                }
                else{
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        MapSection(config) { config = it }
                        SimulationSection(config) { config = it }
                        AnimalSection(config) { config = it }
                    }

                }

            }

            Button(
                onClick = { onSimulationStart(config) }
            ) {
                Text("Uruchom Aplikacje")
            }
        }
    }
}


