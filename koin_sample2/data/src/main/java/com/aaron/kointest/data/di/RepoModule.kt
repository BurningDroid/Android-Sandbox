package com.aaron.kointest.data.di

import com.aaron.kointest.data.repo.GreetingRepo
import com.aaron.kointest.data.repo.impl.GreetingRepository
import com.aaron.kointest.data.source.SettingsDataSource
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent.get

@Module
class RepoModule {

    @Single
    fun provideGreetingRepo(): GreetingRepo {
        return GreetingRepository(get(SettingsDataSource::class.java))
    }
}
