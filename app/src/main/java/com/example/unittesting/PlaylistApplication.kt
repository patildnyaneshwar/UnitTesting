package com.example.unittesting

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlaylistApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}