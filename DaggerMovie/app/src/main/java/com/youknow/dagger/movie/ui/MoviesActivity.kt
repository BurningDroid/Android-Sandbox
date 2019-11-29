package com.youknow.dagger.movie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.youknow.dagger.movie.R
import com.youknow.dagger.movie.data.repository.MoviesRepository
import com.youknow.dagger.movie.data.sources.local.impl.LocalMoviesDao
import com.youknow.dagger.movie.data.sources.remote.impl.RemoteMoviesDao
import com.youknow.dagger.movie.data.sources.remote.impl.api.MoviesApiService

class MoviesActivity : AppCompatActivity(), MoviesContract.View {

    private val moviesPresenter: MoviesPresenter by lazy {
        MoviesPresenter(this, MoviesRepository(LocalMoviesDao(), RemoteMoviesDao(MoviesApiService.service)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesPresenter.getMovies()
    }
}
