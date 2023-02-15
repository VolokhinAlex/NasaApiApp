package com.volokhinaleksey.materialdesign.ui.images

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.SubcomposeAsyncImage
import com.volokhinaleksey.materialdesign.ui.widgets.LoadingProgressBar

interface ImageLoader {

    @Composable
    fun LoadImage(url: String, contentDescription: String)

}

class CoilImageLoader : ImageLoader {

    @Composable
    override fun LoadImage(url: String, contentDescription: String) {
        SubcomposeAsyncImage(
            model = url,
            contentDescription = contentDescription,
            loading = { LoadingProgressBar() },
            modifier = Modifier.fillMaxHeight(0.5f)
        )
    }

}