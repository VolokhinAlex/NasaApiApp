package com.volokhinaleksey.materialdesign.ui.theme_state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue

class ThemeState(
    themeState: Boolean,
) {
    var state by mutableStateOf(themeState)
}

val ThemeStateSaver = Saver<ThemeState, Boolean>(
    save = { it.state },
    restore = { ThemeState(themeState = it) }
)