package com.volokhinaleksey.materialdesign.repository

import com.volokhinaleksey.materialdesign.model.NasaDataDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(@Query("api_key") apiKey: String): Response<NasaDataDTO>

}