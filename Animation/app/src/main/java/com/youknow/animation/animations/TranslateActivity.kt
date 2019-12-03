package com.youknow.animation.animations

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Property
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.youknow.animation.R
import kotlinx.android.synthetic.main.activity_translate.*

class TranslateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)

        btnUp.setOnClickListener { animate(View.TRANSLATION_Y, -200f) }
        btnLeft.setOnClickListener { animate(View.TRANSLATION_X, -200f) }
        btnRight.setOnClickListener { animate(View.TRANSLATION_X, 200f) }
        btnDown.setOnClickListener { animate(View.TRANSLATION_Y, 200f) }
        btnRefresh.setOnClickListener {
            animate(View.TRANSLATION_X, 0f)
            animate(View.TRANSLATION_Y, 0f)
        }
    }

    private fun animate(property: Property<View, Float>, distance: Float) {
        ObjectAnimator.ofFloat(ivSnow, property, distance)
            .apply {
                duration = 1000
            }
            .start()
    }

}
