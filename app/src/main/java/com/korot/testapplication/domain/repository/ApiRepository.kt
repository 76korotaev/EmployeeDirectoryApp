package com.korot.testapplication.domain.repository

import com.korot.testapplication.domain.model.Auth
import com.korot.testapplication.network.apimodel.EmployeeResponse
import com.korot.testapplication.network.apimodel.OrganizationResponse
import io.reactivex.Completable
import io.reactivex.Single

interface ApiRepository {

    fun checkLogin(auth: Auth) : Completable

    fun getOrganization(auth: Auth): Single<OrganizationResponse>
}