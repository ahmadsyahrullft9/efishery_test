package com.example.testefishery.data.utils

sealed class AppResource<T>(val data: T? = null, val errorMessage: String? = null) {

    class Success<T>(data: T) : AppResource<T>(data)

    class Loading<T>(data: T? = null) : AppResource<T>(data)

    class Error<T>(t: Throwable, data: T? = null) :
        AppResource<T>(data, t.localizedMessage)

}