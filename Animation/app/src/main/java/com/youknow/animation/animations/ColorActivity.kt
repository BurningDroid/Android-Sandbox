package com.youknow.animation.animations

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.youknow.animation.R
import kotlinx.android.synthetic.main.activity_color.*
import kotlin.random.Random


class ColorActivity : AppCompatActivity() {

    private var prevColor = Color.BLACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color)

        btnColor.setOnClickListener { animate() }
    }

    private fun animate() {
        val newColor = getRandomColor()
        ObjectAnimator.ofArgb(ivSnow.parent, "backgroundColor", prevColor, newColor).apply { duration = 1000 }.start()
        prevColor = newColor
    }

    private fun getRandomColor() =
        Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))

}
