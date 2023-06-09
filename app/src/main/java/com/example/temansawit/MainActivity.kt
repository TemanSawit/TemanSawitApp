package com.example.temansawit

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.temansawit.ui.screen.splash.SplashViewModel
import com.example.temansawit.ui.theme.TemanSawitTheme

class MainActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

        splashScreen.setKeepOnScreenCondition{viewModel.isLoading.value}

        setContent {
            TemanSawitTheme {
                // A surface container using the 'background' color from the theme
                TemanSawitApp()
            }
        }
    }
  
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TemanSawitTheme {
        TemanSawitApp()
    }
}
