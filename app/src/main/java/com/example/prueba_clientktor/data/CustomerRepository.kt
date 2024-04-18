package com.example.prueba_clientktor.data
import com.example.prueba_clientktor.model.Customer
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CustomerRepository(private val apiService: ApiService) {

    suspend fun getCustomers(): List<Customer> {
        return try {
            val response: HttpResponse = apiService.getCustomers()
            when (response.status) {
                HttpStatusCode.OK -> {
                    val responseBody: String = response.bodyAsText()
                    Json.decodeFromString<List<Customer>>(responseBody)
                }
                else -> {
                    // Manejar el caso de respuesta no exitosa
                    // Registrar un mensaje de error o lanzar una excepción según sea necesario
                    throw Exception("Failed to fetch customers: ${response.status}")
                }
            }
        } catch (e: Exception) {
            // Manejar la excepción
            // Registrar un mensaje de error o lanzar una excepción según sea necesario
            throw Exception("Error fetching customers: ${e.message}")
        }
    }


    suspend fun postCustomer(customer: Customer): HttpResponse {
        val jsonBody = Json.encodeToString(customer)
        return performPostRequest("http://example.com/api/customers", jsonBody)
    }
}