package com.example.ejercicio2bd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface citaDao {
    @Insert
    suspend fun insertar(cita: cita): Long
    @Query("SELECT * FROM cita WHERE id_usuario = :idUsuario")
    suspend fun obtenerPorUsuario(idUsuario: Int): List<cita>
    @Delete
    suspend fun eliminar(cita: cita)
    @Query("SELECT * FROM cita WHERE id = :id")
    suspend fun obtenerPorId(id: Int): cita
    @Update
    suspend fun actualizar(cita: cita)
}
