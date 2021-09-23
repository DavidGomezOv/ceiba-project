package co.com.ceiba.mobile.pruebadeingreso.domain

import co.com.ceiba.mobile.pruebadeingreso.core.InternetChecker
import co.com.ceiba.mobile.pruebadeingreso.data.local.UserLocalSource
import co.com.ceiba.mobile.pruebadeingreso.data.model.*
import co.com.ceiba.mobile.pruebadeingreso.data.remote.UserApiSource
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryImplTest : TestCase() {

    @Mock
    private lateinit var userApiSource: UserApiSource

    @Mock
    private lateinit var userLocalSource: UserLocalSource

    @Mock
    private lateinit var internetChecker: InternetChecker

    @InjectMocks
    private lateinit var userRepositoryImpl: UserRepositoryImpl

    private fun createUserResponse(): List<User> = mutableListOf(
        User(
            1,
            "Test",
            "test@gmail.com",
            Address("Street", "Suite", "City", "ZipCode", Geo("lat", "lng")),
            "3133131313",
            "website.com.co",
            Company("company", "catchPhrase", "bs")
        )
    )

    private fun createPostResponse(): List<Post> = mutableListOf(
        Post(1, 2, "Title", "Body")
    )

    @Before
    fun setUpData() {
        userRepositoryImpl = UserRepositoryImpl(userApiSource, userLocalSource, internetChecker)
    }

    @Test
    fun `getUserList() returns an empty List of User if Network connection isn't available and there isn't any Local data`() {
        runBlocking {
            val expect = mutableListOf<User>()

            Mockito.`when`(internetChecker.isNetworkAvailable()).thenReturn(false)
            Mockito.`when`(userLocalSource.getUserList()).thenReturn(mutableListOf())

            val result = userRepositoryImpl.getUserList()

            assertEquals(expect, result)
        }
    }

    @Test
    fun `getUserList() returns a List of User from Local Source if this isn't empty`() {
        runBlocking {
            val expect = true
            val successResponse = createUserResponse()

            Mockito.`when`(internetChecker.isNetworkAvailable()).thenReturn(true)
            Mockito.`when`(userLocalSource.getUserList()).thenReturn(successResponse)

            val result = userRepositoryImpl.getUserList()

            assertEquals(expect, result.isNotEmpty())
        }
    }

    @Test
    fun `getUserList() returns a List of User from Api Source`() {
        runBlocking {
            val expect = true
            val successResponse = createUserResponse()

            Mockito.`when`(internetChecker.isNetworkAvailable()).thenReturn(true)
            Mockito.`when`(userLocalSource.getUserList()).thenReturn(mutableListOf())
            Mockito.`when`(userApiSource.getUserList()).thenReturn(successResponse)

            val result = userRepositoryImpl.getUserList()

            assertEquals(expect, result.isNotEmpty())
        }
    }


    @Test
    fun `getUserPost() returns an empty List of Post if Network connection isn't available and there isn't any Local data`() {
        runBlocking {
            val userId = 1
            val expect = mutableListOf<Post>()

            Mockito.`when`(internetChecker.isNetworkAvailable()).thenReturn(false)
            Mockito.`when`(userLocalSource.getUserPost(userId)).thenReturn(mutableListOf())

            val result = userRepositoryImpl.getUserPost(userId)

            assertEquals(expect, result)
        }
    }

    @Test
    fun `getUserPost() returns a List of Post from Local Source if this isn't empty`() {
        runBlocking {
            val userId = 1
            val expect = true
            val successResponse = createPostResponse()

            Mockito.`when`(internetChecker.isNetworkAvailable()).thenReturn(true)
            Mockito.`when`(userLocalSource.getUserPost(userId)).thenReturn(successResponse)

            val result = userRepositoryImpl.getUserPost(userId)

            assertEquals(expect, result.isNotEmpty())
        }
    }

    @Test
    fun `getUserPost() returns a List of Post from Api Source`() {
        runBlocking {
            val userId = 1
            val expect = true
            val successResponse = createPostResponse()

            Mockito.`when`(internetChecker.isNetworkAvailable()).thenReturn(true)
            Mockito.`when`(userLocalSource.getUserPost(userId)).thenReturn(mutableListOf())
            Mockito.`when`(userApiSource.getUserPost(userId)).thenReturn(successResponse)

            val result = userRepositoryImpl.getUserPost(userId)

            assertEquals(expect, result.isNotEmpty())
        }
    }

}