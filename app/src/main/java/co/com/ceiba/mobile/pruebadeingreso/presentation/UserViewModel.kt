package co.com.ceiba.mobile.pruebadeingreso.presentation

import androidx.lifecycle.*
import co.com.ceiba.mobile.pruebadeingreso.core.Result
import co.com.ceiba.mobile.pruebadeingreso.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor (private val repository: UserRepository): ViewModel() {


    fun getUserList() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repository.getUserList()))
        } catch (e: Exception) {
            emit(Result.Failed(e))
        }
    }

    fun getUserPost(id: Int) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repository.getUserPost(id)))
        } catch (e: Exception) {
            emit(Result.Failed(e))
        }
    }

}