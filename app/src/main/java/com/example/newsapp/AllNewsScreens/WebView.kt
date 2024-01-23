package com.example.newsapp.AllNewsScreens

import android.content.Context
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.newsapp.network.Article

@Composable
fun WebView(article: Article, addToDB:(Article)->Unit) {
    val Context = LocalContext.current
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {addToDB(article)
                    Toast.makeText(Context, "Article saved successfully", Toast.LENGTH_SHORT).show()},
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier.size(70.dp),
                containerColor = MaterialTheme.colorScheme.inversePrimary
            ){
                Icon(Icons.Filled.Star, contentDescription = null, Modifier.size(30.dp))
            }
        }

    ){it->
        AndroidView(
            factory = { android.webkit.WebView(it).apply{
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient= WebViewClient()
                loadUrl(article.url.toString())
                settings.javaScriptEnabled = true
            } },
            update = { it.loadUrl(article.url.toString()) }
        )
    }

}