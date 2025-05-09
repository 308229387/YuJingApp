package com.neworange.yujingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.jpush.android.api.JPushInterface
import com.neworange.yujingapp.adapter.ItemAdapter
import com.neworange.yujingapp.data.AppConstants
import com.neworange.yujingapp.data.WarningData
import com.neworange.yujingapp.net.NetworkResult
import com.neworange.yujingapp.utils.SPManager
import com.neworange.yujingapp.viewModel.WarningViewModel

class WarningListActivity : ComponentActivity() {
    private lateinit var viewModel: WarningViewModel
    private lateinit var adapter: ItemAdapter
    private lateinit var code:String
    private lateinit var phone:String
    private val dataList = mutableListOf<WarningData>()
    private var backPressedTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_warning_list)

        // 初始化 RecyclerView
        setupRecyclerView()


        // 初始化 ViewModel
        viewModel = ViewModelProvider(this).get(WarningViewModel::class.java)
        SPManager.init(this)
        code = SPManager.get(AppConstants.CODE, "")
        phone = SPManager.get(AppConstants.PHONE,"")


        // 调用方法（示例：传入手机号）
        viewModel.warningList(code, phone)

        // 观察数据变化
        observeData()

    }


    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // 设置布局管理器
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            orientation = RecyclerView.VERTICAL
        }

        // 初始化 Adapter 并设置点击事件
        adapter = ItemAdapter(dataList) { clickedItem ->
            // 处理点击事件
            // 跳转到详情页示例
            startActivity(Intent(this, WarningDetailActivity::class.java).apply {
                putExtra("DETAIL_DATA", clickedItem)
            })
        }

        // 添加分割线（需自定义ItemDecoration）
//        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        recyclerView.adapter = adapter
    }

    override fun onNewIntent(intent1: Intent?) {
        super.onNewIntent(intent1)
        Log.i("song_test","执行了onNewIntent方法")
        val bundle = intent1!!.extras
        bundle?.let {
            val data = it.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE, "")
            if(data.isNotEmpty()){
                Log.i("song_test","接收到intent信息 = "+data)
                viewModel.warningList(code, phone)
            }
        }
    }



    private fun observeData() {
        viewModel.warningListLiveData.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    // 显示加载状态（如 ProgressBar）
                }

                is NetworkResult.Success -> {
                    // 处理成功数据
                    val warningList = result.data
                    adapter.updateData(warningList)
                }

                is NetworkResult.Error -> {
                    if(result.code==401){
                        SPManager.clear()
                        startActivity(Intent(this,LoginActivity::class.java))
                    }
                    // 处理错误
//                    showError(result.message)
                }

                else -> {}
            }
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
