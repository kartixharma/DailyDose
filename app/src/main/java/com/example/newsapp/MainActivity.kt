@file:OptIn(ExperimentalFoundationApi::class)

package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.AllNewsScreens.DetailScreen
import com.example.newsapp.AllNewsScreens.MainScreen
import com.example.newsapp.AllNewsScreens.SavedScreen
import com.example.newsapp.AllNewsScreens.SearchScreen
import com.example.newsapp.AllNewsScreens.WebView
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
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val Context = LocalContext.current
            NewsAppTheme {
                val newsViewModel: NewsViewModel = viewModel(factory = NewsViewModel.Factory)
                val state by newsViewModel.state.collectAsState(initial = NewsState())
                val items = listOf(
                    BottomNavigationItem(
                        title = "News",
                        selectedIcon = Icons.Filled.Newspaper,
                        unselectedIcon = Icons.Outlined.Newspaper,
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
                        unselectedIcon = Icons.Filled.StarOutline,
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
                    ) {innerpadding->
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, state.article.url)
                        }
                        NavHost(
                            navController = navController,
                            startDestination = Screens.News.name,
                            modifier = Modifier.padding(innerpadding)
                        ) {
                            composable(route = Screens.News.name){
                                MainScreen(newsViewModel,
                                        isClicked = {
                                    newsViewModel.setArticle(it)
                                    navController.navigate(route = Screens.Detail.name)
                                     })
                            }
                            composable(route = Screens.Search.name){
                                SearchScreen(isClicked = {
                                    newsViewModel.setArticle(it)
                                    navController.navigate(route = Screens.Detail.name)
                                })
                            }
                            composable(route = Screens.Saved.name){
                                SavedScreen(state, isClicked = {
                                    newsViewModel.setArticle(it)
                                    navController.navigate(route = Screens.Detail.name)
                                }, onSwiped = {
                                    newsViewModel.setArticle(it)
                                    newsViewModel.deleteArticle(it)
                                })
                            }
                            composable(route = Screens.Settings.name){
                                Settings(selectedCountry = newsViewModel.country, onCountrySelected = { cn, cd->
                                    newsViewModel.country=cn
                                    newsViewModel.cnCode=cd
                                })
                            }
                            composable(route = Screens.Detail.name){

                                DetailScreen(state.article, addToDB = {newsViewModel.insertArticle(it)}, web={
                                    navController.navigate(route = Screens.Web.name)
                                },
                                    intent = {
                                        ContextCompat.startActivity(Context, intent, null) })
                            }
                            composable(route = Screens.Web.name){
                                WebView(addToDB = {newsViewModel.insertArticle(it)},
                                    article = state.article
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}