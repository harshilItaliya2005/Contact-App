package com.example.contactapp.DataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.contactapp.DataBase.table.Contacts

@Dao
interface ContactDao {

    @Insert
    fun insert(contact: Contacts)

    @Query("UPDATE Contacts SET first_name = :firstName, surname = :surname, phone = :phone, city = :city, company = :company, dob = :dob WHERE id = :id")
    fun updateContact(
        id: Int,
        firstName: String,
        surname: String,
        phone: String,
        city: String,
        company: String,
        dob: String
    )

    @Query("DELETE FROM contacts")
    fun deleteContact()

    @Query("SELECT * FROM contacts")
    fun selectAllContacts(): List<Contacts>
}

