package com.aaron.inflearnanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aaron.inflearnanimation.ui.example.Ex1_12
import com.aaron.inflearnanimation.ui.screen.MainScreen
import com.aaron.inflearnanimation.ui.theme.InflearnAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InflearnAnimationTheme {
                MainScreen()
            }
        }
    }
}