package com.example.ejercicio2bd

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "doctor",
    foreignKeys = [ForeignKey(
        entity = especialidad::class,
        parentColumns = ["id_especialidad"],
        childColumns = ["id_especialidad"],
        onDelete = ForeignKey.CASCADE
    )],
)
data class doctor(
    @PrimaryKey(autoGenerate = true) val id_doctor:  Int = 0,
    val nombre: String,
    val id_especialidad: Int
)
