package com.korot.testapplication.domain.interactor

import com.korot.testapplication.domain.model.Department
import com.korot.testapplication.domain.model.Employee
import com.korot.testapplication.domain.repository.ApiCall
import io.reactivex.Single

interface EmployeeInteractor {

    suspend fun getAllEmployee() : ApiCall<Department>

    suspend fun getPhotoUrl(id: Int): ApiCall<String>

}