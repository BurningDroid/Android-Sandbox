package com.youknow.movie.ui.nowplaying

import android.view.View
import com.youknow.domain.usecase.GetNowPlayingMovies
import com.youknow.movie.R
import kotlinx.coroutines.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import kotlin.coroutines.CoroutineContext

class NowPlayingPresenter(
    private val view: NowPlayingContract.View,
    private val getMovies: GetNowPlayingMovies,
    private val uiContext: CoroutineContext = Dispatchers.Main,
    ioContext: CoroutineContext = Dispatchers.IO
) : NowPlayingContract.Presenter, CoroutineScope, AnkoLogger {

    override val coroutineContext: CoroutineContext = Job() + ioContext

    override fun unsubscribe() {
        coroutineContext.cancel()
    }

    override fun getMoviesNowPlaying() {
        view.showProgressBar(View.VISIBLE)
        view.hideError()

        launch {
            try {
                val movies = getMovies.get()
                withContext(uiContext) {
                    view.showProgressBar(View.GONE)
                    if (movies.isNullOrEmpty()) {
                        view.onError(R.string.err_movies_not_exists)
                    } else {
                        view.onMoviesLoaded(movies)
                    }
                }
            } catch (t: Throwable) {
                view.showProgressBar(View.GONE)
                view.onError(R.string.err_get_movies_failed)
                error("[Y.M.] getMoviesNowPlaying - failed: ${t.message}", t)
            }
        }
    }

}