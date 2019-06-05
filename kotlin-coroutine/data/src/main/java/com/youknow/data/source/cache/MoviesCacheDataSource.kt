package com.youknow.data.source.cache

import com.youknow.data.source.MoviesDataSource
import com.youknow.domain.model.Movie
import com.youknow.domain.model.SimpleMovie

class MoviesCacheDataSource : MoviesDataSource {

    override suspend fun getNowPlaying(): List<SimpleMovie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getUpcoming(): List<SimpleMovie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getMovie(id: String): Movie {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}