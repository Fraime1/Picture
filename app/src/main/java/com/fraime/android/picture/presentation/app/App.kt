package com.fraime.android.picture.presentation.app

import android.app.Application
import com.fraime.android.picture.presentation.di.appModule
import com.fraime.android.picture.presentation.di.dataModule
import com.fraime.android.picture.presentation.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
//            androidLogger()
            androidContext(this@App)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}