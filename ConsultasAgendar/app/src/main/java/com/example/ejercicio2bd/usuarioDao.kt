package com.example.ejercicio2bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface usuarioDao {
    @Insert
    suspend fun insertar(usuario: usuario): Long

    @Query("SELECT * FROM usuario WHERE correo = :correo AND contraseña = :contraseña")
    suspend fun login(correo: String, contraseña: String): usuario?
}

