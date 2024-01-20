package com.example.newsapp.AllNewsScreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.network.Article
import com.example.newsapp.NewsUiState
import com.example.newsapp.ShimmerCard
import com.example.newsapp.ShimmerItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopNews(newsUiState: NewsUiState, isClicked:(Article) -> Unit, retry:()->Unit){
    val pagerState = rememberPagerState(
        pageCount = {5}, initialPage = 1
    )
    Text(text = "Top Headlines", modifier = Modifier.padding(start=15.dp), style = MaterialTheme.typography.headlineMedium,
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
            ShimmerCard()
            LazyColumn(modifier = Modifier.padding(top=370.dp)){
                items(10){
                    ShimmerItem()
                }
            }
        }
        is NewsUiState.Success -> {
            HorizontalPager(state = pagerState, contentPadding = PaddingValues(horizontal = 40.dp)) {
                val pgofset = (pagerState.currentPage - it) + pagerState.currentPageOffsetFraction
                Card(elevation = CardDefaults.cardElevation(5.dp),
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .clickable { isClicked(newsUiState.news.articles[it]) }
                        .height(300.dp)
                        .graphicsLayer {
                            lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - kotlin.math
                                    .abs(pgofset)
                                    .coerceIn(0f, 1f)
                            )
                                .also { scale ->
                                    scaleX = scale
                                    scaleY = scale
                                }
                        }, colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)) {
                    Box {
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
                                .size(320.dp)
                        )
                        Image(painter = painterResource(id = R.drawable.bgrad,
                        ), contentDescription = null, modifier = Modifier.scale(1.02f))
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(text = newsUiState.news.articles[it].title.toString(),
                                fontSize = 17.sp,
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }
            Row(modifier = Modifier
                .padding(top = 350.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                repeat(5){
                    val color = if (pagerState.currentPage==it){ if(isSystemInDarkTheme()) Color(0xFF1560bd) else Color(0xFF0047ab) }else Color.LightGray
                    Box(modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .size(11.dp)
                        .background(color))
                }
                
            }
            LazyColumn(modifier = Modifier.padding(top = 370.dp)){
                items(newsUiState.news.articles){
                    if(it.urlToImage.toString()!="null") {
                        NewsCard(article = it, isClicked = {
                            isClicked(it)
                        })
                    }
                }
            }

        }
    }
}
@Composable
fun NewsCard(article: Article, isClicked:(Article) ->Unit){
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