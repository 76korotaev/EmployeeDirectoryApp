package com.korot.testapplication.domain.repository

import com.korot.testapplication.domain.model.Auth
import io.reactivex.Completable

interface ApiRepository {

    fun checkLogin(auth: Auth) : Completable
}