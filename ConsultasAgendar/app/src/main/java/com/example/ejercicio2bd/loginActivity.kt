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

class loginActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var edtCorreo: EditText
    private lateinit var edtContraseña: EditText
    private lateinit var edtRut: EditText
    private lateinit var btnIngresar: Button
    private lateinit var txtRegistrarse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        db = AppDatabase.getDatabase(this)
        unitViews()
        setupListeners()
    }
    private fun unitViews() { //se asocian las variables con los elementos del layout
        edtCorreo = findViewById(R.id.edtCorreo)
        edtContraseña = findViewById(R.id.edtContraseña)
        edtRut = findViewById(R.id.edtRut)
        btnIngresar = findViewById(R.id.btnIngresar)
        txtRegistrarse = findViewById(R.id.txtRegistrarse)
    }
    private fun setupListeners() { //se asocian los clics de botones, etc
        btnIngresar.setOnClickListener {
            validarIngreso()
        }
        txtRegistrarse.setOnClickListener {
            val intent = Intent(this, registroActivity::class.java)
            startActivity(intent)
        }
    }
    private fun validarIngreso() {
        val correo = edtCorreo.text.toString()
        val contraseña = edtContraseña.text.toString()

        if(correo.isEmpty() || contraseña.isEmpty()){
            Toast.makeText(this, "Por favor ingrese correo y contraseña", Toast.LENGTH_SHORT).show()
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            val usuario = db.usuarioDao().login(correo, contraseña)
            withContext(Dispatchers.Main) {
                if (usuario != null) {
                    Toast.makeText(this@loginActivity, "Bienvenido", Toast.LENGTH_SHORT).show()
                    //  pasamos el  id_usuario
                    val intent = Intent(this@loginActivity, homeActivity::class.java)
                    intent.putExtra("idUsuario", usuario.id_usuario)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@loginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
