package com.example.ejercicio2bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface especialidadDao {
    @Insert
    suspend fun insertar(especialidad: especialidad): Long
    @Query("SELECT * FROM especialidad")
    suspend fun obtenerTodas(): List<especialidad>
}
