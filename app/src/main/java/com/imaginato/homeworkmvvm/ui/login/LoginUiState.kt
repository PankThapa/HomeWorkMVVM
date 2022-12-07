package com.imaginato.homeworkmvvm.ui.login

import com.imaginato.homeworkmvvm.data.remote.login.model.response.LoginResponse

sealed interface LoginUiState {

    object LoginLoadingUiState : LoginUiState
    data class LoginErrorUiState(val error: String) : LoginUiState
    data class LoginSuccessUiState(val loginResponse: LoginResponse) : LoginUiState

}

