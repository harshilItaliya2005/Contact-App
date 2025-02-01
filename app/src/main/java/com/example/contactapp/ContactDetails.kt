package com.example.contactapp

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contactapp.DataBase.table.Contacts
import com.example.contactapp.ui.theme.ContactAppTheme

@Suppress("DEPRECATION")
class ContactDetails : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactAppTheme {
                val details = intent.getSerializableExtra("contact",Contacts::class.java)
                Log.d("====", "Contact: $details")

                    Design(details!!)

            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Design(details: Contacts) {
        val fName = details.firstName
        val number = details.phone
        val city = details.city
        var company = details.company
        var dob = details.dob

        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF64B5F6), Color(0xFF1976D2))
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(15.dp))
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = fName, fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.like),
                            contentDescription = "Like",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                        Icon(
                            painter = painterResource(R.drawable.edit),
                            contentDescription = "Edit",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp).clickable {
                                val intent = Intent(applicationContext, ContactActivity::class.java)
                                intent.putExtra("details", details)
                                startActivity(intent)
                            }
                        )
                    }
                }

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    shape = RoundedCornerShape(15.dp),
                    color = Color.White,
                    shadowElevation = 5.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Top,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = number, fontSize = 20.sp, color = Color.Black)
                                Text(text = city, fontSize = 14.sp, color = Color.Gray)
                            }
                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.msg),
                                    contentDescription = "Msg",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clickable {
                                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                                data = Uri.parse("sms:+91$number")
                                            }
                                            startActivity(intent)
                                        }
                                )
                                Spacer(modifier = Modifier.width(15.dp))
                                Image(
                                    painter = painterResource(R.drawable.call),
                                    contentDescription = "Call",
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clickable {
                                            val intent = Intent(
                                                Intent.ACTION_DIAL,
                                                Uri.parse("tel:+91$number")
                                            )
                                            startActivity(intent)
                                        }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(40.dp))

                        ContactOption(
                            title = "Meet",
                            iconRes = R.drawable.meet,
                            url = "https://meet.google.com/",
                            packageName = "com.google.android.apps.meetings"
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        ContactOption(
                            title = "WhatsApp",
                            iconRes = R.drawable.whatsapp,
                            url = "https://api.whatsapp.com/send?phone=+91$number",
                            packageName = "com.whatsapp"
                        )

                        Spacer(modifier = Modifier.height(30.dp))


                        OutlinedTextField(
                            value = company,
                            onValueChange = { cName -> company = cName },
                            readOnly = true,
                            label = { Text("Company",color = Color.Black) },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.company),
                                    contentDescription = "",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Black
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color.Black,
                                unfocusedLabelColor = Color.Gray,
                                containerColor = Color.Transparent
                            )
                        )
                        Spacer(modifier = Modifier.heightIn(10.dp))
                        OutlinedTextField(
                            value = dob,
                            onValueChange = { d -> dob = d },
                            readOnly = true,
                            label = { Text("DOB",color = Color.Black) },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.dob),
                                    contentDescription = "",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Black
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Gray,
                                focusedLabelColor = Color.Black,
                                unfocusedLabelColor = Color.Gray,
                                containerColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ContactOption(title: String, iconRes: Int, url: String, packageName: String) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 20.sp, color = Color.Black)
            Image(
                painter = painterResource(iconRes),
                contentDescription = title,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(url)
                            setPackage(packageName)
                        }
                        try {
                            startActivity(intent)
                        } catch (e: Exception) {
                            Log.d("=====", "Error: $e")
                            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(webIntent)
                        }
                    }
            )
        }
    }
}
