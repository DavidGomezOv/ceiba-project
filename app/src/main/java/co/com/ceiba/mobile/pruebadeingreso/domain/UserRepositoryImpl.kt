package co.com.ceiba.mobile.pruebadeingreso.domain

import co.com.ceiba.mobile.pruebadeingreso.core.InternetChecker
import co.com.ceiba.mobile.pruebadeingreso.data.local.UserLocalSource
import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.data.model.toPostEntity
import co.com.ceiba.mobile.pruebadeingreso.data.model.toUserEntity
import co.com.ceiba.mobile.pruebadeingreso.data.remote.UserApiSource

class UserRepositoryImpl(
    private val userApiSource: UserApiSource,
    private val userLocalSource: UserLocalSource,
    private val internetChecker: InternetChecker
) : UserRepository {

    override suspend fun getUserList(): List<User> {
        return if (internetChecker.isNetworkAvailable()) {
            if (userLocalSource.getUserList().isNotEmpty()) {
                userLocalSource.getUserList()
            } else {
                val result = userApiSource.getUserList()
                result.forEach{ user ->
                    userLocalSource.saveUserList(user.toUserEntity())
                }
                result
            }
        } else {
            userLocalSource.getUserList()
        }
    }

    override suspend fun getUserPost(id: Int): List<Post> {

        return if (internetChecker.isNetworkAvailable()) {
            if (userLocalSource.getUserPost(id).isNotEmpty()){
                userLocalSource.getUserPost(id)
            } else {
                val result = userApiSource.getUserPost(id)
                result.forEach{ post ->
                    userLocalSource.saveUserPost(post.toPostEntity())
                }
                result
            }
        } else {
            userLocalSource.getUserPost(id)
        }

    }

}