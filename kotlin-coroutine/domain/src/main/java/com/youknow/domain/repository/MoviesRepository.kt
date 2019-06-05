package com.youknow.domain.repository

import com.youknow.domain.model.Movie
import com.youknow.domain.model.SimpleMovie

interface MoviesRepository {

    suspend fun getNowPlaying(): List<SimpleMovie>

    suspend fun getUpcoming(): List<SimpleMovie>

    suspend fun getMovie(id: String): Movie
}