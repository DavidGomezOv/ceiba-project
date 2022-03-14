package co.com.ceiba.mobile.pruebadeingreso.core

import java.lang.Exception

sealed class Result<out T> {

    class Loading<out T>: Result<T>()
    data class Success<out T>(val data: T): Result<T>()
    data class Failed(val exception: Exception): Result<Nothing>()

}