package com.imaginato.homeworkmvvm.data.remote.login.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponseData(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("isDeleted")
    val isDeleted: Boolean
)


//"data": {
//    "userId":"83878737",
//    "userName": "zhangsan",
//    "isDeleted":true