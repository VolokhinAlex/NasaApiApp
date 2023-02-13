package com.volokhinaleksey.materialdesign.repository

import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

interface ApiHolder {
    val apiService: NasaApiService
}

@BoundTo(supertype = ApiHolder::class, component = SingletonComponent::class)
class NasaApiHolder @Inject constructor(override val apiService: NasaApiService) : ApiHolder