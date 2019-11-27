package com.youknow.animation.animations

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Property
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.youknow.animation.R
import kotlinx.android.synthetic.main.activity_rotate.*

class RotateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotate)

        btnStart.setOnClickListener {
            val property = when (rGroup.checkedRadioButtonId) {
                R.id.rBtnRotationX -> View.ROTATION_X
                R.id.rBtnRotationY -> View.ROTATION_Y
                else -> View.ROTATION
            }

            animate(property)
        }
    }

    private fun animate(property: Property<View, Float>) {
        ObjectAnimator.ofFloat(ivSnow, property, -360f, 0f)
            .apply {
                duration = 2000
            }
            .start()
    }

}
