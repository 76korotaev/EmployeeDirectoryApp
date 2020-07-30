package com.korot.testapplication.network

import com.korot.testapplication.network.apimodel.LoginResponse
import com.korot.testapplication.network.apimodel.OrganizationResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("Hello")
    fun login(): Deferred<Response<LoginResponse>>


    @GET("GetAll")
    fun getAll(): Deferred<Response<OrganizationResponse>>

    @GET("GetWPhoto")
    fun getPhoto(): Deferred<Response<Any>>

}