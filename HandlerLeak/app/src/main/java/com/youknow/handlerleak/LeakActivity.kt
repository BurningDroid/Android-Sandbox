package com.youknow.handlerleak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log

class LeakActivity : AppCompatActivity() {

    private val TAG = LeakActivity::class.java.simpleName

    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d(TAG, "handleMessage")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak)

        handler.postDelayed({
            Log.d(TAG, "run!!!")
        }, 6_000)
        finish()
    }
}
