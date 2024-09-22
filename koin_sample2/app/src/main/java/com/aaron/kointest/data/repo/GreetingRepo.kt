package com.aaron.kointest.data.repo

interface GreetingRepo {
    suspend fun getGreeting(): String
}