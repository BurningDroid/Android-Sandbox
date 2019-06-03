package com.youknow.placepickersample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlacePicker.setOnClickListener { showPlacePicker() }
    }

    private fun showPlacePicker() {
        startActivity(Intent(this, PlacePickerActivity::class.java))
    }
}
