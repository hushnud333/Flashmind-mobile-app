package com.example

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        WebPageScreen()
      }
    }
  }
}

@Composable
fun WebPageScreen() {
  AndroidView(
    modifier = Modifier.fillMaxSize(),
    factory = { context ->
      WebView(context).apply {
        settings.apply {
          javaScriptEnabled = true
          domStorageEnabled = true
          databaseEnabled = true
          allowFileAccess = true
          allowContentAccess = true
          loadWithOverviewMode = true
          useWideViewPort = true
          mediaPlaybackRequiresUserGesture = false
        }
        webViewClient = object : WebViewClient() {
          override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            // Inject the API key securely from BuildConfig so it is available to the web app
            val apiKey = BuildConfig.GEMINI_API_KEY
            view?.evaluateJavascript("window.GEMINI_API_KEY = '$apiKey'; console.log('API Key Injected');", null)
          }
        }
        webChromeClient = WebChromeClient()
        loadUrl("file:///android_asset/index.html")
      }
    }
  )
}
