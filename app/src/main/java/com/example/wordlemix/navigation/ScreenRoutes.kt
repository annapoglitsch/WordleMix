package com.example.wordlemix.navigation

sealed class ScreenRoutes(val route: String) {
    data object StartScreen : ScreenRoutes("startScreen")
    data object GameScreen : ScreenRoutes("gameScreen")
    data object SettingsScreen : ScreenRoutes("settingsScreen")
}