package com.neworange.yujingapp.net

/**
 * 网络请求结果统一封装类
 * 泛型 T 表示成功时携带的数据类型
 */
sealed class NetworkResult<out T> {

    // 请求成功，携带数据
    data class Success<out T>(val data: T) : NetworkResult<T>()

    // 业务逻辑错误（如 HTTP 4xx/5xx 响应）
    data class Error(
        val code: Int,           // 错误码（HTTP 状态码或自定义错误码）
        val message: String?     // 可选的错误信息
    ) : NetworkResult<Nothing>() // Nothing 表示不携带数据

    // 网络层错误（如无网络连接、超时等）
    object NetworkError : NetworkResult<Nothing>()

    // 加载状态（用于 UI 显示加载指示器）
    object Loading : NetworkResult<Nothing>()

    /* 可选扩展方法 */
    companion object {
        /**
         * 快速创建 Success 的工厂方法
         * 示例：NetworkResult.success(data)
         */
        fun <T> success(data: T) = Success(data)

        /**
         * 快速创建 Error 的工厂方法
         * 示例：NetworkResult.error(404, "Not Found")
         */
        fun error(code: Int, message: String?) = Error(code, message)
    }
}
