package com.youknow.fabcustom

import android.animation.ObjectAnimator
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isFabOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabMain.setOnClickListener {
            toggleFab()
        }

        fabSub1.setOnClickListener {
            Toast.makeText(this, "Sub 1 clicked!", Toast.LENGTH_SHORT).show()
        }

        fabSub2.setOnClickListener {
            Toast.makeText(this, "Sub 2 clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleFab() {
        Toast.makeText(this, "Main clicked: $isFabOpen", Toast.LENGTH_SHORT).show()
        val transitionDrawable = fabMain.drawable as TransitionDrawable
        if (isFabOpen) {
            ObjectAnimator.ofFloat(fabSub1, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(fabSub2, "translationY", 0f).apply { start() }
            transitionDrawable.reverseTransition(300)
        } else {
            ObjectAnimator.ofFloat(fabSub1, "translationY", -200f).apply { start() }
            ObjectAnimator.ofFloat(fabSub2, "translationY", -400f).apply { start() }
            transitionDrawable.startTransition(300)
        }

        isFabOpen = !isFabOpen
    }
}
