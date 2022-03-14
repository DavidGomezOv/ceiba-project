package co.com.ceiba.mobile.pruebadeingreso.data.remote

import co.com.ceiba.mobile.pruebadeingreso.data.model.*
import co.com.ceiba.mobile.pruebadeingreso.domain.WebService
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserApiSourceTest : TestCase() {

    @Mock
    private lateinit var webService: WebService

    @InjectMocks
    private lateinit var userApiSource: UserApiSource

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

    @Test
    fun `getUserList() returns a List of User when http call completes successfully`() {
        runBlocking {

            val expect = false
            val returnResponse = createUserResponse()

            Mockito.`when`(webService.getUserList()).thenReturn(returnResponse)

            val result = userApiSource.getUserList()

            assertEquals(expect, result.isEmpty())
        }
    }

    @Test
        fun `getUserPost() returns a List of Post when http call completes successfully`() {
        runBlocking {

            val userId = 1
            val expect = false
            val returnResponse = createPostResponse()

            Mockito.`when`(webService.getUserPost(userId)).thenReturn(returnResponse)

            val result = userApiSource.getUserPost(userId)

            assertEquals(expect, result.isEmpty())

        }
    }

}