package com.volokhinaleksey.materialdesign.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.volokhinaleksey.materialdesign.model.NasaDataDTO
import com.volokhinaleksey.materialdesign.repository.NasaApiHolder
import com.volokhinaleksey.materialdesign.repository.PictureRepository
import com.volokhinaleksey.materialdesign.repository.PictureRepositoryImpl
import com.volokhinaleksey.materialdesign.states.PictureOfTheDayState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureViewModel(private val pictureRepository: PictureRepository) : ViewModel() {

    private var _pictureOfTheDay: MutableLiveData<PictureOfTheDayState> =
        MutableLiveData(PictureOfTheDayState.Loading)

    val pictureOfTheDay: LiveData<PictureOfTheDayState> get() = _pictureOfTheDay

    private val callback = object : Callback<NasaDataDTO> {
        override fun onResponse(call: Call<NasaDataDTO>, response: Response<NasaDataDTO>) {
            val serverResponse = response.body()
            _pictureOfTheDay.value = if (response.isSuccessful && serverResponse != null) {
                PictureOfTheDayState.Success(serverResponse)
            } else {
                PictureOfTheDayState.Error(Throwable("SERVER ERROR"))
            }
        }

        override fun onFailure(call: Call<NasaDataDTO>, error: Throwable) {
            PictureOfTheDayState.Error(error)
        }
    }

    fun getPictureOfTheDay() {
        _pictureOfTheDay.value = PictureOfTheDayState.Loading
        pictureRepository.getPictureOfTheDay(callBack = callback)
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