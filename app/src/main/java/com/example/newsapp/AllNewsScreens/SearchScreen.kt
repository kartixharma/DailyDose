package com.example.newsapp.AllNewsScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.LoadingItem
import com.example.newsapp.NewsUiState
import com.example.newsapp.NewsViewModel
import com.example.newsapp.R
import com.example.newsapp.ShimmerItem
import com.example.newsapp.network.Article

@Composable
fun SearchScreen(isClicked: (Article) -> Unit){
    val newsViewModel: NewsViewModel = viewModel(factory = NewsViewModel.Factory)
    val newsUiState = newsViewModel.newsUiState
    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 30.dp, top = 15.dp, end = 30.dp),
        value = newsViewModel.search,
        onValueChange ={newsViewModel.setSrc(it)},
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null)},
        shape = CircleShape,
        placeholder = { Text(text = "Search...")}
    )
    when(newsUiState){
        NewsUiState.Error -> {
            Column(modifier = Modifier.fillMaxSize().padding(top = 200.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.no_wifi), contentDescription = null, modifier = Modifier)
            }
        }
        NewsUiState.Loading -> {
        }
        is NewsUiState.Success -> {
            LazyColumn(modifier = Modifier.padding(top = 80.dp)){
                items(newsUiState.news.articles){
                    SrNewsCard(article=it, isClicked = {isClicked(it)})
                }
            }

        }
    }
}

@Composable
fun SrNewsCard(article: Article, isClicked: (Article) -> Unit) {
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