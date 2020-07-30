package com.korot.testapplication.domain.interactor

import com.korot.testapplication.domain.model.Department
import com.korot.testapplication.domain.repository.ApiCall
import com.korot.testapplication.domain.repository.ApiRepository
import com.korot.testapplication.domain.repository.ApiRepositoryImpl
import com.korot.testapplication.network.apimodel.OrganizationMapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class EmployeeInteractorImpl(private val apiRepository: ApiRepository) : EmployeeInteractor {

    object Holder {
        val instance = EmployeeInteractorImpl(ApiRepositoryImpl())
    }

    companion object {
        val instance by lazy { Holder.instance }
    }

    override suspend fun getAllEmployee(): ApiCall<Department> {
        return GlobalScope.async {
            val depApi = apiRepository.getOrganization()
            val body = depApi.body
            if (body != null) {
                ApiCall(OrganizationMapper.departmentMap(body))
            } else {
                ApiCall(error = depApi.error)
            }

        }.await()
    }

    //todo временно URL с Бредом Питом, так как тестовый url не работает
    override suspend fun getPhotoUrl(id: Int): ApiCall<String> {
        return GlobalScope.async {
            ApiCall("https://tv.ua/i/11/71/59/3/1171593/image_main/6d8b90737eb7f5f585e93de2c8ecb91b-quality_70Xresize_crop_1Xallow_enlarge_0Xw_750Xh_463.jpg")
        }.await()
    }


}