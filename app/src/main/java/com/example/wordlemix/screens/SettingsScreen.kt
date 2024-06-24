@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.example.wordlemix.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wordlemix.R
import com.example.wordlemix.reusableItems.AppBars


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navController: NavController,
    route: String,
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit
) {

    val topAppBar = AppBars()
    var isDarkTheme by remember { mutableStateOf(false) }
    ThemeSwitch(darkTheme = isDarkTheme) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color(0xFFAAD6F3)
        ) {

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    topAppBar.TopAppBar(
                        titleText = "Settings",
                        icon = true,
                        navController = navController
                    )
                }
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    /*  ThemeSwitcherToggle(
                          darkTheme = darkTheme,
                          onClick = onThemeUpdated
                      )*/
                    ThemeSwitchToggle(
                        darkTheme = isDarkTheme,
                        onClick = { isDarkTheme = !isDarkTheme })
                }
            }
        }

    }

}
@Composable
fun ThemeSwitch(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    val color = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFFAAD6F3),
            primaryContainer = Color.Black,
            secondary = Color.Gray,
        )
    } else {
        lightColorScheme(
            primary = Color.Black,
            primaryContainer = Color(0xFFAAD6F3),
            secondary = Color.Gray

            )
    }
    MaterialTheme(
        colorScheme = color,
        typography = Typography(),
        content = content
    )
}



//ToDo: default background should be Color(0xFFAAD6F3) (lightmode)

//followed this tutorial: https://www.youtube.com/watch?v=Nvphdmi-6qc
@Composable
fun ThemeSwitchToggle(
    darkTheme: Boolean = false,
    size: Dp = 100.dp,
    iconSize: Dp = size / 3,
    padding: Dp = 10.dp,
    borderWidth: Dp = 1.dp,
    parentShape: RoundedCornerShape = CircleShape,
    toggleShape: RoundedCornerShape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    onClick: () -> Unit
) {
    val offset by animateDpAsState(
        targetValue = if (darkTheme) 0.dp else size,
        animationSpec = animationSpec
    )

    Box(modifier = Modifier
        .width(size * 2)
        .height(size)
        .clip(shape = parentShape)
        .clickable {
            onClick()
        }
        .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = offset)
                .padding(all = padding)
                .clip(shape = toggleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {}
        Row(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = borderWidth,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    shape = parentShape
                )
        ) {
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_dark_mode_24),
                    contentDescription = "Dark Mode",
                    tint = if (darkTheme) MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.primary
                )
            }
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_light_mode_24),
                    contentDescription = "Light Mode",
                    tint = if (darkTheme) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }
    }
}



