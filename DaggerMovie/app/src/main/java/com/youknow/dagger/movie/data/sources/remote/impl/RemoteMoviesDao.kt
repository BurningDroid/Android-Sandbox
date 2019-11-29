package com.youknow.dagger.movie.data.sources.remote.impl

import com.youknow.dagger.movie.data.sources.remote.RemoteMoviesDataSource
import com.youknow.dagger.movie.data.sources.remote.impl.api.MoviesApiService
import com.youknow.dagger.movie.data.sources.remote.impl.api.models.NowPlayingResp
import io.reactivex.Single

class RemoteMoviesDao(
    private val moviesApiService: MoviesApiService
) : RemoteMoviesDataSource {
    override fun getMovies(): Single<List<NowPlayingResp>> {
        return moviesApiService.getMoviesNowPlaying()
    }

}