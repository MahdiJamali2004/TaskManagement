package com.solo.tasky.feature_login.domain.util

sealed class Response<T>(val data: T? = null, val errorMessage: String? = null) {
    data class Success<T>(val result: T?) : Response<T>(result, null)
    data class Error<T>( val error: String?) : Response<T>(null, error)
}