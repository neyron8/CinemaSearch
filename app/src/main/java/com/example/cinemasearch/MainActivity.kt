package com.example.cinemasearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                        FilmInfoScreen(navController)
                    }
                    composable("DbList") {
                        DbFilmsList(navController)
                    }
                }
            }
        }
    }
}
