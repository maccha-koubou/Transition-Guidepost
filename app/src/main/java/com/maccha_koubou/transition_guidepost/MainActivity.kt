package com.maccha_koubou.transition_guidepost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.maccha_koubou.transition_guidepost.view.MainPage
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showAddDataCard by remember { mutableStateOf(false) }
            var showPopUp by remember { mutableStateOf(false) }

            // Set the language environment of this app
            val context = LocalContext.current
            val configuration = context.resources.configuration
            configuration.setLocale(Locale.SIMPLIFIED_CHINESE)
            context.createConfigurationContext(configuration)

            MainPage()
        }
    }
}