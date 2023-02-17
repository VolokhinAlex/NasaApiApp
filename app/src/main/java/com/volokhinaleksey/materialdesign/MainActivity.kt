package com.volokhinaleksey.materialdesign

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.volokhinaleksey.materialdesign.ui.navigation.ScreenState
import com.volokhinaleksey.materialdesign.ui.screens.MarsPhotosScreen
import com.volokhinaleksey.materialdesign.ui.screens.PictureOfTheDayScreen
import com.volokhinaleksey.materialdesign.ui.screens.SettingsScreen
import com.volokhinaleksey.materialdesign.ui.theme.MaterialDesignTheme
import com.volokhinaleksey.materialdesign.ui.theme_state.ThemeState
import com.volokhinaleksey.materialdesign.ui.theme_state.rememberThemeState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
                        themeState = themeState
                    )
                }
            }
        }
    }

}

@Composable
fun Navigation(
    themeState: ThemeState
) {
    val navController = rememberNavController()
    AppBottomBar(navController = navController) {
        NavHost(
            navController = navController,
            startDestination = ScreenState.PictureOfDayScreen.route,
            modifier = Modifier.padding(it)
        ) {
            composable(route = ScreenState.PictureOfDayScreen.route) {
                PictureOfTheDayScreen()
            }
            composable(route = ScreenState.SettingsScreen.route) {
                SettingsScreen(themeState = themeState)
            }
            composable(route = ScreenState.MarsPhotosScreen.route) {
                MarsPhotosScreen()
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
            ScreenState.MarsPhotosScreen
        )
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(bottomBar = {
        NavigationBar(containerColor = MaterialTheme.colorScheme.onSecondary) {
            bottomNavItems.forEach { item ->
                val selected = item.route == currentRoute
                val iconNavItem = ImageVector.vectorResource(id = item.icon)
                NavigationBarItem(
                    selected = selected,
                    onClick = { navController.navigate(item.route) },
                    label = {
                        Text(
                            text = stringResource(id = item.label)
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = iconNavItem,
                            contentDescription = iconNavItem.name
                        )
                    }
                )
            }
        }
    }, content = content)
}




