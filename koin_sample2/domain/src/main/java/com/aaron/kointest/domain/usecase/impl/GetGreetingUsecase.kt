package com.aaron.kointest.domain.usecase.impl

import android.util.Log
import com.aaron.kointest.data.repo.GreetingRepo
import com.aaron.kointest.domain.usecase.GetGreeting
import org.koin.core.annotation.Single

@Single
class GetGreetingUsecase(
    private val greetingRepo: GreetingRepo
): GetGreeting {

    init {
        Log.w(TAG, "[test] init")
    }

    override suspend fun get(): String {
        Log.w(TAG, "[test] get")
        return greetingRepo.getGreeting()
    }

    companion object {
        private const val TAG = "GetGreetingUsecase"
    }
}