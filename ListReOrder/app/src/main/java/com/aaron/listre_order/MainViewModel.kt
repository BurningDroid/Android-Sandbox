package com.aaron.listre_order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var items: List<Item> by mutableStateOf((1..20).map { Item(it.toLong(), "Item $it") })
        private set

}