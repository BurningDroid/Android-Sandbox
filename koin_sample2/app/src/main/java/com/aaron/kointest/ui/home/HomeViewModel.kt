package com.aaron.kointest.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaron.kointest.data.GreetingRepo
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val greetingRepo: GreetingRepo
): ViewModel() {

    var greeting: String by mutableStateOf("Android")
        private set

    init {
        viewModelScope.launch {
            greeting = greetingRepo.getGreeting()
        }
    }
}