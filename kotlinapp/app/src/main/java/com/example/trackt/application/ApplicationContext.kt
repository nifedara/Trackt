package com.example.trackt.application

import android.app.Application
import com.example.trackt.data.AppViewModelProvider

class ApplicationContext : Application() {

    override fun onCreate() {
        super.onCreate()

        AppViewModelProvider.initialize(this)
    }
}