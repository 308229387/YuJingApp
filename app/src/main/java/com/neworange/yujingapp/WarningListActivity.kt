package com.neworange.yujingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neworange.yujingapp.adapter.ItemAdapter
import com.neworange.yujingapp.data.Item

class WarningListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_warning_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 1. 准备测试数据
        val itemList = listOf(
            Item(1, "标题1", "描述1"),
            Item(2, "标题2", "描述2"),
            Item(3, "标题3", "描述3")
        )

        recyclerView.adapter = ItemAdapter(itemList)
    }
}