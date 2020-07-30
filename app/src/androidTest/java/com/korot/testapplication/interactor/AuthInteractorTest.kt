package com.korot.testapplication.interactor

import android.util.Log
import com.korot.testapplication.domain.interactor.AuthInteractor
import com.korot.testapplication.domain.interactor.AuthInteractorImpl
import com.korot.testapplication.domain.model.Auth
import com.korot.testapplication.domain.repository.*
import com.korot.testapplication.network.apimodel.OrganizationResponse
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class AuthInteractorTest {

    private val correctLogin = "test_user"
    private val correctPassword = "test_pass"
    private val incorrectLogin = "123"
    private val incorrectPassword = "123"
    private val exception = "Не верный логин или пароль"

    private lateinit var interactor : AuthInteractor
    private lateinit var apiRepository : ApiRepository
    private lateinit var persistentRepository: PersistentRepository

    @Before
    fun setUp(){
        apiRepository = ApiRepositoryImpl()
        persistentRepository = object : PersistentRepository{

            private var auth : Auth? = null

            override fun getAuth(): Auth? {
                return auth
            }

            override fun saveAuth(auth: Auth) {
                this.auth = auth
            }

            override fun clearAuth() {
                auth = null
            }

        }
        apiRepository = object : ApiRepository{
            override suspend fun checkLogin(): ApiCall<Boolean> {
                return GlobalScope.async {
                    val auth = persistentRepository.getAuth()
                    val login = auth?.login ?: ""
                    val pass = auth?.password ?: ""
                    if (login == correctLogin && pass == correctPassword){
                        ApiCall(true)
                    } else {
                        ApiCall(error = exception)
                    }
                }.await()
            }

            override suspend fun getOrganization(): ApiCall<OrganizationResponse> {
                return GlobalScope.async {
                    ApiCall(OrganizationResponse())
                }.await()
            }

        }
        interactor = AuthInteractorImpl(persistentRepository, apiRepository)
    }


    @Test
    fun loginTest(){
        runBlocking {
            val isCurrentlogin = interactor.checkLogin()
            Assert.assertNull(isCurrentlogin.body)
            Assert.assertNotNull(isCurrentlogin.error)
            val correctLogin = interactor.logIn(correctLogin, correctPassword)
            Assert.assertEquals(correctLogin.body, true)
            val correct = interactor.checkLogin()
            Assert.assertEquals(correct.body, true)
        }
    }
}