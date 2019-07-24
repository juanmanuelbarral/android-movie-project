package com.example.moviedatabaseapp.model.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "a96994d867a6bbedf3fab3d61dda712c"
    private const val LANGUAGE = "en-US"

    // Interceptor to add the api_key and langugage to all requests
    private val tmbdUrlInterceptor = Interceptor {chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("language", LANGUAGE)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    // OkhttpClient for building http request url
    private val tmbdClient = OkHttpClient().newBuilder()
        .addInterceptor(tmbdUrlInterceptor)
        .build()

    fun makeMovieDatabaseService(): MovieDatabaseService {
        return Retrofit.Builder()
            .client(tmbdClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(MovieDatabaseService::class.java)
    }
}