package com.example.wordlemix.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wordlemix.screens.GameScreen
import com.example.wordlemix.screens.SettingsScreen
import com.example.wordlemix.screens.StartScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    var darkTheme by remember {
        mutableStateOf(false)
    }
    NavHost(navController = navController, startDestination = ScreenRoutes.StartScreen.route){
        composable(route = ScreenRoutes.StartScreen.route){
            StartScreen(navController, ScreenRoutes.StartScreen.route, darkTheme = darkTheme, onThemeUpdated = {darkTheme = !darkTheme})
        }
        composable(route = ScreenRoutes.GameScreen.route){
            GameScreen(navController, ScreenRoutes.GameScreen.route)
        }
        composable(route = ScreenRoutes.SettingsScreen.route){
            SettingsScreen(navController, ScreenRoutes.SettingsScreen.route, darkTheme = darkTheme, onThemeUpdated = {darkTheme = !darkTheme})
        }
    }
}