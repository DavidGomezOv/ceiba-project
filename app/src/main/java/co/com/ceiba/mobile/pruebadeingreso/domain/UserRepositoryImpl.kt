package co.com.ceiba.mobile.pruebadeingreso.domain

import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.data.remote.UserApiSource

class UserRepositoryImpl(
    private val userApiSource: UserApiSource
) : UserRepository {

    override suspend fun getUserList(): List<User> {

        return userApiSource.getUserList()

    }

    override suspend fun getUserPost(id: Int): List<Post> {

        return userApiSource.getUserPost(id)

    }

}