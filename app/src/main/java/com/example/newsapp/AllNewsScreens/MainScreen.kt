package com.example.newsapp.AllNewsScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.NewsViewModel
import com.example.newsapp.network.Article

enum class NewsScreens {
    Main,
    Health,
    Entertainment,
    Sports,
    Science,
    Tech,
    Bus
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(newsViewModel:NewsViewModel, isClicked:(Article)->Unit){
    val navController = rememberNavController()
    var selectedCard by rememberSaveable{
        mutableStateOf(0)
    }
    val categories = listOf(
        "Headlines",
        "Health",
        "Entertainment",
        "Sports",
        "Science",
        "Technology",
        "Business"
    )
    LazyRow(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        items(categories.size) { index ->
            val name = categories[index]
            Card(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface,RoundedCornerShape(16.dp) )
                    .padding(end = 10.dp)
                    .height(50.dp)
                    .clickable {
                        selectedCard = index
                        when (index) {
                            0 -> navController.navigate(NewsScreens.Main.name)
                            1 -> navController.navigate(NewsScreens.Health.name)
                            2 -> navController.navigate(NewsScreens.Entertainment.name)
                            3 -> navController.navigate(NewsScreens.Sports.name)
                            4 -> navController.navigate(NewsScreens.Science.name)
                            5 -> navController.navigate(NewsScreens.Tech.name)
                            6 -> navController.navigate(NewsScreens.Bus.name)
                        }
                    },
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(if (selectedCard == index) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }

    NavHost(
                navController = navController,
                startDestination = NewsScreens.Main.name,
                modifier = Modifier.padding(top = 60.dp)
            ) {
                composable(route = NewsScreens.Main.name){
                    TopNews(newsViewModel= newsViewModel, isClicked ={isClicked(it)}, retry = {navController.navigate(NewsScreens.Main.name)})
                }
                composable(route = NewsScreens.Health.name){
                    HealthNews(newsViewModel= newsViewModel, isClicked={isClicked(it)}, retry = {navController.navigate(NewsScreens.Health.name)})
                }
                composable(route = NewsScreens.Entertainment.name){
                    EntNews(newsViewModel= newsViewModel, isClicked={isClicked(it)}, retry = {navController.navigate(NewsScreens.Entertainment.name)})
                }
                composable(route = NewsScreens.Sports.name){
                    SportsNews(newsViewModel= newsViewModel , isClicked={isClicked(it)}, retry = {navController.navigate(NewsScreens.Sports.name)})
                }
                composable(route = NewsScreens.Science.name){
                    ScienceNews(newsViewModel= newsViewModel, isClicked={isClicked(it)}, retry = {navController.navigate(NewsScreens.Science.name)})
                }
                composable(route = NewsScreens.Tech.name){
                    TechNews(newsViewModel= newsViewModel, isClicked={isClicked(it)}, retry = {navController.navigate(NewsScreens.Tech.name)})
                }
                composable(route = NewsScreens.Bus.name){
                    BusNews(newsViewModel= newsViewModel, isClicked={isClicked(it)}, retry = {navController.navigate(NewsScreens.Bus.name)})
                }

            }
}