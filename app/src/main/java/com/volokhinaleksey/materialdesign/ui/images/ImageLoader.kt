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

    /**
     * Method for getting and inserting an image into a container
     *
     * @param modifier - An object of the Modifier class for configuring a composable element
     * @param url - Image url to get it
     * @param contentDescription - Picture Description
     * @param contentScale - Represents a rule to apply to scale a source rectangle to be inscribed into a destination
     */

    @Composable
    fun LoadImage(
        modifier: Modifier,
        url: String,
        contentDescription: String,
        contentScale: ContentScale
    )

}

@BoundTo(supertype = ImageLoader::class, component = SingletonComponent::class)
class CoilImageLoader @Inject constructor() : ImageLoader {

    /**
     * The method downloads an image from the network and inserts it into a composable element
     *
     * @param modifier - An object of the Modifier class for configuring a composable element
     * @param url - Image url to get it
     * @param contentDescription - Picture Description
     * @param contentScale - Represents a rule to apply to scale a source rectangle to be inscribed into a destination
     */

    @Composable
    override fun LoadImage(
        modifier: Modifier,
        url: String,
        contentDescription: String,
        contentScale: ContentScale
    ) {
        SubcomposeAsyncImage(
            model = url,
            contentDescription = contentDescription,
            loading = { LoadingProgressBar() },
            modifier = modifier,
            contentScale = contentScale
        )
    }

}