package com.korot.testapplication.ui.department

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.korot.testapplication.domain.LoadTransformer
import com.korot.testapplication.domain.interactor.AuthInteractorImpl
import com.korot.testapplication.domain.interactor.EmployeeInteractorImpl
import com.korot.testapplication.domain.model.Department
import com.korot.testapplication.ui.base.BaseViewModel
import com.korot.testapplication.ui.base.LoaderInterface

class DepartmentViewModel(loaderInterface: LoaderInterface) : BaseViewModel(loaderInterface) {

    private val controller = MutableLiveData<Department>()
    val observer: LiveData<Department> = controller

    private val loginController = MutableLiveData<String>()
    val loginObserver: LiveData<String> = loginController

    private val interactor = EmployeeInteractorImpl.instance
    private val authInteractor = AuthInteractorImpl.instance

    fun loadEmployee() {
        compositDisposable.add(
            interactor.getAllEmployee()
                .compose(LoadTransformer(loader) { loadEmployee() })
                .subscribe({
                    controller.value = it
                }, {
                    onError(it)
                })
        )
    }

    fun getLogin() {
        compositDisposable.add(
            authInteractor.getCurrentLogin()
                .compose(LoadTransformer(loader))
                .subscribe({
                    loginController.value = it
                }, {
                    onError(it)
                })
        )
    }

}