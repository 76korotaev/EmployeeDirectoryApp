package com.korot.testapplication.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception

abstract class BaseViewModel: ViewModel() {

    val compositDisposable: CompositeDisposable = CompositeDisposable()

    protected val errorController  = MutableLiveData<String>()
    val errorObserver : LiveData<String> = errorController

    override fun onCleared() {
        compositDisposable.dispose()
        super.onCleared()
    }

    fun onError(exception: Throwable){
        errorController.value = exception.message
    }

}