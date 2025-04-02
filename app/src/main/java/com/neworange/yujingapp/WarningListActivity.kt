package com.neworange.yujingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neworange.yujingapp.adapter.ItemAdapter
import com.neworange.yujingapp.data.Item
import com.neworange.yujingapp.net.NetworkResult
import com.neworange.yujingapp.utils.SPManager
import com.neworange.yujingapp.viewModel.WarningViewModel

class WarningListActivity : ComponentActivity() {
    private lateinit var viewModel: WarningViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_warning_list)

        // 初始化 ViewModel
        viewModel = ViewModelProvider(this).get(WarningViewModel::class.java)
        SPManager.init(this)
        val code = SPManager.get<String>("code", "")

        // 调用方法（示例：传入手机号）
        viewModel.warningList(code,"18519266665")

        // 观察数据变化
        observeData()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 1. 准备测试数据
        val itemList = listOf(
            Item("标题1", "2025-12-12 12:22:22", "北京市海淀区", ""),
            Item("标题1", "2025-12-12 12:22:22", "北京市海淀区", ""),
            Item("标题1", "2025-12-12 12:22:22", "北京市海淀区", "")
        )

        recyclerView.adapter = ItemAdapter(itemList)
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
//                    updateUI(warningList)
                }
                is NetworkResult.Error -> {
                    // 处理错误
//                    showError(result.message)
                }

                else -> {}
            }
        }
    }
}
