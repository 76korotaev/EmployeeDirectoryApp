package com.korot.testapplication.domain

import com.korot.testapplication.ui.MainActivity
import com.korot.testapplication.ui.base.LoaderInterface
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoadTransformer<R>(private val loader: LoaderInterface, val errorCallback: (() -> Unit)? = null):
    SingleTransformer<R, R>,
        CompletableTransformer
{

    override fun apply(upstream: Single<R>): SingleSource<R> {
        return upstream
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loader.onLoadingStart()}
            .doOnTerminate { loader.onLoadingStop() }
            .doOnError { loader.onError(it.message ?: "", errorCallback) }
    }

    override fun apply(upstream: Completable): CompletableSource {
        return upstream
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loader.onLoadingStart()}
            .doOnTerminate { loader.onLoadingStop() }
            .doOnError { loader.onError(it.message ?: "", errorCallback) }
    }
}


