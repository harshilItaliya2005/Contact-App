package com.example.contactapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.contactapp.DataBase.table.Contacts
import com.example.contactapp.ui.theme.ContactAppTheme
import com.example.contactapp.DataBase.AppDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class ContactActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactAppTheme {
                val getInfo = intent.getSerializableExtra("details", Contacts::class.java)
                ContactScreen(getInfo)

            }
        }
    }

    @OptIn(
        ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class, DelicateCoroutinesApi::class
    )
    @Composable
    fun ContactScreen(getInfo: Contacts?) {
        val firstName = remember { mutableStateOf(getInfo?.firstName ?: "") }
        val suraName = remember { mutableStateOf(getInfo?.surname ?: "") }
        val phone = remember { mutableStateOf(getInfo?.phone ?: "") }
        val city = remember { mutableStateOf(getInfo?.city ?: "") }
        val company = remember { mutableStateOf(getInfo?.company ?: "") }
        val dob = remember { mutableStateOf(getInfo?.dob ?: "") }
        val title = remember { mutableStateOf("") }
        val activityStatus = intent.getStringExtra("activity")


        if (activityStatus == "homePage") {
            title.value = "Add Contact"
        } else {
            title.value = "Update Contact"

        }

        val db = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "database-name"
        ).build()

        var contactsList by remember { mutableStateOf<List<Contacts>>(emptyList()) }

        fun loadContacts() {
            GlobalScope.launch {
                contactsList = db.contactDao().selectAllContacts()
            }
        }

        LaunchedEffect(Unit) {
            loadContacts()
        }

        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
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
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title.value,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    OutlinedTextField(
                        value = firstName.value,
                        onValueChange = { firstName.value = it },
                        textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                        label = { Text("First Name") },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.Gray,
                            containerColor = Color.Transparent
                        )
                    )

                    OutlinedTextField(
                        value = suraName.value,
                        onValueChange = { suraName.value = it },
                        textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                        label = { Text("Surname") },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.Gray,
                            containerColor = Color.Transparent
                        )
                    )

                    OutlinedTextField(
                        value = phone.value,
                        onValueChange = { phone.value = it },
                        textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                        label = { Text("Phone Number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.Gray,
                            containerColor = Color.Transparent
                        )
                    )
                    OutlinedTextField(
                        value = city.value,
                        onValueChange = { city.value = it },
                        textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                        label = { Text("City") },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.Gray,
                            containerColor = Color.Transparent
                        )
                    )

                    OutlinedTextField(
                        value = dob.value,
                        onValueChange = { dob.value = it },
                        textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                        label = { Text("Date of Birth") },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.Gray,
                            containerColor = Color.Transparent
                        )
                    )


                    OutlinedTextField(
                        value = company.value,
                        onValueChange = { company.value = it },
                        textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                        label = { Text("Company") },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.Gray,
                            containerColor = Color.Transparent
                        )
                    )

                    ElevatedButton(
                        onClick = {

                            if (firstName.value.isNotBlank() && suraName.value.isNotBlank() && phone.value.isNotBlank() && city.value.isNotBlank() && company.value.isNotBlank() && dob.value.isNotBlank()) {
                                val contact = Contacts(
                                    firstName = firstName.value,
                                    surname = suraName.value,
                                    phone = phone.value,
                                    city = city.value,
                                    company = company.value,
                                    dob = dob.value
                                )


                                try {
                                    GlobalScope.launch {

                                        if (activityStatus == "homePage") {
                                            db.contactDao().insert(contact)
                                        } else {
                                            Log.d("---------=====", "ContactScreen: $getInfo")
                                            db.contactDao().updateContact(
                                                id = getInfo?.id ?: 0,
                                                dob = contact.dob,
                                                city = contact.city,
                                                phone = contact.phone,
                                                surname = contact.surname,
                                                firstName = contact.firstName,
                                                company = contact.company
                                            )

                                        }

                                    }
                                } catch (e: Exception) {
                                    Log.d("My", "Error : $e")
                                }

                                val intent = Intent(applicationContext, HomePage::class.java)
                                startActivity(intent)
                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        border = BorderStroke(3.dp, Color(0xFF00B8D4)),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Transparent, containerColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = "Submit",
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
