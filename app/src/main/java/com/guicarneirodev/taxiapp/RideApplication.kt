package com.guicarneirodev.taxiapp

import android.app.Application
import com.guicarneirodev.taxiapp.domain.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class RideApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RideApplication)
            modules(appModule)
        }
    }
}