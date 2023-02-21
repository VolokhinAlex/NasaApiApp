package com.volokhinaleksey.materialdesign.states

import com.volokhinaleksey.materialdesign.model.MarsPhotosDTO

/**
 * States that come from the remote server. Total 3 states
 * 1. [Success] - If the request is successful, stores a mars photos
 * 2. [Error] - If the request is executed unsuccessfully, stores an error.
 * 3. [Loading] - If the request is still being executed.
 */

sealed class MarsPhotosState {
    data class Success(val nasaDataDTO: MarsPhotosDTO) : MarsPhotosState()
    data class Error(val message: Throwable) : MarsPhotosState()
    object Loading : MarsPhotosState()
}
