package com.example.ejercicio2bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface consultorioDao {
    @Insert
    suspend fun insertar(consultorio: consultorio) : Long
    @Query("SELECT * FROM consultorio")
    suspend fun obtenerTodos(): List<consultorio>
    @Query("SELECT * FROM consultorio WHERE nombre = :nombre LIMIT 1")
    suspend fun obtenerPorNombre(nombre: String): consultorio
}
