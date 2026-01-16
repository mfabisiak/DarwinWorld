package io.github.mfabisiak.darwinworld.ui.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ConfigInput(
    label: String,
    value: Int,
    sendOnChange: (Int) -> Unit
){
    OutlinedTextField(
        value = value.toString(),
        onValueChange = { text ->
            val digits = text.filter { it.isDigit() }
            sendOnChange(digits.toIntOrNull() ?: 0)
        },
        label = { Text(label) },
        placeholder = { Text("Wpisz wartość")},
    )
}
