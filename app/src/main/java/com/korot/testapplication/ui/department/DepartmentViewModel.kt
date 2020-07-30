package com.korot.testapplication.ui.department

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.korot.testapplication.domain.interactor.AuthInteractorImpl
import com.korot.testapplication.domain.interactor.EmployeeInteractorImpl
import com.korot.testapplication.domain.model.Department
import com.korot.testapplication.ui.base.BaseViewModel
import com.korot.testapplication.ui.base.LoaderInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DepartmentViewModel(loaderInterface: LoaderInterface) : BaseViewModel(loaderInterface) {

    private val controller = MutableLiveData<Department>()
    val observer: LiveData<Department> = controller

    private val loginController = MutableLiveData<String>()
    val loginObserver: LiveData<String> = loginController

    private val interactor = EmployeeInteractorImpl.instance
    private val authInteractor = AuthInteractorImpl.instance

    fun loadEmployee() {
        loader.onLoadingStart()
        scope.launch {
            val res = interactor.getAllEmployee()
            when{
                res.body != null -> controller.postValue(res.body)
                res.error != null ->
                    withContext(Dispatchers.Main) {
                        loader.onError(res.error){loadEmployee()}
                    }

            }
            withContext(Dispatchers.Main) {
                loader.onLoadingStop()
            }
        }
    }

    fun getLogin() {

        loader.onLoadingStart()

        scope.launch {
            val res = authInteractor.getCurrentLogin()
            when{
                res.body != null -> loginController.postValue(res.body)
                res.error != null ->
                    withContext(Dispatchers.Main) {
                        loader.onError(res.error){getLogin()}
                    }

            }
            withContext(Dispatchers.Main) {
                loader.onLoadingStop()
            }
        }

    }

}