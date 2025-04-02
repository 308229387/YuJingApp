package com.neworange.yujingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neworange.yujingapp.data.UserInfoResponse
import com.neworange.yujingapp.net.NetworkResult
import com.neworange.yujingapp.net.Repository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = Repository()

    private val _userInfoLiveData = MutableLiveData<NetworkResult<UserInfoResponse>>()
    val userInfoLiveData: LiveData<NetworkResult<UserInfoResponse>> = _userInfoLiveData

    fun fetchUserInfo(phone: String) {
        viewModelScope.launch {
            _userInfoLiveData.value = NetworkResult.Loading
            _userInfoLiveData.value = repository.fetchUserInfo(phone)
        }
    }

    fun loginWithPassword(phone: String, password: String) {
        viewModelScope.launch {
            _userInfoLiveData.value = NetworkResult.Loading
            _userInfoLiveData.value = repository.loginWithPassword(phone,password)
        }
    }
}
