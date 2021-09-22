package co.com.ceiba.mobile.pruebadeingreso.ui

import  android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import co.com.ceiba.mobile.pruebadeingreso.core.Result
import co.com.ceiba.mobile.pruebadeingreso.data.local.AppUserDatabase
import co.com.ceiba.mobile.pruebadeingreso.data.local.UserLocalSource
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.data.remote.UserApiSource
import co.com.ceiba.mobile.pruebadeingreso.databinding.ActivityPostBinding
import co.com.ceiba.mobile.pruebadeingreso.domain.RetrofitClient
import co.com.ceiba.mobile.pruebadeingreso.domain.UserRepositoryImpl
import co.com.ceiba.mobile.pruebadeingreso.presentation.PostActivityViewModel
import co.com.ceiba.mobile.pruebadeingreso.presentation.PostActivityViewModelFactory
import co.com.ceiba.mobile.pruebadeingreso.rest.Constants
import co.com.ceiba.mobile.pruebadeingreso.ui.adapter.PostActivityAdapter
import co.com.ceiba.mobile.pruebadeingreso.ui.utils.CustomLoadingDialog

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding

    private lateinit var postActivityViewModel: PostActivityViewModel

    private lateinit var itemUser: User

    private lateinit var customLoadingDialog: CustomLoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        initData()
        observer()
    }

    private fun initComponents() {
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        postActivityViewModel = ViewModelProvider(
            this, PostActivityViewModelFactory(
                UserRepositoryImpl(
                    UserApiSource(RetrofitClient.webService),
                    UserLocalSource(AppUserDatabase.getDatabase(applicationContext).userDao())
                )
            )
        ).get()
        customLoadingDialog = CustomLoadingDialog(this)
    }

    private fun initData() {
        intent.getParcelableExtra<User>(Constants.INTENT_KEY)?.let {
            itemUser = it
            binding.name.text = it.name
            binding.phone.text = it.phone
            binding.email.text = it.email
        }
    }

    private fun observer() {
        itemUser.id?.let {
            postActivityViewModel.getUserPost(it).observe(this, { result ->
                when (result) {
                    is Result.Loading -> {
                        customLoadingDialog.showLoadingDialog()
                        Toast.makeText(applicationContext, "Cargando...", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Success -> {
                        customLoadingDialog.cancelDialog()
                        binding.recyclerViewPostsResults.adapter = PostActivityAdapter(result.data)
                    }
                    is Result.Failed -> {
                        customLoadingDialog.cancelDialog()
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