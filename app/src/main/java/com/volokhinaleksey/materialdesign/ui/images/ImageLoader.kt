package com.volokhinaleksey.materialdesign.ui.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.volokhinaleksey.materialdesign.ui.widgets.LoadingProgressBar

interface ImageLoader {

    @Composable
    fun LoadImage(modifier: Modifier, url: String, contentDescription: String)

}

class CoilImageLoader : ImageLoader {

    @Composable
    override fun LoadImage(modifier: Modifier, url: String, contentDescription: String) {
        SubcomposeAsyncImage(
            model = url,
            contentDescription = contentDescription,
            loading = { LoadingProgressBar() },
            modifier = modifier,
            contentScale = ContentScale.FillBounds
        )
    }

}