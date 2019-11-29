package com.youknow.dagger.movie.data.sources.remote

import com.youknow.dagger.movie.data.sources.remote.impl.api.models.NowPlayingResp
import io.reactivex.Single

interface RemoteMoviesDataSource {
    fun getMovies(): Single<List<NowPlayingResp>>
}