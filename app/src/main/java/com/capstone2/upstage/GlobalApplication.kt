package com.capstone2.upstage

import android.app.Application
import com.capstone2.util.LoggerUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

    }

    private fun printStartingLog() =
        LoggerUtil.v(this.getString(R.string.app_name) + " Start!")
}