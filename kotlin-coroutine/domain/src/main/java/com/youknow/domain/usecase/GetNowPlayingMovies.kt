package com.youknow.domain.usecase

import com.youknow.domain.model.SimpleMovie

interface GetNowPlayingMovies {

    suspend fun get(): List<SimpleMovie>

}