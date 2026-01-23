package io.github.mfabisiak.darwinworld.ui.config

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.mfabisiak.darwinworld.config.ConfigBuilder
import io.github.mfabisiak.darwinworld.ui.config.components.AnimalSection
import io.github.mfabisiak.darwinworld.ui.config.components.MapSection
import io.github.mfabisiak.darwinworld.ui.config.components.SimulationSection
import io.github.mfabisiak.darwinworld.ui.utils.launchSimulation

@Composable
fun ConfigInputScreen() {
    val config = remember { ConfigBuilder() }

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
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),

                        ) {
                        Box(modifier = Modifier.weight(1f)) { MapSection(config) }

                        Box(modifier = Modifier.weight(1f)) { SimulationSection(config) }

                        Box(modifier = Modifier.weight(1f)) { AnimalSection(config) }
                    }

                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        MapSection(config)
                        SimulationSection(config)
                        AnimalSection(config)
                    }

                }

            }

            Button(
                onClick = { launchSimulation(config) }
            ) {
                Text("Uruchom Aplikację")
            }
        }
    }
}


