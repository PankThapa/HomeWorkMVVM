package com.imaginato.homeworkmvvm.data.remote.login.loginapi

import com.imaginato.homeworkmvvm.data.remote.login.model.request.LoginRequest
import com.imaginato.homeworkmvvm.data.remote.login.model.response.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApi {

    @POST("/api/login")
    fun postLoginRequest(
        @Header("IMSI") iMSI: String,
        @Header("IMEI") iMEI: String,
        @Body loginRequest: LoginRequest
    ): Deferred<Response<LoginResponse>>
}