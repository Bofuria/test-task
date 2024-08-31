package com.example.task

import android.app.Application
import com.example.task.retrofit.ApiService
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}