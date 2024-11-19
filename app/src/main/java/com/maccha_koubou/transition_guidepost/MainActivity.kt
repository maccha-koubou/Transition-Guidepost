package com.maccha_koubou.transition_guidepost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.maccha_koubou.transition_guidepost.view.MainPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showAddDataCard by remember { mutableStateOf(false) }
            var showPopUp by remember { mutableStateOf(false) }
            MainPage()
        }
    }
}