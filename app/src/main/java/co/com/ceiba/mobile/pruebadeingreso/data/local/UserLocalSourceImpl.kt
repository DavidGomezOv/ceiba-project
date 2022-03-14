package co.com.ceiba.mobile.pruebadeingreso.data.local

import co.com.ceiba.mobile.pruebadeingreso.data.model.*

class UserLocalSourceImpl (private val userDao: UserDao) : UserLocalSource {

    override suspend fun getUserList(): List<User> {
        return userDao.getLocalUserList().toUserList()
    }

    override suspend fun saveUserList(user: UserEntity) {
        userDao.saveLocalUserList(user)
    }

    override suspend fun getUserPost(id: Int): List<Post> {
        return userDao.getLocalUserPost(id).toPostList()
    }

    override suspend fun saveUserPost(user: PostEntity) {
        userDao.saveLocalUserPost(user)
    }
}