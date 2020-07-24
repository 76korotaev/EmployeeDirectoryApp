package com.korot.testapplication.domain.repository

import com.korot.testapplication.domain.model.Auth

interface PersistentRepository {

    fun getAuth(): Auth?

    fun saveAuth(auth: Auth)

    fun clearAuth()
}