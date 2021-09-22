package co.com.ceiba.mobile.pruebadeingreso.domain

import co.com.ceiba.mobile.pruebadeingreso.core.Result
import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.data.remote.UserApiSource

class UserRepositoryImpl(
    private val userApiSource: UserApiSource
) : UserRepository {

    override suspend fun getUserList(): Result<List<User>> {

        return Result.Success(userApiSource.getUserList())

    }

    override suspend fun getUserPost(): Result<List<Post>> {
        TODO("Not yet implemented")
    }

}