package co.com.ceiba.mobile.pruebadeingreso.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import co.com.ceiba.mobile.pruebadeingreso.core.Result
import co.com.ceiba.mobile.pruebadeingreso.data.local.AppUserDatabase
import co.com.ceiba.mobile.pruebadeingreso.data.local.UserLocalSource
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.data.remote.UserApiSource
import co.com.ceiba.mobile.pruebadeingreso.databinding.ActivityMainBinding
import co.com.ceiba.mobile.pruebadeingreso.domain.RetrofitClient
import co.com.ceiba.mobile.pruebadeingreso.domain.UserRepositoryImpl
import co.com.ceiba.mobile.pruebadeingreso.presentation.MainActivityViewModel
import co.com.ceiba.mobile.pruebadeingreso.presentation.MainActivityViewModelFactory
import co.com.ceiba.mobile.pruebadeingreso.rest.Constants
import co.com.ceiba.mobile.pruebadeingreso.ui.adapter.MainActivityAdapter
import co.com.ceiba.mobile.pruebadeingreso.ui.adapter.OnClickListenerCardView

class MainActivity : AppCompatActivity(), OnClickListenerCardView {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        observer()
    }

    private fun initComponents() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainActivityViewModel = ViewModelProvider(
            this, MainActivityViewModelFactory(
                UserRepositoryImpl(
                    UserApiSource(RetrofitClient.webService),
                    UserLocalSource(AppUserDatabase.getDatabase(applicationContext).userDao())
                )
            )
        ).get()
    }

    private fun observer() {
        mainActivityViewModel.getUserList().observe(this, { result ->
            when (result) {
                is Result.Loading -> {
                    Toast.makeText(applicationContext, "Cargando...", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    binding.recyclerViewSearchResults.adapter =
                        MainActivityAdapter(result.data, this)
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

    override fun onClick(userItem: User) {
        startActivity(Intent(this, PostActivity::class.java).
            putExtra(Constants.INTENT_KEY, userItem)
        )
    }

}