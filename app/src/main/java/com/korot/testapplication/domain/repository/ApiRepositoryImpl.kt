package com.korot.testapplication.domain.repository

import com.korot.testapplication.network.ApiClient
import com.korot.testapplication.network.apimodel.OrganizationResponse

class ApiRepositoryImpl : ApiRepository {

    val client = ApiClient.getClient()

    override suspend fun checkLogin(): ApiCall<Boolean> {
        val response = client.login().await()
        response.body()?.let {
            if (it.isSuccess) {
                return ApiCall(true)
            } else {
                return ApiCall(error = it.message)
            }
        }
        return ApiCall(error = "Ошибка получения данных")
    }

    override suspend fun getOrganization(): ApiCall<OrganizationResponse> {
        val response = client.getAll().await()
        if (response.isSuccessful) {
            return ApiCall(response.body())
        } else {
            return ApiCall(error = "Ошибка получения данных")
        }
    }

}
