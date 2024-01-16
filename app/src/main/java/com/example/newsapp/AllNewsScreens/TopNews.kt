package com.example.newsapp.AllNewsScreens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopNews(newsUiState: NewsUiState){
    val pagerState = rememberPagerState(
        pageCount = {5}
    )
    Text(text = "Headlines", modifier = Modifier.padding(start=10.dp), style = MaterialTheme.typography.headlineLarge)
    when(newsUiState){
        NewsUiState.Error -> Text(text = "error")
        NewsUiState.Loading -> Text(text = "loading")
        is NewsUiState.Success -> {
            HorizontalPager(state = pagerState) {
                val pgofset = (pagerState.currentPage - it) + pagerState.currentPageOffsetFraction
                val imgsize by animateFloatAsState(targetValue = if(pgofset!=0.0f) 0.75f else 1f,
                    animationSpec = tween(durationMillis = 300), label = ""
                )
                Card(elevation = CardDefaults.cardElevation(5.dp),
                    modifier = Modifier
                        .padding(top = 40.dp, start = 10.dp, end = 10.dp)
                        .height(300.dp)
                        .graphicsLayer {
                            scaleX = imgsize
                            scaleY = imgsize
                        }, colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)) {
                    Row {
                        Column(modifier = Modifier
                            .padding(start = 10.dp)
                            .height(300.dp)) {
                            AsyncImage(model = ImageRequest.Builder(context = LocalContext.current)
                                .data(newsUiState.news.articles[it].urlToImage)
                                .crossfade(true)
                                .build(),
                                placeholder = painterResource(R.drawable._33_2332677_image_500580_placeholder_transparent),
                                error = painterResource(
                                    R.drawable.nope_not_here
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .aspectRatio(16f / 9f)
                                    .scale(1.1f)
                                    .clip(RoundedCornerShape(12.dp)))
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = newsUiState.news.articles[it].title.toString(), fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                            Text(text = newsUiState.news.articles[it].content.toString(), maxLines = 2, overflow = TextOverflow.Ellipsis)

                        }
                    }
                }
            }
            LazyColumn(modifier = Modifier.padding(top = 350.dp)){
                items(newsUiState.news.articles){
                    if(it.urlToImage!="null") {
                        NewsCard(article = it)
                    }
                }
            }

        }
    }
}
@Composable
fun NewsCard(article: Article){
    Card(
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
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
                .padding(start = 10.dp, top = 2.dp)) {
                Text(text = article.title.toString(), maxLines = 2, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold)
                Text(text = article.content.toString(), maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(text = article.publishedAt.toString(), modifier = Modifier.padding(top=9.dp,start = 85.dp), style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}