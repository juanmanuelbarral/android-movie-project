package com.example.moviedatabaseapp.model.api

import com.example.moviedatabaseapp.model.data.Movie
import com.example.moviedatabaseapp.model.data.ResponsePopularMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDatabaseService {

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<ResponsePopularMovies>

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int
    ): Response<Movie>

}