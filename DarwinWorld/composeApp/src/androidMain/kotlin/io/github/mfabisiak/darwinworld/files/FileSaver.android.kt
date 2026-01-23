package io.github.mfabisiak.darwinworld.files

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext


@Composable
actual fun rememberFileSaver(): FileSaver {
    val context = LocalContext.current

    return remember {
        object : FileSaver {
            override fun save(name: String, extension: String, content: String) {

                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, content)
                    type = "application/json"
                }

                val shareIntent = Intent.createChooser(sendIntent, "Eksportuj config")

                context.startActivity(shareIntent)
            }
        }
    }
}

