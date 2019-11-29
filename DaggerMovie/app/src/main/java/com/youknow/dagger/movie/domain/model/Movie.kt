package com.youknow.dagger.movie.domain.model

data class Movie (
    val adult: Boolean = false,
    val genres: List<String> = listOf(),
    val homepage: String = "",
    val id: Int = 0,
    val overview: String = "",
    val popularity: Float = 0f,
    val posterPath: String = "",
    val releaseDate: String = "",
    val runtime: Int = 0,
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val voteAverage: Float = 0f,
    val voteCount: Int = 0
)

data class SimpleMovie(
    val posterPath: String,
    val adult: Boolean,
    val releaseDate: String,
    val id: Int,
    val title: String,
    val voteAverage: Float
)
