package com.volokhinaleksey.materialdesign

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.volokhinaleksey.materialdesign.ui.images.ImageLoader
import com.volokhinaleksey.materialdesign.ui.navigation.ScreenState
import com.volokhinaleksey.materialdesign.ui.screens.*
import com.volokhinaleksey.materialdesign.ui.theme.MaterialDesignTheme
import com.volokhinaleksey.materialdesign.ui.theme_state.ThemeState
import com.volokhinaleksey.materialdesign.ui.theme_state.rememberThemeState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeState = rememberThemeState()
            MaterialDesignTheme(darkTheme = themeState.state) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(
                        themeState = themeState,
                        imageLoader = imageLoader
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    themeState: ThemeState,
    imageLoader: ImageLoader
) {
    val navController = rememberAnimatedNavController()
    AppBottomBar(navController = navController) {
        AnimatedNavHost(
            navController = navController,
            startDestination = ScreenState.PictureOfDayScreen.route,
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
                SettingsScreen(themeState = themeState)
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
        if (currentRoute != ScreenState.FullSizeImageScreen.route) {
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




