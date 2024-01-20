package com.example.newsapp

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShimmerCard(){
    val pagerState = rememberPagerState(
        pageCount = {5}, initialPage = 1
    )
    Text(text = "Top Headlines", modifier = Modifier.padding(start=15.dp), style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onPrimaryContainer
    )
        Card(
            modifier = Modifier
                .padding(top = 40.dp, start = 30.dp, end = 30.dp )
                .height(300.dp).fillMaxWidth(), shape = RoundedCornerShape(15.dp)
                ){
            Column(modifier = Modifier.fillMaxSize()
                .shimmerEffect()) {
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
}
@Composable
fun ShimmerItem(){
    Card(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .height(120.dp), colors = CardDefaults.cardColors(Color.Transparent)) {
        Row {
            Column(
                modifier = Modifier
                    .shimmerEffect()
                    .size(120.dp)
                    .clip(RoundedCornerShape(11.dp))){}
            Column(modifier = Modifier
                .padding(start = 10.dp, top = 2.dp)
                .fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .shimmerEffect(),
                    horizontalAlignment = Alignment.End
                ) {
                }
            }
        }
    }
    Divider(modifier = Modifier.padding(5.dp))
}

fun Modifier.shimmerEffect(): Modifier = composed{
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "")
    val startofst by transition.animateFloat(
        initialValue = -2*size.width.toFloat(),
        targetValue = 2*size.width.toFloat() ,
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ), label = ""
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5)
            ),
            start = Offset(startofst, 0f),
            end = Offset(startofst+size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}