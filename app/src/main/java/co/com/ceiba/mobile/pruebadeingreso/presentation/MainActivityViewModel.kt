package co.com.ceiba.mobile.pruebadeingreso.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import co.com.ceiba.mobile.pruebadeingreso.core.Result
import co.com.ceiba.mobile.pruebadeingreso.domain.UserRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainActivityViewModel(private val repository: UserRepository): ViewModel() {

    fun getUserList() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repository.getUserList()))
        } catch (e: Exception) {
            emit(Result.Failed(e))
        }
    }

}

class MainActivityViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserRepository::class.java).newInstance(repository)
    }


}