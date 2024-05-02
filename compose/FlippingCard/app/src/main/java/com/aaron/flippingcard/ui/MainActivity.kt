package com.aaron.flippingcard.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.aaron.flippingcard.ui.page.MainScreen
import com.aaron.flippingcard.ui.page.MainViewModel
import com.aaron.flippingcard.ui.theme.FlippingCardTheme

class MainActivity : ComponentActivity() {

    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlippingCardTheme {
                MainScreen(vm = vm)
            }
        }
    }
}