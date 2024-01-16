package com.example.newsapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.AllNewsScreens.EntNews
import com.example.newsapp.AllNewsScreens.HealthNews
import com.example.newsapp.AllNewsScreens.SportsNews
import com.example.newsapp.AllNewsScreens.TopNews

enum class NewsScreens {
    Main,
    Health,
    Entertainment,
    Sports
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    val newsViewModel: NewsViewModel = viewModel(factory = NewsViewModel.Factory)
    val newsUiState = newsViewModel.newsUiState
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    LazyRow {
                        item {
                            Card(modifier = Modifier
                                .padding(end = 10.dp),
                                shape = CircleShape
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
                                            newsViewModel.getTop()
                                            navController.navigate(NewsScreens.Main.name)
                                        })
                            }
                            Card(modifier = Modifier
                                .padding(end = 10.dp),
                                shape = CircleShape
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
                                            newsViewModel.getHealth()
                                            navController.navigate(NewsScreens.Health.name)
                                        })
                            }
                            Card(modifier = Modifier
                                .padding(end = 10.dp),
                                shape = CircleShape
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
                                            newsViewModel.getEnt()
                                            navController.navigate(NewsScreens.Entertainment.name)
                                        })
                            }
                            Card(modifier = Modifier
                                .padding(end = 10.dp),
                                shape = CircleShape
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
                    TopNews(newsUiState = newsUiState)
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