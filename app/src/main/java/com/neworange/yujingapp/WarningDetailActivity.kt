package com.neworange.yujingapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import com.neworange.yujingapp.data.WarningData
import com.neworange.yujingapp.utils.SPManager
import com.neworange.yujingapp.viewModel.WarningViewModel

class WarningDetailActivity : ComponentActivity() {
    private lateinit var viewModel: WarningViewModel
    private lateinit var mAgentWeb: AgentWeb
    private lateinit var code: String
    private lateinit var type: String
    private lateinit var uuid: String


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_warning_detail)

        val clickedItem = intent.getSerializableExtra("DETAIL_DATA") as? WarningData
        SPManager.init(this)
        code = SPManager.get<String>("code", "")
        type = if (clickedItem!!.type == 122) {
            "audio"
        } else {
            "video"
        }

        val uuidStr: String = clickedItem?.uuid ?: ""


        var url = "https://www.neworangegroup.com/play/index.html?id=" + clickedItem.id + "&code=" + code + "&type=" + type + uuidStr
//        var url = "https://www.neworangegroup.com/play/index.html?id=1739&code=3a84d444-ce84-472b-a285-976f9be1dc9f&type=video"
        Log.i("song_test", "url = " + url)

        // 初始化但不加载
        initAgentWeb()
        configureWebSettings()

        loadUrlWhenNeeded(url)
    }

    private fun initAgentWeb() {

        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(findViewById(R.id.web_container), ViewGroup.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebChromeClient(object : WebChromeClient() { // 处理全屏播放
                override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                    // 实现全屏逻辑
                }
            })
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .createAgentWeb()
            .ready()
            .go("about:blank")


    }


    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    private fun configureWebSettings() {
        val webSettings = mAgentWeb.agentWebSettings.webSettings
        webSettings.javaScriptEnabled = true // 启用 JS 支持视频播放控件 :ml-citation{ref="3" data="citationList"}
        webSettings.domStorageEnabled = true // 支持 HTML5 本地存储
        webSettings.mediaPlaybackRequiresUserGesture = false // 允许自动播放（部分设备限制）:ml-citation{ref="8" data="citationList"}
        webSettings.allowFileAccess = true
        webSettings.allowFileAccessFromFileURLs = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

    }


    // Button 点击事件或业务逻辑触发
    fun loadUrlWhenNeeded(url: String) {
        mAgentWeb.urlLoader.loadUrl(url)  // 动态加载目标 URL :ml-citation{ref="1,8" data="citationList"}
    }

}
