package com.example.contactapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.contactapp.DataBase.AppDatabase
import com.example.contactapp.DataBase.table.Contacts
import com.example.contactapp.SplaceActivity.Companion.edit
import com.example.contactapp.SplaceActivity.Companion.sp
import com.example.contactapp.ui.theme.ContactAppTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomePage : ComponentActivity() {

    private lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ContactAppTheme {

                Design()
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(DelicateCoroutinesApi::class)
    @Composable
    fun Design() {

        var result by remember { mutableStateOf<List<Contacts>>(emptyList()) }

        GlobalScope.launch {
            db = Room.databaseBuilder(
                applicationContext, AppDatabase::class.java, "database-name"
            ).build()
            result = db.contactDao().selectAllContacts()
        }
        Log.d("==========", "Design: $result")
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
                HeaderSection()

                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(result.size) { index ->
                        UserCard(
                            contact = result[index],
                            onContactDeleted = { deleteContact ->
                                result = result.filter { it != deleteContact }

                            }
                        )
                    }
                }
                FloatingButtonSection()
            }
        }
    }

    @Composable
    fun HeaderSection() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(R.drawable.logout),
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            edit.clear()
                            edit.apply()
                            loginScreen()
                        }
                )
                val name = sp.getString("username", "none") ?: "Not Found"
                Log.d("-----------", "Design: $name")

                Text(
                    text = name,
                    color = Color.Black,
                    fontSize = 30.sp,
                )

                Text(
                    text = ""
                )
            }
        }
    }

    private fun loginScreen() {
        val intent = Intent(applicationContext, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    @Composable
    fun FloatingButtonSection() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(applicationContext, ContactActivity::class.java)
                    intent.putExtra("activity", "homePage")
                    startActivity(intent)
                }, modifier = Modifier.size(70.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.aadd),
                    contentDescription = "",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Composable
    fun UserCard(contact: Contacts, onContactDeleted: (Contacts) -> Unit) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(applicationContext, ContactDetails::class.java)
                intent.putExtra("contact", contact)
                startActivity(intent)
            }
            .background(
                color = Color(0xFFBBDEFB), shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = CircleShape, color = Color.Gray, modifier = Modifier.size(50.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "${contact.firstName[0]}", color = Color.Black, fontSize = 20.sp
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)

                    .padding(16.dp), verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${contact.firstName} ${contact.surname}\n",
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Text(
                    text = contact.phone, fontSize = 20.sp, color = Color.Black
                )
            }
            Image(
                painter = painterResource(R.drawable.remove),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        GlobalScope.launch {
                            Toast.makeText(
                                applicationContext,
                                "${contact.firstName} This Contact Is Successfully Deleted!!",
                                Toast.LENGTH_SHORT
                            ).show()
                            db.contactDao().deleteContact()
                            onContactDeleted(contact)
                        }
                    }
            )
        }
    }
}
