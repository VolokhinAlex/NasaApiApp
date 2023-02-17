package com.volokhinaleksey.materialdesign.repository

import androidx.annotation.WorkerThread
import com.volokhinaleksey.materialdesign.BuildConfig
import com.volokhinaleksey.materialdesign.model.NasaDataDTO
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import retrofit2.Response
import javax.inject.Inject

interface PictureRepository {
    suspend fun getPictureOfTheDay(): Response<NasaDataDTO>

}

@BoundTo(supertype = PictureRepository::class, component = SingletonComponent::class)
class PictureRepositoryImpl @Inject constructor(
    private val apiHolder: ApiHolder
) : PictureRepository {

    @WorkerThread
    override suspend fun getPictureOfTheDay() =
        apiHolder.apiService.getPictureOfTheDay(BuildConfig.NASA_API_KEY)

}