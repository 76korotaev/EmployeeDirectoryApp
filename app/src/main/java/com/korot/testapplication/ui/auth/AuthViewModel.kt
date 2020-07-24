package com.korot.testapplication.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.korot.testapplication.domain.LoadTransformer
import com.korot.testapplication.domain.interactor.AuthInteractorImpl
import com.korot.testapplication.ui.base.BaseViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthViewModel: BaseViewModel(){

    private val controller = MutableLiveData<Boolean>()
    val observer: LiveData<Boolean> = controller

    val interactor = AuthInteractorImpl.instance

    fun login(login: String, password: String) {
        compositDisposable.add(
            interactor.logIn(login, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    controller.value = true
                },{
                    onError(it)
                })
        )
    }
}