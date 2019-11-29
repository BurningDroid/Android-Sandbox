package com.youknow.dagger.movie.ui

import com.youknow.dagger.movie.data.repository.MoviesRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.schedulers.IoScheduler
import io.reactivex.schedulers.Schedulers

class MoviesPresenter (
    private val view: MoviesContract.View,
    private val moviesRepo: MoviesRepository,
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
): MoviesContract.Presenter {

    override fun getMovies() {
        disposable.add(
            moviesRepo.getMovies()
        )
    }

}