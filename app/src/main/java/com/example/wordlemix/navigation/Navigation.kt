package com.example.wordlemix.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wordlemix.screens.GameScreen
import com.example.wordlemix.screens.StartScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenRoutes.StartScreen.route){
        composable(route = ScreenRoutes.StartScreen.route){
            StartScreen(navController)
        }
        composable(route = ScreenRoutes.GameScreen.route){
            GameScreen(navController)
        }
    }
}