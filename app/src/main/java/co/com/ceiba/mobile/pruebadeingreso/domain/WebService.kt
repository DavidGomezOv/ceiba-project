package co.com.ceiba.mobile.pruebadeingreso.domain

import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.rest.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET(Constants.GET_USERS)
    suspend fun getUserList(): List<User>

    @GET(Constants.GET_POST_USER)
    suspend fun getUserPost(@Query("userId") id: Int): List<Post>

}