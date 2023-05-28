package com.delirium.newsline

import android.app.Application
import com.delirium.newsline.di.appModule
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(appModule))
        }
    }
}