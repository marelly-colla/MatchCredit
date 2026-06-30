package com.example.matchcredit.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "perfiles_financieros",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["usuarioId"])]
)
data class PerfilFinanciero(
    @PrimaryKey(autoGenerate = true)
    val perfilId: Int = 0,

    val usuarioId: Int,

    val tipoTrabajo: String,
    val ingresoMensual: Double,
    val gastosMensuales: Double,
    val deudaTotalActual: Double,
    val cuotaMensualDeudas: Double,
    val antiguedadTrabajandoMeses: Int,
    val clasificacionSbsDeclarada: String="",
    val tieneAhorros: Boolean,
    val montoAhorros: Double,
    val scoreMatchcredit: Int,
    val nivelRiesgo: String,
    val ratioEndeudamientoActual: Double,
    val capacidadPagoDisponible: Double
)