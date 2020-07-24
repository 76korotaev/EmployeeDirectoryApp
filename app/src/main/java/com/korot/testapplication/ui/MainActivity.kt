package com.korot.testapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.korot.testapplication.R
import com.korot.testapplication.ui.auth.AuthFragment
import com.korot.testapplication.ui.base.BaseFragment
import com.korot.testapplication.ui.base.ConsumerFragmentProvider
import com.korot.testapplication.ui.base.FragmentProvider
import com.korot.testapplication.ui.base.LoaderInterface
import com.korot.testapplication.ui.department.DepartmentFragment

class MainActivity : AppCompatActivity(), ConsumerFragmentProvider {

    private val currentBackStack: String = "MAIN"

    lateinit var progress : ProgressBar
    val fragmentProvider = FragmentProvider(this)
    private val model = MainViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress = findViewById(R.id.view_main_progress)

        model.loginObserver.observe(this, Observer {
            if (it){
               fragmentProvider.startDepartment()
            } else {
                fragmentProvider.startAuth()
            }
        })
        start()
    }


    fun start(){
        model.load()
    }

    override fun startFragment(fragment: BaseFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.view_fragment, fragment)
        transaction.replace(R.id.view_fragment, fragment)
        transaction.commit()
    }

    override fun addFragment(fragment: BaseFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.view_fragment, fragment)
        transaction.addToBackStack(currentBackStack)
        transaction.commit()
    }

    override fun getProvider(): FragmentProvider {
        return fragmentProvider
    }

    override fun onLoadingStart() {
        progress.visibility = View.VISIBLE
    }

    override fun onLoadingStop() {
        progress.visibility = View.GONE
    }

}