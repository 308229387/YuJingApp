package com.neworange.yujingapp


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.neworange.yujingapp.net.NetworkResult
import com.neworange.yujingapp.viewModel.UserViewModel

class LoginActivity : ComponentActivity() {
    private var backPressedTime: Long = 0
    lateinit var loginBtn : TextView
    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        viewModel.userInfoLiveData.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    // 请求成功，更新 UI
                    val user = result.data
                    val intent = Intent(this, WarningListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is NetworkResult.Error -> {
                    // 显示错误信息
                    Toast.makeText(this, "错误：${result.message}", Toast.LENGTH_SHORT).show()
                }
                NetworkResult.NetworkError -> {
                    Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }

        loginBtn = findViewById(R.id.login_btn)
        loginBtn.setOnClickListener { v: View? ->
            viewModel.fetchUserInfo("18519266665")
        }
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime < 2000) {
            super.onBackPressed()
            finish()
        } else {
            // 第一次按下或超时后，更新记录时间并提示用户
            backPressedTime = currentTime
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show()
        }
    }
}
