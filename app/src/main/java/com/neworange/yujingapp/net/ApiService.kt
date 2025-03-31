package com.neworange.yujingapp.net

import com.neworange.yujingapp.data.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("miniapp/use/login")
    suspend fun getUserInfo(@Query("phone") phone: String): Response<UserInfoResponse>
}

