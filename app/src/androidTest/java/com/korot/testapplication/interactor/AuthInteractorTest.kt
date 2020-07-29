package com.korot.testapplication.interactor

import com.korot.testapplication.domain.interactor.AuthInteractor
import com.korot.testapplication.domain.interactor.AuthInteractorImpl
import com.korot.testapplication.domain.model.Auth
import com.korot.testapplication.domain.repository.ApiRepository
import com.korot.testapplication.domain.repository.ApiRepositoryImpl
import com.korot.testapplication.domain.repository.PersistentRepository
import com.korot.testapplication.domain.repository.PersistentRepositoryImpl
import com.korot.testapplication.network.apimodel.OrganizationResponse
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class AuthInteractorTest {

    private val correctLogin = "test_user"
    private val correctPassword = "test_pass"
    private val incorrectLogin = "123"
    private val incorrectPassword = "123"
    private val exception = Exception("Не верный логин или пароль")

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
            override fun checkLogin(): Completable {
                return Completable.create {
                    emitter ->
                        val auth = persistentRepository.getAuth()
                        val login = auth?.login ?: ""
                        val pass = auth?.password ?: ""
                        if (login == correctLogin && pass == correctPassword){
                            emitter.onComplete()
                        } else {
                            emitter.onError(exception)
                        }
                }
            }

            override fun getOrganization(): Single<OrganizationResponse> {
                return Single.just(OrganizationResponse())
            }

        }
        interactor = AuthInteractorImpl(persistentRepository, apiRepository)
    }


    @Test
    fun loginTest(){
        try {
            val isCurrentlogin = interactor.checkLogin().blockingGet()
            Assert.assertEquals(isCurrentlogin, false)
        } catch (e: Exception){
            assert(true)
        }

        interactor.logIn(incorrectLogin, incorrectPassword).blockingGet()

        interactor.logIn(correctLogin, correctPassword).blockingGet()

        val correct = interactor.checkLogin().blockingGet()
        Assert.assertEquals(correct, true)

    }
}