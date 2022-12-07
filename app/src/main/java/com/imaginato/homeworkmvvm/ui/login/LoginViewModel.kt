package com.imaginato.homeworkmvvm.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.imaginato.homeworkmvvm.data.remote.login.LoginRepository
import com.imaginato.homeworkmvvm.data.remote.login.model.request.LoginRequest
import com.imaginato.homeworkmvvm.ui.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject

@KoinApiExtension
class LoginViewModel : BaseViewModel() {

    private val repository: LoginRepository by inject()

    private var _resultLiveData: MutableLiveData<LoginUiState> =
        MutableLiveData(LoginUiState.LoginLoadingUiState)

    val resultLiveData: LiveData<LoginUiState> = _resultLiveData

    private var _userNameErrorLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    val userNameErrorLiveData: LiveData<Boolean> = _userNameErrorLiveData

    private var _passwordErrorLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    val passwordErrorLiveData: LiveData<Boolean> = _passwordErrorLiveData


    /**
     * on Post Login Request
     * */
    fun postLoginRequest(imsi: String, imei: String, userName: String, password: String) {

        if (isFieldEmpty(userName) || isFieldEmpty(password)) {

            _passwordErrorLiveData.value = password.isEmpty()

            _userNameErrorLiveData.value = userName.isEmpty()

        } else {

            val loginRequest = LoginRequest(username = userName, password = password)

            viewModelScope.launch {
                repository.postLoginData(imsi, imei, loginRequest = loginRequest)
                    .catch {
                        it.printStackTrace()
                        _resultLiveData.value =
                            LoginUiState.LoginErrorUiState("Something went wrong")

                    }.onCompletion {}.collect { loginResponse ->


                        _resultLiveData.value = loginResponse
                    }
            }
        }
    }

    // check field is empty or not
    fun isFieldEmpty(value: String) = value.isEmpty()


}