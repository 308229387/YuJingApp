package com.neworange.yujingapp.data

import com.google.gson.annotations.SerializedName

// 数据项类
import java.io.Serializable

data class WarningData(
    val id: Long,
    val type: Int?,
    val takeTime: String,
    val cameraId: String,
    val address: String,
    val uuid: String,
    val content: String,
    val userPhones: Any?, // 需要确认实际类型是否可序列化
    val aiAlert: Int?,
    val msg: String?,
    val modelName: String,
    val modelId: Long,
    val taskId: Int = 0,
    val updateFrameNum: Int,
    val preRecordStatus: Int?,
    val preRecordVideo: Any?, // 需要确认实际类型是否可序列化
    val seekTime: Int,
    val audioFile: String,
    val createTime: String,
    val targetScene: Any?, // 需要确认实际类型是否可序列化
    val targetSceneWithBox: Any? // 需要确认实际类型是否可序列化
) : Serializable
