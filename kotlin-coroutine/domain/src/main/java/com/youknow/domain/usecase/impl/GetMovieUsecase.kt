package com.youknow.domain.usecase.impl

import com.youknow.domain.model.Movie
import com.youknow.domain.repository.MoviesRepository
import com.youknow.domain.usecase.GetMovie

class GetMovieUsecase(private val moviesRepository: MoviesRepository) : GetMovie {

    override suspend fun get(id: String): Movie = moviesRepository.getMovie(id)

}