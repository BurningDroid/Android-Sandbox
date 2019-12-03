package com.youknow.animation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.youknow.animation.animations.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRotate.setOnClickListener { moveActivity(RotateActivity::class.java) }
        btnTranslate.setOnClickListener { moveActivity(TranslateActivity::class.java) }
        btnScale.setOnClickListener { moveActivity(ScaleActivity::class.java) }
        btnFade.setOnClickListener { moveActivity(FadeActivity::class.java) }
        btnColor.setOnClickListener { moveActivity(ColorActivity::class.java) }
        btnSnowing.setOnClickListener { moveActivity(SnowingActivity::class.java) }
    }

    private fun moveActivity(activity: Class<out AppCompatActivity>) {
        startActivity(Intent(this, activity))
    }

}
