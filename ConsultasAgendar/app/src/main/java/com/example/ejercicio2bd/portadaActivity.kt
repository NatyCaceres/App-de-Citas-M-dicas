package com.example.ejercicio2bd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class portadaActivity : AppCompatActivity() {
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnRegistrarse: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)
        unitViews()
        setupListeners()
    }
    private fun unitViews() { //se asocian las variables con los elementos del layout
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)
    }
    private fun setupListeners() { //se asocian los clics de botones, etc
        btnIniciarSesion.setOnClickListener {
            val intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
        }
        btnRegistrarse.setOnClickListener {
            val intent = Intent(this, registroActivity::class.java)
            startActivity(intent)
        }
    }
}
