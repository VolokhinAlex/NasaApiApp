package com.volokhinaleksey.materialdesign.repository

import com.volokhinaleksey.materialdesign.model.NasaDataDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<NasaDataDTO>

}