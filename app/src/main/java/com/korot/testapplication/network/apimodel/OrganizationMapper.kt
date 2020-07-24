package com.korot.testapplication.network.apimodel

import com.korot.testapplication.domain.model.Department
import com.korot.testapplication.domain.model.Employee
import java.lang.Exception

object OrganizationMapper {

    fun departmentMap(organization: OrganizationResponse): Department{
        val departments = ArrayList<Department>()
        val id = organization.id?.toIntOrNull() ?: throw Exception("Organization id = null")
        val name = organization.name ?: throw Exception("Organization name = null")

        val subDepartment = getDepartments(organization.departments)
        return Department(id,name, emptyList(), subDepartment)
    }

    private fun getDepartment(department: DepartmentResponce): Department?{
        val id = department.id?.toIntOrNull() ?: return null
        val employees = getEmployes(department, id)
        val name = department.name ?: return null
        val departments = getDepartments(department.departments)
        return Department(
            id = id,
            employees = employees,
            name = name,
            department = departments
        )
    }

    private fun getDepartments(departments: List<DepartmentResponce>): List<Department>{
        if (departments.size == 0) return emptyList()
        val departmentsMap = ArrayList<Department>()
        departments.forEach {
            val dep = getDepartment(it)
            if (dep != null) { departmentsMap.add(dep)}
        }
        return departmentsMap
    }

    private fun getEmployes(department: DepartmentResponce, departmentId : Int): List<Employee>{
        val employees = ArrayList<Employee>()
        department.emloyees.forEach {
            employe ->
                getEmployee(employe, departmentId)?.let {
                    employees.add(it)
                }

        }
        return employees
    }

    private fun getEmployee(employee: EmployeeResponse, departmentId: Int): Employee? {
        val id = employee.id?.toIntOrNull() ?: return null
        val name = employee.name ?: return null
        val title = employee.title ?: return null
        val email = employee.email ?: return null
        val phone = employee.phone ?: return null
        return Employee(
             id = id,
             name = name,
             title = title,
             email = email,
             phone = phone,
             departmentId = departmentId
         )
    }
}