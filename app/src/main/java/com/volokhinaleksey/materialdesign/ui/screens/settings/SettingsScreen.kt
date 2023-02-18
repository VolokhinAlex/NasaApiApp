package com.volokhinaleksey.materialdesign.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.volokhinaleksey.materialdesign.BuildConfig
import com.volokhinaleksey.materialdesign.R
import com.volokhinaleksey.materialdesign.modeThemeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * The main method for the layout of all the methods of the settings screen
 * @param dataStore - It is needed to save the application settings to the local storage
 */

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SettingsScreen(dataStore: DataStore<Preferences>) {
    val pagerState = rememberPagerState()
    val tabItems = listOf("Settings", "About App")
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.onSecondary
        ) {
            tabItems.forEachIndexed { index, title ->
                Tab(selected = pagerState.currentPage == index, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }) {
                    Text(text = title, modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
                }
            }
        }
        HorizontalPager(count = tabItems.size, state = pagerState) { page ->
            if (tabItems[page] == "Settings") {
                Settings(dataStore = dataStore)
            } else {
                AboutApp()
            }
        }
    }
}

/**
 * Screen with information about the application
 */

@Composable
private fun AboutApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(text = "About app", fontSize = 25.sp)
        Text(
            text = stringResource(id = R.string.about_app),
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )
        Text(
            text = "${stringResource(id = R.string.current_version)}: ${BuildConfig.VERSION_NAME}",
            fontSize = 16.sp
        )
    }
}

/**
 * Settings Screen Application.
 * @param dataStore - It is needed to save the application settings to the local storage
 */

@Composable
private fun Settings(dataStore: DataStore<Preferences>) {

    val getThemeMode: Flow<Boolean> by lazy {
        dataStore.data
            .map { preferences ->
                preferences[modeThemeState] ?: false
            }
    }

    var isDarkMode by remember {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()

    isDarkMode = getThemeMode.collectAsState(initial = false).value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = if (isDarkMode) stringResource(R.string.day_mode) else stringResource(
                R.string.night_mode
            ),
            fontSize = 20.sp
        )
        Switch(checked = isDarkMode, onCheckedChange = {
            isDarkMode = it
            coroutineScope.launch {
                dataStore.edit { preferences ->
                    preferences[modeThemeState] = it
                }
            }
        })
    }
}