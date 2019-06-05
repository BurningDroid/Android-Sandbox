package com.youknow.data.source.remote.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.youknow.data.source.remote.api.model.NowPlayingResp
import com.youknow.data.source.remote.api.model.TmdbMovieResp
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("/3/movie/now_playing")
    fun getMoviesNowPlaying(@Query("api_key") apiKey: String): Deferred<NowPlayingResp>

    @GET("/3/movie/upcoming")
    fun getMoviesUpcoming(@Query("api_key") apiKey: String): Deferred<NowPlayingResp>

    @GET("/3/movie/{movieId}?api_key=")
    fun getMovie(@Path("movieId") movieId: String, @Query("api_key") apiKey: String): Deferred<TmdbMovieResp>

    companion object {
        fun getService(): MoviesApi {
            val client: OkHttpClient = OkHttpClient.Builder().apply {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }.build()

            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

            return retrofit.create(MoviesApi::class.java)
        }
    }

}
