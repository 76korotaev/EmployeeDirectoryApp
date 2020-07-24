package com.korot.testapplication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.korot.testapplication.domain.LoadTransformer
import com.korot.testapplication.domain.interactor.AuthInteractor
import com.korot.testapplication.domain.interactor.AuthInteractorImpl
import com.korot.testapplication.ui.base.BaseViewModel

class MainViewModel: BaseViewModel() {

    private val loginController = MutableLiveData<Boolean>()
    val loginObserver = loginController

    private val authInteractor : AuthInteractor = AuthInteractorImpl.instance

    fun load(){
        compositDisposable.add(
            authInteractor.checkLogin()
                .compose(LoadTransformer())
                .subscribe({
                    loginController.value = it
                },{
                    onError(it)
                })
        )
    }


}