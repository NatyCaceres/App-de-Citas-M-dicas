package com.example.ejercicio2bd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class homeActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var btnAgendar: LinearLayout
    private lateinit var btnVerCitas: LinearLayout
    private lateinit var btnCerrarSesion: Button
    private lateinit var btnHospitalCercano: LinearLayout
    private lateinit var txtBienvenida: TextView
    private var idUsuario: Int = -1// identificar al usuario que inició sesión, traido desde loginActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        db = AppDatabase.getDatabase(this)
        idUsuario = intent.getIntExtra("idUsuario", -1)//recupera el ID,si no lo encuentra, devuelve -1
        initViews()
        setupListeners()
        cargarNombre()
    }
    private fun initViews() {
        txtBienvenida = findViewById(R.id.txtBienvenida)
        btnAgendar = findViewById(R.id.btnAgendar)
        btnVerCitas = findViewById(R.id.btnVerCitas)
        btnHospitalCercano = findViewById(R.id.btnHospitalCercano)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
    }
    private fun setupListeners() {
        btnAgendar.setOnClickListener {
            val intent = Intent(this, agendar_citaActivity::class.java)
            intent.putExtra("idUsuario", idUsuario)
            startActivity(intent)
        }
        btnVerCitas.setOnClickListener {
           val intent = Intent(this, ver_citasActivity::class.java)
            intent.putExtra("idUsuario", idUsuario)
            startActivity(intent)
        }
        btnCerrarSesion.setOnClickListener {
            finish()
        }
        btnHospitalCercano.setOnClickListener {
            val intent = Intent(this, MapsConsultoriosActivity::class.java)
            startActivity(intent)
        }
    }
    private fun cargarNombre() {
        CoroutineScope(Dispatchers.IO).launch {
            val paciente = db.pacienteDao().buscarPorUsuario(idUsuario)
            withContext(Dispatchers.Main) {
                paciente?.let {// solo se ejecuta si paciente no es nulo
                    //it representa al paciente y accede al atributo nombre
                    txtBienvenida.text = "Bienvenido, ${it.nombre}"
                }
            }
        }
    }
}