package com.aaron.annotationtest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aaron.annotationtest.ui.main.MainScreen
import com.aaron.annotationtest.ui.theme.AnnotationTestTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnnotationTestTheme {
                MainScreen()
            }
        }
    }
}