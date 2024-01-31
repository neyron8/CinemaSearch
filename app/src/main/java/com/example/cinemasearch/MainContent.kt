package com.example.cinemasearch

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cinemasearch.network.ShortInfoFilm

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
) {
    val query: MutableState<String> = remember { mutableStateOf("") }
    val result = viewModel.list1.value

    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            AddTweetButton(navController)
        }) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.back),
            contentDescription = "sd",
            contentScale = ContentScale.FillBounds
        )
        Column(modifier = Modifier.padding(8.dp)) {
            OutlinedTextField(value = query.value, onValueChange = {
                query.value = it
            },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { viewModel.getCinemaByName(query.value) }
                ),
                enabled = true,
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                label = { Text(text = "Search here...") },
                modifier = Modifier.fillMaxWidth()
            )

            if (result.isLoading) {
                Log.d("TAG", "MainContent: in the loading")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            if (result.error.isNotBlank()) {
                Log.d("TAG", "MainContent: ${result.error}")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = viewModel.list.value.error
                    )
                }
            }

            if (result.data.isNotEmpty()) {
                contentGrid(viewModel, navController)
            }

        }
    }
}

@Composable
private fun contentGrid(
    viewModel: MainViewModel,
    navController: NavController
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        Log.d("TAG", "MainContent: Your Token")
        viewModel.list1.value.data.let { it ->
            items(it) {
                MainContentItem(
                    ShortInfoFilm(
                        it.filmId,
                        it.nameRu ?: it.nameEn,
                        it.rating,
                        it.description,
                        it.filmLength,
                        it.posterUrl
                    ), navController
                )
            }
        }
    }
}

@Composable
fun CircularProgressIndicator(modifier: Modifier) {

}

@Composable
fun MainContentItem(hit: ShortInfoFilm, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clickable(
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "Product",
                        value = hit
                    )
                    navController.navigate("Demo")
                }
            )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            hit.nameEn?.let { Text(it, textAlign = TextAlign.Center) }
            AsyncImage(
                model = hit.posterUrl, contentDescription = "sd", contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}


@Composable
fun AddTweetButton(navController: NavController) {
    FloatingActionButton(modifier = Modifier.size(50.dp), onClick = {
        navController.navigate("DbList")
    }) {
        Image(
            modifier = Modifier.background(Color(0xFFF4FF81)),
            painter = painterResource(id = R.drawable.ic_favourite_foreground),
            contentDescription = "fab",
            contentScale = ContentScale.FillBounds
        )
    }
}
