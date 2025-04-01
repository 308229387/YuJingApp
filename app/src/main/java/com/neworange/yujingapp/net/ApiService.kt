package com.neworange.yujingapp.net

import com.neworange.yujingapp.data.BaseResponse
import com.neworange.yujingapp.data.NewDataResponse
import com.neworange.yujingapp.data.UserInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("miniapp/use/login")
    suspend fun getUserInfo(@Query("phone") phone: String): BaseResponse<UserInfoResponse>

    @GET("new/endpoint")
    suspend fun getNewData(@Query("param") param: String): BaseResponse<NewDataResponse> // 复用BaseResponse
}

