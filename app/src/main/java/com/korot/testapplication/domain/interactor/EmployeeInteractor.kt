package com.korot.testapplication.domain.interactor

import com.korot.testapplication.domain.model.Department
import com.korot.testapplication.domain.repository.ApiCall


interface EmployeeInteractor {

    suspend fun getAllEmployee(): ApiCall<Department>

    suspend fun getPhotoUrl(id: Int): ApiCall<String>

}