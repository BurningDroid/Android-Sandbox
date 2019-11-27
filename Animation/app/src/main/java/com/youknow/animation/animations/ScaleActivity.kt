package com.youknow.animation.animations

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.youknow.animation.R
import kotlinx.android.synthetic.main.activity_scale.*

class ScaleActivity : AppCompatActivity() {

    private var scaleLevel = 2F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale)

        btnScale.setOnClickListener {
            animate(scaleLevel)
            if (scaleLevel == 2F) {
                scaleLevel = 1F
                btnScale.setImageResource(R.drawable.ic_zoom_out)
            } else {
                scaleLevel = 2F
                btnScale.setImageResource(R.drawable.ic_zoom_in)
            }
        }
    }

    private fun animate(scaleLevel: Float) {
        ObjectAnimator.ofFloat(ivSnow, View.SCALE_X, scaleLevel).start()
        ObjectAnimator.ofFloat(ivSnow, View.SCALE_Y, scaleLevel).start()
    }

}
