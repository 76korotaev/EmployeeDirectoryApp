package com.korot.testapplication.ui

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.korot.testapplication.R
import com.korot.testapplication.ui.base.*
import com.korot.testapplication.ui.base.utils.PermissionManager

class MainActivity : AppCompatActivity(), BaseActivity {

    private val currentBackStack: String = "MAIN"

    lateinit var progress : ProgressBar
    lateinit var viewMain: View
    val fragmentProvider = FragmentProvider(this)
    private val model = MainViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress = findViewById(R.id.view_main_progress)
        viewMain = findViewById(R.id.view_main)
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

    override fun onError(text: String, callback: (() -> Unit)?) {
        val snackbar = Snackbar.make(viewMain,text, Snackbar.LENGTH_LONG)
        callback?.let {callBack ->
            snackbar.setAction("Повторить") {callBack.invoke()}
        }
        snackbar.show()
    }

    override fun back() {
        onBackPressed()
    }

    override fun logout() {
        model.logout()
    }

}