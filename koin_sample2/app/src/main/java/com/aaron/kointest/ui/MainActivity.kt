package com.aaron.kointest.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aaron.kointest.ui.home.HomePane
import com.aaron.kointest.ui.theme.KoinTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinTestTheme {
                HomePane()
            }
        }
    }
}
