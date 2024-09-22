package com.aaron.kointest.data.repo.impl

import android.util.Log
import com.aaron.kointest.data.repo.GreetingRepo
import com.aaron.kointest.data.source.SettingsDataSource
import kotlinx.coroutines.delay
import org.koin.core.annotation.Single

@Single
class GreetingRepository: GreetingRepo {

    init {
        Log.w(TAG, "[test] init")
    }

    override suspend fun getGreeting(): String {
        Log.w(TAG, "[test] getGreeting")
        delay(2000)
        return "Koin"
    }

    companion object {
        private const val TAG = "GreetingRepository"
    }
}