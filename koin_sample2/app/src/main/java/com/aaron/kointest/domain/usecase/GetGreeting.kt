package com.aaron.kointest.domain.usecase

interface GetGreeting {
    suspend fun get(): String
}