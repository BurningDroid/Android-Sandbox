package com.aaron.kointest.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaron.kointest.domain.usecase.GetGreeting
import com.aaron.kointest.domain.usecase.UpdateGreeting
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val getGreeting: GetGreeting,
    private val updateGreeting: UpdateGreeting
): ViewModel() {

    private val random: String
        get() = listOf("Android", "Kotlin", "Compose", "KMP", "Hilt", "Koin", "Google", "Jetbrain").random()

    var greeting: String by mutableStateOf("Android")
        private set

    init {
        viewModelScope.launch {
            greeting = getGreeting.get()
        }
    }

    fun onClick() {
        viewModelScope.launch {
            greeting = updateGreeting.update(random)
        }
    }
}