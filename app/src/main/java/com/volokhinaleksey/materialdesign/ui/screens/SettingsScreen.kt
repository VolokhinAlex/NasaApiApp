package com.volokhinaleksey.materialdesign.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.volokhinaleksey.materialdesign.ui.theme_state.ThemeState


@Composable
fun SettingsScreen(themeState: ThemeState) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(text = "Включить темную тему", fontSize = 20.sp)
        Switch(checked = themeState.state, onCheckedChange = { themeState.state = it })
    }

}