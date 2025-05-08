package com.neworange.yujingapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import cn.jpush.android.api.JPushInterface
import com.neworange.yujingapp.utils.SPManager

class SplashActivity : ComponentActivity() {
    private lateinit var code: String
    private lateinit var jpush: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        SPManager.init(this)

        code = SPManager.get("code", "")
        jpush = SPManager.get("jpush", "")
        Log.i("song_test", "jpush = $jpush")

        if (jpush.isNotBlank()) {
            // 用户登录成功后调用
            val sequence = (System.currentTimeMillis() % 10000).toInt() // 生成唯一请求序列号
            JPushInterface.setAlias(
                this,
                sequence,
                jpush
            )

        }
        // 延迟 2 秒后跳转
        Handler().postDelayed({
            if (code.isNotBlank()) {
                startActivity(Intent(this, WarningListActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 1500) // 2000 毫秒 = 2 秒
    }
}
