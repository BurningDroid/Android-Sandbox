package com.aaron.kointest.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaron.kointest.domain.usecase.UpdateGreeting
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DashboardViewModel(
    private val updateGreeting: UpdateGreeting
): ViewModel() {

    private val random: String
        get() = listOf("Android", "Kotlin", "Compose", "KMP", "Hilt", "Koin", "Google", "Jetbrain").random()

    fun onClick() {
        viewModelScope.launch {
            updateGreeting.update(random)
        }
    }
}