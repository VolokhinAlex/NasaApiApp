package com.volokhinaleksey.materialdesign.states

import com.volokhinaleksey.materialdesign.model.NasaDataDTO

sealed class PictureOfTheDayState {
    data class Success(val nasaDataDTO: NasaDataDTO) : PictureOfTheDayState()
    data class Error(val message: Throwable) : PictureOfTheDayState()
    object Loading : PictureOfTheDayState()
}
