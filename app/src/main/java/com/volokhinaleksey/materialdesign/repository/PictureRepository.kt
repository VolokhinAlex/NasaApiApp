package com.volokhinaleksey.materialdesign.repository

import androidx.annotation.WorkerThread
import com.volokhinaleksey.materialdesign.BuildConfig
import com.volokhinaleksey.materialdesign.model.NasaDataDTO
import retrofit2.Response

interface PictureRepository {
    suspend fun getPictureOfTheDay(): Response<NasaDataDTO>

}

class PictureRepositoryImpl(private val apiHolder: ApiHolder) : PictureRepository {

    @WorkerThread
    override suspend fun getPictureOfTheDay() =
        apiHolder.apiService.getPictureOfTheDay(BuildConfig.NASA_API_KEY)

}