package com.korot.testapplication.ui.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.korot.testapplication.domain.interactor.EmployeeInteractorImpl
import com.korot.testapplication.ui.base.BaseViewModel
import com.korot.testapplication.ui.base.LoaderInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeViewModel(loader: LoaderInterface): BaseViewModel(loader) {

    private var photoController = MutableLiveData<String>()
    val photoObserver:LiveData<String> = photoController
    private val interactor = EmployeeInteractorImpl.instance

    fun loadPhoto(id: Int){
        loader.onLoadingStart()
        scope.launch {
            val res = interactor.getPhotoUrl(id)
            when{
                res.body != null -> photoController.postValue(res.body)
                res.error != null ->
                    withContext(Dispatchers.Main) {
                        loader.onError(res.error){loadPhoto(id)}
                    }

            }
            withContext(Dispatchers.Main) {
                loader.onLoadingStop()
            }
        }

    }
}