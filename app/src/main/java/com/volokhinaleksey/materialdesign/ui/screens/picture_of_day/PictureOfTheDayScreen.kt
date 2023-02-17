package com.volokhinaleksey.materialdesign.ui.screens.picture_of_day

import androidx.compose.foundation.layout.*
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

@Composable
fun PictureOfTheDayScreen(
    pictureViewModel: PictureViewModel = hiltViewModel(),
    imageLoader: ImageLoader
) {
    pictureViewModel.pictureOfTheDay.observeAsState().value?.let {
        RenderData(state = it, imageLoader = imageLoader)
    }
}

@Composable
private fun RenderData(
    state: PictureOfTheDayState,
    imageLoader: ImageLoader,
    searchState: SearchState = rememberSearchState()
) {
    val chipsList = listOf("Image HD", "Image")
    var selectedItem by remember {
        mutableStateOf("")
    }
    when (state) {
        is PictureOfTheDayState.Error -> state.message.localizedMessage?.let { ErrorMessage(it) }
        PictureOfTheDayState.Loading -> LoadingProgressBar()
        is PictureOfTheDayState.Success -> {
            val nasaDataDTO = state.nasaDataDTO
            Column(
                modifier = Modifier.padding(top = 20.dp)
            ) {
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

                SetImageByChips(
                    selectedItem = selectedItem,
                    nasaDataDTO = nasaDataDTO,
                    imageLoader = imageLoader
                )

                ChipsWidget(chips = chipsList, onSelected = {
                    selectedItem = it
                }, selectedChip = selectedItem)

                BottomSheetBehavior(nasaDataDTO = nasaDataDTO)
            }
        }
    }
}

@Composable
private fun SetImageByChips(
    selectedItem: String,
    nasaDataDTO: PictureOfTheDayDTO,
    imageLoader: ImageLoader
) {
    if (selectedItem.contains("HD")) {
        imageLoader.LoadImage(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .padding(start = 20.dp, end = 20.dp),
            url = nasaDataDTO.hdurl.orEmpty(),
            contentDescription = "Image HD of day url",
            contentScale = ContentScale.Crop
        )
    } else {
        imageLoader.LoadImage(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .padding(start = 20.dp, end = 20.dp),
            url = nasaDataDTO.url.orEmpty(),
            contentDescription = "Image of day url",
            contentScale = ContentScale.Crop
        )
    }
}