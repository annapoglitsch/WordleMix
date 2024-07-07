package com.example.wordlemix

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.pm.ShortcutInfoCompat.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordlemix.data.Player
import com.example.wordlemix.data.PlayerDatabase
import com.example.wordlemix.data.PlayerRepository
import com.example.wordlemix.navigation.Navigation
import com.example.wordlemix.ui.theme.WordleMixTheme
import com.example.wordlemix.viewModel.SharedViewModel

import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background)
                {
                    val db = PlayerDatabase.getDatabase(LocalContext.current)
                    val repository = PlayerRepository(playerDAO = db.playerDao())
                    val coroutineScope = rememberCoroutineScope()

                    coroutineScope.launch {
                        val players = repository.getAllPlayers().firstOrNull()
                        if (players.isNullOrEmpty()) {
                            repository.addPlayer(Player(username = "Stefan", record = 2000))
                            repository.addPlayer(Player(username = "Anna", record = 3000))
                            repository.addPlayer(Player(username = "Manu", record = 1000))
                            repository.addPlayer(Player(username = "John", record = 2500))
                            repository.addPlayer(Player(username = "Kate", record = 3500))
                            repository.addPlayer(Player(username = "Mike", record = 1500))
                            repository.addPlayer(Player(username = "Laura", record = 1800))
                            repository.addPlayer(Player(username = "James", record = 2200))
                            repository.addPlayer(Player(username = "Sophie", record = 2700))
                            repository.addPlayer(Player(username = "Tom", record = 1400))
                        }
                    }
                    Navigation(coroutineScope)
                }
        }
    }
}
