package com.example.vibratebreath.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vibratebreath.room.entities.User

@Dao
interface DaoUser {
    @Query("SELECT * FROM User")
    fun findAll(): List<User>

    @Query("SELECT * FROM User WHERE email=:email")
    fun findUserByEmail(email: String) : User

    @Query("SELECT * FROM User WHERE email=:email AND password=:password")
    fun login(email: String, password: String): User

    @Insert
    fun save(user: User) : Long

    @Query("UPDATE User SET name=:name, email=:email, dateOfBirth=:dateOfBirth, password=:password WHERE id=:id")
    fun update(name:String, email: String, dateOfBirth:String, password:String, id:Long): Int

    @Query("DELETE FROM User WHERE id=:id")
    fun delete(id : Long)

}