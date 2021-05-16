package com.example.epifier.repository.remote

import retrofit2.Response

interface ApiHelper {
    suspend fun getNothing(): Response<Nothing>
}