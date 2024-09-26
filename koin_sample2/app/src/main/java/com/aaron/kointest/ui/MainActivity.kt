package com.aaron.kointest.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aaron.kointest.domain.usecase.GetGreeting
import com.aaron.kointest.ui.nav.MainNavHost
import com.aaron.kointest.ui.theme.KoinTestTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {

    private val getGreeting: GetGreeting = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        CoroutineScope(Dispatchers.IO).launch {
            val data = getGreeting.get()
            Log.w("MainActivity", "[test] onCreate - data: $data")
        }
        setContent {
            KoinTestTheme {
                MainNavHost()
            }
        }
    }
}
