package com.neworange.yujingapp.net

import com.neworange.yujingapp.data.UserInfoResponse
import com.neworange.yujingapp.data.WarningData
import com.neworange.yujingapp.data.WarningDetail

class Repository : BaseRepository() {
    private val apiService = RetrofitClient.createService(ApiService::class.java)

    suspend fun fetchUserInfo(phone: String): NetworkResult<UserInfoResponse> {
        return executeRequest {
            apiService.getUserInfo(phone)
        }
    }

    suspend fun loginWithPassword(phone: String, password: String): NetworkResult<UserInfoResponse> {
        return executeRequest {
            apiService.loginWithPassword(phone, password)
        }
    }

    suspend fun warningList(code: String, phone: String): NetworkResult<List<WarningData>> {
        return executeRequest {
            apiService.warningList(code, phone)
        }
    }

    suspend fun warningDetail(code: String, id: String): NetworkResult<WarningDetail> {
        return executeRequest {
            apiService.warningDetail(code, id)
        }
    }


}

