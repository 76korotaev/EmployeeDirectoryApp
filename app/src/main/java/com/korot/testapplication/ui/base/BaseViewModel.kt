package com.korot.testapplication.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception

abstract class BaseViewModel(val loader: LoaderInterface): ViewModel() {

    val compositDisposable: CompositeDisposable = CompositeDisposable()

    protected val errorController  = MutableLiveData<String>()
    val errorObserver : LiveData<String> = errorController

    protected val loadingController = MutableLiveData<Boolean>()
    val loadingObserver : LiveData<Boolean> = loadingController

    override fun onCleared() {
        compositDisposable.dispose()
        super.onCleared()
    }

    fun onError(exception: Throwable){
        errorController.value = exception.message
    }

    fun loading(isLoading: Boolean){
        loadingController.value = isLoading
    }

}