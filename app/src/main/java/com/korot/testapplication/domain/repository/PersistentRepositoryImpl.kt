package com.korot.testapplication.domain.repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.korot.testapplication.TestApplication
import com.korot.testapplication.domain.model.Auth
import kotlin.math.log

class PersistentRepositoryImpl(val context: Context) : PersistentRepository{

    private val LOGIN = "LOGIN"
    private val PASSWORD = "PASSWORD"

    companion object{

        val instance : PersistentRepository by lazy {Holder.INSTANCE}
    }

    private object Holder {
        val INSTANCE = PersistentRepositoryImpl(context = TestApplication.application)
    }



    private val preferences = context.getSharedPreferences("TestApplicationPreference", Context.MODE_PRIVATE)

    override fun getAuth(): Auth? {
        val login = preferences.getString(LOGIN, null)
        val password = preferences.getString(PASSWORD, null)

        return if (login != null && password != null){
            Auth(login, password)
        } else {
            null
        }
    }

    override fun saveAuth(auth: Auth) {
        val prefEditor = preferences.edit()
        prefEditor.putString(LOGIN, auth.login)
        prefEditor.putString(PASSWORD, auth.password)
        prefEditor.apply()
    }

    override fun clearAuth() {
        val prefEdit = preferences.edit()
        prefEdit.remove(LOGIN)
        prefEdit.remove(PASSWORD)
        prefEdit.apply()
    }

}