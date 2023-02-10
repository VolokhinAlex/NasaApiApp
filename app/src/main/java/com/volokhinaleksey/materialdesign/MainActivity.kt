package com.volokhinaleksey.materialdesign

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.volokhinaleksey.materialdesign.ui.navigation.ScreenState
import com.volokhinaleksey.materialdesign.ui.screens.PictureOfTheDayScreen
import com.volokhinaleksey.materialdesign.ui.theme.MaterialDesignTheme
import com.volokhinaleksey.materialdesign.ui.widgets.DropDownMenuWidget
import com.volokhinaleksey.materialdesign.viewmodels.PictureViewModel
import com.volokhinaleksey.materialdesign.viewmodels.PictureViewModelFactory

class MainActivity : ComponentActivity() {

    private val pictureViewModel: PictureViewModel by viewModels { PictureViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialDesignTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(pictureViewModel = pictureViewModel)
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    pictureViewModel: PictureViewModel
) {
    val navController = rememberNavController()
    Scaffold(bottomBar = { AppBottomBar() }) {
        NavHost(
            navController = navController,
            startDestination = ScreenState.PictureOfDayScreen.route,
            modifier = Modifier.padding(it)
        ) {
            composable(route = ScreenState.PictureOfDayScreen.route) {
                PictureOfTheDayScreen(pictureViewModel = pictureViewModel)
            }
        }
    }
}

@Composable
fun AppBottomBar() {
    var expanded by remember { mutableStateOf(false) }
    DropDownMenuWidget(expanded = expanded) { expanded = false }
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
            }
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.White)
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
}



