package com.youknow.data.source

import com.youknow.domain.model.Movie
import com.youknow.domain.model.SimpleMovie

interface MoviesDataSource {

    suspend fun getNowPlaying(): List<SimpleMovie>

    suspend fun getUpcoming(): List<SimpleMovie>

    suspend fun getMovie(id: String): Movie

}