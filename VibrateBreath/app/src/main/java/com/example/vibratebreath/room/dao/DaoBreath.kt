package com.example.vibratebreath.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vibratebreath.room.entities.Breath

@Dao
interface DaoBreath {

    @Query("SELECT * FROM Breath")
    fun findAll() : List<Breath>

    @Query("SELECT * FROM Breath WHERE user=:user")
    fun findAllByUser(user:String) : List<Breath>

    @Query("SELECT * FROM Breath WHERE user=:user AND breathType=:breathType")
    fun findAllByUserAndBreathType(user:String, breathType:String) : List<Breath>

    @Insert
    fun save(breath : Breath) : Long

    @Query("UPDATE Breath SET name=:name, description=:description, benefits=:benefits, breathType=:breathType WHERE id=:id")
    fun update(name:String, description:String, benefits:String, breathType:String, id:Long) : Int

    @Query("DELETE FROM Breath WHERE id=:id")
    fun delete(id:Long)

}