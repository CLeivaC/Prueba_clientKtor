package com.example.prueba_clientktor.data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class ApiService(private val httpClient: HttpClient) {

    suspend fun getCustomers(): HttpResponse {
        return httpClient.get("http://192.168.1.241:8080/customers")
    }
}