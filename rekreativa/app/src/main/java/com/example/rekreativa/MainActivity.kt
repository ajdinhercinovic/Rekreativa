package com.example.rekreativa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.rekreativa.ui.theme.RekreativaTheme
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseFirestore.getInstance()

        enableEdgeToEdge()
        setContent {
            RekreativaTheme {
                AppNav()
            }
        }
    }
}
