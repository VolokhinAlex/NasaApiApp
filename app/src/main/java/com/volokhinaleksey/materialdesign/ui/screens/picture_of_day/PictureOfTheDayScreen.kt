package com.volokhinaleksey.materialdesign.ui.screens.picture_of_day

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.volokhinaleksey.materialdesign.R
import com.volokhinaleksey.materialdesign.model.PictureOfTheDayDTO
import com.volokhinaleksey.materialdesign.states.PictureOfTheDayState
import com.volokhinaleksey.materialdesign.ui.images.ImageLoader
import com.volokhinaleksey.materialdesign.ui.widgets.*
import com.volokhinaleksey.materialdesign.viewmodels.PictureViewModel

/**
 * The main method for the layout of all the methods of the picture of the day screen
 * @param pictureViewModel - Picture View Model to work with Repository
 * @param imageLoader - It's need to load picture of the day image
 */

@Composable
fun PictureOfTheDayScreen(
    pictureViewModel: PictureViewModel = hiltViewModel(),
    imageLoader: ImageLoader
) {
    pictureViewModel.pictureOfTheDay.observeAsState().value?.let {
        RenderData(state = it, imageLoader = imageLoader)
    }
}

/**
 * A class for processing states coming from the View Model
 * @param state - The state that came from the repository
 * @param imageLoader - Needed for downloading and displaying image
 */

@Composable
private fun RenderData(
    state: PictureOfTheDayState,
    imageLoader: ImageLoader
) {
    val chipsList = listOf("Image HD", "Image")
    var selectedChip by remember { mutableStateOf("") }
    when (state) {
        is PictureOfTheDayState.Error -> state.message.localizedMessage?.let { ErrorMessage(it) }
        PictureOfTheDayState.Loading -> LoadingProgressBar()
        is PictureOfTheDayState.Success -> {
            val nasaDataDTO = state.nasaDataDTO
            Column(
                modifier = Modifier.padding(top = 20.dp)
            ) {
                TopBar()

                SetImageByChips(
                    selectedItem = selectedChip,
                    pictureOfTheDayDTO = nasaDataDTO,
                    imageLoader = imageLoader
                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    itemsIndexed(chipsList) { _, item ->
                        Chip(
                            title = item,
                            selected = selectedChip,
                            onSelected = {
                                selectedChip = item
                            }
                        )
                    }
                }

                BottomSheetBehavior(nasaDataDTO = nasaDataDTO)
            }
        }
    }
}

/**
 * Method for installing photos by chips
 * @param selectedItem - Selected Chip
 * @param pictureOfTheDayDTO - Data class with information about the picture of the day
 * @param imageLoader - Needed for downloading and displaying image
 */

@Composable
private fun SetImageByChips(
    selectedItem: String,
    pictureOfTheDayDTO: PictureOfTheDayDTO,
    imageLoader: ImageLoader
) {
    if (selectedItem.contains("HD")) {
        imageLoader.LoadImage(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .padding(start = 20.dp, end = 20.dp),
            url = pictureOfTheDayDTO.hdurl.orEmpty(),
            contentDescription = "Image HD of day url",
            contentScale = ContentScale.Crop
        )
    } else {
        imageLoader.LoadImage(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .padding(start = 20.dp, end = 20.dp),
            url = pictureOfTheDayDTO.url.orEmpty(),
            contentDescription = "Image of day url",
            contentScale = ContentScale.Crop
        )
    }
}

/**
 * The method adds a top bar to the picture of the day screen
 * @param searchState - Search State
 */

@Composable
private fun TopBar(searchState: SearchState = rememberSearchState()) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        SearchWidget(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
            query = searchState.query,
            label = stringResource(R.string.search_in_wiki_label)
        ) { searchState.query = TextFieldValue(it) }
    }
}