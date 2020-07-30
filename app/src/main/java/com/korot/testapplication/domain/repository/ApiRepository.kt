package com.korot.testapplication.domain.repository

import com.korot.testapplication.domain.model.Auth
import com.korot.testapplication.network.apimodel.EmployeeResponse
import com.korot.testapplication.network.apimodel.OrganizationResponse
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.Exception

interface ApiRepository {

    suspend fun checkLogin() : ApiCall<Boolean>

    suspend fun getOrganization(): ApiCall<OrganizationResponse>
}

class ApiCall<I>(val body: I? = null,val error: String? = null)