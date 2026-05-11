package com.example.matchcredit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombres: String,
    val correo: String,
    val contrasena: String,
    val ingreso: String,
    val deudas: String,
    val estadoTrabajo: String
)