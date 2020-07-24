package com.korot.testapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import com.korot.testapplication.R

class MainActivity : AppCompatActivity() {

    lateinit var progress : ProgressBar
    private val model = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress = findViewById(R.id.view_main_progress)

        model.loginObserver.observe(this, Observer {
            if (it){
                //START APPS
            } else {
                //START LOGIN
            }
        })

        model.load()
    }

}