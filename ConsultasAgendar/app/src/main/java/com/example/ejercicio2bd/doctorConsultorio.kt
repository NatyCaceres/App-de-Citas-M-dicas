package com.example.ejercicio2bd

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "doctor_consultorio",
    primaryKeys = ["id_doctor", "id_consultorio"],
    foreignKeys = [
        ForeignKey(entity = doctor::class,
            parentColumns = ["id_doctor"],
            childColumns = ["id_doctor"],
            onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = consultorio::class,
            parentColumns = ["id_consultorio"],
            childColumns = ["id_consultorio"],
            onDelete = ForeignKey.CASCADE)
    ],
)
data class doctorConsultorio(
    val id_doctor: Int,
    val id_consultorio: Int
)
