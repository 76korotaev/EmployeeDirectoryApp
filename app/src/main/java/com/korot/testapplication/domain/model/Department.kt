package com.korot.testapplication.domain.model

data class Department (
    val id: Int,
    val name: String,
    val employees: List<Employee>,
    val department: List<Department>? = null
)