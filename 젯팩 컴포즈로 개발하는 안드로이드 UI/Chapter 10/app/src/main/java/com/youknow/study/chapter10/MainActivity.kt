package com.youknow.study.chapter10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.youknow.study.chapter10.ui.screen.BoxButtonDemo
import com.youknow.study.chapter10.ui.screen.ImageDemo
import com.youknow.study.chapter10.ui.screen.SimpleButtonDemo
import com.youknow.study.chapter10.ui.theme.Chapter10TestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Chapter10TestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    SimpleButtonDemo()
//                    ImageDemo()
                    BoxButtonDemo()
                }
            }
        }
    }
}