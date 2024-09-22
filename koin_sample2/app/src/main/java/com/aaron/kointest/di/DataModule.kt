package com.aaron.kointest.di

import com.aaron.kointest.data.GreetingRepo
import com.aaron.kointest.data.impl.GreetingRepository
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DataModule {

    @Single
    fun provideGreetingRepo(): GreetingRepo {
        return GreetingRepository()
    }
}
