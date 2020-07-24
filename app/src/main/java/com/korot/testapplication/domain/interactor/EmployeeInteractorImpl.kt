package com.korot.testapplication.domain.interactor

import com.korot.testapplication.domain.model.Auth
import com.korot.testapplication.domain.model.Department
import com.korot.testapplication.domain.model.Employee
import com.korot.testapplication.domain.repository.ApiRepository
import com.korot.testapplication.domain.repository.ApiRepositoryImpl
import com.korot.testapplication.domain.repository.PersistentRepository
import com.korot.testapplication.domain.repository.PersistentRepositoryImpl
import com.korot.testapplication.network.apimodel.OrganizationMapper
import io.reactivex.Single
import java.lang.Exception

class EmployeeInteractorImpl(private val apiRepository: ApiRepository, private val preferenceRepository: PersistentRepository) : EmployeeInteractor{

    object Holder {
        val instance = EmployeeInteractorImpl(ApiRepositoryImpl(), PersistentRepositoryImpl.instance)
    }

    companion object {
        val instance by lazy { Holder.instance }
    }

    override fun getAllEmployee(): Single<Department> {
        return Single.create<Auth> {
            val auth = preferenceRepository.getAuth()
            if (auth == null){
                it.onError(Exception("Ошибка авторизации"))
            } else {
                it.onSuccess(auth)
            }
        }.flatMap {
            apiRepository.getOrganization(it)
        }.map {
            OrganizationMapper.departmentMap(it)
        }
    }

    override fun getEmployeeForId(id: Int): Single<Employee> {
        TODO("Not yet implemented")
    }

}