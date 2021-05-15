package com.example.epifier.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detail")
data class Detail (@PrimaryKey val pan: String, val yob: Int)