package com.korot.testapplication.domain.interactor

import io.reactivex.Completable
import io.reactivex.Single

interface AuthInteractor {

    fun logIn(login: String, password: String): Completable

    fun logOut(): Completable

    fun checkLogin(): Single<Boolean>

}