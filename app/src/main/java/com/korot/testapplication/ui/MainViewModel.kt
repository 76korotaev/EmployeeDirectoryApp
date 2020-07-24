package com.korot.testapplication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.korot.testapplication.domain.LoadTransformer
import com.korot.testapplication.domain.interactor.AuthInteractor
import com.korot.testapplication.domain.interactor.AuthInteractorImpl
import com.korot.testapplication.ui.base.BaseViewModel
import com.korot.testapplication.ui.base.LoaderInterface

class MainViewModel(loader: LoaderInterface): BaseViewModel(loader) {

    private val loginController = MutableLiveData<Boolean>()
    val loginObserver = loginController

    val authInteractor : AuthInteractor by lazy { AuthInteractorImpl.instance }

    fun load(){
        compositDisposable.add(
            authInteractor.checkLogin()
                .compose(LoadTransformer(loader))
                .subscribe({
                    loginController.value = true
                },{
                    loginController.value = false
                })
        )
    }


}