package com.aaron.listre_order

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var items: List<Item> = (1..20).map { Item(it.toLong(), "Item $it") }
        private set
}