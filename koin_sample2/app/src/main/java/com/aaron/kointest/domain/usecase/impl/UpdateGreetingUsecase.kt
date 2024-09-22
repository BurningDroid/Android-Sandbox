package com.aaron.kointest.domain.usecase.impl

import android.util.Log
import com.aaron.kointest.data.repo.GreetingRepo
import com.aaron.kointest.domain.usecase.UpdateGreeting
import org.koin.core.annotation.Single

@Single
class UpdateGreetingUsecase(
    private val greetingRepo: GreetingRepo
): UpdateGreeting {

    init {
        Log.w(TAG, "[test] init")
    }

    override suspend fun update(value: String): String {
        Log.w(TAG, "[test] update - $value")
        return greetingRepo.updateGreeting(value)
    }

    companion object {
        private const val TAG = "UpdateGreetingUsecase"
    }
}