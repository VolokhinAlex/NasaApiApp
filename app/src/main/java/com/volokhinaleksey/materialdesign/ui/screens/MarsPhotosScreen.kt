package com.volokhinaleksey.materialdesign.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.volokhinaleksey.materialdesign.R
import com.volokhinaleksey.materialdesign.states.MarsPhotosState
import com.volokhinaleksey.materialdesign.ui.images.CoilImageLoader
import com.volokhinaleksey.materialdesign.ui.navigation.ScreenState
import com.volokhinaleksey.materialdesign.ui.navigation.navigate
import com.volokhinaleksey.materialdesign.ui.widgets.ErrorMessage
import com.volokhinaleksey.materialdesign.ui.widgets.LoadingProgressBar
import com.volokhinaleksey.materialdesign.viewmodels.MarsPhotosViewModel
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun MarsPhotosScreen(
    marsPhotosViewModel: MarsPhotosViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = rememberCollapsingToolbarScaffoldState()
    CollapsingToolbarScaffold(
        modifier = Modifier,
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Text(
                stringResource(R.string.mars_photos),
                style = TextStyle(
                    fontSize = (18 + (30 - 12) * state.toolbarState.progress).sp
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .road(whenCollapsed = Alignment.TopStart, whenExpanded = Alignment.BottomStart)
            )
        },
        toolbarModifier = Modifier.drawBehind {
            val size = size

            val shadowStart = Color.Black.copy(alpha = 0.22f)
            val shadowEnd = Color.Transparent

            if (state.toolbarState.progress < 1f) {
                drawRect(
                    brush = Brush.verticalGradient(
                        listOf(shadowStart, shadowEnd),
                        startY = size.height,
                        endY = size.height + 56f
                    ),
                    topLeft = Offset(0f, size.height),
                    size = Size(size.width, 56f),
                )
            }
        }) {
        marsPhotosViewModel.marsPhotos.observeAsState().value?.let {
            RenderData(marsPhotosState = it, navController = navController)
        }
    }
}

@Composable
private fun RenderData(marsPhotosState: MarsPhotosState, navController: NavController) {
    val imageLoader by remember {
        mutableStateOf(CoilImageLoader())
    }
    when (marsPhotosState) {
        is MarsPhotosState.Error -> {
            val errorMessage = marsPhotosState.message
            errorMessage.localizedMessage?.let {
                ErrorMessage(
                    errorMessage = it
                )
            }
        }
        MarsPhotosState.Loading -> LoadingProgressBar()
        is MarsPhotosState.Success -> {
            val marsPhotosData = marsPhotosState.nasaDataDTO
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                marsPhotosData.photos?.let {
                    itemsIndexed(it) { _, item ->
                        val subUrl = item.imgSrc?.substring(item.imgSrc.indexOf(":"))
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .clickable {
                                    navController.navigate(
                                        route = ScreenState.FullSizeImageScreen.route,
                                        bundleOf("FullSizeImage" to "https${subUrl}")
                                    )
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            imageLoader.LoadImage(
                                modifier = Modifier,
                                url = "https${subUrl}",
                                contentDescription = "Mars Photo"
                            )
                            Column {
                                Text(
                                    text = "Camera: ${item.camera?.name}",
                                    modifier = Modifier.padding(top = 10.dp)
                                )
                                Text(
                                    text = "EarthDate: ${item.earthDate}",
                                    modifier = Modifier.padding(top = 10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
