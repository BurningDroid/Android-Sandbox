package com.youknow.data.repository

import com.youknow.data.source.MoviesDataSource
import com.youknow.domain.model.Movie
import com.youknow.domain.model.SimpleMovie
import com.youknow.domain.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val moviesCacheDataSource: MoviesDataSource,
    private val moviesRemoteDataSource: MoviesDataSource
) : MoviesRepository {

    override suspend fun getNowPlaying(): List<SimpleMovie> = moviesRemoteDataSource.getNowPlaying()

    override suspend fun getUpcoming(): List<SimpleMovie> = moviesRemoteDataSource.getUpcoming()

    override suspend fun getMovie(id: String): Movie = moviesRemoteDataSource.getMovie(id)

}