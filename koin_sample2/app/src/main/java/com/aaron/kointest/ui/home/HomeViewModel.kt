package com.aaron.kointest.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    var greeting: String by mutableStateOf("Android")
        private set

    init {
        viewModelScope.launch {
            delay(2_000)
            greeting = "Koin"
        }
    }
}