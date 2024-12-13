package com.example.audiovideocall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.audiovideocall.ui.theme.AudioVideoCallTheme

class ChatBot : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val chatBotViewModel = ViewModelProvider(this)[ChatBotViewModel::class.java]
        setContent {
            AudioVideoCallTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChatPage(modifier = Modifier.padding(innerPadding), chatBotViewModel)
                }
            }
        }
    }
}

