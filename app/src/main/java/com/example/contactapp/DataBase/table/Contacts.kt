package com.example.contactapp.DataBase.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "contacts")
data class Contacts(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "first_name") val firstName: String,
        @ColumnInfo(name = "surname") val surname: String,
        @ColumnInfo(name = "phone") val phone: String,
        @ColumnInfo(name = "city") val city: String,
        @ColumnInfo(name = "company") val company: String,
        @ColumnInfo(name = "dob") val dob: String
) : Serializable

