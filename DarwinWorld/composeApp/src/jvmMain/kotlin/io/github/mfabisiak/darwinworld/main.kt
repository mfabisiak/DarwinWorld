package io.github.mfabisiak.darwinworld

import androidx.compose.runtime.key
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.mfabisiak.darwinworld.ui.simulation.AnimationScreen
import io.github.mfabisiak.darwinworld.ui.utils.WindowManager

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "darwinworld",
    ) {
        App()
    }

    for (config in WindowManager.openWindows) {
        key(config) {
            Window(title = "Simulation", onCloseRequest = { WindowManager.closeSimulation(config) }) {
                AnimationScreen(config)
            }
        }
    }
}