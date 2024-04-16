package com.aaron.inflearnanimation.ui.screen.section4

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.time.Clock
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Section4Screen() {
    Clock()
}

@Composable
private fun Clock() {

    var inputText by remember { mutableStateOf("") }
    var progress by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = progress) {
        if (progress > 0) {
            delay(1_000)
            progress -= 1
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = inputText,
            onValueChange = {
                inputText = it
            }
        )

        Spacer(modifier = Modifier.padding(16.dp))

        val context = LocalContext.current
        Button(
            onClick = {
                val number = inputText.toIntOrNull()
                if (number == null) {
                    Toast.makeText(context, "숫자를 제대로 입력하세요.", Toast.LENGTH_SHORT).show()
                } else if (number in 1..60) {
                    progress = number
                } else {
                    Toast.makeText(context, "1~60 사이의 값을 입력하세요.", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(
                text = "시간 설정"
            )
        }

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.minDimension / 2
            Log.w("TEST", "[test] radius: $radius")
            for (second in 0 until 60) {
                val angle = Math.toRadians(second * 6.0 - 90)

                val startRadius =
                    if (second % 5 == 0) radius - 20.dp.toPx() else radius - 10.dp.toPx()
                Log.w("TEST", "[test] startRadius: $startRadius")
                val endRadius = radius

                val startX = center.x + cos(angle).toFloat() * startRadius
                val endX = center.x + cos(angle).toFloat() * endRadius

                val startY = center.y + sin(angle).toFloat() * startRadius
                val endY = center.y + sin(angle).toFloat() * endRadius

                drawLine(
                    Color.Black,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = if (second % 5 == 0) 3.dp.toPx() else 1.dp.toPx()
                )
            }

            val sweepAngle = (progress.toFloat() / 60F) * 360F
            drawArc(
                color = Color.Red,
                startAngle = -90F,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Fill
            )
        }
    }
}