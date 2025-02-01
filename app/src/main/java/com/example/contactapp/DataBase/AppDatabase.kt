package com.example.contactapp.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactapp.DataBase.table.Contacts
import com.example.contactapp.DataBase.table.User

@Database(entities = [User::class, Contacts::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun contactDao(): ContactDao

}
