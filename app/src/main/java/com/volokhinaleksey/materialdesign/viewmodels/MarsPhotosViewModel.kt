package com.volokhinaleksey.materialdesign.viewmodels

import androidx.lifecycle.*
import com.volokhinaleksey.materialdesign.repository.MarsPhotosRepository
import com.volokhinaleksey.materialdesign.repository.MarsPhotosRepositoryImpl
import com.volokhinaleksey.materialdesign.repository.NasaApiHolder
import com.volokhinaleksey.materialdesign.states.MarsPhotosState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MarsPhotosViewModel(private val marsPhotosRepository: MarsPhotosRepository) : ViewModel() {

    private val _marsPhotos: MutableLiveData<MarsPhotosState> =
        MutableLiveData(MarsPhotosState.Loading)

    val marsPhotos: LiveData<MarsPhotosState> = _marsPhotos

    init {
        getMarsPhotos()
    }

    fun getMarsPhotos() {
        _marsPhotos.value = MarsPhotosState.Loading
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            MarsPhotosState.Error(throwable)
        }) {
            val response = marsPhotosRepository.getMarsPhotos(1000)
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

@Suppress("UNCHECKED_CAST")
class MarsPhotosViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MarsPhotosViewModel::class.java)) {
            MarsPhotosViewModel(marsPhotosRepository = MarsPhotosRepositoryImpl(NasaApiHolder)) as T
        } else {
            throw IllegalArgumentException("MarsPhotosViewModel not found")
        }
    }
}