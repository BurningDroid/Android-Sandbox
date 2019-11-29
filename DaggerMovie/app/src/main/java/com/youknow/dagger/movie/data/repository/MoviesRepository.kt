package com.youknow.dagger.movie.data.repository

import com.youknow.dagger.movie.data.sources.local.LocalMoviesDataSource
import com.youknow.dagger.movie.data.sources.remote.RemoteMoviesDataSource
import com.youknow.dagger.movie.data.sources.remote.impl.api.models.NowPlayingResp
import io.reactivex.Single

class MoviesRepository(
    private val localMoviesDao: LocalMoviesDataSource,
    private val remoteMoviesDao: RemoteMoviesDataSource
) {
    fun getMovies(): Single<List<NowPlayingResp>> {
        return remoteMoviesDao.getMovies()
    }
}