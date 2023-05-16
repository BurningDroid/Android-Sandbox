package com.youknow.study.chapter8.animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.youknow.study.chapter8.animation.demos.AnimatedVisibilityDemo
import com.youknow.study.chapter8.animation.demos.CrossfadeAnimationDemo
import com.youknow.study.chapter8.animation.demos.InfiniteRepeatableDemo
import com.youknow.study.chapter8.animation.demos.SizeChangeAnimationDemo
import com.youknow.study.chapter8.animation.ui.theme.Chapter8AnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Chapter8AnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    MultipleValuesAnimationDemo()
//                    AnimatedVisibilityDemo()
//                    SizeChangeAnimationDemo()
//                    CrossfadeAnimationDemo()
                    InfiniteRepeatableDemo()
                }
            }
        }
    }
}
