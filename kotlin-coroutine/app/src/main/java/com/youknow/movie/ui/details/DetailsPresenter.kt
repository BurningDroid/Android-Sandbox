package com.youknow.movie.ui.details

import android.view.View
import com.youknow.domain.usecase.GetMovie
import com.youknow.movie.R
import kotlinx.coroutines.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import kotlin.coroutines.CoroutineContext

class DetailsPresenter(
    private val view: DetailsContract.View,
    private val getMovie: GetMovie,
    private val uiContext: CoroutineContext = Dispatchers.Main,
    ioContext: CoroutineContext = Dispatchers.IO
) : DetailsContract.Presenter, CoroutineScope, AnkoLogger {

    override val coroutineContext: CoroutineContext = Job() + ioContext

    override fun unsubscribe() {
        coroutineContext.cancel()
    }

    override fun getMovie(movieId: String) {
        view.showProgressBar(View.VISIBLE)
        view.hideError()

        launch {
            try {
                val movie = getMovie.get(movieId)
                withContext(uiContext) {
                    view.showProgressBar(View.GONE)
                    view.onMovieLoaded(movie)
                }
            } catch (t: Throwable) {
                view.showProgressBar(View.GONE)
                view.onError(R.string.err_get_movie_failed)
                error("[Y.M.] getMovie - failed: ${t.message}", t)
            }
        }
    }

}