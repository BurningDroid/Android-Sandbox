package com.youknow.domain.usecase.impl

import com.youknow.domain.model.SimpleMovie
import com.youknow.domain.repository.MoviesRepository
import com.youknow.domain.usecase.GetUpcomingMovies

class GetUpcomingMoviesUsecase(private val moviesRepository: MoviesRepository) : GetUpcomingMovies {

    override suspend fun get(): List<SimpleMovie> = moviesRepository.getUpcoming()

}