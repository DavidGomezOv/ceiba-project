package co.com.ceiba.mobile.pruebadeingreso.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
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
import co.com.ceiba.mobile.pruebadeingreso.ui.utils.CustomLoadingDialog

class MainActivity : AppCompatActivity(), OnClickListenerCardView {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainActivityViewModel: MainActivityViewModel

    private lateinit var customLoadingDialog: CustomLoadingDialog

    private lateinit var adapter: MainActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        observer()
        userFilter()
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
        adapter = MainActivityAdapter(mutableListOf(), this)
        customLoadingDialog = CustomLoadingDialog(this)
    }

    private fun observer() {
        mainActivityViewModel.getUserList().observe(this, { result ->
            when (result) {
                is Result.Loading -> {
                    customLoadingDialog.showLoadingDialog()
                }
                is Result.Success -> {
                    customLoadingDialog.cancelDialog()
                    adapter = MainActivityAdapter(result.data.toMutableList(), this)
                    binding.recyclerViewSearchResults.adapter = adapter
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
        })
    }

    private fun userFilter() {
        binding.editTextSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Nothing to do
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Nothing to do
            }

            override fun afterTextChanged(s: Editable?) {
                val count = adapter.userFilter(s.toString())
                if (count.isEmpty()) {
                    binding.emptyView.root.visibility = View.VISIBLE
                } else {
                    binding.emptyView.root.visibility = View.INVISIBLE
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