package io.github.mfabisiak.darwinworld.files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

@Composable
actual fun rememberFileSaver(): FileSaver {
    return remember {
        object : FileSaver {
            override fun save(name: String, extension: String, content: String) {
                val dialog = FileDialog(null as Frame?, "Zapisz symulację", FileDialog.SAVE)

                dialog.file = "$name.$extension"
                dialog.isVisible = true

                if (dialog.file != null) {
                    val fullPath = dialog.directory + dialog.file
                    val file = File(fullPath)
                    file.writeText(content)
                }
            }
        }
    }
}