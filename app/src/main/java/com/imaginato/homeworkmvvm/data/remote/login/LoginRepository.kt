package com.imaginato.homeworkmvvm.data.remote.login

import com.imaginato.homeworkmvvm.data.remote.login.model.request.LoginRequest
import com.imaginato.homeworkmvvm.data.remote.login.model.response.LoginResponse
import com.imaginato.homeworkmvvm.ui.login.LoginUiState
import kotlinx.coroutines.flow.Flow


interface LoginRepository {

    suspend fun postLoginData(
        imsi: String,
        imei: String,
        loginRequest: LoginRequest
    ): Flow<LoginUiState>
}