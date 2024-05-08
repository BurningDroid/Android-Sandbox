package com.aaron.annotationtest.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aaron.annotationtest.ui.main.model.StableData
import kotlinx.coroutines.Job

class MainViewModel: ViewModel() {

    private var loopJob: Job? = null

    var stableData: StableData by mutableStateOf(StableData())
        private set

    fun onClickInc() {
//        if (loopJob != null) {
//            return
//        }
//
//        loopJob = viewModelScope.launch {
//            while(true) {
//                delay(1000)
//                stableData = stableData.copy(progress = stableData.progress + 1)
//            }
//        }
        stableData = stableData.copy(progress = stableData.progress + 1)
    }

    fun onClickDec() {
//        loopJob?.cancel()
//        loopJob = null

        stableData = stableData.copy(progress = stableData.progress - 1)
    }
}