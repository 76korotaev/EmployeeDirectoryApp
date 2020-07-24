package com.korot.testapplication

import android.app.Application

class TestApplication :Application(){

    companion object {
        lateinit var application: TestApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        application = this

    }
}