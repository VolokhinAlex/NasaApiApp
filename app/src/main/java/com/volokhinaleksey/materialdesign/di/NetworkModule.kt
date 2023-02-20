package com.volokhinaleksey.materialdesign.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.volokhinaleksey.materialdesign.repository.NasaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * A module for providing a dependency for working with the network
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    /**
     * Method for providing the main link for requests via retrofit
     */

    @Provides
    @Named("baseUrl")
    fun baseUrl(): String = "https://api.nasa.gov/"

    /**
     * The method provides a dependency for the OkHttpClient which is needed to enable interceptors
     */

    @Singleton
    @Provides
    fun client(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    /**
     * The method represents a dependency for logging requests to the network
     */

    @Singleton
    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()

    /**
     * The method represents a dependency for the converter of objects from JSON to data classes
     */

    @Singleton
    @Provides
    fun gson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    /**
     * A method for providing a service dependency for retrofit
     * @param baseUrl - the main url for going online
     * @param gson - Converter of elements from json to data classes
     * @param client - OkHttpClient
     */

    @Singleton
    @Provides
    fun nasaApi(
        @Named("baseUrl") baseUrl: String,
        gson: Gson,
        client: OkHttpClient
    ): NasaApiService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()
        .create(NasaApiService::class.java)
}