package com.korot.testapplication.network.apimodel

import com.google.gson.annotations.SerializedName


data class OrganizationResponse(
    @SerializedName("ID")
    val id: String? = null,
    @SerializedName("Name")
    val name: String? = null,
    @SerializedName("Departments")
    val departments: List<DepartmentResponce> = emptyList()
)

data class DepartmentResponce (
    @SerializedName("ID")
    val id: String? = null,
    @SerializedName("Name")
    val name: String? = null,
    @SerializedName("Employees")
    val emloyees: List<EmployeeResponse> = emptyList(),
    @SerializedName("Departments")
    val departments: List<DepartmentResponce> = emptyList()
)

data class EmployeeResponse(
    @SerializedName("ID")
    val id: String? = null,
    @SerializedName("Name")
    val name: String? = null,
    @SerializedName("Title")
    val title: String? = null,
    @SerializedName("Email")
    val email: String? = null,
    @SerializedName("Phone")
    val phone: String? = null
)

