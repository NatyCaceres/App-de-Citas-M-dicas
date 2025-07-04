package com.example.ejercicio2bd

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ver_citasActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private lateinit var recyclerView: RecyclerView//se listan las citas
    private var idUsuario: Int = -1
    private lateinit var citaAdapter: CitaAdapter// el adaptador que se usara para mostrar la info
    //datos que usara el Adapter
    private lateinit var doctores: List<doctor>
    private lateinit var consultorios: List<consultorio>
    private lateinit var horarios: List<horario>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver_citas)

        db = AppDatabase.getDatabase(this)
        idUsuario = intent.getIntExtra("idUsuario", -1)//recuperamos el idUsuario
        recyclerView = findViewById(R.id.rvCitas)
        recyclerView.layoutManager = LinearLayoutManager(this)//citas en una lista vertical
        cargarDatos()
    }

    private fun cargarDatos() {
        CoroutineScope(Dispatchers.IO).launch {
            //devuelve una lista de todas las citas que ha registrado ese usuario.
            val listaCitas = db.citaDao().obtenerPorUsuario(idUsuario)
            doctores = db.doctorDao().obtenerTodos()
            consultorios = db.consultorioDao().obtenerTodos()
            horarios = db.horarioDao().obtenerTodos()

            withContext(Dispatchers.Main) {
                // Creamos el adapter y pasamos al recicleView para mostrar los datos.
                citaAdapter = CitaAdapter(
                    context = this@ver_citasActivity,
                    listaCitas = listaCitas,
                    doctores = doctores,
                    consultorios = consultorios,
                    horarios = horarios,

                    // Función que se llama cuando se presiona el botón "Editar"
                    onEditar = { cita ->
                        val intent = Intent(this@ver_citasActivity, editar_citaActivity::class.java)
                        // pasa el ID de la cita seleccionada
                        intent.putExtra("idCita", cita.id)
                        intent.putExtra("idUsuario", idUsuario)
                        startActivity(intent)
                    },

                    // Función que se llama cuando se presiona el botón "Eliminar"
                    onEliminar = { cita ->
                        eliminarCita(cita)
                    }
                )
                //el RecyclerView usa este adaptador para mostrar las citas en pantalla
                recyclerView.adapter = citaAdapter
            }
        }
    }
    private fun eliminarCita(cita: cita) {
        CoroutineScope(Dispatchers.IO).launch {
            db.citaDao().eliminar(cita)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@ver_citasActivity, "Cita eliminada", Toast.LENGTH_SHORT).show()
                cargarDatos() // actualizar la lista.
            }
        }
    }
}
