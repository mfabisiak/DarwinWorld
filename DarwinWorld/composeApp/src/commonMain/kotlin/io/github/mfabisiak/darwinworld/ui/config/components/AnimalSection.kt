package io.github.mfabisiak.darwinworld.ui.config.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.mfabisiak.darwinworld.config.ConfigBuilder

@Composable
fun AnimalSection(config: ConfigBuilder)
{
    Column(modifier = Modifier) {
        Text(
            "Zwierzęta",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        ConfigInput("Energia z którą startują zwierzaki:", config.initialEnergy) {
            config.initialEnergy = it
        }

        ConfigInput("Energia tracona każdego dnia:", config.energyConsumedEachDay) {
            config.energyConsumedEachDay = it
        }

        ConfigInput("Energia potrzebna do rozmnażania:", config.energyRequiredToBreed) {
            config.energyRequiredToBreed = it
        }

        ConfigInput("Energia przekazywana dziecku:", config.energyGivenToNewborn) {
            config.energyGivenToNewborn = it
        }

        ConfigInput("Minimalna liczba mutacji:", config.minNumberOfMutations) {
            config.minNumberOfMutations = it
        }

        ConfigInput("Maksymalna liczba mutacji:", config.maxNumberOfMutations) {
            config.maxNumberOfMutations = it
        }

        ConfigInput("Długość genotypu:", config.genotypeSize) {
            config.genotypeSize = it
        }

        ConfigSwitch("Wariant Szybkie zwierzaki:", config.fastAnimalsEnabled) {
            config.fastAnimalsEnabled = it
            if (!it) config.maxRange = 1
        }

        ConfigInput(
            "Energia wymagana do szybkiego ruchu:",
            config.energyRequiredToMoveFast,
            config.fastAnimalsEnabled
        ) {
            config.energyRequiredToMoveFast = it
        }

        ConfigInput(
            "Energia do przyspieszenia o kolejny poziom:",
            config.energyPerExtraStep,
            config.fastAnimalsEnabled
        ) {
            config.energyPerExtraStep = it
        }

        ConfigInput(
            "Maksymalny zasięg szybkiego ruchu:",
            config.maxRange,
            config.fastAnimalsEnabled
        ) {
            config.maxRange = it
        }
    }
}