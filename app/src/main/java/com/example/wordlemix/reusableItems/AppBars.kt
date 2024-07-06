package com.example.wordlemix.reusableItems

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wordlemix.R

class AppBars {
    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun TopAppBar(titleText: String, icon: Boolean, navController: NavController) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = { Text(text = titleText) },
            navigationIcon = {
                if (icon) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Navigation Icon")
                    }
                }
            }
        )
    }

    @Composable
    fun BottomAppBar(navController: NavController) {
        val currentEntry = navController.currentBackStackEntryAsState().value
        val currentDestination = currentEntry?.destination
        androidx.compose.material3.BottomAppBar() {
            NavigationBarItem(
                label = { Text(text = "Home") },
                selected = currentDestination?.route == "homeScreen",
                onClick = { navController.navigate("homeScreen") },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_home_24),
                        contentDescription = null
                    )
                })
        }
    }
}