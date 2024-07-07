package com.example.wordlemix.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wordlemix.PlayerPreferences
import com.example.wordlemix.SharedViewModelFactory
import com.example.wordlemix.data.Player
import com.example.wordlemix.data.PlayerDAO
import com.example.wordlemix.data.PlayerDatabase
import com.example.wordlemix.data.PlayerRepository
import com.example.wordlemix.reusableItems.AppBars
import com.example.wordlemix.ui.theme.WordleMixTheme
import com.example.wordlemix.viewModel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

private const val PREFS_NAME = "sample_data_prefs"
private const val KEY_DATA_INSERTED = "data_inserted"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScoreBoard(
    navController: NavController,
    route: String,
    sharedViewModel: SharedViewModel
) {

    val db = PlayerDatabase.getDatabase(LocalContext.current)
    val repository = PlayerRepository(playerDAO = db.playerDao())
    val factory = SharedViewModelFactory(repository = repository)
    val sharedViewModel : SharedViewModel = viewModel(factory = factory)
    val coroutineScope = rememberCoroutineScope()

    insertSampleData(coroutineScope,db.playerDao())

    val context = LocalContext.current

    var playerPreferences: PlayerPreferences
    playerPreferences = PlayerPreferences(context)

    println("Hello1")
    println(playerPreferences.getUsername())
    println("Hello2")

    val topAppBar = AppBars()
    val isDark = sharedViewModel.isDarkBool.collectAsState()
    WordleMixTheme(darkTheme = isDark.value) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    topAppBar.TopAppBar(
                        titleText = "Scoreboard",
                        icon = true,
                        navController = navController
                    )
                }
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color(0xFFAAD6F3))
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                }
                val userList by sharedViewModel.players.collectAsState()

                LazyColumn {
                    userList.forEach { user ->
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(text = user.username, modifier = Modifier.weight(1f))
                                Text(text = user.record.toString(), modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

fun insertSampleData(coroutineScope: CoroutineScope, playerDAO: PlayerDAO) {
    coroutineScope.launch {
        val players = playerDAO.getAll().firstOrNull()
        if (players.isNullOrEmpty()) {
            playerDAO.add(Player(username = "Anna", record = 3000))
            playerDAO.add(Player(username = "Stefan", record = 2000))
            playerDAO.add(Player(username = "Manu", record = 1000))
        }
    }
}