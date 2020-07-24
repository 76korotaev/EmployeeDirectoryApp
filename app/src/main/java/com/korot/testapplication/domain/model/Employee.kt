package com.korot.testapplication.domain.model

import android.provider.ContactsContract

data class Employee (
    val id: Int,
    val name: String,
    val title: String,
    val email: String,
    val phone: String,
    val departmentId: Int
)