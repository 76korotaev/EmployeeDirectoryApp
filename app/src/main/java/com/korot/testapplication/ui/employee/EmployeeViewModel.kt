package com.korot.testapplication.ui.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.korot.testapplication.domain.LoadTransformer
import com.korot.testapplication.domain.interactor.EmployeeInteractorImpl
import com.korot.testapplication.ui.base.BaseViewModel
import com.korot.testapplication.ui.base.LoaderInterface

class EmployeeViewModel(loader: LoaderInterface): BaseViewModel(loader) {

    private var photoController = MutableLiveData<String>()
    val photoObserver:LiveData<String> = photoController
    private val interactor = EmployeeInteractorImpl.instance

    fun loadPhoto(id: Int){
        compositDisposable.add(
            interactor.getPhotoUrl(id)
                .compose(LoadTransformer(loader){loadPhoto(id)})
                .subscribe({photoController.value = it},{})
        )
    }
}