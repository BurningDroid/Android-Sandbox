package com.aaron.kointest.domain.usecase

interface UpdateGreeting {
    suspend fun update(value: String): String
}