package com.example.temansawit.ui.screen.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.temansawit.ui.theme.TemanSawitTheme

class EditProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemanSawitTheme {
                EditProfile(navigateBack = { onBackPressed() })
            }
        }
    }
}