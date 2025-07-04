package com.example.ejercicio2bd

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "horario",
    foreignKeys = [ForeignKey(
        entity = doctor::class, parentColumns = ["id_doctor"], childColumns = ["id_doctor"], onDelete = ForeignKey.CASCADE
    )],
)
data class horario(
    @PrimaryKey(autoGenerate = true) val id_horario: Int = 0,
    val dia: String, val hora_inicio: String, val hora_fin: String, val id_doctor: Int
)
