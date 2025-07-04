package com.example.ejercicio2bd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class registroActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var edtNombre: EditText
    private lateinit var edtCorreo: EditText
    private lateinit var edtEdad: EditText
    private lateinit var edtRut: EditText
    private lateinit var edtContraseña: EditText
    private lateinit var txtVolverLogin: TextView
    private lateinit var btnRegistrarse: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        db = AppDatabase.getDatabase(this)
        initViews()
        setupListeners()
    }
    private fun initViews() {
        edtNombre = findViewById(R.id.edtNombre)
        edtCorreo = findViewById(R.id.edtCorreo)
        edtContraseña = findViewById(R.id.edtContraseña)
        edtEdad = findViewById(R.id.edtEdad)
        edtRut = findViewById(R.id.edtRut)
        txtVolverLogin = findViewById(R.id.txtVolverLogin)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)
    }
    private fun setupListeners() {
        btnRegistrarse.setOnClickListener {
            validarRegistro()
        }
        txtVolverLogin.setOnClickListener {
            startActivity(Intent(this, loginActivity::class.java))
            finish()
        }
    }
    private fun validarRegistro() {
        val nombre = edtNombre.text.toString()
        val edad = edtEdad.text.toString()
        val rut = edtRut.text.toString()
        val correo = edtCorreo.text.toString()
        val contraseña = edtContraseña.text.toString()

        if(nombre.isEmpty() || edad.isEmpty()  || rut.isEmpty() || correo.isEmpty() || contraseña.isEmpty()){
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        if(edad.toInt() < 0){
            Toast.makeText(this, "La edad no puede ser negativa", Toast.LENGTH_SHORT).show()
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            val usuario = usuario(correo=correo, contraseña=contraseña)
            val idusuario = db.usuarioDao().insertar(usuario)
            val nuevoPaciente = paciente(
                nombre = nombre,
                edad = edad.toInt(),
                rut = rut.toInt(),
                id_usuario = idusuario.toInt()
            )
            db.pacienteDao().insertar(nuevoPaciente)
            withContext(Dispatchers.Main) {//interactúa con la interfaz gráfica
                Toast.makeText(this@registroActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@registroActivity, loginActivity::class.java))
                finish()
            }
        }
    }
}
