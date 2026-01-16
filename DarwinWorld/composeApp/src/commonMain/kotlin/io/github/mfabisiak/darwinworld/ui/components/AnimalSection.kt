package io.github.mfabisiak.darwinworld.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.mfabisiak.darwinworld.config.ProductionConfig

@Composable
fun AnimalSection(config: ProductionConfig, onConfigChange: (ProductionConfig) -> Unit)
{
    Column(
        modifier = Modifier

    ) {
        Text(
            "Zwierzęta",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        ConfigInput("Energia z którą startują zwierzaki:", config.initialEnergy) {
            onConfigChange(config.copy(initialEnergy = it))
        }

        ConfigInput("Energia tracona każdego dnia:", config.energyConsumedEachDay) {
            onConfigChange(config.copy(energyConsumedEachDay = it))
        }

        ConfigInput("Energia potrzebna do rozmnażania:", config.energyRequiredToBreed) {
            onConfigChange(config.copy(energyRequiredToBreed = it))
        }

        ConfigInput("Energia przekazywana dziecku:", config.energyGivenToNewborn) {
            onConfigChange(config.copy(energyGivenToNewborn = it))
        }

        ConfigInput("Minimalna liczba mutacji:", config.minNumberOfMutations) {
            onConfigChange(config.copy(minNumberOfMutations = it))
        }

        ConfigInput("Maksymalna liczba mutacji:", config.maxNumberOfMutations) {
            onConfigChange(config.copy(maxNumberOfMutations = it))
        }

        ConfigInput("Długość genotypu:", config.genotypeSize) {
            onConfigChange(config.copy(genotypeSize = it))
        }
    }
}