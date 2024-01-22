package com.example.newsapp

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
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

@Composable
fun ShimmerCard(){
    Text(text = "Top Headlines", modifier = Modifier.padding(start=15.dp, top = 10.dp), style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.SemiBold
    )
        Card(
            modifier = Modifier
                .padding(top = 50.dp, start = 40.dp, end = 40.dp )
                .height(300.dp).fillMaxWidth(), shape = RoundedCornerShape(15.dp)
                ){
            Column(modifier = Modifier.fillMaxSize()
                .shimmerEffect()) {
            }
        }

    Row(modifier = Modifier
        .padding(top = 355.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        repeat(5){
            val color = if (it==1){ if(isSystemInDarkTheme()) Color(0xFF1560bd) else Color(0xFF0047ab) }else Color.LightGray
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
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.clip(RoundedCornerShape(11.dp))
                    .shimmerEffect()
                    .size(120.dp)
            ){}
            Column(modifier = Modifier
                .padding(start = 16.dp)
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
                Color(0xFFC5C5C5),
                Color(0xFF8F8B8B),
                Color(0xFFC5C5C5)
            ),
            start = Offset(startofst, 0f),
            end = Offset(startofst+size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}