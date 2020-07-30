package com.korot.testapplication.interactor

import com.korot.testapplication.domain.interactor.EmployeeInteractor
import com.korot.testapplication.domain.interactor.EmployeeInteractorImpl
import com.korot.testapplication.domain.model.Auth
import com.korot.testapplication.domain.model.Department
import com.korot.testapplication.domain.model.Employee
import com.korot.testapplication.domain.repository.ApiRepository
import com.korot.testapplication.domain.repository.ApiRepositoryImpl
import com.korot.testapplication.domain.repository.PersistentRepository
import com.korot.testapplication.domain.repository.PersistentRepositoryImpl
import kotlinx.coroutines.runBlocking
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
        persistentRepository = PersistentRepositoryImpl()
        interactor = EmployeeInteractorImpl(apiRepository)

        persistentRepository.saveAuth(Auth(correctLogin, correctPassword))

    }

    @Test
    fun getAll() {
        runBlocking {
            val dep = interactor.getAllEmployee()
            Assert.assertNotNull(dep.body?.department)
            Assert.assertEquals(dep.body?.employees?.size, 0)
            Assert.assertNotEquals(dep.body?.department?.size, 0)
        }

    }

    @Test
    fun getAllEmpoyee() {
        runBlocking {
            val dep = interactor.getAllEmployee()
            Assert.assertNotNull(dep.body)
            val all = getChildEmployee(dep.body!!)
            Assert.assertNotEquals(all.size, 0)
        }

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