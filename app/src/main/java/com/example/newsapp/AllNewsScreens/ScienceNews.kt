package com.example.newsapp.AllNewsScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.NewsUiState
import com.example.newsapp.R
import com.example.newsapp.ShimmerItem
import com.example.newsapp.network.Article

@Composable
fun ScienceNews(newsUiState: NewsUiState, isClicked:(Article)->Unit, retry:()->Unit){
    Text(text = "Science", modifier = Modifier.padding(start=15.dp), style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onPrimaryContainer
    )
    when(newsUiState){
        NewsUiState.Error -> {
            Column(modifier = Modifier.fillMaxSize().padding(top = 200.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.no_wifi), contentDescription = null, modifier = Modifier)
                TextButton(onClick = retry) {
                    Text(text = "Retry", style = MaterialTheme.typography.headlineMedium)
                }
            }
        }
        NewsUiState.Loading -> {
            LazyColumn(modifier = Modifier.padding(top = 40.dp)){
                items(10){
                    ShimmerItem()
                }
            }
        }
        is NewsUiState.Success -> {
            LazyColumn(modifier = Modifier.padding(top = 40.dp)){
                items(newsUiState.news.articles){
                    ScNewsCard(article=it, isClicked={
                        isClicked(it)
                    })
                }
            }
        }
    }
}
@Composable
fun ScNewsCard(article: Article, isClicked: (Article) -> Unit){
    Card(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .clickable { isClicked(article) }
            .height(120.dp), colors = CardDefaults.cardColors(Color.Transparent)) {
        Row {
            AsyncImage(model = ImageRequest.Builder(context = LocalContext.current)
                .data(article.urlToImage)
                .crossfade(true)
                .build(),
                placeholder = painterResource(R.drawable._33_2332677_image_500580_placeholder_transparent),
                error = painterResource(R.drawable.nope_not_here),
                contentDescription = null,
                contentScale= ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(11.dp)))
            Column(modifier = Modifier
                .padding(start = 10.dp, top = 2.dp)
                .fillMaxHeight()) {
                Text(text = article.title.toString(), maxLines = 3, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold)
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = article.publishedAt.toString(), style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
    Divider(modifier = Modifier.padding(5.dp))
}