package com.sahla.sahla.data.manager.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sahla.sahla.data.model.entity.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = true
)
abstract class SahlaDatabase : RoomDatabase()
