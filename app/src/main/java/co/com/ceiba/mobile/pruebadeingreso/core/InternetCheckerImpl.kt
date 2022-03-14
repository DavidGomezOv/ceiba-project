package co.com.ceiba.mobile.pruebadeingreso.core

import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.net.InetSocketAddress
import java.net.Socket

class InternetCheckerImpl: InternetChecker {

    override suspend fun isNetworkAvailable() = coroutineScope {
        return@coroutineScope try {
            val sock = Socket()
            val socketAddress = InetSocketAddress("8.8.8.8", 53)
            sock.connect(socketAddress, 3000)
            sock.close()
            true
        } catch (e: Exception) {
            false
        }
    }

}