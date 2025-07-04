package com.example.ejercicio2bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface pacienteDao {
    @Insert
    suspend fun insertar(paciente: paciente): Long
    @Query("SELECT * FROM paciente WHERE id_usuario = :idUsuario LIMIT 1")
    suspend fun buscarPorUsuario(idUsuario: Int): paciente?
}


