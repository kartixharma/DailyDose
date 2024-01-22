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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.LoadingItem
import com.example.newsapp.NewsViewModel
import com.example.newsapp.R
import com.example.newsapp.network.Article
import com.example.newsapp.ShimmerCard
import com.example.newsapp.ShimmerItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopNews(newsViewModel: NewsViewModel, isClicked:(Article) -> Unit, retry:()->Unit){
    val news = remember { newsViewModel.getNews("") }
    val newsList = news.collectAsLazyPagingItems()
    val pagerState = rememberPagerState(
        pageCount = {5}, initialPage = 1
    )
    Text(text = "Top Headlines", modifier = Modifier.padding(start=15.dp, top = 10.dp), style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.SemiBold
    )
    when (newsList.loadState.refresh){
        is LoadState.Error -> {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.no_wifi), contentDescription = null, modifier = Modifier)
                    TextButton(onClick = retry) {
                        Text(text = "Retry", style = MaterialTheme.typography.headlineMedium)
                    }
                }
        }
        LoadState.Loading -> {
            ShimmerCard()
        }
        is LoadState.NotLoading -> {
            HorizontalPager(state = pagerState, contentPadding = PaddingValues(horizontal = 40.dp)) {
                if(newsList.itemCount!=0){
                    val pgofset = (pagerState.currentPage - it) + pagerState.currentPageOffsetFraction
                    Card(elevation = CardDefaults.cardElevation(5.dp),
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .clickable { isClicked(newsList[it]!!) }
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
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(model = ImageRequest.Builder(context = LocalContext.current)
                                .data(newsList[it]!!.urlToImage)
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
                            Image(painter = painterResource(id = R.drawable.whatsapp_image_2024_01_22_at_14_19_20_0d04cdfe,
                            ), contentDescription = null, modifier = Modifier.fillMaxSize())
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(text = newsList[it]!!.title.toString(),
                                    fontSize = 17.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    }
                }
            }
            Row(modifier = Modifier
                .padding(top = 355.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                repeat(5){
                    val color = if (pagerState.currentPage==it){
                        if(isSystemInDarkTheme()) Color(0xFF1560bd) else Color(0xFF0047ab) }
                    else Color.LightGray
                    val size = if (pagerState.currentPage==it){ 13.dp} else 10.dp
                    Box(modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .size((size))
                        .background(color))
                }
            }
        }

    }

            LazyColumn(modifier = Modifier.padding(top = 375.dp)){
                items(newsList.itemCount){
                    it?.let {  val item = newsList[it]
                        if(item!!.urlToImage.toString()!="null") {
                            NewsCard(article = item, isClicked = {
                                isClicked(it)
                            })
                        }
                    }
                }
                when (newsList.loadState.append) {
                    is LoadState.Error -> {
                        item {
                            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                                TextButton(onClick = retry) {
                                    Text(text = "Retry", style = MaterialTheme.typography.headlineMedium)
                                }
                            }
                        }
                    }
                    LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                    is LoadState.NotLoading -> Unit
                }
                when (newsList.loadState.refresh){
                    is LoadState.Error -> {
                    }
                    LoadState.Loading -> {
                        items(10){
                            ShimmerItem()
                        }
                    }
                    is LoadState.NotLoading -> {}
                }
            }
    }
@Composable
fun NewsCard(article: Article, isClicked: (Article) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .fillMaxWidth()
            .clickable { isClicked(article) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
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
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = article.title.toString(),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = article.publishedAt.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
