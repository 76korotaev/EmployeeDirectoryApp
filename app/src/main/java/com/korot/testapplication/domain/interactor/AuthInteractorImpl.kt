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
        val instance = AuthInteractorImpl(PersistentRepositoryImpl(), ApiRepositoryImpl())
    }

    companion object{
        val instance : AuthInteractor by lazy { Holder.instance }
    }

    override fun logIn(login: String, password: String) : Completable {
        val auth = Auth(login, password)
        repository.saveAuth(auth)
        return apiRepository.checkLogin()
    }

    override fun logOut(): Completable {
        return Completable.create {
            repository.clearAuth()
            it.onComplete()
        }
    }

    override fun checkLogin(): Single<Boolean> {
        return Single.create<Boolean> { emitter ->
                apiRepository.checkLogin()
                    .subscribe (
                        {
                            emitter.onSuccess(true)
                        },{
                            emitter.onError(it)
                        }
                    )
        }
    }

    override fun getCurrentLogin(): Single<String> {
        return Single.create{emitter ->
            val login = repository.getAuth()?.login
            if (login != null){
                emitter.onSuccess(login)
            } else {
                emitter.onError(Exception("Login is empty"))
            }
        }
    }

}