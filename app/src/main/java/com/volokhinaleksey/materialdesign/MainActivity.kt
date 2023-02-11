package com.volokhinaleksey.materialdesign

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.volokhinaleksey.materialdesign.ui.navigation.ScreenState
import com.volokhinaleksey.materialdesign.ui.screens.PictureOfTheDayScreen
import com.volokhinaleksey.materialdesign.ui.screens.SearchScreen
import com.volokhinaleksey.materialdesign.ui.screens.SettingsScreen
import com.volokhinaleksey.materialdesign.ui.theme.MaterialDesignTheme
import com.volokhinaleksey.materialdesign.ui.theme_state.ThemeState
import com.volokhinaleksey.materialdesign.ui.theme_state.rememberThemeState
import com.volokhinaleksey.materialdesign.viewmodels.PictureViewModel
import com.volokhinaleksey.materialdesign.viewmodels.PictureViewModelFactory

class MainActivity : ComponentActivity() {

    private val pictureViewModel: PictureViewModel by viewModels { PictureViewModelFactory() }

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
                        pictureViewModel = pictureViewModel,
                        themeState = themeState
                    )
                }
            }
        }
    }

}

@Composable
fun Navigation(
    pictureViewModel: PictureViewModel,
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
                PictureOfTheDayScreen(pictureViewModel = pictureViewModel)
            }
            composable(route = ScreenState.SettingsScreen.route) {
                SettingsScreen(themeState = themeState)
            }
            composable(route = ScreenState.SearchScreen.route) {
                SearchScreen()
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
    Scaffold(bottomBar = {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primary,
            actions = {
                IconButton(onClick = { navController.navigate(ScreenState.PictureOfDayScreen.route) }) {
                    Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White)
                }
                IconButton(onClick = { navController.navigate(ScreenState.SettingsScreen.route) }) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.White
                    )
                }
                IconButton(onClick = { navController.navigate(ScreenState.SearchScreen.route) }) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { },
                    containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                ) {
                    Icon(Icons.Filled.Add, "")
                }
            }
        )
    }, content = content)
}




