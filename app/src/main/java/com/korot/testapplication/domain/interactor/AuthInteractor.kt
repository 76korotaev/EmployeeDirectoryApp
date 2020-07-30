package com.korot.testapplication.domain.interactor

import com.korot.testapplication.domain.repository.ApiCall
import io.reactivex.Completable
import io.reactivex.Single

interface AuthInteractor {

    suspend fun logIn(login: String, password: String): ApiCall<Boolean>

    suspend fun logOut(): ApiCall<Boolean>

    suspend fun checkLogin(): ApiCall<Boolean>

    suspend fun getCurrentLogin(): ApiCall<String>

}