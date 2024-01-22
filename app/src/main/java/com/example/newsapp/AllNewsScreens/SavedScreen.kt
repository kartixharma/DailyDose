package com.example.newsapp.AllNewsScreens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.NewsState
import com.example.newsapp.R
import com.example.newsapp.network.Article
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(state: NewsState, isClicked:(Article) -> Unit, onSwiped:(Article)->Unit) {
    if(state.savedNews.isEmpty()){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(textAlign = TextAlign.Center, text = "No saved news!",
                modifier = Modifier.padding(top = 30.dp),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Gray)
        }
    }
    Text(text = "Saved News", modifier = Modifier.padding(10.dp), style = MaterialTheme.typography.headlineLarge)
    LazyColumn(modifier = Modifier.padding(top = 50.dp)){
            items(items = state.savedNews, key = { article -> article.id!! }) { article->
                val stat = rememberDismissState(confirmValueChange = {
                    if (it == DismissValue.DismissedToStart){
                        onSwiped(article)
                        true
                    }
                    else{
                        false
                    }
                })
                SwipeToDismiss(state = stat, background = {
                    val iconScale by animateFloatAsState(
                        targetValue = if (stat.targetValue == DismissValue.DismissedToStart) 1.3f else 0.8f,
                        label = ""
                    )
                    Box(modifier = Modifier
                        .padding(vertical = 9.dp, horizontal = 16.dp)
                        .fillMaxSize()
                        .background(Color.Transparent, shape = RoundedCornerShape(12.dp))){
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete",
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 20.dp)
                                .scale(iconScale))
                    }
                }, dismissContent = {
                    SavedNewsCard(article = article, isClicked = {
                        isClicked(it)
                    })
                },directions = setOf(DismissDirection.EndToStart))
            }
    }
}

@Composable
fun SavedNewsCard(article: Article, isClicked: (Article) -> Unit) {
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