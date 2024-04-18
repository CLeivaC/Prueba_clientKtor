package com.example.prueba_clientktor

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.prueba_clientktor.ui.CustomerViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CustomerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)

        viewModel.customers.observe(
            this,
            { customers ->
                Log.d("GET", "Customers received: $customers")
                // Actualizar la interfaz de usuario con la lista de clientes
            },
        )
    }

    /*private fun updateUI(customers: List<Customer>?) {
        // Implementar la lógica para actualizar la interfaz de usuario aquí
    }*/
}
