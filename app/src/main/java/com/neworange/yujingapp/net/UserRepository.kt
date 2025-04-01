package com.neworange.yujingapp.net

import com.neworange.yujingapp.data.UserInfoResponse

class UserRepository {
    private val apiService = RetrofitClient.createService(ApiService::class.java)

    suspend fun fetchUserInfo(phone: String): NetworkResult<UserInfoResponse> {
        return try {
            val response = apiService.getUserInfo(phone)
            when {
                response.status == 0 && response.code == 0 -> {
                    NetworkResult.Success(response.data) // 正确获取嵌套的data
                }
                else -> {
                    NetworkResult.Error(
                        message = response.msg,
                        code = response.code
                    )
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(
                message = e.message ?: "请求失败",
                code = -1
            )
        }
    }

}
