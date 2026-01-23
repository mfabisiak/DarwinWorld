package io.github.mfabisiak.darwinworld.files

import androidx.compose.runtime.Composable

interface FileLoader {
    fun openFile(extension: String, onFileRead: (String) -> Unit)
}

@Composable
expect fun rememberFileLoader(): FileLoader