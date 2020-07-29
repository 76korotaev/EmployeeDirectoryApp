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

class EmployeeInteractorImpl(private val apiRepository: ApiRepository) : EmployeeInteractor{

    object Holder {
        val instance = EmployeeInteractorImpl(ApiRepositoryImpl())
    }

    companion object {
        val instance by lazy { Holder.instance }
    }

    override fun getAllEmployee(): Single<Department> {
        return apiRepository.getOrganization()
                .map {
            OrganizationMapper.departmentMap(it)
        }
    }

    //todo временно URL с Бредом Питом, так как тестовый url не работает
    override fun getPhotoUrl(id: Int): Single<String> {
        return Single.just("https://tv.ua/i/11/71/59/3/1171593/image_main/6d8b90737eb7f5f585e93de2c8ecb91b-quality_70Xresize_crop_1Xallow_enlarge_0Xw_750Xh_463.jpg")
    }


}