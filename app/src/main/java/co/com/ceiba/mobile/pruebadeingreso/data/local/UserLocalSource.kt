package co.com.ceiba.mobile.pruebadeingreso.data.local

import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.data.model.PostEntity
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.data.model.UserEntity

interface UserLocalSource {

    suspend fun getUserList(): List<User>
    suspend fun saveUserList(user: UserEntity)
    suspend fun getUserPost(id: Int): List<Post>
    suspend fun saveUserPost(user: PostEntity)

}