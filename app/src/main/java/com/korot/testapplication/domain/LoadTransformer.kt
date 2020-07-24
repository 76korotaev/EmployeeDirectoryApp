package com.korot.testapplication.domain

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoadTransformer<R>: SingleTransformer<R, R> {

    override fun apply(upstream: Single<R>): SingleSource<R> {
        return upstream
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}


