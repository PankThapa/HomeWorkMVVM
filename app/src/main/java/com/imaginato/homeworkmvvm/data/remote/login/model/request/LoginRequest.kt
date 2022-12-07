package com.imaginato.homeworkmvvm.data.remote.login.model.request

import com.google.gson.annotations.SerializedName


data class LoginRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)