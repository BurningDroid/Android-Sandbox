package com.aaron.sample.dragndroplist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var users by mutableStateOf((1..100).map { User(it, "Name: $it") })
        private set

    fun swapUser(from: Int, to: Int) {
        Log.d("TEST", "[test] swap - $from -> $to")
        users = users.toMutableList().apply {
            add(to, removeAt(from))
        }
    }

}