package co.com.ceiba.mobile.pruebadeingreso.domain

import co.com.ceiba.mobile.pruebadeingreso.core.InternetCheck
import co.com.ceiba.mobile.pruebadeingreso.data.local.UserLocalSource
import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.data.model.toPostEntity
import co.com.ceiba.mobile.pruebadeingreso.data.model.toUserEntity
import co.com.ceiba.mobile.pruebadeingreso.data.remote.UserApiSource

class UserRepositoryImpl(
    private val userApiSource: UserApiSource,
    private val userLocalSource: UserLocalSource
) : UserRepository {

    override suspend fun getUserList(): List<User> {
        return if (InternetCheck.isNetworkAvailable()) {
            if (userLocalSource.getUserList().isNotEmpty()) {
                userLocalSource.getUserList()
            } else {
                userApiSource.getUserList().forEach{ user ->
                    userLocalSource.saveUserList(user.toUserEntity())
                }
                userLocalSource.getUserList()
            }
        } else {
            userLocalSource.getUserList()
        }
    }

    override suspend fun getUserPost(id: Int): List<Post> {

        return if (InternetCheck.isNetworkAvailable()) {
            if (userLocalSource.getUserPost(id).isNotEmpty()){
                userLocalSource.getUserPost(id)
            } else {
                userApiSource.getUserPost(id).forEach{ post ->
                    userLocalSource.saveUserPost(post.toPostEntity())
                }
                userLocalSource.getUserPost(id)
            }
        } else {
            userLocalSource.getUserPost(id)
        }

    }

}