package com.example.contactapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.contactapp.ui.theme.ContactAppTheme
import com.example.contactapp.DataBase.AppDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.contactapp.SplaceActivity.Companion.edit
import com.example.contactapp.SplaceActivity.Companion.sp

@Suppress("DEPRECATION")
class SignInActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ContactAppTheme {
                Design()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
    @Composable
    fun Design() {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFBBDEFB), Color(0xFF64B5F6))
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Login",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Row {
                        TextButton(onClick = {
                            val intent = Intent(applicationContext, SignUpActivity::class.java)
                            startActivity(intent)
                        }) {
                            Text(
                                text = "Don't have an account?",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "  Sign Up",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Yellow
                            )
                        }
                    }

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Address", color = Color.Gray) },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.email),
                                contentDescription = "Email Icon",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Black
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.Gray,
                            containerColor = Color.Transparent
                        )
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password", color = Color.Gray) },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.pass),
                                contentDescription = "Password Icon",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Black
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.Gray,
                            containerColor = Color.Transparent
                        )
                    )

                    TextButton(
                        onClick = { },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "Forgot Password?",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Yellow
                        )
                    }

                    ElevatedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        border = BorderStroke(3.dp, Color(0xFF00B8D4)),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Transparent,
                            containerColor = Color.Transparent
                        ),
                        onClick = {


                            if (email.isNotEmpty() && password.isNotEmpty()) {

                                GlobalScope.launch {
                                    val db = Room.databaseBuilder(
                                        applicationContext,
                                        AppDatabase::class.java,
                                        "database-name"
                                    ).build()

                                    val user = db.userDao().getUserByCredentials(email, password)
                                    edit.putString("username", user!!.firstName)
                                    edit.apply()
                                    Log.d(
                                        "-------------------",
                                        "Design: ${sp.getString("username", "Not Found")}"
                                    )
                                    edit.putBoolean("user", true)
                                    edit.apply()

                                    val intent = Intent(applicationContext, HomePage::class.java)
                                    startActivity(intent)
                                    finish()

                                }
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Please enter both email and password.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    ) {
                        Text(
                            text = "LOGIN",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
