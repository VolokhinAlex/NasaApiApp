package com.volokhinaleksey.materialdesign.ui.theme_state

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun rememberThemeState(
    themeState: Boolean = isSystemInDarkTheme(),
): ThemeState {
    return rememberSaveable(saver = ThemeStateSaver) {
        ThemeState(themeState = themeState)
    }
}