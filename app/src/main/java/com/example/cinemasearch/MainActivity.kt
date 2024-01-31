package com.example.cinemasearch

import android.annotation.SuppressLint
import android.graphics.fonts.FontStyle
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.cinemasearch.network.ShortInfoFilm
import com.example.cinemasearch.ui.theme.CinemaSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CinemaSearchTheme {
                NavHost(
                    navController = navController,
                    startDestination = "MainContent"
                ) {
                    composable("MainContent") {
                        MainContent(navController = navController)
                    }
                    composable("Demo") {
                        Demo(navController)
                    }
                    composable("DbList") {
                        DbList(navController)
                    }
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun Demo(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    val product =
        navController.previousBackStackEntry?.savedStateHandle?.get<ShortInfoFilm>("Product")
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.back),
        contentDescription = "sd",
        contentScale = ContentScale.FillBounds
    )
    Column(
        modifier = Modifier
            .padding(10.dp, 0.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (product != null) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(Color(0xFF10141F))
            ) {
                product.nameEn?.let { Text(it, fontSize = 20.sp, color = Color.White) }
                AsyncImage(
                    model = product.posterUrl,
                    contentDescription = "sd",
                    alignment = Alignment.Center
                )
                Text("Рейтинг: " + product.rating, color = Color.White)
                Text("Описание: " + product.description, color = Color.White)
                Text("Длина: " + product.filmLength, color = Color.White)
            }
        }
        Box(contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    viewModel.insertProduct(product)
                }) {
                    Text(text = "Insert into favourites")
                }
                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text(text = "Back")
                }
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DbList(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    viewModel.getAllProducts()
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.back),
        contentDescription = "sd",
        contentScale = ContentScale.FillBounds
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Избранное", color = Color.White, fontSize = 20.sp)
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            Log.d("TAG", "MainContent: Your Token")
            viewModel.dbList.value.let { it ->
                items(it) {
                    MainContentItem(
                        ShortInfoFilm(
                            it.id, it.nameEn, it.rating, it.description, it.filmLength, it.posterUrl
                        ), navController
                    )
                }
            }

        }
        FloatingActionButton(onClick = { /*TODO*/ }) {

        }
    }
}
