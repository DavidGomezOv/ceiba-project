package co.com.ceiba.mobile.pruebadeingreso.data.local

import co.com.ceiba.mobile.pruebadeingreso.data.model.*

class UserLocalSource(private val userDao: UserDao) {

    suspend fun getUserList(): List<User> {
        return userDao.getLocalUserList().toUserList()
    }

    suspend fun saveUserList(user: UserEntity) {
        userDao.saveLocalUserList(user)
    }

    suspend fun getUserPost(id: Int): List<Post> {
        return userDao.getLocalUserPost(id).toPostList()
    }

    suspend fun saveUserPost(user: PostEntity) {
        userDao.saveLocalUserPost(user)
    }

}