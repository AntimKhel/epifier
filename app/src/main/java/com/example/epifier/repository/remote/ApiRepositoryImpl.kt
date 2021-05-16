package com.example.epifier.repository.remote

import retrofit2.Response
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(private val apiService: ApiService): ApiHelper {
    override suspend fun getNothing(): Response<Nothing> {
        return apiService.getNothing()
    }
}