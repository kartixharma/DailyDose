@file:OptIn(ExperimentalFoundationApi::class)

package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.AllNewsScreens.DetailScreen
import com.example.newsapp.AllNewsScreens.MainScreen
import com.example.newsapp.AllNewsScreens.SavedScreen
import com.example.newsapp.AllNewsScreens.SearchScreen
import com.example.newsapp.AllNewsScreens.WebView
import com.example.newsapp.network.Article
import com.example.newsapp.ui.theme.NewsAppTheme

enum class Screens {
    News,
    Saved,
    Search,
    Settings,
    Detail,
    Web
}
data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(), Color.Transparent.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(), Color.Transparent.toArgb()
            )
        )
        setContent {
            NewsAppTheme {
                val items = listOf(
                    BottomNavigationItem(
                        title = "News",
                        selectedIcon = Icons.Filled.List,
                        unselectedIcon = Icons.Outlined.List,
                        hasNews = false,
                    ),
                    BottomNavigationItem(
                        title = "Search",
                        selectedIcon = Icons.Filled.Search,
                        unselectedIcon = Icons.Outlined.Search,
                        hasNews = false,
                        badgeCount = 45
                    ),
                    BottomNavigationItem(
                        title = "Saved",
                        selectedIcon = Icons.Filled.Star,
                        unselectedIcon = Icons.Outlined.Star,
                        hasNews = false,
                        badgeCount = 45
                    ),
                    BottomNavigationItem(
                        title = "Settings",
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings,
                        hasNews = true,
                    ),
                )
                var selectedItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                items.forEachIndexed{index, item ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = { selectedItemIndex = index
                                                 navController.navigate(route = item.title) },
                                        icon = { Icon(
                                            imageVector = if (index == selectedItemIndex) {
                                                item.selectedIcon
                                            } else item.unselectedIcon,
                                            contentDescription = item.title
                                        )
                                        },
                                        label = {
                                            Text(text = item.title)
                                        }
                                    )
                            }
                        }
                        }
                    ) {_->
                        val newsViewModel: NewsViewModel = viewModel(factory = NewsViewModel.Factory)
                        val state by newsViewModel.state.collectAsState(initial = NewsState())
                        lateinit var article: Article
                        NavHost(
                            navController = navController,
                            startDestination = Screens.News.name,
                            modifier = Modifier.padding(bottom = 80.dp)
                        ) {
                            composable(route = Screens.News.name){
                                MainScreen(isClicked = {
                                    article = it
                                    navController.navigate(route = Screens.Detail.name)
                                     })
                            }
                            composable(route = Screens.Search.name){
                                SearchScreen(isClicked = {
                                    article = it
                                    navController.navigate(route = Screens.Detail.name)
                                })
                            }
                            composable(route = Screens.Saved.name){
                                SavedScreen(state, isClicked = {
                                    article = it
                                    navController.navigate(route = Screens.Detail.name)
                                }, onSwiped = {
                                    article = it
                                    newsViewModel.deleteArticle(it)
                                })
                            }
                            composable(route = Screens.Settings.name){
                                Settings()
                            }
                            composable(route = Screens.Detail.name){
                                DetailScreen(article, addToDB = {newsViewModel.insertArticle(it)}, web={
                                    article=it
                                    navController.navigate(route = Screens.Web.name)
                                })
                            }
                            composable(route = Screens.Web.name){
                                WebView(addToDB = {newsViewModel.insertArticle(it)},
                                    article = article
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}