package com.volokhinaleksey.materialdesign.repository

import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

/**
 * Interface for working with retrofit
 */

interface ApiHolder {
    val apiService: NasaApiService
}

/**
 * Implementation of the interface for working with retrofit.
 * The retrofit object itself is created automatically using dependency injection.
 */

@BoundTo(supertype = ApiHolder::class, component = SingletonComponent::class)
class NasaApiHolder @Inject constructor(override val apiService: NasaApiService) : ApiHolder