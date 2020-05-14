package com.youknow.colortest

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bgDrawable = getDrawable(R.drawable.bg_rounded)
        val intColor = Color.parseColor("#FF0000")
        (bgDrawable as? GradientDrawable)?.setColor(intColor)

        tv.background = bgDrawable
    }
}
