package com.example.newsapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.AllNewsScreens.EntNews
import com.example.newsapp.AllNewsScreens.HealthNews
import com.example.newsapp.AllNewsScreens.SportsNews
import com.example.newsapp.AllNewsScreens.TopNews
import com.example.newsapp.network.Article

enum class NewsScreens {
    Main,
    Health,
    Entertainment,
    Sports
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(isClicked:(Article)->Unit){
    val newsViewModel: NewsViewModel = viewModel(factory = NewsViewModel.Factory)
    val newsUiState = newsViewModel.newsUiState
    val navController = rememberNavController()
    var selectedCard by rememberSaveable{
        mutableStateOf(1)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    LazyRow {
                        item {
                            Card(modifier = Modifier
                                .padding(end = 10.dp),
                                shape = CircleShape,
                                border = if(selectedCard==1) null else CardDefaults.outlinedCardBorder(),
                                colors = if(selectedCard==1) CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer) else CardDefaults.cardColors(Color.Transparent)
                            ) {
                                Text(text = "HeadLines",
                                    modifier = Modifier
                                        .padding(
                                            bottom = 5.dp,
                                            top = 5.dp,
                                            end = 10.dp,
                                            start = 10.dp
                                        )
                                        .clickable {
                                            selectedCard = 1
                                            newsViewModel.getTop()
                                            navController.navigate(NewsScreens.Main.name)
                                        })
                            }
                            Card(modifier = Modifier
                                .padding(end = 10.dp),
                                shape = CircleShape,
                                border = if(selectedCard==2) null else CardDefaults.outlinedCardBorder(),
                                colors = if(selectedCard==2) CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer) else CardDefaults.cardColors(Color.Transparent)
                            ) {
                                Text(text = "Health",
                                    modifier = Modifier
                                        .padding(
                                            bottom = 5.dp,
                                            top = 5.dp,
                                            end = 10.dp,
                                            start = 10.dp
                                        )
                                        .clickable {
                                            selectedCard = 2
                                            newsViewModel.getHealth()
                                            navController.navigate(NewsScreens.Health.name)
                                        })
                            }
                            Card(modifier = Modifier
                                .padding(end = 10.dp),
                                shape = CircleShape,
                                border = if(selectedCard==3) null else CardDefaults.outlinedCardBorder(),
                                colors = if(selectedCard==3) CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer) else CardDefaults.cardColors(Color.Transparent)
                            ){
                                Text(text = "Entertainment",
                                    modifier = Modifier
                                        .padding(
                                            bottom = 5.dp,
                                            top = 5.dp,
                                            end = 10.dp,
                                            start = 10.dp
                                        )
                                        .clickable {
                                            selectedCard = 3
                                            newsViewModel.getEnt()
                                            navController.navigate(NewsScreens.Entertainment.name)
                                        })
                            }
                            Card(modifier = Modifier
                                .padding(end = 10.dp),
                                shape = CircleShape,
                                border = if(selectedCard==4) null else CardDefaults.outlinedCardBorder(),
                                colors = if(selectedCard==4) CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer) else CardDefaults.cardColors(Color.Transparent)
                            ){
                                Text(text = "Sports",
                                    modifier = Modifier
                                        .padding(
                                            bottom = 5.dp,
                                            top = 5.dp,
                                            end = 10.dp,
                                            start = 10.dp
                                        )
                                        .clickable {
                                            selectedCard = 4
                                            newsViewModel.getSports()
                                            navController.navigate(NewsScreens.Sports.name)
                                        })
                            }
                        }
                    }
                }
            )
        },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = NewsScreens.Main.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = NewsScreens.Main.name){
                    TopNews(newsUiState = newsUiState, isClicked={isClicked(it)})
                }
                composable(route = NewsScreens.Health.name){
                    HealthNews(newsUiState = newsUiState)
                }
                composable(route = NewsScreens.Entertainment.name){
                    EntNews(newsUiState)
                }
                composable(route = NewsScreens.Sports.name){
                    SportsNews(newsUiState)
                }

            }
        }
    )

}