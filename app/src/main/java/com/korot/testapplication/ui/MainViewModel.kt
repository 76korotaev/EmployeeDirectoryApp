package com.korot.testapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.korot.testapplication.domain.LoadTransformer
import com.korot.testapplication.domain.interactor.AuthInteractor
import com.korot.testapplication.domain.interactor.AuthInteractorImpl
import com.korot.testapplication.ui.base.BaseViewModel
import com.korot.testapplication.ui.base.LoaderInterface
import io.reactivex.Completable
import kotlinx.coroutines.*

class MainViewModel(loader: LoaderInterface): BaseViewModel(loader) {

    private val loginController = MutableLiveData<Boolean>()
    val loginObserver: LiveData<Boolean> = loginController

    val authInteractor : AuthInteractor by lazy { AuthInteractorImpl.instance }

    fun load(){
        loader.onLoadingStart()
        scope.launch {
            val res = authInteractor.checkLogin()
            when{
                res.body != null ->loginController.postValue(res.body)
                res.error != null ->loginController.postValue(false)

            }
            withContext(Dispatchers.Main) {
                loader.onLoadingStop()
            }
        }
    }

    fun logout(){
        loader.onLoadingStart()
        scope.launch {
            val res = authInteractor.logOut()
            when{
                res.body != null ->loginController.postValue(false)
                res.error != null -> withContext(Dispatchers.Main) {
                    loader.onError(res.error){logout()}
                }
            }
            withContext(Dispatchers.Main) {
                loader.onLoadingStop()
            }
        }


    }


}