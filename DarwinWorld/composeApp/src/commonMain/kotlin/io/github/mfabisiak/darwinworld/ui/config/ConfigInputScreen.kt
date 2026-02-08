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
import io.github.mfabisiak.darwinworld.config.*
import io.github.mfabisiak.darwinworld.files.rememberFileLoader
import io.github.mfabisiak.darwinworld.files.rememberFileSaver
import io.github.mfabisiak.darwinworld.ui.config.components.AnimalSection
import io.github.mfabisiak.darwinworld.ui.config.components.MapSection
import io.github.mfabisiak.darwinworld.ui.config.components.SimulationSection
import io.github.mfabisiak.darwinworld.ui.utils.launchSimulation
import kotlinx.serialization.json.Json

@Composable
fun ConfigInputScreen() {
    val config = remember { ConfigBuilder() }
    val fileSaver = rememberFileSaver()
    val fileLoader = rememberFileLoader()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp),
    ) {
        val isWideScreen = this.maxWidth > 750.dp
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { config.fromSerializableConfig(CLASSIC_CONFIG) }) {
                    Text("Klasyczny")
                }

                Button(onClick = { config.fromSerializableConfig(DESERT_CONFIG) }) {
                    Text("Pustynia")
                }

                Button(onClick = { config.fromSerializableConfig(FULL_OF_PLANTS_CONFIG) }) {
                    Text("Dużo roślin")
                }

                Button(onClick = { config.fromSerializableConfig(FAST_ANIMALS_CONFIG) }) {
                    Text("Szybkie zwierzaki")
                }
            }

            Box(modifier = Modifier.weight(1f)) {
                if (isWideScreen) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
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
            FlowRow(modifier = Modifier.padding(10.dp), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = { launchSimulation(config) }
                ) {
                    Text("Uruchom Aplikację")
                }

                Button(onClick = {
                    fileSaver.save(
                        "Config", "json",
                        Json.encodeToString(config.toSerializableConfig())
                    )
                }) {
                    Text("Zapisz konfigurację")
                }

                Button(onClick = {
                    fileLoader.openFile("json") {
                        val loadedConfig = try {
                            Json.decodeFromString<SerializableConfig>(it)
                        } catch (_: Exception) {
                            return@openFile
                        }

                        config.fromSerializableConfig(loadedConfig)
                    }
                }) {
                    Text("Wczytaj konfigurację")
                }

            }
        }
    }
}


