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
        btnPlacePicker2.setOnClickListener {
            showPlacePickerWithParam()
        }
    }

    private fun showPlacePicker() {
        startActivity(Intent(this, PlacePickerActivity::class.java))
    }

    private fun showPlacePickerWithParam() {
        val intent = Intent(this, PlacePickerActivity::class.java)
            .putExtra(LAT, 37.57946459244118)
            .putExtra(LNG, 126.97661027312279)
        startActivity(intent)
    }
}
