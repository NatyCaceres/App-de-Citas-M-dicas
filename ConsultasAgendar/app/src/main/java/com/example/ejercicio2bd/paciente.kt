package com.example.ejercicio2bd

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "paciente",
    foreignKeys = [ForeignKey(
        entity = usuario::class,
        parentColumns = ["id_usuario"],
        childColumns = ["id_usuario"],
        onDelete = ForeignKey.CASCADE
    )],
)
data class paciente(
    @PrimaryKey(autoGenerate = true) val id_paciente:  Int = 0,
    val nombre: String,
    val edad: Int,
    val rut: Int,
    val id_usuario: Int
)

