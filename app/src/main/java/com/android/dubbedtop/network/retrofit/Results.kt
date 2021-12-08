package com.android.dubbedtop.network.retrofit

import retrofit2.HttpException

interface Results<T> {
    fun onSuccess(t: T)
    fun onFailure(message: String)
    fun onException(e: HttpException)
}