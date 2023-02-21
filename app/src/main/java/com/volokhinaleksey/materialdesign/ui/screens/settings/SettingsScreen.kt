package com.volokhinaleksey.materialdesign.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.volokhinaleksey.materialdesign.BuildConfig
import com.volokhinaleksey.materialdesign.R
import com.volokhinaleksey.materialdesign.ui.theme_state.ThemeState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SettingsScreen(themeState: ThemeState) {
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Text(
                        text = if (themeState.state) stringResource(R.string.day_mode) else stringResource(
                            R.string.night_mode
                        ),
                        fontSize = 20.sp
                    )
                    Switch(checked = themeState.state, onCheckedChange = { themeState.state = it })
                }
            } else {
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
        }
    }
}