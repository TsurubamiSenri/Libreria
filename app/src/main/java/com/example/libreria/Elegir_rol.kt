package com.example.libreria

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.libreria.Administrador.Login
import com.example.libreria.Administrador.Registrar_admin
import com.example.libreria.databinding.ActivityElegirRolBinding

class Elegir_rol : AppCompatActivity() {

    private lateinit var binding : ActivityElegirRolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityElegirRolBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.BtnRolAdmin.setOnClickListener {
            //Toast.makeText(applicationContext, "Rol administrador", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@Elegir_rol, Login::class.java))
        }
        binding.BtnRolCliente.setOnClickListener {
            Toast.makeText(applicationContext, "Rol usuario", Toast.LENGTH_SHORT).show()
        }
    }
}