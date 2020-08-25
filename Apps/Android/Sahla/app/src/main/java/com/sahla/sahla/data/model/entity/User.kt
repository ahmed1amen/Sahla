package com.sahla.sahla.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(
    @PrimaryKey(autoGenerate = false)
    val userId: Int,
    val userToken: String
)