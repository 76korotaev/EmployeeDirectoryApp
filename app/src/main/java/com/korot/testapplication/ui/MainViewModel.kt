package com.korot.testapplication.ui

import androidx.lifecycle.MutableLiveData
import com.korot.testapplication.domain.LoadTransformer
import com.korot.testapplication.domain.interactor.AuthInteractor
import com.korot.testapplication.domain.interactor.AuthInteractorImpl
import com.korot.testapplication.ui.base.BaseViewModel
import com.korot.testapplication.ui.base.LoaderInterface
import io.reactivex.Completable

class MainViewModel(loader: LoaderInterface): BaseViewModel(loader) {

    private val loginController = MutableLiveData<Boolean>()
    val loginObserver = loginController

    val authInteractor : AuthInteractor by lazy { AuthInteractorImpl.instance }

    fun load(){
        compositDisposable.add(
            authInteractor.checkLogin()
                .compose(LoadTransformer(loader){load()})
                .subscribe({
                    loginController.value = true
                },{
                    loginController.value = false
                })
        )
    }

    fun logout(){
        compositDisposable.add(
            authInteractor.logOut()
                .compose(LoadTransformer<Completable>(loader){
                    load()
                })
                .subscribe {
                    loginController.value = false
                }
        )
    }


}