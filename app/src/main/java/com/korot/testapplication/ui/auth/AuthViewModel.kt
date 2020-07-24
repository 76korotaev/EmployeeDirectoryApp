package com.korot.testapplication.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.korot.testapplication.domain.LoadTransformer
import com.korot.testapplication.domain.interactor.AuthInteractorImpl
import com.korot.testapplication.ui.base.BaseViewModel
import com.korot.testapplication.ui.base.LoaderInterface
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthViewModel(loader: LoaderInterface): BaseViewModel(loader){

    private val controller = MutableLiveData<Boolean>()
    val observer: LiveData<Boolean> = controller

    val interactor = AuthInteractorImpl.instance

    fun login(login: String, password: String) {
        compositDisposable.add(
            interactor.logIn(login, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loader.onLoadingStart() }
                .doOnTerminate { loader.onLoadingStop() }
                .doOnError { loader.onError(it.message ?: ""){login(login, password)} }
                .subscribe({
                    controller.value = true
                },{
                    onError(it)
                })
        )
    }
}