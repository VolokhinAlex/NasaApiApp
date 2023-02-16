package com.volokhinaleksey.materialdesign.repository

import androidx.annotation.WorkerThread
import com.volokhinaleksey.materialdesign.BuildConfig
import com.volokhinaleksey.materialdesign.model.PictureOfTheDayDTO
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import retrofit2.Response
import javax.inject.Inject

interface PictureRepository {

    /**
     * Method for executing a request to a remote repository to get picture of the day and information about it
     */

    suspend fun getPictureOfTheDay(): Response<PictureOfTheDayDTO>

}

@BoundTo(supertype = PictureRepository::class, component = SingletonComponent::class)
class PictureRepositoryImpl @Inject constructor(
    private val apiHolder: ApiHolder
) : PictureRepository {

    /**
     * Method for executing a request to a remote repository to get picture of the day and information about it
     */

    @WorkerThread
    override suspend fun getPictureOfTheDay() =
        apiHolder.apiService.getPictureOfTheDay(BuildConfig.NASA_API_KEY)

}