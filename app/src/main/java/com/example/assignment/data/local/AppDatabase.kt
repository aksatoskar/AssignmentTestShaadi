package com.example.assignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment.model.ProfileDetails

@Database(entities = [ProfileDetails::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}