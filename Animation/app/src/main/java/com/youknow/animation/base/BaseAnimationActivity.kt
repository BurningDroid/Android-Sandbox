package com.youknow.animation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.youknow.animation.R
import kotlinx.android.synthetic.main.activity_rotate.*

abstract class BaseAnimationActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotate)

        btnStart.setOnClickListener { animate() }
    }

    abstract fun animate()

}
