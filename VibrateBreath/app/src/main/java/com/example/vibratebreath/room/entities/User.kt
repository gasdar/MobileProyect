package com.example.vibratebreath.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User {

    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
    var name : String? = null
    var email : String? = null
    var dateOfBirth : String? = null
    var password : String? = null

    constructor(name: String?, email: String?, dateOfBirth: String?, password: String?) {
        this.name = name
        this.email = email
        this.dateOfBirth = dateOfBirth
        this.password = password
    }

}