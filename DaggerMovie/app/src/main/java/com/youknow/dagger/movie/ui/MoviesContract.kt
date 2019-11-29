package com.youknow.dagger.movie.ui

interface MoviesContract {
    interface View
    interface Presenter {
        fun getMovies()
    }
}