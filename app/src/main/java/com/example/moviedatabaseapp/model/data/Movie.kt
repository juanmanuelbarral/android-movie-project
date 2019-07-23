package com.example.moviedatabaseapp.model.data

data class Movie (
    val movieId: Int,
    val title: String,
    val posterPath: String
)

data class PopularMoviesResponse (
    val results: List<Movie>
)