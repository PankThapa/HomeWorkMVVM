package com.imaginato.homeworkmvvm.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.imaginato.homeworkmvvm.R
import com.imaginato.homeworkmvvm.databinding.ActivityLoginBinding
import com.imaginato.homeworkmvvm.ui.base.BaseActivity
import com.imaginato.homeworkmvvm.ui.demo.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private val viewModel by viewModel<LoginViewModel>()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        observedFieldError()

        binding.loginButton.setOnClickListener {

            onLoginButtonClick()
        }
    }

    // handle input field error state
    private fun observedFieldError() {
        // comenting for testin
       viewModel.oncleared()

        viewModel.passwordErrorLiveData.observe(this) { passwordError: Boolean ->

            if (passwordError) {

                binding.textInputEditTextPassword.error = getString(R.string.password_field_error)

                buttonProgressBarAction(isButtonEnabled = true, progressBarVisibility = false)
            }

        }

        viewModel.userNameErrorLiveData.observe(this) { userNameError: Boolean ->
            if (userNameError) {

                binding.textInputEditTextUsername.error = getString(R.string.username_field_error)

                buttonProgressBarAction(isButtonEnabled = true, progressBarVisibility = false)
            }
        }
    }

    // on login button click
    private fun onLoginButtonClick() {

        viewModel.resultLiveData.observe(this) { loginUiState: LoginUiState ->

            when (loginUiState) {
                is LoginUiState.LoginErrorUiState -> {

                    Toast.makeText(this, loginUiState.error, Toast.LENGTH_SHORT).show()

                    buttonProgressBarAction(isButtonEnabled = true, progressBarVisibility = false)
                }
                LoginUiState.LoginLoadingUiState -> {

                    buttonProgressBarAction(isButtonEnabled = false, progressBarVisibility = true)

                }
                is LoginUiState.LoginSuccessUiState -> {

                    buttonProgressBarAction(isButtonEnabled = true, progressBarVisibility = false)

                    startActivity(Intent(this, MainActivity::class.java))

                    finish()

                }
            }
        }

        viewModel.postLoginRequest(
            "357175048449937",
            "510110406068589",
            binding.textInputEditTextUsername.text.toString(),
            binding.textInputEditTextPassword.text.toString()
        )


    }

    // button and progress bar visibility
    private fun buttonProgressBarAction(isButtonEnabled: Boolean, progressBarVisibility: Boolean) {

        binding.circularProgressBar.visibility = if (progressBarVisibility)
            View.VISIBLE
        else
            View.GONE

        binding.loginButton.isEnabled = isButtonEnabled
    }
}







