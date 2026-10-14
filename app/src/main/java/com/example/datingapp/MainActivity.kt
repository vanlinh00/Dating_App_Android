package com.example.datingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.datingapp.ui.screens.dating.DatingScreen
import com.example.ui.theme.DatingAppTheme

/**
 * Main entry point of the Dating Application.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DatingAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    DatingScreen()
                }
            }
        }
    }
}
