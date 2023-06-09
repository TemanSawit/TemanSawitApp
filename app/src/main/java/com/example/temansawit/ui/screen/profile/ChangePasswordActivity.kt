package com.example.temansawit.ui.screen.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.temansawit.ui.navigation.Screen
import com.example.temansawit.ui.theme.TemanSawitTheme

class ChangePasswordActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemanSawitTheme {
              ChangePassword(navigateBack = { onBackPressed() })
            }
        }
    }
}