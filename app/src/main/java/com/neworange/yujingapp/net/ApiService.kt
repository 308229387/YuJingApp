package com.neworange.yujingapp.net

import com.neworange.yujingapp.data.BaseResponse
import com.neworange.yujingapp.data.UserInfoResponse
import com.neworange.yujingapp.data.WarningData
import com.neworange.yujingapp.data.WarningDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("testapp/use/login")
    suspend fun getUserInfo(@Query("phone") phone: String): BaseResponse<UserInfoResponse>

    @GET("testapp/use/login")
    suspend fun loginWithPassword(@Query("phone") phone: String, @Query("password") password: String): BaseResponse<UserInfoResponse>

    @GET("testapp/{code}/api/applet/result/list")
    suspend fun warningList(@Path("code") code: String, @Query("phone") phone: String): BaseResponse<List<WarningData>>

    @GET("testapp/{code}/api/applet/result/{id}")
    suspend fun warningDetail(@Path("code") code: String, @Path("id") id: String): BaseResponse<WarningDetail>
}

