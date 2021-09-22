package co.com.ceiba.mobile.pruebadeingreso.ui

import  android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import co.com.ceiba.mobile.pruebadeingreso.core.Result
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.data.remote.UserApiSource
import co.com.ceiba.mobile.pruebadeingreso.databinding.ActivityPostBinding
import co.com.ceiba.mobile.pruebadeingreso.domain.RetrofitClient
import co.com.ceiba.mobile.pruebadeingreso.domain.UserRepositoryImpl
import co.com.ceiba.mobile.pruebadeingreso.presentation.PostActivityViewModel
import co.com.ceiba.mobile.pruebadeingreso.presentation.PostActivityViewModelFactory
import co.com.ceiba.mobile.pruebadeingreso.rest.Constants
import co.com.ceiba.mobile.pruebadeingreso.ui.adapter.PostActivityAdapter

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding

    private lateinit var postActivityViewModel: PostActivityViewModel

    private lateinit var itemUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        initData()
        observer()
    }

    private fun initData() {
        intent.getParcelableExtra<User>(Constants.INTENT_KEY)?.let {
            itemUser = it
            binding.name.text = it.name
            binding.phone.text = it.phone
            binding.email.text = it.email
        }
    }

    private fun initComponents() {
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        postActivityViewModel = ViewModelProvider(
            this, PostActivityViewModelFactory(
                UserRepositoryImpl(
                    UserApiSource(RetrofitClient.webService)
                )
            )
        ).get()
    }

    private fun observer() {
        itemUser.id?.let {
            postActivityViewModel.getUserPost(it).observe(this, { result ->
                when (result) {
                    is Result.Loading -> {
                        Toast.makeText(applicationContext, "Cargando...", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Success -> {
                        binding.recyclerViewPostsResults.adapter = PostActivityAdapter(result.data)
                    }
                    is Result.Failed -> {
                        println("Error ${result.exception}")
                        Toast.makeText(
                            applicationContext,
                            "Error: ${result.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }
}