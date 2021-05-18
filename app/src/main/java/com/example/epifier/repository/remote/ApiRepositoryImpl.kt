package com.example.epifier.repository.remote

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val apiService: ApiService): ApiHelper {
    @ExperimentalCoroutinesApi
    override suspend fun getNothing(): Flow<String> {
        return callbackFlow {
            offer( apiService.getNothing())
            awaitClose { close() }
        }
    }
}