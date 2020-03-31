package com.youknow.rxandroidsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            Log.d("TEST", "[test] OnClick")
        }
        btn.setOnTouchListener { v, event ->
            when(event.action) {
                MotionEvent.ACTION_UP -> {
                    Log.d("TEST", "[test] OnTouchClick - UP")
                    disposable.clear()
                }
            }
            false
        }
        btn.setOnLongClickListener {
            Log.d("TEST", "[test] long click")
            Observable.interval(200, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d("TEST", "[test] long click - $it")
                    }, {

                    }).addTo(disposable)

            false
        }
    }
}
