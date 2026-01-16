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
fun MapSection(config: ProductionConfig, onConfigChange: (ProductionConfig) -> Unit){
    Column(
        modifier = Modifier

    ) {
        Text("Mapa",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        ConfigInput("Szerokość mapy:", config.upperBound.x + 1) { newWidth ->
            onConfigChange(config.copy(upperBound = config.upperBound.copy( x = newWidth - 1)))
        }

        ConfigInput("Wysokość mapy:", config.upperBound.y + 1) { newHeight ->
            onConfigChange(config.copy(upperBound = config.upperBound.copy( y = newHeight - 1)))
        }

        ConfigInput("Energia ze zjedzenia pojedyńczej rośliny:", config.energyFromSinglePlant) {
            onConfigChange(config.copy(energyFromSinglePlant = it))
        }
    }
}