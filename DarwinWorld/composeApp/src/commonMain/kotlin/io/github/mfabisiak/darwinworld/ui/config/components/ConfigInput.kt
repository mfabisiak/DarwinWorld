package io.github.mfabisiak.darwinworld.ui.config.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ConfigInput(
    label: String,
    value: Int,
    isVisible: Boolean = true,
    condition: (Int) -> Boolean = { _ -> true },
    sendOnChange: (Int) -> Unit,
){
    if (isVisible) OutlinedTextField(
        value = value.toString(),
        onValueChange = { text ->
            text.toIntOrNull()
                ?.let {
                    if (condition(it)) sendOnChange(it)
                    else sendOnChange(value)
                }

        },
        label = { Text(label) },
        placeholder = { Text("Wpisz wartość")},
    )
}
