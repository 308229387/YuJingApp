package com.neworange.yujingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neworange.yujingapp.data.WarningData
import com.neworange.yujingapp.data.WarningDetail
import com.neworange.yujingapp.net.NetworkResult
import com.neworange.yujingapp.net.Repository
import kotlinx.coroutines.launch

class WarningViewModel : ViewModel() {
    private val repository = Repository()

    // 内部使用 MutableLiveData（可修改）
    private val _warningListLiveData = MutableLiveData<NetworkResult<List<WarningData>>>()
    private val _warningDetailLiveData = MutableLiveData<NetworkResult<WarningDetail>>()

    // 对外暴露 LiveData（只读）
    val warningListLiveData: LiveData<NetworkResult<List<WarningData>>> = _warningListLiveData
    val warningDetailLiveData: LiveData<NetworkResult<WarningDetail>> = _warningDetailLiveData

    fun warningList(code: String, phone: String) {
        viewModelScope.launch {
            _warningListLiveData.value = NetworkResult.Loading
            _warningListLiveData.value = repository.warningList(code, phone)
        }
    }

    fun warningDetail(code: String, id: String) {
        viewModelScope.launch {
            _warningDetailLiveData.value = NetworkResult.Loading
            _warningDetailLiveData.value = repository.warningDetail(code, id)
        }
    }
}
