package com.example.ejercicio2bd

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "consultorio")
data class consultorio(
    @PrimaryKey(autoGenerate = true) val id_consultorio:  Int = 0,
    val nombre: String,
    val direccion: String,
    val latitud: Double,
    val longitud: Double
)
