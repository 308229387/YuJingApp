package com.neworange.yujingapp


import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.neworange.yujingapp.net.NetworkResult
import com.neworange.yujingapp.viewModel.UserViewModel

class LoginActivity : ComponentActivity() {
    private var backPressedTime: Long = 0
    lateinit var loginBtn: TextView
    lateinit var viewModel: UserViewModel

    private companion object {
        const val REQUEST_CODE_PHONE_PERMISSION = 1001
        val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.READ_PHONE_STATE)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // 检查权限状态
        var phone: String = ""

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        viewModel.userInfoLiveData.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    Toast.makeText(this,result.data.token,Toast.LENGTH_SHORT).show()
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
//            checkPermissions()
//            showLoginDialog(this)
        }
    }

    /**
     * 权限检查入口方法
     */
    fun checkPermissions() {
        val requiredPermissions = if (Build.VERSION.SDK_INT >= 33) {
            arrayOf(Manifest.permission.READ_PHONE_NUMBERS)
        } else {
            arrayOf(Manifest.permission.READ_PHONE_STATE)
        }

        if (requiredPermissions.any {
                ContextCompat.checkSelfPermission(this, it) != PERMISSION_GRANTED
            }) {
            ActivityCompat.requestPermissions(
                this,
                requiredPermissions,
                REQUEST_CODE_PHONE_PERMISSION
            )
        } else {
            showPhoneNumberToast() // 已授权时直接获取
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PHONE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    // 权限通过后获取手机号
                    showPhoneNumberToast()
                } else {
                    // 显示权限拒绝引导
                    showPermissionDeniedDialog()
                }
            }
        }
    }

    /**
     * 获取手机号并显示 Toast
     */
    private fun showPhoneNumberToast() {
        val phoneNumber = try {
            val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            telephonyManager.line1Number ?: "无法获取号码"
        } catch (e: SecurityException) {
            "权限未正常授予"
        }

        Toast.makeText(
            this,
            "当前设备号码：${phoneNumber.takeIf { it.isNotBlank() } ?: "空值"}",
            Toast.LENGTH_LONG
        ).show()
    }


    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("权限被拒绝")
            .setMessage("需要电话权限以获取设备信息，请前往设置开启")
            .setPositiveButton("去设置") { _, _ ->
                // 跳转应用详情页
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
            .setNegativeButton("取消", null)
            .show()
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

    fun showLoginDialog(context: Context) {
// 创建弹窗对象
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme) // 使用自定义主题

// 设置布局并强制展开
        dialog.setContentView(R.layout.dialog_login_bottom)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

// ⭐ 修复圆角的核心代码：移除默认背景叠加
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.behavior.isFitToContents = true // 确保内容填充高度

// 显示弹窗
        dialog.show()
    }

}
