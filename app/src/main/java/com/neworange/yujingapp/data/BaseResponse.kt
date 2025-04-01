package com.neworange.yujingapp.data

// 基础响应体（所有接口共用）
data class BaseResponse<T>(
    val status: Int,
    val code: Int,
    val msg: String,
    val consume: Int,
    val data: T // 泛型承载具体数据
)
