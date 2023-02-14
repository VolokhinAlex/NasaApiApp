package com.volokhinaleksey.materialdesign.repository

import com.volokhinaleksey.materialdesign.BuildConfig
import com.volokhinaleksey.materialdesign.model.NasaDataDTO
import retrofit2.Callback

interface PictureRepository {

    fun getPictureOfTheDay(callBack: Callback<NasaDataDTO>)

}

class PictureRepositoryImpl(private val apiHolder: ApiHolder) : PictureRepository {

    override fun getPictureOfTheDay(callBack: Callback<NasaDataDTO>) =
        apiHolder.apiService.getPictureOfTheDay(BuildConfig.NASA_API_KEY).enqueue(callBack)

}