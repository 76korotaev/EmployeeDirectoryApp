package com.korot.testapplication.domain.repository

import com.korot.testapplication.network.apimodel.OrganizationResponse

interface ApiRepository {

    suspend fun checkLogin(): ApiCall<Boolean>

    suspend fun getOrganization(): ApiCall<OrganizationResponse>
}

class ApiCall<I>(val body: I? = null, val error: String? = null)