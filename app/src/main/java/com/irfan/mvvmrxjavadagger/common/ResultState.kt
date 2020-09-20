package com.irfan.mvvmrxjavadagger.common


data class ResultState<out T>(val status: ResultStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ResultState<T> = ResultState(status = ResultStatus.SUCCESS, data = data, message = null)

        fun <T> error(message: String): ResultState<T> =
            ResultState(status = ResultStatus.ERROR, data = null, message = message)

        fun <T> loading(): ResultState<T> = ResultState(status = ResultStatus.LOADING, data = null, message = null)
    }
}