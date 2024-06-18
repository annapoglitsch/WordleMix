package com.example.wordlemix.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wordlemix.appbars.AppBars

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GameScreen(navController: NavController, route: String) {
    val topAppBar = AppBars()
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                topAppBar.TopAppBar(titleText = "Game", icon = true, navController = navController)
            }
        ) {
        }
    }
}

@Composable
fun GameScreenStructure() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
            .background(Color.Gray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        }
    }
