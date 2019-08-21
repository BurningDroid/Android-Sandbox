package com.youknow.fabcustom

import android.animation.ObjectAnimator
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

        if (isFabOpen) {
            ObjectAnimator.ofFloat(fabSub1, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(fabSub2, "translationY", 0f).apply { start() }
            fabMain.setImageResource(R.drawable.ic_add)
        } else {
            ObjectAnimator.ofFloat(fabSub1, "translationY", -200f).apply { start() }
            ObjectAnimator.ofFloat(fabSub2, "translationY", -400f).apply { start() }
            fabMain.setImageResource(R.drawable.ic_close)
        }

        isFabOpen = !isFabOpen
    }

}
