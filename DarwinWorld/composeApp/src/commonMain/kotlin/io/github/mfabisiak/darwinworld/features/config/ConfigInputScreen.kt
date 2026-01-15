package io.github.mfabisiak.darwinworld.features.config

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.mfabisiak.darwinworld.config.SimulationConfig

@Composable
fun ConfigInputScreen(
    onSimulationStart: (SimulationConfig) -> Unit
){
    var config by remember { mutableStateOf(AppConfig(
        mapWidth = 0, mapHeight = 0, numberOfPlants = 0,
        plantsGrowingEachDay = 0, numberOfAnimals = 0,
        energyFromSinglePlant = 0, energyConsumedEachDay = 0,
        energyRequiredToBreed = 0, energyGivenToNewborn = 0,
        minNumberOfMutations = 0, maxNumberOfMutations = 0,
        genotypeSize = 0
    )) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        Text("Konfiguracja")

        ConfigInput("Szerokość mapy:", config.mapWidth){
            config = config.copy(mapWidth = it)
        }

        ConfigInput("Wysokość mapy:", config.mapHeight){
            config = config.copy(mapHeight = it)
        }

        ConfigInput("Liczba roślin:", config.numberOfPlants){
            config = config.copy(numberOfPlants = it)
        }

        ConfigInput("Liczba roślin rosnących codziennie:", config.plantsGrowingEachDay){
            config = config.copy(plantsGrowingEachDay = it)
        }

        ConfigInput("Liczba zwierząt:", config.numberOfAnimals){
            config = config.copy(numberOfAnimals = it)
        }

        ConfigInput("Energia ze zjedzenia pojedyńczej rośliny:", config.energyFromSinglePlant){
            config = config.copy(energyFromSinglePlant = it)
        }

        ConfigInput("Energia tracona każdego dnia:", config.energyConsumedEachDay){
            config = config.copy(energyConsumedEachDay = it)
        }

        ConfigInput("Energia potrzebna do rozmnażania:", config.energyRequiredToBreed){
            config = config.copy(energyRequiredToBreed = it)
        }

        ConfigInput("Energia przekazywana dziecku:", config.energyGivenToNewborn){
            config = config.copy(energyGivenToNewborn = it)
        }

        ConfigInput("Minimalna liczba mutacji:", config.minNumberOfMutations){
            config = config.copy(minNumberOfMutations = it)
        }

        ConfigInput("Maksymalna liczba mutacji:", config.maxNumberOfMutations){
            config = config.copy(maxNumberOfMutations = it)
        }

        ConfigInput("Długość genotypu:", config.genotypeSize){
            config = config.copy(genotypeSize = it)
        }
    }
    Button(
        onClick = { onSimulationStart(config) }
    ){
        Text("Uruchom Aplikacje")
    }
}


