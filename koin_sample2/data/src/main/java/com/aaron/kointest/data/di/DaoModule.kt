package com.aaron.kointest.data.di

import android.content.Context
import com.aaron.kointest.data.source.SettingsDataSource
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DaoModule {
    @Single
    fun provideSettingsDao(context: Context): SettingsDataSource {
        return SettingsDataSource(context.getSharedPreferences("koin-sample", Context.MODE_PRIVATE))
    }
}