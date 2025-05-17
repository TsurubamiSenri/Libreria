package com.example.libreria.Administrador

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.libreria.MainActivity
import com.example.libreria.R
import com.example.libreria.databinding.ActivityAgregarCategoriaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Agregar_categoria : AppCompatActivity() {

    private lateinit var binding : ActivityAgregarCategoriaBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarCategoriaBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.IbRegresar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.AgregarCategoriaBD.setOnClickListener {
            ValidarDatos()
        }
    }

    private var categoria =""
    private fun ValidarDatos() {
        categoria = binding.EtCategoria.text.toString().trim()
        if(categoria.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese una categoría", Toast.LENGTH_SHORT).show()
        }
        else{
            AgregarCategoriaBD()
        }

    }

    private fun AgregarCategoriaBD() {
        progressDialog.setMessage("Agregando categoría")
        progressDialog.show()

        val tiempo = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$tiempo"
        hashMap["categoria"] = categoria
        hashMap["tiempo"] = tiempo
        hashMap["uid"] = "${firebaseAuth.uid}"
        val ref = FirebaseDatabase.getInstance().getReference("Categorias")
        ref.child("$tiempo")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Categoría agregadada", Toast.LENGTH_SHORT).show()
                binding.EtCategoria.setText("")
                startActivity(Intent(this@Agregar_categoria, MainActivity::class.java))
                finishAffinity()

            }
            .addOnFailureListener {e ->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "No se agregó la categoría ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }
}