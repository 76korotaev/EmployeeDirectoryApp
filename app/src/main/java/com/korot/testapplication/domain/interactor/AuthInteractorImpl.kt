package com.korot.testapplication.domain.interactor

import com.korot.testapplication.domain.model.Auth
import com.korot.testapplication.domain.repository.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AuthInteractorImpl(val repository: PersistentRepository, val apiRepository: ApiRepository) :
    AuthInteractor {

    private object Holder {
        val instance = AuthInteractorImpl(PersistentRepositoryImpl(), ApiRepositoryImpl())
    }

    companion object {
        val instance: AuthInteractor by lazy { Holder.instance }
    }

    override suspend fun logIn(login: String, password: String): ApiCall<Boolean> {
        val auth = Auth(login, password)
        repository.saveAuth(auth)
        return apiRepository.checkLogin()
    }

    override suspend fun logOut(): ApiCall<Boolean> {
        return GlobalScope.async {
            repository.clearAuth()
            ApiCall(false)
        }.await()
    }

    override suspend fun checkLogin(): ApiCall<Boolean> {
        return apiRepository.checkLogin()
    }

    override suspend fun getCurrentLogin(): ApiCall<String> {
        val login = repository.getAuth()?.login
        return GlobalScope.async {
            if (login == null) {
               ApiCall(error ="Login is empty")
            } else {
                ApiCall(login)
            }
        }.await()
    }
}