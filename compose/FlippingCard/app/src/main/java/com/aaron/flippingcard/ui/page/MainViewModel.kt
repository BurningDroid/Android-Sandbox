package com.aaron.flippingcard.ui.page

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aaron.flippingcard.App
import com.aaron.flippingcard.ui.page.model.CardItem

class MainViewModel : ViewModel(), SensorEventListener {

    private val sensorManager: SensorManager by lazy {
        App.appContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    var cardState: CardItem by mutableStateOf(CardItem(message = "우동사리님, 맴버십 가입 1주년 너무 감사하고 축하해요! 감사의 마음을 담아 목소리 보내드려요! 앞으로 우리 더 좋은 추억 만들어가요~❤"))
        private set

    // 각도 값을 저장할 상태
    var accelerometer by mutableStateOf(Accelerometer())  // 이미지 회전을 위한 상태
        private set

    init {
        val gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (gyroscopeSensor != null) {
            sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }

    fun onFlip() {
        cardState = cardState.copy(isFlipped = !cardState.isFlipped)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) {
            return
        }

        accelerometer = Accelerometer(
            x = getDegree(event.values[0]),
            y = getDegree(event.values[1]),
            z = getDegree(event.values[2]),
        )
    }

    private fun getDegree(rawValue: Float): Float {
        return Math.toDegrees(rawValue.toDouble()).toFloat() * 0.1f
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // 정확도 변경 시 처리할 내용
        Log.d("TEST", "[test] onAccuracyChanged - accuracy: $accuracy")
    }

}
