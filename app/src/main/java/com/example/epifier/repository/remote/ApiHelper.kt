package com.example.epifier.repository.remote

import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    suspend fun getNothing(): Flow<String>
}