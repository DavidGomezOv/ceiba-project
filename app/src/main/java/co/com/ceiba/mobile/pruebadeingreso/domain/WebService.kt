package co.com.ceiba.mobile.pruebadeingreso.domain

import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("movie/upcoming")
    suspend fun getUserList(): List<User>

    @GET("movie/top_rated")
    suspend fun getUserPost(@Query("id") id: String): List<Post>

}


object RetrofitClient {

    val webService: WebService by lazy {
        Retrofit.Builder().baseUrl(Endpoints.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
            .create(WebService::class.java)
    }

}