package com.aaron.kointest.domain.di

import com.aaron.kointest.data.repo.GreetingRepo
import com.aaron.kointest.domain.usecase.GetGreeting
import com.aaron.kointest.domain.usecase.UpdateGreeting
import com.aaron.kointest.domain.usecase.impl.GetGreetingUsecase
import com.aaron.kointest.domain.usecase.impl.UpdateGreetingUsecase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.java.KoinJavaComponent.get

@Module
class DomainModule {

    @Single
    fun provideGetGreeting(): GetGreeting {
        return GetGreetingUsecase(get(GreetingRepo::class.java))
    }

    @Single
    fun provideUpdateGreeting(): UpdateGreeting {
        return UpdateGreetingUsecase(get(GreetingRepo::class.java))
    }
}