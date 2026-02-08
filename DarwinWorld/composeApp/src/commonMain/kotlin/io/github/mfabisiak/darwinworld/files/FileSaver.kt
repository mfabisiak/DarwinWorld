package io.github.mfabisiak.darwinworld.files

import androidx.compose.runtime.Composable

interface FileSaver {
    fun save(name: String, extension: String, content: String)
}

@Composable
expect fun rememberFileSaver(): FileSaver