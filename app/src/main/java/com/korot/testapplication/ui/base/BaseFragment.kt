package com.korot.testapplication.ui.base

import android.opengl.Visibility
import android.view.View
import androidx.fragment.app.Fragment
import com.korot.testapplication.ui.MainActivity

abstract class BaseFragment(val loader: ConsumerFragmentProvider) : Fragment(){

    fun showLoading(){
        loader.onLoadingStart()
    }

    fun hideLoading(){
        loader.onLoadingStop()
    }


}