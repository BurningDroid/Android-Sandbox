package com.aaron.kointest.data

interface GreetingRepo {
    suspend fun getGreeting(): String
}