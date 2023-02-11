package com.volokhinaleksey.materialdesign.ui.theme_state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ThemeState(
    themeState: Boolean,
) {
    var state by mutableStateOf(themeState)
}