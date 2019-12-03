package com.youknow.animation.animations

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.youknow.animation.R
import kotlinx.android.synthetic.main.activity_fade.*

class FadeActivity : AppCompatActivity() {

    private var isSnowVisible: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fade)

        btnFade.setOnClickListener {
            animate()
            val icon = if (isSnowVisible) R.drawable.ic_visible else R.drawable.ic_unvisible
            btnFade.setImageResource(icon)
            isSnowVisible = !isSnowVisible
        }
    }

    private fun animate() {
        val visibility = if (isSnowVisible) 0F else 1F
        ObjectAnimator.ofFloat(ivSnow, View.ALPHA, visibility).apply { duration = 1000 }.start()
    }

}
