package io.github.mfabisiak.darwinworld

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import io.github.mfabisiak.darwinworld.ui.config.ConfigInputScreen


@Composable
fun App() {
    MaterialTheme{
        ConfigInputScreen()
    }
}