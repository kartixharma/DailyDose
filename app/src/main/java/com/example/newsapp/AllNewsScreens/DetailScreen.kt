package com.example.newsapp.AllNewsScreens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Info
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
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.network.Article
import kotlinx.serialization.descriptors.PrimitiveKind

@Composable
fun DetailScreen(article: Article, addToDB:(Article)-> Unit, web:(Article)->Unit){
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

    )
    {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, article.url.toString())
        }
        LazyColumn( modifier = Modifier.padding(it)) {
            item {
                AsyncImage(model = ImageRequest.Builder(context = LocalContext.current)
                    .data(article.urlToImage)
                    .crossfade(true)
                    .build(),
                    placeholder = painterResource(R.drawable._33_2332677_image_500580_placeholder_transparent),
                    error = painterResource(R.drawable.nope_not_here),
                    contentDescription = null,
                    contentScale= ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(11.dp)))
                Text(modifier = Modifier.padding(10.dp),
                    text = article.title.toString(),
                    fontWeight = FontWeight.Bold
                )
                Divider(modifier = Modifier.padding(5.dp))
                if(article.content.toString()!="null"){
                    Text(modifier = Modifier.padding(10.dp),
                        text = article.content.toString()
                    )
                }
                Divider(modifier = Modifier.padding(5.dp))
                if(article.author.toString()!="null"){
                    Text(modifier = Modifier.padding(10.dp),
                        text = "Author: ${article.author.toString()}",
                    )
                }
                Text(modifier = Modifier.padding(10.dp),
                    text = "Published at: ${article.publishedAt.toString()}")
                Row(modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Start) {
                    IconButton(onClick = { web(article) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.worldwide),
                            contentDescription = null,
                            Modifier.scale(0.7f)
                        )
                    }
                    IconButton(onClick = {startActivity(Context, intent, null)}) {
                        Icon(imageVector = Icons.Filled.Share, contentDescription = null)
                    }
                }
            }
        }
    }

}