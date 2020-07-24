package com.korot.testapplication.domain.repository

import com.korot.testapplication.domain.model.Auth
import com.korot.testapplication.network.ApiClient
import com.korot.testapplication.network.apimodel.EmployeeResponse
import com.korot.testapplication.network.apimodel.OrganizationResponse
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.Exception

class ApiRepositoryImpl : ApiRepository{

    val client = ApiClient.getClient()

    override fun checkLogin(auth: Auth): Completable {
        return client.login(auth.login, auth.password)
            .flatMapCompletable {
                if (it.isSuccess) Completable.complete()
                else Completable.error(Exception(it.message))
            }
    }

    override fun getOrganization(auth: Auth): Single<OrganizationResponse> {
        return client.getAll(auth.login, auth.password)
    }

}