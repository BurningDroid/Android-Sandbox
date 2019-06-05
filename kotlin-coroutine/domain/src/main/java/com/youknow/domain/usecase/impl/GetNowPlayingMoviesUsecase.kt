package com.youknow.domain.usecase.impl

import com.youknow.domain.model.SimpleMovie
import com.youknow.domain.repository.MoviesRepository
import com.youknow.domain.usecase.GetNowPlayingMovies

class GetNowPlayingMoviesUsecase(private val moviesRepository: MoviesRepository) : GetNowPlayingMovies {

    override suspend fun get(): List<SimpleMovie> = moviesRepository.getNowPlaying()

}