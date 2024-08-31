package com.example.task.presentation.main

import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.task.domain.entities.Item


@Composable
fun MainScreen(
    viewModel: MainScreenViewModel
) {
    val state = viewModel.state.collectAsState().value
    MainScreenContent(
        onButtonClick = { viewModel.getResource() },
        currentItem = state.currentItem
    )
}

@Composable
fun MainScreenContent(
    onButtonClick: () -> Unit,
    currentItem: Item
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when(currentItem.type) {
            "text" -> {
                TextItem(currentItem)
            }
            "webview" -> {
                WebViewItem(currentItem)
            }
            "image" -> {
                ImageItem(currentItem)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.8f)
            ) {
                Text(text = "Next")
            }
        }
    }
}

@Composable
fun TextItem(currentItem: Item) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.9f)
        .background(Color.Blue)) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = currentItem.message
        )
    }
}

@Composable
fun WebViewItem(currentItem: Item) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.9f)) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    settings.loadWithOverviewMode = true
                }
            },
            update = { webView ->
                webView.loadUrl(currentItem.url)
            }
        )
    }
}

@Composable
fun ImageItem(currentItem: Item) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.9f)) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            model = currentItem.url,
            contentDescription = "image"
        )
    }
}