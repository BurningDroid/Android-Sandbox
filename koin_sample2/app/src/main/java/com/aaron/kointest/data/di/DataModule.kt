package com.aaron.kointest.data.di

import com.aaron.kointest.data.repo.GreetingRepo
import com.aaron.kointest.data.repo.impl.GreetingRepository
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DataModule {

    @Single
    fun provideGreetingRepo(): GreetingRepo {
        return GreetingRepository()
    }
}
