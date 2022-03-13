package co.com.ceiba.mobile.pruebadeingreso.data.remote

import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.domain.WebService
import javax.inject.Inject

class UserApiSource @Inject constructor(private val webService: WebService) {

    suspend fun getUserList(): List<User> = webService.getUserList()

    suspend fun getUserPost(id: Int): List<Post> = webService.getUserPost(id)

}