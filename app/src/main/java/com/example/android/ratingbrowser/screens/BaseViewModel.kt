package com.example.android.ratingbrowser.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

abstract class BaseViewModel(app: Application) : AndroidViewModel(app), KodeinAware {
    override val kodein by kodein()
}