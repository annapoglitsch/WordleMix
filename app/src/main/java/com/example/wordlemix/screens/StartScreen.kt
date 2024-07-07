package com.example.wordlemix.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wordlemix.PlayerPreferences
import com.example.wordlemix.data.Player
import com.example.wordlemix.data.PlayerDatabase
import com.example.wordlemix.data.PlayerRepository
import com.example.wordlemix.navigation.ScreenRoutes
import com.example.wordlemix.reusableItems.Headline
import com.example.wordlemix.reusableItems.button
import com.example.wordlemix.ui.theme.WordleMixTheme
import com.example.wordlemix.viewModel.SharedViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StartScreen(
    navController: NavController,
    route: String,
    sharedViewModel: SharedViewModel
) {
    val isDark = sharedViewModel.isDarkBool.collectAsState()
    WordleMixTheme(darkTheme = isDark.value) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {

            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) {
                StartScreenStructure(navController)
                initGuest(sharedViewModel)
            }
    }

    }
}

@Composable
fun StartScreenStructure(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Headline(headlineText = "WordleMix")
        Spacer(modifier = Modifier.height(32.dp))
        button(
            buttonText = "Start Game",
            onClick = { navController.navigate(ScreenRoutes.GameScreen.route) })
        Spacer(modifier = Modifier.height(20.dp))
        button(
            buttonText = "Settings",
            onClick = { navController.navigate(ScreenRoutes.SettingsScreen.route) })
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun initGuest(sharedViewModel: SharedViewModel) {
    val playerPreferences: PlayerPreferences = PlayerPreferences(LocalContext.current)
    //playerPreferences.saveUsername("")
    println(playerPreferences.getUsername())
    val coroutineScope = rememberCoroutineScope()

    val db = PlayerDatabase.getDatabase(LocalContext.current)
    val repository = PlayerRepository(playerDAO = db.playerDao())

    if (playerPreferences.getUsername() == null){
        playerPreferences.saveUsername("Guest")
        coroutineScope.launch {
            val existingPlayer: Player? = try {
                sharedViewModel.getByUsername("Guest")
            } catch (e: Exception) {
                null
            }

            if (existingPlayer == null) {
                sharedViewModel.addPlayer(Player(username = "Guest", record = 0))
            }
        }
    }
}



