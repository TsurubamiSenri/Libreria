package com.example.libreria.Administrador

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.libreria.MainActivity
import com.example.libreria.R
import com.example.libreria.databinding.ActivityElegirRolBinding
import com.example.libreria.databinding.ActivityRegistrarAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Registrar_admin : AppCompatActivity() {

    private lateinit var binding : ActivityRegistrarAdminBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistrarAdminBinding.inflate(layoutInflater)
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

        binding.BtnRegistrarAdmin.setOnClickListener {
            ValidarInfo()
        }



    }

    var nombres =""
    var correo =""
    var password =""
    var r_password =""

    private fun ValidarInfo() {
        nombres = binding.EtNombresAdmin.text.toString().trim()
        correo = binding.EtCorreoAdmin.text.toString().trim()
        password = binding.EtPasswordAdmin.text.toString().trim()
        r_password = binding.EtRepetirPasswordAdmin.text.toString().trim()




        if(nombres.isEmpty()){
            binding.EtNombresAdmin.error = "Ingrese nombres"
            binding.EtNombresAdmin.requestFocus()
        }
        else if(correo.isEmpty()){
            binding.EtCorreoAdmin.error = "Ingrese correo"
            binding.EtCorreoAdmin.requestFocus()

        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            binding.EtCorreoAdmin.error = "Correo no válido"
            binding.EtCorreoAdmin.requestFocus()
        }
        else if(password.isEmpty()){
            binding.EtPasswordAdmin.error = "Ingrese contraseña"
            binding.EtPasswordAdmin.requestFocus()
        }
        else if(password.length <6){
            binding.EtPasswordAdmin.error = "La contraseña debe tener más de 6 carácteres"
            binding.EtPasswordAdmin.requestFocus()
        }
        else if(r_password.isEmpty()){
            binding.EtRepetirPasswordAdmin.error = "Repita la contraseña"
            binding.EtRepetirPasswordAdmin.requestFocus()
        }
        else if(password != r_password){
            binding.EtRepetirPasswordAdmin.error = "La contraseña no coincide"
            binding.EtRepetirPasswordAdmin.requestFocus()
        }
        else{
            CrearCuentaAdmin(correo, password)
        }







    }

    private fun CrearCuentaAdmin(correo: String, password: String) {
        progressDialog.setMessage("Creando cuenta")
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(correo, password)
            .addOnSuccessListener {
                AgregarInfo()
            }
            .addOnFailureListener {e ->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "No se ha creado la cuenta. (${e.message})", Toast.LENGTH_SHORT)
                    .show()
            }

    }

    private fun AgregarInfo() {
        progressDialog.setMessage("Guardando información")
        val tiempo = System.currentTimeMillis()
        val uid = firebaseAuth.uid

        val datos_admin : HashMap<String, Any?> = HashMap()
        datos_admin["uid"] = uid
        datos_admin["nombres"] = nombres
        datos_admin["correo"] = correo
        datos_admin["rol"] = "admin"
        datos_admin["tiempo de registro"] = tiempo
        datos_admin["imagen"] =""

        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(uid!!)
            .setValue(datos_admin)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Cuenta creada", Toast.LENGTH_SHORT)
                    .show()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()

            }
            .addOnFailureListener{e ->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "No se pudo guardar la información (${e.message})", Toast.LENGTH_SHORT)
                    .show()

            }
    }
}