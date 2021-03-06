package co.com.ceiba.mobile.pruebadeingreso.domain

import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.data.model.User

interface UserRepository {

    suspend fun getUserList(): List<User>
    suspend fun getUserPost(id: Int): List<Post>

}