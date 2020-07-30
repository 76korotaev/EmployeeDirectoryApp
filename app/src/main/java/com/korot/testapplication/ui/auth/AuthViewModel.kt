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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(loader: LoaderInterface): BaseViewModel(loader){

    private val controller = MutableLiveData<Boolean>()
    val observer: LiveData<Boolean> = controller

    val interactor = AuthInteractorImpl.instance

    fun login(login: String, password: String) {
        loader.onLoadingStart()
        GlobalScope.launch {
            val res = interactor.logIn(login, password)
            when{
                res.body != null -> controller.postValue(res.body)
                res.error != null ->
                    withContext(Dispatchers.Main) {
                        loader.onError(res.error){login(login, password)}
                    }

            }
            withContext(Dispatchers.Main) {
                loader.onLoadingStop()
            }
        }
    }
}