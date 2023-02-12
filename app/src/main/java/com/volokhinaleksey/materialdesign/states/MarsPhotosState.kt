package com.volokhinaleksey.materialdesign.states

import com.volokhinaleksey.materialdesign.model.MarsPhotosDTO

sealed class MarsPhotosState {
    data class Success(val nasaDataDTO: MarsPhotosDTO) : MarsPhotosState()
    data class Error(val message: Throwable) : MarsPhotosState()
    object Loading : MarsPhotosState()
}
