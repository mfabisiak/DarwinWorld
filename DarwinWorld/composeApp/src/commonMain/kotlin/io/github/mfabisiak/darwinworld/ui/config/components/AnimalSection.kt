package io.github.mfabisiak.darwinworld.ui.config.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.mfabisiak.darwinworld.config.ConfigBuilder
import kotlin.math.max
import kotlin.math.min

@Composable
fun AnimalSection(config: ConfigBuilder)
{
    Column(modifier = Modifier) {
        Text(
            "Zwierzęta",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        ConfigInput(
            "Energia z którą startują zwierzaki:", config.initialEnergy,
            condition = { it > 0 }) {
            config.initialEnergy = it
        }

        ConfigInput(
            "Energia tracona każdego dnia:", config.energyConsumedEachDay,
            condition = { it > 0 }) {
            config.energyConsumedEachDay = it
        }

        ConfigInput(
            "Energia potrzebna do rozmnażania:", config.energyRequiredToBreed,
            condition = { it > 0 }) {
            config.energyRequiredToBreed = it
        }

        ConfigInput(
            "Energia przekazywana dziecku:", config.energyGivenToNewborn,
            condition = { it > 0 }) {
            config.energyGivenToNewborn = it
        }

        ConfigInput(
            "Minimalna liczba mutacji:", config.minNumberOfMutations,
            condition = { it >= 0 }) {
            config.minNumberOfMutations = it
            config.maxNumberOfMutations = max(it, config.maxNumberOfMutations)
        }

        ConfigInput(
            "Maksymalna liczba mutacji:", config.maxNumberOfMutations,
            condition = { it >= 0 }) {
            config.maxNumberOfMutations = it
            config.minNumberOfMutations = min(it, config.minNumberOfMutations)
        }

        ConfigInput(
            "Długość genotypu:", config.genotypeSize,
            condition = { it > 0 }) {
            config.genotypeSize = it
        }

        ConfigSwitch("Wariant Szybkie zwierzaki:", config.fastAnimalsEnabled) {
            config.fastAnimalsEnabled = it
            if (!it) config.maxRange = 1
        }

        ConfigInput(
            "Energia wymagana do szybkiego ruchu:",
            config.energyRequiredToMoveFast,
            config.fastAnimalsEnabled,
            condition = { it > 0 }
        ) {
            config.energyRequiredToMoveFast = it
        }

        ConfigInput(
            "Energia do przyspieszenia o kolejny poziom:",
            config.energyPerExtraStep,
            config.fastAnimalsEnabled,
            condition = { it > 0 }
        ) {
            config.energyPerExtraStep = it
        }

        ConfigInput(
            "Maksymalny zasięg szybkiego ruchu:",
            config.maxRange,
            config.fastAnimalsEnabled,
            condition = { it > 0 }
        ) {
            config.maxRange = it
        }
    }
}