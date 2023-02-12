package com.volokhinaleksey.materialdesign.repository

import androidx.annotation.WorkerThread
import com.volokhinaleksey.materialdesign.BuildConfig
import com.volokhinaleksey.materialdesign.model.MarsPhotosDTO
import retrofit2.Response

interface MarsPhotosRepository {

    suspend fun getMarsPhotos(sol: Int): Response<MarsPhotosDTO>

}

class MarsPhotosRepositoryImpl(private val apiHolder: ApiHolder) : MarsPhotosRepository {

    @WorkerThread
    override suspend fun getMarsPhotos(sol: Int): Response<MarsPhotosDTO> =
        apiHolder.apiService.getMarsPhotos(
            sol = sol,
            apiKey = BuildConfig.NASA_API_KEY
        )

}