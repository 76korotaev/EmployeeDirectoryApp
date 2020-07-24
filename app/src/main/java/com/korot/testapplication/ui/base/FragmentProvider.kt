package com.korot.testapplication.ui.base

import androidx.core.content.contentValuesOf
import com.korot.testapplication.ui.auth.AuthFragment
import com.korot.testapplication.ui.department.DepartmentFragment

class FragmentProvider(val consummer: ConsumerFragmentProvider) {

    fun startAuth(){
        consummer.startFragment(AuthFragment(consummer))
    }

    fun startDepartment(){
        consummer.startFragment(DepartmentFragment(consummer))
    }

    fun endAuth(){
        consummer.startFragment(DepartmentFragment(consummer))
    }

    fun startCardEmpoyee(){
        //todo реализовать
    }

}