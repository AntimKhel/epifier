package com.example.epifier.repository.remote

import retrofit2.http.GET

interface ApiService {
    @GET
    suspend fun getNothing(): String
}