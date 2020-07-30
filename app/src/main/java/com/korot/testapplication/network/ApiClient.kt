package com.korot.testapplication.network

import android.content.Context
import androidx.core.app.CoreComponentFactory
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.korot.testapplication.domain.repository.PersistentRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val timeOut = 10000L
    private val baseUrl = "https://contact.taxsee.com/Contacts.svc/"

    fun getClient(): Api {
        val builder = OkHttpClient.Builder()
            .connectTimeout(timeOut, TimeUnit.MILLISECONDS)
            .readTimeout(timeOut, TimeUnit.MILLISECONDS)


        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY



        builder.addInterceptor(loggingInterceptor)
        builder.addInterceptor(getAuthInterceptor())

        val gson = GsonBuilder()
            .create()


        val httpClient = builder.build()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .client(httpClient)
            .build()
        return retrofit.create<Api>(Api::class.java)

    }

    private fun getAuthInterceptor():AuthInterceptor{
        val repo = PersistentRepositoryImpl()
        return AuthInterceptor(repo)
    }


}