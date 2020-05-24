package com.example.android.ratingbrowser

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.serialization.UnstableDefault
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    @UnstableDefault
    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(mainModule)
        }
    }
}