package io.github.mfabisiak.darwinworld.files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.io.FilenameFilter

@Composable
actual fun rememberFileLoader(): FileLoader {
    return remember { JvmFileOpener() }
}

class JvmFileOpener : FileLoader {
    override fun openFile(extension: String, onFileRead: (String) -> Unit) {
        val dialog = FileDialog(null as Frame?, "Wybierz plik", FileDialog.LOAD)

        dialog.filenameFilter = FilenameFilter { _, name ->
            name.endsWith(".$extension", ignoreCase = true)
        }


        dialog.isVisible = true

        if (dialog.file != null) {
            val file = File(dialog.directory + dialog.file)
            val content = file.readText()
            onFileRead(content)
        }
    }
}