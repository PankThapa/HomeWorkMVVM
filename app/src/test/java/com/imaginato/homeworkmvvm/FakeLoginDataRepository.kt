package com.imaginato.homeworkmvvm

import com.imaginato.homeworkmvvm.data.remote.login.model.request.LoginRequest

class FakeLoginDataRepository {

    fun postFakeLoginData(
        imsi: String,
        imei: String,
        loginRequest: LoginRequest
    ): Boolean = true

}