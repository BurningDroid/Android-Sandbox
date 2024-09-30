package com.aaron.listre_order

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var items: List<Item> by mutableStateOf((1..20).map { Item(it.toLong(), "Item $it") })
        private set

    fun onClickDone() {
        items.forEach {
            Log.w("TEST", "[test] $it")
        }
    }

    fun onMove(from: Int, to: Int) {
        Log.w("TEST", "[test] onMove: $from -> $to")
        items = items.toMutableList().apply {
            add(to, removeAt(from))
        }
    }


}