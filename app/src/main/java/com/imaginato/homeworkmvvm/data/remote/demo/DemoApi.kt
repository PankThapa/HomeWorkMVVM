package com.imaginato.homeworkmvvm.data.remote.demo

import com.imaginato.homeworkmvvm.data.remote.demo.response.DemoResponse
import com.imaginato.homeworkmvvm.data.remote.login.model.request.LoginRequest
import com.imaginato.homeworkmvvm.data.remote.login.model.response.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface DemoApi {
    @GET
    fun getDemoDataAsync(@Url url: String): Deferred<DemoResponse>



}