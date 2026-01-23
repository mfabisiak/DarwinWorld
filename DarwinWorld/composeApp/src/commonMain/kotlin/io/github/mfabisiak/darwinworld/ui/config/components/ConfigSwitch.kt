package io.github.mfabisiak.darwinworld.ui.config.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun ConfigSwitch(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .toggleable(checked, onValueChange = onChange, role = Role.Switch)
            .padding(8.dp)
    ) {
        Text(label)
        Spacer(Modifier.width(2.dp))
        Switch(checked = checked, onCheckedChange = null)
    }
}