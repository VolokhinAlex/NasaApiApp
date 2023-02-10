package com.volokhinaleksey.materialdesign.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.volokhinaleksey.materialdesign.model.NasaDataDTO
import com.volokhinaleksey.materialdesign.states.PictureOfTheDayState
import com.volokhinaleksey.materialdesign.ui.widgets.*
import com.volokhinaleksey.materialdesign.viewmodels.PictureViewModel
import java.util.*

@Composable
fun PictureOfTheDayScreen(pictureViewModel: PictureViewModel) {
    LaunchedEffect(key1 = true) {
        pictureViewModel.getPictureOfTheDay()
    }
    pictureViewModel.pictureOfTheDay.observeAsState().value?.let {
        RenderData(
            state = it,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RenderData(
    state: PictureOfTheDayState,
    searchState: SearchState = rememberSearchState(),
) {
    val openBottomSheet = rememberSaveable {
        mutableStateOf(false)
    }
    val skipHalfExpanded = remember { mutableStateOf(false) }
    val sheetState = rememberSheetState(skipHalfExpanded = skipHalfExpanded.value)
    val chipsList = listOf("Image HD", "Image")
    var selectedItem by remember {
        mutableStateOf("")
    }
    when (state) {
        is PictureOfTheDayState.Error -> state.message.localizedMessage?.let { ErrorMessage(it) }
        PictureOfTheDayState.Loading -> LoadingProgressBar()
        is PictureOfTheDayState.Success -> {
            val nasaDataDTO = state.nasaDataDTO

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                SearchWidget(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                    text = searchState.query,
                    label = "Search in Wiki"
                ) { searchState.query = it }

                SetImageByChips(selectedItem = selectedItem, nasaDataDTO)

                Button(onClick = {
                    openBottomSheet.value = true
                }, modifier = Modifier.padding(10.dp)) {
                    Text(text = "Image Description")
                }

                ChipsWidget(chipsList) {
                    selectedItem = it
                }

                if (openBottomSheet.value) {
                    BottomSheetModal(
                        onDismissRequest = {
                            openBottomSheet.value = false
                        },
                        sheetState = sheetState,
                        nasaDataDTO = nasaDataDTO
                    )
                }

            }
        }
    }
}

@Composable
private fun SetImageByChips(selectedItem: String, nasaDataDTO: NasaDataDTO) {
    if (selectedItem.contains("HD")) {
        AsyncImage(
            model = "${nasaDataDTO.hdurl}",
            contentDescription = "Image HD of day url",
        )
    } else {
        AsyncImage(
            model = "${nasaDataDTO.url}",
            contentDescription = "Image of day url",
        )
    }
}