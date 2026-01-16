package io.github.mfabisiak.darwinworld.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.mfabisiak.darwinworld.config.ProductionConfig
import io.github.mfabisiak.darwinworld.config.SimulationConfig
import io.github.mfabisiak.darwinworld.ui.components.ConfigInput
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


