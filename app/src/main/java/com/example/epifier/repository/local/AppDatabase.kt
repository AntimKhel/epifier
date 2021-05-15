package com.example.epifier.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.epifier.repository.model.Detail

@Database(entities = [Detail::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun detailDao(): DetailDao
}