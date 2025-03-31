package com.neworange.yujingapp.net

import com.neworange.yujingapp.data.UserInfoResponse

class UserRepository {
    private val apiService = RetrofitClient.createService(ApiService::class.java)

    suspend fun fetchUserInfo(phone: String): NetworkResult<UserInfoResponse> {
        return NetworkRequest.safeApiCall {
            apiService.getUserInfo(phone)
        }
    }
}
