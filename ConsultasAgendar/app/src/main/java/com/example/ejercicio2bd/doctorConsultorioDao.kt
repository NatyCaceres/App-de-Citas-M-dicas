package com.example.ejercicio2bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface doctorConsultorioDao {
    @Insert
    suspend fun insertar(doctorConsultorio: doctorConsultorio): Long
    @Query("""
        SELECT c.* FROM consultorio c
        INNER JOIN doctor_consultorio dc ON dc.id_consultorio = c.id_consultorio
        WHERE dc.id_doctor = :idDoctor
    """)
    suspend fun obtenerConsultoriosPorDoctor(idDoctor: Int): List<consultorio>
}
