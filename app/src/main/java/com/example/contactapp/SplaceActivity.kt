package com.example.contactapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.contactapp.ui.theme.ContactAppTheme


class SplaceActivity : ComponentActivity() {
    companion object {
        lateinit var sp: SharedPreferences
        lateinit var edit: SharedPreferences.Editor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactAppTheme {
                sp = getSharedPreferences("login", MODE_PRIVATE)
                edit = sp.edit()

                val isLoggedIn = sp.getBoolean("user", false)

                val intent = Intent(
                    applicationContext,
                    if (isLoggedIn) HomePage::class.java else SignInActivity::class.java
                )
                startActivity(intent)
                finish()


            }
        }
    }
}

