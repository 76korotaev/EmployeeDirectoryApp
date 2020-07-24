package com.korot.testapplication.ui.department.model

import com.korot.testapplication.domain.model.Department
import com.korot.testapplication.domain.model.Employee

data class DepartmentItem(
    val department: Department? = null,
    val employee: Employee? = null
)