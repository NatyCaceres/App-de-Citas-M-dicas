package com.example.ejercicio2bd

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cita",
    foreignKeys = [
        ForeignKey(entity = usuario::class, parentColumns = ["id_usuario"], childColumns = ["id_usuario"]),
        ForeignKey(entity = doctor::class, parentColumns = ["id_doctor"], childColumns = ["id_doctor"]),
        ForeignKey(entity = consultorio::class, parentColumns = ["id_consultorio"], childColumns = ["id_consultorio"]),
        ForeignKey(entity = horario::class, parentColumns = ["id_horario"], childColumns = ["id_horario"])
    ],
)
data class cita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val id_usuario: Int,
    val id_doctor: Int,
    val id_consultorio: Int,
    val id_horario: Int,
    val motivo: String
)
