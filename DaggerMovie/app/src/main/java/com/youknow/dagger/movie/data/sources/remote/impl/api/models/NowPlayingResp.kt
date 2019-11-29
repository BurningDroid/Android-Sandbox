package com.youknow.dagger.movie.data.sources.remote.impl.api.models

import com.google.gson.annotations.SerializedName
import com.youknow.dagger.movie.domain.model.SimpleMovie

data class NowPlayingResp(
    val results: List<Result> = listOf()
)

data class Result(
    @SerializedName("poster_path")
    val posterPath: String = "",
    val adult: Boolean = false,
    @SerializedName("release_date")
    val releaseDate: String = "",
    val id: Int = 0,
    val title: String = "",
    @SerializedName("vote_average")
    val voteAverage: Float = 0f
)

fun NowPlayingResp.mapToDomain(): List<SimpleMovie> = results.map {
    SimpleMovie(
        posterPath = it.posterPath,
        adult = it.adult,
        releaseDate = it.releaseDate,
        id = it.id,
        title = it.title,
        voteAverage = it.voteAverage
    )
}.toList()