package com.korot.testapplication.ui.base

import androidx.fragment.app.Fragment

abstract class BaseFragment(val loader: BaseActivity) : Fragment(){

    fun showLoading(){
        loader.onLoadingStart()
    }

    fun hideLoading(){
        loader.onLoadingStop()
    }


}