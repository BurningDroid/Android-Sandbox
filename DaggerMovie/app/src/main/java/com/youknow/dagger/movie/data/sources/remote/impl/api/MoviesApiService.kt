package com.youknow.dagger.movie.data.sources.remote.impl.api

import com.youknow.dagger.movie.data.sources.remote.impl.api.models.NowPlayingResp
import com.youknow.dagger.movie.data.sources.remote.impl.api.models.TmdbMovieResp
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET("/3/movie/now_playing")
    fun getMoviesNowPlaying(@Query("api_key") apiKey: String): Single<NowPlayingResp>

    @GET("/3/movie/upcoming")
    fun getMoviesUpcoming(@Query("api_key") apiKey: String): Single<NowPlayingResp>

    @GET("/3/movie/{movieId}?api_key=")
    fun getMovie(@Path("movieId") movieId: String, @Query("api_key") apiKey: String): Single<TmdbMovieResp>

    companion object {
        val service: MoviesApiService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

            retrofit.create(MoviesApiService::class.java)
        }
    }

}