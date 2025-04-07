package com.neworange.yujingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.neworange.yujingapp.data.WarningData
import com.neworange.yujingapp.utils.SPManager
import com.neworange.yujingapp.viewModel.WarningViewModel

class WarningDetailActivity : ComponentActivity() {
    private lateinit var viewModel: WarningViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_warning_detail)

        val clickedItem = intent.getSerializableExtra("DETAIL_DATA") as? WarningData
        val itemId = clickedItem?.id

        // 处理 id
        if (itemId != null) {
            viewModel = ViewModelProvider(this).get(WarningViewModel::class.java)
            SPManager.init(this)
            val code = SPManager.get<String>("code", "")
            viewModel.warningDetail(code, itemId.toString())
        } else {
            finish()
        }
        // 处理数据不存在的情况，例如关闭 Activity
    }
}
