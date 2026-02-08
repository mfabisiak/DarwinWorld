package io.github.mfabisiak.darwinworld.files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.browser.document
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.url.URL
import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.toJsArray
import kotlin.js.toJsString


@OptIn(ExperimentalWasmJsInterop::class)
@Composable
actual fun rememberFileSaver(): FileSaver {
    return remember {
        object : FileSaver {
            override fun save(name: String, extension: String, content: String) {
                val blob = Blob(
                    listOf(content.toJsString()).toJsArray(),
                    BlobPropertyBag(type = "application/json")
                )

                val url = URL.createObjectURL(blob)

                val link = document.createElement("a") as HTMLAnchorElement
                link.href = url
                link.download = "$name.$extension"

                document.body?.appendChild(link)
                link.click()
                document.body?.removeChild(link)
            }
        }
    }
}