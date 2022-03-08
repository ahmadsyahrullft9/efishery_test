package com.example.testefishery.data.networks

import android.util.Log
import com.example.testefishery.data.utils.NetworkResult
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseApiResponse {

    private val TAG = "BaseApiResponse"

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return NetworkResult.Error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return NetworkResult.Error(e.message ?: e.toString())
        }
    }

    fun <T> submitPost(apiCall: () -> Call<T>, resultCallback: (NetworkResult<T>) -> Unit) {
        val call = apiCall()
        resultCallback(NetworkResult.Loading())
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (!call.isCanceled) {
                    if (response.isSuccessful) {
                        resultCallback(NetworkResult.Success(response.body()!!))
                    } else {
                        Log.d(TAG, "onResponse: ${response.errorBody()} code : ${response.code()}")
                        resultCallback(NetworkResult.Error("failed"))
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                if (!call.isCanceled) {
                    resultCallback(NetworkResult.Error("error ${t.localizedMessage}"))
                }
            }

        })
    }
}