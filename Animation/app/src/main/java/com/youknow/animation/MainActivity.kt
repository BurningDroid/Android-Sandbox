package com.youknow.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRotate.setOnClickListener { rotate() }
        btnTranslate.setOnClickListener { translate() }
        btnScale.setOnClickListener { scale() }
        btnFade.setOnClickListener { fade() }
        btnColor.setOnClickListener { color() }
        btnSnowing.setOnClickListener { snowing() }
    }

    private fun rotate() {
        
    }

    private fun translate() {

    }

    private fun scale() {

    }

    private fun fade() {

    }

    private fun color() {
    }

    private fun snowing() {

    }

}
