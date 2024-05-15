package com.example.wordlemix.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.wordlemix.navigation.ScreenRoutes
import com.example.wordlemix.reusableItems.Headline
import com.example.wordlemix.reusableItems.button

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StartScreen(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
        StartScreenStructure(navController)
        }
    }
}

@Composable
fun StartScreenStructure(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Gray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Headline(headlineText = "WordleMix")
        button(buttonText = "Start Game", onClick = { navController.navigate(ScreenRoutes.GameScreen.route) })
        button(buttonText = "Settings")
    }
}
