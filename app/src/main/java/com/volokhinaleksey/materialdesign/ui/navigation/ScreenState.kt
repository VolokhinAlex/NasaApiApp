package com.volokhinaleksey.materialdesign.ui.navigation

sealed class ScreenState(val route: String) {
    object PictureOfDayScreen : ScreenState(route = "picture_of_day_screen")
    object AsteroidsNeoWsScreen : ScreenState(route = "asteroids_neo_ws_screen")
    object TechPortScreen : ScreenState(route = "tech_port_screen")
}
