package com.neworange.yujingapp.net

import com.neworange.yujingapp.data.BaseResponse
import com.neworange.yujingapp.data.UserInfoResponse
import com.neworange.yujingapp.data.WarningListResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path



interface ApiService {
    @GET("miniapp/use/login")
    suspend fun getUserInfo(@Query("phone") phone: String): BaseResponse<UserInfoResponse>

    @GET("miniapp/use/login")
    suspend fun loginWithPassword(@Query("phone") phone: String,@Query("password")password: String): BaseResponse<UserInfoResponse>

    @GET("miniapp/{code}/api/applet/result/list")
    suspend fun warningList(@Path("code") code: String,@Query("phone") phone: String): BaseResponse<WarningListResponse>
}

