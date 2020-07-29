package com.korot.testapplication.network

import com.korot.testapplication.domain.repository.PersistentRepository
import okhttp3.Credentials
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(val repository: PersistentRepository): Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val auth = repository.getAuth()
        val originalHttpUrl: HttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("login", auth?.login ?: "")
            .addQueryParameter("password", auth?.password ?: "")
            .build()

        val request = original.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }

}