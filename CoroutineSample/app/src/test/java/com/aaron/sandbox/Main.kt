package com.aaron.sandbox

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.runBlocking

suspend fun main(): Unit = runBlocking {
    val api = FakeUserApi()
    val users = allUsersFlow(api)
    users
        .onStart {
            println("show progress")
        }
        .onCompletion {
            println("hide progress")
        }
        .onEach {
        println("onEach: $it")
    }.collect {
        println("collect")
    }
}

fun allUsersFlow(api: UserApi): Flow<User> = channelFlow {
    var page = 0
    do {
        val users = api.takePage(page++)
//        emitAll(users.asFlow())
        users.forEach { send(it) }
    } while (!users.isNullOrEmpty())
}


data class User(val name: String)

interface UserApi {
    suspend fun takePage(pageNumber: Int): List<User>
}

class FakeUserApi: UserApi {
    private val users = (0..20).map { User("User_$it") }
    private val pageSize = 1

    override suspend fun takePage(pageNumber: Int): List<User> {
        delay(100L)
        return users.drop(pageSize * pageNumber)
            .take(pageSize)
    }
}