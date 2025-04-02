package com.neworange.yujingapp.net

import com.neworange.yujingapp.data.UserInfoResponse

class UserRepository : BaseRepository() {
    private val apiService = RetrofitClient.createService(ApiService::class.java)

    suspend fun fetchUserInfo(phone: String): NetworkResult<UserInfoResponse> {
        return executeRequest {
            apiService.getUserInfo(phone)
        }
    }

//    // 新增其他接口（只需1行核心代码）
//    suspend fun updateProfile(avatar: String): NetworkResult<ProfileResponse> {
//        return executeRequest { apiService.updateProfile(avatar) }
//    }
}

