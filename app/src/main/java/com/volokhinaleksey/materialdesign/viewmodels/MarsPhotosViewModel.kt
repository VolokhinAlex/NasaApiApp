package com.volokhinaleksey.materialdesign.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volokhinaleksey.materialdesign.repository.MarsPhotosRepository
import com.volokhinaleksey.materialdesign.states.MarsPhotosState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A class with the business logic of the Mars Photos Screen.
 * The class is engaged in obtaining data about Mars from the repository.
 *
 * @param marsPhotosRepository - Repository for getting data
 */

@HiltViewModel
class MarsPhotosViewModel @Inject constructor(
    private val marsPhotosRepository: MarsPhotosRepository
) : ViewModel() {

    private val _marsPhotos: MutableLiveData<MarsPhotosState> =
        MutableLiveData(MarsPhotosState.Loading)

    val marsPhotos: LiveData<MarsPhotosState> = _marsPhotos

    init {
        getMarsPhotos()
    }

    /**
     * The method gets some state from the repository and performs some actions depending on it
     */

    private fun getMarsPhotos() {
        _marsPhotos.value = MarsPhotosState.Loading
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            MarsPhotosState.Error(throwable)
        }) {
            val response = marsPhotosRepository.getMarsPhotos(sol = 1000)
            val responseData = response.body()
            _marsPhotos.postValue(
                if (response.isSuccessful && responseData?.photos != null) {
                    MarsPhotosState.Success(responseData)
                } else {
                    MarsPhotosState.Error(Throwable(response.message()))
                }
            )
        }
    }

}
