package com.example.libreria.Administrador

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.libreria.MainActivity
import com.example.libreria.R
import com.example.libreria.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
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

        binding.BtnLoginAdmin.setOnClickListener {
            ValidarInfo()
        }
    }

    private var email = ""
    private var password = ""

    private fun ValidarInfo() {
        email = binding.EtCorreoAdmin.text.toString().trim()
        password = binding.EtPasswordAdmin.text.toString().trim()

        if(email.isEmpty()){
            binding.EtCorreoAdmin.error = "Ingrese correo"
            binding.EtCorreoAdmin.requestFocus()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.EtCorreoAdmin.error = "Correo inválido"
            binding.EtCorreoAdmin.requestFocus()
        }
        else if(password.isEmpty()){
            binding.EtPasswordAdmin.error = "Ingrese la contraseña"
            binding.EtPasswordAdmin.requestFocus()
        }
        else{
            LoginAdmin()
        }
    }

    private fun LoginAdmin(){
        progressDialog.setMessage("Iniciando sesión")
        progressDialog.show()


        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this@Login, MainActivity::class.java))
                finishAffinity()

            }
            .addOnFailureListener {e ->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "No se pudo iniciar sesión (${e.message})", Toast.LENGTH_SHORT).show()
            }
    }
}

