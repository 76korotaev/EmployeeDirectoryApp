package com.korot.testapplication.network

import com.korot.testapplication.network.apimodel.LogiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/Hello")
    fun login(
        @Query("login") login: String,
        @Query("password") password: String
    ):Single<LogiResponse>


}