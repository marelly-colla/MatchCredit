package com.example.matchcredit.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "productos_crediticios",
    foreignKeys = [
        ForeignKey(
            entity = Banco::class,
            parentColumns = ["bancoId"],
            childColumns = ["bancoId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TipoPrestamo::class,
            parentColumns = ["tipoPrestamoId"],
            childColumns = ["tipoPrestamoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["bancoId"]),
        Index(value = ["tipoPrestamoId"])
    ]
)
data class ProductoCrediticio(
    @PrimaryKey
    val productoId: Int,

    val bancoId: Int,
    val tipoPrestamoId: Int,

    val nombreProducto: String,
    val teaReferencialSbsPct: Double,

    val minTrabajoMeses: Int=3,

    val montoMin: Double=100.0,
    val montoMax: Double,

    val plazoMinMeses: Int,
    val plazoMaxMeses: Int,

    val ingresoMin: Double,

    val seguroDesgravamenMensualPct: Double,
    val comisionMensual: Double=0.0,
    val gastoAdministrativo: Double=0.0,

    val urlBanco: String="",
    val requisitosTexto: String=""
)