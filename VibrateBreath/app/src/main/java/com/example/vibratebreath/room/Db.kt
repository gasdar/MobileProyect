package com.example.vibratebreath.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vibratebreath.room.dao.DaoBreath
import com.example.vibratebreath.room.dao.DaoUser
import com.example.vibratebreath.room.entities.Breath
import com.example.vibratebreath.room.entities.User

@Database(
    entities = [User::class, Breath::class],
    version = 1
)
abstract class Db : RoomDatabase() {

    abstract fun daoUser() : DaoUser
    abstract fun daoBreath() : DaoBreath

}