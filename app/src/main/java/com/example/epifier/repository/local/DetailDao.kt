package com.example.epifier.repository.local

import androidx.room.Dao
import androidx.room.Insert
import com.example.epifier.repository.model.Detail

@Dao
interface DetailDao {
    @Insert
    fun saveDetail(detail: Detail)
}