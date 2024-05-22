package com.example.wordlemix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.wordlemix.navigation.Navigation
import com.example.wordlemix.ui.theme.WordleMixTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordleMixTheme {
                Navigation()
            }
        }
    }
}
