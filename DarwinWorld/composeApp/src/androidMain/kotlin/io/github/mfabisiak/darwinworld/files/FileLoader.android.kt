package io.github.mfabisiak.darwinworld.files

import androidx.compose.runtime.Composable

@Composable
actual fun rememberFileLoader(): FileLoader = object : FileLoader {
    override fun openFile(extension: String, onFileRead: (String) -> Unit) {
    }

}