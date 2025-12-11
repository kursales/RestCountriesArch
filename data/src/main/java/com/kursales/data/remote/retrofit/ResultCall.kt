package com.kursales.data.remote.retrofit

import com.kursales.core.result.ResponseResult
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


internal class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, ResponseResult<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<ResponseResult<T>>) {
        proxy.enqueue(ResultCallback(this, callback))
    }

    override fun cloneImpl(): ResultCall<T> {
        return ResultCall(proxy.clone())
    }

    private class ResultCallback<T>(
        private val proxy: ResultCall<T>,
        private val callback: Callback<ResponseResult<T>>,
    ) : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val value: T? = response.body()
            val result: ResponseResult<T> = if (value == null) {
                ResponseResult.Success.Empty(
                    statusCode = response.code(),
                    statusMessage = response.message(),
                    url = call.request().url.toString(),
                    responseHeaders = response.headers().toMultimap(),
                )
            } else {
                ResponseResult.Success.HttpResponse(
                    value = value,
                    statusCode = response.code(),
                    statusMessage = response.message(),
                    url = call.request().url.toString(),
                    responseHeaders = response.headers().toMultimap(),
                )
            }
            callback.onResponse(proxy, Response.success(result))
        }

        override fun onFailure(call: Call<T>, error: Throwable) {
            val result = ResponseResult.Failure.Error(error)
            callback.onResponse(proxy, Response.success(result))
        }
    }

    override fun timeout(): Timeout {
        return proxy.timeout()
    }

}
