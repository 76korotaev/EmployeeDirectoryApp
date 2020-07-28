package com.korot.testapplication.domain.interactor

import com.korot.testapplication.domain.model.Department
import com.korot.testapplication.domain.model.Employee
import io.reactivex.Single

interface EmployeeInteractor {

    fun getAllEmployee() : Single<Department>

    fun getPhotoUrl(id: Int): Single<String>

}