package com.neworange.yujingapp.data

import com.google.gson.annotations.SerializedName

// 新增对应接口的响应数据结构
data class NewDataResponse(
    @SerializedName("field1") val field1: String,
    @SerializedName("field2") val field2: Int,
    // ...其他字段
)
