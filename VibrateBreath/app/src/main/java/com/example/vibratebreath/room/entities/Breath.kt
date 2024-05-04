package com.example.vibratebreath.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Breath {

    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
    var name : String? = null
    var description : String? = null
    var benefits : String? = null
    var breathType : String? = null
    var user : String? = null

    constructor(name: String?, description: String?, benefits: String?, breathType: String?, user: String?) {
        this.name = name
        this.description = description
        this.benefits = benefits
        this.breathType = breathType
        this.user = user
    }

}