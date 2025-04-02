package com.neworange.yujingapp.data

import java.time.LocalDateTime

// 外层响应类
data class WarningListResponse(
    val data: List<WarningData>
)

// 数据项类
data class WarningData(
    val address: String,
    val aiAlert: Int,
    val audioFile: String,
    val cameraId: String,
    val content: String,

    val createTime: LocalDateTime,

    val id: Long,
    val modelId: Int,
    val modelName: String,
    val msg: String,
    val preRecordStatus: Int,
    val preRecordVideo: String,
    val seekTime: Int,

    val takeTime: LocalDateTime,

    val targetScene: String,
    val taskId: Long,
    val type: Int,
    val userPhones: List<String>,
    val uuid: String
)
