package com.volokhinaleksey.materialdesign.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.volokhinaleksey.materialdesign.model.NasaDataDTO
import com.volokhinaleksey.materialdesign.states.PictureOfTheDayState
import com.volokhinaleksey.materialdesign.ui.images.CoilImageLoader
import com.volokhinaleksey.materialdesign.ui.images.ImageLoader
import com.volokhinaleksey.materialdesign.ui.widgets.BottomSheetBehavior
import com.volokhinaleksey.materialdesign.ui.widgets.ChipsWidget
import com.volokhinaleksey.materialdesign.ui.widgets.ErrorMessage
import com.volokhinaleksey.materialdesign.ui.widgets.LoadingProgressBar
import com.volokhinaleksey.materialdesign.viewmodels.PictureViewModel

@Composable
fun PictureOfTheDayScreen(pictureViewModel: PictureViewModel) {
    val coilImageLoader by remember {
        mutableStateOf(CoilImageLoader())
    }
    pictureViewModel.pictureOfTheDay.observeAsState().value?.let {
        RenderData(state = it, imageLoader = coilImageLoader)
    }
}

@Composable
private fun RenderData(
    state: PictureOfTheDayState,
    imageLoader: ImageLoader
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
                modifier = Modifier.padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SetImageByChips(
                    selectedItem = selectedItem,
                    nasaDataDTO = nasaDataDTO,
                    imageLoader = imageLoader
                )

                ChipsWidget(chips = chipsList) {
                    selectedItem = it
                }

                BottomSheetBehavior(nasaDataDTO = nasaDataDTO)
            }
        }
    }
}

@Composable
private fun SetImageByChips(
    selectedItem: String,
    nasaDataDTO: NasaDataDTO,
    imageLoader: ImageLoader
) {
    if (selectedItem.contains("HD")) {
        imageLoader.LoadImage(
            url = nasaDataDTO.hdurl.orEmpty(),
            contentDescription = "Image HD of day url"
        )
    } else {
        imageLoader.LoadImage(
            url = nasaDataDTO.url.orEmpty(),
            contentDescription = "Image of day url"
        )
    }
}