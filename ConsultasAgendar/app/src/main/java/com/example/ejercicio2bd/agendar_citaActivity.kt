package com.example.ejercicio2bd

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class agendar_citaActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    private var idUsuario: Int = -1

    private lateinit var spEspecialidad: Spinner
    private lateinit var spDoctor: Spinner
    private lateinit var spConsultorio: Spinner
    private lateinit var spHorario: Spinner
    private lateinit var editTextMotivo: EditText
    private lateinit var btnConfirmar: Button
    private lateinit var doctores: List<doctor>
    private lateinit var consultorios: List<consultorio>
    private lateinit var horarios: List<horario>
    private lateinit var especialidades: List<especialidad>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agendar_cita)
        db = AppDatabase.getDatabase(this)
        // recuperamos el ID del usuario que se paso desde homeActivity
        idUsuario = intent.getIntExtra("idUsuario", -1)
        initViews()
        setupListeners()

        // Cargamos las especialidades desde la BD
        cargarEspecialidades()
    }
    private fun initViews() {
        spEspecialidad = findViewById(R.id.spEspecialidad)
        spDoctor = findViewById(R.id.spDoctor)
        spConsultorio = findViewById(R.id.spConsultorio)
        spHorario = findViewById(R.id.spHorario)
        editTextMotivo = findViewById(R.id.editTextMotivo)
        btnConfirmar = findViewById(R.id.btnConfirmar)
    }
    private fun setupListeners() {
        btnConfirmar.setOnClickListener {
            agendarCita()
        }
    }
    private fun cargarEspecialidades() {
        CoroutineScope(Dispatchers.IO).launch {
            especialidades = db.especialidadDao().obtenerTodas()//todas las especialidades desde la base de datos
            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter(//se encarga de llenar el Spinner con los nombres
                    this@agendar_citaActivity,
                    android.R.layout.simple_spinner_item,
                    especialidades.map { it.nombre }//transforma una lista de objetos especialidad en una lista de Strings
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spEspecialidad.adapter = adapter//spEspecialidad mostrará los nombres de las especialidades

                // que hacer cuando el usuario selecciona una especialidad: se cargan los doctores asociados
                spEspecialidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        val idEspecialidad = especialidades[position].id_especialidad
                        cargarDoctores(idEspecialidad)
                    }
                    // si no se selecciona nada, se deja vacio
                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
            }
        }
    }
    private fun cargarDoctores(idEspecialidad: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            doctores = db.doctorDao().obtenerPorEspecialidad(idEspecialidad)
            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter(
                    this@agendar_citaActivity,
                    android.R.layout.simple_spinner_item,
                    doctores.map { it.nombre }
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spDoctor.adapter = adapter

                spDoctor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        val idDoctor = doctores[position].id_doctor
                        cargarConsultorios(idDoctor)
                        cargarHorarios(idDoctor)
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
            }
        }
    }
    private fun cargarConsultorios(idDoctor: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            consultorios = db.doctorConsultorioDao().obtenerConsultoriosPorDoctor(idDoctor)
            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter(
                    this@agendar_citaActivity,
                    android.R.layout.simple_spinner_item,
                    consultorios.map { it.nombre }
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spConsultorio.adapter = adapter
            }
        }
    }
    private fun cargarHorarios(idDoctor: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            horarios = db.horarioDao().obtenerPorDoctor(idDoctor)
            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter(
                    this@agendar_citaActivity,
                    android.R.layout.simple_spinner_item,
                    horarios.map { "${it.dia} - ${it.hora_inicio} a ${it.hora_fin}" }
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spHorario.adapter = adapter
            }
        }
    }
    private fun agendarCita() {
        val motivoTexto = editTextMotivo.text.toString().trim()
        val doctorSeleccionado = doctores.getOrNull(spDoctor.selectedItemPosition)
        val consultorioSeleccionado = consultorios.getOrNull(spConsultorio.selectedItemPosition)
        val horarioSeleccionado = horarios.getOrNull(spHorario.selectedItemPosition)
        // Validación básica
        if (motivoTexto.isEmpty() || doctorSeleccionado == null || consultorioSeleccionado == null || horarioSeleccionado == null) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        // Creamos la nueva cita, usando los datos que selecciono el usuario
        val nuevaCita = cita(
            id_usuario = idUsuario,
            id_doctor = doctorSeleccionado.id_doctor,
            id_consultorio = consultorioSeleccionado.id_consultorio,
            id_horario = horarioSeleccionado.id_horario,
            motivo = motivoTexto
        )
        // Guardamos en la BD
        CoroutineScope(Dispatchers.IO).launch {
            db.citaDao().insertar(nuevaCita)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@agendar_citaActivity, "Cita agendada exitosamente", Toast.LENGTH_SHORT).show()
                finish() // Vuelve a la pantalla anterior
            }
        }
    }
}
