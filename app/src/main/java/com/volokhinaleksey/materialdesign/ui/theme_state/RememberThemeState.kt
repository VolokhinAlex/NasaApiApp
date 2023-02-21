package com.volokhinaleksey.materialdesign.ui.theme_state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun rememberThemeState(
): ThemeState {
    return rememberSaveable(saver = ThemeStateSaver) {
        ThemeState(themeState = false)
    }
}