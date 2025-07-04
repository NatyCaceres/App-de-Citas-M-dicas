package com.example.ejercicio2bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface horarioDao {
    @Insert
    suspend fun insertar(horario: horario): Long
    @Query("SELECT * FROM horario WHERE id_doctor = :idDoctor")
    suspend fun obtenerPorDoctor(idDoctor: Int): List<horario>
    @Query("SELECT * FROM horario WHERE dia || ' - ' || hora_inicio || ' a ' || hora_fin = :texto LIMIT 1")
    suspend fun obtenerPorTexto(texto: String): horario
    @Query("SELECT * FROM horario")
    suspend fun obtenerTodos(): List<horario>
}

