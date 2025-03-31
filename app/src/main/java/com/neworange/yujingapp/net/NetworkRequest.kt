package com.neworange.yujingapp.net

import retrofit2.Response
import java.io.IOException

/**
 * 网络请求统一封装工具类
 * 核心功能：处理 Retrofit 请求的异常和错误，返回标准化的 NetworkResult 结果
 */
object NetworkRequest {

    /**
     * 安全执行网络请求（协程版）
     * @param apiCall 网络请求的挂起函数（如 apiService.getUser()）
     * @return NetworkResult<T> 标准化结果
     */
    suspend fun <T : Any> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        return try {
            // 执行网络请求
            val response = apiCall()

            // 处理 HTTP 响应码
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    NetworkResult.Success(body)
                } else {
                    // 响应体为空（如 HTTP 204）
                    NetworkResult.Error(code = -1, message = "Empty response body")
                }
            } else {
                // HTTP 错误（如 404、500）
                val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                NetworkResult.Error(code = response.code(), message = errorMsg)
            }
        } catch (e: IOException) {
            // 网络层错误（如无网络连接、超时）
            NetworkResult.NetworkError
        } catch (e: Exception) {
            // 其他未知异常（如 JSON 解析错误）
            NetworkResult.Error(code = -1, message = e.message ?: "Unknown exception")
        }
    }

    /**
     * 扩展：带重试逻辑的安全请求（可选）
     * @param retries 最大重试次数
     */
    suspend fun <T : Any> safeApiCallWithRetry(
        retries: Int = 3,
        apiCall: suspend () -> Response<T>
    ): NetworkResult<T> {
        var currentRetry = 0
        while (currentRetry < retries) {
            val result = safeApiCall(apiCall)
            if (result !is NetworkResult.NetworkError) {
                return result // 非网络错误直接返回
            }
            currentRetry++
            kotlinx.coroutines.delay(1000L * currentRetry) // 指数退避
        }
        return NetworkResult.NetworkError // 重试后仍失败
    }
}
