package com.example.temansawit.ui.screen.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.temansawit.ui.screen.camera.HasilScreen
import com.example.temansawit.ui.theme.TemanSawitTheme

class ContactUsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemanSawitTheme {
                ContactUs(navigateBack = { onBackPressed() })
            }
        }
    }
}