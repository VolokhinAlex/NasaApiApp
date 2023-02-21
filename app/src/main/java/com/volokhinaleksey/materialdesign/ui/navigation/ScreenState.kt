package com.volokhinaleksey.materialdesign.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.volokhinaleksey.materialdesign.R

/**
 * The class is needed for easy navigation between screens
 * @param route - Needed to determine the screen for navigation
 * @param label - Needed to display the screen name in the bottom navigation bar. The value as String Res
 * @param icon - Needed to display the screen icon in the bottom navigation bar. The value as Drawable Res
 */

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

    object SplashScreen : ScreenState(
        route = "splash_screen",
        label = R.string.splash_screen,
        icon = null
    )
}
