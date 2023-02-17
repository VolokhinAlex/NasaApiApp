package com.volokhinaleksey.materialdesign.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Build
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.material.icons.sharp.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.volokhinaleksey.materialdesign.R

sealed class ScreenState(val route: String, @StringRes val label: Int, val icon: ImageVector) {
    object PictureOfDayScreen : ScreenState(
        route = "picture_of_day_screen",
        label = R.string.picture_of_day_screen,
        icon = Icons.Sharp.Home
    )

    object SettingsScreen : ScreenState(
        route = "settings_screen",
        label = R.string.settings_screen,
        icon = Icons.Sharp.Settings
    )

    object MarsPhotosScreen : ScreenState(
        route = "mars_photos_screen",
        label = R.string.mars_photos_screen,
        icon = Icons.Sharp.Star
    )
}
