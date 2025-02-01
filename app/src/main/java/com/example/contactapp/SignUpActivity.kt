package com.example.contactapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.*
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
import com.example.contactapp.DataBase.table.User
import com.example.contactapp.SplaceActivity.Companion.edit
import com.example.contactapp.SplaceActivity.Companion.sp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Suppress("DEPRECATION")
class SignUpActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
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
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Design() {
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var number by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

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
                        text = "Signup",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Row {
                        TextButton(onClick = {
                            val intent = Intent(applicationContext, SignInActivity::class.java)
                            startActivity(intent)
                        }) {
                            Text(
                                text = "Already have an account?",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "  Login",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Yellow
                            )
                        }
                    }

                    OutlinedTextField(value = name,
                        onValueChange = { name = it },
                        label = { Text("Name", color = Color.Gray) },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.profile),
                                contentDescription = "Profile Icon",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Black
                            )
                        },
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

                    OutlinedTextField(value = number,
                        onValueChange = { number = it },
                        label = { Text("Mobile Number", color = Color.Gray) },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.phone),
                                contentDescription = "Phone Icon",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Black
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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

                    OutlinedTextField(value = email,
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

                    OutlinedTextField(value = password,
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

                    OutlinedTextField(value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password", color = Color.Gray) },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.pass),
                                contentDescription = "Confirm Password Icon",
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

                    ElevatedButton(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                        border = BorderStroke(3.dp, Color(0xFF00B8D4)),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Transparent, containerColor = Color.Transparent
                        ),
                        onClick = {
                            when {
                                name.isEmpty() || email.isEmpty() || number.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
                                    Toast.makeText(
                                        applicationContext,
                                        "All fields are required!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                !email.contains("@") || !email.contains(".") -> {
                                    Toast.makeText(
                                        applicationContext,
                                        "Enter a valid email!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                number.length != 10 -> {
                                    Toast.makeText(
                                        applicationContext,
                                        "Enter a valid mobile number!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                password != confirmPassword -> {
                                    Toast.makeText(
                                        applicationContext,
                                        "Passwords do not match!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                else -> {

                                    GlobalScope.launch {
                                        val db = Room.databaseBuilder(
                                            applicationContext,
                                            AppDatabase::class.java,
                                            "database-name"
                                        ).fallbackToDestructiveMigration().build()

                                        db.userDao().insert(
                                            User(
                                                firstName = name,
                                                email = email,
                                                mobileNumber = number,
                                                password = password
                                            )
                                        )
                                        val intent =
                                            Intent(applicationContext, SignInActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }


                                    Toast.makeText(
                                        applicationContext,
                                        "You are registered successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }
                        }) {
                        Text(
                            text = "REGISTER NOW",
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
