package com.neworange.yujingapp

import android.app.Application
import cn.jiguang.api.utils.JCollectionAuth
import cn.jpush.android.api.JPushInterface

class Application:Application(){
    override fun onCreate() {
        super.onCreate()
        initJPush()
    }

    private fun initJPush() {
        // 调试模式（开发阶段启用，发布时关闭）
        JPushInterface.setDebugMode(true)

        // 初始化极光推送
        JPushInterface.init(this)
        JCollectionAuth.setAuth(this, true); //如初始化被拦截过，将重试初始化过程
    }
}