package co.com.ceiba.mobile.pruebadeingreso.domain

import co.com.ceiba.mobile.pruebadeingreso.core.Result
import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.data.model.User

interface UserRepository {

    suspend fun getUserList(): Result<List<User>>
    suspend fun getUserPost(): Result<List<Post>>

}