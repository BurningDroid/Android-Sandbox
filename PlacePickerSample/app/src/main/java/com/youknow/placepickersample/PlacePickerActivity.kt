package com.youknow.placepickersample

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_place_picker.*
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng


private const val PERMISSION_REQUEST_CODE = 1200
const val LAT = "LAT"
const val LNG = "LNG"

class PlacePickerActivity : AppCompatActivity() {

    private lateinit var map: GoogleMap

    private var lat: Double = 0.0
    private var lng: Double = 0.0

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_picker)

        lat = intent.getDoubleExtra(LAT, 0.0)
        lng = intent.getDoubleExtra(LNG, 0.0)

        if (lat == 0.0 || lng == 0.0) {
            if (hasPermission()) {
                getCurrentLocation()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
            }
        }

        initMap()

        btnSelectLocation.setOnClickListener { selectLocation() }
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            map = googleMap
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 17F))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation()
                } else {
                    finish()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10F, object : LocationListener {
            override fun onLocationChanged(location: Location?) {
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onProviderDisabled(provider: String?) {
            }

        })
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        lat = location.latitude
        lng = location.longitude
    }

    private fun hasPermission(): Boolean = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun selectLocation() {
        Log.d("TEST", "[PP] lat: ${map.cameraPosition.target.latitude}, lng: ${map.cameraPosition.target.longitude}")
    }
}
