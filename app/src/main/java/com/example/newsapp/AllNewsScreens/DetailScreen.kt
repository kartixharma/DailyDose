package com.example.newsapp.AllNewsScreens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.network.Article
import kotlinx.serialization.descriptors.PrimitiveKind

@Composable
fun DetailScreen(article: Article, addToDB: (Article) -> Unit, web: (Article) -> Unit, intent: () -> Unit) {
    val context = LocalContext.current
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    addToDB(article)
                    Toast.makeText(context, "Article saved successfully", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier.size(70.dp),
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Filled.Star, contentDescription = null, Modifier.size(30.dp))
            }
        }
    ) {it->
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            item {
                Text(
                    text = article.title.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                if (article.author.toString() != "null") {
                    Text(
                        text = "Author: ${article.author.toString()}",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(article.urlToImage)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable._33_2332677_image_500580_placeholder_transparent),
                    error = painterResource(R.drawable.nope_not_here),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(16.dp))
                )
                if (article.description.toString() != "null") {
                Text(modifier = Modifier.padding(vertical = 10.dp),
                    text = article.description.toString(),
                    color = MaterialTheme.colorScheme.onBackground
                    )
                }
                if (article.content.toString() != "null") {
                    Text(
                        text = article.content.toString(),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(modifier = Modifier.padding(vertical = 10.dp).clickable { web(article) },
                    text = "Read more..",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp, textDecoration = TextDecoration.Underline)
                Divider(modifier = Modifier.padding(vertical = 16.dp), color = MaterialTheme.colorScheme.onBackground)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = intent) {
                        Icon(modifier = Modifier.size(30.dp),
                            imageVector = Icons.Outlined.Share,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary)
                    }
                }
            }
        }
    }
}
