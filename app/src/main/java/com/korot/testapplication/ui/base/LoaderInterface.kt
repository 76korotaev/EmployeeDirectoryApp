package com.korot.testapplication.ui.base

interface LoaderInterface {
    fun onLoadingStart()
    fun onLoadingStop()
    fun onError(text: String, callback: (() -> Unit)? = null)
}