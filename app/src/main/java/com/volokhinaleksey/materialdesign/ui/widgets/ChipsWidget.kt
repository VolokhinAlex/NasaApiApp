package com.volokhinaleksey.materialdesign.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.volokhinaleksey.materialdesign.ui.theme.PrimaryDark
import com.volokhinaleksey.materialdesign.ui.theme.Purple40

@Composable
fun Chip(
    title: String,
    selected: String?,
    onSelected: (String) -> Unit
) {
    val isSelected = selected == title
    AssistChip(
        onClick = { onSelected(title) },
        leadingIcon = {
            AnimatedVisibility(visible = isSelected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "check",
                    tint = Color.White
                )
            }
        },
        label = { Text(text = title, fontSize = 16.sp) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (isSelected) Purple40 else PrimaryDark,
            labelColor = if (isSelected) Color.White else Color.Black
        ),
        modifier = Modifier.padding(5.dp)
    )
}