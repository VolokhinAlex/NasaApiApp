package com.volokhinaleksey.materialdesign.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.volokhinaleksey.materialdesign.repository.PictureRepository
import com.volokhinaleksey.materialdesign.states.PictureOfTheDayState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A class with the business logic of the Picture of the day Screen.
 * The class is engaged in obtaining picture of the day and information about it from the repository.
 *
 * @param pictureRepository - Repository for getting data
 */

@HiltViewModel
class PictureViewModel @Inject constructor(
    private val pictureRepository: PictureRepository
) : ViewModel() {

    private val _pictureOfTheDay: MutableLiveData<PictureOfTheDayState> =
        MutableLiveData(PictureOfTheDayState.Loading)

    val pictureOfTheDay: LiveData<PictureOfTheDayState> get() = _pictureOfTheDay

    init {
        getPictureOfTheDay()
    }

    /**
     * The method gets some state from the repository and performs some actions depending on it
     */

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