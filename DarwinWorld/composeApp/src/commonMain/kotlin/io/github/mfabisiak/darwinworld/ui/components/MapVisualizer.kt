package io.github.mfabisiak.darwinworld.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import darwinworld.composeapp.generated.resources.Res
import darwinworld.composeapp.generated.resources.animal_0
import darwinworld.composeapp.generated.resources.animal_135
import darwinworld.composeapp.generated.resources.animal_180
import darwinworld.composeapp.generated.resources.animal_225
import darwinworld.composeapp.generated.resources.animal_270
import darwinworld.composeapp.generated.resources.animal_315
import darwinworld.composeapp.generated.resources.animal_45
import darwinworld.composeapp.generated.resources.animal_90
import io.github.mfabisiak.darwinworld.model.Direction
import io.github.mfabisiak.darwinworld.model.Position
import io.github.mfabisiak.darwinworld.model.map.WorldMap
import org.jetbrains.compose.resources.painterResource

@Composable
fun MapVisualizer(worldMap: WorldMap, height: Int, width: Int ){

    val animalDrawables =  listOf(
            painterResource(Res.drawable.animal_0),
            painterResource(Res.drawable.animal_45),
            painterResource(Res.drawable.animal_90),
            painterResource(Res.drawable.animal_135),
            painterResource(Res.drawable.animal_180),
            painterResource(Res.drawable.animal_225),
            painterResource(Res.drawable.animal_270),
            painterResource(Res.drawable.animal_315)
        )

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

            for (plantPos in worldMap.plants) {
                drawRect(
                    color = Color.Green,
                    topLeft = Offset(plantPos.x * cellWidthPx, plantPos.y * cellHeightPx),
                    size = cellSizePx
                )
            }
            for (animal in worldMap.animals.values) {
                val x = animal.position.x * cellWidthPx
                val y = animal.position.y * cellHeightPx

                val painterIndex = animal.direction.ordinal % animalDrawables.size
                val currentPainter = animalDrawables[painterIndex]

                translate(left = x, top = y) {
                    with(currentPainter) {
                        draw(size = Size(cellWidthPx, cellHeightPx))
                    }
                }
                for (i in 0..width) {
                    drawLine(Color.Gray, Offset(i * cellWidthPx, 0f), Offset(i * cellWidthPx, size.height))
                }
                for (i in 0..height) {
                    drawLine(Color.Gray, Offset(0f, i * cellHeightPx), Offset(size.width, i * cellHeightPx))
                }
            }


        }
    }
}