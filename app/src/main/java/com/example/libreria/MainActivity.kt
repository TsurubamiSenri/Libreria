package com.example.libreria

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.libreria.Fragmentos_Admin.fragment_admin_cuenta
import com.example.libreria.Fragmentos_Admin.fragment_admin_dashboard
import com.example.libreria.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ComprobarSesion()
        VerFragmentoDashboard()

        binding.BtnNVAdmin.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.Menu_panel ->{
                    VerFragmentoDashboard()
                    true
                }
                R.id.Menu_cuenta ->{
                    VerFragmentoCuenta()
                    true
                }
                else ->{
                    false
                }
            }
        }
    }

    private fun VerFragmentoDashboard(){
        val nombre_titulo = "Dashboard"
        binding.TituloRLAdmin.text = nombre_titulo

        val fragment = fragment_admin_dashboard()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentsAdmin.id, fragment, "Fragment dashboard")
        fragmentTransaction.commit()

    }

    private fun VerFragmentoCuenta(){
        val nombre_titulo = "Cuenta"
        binding.TituloRLAdmin.text = nombre_titulo

        val fragment = fragment_admin_cuenta()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentsAdmin.id, fragment, "Fragment cuenta")
        fragmentTransaction.commit()

    }

    private fun ComprobarSesion(){
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            startActivity(Intent(this, Elegir_rol::class.java))
            finishAffinity()
        }else{
            //Toast.makeText(applicationContext, "Bienvenido", Toast.LENGTH_SHORT).show()
        }
    }
}