package com.volokhinaleksey.materialdesign.ui.screens.mars_photos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.volokhinaleksey.materialdesign.R
import com.volokhinaleksey.materialdesign.model.MarsPhotosDTO
import com.volokhinaleksey.materialdesign.model.PhotoDTO
import com.volokhinaleksey.materialdesign.states.MarsPhotosState
import com.volokhinaleksey.materialdesign.ui.images.ImageLoader
import com.volokhinaleksey.materialdesign.ui.navigation.ScreenState
import com.volokhinaleksey.materialdesign.ui.navigation.navigate
import com.volokhinaleksey.materialdesign.ui.widgets.ErrorMessage
import com.volokhinaleksey.materialdesign.ui.widgets.LoadingProgressBar
import com.volokhinaleksey.materialdesign.viewmodels.MarsPhotosViewModel
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

/**
 * The main method for the layout of all the methods of mars photos screen
 * @param marsPhotosViewModel - Mars Photos View Model to work with Repository
 * @param navController - Needed to navigate between screens
 * @param imageLoader - It's need to load mars images
 */

@Composable
fun MarsPhotosScreen(
    marsPhotosViewModel: MarsPhotosViewModel = hiltViewModel(),
    navController: NavController,
    imageLoader: ImageLoader
) {
    CollapsingToolBar {
        marsPhotosViewModel.marsPhotos.observeAsState().value?.let {
            RenderData(
                marsPhotosState = it,
                navController = navController,
                imageLoader = imageLoader
            )
        }
    }
}

/**
 * A class for handling states coming from the repository
 * @param marsPhotosState - Mars photos state
 * @param navController - Needed to navigate between screens
 * @param imageLoader - It's need to load mars images
 */

@Composable
private fun RenderData(
    marsPhotosState: MarsPhotosState,
    navController: NavController,
    imageLoader: ImageLoader
) {
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
            MarsPhotosList(
                marsPhotosData = marsPhotosData,
                navController = navController,
                imageLoader = imageLoader
            )
        }
    }
}

/**
 * List of photos of Mars
 * @param marsPhotosData - The data class with information about Mars
 * @param navController - Needed to navigate between screens
 * @param imageLoader - It's need to load mars images
 */

@Composable
private fun MarsPhotosList(
    marsPhotosData: MarsPhotosDTO,
    navController: NavController,
    imageLoader: ImageLoader
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        marsPhotosData.photos?.let {
            itemsIndexed(it) { _, item ->
                MarsCardItem(photoDTO = item, imageLoader = imageLoader) { url ->
                    navController.navigate(
                        route = ScreenState.FullSizeImageScreen.route,
                        bundleOf("FullSizeImage" to url)
                    )
                }
            }
        }
    }
}

/**
 * The method adds a collapsing tool bar to screen
 * @param content - Content under the tool bar
 */

@Composable
private fun CollapsingToolBar(content: @Composable () -> Unit) {
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
        }
    ) {
        content()
    }
}

/**
 * A card for an item in the list of photos of Mars.
 * @param photoDTO - A mars data.
 * @param imageLoader - It's need to load mars images
 * @param onClick - Event when clicking on the card
 */

@Composable
private fun MarsCardItem(
    photoDTO: PhotoDTO,
    imageLoader: ImageLoader,
    onClick: (String) -> Unit
) {
    val subUrl = photoDTO.imgSrc?.substring(photoDTO.imgSrc.indexOf(":"))
    Column(
        modifier = Modifier
            .padding(20.dp)
            .clickable {
                onClick("https${subUrl}")
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imageLoader.LoadImage(
            modifier = Modifier,
            url = "https${subUrl}",
            contentDescription = "Mars Photo",
            contentScale = ContentScale.Inside
        )
        Column {
            Text(
                text = "${stringResource(R.string.camera_label)}: ${photoDTO.camera?.name}",
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = "${stringResource(R.string.earth_date_label)}: ${photoDTO.earthDate}",
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}