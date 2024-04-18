package com.example.prueba_clientktor.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prueba_clientktor.data.ApiService
import com.example.prueba_clientktor.data.CustomerRepository
import com.example.prueba_clientktor.data.httpClientAndroid
import com.example.prueba_clientktor.model.Customer
import io.ktor.http.isSuccess
import kotlinx.coroutines.launch

class CustomerViewModel : ViewModel() {

    private val repository = CustomerRepository(ApiService(httpClientAndroid))
    private val _customers = MutableLiveData<List<Customer>>()
    val customers: LiveData<List<Customer>> = _customers

    init {
        fetchCustomers()
    }

    private fun fetchCustomers() {
        viewModelScope.launch {
            val repository = repository // Verificar si el repositorio es nulo
            val customersResult = repository.getCustomers()
            Log.d("CustomerViewModel", "Customers result: $customersResult")
            _customers.value = customersResult // Verificar si customersResult es nulo
        }
    }

    suspend fun createCustomer(customer: Customer) {
        val response = repository.postCustomer(customer)
        // Verificar si la solicitud POST fue exitosa
        if (response.status.isSuccess()) {
            // Obtener la lista actual de clientes del LiveData
            val currentCustomers = _customers.value.orEmpty().toMutableList()

            // Agregar el nuevo cliente a la lista
            currentCustomers.add(customer)

            // Actualizar el LiveData con la lista actualizada de clientes
            _customers.value = currentCustomers.toList()
        } else {
            Log.e("CustomerViewModel", "Error al crear el cliente: ${response.status}")
        }
    }
}
