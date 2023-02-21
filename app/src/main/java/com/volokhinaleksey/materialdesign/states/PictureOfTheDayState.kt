package com.volokhinaleksey.materialdesign.states

import com.volokhinaleksey.materialdesign.model.PictureOfTheDayDTO
import com.volokhinaleksey.materialdesign.states.PictureOfTheDayState.*

/**
 * States that come from the remote server. Total 3 states
 * 1. [Success] - If the request is successful, stores a picture of the day
 * 2. [Error] - If the request is executed unsuccessfully, stores an error.
 * 3. [Loading] - If the request is still being executed.
 */

sealed class PictureOfTheDayState {
    data class Success(val nasaDataDTO: PictureOfTheDayDTO) : PictureOfTheDayState()
    data class Error(val message: Throwable) : PictureOfTheDayState()
    object Loading : PictureOfTheDayState()
}
