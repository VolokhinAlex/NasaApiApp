package com.volokhinaleksey.materialdesign

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.volokhinaleksey.materialdesign.ui.images.ImageLoader
import com.volokhinaleksey.materialdesign.ui.navigation.ScreenState
import com.volokhinaleksey.materialdesign.ui.screens.*
import com.volokhinaleksey.materialdesign.ui.screens.big_image.FullSizeImage
import com.volokhinaleksey.materialdesign.ui.screens.mars_photos.MarsPhotosScreen
import com.volokhinaleksey.materialdesign.ui.screens.picture_of_day.PictureOfTheDayScreen
import com.volokhinaleksey.materialdesign.ui.screens.settings.SettingsScreen
import com.volokhinaleksey.materialdesign.ui.screens.splash.SplashScreen
import com.volokhinaleksey.materialdesign.ui.screens.tasks.TasksScreen
import com.volokhinaleksey.materialdesign.ui.theme.MaterialDesignTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val modeThemeState = booleanPreferencesKey("mode_theme_state")

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var dataStorePreferences: DataStore<Preferences>

    private val getThemeMode: Flow<Boolean> by lazy {
        dataStorePreferences.data
            .map { preferences ->
                preferences[modeThemeState] ?: false
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val isDarkMode by getThemeMode.collectAsState(initial = false)

            MaterialDesignTheme(darkTheme = if (isSystemInDarkTheme()) true else isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(
                        imageLoader = imageLoader,
                        dataStorePreferences = dataStorePreferences
                    )
                }
            }

        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    imageLoader: ImageLoader,
    dataStorePreferences: DataStore<Preferences>
) {

    val navController = rememberAnimatedNavController()

    AppBottomBar(navController = navController) {
        AnimatedNavHost(
            navController = navController,
            startDestination = ScreenState.SplashScreen.route,
            modifier = Modifier.padding(it)
        ) {

            composable(route = ScreenState.PictureOfDayScreen.route, enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            }, exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            }) {
                PictureOfTheDayScreen(imageLoader = imageLoader)
            }

            composable(route = ScreenState.SettingsScreen.route, enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            }, exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            }) {
                SettingsScreen(dataStore = dataStorePreferences)
            }

            composable(route = ScreenState.MarsPhotosScreen.route, enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            }, exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            }) {
                MarsPhotosScreen(navController = navController, imageLoader = imageLoader)
            }

            composable(route = ScreenState.FullSizeImageScreen.route,
                enterTransition = {
                    slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Start,
                        animationSpec = tween(700)
                    )
                }, exitTransition = {
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.End,
                        animationSpec = tween(700)
                    )
                }) { content ->
                val imageUrl = content.arguments?.getString("FullSizeImage")
                imageUrl?.let { url ->
                    FullSizeImage(
                        imageUrl = url,
                        navController = navController
                    )
                }
            }

            composable(route = ScreenState.TasksScreen.route) {
                TasksScreen()
            }

            composable(route = ScreenState.SplashScreen.route) {
                SplashScreen(navController = navController)
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomBar(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {

    val bottomNavItems =
        listOf(
            ScreenState.PictureOfDayScreen,
            ScreenState.SettingsScreen,
            ScreenState.MarsPhotosScreen,
            ScreenState.TasksScreen
        )

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(bottomBar = {
        if (currentRoute != ScreenState.SplashScreen.route &&
            currentRoute != ScreenState.FullSizeImageScreen.route
        ) {
            NavigationBar(containerColor = MaterialTheme.colorScheme.onSecondary) {
                bottomNavItems.forEach { item ->
                    val selected = item.route == currentRoute
                    val iconNavItem = item.icon?.let { ImageVector.vectorResource(id = it) }
                    NavigationBarItem(
                        selected = selected,
                        onClick = { navController.navigate(item.route) },
                        icon = {
                            iconNavItem?.let {
                                Icon(
                                    imageVector = it,
                                    contentDescription = iconNavItem.name
                                )
                            }
                        }
                    )
                }
            }
        }
    }, content = content)

}




