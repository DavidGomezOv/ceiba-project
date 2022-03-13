package co.com.ceiba.mobile.pruebadeingreso.domain

import android.content.Context
import co.com.ceiba.mobile.pruebadeingreso.core.InternetChecker
import co.com.ceiba.mobile.pruebadeingreso.data.local.AppUserDatabase
import co.com.ceiba.mobile.pruebadeingreso.data.model.*
import co.com.ceiba.mobile.pruebadeingreso.data.remote.UserApiSource

class UserRepositoryImpl (
    private val userApiSource: UserApiSource,
    context: Context,
    private val internetChecker: InternetChecker
) : UserRepository {

    private val userDb: AppUserDatabase = AppUserDatabase.getDatabase(context)

    override suspend fun getUserList(): List<User> {
        return if (internetChecker.isNetworkAvailable()) {
            if (userDb.userDao().getLocalUserList().isNotEmpty()) {
                userDb.userDao().getLocalUserList().toUserList()
            } else {
                val result = userApiSource.getUserList()
                result.forEach{ user ->
                    userDb.userDao().saveLocalUserList(user.toUserEntity())
                }
                result
            }
        } else {
            userDb.userDao().getLocalUserList().toUserList()
        }
    }

    override suspend fun getUserPost(id: Int): List<Post> {

        return if (internetChecker.isNetworkAvailable()) {
            if (userDb.userDao().getLocalUserPost(id).isNotEmpty()){
                userDb.userDao().getLocalUserPost(id).toPostList()
            } else {
                val result = userApiSource.getUserPost(id)
                result.forEach{ post ->
                    userDb.userDao().saveLocalUserPost(post.toPostEntity())
                }
                result
            }
        } else {
            userDb.userDao().getLocalUserPost(id).toPostList()
        }

    }

}