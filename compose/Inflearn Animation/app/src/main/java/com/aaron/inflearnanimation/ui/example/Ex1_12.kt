package com.aaron.inflearnanimation.ui.example

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Ex1_12() {
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        var sliderProgress by remember { mutableStateOf(0.5F) }
        Slider(
            value = sliderProgress,
            onValueChange = {
                sliderProgress = it
            },
            colors = SliderDefaults.colors(
                thumbColor = Color.Red,
                activeTrackColor = Color.Black,
            ),
        )

        SliderCircle(
            sliderProgress = sliderProgress
        )
    }
}

@Composable
fun SliderCircle(
    sliderProgress: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(250.dp)
        ) {
            drawArc(
                brush = SolidColor(Color.Gray),
                startAngle = 0F,
                sweepAngle = 360F,
                useCenter = false,
                style = Stroke(35F)
            )

            val slideChangedProgress = sliderProgress * 360
            drawArc(
                brush = SolidColor(Color.Blue),
                startAngle = 0F,
                sweepAngle = slideChangedProgress,
                useCenter = false,
                style = Stroke(35F, cap = StrokeCap.Round)
            )

        }

        Text(
            text = "${(sliderProgress * 100).toInt()}%",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}