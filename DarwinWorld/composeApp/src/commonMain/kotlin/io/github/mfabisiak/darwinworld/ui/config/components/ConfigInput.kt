package io.github.mfabisiak.darwinworld.ui.config.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ConfigInput(
    label: String,
    value: Int,
    isVisible: Boolean = true,
    sendOnChange: (Int) -> Unit,
){
    if (isVisible) OutlinedTextField(
        value = value.toString(),
        onValueChange = { text ->
            val digits = text.filter { it.isDigit() }
            sendOnChange(digits.toIntOrNull() ?: 0)
        },
        label = { Text(label) },
        placeholder = { Text("Wpisz wartość")},
    )
}
