package com.volokhinaleksey.materialdesign.ui.theme_state

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberThemeState(
    themeState: Boolean = isSystemInDarkTheme(),
): ThemeState {
    return remember {
        ThemeState(
            themeState = themeState
        )
    }
}