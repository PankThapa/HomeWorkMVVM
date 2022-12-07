package com.imaginato.homeworkmvvm.data.remote.login

import com.imaginato.homeworkmvvm.data.local.login.LoginDao
import com.imaginato.homeworkmvvm.data.local.login.LoginUserDBData
import com.imaginato.homeworkmvvm.data.remote.login.loginapi.LoginApi
import com.imaginato.homeworkmvvm.data.remote.login.model.request.LoginRequest
import com.imaginato.homeworkmvvm.data.remote.login.model.response.LoginResponse
import com.imaginato.homeworkmvvm.data.remote.login.model.response.LoginResponseData
import com.imaginato.homeworkmvvm.ui.login.LoginUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


enum class ErrorCode(val errorCode: String) {
    SUCCESS("00"), PASSWORD_ERROR("01")
}

enum class HeaderValue(val headerCode: String) {
    Token("x-acc")
}

class LoginDataRepository constructor(
    private var api: LoginApi,
    private val dao: LoginDao
) : LoginRepository {


    override suspend fun postLoginData(
        imsi: String,
        imei: String,
        loginRequest: LoginRequest
    ) = flow {
        val response = api.postLoginRequest(
            imsi, imei, loginRequest = loginRequest
        ).await()

        if (response.isSuccessful) {

            response.body()?.let { loginResponse: LoginResponse ->

                if (loginResponse.errorCode == ErrorCode.SUCCESS.errorCode) {

                    emit(LoginUiState.LoginSuccessUiState(loginResponse))

                    loginResponse.loginResponseData?.let { loginResponseData: LoginResponseData ->


                        val headersList = response.headers()

                        for (header in headersList) {

                            if (header.first == HeaderValue.Token.headerCode) {
                                val loginUserDBData = LoginUserDBData(
                                    userId = loginResponseData.userId,
                                    userName = loginResponseData.userName,
                                    isDeleted = loginResponseData.isDeleted,
                                    xAcc = header.second
                                )

                                saveToDB(loginUserDBData = loginUserDBData)
                                break

                            }
                        }
                    }
                } else if (loginResponse.errorCode == ErrorCode.PASSWORD_ERROR.errorCode) {
                    emit(LoginUiState.LoginErrorUiState("Something went wrong"))
                } else {
                    emit(LoginUiState.LoginErrorUiState("Something went wrong"))

                }
            }
        } else {

            emit(LoginUiState.LoginErrorUiState("Something went wrong"))
        }

    }.flowOn(Dispatchers.IO)


    // save data to db
    private fun saveToDB(loginUserDBData: LoginUserDBData) {

        dao.insertDemo(loginUserDBData)


    }
}