package com.example.contactapp.DataBase

import androidx.room.*
import com.example.contactapp.DataBase.table.User


@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password LIMIT 1")
    fun getUserByCredentials(email: String, password: String): User?

    @Query("SELECT * FROM user WHERE uid = :id LIMIT 1")
    fun getUserById(id: Int): User?

    @Query("SELECT * FROM user")
    fun selectAllUsers(): List<User>

    @Query("UPDATE user SET password = :newPassword WHERE email = :email")
    fun updatePassword(email: String, newPassword: String)

}
