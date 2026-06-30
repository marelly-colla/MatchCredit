package com.example.matchcredit.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "tipos_prestamo")
data class TipoPrestamo(
    @PrimaryKey
    val tipoPrestamoId: Int,

    val nombre: String,
    val descripcion: String=""
)