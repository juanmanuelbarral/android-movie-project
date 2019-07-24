package com.example.moviedatabaseapp.model.data

import com.squareup.moshi.Json

data class Movie (
    @field:Json(name = "id") val movieId: Int,
    val title: String,
    @field:Json(name = "poster_path") var posterPath: String = ""
)