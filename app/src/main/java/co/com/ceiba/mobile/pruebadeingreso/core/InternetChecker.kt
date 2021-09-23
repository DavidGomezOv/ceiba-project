package co.com.ceiba.mobile.pruebadeingreso.core

interface InternetChecker {

    suspend fun isNetworkAvailable(): Boolean

}