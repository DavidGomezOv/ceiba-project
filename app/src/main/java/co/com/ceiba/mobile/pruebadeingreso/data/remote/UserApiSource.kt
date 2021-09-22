package co.com.ceiba.mobile.pruebadeingreso.data.remote

import co.com.ceiba.mobile.pruebadeingreso.core.Result
import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.domain.WebService

class UserApiSource(private val webService: WebService) {

    suspend fun getUserList(): List<User> = webService.getUserList()

    suspend fun getUserPost(id: String): List<Post> = webService.getUserPost(id)

}