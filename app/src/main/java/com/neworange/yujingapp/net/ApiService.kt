package com.neworange.yujingapp.net

import com.neworange.yujingapp.data.BaseResponse
import com.neworange.yujingapp.data.UserInfoResponse
import com.neworange.yujingapp.data.WarningData
import com.neworange.yujingapp.data.WarningDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("miniapp/use/login")
    suspend fun getUserInfo(@Query("phone") phone: String): BaseResponse<UserInfoResponse>

    @GET("miniapp/use/login")
    suspend fun loginWithPassword(@Query("phone") phone: String, @Query("password") password: String): BaseResponse<UserInfoResponse>

    @GET("miniapp/{code}/api/applet/result/list")
    suspend fun warningList(@Path("code") code: String, @Query("phone") phone: String): BaseResponse<List<WarningData>>

    @GET("miniapp/{code}/api/applet/result/{id}")
    suspend fun warningDetail(@Path("code") code: String, @Path("id") id: String, @Query("callback") callback: String): BaseResponse<WarningDetail>
}

