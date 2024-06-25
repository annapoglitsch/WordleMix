package com.example.wordlemix.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wordlemix.screens.GameScreen
import com.example.wordlemix.screens.SettingsScreen
import com.example.wordlemix.screens.StartScreen
import com.example.wordlemix.viewModel.SharedViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val sharedViewModel : SharedViewModel = viewModel()

    NavHost(navController = navController, startDestination = ScreenRoutes.StartScreen.route){
        composable(route = ScreenRoutes.StartScreen.route){
            StartScreen(navController, ScreenRoutes.StartScreen.route)
        }
        composable(route = ScreenRoutes.GameScreen.route){
            GameScreen(navController, ScreenRoutes.GameScreen.route)
        }
        composable(route = ScreenRoutes.SettingsScreen.route){
            SettingsScreen(navController, ScreenRoutes.SettingsScreen.route, sharedViewModel)
        }
    }
}