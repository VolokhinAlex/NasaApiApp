package com.volokhinaleksey.materialdesign.repository

import com.volokhinaleksey.materialdesign.model.MarsPhotosDTO
import com.volokhinaleksey.materialdesign.model.PictureOfTheDayDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for interacting with the Github API
 */

interface NasaApiService {

    /**
     * A method for requesting a remote repository to receive a picture of the day and information about it
     * @param apiKey - Api key for registering requests
     */

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(@Query("api_key") apiKey: String): Response<PictureOfTheDayDTO>

    /**
     * A method for requesting a remote repository to receive a mars photos and information about them
     * @param apiKey - Api key for registering requests
     */

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getMarsPhotos(
        @Query("sol") sol: Int,
        @Query("api_key") apiKey: String,
    ): Response<MarsPhotosDTO>

}