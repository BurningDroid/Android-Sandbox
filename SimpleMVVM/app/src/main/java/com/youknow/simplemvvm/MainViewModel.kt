package com.youknow.simplemvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val _name = MutableLiveData("Ada")
    private val _lastName = MutableLiveData("Lovelace")
    private val _count =  MutableLiveData(0)

    val name: LiveData<String> = _name
    val lastName: LiveData<String> = _lastName
    val count: LiveData<Int> = _count

    fun increaseCount() {
        _count.value = (_count.value ?: 0) + 1
        Log.d("TEST", "increaseCount: $count")
    }

}