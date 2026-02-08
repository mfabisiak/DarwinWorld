package io.github.mfabisiak.darwinworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.mfabisiak.darwinworld.ui.simulation.AnimationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val activeSimulation = SimulationNavigation.currentConfig

            if (activeSimulation != null) {
                BackHandler {
                    SimulationNavigation.close()
                }

                AnimationScreen(activeSimulation)

            } else {
                App()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}