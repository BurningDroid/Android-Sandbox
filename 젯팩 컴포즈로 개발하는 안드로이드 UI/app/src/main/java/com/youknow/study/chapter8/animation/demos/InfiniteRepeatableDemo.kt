package com.youknow.study.chapter8.animation.demos

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import com.youknow.study.chapter8.animation.R

@Composable
fun InfiniteRepeatableDemo() {
    val infiniteTransition = rememberInfiniteTransition()
    val degrees by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 359F,
        animationSpec = infiniteRepeatable(
//            animation = keyframes {
//                durationMillis = 3000
//                0F at 0
//                180F at 2500
//                359F at 3000
//            },
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier.rotate(degrees = degrees)
        )
    }
}