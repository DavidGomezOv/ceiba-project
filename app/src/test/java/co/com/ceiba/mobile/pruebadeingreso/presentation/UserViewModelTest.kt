package co.com.ceiba.mobile.pruebadeingreso.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import co.com.ceiba.mobile.pruebadeingreso.MainCoroutineRule
import co.com.ceiba.mobile.pruebadeingreso.domain.UserRepository
import co.com.ceiba.mobile.pruebadeingreso.core.Result
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class UserViewModelTest : TestCase(){

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var observer: Observer<Result<*>>

    private lateinit var userViewModel: UserViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUpData() {
        userViewModel = UserViewModel(userRepository)
    }

    @Test
    fun `getUserList return Result of Success type and then show User List on RecyclerView`() {
        runBlocking {
            val expect = Result.Success(null)

            userViewModel.getUserList().observeForever(observer)

            Mockito.`when`(userRepository.getUserList()).thenReturn(mutableListOf())

            userViewModel.getUserList()

            Mockito.verify(observer).onChanged(expect)
        }
    }

    @Test
    fun `getUserPost return Result of Success type and then show User List on RecyclerView`() {
        runBlocking {
            val userId = 1
            val expect = Result.Success(null)

            userViewModel.getUserPost(userId).observeForever(observer)

            Mockito.`when`(userRepository.getUserPost(userId)).thenReturn(mutableListOf())

            userViewModel.getUserPost(userId)

            Mockito.verify(observer).onChanged(expect)
        }
    }

}