package com.neworange.yujingapp.utils

import android.content.Context
import android.content.SharedPreferences


object SPManager {
    private lateinit var sp: SharedPreferences

    // 初始化（在 Application 中调用）
    fun init(context: Context) {
        sp = context.getSharedPreferences("app_sp", Context.MODE_PRIVATE)
    }

    // 存储数据（自动异步提交）
    fun put(key: String, value: Any?) {
        when (value) {
            is String -> sp.edit { putString(key, value) }
            is Int -> sp.edit { putInt(key, value) }
            is Boolean -> sp.edit { putBoolean(key, value) }
            is Float -> sp.edit { putFloat(key, value) }
            is Long -> sp.edit { putLong(key, value) }
            null -> sp.edit { remove(key) }
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    // 获取数据（需指定类型）
    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> sp.getString(key, defaultValue) as T
            is Int -> sp.getInt(key, defaultValue) as T
            is Boolean -> sp.getBoolean(key, defaultValue) as T
            is Float -> sp.getFloat(key, defaultValue) as T
            is Long -> sp.getLong(key, defaultValue) as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    // 删除数据
    fun remove(key: String) = sp.edit { remove(key) }

    // 清空全部数据
    fun clear() = sp.edit { clear() }

    private inline fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
        edit().apply(action).apply()
    }
}

