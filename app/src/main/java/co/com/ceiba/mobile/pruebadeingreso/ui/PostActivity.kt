package co.com.ceiba.mobile.pruebadeingreso.ui

import  android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.com.ceiba.mobile.pruebadeingreso.core.Result
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.databinding.ActivityPostBinding
import co.com.ceiba.mobile.pruebadeingreso.presentation.UserViewModel
import co.com.ceiba.mobile.pruebadeingreso.rest.Constants
import co.com.ceiba.mobile.pruebadeingreso.ui.adapter.PostActivityAdapter
import co.com.ceiba.mobile.pruebadeingreso.ui.utils.CustomLoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding

    private val userViewModel: UserViewModel by viewModels()

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
            userViewModel.getUserPost(it).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        customLoadingDialog.showLoadingDialog()
                    }
                    is Result.Success -> {
                        customLoadingDialog.cancelDialog()
                        binding.recyclerViewPostsResults.adapter = PostActivityAdapter(result.data)
                    }
                    is Result.Failed -> {
                        customLoadingDialog.cancelDialog()
                        Toast.makeText(
                            applicationContext,
                            "Error: ${result.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}