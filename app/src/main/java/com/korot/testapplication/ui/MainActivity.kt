package com.korot.testapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.korot.testapplication.R
import com.korot.testapplication.ui.auth.AuthFragment

class MainActivity : AppCompatActivity() {

    lateinit var progress : ProgressBar
    lateinit var viewFragment: Fragment
    private val model = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress = findViewById(R.id.view_main_progress)

        model.loginObserver.observe(this, Observer {
            if (it){
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(R.id.view_fragment, AuthFragment())
                transaction.commit()
            } else {
                //START LOGIN
            }
        })

        model.load()
    }

}