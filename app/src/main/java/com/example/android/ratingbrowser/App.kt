package com.example.android.ratingbrowser

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class App : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(androidXModule(this@App))
        import(mainModule)
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}