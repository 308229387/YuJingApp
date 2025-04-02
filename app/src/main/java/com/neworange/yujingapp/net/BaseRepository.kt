package com.neworange.yujingapp.net

import com.neworange.yujingapp.data.BaseResponse
import okio.IOException

abstract class BaseRepository {
    // 统一响应处理
    protected suspend fun <T : Any> executeRequest(
        call: suspend () -> BaseResponse<T>
    ): NetworkResult<T> {
        return try {
            val response = call()
            when {
                response.status == 0 && response.code == 0 -> {
                    NetworkResult.Success(response.data ?: throw NullDataException())
                }
                else -> {
                    NetworkResult.Error(
                        code = response.status,
                        message = response.msg
                    )
                }
            }
        } catch (e: IOException) {
            NetworkResult.Error(message = "网络连接异常", code = -1)
        } catch (e: NullDataException) {
            NetworkResult.Error(message = "数据解析为空", code = -2)
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message ?: "未知错误", code = -3)
        }
    }
}

// 空数据专属异常
class NullDataException : Exception()
