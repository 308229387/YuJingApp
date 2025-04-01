package com.neworange.yujingapp.data

data class UserInfoResponse(
    val id: Long,
    val openId: String,
    val publicId: Long,
    val nickname: String,
    val code: String,
    val name: String,
    val headImgUrl: String,
    val unionId: String,
    val sessionKey: String,
    val lastLoginTime: String,
    val phone: String,
    val createTime: String,
    val updateTime: String,
    val status: Int,
    val areaName: String,
    val token: String,
    val type: Int
)
