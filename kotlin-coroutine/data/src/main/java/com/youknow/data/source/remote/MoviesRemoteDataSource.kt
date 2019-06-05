package com.youknow.data.source.remote

import com.youknow.data.BuildConfig
import com.youknow.data.source.MoviesDataSource
import com.youknow.data.source.remote.api.MoviesApi
import com.youknow.data.source.remote.api.model.mapToDomain
import com.youknow.domain.model.Movie
import com.youknow.domain.model.SimpleMovie

class MoviesRemoteDataSource(
    private val api: MoviesApi
) : MoviesDataSource {

    override suspend fun getNowPlaying(): List<SimpleMovie> = api.getMoviesNowPlaying(BuildConfig.API_KEY).await().mapToDomain()

    override suspend fun getUpcoming(): List<SimpleMovie> = api.getMoviesUpcoming(BuildConfig.API_KEY).await().mapToDomain()

    override suspend fun getMovie(id: String): Movie = api.getMovie(id, BuildConfig.API_KEY).await().mapToDomain()

}