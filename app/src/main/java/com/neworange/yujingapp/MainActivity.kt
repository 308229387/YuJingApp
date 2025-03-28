package com.neworange.yujingapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 延迟 2 秒后跳转
        Handler().postDelayed({
            // 创建跳转 Intent
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // 关闭当前 Activity（可选，根据需求决定）
        }, 2000) // 2000 毫秒 = 2 秒
    }
}
