package com.korot.testapplication.domain.interactor

import com.korot.testapplication.domain.model.Auth
import com.korot.testapplication.domain.repository.ApiRepository
import com.korot.testapplication.domain.repository.ApiRepositoryImpl
import com.korot.testapplication.domain.repository.PersistentRepository
import com.korot.testapplication.domain.repository.PersistentRepositoryImpl
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.Exception

class AuthInteractorImpl(val repository: PersistentRepository, val apiRepository: ApiRepository) : AuthInteractor {

    private object Holder {
        val instance = AuthInteractorImpl(PersistentRepositoryImpl.instance, ApiRepositoryImpl())
    }

    companion object{
        val instance : AuthInteractor by lazy { Holder.instance }
    }

    override fun logIn(login: String, password: String) : Completable {
        val auth = Auth(login, password)
        return apiRepository.checkLogin(auth)
            .doOnComplete {
                repository.saveAuth(auth)
            }
    }

    override fun logOut(): Completable {
        return Completable.create {
            repository.clearAuth()
            it.onComplete()
        }
    }

    override fun checkLogin(): Single<Boolean> {
        return Single.create<Boolean> { emitter ->
            val auth : Auth? = repository.getAuth()
            if (auth != null){
                apiRepository.checkLogin(auth = auth)
                    .subscribe (
                        {
                            emitter.onSuccess(true)
                        },{
                            emitter.onError(it)
                        }
                    )
            } else {
                emitter.onError(Exception("LOGIN is empty"))
            }
        }
    }

}