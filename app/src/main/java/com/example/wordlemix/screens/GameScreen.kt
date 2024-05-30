package com.example.wordlemix.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.wordlemix.game.GameLogic

class GameScreen {
    val GameLlogic = GameLogic()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun StartScreen() {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {

            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) {
                GameLlogic.Game()
            }
        }
    }
}