package com.aaron.kointest

import android.app.Application
import com.aaron.kointest.data.di.DaoModule
import com.aaron.kointest.data.di.RepoModule
import com.aaron.kointest.domain.di.DomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module

class KoinApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@KoinApp)
            modules(
                DaoModule().module,
                RepoModule().module,
                DomainModule().module,
                defaultModule
            )
        }
    }
}