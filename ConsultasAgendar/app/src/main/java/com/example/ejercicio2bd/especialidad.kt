package com.example.ejercicio2bd

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "especialidad")
data class especialidad(
    @PrimaryKey(autoGenerate = true) val id_especialidad:  Int = 0,
    val nombre: String
)
