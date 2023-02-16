package com.volokhinaleksey.materialdesign.repository

import androidx.annotation.WorkerThread
import com.volokhinaleksey.materialdesign.BuildConfig
import com.volokhinaleksey.materialdesign.model.MarsPhotosDTO
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import retrofit2.Response
import javax.inject.Inject

interface MarsPhotosRepository {

    /**
     * Method for executing a request to a remote repository to get mars photos data
     * @param sol - Martian sol of the Rover's mission
     */

    suspend fun getMarsPhotos(sol: Int): Response<MarsPhotosDTO>

}

@BoundTo(supertype = MarsPhotosRepository::class, component = SingletonComponent::class)
class MarsPhotosRepositoryImpl @Inject constructor(
    private val apiHolder: ApiHolder
) : MarsPhotosRepository {

    /**
     * Method for executing a request to a remote repository to get mars photos data
     */

    @WorkerThread
    override suspend fun getMarsPhotos(sol: Int): Response<MarsPhotosDTO> =
        apiHolder.apiService.getMarsPhotos(
            sol = sol,
            apiKey = BuildConfig.NASA_API_KEY
        )

}