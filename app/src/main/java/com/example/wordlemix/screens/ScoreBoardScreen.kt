package com.example.wordlemix.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wordlemix.PlayerPreferences
import com.example.wordlemix.data.PlayerDatabase
import com.example.wordlemix.reusableItems.AppBars
import com.example.wordlemix.ui.theme.WordleMixTheme
import com.example.wordlemix.viewModel.SharedViewModel

private const val PREFS_NAME = "sample_data_prefs"
private const val KEY_DATA_INSERTED = "data_inserted"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun ScoreBoard(
    navController: NavController,
    route: String,
    sharedViewModel: SharedViewModel
) {

    val context = LocalContext.current



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
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                }
                val userList by sharedViewModel.players.collectAsState()
                val sortedUserList = userList.sortedByDescending { it.record }

                LazyColumn(
                    modifier = Modifier.padding(top = 40.dp)
                ) {
                    sortedUserList.forEach { user ->
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