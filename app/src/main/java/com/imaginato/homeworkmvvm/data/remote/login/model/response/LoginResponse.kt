package com.imaginato.homeworkmvvm.data.remote.login.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("errorCode")
    val errorCode:String,
    @SerializedName("errorMessage")
    val errorMessage:String,
    @SerializedName("data")
    val loginResponseData : LoginResponseData?
    )

//"errorCode": "00",
//"errorMessage": "Sukses.",
//"data": {
//    "userId":"83878737",
//    "userName": "zhangsan",
//    "isDeleted":true