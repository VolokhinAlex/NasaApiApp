package com.volokhinaleksey.materialdesign.viewmodels

import androidx.lifecycle.*
import com.volokhinaleksey.materialdesign.repository.NasaApiHolder
import com.volokhinaleksey.materialdesign.repository.PictureRepository
import com.volokhinaleksey.materialdesign.repository.PictureRepositoryImpl
import com.volokhinaleksey.materialdesign.states.PictureOfTheDayState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PictureViewModel(private val pictureRepository: PictureRepository) : ViewModel() {

    private val _pictureOfTheDay: MutableLiveData<PictureOfTheDayState> =
        MutableLiveData(PictureOfTheDayState.Loading)

    val pictureOfTheDay: LiveData<PictureOfTheDayState> get() = _pictureOfTheDay

    init {
        getPictureOfTheDay()
    }

    private fun getPictureOfTheDay() {
        _pictureOfTheDay.value = PictureOfTheDayState.Loading
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            PictureOfTheDayState.Error(throwable)
        }) {
            val response = pictureRepository.getPictureOfTheDay()
            val responseData = response.body()
            _pictureOfTheDay.postValue(
                if (response.isSuccessful && responseData != null) {
                    PictureOfTheDayState.Success(responseData)
                } else {
                    PictureOfTheDayState.Error(Throwable(response.message()))
                }
            )
        }
    }

}


@Suppress("UNCHECKED_CAST")
class PictureViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PictureViewModel::class.java)) {
            PictureViewModel(pictureRepository = PictureRepositoryImpl(NasaApiHolder)) as T
        } else {
            throw IllegalArgumentException("PictureViewModelFactory not found")
        }
    }
}