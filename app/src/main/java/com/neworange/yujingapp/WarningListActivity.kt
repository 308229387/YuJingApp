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
            Item("标题1", "2025-12-12 12:22:22","北京市海淀区",""),
            Item("标题1", "2025-12-12 12:22:22","北京市海淀区",""),
            Item("标题1", "2025-12-12 12:22:22","北京市海淀区","")
        )

        recyclerView.adapter = ItemAdapter(itemList)
    }
}