package com.youknow.handlerleak

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class NonLeakActivity : AppCompatActivity() {

    private val TAG = NonLeakActivity::class.java.simpleName

    private val handler = NonLeakHandler()

    private class NonLeakHandler : Handler() {
        override fun handleMessage(msg: Message) {
            Log.d("TEST", "[MemoryLeak] handleMessage")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak)

        handler.postDelayed({
            Log.d(TAG, "run!!!")
        }, 10_000)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

}