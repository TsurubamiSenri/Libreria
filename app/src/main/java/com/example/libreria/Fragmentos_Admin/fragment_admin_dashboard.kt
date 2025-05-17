package com.example.libreria.Fragmentos_Admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libreria.Administrador.AdaptarCategoria
import com.example.libreria.Administrador.Agregar_categoria
import com.example.libreria.Administrador.ModeloCategoria
import com.example.libreria.R
import com.example.libreria.databinding.ActivityLoginBinding
import com.example.libreria.databinding.FragmentAdminDashboardBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class fragment_admin_dashboard : Fragment() {

    private lateinit var binding: FragmentAdminDashboardBinding
    private lateinit var mContext : Context
    private lateinit var categoriaArrayList: ArrayList<ModeloCategoria>
    private lateinit var adaptarCategoria : AdaptarCategoria

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoriasRv.layoutManager = LinearLayoutManager(mContext)

        ListarCategorias()

        binding.BtnAgregarCategoria.setOnClickListener {
            startActivity(Intent(mContext, Agregar_categoria::class.java))
        }
        binding.AgregarPdf.setOnClickListener {

        }
    }

    private fun ListarCategorias() {
        categoriaArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Categorías").orderByChild("categoria")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                categoriaArrayList.clear()
                for(ds in snapshot.children){
                    val modelo = ds.getValue(ModeloCategoria::class.java)
                    categoriaArrayList.add(modelo!!)
                }
                adaptarCategoria = AdaptarCategoria(mContext, categoriaArrayList)
                binding.categoriasRv.adapter = adaptarCategoria
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}