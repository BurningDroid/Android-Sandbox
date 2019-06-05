package com.youknow.domain.usecase

import com.youknow.domain.model.SimpleMovie

interface GetUpcomingMovies {

    suspend fun get(): List<SimpleMovie>

}