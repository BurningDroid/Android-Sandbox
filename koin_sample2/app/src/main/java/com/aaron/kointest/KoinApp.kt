package com.aaron.kointest

import android.app.Application
import com.aaron.kointest.di.DataModule
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module

class KoinApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                DataModule().module,
                defaultModule
            )
        }
    }
}