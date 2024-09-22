package com.aaron.kointest.data.impl

import com.aaron.kointest.data.GreetingRepo
import kotlinx.coroutines.delay
import org.koin.core.annotation.Single

@Single
class GreetingRepository: GreetingRepo {
    override suspend fun getGreeting(): String {
        delay(2000)
        return "Koin"
    }
}