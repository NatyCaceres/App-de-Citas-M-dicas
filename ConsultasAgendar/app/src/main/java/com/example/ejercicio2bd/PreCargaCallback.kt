package com.example.ejercicio2bd

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PreCargaCallback(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(context)

            // 1. Especialidades
            val idCardio = database.especialidadDao().insertar(especialidad(nombre = "Cardiología"))
            val idPediatria = database.especialidadDao().insertar(especialidad(nombre = "Pediatría"))
            val idMedicoGeneral = database.especialidadDao().insertar(especialidad(nombre = "Médico General"))
            val idPsicologia = database.especialidadDao().insertar(especialidad(nombre = "Psicología"))
            val idNeurologia = database.especialidadDao().insertar(especialidad(nombre = "Neurología"))
            val idOftalmologia = database.especialidadDao().insertar(especialidad(nombre = "Oftalmología"))

            // 2. Doctores
            val idDrCarlos = database.doctorDao().insertar(doctor(nombre = "Dr. Carlos Rodríguez", id_especialidad = idMedicoGeneral.toInt()))
            val idDrJuan = database.doctorDao().insertar(doctor(nombre = "Dr. Juan Pérez", id_especialidad = idCardio.toInt()))
            val idDraAna = database.doctorDao().insertar(doctor(nombre = "Dra. Ana García", id_especialidad = idPediatria.toInt()))
            val idDraMaria = database.doctorDao().insertar(doctor(nombre = "Dra. María López", id_especialidad = idPsicologia.toInt()))
            val idDrLuis = database.doctorDao().insertar(doctor(nombre = "Dr. Luis Martínez", id_especialidad = idNeurologia.toInt()))
            val idDraSofia = database.doctorDao().insertar(doctor(nombre = "Dra. Sofía Figueroa", id_especialidad = idOftalmologia.toInt()))

            // 3. Consultorios
            val idCons1 = database.consultorioDao().insertar(
                consultorio(
                    nombre = "CESFAM Padre Joan Alsina",
                    direccion = "Av. Sta. Rosa 1234, Santiago, Región Metropolitana",
                    latitud = -33.6141379,
                    longitud = -70.6982228
                )
            )
            val idCons2 = database.consultorioDao().insertar(
                consultorio(
                    nombre = "Hospital San Borja Arriarán",
                    direccion = "Av. Sta. Rosa 1234, Santiago, Región Metropolitana",
                    latitud = -33.4759522,
                    longitud = -70.6843776
                )
            )
            val idCons3 = database.consultorioDao().insertar(
                consultorio(
                    nombre = "Hospital Dr. Sótero del Río",
                    direccion = "Av. Concha y Toro 3459, Puente Alto",
                    latitud = -33.576944,
                    longitud = -70.579167
                )
            )

            // 4. Relaciones doctor-consultorio
            database.doctorConsultorioDao().insertar(doctorConsultorio(id_doctor = idDrJuan.toInt(), id_consultorio = idCons1.toInt()))
            database.doctorConsultorioDao().insertar(doctorConsultorio(id_doctor = idDrJuan.toInt(), id_consultorio = idCons2.toInt()))
            database.doctorConsultorioDao().insertar(doctorConsultorio(id_doctor = idDraAna.toInt(), id_consultorio = idCons2.toInt()))
            database.doctorConsultorioDao().insertar(doctorConsultorio(id_doctor = idDrCarlos.toInt(), id_consultorio = idCons1.toInt()))
            database.doctorConsultorioDao().insertar(doctorConsultorio(id_doctor = idDraSofia.toInt(), id_consultorio = idCons3.toInt()))
            database.doctorConsultorioDao().insertar(doctorConsultorio(id_doctor = idDrLuis.toInt(), id_consultorio = idCons3.toInt()))
            database.doctorConsultorioDao().insertar(doctorConsultorio(id_doctor = idDraMaria.toInt(), id_consultorio = idCons2.toInt()))
            database.doctorConsultorioDao().insertar(doctorConsultorio(id_doctor = idDraMaria.toInt(), id_consultorio = idCons1.toInt()))
            // 5. Horarios de los doctores
            database.horarioDao().insertar(horario(dia = "Lunes", hora_inicio = "09:00", hora_fin = "11:00", id_doctor = idDrCarlos.toInt()))
            database.horarioDao().insertar(horario(dia = "Martes", hora_inicio = "13:00:00", hora_fin = "15:00", id_doctor = idDrCarlos.toInt()))
            database.horarioDao().insertar(horario(dia = "Miércoles", hora_inicio = "15:30", hora_fin = "18:00", id_doctor = idDrCarlos.toInt()))

            database.horarioDao().insertar(horario(dia = "Lunes", hora_inicio = "09:00", hora_fin = "12:00", id_doctor = idDrJuan.toInt()))
            database.horarioDao().insertar(horario(dia = "Martes", hora_inicio = "13:00:00", hora_fin = "15:00", id_doctor = idDrJuan.toInt()))
            database.horarioDao().insertar(horario(dia = "Miércoles", hora_inicio = "15:30", hora_fin = "18:00", id_doctor = idDrJuan.toInt()))

            database.horarioDao().insertar(horario(dia = "Lunes", hora_inicio = "10:00", hora_fin = "13:00", id_doctor = idDraAna.toInt()))
            database.horarioDao().insertar(horario(dia = "Martes", hora_inicio = "10:00", hora_fin = "13:00", id_doctor = idDraAna.toInt()))
            database.horarioDao().insertar(horario(dia = "Jueves", hora_inicio = "15:00", hora_fin = "18:00", id_doctor = idDraAna.toInt()))

            database.horarioDao().insertar(horario(dia = "Viernes", hora_inicio = "11:00", hora_fin = "11:30", id_doctor = idDraSofia.toInt()))
            database.horarioDao().insertar(horario(dia = "Sabado", hora_inicio = "12:00", hora_fin = "12:30", id_doctor = idDraSofia.toInt()))
            database.horarioDao().insertar(horario(dia = "Miercoles", hora_inicio = "14:00", hora_fin = "14:30", id_doctor = idDraSofia.toInt()))

            database.horarioDao().insertar(horario(dia = "Lunes", hora_inicio = "18:00", hora_fin = "18:30", id_doctor = idDraMaria.toInt()))
            database.horarioDao().insertar(horario(dia = "Miercoles", hora_inicio = "17:30", hora_fin = "18:00", id_doctor = idDraMaria.toInt()))
            database.horarioDao().insertar(horario(dia = "Jueves", hora_inicio = "16:00", hora_fin = "16:30", id_doctor = idDraMaria.toInt()))

            database.horarioDao().insertar(horario(dia = "Domingo", hora_inicio = "09:00", hora_fin = "09:30", id_doctor = idDrLuis.toInt()))
            database.horarioDao().insertar(horario(dia = "Martes", hora_inicio = "10:30", hora_fin = "10:30", id_doctor = idDrLuis.toInt()))
            database.horarioDao().insertar(horario(dia = "Viernes", hora_inicio = "12:00", hora_fin = "12:30", id_doctor = idDrLuis.toInt()))
        }
    }
}
