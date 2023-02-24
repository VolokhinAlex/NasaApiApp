package com.volokhinaleksey.materialdesign.ui.screens.big_image

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.volokhinaleksey.materialdesign.ui.images.CoilImageLoader

/**
 * A screen for displaying large-size images
 * @param imageUrl - Link to the photo to upload
 * @param navController - Needed to navigate between screens
 */

@Composable
fun FullSizeImage(imageUrl: String, navController: NavController) {

    val imageLoader by remember {
        mutableStateOf(CoilImageLoader())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        IconButton(
            onClick = { navController.popBackStack() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = Icons.Default.ArrowBack.name
            )
        }

        imageLoader.LoadImage(
            modifier = Modifier.fillMaxSize(),
            url = imageUrl,
            contentDescription = "image",
            contentScale = ContentScale.Inside
        )
    }

}