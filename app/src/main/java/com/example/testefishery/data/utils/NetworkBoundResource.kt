package com.example.testefishery.data.utils

import kotlinx.coroutines.flow.*

inline fun <Result, Request> networkBoundResource(
    crossinline localCached: () -> Flow<Result>,
    crossinline performRequest: suspend () -> Request,
    crossinline saveResult: suspend (Request) -> Unit,
    crossinline checkToRenewData: (Result) -> Boolean = { true }
) = flow {
    //first check cached data is exist
    val data = localCached().first()
    //then check if you want to make a new request from api
    val newFlow = if (checkToRenewData(data)) {
        //set state to loading
        emit(AppResource.Loading(data))

        try {
            //save result of request api to local
            saveResult(performRequest())

            //emit result is success
            localCached().map {
                AppResource.Success(it)
            }
        } catch (t: Throwable) {
            //emit result is failed
            localCached().map {
                AppResource.Error(t, it)
            }
        }
    } else {
        //emit result from local cached data
        localCached().map { AppResource.Success(it) }
    }

    emitAll(newFlow)
}