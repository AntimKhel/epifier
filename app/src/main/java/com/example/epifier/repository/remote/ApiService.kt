package com.example.epifier.repository.remote

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET
    suspend fun getNothing(): Response<Nothing>
}