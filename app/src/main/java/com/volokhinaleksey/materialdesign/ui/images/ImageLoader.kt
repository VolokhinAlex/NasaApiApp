package com.volokhinaleksey.materialdesign.ui.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.volokhinaleksey.materialdesign.ui.widgets.LoadingProgressBar
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

interface ImageLoader {

    @Composable
    fun LoadImage(modifier: Modifier, url: String, contentDescription: String)

}

@BoundTo(supertype = ImageLoader::class, component = SingletonComponent::class)
class CoilImageLoader @Inject constructor() : ImageLoader {

    @Composable
    override fun LoadImage(modifier: Modifier, url: String, contentDescription: String) {
        SubcomposeAsyncImage(
            model = url,
            contentDescription = contentDescription,
            loading = { LoadingProgressBar() },
            modifier = modifier,
            contentScale = ContentScale.Inside
        )
    }

}