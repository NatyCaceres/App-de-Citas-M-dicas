package com.example.ejercicio2bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        //todas las tablas
        usuario::class,
        paciente::class,
        doctor::class,
        especialidad::class,
        consultorio::class,
        doctorConsultorio::class,
        horario::class,
        cita::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    //Conectamos cada DAO con Room
    abstract fun usuarioDao(): usuarioDao
    abstract fun pacienteDao(): pacienteDao
    abstract fun doctorDao(): doctorDao
    abstract fun especialidadDao(): especialidadDao
    abstract fun consultorioDao(): consultorioDao
    abstract fun doctorConsultorioDao(): doctorConsultorioDao
    abstract fun horarioDao(): horarioDao
    abstract fun citaDao(): citaDao

    //se crea una única instancia compartida de la base de datos
    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        //Esta funcion devuelve una unica instancia
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,//haz esta base de datos en el espacio de almacenamiento de esta app
                    AppDatabase::class.java,
                    "app_citas_medicas"
                )
                    //llama a una funcion que precarga datos la primera vez que se abre la app
                    .addCallback(PreCargaCallback(context))
                    .build()
                INSTANCE = instance//guarda la instancia para que se reutilice.
                instance
            }
        }
    }
}
