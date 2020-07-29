package com.korot.testapplication.network

import com.korot.testapplication.network.apimodel.LoginResponse
import com.korot.testapplication.network.apimodel.OrganizationResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("Hello")
    fun login():Single<LoginResponse>


    @GET("GetAll")
    fun getAll():Single<OrganizationResponse>

    @GET("GetWPhoto")
    fun getPhoto():Single<Any>

}