package com.neworange.yujingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neworange.yujingapp.adapter.ItemAdapter
import com.neworange.yujingapp.data.WarningData
import com.neworange.yujingapp.net.NetworkResult
import com.neworange.yujingapp.utils.SPManager
import com.neworange.yujingapp.viewModel.WarningViewModel

class WarningListActivity : ComponentActivity() {
    private lateinit var viewModel: WarningViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private val dataList = mutableListOf<WarningData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_warning_list)


        // 初始化 RecyclerView
        setupRecyclerView()


        // 初始化 ViewModel
        viewModel = ViewModelProvider(this).get(WarningViewModel::class.java)
        SPManager.init(this)
        val code = SPManager.get<String>("code", "")

        // 调用方法（示例：传入手机号）
        viewModel.warningList(code, "18519266665")

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
            Toast.makeText(this, "点击了：${clickedItem.modelName}", Toast.LENGTH_SHORT).show()
            // 跳转到详情页示例
            startActivity(Intent(this, MainActivity::class.java).apply {
                putExtra("DETAIL_DATA", clickedItem)
            })
        }

        // 添加分割线（需自定义ItemDecoration）
//        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        recyclerView.adapter = adapter
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
                    Toast.makeText(this, result.data.toString(), Toast.LENGTH_SHORT).show()
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
