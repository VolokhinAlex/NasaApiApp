package com.volokhinaleksey.materialdesign.ui.screens

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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.volokhinaleksey.materialdesign.states.MarsPhotosState
import com.volokhinaleksey.materialdesign.ui.images.CoilImageLoader
import com.volokhinaleksey.materialdesign.ui.widgets.ErrorMessage
import com.volokhinaleksey.materialdesign.ui.widgets.LoadingProgressBar
import com.volokhinaleksey.materialdesign.viewmodels.MarsPhotosViewModel

@Composable
fun MarsPhotosScreen(marsPhotosViewModel: MarsPhotosViewModel) {
    marsPhotosViewModel.marsPhotos.observeAsState().value?.let {
        RenderData(marsPhotosState = it)
    }
}

@Composable
private fun RenderData(marsPhotosState: MarsPhotosState) {
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
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val subUrl = item.imgSrc?.substring(item.imgSrc.indexOf(":"))
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
