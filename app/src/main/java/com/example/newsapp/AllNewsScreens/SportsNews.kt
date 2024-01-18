package com.example.newsapp.AllNewsScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.network.Article
import com.example.newsapp.NewsUiState

@Composable
fun SportsNews(newsUiState: NewsUiState){
    Text(text = "Sports", modifier = Modifier.padding(start=10.dp), style = MaterialTheme.typography.headlineLarge)
    when(newsUiState){
        NewsUiState.Error -> Text(text = "error")
        NewsUiState.Loading -> Text(text = "loading")
        is NewsUiState.Success -> {
            LazyColumn(modifier = Modifier.padding(top = 40.dp)){
                items(newsUiState.news.articles){
                    SNewsCard(article=it)
                }
            }

        }
    }
}
@Composable
fun SNewsCard(article: Article){
    Card(elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
            .height(300.dp)) {
        Row {
            Column(modifier = Modifier
                .padding(start = 10.dp)
                .height(280.dp)) {
                AsyncImage(model = ImageRequest.Builder(context = LocalContext.current)
                    .data(article.urlToImage)
                    .crossfade(true)
                    .build(),
                    placeholder = painterResource(R.drawable._33_2332677_image_500580_placeholder_transparent),
                    error = painterResource(
                        R.drawable.nope_not_here
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.aspectRatio(16f/9f).scale(1.1f)
                        .clip(RoundedCornerShape(12.dp)))
                Text(text = article.title.toString(), fontWeight = FontWeight.Bold)
                Text(text = article.content.toString(), maxLines = 2, overflow = TextOverflow.Ellipsis)

            }
        }

    }
}