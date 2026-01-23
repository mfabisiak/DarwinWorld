package io.github.mfabisiak.darwinworld.ui.simulation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import darwinworld.composeapp.generated.resources.*
import io.github.mfabisiak.darwinworld.logic.model.animal.Animal
import io.github.mfabisiak.darwinworld.logic.model.animal.Genotype
import io.github.mfabisiak.darwinworld.logic.model.map.WorldMap
import org.jetbrains.compose.resources.painterResource
import kotlin.math.max
import kotlin.math.min


@Composable
fun MapVisualizer(worldMap: WorldMap, height: Int, width: Int, topGenotype: Genotype? = null) {

    fun calculateColor(animal: Animal): Color = when {
        animal.energy > 8 * worldMap.config.energyConsumedEachDay -> Color.hsl(120f, 0.65f, 0.25f)
        animal.energy > 6 * worldMap.config.energyConsumedEachDay -> Color.Green
        animal.energy > 4 * worldMap.config.energyConsumedEachDay -> Color.hsl(45f, 0.8f, 0.4f)
        animal.energy > 2 * worldMap.config.energyConsumedEachDay -> Color.hsl(55f, 0.9f, 0.65f)
        else -> Color.Red
    }

    val animalDrawables = listOf(
        painterResource(Res.drawable.animal_0),
        painterResource(Res.drawable.animal_45),
        painterResource(Res.drawable.animal_90),
        painterResource(Res.drawable.animal_135),
        painterResource(Res.drawable.animal_180),
        painterResource(Res.drawable.animal_225),
        painterResource(Res.drawable.animal_270),
        painterResource(Res.drawable.animal_315)
    )
    val plantDrawable = painterResource(Res.drawable.plant)

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        val cellSizeDp = min(maxWidth / width, maxHeight / height)
        val canvasWidth = cellSizeDp * width
        val canvasHeight = cellSizeDp * height

        Canvas(modifier = Modifier.size(canvasWidth, canvasHeight)) {
            val cellWidthPx = size.width / width
            val cellHeightPx = size.height / height
            val cellSizePx = Size(cellWidthPx, cellHeightPx)

            drawRect(color = Color(0xFFF1F8E9))

            val jungleBounds = worldMap.config.jungle
            val jungleX = jungleBounds.start.x * cellWidthPx
            val jungleY = jungleBounds.start.y * cellHeightPx
            val jungleWidth = (jungleBounds.end.x - jungleBounds.start.x + 1) * cellWidthPx
            val jungleHeight = (jungleBounds.end.y - jungleBounds.start.y + 1) * cellHeightPx

            drawRect(
                color = Color(0xFF2E7D32),
                topLeft = Offset(jungleX, jungleY),
                size = Size(jungleWidth, jungleHeight)
            )

            for (i in 0..width) {
                drawLine(
                    Color.Gray,
                    Offset(i * cellWidthPx, 0f),
                    Offset(i * cellWidthPx, size.height),
                    strokeWidth = 1f
                )
            }
            for (i in 0..height) {
                drawLine(
                    Color.Gray,
                    Offset(0f, i * cellHeightPx),
                    Offset(size.width, i * cellHeightPx),
                    strokeWidth = 1f
                )
            }

            for (plantPos in worldMap.plants) {

                val plantSizePx = cellSizePx * 0.65f

                val x = plantPos.x * cellWidthPx
                val y = plantPos.y * cellHeightPx

                translate(left = x + (cellWidthPx * (1 - 0.8f)), top = y + (cellHeightPx * (1 - 0.8f))) {
                    with(plantDrawable) {
                        draw(size = plantSizePx)
                    }
                }
            }

            for (animal in worldMap.animals.values) {
                val x = animal.position.x * cellWidthPx
                val y = animal.position.y * cellHeightPx

                if (animal.genotype.genes.actualList == topGenotype?.genes?.actualList) {
                    drawRect(
                        color = Color.Yellow.copy(alpha = 0.5f),
                        topLeft = Offset(x, y),
                        size = cellSizePx
                    )
                }
                val painterIndex = animal.direction.ordinal % animalDrawables.size
                val currentPainter = animalDrawables[painterIndex]

                translate(left = x, top = y) {
                    with(currentPainter) {
                        draw(size = Size(cellWidthPx, cellHeightPx))
                    }
                }
                val color = calculateColor(animal)
                val maxLength = 0.8f
                val length =
                    min(
                        max((animal.energy.toFloat() / (8f * worldMap.config.energyConsumedEachDay)) * maxLength, 0.1f),
                        maxLength
                    )

                drawRect(
                    color = color,
                    topLeft = Offset(x + cellWidthPx * 0.125f, y + cellHeightPx * 0.05f),
                    size = Size(cellWidthPx * length, cellHeightPx * 0.15f)
                )
            }
        }
    }
}