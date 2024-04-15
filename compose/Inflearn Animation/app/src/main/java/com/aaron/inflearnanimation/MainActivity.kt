package com.aaron.inflearnanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aaron.inflearnanimation.ui.screen.section2.MainScreen
import com.aaron.inflearnanimation.ui.screen.section3.Section3Screen
import com.aaron.inflearnanimation.ui.theme.InflearnAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InflearnAnimationTheme {
//                MainScreen()
                Section3Screen()
            }
        }
    }
}