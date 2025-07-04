package com.example.ejercicio2bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface doctorDao {
    @Insert
    suspend fun insertar(doctor: doctor): Long
    @Query("SELECT * FROM doctor WHERE id_especialidad = :idEspecialidad")
    suspend fun obtenerPorEspecialidad(idEspecialidad: Int): List<doctor>
    @Query("SELECT * FROM doctor WHERE nombre = :nombre LIMIT 1")
    suspend fun obtenerPorNombre(nombre: String): doctor
    @Query("SELECT * FROM doctor")
    suspend fun obtenerTodos(): List<doctor>
    @Query("SELECT * FROM doctor WHERE id_doctor = :id")
    suspend fun obtenerPorId(id: Int): doctor
}
