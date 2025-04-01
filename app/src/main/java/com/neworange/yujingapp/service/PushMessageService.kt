package com.neworange.yujingapp.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.JPushInterface
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver
import com.neworange.yujingapp.LoginActivity

class PushMessageService : JPushMessageReceiver() {
    var TAG: String = "PushMessageService"

    override fun onMessage(context: Context, customMessage: CustomMessage) {
        Log.e(TAG, "[onMessage] $customMessage")
        val intent = Intent("com.jiguang.demo.message")
        intent.putExtra("msg", customMessage.message)
        context.sendBroadcast(intent)
    }

    override fun onNotifyMessageOpened(context: Context, message: NotificationMessage) {
        Log.e(TAG, "[onNotifyMessageOpened] $message")
        try {
            //打开自定义的Activity
            val i = Intent(context, LoginActivity::class.java)
            val bundle = Bundle()
            bundle.putString(JPushInterface.EXTRA_NOTIFICATION_TITLE, message.notificationTitle)
            bundle.putString(JPushInterface.EXTRA_ALERT, message.notificationContent)
            i.putExtras(bundle)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(i)
        } catch (throwable: Throwable) {
        }
    }
}