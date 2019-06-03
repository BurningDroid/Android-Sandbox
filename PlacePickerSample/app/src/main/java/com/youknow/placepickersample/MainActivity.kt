package com.youknow.placepickersample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        startActivityForResult(Intent(this, PlacePickerActivity::class.java), PLACE_PICKER_REQUEST_CODE)
    }

    private fun showPlacePickerWithParam() {
        val intent = Intent(this, PlacePickerActivity::class.java)
            .putExtra(LAT, 37.57946459244118)
            .putExtra(LNG, 126.97661027312279)
        startActivityForResult(intent, PLACE_PICKER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PLACE_PICKER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val address = data?.getStringExtra(ADDRESS)
                val lat = data?.getDoubleExtra(LAT, 0.0)
                val lng = data?.getDoubleExtra(LNG, 0.0)
            }
        }
    }
}
