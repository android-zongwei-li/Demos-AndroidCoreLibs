package com.lizw.ui_demos.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.webkit.WebView
import android.webkit.WebViewClient
import com.lizw.ui_demos.R

/**
 *
 * NotesUrl：WebView使用.md
 * 技术点：
 */
class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val myWebView: WebView = findViewById(R.id.webview)

        // 不设置 webViewClient 的话，点击 WebView 中的链接时，会跳转到默认浏览器
        myWebView.webViewClient = WebViewClient()

//        myWebView.loadUrl("https://www.baidu.com")

        myWebView.settings.domStorageEnabled = true

//        loadLocal(myWebView)

        loadFromAssets(myWebView)
    }

    fun loadFromAssets(myWebView: WebView) {
        // 两种写法都可以
        val assetsDir = "file:android_asset/"
//        val assetsDir = "file:///android_asset/"

        val assetsUrl = assetsDir + "baidu.html"
        myWebView.loadUrl(assetsUrl)
    }

    fun loadLocal(myWebView: WebView) {
        val unencodedHtml = "<html><body>'%23' is the percent code for ‘#‘ </body></html>"
        val encodedHtml = Base64.encodeToString(unencodedHtml.toByteArray(), Base64.NO_PADDING)
        myWebView.loadData(encodedHtml, "text/html", "base64")
    }
}