package com.korot.testapplication.interactor

import com.korot.testapplication.domain.interactor.EmployeeInteractor
import com.korot.testapplication.domain.interactor.EmployeeInteractorImpl
import com.korot.testapplication.domain.model.Auth
import com.korot.testapplication.domain.model.Department
import com.korot.testapplication.domain.model.Employee
import com.korot.testapplication.domain.repository.ApiRepository
import com.korot.testapplication.domain.repository.ApiRepositoryImpl
import com.korot.testapplication.domain.repository.PersistentRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class EmployeeInteractorTest {

    private val correctLogin = "test_user"
    private val correctPassword = "test_pass"

    private lateinit var interactor: EmployeeInteractor
    private lateinit var apiRepository: ApiRepository
    private lateinit var persistentRepository: PersistentRepository

    @Before
    fun setUp() {
        apiRepository = ApiRepositoryImpl()
        persistentRepository = object : PersistentRepository {

            private var auth: Auth? = null

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
        interactor = EmployeeInteractorImpl(apiRepository, persistentRepository)

        persistentRepository.saveAuth(Auth(correctLogin, correctPassword))

    }

    @Test
    fun getAll() {
        val dep = interactor.getAllEmployee().blockingGet()
        Assert.assertNotNull(dep.department)
        Assert.assertEquals(dep.employees.size, 0)
        Assert.assertNotEquals(dep.department?.size, 0)
    }

    @Test
    fun getAllEmpoyee() {
        val dep = interactor.getAllEmployee().blockingGet()
        val all = getChildEmployee(dep)
        Assert.assertNotEquals(all.size, 0)
    }

    private fun getChildEmployee(department: Department): List<Employee> {
        val res = ArrayList<Employee>()
        department.department?.forEach {
            res.addAll(getChildEmployee(it))
        }
        res.addAll(department.employees)
        return res
    }

}