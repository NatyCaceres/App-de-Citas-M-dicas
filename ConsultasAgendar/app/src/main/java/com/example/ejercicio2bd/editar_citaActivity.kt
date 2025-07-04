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

class editar_citaActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase//acceso a la bd
    private var idUsuario: Int = -1
    private var idCita: Int = -1
    private lateinit var spEspecialidad: Spinner
    private lateinit var spDoctor: Spinner
    private lateinit var spConsultorio: Spinner
    private lateinit var spHorario: Spinner
    private lateinit var editTextMotivo: EditText
    private lateinit var btnGuardar: Button

    private lateinit var especialidades: List<especialidad>
    private lateinit var doctores: List<doctor>
    private lateinit var consultorios: List<consultorio>
    private lateinit var horarios: List<horario>
    // cita que se quiere editar
    private var citaActual: cita? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_cita)

        db = AppDatabase.getDatabase(this)

        // Obtenemos ID usuario y cita de Intent
        idUsuario = intent.getIntExtra("idUsuario", -1)
        idCita = intent.getIntExtra("idCita", -1)

        initViews()
        setupListeners()
        // Cargamos datos de la cita y las especialidades para llenar los spinners
        cargarCitaYEspecialidades()
    }
    private fun initViews() {
        spEspecialidad = findViewById(R.id.spEspecialidad)
        spDoctor = findViewById(R.id.spDoctor)
        spConsultorio = findViewById(R.id.spConsultorio)
        spHorario = findViewById(R.id.spHorario)
        editTextMotivo = findViewById(R.id.editTextMotivo)
        btnGuardar = findViewById(R.id.btnActualizar)
    }
    private fun setupListeners() {
        btnGuardar.setOnClickListener {
            guardarCambios()
        }
    }
    // Cargamos la cita de la base de datos y las especialidades
    private fun cargarCitaYEspecialidades() {
        CoroutineScope(Dispatchers.IO).launch {
            citaActual = db.citaDao().obtenerPorId(idCita)//idCita, que fue recibido por Intent.
            especialidades = db.especialidadDao().obtenerTodas()
            withContext(Dispatchers.Main) {
                if (citaActual == null) {
                    Toast.makeText(this@editar_citaActivity, "Cita no encontrada", Toast.LENGTH_SHORT).show()
                    finish()
                    //Si no esta la cita, muestra el mensaje y solta solo del bloque de interfaz
                    return@withContext
                }
                // configura y llena el Spinner
                val adapterEsp = ArrayAdapter(
                    this@editar_citaActivity,
                    android.R.layout.simple_spinner_item,
                    especialidades.map { it.nombre }
                )
                adapterEsp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)//como se vere, item despegable
                //nombres de especialidades y se ven las opciones
                spEspecialidad.adapter = adapterEsp
                // Preseleccionar especialidad segun citaActual
                cargarDoctoresYSeleccionEspecialidad()
            }
        }
    }
    // Cargamos doctores y preseleccionamos el doctor, luego consultorios y horarios según doctor
    private fun cargarDoctoresYSeleccionEspecialidad() {
        // Encontrar especialidad del doctor de la cita
        CoroutineScope(Dispatchers.IO).launch {
            //forzamos que no sea null (porque ya fue cargada antes)
            val doctorCita = db.doctorDao().obtenerPorId(citaActual!!.id_doctor)
            val indexEspecialidad = especialidades.indexOfFirst { it.id_especialidad == doctorCita.id_especialidad }

            // Cargamos doctores de esa especialidad
            val idEspecialidad = doctorCita.id_especialidad
            doctores = db.doctorDao().obtenerPorEspecialidad(idEspecialidad)
            withContext(Dispatchers.Main) {
                // Si encontramos la especialidad, la seleccionamos en el Spinner.
                if (indexEspecialidad != -1) spEspecialidad.setSelection(indexEspecialidad)
                // llenar el Spinner de doctores
                val adapterDoc = ArrayAdapter(
                    this@editar_citaActivity,
                    android.R.layout.simple_spinner_item,
                    doctores.map { it.nombre }
                )
                adapterDoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spDoctor.adapter = adapterDoc

                // Preseleccionamos el doctor
                val indexDoctor = doctores.indexOfFirst { it.id_doctor == citaActual!!.id_doctor }
                if (indexDoctor != -1) spDoctor.setSelection(indexDoctor)

                // Cargamos consultorios y horarios con el doctor seleccionado
                cargarConsultoriosYHorarios(doctores[indexDoctor].id_doctor)

                // Cuando el usuario selecciona otra especialidad, actualizamos la lista de doctores.
                spEspecialidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    //se ejecuta automaticamente cuando se selecciona una opcion
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        // especialidades[2]
                        val idEsp = especialidades[position].id_especialidad
                        //cargar todos los doctores que tienen esa especialidad
                        actualizarDoctoresPorEspecialidad(idEsp)
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }

                // selecciona otro doctor, se recargan consultorios y horarios.
                spDoctor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        val idDoc = doctores[position].id_doctor
                        cargarConsultoriosYHorarios(idDoc)
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
            }
        }
    }
    // Actualiza spinner de doctores cuando cambia especialidad
    private fun actualizarDoctoresPorEspecialidad(idEspecialidad: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            doctores = db.doctorDao().obtenerPorEspecialidad(idEspecialidad)
            withContext(Dispatchers.Main) {
                val adapterDoc = ArrayAdapter(
                    this@editar_citaActivity,
                    android.R.layout.simple_spinner_item,
                    doctores.map { it.nombre }
                )
                adapterDoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spDoctor.adapter = adapterDoc//nombres de doctores filtrados por especialidad
                if (doctores.isNotEmpty()) {
                    spDoctor.setSelection(0)
                }
            }
        }
    }
    // Cargar consultorios y horarios del doctor seleccionado y preseleccionar si estamos en edición
    private fun cargarConsultoriosYHorarios(idDoctor: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            consultorios = db.doctorConsultorioDao().obtenerConsultoriosPorDoctor(idDoctor)
            horarios = db.horarioDao().obtenerPorDoctor(idDoctor)

            withContext(Dispatchers.Main) {
                // Adaptadores para consultorios y horarios
                val adapterConsultorio = ArrayAdapter(
                    this@editar_citaActivity,
                    android.R.layout.simple_spinner_item,
                    consultorios.map { it.nombre }
                )
                adapterConsultorio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spConsultorio.adapter = adapterConsultorio

                val adapterHorario = ArrayAdapter(
                    this@editar_citaActivity,
                    android.R.layout.simple_spinner_item,
                    horarios.map { "${it.dia} - ${it.hora_inicio} a ${it.hora_fin}" }
                )
                adapterHorario.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spHorario.adapter = adapterHorario

                //  Buscamos el índice que coincide con el de la cita original (citaActual).
                val indexConsultorio = consultorios.indexOfFirst { it.id_consultorio == citaActual!!.id_consultorio }
                if (indexConsultorio != -1) spConsultorio.setSelection(indexConsultorio)

                val indexHorario = horarios.indexOfFirst { it.id_horario == citaActual!!.id_horario }
                if (indexHorario != -1) spHorario.setSelection(indexHorario)

                // Se carga el texto del motivo que ya tenia la cita en el EditText
                editTextMotivo.setText(citaActual!!.motivo)
            }
        }
    }

    // Guardar cambios en la cita
    private fun guardarCambios() {
        val motivoTexto = editTextMotivo.text.toString().trim()
        //accede al elemento sleccionado y si no existe devulve null( indica la posicion actual seleccionada.)
        val doctorSeleccionado = doctores.getOrNull(spDoctor.selectedItemPosition)
        val consultorioSeleccionado = consultorios.getOrNull(spConsultorio.selectedItemPosition)
        val horarioSeleccionado = horarios.getOrNull(spHorario.selectedItemPosition)

        // Validaciones basicas
        if (motivoTexto.isEmpty() || doctorSeleccionado == null || consultorioSeleccionado == null || horarioSeleccionado == null) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        // Crear cita actualizada con mismos IDs que la original
        val citaActualizada = cita(
            id = idCita,
            id_usuario = idUsuario,
            //doctor seleccionado, accede al id de ese doctor
            id_doctor = doctorSeleccionado.id_doctor,
            id_consultorio = consultorioSeleccionado.id_consultorio,
            id_horario = horarioSeleccionado.id_horario,
            motivo = motivoTexto
        )
        // Actualizar en BD
        CoroutineScope(Dispatchers.IO).launch {
            db.citaDao().actualizar(citaActualizada)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@editar_citaActivity, "Cita actualizada correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
