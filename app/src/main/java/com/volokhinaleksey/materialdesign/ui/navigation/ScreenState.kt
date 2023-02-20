package com.volokhinaleksey.materialdesign.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.volokhinaleksey.materialdesign.R

sealed class ScreenState(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val icon: Int?
) {
    object PictureOfDayScreen : ScreenState(
        route = "picture_of_day_screen",
        label = R.string.picture_of_day_screen,
        icon = R.drawable.ic_telescope
    )

    object SettingsScreen : ScreenState(
        route = "settings_screen",
        label = R.string.settings_screen,
        icon = R.drawable.baseline_settings_24
    )

    object MarsPhotosScreen : ScreenState(
        route = "mars_photos_screen",
        label = R.string.mars_photos_screen,
        icon = R.drawable.ic_mars
    )

    object FullSizeImageScreen : ScreenState(
        route = "full_size_image_screen",
        label = R.string.full_size_image_screen,
        icon = null
    )

    object TasksScreen : ScreenState(
        route = "tasks_screen",
        label = R.string.tasks_screen,
        icon = R.drawable.baseline_edit_note_24
    )
}
