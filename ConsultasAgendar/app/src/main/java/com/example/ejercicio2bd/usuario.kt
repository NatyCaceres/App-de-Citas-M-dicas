package com.example.ejercicio2bd

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class usuario(
    @PrimaryKey(autoGenerate = true) val id_usuario:  Int = 0,
    val correo: String,
    val contraseña: String
)
